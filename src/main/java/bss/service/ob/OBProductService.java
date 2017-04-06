package bss.service.ob;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * 
	 * Description: 验证产品代码唯一
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月15日 
	 * @param  @param code
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int yzProductCode(String code,String id);
	
	/**
	 * 
	 * Description: 验证产品名称唯一
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月15日 
	 * @param  @param name
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int yzProductName(String name,String id);
	
	/**
	 * 
	 * Description: 验证采购机构是否存在
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月16日 
	 * @param  @param shortName
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int yzorg(String shortName);
	
	/**
	 * 
	 * Description: 修改产品发布状态
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月23日 
	 * @param  @param id 
	 * @return void 
	 * @exception
	 */
	void fab(String id);
	
	/**
	 * 
	 * Description: 撤回发布
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月1日 
	 * @param  @param id 
	 * @return void 
	 * @exception
	 */
	void chfab(String id);
	
	/**
	 * 
	 * Description: 查询所有的末节点
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月29日 
	 * @param  @param name
	 * @param  @return 
	 * @return List<OBProduct> 
	 * @exception
	 */
	List<OBProduct> selectAllAmallPointsId(String name);
	
	/**
	 * 
	 * Description: 根据目录获取采购机构
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月2日 
	 * @param  @param smallPointsId
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	String selOrgByCategory(String smallPointsId,String id);
	/**
	 * 导出竞价 定型产品数据
	 * @author YangHongLiang
	 * @param start
	 * @param end
	 * @param synchDate
	 * @return
	 */
	boolean exportProduct(String start,String end,Date synchDate);
}
