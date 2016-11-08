package bss.url;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  url测试
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class UrlEstimateTest {
    
    @Test
    public void urlTest(){
        String req_url = "/test/view/test.html";
        String src_url = "/test/view/test.html?value=0";
        Assert.assertTrue(src_url.indexOf(req_url) == 0);
    }
}
