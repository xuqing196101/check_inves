package synchro.test;

import org.junit.Assert;
import org.junit.Test;

import synchro.util.DateUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>日期测试
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class DateUtilTest {

    @Test
    public void testCurrentDate(){
        String currentDate = DateUtils.getCurrentDate();
        Assert.assertTrue(currentDate.equals("2016-12-27"));
    }
    
    @Test
    public void testYesterday(){
        String currentDate = DateUtils.getYesterDay();
        Assert.assertTrue(currentDate.equals("2016-12-26"));
    }
}
