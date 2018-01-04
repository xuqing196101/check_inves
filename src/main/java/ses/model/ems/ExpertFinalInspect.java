package ses.model.ems;

import java.util.Date;
/**
 * <p>Title:Expert </p>
 * <p>Description: </p>专家复查记录实体
 */
public class ExpertFinalInspect {
	
	/**主键**/
	private String id;
	
	/**专家ID**/
	private String expertId;
	
	/**附件ID**/
	private String fileId;
	
	/**附件类型**/
	private String fileType;
	
	/** 
	 * 复查状态
	 * 1：一致 
	 * 2：不一致 
	 * 为空是只填写了理由未选择该附件
	 * **/
	private String status;
	
	/**审核理由**/
	private String reason;
	
	/**创建时间**/
	private Date createAt;
	
	/**修改时间**/
	private Date updateAt;
	
	/**标识此记录产生于哪次复查**/
	private String finalInspectNumber;
	
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

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getFinalInspectNumber() {
		return finalInspectNumber;
	}

	public void setFinalInspectNumber(String finalInspectNumber) {
		this.finalInspectNumber = finalInspectNumber;
	}
	
	
	
}
