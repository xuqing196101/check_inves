package ses.model.ems;

import java.io.Serializable;
import java.util.Date;

public class ExpertTitle implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键ID **/
	private String id;
	
	/** 执业资格职称 **/
	private String qualifcationTitle;
	
	/** 取得职称时间 **/
	private Date  getTitleTime;
	
	/** 创建时间 **/
    private Date createdAt;
    
    /** 修改时间 **/
    private Date updateAt; 
    
    /** 状态**/
    private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQualifcationTitle() {
		return qualifcationTitle;
	}

	public void setQualifcationTitle(String qualifcationTitle) {
		this.qualifcationTitle = qualifcationTitle;
	}

	public Date getGetTitleTime() {
		return getTitleTime;
	}

	public void setGetTitleTime(Date getTitleTime) {
		this.getTitleTime = getTitleTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    

}
