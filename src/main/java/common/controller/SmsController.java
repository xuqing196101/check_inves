package common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Description: 短信上行请求接口
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Controller
@RequestMapping("/sms")
public class SmsController {

    /**
     * Stat  状态报告值
     * 
     * DELIVRD  短消息转发成功
     * EXPIRED  短消息超过有效期
     * UNDELIV  短消息是不可达的
     * UNKNOWN  未知短消息状态
     * REJECTD  短消息被短信中心拒绝
     * DTBLACK  目的号码是黑名单号码
     * ERR:104  系统忙
     * REJECT   审核驳回
     * 其他  网关内部状态
     */

    /**
     * API文档地址   http://bcp.pro-group.cn:7002/Docs/#!easycloud-smsapi.md
     */

    /**
     * 
     * Description: 状态报告推送         接收短信发送的状态
     * 
     * @data 2017年11月9日
     * 
     * @param MsgId  提交短信时平台返回的MsgId
     * @param Mobile  单一的手机号码
     * @param RptTime  格式YYYYMMDDhhmmss
     * @param Stat  状态报告值
     * @param IUser  客户端的账号(非必传)
     * @param IPass  客户端的密码(非必传)
     * 
     * @return 0  代表收到。否则服务端会再次推送。
     */
    @RequestMapping("/statusReport")
    @ResponseBody
    public String statusReport(String IUser, String IPass, String MsgId, String Mobile, String RptTime, String Stat){
        //此处仅供测试请求地址使用  具体的业务逻辑后续补加
        return "0";
    }

    /**
     * 
     * Description: 上行短信推送       接收用户回复短信的内容
     * 
     * @data 2017年11月9日
     * 
     * @param MsgId  MsgId
     * @param Mobile  单一的手机号码
     * @param MsgCont  短信内容
     * @param MoTime  上行时间，格式YYYYMMDDhhmmss
     * @param IUser  客户端的账号(非必传)
     * @param IPass  客户端的密码(非必传)
     * 
     * @return 0  代表收到。否则服务端会再次推送。
     * @throws IOException 
     */
    @RequestMapping("/uplinkSms")
    public void uplinkSms(String IUser, String IPass, String MsgId, String Mobile, String MoTime, HttpServletRequest request, HttpServletResponse response) throws IOException{
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //短信内容编码处理
        String MsgCont =new String(request.getParameter("MsgCont").getBytes("ISO-8859-1"), "GBK");
        MsgCont = URLDecoder.decode(MsgCont,"GBK");
        //此处仅供测试请求地址使用  具体的业务逻辑后续补加
        out.print("0");
        if(out != null){
            out.close();
        }
    }
}
