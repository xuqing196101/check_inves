package ses.model.oms;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PurchaseDep extends Orgnization{
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	//private String id;
	@NotBlank(message="级别不能为空")
    private String levelDep;//采购机构级别

    private String subordinateOrgId;
    
    @NotBlank(message="单位名称不能为空")
    @Length(min=1,max=50,message="长度不可超过50")
    private String subordinateOrgName;//行政隶属单位名称

    private String businessDep;//暂时废弃

    private String businessDepId;

    @NotBlank(message="不能为空")
    @Length(min=0,max=800,message="长度不可超过800")
    private String businessRange;//采购业务范围
    
    @NotBlank(message="电话不能为空")
    @Length(min=0,max=50,message="长度不可超过50")
    private String dutyRoomPhone;//值班室电话

    @Length(min=0,max=100,message="长度不可超过50")
    private String quaCode;//采购资质编号

    private String quaLevel;//采购资质等级 一级----九级
    
    private Date quaStartDate;//采购资质开始日期
    
    private Date quaEdndate;//采购资质截止日期

    private Integer quaRange;//采购资质范围,1：综合2：物资3：工程 4：服务 
    
    @Length(min=0,max=100,message="长度不可超过100")
    private String supplierContact;//供应商注册联系人
    
    private String supplierPhone;//供应商注册联系人电话
    
    @Length(min=0,max=300,message="长度不可超过300")
    private String supplierAddress;//供应商注册地址
    
    private String supplierPostcode;//供应商注册邮编
    
    private String experContact;
    
    private String experPhone;
    
    private String experAddress;
    
    private String experPostcode;

    private String quaCert;
    
    @Length(min=0,max=30,message="长度不可超过30")
    private String leaderTelephone;//单位主要领导姓名及电话

    private Integer officerCountnum;//军官编制人数

    private Integer officerNowCounts;//军官现有人数

    private Integer soldierNum;//士兵编制人数

    private Integer soldierNowCounts;//士兵现有人数

    private Integer staffNum;//职工编制人数

    private Integer staffNowCounts;//职工现有人数

    private Integer purchasersCount;//具备采购资格人员数量

    private Integer juniorPurCount;//初级采购师人数

    private Integer seniorPurCount;//高级采购师人数

    @Length(min=0,max=20,message="长度不可超过20")
    private String depName;//单位名称

    @Length(min=0,max=10,message="长度不可超过50")
    private String legal;//法定代表人

    @Length(min=0,max=20,message="长度不可超过20")
    private String agent;//委托代理人

    @Length(min=0,max=20,message="长度不可超过20")
    private String contact;//联系人

    @Length(min=0,max=20,message="长度不可超过20")
    private String contactTelephone;//联系电话

    @Length(min=0,max=100,message="长度不可超过100")
    private String contactAddress;//通讯地址

    private Integer unitPostCode;//邮政编码

    @Length(min=0,max=50,message="长度不可超过50")
    private String payDep;//付款单位

    @Length(min=0,max=50,message="长度不可超过50")
    private String bank;//开户银行

    private String accountName;//开户名称
    
    private BigDecimal bankAccount;//银行账号
    
    @Length(min=0,max=50,message="长度不可超过50")
    private String officeArea;//办公场地总面积

    private Integer officeCount;//办公司数量

    private Integer mettingRoomCount;//会议室数量

    private Integer inviteRoomCount;//招标室数量

    private Integer bidRoomCount;//评标室数量

    private Date updatedAt;

    private Date createdAt;
    
    private String orgId;
    
    private Orgnization orgnization;
    
    private String areaName;//采购机构归属地  组合  省市
    
    private Integer isDeleted;
    
    private Integer isAuditSupplier;//是否具有进口供应商审核权限，0没有1有

    private String accessNetwork;
    
    private String accessWay;
    
    private String videoSurveillance;
    
    private int pendingAuditCount;// 待审核数量
    
    private String flag;
    
    private String shortName;
    
    public String getShortName() {
			return shortName;
		}
		public void setShortName(String shortName) {
			this.shortName = shortName;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public PurchaseDep(String id) {
    	super();
    	super.setId(id);
	}
    public PurchaseDep() {
    	super();
	}


   

    public String getLevelDep() {
		return levelDep;
	}

	public void setLevelDep(String levelDep) {
		this.levelDep = levelDep;
	}

	public String getSubordinateOrgId() {
        return subordinateOrgId;
    }

    public void setSubordinateOrgId(String subordinateOrgId) {
        this.subordinateOrgId = subordinateOrgId == null ? null : subordinateOrgId.trim();
    }

    public String getSubordinateOrgName() {
        return subordinateOrgName;
    }

    public void setSubordinateOrgName(String subordinateOrgName) {
        this.subordinateOrgName = subordinateOrgName == null ? null : subordinateOrgName.trim();
    }

    public String getBusinessDep() {
        return businessDep;
    }

    public void setBusinessDep(String businessDep) {
        this.businessDep = businessDep == null ? null : businessDep.trim();
    }

    public String getBusinessDepId() {
        return businessDepId;
    }
    
    public Integer getIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public void setBusinessDepId(String businessDepId) {
        this.businessDepId = businessDepId == null ? null : businessDepId.trim();
    }

    public String getBusinessRange() {
        return businessRange;
    }

    public void setBusinessRange(String businessRange) {
        this.businessRange = businessRange == null ? null : businessRange.trim();
    }

    public String getQuaCode() {
        return quaCode;
    }

    public void setQuaCode(String quaCode) {
        this.quaCode = quaCode == null ? null : quaCode.trim();
    }

    public String getQuaLevel() {
        return quaLevel;
    }

    public void setQuaLevel(String quaLevel) {
        this.quaLevel = quaLevel == null ? null : quaLevel.trim();
    }
    
    public Date getQuaEdndate() {
        return quaEdndate;
    }

    public void setQuaEdndate(Date quaEdndate) {
        this.quaEdndate = quaEdndate;
    }

    public Integer getQuaRange() {
        return quaRange;
    }

    public void setQuaRange(Integer quaRange) {
        this.quaRange = quaRange;
    }

    public String getQuaCert() {
        return quaCert;
    }

    public void setQuaCert(String quaCert) {
        this.quaCert = quaCert == null ? null : quaCert.trim();
    }

    public String getLeaderTelephone() {
        return leaderTelephone;
    }

    public void setLeaderTelephone(String leaderTelephone) {
        this.leaderTelephone = leaderTelephone == null ? null : leaderTelephone.trim();
    }

    public Integer getOfficerCountnum() {
        return officerCountnum;
    }

    public void setOfficerCountnum(Integer officerCountnum) {
        this.officerCountnum = officerCountnum;
    }

    public Integer getOfficerNowCounts() {
        return officerNowCounts;
    }

    public void setOfficerNowCounts(Integer officerNowCounts) {
        this.officerNowCounts = officerNowCounts;
    }

    public Integer getSoldierNum() {
        return soldierNum;
    }

    public void setSoldierNum(Integer soldierNum) {
        this.soldierNum = soldierNum;
    }

    public Integer getSoldierNowCounts() {
        return soldierNowCounts;
    }

    public void setSoldierNowCounts(Integer soldierNowCounts) {
        this.soldierNowCounts = soldierNowCounts;
    }

    public Integer getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(Integer staffNum) {
        this.staffNum = staffNum;
    }

    public Integer getStaffNowCounts() {
        return staffNowCounts;
    }

    public void setStaffNowCounts(Integer staffNowCounts) {
        this.staffNowCounts = staffNowCounts;
    }

    public Integer getPurchasersCount() {
        return purchasersCount;
    }

    public void setPurchasersCount(Integer purchasersCount) {
        this.purchasersCount = purchasersCount;
    }

    public Integer getJuniorPurCount() {
        return juniorPurCount;
    }

    public void setJuniorPurCount(Integer juniorPurCount) {
        this.juniorPurCount = juniorPurCount;
    }

    public Integer getSeniorPurCount() {
        return seniorPurCount;
    }

    public void setSeniorPurCount(Integer seniorPurCount) {
        this.seniorPurCount = seniorPurCount;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal == null ? null : legal.trim();
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone == null ? null : contactTelephone.trim();
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    public Integer getUnitPostCode() {
        return unitPostCode;
    }

    public void setUnitPostCode(Integer unitPostCode) {
        this.unitPostCode = unitPostCode;
    }

    public String getPayDep() {
        return payDep;
    }

    public void setPayDep(String payDep) {
        this.payDep = payDep == null ? null : payDep.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public BigDecimal getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BigDecimal bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getOfficeArea() {
        return officeArea;
    }

    public void setOfficeArea(String officeArea) {
        this.officeArea = officeArea;
    }

    public Integer getOfficeCount() {
        return officeCount;
    }

    public void setOfficeCount(Integer officeCount) {
        this.officeCount = officeCount;
    }

    public Integer getMettingRoomCount() {
        return mettingRoomCount;
    }

    public void setMettingRoomCount(Integer mettingRoomCount) {
        this.mettingRoomCount = mettingRoomCount;
    }

    public Integer getInviteRoomCount() {
        return inviteRoomCount;
    }

    public void setInviteRoomCount(Integer inviteRoomCount) {
        this.inviteRoomCount = inviteRoomCount;
    }

    public Integer getBidRoomCount() {
        return bidRoomCount;
    }

    public void setBidRoomCount(Integer bidRoomCount) {
        this.bidRoomCount = bidRoomCount;
    }

    

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	
	public String getDutyRoomPhone() {
		return dutyRoomPhone;
	}

	public void setDutyRoomPhone(String dutyRoomPhone) {
		this.dutyRoomPhone = dutyRoomPhone;
	}

	public Integer getIsAuditSupplier() {
		return isAuditSupplier;
	}

	public void setIsAuditSupplier(Integer isAuditSupplier) {
		this.isAuditSupplier = isAuditSupplier;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getQuaStartDate() {
		return quaStartDate;
	}

	public void setQuaStartDate(Date quaStartDate) {
		this.quaStartDate = quaStartDate;
	}


	public Orgnization getOrgnization() {
		return orgnization;
	}

	public void setOrgnization(Orgnization orgnization) {
		this.orgnization = orgnization;
	}
    public String getAccessNetwork() {
        return accessNetwork;
    }
    public void setAccessNetwork(String accessNetwork) {
        this.accessNetwork = accessNetwork;
    }
    public String getAccessWay() {
        return accessWay;
    }
    public void setAccessWay(String accessWay) {
        this.accessWay = accessWay;
    }
    public String getVideoSurveillance() {
        return videoSurveillance;
    }
    public void setVideoSurveillance(String videoSurveillance) {
        this.videoSurveillance = videoSurveillance;
    }
    public String getAccountName() {
      return accountName;
    }
    public void setAccountName(String accountName) {
      this.accountName = accountName;
    }
    public String getSupplierContact() {
        return supplierContact;
    }
    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }
    public String getSupplierPhone() {
        return supplierPhone;
    }
    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }
    public String getSupplierAddress() {
        return supplierAddress;
    }
    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }
    public String getSupplierPostcode() {
        return supplierPostcode;
    }
    public void setSupplierPostcode(String supplierPostcode) {
        this.supplierPostcode = supplierPostcode;
    }
    public String getExperContact() {
        return experContact;
    }
    public void setExperContact(String experContact) {
        this.experContact = experContact;
    }
    public String getExperPhone() {
        return experPhone;
    }
    public void setExperPhone(String experPhone) {
        this.experPhone = experPhone;
    }
    public String getExperAddress() {
        return experAddress;
    }
    public void setExperAddress(String experAddress) {
        this.experAddress = experAddress;
    }
    public String getExperPostcode() {
        return experPostcode;
    }
    public void setExperPostcode(String experPostcode) {
        this.experPostcode = experPostcode;
    }
	public int getPendingAuditCount() {
		return pendingAuditCount;
	}
	public void setPendingAuditCount(int pendingAuditCount) {
		this.pendingAuditCount = pendingAuditCount;
	}
    
}