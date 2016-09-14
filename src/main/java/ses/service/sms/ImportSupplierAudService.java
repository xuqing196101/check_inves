package ses.service.sms;

import ses.model.sms.ImportSupplierAud;


/**
 * @Title: ImportSupplierAudService
 * @Description: 进口供应商初审
 * @author: Song Biaowei
 * @date: 2016-9-12上午9:23:34
 */
public interface ImportSupplierAudService {
	/**
	* @Title: register
	* @author Song Biaowei
	* @date 2016-9-1 下午3:31:35  
	* @Description: 进口供应商注册 
	* @param @param isa      
	* @return void
	 */
	void register(ImportSupplierAud isa);
	
	/**
	* @Title: updateRegisterInfo
	* @author Song Biaowei
	* @date 2016-9-1 下午4:07:18  
	* @Description:修改进口供应商注册信息 
	* @param @param isa      
	* @return void
	 */
	void updateRegisterInfo(ImportSupplierAud isa);
	
	/**
	 * @Title: findById
	 * @author Song Biaowei
	 * @date 2016-9-12 下午2:13:39  
	 * @Description: 按照id查询 
	 * @param @return      
	 * @return ImportSupplierAud
	 */
	ImportSupplierAud findById(String id);
}
