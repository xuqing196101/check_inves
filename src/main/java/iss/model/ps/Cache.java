package iss.model.ps;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: Cache 
* @Description: 封装缓存信息实体类
* @author Easong
* @date 2017年3月24日 下午3:09:24 
*
 */
public class Cache implements Serializable{
	/**
	 * Cache.java
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 缓存名称
	 */
	private String name;
	
	/**
	 * 缓存类型
	 */
	private String type;
	/**
	 * 缓存有效时长
	 */
	private Long time;
	/**
	 * 缓存剩余时长
	 */
	private Long timeout;
	/**
	 * 缓存内容
	 */
	private Object content;

	private Date timeDate;
	
	
	
	public Date getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(Date timeDate) {
		this.timeDate = timeDate;
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
}
