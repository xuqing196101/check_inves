package bss.model.ob;

import java.util.List;

/**
 * 
 * @author Ma Mingwei
 * @description 供应商下面的产品信息
 * 		页面需求信息量不大，操作少，固有此实体类专门作为介质
 * 		在OBProjectResultMapper.xml里映射
 *
 */
public class SupplierProductVo {

	//供应商id
	private String supplierId;
	//供应商名称
	private String supplierName;
	//供应商排名
	private Integer ranking;
	//供应商成交比例
	private String dealQuote;
	//对应的产品信息列表
	private List<BidProductVo> productList;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getDealQuote() {
		return dealQuote;
	}

	public void setDealQuote(String dealQuote) {
		this.dealQuote = dealQuote;
	}

	public List<BidProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<BidProductVo> productList) {
		this.productList = productList;
	}
}
