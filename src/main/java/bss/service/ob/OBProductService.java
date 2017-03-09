package bss.service.ob;

import java.util.List;

import bss.model.ob.OBProduct;

/**
 * 
 * Description： 定型产品管理 Service
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年3月6日 下午5:26:38
 * 
 */
public interface OBProductService {

	/**
	 * 
	 * Description: 查询列表
	 * 
	 * @author zhang shubin
	 * @version 2017年3月6日
	 * @param @param example
	 * @param @return
	 * @return List<OBProduct>
	 * @exception
	 */
	List<OBProduct> selectByExample(OBProduct example, Integer page);

	/**
	 * 
	 * Description: 删除  修改删除状态
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月7日 
	 * @param  @param id 
	 * @return void 
	 * @exception
	 */
	void deleteByPrimaryKey(String id);
	
	/**
	 * 
	 * Description: 通过ID查询
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月7日 
	 * @param  @param id
	 * @param  @return 
	 * @return OBProduct 
	 * @exception
	 */
	OBProduct selectByPrimaryKey(String id);
	
	/**
	 * 
	 * Description: 插入非空数据
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月8日 
	 * @param  @param record
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int insertSelective(OBProduct record);
	
	
	/**
	 * 
	 * Description: 修改定型产品
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月8日 
	 * @param  @param record
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int updateByPrimaryKeySelective(OBProduct record);
}
