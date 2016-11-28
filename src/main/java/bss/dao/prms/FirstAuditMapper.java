package bss.dao.prms;

import java.util.List;

import bss.model.prms.FirstAudit;

public interface FirstAuditMapper {
    int deleteByPrimaryKey(String id);

    int insert(FirstAudit record);

    int insertSelective(FirstAudit record);

    FirstAudit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FirstAudit record);

    int updateByPrimaryKey(FirstAudit record);
    /**
     * 
      * @Title: selectListByProjectId
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:27:58  
      * @Description: TODO 根据项目id 查询初审项集合
      * @param @param projectId
      * @param @return      
      * @return List<FirstAudit>
     */
    List<FirstAudit> selectListByProjectId(String projectId);
    
    /**
     *〈简述〉根据项目id和初审项种类查询
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record
     * @return
     */
    List<FirstAudit> find(FirstAudit record);
}