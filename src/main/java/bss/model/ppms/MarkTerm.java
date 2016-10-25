package bss.model.ppms;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: ScoreStandard
 * @Description: 评分标准
 * @author: Tian Kunfeng
 * @date: 2016-10-17下午7:45:13
 */
public class MarkTerm implements Serializable{
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//
	private String pid;//父id
	private String name;//名称
	private String pname;//父节点名称
	private String url;//
	private boolean checked;//
	private Integer isDeleted;//
	private Date createdAt;//
	private Date updatedAt;//
	private String isRoot ;
	public MarkTerm() {
		super();
	}
	public MarkTerm(String id, String pid, String name, String pname) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.pname = pname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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
	public String getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(String isRoot) {
		this.isRoot = isRoot;
	}
	
}
