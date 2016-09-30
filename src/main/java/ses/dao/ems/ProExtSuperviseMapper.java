package ses.dao.ems;

import ses.model.ems.ProExtSupervise;

public interface ProExtSuperviseMapper {
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
    int insert(ProExtSupervise record);

    /**
     *
     * @param record
     */
    int insertSelective(ProExtSupervise record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ProExtSupervise selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProExtSupervise record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(ProExtSupervise record);
}