package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.service.bms.CategoryAuditService;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  参数审核service实现
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class CategoryAuditServiceImpl implements CategoryAuditService {
    
    
    /** 根目录 */
    private static final String ROOT_PID = "0";
    
    /** 字典类型-品目根节点 */
    private static final String  KIND_TYPE = "6";
    
    /** 物资类编码 */
    private static final String GOODS_CODE = "GOODS";
    
    /** 数据字典service */
    @Autowired
    private DictionaryDataServiceI directionService;
    
    /** 品目service */
    @Autowired
    private CategoryService categoryService;
    
    
    /**
     * 
     * @see ses.service.bms.CategoryAuditService#initTree(String treeId)
     */
    @Override
    public List<CategoryTree> initTree(String treeId) {
        
        List<CategoryTree> list = new ArrayList<CategoryTree>();
        if (StringUtils.isNotBlank(treeId)){
            List<Category> treeList = categoryService.findTreeByPid(treeId);
            for (Category cate : treeList){
                CategoryTree tree = new CategoryTree();
                tree.setId(cate.getId());
                tree.setName(cate.getName());
                tree.setpId(treeId);
                tree.setClassify(cate.getClassify()+"");
                tree.setPubStatus(cate.getIsPublish());
                list.add(tree);
            }
        } else {
            loadRoot(list);
        }
       
        return list;
    }
    
    /**
     * 
     *〈简述〉
     * 加载根节点
     *〈详细描述〉
     * @author myc
     * @param list {@link 对象list}
     */
    private void loadRoot(List<CategoryTree> list){
        
        List<DictionaryData> treeRootList = directionService.findByKind(KIND_TYPE);
        
        for (DictionaryData data : treeRootList) {
            
            CategoryTree tree = new CategoryTree();
            tree.setId(data.getId());
            tree.setName(data.getName());
            tree.setpId(ROOT_PID);
            tree.setIsParent("true");
            if (data.getCode().equals(GOODS_CODE)){
                tree.setClassify(GOODS_CODE);
            }else {
                tree.setClassify(null);
            }
            list.add(tree);
        }
    }

}
