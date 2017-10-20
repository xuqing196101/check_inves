package synchro.service;

import java.io.File;
import java.util.Date;

/**
 * 
 * Description: 专家同步
 * 
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface SynchMilitaryExpertService {

	/**
	 * 
	 * Description: 军队专家导出
	 * 
	 * @data 2017年10月20日
	 * @param 
	 * @return
	 */
	public void militaryExpertExport(String start, String end, Date synchDate);
	
	/**
	 * 
	 * Description: 军队专家导入
	 * 
	 * @author zhang shubin
	 * @data 2017年10月20日
	 * @param 
	 * @return
	 */
	public void militaryExpertImport(File file);
}
