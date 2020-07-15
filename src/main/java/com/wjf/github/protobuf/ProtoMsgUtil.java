package com.wjf.github.protobuf;

public class ProtoMsgUtil {

	public static ProtoMsg.MessageInfo createNewLoginResponseInfo(boolean result, int code, String msg, String token, String sessionId, String uid) {
		ProtoMsg.LoginResponse.Builder loginResponseBuilder = ProtoMsg.LoginResponse.newBuilder();
		loginResponseBuilder.setResult(result);
		loginResponseBuilder.setCode(code);
		loginResponseBuilder.setMsg(msg);
		loginResponseBuilder.setToken(token);
		loginResponseBuilder.setSessionId(sessionId);
		loginResponseBuilder.setUid(uid);

		ProtoMsg.MessageInfo.Builder messageBuilder = ProtoMsg.MessageInfo.newBuilder();
		messageBuilder.setHeadType(ProtoMsg.HeadType.Login_Response);
		messageBuilder.setLoginResponse(loginResponseBuilder);

		return messageBuilder.build();
	}

	public static ProtoMsg.MessageInfo createNewLogoutResponseInfo(boolean result, int code, String msg) {
		ProtoMsg.LogoutResponse.Builder logoutResponseBuilder = ProtoMsg.LogoutResponse.newBuilder();
		logoutResponseBuilder.setResult(result);
		logoutResponseBuilder.setCode(code);
		logoutResponseBuilder.setMsg(msg);

		ProtoMsg.MessageInfo.Builder messageBuilder = ProtoMsg.MessageInfo.newBuilder();
		messageBuilder.setHeadType(ProtoMsg.HeadType.Logout_Response);
		messageBuilder.setLogoutResponse(logoutResponseBuilder);
		return messageBuilder.build();
	}

	public static ProtoMsg.MessageInfo createNewKeepAliveResponseInfo(boolean result, int code, String msg) {
		ProtoMsg.KeepAliveResponse.Builder keepAliveResponseBuilder = ProtoMsg.KeepAliveResponse.newBuilder();
		keepAliveResponseBuilder.setResult(result);
		keepAliveResponseBuilder.setCode(code);
		keepAliveResponseBuilder.setMsg(msg);

		ProtoMsg.MessageInfo.Builder messageBuilder = ProtoMsg.MessageInfo.newBuilder();
		messageBuilder.setHeadType(ProtoMsg.HeadType.KeepAlive_Response);
		messageBuilder.setKeepAliveResponse(keepAliveResponseBuilder);
		return messageBuilder.build();
	}

	public static ProtoMsg.MessageInfo createNewMessageResponseInfo(boolean result, int code, String msg) {
		ProtoMsg.MessageResponse.Builder messageResponseBuilder = ProtoMsg.MessageResponse.newBuilder();
		messageResponseBuilder.setResult(result);
		messageResponseBuilder.setCode(code);
		messageResponseBuilder.setMsg(msg);

		ProtoMsg.MessageInfo.Builder messageBuilder = ProtoMsg.MessageInfo.newBuilder();
		messageBuilder.setHeadType(ProtoMsg.HeadType.Message_Response);
		messageBuilder.setMessageResponse(messageResponseBuilder);
		return messageBuilder.build();
	}

	public static ProtoMsg.MessageInfo createMessageNoticeInfo(String fromUid, String fromGroup, String content, String resourceUrl, long timestamp) {
		ProtoMsg.MessageNotice.Builder messageNoticeBuilder = ProtoMsg.MessageNotice.newBuilder();
		messageNoticeBuilder.setFromUid(fromUid);
		messageNoticeBuilder.setFromGroup(fromGroup);
		messageNoticeBuilder.setContent(content);
		messageNoticeBuilder.setResourceUrl(resourceUrl);
		messageNoticeBuilder.setTimestamp(timestamp);

		ProtoMsg.MessageInfo.Builder messageBuilder = ProtoMsg.MessageInfo.newBuilder();
		messageBuilder.setHeadType(ProtoMsg.HeadType.Message_Notice);
		messageBuilder.setMessageNotice(messageNoticeBuilder);
		return messageBuilder.build();
	}

	public static ProtoMsg.MessageInfo createLoginRequestInfo(String username,String password,int platform){
		ProtoMsg.LoginRequest.Builder loginRequestBuilder = ProtoMsg.LoginRequest.newBuilder();
		loginRequestBuilder.setUsername(username);
		loginRequestBuilder.setPassword(password);
		loginRequestBuilder.setPlatform(platform);

		ProtoMsg.MessageInfo.Builder messageBuilder = ProtoMsg.MessageInfo.newBuilder();
		messageBuilder.setHeadType(ProtoMsg.HeadType.Login_Request);
		messageBuilder.setLoginRequest(loginRequestBuilder);
		return messageBuilder.build();
	}

	public static ProtoMsg.MessageInfo createKeepAliveRequestInfo(String uid,String token,String sessionId){
		ProtoMsg.KeepAliveRequest.Builder keepAliveRequestBuilder = ProtoMsg.KeepAliveRequest.newBuilder();
		keepAliveRequestBuilder.setUid(uid);
		keepAliveRequestBuilder.setToken(token);
		keepAliveRequestBuilder.setSessionId(sessionId);

		ProtoMsg.MessageInfo.Builder messageBuilder = ProtoMsg.MessageInfo.newBuilder();
		messageBuilder.setHeadType(ProtoMsg.HeadType.KeepAlive_Request);
		messageBuilder.setKeepAliveRequest(keepAliveRequestBuilder);
		return messageBuilder.build();
	}
}
