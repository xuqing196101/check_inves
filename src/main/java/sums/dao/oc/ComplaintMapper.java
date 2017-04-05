package sums.dao.oc;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
    
    /**
     * 
     * Description: 根据投诉人id查询
     * 
     * @author  zhang shubin
     * @version  2017年3月17日 
     * @param  @param userId
     * @param  @return 
     * @return List<Complaint> 
     * @exception
     */
    List<Complaint> selectComplaintByUserId(Complaint record);
    
    /**
     * 
     * Description: 删除 改变删除状态
     * 
     * @author  zhang shubin
     * @version  2017年3月18日 
     * @param  @param id 
     * @return void 
     * @exception
     */
    void updateIsDeleteByPrimaryKey(String id);
    
    /**
     * 
     * Description: 验证文件上传
     * 
     * @author  zhang shubin
     * @version  2017年4月2日 
     * @param  @param 
     * @param  @return 
     * @return Integer 
     * @exception
     */
    Integer yzsc(@Param("businessid") String businessid,@Param("typeId") String typeId);
}