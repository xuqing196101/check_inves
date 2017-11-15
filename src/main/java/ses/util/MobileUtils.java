package ses.util;

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
    
    /** 当前地区的区号 **/
    public static final String AREACODE = "0512";//江苏苏州

	public void setMobileMapper(MobileMapper mobileMapper) {
		this.mobileMapper = mobileMapper;
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
     * @return
     */
    public static String getMobile(String mobileNum) {
    	String num22 = "";
    	if(mobileNum != null && mobileNum.length() > 7){
    		num22 = mobileNum.substring(0, 7);
    	}else{
    		num22 = mobileNum;
    	}
        Mobile mobile = mobileUtils.mobileMapper.findByMobile(num22);
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
