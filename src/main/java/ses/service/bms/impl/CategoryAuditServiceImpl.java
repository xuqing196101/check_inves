package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.formbean.ResponseBean;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.service.bms.CategoryAuditService;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.StringUtil;

import common.constant.StaticVariables;

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
            List<Category> treeList = categoryService.findTreeByStatus(treeId,StaticVariables.CATEGORY_SUBMIT_STATUS);
            for (Category cate : treeList){
                CategoryTree tree = new CategoryTree();
                tree.setId(cate.getId());
                tree.setName(cate.getName());
                tree.setpId(treeId);
                List<Category> cList = categoryService.findTreeByPid(cate.getId());
                if (cList != null && cList.size() > 0){
                    tree.setIsParent("true");
                } else {
                    tree.setIsParent("false");
                }
                tree.setClassify(cate.getClassify()+"");
                tree.setPubStatus(cate.getIsPublish());
                tree.setStatus(cate.getParamStatus());
                list.add(tree);
            }
        } else {
            loadRoot(list);
        }
       
        return list;
    }
    
    /**
     * 
     * @see ses.service.bms.CategoryAuditService#audit(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ResponseBean audit(String id, String status, String advise) {
        
        ResponseBean rb = new ResponseBean();
        if (!StringUtils.isNotBlank(status)){
            rb.setResult(false);
            rb.setErrorMsg("审核状态不能为空");
            return rb;
        }
        
        Integer auditStatus = Integer.parseInt(status);
        if (auditStatus == StaticVariables.CATEGORY_ASSIGNED_STATUS){
            if (!StringUtils.isNotBlank(advise)){
                rb.setResult(false);
                rb.setErrorMsg("审核意见不能为空");
                return rb;
            }
        }
        
        if (!StringUtil.validateStrByLength(advise, 200)){
            rb.setResult(false);
            rb.setErrorMsg("审核意见不能超过200个字");
            return rb;
        }
        
        
        if (StringUtils.isNotBlank(id)){
            Category category = categoryService.selectByPrimaryKey(id);
            if (category != null){
                
                if (category.getParamStatus() >= StaticVariables.CATEGORY_AUDIT_STATUS){
                    rb.setResult(false);
                    rb.setErrorMsg(category.getName() + StaticVariables.CATEGORY_AUDIT_MSG + StaticVariables.OPERA_SUBMIT_MSG);
                    return rb;
                    
                } else if (category.getParamStatus() == StaticVariables.CATEGORY_ASSIGNED_STATUS){
                    rb.setResult(false);
                    rb.setErrorMsg(category.getName() + StaticVariables.CATEGORY_REJECT_MSG);
                    return rb;
                } else {
                    category.setParamStatus(auditStatus);
                    category.setAuditDate(new Date());
                    category.setAuditPersonId("");
                    category.setAuditAdvise(advise);
                    categoryService.updateByPrimaryKeySelective(category);
                    rb.setResult(true);
                    rb.setObj(category);
                    return rb;
                }
            }
        }
        return rb;
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
