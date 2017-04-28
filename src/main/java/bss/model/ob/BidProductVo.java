package bss.model.ob;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author Ma Mingwei
 * @description 竞价商品的信息
 *
 */
public class BidProductVo implements Serializable{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	//产品Id
	private String id;
	//产品名称
	private String productName;
	//产品数量
	private Integer productNum;
	//自报单价
	private BigDecimal myOfferMoney;
	//单个单价
	private BigDecimal dealPrice;
	//单个总价(成交竞价)
	private BigDecimal dealMoney;
	//备注
	private String remark;
	
	public BigDecimal getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(BigDecimal dealPrice) {
		this.dealPrice = dealPrice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	public BigDecimal getMyOfferMoney() {
		return myOfferMoney;
	}
	public void setMyOfferMoney(BigDecimal myOfferMoney) {
		this.myOfferMoney = myOfferMoney;
	}
	public BigDecimal getDealMoney() {
		return dealMoney;
	}
	public void setDealMoney(BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "BidProductVo [id=" + id + ", productName=" + productName
				+ ", productNum=" + productNum + ", myOfferMoney="
				+ myOfferMoney + ", dealPrice=" + dealPrice + ", dealMoney="
				+ dealMoney + ", remark=" + remark + "]";
	}
	
}
