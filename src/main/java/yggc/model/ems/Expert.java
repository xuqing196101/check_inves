package yggc.model.ems;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Expert {
    private String id;

    private String loginName;

    private String password;

    private Short isDelete;

    private Date createdAt;

    private String sex;

    private Date updatedAt;

    private String mobile;

    private Date birthday;

    private String idNumber;

    private String idType;

    private String expertsFrom;

    private String politicsStatus;

    private String nayion;

    private String graduateSchool;

    private String hightEducation;

    private String major;

    private String workUnit;

    private String fixPhone;

    private String fax;

    private Date makeTechDate;

    private String healthState;

    private String address;

    private String professTechTitles;

    private Date timeToWork;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date timeStartWork;

    private String unitAddress;

    private String zipCode;

    private String degree;

    private String atDuty;

    private String expertsTypeId;

    private Long purchaseDepId;

    private String status;

    private String isBlack;

    private Integer honestyScore;

    private String relName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getExpertsFrom() {
        return expertsFrom;
    }

    public void setExpertsFrom(String expertsFrom) {
        this.expertsFrom = expertsFrom == null ? null : expertsFrom.trim();
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus == null ? null : politicsStatus.trim();
    }

    public String getNayion() {
        return nayion;
    }

    public void setNayion(String nayion) {
        this.nayion = nayion == null ? null : nayion.trim();
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool == null ? null : graduateSchool.trim();
    }

    public String getHightEducation() {
        return hightEducation;
    }

    public void setHightEducation(String hightEducation) {
        this.hightEducation = hightEducation == null ? null : hightEducation.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public String getFixPhone() {
        return fixPhone;
    }

    public void setFixPhone(String fixPhone) {
        this.fixPhone = fixPhone == null ? null : fixPhone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public Date getMakeTechDate() {
        return makeTechDate;
    }

    public void setMakeTechDate(Date makeTechDate) {
        this.makeTechDate = makeTechDate;
    }

    public String getHealthState() {
        return healthState;
    }

    public void setHealthState(String healthState) {
        this.healthState = healthState == null ? null : healthState.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getProfessTechTitles() {
        return professTechTitles;
    }

    public void setProfessTechTitles(String professTechTitles) {
        this.professTechTitles = professTechTitles == null ? null : professTechTitles.trim();
    }

    public Date getTimeToWork() {
        return timeToWork;
    }

    public void setTimeToWork(Date timeToWork) {
        this.timeToWork = timeToWork;
    }

    public Date getTimeStartWork() {
        return timeStartWork;
    }

    public void setTimeStartWork(Date timeStartWork) {
        this.timeStartWork = timeStartWork;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress == null ? null : unitAddress.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getAtDuty() {
        return atDuty;
    }

    public void setAtDuty(String atDuty) {
        this.atDuty = atDuty == null ? null : atDuty.trim();
    }

    public String getExpertsTypeId() {
        return expertsTypeId;
    }

    public void setExpertsTypeId(String expertsTypeId) {
        this.expertsTypeId = expertsTypeId == null ? null : expertsTypeId.trim();
    }

    public Long getPurchaseDepId() {
        return purchaseDepId;
    }

    public void setPurchaseDepId(Long purchaseDepId) {
        this.purchaseDepId = purchaseDepId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack == null ? null : isBlack.trim();
    }

    public Integer getHonestyScore() {
        return honestyScore;
    }

    public void setHonestyScore(Integer honestyScore) {
        this.honestyScore = honestyScore;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName == null ? null : relName.trim();
    }
}