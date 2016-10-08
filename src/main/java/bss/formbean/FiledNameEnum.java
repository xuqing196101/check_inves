package bss.formbean;
/**
 * 
 * @Title: FiledNameEnum
 * @Description: 列举出所有是否修改的字段名称 
 * @author Li Xiaoxiao
 * @date  2016年9月28日,下午2:17:03
 *
 */
public enum FiledNameEnum {

	DEPARTMENT("required_department","需求部门"),
	CODE("required_code","预算科目编码"),
	PLANTYPE("required_plantype","项目类别"),
	PLANNAME("required_planName","项目名称"),
	TECHPARAM("required_techparam","需求部门"),
	COUNT("required_COUNT","数量"),
	BUDGET("required_budget","预算"),
	GIVEDATE("required_givedate","交货日期"),
	PURCHASETYPE("required_purchasetype","采购方式建议"),
	GYSNAMW("required_gysname","供应商名称"),
	ISTAXFREE("required_isfree","是否申请办理免税"),
	GOODSUSE("required_goodsuse","物资用途"),
	USEUNIT("required_unit","使用单位");
	
	
	private String code;
	private String desc;
	
	private FiledNameEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
