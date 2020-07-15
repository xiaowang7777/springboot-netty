package com.wjf.github.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerIdleHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(ServerIdleHandler.class);

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				log.info("通道编号：{}---reader idle!", ctx.channel().id().asLongText());
			}
			if (event.state() == IdleState.WRITER_IDLE) {
				log.info("通道编号：{}---writer idle!", ctx.channel().id().asLongText());
			}
			if (event.state() == IdleState.ALL_IDLE) {
				log.info("通道编号：{}---all idle!", ctx.channel().id().asLongText());
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
