package common.constant;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  常量
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
    public static final String CATEGORY_AUDIT_MSG = "已审核完成,不能重复提交";
    
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
    
    
    /** 物资生产状态 **/
    public static final Integer GOODS_PRODUCT_STATUS = 1;
    /** 物资销售状态 **/
    public static final Integer GOODS_SALES_STATUS = 2;
    /** 物资生产销售状态 **/
    public static final Integer GOODS_PS_STATUS = 3;
}
