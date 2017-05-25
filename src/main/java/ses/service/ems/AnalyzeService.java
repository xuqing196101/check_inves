package ses.service.ems;

import java.util.Date;
import java.util.List;

import ses.model.bms.Analyze;
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
	 * @param @return 设定文件
	 * @return AnalyzeItem 返回类型
	 * @throws
	 */
	public AnalyzeItem getFileCountByEmp();

	/**
	 * 
	 * @Title: getRegisterCountByEmp
	 * @Description: 用户注册统计
	 * @author Easong
	 * @param @return 设定文件
	 * @return AnalyzeItem 返回类型
	 * @throws
	 */
	public AnalyzeItem getRegisterCountByEmp();

	/**
	 * 
	 * @Title: getLoginCountByEmp
	 * @Description: 统计用户日登录量
	 * @author Easong
	 * @param @return 设定文件
	 * @return AnalyzeItem 返回类型
	 * @throws
	 */
	public AnalyzeItem getLoginCountByEmp();

	/**
	 * 
	 * @Title: taskAnalyzeLogin
	 * @Description: 定时执行任务，登录日志表 新增数据转到统计表
	 * @author Easong
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void taskAnalyzeLogin(Date searchData);

	/**
	 * 定时执行任务，注册， 新增数据转到统计表
	 * 
	 * @Title: taskAnalyzeRegister
	 * @Description:
	 * @author Easong
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void taskAnalyzeRegister(Date searchData);

	/**
	 * 
	 * @Title: taskAnalyzeAttUpload
	 * @Description: 定时执行任务，文件上传表 新增数据转到统计表
	 * @author Easong
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void taskAnalyzeAttUpload(Date searchData);

	/**
	 * 
	 * @Title: analyzeLoginCount
	 * @Description: 登录统计
	 * @author Easong
	 * @param @param analyzeType
	 * @param @return 设定文件
	 * @return AnalyzeItem 返回类型
	 * @throws
	 */
	public List<Analyze> analyzeLoginCount(String analyzeType,
			Integer analyzeTypeIntegerStart, Integer analyzeTypeIntegerEnd, String analyzeTypeByCate);
	
	/**
	 * 
	* @Title: vertifyIpAddressType 
	* @Description: 判断内外网
	* @author Easong
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String vertifyIpAddressType();
}
