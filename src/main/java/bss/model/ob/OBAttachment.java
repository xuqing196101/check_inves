package bss.model.ob;

import iss.model.ps.Article;

import java.io.Serializable;
import java.util.Date;
/**

* @Title:BidAnnouncementAttach 
* @Description: 网上竞价附件实体类
* @author YangHongLiang
 */
public class OBAttachment implements Serializable {
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	/**
     * @Fields id : 主键
     */
    private String id;
    /**
     * @Fields bidAnnouncement : 所属招标公告
     */
    private Article article;
    /**
     * @Fields name : 文件名
     */
    private String name;
    /**
     * @Fields type : 文件类型
     */
    private String type;
    /**
     * @Fields size : 文件大小
     */
    private Float size;
    /**
     * @Fields path : 文件路径
     */
    private String path;
    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;
    /**
     * @Fields updatedAt : 修改时间
     */
    private Date updatedAt;
    /**
     * @Fields isDeleted : 是否删除
     */
    private Integer isDeleted;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Float getSize() {
		return size;
	}
	public void setSize(Float size) {
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
   
}