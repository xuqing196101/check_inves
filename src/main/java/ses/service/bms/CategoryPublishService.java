package ses.service.bms;

import java.util.List;

import ses.model.bms.CategoryTree;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  品目发布参数
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface CategoryPublishService {

    
    /**
     * 
     *〈简述〉
     *  初始化tree
     *〈详细描述〉
     * @author myc
     * @param treeId 父级Id
     * @return
     */
    public List<CategoryTree> initTree(String treeId);

    /**
     * 
     *〈简述〉
     * 发布
     *〈详细描述〉
     * @author myc
     * @param ids 选择的所有Id
     * @return 成功返回ok,失败返回提示信息
     */
    public String publish(String ids);
    
    /**
     * 
    * @Title: initTree 
    * @Description: 门户技术参数库显示
    * @author Easong
    * @param @param treeId
    * @param @return    设定文件 
    * @return List<CategoryTree>    返回类型 
    * @throws
     */
    public List<CategoryTree> initTreeIndex(String treeId);
}
