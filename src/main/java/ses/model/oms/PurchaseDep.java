package ses.model.oms;

import java.math.BigDecimal;
import java.util.Date;

public class PurchaseDep extends Orgnization{
    private String id;

    private String level;

    private String subordinateOrgId;

    private String subordinateOrgName;

    private String businessDep;

    private String businessDepId;

    private String businessRange;

    private String quaCode;

    private String quaLevel;

    private Date quaEdndate;

    private Integer quaRange;

    private String quaCert;

    private String leaderTelephone;

    private Integer officerCountnum;

    private Integer officerNowCounts;

    private Integer soldierNum;

    private Integer soldierNowCounts;

    private Integer staffNum;

    private Integer staffNowCounts;

    private Integer purchasersCount;

    private Integer juniorPurCount;

    private Integer seniorPurCount;

    private String depName;

    private String legal;

    private String agent;

    private String contact;

    private String contactTelephone;

    private String contactAddress;

    private Integer unitPostCode;

    private String payDep;

    private String bank;

    private BigDecimal bankAccount;

    private Integer officeArea;

    private Integer officeCount;

    private Integer mettingRoomCount;

    private Integer inviteRoomCount;

    private Integer bidRoomCount;

    private Integer isDeleted;

    private Date updatedAt;

    private Date createdAt;
    
    private String orgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
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

    public Integer getOfficeArea() {
        return officeArea;
    }

    public void setOfficeArea(Integer officeArea) {
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
    
}