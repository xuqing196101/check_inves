package ses.dao.sms;

import java.util.List;

import ses.model.sms.ImportRecommend;
/**
 * 版权：(C) 版权所有 
 * <简述>进口代理商持久层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public interface ImportRecommendMapper {
    /**
     *〈简述〉按照主键删除
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @return int
     */
    int deleteByPrimaryKey(String id);
    /**
     *〈简述〉新增数据
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int insert(ImportRecommend record);
    /**
     *〈简述〉新增数据某字段为空就不插入
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int insertSelective(ImportRecommend record);
    /**
     *〈简述〉按住键查找
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @return ImportRecommend
     */
    ImportRecommend selectByPrimaryKey(String id);
    
    /**
     *〈简述〉修改数据某字段为空就不修改
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKeySelective(ImportRecommend record);
    /**
     *〈简述〉修改数据
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKey(ImportRecommend record);
    
    /**
     *〈简述〉条件查询
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return List<ImportRecommend>
     */
    List<ImportRecommend> selectByRecommend(ImportRecommend record);
}