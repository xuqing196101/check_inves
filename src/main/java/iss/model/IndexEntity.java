package iss.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 
* <p>Title:IndexEntity </p>
* <p>Description: 需要加到索引中的属性</p>
* <p>Company: ses </p> 
* @author MRlovablee
* @date 2016-5-30上午9:23:29
 */
public class IndexEntity implements Serializable{
	/**
	 * 文章id
	 */
	@Field
	private String id;
	
	/**
	 * 文章标题
	 */
	@Field
	private String title;
	
	/**
	 * 发布时间
	 */
	@Field
	private Date publishtime;
	
	/**
	 * 类型名称
	 */
	@Field
	private String articlename;
	
	/**
	 * 内容
	 */
	@Field
	private String context;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}
	public String getArticlename() {
		return articlename;
	}
	public void setArticlename(String articlename) {
		this.articlename = articlename;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
}
