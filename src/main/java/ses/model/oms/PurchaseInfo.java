package ses.model.oms;

import java.util.Date;

import ses.model.bms.User;

public class PurchaseInfo extends User{
    private String id;

   // private Integer gender;//父类继承

    private String nation;

    private String purchaseDepId;

    private String political;

    private Integer purcahserType;

    private Date birthAt;

    private String area;

    private Integer provinceId;

    private Integer cityId;

    private Integer townId;

    private String detailAddr;

    private String idCard;

    private String professional;

    private String topStudy;

    private String graduateSchool;

    private String quaCode;

    private String quaLevel;

    private Date quaEdndate;

    private String quaCert;

    private String quaRank;

    private String fax;

    private String address;

    private String postCode;
    private String workExperience;

    private String trainExperience;
    
    private Date quaStartDate;
    private Integer isDeleted; 
    private String userId;//用户id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    

    public String getPurchaseDepId() {
        return purchaseDepId;
    }

    public void setPurchaseDepId(String purchaseDepId) {
        this.purchaseDepId = purchaseDepId == null ? null : purchaseDepId.trim();
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political == null ? null : political.trim();
    }

    public Integer getPurcahserType() {
        return purcahserType;
    }

    public void setPurcahserType(Integer purcahserType) {
        this.purcahserType = purcahserType;
    }

    public Date getBirthAt() {
        return birthAt;
    }

    public void setBirthAt(Date birthAt) {
        this.birthAt = birthAt;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr == null ? null : detailAddr.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional == null ? null : professional.trim();
    }

    public String getTopStudy() {
        return topStudy;
    }

    public void setTopStudy(String topStudy) {
        this.topStudy = topStudy == null ? null : topStudy.trim();
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool == null ? null : graduateSchool.trim();
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

    public String getQuaCert() {
        return quaCert;
    }

    public void setQuaCert(String quaCert) {
        this.quaCert = quaCert == null ? null : quaCert.trim();
    }

    public String getQuaRank() {
        return quaRank;
    }

    public void setQuaRank(String quaRank) {
        this.quaRank = quaRank == null ? null : quaRank.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }
    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience == null ? null : workExperience.trim();
    }

    public String getTrainExperience() {
        return trainExperience;
    }

    public void setTrainExperience(String trainExperience) {
        this.trainExperience = trainExperience == null ? null : trainExperience.trim();
    }

	

	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getQuaStartDate() {
		return quaStartDate;
	}

	public void setQuaStartDate(Date quaStartDate) {
		this.quaStartDate = quaStartDate;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}