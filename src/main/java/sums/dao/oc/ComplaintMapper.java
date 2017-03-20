package sums.dao.oc;

import java.util.List;

import sums.model.oc.Complaint;
/**
 * 
 * @author jff
 * @version  
 * @since
 * @see
 */

public interface ComplaintMapper {
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
    int insert(Complaint record);

    /**
     *
     * @param record
     */
    int insertSelective(Complaint record);

    /**
     * 根据主键获取一条数据库记录
     *  对象
     * @param id
     */
    Complaint selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Complaint record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(Complaint record);
    
    /**
     * 查询所有投诉信息
     * @return
     */
    List<Complaint> selectAllComplaint(String id);
    
    //List<Complaint> selectByPrimaryKey(String id);
}