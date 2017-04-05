package iss.model.ps;

import java.util.ArrayList;
import java.util.List;


/**
 * 
* @ClassName: Cache 
* @Description: 封装缓存分页
* @author Easong
* @date 2017年3月24日 下午3:09:24 
*
 */
public class CachePage<T> {
	/**
	 * 存储缓存信息
	 */
	private List<T> list = new ArrayList<T>();
	
	/**
	 * 总页数
	 */
	private Integer pages;
	
	/**
	 * 总条数
	 */
	private Integer total;
	
	/**
	 * 开始页索引
	 */
	private Integer startRow;
	
	
	/**
	 * 结束页索引
	 */
	private Integer endRow;
	
	/**
	 * 当前页
	 */
	private Integer pageNum;
	
	/**
	 * 每页显示条数
	 */
	private Integer pageSize;
	
	

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	
}
