package ses.model.oms;

public class PurchaseInfoWithBLOBs extends PurchaseInfo {
    private String workExperience;

    private String trainExperience;

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience == null ? null : workExperience.trim();
    }

    public String getTrainExperience() {
        return trainExperience;
    }

    public void setTrainExperience(String trainExperience) {
        this.trainExperience = trainExperience == null ? null : trainExperience.trim();
    }
}