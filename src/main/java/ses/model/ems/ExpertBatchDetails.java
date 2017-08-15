package ses.model.ems;

import java.util.Date;

public class ExpertBatchDetails {
	
	/**批次详情表ID*/
	private String id;
	/**专家ID*/
	private String expertId;
	/**批次编号*/
	private String batchNumber;
	/**批次详情编号*/
	private String batchDetailsNumber;
	/**专家编号*/
	private String count;
	/**批次名称*/
	private String batchName;
	/**所属组ID*/
	private String groupId;
	/**组名*/
	private String groupName;
	/**创建时间*/
	private Date createdAt;
	/**修改时间*/
	private Date updatedAt;
	/**所属批次ID*/
	private String batchId;
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getBatchDetailsNumber() {
		return batchDetailsNumber;
	}
	public void setBatchDetailsNumber(String batchDetailsNumber) {
		this.batchDetailsNumber = batchDetailsNumber;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
