package ses.dao.bms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.Category;
import ses.model.bms.CategoryParameter;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  参数管理持久层
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryParameterMapper {

    /**
     * 
     *〈简述〉
     * 查询品目tree数据
     *〈详细描述〉
     * @author myc
     * @param orgId 需求单位Id
     * @return
     */
    public List<Category> findCategoryTree(String orgId);
    
    /**
     * 
     *〈简述〉
     * 保存
     *〈详细描述〉
     * @author myc
     * @param cp {@link CategoryParameter}
     */
    public void saveParameter(CategoryParameter cp);
    
    /**
     * 
     *〈简述〉
     *  根据品目Id进行删除
     *〈详细描述〉
     * @author myc
     * @param cateId 品目ID
     */
    public void delParameter(String cateId);
    
    
    /**
     * 
     *〈简述〉
     *  根据品目类型ID获取对应的参数
     *〈详细描述〉
     * @author myc
     * @param cateId 类型参数Id
     * @return
     */
    public List<CategoryParameter> getParamsByCateId(String cateId);
    
    /**
     * 
     *〈简述〉
     * 根据主键Id查询CategoryParameter对象
     *〈详细描述〉
     * @author myc
     * @param id {@link java.lang.String}
     * @return
     */
    public CategoryParameter getParameterById(String id);
    
    /**
     * 
     *〈简述〉
     *  更新对象
     *〈详细描述〉
     * @author myc
     * @param cp CategoryParameter 对象
     */
    public void update(CategoryParameter cp);
    
    /**
     * 
     *〈简述〉
     * 根据品目Id查询该品目是否存在参数
     *〈详细描述〉
     * @author myc
     * @param categoryId 品目ID
     * @return 存在返回大于0,不存在返回0
     */
    public Integer exsitByCateId(@Param("cateId")String categoryId);
    
    
    /**
     * 
    * @Title: exportCategoryParam 
    * @Description: 产品目录参数导出外网
    * @author Easong
    * @param @param start
    * @param @param end
    * @param @return    设定文件 
    * @return List<CategoryParameter>    返回类型 
    * @throws
     */
    public List<CategoryParameter> exportCategoryParam(@Param("start")String start,@Param("end")String end);
    
    /**
     * 
    * @Title: insertParameter 
    * @Description: 产品目录参数导入内网
    * @author Easong
    * @param @param cp    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void insertParameter(CategoryParameter cp);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective 
    * @Description: 产品目录参数导内网更新
    * @author Easong
    * @param @param cp    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void updateByPrimaryKeySelective(CategoryParameter cp);
    
}
