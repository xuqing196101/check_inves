package ses.service.sms;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ses.model.bms.User;
import ses.model.sms.SMSProductBasic;
import ses.model.sms.SMSProductCheckRecord;
import ses.model.sms.SMSProductVO;
import common.utils.JdcgResult;

/**
 * 
 * @ClassName: SMSProductLibService
 * @Description: 产品库管理接口的定义
 * @author Easong
 * @date 2017年4月18日 下午6:06:02
 * 
 */
public interface SMSProductLibService {

	/**
	 * 
	 * @Title: addProductLibInfo
	 * @Description: 供应商后台录入产品信息
	 * @author Easong
	 * @param @param smsProductVO
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult addProductLibInfo(SMSProductVO smsProductVO,
			Integer flag, User user);

	/**
	 * 
	 * @Title: findAllProductLibBasicInfo
	 * @Description: 查询商品的基本信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return List<SMSProductBasic> 返回类型
	 * @throws
	 */
	public List<SMSProductBasic> findAllProductLibBasicInfo(
			Map<String, Object> map);

	/**
	 * 
	 * @Title: findSignalProductInfo
	 * @Description: 查询单个商品的全部信息
	 * @author Easong
	 * @param @param id
	 * @param @return 设定文件
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	public Map<String, Object> findSignalProductInfo(String id);

	/**
	 * 
	 * @Title: updateSignalProductInfo
	 * @Description: 供应商后台修改产品信息
	 * @author Easong
	 * @param @param smsProductVO
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult updateSignalProductInfo(SMSProductVO smsProductVO);

	/**
	 * 
	 * @Title: deleteProductLibInfo
	 * @Description: 删除产品
	 * @author Easong
	 * @param @param ids
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult deleteProductLibInfo(String ids[]);

	/**
	 * 
	 * @Title: findAllWaitCheck
	 * @Description: 供应商审核查询信息
	 * @author Easong
	 * @param @param map
	 * @param @return 设定文件
	 * @return List<SMSProductBasic> 返回类型
	 * @throws
	 */
	public List<SMSProductBasic> findAllWaitCheck(Map<String, Object> map);

	/**
	 * 
	 * @Title: checkProductInfo
	 * @Description: 供应商审核产品信息
	 * @author Easong
	 * @param @param user
	 * @param @param smsProductCheckRecord
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult checkProductInfo(User user,
			SMSProductCheckRecord smsProductCheckRecord);
	
	
	/**
	 * 
	* @Title: vartifyUniqueSKU 
	* @Description: SKU唯一校验
	* @author Easong
	* @param @param sku
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult vertifyUniqueSKU(String sku);
	  /**
     * 实现 产品库录入的未审核的数据   外网  导出 
     */
	public boolean exportAddProjectData(String start, String end, Date synchDate);
	 /***
     * 实现 管理员 产品库审核的 相关数据 内网
     */
	public boolean exportCheckProjectData(String start, String end,Date synchDate);
	   /**
     * 实现  导入产品库录入的未审核的数据
     */
	public boolean importAddProjectData(File file);
	 /***
     * 实现导入    管理员 产品库审核的 相关数据
     */
	public boolean importCheckProjectData(File file);
	
}