package ses.model.oms;

import java.math.BigDecimal;
import java.util.Date;

public class PurchaseRoom {
    private String id;

    private String purchaseDepId;

    private Integer typeName;

    private BigDecimal area;

    private Integer isNetConnect;

    private String netConnectStyle;

    private Integer hasVideoSys;

    private Date createdAt;

    private Date updatedAt;

    private String code;

    private String location;

    private Integer capacity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPurchaseDepId() {
        return purchaseDepId;
    }

    public void setPurchaseDepId(String purchaseDepId) {
        this.purchaseDepId = purchaseDepId == null ? null : purchaseDepId.trim();
    }

    public Integer getTypeName() {
        return typeName;
    }

    public void setTypeName(Integer typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getIsNetConnect() {
        return isNetConnect;
    }

    public void setIsNetConnect(Integer isNetConnect) {
        this.isNetConnect = isNetConnect;
    }

    public String getNetConnectStyle() {
        return netConnectStyle;
    }

    public void setNetConnectStyle(String netConnectStyle) {
        this.netConnectStyle = netConnectStyle == null ? null : netConnectStyle.trim();
    }

    public Integer getHasVideoSys() {
        return hasVideoSys;
    }

    public void setHasVideoSys(Integer hasVideoSys) {
        this.hasVideoSys = hasVideoSys;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}