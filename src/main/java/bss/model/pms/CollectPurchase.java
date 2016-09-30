package bss.model.pms;

public class CollectPurchase {
    private String collectPlanId;

    private String planNo;

    public String getCollectPlanId() {
        return collectPlanId;
    }

    public void setCollectPlanId(String collectPlanId) {
        this.collectPlanId = collectPlanId == null ? null : collectPlanId.trim();
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }
}