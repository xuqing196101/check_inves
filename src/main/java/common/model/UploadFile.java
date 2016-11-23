package common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>上传文件对象
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class UploadFile implements Serializable {

    /**
     * @Fields serialVersionUID : TODO(目的和意义)
     */
    private static final long serialVersionUID = 6469914231342058083L;
    /** 主键 */
    private String id;
    /** 文件名称 */
    private String name;
    /** 路径 */
    private String path;
    /** 业务id */
    private String businessId;
    /** 业务类型id  */
    private String typeId;
    /** 文件大小,单位byte */
    private Long size;
    /** 创建时间 */
    private Date createDate;
    /** 修改时间 */
    private Date updateDate;
    /** 是否删除 0:不删,1:删除 */
    private int isDelete;
    /** 表名称 */
    private String tableName;
    /** 状态 */
    private Integer status;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getBusinessId() {
        return businessId;
    }
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public int getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
    
}
