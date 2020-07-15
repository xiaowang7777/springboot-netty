package com.wjf.github.netty;

import com.wjf.github.protobuf.ProtoMsg;
import com.wjf.github.protobuf.ProtoMsgUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(MyServerHandler.class);

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("通道{}断开", ctx.channel().id().asLongText());
//		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			if (msg instanceof ProtoMsg.MessageInfo) {
				ProtoMsg.MessageInfo messageInfo = (ProtoMsg.MessageInfo) msg;
				switch (messageInfo.getHeadType()) {
					case Login_Request:
						if (loginRequest(messageInfo)) {
							UUID uuid = UUID.randomUUID();
							ctx.channel().writeAndFlush(ProtoMsgUtil.createNewLoginResponseInfo(true, 200, "登陆成功！", uuid.toString(), ctx.channel().id().asLongText(), messageInfo.getLoginRequest().getUsername()));
						} else {
							ctx.channel().writeAndFlush(ProtoMsgUtil.createNewLoginResponseInfo(false, 400, "登陆失败！", null, null, null));
						}
						break;
					case Logout_Request:
						log.info("来自{}的登出请求信息--{}", messageInfo.getLogoutRequest().getUid(), ctx.channel().id().asLongText());
						ctx.channel().writeAndFlush(ProtoMsgUtil.createNewLogoutResponseInfo(true, 200, "请求成功！"));
						break;
					case Message_Request:
//						log.info("来自{}的消息");
						break;
					case KeepAlive_Request:
						log.info("来自{}的心跳维持请求信息--{}", messageInfo.getKeepAliveRequest().getUid(), ctx.channel().id().asLongText());
						ctx.channel().writeAndFlush(ProtoMsgUtil.createNewKeepAliveResponseInfo(true, 200, "pong"));
						break;
					case Message_Notice:
					case Login_Response:
					case Logout_Response:
					case Message_Response:
					case KeepAlive_Response:
					case UNRECOGNIZED:
						log.error("意料外的信息类型！");
						break;
				}
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	private boolean loginRequest(ProtoMsg.MessageInfo messageInfo) {
		if (messageInfo.getHeadType() != ProtoMsg.HeadType.Login_Request) {
			log.error("消息错误！---{}", messageInfo.toString());
			throw new IllegalMonitorStateException();
		}
		ProtoMsg.LoginRequest loginRequest = messageInfo.getLoginRequest();

		if (NettyConfig.getPassWordByUserName(loginRequest.getUsername()) != null && NettyConfig.getPassWordByUserName(loginRequest.getUsername()).equals(loginRequest.getPassword())) {
			log.info("用户名：{}---密码：{}---登陆成功！---平台编号：{}", loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getPlatform());
			return true;
		}

		log.info("用户名：{}---密码：{}---登陆失败！用户名或密码错误！---平台编号：{}", loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getPlatform());

		return false;
	}
}
