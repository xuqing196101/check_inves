package ses.model.sms;

public class ImportSupplierWithBLOBs extends ImportSupplier {
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String civilAchievement;

    private String remark;

    public String getCivilAchievement() {
        return civilAchievement;
    }

    public void setCivilAchievement(String civilAchievement) {
        this.civilAchievement = civilAchievement == null ? null : civilAchievement.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}