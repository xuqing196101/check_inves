package ses.model.ems;

import java.io.Serializable;

/**
 * 
 * Description:专家审核公示实体封装
 * 
 * @author Easong
 * @version 2017年6月27日
 * @since JDK1.7
 */
public class ExpertPublicity extends Expert implements Serializable{

	/**
	 * ExpertPublicity.java
	 */
	private static final long serialVersionUID = 1L;
	
	// 选择的品目数量（小类）
	private Integer passCateCount;
	
	// 未通过品目数量（小类）
	private Integer noPassCateCount;

	// 审核意见
	private String auditOpinion;
	// 专家编号
	private String expertNum;

	public Integer getPassCateCount() {
		return passCateCount;
	}

	public void setPassCateCount(Integer passCateCount) {
		this.passCateCount = passCateCount;
	}

	public Integer getNoPassCateCount() {
		return noPassCateCount;
	}

	public void setNoPassCateCount(Integer noPassCateCount) {
		this.noPassCateCount = noPassCateCount;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getExpertNum() {
		return expertNum;
	}

	public void setExpertNum(String expertNum) {
		this.expertNum = expertNum;
	}
}
