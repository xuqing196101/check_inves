package ses.service.bms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ses.formbean.ResponseBean;
import ses.model.bms.CategoryParameter;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *   参数管service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryParameterService {

    /**
     * 
     *〈简述〉
     * 按登陆人的所属需求部门进行初始化品目tree
     *〈详细描述〉
     * @author myc
     * @return CategoryTree 对象集合
     */
    public List<CategoryTree> initTree(HttpServletRequest request);
    
    
    /**
     * 
     *〈简述〉
     * 初始化产品数据类型
     *〈详细描述〉
     * @author myc
     * @return DictionaryData集合
     */
    public List<DictionaryData> initTypes();

    /**
     * 
     *〈简述〉
     * 保存参数
     *〈详细描述〉
     * @author myc
     * @param name 参数名称
     * @param type 参数类型
     * @param orgId 组织机构Id
     * @param cateId 品目Id
     * @param id 主键
     * @return
     */
    public ResponseBean saveParameter(String name, String type, String orgId, String cateId , String id);

    /**
     * 
     *〈简述〉
     *  获取品目参数
     *〈详细描述〉
     * @author myc
     * @param cateId 品目Id
     * @return
     */
    public List<CategoryParameter> getParamsByCateId(String cateId);
    
    
    /**
     * 
     *〈简述〉
     * 根据品目id查询参数
     *〈详细描述〉
     * @author myc
     * @param itemId
     * @return
     */
    public List<CategoryParameter> getParametersByItemId(String itemId);

    /**
     * 
     *〈简述〉
     *  根据id查询CategoryParameter 对象
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return
     */
    public CategoryParameter findById(String id);

    /**
     * 
     *〈简述〉
     * 根据Id删除所选择的参数项
     *〈详细描述〉
     * @author myc
     * @param ids 选中的所有id
     * @return 成功返回ok
     */
    public String deleteParamters(String ids);
}
