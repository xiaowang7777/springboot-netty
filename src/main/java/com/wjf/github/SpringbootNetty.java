package com.wjf.github;

import com.wjf.github.config.NettyServerProperties;
import com.wjf.github.netty.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.wjf.github")
@SpringBootApplication
public class SpringbootNetty {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringbootNetty.class);
		NettyServer nettyServer = context.getBean(NettyServer.class);
		try {
			nettyServer.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private NettyServerProperties properties;

	@Bean(name = "bossEventGroup")
	public EventLoopGroup bossEventLoopGroup() {
		return new NioEventLoopGroup(properties.getNettyServerBossThreadCount() == null ? 1 : properties.getNettyServerBossThreadCount());
	}

	@Bean(name = "workEventGroup")
	public EventLoopGroup workEventLoopGroup() {
		return new NioEventLoopGroup(properties.getNettyServerWorkThreadCount() == null ? 1 : properties.getNettyServerWorkThreadCount());
	}

	@Bean
	public ServerBootstrap bootstrap() {
		return new ServerBootstrap();
	}

//	@Bean("webSocketServerProtocolHandler")
//	public ChannelHandler webSocketServerProtocolHandler(){
//		return new WebSocketServerProtocolHandler("/ws","WebSocket",true,65536*10);
//	}
}
