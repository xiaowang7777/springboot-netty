package com.wjf.github.netty;

import com.wjf.github.protobuf.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class MyProtoBufDecoder extends MessageToMessageDecoder<ByteBuf> {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		int length = msg.readableBytes();
		if (length > 0) {
			byte[] bytes = new byte[length];
			msg.getBytes(msg.readerIndex(), bytes);
			msg.readBytes(length);

			ProtoMsg.MessageInfo messageInfo = ProtoMsg.MessageInfo.parseFrom(bytes);
			out.add(messageInfo);
		}
	}
}
