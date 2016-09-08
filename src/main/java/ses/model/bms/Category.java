package ses.model.bms;


import java.util.Date;

public class Category {
	/*
	 * 目录id
	 * 
	 * */
    private String id;
    /*
     * 目录名称
     * 
     * */
    private String name;
    /*
     * 状态（激活、休眠）
     * 
     * */
    private Integer status;
    /*
     * 层级（pid）
     * 
     * 
     * */
    private String ancestry;
    /*
     * 创建时间
     * 
     * */
    private Date createdAt;
    /*
     * 更新时间
     * 
     * */
    private Date updatedAt;
    /*
     * 图片（附件上传）
     * 
     * 
     * */
    private String attchment;
    /*
     * 描述
     * 
     * */
    private String description;
    /*
     * 是否为末级
     * 
     * */
    private String isEnd;
    /*
     * 前台展示优先级
     * 
     * */
    private Integer code;
    /*
     * 排序号
     * 
     * */
    private Integer orderNum;
    /*
     * 是否删除
     * 
     * */
    private Integer isDeleted;

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAncestry() {
        return ancestry;
    }

    public void setAncestry(String ancestry) {
        this.ancestry = ancestry;
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

    public String getAttchment() {
        return attchment;
    }

    public void setAttchment(String attchment) {
        this.attchment = attchment == null ? null : attchment.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd == null ? null : isEnd.trim();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}