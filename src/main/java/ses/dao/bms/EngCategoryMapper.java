package ses.dao.bms;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.Category;



    /**
    * 
    * <p>Title:CategoryMapper</p>
    * <p>Description: 工程目录管理接口</p>
    * @author WangHuijie
    * @date 
    */
public interface EngCategoryMapper {

	
	
	/**   
	* @Title: selectAll
	* @author WangHuijie
	* @date 2016-7-27 下午4:52:29  
	* @Description:获取所有数据
	* @param @param list<Category>
	*/
	public List<Category> selectAll();
	

	/**   
	* @Title: readExcel
	* @author WangHuijie
	* @date 2016-7-27 下午4:52:29  
	* @Description: 根据编码获取所有数据
	* @param @param list<Category>
	*/
    public List<Category> readExcel(Category category);
	/**   
	* @Title: insert
	* @author WangHuijie
	* @date 2016-7-27 下午4:52:29  
	* @Description: 新增用户信息
	* @param @param category
	* 
	*/
	public void insertSelective(Category category);
	/**
	 * 
	* @Title: findTreeByPid
	* @author WangHuijie
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据父节点找出子节点 
	* @param @param pid
	* @param @param pager
	* @param @return
	 */
	public List<Category> findTreeByPid(String id);
	
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
	* @author WangHuijie 
	* @Description: 根据id更新目录信息
	* @param @param pid
	* @param @param 
	* @param @return
	 */
	public void updateByPrimaryKey(Category category);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author WangHuijie  
	* @Description: 根据id查询目录信息
	* @param @param pid
	* @param @param pager
	* @param @return
	 */
	public Category selectByPrimaryKey(String id);
	/**
	 * 
	* @Title: uploadAttchment
	* @author WangHuijie
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
	* @author WangHuijie 
	* @Description: 根据节点id修改目录名称
	* @param @param id
	* @return void
	 */
	public void updateNameById(String id);
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author WangHuijie 
	* @Description: 根据节点id修改目录名称
	* @param @param category
	* @return void
	 */
	public void updateByPrimaryKeySelective(Category category);
		
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author WangHuijie 
	* @Description: 根据节点id删除目录信息
	* @param @param id
	* @return int
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: listByKeyword
	* @author WangHuijie 
	* @Description:根据关键字查询品目名称
	* @param @param id
	* @return int
	 */
	public List<Category> listByKeyword(Map<String, Object> map);

	

	/**
	 * 
	* @Title: listByKeyword
	* @author WangHuijie 
	* @Description:根据关键字查询品目名称
	* @param @param id
	* @return int
	 */
	void delete(String id);

	/**
	 * 
	* @Title: findCategoryByType
	* @author WangHuijie 
	* @Description:根据类型查询品目名称
	* @param @param category
	* @return     List
	 */
    List<Category> findCategoryByType(Category category);
    /**
	 * 
	* @Title: checkName
	* @author WangHuijie 
	* @Description:根据类型查询品目名称
	* @param @param Category
	* @return Integer
	 */
    BigDecimal checkName(String name); 
    /**
	 * 
	* @Title: listByParamstatus
	* @author WangHuijie 
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


	public List<Category> selectParentId(String parentId);
	
	public List<Category> findExtractTree();
}