package ses.model.ems;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ExpertTitle implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键ID **/
	private String id;
	
	/** 执业资格职称 **/
	private String qualifcationTitle;
	
	/** 取得职称时间 **/
	 @DateTimeFormat(pattern="yyyy-MM")
	private Date  titleTime;
	
	/** 创建时间 **/
    private Date createdAt;
    
    /** 修改时间 **/
    private Date updatedAt; 
    
    /** 状态**/
    private String status;

    private String expertId;
    
    private String expertTypeId;
    
	public String getExpertTypeId() {
		return expertTypeId;
	}

	public void setExpertTypeId(String expertTypeId) {
		this.expertTypeId = expertTypeId;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

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

	public Date getTitleTime() {
		return titleTime;
	}

	public void setTitleTime(Date titleTime) {
		this.titleTime = titleTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    

}
