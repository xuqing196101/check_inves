package ses.service.bms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
}
