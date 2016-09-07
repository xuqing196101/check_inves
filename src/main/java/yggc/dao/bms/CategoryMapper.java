package yggc.dao.bms;

import java.io.File;
import java.util.List;



import yggc.model.bms.Category;
/**
 * 
* <p>Title:CategoryMapper</p>
* <p>Description: 采购目录管理接口</p>
* <p>Company: yggc </p> 
* @author javazxf
* @date 
 */
public interface CategoryMapper {

	/**   
	* @Title: selectAll
	* @author yyyml
	* @date 2016-7-27 下午4:52:29  
	* @Description: 根据pid获取所有子栏目
	* @param @param list<Category>
	*/
	public List<Category> listByParent(String pid);
	

	/**   
	* @Title: insert
	* @author yyyml
	* @date 2016-7-27 下午4:52:29  
	* @Description: 新增用户信息
	* @param @param category
	* 
	*/
	public void insertSelective(Category category);
	/**
	 * 
	* @Title: findTreeByPid
	* @author MRlovablee
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据父节点找出子节点 
	* @param @param pid
	* @param @param pager
	* @param @return
	 */
	public List<Category> findTreeByPid(String pid);
	
	/**
	 * 
	* @Title: updateByPrimaryKey
	* @author MRlovablee
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据id更新目录信息
	* @param @param pid
	* @param @param pager
	* @param @return
	 */
	public void updateByPrimaryKey(Category category);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author 
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据id查询目录信息
	* @param @param pid
	* @param @param pager
	* @param @return
	 */
	public Category selectByPrimaryKey(String id);
	/**
	 * 
	* @Title: uploadIndexPic
	* @author MRlovablee
	* @date 2016-5-27 下午3:20:22  
	* @Description: 上传首页图片 
	* @param @param picFile
	* @param @param fileName
	* @param @param picPath      
	* @return void
	 */
	public String uploadAttchment(File picFile,String fileName,String picPath);
}