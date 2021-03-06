package ses.service.bms;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierTypeTree;

import common.bean.ResBean;

/**
 * @Description: 采购目录管理接口
 * @author javazxf
 * @date 2017-11-29 下午7:36:03
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
	 *〈简述〉
	 * 根据主键查询
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param id
	 * @return
	 */
	public Category findById(String id);

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
	 * Description:分离 物资目录树  封装数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-16
	 * @param id
	 * @return
	 */
	public List<Category> disTreeGoodsData(String id);
	
	/**
	 * 
	 * Description: 根据父节点找子节点(公开的)
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月22日 
	 * @param  @param id
	 * @param  @return 
	 * @return List<Category> 
	 * @exception
	 */
	public List<Category> findTreeByPidPublish(String id);
	/**
	 * 
	 *〈简述〉
	 * 查询状态大于当状态的数据
	 *〈详细描述〉
	 * @author myc
	 * @param id 父节点Id
	 * @param status  状态
	 * @return 返回 Category 集合
	 */
	public List<Category> findTreeByStatus(String id, Integer status);
	
	/**
	 * 
	 *〈简述〉获取发布的
	 *〈详细描述〉
	 * @author myc
	 * @param id 父节点Id
	 * @param classfy 状态 
	 * @return
	 */
	public List<Category> findPublishTree(String id, Integer status);
	
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
	public List<Category> listByKeyword(Map<String, Object> map);
	
	/**
	 * 
	* @Title: listByKeyname
	* @author ZhaoBo
	* @date 2016-12-14 下午2:47:11  
	* @Description: 查找匹配的内容 
	* @param @param map
	* @param @return      
	* @return List<Category>
	 */
	public List<Category> listByKeyname(Map<String, Object> map);
	
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
	public List<SupplierTypeTree> queryCategory(Category category, List<String> listCategoryIds ,Integer type);
    
	  /**
		* 
		* @Title: findCategoryByType
		* @author zhangxuefeng 
		* @Description:根据类型查询品目名称
		* @param @param Category
		* @return Integer
		*/
    public BigDecimal checkName(String name); 
    /**
	 * 
	* @Title: listByParamstatus
	* @author zhangxuefeng 
	* @Description:根据参数状态查询品目
	* @param @param category
	* @return     List
	 */
   public List<Category> listByParamstatus(Map<String, Integer> map);
   
    public List<Category> findByStatus(Map<String, Object> map);
    
    List<Category> findByOrgId(String id);
    
    List<Category> listByCateogryName(Map<String, Object> map);
    
    /**
     * 
     *〈简述〉逻辑删除节点以及节点下的子节点
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param Id 节点id
     */
    public void deleted(String Id);

    /**
     * 
     *〈简述〉 按照名称检索
     *〈详细描述〉
     * @author myc
     * @param code 编码
     * @return 返回数量
     */
    public Integer findByCode(String code);
    
    /**
     * 
     *〈简述〉
     * 保存品目
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return
     */

    public ResBean saveCategory(HttpServletRequest request);
    
    /**
     * 
     *〈简述〉
     *  更新品目状态
     *〈详细描述〉
     * @author myc
     * @param status 状态
     * @param categoryId 品目ID
     * @return 成功返回ok,否则返回false
     */
    public String updateStatus(Integer status, String categoryId);
    
    /**
     * 
     *〈简述〉
     *  判断是否可以编辑或者删除
     *〈详细描述〉
     * @author myc
     * @param id {@link java.lang.string} 主键
     * @param opera {@link java.lang.string} 操作类型
     * @param stepMsg 步骤提示
     * @param status 状态
     * @return 
     */
    public String estimate(String id, String opera,String stepMsg ,Integer status);
    /**
     * 
    * @Title: findCategory
    * @Description: TODO 
    * author: Li Xiaoxiao 
    * @param @param map
    * @param @return     
    * @return List<>     
    * @throws
     */
    public List<Category> findCategory(Map<String, Object> map,Integer page);
    
    /**
     * 
     *〈简述〉根据品目Id查询品目和资质信息
     *〈详细描述〉
     * @author myc
     * @param id 品目Id
     * @param isProjectCate 
     * @return Category
     */
    public Category getCategoryQuaById(String id);
    
    public List<Category> findCategoryByStatusAll(Integer status);
    public List<Category> findCategoryByParentNode(HashMap<String, Object> map);
    public List<Category> findCategoryByChildren(HashMap<String, Object> map);
    
    public List<Category> findCategoryByName(HashMap<String, Object> map);
    public List<Category> findCategoryByNameOrClassify(HashMap<String, Object> map);
    
    public List<Category> findTreeByPidAndName(HashMap<String, Object> map);
    public List<Category> readNameAndPid(HashMap<String, Object> map);
    public List<Category> searchByNameAndCode(String name,String code,Integer ispublish);

	public List<Category> findByParentId(String parentId);
	 public List<Category> findCategoryByChildrenAndWuZi(HashMap<String, Object> map);
    
	/**
	 * 
	 * Description: 根据名称查询
	 * 
	 * @author zhang shubin
	 * @version 2017年3月21日
	 * @param @param code
	 * @param @return
	 * @return List<Category>
	 * @exception
	 */
	public List<Category> selectByCode(String code);

	/**
	 * 门户网查询公开产品
	 * 
	 * @param pid
	 * @return
	 */
	public List<Category> findTreeByPidIsPublish(String pid);
	/**
	 * 导出产品目录 根据时间范围
	 * @param start
	 * @param end
	 * @param synchDate
	 * @return
	 */
	public boolean exportCategory(String start ,String end,Date synchDate);
	/**
	 * 导入产品目录数据 
	 * @param file
	 * @return
	 */
	public boolean importCategory(File file);
	/**
	 * 导出目录资质关联表录 根据时间范围
	 * @param start
	 * @param end
	 * @param synchDate
	 * @return
	 */
	public boolean exportCategoryQua(String start ,String end,Date synchDate);
	/**
	 * 导入目录资质关联表录数据 
	 * @param file
	 * @return
	 */
	public boolean importCategoryQua(String synchType,File file);
	/**
	 * 
	 * Description:获取目录的父 类
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-26
	 * @param categoryId
	 * @return
	 */
	public List<Category> getAllParentNode(String categoryId);
	/**
	 * 
	 * Description:获取目录 类型大类 中类 小类 品种
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-26
	 * @param parentNodeList
	 * @return
	 */
	public SupplierCateTree addNode(List<Category> parentNodeList);

	/**
	 * 根据ID查询所有子节点
	 * @param id
	 * @return
	 */
	public List<Category> getCListById(String id);
	/**
	 * 根据ID查询所有父节点
	 * @param id
	 * @return
	 */
	public List<Category> getPListById(String id);
	/**
	 * 根据CODE查询所有子节点
	 * @param code
	 * @return
	 */
	public List<Category> getCListByCode(String code);
	/**
	 * 根据CODE查询所有父节点
	 * @param code
	 * @return
	 */
	public List<Category> getPListByCode(String code);

	public List<CategoryTree> getTreeForExt(Category category,
			String supplierTypeCode, String categoryId);

	public List<DictionaryData> getEngAptitudeLevelByCategoryId(String categoryId);

	public List<DictionaryData> getQuaByCid(String categoryId);

	/**
	 * 根据itme中间表id查询categor
	 * @param itemsId
	 * @return
	 */
	Category selectCategoryByItemId (String itemsId);
	
	/**
	 * 按品目名称搜索品目树
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-9下午3:10:57
	 * @param typeId
	 * @param cateName
	 * @param cateCode 
	 * @return
	 */
	Set<CategoryTree> selectCategoryByName(String typeId, String cateName, String cateCode);
	 
	/**
	 * Description: 根据条件查询目录树
	 *              包括：
	 *              物资生产、物资销售、工程、服务
	 *
	 * @author Easong
	 * @version 2017/10/24
	 * @param 
	 * @since JDK1.7
	 */
	List<Category> selectAllCateByCond(Map<String, Object> map);
	
	/**
	 * 根据名称搜索类别
	 * @param cateName
	 * @param flag
	 * @param codeName
	 * @return
	 */
	List<Category> searchByName(String cateName, String codeName);

	/**
	 * 搜索品目
	 * @param type
	 * @param cateName
	 * @param codeName
	 * @return
	 */
	List<Category> searchList(int type, String cateName, String codeName);
	
	/**
	 * 
	 * 
	 * Description: 根据id查询名称
	 * 
	 * @data 2017年12月4日
	 * @param 
	 * @return String
	 */
	Category selectById(String id);
	
}
