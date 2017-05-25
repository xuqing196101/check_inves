package iss.model.hl;

import java.util.Date;

/**
 * 
 * Description： 服务热线实体类
 * 
 * @author  zhang shubin
 * @version  
 * @since JDK1.7
 * @date 2017年5月25日 下午2:24:23 
 *
 */
public class ServiceHotline {
	
	/**
	 * 主键ID
	 */
    private String id;

    /**
     * 服务内容
     */
    private String servicecontent;

    /**
     * 联系电话
     */
    private String contactphonenumber;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 创建人
     */
    private String createrId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标识
     * 0	未删除
     * 1	删除
     */
    private Integer isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getServicecontent() {
        return servicecontent;
    }

    public void setServicecontent(String servicecontent) {
        this.servicecontent = servicecontent == null ? null : servicecontent.trim();
    }

    public String getContactphonenumber() {
        return contactphonenumber;
    }

    public void setContactphonenumber(String contactphonenumber) {
        this.contactphonenumber = contactphonenumber == null ? null : contactphonenumber.trim();
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}