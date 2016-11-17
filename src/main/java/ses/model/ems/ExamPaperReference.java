/**
 * 
 */
package ses.model.ems;

/**
 * @Title:ExamPaperReference
 * @Description: 考卷的参考人员实体类(导入Excel)
 * @author ZhaoBo
 * @date 2016-9-22下午4:38:18
 */
public class ExamPaperReference {
	private String relName;
	
	private String userId;
	
	private String paperId;
	
	private String unitName;
	
	private String card;
	
	private String code;
	
	private String idNumber;
	
	private String ruleId;
	
	private Integer userType;
	
	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	
}
