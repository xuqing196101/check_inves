package common.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: AttUploadAnalyze 
* @Description: 统计文件上传实体
* @author Easong
* @date 2017年5月12日 上午10:41:37 
*
 */
public class AttUploadAnalyze implements Serializable{
    /** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	private String id;

    /** 用户类型  1：专家  2：供应商 3：后台管理员  **/
    private String typeId;

    /** 统计类型数据字典ID **/
    private Integer type;

    /** 创建时间 **/
    private Date createdAt;

    /** 0:未删除 1：删除 **/
    private Integer isDeleted;

    /** 日 **/
    private Integer indexDay;
    
    /** 周 **/
    private Integer indexWeek;
    
    /** 月  **/
    private Integer indexMonth;
    
    /** 上传文件数 **/
    private Integer uploadNum;
    
    private String remark;

    public Integer getIndexDay() {
		return indexDay;
	}

	public void setIndexDay(Integer indexDay) {
		this.indexDay = indexDay;
	}

	public Integer getIndexWeek() {
		return indexWeek;
	}

	public void setIndexWeek(Integer indexWeek) {
		this.indexWeek = indexWeek;
	}

	public Integer getIndexMonth() {
		return indexMonth;
	}

	public void setIndexMonth(Integer indexMonth) {
		this.indexMonth = indexMonth;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getUploadNum() {
        return uploadNum;
    }

    public void setUploadNum(Integer uploadNum) {
        this.uploadNum = uploadNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    
}