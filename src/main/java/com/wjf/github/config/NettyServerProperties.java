package com.wjf.github.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties(prefix = "project")
public class NettyServerProperties {
	private Integer nettyPort;
	private Long nettyReadIdleTime;
	private Long nettyWriteIdleTime;
	private Long nettyAllIdleTime;
	private TimeUnit nettyIdleTimeUnit;
	private Integer nettyServerBossThreadCount;
	private Integer nettyServerWorkThreadCount;
	private Map<String, Object> nettyServerOption = new HashMap<>();
	private Integer nettyHeadVersion;
	private Integer nettyHeadLength;
	private Integer nettyLengthOffset;
	private Integer nettyLengthCount;

	public Integer getNettyPort() {
		return nettyPort;
	}

	public void setNettyPort(Integer nettyPort) {
		this.nettyPort = nettyPort;
	}

	public Long getNettyReadIdleTime() {
		return nettyReadIdleTime;
	}

	public void setNettyReadIdleTime(Long nettyReadIdleTime) {
		this.nettyReadIdleTime = nettyReadIdleTime;
	}

	public Long getNettyWriteIdleTime() {
		return nettyWriteIdleTime;
	}

	public void setNettyWriteIdleTime(Long nettyWriteIdleTime) {
		this.nettyWriteIdleTime = nettyWriteIdleTime;
	}

	public Long getNettyAllIdleTime() {
		return nettyAllIdleTime;
	}

	public void setNettyAllIdleTime(Long nettyAllIdleTime) {
		this.nettyAllIdleTime = nettyAllIdleTime;
	}

	public TimeUnit getNettyIdleTimeUnit() {
		return nettyIdleTimeUnit;
	}

	public void setNettyIdleTimeUnit(TimeUnit nettyIdleTimeUnit) {
		this.nettyIdleTimeUnit = nettyIdleTimeUnit;
	}

	public Integer getNettyServerBossThreadCount() {
		return nettyServerBossThreadCount;
	}

	public void setNettyServerBossThreadCount(Integer nettyServerBossThreadCount) {
		this.nettyServerBossThreadCount = nettyServerBossThreadCount;
	}

	public Integer getNettyServerWorkThreadCount() {
		return nettyServerWorkThreadCount;
	}

	public void setNettyServerWorkThreadCount(Integer nettyServerWorkThreadCount) {
		this.nettyServerWorkThreadCount = nettyServerWorkThreadCount;
	}

	public Map<String, Object> getNettyServerOption() {
		return nettyServerOption;
	}

	public void setNettyServerOption(Map<String, Object> nettyServerOption) {
		this.nettyServerOption = nettyServerOption;
	}

	public Integer getNettyHeadVersion() {
		return nettyHeadVersion;
	}

	public void setNettyHeadVersion(Integer nettyHeadVersion) {
		this.nettyHeadVersion = nettyHeadVersion;
	}

	public Integer getNettyHeadLength() {
		return nettyHeadLength;
	}

	public void setNettyHeadLength(Integer nettyHeadLength) {
		this.nettyHeadLength = nettyHeadLength;
	}

	public Integer getNettyLengthOffset() {
		return nettyLengthOffset;
	}

	public void setNettyLengthOffset(Integer nettyLengthOffset) {
		this.nettyLengthOffset = nettyLengthOffset;
	}

	public Integer getNettyLengthCount() {
		return nettyLengthCount;
	}

	public void setNettyLengthCount(Integer nettyLengthCount) {
		this.nettyLengthCount = nettyLengthCount;
	}
}
