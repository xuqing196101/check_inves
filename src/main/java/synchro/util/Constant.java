package synchro.util;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>静态常量
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class Constant {

    /** 数据类型-供应商注册 **/
    public final static Integer DATA_TYPE_SUPPLIER_REG = 1;
    /** 数据类型-供应商修改 **/
    public final static Integer DATA_TYPE_SUPPLIER_MODIFY = 2;
    
    /** 数据类型-供应商修改 **/
    public final static Integer DATA_TYPE_SUPPLIER_AUDIT = 5;
    
    /** 操作类型-导出 **/
    public final static Integer OPER_TYPE_EXPORT = 1;
    /** 操作类型-导入**/
    public final static Integer OPER_TYPE_IMPORT = 2;
    /** 新提交供应商描述 **/
    public final static String NEW_COMMIT_SUPPLIER = "导出供应商数量:";
    
    /** 数据类型-专家注册 **/
    public final static Integer DATA_TYPE_EXPERT_REG = 3;
    
    /** 数据类型-专家修改 **/
    public final static Integer DATA_TYPE_EXPERT_MODIFY = 4;
    
    /** 数据类型-信息 **/
    public final static String DATA_TYPE_INFOS_CODE = "SYNCH_NEWS";
    
    /** 数据类型-附件 **/
    public final static String DATA_TYPE_ATTACH_CODE = "SYNCH_ATTACH";
    
    /** 数据类型-供应商  **/
    public final static String DATA_TYPE_SUPPLIER_CODE = "SYNCH_SUPPLIER";
    
    /** 数据类型-采购机构  **/
    public final static String DATA_TYPE_ORG = "SYNCH_ORG";
    
    /** 新提交专家描述 **/
    public final static String NEW_COMMIT_EXPERT = "新提交专家数量:";
    
    public final static String DATA_TYPE_EXPERT_CODE = "SYNCH_EXPERT";
    
    /**竞价信息**/
    public final static String DATA_TYPE_BIDDING_CODE="SYNCH_BIDDING_PROJECT";
    
    /**竞价信息 附件 10**/
    public final static Integer DATA_TYPE_BIDDING_FILE_CODE=10;
    /** 竞价 信息描述 **/
    public final static String OB_PROJECT_COMMIT = "导出竞价信息数量:";
    /** 竞价 信息 同步描述 **/
    public final static String OB_PROJECT_COMMIT_IMPORT = "同步竞价信息数量:";
    /**竞价结果**/
    public final static String DATA_TYPE_BIDDING_RESULT_CODE="SYNCH_BIDDING_RESULT";
    /**竞价结果描述**/
    public final static String OB_RESULT_COMMIT="导出竞价结果数量:";
    /**竞价结果  同步描述**/
    public final static String OB_RESULT_COMMIT_IMPORT="导出竞价结果数量:";
    /** 竞价 定型产品信息描述 **/
    public final static String PRODUCT_COMMIT = "导出竞价定型产品数量:";
    /** 竞价 定型产品信息  导入描述 **/
    public final static String PRODUCT_COMMIT_IMPORT = "同步竞价定型产品数量:";
    /**竞价 定型产品信息**/
    public final static String DATE_SYNCH_BIDDING_PRODUCT ="SYNCH_BIDDING_PRODUCT";
    
    /** 竞价 特殊信息描述 **/
    public final static String OB_SPECIAL_DATE_COMMIT = "导出竞价特殊日期数量:";
    /** 竞价 特殊信息 导入 描述 **/
    public final static String OB_SPECIAL_DATE_COMMIT_IMPORT = "同步竞价特殊日期数量:";
    /**竞价 特殊日期**/
    public final static String DATE_SYNCH_BIDDING_SPECIAL_DATE ="SYNCH_BIDDING_SPECIAL_DATE";
    
    /** 竞价 供应商信息描述 **/
    public final static String OB_SUPPLIER_COMMIT = "导出竞价供应商数量:";
    /** 竞价 供应商信息 导入描述 **/
    public final static String OB_SUPPLIER_COMMIT_IMPORT = "同步竞价供应商数量:";
    /**竞价 供应商**/
    public final static String DATE_SYNCH_BIDDING_SUPPLIER ="SYNCH_BIDDING_SUPPLIER";
    
    /** 修改专家描述 **/
    public final static String MODIFY_COMMIT_EXPERT = "修改专家数量:";
    
    /** 提交信息描述 **/
    public final static String CREATED_COMMIT_INFOS = "同步信息数量:";
    
    /** 提交附件描述 **/
    public final static String CREATED_COMMIT_ATTACH = "同步附件数量:";
    
    /** 供应商附件文件名称 **/
    public final static String ATTACH_FILE_SUPPLIER = "supplier";
    
    /** 招标附件文件名称 **/
    public final static String ATTACH_FILE_TENDER = "tender";
    
    /** 专家附件文件名称 **/
    public final static String ATTCH_FILE_EXPERT = "expert";
    
    /** 竞价 信息文件名称 **/
    public final static String PROJECT_EXPERT = "bidding_project";
    /** 竞价 信息 附件名称 **/
    public final static String PROJECT_FILE_EXPERT = "bidding_project_file";
    /** 竞价 产品文件名称 **/
    public final static String PRODUCT_FILE_EXPERT = "bidding_product";
    /** 竞价 特殊日期文件名称 **/
    public final static String SPECIAL_DATE_FILE_EXPERT = "bidding_special_date";
    /** 竞价 供应商文件名称 **/
    public final static String SUPPLIER_FILE_EXPERT = "bidding_supplier";
    /** 竞价 结果文件名称 **/
    public final static String RESULT_FILE_EXPERT = "bidding_result";
    /** 产品库 参数管理附件 **/
    public final static String PRODUCT_LIB_FILE = "PRODUCT_LIB_FILE";
}
