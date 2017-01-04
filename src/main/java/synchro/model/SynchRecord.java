package synchro.model;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 同步日志记录表
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class SynchRecord {
    
    /** 主键 **/
    private String id;
    
    /** 操作类型  1:导出,2:导入**/
    private int operType;
    
    /** 同步日期 **/
    private Date synchDate;
    
    /** 数据类型:看数据字典**/
    private String dataType;
    
    /** 描述 **/
    private String descriptions;
    
    /** 同步时间  **/
    private String synchTime;
    
    /** 类型名称 **/
    private String dataTypeName;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getOperType() {
        return operType;
    }
    public void setOperType(int operType) {
        this.operType = operType;
    }
    public Date getSynchDate() {
        return synchDate;
    }
    public void setSynchDate(Date synchDate) {
        this.synchDate = synchDate;
    }
    public String getDescriptions() {
        return descriptions;
    }
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    public String getSynchTime() {
        return synchTime;
    }
    public void setSynchTime(String synchTime) {
        this.synchTime = synchTime;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getDataTypeName() {
        return dataTypeName;
    }
    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }
    
}
