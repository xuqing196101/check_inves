/**
 * 
 */
package ses.model.ems;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月30日上午10:35:19
 * @since  JDK 1.7
 */
public class ExtConTypeArray {
	String[] expertsTypeId;//专家类型
	String[] extCount;//专家数量
	String[] extQualifications;//执业资格
	String[] categoryName;//品目name
	String[] categoryId;//品目id
	String[] isSatisfy;//是否满足
	public String[] getExpertsTypeId() {
		return expertsTypeId;
	}
	public void setExpertsTypeId(String[] expertsTypeId) {
		this.expertsTypeId = expertsTypeId;
	}
	public String[] getExtCount() {
		return extCount;
	}
	public void setExtCount(String[] extCount) {
		this.extCount = extCount;
	}
	public String[] getExtQualifications() {
		return extQualifications;
	}
	public void setExtQualifications(String[] extQualifications) {
		this.extQualifications = extQualifications;
	}
	public String[] getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String[] categoryName) {
		this.categoryName = categoryName;
	}
	public String[] getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String[] categoryId) {
		this.categoryId = categoryId;
	}
	public String[] getIsSatisfy() {
		return isSatisfy;
	}
	public void setIsSatisfy(String[] isSatisfy) {
		this.isSatisfy = isSatisfy;
	}
	
 }
