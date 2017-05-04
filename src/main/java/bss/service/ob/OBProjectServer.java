package bss.service.ob;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import bss.model.ob.OBProduct;
import bss.model.ob.OBProject;
import bss.model.ob.OBSupplier;

/***
 * 竞价信息管理 接口
 * 
 * @author YangHongLiang
 * 
 */
public interface OBProjectServer {
	/***
	 * 分页显示竞价信息
	 */
	List<OBProject> list(OBProject op);
	/**
	 * 更新竞价信息
	 * @param project
	 * @return
	 */
    int updateProject(OBProject project);  
	/**
	 * 
	 * @Title: list
	 * @Description: 查询所有竞价信息列表
	 * @param @param map
	 * @param @return 设定文件
	 * @return List<OBProject> 返回类型
	 * @throws
	 */
	List<OBProject> selectAllOBproject(Map<String, Object> map);

	/** 获取 产品 **/
	List<OBProduct> productList();

	/**
	 * 根据创建人 获取数据
	 * **/
	List<OBProject> List(Map<String, Object> map);

	/**
	 * 保存竞价信息
	 * 
	 * @param OBProject
	 * @param List
	 *            <OBProductInfo>
	 * 
	 * **/
	String saveProject(OBProject project, String uid, String fileid);
	/**
	 * 更新竞价信息
	 * 
	 * @param OBProject
	 * @param List
	 *            <OBProductInfo>
	 * 
	 * **/
	String updateProject(OBProject project, String uid, String fileid);

	/**
	 * 获取定型产品相关信息 并返回 json
	 * @author Yanghongliang
	 * */
	String getProduct(Map<String ,Object> map);
	/**
	 * 获取可编辑竞价信息
	 * @author YangHongLiang
	 * @param projectId
	 * @param userID
	 * @return
	 */
	OBProject editOBProject(Map<String,Object> map);
	
	/**
	 * 验证 产品 是否在同一目录下
	 * @author YangHongLiang
	 * @param productList
	 * @return
	 */
	 String verifyCatalog(List<String> productList);

	/**
	 * 
	 * Description: 根据主键查询竞价信息
	 * 
	 * @author zhang shubin
	 * @version 2017年3月11日
	 * @param @param id
	 * @param @return
	 * @return OBProject
	 * @exception
	 */
	OBProject selectByPrimaryKey(String id);
    
	/**
	 * 
	 * 根据规则 改变状态
	 * 
	 * @author YangHongLiang
	 * @param @param id
	 * @exception
	 */
	void changeStatus(String projectId);
	/**
	 * 
	 * 获取 并集 供应商
	 * @author YangHongLiang
	 * @exception
	 */
	List<OBSupplier> selecUniontSupplier(List<String> productID);
	
	/**
	 * 
	* @Title: selectSupplierOBproject 
	* @Description: 供应商查看符合自己的竞价项目
	* @author Easong
	* @param @param map
	* @param @return    设定文件 
	* @return List<OBProject>    返回类型 
	* @throws
	 */
	Map<String, Object> selectSupplierOBproject(Map<String,Object> map);

	/**
	 * 
	 * 获取 供应商信息
	 * @author YangHongLiang
	 * @exception
	 */
	List<OBSupplier>  supplierList (Integer page,String projectid,
			String name,String status,String result);
	
	/**
	 * 
	* @Title: findBiddingInfo 
	* @Description: 根据主键查询竞价信息
	* @author Easong
	* @param @param projectId
	* @param @return    设定文件 
	* @return OBProject    返回类型 
	* @throws
	 */
	Map<String, Object> findBiddingInfo(String projectId);
	/**
	 * 导出竞价相关的信息
	 * @author YangHongLiang
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param synchDate同步时间
	 * @return
	 */
	boolean exportProject(String start,String end,Date synchDate);
	/**
	 * 导入 竞价信息 文件数据
	 * @author YangHongLiang
	 * @param file
	 * @return
	 */
	boolean importProject(File file);
	/**
	 * 导入 竞价信息 文件
	 * @param file
	 * @return
	 */
	boolean importFile(File file, Integer sysKey);
	/**
	 * 导出竞价结果相关数据
	 * @author YangHongLiang
	 * @param start
	 * @param end
	 * @param synchDate
	 * @return
	 */
	boolean exportProjectResult(String start,String end,Date synchDate);
	/**
	 * 导入竞价结果数据
	 * @author YangHongLiang
	 * @param file
	 * @return
	 */
	boolean importProjectResult(File file);

}
