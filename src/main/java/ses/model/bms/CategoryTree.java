package ses.model.bms;

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
     * 是否是父类
     */
    private String isParent;

    private Integer isEnd;

    private Integer status;

    private String paramStatus;

    /** 类型,物资类:1,其他为空 **/
    private transient String classify;

    /** 公开状态 **/
    private transient Integer pubStatus;

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





}


