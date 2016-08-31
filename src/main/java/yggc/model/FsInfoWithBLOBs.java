package yggc.model;

public class FsInfoWithBLOBs extends FsInfo {
    private String supplyLevel;

    private String supplierRemark;

    public String getSupplyLevel() {
        return supplyLevel;
    }

    public void setSupplyLevel(String supplyLevel) {
        this.supplyLevel = supplyLevel == null ? null : supplyLevel.trim();
    }

    public String getSupplierRemark() {
        return supplierRemark;
    }

    public void setSupplierRemark(String supplierRemark) {
        this.supplierRemark = supplierRemark == null ? null : supplierRemark.trim();
    }
}