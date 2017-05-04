package ses.service.ems;

import ses.model.bms.AnalyzeItem;

/**
 * 
* @ClassName: AnalyzeService 
* @Description: 统计分析Service
* @author Easong
* @date 2017年5月3日 下午3:13:50 
*
 */
public interface AnalyzeService {
	
	/**
	 * 
	* @Title: getFileCountByEmp 
	* @Description: 文件上传统计
	* @author Easong
	* @param @return    设定文件 
	* @return AnalyzeItem    返回类型 
	* @throws
	 */
	public AnalyzeItem getFileCountByEmp();
	
	/**
	 * 
	* @Title: getRegisterCountByEmp 
	* @Description: 用户注册统计
	* @author Easong
	* @param @return    设定文件 
	* @return AnalyzeItem    返回类型 
	* @throws
	 */
	public AnalyzeItem getRegisterCountByEmp();
	
	/**
	 * 
	* @Title: getLoginCountByEmp 
	* @Description: 统计用户日登录量
	* @author Easong
	* @param @return    设定文件 
	* @return AnalyzeItem    返回类型 
	* @throws
	 */
	public AnalyzeItem getLoginCountByEmp();
}
