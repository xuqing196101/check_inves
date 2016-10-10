package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpExtractRecord;

public interface ExpExtractRecordMapper {
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
    int insert(ExpExtractRecord record);

    /**
     *
     * @param record
     */
    int insertSelective(ExpExtractRecord record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ExpExtractRecord selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ExpExtractRecord record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(ExpExtractRecord record);
    
    /**
     * @Description:获取集合信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月9日 下午4:59:51  
     * @param @param record
     * @param @return      
     * @return List<ExpExtractRecord>
     */
    List<ExpExtractRecord> list(ExpExtractRecord record);
}