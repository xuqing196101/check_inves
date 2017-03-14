package bss.model.ob;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Ma Mingwei
 * <p>Description:竞价管理-结果查询 页面信息封装类<br/>
 * 		关联信息比较多,暂不考虑继承
 * </p>
 *
 */
public class ConfirmInfoVo {

	//竞价标题
	private String quoteName;
	//排名
	private Integer ranking;
	//是否中标
	private String bidStatus;
	//中标比例字段
	private String bidRatio;
	//确认结束时间
	private Date confirmOvertime;
	//产品列表
	private List<OBProduct> productList;
	private List<BidProductVo> bidProductList;
	public List<BidProductVo> getBidProductList() {
		return bidProductList;
	}
	public void setBidProductList(List<BidProductVo> bidProductList) {
		this.bidProductList = bidProductList;
	}
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getBidStatus() {
		return bidStatus;
	}
	public void setBidStatus(String bidStatus) {
		this.bidStatus = bidStatus;
	}
	public String getBidRatio() {
		return bidRatio;
	}
	public void setBidRatio(String bidRatio) {
		this.bidRatio = bidRatio;
	}
	public Date getConfirmOvertime() {
		return confirmOvertime;
	}
	public void setConfirmOvertime(Date confirmOvertime) {
		this.confirmOvertime = confirmOvertime;
	}
	public List<OBProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<OBProduct> productList) {
		this.productList = productList;
	}
	
}