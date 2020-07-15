package com.wjf.client;

import com.wjf.github.netty.MyLengthDecoder;
import com.wjf.github.netty.MyProtoBufDecoder;
import com.wjf.github.netty.MyProtoBufEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyClient {

	public static String uid = null;
	public static String token = null;
	public static String sessionId = null;

	public static void main(String[] args) {
		new NettyClient().start("127.0.0.1",8081);
	}

	private void start(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel channel) throws Exception {
							channel.pipeline()
									.addLast(new IdleStateHandler(3, 3, 3, TimeUnit.SECONDS))
									.addLast(new ClientIdleHandler())
									.addLast(new MyLengthDecoder(6, 2))
									.addLast(new MyProtoBufDecoder())
									.addLast(new MyProtoBufEncoder(1, 6))
									.addLast(new ClientHandler());
						}
					});
			bootstrap.connect(host, port).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				group.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
