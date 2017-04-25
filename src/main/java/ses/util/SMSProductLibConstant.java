package ses.util;
/**
 * 
* @ClassName: SMSProductLibConstant 
* @Description: 产品库所使用的常量
* @author Easong
* @date 2017年4月20日 上午10:40:34 
*
 */
public final class SMSProductLibConstant {
	
	/**判断用户点击的按钮（1:提交  0:保存）**/
	// 提交
	public static final Integer PRODUCT_LIB_SUBMIT_FLAG = 1;
	// 保存
	public static final Integer PRODUCT_LIB_SAVE_FLAG = 0;
	
	/**产品状态（1:上架  0:下架）**/
	public static final Integer PRODUCT_LIB_ITEM_STATUS_PUTAWAY = 1;
	public static final Integer PRODUCT_LIB_ITEM_STATUS_SOLD_OUT = 0;
	
	
	/**产品审核状态 0：暂存  1：待审核   2：审核不通过  3：审核通过 **/
	public static final Integer PRODUCT_LIB_ITEM_STATUS_TEMP_SAVE = 0;
	public static final Integer PRODUCT_LIB_ITEM_STATUS_WAIT_CHECK = 1;
	public static final Integer PRODUCT_LIB_ITEM_STATUS_NOT_THROUGH_CHECK = 2;
	public static final Integer PRODUCT_LIB_ITEM_STATUS_THROUGH_CHECK = 3;
	
	/**产品删除状态    是否删除  0：否   1：是 **/
	public static final Integer PRODUCT_LIB_ITEM_NOT_DELETE = 0;
	public static final Integer PRODUCT_LIB_ITEM_DELETE = 1;
	
	
	/**产品参数状态    参数必填字段  1填写，0可以不填 **/
	public static final Integer PRODUCT_LIB_ARGU_NOT_REQUIRED = 0;
	public static final Integer PRODUCT_LIB_ARGU_REQUIRED = 1;
	
	/**参数类型**/
	public static final String PRODUCT_LIB_ARGU_TYPE="附件";
	
}
