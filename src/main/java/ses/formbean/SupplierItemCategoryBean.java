package ses.formbean;

import ses.model.bms.Category;

/**
 * 供应商品目类别的bean
 * @author hxg
 *
 */
public class SupplierItemCategoryBean extends Category {
	
	private String itemId;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
