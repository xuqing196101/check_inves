package bss.model.ob;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import ses.model.sms.Supplier;

public class OBProjectResult {
	private String id;

	//产品ID
	private String productId;

	//竞价标题ID
	private String projectId;

	//供应商ID
	private String supplierId;

	//排名
	private Integer ranking;

	//成交比例
	private String proportion;

	//供应商提供的报价
	private BigDecimal offerPrice;

	private BigDecimal totalAmount;

	private Date createTime;

	//是否接受的状态： -1默认		0表示不接受	1表示第一轮接受		2表示第二轮接受		第二轮放弃状态仍为1
	private Integer status;

	private String createrId;

	private String remark;

	private Date createdAt;

	private Date updatedAt;

	//竞价结果数量
	private Integer resultCount;

	private OBSupplier supplier;
	//竞价产品 信息
    private List<OBProductInfo> productInfo;
	/**
	 * 供应商自报单价总和
	 */
	private Integer countOfferPrice;

	/**
	 * 金额总计
	 */
	private Integer countTotalAmount;

	/**
	 * 成交数量总和
	 */
	private Integer countresultCount;

	
	public List<OBProductInfo> getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(List<OBProductInfo> productInfo) {
		this.productInfo = productInfo;
	}

	public Integer getCountOfferPrice() {
		return countOfferPrice;
	}

	public void setCountOfferPrice(Integer countOfferPrice) {
		this.countOfferPrice = countOfferPrice;
	}

	public Integer getCountTotalAmount() {
		return countTotalAmount;
	}

	public void setCountTotalAmount(Integer countTotalAmount) {
		this.countTotalAmount = countTotalAmount;
	}

	public Integer getCountresultCount() {
		return countresultCount;
	}

	public void setCountresultCount(Integer countresultCount) {
		this.countresultCount = countresultCount;
	}

	public OBSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(OBSupplier supplier) {
		this.supplier = supplier;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId == null ? null : supplierId.trim();
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion == null ? null : proportion.trim();
	}

	public BigDecimal getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(BigDecimal offerPrice) {
		this.offerPrice = offerPrice;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId == null ? null : createrId.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}