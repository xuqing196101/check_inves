package common.utils;

import java.util.concurrent.Callable;

/**
 * 
 * Description: 异步调用短信接口
 * 
 * @date 2017年11月24日
 * @since JDK1.7
 */
public class SMSCallable implements Callable<String> {

	private String msg;
	private String mobile;

	public SMSCallable(String mobile, String msg) {
		this.mobile = mobile;
		this.msg = msg;
	}

	@Override
	public String call() throws Exception {
		String result = SMSUtil.requestMsg(mobile, msg);
		return result;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}