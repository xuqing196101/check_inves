package ses.service.bms.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import common.bean.ResBean;
import common.constant.StaticVariables;
import ses.dao.bms.CategoryMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.model.bms.Category;
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
    
    /** 操作类型 - 添加 */
    private static final String OPERA_ADD = "add";
    /** 操作类型 - 编辑 */
    private static final String OPERA_EDIT = "edit";
    /** 品目名称不能为空 **/
    private static final String CATEGORY_ISNOTNULL = "品目名称不能为空";
    /** 品目名称已经存在 **/
    private static final String CATEGORY_EXIST = "品目名称已经存在";
    /** 序号不能为空 **/
    private static final String CATEGORY_CODE_ISNUOTNUll = "序号不能为空";
    /** 序号不能为空 **/
    private static final String CATEGORY_CODE_ISINTEGER = " 序号只能输入正整数";
    /** 最大输入值 **/
    private static final String CATEGORY_MAX_VALUE = "最多只能输入200个汉字";

   
    
    public void insertSelective(Category category) {
        categoryMapper.insertSelective(category);
    }

    public List<Category> findTreeByPid(String id) {
        return categoryMapper.findTreeByPid(id);
    }
    
    
    /**
     * 
     * @see ses.service.bms.CategoryService#findTreeByStatus(java.lang.String, java.lang.Integer)
     */
    @Override
    public List<Category> findTreeByStatus(String id, Integer status) {
        
        return categoryMapper.findTreeByStatus(id, status);
    }

    public void updateByPrimaryKey(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    public Category selectByPrimaryKey(String id) {
        return categoryMapper.selectByPrimaryKey(id);
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
        
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String position = request.getParameter("position");
        String operaType = request.getParameter("opera");
        String desc = request.getParameter("description");
        
        ResBean res = new ResBean();
        if (StringUtils.isEmpty(name)) {
            res.setSuccess(false);
            res.setMsg(CATEGORY_ISNOTNULL);
           return  res;
        }
        
        if (StringUtils.isEmpty(position)) {
            res.setSuccess(false);
            res.setError(CATEGORY_CODE_ISNUOTNUll);
           return  res;
        }
        
        if (!StringUtils.isNumeric(position)) {
            res.setSuccess(false);
            res.setError(CATEGORY_CODE_ISINTEGER);
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
            
            Integer count = findByName(name.trim());
            
            if (count != null && count > 0) {
                res.setSuccess(false);
                res.setMsg(CATEGORY_EXIST);
               return  res;
            }
            
            Category category = new Category();
            category.setId(id);
            category.setPosition(Integer.parseInt(position));
            category.setParentId(request.getParameter("parentId"));
            category.setName(name);
            category.setStatus(1);
            category.setDescription(desc);
            category.setCreatedAt(new Date());
            category.setIsDeleted(0);
            category.setParamStatus(StaticVariables.CATEGORY_NEW_STATUS);
            insertSelective(category);
            res.setSuccess(true);
        }
        /**
         * 编辑
         */
        if (operaType.equals(OPERA_EDIT)) {
            Category category = selectByPrimaryKey(id);
            if (category != null) {
                category.setPosition(Integer.parseInt(position));
                category.setDescription(desc);
                category.setName(name);
                category.setUpdatedAt(new Date());
                updateByPrimaryKeySelective(category);
                res.setSuccess(true);
            }
        }
        return res;
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
        if (cate != null){
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
    public List<Category> listByParent(String pid) {
        // TODO Auto-generated method stub
        return categoryMapper.findTreeByPid(pid);
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
        for (Category c : listCategorys) {
            SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
            supplierTypeTree.setId(c.getId());
            supplierTypeTree.setParentId(c.getParentId());
            supplierTypeTree.setName(c.getName());
            supplierTypeTree.setChkDisabled(true);
            if (listCategoryIds.contains(c.getId())) {
                supplierTypeTree.setChecked(true);
            }
            listSupplierTypeTrees.add(supplierTypeTree);
        }
        return listSupplierTypeTrees;
    }

    @Override
    public List<SupplierTypeTree> queryCategory(Category category,List<String> listCategoryIds) {
        List<Category> listCategorys = categoryMapper.findCategoryByType(category);
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
    
    public Integer findByName(String name){
       return  categoryMapper.findByName(name);
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

}
