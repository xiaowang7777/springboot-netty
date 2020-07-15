package com.wjf.github.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class MyLengthDecoder extends ByteToMessageDecoder {

	private final static Logger log = LoggerFactory.getLogger(MyLengthDecoder.class);

	private final int headLength;
	private final int lengthOffset;

	public MyLengthDecoder(int headLength, int lengthOffset) {
		super();
		this.headLength = headLength;
		this.lengthOffset = lengthOffset;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < headLength) {
			return;
		}
		int length = in.getInt(in.readerIndex() + lengthOffset);
		log.info("msg bytes length:{} and readable bytes length:{}",length,in.readableBytes());
		if (in.readableBytes() < headLength + length) {
			return;
		}
		byte[] bytes = new byte[length];
		in.getBytes(in.readerIndex() + headLength, bytes);
		in.readBytes(in.readerIndex() + length + headLength);
		log.info("read bytes:{}", Arrays.toString(bytes));
		ByteBuf buffer = Unpooled.buffer(length);
		buffer.writeBytes(bytes);
		out.add(buffer);
	}
}
