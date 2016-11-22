package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.CategoryParameterMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.CategoryParameterService;
import ses.service.bms.DictionaryDataServiceI;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  品目参数管理
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class CategoryParameterServiceImpl implements CategoryParameterService {
    
    /** 根目录 */
    private static final String ROOT_PID = "0";
    /** 字典类型-根节点 */
    private static final String  KIND_TYPE = "6";
    
    /** 产品参数管理 */
    @Autowired
    private CategoryParameterMapper cateParamterMapper;
    
    /** 数据字典 */
    @Autowired
    private DictionaryDataServiceI directionService;
    
    /**
     * 
     * @see ses.service.bms.CategoryParameterService#initTree()
     */
    @Override
    public List<CategoryTree> initTree(HttpServletRequest request) {
        
        List<CategoryTree> treeList = new ArrayList<CategoryTree>();
        User user = (User)request.getSession().getAttribute("loginUser");
        if (user != null) {
            if (user.getOrg() == null){
                return treeList;
            }
            
            String orgId = user.getOrg().getId();
            if (StringUtils.isNotBlank(orgId)) {
                Map<String,String> dupMap = new ConcurrentHashMap<String, String>(); 
                //加载tree
                loadTree(treeList, orgId, dupMap);
                //加载根节点
                loadRootTree(treeList, dupMap);
            }
        }
        
        return treeList;
    }

    /**
     * 
     *〈简述〉
     * 加载所有的品目
     *〈详细描述〉
     * @author myc
     * @param treeList {@link CategoryTree}集合
     * @param orgId 组织架构id
     * @param dupMap 去重map
     */
    private void loadTree(List<CategoryTree> treeList, String orgId, Map<String,String> dupMap){
        List<Category> cateList = cateParamterMapper.findCategoryTree(orgId);
        for (Category cate: cateList) {
             CategoryTree tree = new CategoryTree();
             tree.setId(cate.getId());
             tree.setName(cate.getName());
             tree.setpId(cate.getParentId());
             treeList.add(tree);
             dupMap.put(cate.getParentId(),"");
       }
    }
    
    /**
     * 
     *〈简述〉
     * 加载根节点
     *〈详细描述〉
     * @author myc
     * @param treeList
     * @param dupMap
     */
    private void loadRootTree(List<CategoryTree> treeList, Map<String,String> dupMap){
        
        List<DictionaryData> treeRootList = directionService.findByKind(KIND_TYPE);
        for (DictionaryData data : treeRootList) {
             
             CategoryTree tree = new CategoryTree();
             tree.setId(data.getId());
             tree.setName(data.getName());
             tree.setpId(ROOT_PID);
             
             if (!treeList.contains(tree)){
                 if (dupMap.containsKey(data.getId())) {
                     treeList.add(tree);
                 }
             }
        }
    }
}
