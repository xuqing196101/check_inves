/**
 * 
 */
package ses.service.ems;

import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.statement.update.Update;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierExtRelate;

/**
 * @Description:抽取记录关联专家
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日下午4:10:47
 * @since  JDK 1.7
 */
public interface ProjectExtractService {
    /**
     * @Description:insert
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午4:12:09  
     * @param       
     * @return void
     */
    String insert(String  cId,String userId,String[] projectId,String conditionId);

    /**
     * @Description:集合展示
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:07:39  
     * @param @param projectExtract      
     * @return void
     */
    List<ProjectExtract> list(ProjectExtract projectExtract);

    /**
     * @Description:修改操作状态
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午8:02:39  
     * @param @param projectExtract      
     * @return void
     */
    void update(ProjectExtract projectExtract);

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
    void updateStatusCount(String type,String conTypeId);

    /**
     * @Description:insert
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午4:12:09  
     * @param       
     * @return void
     */
    void insertProjectExtract(ProjectExtract projectExtract);

    /**
     * 
     *〈简述〉获取单个对象
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     * @return 
     */
    ProjectExtract getExpExtRelate(String id);

    List<ProjectExtract> findExtractByExpertId (String expertId);

    /**
     * 删除为抽取的信息
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId
     */
    void del(String condition, String projectId, List<String> expertTypeIds,List<String> saveExpertTypeIds);

    /**
     * 抽取完成后删除信息
     */
    void delPe(String projectId);
    
    /**
     * 
     *〈简述〉根据项目ID关联查询抽取专家
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    List<Map<String, Object>> selectProExpert(String projectId);
    
    
    /**
     * 专家抽取结果回显
     * @param projectId
     * @return
     */
    List<Map<String, Object>> selectProResultExpert(String projectId);
}
