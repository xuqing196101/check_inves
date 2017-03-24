package iss.service.ps;

import java.util.Map;

import common.utils.JdcgResult;
/**
 * 
* @ClassName: SearchService 
* @Description: 搜索接口的定义
* @author SongDong 
* @date 2017年3月1日 下午5:49:40 
*
 */
public interface SearchService {
	/**
	 * 
	* @Title: importAll 
	* @Description: 全量导入索引库
	* @author SongDong
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult importAll();
	
	
	/**
	 * 
	* @Title: search 
	* @Description: 搜索
	* @author SongDong
	* @param @param queryKey
	* @param @param page
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public Map<String, Object> search(String queryKey,Integer page) throws Exception;
	
	/**
	 * 
	* @Title: clearIndex 
	* @Description: 清空索引库
	* @author SongDong
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult clearIndex();


	/**
	 * 
	* @Title: deleteSignalIndex 
	* @Description: 条件清空索引
	* @author Easong
	* @param @param ids
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult deleteSignalIndex(String[] ids);


	/**
	 * 
	* @Title: addSignalIndex 
	* @Description: 将指定的标题添加到索引库
	* @author Easong
	* @param @param ids
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult addSignalIndex(String[] ids);
}
