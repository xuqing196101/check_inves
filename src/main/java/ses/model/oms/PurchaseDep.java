package ses.model.oms;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
* <p>Title:PurchaseDep </p>
* <p>Description: </p>
* <p>Company: ses </p> 
* @author tkf
* @date 2016-9-6上午9:49:54
 */
public class PurchaseDep {
    private String id;

    private String name;

    private String level;

    private String unitId;

    private String unitName;

    private String propBusyUnit;

    private String propBusyId;

    private String propBusyScope;

    private String addr;

    private Integer postCode;

    private String fax;

    private String quaCode;

    private String quaLevel;

    private Date quaEdndate;

    private Integer quaRange;

    private String quaCert;

    private String leaderTel;

    private Integer officerNum;

    private Integer officerNows;

    private Integer soldierNum;

    private Integer soldierNows;

    private Integer staffNum;

    private Integer staffNows;

    private Integer purchasers;

    private Integer juniorPur;

    private Integer seniorPur;

    private String unit;

    private String legalPerson;

    private String entrustPerson;

    private String contractPerson;

    private String contractTel;

    private String comunicationAddr;

    private Integer unitPostCode;

    private String payUnit;

    private String openingBank;

    private BigDecimal bankAccount;

    private Integer officeArea;

    private Integer officeNum;

    private Integer meetNum;

    private Integer bidOffice;

    private Integer bidRoom;

    private String bidRoomCode;

    private String bidRoomPosition;

    private Integer bidRoomArea;

    private Integer bidRoomCapacity;

    private String bidOfficeCode;

    private String bidOfficePosition;

    private Integer bidOfficeArea;

    private Integer bidOfficeCapacity;

    private Integer isInInternet;

    private Integer isHaveMonitoringSystem;

    private String accessWay;
    private Integer isDeleted;
    private Date createdAt;

    private Date updatedAt;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getPropBusyUnit() {
        return propBusyUnit;
    }

    public void setPropBusyUnit(String propBusyUnit) {
        this.propBusyUnit = propBusyUnit == null ? null : propBusyUnit.trim();
    }

    public String getPropBusyId() {
        return propBusyId;
    }

    public void setPropBusyId(String propBusyId) {
        this.propBusyId = propBusyId == null ? null : propBusyId.trim();
    }

    public String getPropBusyScope() {
        return propBusyScope;
    }

    public void setPropBusyScope(String propBusyScope) {
        this.propBusyScope = propBusyScope == null ? null : propBusyScope.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
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

    public String getLeaderTel() {
        return leaderTel;
    }

    public void setLeaderTel(String leaderTel) {
        this.leaderTel = leaderTel == null ? null : leaderTel.trim();
    }

    public Integer getOfficerNum() {
        return officerNum;
    }

    public void setOfficerNum(Integer officerNum) {
        this.officerNum = officerNum;
    }

    public Integer getOfficerNows() {
        return officerNows;
    }

    public void setOfficerNows(Integer officerNows) {
        this.officerNows = officerNows;
    }

    public Integer getSoldierNum() {
        return soldierNum;
    }

    public void setSoldierNum(Integer soldierNum) {
        this.soldierNum = soldierNum;
    }

    public Integer getSoldierNows() {
        return soldierNows;
    }

    public void setSoldierNows(Integer soldierNows) {
        this.soldierNows = soldierNows;
    }

    public Integer getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(Integer staffNum) {
        this.staffNum = staffNum;
    }

    public Integer getStaffNows() {
        return staffNows;
    }

    public void setStaffNows(Integer staffNows) {
        this.staffNows = staffNows;
    }

    public Integer getPurchasers() {
        return purchasers;
    }

    public void setPurchasers(Integer purchasers) {
        this.purchasers = purchasers;
    }

    public Integer getJuniorPur() {
        return juniorPur;
    }

    public void setJuniorPur(Integer juniorPur) {
        this.juniorPur = juniorPur;
    }

    public Integer getSeniorPur() {
        return seniorPur;
    }

    public void setSeniorPur(Integer seniorPur) {
        this.seniorPur = seniorPur;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getEntrustPerson() {
        return entrustPerson;
    }

    public void setEntrustPerson(String entrustPerson) {
        this.entrustPerson = entrustPerson == null ? null : entrustPerson.trim();
    }

    public String getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson == null ? null : contractPerson.trim();
    }

    public String getContractTel() {
        return contractTel;
    }

    public void setContractTel(String contractTel) {
        this.contractTel = contractTel == null ? null : contractTel.trim();
    }

    public String getComunicationAddr() {
        return comunicationAddr;
    }

    public void setComunicationAddr(String comunicationAddr) {
        this.comunicationAddr = comunicationAddr == null ? null : comunicationAddr.trim();
    }

    public Integer getUnitPostCode() {
        return unitPostCode;
    }

    public void setUnitPostCode(Integer unitPostCode) {
        this.unitPostCode = unitPostCode;
    }

    public String getPayUnit() {
        return payUnit;
    }

    public void setPayUnit(String payUnit) {
        this.payUnit = payUnit == null ? null : payUnit.trim();
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank == null ? null : openingBank.trim();
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

    public Integer getOfficeNum() {
        return officeNum;
    }

    public void setOfficeNum(Integer officeNum) {
        this.officeNum = officeNum;
    }

    public Integer getMeetNum() {
        return meetNum;
    }

    public void setMeetNum(Integer meetNum) {
        this.meetNum = meetNum;
    }

    public Integer getBidOffice() {
        return bidOffice;
    }

    public void setBidOffice(Integer bidOffice) {
        this.bidOffice = bidOffice;
    }

    public Integer getBidRoom() {
        return bidRoom;
    }

    public void setBidRoom(Integer bidRoom) {
        this.bidRoom = bidRoom;
    }

    public String getBidRoomCode() {
        return bidRoomCode;
    }

    public void setBidRoomCode(String bidRoomCode) {
        this.bidRoomCode = bidRoomCode == null ? null : bidRoomCode.trim();
    }

    public String getBidRoomPosition() {
        return bidRoomPosition;
    }

    public void setBidRoomPosition(String bidRoomPosition) {
        this.bidRoomPosition = bidRoomPosition == null ? null : bidRoomPosition.trim();
    }

    public Integer getBidRoomArea() {
        return bidRoomArea;
    }

    public void setBidRoomArea(Integer bidRoomArea) {
        this.bidRoomArea = bidRoomArea;
    }

    public Integer getBidRoomCapacity() {
        return bidRoomCapacity;
    }

    public void setBidRoomCapacity(Integer bidRoomCapacity) {
        this.bidRoomCapacity = bidRoomCapacity;
    }

    public String getBidOfficeCode() {
        return bidOfficeCode;
    }

    public void setBidOfficeCode(String bidOfficeCode) {
        this.bidOfficeCode = bidOfficeCode == null ? null : bidOfficeCode.trim();
    }

    public String getBidOfficePosition() {
        return bidOfficePosition;
    }

    public void setBidOfficePosition(String bidOfficePosition) {
        this.bidOfficePosition = bidOfficePosition == null ? null : bidOfficePosition.trim();
    }

    public Integer getBidOfficeArea() {
        return bidOfficeArea;
    }

    public void setBidOfficeArea(Integer bidOfficeArea) {
        this.bidOfficeArea = bidOfficeArea;
    }

    public Integer getBidOfficeCapacity() {
        return bidOfficeCapacity;
    }

    public void setBidOfficeCapacity(Integer bidOfficeCapacity) {
        this.bidOfficeCapacity = bidOfficeCapacity;
    }


    public Integer getIsInInternet() {
		return isInInternet;
	}

	public void setIsInInternet(Integer isInInternet) {
		this.isInInternet = isInInternet;
	}

	public Integer getIsHaveMonitoringSystem() {
		return isHaveMonitoringSystem;
	}

	public void setIsHaveMonitoringSystem(Integer isHaveMonitoringSystem) {
		this.isHaveMonitoringSystem = isHaveMonitoringSystem;
	}

	public String getAccessWay() {
        return accessWay;
    }

    public void setAccessWay(String accessWay) {
        this.accessWay = accessWay == null ? null : accessWay.trim();
    }

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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
    
}