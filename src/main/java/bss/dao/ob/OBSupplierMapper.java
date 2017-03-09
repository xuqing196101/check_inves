package bss.dao.ob;

import bss.model.ob.OBSupplier;
import bss.model.ob.OBSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBSupplierMapper {
	int countByExample(OBSupplierExample example);

	/**
	 * 
	 * Description: 删除 改变删除状态
	 * 
	 * @author zhang shubin
	 * @version 2017年3月9日
	 * @param @param id
	 * @return void
	 * @exception
	 */
	void deleteByPrimaryKey(@Param("id") String id);

	int insert(OBSupplier record);

	int insertSelective(OBSupplier record);

	List<OBSupplier> selectByExample(OBSupplierExample example);

	/***
	 * 根据产品id 获取供应商信息
	 * 
	 * @param id
	 * @return
	 */
	List<OBSupplier> selectByProductID(String id);

	/**
	 * 
	 * Description: 根据ID查询所有
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param id
	 * @param @return
	 * @return List<OBSupplier>
	 * @exception
	 */
	List<OBSupplier> selectByProductId(@Param("productId") String productId);

	/**
	 * 
	 * Description: 根据ID查询 证书过期
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param id
	 * @param @return
	 * @return List<OBSupplier>
	 * @exception
	 */
	List<OBSupplier> selectByProductId1(@Param("productId") String productId);

	/**
	 * 
	 * Description: 根据ID查询 证书未过期
	 * 
	 * @author zhang shubin
	 * @version 2017年3月8日
	 * @param @param id
	 * @param @return
	 * @return List<OBSupplier>
	 * @exception
	 */

	List<OBSupplier> selectByProductId2(@Param("productId") String productId);

	/**
	 * 
	 * Description: 查询产品对应的供应商数量
	 * 
	 * @author zhang shubin
	 * @version 2017年3月8日
	 * @param @return
	 * @return List<OBSupplier>
	 * @exception
	 */
	List<OBSupplier> selectSupplierNum();

	OBSupplier selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") OBSupplier record,
			@Param("example") OBSupplierExample example);

	int updateByExample(@Param("record") OBSupplier record,
			@Param("example") OBSupplierExample example);

	int updateByPrimaryKeySelective(OBSupplier record);

	int updateByPrimaryKey(OBSupplier record);
}