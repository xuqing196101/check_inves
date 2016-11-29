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
	
	String[] typeId;//id
	String[] expertsTypeId;//专家类型
	String[] extCount;//专家数量
	String[] extQualifications;//执业资格
	String[] extCategoryName;//品目name
	String[] extCategoryId;//品目id
	String[] isSatisfy;//是否满足
	String[] expertsFrom;//专家来源
	
	
	
	public String[] getTypeId() {
		return typeId;
	}
	public void setTypeId(String[] typeId) {
		this.typeId = typeId;
	}
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
	public String[] getExtCategoryName() {
		return extCategoryName;
	}
	public void setExtCategoryName(String[] extCategoryName) {
		this.extCategoryName = extCategoryName;
	}
	public String[] getExtCategoryId() {
		return extCategoryId;
	}
	public void setExtCategoryId(String[] extCategoryId) {
		this.extCategoryId = extCategoryId;
	}
	public String[] getIsSatisfy() {
		return isSatisfy;
	}
	public void setIsSatisfy(String[] isSatisfy) {
		this.isSatisfy = isSatisfy;
	}
    /**
     * @return Returns the expertsFrom.
     */
    public String[] getExpertsFrom() {
        return expertsFrom;
    }
    /**
     * @param expertsFrom The expertsFrom to set.
     */
    public void setExpertsFrom(String[] expertsFrom) {
        this.expertsFrom = expertsFrom;
    }
	
 }
