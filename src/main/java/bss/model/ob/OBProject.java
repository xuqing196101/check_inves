package bss.model.ob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 *  @Title:BidAnnouncementAttach   @Description: 存储竞价信息的内容  @author 注释--Ma
 * Mingwei
 */
public class OBProject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 竞价规则id
	 */
	private String ruleId;
	private OBRule obRule;
	private OBProjectRule obProjectRule;
	private String id;
	/**
	 * 竞价编号
	 */
	private BigDecimal projectNumber;
	/**
	 * @Fields name : 竞价名称
	 */
	private String name;
	/**
	 * @Fields deliveryDeadline : 交货截止时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryDeadline;
	/**
	 * @Fields deliveryAddress : 交货地点
	 */
	private String deliveryAddress;
	/**
	 * @Fields tradedSupplierCount : 成交供应商数
	 */
	private Integer tradedSupplierCount;
	/**
	 * @Fields transportFees : 运杂费
	 */
	private String transportFees;

	/**
	 * 乙方包干使用运杂费
	 */
	private BigDecimal transportFeesPrice;

	/**
	 * @Fields demandUnit : 需求单位
	 */
	private String demandUnit;
	/**
	 * @Fields contactName : 联系人
	 */
	private String contactName;
	/**
	 * @Fields contactTel : 联系电话
	 */
	private String contactTel;
	/**
	 * @Fields orgId : 采购机构
	 */
	private String orgId;
	/**
	 * @Fields orgContactName : 采购联系人
	 */
	private String orgContactName;
	/**
	 * @Fields orgContactTel : 采购联系电话
	 */
	private String orgContactTel;
	/**
	 * @Fields startTime : 竞价开始时间
	 */
	private Date startTime;
	/**
	 * @Fields endTime : 竞价结束时间
	 */
	private Date endTime;
	/**
	 * @Fields content : 竞价内容
	 */
	private String content;
	/**
	 * @Fields attachmentId : 附件
	 */
	private String attachmentId;
	/**
	 * @Fields remark : 备注
	 */
	private String remark;
	/**
	 * @Fields status : 发布状态
	 */

	/** 竞价状态 0：暂存 1已发布 2报价中 3：竞价结束 4.流拍 5.第一轮待确认 6.第二轮待确认  7第二次竞价**/
	/**PROJECT_SUPPLIER表中remark字段：
	 * 4.第一轮接受  3.放弃   
	 * 42.第二轮接受  32.第二轮放弃
	 * 666.未中标
	 * **/
	
	/***
	 *  status        remark
	 *   0:暂存
	 *   1:已发布		   0竞价未开始
	 *   2：报价中（1）	   1.已报价  0.未报价
	 *   7：报价中（2）            21.已报价  20.未报价
	 *   5：待确认（1）             4.已确定3，拒绝
	 *   6：待确认（2）            42.已确定32，拒绝  22说明第一轮已成交
	 *                 666.未中标
	 *   3：竞价结束
	 *   4.流拍               
	 *                 -1无效状态
	 * 
	 * 
	 * 
	 */
	private Integer status;
	/**
	 * @Fields createrId : 创建人ID
	 */
	private String createrId;
	/**
	 * @Fields formId : 来源竞价ID
	 */
	private String formId;
	
	/**
	 * 是否采用应急采购标识
	 * 默认-1 不采用应急   0采用应急
	 */
	private Integer isEmergency;
	/**
	 * @Fields turnoverRation : 成交比例
	 */
    
	/** 成交比例 **/
	private Integer turnoverRation;
	/**
	 * @Fields createdAt : 创建时间
	 */
	private Date createdAt;
	/**
	 * @Fields updatedAt : 修改时间
	 */
	private Date updatedAt;
	/**
	 * 竞价 相关的产品
	 */
	private List<OBProductInfo> obProductInfo;

	/**
	 * 页面产品
	 */
	private List<String> productName;
	/**
	 * 页面 限价
	 */
	private List<String> productMoney;
	/**
	 * 页面备注
	 */
	private List<String> productRemark;
	/**
	 * 页面数量
	 */

	private List<String> productCount;
	/***
	 * 选中的供应商id
	 */
	private String[] supplieId;
	// 成交供应商 数量
	private Integer closingSupplier;
	// 合格供应商数量
	private Integer qualifiedSupplier;

	/** 报价截止时间字段 **/
	private Date quoteEndTime;
	/** 二次报价截止时间字段 **/
	private Date quoteEndTimeSecond;

	/**
	 * 删除标识 0 正常 1删除
	 * 
	 * @return
	 */
	private Integer isDelete;

	private Integer offerSupplierNumber;
    /**竞价供应商 关系 **/
	private List<OBProjectSupplier> obProjectSupplier;
	/**竞价 结果**/
	private List<OBProjectResult> obProjectResult;
	
    public Integer getIsEmergency() {
		return isEmergency;
	}
	public void setIsEmergency(Integer isEmergency) {
		this.isEmergency = isEmergency;
	}
	public List<OBProjectResult> getObProjectResult() {
		return obProjectResult;
	}
	public void setObProjectResult(List<OBProjectResult> obProjectResult) {
		this.obProjectResult = obProjectResult;
	}
	public OBProjectRule getObProjectRule() {
		return obProjectRule;
	}
	public void setObProjectRule(OBProjectRule obProjectRule) {
		this.obProjectRule = obProjectRule;
	}
	public List<OBProjectSupplier> getObProjectSupplier() {
		return obProjectSupplier;
	}
	public void setObProjectSupplier(List<OBProjectSupplier> obProjectSupplier) {
		this.obProjectSupplier = obProjectSupplier;
	}
	public Integer getOfferSupplierNumber() {
		return offerSupplierNumber;
	}
	public void setOfferSupplierNumber(Integer offerSupplierNumber) {
		this.offerSupplierNumber = offerSupplierNumber;
	}
	
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public BigDecimal getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(BigDecimal projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String[] getSupplieId() {
		return supplieId;
	}

	public void setSupplieId(String[] supplieId) {
		this.supplieId = supplieId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getClosingSupplier() {
		return closingSupplier;
	}

	public void setClosingSupplier(Integer closingSupplier) {
		this.closingSupplier = closingSupplier;
	}

	public Integer getQualifiedSupplier() {
		return qualifiedSupplier;
	}

	public void setQualifiedSupplier(Integer qualifiedSupplier) {
		this.qualifiedSupplier = qualifiedSupplier;
	}

	public List<OBProductInfo> getObProductInfo() {
		return obProductInfo;
	}

	public void setObProductInfo(List<OBProductInfo> obProductInfo) {
		this.obProductInfo = obProductInfo;
	}

	public List<String> getProductName() {
		return productName;
	}

	public void setProductName(List<String> productName) {
		this.productName = productName;
	}

	public List<String> getProductMoney() {
		return productMoney;
	}

	public void setProductMoney(List<String> productMoney) {
		this.productMoney = productMoney;
	}

	public List<String> getProductRemark() {
		return productRemark;
	}

	public void setProductRemark(List<String> productRemark) {
		this.productRemark = productRemark;
	}

	public List<String> getProductCount() {
		return productCount;
	}

	public void setProductCount(List<String> productCount) {
		this.productCount = productCount;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgContactName() {
		return orgContactName;
	}

	public void setOrgContactName(String orgContactName) {
		this.orgContactName = orgContactName;
	}

	public String getOrgContactTel() {
		return orgContactTel;
	}

	public void setOrgContactTel(String orgContactTel) {
		this.orgContactTel = orgContactTel;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Date getDeliveryDeadline() {
		return deliveryDeadline;
	}

	public void setDeliveryDeadline(Date deliveryDeadline) {
		this.deliveryDeadline = deliveryDeadline;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress
				.trim();
	}

	public Integer getTradedSupplierCount() {
		return tradedSupplierCount;
	}

	public void setTradedSupplierCount(Integer tradedSupplierCount) {
		this.tradedSupplierCount = tradedSupplierCount;
	}

	public String getTransportFees() {
		return transportFees;
	}

	public void setTransportFees(String transportFees) {
		this.transportFees = transportFees;
	}

	public String getDemandUnit() {
		return demandUnit;
	}

	public void setDemandUnit(String demandUnit) {
		this.demandUnit = demandUnit == null ? null : demandUnit.trim();
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName == null ? null : contactName.trim();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId == null ? null : attachmentId.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId == null ? null : formId.trim();
	}

	public Integer getTurnoverRation() {
		return turnoverRation;
	}

	public void setTurnoverRation(Integer turnoverRation) {
		this.turnoverRation = turnoverRation;
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

	@Override
	public String toString() {
		return "OBProject [ruleId=" + ruleId + ", id=" + id + ", name=" + name
				+ ", deliveryDeadline=" + deliveryDeadline
				+ ", deliveryAddress=" + deliveryAddress
				+ ", tradedSupplierCount=" + tradedSupplierCount
				+ ", transportFees=" + transportFees + ", demandUnit="
				+ demandUnit + ", contactName=" + contactName + ", contactTel="
				+ contactTel + ", orgId=" + orgId + ", orgContactName="
				+ orgContactName + ", orgContactTel=" + orgContactTel
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", content=" + content + ", attachmentId=" + attachmentId
				+ ", remark=" + remark + ", status=" + status + ", createrId="
				+ createrId + ", formId=" + formId + ", turnoverRation="
				+ turnoverRation + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", obProductInfo=" + obProductInfo
				+ ", productName=" + productName + ", productMoney="
				+ productMoney + ", productRemark=" + productRemark
				+ ", productCount=" + productCount + ", supplieId=" + supplieId
				+ ", closingSupplier=" + closingSupplier
				+ ", qualifiedSupplier=" + qualifiedSupplier + "]";
	}

	public OBRule getObRule() {
		return obRule;
	}

	public void setObRule(OBRule obRule) {
		this.obRule = obRule;
	}

	public Date getQuoteEndTime() {
		return quoteEndTime;
	}

	public void setQuoteEndTime(Date quoteEndTime) {
		this.quoteEndTime = quoteEndTime;
	}

	public BigDecimal getTransportFeesPrice() {
		return transportFeesPrice;
	}

	public void setTransportFeesPrice(BigDecimal transportFeesPrice) {
		this.transportFeesPrice = transportFeesPrice;
	}
	public Date getQuoteEndTimeSecond() {
		return quoteEndTimeSecond;
	}
	public void setQuoteEndTimeSecond(Date quoteEndTimeSecond) {
		this.quoteEndTimeSecond = quoteEndTimeSecond;
	}
	
	
}
