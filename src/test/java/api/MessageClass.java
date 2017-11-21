package api;

//接口类型：互亿无线语音验证码接口。
//账户注册：请通过该地址开通账户http://sms.ihuyi.com/register.html
//注意事项：
//（1）调试期间，请仔细阅读接口文档；
//（2）请使用 用户名(例如：cf_demo123)及 APIkey来调用接口，APIkey在会员中心可以获取；
//（3）该代码仅供接入互亿无线短信接口参考使用，客户可根据实际需要自行编写；
/**
 *
 * Description: 
 *
 * @author Easong
 * @version 2017/11/15
 * @param 
 * @since JDK1.7
 */
public class MessageClass {

    private static String Url = "http://api.voice.ihuyi.com/webservice/voice.php?method=Submit";

    /*public static void main(String[] args) throws IOException, DocumentException {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        //client.getParams().setContentCharset("GBK");
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "V514549221"),//用户名是登录ihuyi.com账号名（例如：cf_demo123）
                new NameValuePair("password", "8020e961c2b22fcb2229305235d6df491"),//查看密码请登录用户中心->语音验证码->帐户参数设置->APIKEY
                new NameValuePair("mobile", "18513119022"),//手机号码
                new NameValuePair("content", "111111"),
        };

        method.setRequestBody(data);

        client.executeMethod(method);

        String SubmitResult = method.getResponseBodyAsString();

        //System.out.println(SubmitResult);

        Document doc = DocumentHelper.parseText(SubmitResult);
        Element root = doc.getRootElement();

        String code = root.elementText("code");
        String msg = root.elementText("msg");
        String voiceid = root.elementText("voiceid");

        System.out.println(code);
        System.out.println(msg);
        System.out.println(voiceid);

        if ("2".equals(code)) {
            System.out.println("短信提交成功");
        }
    }*/
}
