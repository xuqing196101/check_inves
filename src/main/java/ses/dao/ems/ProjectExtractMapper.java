package ses.dao.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpExtractRecord;
import ses.model.ems.ProjectExtract;

public interface ProjectExtractMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(ProjectExtract record);

    /**
     *
     * @param record
     */
    int insertSelective(ProjectExtract record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ProjectExtract selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProjectExtract record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(ProjectExtract record);
    
    /**
     * @Description:集合获取中间表
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    List<ProjectExtract> list(ProjectExtract extract);

    /**
     * @Description:删除重复记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    void deleteData(Map map);
    
    /**
     * @Description:当抽取数量满足时修改还未抽取的专家状态为1
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    void updateStatusCount(Map map);
    
    
    /**
     * 
     *〈简述〉查询是否已经存在表中
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param map
     * @return
     */
    Integer getexpCount(Map map);
    
    /**
     * 
     *〈简述〉批量插入
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    void insertList(List<ProjectExtract> list);
    
    List<ProjectExtract> findExtractByExpertId (String expertId);
    
    /**
     * 删除为抽取的信息
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId
     */
    void del(Map<String, String> map);
    
    /**
     * 
     *〈简述〉抽取之后删除
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId
     */
    void delPe(String projectId);

    /**
     * 根据条件查询专家/包关联
     * @param projectExtract
     * @return
     */
    List<ProjectExtract> findProjectExtractByCondition(ProjectExtract projectExtract);

    /**
     * 批量插入,必要参数(用于插入临时专家)
     * @param list
     */
    void insertBatch(List<ProjectExtract> list);
    
    /**
     * 
     * Description: 修改临时专家的包Id
     * 
     * @author zhang shubin
     * @data 2017年6月27日
     * @param 
     * @return
     */
    void updateProjectByExpertId(Map<String, Object> map);
    
    /**
     * 
     *〈简述〉根据项目ID关联查询抽取专家
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<Map<String, Object>> selectProExpert(String projectId);
}