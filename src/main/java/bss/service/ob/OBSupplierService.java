package bss.service.ob;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.User;

import common.model.UploadFile;
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
	 * @param user 
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
			String supplierName,String smallPointsName,String smallPointsId, String string);

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
	 * Description:暂停
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
	 * Description: 恢复
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月10日 
	 * @param  @param id 
	 * @return void 
	 * @exception
	 */
	void restoreByPrimaryKey(String id);

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
	int yzSupplierName(String supplierId, String smallPointsId,String id);
	
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
	
	/**
	 * 
	 * Description: 根据业务id查询主键id
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月21日 
	 * @param  @param businessId
	 * @param  @param key
	 * @param  @return 
	 * @return List<UploadFile> 
	 * @exception
	 */
	List<UploadFile> findBybusinessId(String businessId,Integer key);
	
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
	Integer yzzsCode(String certCode,String id);
	/**
	 * 根据时间导出 竞价供应商数据表
	 * @param start
	 * @param end
	 * @param synchDate
	 * @return
	 */
	boolean exportSupplier(String start,String end,Date synchDate);
	/**
	 * 导入 竞价供应商 数据文件
	 * @param file
	 * @return
	 */
	boolean importSupplier(File file);

}
