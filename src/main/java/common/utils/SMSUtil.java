package common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import system.model.sms.SmsRecord;
import system.service.sms.SmsRecordService;

import com.alibaba.fastjson.JSON;
/**
 * 
 * Description: 短信工具类
 * 
 * @version 2016-9-7
 * @since JDK1.7
 */
@Component("smsUtil")
public class SMSUtil {
	
    /**
     * API文档地址   http://bcp.pro-group.cn:7002/Docs/#!easycloud-smsapi.md
     */

    /** 用户账号 **/
    public static final String ACCTNO = "100514";

    /** 密码 **/
    public static final String Passwd = "gpjEPEvx";

    /** 请求地址 **/
    public static final String SENDURL = "http://118.178.117.163/smsapi/SmsMt.php";

	@Autowired
	private SmsRecordService smsRecordService;
	
	private static SMSUtil smsUtil;
	
	public void setSmsRecordService(SmsRecordService smsRecordService) {
		this.smsRecordService = smsRecordService;
	}

	@PostConstruct
    public void init() {
		smsUtil = this;
		smsUtil.smsRecordService = this.smsRecordService;
    }

	/**
     * 
     * 
     * Description: 发送短信
     * 
     * @data 2017年11月27日
     * @param 
     * @return String
     */
    public static void sendMsg(SmsRecord smsRecord){
    	// 创建一个线程池
        /*ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(new SMSRunnable(mobile, msg));
        pool.shutdown();*/
    	String result = requestMsg(smsRecord.getReceiveNumber(),smsRecord.getSendContent());
    	smsRecord.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    	smsRecord.setSendTime(new Date());
    	smsRecord.setUpdatedAt(new Date());
    	smsRecord.setIsDeleted((short)0);
    	if("0".equals(result)){
    		smsRecord.setStatus("0");
    	}else{
    		smsRecord.setStatus("1");
    		smsRecord.setFailReason(getResultStatus(result));
    	}
    	smsUtil.smsRecordService.insertSelective(smsRecord);
    }
    
    /**
     * 
     * Description: 请求短信接口
     * 
     * @data 2017年11月9日
     * 
     * @param mobile  手机号码，半角数字，多个号码用半角逗号分隔。
     * @param msg  短信内容，内容长度最大不能超过500个汉字（一个字母、符号等同一个汉字），使用URL使用为UTF-8编码格式。短信内容超过70个字时，会被自动拆分成多条，然后以长短信的格式发送。
     * 
     * @return "0" : 成功返回
     */
    public static String requestMsg(String mobile, String msg){
        try {
			msg = URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String token = getMD5(ACCTNO + time + Passwd);
        String param = "token=" + token + "&mobile=" + mobile + "&msg=" + msg;
        String result = "";
        try {
            result = send_httpURLConnection_nobody(SENDURL, param, time);
        } catch (Exception e) {
            result = "send msg error!" + e;
        }
        @SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)JSON.parse(result);
        return (String)map.get("RetCode");
    }

    /**
     * 
     * Description: 发送请求
     * 
     * @data 2017年11月9日
     * @param 
     * @return
     */
    public static String send_httpURLConnection_nobody(String url, String param, String key) throws Exception {
        BufferedReader bufferedReader = null;
        String responseResult = "";
        HttpURLConnection httpURLConnection = null; // 创建HttpURLConnection
        try {
            // 拼接URL
            String strurl = url + "?" + param;
            URL realUrl = new URL(strurl);
            // 打开和URL之间的连接
            httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            // httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            String authorization = ACCTNO + ":" + key;
            byte[] datas = authorization.getBytes("GBK");
            String Author = new String(Base64.encodeBase64(datas));
            // String Author = Base64Util.encode("test06"+":"+key);
            httpURLConnection.setRequestProperty("Authorization", Author);
            // 根据ResponseCode判断连接是否成功
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                responseResult = " Error===" + responseCode;
            }
            // 定义BufferedReader输入流来读取URL的ResponseData
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseResult += line;
            }
        } catch (Exception e) {
            responseResult = "send post request error!" + e;
        } finally {
            httpURLConnection.disconnect();
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return responseResult;
    }

    /**
     * 
     * Description: MD5
     * 
     * @data 2017年11月9日
     * @param 
     * @return
     */
    public static String getMD5(String src) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(src.getBytes());
            byte[] s = m.digest();
            return bintoascii(s);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    public static String bintoascii(byte[] bySourceByte) {
        int len, i;
        byte tb;
        char high, tmp, low;
        String result = new String();
        len = bySourceByte.length;
        for (i = 0; i < len; i++) {
            tb = bySourceByte[i];
            tmp = (char) ((tb >>> 4) & 0x000f);
            if (tmp >= 10) {
                high = (char) ('a' + tmp - 10);
            } else {
                high = (char) ('0' + tmp);
            }
            result += high;
            tmp = (char) (tb & 0x000f);
            if (tmp >= 10) {
                low = (char) ('a' + tmp - 10);
            } else {
                low = (char) ('0' + tmp);
            }
            result += low;
        }
        return result;
    }
    
    /**
     * 
     * 
     * Description: 短信发送返回状态值转换
     * 
     * @data 2017年12月6日
     * @param 
     * @return String
     */
	public static String getResultStatus(String status) {
		String result = "其他错误";
		switch (status) {
			case "0" :
				result = "成功返回";
				break;
			case "100" :
				result = "系统忙（因平台侧原因，暂时无法处理提交的短信）";
				break;
			case "101" :
				result = "无此用户/用户未登陆";
				break;
			case "102" :
				result = "密码错";
				break;
			case "103" :
				result = "提交过快（提交速度超过流速限制）";
				break;
			case "104" :
				result = "未知错误";
				break;
			case "105" :
				result = "敏感短信（短信内容包含敏感词）";
				break;
			case "106" :
				result = "消息长度错误";
				break;
			case "107" :
				result = "无合法手机号码";
				break;
			case "108" :
				result = "手机号码个数错误";
				break;
			case "109" :
				result = "无发送额度";
				break;
			case "111" :
				result = "用户自定义扩展号超长";
				break;
			case "112" :
				result = "无此产品，用户没有订购该产品";
				break;
			case "114" :
				result = "签名在黑名单";
				break;
			case "115" :
				result = "签名不合法，未带签名";
				break;
			case "116" :
				result = "IP地址在黑名单内";
				break;
			case "117" :
				result = "IP地址认证错,请求调用的IP地址不是系统登记的IP地址";
				break;
			case "118" :
				result = "用户没有相应的发送权限";
				break;
			case "119" :
				result = "用户已过期";
				break;
			case "121" :
				result = "手机号码在黑名单";
				break;
			case "122" :
				result = "手机号码不在白名单";
				break;
			case "124" :
				result = "手机号码未找到对应运营商";
				break;
			case "125" :
				result = "手机号码格式错误";
				break;
			case "126" :
				result = "号码发送频率超速";
				break;
			case "199" :
				result = "无此类型接口权限";
				break;
		}
		return result;
	}
}
