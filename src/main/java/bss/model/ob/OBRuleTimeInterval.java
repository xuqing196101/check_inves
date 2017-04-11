package bss.model.ob;

import java.util.Date;

/**
 * 
* @ClassName: OBRuleTimeInterval 
* @Description: 记录竞价规则各个时间段
* @author Easong
* @date 2017年3月31日 上午9:59:55 
*
 */
public class OBRuleTimeInterval {
	
	/**
	 * 竞价项目ID
	 */
	private String projectId;
	
	/**
	 * 报价开始时间
	 */
	private Date quotoTimeDate;
	
	/**
	 * 报价结束时间
	 */
	private Date endQuotoTimeDate;
	/**
	 * 二次报价结束时间
	 */
	private Date endQuotoTimeDateSecond;
	
	/**
	 * 第一轮确认时间
	 */
	private Date confirmTime;
	
	/**
	 * 第二轮确认时间
	 */
	private Date secondConfirmTime;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getQuotoTimeDate() {
		return quotoTimeDate;
	}

	public void setQuotoTimeDate(Date quotoTimeDate) {
		this.quotoTimeDate = quotoTimeDate;
	}

	public Date getEndQuotoTimeDate() {
		return endQuotoTimeDate;
	}

	public void setEndQuotoTimeDate(Date endQuotoTimeDate) {
		this.endQuotoTimeDate = endQuotoTimeDate;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getSecondConfirmTime() {
		return secondConfirmTime;
	}

	public void setSecondConfirmTime(Date secondConfirmTime) {
		this.secondConfirmTime = secondConfirmTime;
	}

	public Date getEndQuotoTimeDateSecond() {
		return endQuotoTimeDateSecond;
	}

	public void setEndQuotoTimeDateSecond(Date endQuotoTimeDateSecond) {
		this.endQuotoTimeDateSecond = endQuotoTimeDateSecond;
	}
	
	
	
}
