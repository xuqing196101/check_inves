package bss.model.ob;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 
* @ClassName: OBSpecialDate 
* @Description: 特殊节假日实体类
* @author Easong
* @date 2017年3月20日 下午1:17:21 
*
 */
public class OBSpecialDate implements Serializable{
    /** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	private String id;

    /**
     * 创建人
     */
    private String createrId;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date specialDate;
    
    /**1：上班     0：放假**/
    private String dateType;

    /**
     * 创建日期
     */
    private Date createdAt;

    /**
     * 备注
     */
    private String remark;
    /**
     * 修改日期
     */
    private Date updatedAt;

    /**
     * 创建人姓名
     */
    private String createrName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public Date getSpecialDate() {
        return specialDate;
    }

    public void setSpecialDate(Date specialDate) {
        this.specialDate = specialDate;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType == null ? null : dateType.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName == null ? null : createrName.trim();
    }
}