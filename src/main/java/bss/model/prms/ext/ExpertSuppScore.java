package bss.model.prms.ext;

/**
 * 版权：(C) 版权所有 2011-2016
 * <简述>
 * 专家详细评审打分的封装类
 * <详细描述>
 * @author   Wang Huijie
 * @version  
 * @since 
 * @see
 */
public class ExpertSuppScore {
	/** 包编号 */
    private String packageId;
    /** 供应商编号 */
    private String supplierId;
    /** 专家编号 */
    private String expertId;
    /** 专家给供应商的总分 */
    private String score;
    public String getPackageId() {
        return packageId;
    }
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    public String getExpertId() {
        return expertId;
    }
    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }
    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    
}
