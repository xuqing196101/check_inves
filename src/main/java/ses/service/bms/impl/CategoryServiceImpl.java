package ses.service.bms.impl;

import java.io.File;
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
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import common.bean.ResBean;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;
import ses.dao.bms.CategoryMapper;
import ses.dao.bms.CategoryQuaMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.QualificationMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryQua;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.CategoryService;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import ses.util.StringUtil;
import ses.util.SupplierToolUtil;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

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
    /**
     * 同步service
     */
    @Autowired
    private SynchRecordService recordService;
    /**文件**/
	@Autowired
    private UploadService uploadService;
	
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
        /*Category ct = categoryMapper.findById(category.getParentId());
        if(ct==null){
          category.setLevel(2);
        }else{
          category.setLevel(ct.getLevel()+1);
          ct.setIsParent("true");
          categoryMapper.updateByPrimaryKeySelective(ct);
        }
        category.setIsParent("false");*/
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
        if (StringUtils.isEmpty(name)||name.trim().length()<=0) {
            res.setSuccess(false);
            res.setMsg(CATEGORY_ISNOTNULL);
           return  res;
        }
        
        if (StringUtils.isEmpty(code)||code.trim().length()<=0) {
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
                return res;
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
            saveGeneral(id, generalIds,new Date());
            saveProfile(id, profileIds, isProjected,new Date());
            //保存物资销售型资质文件id
            saveProfileSales(id, profileSalesIds,new Date());
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
            	Category parentCategory = selectByPrimaryKey(category.getParentId());
            	if(parentCategory!=null){
            		 updateByPrimaryKeySelective(parentCategory);
            	}
            	
                category.setCode(code);
                category.setDescription(desc);
                category.setName(name);
                category.setEngLevel(engLevel);
                category.setUpdatedAt(new Date());
                if (classified != null){
                    category.setClassify(classified);
                }
                if (isPublished != null){
                    category.setIsPublish(isPublished);;
                }
               
                updateByPrimaryKeySelective(category);
                delCategoryQua(id,new Date());
                saveGeneral(id, generalIds,new Date());
                saveProfile(id, profileIds, isProjected,new Date());
                //保存物资销售型资质文件id
                saveProfileSales(id, profileSalesIds,new Date());
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
    private void saveProfileSales(String categorId, String profileSalesIds,Date date){
        if (StringUtils.isNotBlank(profileSalesIds)){
            if (profileSalesIds.contains(StaticVariables.COMMA_SPLLIT)){
                String [] profileArray = profileSalesIds.split(",");
                for (String profileId : profileArray){
                    saveCategoryQua(categorId, profileId, StaticVariables.CATEGORY_QUALIFICATION_SALES_PROFILE,date);
                }
            } else {
                saveCategoryQua(categorId, profileSalesIds, StaticVariables.CATEGORY_QUALIFICATION_SALES_PROFILE,date);
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
    private void delCategoryQua(String categoryId,Date date){
    	HashMap<String,Object> map=new HashMap<>();
    	map.put("categoryId", categoryId);
    	map.put("updateDate", date);
        categoryQuaMapper.updateQuaByCategoryId(map);
    }
    
    
    /**
     * 
     *〈简述〉批量保存通用品目和资质
     *〈详细描述〉
     * @author myc
     */
    private void saveGeneral(String categorId,String generalIds,Date date){
        if (StringUtils.isNotBlank(generalIds)){
            if (generalIds.contains(StaticVariables.COMMA_SPLLIT)){
                String [] generalArray = generalIds.split(",");
                for (String generalId : generalArray){
                    saveCategoryQua(categorId, generalId, StaticVariables.CATEGORY_QUALIFICATION_GENERAL,date);
                }
            } else {
                saveCategoryQua(categorId, generalIds, StaticVariables.CATEGORY_QUALIFICATION_GENERAL,date);
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
    private void saveProfile(String categorId, String profileIds, Integer isProjected,Date date){
        if (StringUtils.isNotBlank(profileIds)){
            if (profileIds.contains(StaticVariables.COMMA_SPLLIT)){
                String [] profileArray = profileIds.split(",");
                for (String profileId : profileArray){
                    if (isProjected != null && isProjected == 1) {
                        //如果是工程品目
                        saveCategoryQua(categorId, profileId, StaticVariables.CATEGORY_QUALIFICATION_PROJECT_PROFILE,date);
                    } else {
                        saveCategoryQua(categorId, profileId, StaticVariables.CATEGORY_QUALIFICATION_PROFILE,date);
                    }
                }
            } else {
                if (isProjected != null && isProjected == 1) {
                    saveCategoryQua(categorId, profileIds, StaticVariables.CATEGORY_QUALIFICATION_PROJECT_PROFILE,date);
                } else {
                    saveCategoryQua(categorId, profileIds, StaticVariables.CATEGORY_QUALIFICATION_PROFILE,date);
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
    private void saveCategoryQua(String categorId, String generalId, Integer quraType,Date date) {
        CategoryQua cq = new CategoryQua();
        cq.setCategoryId(categorId);
        cq.setQuaId(generalId);
        cq.setQuaType(quraType);
        cq.setCreatedAt(date);
        cq.setIsDeleted(StaticVariables.ISNOT_DELETED);
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
		List<Category> category = categoryMapper.findCategoryByName(map);
		
		for(Category cate:category){
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
		
		return category;
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

	@Override
	public List<Category> findTreeByPidIsPublish(String pid) {
		// TODO Auto-generated method stub
		return categoryMapper.findTreeByPidIsPublish(pid);
	}
    /**
     * 实现 导出 产品目录
     */
	@Override
	public boolean exportCategory(String start, String end, Date synchDate) {
	  //根据时间获取创建 数据范围
	  List<Category> createList=categoryMapper.selectByCreatedAt(start, end);
	  List<Category> updateList=categoryMapper.selectByUpdatedAt(start, end);
	  List<Category> list=new ArrayList<>();
	  //上传文件 集合
	  List<UploadFile> uploadList=new ArrayList<>();
	  if(createList!=null  && createList.size()>0){
		  for (Category create : createList) {
			//查询文件路径
			List<UploadFile> fileList = uploadService.findBybusinessId(create.getId(),Constant.TENDER_SYS_KEY);
			uploadList.addAll(fileList);
		 }
		  list.addAll(createList);
	  }
	  if(updateList!=null  && updateList.size()>0){
		  for (Category upload : updateList) {
			//查询文件路径
				List<UploadFile> fileList = uploadService.findBybusinessId(upload.getId(),Constant.TENDER_SYS_KEY);
				uploadList.addAll(fileList);
			}
		  list.addAll(updateList);
	  }
	  if(list!=null  && list.size()>0){
		  FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_CATEGORY_FILENAME, 14),JSON.toJSONString(list));
	  }
	  //同步附件
      if (uploadList != null && uploadList.size() > 0){
          FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_FILE_CATEGORY_FILENAME, 14),JSON.toJSONString(uploadList));
          String basePath = FileUtils.attachExportPath(15);
          if (StringUtils.isNotBlank(basePath)){
              OperAttachment.writeFile(basePath, uploadList);
              recordService.synchBidding(synchDate, new Integer(uploadList.size()).toString(), synchro.util.Constant.DATA_TYPE_ATTACH_CODE, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.COMMIT_FILE_NUMBER_SYNCH_CATEGORY);
          }
      }
      if(list!=null){
    	  recordService.synchBidding(synchDate, String.valueOf(list.size()), synchro.util.Constant.SYNCH_CATEGORY, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.COMMIT_SYNCH_CATEGORY);
      }
      return false;
	}
    /**
     * 实现导入 产品目录
     */
	@Override
	public boolean importCategory(File file) {
		// TODO Auto-generated method stub
		 List<Category> list = FileUtils.getBeans(file, Category.class); 
		 if(list!=null  && list.size()>0){
			 for(Category category:list){
			 Integer isExist=categoryMapper.countByPrimaryKey(category.getId());
			  if(isExist!=null &&isExist >0){
				  categoryMapper.updateByPrimaryKeySelective(category);
			  }else{
				  categoryMapper.insertSelective(category);
			  }
			 }
			 recordService.synchBidding(new Date(), list.size()+"", synchro.util.Constant.SYNCH_CATEGORY, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.IMPORT_FILE_NUMBER_SYNCH_CATEGORY);
		 }
		return false;
	}
	/**
	 * 实现导出目录资质关联表录 根据时间范围
	 */
	@Override
	public boolean exportCategoryQua(String start, String end, Date synchDate) {
		//根据时间获取创建 数据范围
		List<CategoryQua> createList=categoryQuaMapper.selectByCreatedAt(start, end);
		List<CategoryQua> updateList=categoryQuaMapper.selectByUpdatedAt(start, end);
		List<CategoryQua> list=new ArrayList<>();
		if(createList!=null  && createList.size()>0){
			list.addAll(createList);
		}
		if(updateList!=null  && updateList.size()>0){
			list.addAll(updateList);
		  }
		if(list!=null  && !list.isEmpty()){
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_CATEGORY_QUA, 21),JSON.toJSONString(list));
		}
	    if(list!=null){
	    	recordService.synchBidding(synchDate, String.valueOf(list.size()), synchro.util.Constant.DATA_SYNCH_CATEGORY_QUA, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.IMPORT_COMMIT_SYNCH_CATEGORY_QUA);
	    }
		return false;
	}
	/**
	 * 导入目录资质关联表录数据 
	 */
	private boolean importDate(File file){
		List<CategoryQua> list = FileUtils.getBeans(file, CategoryQua.class); 
		if(list!=null  && !list.isEmpty()){
			for(CategoryQua category:list){
				Integer isExist=categoryQuaMapper.countByPrimaryKey(category.getId());
				if(isExist!=null && isExist >0){
					categoryQuaMapper.updateByPrimaryKeySelective(category);
				}else{
					categoryQuaMapper.insertSelective(category);
				}
			}
			recordService.synchBidding(new Date(), list.size()+"", synchro.util.Constant.DATA_SYNCH_CATEGORY_QUA, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.EXPORT_COMMIT_SYNCH_CATEGORY_QUA);
		}
		return false;
	}
	/**
	 * 导入目录资质关联表录数据 
	 */
	@Override
	public boolean importCategoryQua(String synchType,File file) {
		 /**目录资质关联表*/
        if(synchType.contains(synchro.util.Constant.DATA_SYNCH_CATEGORY_QUA)){
			if (synchro.util.Constant.FILE_SYNCH_CATEGORY_QUA_PATH.equals(file.getName())) {
				for (File file2 : file.listFiles()) {
					if (file2.getName().contains(FileUtils.C_SYNCH_CATEGORY_QUA)) {
						importDate(file2);
					}
				}
			}
		}
		return false;
	}
	@Override
	public List<Category> disTreeGoodsData(String id) {
		List<Category> cateList=null;
		 //物质生产   1/3
        if(SupplierToolUtil.PRODUCT_ID.equals(id) ){
        	cateList=findPublishTree(SupplierToolUtil.GOODS_ID, 1);
        }else if(SupplierToolUtil.SALES_ID.equals(id)){
      	  //物质销售  3/2
        	cateList=findPublishTree(SupplierToolUtil.GOODS_ID, 2);
        }else{
      	  cateList=findTreeByPid(id);
        }
		return cateList;
	}

	@Override
	public List<Category> getAllParentNode(String categoryId) {
		List < Category > categoryList = new ArrayList < Category > ();
		while(true) {
			Category cate = findById(categoryId);
			if(cate == null) {
				DictionaryData root = DictionaryDataUtil.findById(categoryId);
				Category rootNode = new Category();
				rootNode.setId(root.getId());
				rootNode.setName(root.getName());
				categoryList.add(rootNode);
				break;
			} else {
				categoryList.add(cate);
				categoryId = cate.getParentId();
			}
		}
		return categoryList;
	}
	
	@Override
	public SupplierCateTree addNode(List<Category> parentNodeList) {
		SupplierCateTree cateTree = new SupplierCateTree();
		// 加入根节点
		for(int i = 0; i < parentNodeList.size(); i++) {
			DictionaryData rootNode = DictionaryDataUtil.findById(parentNodeList.get(i).getId());
			if(rootNode != null) {
				//其他，3物资生产/物资销售 2物质销售 1物资生产
				cateTree.setRootNode(rootNode.getName());
				cateTree.setRootNodeID(rootNode.getId());
				cateTree.setItemsId(rootNode.getId());
				cateTree.setItemsName(rootNode.getName());
			}
		}
		// 加入一级节点
		if(cateTree.getRootNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					DictionaryData rootNode = DictionaryDataUtil.findById(cate.getParentId());
					if(rootNode != null && cateTree.getRootNode().equals(rootNode.getName())) {
						cateTree.setFirstNode(cate.getName());
						cateTree.setFirstNodeID(cate.getId());
						cateTree.setItemsId(cate.getId());
						cateTree.setItemsName(cate.getName());
					}
				}
			}
		}
		// 加入二级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = findById(cate.getParentId());
					if(parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
					cateTree.setSecondNode(cate.getName());
					cateTree.setSecondNodeID(cate.getId());
					cateTree.setItemsId(cate.getId());
					cateTree.setItemsName(cate.getName());
					}
				}
			}
		}
		// 加入三级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
	    		Category cate = findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = findById(cate.getParentId());
					if(parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
						cateTree.setThirdNode(cate.getName());
						cateTree.setThirdNodeID(cate.getId());
						cateTree.setItemsId(cate.getId());
						cateTree.setItemsName(cate.getName());
					}
				}
		    }
	    }
		// 加入末级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
		    if(parentNodeList.size()>4){
                for(int i = 0; i < parentNodeList.size(); i++) {
	                Category cate = findById(parentNodeList.get(i).getId());
		            if(cate != null && cate.getParentId() != null) {
			            Category parentNode = findById(cate.getParentId());
			            if(parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
			                cateTree.setFourthNode(cate.getName());
							cateTree.setFourthNodeID(cate.getId());
							cateTree.setItemsId(cate.getId());
							cateTree.setItemsName(cate.getName());
		                }
	                }
		        }
		    }
		}
		return cateTree;
	}

	@Override
	public List<Category> getCListById(String id) {
		return categoryMapper.selectCListById(id);
	}

	@Override
	public List<Category> getPListById(String id) {
		return categoryMapper.selectPListById(id);
	}

	@Override
	public List<Category> getCListByCode(String code) {
		return categoryMapper.selectCListByCode(code);
	}

	@Override
	public List<Category> getPListByCode(String code) {
		return categoryMapper.selectPListByCode(code);
	}

	@Override
	public List<CategoryTree> getTreeForExt(Category category,String supplierTypeCode) {
		
		List<CategoryTree> jList = new ArrayList<>();
		if(category.getId()==null){
	    	   category.setId(dictionaryDataMapper.selectByCode(supplierTypeCode).get(0).getId());
	    }
         List<Category> cateList= disTreeGoodsData(category.getId());
         for(Category cate:cateList){
             List<Category> cList= disTreeGoodsData(cate.getId());
             CategoryTree ct=new CategoryTree();
             if(!cList.isEmpty()){
                 ct.setIsParent("true");
             }else{
                 ct.setIsParent("false");
             }
             ct.setId(cate.getId());
             ct.setName(cate.getName());
             ct.setpId(cate.getParentId());
             ct.setKind(cate.getKind());
             ct.setStatus(cate.getStatus());
             jList.add(ct);
         }
		return jList;
	}
	
	@Override
	public List<DictionaryData> getEngAptitudeLevelByCategoryId(
			String categoryId) {
		Map<String, String[]> map = new HashMap<>();
		map.put("categoryIds", categoryId.split(","));
		return categoryQuaMapper.getEngAptitudeLevelByCategoryId(map);
	}

	@Override
	public List<DictionaryData> getQuaByCid(String categoryId) {
		String[] categoryIds = categoryId.split(",");
		return categoryQuaMapper.getQuaByCid(categoryIds);
	}
	
}
