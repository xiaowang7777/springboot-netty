package com.wjf.github.netty;

import com.wjf.github.protobuf.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MyProtoBufEncoder extends MessageToByteEncoder<ProtoMsg.MessageInfo> {

	private final static Logger log = LoggerFactory.getLogger(MyProtoBufEncoder.class);

	private final int headVersion;
	private final int headLength;

	public MyProtoBufEncoder(int headVersion, int headLength) {
		super();
		this.headVersion = headVersion;
		this.headLength = headLength;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ProtoMsg.MessageInfo msg, ByteBuf out) throws Exception {
		byte[] bytes = msg.toByteArray();
		log.info("msg bytes:{}", Arrays.toString(bytes));
		int length = bytes.length;
		ByteBuf buffer = Unpooled.buffer(length + headLength);
		buffer.writeShort(headVersion);
		buffer.writeInt(length);
		buffer.writeBytes(bytes);
		out.writeBytes(buffer);
	}
}
