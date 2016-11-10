package ses.service.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpertAttachment;

public interface ExpertAttachmentService {
	 /**
     * 
      * @Title: selectListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月29日 上午11:06:59  
      * @Description: TODO 根据条件查询附件集合
      * @param @param map
      * @param @return      
      * @return List<ExpertAttachment>
     */
	List<ExpertAttachment> selectListByMap(Map<String,Object> map);
}
