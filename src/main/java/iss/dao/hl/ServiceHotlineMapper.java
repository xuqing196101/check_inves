package iss.dao.hl;

import java.util.List;

import iss.model.hl.ServiceHotline;

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
}