package common.utils;


/**
 * 
 * Description: 异步调用短信接口
 * 
 * @date 2017年11月24日
 * @since JDK1.7
 */
public class SMSRunnable implements Runnable {

	private String msg;
	private String mobile;

	public SMSRunnable(String mobile, String msg) {
		this.mobile = mobile;
		this.msg = msg;
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

	@Override
	public void run() {
		String result = SMSUtil.requestMsg(mobile, msg);
		if("0".equals(result)){
			System.out.println("发送成功！");
		}else{
			System.out.println("发送失败！");
		}
	}
}