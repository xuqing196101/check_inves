package bss.model.pms;

public class AuditParam {
    private String id;

    private String dictioanryId;

    private String param;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDictioanryId() {
        return dictioanryId;
    }

    public void setDictioanryId(String dictioanryId) {
        this.dictioanryId = dictioanryId == null ? null : dictioanryId.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }
}