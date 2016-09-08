package ses.model.oms.util;


/**
 * 
* <p>Title:SessionStringPool </p>
* <p>Description: </p>
* <p>Company: ses </p> 
* @author tkf
* @date 2016-9-6下午5:53:58
 */
public class SessionStringPool {
	
	public static final String LOGIN_USER = "LOGIN_USER";//登陆用户
	
	public static final String LOGIN_VERIFY_CODE = "LOGIN_VERIFY_CODE";//登陆验证码
	
	public static final String CHECK_LOGIN_SMSCODE = "IS_CHECK_LOGIN_SMSCODE";	//登陆短信验证码是否被验证 :"OK"通过验证
	
	public static final String CHECK_AWARD_SMSCODE = "IS_CHECK_AWARD_SMSCODE";//是否经过短信验证
	
	public static final String CHECK_LOGIN_ORCODE = "IS_CHECK_LOGIN_ORCODE"; //登陆短信二维码
	
	public static final String CHECK_SMS_OK = "OK";//是否经过短信,二维码验证
	
	public static final String MENU_LIST = "MENU_LIST";  //登录用户的菜单
	
	public static final String FORGOT_PASS_USER = "FORGOT_PASS_USER";//密码找回用户
	
	public static final String PASS_CODE_CHECK = "PASS_CODE_CHECK";//密码找回是否通过验证码
	
	public static final String STAFF_ID_NOREGISTER = "STAFF_ID_NOREGISTER";//未注册的用户的id
	
	public static final String CHECK_UPDATEMOBILE_CODE = "CHECK_UPDATEMOBILE_CODE";//更改手机号的验证码是否验证通过,1通过
	
	public static final String LAST_LOGIN_LOG = "LAST_LOGIN_LOG";//用户最后登录信息
	
	public static final String PROVINCE_TEL = "PROVINCE_TEL";//省内电话
}
