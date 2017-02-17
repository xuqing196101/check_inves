package ses.model.sms;

import java.io.Serializable;

/**
 * 版权：(C) 版权所有
 * <简述>
 *  供应商售后服务机构表对应实体类
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0
 * @since    2017年2月17日 18:18:08
 * @see
 */
public class SupplierAfterSaleDep implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** 主键ID **/
    private String id;
    
    /** 机构名称 **/
    private String name;
    
    /** 类别 **/
    private Integer type;
    
    /** 地址 **/
    private String address;
    
    /** 负责人姓名 **/
    private String leadName;
    
    /** 联系方式 **/
    private String mobile;

    /** 供应商ID **/
    private String supplierId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

}
