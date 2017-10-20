package extract.dao.expert;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import extract.model.expert.ExtractCategory;

/**
 * 
 * Description: 专家抽取品目信息记录表
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExtractCategoryMapper {

    /**
     * 
     * Description: 新增
     * 
     * @author zhang shubin
     * @data 2017年9月11日
     * @param 
     * @return
     */
    int insertSelective(ExtractCategory record);
    
    /**
     * 
     * Description: 根据抽取条件id查询参评类别
     * 
     * @author zhang shubin
     * @data 2017年9月25日
     * @param 
     * @return
     */
    List<String> selByConditionId(@Param("conditionId") String conditionId,@Param("typeId") String typeId);

    /**
     * 
     * Description: 根据条件查询所有信息
     * 
     * @author zhang shubin
     * @data 2017年9月29日
     * @param 
     * @return
     */
    List<ExtractCategory> findAllByConditionId(String conditionId);
    
    ExtractCategory selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(ExtractCategory record);
    
    /**
     * 
     * Description: 条件查询参评类别
     * 
     * @author zhang shubin
     * @data 2017年10月17日
     * @param 
     * @return
     */
    List<String> selByMap(Map<String, Object> map);
}
