package bss.model.ob;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Ma Mingwei
 *         <p>
 *         Description:竞价管理-结果查询 页面信息封装类<br/>
 *         关联信息比较多,暂不考虑继承
 *         </p>
 * 
 */
public class ConfirmInfoVo implements Serializable {
	
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	//供应商结果 id
    private String resultId;
	// 竞价id
	private String projectId;
	//供应商 id
	private String supplierId;
	// 竞价标题
	private String quoteName;
	// 排名
	private Integer ranking;
	// 是否中标
	private String bidStatus;
	// 中标比例字段 第一轮
	private Integer firstRatio;
	// 中标比例字段 第二轮
	private Integer SecondRatio;
	// 确认开始时间
	private Date confirmStarttime;
	// 确认结束时间
	private Date confirmOvertime;
	// 第二轮确认结束时间
	private Date secondOvertime;
	// 第一轮确认结束时间段
	private Integer confirmTime;
	// 第二轮确认结束时间段
	private Integer confirmTimeSecond;
	// 竞价时间段
	private Integer quoteTime;
	// 产品报价列表
	private List<OBResultsInfo> OBResultsInfo;

	public String getResultId() {
		return resultId;
	}


	public String getSupplierId() {
		return supplierId;
	}


	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}


	public void setResultId(String resultId) {
		this.resultId = resultId;
	}


	public String getProjectId() {
		return projectId;
	}

	
	public List<OBResultsInfo> getOBResultsInfo() {
		return OBResultsInfo;
	}


	public void setOBResultsInfo(List<OBResultsInfo> oBResultsInfo) {
		OBResultsInfo = oBResultsInfo;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getConfirmStarttime() {
		return confirmStarttime;
	}

	public void setConfirmStarttime(Date confirmStarttime) {
		this.confirmStarttime = confirmStarttime;
	}

	public Date getSecondOvertime() {
		return secondOvertime;
	}

	public void setSecondOvertime(Date secondOvertime) {
		this.secondOvertime = secondOvertime;
	}

	public Integer getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Integer confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Integer getConfirmTimeSecond() {
		return confirmTimeSecond;
	}

	public void setConfirmTimeSecond(Integer confirmTimeSecond) {
		this.confirmTimeSecond = confirmTimeSecond;
	}

	public Integer getQuoteTime() {
		return quoteTime;
	}

	public void setQuoteTime(Integer quoteTime) {
		this.quoteTime = quoteTime;
	}

	public String getQuoteName() {
		return quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(String bidStatus) {
		this.bidStatus = bidStatus;
	}

	public Date getConfirmOvertime() {
		return confirmOvertime;
	}

	public void setConfirmOvertime(Date confirmOvertime) {
		this.confirmOvertime = confirmOvertime;
	}

	public Integer getFirstRatio() {
		return firstRatio;
	}

	public void setFirstRatio(Integer firstRatio) {
		this.firstRatio = firstRatio;
	}

	public Integer getSecondRatio() {
		return SecondRatio;
	}

	public void setSecondRatio(Integer secondRatio) {
		SecondRatio = secondRatio;
	}

	@Override
	public String toString() {
		return "ConfirmInfoVo [projectId=" + projectId + ", quoteName="
				+ quoteName + ", ranking=" + ranking + ", bidStatus="
				+ bidStatus + ", firstRatio=" + firstRatio + ", SecondRatio="
				+ SecondRatio + ", confirmStarttime=" + confirmStarttime
				+ ", confirmOvertime=" + confirmOvertime + ", secondOvertime="
				+ secondOvertime + ", confirmTime=" + confirmTime
				+ ", confirmTimeSecond=" + confirmTimeSecond + ", quoteTime="
				+ quoteTime + ", OBResultsInfo=" + OBResultsInfo + "]";
	}

}
