package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.StaticVariables;

import ses.dao.bms.CategoryParameterMapper;
import ses.formbean.ResponseBean;
import ses.model.bms.Category;
import ses.model.bms.CategoryParameter;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.service.bms.CategoryParameterService;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.StringUtil;

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
    /** 字典类型-品目根节点 */
    private static final String  KIND_TYPE = "6";
    /** 字典类型-小类别 */
    private static final String  KIND_SMALL_TYPE = "8";
    /** 字典类型-品目参数类型 */
    private static final String KIND_PARAM_TYPE = "14";
    /** 分隔符 */
    private static final String SPLIT_SYMBOL = ",";
    /** 未删除状态 */
    private static final Integer UNDELETED_STATUS = 0;
    /** 物资类编码 */
    private static final String GOODS_CODE = "GOODS";
    
    /** 提交操作 **/
    private static final String OPERA_SUBMIT = "提交";
    
    /** 产品参数管理 */
    @Autowired
    private CategoryParameterMapper cateParamterMapper;
    
    /** 数据字典 */
    @Autowired
    private DictionaryDataServiceI directionService;
    
    /** 品目service */
    @Autowired
    private CategoryService categoryService;
    
    /** 注册 SqlSessionFactory */
    @Autowired
    private SqlSessionFactory sqlSessionFactory; 
    
    /** 提交事物阈值 */
    private static final int COUNT_COMMIT = 10;
    /** 失败标识**/
    private static final String IS_FAILED = "failed";
    /** 成功标识**/
    private static final String IS_OK = "ok";
    /** 为空校验 **/
    private static final String ISNULL_MSG = "参数名称不能为空";
    /** 错误替提示消息 */
    private static final String ERROR_MSG = "参数名称最大只能输入100汉字";
    
    
    
    /**
     * 
     * @see ses.service.bms.CategoryParameterService#initTree()
     */
    @Override
    public List<CategoryTree> initTree(HttpServletRequest request) {
        
        List<CategoryTree> treeList = new ArrayList<CategoryTree>();
        String orgId = request.getParameter("orgId");
        
        if (StringUtils.isNotBlank(orgId)) {
            Map<String,String> dupMap = new ConcurrentHashMap<String, String>(); 
            //加载tree
            loadTree(treeList, orgId, dupMap);
            //加载根节点
            loadRootTree(treeList, dupMap);
        }
        return treeList;
    }
    
    
    /**
     * 
     * @see ses.service.bms.CategoryParameterService#initTypes()
     */
    @Override
    public List<DictionaryData> initTypes() {
        
        return directionService.findByKind(KIND_PARAM_TYPE);
    }
    
    
    
    /**
     * 
     * @see ses.service.bms.CategoryParameterService#initSmallTypes()
     */
    @Override
    public List<DictionaryData> initSmallTypes() {
        
        return directionService.findByKind(KIND_SMALL_TYPE);
    }


    /**
     * 
     * @see ses.service.bms.CategoryParameterService#saveParameter
     *          (java.lang.String, java.lang.String, 
     *           java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ResponseBean saveParameter(String name, String type, String orgId, 
                                        String cateId , String id) {
        
        ResponseBean res = new ResponseBean();
        
        if (!StringUtils.isNotBlank(name)) {
            res.setResult(false);
            res.setErrorMsg(ISNULL_MSG);
            return res ;
        }
        
        if (!StringUtil.validateStrByLength(name, 200)) {
            res.setResult(false);
            res.setErrorMsg(ERROR_MSG);
            return res;
        }
        
        //编辑
        if (StringUtils.isNotBlank(id)) {
            CategoryParameter cp = cateParamterMapper.getParameterById(id);
            if (cp != null) {
                cp.setParamName(name);
                cp.setParamTypeId(type);
                cp.setUpdatedAt(new Date());
                cateParamterMapper.update(cp);
                res.setResult(true);
            }
            
        //新增
        } else {
            CategoryParameter cp = getCategoryParameter(cateId, orgId, name, type);
            cateParamterMapper.saveParameter(cp);
            res.setResult(true);
            res.setObj(cp);
        }
        return res;
    }
    
    
    /**
     * 
     * @see ses.service.bms.CategoryParameterService#getParametersByItemId(java.lang.String)
     */
    @Override
    public List<CategoryParameter> getParametersByItemId(String itemId) {
        
        return cateParamterMapper.getParamsByCateId(itemId);
    }

    
    /**
     * 
     * @see ses.service.bms.CategoryParameterService#findById(java.lang.String)
     */
    @Override
    public CategoryParameter findById(String id) {
        
        CategoryParameter category = cateParamterMapper.getParameterById(id);
        if (category != null){
            return category;
        }
        return new CategoryParameter();
    }


    /**
     * 
     * @see ses.service.bms.CategoryParameterService#getParamsByCateId(java.lang.String)
     */
    @Override
    public List<CategoryParameter> getParamsByCateId(String cateId) {
        
        List<CategoryParameter> list = cateParamterMapper.getParamsByCateId(cateId);
        List<CategoryParameter> cpList = new ArrayList<CategoryParameter>();
        if (list != null && list.size() > 0) {
            for (CategoryParameter cp: list){
                DictionaryData  dict = directionService.getDictionaryData(cp.getParamTypeId());
                if (dict != null) {
                    cp.setParamTypeName(dict.getName());
                }
                cpList.add(cp);
            }
            return cpList;
        }
        return new ArrayList<CategoryParameter>();
    }
    
    
    /**
     * 
     * @see ses.service.bms.CategoryParameterService#deleteParamters(java.lang.String)
     */
    @Override
    public String deleteParamters(String ids) {
        if (StringUtils.isNotBlank(ids)){
            if (ids.contains(SPLIT_SYMBOL)){
                boolean flag = batchUpdate(ids);
                if (!flag){
                    return IS_FAILED;
                }
            } else {
                CategoryParameter cp = cateParamterMapper.getParameterById(ids);
                if (cp != null) {
                    cp.setIsDeleted(1);
                    cp.setUpdatedAt(new Date());
                    cateParamterMapper.update(cp);
                }
            }
            return IS_OK;
        }
        return IS_FAILED;
    }


    /**
     * 
     *〈简述〉
     * 批量更新
     *〈详细描述〉
     * @author myc
     * @param ids 主键id集合
     * @return 成功返回true,失败返回false
     */
    public boolean batchUpdate(String ids) {
        
        boolean flag = true;
        String[] idArray = ids.split(SPLIT_SYMBOL);
        SqlSession batchSqlSession = null;
        try {
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int count = 0;
            for (String id : idArray) {
                 CategoryParameter cp = cateParamterMapper.getParameterById(id);
                 if (cp != null) {
                     count ++ ;
                     cp.setIsDeleted(1);
                     cp.setUpdatedAt(new Date());
                     batchSqlSession.getMapper(CategoryParameterMapper.class).update(cp);
                     if (count % COUNT_COMMIT == 0) {
                         batchSqlSession.commit();
                     }
                 }
            }
            batchSqlSession.commit();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            if (batchSqlSession != null){
                batchSqlSession.close();
            }
        }
        return flag;
    }

    
    
    @Override
    public String submit(String open, String classify, String cateId) {
        
        String msg  = StaticVariables.SUCCESS;
        
        if (StringUtils.isBlank(open)){
           return StaticVariables.FAILED;
        }
        
        //校验
        Integer classified = null;
        if (StringUtils.isNotBlank(classify)){
            
            if (classify.contains(StaticVariables.COMMA_SPLLIT)){
                classified = StaticVariables.GOODS_PS_STATUS;
            } 
            
            if (classify.equals(StaticVariables.GOODS_PRODUCT)){
                classified = StaticVariables.GOODS_PRODUCT_STATUS;
            }
            
            if (classify.equals(StaticVariables.GOODS_SALES)){
                classified = StaticVariables.GOODS_SALES_STATUS;
            }
        }
        
        //提交
        if (StringUtils.isNotBlank(cateId)){
            Category category = categoryService.selectByPrimaryKey(cateId);
            if (category != null){
                
                Integer status = category.getParamStatus();
                
                if (status == StaticVariables.CATEGORY_SUBMIT_STATUS){
                    msg = category.getName() + StaticVariables.CATEGORY_SUBMITED_MSG;
                    return msg;
                }
                
                if (status == StaticVariables.CATEGORY_AUDIT_STATUS){
                    msg = StaticVariables.CATEGORY_AUDIT_MSG + OPERA_SUBMIT;
                    return msg;
                }
                
                if (classified != null){
                    category.setClassify(classified);
                }
                category.setParamStatus(StaticVariables.CATEGORY_SUBMIT_STATUS);
                category.setUpdatedAt(new Date());
                categoryService.updateByPrimaryKeySelective(category);
            }
        }
        return msg;
    }


    /**
     * 
     *〈简述〉
     *  封装CategoryParameter 对象
     *〈详细描述〉
     * @author myc
     * @param cateId      品目ID
     * @param orgId       组织机构Id
     * @param paramName   参数名称
     * @param paramTypeId 参数类型Id
     * @return CategoryParameter
     */
    private CategoryParameter getCategoryParameter(String cateId, String orgId,
                    String paramName, String paramTypeId){
        
        CategoryParameter cp = new CategoryParameter();
        cp.setCateId(cateId);
        cp.setOrgId(orgId);
        cp.setIsDeleted(UNDELETED_STATUS);
        cp.setParamName(paramName);
        cp.setCreatedAt(new Date());
        cp.setParamTypeId(paramTypeId);
        return cp;
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
             tree.setPubStatus(cate.getIsPublish());
             tree.setStatus(cate.getParamStatus());
             tree.setAuditDate(cate.getAuditDate());
             tree.setAuditAdvise(cate.getAuditAdvise());
             if (cate.getClassify() != null) {
                 tree.setClassify(cate.getClassify().toString());
             }
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
             if (data.getCode().equals(GOODS_CODE)){
                 tree.setClassify(GOODS_CODE);
             }else {
                 tree.setClassify(null);
             }
             if (!treeList.contains(tree)){
                 if (dupMap.containsKey(data.getId())) {
                     treeList.add(tree);
                 }
             }
        }
    }
}
