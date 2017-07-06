package system.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.service.SystemPvService;

import ses.service.ems.AnalyzeService;

/**
 * 
 * @ClassName: AnalyzeTask
 * @Description: 统计分析任务
 * @author Easong
 * @date 2017年5月11日 下午4:10:32
 * 
 */
@Component("analyzeTask")
public class AnalyzeTask {

	// 注入统计Service
	@Autowired
	private AnalyzeService analyzeService;
	
	// 注入计数Service
	@Autowired
	private SystemPvService systemPvService;
	/**
	 * 
	* @Title: handlerAnalyze 
	* @Description: 定时执行任务，注册，登录日志表，文件上传表 新增数据转到统计表
	* @author Easong
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void handlerAnalyze(){
		// 执行登录统计任务
		analyzeService.taskAnalyzeLogin(null);
		// 执行注册统计任务
		analyzeService.taskAnalyzeRegister(null);
		// 执行文件上传统计任务
		analyzeService.taskAnalyzeAttUpload(null);
    }
	
	/**
	 * 
	 * Description: 定时执行统计访问量
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 */
	public void handlerPV(){
		systemPvService.handlePvSync();
	}
}
