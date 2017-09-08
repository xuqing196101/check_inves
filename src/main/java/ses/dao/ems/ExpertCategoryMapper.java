package ses.dao.ems;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.ems.ExpertCategory;
import ses.model.sms.SupplierCateTree;

public interface ExpertCategoryMapper {
    int insert(ExpertCategory record);

    int insertSelective(ExpertCategory record);
    /**
     * 
      * @Title: selectListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月28日 上午10:24:33  
      * @Description: TODO 根据专家id查询中间表集合
      * @param @param expertId
      * @param @return      
      * @return List<ExpertCategory>
     */
    List<ExpertCategory> selectListByExpertId(String expertId, String typeId, String level);
    /**
     * 
      * @Title: deleteByExpertId
      * @author ShaoYangYang
      * @date 2016年9月28日 下午6:37:48  
      * @Description: TODO 根据专家id删除数据
      * @param @param expertId      
      * @return void
     */
    void deleteByExpertId(String expertId);
    
    /**
     *〈简述〉
     * 根据专家id和品目id删除
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     */
    void deleteByMap(Map<String, Object> map);
    
    /**
     * 
     *〈简述〉根据专家Id和品目Id查询 ExpertCategory
     *〈详细描述〉
     * @author myc
     * @param expertId 专家Id
     * @param categoryId 品目Id
     * @return
     */
    ExpertCategory getCategoryByExpertId(@Param("expertId")String expertId, @Param("categoryId")String categoryId );

	List<ExpertCategory> findByExpertId(String expertId);

	/**
	 * 树删除非选中节点
	 * @param expertId
	 * @param list
	 */
	void delNoTree(Map<String, Object> map);

	List<ExpertCategory> selectListByExpertId1(String expertId, String typeId);

	List<ExpertCategory> findEnginId(Map<String, Object> map);
	
	
	
	List<ExpertCategory> getCategory(@Param("expertId")String expertId, @Param("categoryId")String categoryId,@Param("typeId")String typeId );
	
	/**
	 * 
	 * Description:根据TYPE_ID查询专家所属各类型数量：
  	 * 1、物资技术  2、工程技术 3、服务技术 4、物资服务经济 5、工程经济
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @param typeId
	 * @return
	 */
	BigDecimal selectExpertCountByCategory(@Param("typeId") String typeId);

	/**
	 * 
	 * Description: 查询专家注册品目类型（小类）的数量 
	 * 
	 * @author Easong
	 * @version 2017年6月27日
	 * @param expertId
	 * @return
	 */
	Integer selectRegExpCateCount(@Param("expertId") String expertId);

	/**
	 *
	 * Description:查询专家审核通过的类型
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param  map
	 * @since JDK1.7
	 */

	List<String> selectCateByExpertId(Map<String,Object> map);
	/**
	 *
	 * Description:查询专家审核通过的类型
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param  map
	 * @since JDK1.7
	 */
	List<ExpertCategory> selectPassCateByExpertId(Map<String,Object> map);
	/**
	 *
	 * Description:查询专家审核通过的数量
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param  map
	 * @since JDK1.7
	 */
	List<ExpertCategory> selectPassCount(Map<String,Object> map);
	
	/**
	 * 更新审核状态 
	 * @param expertCategory
	 */
    void updateAuditStatus(ExpertCategory expertCategory);
    
    /**
     * 
     * Description: 查询专家所有参评类别
     * 
     * @author zhang shubin
     * @data 2017年8月23日
     * @param 
     * @return
     */
    List<SupplierCateTree> findExpertCatrgory(@Param("expertId")String expertId,@Param("typeId")String typeId);
    
    /**
     * 
     * Description: 查询父节点的数量（带本身）
     * 
     * @author zhang shubin
     * @data 2017年8月23日
     * @param 
     * @return
     */
    Integer findCountParent(Map<String,Object> map);
}