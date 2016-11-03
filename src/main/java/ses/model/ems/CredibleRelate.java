package ses.model.ems;

public class CredibleRelate {
    private String expertId;

    private String expertCredibleId;

    private String cause;

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    public String getExpertCredibleId() {
        return expertCredibleId;
    }

    public void setExpertCredibleId(String expertCredibleId) {
        this.expertCredibleId = expertCredibleId == null ? null : expertCredibleId.trim();
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }
}