package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierFsInfoWithBLOBs;


/**
 * @Title: SupplierFsInfoService
 * @Description: 进口供应商注册审核服务层
 * @author: Song Biaowei
 * @date: 2016-9-7下午6:02:26
 */
public interface SupplierFsInfoService {
	
	/**
	* @Title: register
	* @author Song Biaowei
	* @date 2016-9-1 下午3:31:35  
	* @Description: 进口供应商注册 
	* @param @param sfi      
	* @return void
	 */
	void register(SupplierFsInfoWithBLOBs sfi);
	
	/**
	* @Title: updateRegisterInfo
	* @author Song Biaowei
	* @date 2016-9-1 下午4:07:18  
	* @Description:修改进口供应商注册信息 
	* @param @param sfi      
	* @return void
	 */
	void updateRegisterInfo(SupplierFsInfoWithBLOBs sfi);
	
	/**
	* @Title: findById
	* @author Song Biaowei
	* @date 2016-9-1 下午4:07:13  
	* @Description: 按照id查询
	* @param @param id
	* @param @return      
	* @return SupplierFsInfoWithBLOBs
	 */
	SupplierFsInfoWithBLOBs findById(String id);
	
	/**
	* @Title: getCount
	* @author Song Biaowei
	* @date 2016-9-1 下午4:07:05  
	* @Description: 获取数据条数
	* @param @return      
	* @return int
	 */
	int getCount(SupplierFsInfoWithBLOBs sfi);
	
	/**
	* @Title: selectByFsInfo
	* @author Song Biaowei
	* @date 2016-9-5 上午9:26:51  
	* @Description: 查询进口供应商列表
	* @param @param sfi
	* @param @return      
	* @return List<SupplierFsInfoWithBLOBs>
	 */
	List<SupplierFsInfoWithBLOBs> selectByFsInfo(SupplierFsInfoWithBLOBs sfi);
	
	/**
	* @Title: selectByPrimaryKey
	* @author Song Biaowei
	* @date 2016-9-6 上午11:25:27  
	* @Description: TODO 
	* @param @param sfi
	* @param @return      
	* @return SupplierFsInfoWithBLOBs
	 */
	SupplierFsInfoWithBLOBs selectByPrimaryKey(SupplierFsInfoWithBLOBs sfi);
	
	/**
	 * @Title: selectIdByLoginName
	 * @author Song Biaowei
	 * @date 2016-9-7 上午10:10:44
	 * @Description: TODO
	 * @param @param sfi
	 * @param @return
	 * @return String
	 */
	String selectIdByLoginName(SupplierFsInfoWithBLOBs sfi);
}
