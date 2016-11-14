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
	public double getPrarm() {
		return prarm;
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
