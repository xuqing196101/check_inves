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
    
    private List<ExpertResult> expertList;

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

    public List<ExpertResult> getExpertList() {
        return expertList;
    }

    public void setExpertList(List<ExpertResult> expertList) {
        this.expertList = expertList;
    }
}
