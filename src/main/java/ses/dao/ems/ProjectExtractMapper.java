package ses.dao.ems;

import java.util.List;

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
}