package bss.service.ob;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.ob.OBSupplier;

/**
 * 
 * Description： 定型产品网上竞价 供应商信息管理
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年3月7日 下午4:17:50
 * 
 */
public interface OBSupplierService {

	/**
	 * 
	 * Description: 根据产品id查询
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param id
	 * @param @return
	 * @return List<OBSupplier>
	 * @exception
	 */
	List<OBSupplier> selectByProductId(String id, Integer page, Integer status,
			String supplierName);

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

	/**
	 * 
	 * Description: 删除 修改删除状态
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param id
	 * @return void
	 * @exception
	 */
	void deleteByPrimaryKey(String id);

	/**
	 * 
	 * Description: 插入非空数据
	 * 
	 * @author zhang shubin
	 * @version 2017年3月9日
	 * @param @param record
	 * @param @return
	 * @return int
	 * @exception
	 */
	int insertSelective(OBSupplier record);

	/**
	 * 
	 * Description: 根据主键ID查询
	 * 
	 * @author zhang shubin
	 * @version 2017年3月9日
	 * @param @param id
	 * @param @return
	 * @return OBSupplier
	 * @exception
	 */
	OBSupplier selectByPrimaryKey(String id);

	/**
	 * 
	 * Description: 修改
	 * 
	 * @author zhang shubin
	 * @version 2017年3月9日
	 * @param @param record
	 * @param @return
	 * @return int
	 * @exception
	 */
	int updateByPrimaryKeySelective(OBSupplier record);

	/**
	 * 
	 * Description: 验证供应商唯一
	 * 
	 * @author zhang shubin
	 * @version 2017年3月15日
	 * @param @param supplierId
	 * @param @param productId
	 * @param @return
	 * @return int
	 * @exception
	 */
	int yzSupplierName(String supplierId, String productId,String id);
	
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

}
