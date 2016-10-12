package ses.model.oms;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PurchaseDep extends Orgnization{
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String levelDep;//采购机构级别

    private String subordinateOrgId;

    private String subordinateOrgName;//行政隶属单位名称

    private String businessDep;//暂时废弃

    private String businessDepId;

    private String businessRange;//采购业务范围
    
    private String dutyRoomPhone;//值班室电话

    private String quaCode;//采购资质编号

    private String quaLevel;//采购资质等级 一级----九级
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date quaStartDate;//采购资质开始日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date quaEdndate;//采购资质截止日期

    private Integer quaRange;//采购资质范围,1：综合2：物资3：工程 4：服务 
    
    private Integer quaStatus;//资质状态  0资质暂停1默认正常2资质终止

    private String quaCert;

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

    private String depName;//单位名称

    private String legal;//法定代表人

    private String agent;//委托代理人

    private String contact;//联系人

    private String contactTelephone;//联系电话

    private String contactAddress;//通讯地址

    private Integer unitPostCode;//邮政编码

    private String payDep;//付款单位

    private String bank;//开户银行

    private BigDecimal bankAccount;//银行账号
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
    
    
    private Integer isAuditSupplier;//是否具有进口供应商审核权限，0没有1有

    public PurchaseDep(String id) {
    	super();
		this.id = id;
	}
    public PurchaseDep() {
    	super();
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

	public Integer getQuaStatus() {
		return quaStatus;
	}

	public void setQuaStatus(Integer quaStatus) {
		this.quaStatus = quaStatus;
	}

	public Orgnization getOrgnization() {
		return orgnization;
	}

	public void setOrgnization(Orgnization orgnization) {
		this.orgnization = orgnization;
	}
    
}