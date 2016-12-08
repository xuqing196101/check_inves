package ses.model.ems;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 
  * <p>Title:Expert </p>
  * <p>Description: </p>评审专家实体
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年9月8日上午9:26:23
 */
public class Expert implements Serializable{
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	/**主键*/
    private String id;
    /**是否提交*/
    private String isSubmit;
    /**是否考试，1已考，2过期  其他值或null是未考*/
    private String isDo;
    /**是否考试通过 0未通过  1已通过*/
    private Short isPass;
    /**是否为临时专家  0不是 1是*/
    private Short isProvisional;
    /**备注*/
    private String remarks;
    /**是否删除；0未删除，1已删除*/
    private Short isDelete = 0;
    /**创建时间*/
    private Date createdAt = new Date();
    /**性别 M 男  F 女*/
    private String gender;
    /**修改时间*/
    private Date updatedAt;
    /**联系电话*/
    @NotNull(message = "不能为空") 
    private String mobile;
    /**出生日期*/
    private Date birthday;
    /**缴纳社会保险证明*/
    private String coverNote;
    /**居民身份证号码*/
    @NotNull(message = "不能为空") 
    private String idCardNumber;
    /**军队人员身份证件类型*/
    @NotNull(message = "不能为空") 
    private String idType;
    /**证件号码*/
    @NotNull(message = "不能为空") 
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
    @NotNull(message = "不能为空")
    private String email;//邮箱
    /* 12-8新加
     * <1>参评的产品类别
     * <2>专业学术成果
     * <3>参加军队地方采购评审情况
     * <4>需要申请回避的情况
    */
    /**参评的产品类别*/
    private String productCategories;
    /**专业学术成果*/
    private String academicAchievement;
    /**参加军队地方采购评审情况*/
    private String reviewSituation;
    /**需要申请回避的情况*/
    private String avoidanceSituation;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
    @DateTimeFormat(pattern="yyyy-MM")
    private Date timeToWork;
    /**从事专业起始时间*/
    private Date timeStartWork;
    /**从事专业起始时间*/
    private String jobExperiences;
    /**单位地址*/
    private String unitAddress;
    /**邮编*/
    private String postCode;
    /**学位*/
    private String degree;
    /**现任职务*/
    @NotNull(message = "不能为空") 
    private String atDuty;
    /**专家类型*/
    @NotNull(message = "不能为空") 
    private String expertsTypeId;
    /**采购机构id*/
    private String purchaseDepId;
    /**审核状态；0未审核，1通过，2未通过,3退回修改*/
    private String status;
    /**是否拉黑；0未拉黑，1已拉黑*/
    private String isBlack = "0";
    /**诚信积分；根据不诚信指标扣分*/
    private Integer honestyScore;
    /**真实姓名*/
    @NotNull(message = "不能为空") 
    private String relName;
    /**步骤数:用来判断注册走到了第几步*/
    private String stepNumber;
    
    private List<String> ids;
    
    
    
    public String getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
    }

    public Short getIsProvisional() {
		return isProvisional;
	}

	public void setIsProvisional(Short isProvisional) {
		this.isProvisional = isProvisional;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Short getIsPass() {
		return isPass;
	}

	public void setIsPass(Short isPass) {
		this.isPass = isPass;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit == null ? null : isSubmit.trim();
    }


    public String getIsDo() {
		return isDo;
	}

	public void setIsDo(String isDo) {
		this.isDo = isDo;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
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

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getJobExperiences() {
        return jobExperiences;
    }

    public void setJobExperiences(String jobExperiences) {
        this.jobExperiences = jobExperiences;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getCoverNote() {
        return coverNote;
    }

    public void setCoverNote(String coverNote) {
        this.coverNote = coverNote;
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
}