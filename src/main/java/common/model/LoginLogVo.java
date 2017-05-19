package common.model;

import java.io.Serializable;


/**
 * 
 * @ClassName: LoginLogVo
 * @Description: 登录日志查询条件实体类
 * @author Easong
 * @date 2017年5月19日 下午2:12:24
 * 
 */
public class LoginLogVo extends LoginLog implements Serializable{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	// 结束时间
	private String startDate;

	// 结束时间
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
