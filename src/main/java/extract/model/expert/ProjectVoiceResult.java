package extract.model.expert;

import java.util.List;

/**
 * 
 * Description: 语音抽取数据回传实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ProjectVoiceResult {

    private String projectId;
    
    private String recordeId;
    
    private List<ExpertResult> expertResult;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRecordeId() {
        return recordeId;
    }

    public void setRecordeId(String recordeId) {
        this.recordeId = recordeId;
    }

    public List<ExpertResult> getExpertResult() {
        return expertResult;
    }

    public void setExpertResult(List<ExpertResult> expertResult) {
        this.expertResult = expertResult;
    }

}
