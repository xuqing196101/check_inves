package ses.model.sms;

import java.io.Serializable;

/**
 * 
 * Description:供应商公示实体
 * 
 * @author Easong
 * @version 2017年6月28日
 * @since JDK1.7
 */
public class SupplierPublicity extends Supplier implements Serializable {

	/**
	 * SupplierPublicity.java
	 */
	private static final long serialVersionUID = 1L;

	// 选择的品目数量（小类）
	private Integer passCateCount;

	// 未通过品目数量（小类）
	private Integer noPassCateCount;

	public Integer getPassCateCount() {
		return passCateCount;
	}

	public void setPassCateCount(Integer passCateCount) {
		this.passCateCount = passCateCount;
	}

	public Integer getNoPassCateCount() {
		return noPassCateCount;
	}

	public void setNoPassCateCount(Integer noPassCateCount) {
		this.noPassCateCount = noPassCateCount;
	}

}
