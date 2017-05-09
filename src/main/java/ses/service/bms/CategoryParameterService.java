package ses.service.bms;

import java.io.File;
import java.util.Date;
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
    public ResponseBean saveParameter(String name, String type, String orgId, String cateId , String id,Integer paramRequired);

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
     * @param itemId 品目Id
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
     * @return 成功返回ok,否则为failed
     */
    public String deleteParamters(String ids);

    /**
     * 
     *〈简述〉
     *  初始化小类型
     *〈详细描述〉
     * @author myc
     * @return 
     */
    public List<DictionaryData> initSmallTypes();

    /**
     * 
     *〈简述〉
     *  提交参数
     *〈详细描述〉
     * @author myc
     * @param open 是否公开,0:公开,1:不公开
     * @param classify 物资的类型
     * @param cateId 品目ID
     * @return 成功返回ok,否则返回false
     */
    public String submit(String open, String classify, String cateId);
    
    /**
     * 
    * @Title: exportCategoryParamter 
    * @Description: 实现产品目录参数导出外网
    * @author Easong
    * @param @param start
    * @param @param end
    * @param @param synchDate
    * @param @return    设定文件 
    * @return boolean    返回类型 
    * @throws
     */
    public boolean exportCategoryParamter(String start, String end, Date synchDate);
    
    /**
     * 
    * @Title: importCategoryParmter 
    * @Description: 导入产品目录参数信息
    * @author Easong
    * @param @param file
    * @param @return    设定文件 
    * @return boolean    返回类型 
    * @throws
     */
    public boolean importCategoryParmter(File file);
}
