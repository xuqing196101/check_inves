package yggc.model.ems;

import java.util.Date;



/**
 * 
  * <p>Title:Expert </p>
  * <p>Description: </p>评审专家实体
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年9月6日下午3:25:51
 */
public class Expert {
	/**主键*/
    private String id;
    /**登录名*/
    private String loginName;
    /**密码*/
    private String password;
    /**是否删除；0未删除，1已删除*/
    private Short isDelete;
    /**创建时间*/
    private Date createdAt = new Date();
    /**性别*/
    private String sex;
    /**修改时间*/
    private Date updatedAt;
    /**联系电话*/
    private String mobile;
    /**出生日期*/
    private Date birthday;
    /**证件号码*/
    private String idNumber;
    /**证件类型*/
    private String idType;
    /**专家来源*/
    private String expertsFrom;
    /**政治面貌*/
    private String politicsStatus;
    /**民族*/
    private String nayion;
    /**毕业院校*/
    private String graduateSchool;
    /**最高学历*/
    private String hightEducation;
    /**专业*/
    private String major;
    /**工作单位名称*/
    private String workUnit;
    /**固话*/
    private String fixPhone;
    /**传真*/
    private String fax;
    /**取得技术时间*/
    private Date makeTechDate;
    /**健康状态*/
    private String healthState;
    /**所在地区*/
    private String address;
    /**专家技术职称*/
    private String professTechTitles;
    /**参加工作时间*/
    private Date timeToWork;
    /**从事专业起始时间*/
    private Date timeStartWork;
    /**单位地址*/
    private String unitAddress;
    /**邮编*/
    private String zipCode;
    /**学位*/
    private String degree;
    /**现任职务*/
    private String atDuty;
    /**专家类型*/
    private String expertsTypeId;
    /**采购机构id*/
    private String purchaseDepId;
    /**审核状态；0未审核，1通过，2未通过*/
    private String status="0";
    /**是否拉黑；0未拉黑，1已拉黑*/
    private String isBlack = "0";
    /**诚信积分；默认100，根据不诚信指标扣分*/
    private Integer honestyScore = 100;
    /**真是姓名*/
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

    public String getPurchaseDepId() {
        return purchaseDepId;
    }

    public void setPurchaseDepId(String purchaseDepId) {
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