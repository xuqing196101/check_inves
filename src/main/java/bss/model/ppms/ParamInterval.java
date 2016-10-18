package bss.model.ppms;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: ParamInterval
 * @Description: 评分细则  参数区间   八大模型
 * @author: Tian Kunfeng
 * @date: 2016-10-17下午6:58:17
 */
public class ParamInterval implements Serializable {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//
	private String scoreModelId;//模型id
	private String startParam;//起始参数值
	private String endParam;//结束参数值
	private String startRelation;// 与起始参数值之间的关系    0代表<,1代表>,2代表=
	private String endRelation;//与结束参数值之间的关系0代表<,1代表>,2代表=
	private String score;//得分
	private String explain;//解释
	private Integer isDeleted;//解释
	private Date createdAt;//解释
	private Date updatedAt;//解释
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScoreModelId() {
		return scoreModelId;
	}
	public void setScoreModelId(String scoreModelId) {
		this.scoreModelId = scoreModelId;
	}
	public String getStartParam() {
		return startParam;
	}
	public void setStartParam(String startParam) {
		this.startParam = startParam;
	}
	public String getEndParam() {
		return endParam;
	}
	public void setEndParam(String endParam) {
		this.endParam = endParam;
	}
	public String getStartRelation() {
		return startRelation;
	}
	public void setStartRelation(String startRelation) {
		this.startRelation = startRelation;
	}
	public String getEndRelation() {
		return endRelation;
	}
	public void setEndRelation(String endRelation) {
		this.endRelation = endRelation;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
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
	
	
	
}
