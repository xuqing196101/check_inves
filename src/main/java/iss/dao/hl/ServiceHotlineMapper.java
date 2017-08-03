package iss.dao.hl;

import iss.model.hl.ServiceHotline;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ServiceHotlineMapper {
	
	/**
	 * 
	 * Description: 删除    更改删除标识
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月25日 
	 * @param  @param id
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
    int deleteByPrimaryKey(String id);

    int insert(ServiceHotline record);

    /**
     * 
     * Description: 插入非空数据
     * 
     * @author  zhang shubin
     * @version  2017年5月25日 
     * @param  @param record
     * @param  @return 
     * @return int 
     * @exception
     */
    int insertSelective(ServiceHotline record);

    /**
     * 
     * Description: 根据主键查询
     * 
     * @author  zhang shubin
     * @version  2017年5月25日 
     * @param  @param id
     * @param  @return 
     * @return ServiceHotline 
     * @exception
     */
    ServiceHotline selectByPrimaryKey(String id);

    /**
     * 
     * Description: 修改非空数据
     * 
     * @author  zhang shubin
     * @version  2017年5月25日 
     * @param  @param record
     * @param  @return 
     * @return int 
     * @exception
     */
    int updateByPrimaryKeySelective(ServiceHotline record);

    int updateByPrimaryKey(ServiceHotline record);
    
    /**
     * 
     * Description: 条件查询所有
     * 
     * @author  zhang shubin
     * @version  2017年5月25日 
     * @param  @param record
     * @param  @return 
     * @return List<ServiceHotline> 
     * @exception
     */
    List<ServiceHotline> selectAll(ServiceHotline record);
    
    /**
     * 
     * Description: 根据创建时间查询
     * 
     * @author zhang shubin
     * @data 2017年8月3日
     * @param 
     * @return
     */
    List<ServiceHotline> selectByCreateDate(@Param("start")String start,@Param("end")String end);

    /**
     * 
     * Description: 根据修改时间查询
     * 
     * @author zhang shubin
     * @data 2017年8月3日
     * @param 
     * @return
     */
    List<ServiceHotline> selectByUpdateDate(@Param("start")String start,@Param("end")String end);

    /**
     * 
     * Description: 根据id查询数量
     * 
     * @author zhang shubin
     * @data 2017年8月3日
     * @param 
     * @return
     */
    Integer countById(@Param("id")String id);
    
    /**
     * 
     * Description: 新增
     * 
     * @author zhang shubin
     * @data 2017年8月3日
     * @param 
     * @return
     */
    int addHotline(ServiceHotline record);
}