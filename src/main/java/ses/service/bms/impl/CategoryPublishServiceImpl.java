package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.StaticVariables;
import ses.dao.bms.CategoryMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.service.bms.CategoryPublishService;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  参数发布
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class CategoryPublishServiceImpl implements CategoryPublishService {

    /** 数据字典service */
    @Autowired
    private DictionaryDataServiceI directionService;
    
    /** 品目Mapper */
    @Autowired
    private CategoryMapper categoryMapper;
    
    /** 品目service **/
    @Autowired
    private CategoryService categoryService;
    
    /** 注册 SqlSessionFactory */
    @Autowired
    private SqlSessionFactory sqlSessionFactory; 
    
    /** 根目录 */
    private static final String ROOT_PID = "0";
    
    /** 字典类型-品目根节点 */
    private static final String  KIND_TYPE = "6";
    
    /** 物资类编码 */
    private static final String GOODS_CODE = "GOODS";
    
    /** 提交事物阈值 */
    private static final int COUNT_COMMIT = 10;
    
    
    /**
     * 
     * @see ses.service.bms.CategoryPublishService#initTree(java.lang.String)
     */
    @Override
    public List<CategoryTree> initTree(String treeId) {
        List<CategoryTree> list = new ArrayList<CategoryTree>();
        if (StringUtils.isNotBlank(treeId)){
            List<Category> treeList = categoryMapper.findTreeByStatus(treeId,StaticVariables.CATEGORY_AUDIT_STATUS);
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
    * @Title: initTreeIndex 
    * @Description: 门户技术参数库显示
    * @author Easong
    * @param @param treeId
    * @param @return    设定文件 
    * @throws
     */
    @Override
    public List<CategoryTree> initTreeIndex(String treeId) {
        List<CategoryTree> list = new ArrayList<CategoryTree>();
        if (StringUtils.isNotBlank(treeId)){
            List<Category> treeList = categoryMapper.findTreeByStatusIndex(treeId,StaticVariables.CATEGORY_PUBLISH_STATUS);
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
     * @see ses.service.bms.CategoryPublishService#publish(java.lang.String)
     */
    @Override
    public String publish(String ids) {
        
        String res = StaticVariables.SUCCESS;
        List<String> list = new ArrayList<String>();
        if (StringUtils.isNotBlank(ids)){
            if (ids.contains(StaticVariables.COMMA_SPLLIT)){
                String [] idArray = ids.split(StaticVariables.COMMA_SPLLIT);
                list.addAll(Arrays.asList(idArray));
            } else {
                list.add(ids);
            }
        }
        
        SqlSession batchSqlSession = null;
        try {
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int count = 0;
            for (String id : list){
                Category category = categoryMapper.selectByPrimaryKey(id);
                if (category != null){
                    category.setParamStatus(StaticVariables.CATEGORY_PUBLISH_STATUS);
                    count ++;
                    batchSqlSession.getMapper(CategoryMapper.class).updateByPrimaryKeySelective(category);
                    if (count % COUNT_COMMIT == 0) {
                        batchSqlSession.commit();
                    }
                }
            }
            batchSqlSession.commit();
        } catch (Exception e) {
            res = StaticVariables.FAILED;
            e.printStackTrace();
        }finally{
            if (batchSqlSession != null){
                batchSqlSession.close();
            }
        }
        return res;
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
