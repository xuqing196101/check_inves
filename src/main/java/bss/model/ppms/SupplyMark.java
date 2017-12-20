package bss.model.ppms;
/**
 * 
 * @Title: SupplyMark
 * @Description: 供应商评分  模型3 模型4
 * @author: Tian Kunfeng
 * @date: 2016-11-8下午4:24:29
 */
public class SupplyMark {
	private double prarm;  // 基准参数
	private String supplierId;//供应商id
	private String markTermId ;//打分项id
	private String packageId ;//包id
	private int sort;//排名
	private double score;//得分
	private Integer scoreTotal;//总分是否为0分的标识
	private String scoreContent;//总分是否为0分的标识
	
	public String getScoreContent() {
    return scoreContent;
  }
  public void setScoreContent(String scoreContent) {
    this.scoreContent = scoreContent;
  }
  public double getPrarm() {
		return prarm;
	}
	public Integer getScoreTotal() {
    return scoreTotal;
  }
  public void setScoreTotal(Integer scoreTotal) {
    this.scoreTotal = scoreTotal;
  }
  public void setPrarm(double prarm) {
		this.prarm = prarm;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getMarkTermId() {
		return markTermId;
	}
	public void setMarkTermId(String markTermId) {
		this.markTermId = markTermId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
}
