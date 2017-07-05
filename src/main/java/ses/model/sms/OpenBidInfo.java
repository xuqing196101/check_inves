package ses.model.sms;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * Description:供应商在线投标 开标一览表
 * 
 * @author YangHongLiang
 * @version 2017-5-27
 * @since JDK1.7
 */
public class OpenBidInfo {
    /**
     * 主键
     */
    private String id;
    /**
     * 项目详细表id  PROJECTS_DETAIL_ID  T_BSS_PPMS_PROJECTS_DETAIL.ID
     */
    private String projectsDetailId;
    /**
     * 供应商id
     */
    private String supplierId;
    /**
     * 单价含税
     */
    private BigDecimal unitPrice;
    /**
     * 交货时间
     */
    private String deliveryTheGoods;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;
    /**
     * 是否暂存 0暂存  1确定
     */
    private Integer isSave;
    /**
     * 是否删除 0删除  1可用
     */
    private Integer isDeleted;
    
    public Integer getIsDeleted() {
      return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
      this.isDeleted = isDeleted;
    }

    public void setIsSave(Integer isSave) {
      this.isSave = isSave;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectsDetailId() {
        return projectsDetailId;
    }

    public void setProjectsDetailId(String projectsDetailId) {
        this.projectsDetailId = projectsDetailId == null ? null : projectsDetailId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDeliveryTheGoods() {
        return deliveryTheGoods;
    }

    public void setDeliveryTheGoods(String deliveryTheGoods) {
        this.deliveryTheGoods = deliveryTheGoods == null ? null : deliveryTheGoods.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getIsSave() {
      return isSave;
    }
}