package ses.model.oms;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;



public class Orgnization implements Serializable {
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	@NotBlank(message = "机构名称不能为空") 
	@Length(min=1,max=50,message="长度不可超过50")
    private String name;

    /**
     * @Fields typeName : 0:需求部门 1:采购机构 2:管理部门 3其他 
     */
    private String typeName;
    
    private String address;

    private String mobile;

    private String postCode;

    private String orgCode;

    private String telephone;

    private String areaId;

    private String detailAddr;

    private String fax;

    private String website;

    private String princinpal;

    private String princinpalIdCard;

    private String nature;

    private Integer isDeleted;

    private Date createdAt;

    private Date updatedAt;

    private String orgLevel;

    private String position;

    private String parentId;
    private String parentName;

    private String depId;

    private String isRoot;
    
    @NotBlank(message="简称不能为空")
    private String shortName;

    private String describtion;
    
    private String email;
    
    @NotBlank(message="不能为空")
    private String provinceId;
    
    @NotBlank(message="不能为空")
    private String cityId;
    
    private String townId;
    private String provinceName;//区域  省名称
    private String cityName;
    private String townName;
    private String requireDepId;
    private Integer status;
    
    private Integer quaStatus;//资质状态  0资质暂停1默认正常2资质终止
    
    private String quaStashReason;//资质暂停理由
    private String quaNormalReason;//资质启用理由
    private String quaTerminalReason;//资质终止理由
    
    private String contactName;//联系人姓名
    
    private String contactMobile;//联系人电话
    
    /** 品目名称 */
    private transient String cateNames;
    
    /**采购等级**/
    private transient String purchaseLevel;
    
    /** 全称 **/
    private String fullName;
    
    //是否具有审核供应商/专家资格
    private Integer isAuditSupplier;

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    

    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile ;
	}

	public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr == null ? null : detailAddr.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getPrincinpal() {
        return princinpal;
    }

    public void setPrincinpal(String princinpal) {
        this.princinpal = princinpal == null ? null : princinpal.trim();
    }

    public String getPrincinpalIdCard() {
        return princinpalIdCard;
    }

    public void setPrincinpalIdCard(String princinpalIdCard) {
        this.princinpalIdCard = princinpalIdCard == null ? null : princinpalIdCard.trim();
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature == null ? null : nature.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId == null ? null : depId.trim();
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion == null ? null : describtion.trim();
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getRequireDepId() {
		return requireDepId;
	}

	public void setRequireDepId(String requireDepId) {
		this.requireDepId = requireDepId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

    public Integer getQuaStatus() {
        return quaStatus;
    }

    public void setQuaStatus(Integer quaStatus) {
        this.quaStatus = quaStatus;
    }

    public String getQuaStashReason() {
        return quaStashReason;
    }

    public void setQuaStashReason(String quaStashReason) {
        this.quaStashReason = quaStashReason;
    }

    public String getQuaNormalReason() {
        return quaNormalReason;
    }

    public void setQuaNormalReason(String quaNormalReason) {
        this.quaNormalReason = quaNormalReason;
    }

    public String getQuaTerminalReason() {
        return quaTerminalReason;
    }

    public void setQuaTerminalReason(String quaTerminalReason) {
        this.quaTerminalReason = quaTerminalReason;
    }

    public String getCateNames() {
        return cateNames;
    }

    public void setCateNames(String cateNames) {
        this.cateNames = cateNames;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getPurchaseLevel() {
        return purchaseLevel;
    }

    public void setPurchaseLevel(String purchaseLevel) {
        this.purchaseLevel = purchaseLevel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

	public Integer getIsAuditSupplier() {
		return isAuditSupplier;
	}

	public void setIsAuditSupplier(Integer isAuditSupplier) {
		this.isAuditSupplier = isAuditSupplier;
	}
    
    
    
}