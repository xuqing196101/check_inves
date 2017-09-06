package ses.dao.bms;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.CategoryQua;
import ses.model.bms.DictionaryData;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>品目资质Mapper
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryQuaMapper {

    /**
     * 
     *〈简述〉保存质量资质
     *〈详细描述〉
     * @author myc
     * @param categoryQua {@link CategoryQua}
     */
    void save(CategoryQua categoryQua);
    
    /**
     * 
     *〈简述〉根据品目Id查询关联资质信息
     *〈详细描述〉
     * @author myc
     * @param categoryId 品目id
     * @return  
     */
    List<CategoryQua> findList(@Param("categoryId")String categoryId);
    
    /**
     * 
     *〈简述〉根据品目Id查询关联资质信息
     *〈详细描述〉
     * @author myc
     * @param categoryId 品目id
     * @return  
     */
    List<CategoryQua> findListSupplier(@Param("categoryId")String categoryId, Integer type);
    
    /**
     * 
     *〈简述〉根据品目Id删除品目
     *〈详细描述〉
     * @author myc
     * @param categoryId 品目Id
     */
    void updateQuaByCategoryId(Map<String, Object> map);
    /**
	 * 根据更新 时间 获取范围数据
	 * @author YangHongLiang
	 * @param start
	 * @param end
	 * @return
	 */
	List<CategoryQua> selectByUpdatedAt(@Param("start")String start,@Param("end")String end);
	/**
	 * 根据创建  时间 获取范围数据
	 * @author YangHongLiang
	 * @param start
	 * @param end
	 * @return
	 */
	List<CategoryQua> selectByCreatedAt(@Param("start")String start,@Param("end")String end);
	/**
	 * 
	 * Description:添加关联资质信息
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-12
	 * @param qua
	 */
	void insertSelective(CategoryQua qua);
	/**
	 * 
	 * Description:根据id 更新关联资质信息
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-12
	 * @param qua
	 */
	void updateByPrimaryKeySelective(CategoryQua qua);
	/**
	 * 判断是否存在
	 * @param id
	 * @return
	 */
	Integer countByPrimaryKey(String id);

	/**
	 * 查询资质关联信息
	 * @param cq
	 * @return
	 */
	List<CategoryQua> selectCategoryQuaList(CategoryQua cq);

	List<DictionaryData> getEngAptitudeLevelByCategoryId(
			Map<String, String[]> map);
	
}
