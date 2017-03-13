package bss.service.ob;

import java.util.List;
import java.util.Map;

import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;

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

	/** ---------------竞价看板模块---------------- **/

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
	 * 获取定型产品相关信息 并返回 json
	 * */
	String getProduct();

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
	void changeStatus();

}
