package common.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 静态常量
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public final class Constant {
    
    /** 系统上传文件对应表 **/
    public static  Map<Integer,String> fileSystem = new ConcurrentHashMap<Integer, String>();
    
    /** 供应商系统key */
    public static final Integer SUPPLIER_SYS_KEY = 1;
    /** 供应商系统value */
    public static final String SUPPLIER_SYS_VALUE = "T_SES_SMS_SUPPLIER_ATTACHMENT";
    /** 招标采购系统key */
    public static final Integer TENDER_SYS_KEY = 2;
    /** 招标采购系统value */
    public static final String TENDER_SYS_VALUE = "T_SES_BMS_ATTACHMENT";
    /** 专家系统key */
    public static final Integer EXPERT_SYS_KEY = 3;
    /** 专家系统value */
    public static final String EXPERT_SYS_VALUE = "T_SES_EMS_EXPERT_ATTACHMENT";
    /** 论坛系统key */
    public static final Integer FORUM_SYS_KEY = 4;
    /** 论坛系统value */
    public static final String FORUM_SYS_VALUE = "T_ISS_FS_POST_ATTACHMENT";
    /** 网上竞价系统key */
    public static final Integer OB_PROJECT_SYS_KEY = 5;
    /** 网上竞价系统value */
    public static final String OB_PROJECT_SYS_VALUE = "T_BSS_OB_ATTACHMENT";
       
    /**竞价比例分成**/
    public static final Integer [] OB_PROJECT_ONE={100};
    public static final Integer [] OB_PROJECT_TWO={70,30};
    public static final Integer [] OB_PROJECT_THREE={50,30,20};
    public static final Integer [] OB_PROJECT_FOUR={40,30,20,10};
    public static final Integer [] OB_PROJECT_FIVE={30,25,20,15,10};
    public static final Integer [] OB_PROJECT_SIX={25,21,19,15,12,8};
    /**
     * 
     *〈简述〉初始化
     *〈详细描述〉
     * @author myc
     */
    public static void init(){
        fileSystem.put(SUPPLIER_SYS_KEY, SUPPLIER_SYS_VALUE);
        fileSystem.put(TENDER_SYS_KEY, TENDER_SYS_VALUE);
        fileSystem.put(EXPERT_SYS_KEY, EXPERT_SYS_VALUE);
        fileSystem.put(FORUM_SYS_KEY, FORUM_SYS_VALUE);
        fileSystem.put(OB_PROJECT_SYS_KEY, OB_PROJECT_SYS_VALUE);
    }
}
