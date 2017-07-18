package sums.dao.oc;

import java.util.List;

import sums.model.oc.Complaint;

import org.apache.ibatis.annotations.Param;

public interface ComplaintMapper {

    int insert(Complaint record);

    /**
     * 
     * Description: 插入非空数据
     * 
     * @author  zhang shubin
     * @version  2017年5月24日 
     * @param  @param record
     * @param  @return 
     * @return int 
     * @exception
     */
    int insertSelective(Complaint record);

    /**
     * 
     * Description: 通过主键查询
     * 
     * @author  zhang shubin
     * @version  2017年5月24日 
     * @param  @param id
     * @param  @return 
     * @return Complaint 
     * @exception
     */
    Complaint selectByPrimaryKey(String id);
    
    /**
     * 
     * Description: 条件查询所有
     * 
     * @author  zhang shubin
     * @version  2017年5月24日 
     * @param  @param complaint
     * @param  @return 
     * @return List<Complaint> 
     * @exception
     */
    List<Complaint> selectAllComplaint(Complaint complaint);

    /**
     * 
     * Description: 修改非空数据
     * 
     * @author  zhang shubin
     * @version  2017年5月24日 
     * @param  @param record
     * @param  @return 
     * @return int 
     * @exception
     */
    int updateByPrimaryKeySelective(Complaint record);

    int updateByPrimaryKey(Complaint record);
    
    /**
     * 
     * Description: 删除  修改删除状态
     * 
     * @author  zhang shubin
     * @version  2017年5月24日 
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
     * @version  2017年5月24日 
     * @param  @param businessid
     * @param  @param typeId
     * @param  @return 
     * @return Integer 
     * @exception
     */
    Integer yzsc(@Param("businessid") String businessid,@Param("typeId") String typeId);
    
    /**
     * 
     * Description: 根据创建时间查询
     * 
     * @author zhang shubin
     * @data 2017年7月17日
     * @param 
     * @return
     */
    List<Complaint> selectByCreateDate(@Param("start")String start,@Param("end")String end);

    /**
     * 
     * Description: 根据修改时间查询
     * 
     * @author zhang shubin
     * @data 2017年7月17日
     * @param 
     * @return
     */
    List<Complaint> selectByUpdateDate(@Param("start")String start,@Param("end")String end);

    /**
     * 
     * Description: 根据id查询数量
     * 
     * @author zhang shubin
     * @data 2017年7月17日
     * @param 
     * @return
     */
    Integer countById(@Param("id")String id);
}