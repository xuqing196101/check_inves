package bss.model.pqims;


import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import bss.model.cs.PurchaseContract;

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
     * 项目类型
     */
    private String projectType;

    /*
     * 质检单位
     */
    @NotBlank(message = "质检单位不能为空")
    private String unit;

    /*
     * 质检类型、验收类型
     */
    @NotBlank(message = "质检类型不能为空")
    private String type;

    /*
     * 质检地点
     */
    @NotBlank(message = "质检地点不能为空")
    private String place;

    /*
     * 质检日期
     */
    private Date pqdate;

    /*
     * 质检人员
     */
    @NotBlank(message = "质检人员不能为空")
    private String inspectors;

    /*
     * 质检情况
     */
    @NotBlank(message = "质检情况不能为空")
    private String condition;

    /*
     * 质检结果
     */
    @NotBlank(message = "质检单位不能为空")
    private String conclusion;

    /*
     * 详细情况
     */
    @NotBlank(message = "详细情况不能为空")
    private String detail;

    /*
     * 质检报告
     */
    private String report;

    /*
     * 创建时间
     */
    private Timestamp createdAt;

    /*
     * 修改时间
     */
    private Timestamp updatedAt;
    
    /*
     * 合同实体类
     */
    private PurchaseContract contract;
    
    public PurchaseContract getContract() {
		return contract;
	}

	public void setContract(PurchaseContract contract) {
		this.contract = contract;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
    	
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Date getPqdate() {
        return pqdate;
    }

    public void setPqdate(Date pqdate) {
        this.pqdate = pqdate;
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
    	
        return conclusion;
    }

    public void setConclusion(String conclusion) {
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}