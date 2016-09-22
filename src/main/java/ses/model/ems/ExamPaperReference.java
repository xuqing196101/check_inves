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
	private String userId;
	
	private String paperId;
	
	private String unitName;

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
	
	
}
