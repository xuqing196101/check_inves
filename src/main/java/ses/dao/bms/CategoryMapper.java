package ses.dao.bms;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.Category;



    /**
    * 
    * <p>Title:CategoryMapper</p>
    * <p>Description: 采购目录管理接口</p>
    * @author zhangxuefeng
    * @date 
    */
public interface CategoryMapper {

	
	
	/**   
	* @Title: selectAll
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description:获取所有数据
	* @param @param list<Category>
	*/
	public List<Category> selectAll();
	

	/**   
	* @Title: readExcel
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description: 根据编码获取所有数据
	* @param @param list<Category>
	*/
    public List<Category> readExcel(Category category);
	/**   
	* @Title: insert
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description: 新增用户信息
	* @param @param category
	* 
	*/
	public void insertSelective(Category category);
	/**
	 * 
	* @Title: findTreeByPid
	* @author zhangxuefeng
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据父节点找出子节点 
	* @param @param pid
	* @param @param pager
	* @param @return
	 */
	public List<Category> findTreeByPid(String id);
	
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
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @return
     */
    public Category findById(String id);
	
	/**
	 * 
	* @Title: updateByPrimaryKey
	* @author zhangxuefeng 
	* @Description: 根据id更新目录信息
	* @param @param pid
	* @param @param 
	* @param @return
	 */
	public void updateByPrimaryKey(Category category);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author zhangxuefeng  
	* @Description: 根据id查询目录信息
	* @param @param pid
	* @param @param pager
	* @param @return
	 */
	public Category selectByPrimaryKey(String id);
	/**
	 * 
	* @Title: uploadAttchment
	* @author zhangxuefeng
	* @Description: 上传附件
	* @param @param picFile
	* @param @param fileName
	* @param @param picPath      
	* @return string
	 */
	public String uploadAttchment(File picFile,String fileName,String picPath);
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
	* @Title: updateByPrimaryKeySelective
	* @author zhangxuefeng 
	* @Description: 根据节点id修改目录名称
	* @param @param category
	* @return void
	 */
	public void updateByPrimaryKeySelective(Category category);
		
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author zhangxuefeng 
	* @Description: 根据节点id删除目录信息
	* @param @param id
	* @return int
	 */
	public int deleteByPrimaryKey(String id);
	
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
	* @Title: listByKeyword
	* @author zhangxuefeng 
	* @Description:根据关键字查询品目名称
	* @param @param id
	* @return int
	 */
	void delete(String id);

	/**
	 * 
	* @Title: findCategoryByType
	* @author zhangxuefeng 
	* @Description:根据类型查询品目名称
	* @param @param category
	* @return     List
	 */
    List<Category> findCategoryByType(Category category);
    /**
	 * 
	* @Title: checkName
	* @author zhangxuefeng 
	* @Description:根据类型查询品目名称
	* @param @param Category
	* @return Integer
	 */
    BigDecimal checkName(String name); 
    /**
	 * 
	* @Title: listByParamstatus
	* @author zhangxuefeng 
	* @Description:根据参数状态查询品目
	* @param @param category
	* @return     List
	 */
    List<Category> listByParamstatus(Map<String, Integer> map);
    
    List<Category> findByStatus();
    
     List<Category> findByOrgId(String id);
     
     List<Category> listByCateogryName(Map<String, Object> map);
    
     /**
      * 
      *〈简述〉逻辑删除节点以及节点下的子节点
      *〈详细描述〉
      * @author Wang Wenshuai
      * @param id
      */
     void deleted(List<Category> list);
     
     /**
      * 
      *〈简述〉 按照品目名称查看品目是否存在
      *〈详细描述〉
      * @author myc
      * @param code 品目编码
      * @return 数量
      */
     public Integer findByCode(String code);

     /**
      * 
      *〈简述〉
      * 查询大于当前状态的数据
      *〈详细描述〉
      * @author myc
      * @param id 父节点Id
      * @param status 状态
      * @return Category 集合
      */
     public List<Category> findTreeByStatus(@Param("id")String id, @Param("status")Integer status);
     /**
      * 
     * @Title: findTreeByPid
     * @Description: TODO 
     * author: Li Xiaoxiao 
     * @param @param id
     * @param @return     
     * @return List<Category>     
     * @throws
      */
     public List<Category> findCategory(Map<String,Object> map);

     
     /**
      * 
      *〈简述〉根据父级节点id和状态查询已经发布的品目信息,如果状态为空查询所有的
      *〈详细描述〉
      * @author myc
      * @param id 父节点Id
      * @param status 状态
      * @return  Category 集合
      */
     public List<Category> findPublishTree(@Param("id")String id, @Param("status")Integer status);
     
     /**
     *〈简述〉根据产品名称模糊查询
     *〈详细描述〉
     * @author WangHuijie
     * @param cateName 产品名称
     * @return Category 集合
     */
    public List<Category> searchByName(String cateName, String codeName);
    public List<Category> findCategoryByStatusAll(Integer status);
    public List<Category> findCategoryByParentNode(HashMap<String, Object> map);
    public List<Category> findCategoryByChildren(HashMap<String, Object> map);
    public List<Category> findCategoryByName(HashMap<String, Object> map);
    public List<Category> findCategoryByNameOrClassify(HashMap<String, Object> map);
    public List<Category> findTreeByPidAndName(HashMap<String, Object> map);
    public List<Category> readNameAndPid(HashMap<String, Object> map);
    public List<Category> searchByNameAndCode(String name,String code,Integer ispublish);

    public List<Category> findCategoryByChildrenAndWuZi(HashMap<String, Object> map);
	public List<Category> findByParentId(String parentId);
	
	/**
	 * 
	 * Description: 根据名称查询
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月21日 
	 * @param  @param code
	 * @param  @return 
	 * @return List<Category> 
	 * @exception
	 */
	public List<Category> selectByCode(String code);
	
	public List<Category> listByKeywordIsPublish(HashMap<String, Object> map);
	/**
	 * 门户网查询公开产品目录
	 * @param pid
	 * @return
	 */
	public List<Category> findTreeByPidIsPublish(String pid);
	
	
	String getId(@Param("name")String name,@Param("code")String code);
	/**
	 * 根据更新 时间 获取范围数据
	 * @author YangHongLiang
	 * @param start
	 * @param end
	 * @return
	 */
	List<Category> selectByUpdatedAt(@Param("start")String start,@Param("end")String end);
	/**
	 * 根据创建  时间 获取范围数据
	 * @author YangHongLiang
	 * @param start
	 * @param end
	 * @return
	 */
	List<Category> selectByCreatedAt(@Param("start")String start,@Param("end")String end);
	/**
	 * 判断是否存在
	 * @param id
	 * @return
	 */
	Integer countByPrimaryKey(String id);
	
	/**
     * 
     *〈简述〉
     * 查询既公开又发布的数据
     *〈详细描述〉
     * @author Easong
     * @param id 父节点Id
     * @param status 状态
     * @return Category 集合
     */
    public List<Category> findTreeByStatusIndex(@Param("id")String id, @Param("status")Integer status);
    
    /**
     * 
    * @Title: getParentByChildren
    * @Description: 根据当前第查询所有的父节点 
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return List<Category>     
    * @throws
     */
    List<Category> getParentByChildren(@Param("id")String id);
    
    /**
     * 
     * Description:instr条件搜索目录
     * 
     * @author Easong
     * @version 2017年6月15日
     * @param cList
     * @return
     */
    List<Category> selectByCList(@Param("listID") String listID);

    /**
	 * 根据ID查询所有子节点
	 * @param id
	 * @return
	 */
	public List<Category> selectCListById(String id);

	/**
	 * 根据ID查询所有父节点
	 * @param id
	 * @return
	 */
	public List<Category> selectPListById(String id);

	/**
	 * 根据CODE查询所有子节点
	 * @param code
	 * @return
	 */
	public List<Category> selectCListByCode(String code);

	/**
	 * 根据CODE查询所有父节点
	 * @param code
	 * @return
	 */
	public List<Category> selectPListByCode(String code);
	
	/**
	 * 根据itme中间表id查询categor
	 * @param itemsId
	 * @return
	 */
	Category selectCategoryByItemId (String itemsId);
	
	/**
	 * 查询四级品目，供应商等级需要
	 * @param map
	 * @return
	 */
	public List<Category> findCategoryForSupplierLevel (HashMap<String, String> map);

	/**
	 * 
	 * Description: 根据父节点查询四级以上的品目id
	 * 
	 * @author zhang shubin
	 * @data 2017年9月21日
	 * @param 
	 * @return
	 */
	public List<String> selExtractCategory(String pId);


	public List<Category> findpublishTreeByPid(String id);


	public Collection<? extends Category> selectCategoryByName(
			HashMap<String, String> map);
}