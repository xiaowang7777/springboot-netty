package com.wjf.client;

import com.wjf.github.protobuf.ProtoMsgUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientIdleHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			if (NettyClient.uid == null || NettyClient.sessionId == null || NettyClient.token == null) {
				Thread.yield();
			}
			System.out.println("send a ping message");
			ctx.channel().writeAndFlush(ProtoMsgUtil.createKeepAliveRequestInfo(NettyClient.uid, NettyClient.token, NettyClient.sessionId));
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
