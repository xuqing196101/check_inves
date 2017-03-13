package bss.model.ob;

import java.math.BigDecimal;
import java.util.Date;

public class OBProjectResult {
	private String id;

	private String productId;

	private String projectId;

	private String supplierId;

	private Integer ranking;

	private String proportion;

	private BigDecimal offerPrice;

	private BigDecimal totalAmount;

	private Date createTime;

	private Integer status;

	private String createrId;

	private String remark;

	private Date createdAt;

	private Date updatedAt;

	private Integer resultCount;

	private OBSupplier supplier;

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