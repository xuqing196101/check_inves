package rsa;/**
 * Created by Easong on 2017/11/21.
 */

import common.utils.Base64;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * RAS加密算法测试
 *
 * @author Easong
 * @create 2017-11-21 17:36
 **/
public class RSA_ALG_TEST {

    /**
     *
     * Description: RSA测试
     *
     * @author Easong
     * @version 2017/11/21
     * @param 
     * @since JDK1.7
     */
    @Test
    public void test() throws NoSuchAlgorithmException {
        long start = System.currentTimeMillis();
        // 获取秘钥生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 设置生成秘钥位数
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PrivateKey aPrivate = keyPair.getPrivate();
        PublicKey aPublic = keyPair.getPublic();
        byte[] encoded = aPrivate.getEncoded();
        byte[] encoded1 = aPublic.getEncoded();
        long end = System.currentTimeMillis();
        System.err.println(end - start);
        System.err.println(Base64.encode(encoded));
        System.err.println(Base64.encode(encoded1));

    }
}
