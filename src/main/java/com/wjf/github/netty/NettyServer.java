package com.wjf.github.netty;

import com.wjf.github.config.NettyServerProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;

@Component
public class NettyServer {

	private final static Logger log = LoggerFactory.getLogger(NettyServer.class);

	@Autowired
	private NettyServerProperties properties;

	@Autowired
	@Qualifier("bossEventGroup")
	private EventLoopGroup bossEventLoopGroup;

	@Autowired
	@Qualifier("workEventGroup")
	private EventLoopGroup workEventLoopGroup;

	@Autowired
	private ServerBootstrap serverBootstrap;

	private Channel channel = null;

	public void start() throws InterruptedException {
		serverBootstrap.group(bossEventLoopGroup, workEventLoopGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel channel) throws Exception {
						channel.pipeline()
								.addLast(new IdleStateHandler(properties.getNettyReadIdleTime(), properties.getNettyWriteIdleTime(), properties.getNettyAllIdleTime(), properties.getNettyIdleTimeUnit()))
								.addLast(new ServerIdleHandler())
								.addLast(new MyLengthDecoder(properties.getNettyHeadLength(), properties.getNettyLengthOffset()))
								.addLast(new MyProtoBufDecoder())
								.addLast(new MyProtoBufEncoder(properties.getNettyHeadVersion(), properties.getNettyHeadLength()))
								.addLast(new MyServerHandler());
					}
				});
		setChannelOption(properties.getNettyServerOption(), serverBootstrap);

		ChannelFuture future = serverBootstrap.bind(properties.getNettyPort()).sync();

		log.info("netty服务器绑定端口成功！");

		channel = future.channel().closeFuture().sync().channel();
	}

	private void setChannelOption(Map<String, Object> map, ServerBootstrap bootstrap) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			ChannelOption<Object> option = ChannelOption.valueOf(key);
			bootstrap.option(option, value);
		}
	}

	@PreDestroy
	public void stop() throws InterruptedException {
		if (channel != null) {
			channel.close();
			channel.parent().close();
		}
		workEventLoopGroup.shutdownGracefully().sync();
		bossEventLoopGroup.shutdownGracefully().sync();
	}

}
