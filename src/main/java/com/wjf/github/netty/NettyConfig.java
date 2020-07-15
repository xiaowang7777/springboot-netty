package com.wjf.github.netty;


import java.util.HashMap;
import java.util.Map;

public class NettyConfig {

	private static final Map<String, String> userInfo = new HashMap<>();

	static {
		userInfo.put("admin", "admin");
		userInfo.put("zs123", "admin");
		userInfo.put("ls123", "admin");
	}

	public static String getPassWordByUserName(String userName) {
		return userInfo.get(userName);
	}



}
