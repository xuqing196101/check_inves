package common.model;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 系统日志
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class SystemLog {
    
    /** 主键 **/
    private String id;
    
    /** 操作类型 1:供应商,2:招标采购,3:专家,4:论坛 ，5网上竞价**/
    private int operateType;
    
    /** 操作时间 **/
    private Date operateTime;
    
    /** 操作人Id **/
    private String operatePersonId;
    
    /** 操作人名称 **/
    private String operatePersonName;
    
    /** 操作人IP **/
    private String operateIp;
    
    /** 日志类型,1:操作日志,2:异常日志 **/
    private Integer logType;
    
    /** 操作开始时间 **/
    private Date operateStartTime;
    
    /** 操作结束时间 **/
    private Date operateEndTime;
    
    /** 描述 **/
    private String descriptions;
    
    /** 异常详情 **/
    private String exceptionDetail;
    
    /** 调用的方法 **/
    private String method;
    
    /** 响应时间 **/
    private String responseTime;
    
    /** 参数 **/
    private String params;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperatePersonId() {
        return operatePersonId;
    }

    public void setOperatePersonId(String operatePersonId) {
        this.operatePersonId = operatePersonId;
    }

    public String getOperatePersonName() {
        return operatePersonName;
    }

    public void setOperatePersonName(String operatePersonName) {
        this.operatePersonName = operatePersonName;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public Date getOperateStartTime() {
        return operateStartTime;
    }

    public void setOperateStartTime(Date operateStartTime) {
        this.operateStartTime = operateStartTime;
    }

    public Date getOperateEndTime() {
        return operateEndTime;
    }

    public void setOperateEndTime(Date operateEndTime) {
        this.operateEndTime = operateEndTime;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
    
    
    
    
    
}
