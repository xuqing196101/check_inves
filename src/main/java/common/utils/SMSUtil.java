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

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
/**
 * 
 * Description: 短信工具类
 * 
 * @version 2016-9-7
 * @since JDK1.7
 */
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

    /**
     * 
     * Description: 发送短信
     * 
     * @data 2017年11月9日
     * 
     * @param mobile  手机号码，半角数字，多个号码用半角逗号分隔。
     * @param msg  短信内容，内容长度最大不能超过500个汉字（一个字母、符号等同一个汉字），使用URL使用为UTF-8编码格式。短信内容超过70个字时，会被自动拆分成多条，然后以长短信的格式发送。
     * 
     * @return "0" : 成功返回
     */
    public static String sendMsg(String mobile, String msg){
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
        Map<String, Object> map = (Map<String, Object>)JSONObject.fromObject(result);
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
}
