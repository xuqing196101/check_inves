package ses.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @Title: Encrypt
 * @Description: MD5 加密类
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:12:37
 */
public class Encrypt {

    /**
     * @Title: main
     * @author: Wang Zhaohua 
     * @date: 2016-4-13 下午1:52:15  
     * @Description: 测试加密 
     * @param: @param args      
     * @return: void
     */
    public static void main(String[] args) {
        // md5加密测试
        String md5_1 = md5("123456");
        String md5_2 = md5("Poppet_Brook");
        System.out.println(md5_1 + "\n" + md5_2);
        // sha加密测试
        String sha_1 = sha("123456");
        String sha_2 = sha("Poppet_Brook");
        System.out.println(sha_1 + "\n" + sha_2);

    }

    /**
     * @Title: e
     * @author: Wang Zhaohua 
     * @date: 2016-4-13 下午1:53:34  
     * @Description: 加密方法 
     * @param: @param inputText 需要加密的参数
     * @param: @return      
     * @return: String
     */
    public static String e(String inputText) {
        return md5(inputText);
    }

    /**
     * @Title: md5AndSha
     * @author: Wang Zhaohua 
     * @date: 2016-4-13 下午1:54:23  
     * @Description: 二次加密 
     * @param: @param inputText 需要加密的参数
     * @param: @return      
     * @return: String
     */
    public static String md5AndSha(String inputText) {
        return sha(md5(inputText));
    }

    /**
     * @Title: md5
     * @author: Wang Zhaohua 
     * @date: 2016-4-13 下午1:55:19  
     * @Description: md5 加密 
     * @param: @param inputText
     * @param: @return      
     * @return: String
     */
    public static String md5(String inputText) {
        return encrypt(inputText, "md5");
    }

    /**
     * @Title: sha
     * @author: Wang Zhaohua 
     * @date: 2016-4-13 下午1:55:42  
     * @Description: sha 加密
     * @param: @param inputText
     * @param: @return      
     * @return: String
     */
    public static String sha(String inputText) {
        return encrypt(inputText, "sha-1");
    }

    /**
     * @Title: encrypt
     * @author: Wang Zhaohua 
     * @date: 2016-4-13 下午1:57:29  
     * @Description: 加密具体实现
     * @param: @param inputText
     * @param: @param algorithmName
     * @param: @return      
     * @return: String
     */
    private static String encrypt(String inputText, String algorithmName) {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "md5";
        }
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputText.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptText;
    }

    /**
     * @Title: hex
     * @author: Wang Zhaohua 
     * @date: 2016-4-13 下午1:58:02  
     * @Description: 返回 16 进制数据 
     * @param: @param arr
     * @param: @return      
     * @return: String
     */
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
}
