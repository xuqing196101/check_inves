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
			@Param("supplierName") String supplierName,@Param("smallPointsName")String smallPointsName,@Param("smallPointsId")String smallPointsId);

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
			@Param("supplierName") String supplierName,@Param("smallPointsName")String smallPointsNam,@Param("smallPointsId")String smallPointsId);

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
			@Param("supplierName") String supplierName,@Param("smallPointsName")String smallPointsName,@Param("smallPointsId")String smallPointsId);
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
			@Param("smallPointsId") String smallPointsId,@Param("id") String id);

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
	/**
	 * 查询产品对应的 并集供应商
	 * @author YangHongliang
	 * @param map
	 * @return
	 */
	List<OBSupplier> selecUniontSupplier(Map<String,Object> map); 
	/**
	 * 查询产品 模糊对应的 并集供应商
	 * @author YangHongliang
	 * @param map
	 * @return
	 */
	List<OBSupplier> selectSupplierDate(Map<String,Object> map);
	/**
	 * 查询产品 查询供应商 信息
	 * @author YangHongliang
	 * @param map
	 * @return
	 */
	List<OBSupplier> selectSupplierByID(Map<String,Object> map);
	
	/**
	 * 获取注册的有效供应商 数量
	 * @author YangHongliang
	 * @return
	 */
	int countProductId(String productId);
	
	/**
	 * 
	 * Description: 查询报价供应商
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月30日 
	 * @param  @param map
	 * @param  @return 
	 * @return List<OBSupplier> 
	 * @exception
	 */
	List<OBSupplier> selOfferSupplier(Map<String,Object> map);
	
	/**
	 * 
	 * Description: 验证证书编号唯一
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月5日 
	 * @param  @param certCode
	 * @param  @return 
	 * @return Integer 
	 * @exception
	 */
	Integer yzzsCode(@Param("certCode") String certCode,@Param("id") String id);
	/**
	 * 根据时间获取创建数据
	 * @param start
	 * @param end
	 * @return
	 */
	List<OBSupplier> selectByCreateDate(@Param("start")String start,@Param("end")String end);
	/**
	 * 根据时间获取修改数据
	 * @param start
	 * @param end
	 * @return
	 */
	List<OBSupplier> selectByUpdateDate(@Param("start")String start,@Param("end")String end);
}