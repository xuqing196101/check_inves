package ses.controller.sys.sms;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.service.bms.UserServiceI;
/**
 * 版权：(C) 版权所有 
 * <简述>供应商修改密码
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierInfo")
public class SupplierInfoController extends BaseSupplierController {
    /**
     * 常量字符串
     */
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 定义常量15
     */
    public static final int NUMBER_FIFTING = 15;
    /**
     * 用户服务层
     */
    @Autowired
    private UserServiceI userService;
    
    /**
     *〈简述〉
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param model 模型
     * @return String
     */
    @RequestMapping("/open_password")
    public String openPassword(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("loginUser");
        model.addAttribute("loginName", user.getLoginName());
        return "ses/sms/supplier_info/update_password";
    }
    
    /**
     *〈简述〉修改密码
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request 
     * @param oldPassword 旧密码
     * @param newPassword1 新密码
     * @param newPassword2 确认新密码
     * @param model 模型
     * @return String
     */
    @RequestMapping("/udpate_password")
    @ResponseBody
    public String udpatePassword(HttpServletRequest request, String oldPassword, String newPassword1, String newPassword2, Model model) {
        String flag = "1";
        User user = (User) request.getSession().getAttribute("loginUser");
        //生成15位随机码
        String randomCode = generateString(NUMBER_FIFTING);
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
        md5.setEncodeHashAsBase64(false);     
        String pwd = md5.encodePassword(oldPassword, user.getRandomCode());
        if (!user.getPassword().equals(pwd)) {
            //提示密码错误
            flag = "1";
            return flag;
        } else if (newPassword1.equals("") || newPassword2.equals("")) {
            //提示输入密码不为空
            flag = "4";
            return flag;
        } else if (!newPassword1.equals(newPassword2)) {
            //提示两次输入密码不一致
            flag = "2";
            return flag;
        } else {
            flag = "3";
            user.setPassword(md5.encodePassword(newPassword1, randomCode));
            user.setRandomCode(randomCode);
            userService.update(user);
            return flag;
        }
    }
    
    /**
     *〈简述〉返回一个定长的随机字符串(只包含大小写字母、数字)
     *〈详细描述〉
     * @author Song Biaowei
     * @param length 返回的字符串的长度
     * @return String
     */
    public String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sb.toString();  
    }
}
