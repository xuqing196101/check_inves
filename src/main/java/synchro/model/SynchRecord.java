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
    
    /** 数据类型:1.供应商注册,2.供应商修改 ,3.专家注册,4.专家修改**/
    private int dataType;
    
    /** 描述 **/
    private String descriptions;
    
    /** 同步时间  **/
    private String synchTime;
    
    
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
    public int getDataType() {
        return dataType;
    }
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
    public String getSynchTime() {
        return synchTime;
    }
    public void setSynchTime(String synchTime) {
        this.synchTime = synchTime;
    }
    
}
