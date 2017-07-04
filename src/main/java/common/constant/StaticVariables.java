package common.constant;

import ses.util.PropUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 公共常量
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class StaticVariables {
    
    /** 添加操作类型 */
    public static final String OPER_ADD_TYPE = "add";
    /** 编辑操作类型 */
    public static final String OPER_EDIT_TYPE = "edit";
    /** 删除操作类型 */
    public static final String OPER_DEL_TYPE = "del";
    /** 取消操作类型 */
    public static final String OPER_CANCEL_TYPE = "cancel";
    
    
    /** 修改操作 */
    public static final String OPER_ADD_MSG ="新增";
    /** 修改操作 */
    public static final String OPER_EDIT_MSG ="修改";
    /** 删除操作 */
    public static final String OPER_DEL_MSG ="删除";
    /** 取消操作 */
    public static final String OPER_CANCEL_MSG ="取消";
    /** 提交操作 **/
    public static final String OPERA_SUBMIT_MSG = "提交";
    
    
    /** 品目分类的新建状态  */
    public static final Integer CATEGORY_NEW_STATUS = 0;
    /** 品目分类的分配状态  */
    public static final Integer CATEGORY_ASSIGNED_STATUS = 1;
    /** 品目分类的提交状态  */
    public static final Integer CATEGORY_SUBMIT_STATUS = 2;
    /** 品目分类的审核状态  */
    public static final Integer CATEGORY_AUDIT_STATUS = 3;
    /** 品目分类的发布状态  */
    public static final Integer CATEGORY_PUBLISH_STATUS = 4;
    
    
    /** 品目分类的分配提示  */
    public static final String CATEGORY_ASSIGNED_MSG = "已分配,无法";
    
    /** 品目分类的分配提示  */
    public static final String CATEGORY_USED_MSG = "正在编辑参数,无法取消";
    
    /** 品目分类的提交提示  */
    public static final String CATEGORY_SUBMIT_MSG = "已提交,无法";
    
    /** 品目分类的审核提示 - 通过  */
    public static final String CATEGORY_AUDIT_MSG = "已审核完成,不能重复";
    
    /** 品目分类的审核提示 - 驳回  */
    public static final String CATEGORY_REJECT_MSG = "已经驳回";
    
    /** 品目分类的发布状态  */
    public static final String CATEGORY_PUBLISH_MSG = "已发布,无法";
    
    /** 品目已经提交提示 */
    public static final String CATEGORY_SUBMITED_MSG = "已经提交,请等待审核";
    
    
    /** 成功状态  */
    public static final String SUCCESS = "ok";
    /** 失败状态  */
    public static final String FAILED = "failed";
    
    
    /** 逗号分割 */
    public static final String COMMA_SPLLIT = ",";
    /** 逗号分割 */
    public static final String DOLLAR_SPLLIT = "$";
    
    
    /** 生产 **/
    public static final String GOODS_PRODUCT = "PRODUCT";
    /** 销售 **/
    public static final String GOODS_SALES = "SALES";
    /** 工程 **/
    public static final String PROJECT = "PROJECT";
    /** 服务 **/
    public static final String SERVICE = "SERVICE";
    
    
    /** 物资生产状态 **/
    public static final Integer GOODS_PRODUCT_STATUS = 1;
    /** 物资销售状态 **/
    public static final Integer GOODS_SALES_STATUS = 2;
    /** 物资生产销售状态 **/
    public static final Integer GOODS_PS_STATUS = 3;
    
    
    /** 机构类型值 -默认 */
    public static final String ORG_TYPE_DEFAULT = "0";
    /** 机构类型值 -采购机构 */
    public static final String ORG_TYPE_PURCHASE = "1";
    /** 机构类型值 -管理机构 */
    public static final String ORG_TYPE_MANAGE = "2";
    
    
    /** 组织机构添加采购人入口 - 采购机构入口 **/
    public static final String ORG_ORIGIN_PURCHASER = "1";
    /** 组织机构添加采购人入口 - 组织机构入口 **/
    public static final String ORG_ORIGIN_ORG = "2";
    
    
    /** 删除 **/
    public static final Integer IS_DELETED = 1;
    /** 不删除 **/
    public static final Integer ISNOT_DELETED = 0;
    
    
    /** 成功标识 **/
    public static final Integer SUCCESS_INTEGER = 0;
    
    
    /** 组织机构 - 需求部门 **/
    public static final String ORG_TYPE_DEMAND = "ORG_TYPE_DEMAND";
    /** 组织机构 - 管理部门 **/
    public static final String ORG_TYPE_MANAGER = "ORG_TYPE_MANAGE";
    /**采购机构**/
    public static final String ORG_TYPE_JG_CGJG="ORG_TYPE_JG_CGJG";
    /**其他**/
    public static final String ORG_TYPE_OT="ORG_TYPE_OT";
    
    /** 组织机构删除 - 提示 **/
    public static  String ORG_RELATION_EXIST_USER = "该机构下存在{0}个用户，不能被删除！";
    
    
    /** 企业资质等级 - 正常 **/
    public static final Integer PURCHASER_COMPREHEN_NORMAL = 0;
    /** 企业资质等级 - 暂停 **/
    public static final Integer PURCHASER_COMPREHE_PUASE = 1;
    /** 企业资质等级 - 终止 **/
    public static final Integer PURCHASER_COMPREHE_STOP = 2;
    
    
    /** 采购机构终止说明 **/
    public static final String PURCHASER_STATUS_RESON="原因说明不能为空";
    
    /** 企业资质-通用 **/
    public static final Integer CATEGORY_QUALIFICATION_GENERAL = 1;
    /** 企业资质-物资生产型专业 **/
    public static final Integer CATEGORY_QUALIFICATION_PROFILE = 2;
    /** 企业资质-物资销售型专业 **/
    public static final Integer CATEGORY_QUALIFICATION_SALES_PROFILE = 3;
    /** 企业资质-工程 **/
    public static final Integer CATEGORY_QUALIFICATION_PROJECT_PROFILE = 4;
    
    /***角色权限 需求部门  命名***/
    public static final String ROLE_DEMAND_SECTOR="需求部门";
    
    /***日志 标识 网上竞价 名称****/
    public static final String OB_PROJECT_NAME="网上竞价";
    
    /***提示信息***/
    public static final String OB_PROJECT_SHOW="只有资源服务中心才能操作";

    /***日志 标识 网上投诉 名称****/
    public static final String OC_COMPLAINTS_NAME="网上投诉";
    /***日志 标识 网上投诉***/
    public static final int OC_COMPLAINTS_NAME_SIGN=6;
    /***提示信息***/
    public static final String OC_COMPLAINTS="只有资源服务中心才能操作";
    
    /***日志 标识 服务热线 名称****/
    public static final String HL_SERVICEHOTLINE_NAME="服务热线";
    /***日志 标识 服务热线***/
    public static final int HL_SERVICEHOTLINE_NAME_SIGN=7;
    /***提示信息***/
    public static final String HL_SERVICEHOTLINE="只有资源服务中心才能操作";
    /***日志 标识 供应商在线 名称****/
    public static final String SUPPLIER_ONLINE_BIDDING="供应商在线投标";
    /***日志 标识 供应商在线***/
    public static final int SUPPLIER_ONLINE_BIDDING_SIGN=8;
    //获取是否内网标识 1外网 0内网
    public static final String ipAddressType= PropUtil.getProperty("ipAddressType");
    /**
     * 默认页
     */
    public static final Integer DEFAULT_PAGE = 1;
    
    /***定义统计缓存健***/
 	public static final String ANALYZE = "analyze";
    
}
