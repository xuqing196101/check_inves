package ses.model.ems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import common.model.UploadFile;
import ses.model.bms.RoleUser;
import ses.model.bms.User;
import ses.model.bms.Userrole;
/**
 * 
  * <p>Title:Expert </p>
  * <p>Description: </p>评审专家实体
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年9月8日上午9:26:23
 */
public class Expert extends ExpertHistory implements Serializable {
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
    private Date createdAt;
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
    
    private String companyAddress;
    /** 所属采购机构 **/
    private String orgName;
    private String orgId;
    
    private List<ExpertTitle> titles=new ArrayList<ExpertTitle>();
    
    private String isAddExpert;
    
    private List<ExpertTitle> ecoList=new ArrayList<ExpertTitle>();
    
    private Integer isTitle;
    
    private Integer teachTitle;
    
    /**专家附件表**/
    private List<UploadFile> attchList=new ArrayList<UploadFile>();
    
    private  List<RoleUser> userRoles=new LinkedList<RoleUser>();
    
    private RoleUser roleUser;
    
    private Integer netType;
    
    private String fromType;
    
    /**
     * @Fields errorNum : 用户登录密码错误次数
     */
    private Integer errorNum;
    
    public Integer getNetType() {
		return netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}

	public RoleUser getRoleUser() {
		return roleUser;
	}

	public void setRoleUser(RoleUser roleUser) {
		this.roleUser = roleUser;
	}

	public List<RoleUser> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<RoleUser> userRoles) {
		this.userRoles = userRoles;
	}

	public List<UploadFile> getAttchList() {
		return attchList;
	}

	public void setAttchList(List<UploadFile> attchList) {
		this.attchList = attchList;
	}

	public Integer getTeachTitle() {
		return teachTitle;
	}

	public void setTeachTitle(Integer teachTitle) {
		this.teachTitle = teachTitle;
	}

	public Integer getIsTitle() {
		return isTitle;
	}

	public void setIsTitle(Integer isTitle) {
		this.isTitle = isTitle;
	}

	public List<ExpertTitle> getEcoList() {
		return ecoList;
	}

	public void setEcoList(List<ExpertTitle> ecoList) {
		this.ecoList = ecoList;
	}

	public String getIsAddExpert() {
		return isAddExpert;
	}

	public void setIsAddExpert(String isAddExpert) {
		this.isAddExpert = isAddExpert;
	}

	public List<ExpertTitle> getTitles() {
		return titles;
	}

	public void setTitles(List<ExpertTitle> titles) {
		this.titles = titles;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**传真*/
    private String fax;
    /**取得技术时间*/
    @DateTimeFormat(pattern="yyyy-MM")
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
    /**执业资格*/
    private String professional;
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

	/**执业时间*/
    @DateTimeFormat(pattern="yyyy-MM")
    private Date timeProfessional;
    /**从事专业起始时间*/
    @DateTimeFormat(pattern="yyyy-MM")
    private Date timeStartWork;
    /**主要工作经历*/
    private String jobExperiences;
    /**单位地址*/
    private String unitAddress;
    /**邮编*/
    private String postCode;
    //是否推荐信
    private Integer isReferenceLftter;
    /*省+地区*/
    private String range;
    
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

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
    /** -3 公示中
	  -2 预复审结束
	  -1 暂存
	   0 待初审
	   1 初审合格
	   2 初审不合格
       3 初审退回修改
	   4 待复审
	   5 复审不合格
	   6 待复查
	   7 复查合格
	   8 复查不合格
	   9 初审退回再审核
	  10 复审退回修改
	  11 待分配
	  12 处罚中
	  13 无产品专家       
	  14复审待分组专家
		# 初审中（AUDIT_TEMPORARY  1：初审中）
		# 复查中（AUDIT_TEMPORARY  3：复查中）
		# 复审中（AUDIT_TEMPORARY  2：复审中）*/
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
    
    /** 用户 **/
    private User user;
    
    /** 品目 **/
    private List<ExpertCategory> expertCategory;
    
    /** 历史记录 **/
    private ExpertHistory history;
    
    private List<String> ids;
    
    
    private Integer sign; //1：专家:初审 2:复审，3专家复查
    
    private String extractOrgid;//抽取的机构id
    
    private Integer isOrg; //是否为采购机构
    
    private String  sex;
    
    private String auditor;//审核人
    
    private Integer auditTemporary; //审核暂存状态0；未暂存，1：审核中，2：复审中，3复查中）
    
    /** 专家提交审核时间 **/
    private Date submitAt;
    
    private Date auditAt;  //审核时间
    
    //是否发布 0 未公开 1 已公开
    private Integer isPublish = 0;
    
    //用户名
    private String loginName;
    
    /**专家审核记录表*/
    private List<ExpertAudit> expertAuditList=new ArrayList<ExpertAudit>();
    /**工程执业资格历史表*/
    private List<ExpertEngHistory> expertEngHistoryList=new ArrayList<ExpertEngHistory>();
    /**工程执业资格修改表*/
    private List<ExpertEngHistory> expertEngModifyList=new ArrayList<ExpertEngHistory>();
    /**工程执业资格文件修改表*/
    private  List<ExpertAuditFileModify> expertAuditFileModifyList=new ArrayList<ExpertAuditFileModify>();
    //步骤（用于区分第一步和其他几步）
    private String step;
    
    /**审核意见附件**/
	private String auditOpinionAttach;
	
    public String getAuditOpinionAttach() {
		return auditOpinionAttach;
	}

	public void setAuditOpinionAttach(String auditOpinionAttach) {
		this.auditOpinionAttach = auditOpinionAttach;
	}
	
    public String getStep() {
			return step;
		}

		public void setStep(String step) {
			this.step = step;
		}

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

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ExpertCategory> getExpertCategory() {
        return expertCategory;
    }

    public void setExpertCategory(List<ExpertCategory> expertCategory) {
        this.expertCategory = expertCategory;
    }

    public ExpertHistory getHistory() {
        return history;
    }

    public void setHistory(ExpertHistory history) {
        this.history = history;
    }

    public Date getSubmitAt() {
        return submitAt;
    }

    public void setSubmitAt(Date submitAt) {
        this.submitAt = submitAt;
    }

	public Date getAuditAt() {
		return auditAt;
	}

	public void setAuditAt(Date auditAt) {
		this.auditAt = auditAt;
	}

    public Integer getIsReferenceLftter() {
        return isReferenceLftter;
    }

    public void setIsReferenceLftter(Integer isReferenceLftter) {
        this.isReferenceLftter = isReferenceLftter;
    }

	public Integer getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getExtractOrgid() {
		return extractOrgid;
	}

	public void setExtractOrgid(String extractOrgid) {
		this.extractOrgid = extractOrgid;
	}

    public List<ExpertAudit> getExpertAuditList() {
        return expertAuditList;
    }

    public void setExpertAuditList(List<ExpertAudit> expertAuditList) {
        this.expertAuditList = expertAuditList;
    }

    public List<ExpertEngHistory> getExpertEngHistoryList() {
        return expertEngHistoryList;
    }

    public void setExpertEngHistoryList(List<ExpertEngHistory> expertEngHistoryList) {
        this.expertEngHistoryList = expertEngHistoryList;
    }

    public List<ExpertEngHistory> getExpertEngModifyList() {
        return expertEngModifyList;
    }

    public void setExpertEngModifyList(List<ExpertEngHistory> expertEngModifyList) {
        this.expertEngModifyList = expertEngModifyList;
    }

    public List<ExpertAuditFileModify> getExpertAuditFileModifyList() {
        return expertAuditFileModifyList;
    }

    public void setExpertAuditFileModifyList(List<ExpertAuditFileModify> expertAuditFileModifyList) {
        this.expertAuditFileModifyList = expertAuditFileModifyList;
    }

	public Integer getIsOrg() {
		return isOrg;
	}

	public void setIsOrg(Integer isOrg) {
		this.isOrg = isOrg;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getAuditTemporary() {
		return auditTemporary;
	}

	public void setAuditTemporary(Integer auditTemporary) {
		this.auditTemporary = auditTemporary;
	}

  public Integer getErrorNum() {
    return errorNum;
  }

  public void setErrorNum(Integer errorNum) {
    this.errorNum = errorNum;
  }

public String getFromType() {
	return fromType;
}

public void setFromType(String fromType) {
	this.fromType = fromType;
}
    
}