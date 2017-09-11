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
    /** 新提交供应商描述 **/
    public final static String NEW_COMMIT_SUPPLIER_IMPORT = "导入供应商数量:";

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
    public final static String OB_RESULT_COMMIT_IMPORT="导入竞价结果数量:";
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
    
    
    /** 供应商附件文件名称 **/
    public final static String ATTACH_FILE_SUPPLIER_NEW = "supplier_new";
    /**供应商审核附件**/
    public final static String SUPPLIER_CHECK_ATTACHMENT = "SUPPLIER_CHECK_ATTACHMENT";
    /**专家审核附件**/
    public final static String EXPERT_CHECK_ATTACHMENT = "EXPERT_CHECK_ATTACHMENT";
    
    
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
    /**产品库 管理 **/
    public final static String SYNCH_PRODUCT_LIBRARY="SYNCH_PRODUCT_LIBRARY";
    /** 产品库 管理  外网 导出信息描述 **/
    public final static String OUTER_PRODUCT_LIBRARY_COMMIT = "导出产品库信息数量:";
    /**产品库 管理 同步描述**/
    public final static String OUTER_PRODUCT_LIBRARY_IMPORT="导入产品库信息数量:";
    /**产品库  信息文件名称  外网**/
    public final static String OUTER_PRODUCT_LIBRARY_EXPERT = "outer_product_library";
    /**产品库  信息文件名称 内网 **/
    public final static String INNER_PRODUCT_LIBRARY_EXPERT = "inner_product_library";
    /**产品库  信息文件名称 文件 内网 **/
    public final static String OUTER_FILE_PRODUCT_LIBRARY_EXPERT = "outer_file_product_library";
    
    /**产品目录管理 名称**/
    public final static String SYNCH_CATEGORY="SYNCH_CATEGORY";
    /**产品目录 管理  导出信息描述 **/
    public final static String COMMIT_SYNCH_CATEGORY="导出产品目录信息数量:";
    /**产品目录 导出 文件夹名称**/
    public final static String T_SES_BMS_CATEGORY_PATH="t_ses_bms_category_path";
    /**产品目录 导出 附件文件夹名称**/
    public final static String FILE_T_SES_BMS_CATEGORY_PATH="file_t_ses_bms_category_path";
    /** 产品目录 管理  导出 附件描述 **/
    public final static String COMMIT_FILE_NUMBER_SYNCH_CATEGORY = "导出产品目录附件数量:";
    /** 产品目录 管理  导出 附件描述 **/
    public final static String IMPORT_FILE_NUMBER_SYNCH_CATEGORY = "导入产品目录附件数量:";
    /** 产品目录 管理   导入附件描述 **/
    public final static String IMPORT_SYNCH_CATEGORY = "导入产品目录信息数量:";
    
    /**产品目录参数管理 名称**/
    public final static String SYNCH_CATE_PARAMTER="SYNCH_CATE_PARAMTER";
    /**产品目录参数管理  导出信息描述 **/
    public final static String COMMIT_SYNCH_CATEGORY_PARAMTER="导出产品目录参数信息数量:";
    /**产品目录参数管理  导入信息描述 **/
    public final static String IMPORT_SYNCH_CATEGORY_PARAMTER="导入产品目录参数信息数量:";
    /**产品目录参数导出 文件夹名称**/
    public final static String T_SES_BMS_CATEGORY_PARAMTER_PATH="t_ses_bms_category_paramter_path";
    
    /**资料  管理 名称**/
    public final static String SYNCH_DATA="SYNCH_DATA";
    /**资料 管理  导出数据描述 **/
    public final static String COMMIT_SYNCH_DATA="导出资料数量:";
    /**资料 管理 导出 数据的 文档**/
    public final static String ISS_PS_DATA_DOWNLOAD_PATH ="iss_ps_data_download_path";
    /**资料 管理 导出 附件的 文档**/
    public final static String FILE_ISS_PS_DATA_DOWNLOAD_PATH ="file_iss_ps_data_download_path";
    /**资料 管理  导出信息描述 **/
    public final static String COMMIT_FILE_SYNCH_DATA="导出资料管理附件数量:";
    /**资料 管理  导入信息描述 **/
    public final static String IMPORT_FILE_SYNCH_DATA="导入资料管理附件数量:";
    
    /**门户模板管理 名称**/
    public final static String SYNCH_TEMPLATE_DOWNLOAD="SYNCH_TEMPLATE_DOWNLOAD";
    /**门户模板管理  导出信息描述 **/
    public final static String COMMIT_SYNCH_TEMPLATE_DOWNLOAD="导出门户模板信息数量:";
    /**门户模板管理  导入信息描述 **/
    public final static String IMPORT_SYNCH_TEMPLATE_DOWNLOAD="导入门户模板信息数量:";
    /**门户模板管理 文件夹名称**/
    public final static String T_ISS_PS_TEMPLATE_DOWNLOAD_PATH="t_iss_ps_template_download_path";
    /**门户模板管理 附件文件夹名称**/
    public final static String T_ISS_PS_TEMPLATE_DOWNLOAD_ATTFILE_PATH="attfile_t_iss_ps_template_download_path";
    
    /**同步 目录资质关联表**/
    public final static String DATA_SYNCH_CATEGORY_QUA="SYNCH_RELATION_CATEGORY_QUA";
    /**目录资质关联表  导出信息描述 **/
    public final static String IMPORT_COMMIT_SYNCH_CATEGORY_QUA="导出资质关联信息数量:";
    /**目录资质关联表  导入信息描述 **/
    public final static String EXPORT_COMMIT_SYNCH_CATEGORY_QUA="导入资质关联信息数量:";
    public final static String FILE_SYNCH_CATEGORY_QUA_PATH="file_synch_category_qua_path";
    /**同步  产品资质表**/
    public final static String DATA_SYNCH_QUALIFICATION="SYNCH_QUALIFICATION";
    /**产品资质  导出信息描述 **/
    public final static String IMPORT_COMMIT_SYNCH_QUALIFICATION="导出产品资质信息数量:";
    /**产品资质  导入信息描述 **/
    public final static String EXPORT_COMMIT_SYNCH_QUALIFICATION="导入产品资质信息数量:";
    /***产品资质 导出路径***/
    public final static String FILE_SYNCH_QUALIFICATION_PATH="file_synch_qualification_path";

    /**供应商公示信息**/
    public final static String SYNCH_PUBLICITY_SUPPLIER="SYNCH_PUBLICITY_SUPPLIER";
    /**供应商公示  导出信息描述 **/
    public final static String COMMIT_SYNCH_PUBLICITY_SUPPLIER="导出供应商公示信息数量:";
    /**供应商公示  导入信息描述 **/
    public final static String IMPORT_SYNCH_PUBLICITY_SUPPLIER="导入供应商公示信息数量:";
    /**供应商公示  导入文件根路径（文件夹**/
    public final static String T_SES_SMS_SUPPLIER_PUBLICITY_PATH="t_ses_sms_supplier_publicity_path";

    /**专家公示信息**/
    public final static String SYNCH_PUBLICITY_EXPERT="SYNCH_PUBLICITY_EXPERT";
    /**专家公示  导出信息描述 **/
    public final static String COMMIT_SYNCH_PUBLICITY_EXPERT="导出专家公示信息数量:";
    /**专家公示  导入信息描述 **/
    public final static String IMPORT_SYNCH_PUBLICITY_EXPERT="导入专家公示信息数量:";
    /**专家公示  导入文件根路径（文件夹**/
    public final static String T_SES_SMS_EXPERT_PUBLICITY_PATH="t_ses_sms_expert_publicity_path";

    /**目录资质关联表 文件夹名称**/
    
    
    /** 网上投诉信息描述 **/
    public final static String ONLINE_COMPLAINTS_COMMIT = "导出网上投诉信息数量:";
    /** 网上投诉信息导入描述 **/
    public final static String ONLINE_COMPLAINTS_COMMIT_IMPORT = "同步网上投诉信息数量:";
    /** 网上投诉信息 **/
    public final static String DATE_SYNCH_ONLINE_COMPLAINTS ="SYNCH_ONLINE_COMPLAINTS";
    /** 网上投诉信息文件名称 **/
    public final static String ONLINE_COMPLAINTS_FILE_EXPERT = "online_complaints";
    
    /** 供应商黑名单信息描述 **/
    public final static String SUPPLIER_BLACKLIST_COMMIT = "导出供应商黑名单信息数量:";
    /** 供应商黑名单信息导入描述 **/
    public final static String SUPPLIER_BLACKLIST_COMMIT_IMPORT = "同步供应商黑名单信息数量:";
    /** 供应商黑名单信息 **/
    public final static String DATE_SYNCH_SUPPLIER_BLACKLIST ="SYNCH_S_SUPPLIER_BLACKLIST";
    /** 供应商黑名单文件名称 **/
    public final static String SUPPLIER_BLACKLIST_FILE_EXPERT = "supplier_blacklist";
    
    /** 供应商黑名单记录信息描述 **/
    public final static String SUPPLIER_BLACKLIST_LOG_COMMIT = "导出供应商黑名单记录信息数量:";
    /** 供应商黑名单记录信息导入描述 **/
    public final static String SUPPLIER_BLACKLIST_LOG_COMMIT_IMPORT = "同步供应商黑名单记录信息数量:";
    /** 供应商黑名单记录信息 **/
    public final static String DATE_SYNCH_SUPPLIER_BLACKLIST_LOG ="SYNCH_S_SUPPLIER_BLACKLIST_LOG";
    /** 供应商黑名单记录文件名称 **/
    public final static String SUPPLIER_BLACKLIST_LOG_FILE_EXPERT = "supplier_blacklist_log";

    /** 专家黑名单信息描述 **/
    public final static String EXPERT_BLACKLIST_COMMIT = "导出专家黑名单信息数量:";
    /** 专家黑名单信息导入描述 **/
    public final static String EXPERT_BLACKLIST_COMMIT_IMPORT = "同步专家黑名单信息数量:";
    /** 专家黑名单信息 **/
    public final static String DATE_SYNCH_EXPERT_BLACKLIST ="SYNCH_S_EXPERT_BLACKLIST";
    /** 专家黑名单文件名称 **/
    public final static String EXPERT_BLACKLIST_FILE_EXPERT = "expert_blacklist";

    /** 专家黑名单记录信息描述 **/
    public final static String EXPERT_BLACKLIST_LOG_COMMIT = "导出专家黑名单记录信息数量:";
    /** 专家黑名单记录信息导入描述 **/
    public final static String EXPERT_BLACKLIST_LOG_COMMIT_IMPORT = "同步专家黑名单记录信息数量:";
    /** 专家黑名单记录信息 **/
    public final static String DATE_SYNCH_EXPERT_BLACKLIST_LOG ="SYNCH_S_EXPERT_BLACKLIST_LOG";
    /** 专家黑名单记录文件名称 **/
    public final static String EXPERT_BLACKLIST_LOG_FILE_EXPERT = "expert_blacklist_log";
    
    /** 网上投诉信息描述 **/
    public final static String HOT_LINE_COMMIT = "导出服务热线信息数量:";
    /** 网上投诉信息导入描述 **/
    public final static String HOT_LINE_COMMIT_IMPORT = "同步服务热线信息数量:";
    /** 网上投诉信息 **/
    public final static String DATE_SYNCH_HOT_LINE ="SYNCH_HOT_LINE";
    /** 网上投诉信息文件名称 **/
    public final static String HOT_LINE_FILE_EXPERT = "hot_line";

}
