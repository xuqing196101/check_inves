package ses.service.sms;

import java.util.List;

import ses.model.sms.ImportSupplierWithBLOBs;



/**
 * @Title: ImportSupplierService
 * @Description: 进口供应商注册审核服务层
 * @author: Song Biaowei
 * @date: 2016-9-7下午6:02:26
 */
public interface ImportSupplierService {
	
	/**
	* @Title: register
	* @author Song Biaowei
	* @date 2016-9-1 下午3:31:35  
	* @Description: 进口供应商注册 
	* @param @param is      
	* @return void
	 */
	void register(ImportSupplierWithBLOBs is);
	
	/**
	* @Title: updateRegisterInfo
	* @author Song Biaowei
	* @date 2016-9-1 下午4:07:18  
	* @Description:修改进口供应商注册信息 
	* @param @param is      
	* @return void
	 */
	void updateRegisterInfo(ImportSupplierWithBLOBs is);
	
	/**
	* @Title: findById
	* @author Song Biaowei
	* @date 2016-9-1 下午4:07:13  
	* @Description: 按照id查询
	* @param @param id
	* @param @return      
	* @return ImportSupplierWithBLOBs
	 */
	ImportSupplierWithBLOBs findById(String id);
	
	/**
	* @Title: getCount
	* @author Song Biaowei
	* @date 2016-9-1 下午4:07:05  
	* @Description: 获取数据条数
	* @param @return      
	* @return int
	 */
	int getCount(ImportSupplierWithBLOBs is);
	
	/**
	* @Title: selectByFsInfo
	* @author Song Biaowei
	* @date 2016-9-5 上午9:26:51  
	* @Description: 查询进口供应商列表
	* @param @param is
	* @param @return      
	* @return List<ImportSupplierWithBLOBs>
	 */
	List<ImportSupplierWithBLOBs> selectByFsInfo(ImportSupplierWithBLOBs is,Integer page);
	
	/**
	* @Title: selectByPrimaryKey
	* @author Song Biaowei
	* @date 2016-9-6 上午11:25:27  
	* @Description: TODO 
	* @param @param is
	* @param @return      
	* @return ImportSupplierWithBLOBs
	 */
	ImportSupplierWithBLOBs selectByPrimaryKey(ImportSupplierWithBLOBs is);
	
	/**
	 * @Title: selectIdByLoginName
	 * @author Song Biaowei
	 * @date 2016-9-7 上午10:10:44
	 * @Description: TODO
	 * @param @param sfi
	 * @param @return
	 * @return String
	 */
	String selectIdByLoginName(ImportSupplierWithBLOBs is);
}
