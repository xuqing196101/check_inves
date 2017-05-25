package iss.service.hl;

import iss.model.hl.ServiceHotline;

import java.util.List;

/**
 * 
 * Description： 服务热线Service接口
 * 
 * @author  zhang shubin
 * @version  
 * @since JDK1.7
 * @date 2017年5月25日 上午11:25:48 
 *
 */
public interface ServiceHotlineService {
	
	/**
	 * 
	 * Description: 查询所有
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月25日 
	 * @param  @param record
	 * @param  @param page
	 * @param  @return 
	 * @return List<ServiceHotline> 
	 * @exception
	 */
	public List<ServiceHotline> selectAll(ServiceHotline record,Integer page);
	
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
}
