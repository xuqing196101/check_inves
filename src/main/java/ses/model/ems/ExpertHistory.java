package ses.model.ems;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 版权：(C) 版权所有 2011-2016
 * <简述>
 * 用来保存专家历史信息
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
 * @see
 */
@SuppressWarnings("serial")
public class ExpertHistory implements Serializable{
	/**主键*/
    private String id;
    /**是否为临时专家  0不是 1是*/
    private Short isProvisional;
    /**性别 M 男  F 女*/
    private String gender;
    /**联系电话*/
    private String mobile;
    /**出生日期*/
    private Date birthday;
    /**缴纳社会保险证明*/
    private String coverNote;
    /**居民身份证号码*/
    private String idCardNumber;
    /**军队人员身份证件类型*/
    private String idType;
    /**证件号码*/
    private String idNumber;
    /**专家来源*/
    private String expertsFrom;//军队只能在内网注册,地方在内网和外网都可以注册
    /**政治面貌*/
    private String politicsStatus;
    /**民族*/
    private String nation;
    /**毕业院校*/
    private String graduateSchool;//毕业院校及专业
    /**最高学历*/
    private String hightEducation;
    /**专业*/
    private String major;//从事专业
    /**工作单位名称*/
    private String workUnit;
    /**固话*/
    private String  telephone;
    /**邮箱*/
    private String email;//邮箱
    /**参评的产品类别*/
    private String productCategories;
    /**专业学术成果*/
    private String academicAchievement;
    /**参加军队地方采购评审情况*/
    private String reviewSituation;
    /**需要申请回避的情况*/
    private String avoidanceSituation;

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
    /**主要工作经历*/
    private String jobExperiences;
    /**单位地址*/
    private String unitAddress;
    /**邮编*/
    private String postCode;
    /**学位*/
    private String degree;
    /**现任职务*/
    private String atDuty;
    /**专家类型*/
    private String expertsTypeId;
    /**采购机构id*/
    private String purchaseDepId;
    /**真实姓名*/
    private String relName;
    /*是否推荐信*/
    private Integer isReferenceLftter;
    /**执业时间*/
    @DateTimeFormat(pattern="yyyy-MM")
    private Date timeProfessional;
    /**执业资格*/
    private String professional;
    /*省+地区*/
    private String range;
    
    private String expertId;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public Date getTimeProfessional() {

        return timeProfessional;
    }

    public void setTimeProfessional(Date timeProfessional) {
        this.timeProfessional = timeProfessional;
    }

    public Integer getIsReferenceLftter() {
        return isReferenceLftter;
    }

    public void setIsReferenceLftter(Integer isReferenceLftter) {
        this.isReferenceLftter = isReferenceLftter;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Short getIsProvisional() {
        return isProvisional;
    }
    public void setIsProvisional(Short isProvisional) {
        this.isProvisional = isProvisional;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getCoverNote() {
        return coverNote;
    }
    public void setCoverNote(String coverNote) {
        this.coverNote = coverNote;
    }
    public String getIdCardNumber() {
        return idCardNumber;
    }
    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
    public String getIdType() {
        return idType;
    }
    public void setIdType(String idType) {
        this.idType = idType;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public String getExpertsFrom() {
        return expertsFrom;
    }
    public void setExpertsFrom(String expertsFrom) {
        this.expertsFrom = expertsFrom;
    }
    public String getPoliticsStatus() {
        return politicsStatus;
    }
    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }
    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getGraduateSchool() {
        return graduateSchool;
    }
    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }
    public String getHightEducation() {
        return hightEducation;
    }
    public void setHightEducation(String hightEducation) {
        this.hightEducation = hightEducation;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getWorkUnit() {
        return workUnit;
    }
    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getProductCategories() {
        return productCategories;
    }
    public void setProductCategories(String productCategories) {
        this.productCategories = productCategories;
    }
    public String getAcademicAchievement() {
        return academicAchievement;
    }
    public void setAcademicAchievement(String academicAchievement) {
        this.academicAchievement = academicAchievement;
    }
    public String getReviewSituation() {
        return reviewSituation;
    }
    public void setReviewSituation(String reviewSituation) {
        this.reviewSituation = reviewSituation;
    }
    public String getAvoidanceSituation() {
        return avoidanceSituation;
    }
    public void setAvoidanceSituation(String avoidanceSituation) {
        this.avoidanceSituation = avoidanceSituation;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
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
        this.healthState = healthState;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getProfessTechTitles() {
        return professTechTitles;
    }
    public void setProfessTechTitles(String professTechTitles) {
        this.professTechTitles = professTechTitles;
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
    public String getJobExperiences() {
        return jobExperiences;
    }
    public void setJobExperiences(String jobExperiences) {
        this.jobExperiences = jobExperiences;
    }
    public String getUnitAddress() {
        return unitAddress;
    }
    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public String getAtDuty() {
        return atDuty;
    }
    public void setAtDuty(String atDuty) {
        this.atDuty = atDuty;
    }
    public String getExpertsTypeId() {
        return expertsTypeId;
    }
    public void setExpertsTypeId(String expertsTypeId) {
        this.expertsTypeId = expertsTypeId;
    }
    public String getPurchaseDepId() {
        return purchaseDepId;
    }
    public void setPurchaseDepId(String purchaseDepId) {
        this.purchaseDepId = purchaseDepId;
    }
    public String getRelName() {
        return relName;
    }
    public void setRelName(String relName) {
        this.relName = relName;
    }

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
    
}