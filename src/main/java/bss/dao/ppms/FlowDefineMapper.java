package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.FlowDefine;

public interface FlowDefineMapper {
    
    int delete(String id);

    int insert(FlowDefine fd);

    FlowDefine get(String id);

    int update(FlowDefine fd);
    
    List<FlowDefine> findList(FlowDefine fd);
    
    List<FlowDefine> getFlow(FlowDefine flowDefine);
    
    /**
     * 
    * @Title: viewFlow
    * @author FengTian 
    * @date 2017-7-6 下午2:02:16  
    * @Description: 根据步骤查询对应的流程 
    * @param @param map
    * @param @return      
    * @return List<FlowDefine>
     */
    List<FlowDefine> viewFlow(HashMap<String, Object> map);
    
    /**
     * 获取竞争性谈判的流程
     * @param map
     * @return
     */
    List<FlowDefine> getJzxtp(HashMap<String, Object> map);

}