package extract.dao.expert;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import extract.model.expert.ExpertExtractResult;

/**
 * 
 * Description: 专家抽取结果
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractResultMapper {

    int deleteByPrimaryKey(String id);

    int insert(ExpertExtractResult record);

    int insertSelective(ExpertExtractResult record);

    ExpertExtractResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertExtractResult record);

    int updateByPrimaryKey(ExpertExtractResult record);
    
    /**
     * 
     * Description: 条件查询
     * 
     * @author zhang shubin
     * @data 2017年9月12日
     * @param 
     * @return
     */
    List<ExpertExtractResult> findByConditionIdExpertId(Map<String , Object> map);
    
    /**
     * 
     * Description: 根据条件ID查询专家
     * 
     * @author zhang shubin
     * @data 2017年9月12日
     * @param 
     * @return
     */
    List<String> findByConditionId(String conditionId);

    /**
     * 查询详细抽取结果
     * @param id
     * @return
     */
    List<ExpertExtractResult> getResultListByrecordId(String id);

    /**
     * 查询候补专家
     * @param id
     * @return
     */
    List<ExpertExtractResult> getBackExpertListByrecordId(String id);
    
    /**
     * 
     * Description: 查询所有
     * 
     * @author zhang shubin
     * @data 2017年9月21日
     * @param 
     * @return
     */
    List<ExpertExtractResult> findAll();
    
    /**
     * 
     * Description: 项目实施部分查询抽取结果是否存在
     * 
     * @author zhang shubin
     * @data 2017年9月26日
     * @param 
     * @return
     */
    List<ProjectExtract> findByPackageId(Map<String , Object> map);
    
    /**
     * 
     * Description: 新增结果数据（项目实施）
     * 
     * @author zhang shubin
     * @data 2017年9月26日
     * @param 
     * @return
     */
    int insertProject(ProjectExtract projectExtract);
    
    /**
     * 
     * Description: 修改结果数据（项目实施）
     * 
     * @author zhang shubin
     * @data 2017年9月26日
     * @param 
     * @return
     */
    int updateProject(ProjectExtract projectExtract);
    
    /**
     * 
     * Description: 根据项目id查询所有信息
     * 
     * @author zhang shubin
     * @data 2017年9月29日
     * @param 
     * @return
     */
    List<ExpertExtractResult> findAllByProjectId(String projectId);
    
    /**
     * 
     * Description: 根据项目id和专家Id修改是否参加
     * 
     * @author zhang shubin
     * @data 2017年10月17日
     * @param 
     * @return
     */
    int updateByProjectIdandExpertId(ExpertExtractResult expertExtractResult);
    
    /**
     * 
     * Description: 查询参加的专家个数
     * 
     * @author zhang shubin
     * @data 2017年10月17日
     * @param 
     * @return
     */
    Integer selNumByConditionId(Map<String, Object> map);
    
    /**
     * 
     * Description: 根据修改时间查询
     * 
     * @author zhang shubin
     * @data 2017年9月29日
     * @param 
     * @return
     */
    List<ExpertExtractResult> selectByUpdateDate(@Param("start")String start,@Param("end")String end);
    
    /**
     * 
     * Description: 修改删除标识
     * 
     * @author zhang shubin
     * @data 2017年10月20日
     * @param 
     * @return
     */
    int updateIsDelete(ExpertExtractResult expertExtractResult);
    
    
    /**
     * 
     * Description: 根据专家id和包id修改专家参加状态（项目实施）
     * 
     * @author zhang shubin
     * @data 2017年10月24日
     * @param 
     * @return
     */
    int updateProjectByEId(ProjectExtract projectExtract);
    
    /**
     * 
     * Description: 根据专家id查询手机号
     * 
     * @data 2018年1月2日
     * @param 
     * @return
     */
    Expert findByExpertId(@Param("expertId")String expertId);
    
    /**
     * 
     * Description: 根据项目id查询参加的专家
     * 
     * @data 2018年1月2日
     * @param 
     * @return
     */
    List<ExpertExtractResult> findByProjectId(@Param("projectId")String projectId);
    
    /**
     * 
     * Description: 判断专家是否第一次参加评审项目
     * 
     * @data 2018年1月2日
     * @param 
     * @return
     */
    Integer vaIsOnceJoin(@Param("expertId")String expertId);
    
    /**
     * 
     * Description:  根据typeId查询用户ID
     * 
     * @data 2018年1月6日
     * @param 
     * @return
     */
    String findUserByTypeId(@Param("typeId")String typeId);
}