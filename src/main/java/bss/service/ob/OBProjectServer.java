package bss.service.ob;

import java.util.List;
import java.util.Map;

import bss.model.ob.OBProject;

/***
 * 竞价信息管理 接口
 * @author YangHangLiang
 *
 */
public interface OBProjectServer {
    /***
     * 分页显示竞价信息
     */
	List<OBProject> list(OBProject op);
	
	
	/**---------------竞价看板模块----------------**/
	
	/**
	 * 
	* @Title: list 
	* @Description: 查询所有竞价信息列表
	* @param @param map
	* @param @return    设定文件 
	* @return List<OBProject>    返回类型 
	* @throws
	 */
	List<OBProject> selectAllOBproject(Map<String, Object> map);
	
	
}
