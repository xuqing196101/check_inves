package ses.model.bms;

import java.util.Date;

public class CategoryTree {
    /*
     * 节点的id
     */
    private String id;

    /*
     * 节点的名称
     */
    private String name;

    private String kind;
    /*
     * 节点的父节点id
     */
    private String pId;
    
    /*
     * 专家品目Tree专用父节点Id(上面的PID有时获取不到)
     */
    private String parentId;

    /*
     * 是否是父类
     */
    private String isParent;
    
    /*
     * 是否被选中
     */
    private boolean checked;
    
    private boolean nocheck;

    private Integer isEnd;

    private Integer status;

    private String paramStatus;

    /** 类型,物资类:1,其他为空 **/
    private  String classify;

    /** 公开状态 **/
    private transient Integer pubStatus;
    
    /** 审核日期 */
    private transient Date auditDate;
    
    /** 审核意见 */
    private transient String auditAdvise;

    /** 父类的code **/
    private String code;



    public String getParamStatus() {
        return paramStatus;
    }
    public void setParamStatus(String paramStatus) {
        this.paramStatus = paramStatus;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getIsEnd() {
        return isEnd;
    }
    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getIsParent() {
        return isParent;
    }
    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getpId() {
        return pId;
    }
    public void setpId(String pId) {
        this.pId = pId;
    }
    public String getClassify() {
        return classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public Integer getPubStatus() {
        return pubStatus;
    }
    public void setPubStatus(Integer pubStatus) {
        this.pubStatus = pubStatus;
    }
    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }
    public Date getAuditDate() {
        return auditDate;
    }
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }
    public String getAuditAdvise() {
        return auditAdvise;
    }
    public void setAuditAdvise(String auditAdvise) {
        this.auditAdvise = auditAdvise;
    }
    public boolean getChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isNocheck() {
      return nocheck;
    }
    public void setNocheck(boolean nocheck) {
      this.nocheck = nocheck;
    }
    


}


