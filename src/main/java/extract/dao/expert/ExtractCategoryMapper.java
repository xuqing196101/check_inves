package extract.dao.expert;

import java.util.List;

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
}
