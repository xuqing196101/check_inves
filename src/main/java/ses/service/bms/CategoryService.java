package ses.service.bms;

import java.util.List;
import java.util.Map;

import ses.model.bms.Category;
import ses.model.sms.SupplierTypeTree;



   /**
   * 
   * <p>Title:CategoryMapper</p>
   * <p>Description: 采购目录管理接口</p>
   * @author javazxf
   * @date 
   */
	public interface CategoryService {
	/**   
	* @Title: selectAll
	* @author zhangxuefeng
	* @date
	* @Description: 查询目录信息
	* @param @param List<Category>
	* 
	*/
	public List<Category> listByParent(String pid);

	/**   
	* @Title: readExcel
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description: 根据编码获取所有数据
	* @param @param list<Category>
	*/
    public List<Category> readExcel(Category category);
    /**   
	* @Title: selectAll
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description:获取所有数据
	* @param @param list<Category>
	*/
	public List<Category> selectAll();
	/**   
	* @Title: insertsertSelective
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description: 新增目录
	* @param @param category
	* 
	*/
	public void insertSelective(Category category);
	
	/**
	 * 
	* @Title: findTreeByPid
	* @author	zhangxuefeng
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据父节点找出子节点 
	* @param @param pid
	* @param @return
	 */
	public List<Category> findTreeByPid(String id);
	/**
	 * 
	* @Title: updateByPrimaryKey
	* @author zhangxuefeng
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据父节点找出子节点 
	* @param @param pid
	* @param @param pager
	* @param @return
	*/
	public void updateByPrimaryKey(Category category);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author zhagxuefeng
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据id查询目录信息
	* @param @param id
	* @param @return
	*/
	public Category selectByPrimaryKey(String id);
	/**
	 * 
	* @Title: updateByPrimaryKey
	* @author zhangxuefeng 
	* @Description: 根据节点id修改目录名称
	* @param @param id
	* @return void
	 */
	public void updateNameById(String id);
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author zhangxuefeng 
	* @Description: 根据节点id删除
	* @param @param id
	* @return int
	 */
	public int deleteByPrimaryKey(String id);
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author zhangxuefeng 
	* @Description: 根据节点id修改目录名称
	* @param @param category
	* @return void
	 */
	public void updateByPrimaryKeySelective(Category category);
	/**
	 * 
	* @Title: listByKeyword
	* @author zhangxuefeng 
	* @Description:根据关键字查询品目名称
	* @param @param id
	* @return int
	 */
	public List<Category> listByKeyword(Map map);
	

	/**
	 * @Title: findCategoryByType
	 * @author: Wang Zhaohua
	 * @date: 2016-10-3 下午4:12:18
	 * @Description: 根据类型查询
	 * @param: @param category
	 * @param: @return
	 * @return: List<Category>
	 */
	public List<SupplierTypeTree> findCategoryByType(Category category, String supplierId);
	
	/**
	 * @Title: findCategoryByTypeAndDisabled
	 * @author Song Biaowei
	 * @date 2016-10-12 下午5:07:25  
	 * @Description: 查询供应商展示品目,不可选择状态
	 * @param @param category
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierTypeTree>
	 */
	public List<SupplierTypeTree> findCategoryByTypeAndDisabled(Category category, String supplierId);
	
	/**
	 * @Title: queryCategory
	 * @author Song Biaowei
	 * @date 2016-10-8 下午2:22:28  
	 * @Description: 按照品目查询供应商 
	 * @param @param category
	 * @param @param listCategoryIds
	 * @param @return      
	 * @return List<SupplierTypeTree>
	 */
	public List<SupplierTypeTree> queryCategory(Category category, List<String> listCategoryIds);

}
