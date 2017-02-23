package ses.model.bms;

/**
 * Created by yggc on 2017/2/22.
 */
public class QualificationLevel {

    /** 主键 **/
    private String id;
    private String qualificationId;
    private String grade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
