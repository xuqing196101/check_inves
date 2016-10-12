package ses.util;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: Pager
 * @Description:
 * @author: tkf
 * @date: 2016-10-9下午7:29:53
 */
public class Pager<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 起始位置, 默认为-1 */
	private int start = -1;

	/** 最大查询条数, 默认为-1 */
	private int limit = -1;

	/** 每页条数 */
	private int pageSize = 20;

	/** 当前页 */
	private int currPage = 1;

	/** 总条数 */
	private int total;

	/** 总页数 */
	private int totalPage;

	/** 结果集 */
	private List<T> result;

	/** 消息 */
	private String message;

	private int startRow;

	private int endRow;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
}
