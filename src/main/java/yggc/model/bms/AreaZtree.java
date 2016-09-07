package yggc.model.bms;

import java.io.Serializable;
import java.util.Date;

public class AreaZtree implements Serializable{
	/**
	 * 节点的id
	 */
	private String id;
	
	/**
	 * 节点的名称
	 */
	private String name;
	
	/**
	 * 节点的父节点id
	 */
	private String pId;
	
	/**
	 * 是否是父类
	 */
	private String isParent;
	
	/**
	 * 所属年份
	 */
	private Date year;
	
	
	
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	
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
	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	public AreaZtree(String id, String name, String pId, String isParent) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.isParent = isParent;
	}
	public AreaZtree() {
		super();
	}
	public Date getYear() {
		return year;
	}
	public void setYear(Date year) {
		this.year = year;
	}
	
}
