package bss.dao.ob;

import bss.model.ob.OBSupplier;
import bss.model.ob.OBSupplierExample;
import java.util.List;
import java.util.Map;

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
	List<OBSupplier> selectByProductId(@Param("productId") String productId,
			@Param("supplierName") String supplierName);

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
	List<OBSupplier> selectByProductId1(@Param("productId") String productId,
			@Param("supplierName") String supplierName);

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

	List<OBSupplier> selectByProductId2(@Param("productId") String productId,
			@Param("supplierName") String supplierName);

	/**
	 * 
	 * Description:  证书未过期
	 * 
	 * @author YangHongLoang
	 * @version 2017年3月8日
	 * @param @param map
	 * @param @return
	 * @return int
	 * @exception
	 */

	Integer countByProductId2(Map<String, Object> map);

	/**
	 * 
	 * Description: 查询产品对应的合格供应商数量
	 * 
	 * @author zhang shubin
	 * @version 2017年3月8日
	 * @param @return
	 * @return List<OBSupplier>
	 * @exception
	 */
	List<OBSupplier> selectSupplierNum();

	/**
	 * 	
	 * Description: 验证供应商唯一
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月15日 
	 * @param  @param supplierId
	 * @param  @param productId
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int yzSupplierName(@Param("supplierId") String supplierId,
			@Param("productId") String productId,@Param("id") String id);

	/**
	 * 
	 * Description: 验证是否上传图片
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月15日 
	 * @param  @param id
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int yzShangchuan(String id);
	
	OBSupplier selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") OBSupplier record,
			@Param("example") OBSupplierExample example);

	int updateByExample(@Param("record") OBSupplier record,
			@Param("example") OBSupplierExample example);

	int updateByPrimaryKeySelective(OBSupplier record);

	int updateByPrimaryKey(OBSupplier record);
}