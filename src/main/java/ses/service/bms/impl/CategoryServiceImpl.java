package ses.service.bms.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import common.bean.ResBean;
import common.constant.StaticVariables;
import ses.dao.bms.CategoryMapper;
import ses.dao.bms.CategoryQuaMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.QualificationMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryQua;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.CategoryService;
import ses.util.PropertiesUtil;
import ses.util.StringUtil;

/**
 * 
 * <p>Title:CategoryServiceImpl</p>
 * <p>Description: 采购目录管理实现类</p>
 * @author javazxf
 * @date 
 */

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SupplierItemMapper supplierItemMapper;
    
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper;
    
    /** 品目资质关联表 **/
    @Autowired
    private CategoryQuaMapper categoryQuaMapper;
    
    /** 企业资质 **/
    @Autowired
    private QualificationMapper quaMapper;
    
    /** 操作类型 - 添加 */
    private static final String OPERA_ADD = "add";
    /** 操作类型 - 编辑 */
    private static final String OPERA_EDIT = "edit";
    /** 品目名称不能为空 **/
    private static final String CATEGORY_ISNOTNULL = "品目名称不能为空";
    /** 品目名称已经存在 **/
    private static final String CATEGORY_EXIST = "品目名称已经存在";
    /** 序号不能为空 **/
    private static final String CATEGORY_CODE_ISNUOTNUll = "编码不能为空";
    /** 编码已存在 **/
    private static final String CATEGORY_CODE_CODE = "编码已存在";
    /** 上级品目不能为空 **/
    private static final String CATEGORY_PARENT_NAME = "上级品目不能为空";
    /** 最大输入值 **/
    private static final String CATEGORY_MAX_VALUE = "最多只能输入200个汉字";

   
    
    public void insertSelective(Category category) {
        categoryMapper.insertSelective(category);
    }

    public List<Category> findTreeByPid(String id) {
        return categoryMapper.findTreeByPid(id);
    }
    public List<Category> findTreeByPidPublish(String id) {
    	return categoryMapper.findTreeByPidPublish(id);
    }
    
    /**
     * 
     * @see ses.service.bms.CategoryService#findTreeByStatus(java.lang.String, java.lang.Integer)
     */
    @Override
    public List<Category> findTreeByStatus(String id, Integer status) {
        
        return categoryMapper.findTreeByStatus(id, status);
    }
    
    /**
     * 
     * @see ses.service.bms.CategoryService#findPublishTree(java.lang.String, java.lang.Integer)
     */
    @Override
    public List<Category> findPublishTree(String id, Integer status) {
        return categoryMapper.findPublishTree(id, status);
    }

    public void updateByPrimaryKey(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    public Category selectByPrimaryKey(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        return category;
    }
    
    /**
     * 
     * @see ses.service.bms.CategoryService#getCategoryQuaById(java.lang.String)
     */
    @Override
    public Category getCategoryQuaById(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category != null && StringUtils.isNotBlank(category.getId())){
            List<CategoryQua> list = categoryQuaMapper.findList(category.getId());
            String generalIds = "";
            String generalNames = "";
            String profileIds = "";
            String profileNames = "";
            String profileSalesIds = "";
            String profileSalesNames = "";
            for (CategoryQua cq : list){
                if (StringUtils.isNotBlank(cq.getQuaId())){
                    Qualification  qua = quaMapper.getQualification(cq.getQuaId());
                    if (qua != null){
                        
                        if (cq.getQuaType() == StaticVariables.CATEGORY_QUALIFICATION_GENERAL){
                            generalIds += cq.getQuaId() + ",";
                            generalNames += qua.getName() + ",";
                        }
                        
                        if (cq.getQuaType() == StaticVariables.CATEGORY_QUALIFICATION_PROFILE || cq.getQuaType() == StaticVariables.CATEGORY_QUALIFICATION_PROJECT_PROFILE){
                            profileIds += cq.getQuaId() + ",";
                            profileNames += qua.getName() + ",";
                        }
                        
                        //如果是物资销售型资质文件
                        if (cq.getQuaType() == StaticVariables.CATEGORY_QUALIFICATION_SALES_PROFILE){
                            profileSalesIds += cq.getQuaId() + ",";
                            profileSalesNames += qua.getName() + ",";
                        }
                    }
                }
            }
            if (generalIds.contains(StaticVariables.COMMA_SPLLIT)){
                category.setGeneralQuaIds(generalIds.substring(0, generalIds.length() -1));
                category.setGeneralQuaNames(generalNames.substring(0, generalNames.length() -1));
            }
            
            if (profileIds.contains(StaticVariables.COMMA_SPLLIT)){
                category.setProfileQuaIds(profileIds.substring(0, profileIds.length() -1));
                category.setProfileQuaNames(profileNames.substring(0, profileNames.length() -1));
            }
            
            //如果是物资销售型资质文件
            if (profileSalesIds.contains(StaticVariables.COMMA_SPLLIT)){
                category.setProfileSalesQuaIds(profileSalesIds.substring(0, profileSalesIds.length() -1));
                category.setProfileSalesQuaNames(profileSalesNames.substring(0, profileSalesNames.length() -1));
            }
        }
        return category;
    }

    public void updateNameById(String id) {
        categoryMapper.updateNameById(id);
    }

    public int deleteByPrimaryKey(String id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    public void updateByPrimaryKeySelective(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public List<Category> readExcel(Category category) {
        return categoryMapper.readExcel(category);
    }

    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }
    
    
    /**
     * 
     * @see ses.service.bms.CategoryService#saveCategory(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ResBean saveCategory(HttpServletRequest request) {
        
    	String parentName = request.getParameter("parentNameId");
        String name = request.getParameter("name");
        String level = request.getParameter("level");
        String engLevel = request.getParameter("engLevel");
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        String operaType = request.getParameter("opera");
        String desc = request.getParameter("description");
        String generalIds = request.getParameter("generalQuaIds");
        String profileIds = request.getParameter("profileQuaIds");
        //物资销售型文件类型id
        String profileSalesIds = request.getParameter("profileSalesIds");
        String isPublish = request.getParameter("isPublish");
        String classify = request.getParameter("classify");
        //判断是否工程品目
        String isPorject = request.getParameter("isProjectCate");
        Integer isPublished = null;
        Integer classified = null;
        Integer isProjected = null;
        
        Category categoryCode=new Category();
        categoryCode.setCode(code);
    	List<Category> readExcel = categoryMapper.readExcel(categoryCode);
        if (StringUtils.isNotBlank(isPublish)){
            isPublished = Integer.parseInt(isPublish);
        }
        if (StringUtils.isNotBlank(classify)){
            classified = Integer.parseInt(classify);
        }
        if (StringUtils.isNotBlank(isPorject)){
          isProjected = Integer.parseInt(isPorject);
        }
        
        ResBean res = new ResBean();
        if (StringUtils.isEmpty(name)) {
            res.setSuccess(false);
            res.setMsg(CATEGORY_ISNOTNULL);
           return  res;
        }
        
        if (StringUtils.isEmpty(code)) {
            res.setSuccess(false);
            res.setError(CATEGORY_CODE_ISNUOTNUll);
           return  res;
        }
        
       
        if (!StringUtil.validateStrByLength(desc,400)) {
            res.setSuccess(false);
            res.setLenTxt(CATEGORY_MAX_VALUE);
            return  res;
        }
        
        /**
         * 新增
         */
        if (operaType.equals(OPERA_ADD)) {
            	if(readExcel!=null&&readExcel.size()>0){
            		res.setSuccess(false);
                    res.setError(CATEGORY_CODE_CODE);
                   return  res;
            	}
              
        	
            /*Integer count = findByCode(code);
            
            if (count != null && count > 0) {
                res.setSuccess(false);
                res.setMsg(CATEGORY_EXIST);
               return  res;
            }*/
            HashMap<String, Object> map=new HashMap<String, Object>();
            map.put("name", name);
            map.put("pId", request.getParameter("parentId"));
            List<Category> CategoryLists = categoryMapper.readNameAndPid(map);
            if(CategoryLists!=null&&CategoryLists.size()>0){
            	res.setSuccess(false);
                res.setMsg(CATEGORY_EXIST);
               return  res;
            }
            Category category = new Category();
            category.setId(id);
            category.setCode(code);
            category.setLevel(level);
            category.setEngLevel(engLevel);
            category.setParentId(request.getParameter("parentId"));
            category.setName(name);
            category.setStatus(1);
            category.setDescription(desc);
            category.setCreatedAt(new Date());
            category.setIsDeleted(0);
            category.setParamStatus(StaticVariables.CATEGORY_NEW_STATUS);
            if (classified != null){
                category.setClassify(classified);
            }
            if (isPublished != null){
                category.setIsPublish(isPublished);;
            }
            insertSelective(category);
            saveGeneral(id, generalIds);
            saveProfile(id, profileIds, isProjected);
            //保存物资销售型资质文件id
            saveProfileSales(id, profileSalesIds);
            res.setSuccess(true);
        }
        /**
         * 编辑
         */
        if (operaType.equals(OPERA_EDIT)) {
        	Category category = selectByPrimaryKey(id);
        	if(StringUtils.isEmpty(parentName)){
          	  res.setSuccess(false);
                res.setFilePath(CATEGORY_PARENT_NAME);
               return  res;
            }
        	if(!category.getName().equals(name)){
        		HashMap<String, Object> map=new HashMap<String, Object>();
                map.put("name", name);
                map.put("pId", category.getParentId());
                List<Category> CategoryLists = categoryMapper.readNameAndPid(map);
                if(CategoryLists!=null&&CategoryLists.size()>0){
                	res.setSuccess(false);
                    res.setMsg(CATEGORY_EXIST);
                   return  res;
                }
        	}
        	
        	
        	//当前节点的父节点名称修改
        	
        	if(!category.getCode().equals(code)){
        		Category categoryCodeUpdate=new Category();
    			categoryCodeUpdate.setCode(code);
    	    	List<Category> CodeUpdate = categoryMapper.readExcel(categoryCodeUpdate);
    	    	if(CodeUpdate!=null&&CodeUpdate.size()>0){
    		    	res.setSuccess(false);
                    res.setError(CATEGORY_CODE_CODE);
                   return  res;
    		    }
        	}
        	
        	
            
            if (category != null) {
            	Category ParentCategory = selectByPrimaryKey(category.getParentId());
            	ParentCategory.setName(parentName);
                category.setCode(code);
                category.setDescription(desc);
                category.setName(name);
                category.setLevel(level);
                category.setEngLevel(engLevel);
                category.setUpdatedAt(new Date());
                if (classified != null){
                    category.setClassify(classified);
                }
                if (isPublished != null){
                    category.setIsPublish(isPublished);;
                }
                updateByPrimaryKeySelective(ParentCategory);
                updateByPrimaryKeySelective(category);
                delCategoryQua(id);
                saveGeneral(id, generalIds);
                saveProfile(id, profileIds, isProjected);
                //保存物资销售型资质文件id
                saveProfileSales(id, profileSalesIds);
                res.setSuccess(true);
            }
        }
        return res;
    }
    
    /**
     *〈简述〉批量保存物资销售型专业
     *〈详细描述〉
     * @author Ye Maolin
     * @param categorId 品目Id
     * @param profileSalesIds 物资销售型资质Id
     */
    private void saveProfileSales(String categorId, String profileSalesIds){
        if (StringUtils.isNotBlank(profileSalesIds)){
            if (profileSalesIds.contains(StaticVariables.COMMA_SPLLIT)){
                String [] profileArray = profileSalesIds.split(",");
                for (String profileId : profileArray){
                    saveCategoryQua(categorId, profileId, StaticVariables.CATEGORY_QUALIFICATION_SALES_PROFILE);
                }
            } else {
                saveCategoryQua(categorId, profileSalesIds, StaticVariables.CATEGORY_QUALIFICATION_SALES_PROFILE);
            }
        }
    }

    /**
     * 
     *〈简述〉根据品目Id删除品目资质信息
     *〈详细描述〉
     * @author myc
     * @param categoryId 品目Id
     */
    private void delCategoryQua(String categoryId){
        categoryQuaMapper.delQuaByCategoryId(categoryId);
    }
    
    
    /**
     * 
     *〈简述〉批量保存通用品目和资质
     *〈详细描述〉
     * @author myc
     */
    private void saveGeneral(String categorId,String generalIds){
        if (StringUtils.isNotBlank(generalIds)){
            if (generalIds.contains(StaticVariables.COMMA_SPLLIT)){
                String [] generalArray = generalIds.split(",");
                for (String generalId : generalArray){
                    saveCategoryQua(categorId, generalId, StaticVariables.CATEGORY_QUALIFICATION_GENERAL);
                }
            } else {
                saveCategoryQua(categorId, generalIds, StaticVariables.CATEGORY_QUALIFICATION_GENERAL);
            }
        }
    }
    
    /**
     * 
     *〈简述〉批量保存专业
     *〈详细描述〉
     * @author myc 
     * @param categorId 品目Id
     * @param profileIds 资质Id
     * @param isProjected 
     */
    private void saveProfile(String categorId, String profileIds, Integer isProjected){
        if (StringUtils.isNotBlank(profileIds)){
            if (profileIds.contains(StaticVariables.COMMA_SPLLIT)){
                String [] profileArray = profileIds.split(",");
                for (String profileId : profileArray){
                    if (isProjected != null && isProjected == 1) {
                        //如果是工程品目
                        saveCategoryQua(categorId, profileId, StaticVariables.CATEGORY_QUALIFICATION_PROJECT_PROFILE);
                    } else {
                        saveCategoryQua(categorId, profileId, StaticVariables.CATEGORY_QUALIFICATION_PROFILE);
                    }
                }
            } else {
                if (isProjected != null && isProjected == 1) {
                    saveCategoryQua(categorId, profileIds, StaticVariables.CATEGORY_QUALIFICATION_PROJECT_PROFILE);
                } else {
                    saveCategoryQua(categorId, profileIds, StaticVariables.CATEGORY_QUALIFICATION_PROFILE);
                }
            }
        }
    }
    
    /**
     * 
     *〈简述〉保存资质和品目关系
     *〈详细描述〉
     * @author myc
     * @param categorId 品目Id
     * @param generalId 资质Id
     * @param quraType  资质类型
     */
    private void saveCategoryQua(String categorId, String generalId, Integer quraType) {
        CategoryQua cq = new CategoryQua();
        cq.setCategoryId(categorId);
        cq.setQuaId(generalId);
        cq.setQuaType(quraType);
        categoryQuaMapper.save(cq);
    }
    
    /**
     * 
     * @see ses.service.bms.CategoryService#updateStatus(java.lang.Integer, java.lang.String)
     */
    @Override
    public String updateStatus(Integer status, String categoryId) {
            
        Category  category = selectByPrimaryKey(categoryId);
        if (category != null){
            category.setParamStatus(status);
            category.setUpdatedAt(new Date());
            updateByPrimaryKeySelective(category);
            return StaticVariables.SUCCESS;
        }
        return StaticVariables.FAILED;
    }

    
    /**
     * 
     * @see ses.service.bms.CategoryService#estimate(java.lang.String)
     */
    @Override
    public String estimate(String id, String opera,String stepMsg ,Integer status) {
        String msg = StaticVariables.SUCCESS;
        Category cate = selectByPrimaryKey(id);
        if (cate != null && cate.getParamStatus() != null){
            if (cate.getParamStatus() >= status){
                msg = cate.getName() + stepMsg;
                msg = msg + getOperaStatusMsg(opera);
            } 
        }
        return msg;
    }

    /**
     * 
     * @see ses.service.bms.CategoryService#listByKeyword(java.util.Map)
     */
    @Override
    public List<Category> listByKeyword(Map<String, Object> map) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
        return categoryMapper.listByKeyword(map);
    }
    
    @Override
    public List<Category> listByKeyname(Map<String, Object> map) {
        return categoryMapper.listByKeyword(map);
    }
    
    @Override
    public List<Category> listByParent(String pid) {
        // TODO Auto-generated method stub
        return categoryMapper.findTreeByPid(pid);
    }

    /**
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @return
     */
    public Category findById(String id) {
        // TODO Auto-generated method stub
        return categoryMapper.findById(id);
    }
    
    @Override
    public List<Category> findByParentId(String parentId) {
    	// TODO Auto-generated method stub
    	return categoryMapper.findByParentId(parentId);
    }

    /**
     * @Title: findCategoryByType
     * @author: Wang Zhaohua
     * @date: 2016-10-3 下午4:12:18
     * @Description: 根据类型查询
     * @param: @param category
     * @param: @return
     * @return: List<Category>
     */
    @Override
    public List<SupplierTypeTree> findCategoryByType(Category category, String supplierId) {
        String kind = category.getKind();
        if (kind != null && !"".equals(kind)) {
            category.setKind("%" + kind + "%");
        }
        List<Category> listCategorys = categoryMapper.findCategoryByType(category);

        // 查询供应商勾选品目类型
        SupplierItem st = new SupplierItem();
        st.setSupplierId(supplierId);
        st.setSupplierTypeRelateId(kind);
        List<SupplierItem> listSupplierItems = supplierItemMapper.findSupplierItemBySupplierIdAndType(st);
        List<String> listCategoryIds = new ArrayList<String>();
        for(SupplierItem supplierItem : listSupplierItems) {
            listCategoryIds.add(supplierItem.getCategoryId());
        }

        List<SupplierTypeTree> listSupplierTypeTrees = new ArrayList<SupplierTypeTree>();
        for (Category c : listCategorys) {
            SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
            supplierTypeTree.setId(c.getId());
            supplierTypeTree.setParentId(c.getParentId());
            supplierTypeTree.setName(c.getName());
            if (listCategoryIds.contains(c.getId())) {
                supplierTypeTree.setChecked(true);
            }
            listSupplierTypeTrees.add(supplierTypeTree);
        }
        return listSupplierTypeTrees;
    }
    @Override
    public List<SupplierTypeTree> findCategoryByTypeAndDisabled(Category category, String supplierId) {
        List<Category> listCategorys = categoryMapper.findCategoryByType(category);

        // 查询供应商勾选品目类型
        List<SupplierItem> listSupplierItems = supplierItemMapper.findSupplierItemBySupplierId(supplierId);
        List<String> listCategoryIds = new ArrayList<String>();
        for(SupplierItem supplierItem : listSupplierItems) {
            listCategoryIds.add(supplierItem.getCategoryId());
        }

        List<SupplierTypeTree> listSupplierTypeTrees = new ArrayList<SupplierTypeTree>();
        Set<String> setList = new HashSet<String>();
        for (Category c : listCategorys) {
            SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
            if (listCategoryIds.contains(c.getId())) {
                supplierTypeTree.setId(c.getId());
                supplierTypeTree.setParentId(c.getParentId());
                supplierTypeTree.setName(c.getName());
                listSupplierTypeTrees.add(supplierTypeTree);
                setList.add(c.getParentId());
            }
        }
        DictionaryData data=new DictionaryData();
        data.setKind(6);
        List<DictionaryData> listByPage = dictionaryDataMapper.findList(data);
        for (DictionaryData dictionaryData : listByPage) {
            SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
            if (setList.contains(dictionaryData.getId())){
                supplierTypeTree.setId(dictionaryData.getId());
                supplierTypeTree.setName(dictionaryData.getName());
                supplierTypeTree.setParent(true);
                listSupplierTypeTrees.add(supplierTypeTree);
            }
        }
        return listSupplierTypeTrees;
    }

    @Override
    public List<SupplierTypeTree> queryCategory(Category category,List<String> listCategoryIds ,Integer type) {
        List<SupplierTypeTree> listSupplierTypeTrees = new ArrayList<SupplierTypeTree>();
        if (type ==1) {
            DictionaryData data=new DictionaryData();
            data.setKind(6);
            List<DictionaryData> listByPage = dictionaryDataMapper.findList(data);
            for (DictionaryData dictionaryData : listByPage) {
                SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
                supplierTypeTree.setId(dictionaryData.getId());
                supplierTypeTree.setName(dictionaryData.getName());
                supplierTypeTree.setParent(true);
                listSupplierTypeTrees.add(supplierTypeTree);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            List<Category> categoryList = categoryMapper.findCategory(map);
            if (categoryList != null && categoryList.size() > 0) {
                for (Category catge : categoryList) {
                    SupplierTypeTree supplierTypeTree1 = new SupplierTypeTree();
                    if (listCategoryIds.contains(catge.getId())){
                        supplierTypeTree1.setChecked(true);
                    }
                    supplierTypeTree1.setId(catge.getId());
                    supplierTypeTree1.setParentId(catge.getParentId());
                    supplierTypeTree1.setName(catge.getName());
                    listSupplierTypeTrees.add(supplierTypeTree1);
                }
            }
            return listSupplierTypeTrees;
        }
        if (type ==0){
            if (category.getId() == null && listCategoryIds.size() == 0) {
                DictionaryData data=new DictionaryData();
                data.setKind(6);
                List<DictionaryData> listByPage = dictionaryDataMapper.findList(data);
                for (DictionaryData dictionaryData : listByPage) {
                    SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
                    supplierTypeTree.setId(dictionaryData.getId());
                    supplierTypeTree.setName(dictionaryData.getName());
                    supplierTypeTree.setParent(true);
                    listSupplierTypeTrees.add(supplierTypeTree);
                }
            } else if(category.getId() != null && !"".equals(category.getId()) ){
                List<Category> categoryList = categoryMapper.findTreeByPid(category.getId());
                if (categoryList != null && categoryList.size() > 0) {
                    for (Category catge : categoryList) {
                        SupplierTypeTree supplierTypeTree1 = new SupplierTypeTree();
                        if (listCategoryIds.contains(catge.getId())){
                            supplierTypeTree1.setChecked(true);
                        }
                        supplierTypeTree1.setId(catge.getId());
                        supplierTypeTree1.setName(catge.getName());
                        listSupplierTypeTrees.add(supplierTypeTree1);
                    }
                }
            } else if(category.getId() == null && listCategoryIds != null) {
                if (listCategoryIds.size() > 0) {
                    Set<String> setList = new HashSet<String>();
                    for (String string : listCategoryIds) {
                        String pid = categoryMapper.selectByPrimaryKey(string).getParentId();
                        setList.add(pid);
                    }
                    //加入父节点
                    DictionaryData data=new DictionaryData();
                    data.setKind(6);
                    List<DictionaryData> listByPage = dictionaryDataMapper.findList(data);
                    for (DictionaryData dictionaryData : listByPage) {
                        SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
                        supplierTypeTree.setId(dictionaryData.getId());
                        supplierTypeTree.setName(dictionaryData.getName());
                        supplierTypeTree.setParent(true);
                        listSupplierTypeTrees.add(supplierTypeTree);
                    }
                    for (String string : setList) {
                        //给子节点加入进来
                        List<Category> categoryList = categoryMapper.findTreeByPid(string);
                        if (categoryList != null && categoryList.size() > 0) {
                            for (Category catge : categoryList) {
                                SupplierTypeTree supplierTypeTree1 = new SupplierTypeTree();
                                if (listCategoryIds.contains(catge.getId())){
                                    supplierTypeTree1.setChecked(true);
                                }
                                supplierTypeTree1.setId(catge.getId());
                                supplierTypeTree1.setParentId(catge.getParentId());
                                supplierTypeTree1.setName(catge.getName());
                                listSupplierTypeTrees.add(supplierTypeTree1);
                            }
                        }
                    }
                }
            }
        }
        return listSupplierTypeTrees;
    }

    @Override
    public BigDecimal checkName(String name) {
        // TODO Auto-generated method stub
        return categoryMapper.checkName(name);
    }

    @Override
    public List<Category> listByParamstatus(Map<String, Integer> map) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
        return categoryMapper.listByParamstatus(map);
    }



    @Override
    public List<Category> findByStatus( Map<String, Object> map) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
        return categoryMapper.findByStatus();
    }

    @Override
    public List<Category> findByOrgId(String id) {
        // TODO Auto-generated method stub
        return	categoryMapper.findByOrgId(id);
    }

    @Override
    public List<Category> listByCateogryName(Map<String, Object> map) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
        return categoryMapper.listByCateogryName(map);
    }

    /**
     * 
     *〈简述〉逻辑删除节点以及节点下的子节点
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param Id 节点id
     */
    public void deleted(String Id){
        //根据父节点找出子节点 
        List<Category>   list = new  ArrayList<Category>();
//        list=categoryMapper.findTreeByPid(Id);
        Category e=new Category();
        e.setId(Id);
        list.add(e);
        categoryMapper.deleted(list);
    }
    
    public Integer findByCode(String code){
       return  categoryMapper.findByCode(code);
    }
    
    /**
     * 
     *〈简述〉
     *  根据操作类型获取对应的操作提示
     *〈详细描述〉
     * @author myc
     * @param opera 操作类型
     * @return 操作类型对应的提示
     */
    private String getOperaStatusMsg(String opera){
        String msg = "";
        switch(opera){
            case StaticVariables.OPER_ADD_TYPE: 
                msg = StaticVariables.OPER_ADD_MSG;  break;
            case StaticVariables.OPER_EDIT_TYPE: 
                msg = StaticVariables.OPER_EDIT_MSG;  break;
            case StaticVariables.OPER_DEL_TYPE:
                msg = StaticVariables.OPER_DEL_MSG;   break;
            case StaticVariables.OPER_CANCEL_TYPE:
                msg = StaticVariables.OPER_CANCEL_MSG; break;
        }
        return msg;
    }

	@Override
	public List<Category> findCategory(Map<String, Object> map,Integer page) {
		List<Category> list = categoryMapper.findCategory(map);
		PageHelper.startPage(page,30);
		return list;
	}


	@Override
	public List<Category> findCategoryByStatusAll(Integer status) {
		return categoryMapper.findCategoryByStatusAll(status);
	}

	@Override
	public List<Category> findCategoryByParentNode(HashMap<String, Object> map) {
		return categoryMapper.findCategoryByParentNode(map);
	}

	@Override
	public List<Category> findCategoryByChildren(HashMap<String, Object> map) {
		
		return categoryMapper.findCategoryByChildren(map);
	}

	@Override
	public List<Category> findCategoryByName(HashMap<String, Object> map) {
		List<Category> Category = categoryMapper.findCategoryByName(map);
		
		for(Category cate:Category){
			List<CategoryQua> list = categoryQuaMapper.findList(cate.getId());
			String generalQuaNames="";
			if(list!=null&&list.size()>0){
				for (CategoryQua cq : list){
					 Qualification  qua = quaMapper.getQualification(cq.getQuaId());
					 if (qua != null){
						 generalQuaNames+=qua.getName()+",";
					 }
				}
				cate.setGeneralQuaNames(generalQuaNames);
			}
			
		}
			 
		
		 
		return Category;
	}

	@Override
	public List<Category> findCategoryByNameOrClassify(
			HashMap<String, Object> map) {
		return categoryMapper.findCategoryByNameOrClassify(map);
	}

	@Override
	public List<Category> findTreeByPidAndName(HashMap<String, Object> map) {
		
		return categoryMapper.findTreeByPidAndName(map);
	}

	@Override
	public List<Category> readNameAndPid(HashMap<String, Object> map) {
		
		return categoryMapper.readNameAndPid(map);
	}

	@Override
	public List<Category> searchByNameAndCode(String name, String code,
			Integer ispublish) {
		
		return categoryMapper.searchByNameAndCode(name, code, ispublish);
	}

	@Override
	public List<Category> findCategoryByChildrenAndWuZi(
			HashMap<String, Object> map) {
		
		return categoryMapper.findCategoryByChildrenAndWuZi(map);
	}

	@Override
	public List<Category> selectByCode(String code) {
		return categoryMapper.selectByCode(code);
	}

	

}
