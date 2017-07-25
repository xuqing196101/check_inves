package ses.dao.sms;

import org.apache.ibatis.annotations.Param;
import ses.model.sms.SupplierItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SupplierItemMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierItem record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierItem record);
    
    /**
     *〈简述〉条件查询
     *〈详细描述〉
     * @author WangHuijie
     * @param param
     * @return
     */
    List<SupplierItem> selectByMap(Map<String, Object> param);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierItem selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierItem record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierItem record);
    
    List<SupplierItem> findSupplierItemBySupplierId(String supplierId);
    
    List<SupplierItem> findSupplierItemBySupplierIdAndType(SupplierItem supplierItem);
    
    int deleteBySupplierId(String supplierId);
    
    List<String> getSupplierId();
    List<String> getItemBySupplierId();
    
    List<SupplierItem> findByMap(Map<String, Object> param);
    
    int deleteByMap(Map<String, String> param);
    
    List<SupplierItem> getSupplierItem(@Param("supplierId")String supplierId);
    
    /**
     * 
    * @Title: delete
    * @Description: 根据类型id删除有的关联
    * author: Li Xiaoxiao 
    * @param @param relateId     
    * @return void     
    * @throws
     */
    void deleteRelate(@Param("relateId")String relateId ,@Param("supplierId")String supplierId);
    
    
    List<SupplierItem>  getBySupplierIdCategoryId(@Param("supplierId")String supplierId,@Param("categoryId")String categoryId,@Param("type")String type);
    
    /**
     * 
    * @Title: queryBySupplierIdAndType
    * @Description:根据供应商ID和供应商的类型判断是否存在
    * author: Li Xiaoxiao 
    * @param @param supplierId
    * @param @param type
    * @param @return     
    * @return List<SupplierItem>     
    * @throws
     */
    List<SupplierItem> queryBySupplierIdAndType(@Param("supplierId")String supplierId,@Param("type")String type);
    
    /**
	 * @Title: selectByCategoryId
	 * @date 2017-5-9 下午6:44:06  
	 * @Description:查询类型
	 * @param @param categoryId      
	 * @return void
	 */
	List<SupplierItem> selectByCategoryId (String categoryId);
    
	
	/**
	 * 
	 * Description:统计供应商类型数量 物资销售、物资生产、工程、服务 
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @param cateType
	 * @return
	 */
	BigDecimal findAnalyzeSupplierCateType(@Param("cateType") String cateType);
	/**
	 * 
	 * Description:品目id 供应商 类型 查询类型 --
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-15
	 * @param categoryId
	 * @param supplierTypeRelateId
	 * @return
	 */
	List<String>  findSupplierIdByCategoryId(@Param("categoryId")String categoryId);
	
	/**
	 * 
	 * Description:查询注册供应商选择了多少个产品类别
	 * 
	 * @author Easong
	 * @version 2017年6月28日
	 * @param supplierId
	 * @return
	 */
	Integer selectRegSupCateCount(@Param("supplierId") String supplierId);
	/**
	 * 
	 * Description:查询供应商的 类型
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-4
	 * @param supplierId
	 * @return
	 */
	List<String> findSupplierTypeBySupplierId(@Param("supplierId")String supplierId);

	/**
	 *
	 * Description:查询供应商选择的小类节点
	 *
	 * @author Easong
	 * @version 2017/7/6
	 * @param supplierId
	 * @since JDK1.7
	 */
	List<SupplierItem> selectRegSupCateOfLastNode(@Param("supplierId")String supplierId);

	/**
	 *
	 * Description:查询供应商审核通过的产品类别
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param map
	 * @since JDK1.7
	 */
	List<String> findPassSupplierTypeBySupplierId(Map<String,Object> map);

	/**
	 *
	 * Description:查询供应商审核通过的产品类别列表
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param map
	 * @since JDK1.7
	 */
	List<SupplierItem> selectPassItemByCond(Map<String,Object> map);

    List<String> selectCountBySupType(Map<String,Object> map);
}