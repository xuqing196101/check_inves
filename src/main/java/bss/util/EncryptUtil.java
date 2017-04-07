package bss.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.Random;

/**
 * Created by zhangxq on 2017/4/5.
 * 加密工具类
 */
public class EncryptUtil {

    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Description: 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     * @return String
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 根据被加密的字符串和随机字符串生成md5加密码
     * @param str
     * @param generate
     * @return String
     */
    public static String md5Encrypt(String str,String generate){
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版
        md5.setEncodeHashAsBase64(false);
        String encodePassword = md5.encodePassword(str, generate);
        return encodePassword;
    }
}
