package extract.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import extract.dao.common.MobileMapper;
import extract.model.common.Mobile;

/**
 * 
 * Description: 判断手机号归属地
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Component
public class MobileUtils {

    @Autowired
    private MobileMapper mobileMapper;

    private static MobileUtils mobileUtils;
    
    /** 区号 **/
    public static final String AREACODE = "";

    public static void setMobileUtils(MobileUtils mobileUtils) {
        MobileUtils.mobileUtils = mobileUtils;
    }

    @PostConstruct
    public void init() {
        mobileUtils = this;
        mobileUtils.mobileMapper = this.mobileMapper;
    }

    /**
     * 
     * Description: 根据手机号返回是否加0
     * 
     * @author zhang shubin
     * @data 2017年11月2日
     * @param mobileNum 需要查询的手机号前7位
     * @param areaCode 当前地区的区号
     * @return
     */
    public static String getMobile(String mobileNum) {
    	if(mobileNum != null && mobileNum.length() > 7){
    		mobileNum = mobileNum.substring(0, 7);
    	}
        Mobile mobile = mobileUtils.mobileMapper.findByMobile(mobileNum);
        if (mobile != null && AREACODE != null && !mobile.getAreacode().equals(AREACODE)) {
            return "0" + mobileNum;
        }
        return mobileNum;
    }
    
    /**
     * 
     * Description: 手机号去零
     * 
     * @author zhang shubin
     * @data 2017年11月2日
     * @param 
     * @return
     */
    public static String reMobile(String mobileNum){
    	if(mobileNum != null && mobileNum.substring(0,1).equals("0")){
    		return mobileNum.substring(1,mobileNum.length());
    	}
    	return mobileNum;
    }
}
