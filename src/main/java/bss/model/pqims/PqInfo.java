package bss.model.pqims;

import java.util.Date;

import bss.model.cs.Contract;

/**
 * 
 * @Title:PqInfo 
 * @Description:质检信息实体类 
 * @author Liyi
 * @date 2016-9-20下午2:41:11
 *
 */
public class PqInfo {
	/*
	 * 主键
	 */
    private String id;

    /*
     * 合同id
     */
    private String contractId;

    /*
     * 项目类型
     */
    private String projectType;

    /*
     * 质检单位
     */
    private String unit;

    /*
     * 质检类型、验收类型
     */
    private int type;

    /*
     * 质检地点
     */
    private String place;

    /*
     * 质检日期
     */
    private Date date;

    /*
     * 质检人员
     */
    private String inspectors;

    /*
     * 质检情况
     */
    private String condition;

    /*
     * 质检结果
     */
    private int conclusion;

    /*
     * 详细情况
     */
    private String detail;

    /*
     * 质检报告
     */
    private String report;

    /*
     * 创建时间
     */
    private Date createdAt;

    /*
     * 修改时间
     */
    private Date updatedAt;
    
    /*
     * 合同实体类
     */
    private Contract contract;
    
    public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getType() {
    	String typeString = null;
    	if (type==1) {
			typeString="首件检验";
		}else if (type==2) {
			typeString="批量验收";
		}else if (type==3) {
			typeString="出厂验收";
		}else if (type==4) {
			typeString="到货验收";
		}
        return typeString;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInspectors() {
        return inspectors;
    }

    public void setInspectors(String inspectors) {
        this.inspectors = inspectors == null ? null : inspectors.trim();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition == null ? null : condition.trim();
    }

    public String getConclusion() {
    	String conclusionsString = null;
    	if (conclusion==1) {
			conclusionsString="合格";
		}else if (conclusion==0) {
			conclusionsString="不合格";
		}
        return conclusionsString;
    }

    public void setConclusion(int conclusion) {
        this.conclusion = conclusion;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
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