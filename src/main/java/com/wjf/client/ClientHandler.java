package com.wjf.client;

import com.wjf.github.protobuf.ProtoMsg;
import com.wjf.github.protobuf.ProtoMsgUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;


public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("登录");
		ChannelFuture future = ctx.channel().writeAndFlush(ProtoMsgUtil.createLoginRequestInfo("admin", "admin", 0));
		if(future.sync().isSuccess()){
			System.out.println("成功！");
		}else {
			System.out.println("失败！");
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			if (msg instanceof ProtoMsg.MessageInfo) {
				ProtoMsg.MessageInfo messageInfo = (ProtoMsg.MessageInfo) msg;
				switch (messageInfo.getHeadType()) {
					case Login_Request:
					case Logout_Request:
					case Message_Request:
					case KeepAlive_Request:
					case Message_Notice:
					case Logout_Response:
					case Message_Response:
					case UNRECOGNIZED:
						break;
					case Login_Response:
						if (messageInfo.getLoginResponse().getResult()) {
							NettyClient.token = messageInfo.getLoginResponse().getToken();
							NettyClient.sessionId = messageInfo.getLoginResponse().getSessionId();
							NettyClient.uid = messageInfo.getLoginResponse().getUid();
							System.out.println("登陆成功！");
						}
						break;
					case KeepAlive_Response:
						System.out.println("this is a pong message");
						break;
				}
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

}
