package ses.service.sms.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.utils.JdcgResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ses.dao.bms.CategoryMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.formbean.QualificationBean;
import ses.formbean.SupplierItemCategoryBean;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierPorjectQua;
import ses.service.bms.CategoryService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierPorjectQuaService;
import ses.service.sms.SupplierService;
import ses.util.Constant;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service(value = "supplierItemService")
public class SupplierItemServiceImpl implements SupplierItemService {

	@Autowired
	private SupplierItemMapper supplierItemMapper;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SupplierPorjectQuaService supplierPorjectQuaService;

	@Autowired
    private CategoryMapper categoryMapper;
	
	@Autowired
	private SupplierAuditService supplierAuditService;
	
	@Override
	public void saveSupplierItem(Supplier supplier) {
		String id = supplier.getId();
		supplierItemMapper.deleteBySupplierId(id);
		String supplierItemIds = supplier.getSupplierItemIds();
		String supplierTypeIds = supplier.getSupplierTypeIds();
		String[] itemIds = supplierItemIds.split(";");
		if (supplierItemIds != null && !"".equals(supplierItemIds)) {
			for (int i = 0; i < itemIds.length; i++) {
				for (String str : itemIds[i].split(",")) {
					SupplierItem supplierItem = new SupplierItem();
					supplierItem.setSupplierId(id);
					supplierItem.setCategoryId(str);
					supplierItem.setCreatedAt(new Date());
					supplierItem.setSupplierTypeRelateId(supplierTypeIds.split(",")[i]);
					supplierItemMapper.insertSelective(supplierItem);
				}
			}
		}
	}

	@Override
	public List<SupplierItem> getSupplierId(String supplierId) {
		return supplierItemMapper.getSupplierItem(supplierId);
	}

	@Override
	public List<String> getItemSupplierId() {
		return supplierItemMapper.getItemBySupplierId();
	}

	@Override
    @Transactional
	public void saveOrUpdate(SupplierItem supplierItem) {
	    String categoryId = supplierItem.getCategoryId();
	    try{
            if(!StringUtils.isEmpty(categoryId)){
                if(categoryId.indexOf(",")!=-1){
                    String[] strArray = categoryId.split(",");
                    for(int i=0;i<strArray.length;i++){
                        if(!StringUtils.isEmpty(strArray[i])){
                            saveOrUpdateOperation(strArray[i], supplierItem);
                        }
                    }
                }else{
                    saveOrUpdateOperation(categoryId, supplierItem);
                }
            }
        }catch (Exception e){
	        e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
	}
	public void saveOrUpdateOperation(String categoryId, SupplierItem supplierItem) throws Exception {
        List<Category> categoryList = new ArrayList<Category>();
        //categoryList.addAll(getChildrenNodes(categoryId));
        categoryList.addAll(getAllParentNode(categoryId));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("supplierId", supplierItem.getSupplierId());
        map.put("type", supplierItem.getSupplierTypeRelateId());
        for (Category cate : categoryList) {
            map.put("categoryId", cate.getId());
            // 查询是否数据库已存在
            List<SupplierItem> result = supplierItemMapper.findByMap(map);
            if (result == null || result.size() == 0) {
                SupplierItem item = new SupplierItem();
                item.setId(UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
                item.setSupplierId(supplierItem.getSupplierId());
                item.setSupplierTypeRelateId(supplierItem.getSupplierTypeRelateId());
                item.setCategoryId(cate.getId());
                item.setCreatedAt(new Date());
                if(categoryId.equals(cate.getId())){
                    // 设置末级节点
                    List<Category> treeByPid = categoryMapper.findTreeByPid(categoryId);
                    if(treeByPid == null || (treeByPid != null && treeByPid.isEmpty())){
                        // 设置末级
                        item.setNodeLevel(3);
                    }
                }
                supplierItemMapper.insertSelective(item);
            }
        }
    }
		
	/**
     *〈简述〉获取当前节点的所有父级节点(包括根节点)
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId 
     * @return
     */
    public List<Category> getAllParentNode(String categoryId) {
        List<Category> categoryList = new ArrayList<Category>();
        while (true) {
            Category cate = categoryService.findById(categoryId);
            if (cate == null) {
                DictionaryData root = DictionaryDataUtil.findById(categoryId);
                if(null != root){
                    Category rootNode = new Category();
                    rootNode.setId(root.getId());
                    rootNode.setName(root.getName());
                    categoryList.add(rootNode);
                }
                break;
            } else {
                categoryList.add(cate);
                categoryId = cate.getParentId();
            }
            
        }
        // 将集合倒序输出，获取小类
        /*Collections.reverse(categoryList);
        if(categoryList.size() >= 4){
        	// 3：小类
        	categoryList.get(3).setLevel("3");
        }*/
        return categoryList;
    }
	
	/**
     *〈简述〉递归获取所有的子节点
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId
     * @return
     */
    public List<Category> getChildrenNodes(String categoryId) {
        List<Category> allChildrenNodes = new ArrayList<Category>();
        List<Category> childrenList = categoryService.findPublishTree(categoryId, null);
        allChildrenNodes.addAll(childrenList);
        if (childrenList != null && childrenList.size() > 0) {
            for (Category cate : childrenList) {
                allChildrenNodes.addAll(getChildrenNodes(cate.getId()));
            }
        }
        return allChildrenNodes;
    }	
		
	/*	String[] addIds = {supplierItem.getAddProCategoryIds(), supplierItem.getAddSellCategoryIds(), supplierItem.getAddEngCategoryIds(), supplierItem.getAddServeCategoryIds()};
		for(int i = 0; i < addIds.length; i++) {
			String str = addIds[i];
			if (str != null && !"".equals(str)) {
				for (String categoryId : str.split(",")) {
					SupplierItem si = new SupplierItem();
					si.setSupplierId(supplierItem.getSupplierId());
					supplierItem.setCategoryId(categoryId);
					supplierItem.setCreatedAt(new Date());
					supplierItem.setSupplierTypeRelateId(String.valueOf(i + 1));
					supplierItemMapper.insertSelective(supplierItem);
				}
			}
		}
		
		String[] deleteIds = {supplierItem.getDeleteProCategoryIds(), supplierItem.getDeleteSellCategoryIds(), supplierItem.getDeleteEngCategoryIds(), supplierItem.getDeleteServeCategoryIds()};
		for(int i = 0; i < deleteIds.length; i++) {
			String str = deleteIds[i];
			if (str != null && !"".equals(str)) {
				for (String categoryId : str.split(",")) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("categoryId", categoryId);
					param.put("type", String.valueOf(i + 1));
					
					List<SupplierItem> listSupplierItems = supplierItemMapper.findByMap(param);
					for (SupplierItem si : listSupplierItems) {
						List<SupplierProducts> listSupplierProducts = supplierProductsMapper.findProductsByItemId(si.getId());
						for (SupplierProducts sp : listSupplierProducts) {
							productParamMapper.deleteByProductId(sp.getId());// 先删除所有产品参数
						}
						supplierProductsMapper.deleteByItemId(si.getId());// 在删除所有产品
					}
					supplierItemMapper.deleteByMap(param);// 最后删除品目
				}
			}
		}*/

	@Override
	public List<SupplierItem> getSupplierIdCategoryId(String supplierId,String categoryId,String type) {
		 
		return supplierItemMapper.getBySupplierIdCategoryId(supplierId, categoryId,type);
	}
	
	public List<SupplierItem> getCategory(String supplierId,String categoryId,String type){
    	List<SupplierItem> list=new ArrayList<SupplierItem>();
    	//一级节点
    	List<SupplierItem> cateLIst = supplierItemMapper.getBySupplierIdCategoryId(supplierId, categoryId, type);
    
    	for(SupplierItem s:cateLIst){
    	   //二级节点
    	   List<Category> categorylist = categoryService.findPublishTree(s.getCategoryId(),null);
    	   for( Category c:categorylist){
               list = commonCategoryList(supplierId, type, c);
    	   }
    	}
    	return list;		
	}
    @Override
    public List<SupplierItem> getCategoryOther(String supplierId,String categoryId,String type){
        List<SupplierItem> list=new ArrayList<SupplierItem>();
        //全选标识
        boolean fiveAllEx = false;
        boolean fourAllEx = false;
        //一级节点
        List<SupplierItem> cateList = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, categoryId, type);

        for(SupplierItem s:cateList){
            //二级节点
            List<Category> categorylist = categoryService.findPublishTree(s.getCategoryId(),null);
            if(null != categorylist && !categorylist.isEmpty()){
                for(Category c:categorylist){
                    if(c.getCode().equals("B02")||c.getCode().equals("B03")){//工程设计/工程勘察
                        //查询所有的三级节点
                        List<Category> cateThree = categoryService.findPublishTree(c.getId(),null);
                        if(null != cateThree && !cateThree.isEmpty()){
                            List<SupplierItem> supThreeListexists = new ArrayList<>();
                            for(Category cs:cateThree){
                                //查询四级节点
                                List<Category> cateFour = categoryService.findPublishTree(cs.getId(),null);
                                if(null != cateFour && !cateFour.isEmpty()){
                                    List<SupplierItem> supFourListexists = new ArrayList<>();
                                    for(int i=0;i<cateFour.size();i++){
                                        //查询五级节点
                                        List<Category> cateFive = categoryService.findPublishTree(cateFour.get(i).getId(),null);
                                        if(null != cateFive && !cateFive.isEmpty()){
                                            List<SupplierItem> supListexists = new ArrayList<>();
                                            for(int j=0;j<cateFive.size();j++){
                                                //去中间表查是否存在
                                                List<SupplierItem> cateLs = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, cateFive.get(j).getId(),type);
                                                // 判断是否为空,不为空加入子节点
                                                if (cateLs != null && !cateLs.isEmpty()) {
                                                    list.add(cateLs.get(0));
                                                    supListexists.add(cateLs.get(0));
                                                }
                                            }
                                            //如果本节点下所有子节点都已勾选存在,则删除已添加的本级所有子节点,然后添加本节点
                                            if(supListexists.size()==cateFive.size()){
                                                fiveAllEx = true;
                                                for(int j=0;j<supListexists.size();j++){
                                                    list.remove(supListexists.get(j));
                                                }
                                                //去中间表查是否存在
                                                List<SupplierItem> cates = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, cateFour.get(i).getId(),type);
                                                // 判断是否为空,不为空加入子节点
                                                if (cates != null && !cates.isEmpty()) {
                                                    list.add(cates.get(0));
                                                    supFourListexists.add(cates.get(0));
                                                }
                                            }
                                        }else{//没有子节点时显示本节点
                                            fiveAllEx = true;
                                            //去中间表查是否存在
                                            List<SupplierItem> cates = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, cateFour.get(i).getId(),type);
                                            // 判断是否为空,不为空加入子节点
                                            if (cates != null && !cates.isEmpty()) {
                                                list.add(cates.get(0));
                                                supFourListexists.add(cates.get(0));
                                            }
                                        }
                                    }
                                    //如果本节点下所有子节点都已勾选存在,则删除已添加的本级所有子节点,然后添加本节点
                                    if(supFourListexists.size()==cateFour.size() && fiveAllEx){
                                        fourAllEx = true;
                                        for(int i=0;i<supFourListexists.size();i++){
                                            list.remove(supFourListexists.get(i));
                                        }
                                        //去中间表查是否存在
                                        List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, cs.getId(),type);
                                        // 判断是否为空,不为空加入子节点
                                        if (cateLst != null && cateLst.size() > 0) {
                                            list.add(cateLst.get(0));
                                            supThreeListexists.add(cateLst.get(0));
                                        }
                                    }
                                }else{
                                    fourAllEx = true;
                                    //去中间表查是否存在
                                    List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, cs.getId(),type);
                                    // 判断是否为空,不为空加入子节点
                                    if (cateLst != null && cateLst.size() > 0) {
                                        list.add(cateLst.get(0));
                                        supThreeListexists.add(cateLst.get(0));
                                    }
                                }
                            }
                            //如果本节点下所有子节点都已勾选存在,则删除已添加的本级所有子节点,然后添加本节点
                            if(supThreeListexists.size()==cateThree.size() && fourAllEx){
                                for(int i=0;i<supThreeListexists.size();i++){
                                    list.remove(supThreeListexists.get(i));
                                }
                                //去中间表查是否存在
                                List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, c.getId(),type);
                                // 判断是否为空,不为空加入子节点
                                if (cateLst != null && cateLst.size() > 0) {
                                    list.add(cateLst.get(0));
                                }
                            }
                        }else{
                            //去中间表查是否存在
                            List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, c.getId(),type);
                            // 判断是否为空,不为空加入子节点
                            if (cateLst != null && cateLst.size() > 0) {
                                list.add(cateLst.get(0));
                            }
                        }
                    }else{
                        list.addAll(commonCategoryList(supplierId, type, c));
                    }
                }
            }
        }
        return list;
    }

    /**
     * 工程设计/工程勘察类别以外,当全部勾选子节点,则全部显示
     * @param supplierId
     * @param type
     * @param c
     * @return
     */
    public List<SupplierItem> commonCategoryList(String supplierId, String type, Category c){
        List<SupplierItem> list=new ArrayList<SupplierItem>();
        //查询所有的三级节点
        List<Category> cateThree = categoryService.findPublishTree(c.getId(),null);
        for(Category cs:cateThree){
            //查询四级节点
            List<Category> cateFour = categoryService.findPublishTree(cs.getId(),null);
            if(null != cateFour && !cateFour.isEmpty()){
                for(int i=0;i<cateFour.size();i++){
                    //查询五级节点
                    List<Category> cateFive = categoryService.findPublishTree(cateFour.get(i).getId(),null);
                    if(null != cateFive && !cateFive.isEmpty()){
                        for(int j=0;j<cateFive.size();j++){
                            //去中间表查是否存在
                            List<SupplierItem> cateLs = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, cateFive.get(j).getId(),type);
                            // 判断是否为空,不为空加入子节点
                            if (cateLs != null && !cateLs.isEmpty()) {
                                list.add(cateLs.get(0));
                            }
                        }
                    }else{
                        //去中间表查是否存在
                        List<SupplierItem> cates = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, cateFour.get(i).getId(),type);
                        // 判断是否为空,不为空加入子节点
                        if (cates != null && !cates.isEmpty()) {
                            list.add(cates.get(0));
                        }
                    }
                }
            }else{
                //去中间表查是否存在
                List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, cs.getId(),type);
                //list.addAll(cateLst);
                // 判断是否为空,不为空加入子节点
                if (cateLst != null && cateLst.size() > 0) {
                    list.add(cateLst.get(0));
                }
            }
        }
        return list;
    }
	
	@Override
	public List<Category> getCategory(String supplierId,String type) {
		List<Category> list=new ArrayList<Category>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("supplierId", supplierId);
		map.put("type", type);
		List<SupplierItem> itemList = supplierItemMapper.findByMap(map);
		for(SupplierItem item:itemList){
			List<Category> cateList = categoryService.findPublishTree(item.getCategoryId(),null);
			if(cateList == null || cateList.isEmpty()){
				Category category = categoryService.selectByPrimaryKey(item.getCategoryId());
				category.setId(item.getId());
				list.add(category);	
			}
		}
		return list;
	}
	
	@Override
	public List<SupplierItem> findCategoryList(String supplierId, String type, Integer pageNum) {
	   if (pageNum != null) {
	        PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
	    }
	    Map<String, Object> param = new HashMap<String, Object>();
	    param.put("supplierId", supplierId);
	    param.put("type", type);
	    param.put("isDeleted", 0);
	    List<SupplierItem> itemsList = supplierItemMapper.selectByMap(param);
	    return itemsList;
	}

    @Override
    public List<SupplierItem> findCategoryListPassed(String supplierId, String type, Integer pageNum) {
        if (pageNum != null) {
            PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
        }
        // 查询数据条件封装
        PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
        Map<String, Object> param = new HashMap<>();
        param.put("supplierId", supplierId);
        param.put("type", type);
        if(Constant.SUPPLIER_SALES.equals(type)){
            param.put("items_sales_page", ses.util.Constant.ITEMS_SALES_PAGE);
        }else {
            param.put("items_product_page", ses.util.Constant.ITMES_PRODUCT_PAGE);
        }
        // 查询该某类型下的所通过的子节点
        return supplierItemMapper.selectPassItemByCond(param);
    }



	@Override
	public SupplierItem selectByPrimaryKey(String id) {
	    SupplierItem itemsList = supplierItemMapper.selectByPrimaryKey(id);
	    return itemsList;
	}
    @Override
    public List<Category> getCategoryShenhe(String supplierId,String type) {
        List<Category> cateList=new ArrayList<Category>();
        Map<String,Object> map=new HashMap<String,Object>();    
        map.put("supplierId", supplierId);
        map.put("type", type);
        List<SupplierItem> list = supplierItemMapper.findByMap(map);
        for(SupplierItem item:list){
            Category category = categoryService.selectByPrimaryKey(item.getCategoryId());
            if (category != null) {
                cateList.add(category); 
            }
        }
        return cateList;
    }

	
    /**
     * @see ses.service.sms.SupplierItemService#deleteItems(ses.model.sms.SupplierItem)
     */
    @Override
    @Transactional
    public void deleteItems(SupplierItem supplierItem) {
        String categoryId = supplierItem.getCategoryId();
        try{
            if(!StringUtils.isEmpty(categoryId)){
                if(categoryId.indexOf(",")!=-1){
                    String[] strArray = categoryId.split(",");
                    for(int i=0;i<strArray.length;i++){
                        if(!StringUtils.isEmpty(strArray[i])){
                            deleteItemsOpertion(strArray[i], supplierItem);
                        }
                    }
                }else{
                    deleteItemsOpertion(categoryId, supplierItem);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
    public void deleteItemsOpertion(String categoryId, SupplierItem supplierItem) throws Exception{
        List<Category> categoryList = new ArrayList<Category>();
        categoryList.addAll(getChildrenNodes(categoryId));
        Category current = categoryService.findById(categoryId);
        categoryList.add(current);
        Map<String, String> map = new HashMap<String, String>();
        map.put("supplierId", supplierItem.getSupplierId());
        map.put("type", supplierItem.getSupplierTypeRelateId());
        for (Category cate : categoryList) {
            if (cate != null) {
                map.put("categoryId", cate.getId());
                supplierItemMapper.deleteByMap(map);
            }
        }
        // 判断父节点下还有没有子节点被勾选
        if (current != null) {
//            Map<String, Object> param = new HashMap<String, Object>();
//            param.put("supplierId", supplierItem.getSupplierId());
//            param.put("type", supplierItem.getSupplierTypeRelateId());
//            List<SupplierItem> allCategory = supplierItemMapper.findByMap(param);
            String parentId = current.getParentId();
//            boolean pflag = false;
            while(true){

//            没有同级节点删除父级节点
                boolean bool = sameCategory(supplierItem.getSupplierId(),parentId,supplierItem.getSupplierTypeRelateId());
                if(bool==false){
                    Category category = categoryService.findById(parentId);
                    if(category != null){
                        List<SupplierItem> bySupplierIdCategoryId = supplierItemMapper.getBySupplierIdCategoryId(supplierItem.getSupplierId(), category.getId(), supplierItem.getSupplierTypeRelateId());
                        if(bySupplierIdCategoryId!=null&&bySupplierIdCategoryId.size()>0){
                            map.put("categoryId", category.getId());
                            supplierItemMapper.deleteByMap(map);
                            parentId = category.getParentId();
                        }else{
                            break  ;
                        }
                    }else{
                        //如果该类型下没有子节点,删除关联的根节点
                        String rootCategoryId = DictionaryDataUtil.getId(supplierItem.getSupplierTypeRelateId());
                        List<SupplierItem> supplierItemList = this.getSupplierId(supplierItem.getSupplierId());
                        if(null != supplierItemList && !supplierItemList.isEmpty()){
                            for(int i=0;i<supplierItemList.size();i++){
                                if(!StringUtils.isEmpty(rootCategoryId) && rootCategoryId.equals(supplierItemList.get(i).getCategoryId())){
                                    map.put("categoryId", rootCategoryId);
                                    supplierItemMapper.deleteByMap(map);
                                }
                            }
                        }
                        break  ;
                    }
                }else{
                    break  ;
                }
            }
//            if (parentId != null) {
//                out:while (true) {
//                    boolean flag = false;
//                    in:for (SupplierItem category : allCategory) {
//                        Category node = categoryService.findById(category.getCategoryId());
//                        if (node != null) {
//                            if (parentId.equals(node.getParentId())) {
//                                List<Category> childNodes = categoryService.findPublishTree(category.getCategoryId(), null);
//                                if (childNodes == null || childNodes.size() == 0) {
//                                    flag = true;
//                                    break in;
//                                }
//                            }
//                        }
//                    }
//                    if (!flag) {
//                        map.put("categoryId", parentId);
//                        supplierItemMapper.deleteByMap(map);
//                    }
//                    Category category = categoryService.findById(parentId);
//                    if (category == null) {
//                        break out;
//                    } else {
//                        parentId = category.getParentId();
//                    }
//                }
//            }
//        } else {
//            map.put("categoryId", categoryId);
//            supplierItemMapper.deleteByMap(map);
//        }
        }
    }

    @Override
    public List<SupplierItem> findByMap(Map<String, Object> map) {
        return supplierItemMapper.findByMap(map);
    }

    /**
     * @see ses.service.sms.SupplierItemService#updateByPrimaryKeySelective(java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateByPrimaryKeySelective(List<SupplierItem> itemList) {
        for (SupplierItem item : itemList) {
        	if(item.getLevel()==null){
        		item.setLevel("");
        	}
        	if(item.getProfessType()==null){
        		item.setProfessType("");
        	}
            supplierItemMapper.updateByPrimaryKeySelective(item);
        }
    }

	@Override
	public void deleteBySupplierId(String supplierId) {
	 Map<String,String> map=new HashMap<String,String>();
	 map.put("supplierId", supplierId);
	 supplierItemMapper.deleteByMap(map);
	}	
	
	public List<SupplierItem> queryBySupplierAndType(String supplierId,String type){
		List<SupplierItem> items = supplierItemMapper.queryBySupplierIdAndType(supplierId, type);
		return items;
	}
	
	//查询供应商品目中间表是否还有同级
	public boolean sameCategory(String supplierId,String categoryId,String supplierType){
		boolean bool=false;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("supplierId", supplierId);
		param.put("type", supplierType);
		List<SupplierItem> allCategory = supplierItemMapper.findByMap(param);
		for (SupplierItem category : allCategory) {
			Category node = categoryService.findById(category.getCategoryId());
			if (node != null) {
				if (categoryId.equals(node.getParentId())) {
					bool = true;
					break;
				}
			}
		}
		return bool;
	}

	@Override
	public List<String> findSupplierIdByCategoryId(String categoryId) {
		return supplierItemMapper.findSupplierIdByCategoryId(categoryId);
	}

	@Override
	public List<String> findSupplierTypeBySupplierId(String supplierId) {
		return supplierItemMapper.findSupplierTypeBySupplierId(supplierId);
	}

	/**
	 *
	 * Description:查询供应商审核通过的产品类别
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param map
	 * @since JDK1.7
	 */
	public Set<String> findPassSupplierTypeBySupplierId(Map<String,Object> map){
        Set<String> set = new HashSet<>();
        // 查询供应商是否选择了物资类型
        String supplierId = (String) map.get("supplierId");
        List<String> allTypeOfSup = supplierItemMapper.findSupplierTypeBySupplierId(supplierId);
        List<String> passSupplierTypeBySupplierId = new ArrayList<>();
        // 物资类型做特殊处理
        boolean flag = false;
        if(allTypeOfSup != null && allTypeOfSup.contains(Constant.SUPPLIER_SALES)){
            map.put("type", Constant.SUPPLIER_SALES);
            map.put("items_sales_page", ses.util.Constant.ITEMS_SALES_PAGE);
            map.put("supplierType_page", ses.util.Constant.SUPPLIER_CATE_INFO_ITEM_FLAG);
            passSupplierTypeBySupplierId = supplierItemMapper.findPassSupplierTypeBySupplierId(map);
            if(passSupplierTypeBySupplierId != null && !passSupplierTypeBySupplierId.isEmpty()){
                set.addAll(passSupplierTypeBySupplierId);
            }
            flag = true;
        }

        // gys没有选择物资，工程和服务类型直接返回
        if(allTypeOfSup != null && !allTypeOfSup.contains(Constant.SUPPLIER_PRODUCT) && !allTypeOfSup.contains(Constant.SUPPLIER_PROJECT)
                && !allTypeOfSup.contains(Constant.SUPPLIER_SERVICE)){
            return set;
        }
        // 清空
        passSupplierTypeBySupplierId.clear();
        map.remove("type");
        map.remove("items_sales_page");
        // 查询其他类型
        if(flag){
            map.put("type", "SALES");
        }
        map.put("items_product_page", ses.util.Constant.ITMES_PRODUCT_PAGE);
        map.put("supplierType_page", ses.util.Constant.SUPPLIER_CATE_INFO_ITEM_FLAG);
        passSupplierTypeBySupplierId = supplierItemMapper.findPassSupplierTypeBySupplierId(map);
        set.addAll(passSupplierTypeBySupplierId);
        return set;
    }

	/**
	 *
	 * Description:查询供应商选择的小类节点
	 *
	 * @author Easong
	 * @version 2017/7/6
	 * @param supplierId
	 * @since JDK1.7
	 */
    @Override
    public JdcgResult selectRegSupCateOfLastNode(String supplierId) {
        // 查询供应商选择的小类节点
        // 封装集合
        List<List<Category>> list = new ArrayList<>();
        List<SupplierItem> supplierItemsOfLastNode = supplierItemMapper.selectRegSupCateOfLastNode(supplierId);
        if(supplierItemsOfLastNode != null && !supplierItemsOfLastNode.isEmpty()){
            for (SupplierItem supplierItem : supplierItemsOfLastNode){
                // 获取categoryId
                String categoryId = supplierItem.getCategoryId();
                List<Category> allParentNode = categoryService.getAllParentNode(categoryId);
                // 将集合按照品目从大到小排序
                Collections.reverse(allParentNode);
                list.add(allParentNode);
            }
        }
        return JdcgResult.ok(list);
    }

    /**
     * 
     * Description:查询供应商审核通过的产品类别列表
     * 
     * @author Easong
     * @version 2017/7/7
     * @param 
     * @since JDK1.7
     */
    @Override
    public List<SupplierItem> selectPassItemByCond(String supplierId, String type, Integer pageNum) {
        if (pageNum != null) {
            PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
        }
        Map<String, Object> param = new HashMap<>();
        param.put("supplierId", supplierId);
        param.put("type", type);
        if(Constant.SUPPLIER_SALES.equals(type)){
            param.put("items_sales_page", ses.util.Constant.ITEMS_SALES_PAGE);
        }else {
            param.put("items_product_page", ses.util.Constant.ITMES_PRODUCT_PAGE);
        }
        return supplierItemMapper.selectPassItemByCond(param);
    }



    
	// 获取供应商品目类别
	public List < SupplierItemCategoryBean > getSupplierItemCategoryList(String supplierId, String code) {
		List < SupplierItemCategoryBean > sicList = new ArrayList < SupplierItemCategoryBean > ();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("supplierId", supplierId);
		paramMap.put("type", code);
		paramMap.put("isReturned", 0);
		paramMap.put("isDeleted", 0);
		List < SupplierItem > itemList = this.findByMap(paramMap);
		for(SupplierItem item: itemList) {
			Category cate = categoryService.selectByPrimaryKey(item.getCategoryId());
			SupplierItemCategoryBean sic = new SupplierItemCategoryBean();
			if (cate == null) {
				DictionaryData data = DictionaryDataUtil.findById(item.getCategoryId());
				if(data != null){
					sic.setId(data.getId());
					//sic.setParentId(data.getId());
					sic.setName(data.getName());
				}
			} else {
				//供应商中间表的id和资质证书的id
				//cate.setParentId(item.getId());
				BeanUtils.copyProperties(cate, sic);
			}
			sic.setItemId(item.getId());
			sicList.add(sic);
		}
		return sicList;
	}

	public SupplierCateTree getTreeListByCategoryId(String categoryId,
			SupplierItem item) {
		SupplierCateTree cateTree = new SupplierCateTree();
		// 递归获取所有父节点
		List < Category > parentNodeList = getAllParentNode(categoryId);
		// 加入根节点
		for(int i = 0; i < parentNodeList.size(); i++) {
			DictionaryData rootNode = DictionaryDataUtil.findById(parentNodeList.get(i).getId());
			if(rootNode != null) {
				cateTree.setRootNode(rootNode.getName());
			}
		}
		// 加入一级节点
		if(cateTree.getRootNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					DictionaryData rootNode = DictionaryDataUtil.findById(cate.getParentId());
					if(rootNode != null && cateTree.getRootNode().equals(rootNode.getName())) {
						cateTree.setFirstNode(cate.getName());
					}
				}
			}
		}
		// 加入二级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
						cateTree.setSecondNode(cate.getName());
					}
				}
			}
		}
		// 加入三级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
						cateTree.setThirdNode(cate.getName());
					}
				}
			}
		}
		// 加入末级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
		    if(parentNodeList.size()>4){
                for(int i = 0; i < parentNodeList.size(); i++) {
                    Category cate = categoryService.findById(parentNodeList.get(i).getId());
                    if(cate != null && cate.getParentId() != null) {
                        Category parentNode = categoryService.findById(cate.getParentId());
                        if(parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
                            cateTree.setFourthNode(cate.getName());
                        }
                    }
                }
            }
		}

		// 工程类等级
		if(item != null) {
			// 等级
			if(item != null && item.getLevel() != null) {
				DictionaryData data = DictionaryDataUtil.findById(item.getLevel());
				if(data!=null){
					cateTree.setLevel(data);
				}else{
					List<SupplierPorjectQua> projectData = supplierPorjectQuaService.queryByNameAndSupplierId(item.getQualificationType(), item.getSupplierId());
					if(projectData!=null&&projectData.size()>0){
				    	DictionaryData dd=new DictionaryData();
				    	dd.setName(projectData.get(0).getCertLevel());
				    	dd.setId(projectData.get(0).getId());
				    	cateTree.setLevel(dd); 
			    	}
				}
			}
			// 证书编号
			if(item != null && item.getCertCode() != null) {
				cateTree.setCertCode(item.getCertCode());
			}
			// 资质等级
			if(item != null && item.getQualificationType() != null) {
				cateTree.setQualificationType(item.getQualificationType());
			}
			if(item != null && item.getProfessType()!= null) {
				cateTree.setProName(item.getProfessType());
			}
			
			// 所有资质类型
			/*List < Category > cateList = new ArrayList < Category > ();
			cateList.add(categoryService.selectByPrimaryKey(categoryId));
			List < QualificationBean > type = supplierService.queryCategoyrId(cateList, 4);
			List < Qualification > typeList = new ArrayList < Qualification > ();
			if(type != null && type.size() > 0 && type.get(0).getList() != null && type.get(0).getList().size() > 0) {
				typeList = type.get(0).getList();
			}*/
			// 所有资质类型，包括父节点的资质
			List < QualificationBean > qbList = supplierService.queryCategoyrId(parentNodeList, 4);
			List < Qualification > typeList = new ArrayList < Qualification > ();
			if(qbList != null && qbList.size() > 0){
				for(QualificationBean qb : qbList){
					typeList.addAll(qb.getList());
				}
			}
			//自定义等级
//			List<SupplierPorjectQua> supplierQua = supplierPorjectQuaService.queryByNameAndSupplierId(null, item.getSupplierId());
//			for(SupplierPorjectQua qua:supplierQua){
//				Qualification q=new Qualification();
//				q.setId(qua.getName());
//				q.setName(qua.getName());
//				typeList.add(q);
//			}
			
			cateTree.setTypeList(typeList);
		}
		return cateTree;
	}

	@Override
	public List<Category> getThirdCategoryList(String supplierId, String type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("supplierId", supplierId);
		paramMap.put("type", type);
        List < SupplierItem > itemList = findByMap(paramMap);
        if(itemList != null){
        	List < Category > categoryList = new ArrayList < Category > ();
        	for(SupplierItem item: itemList) {
    		    Category cate = categoryService.selectByPrimaryKey(item.getCategoryId());
                if (cate == null) {
                    cate = new Category();
                    DictionaryData data = DictionaryDataUtil.findById(item.getCategoryId());
                    cate.setId(data.getId());
                    cate.setParentId(data.getId());
                    cate.setName(data.getName());
                } else {
                    cate.setParentId(item.getId());
                }
                categoryList.add(cate);
    		}
        	return categoryList;
        }
        return null;
	}

	@Override
	public Map<String, Object> getAptitude(String supplierId,
			String supplierTypeIds) {
		List < QualificationBean > proQua = null;
		List < QualificationBean > saleQua = null;
		List < QualificationBean > serviceQua = null;
		if(supplierTypeIds != null){
			String[] typeIdAry = supplierTypeIds.split(",");
			for(String typeId : typeIdAry){
				if("PRODUCT".equals(typeId)){
					/*//查询所有的三级品目生产
					List < Category > listPro = this.getThirdCategoryList(supplierId, "PRODUCT");
					removeSame(listPro);
					//根据品目id查询所有的生产证书信息
					proQua = supplierService.queryCategoyrId(listPro, 2);*/
					//查询品目信息--生产
					List<SupplierItemCategoryBean> sicList_pro = this.getSupplierItemCategoryList(supplierId, "PRODUCT");
					removeSameSic(sicList_pro);
					//根据品目id查询所有的证书信息
					proQua = supplierService.getQualificationList(sicList_pro, 2);
				}

				if("SALES".equals(typeId)){
					/*//查询所有的三级品目销售
					List < Category > listSale = this.getThirdCategoryList(supplierId, "SALES");
					removeSame(listSale);
					//根据品目id查询所有的销售证书信息
					saleQua = supplierService.queryCategoyrId(listSale, 3);*/
					//查询品目信息--销售
					List<SupplierItemCategoryBean> sicList_sale = this.getSupplierItemCategoryList(supplierId, "SALES");
					removeSameSic(sicList_sale);
					//根据品目id查询所有的证书信息
					saleQua = supplierService.getQualificationList(sicList_sale, 3);
				}
				
				if("SERVICE".equals(typeId)){
					/*//查询所有的三级品目服务
					List < Category > listService = this.getThirdCategoryList(supplierId, "SERVICE");
					removeSame(listService);
					//根据品目id查询所有的服务证书信息
					serviceQua = supplierService.queryCategoyrId(listService, 1);*/
					//查询品目信息--服务
					List<SupplierItemCategoryBean> sicList_service = this.getSupplierItemCategoryList(supplierId, "SERVICE");
					removeSameSic(sicList_service);
					//根据品目id查询所有的证书信息
					serviceQua = supplierService.getQualificationList(sicList_service, 2);
				}
			}
		}

		List < Qualification > proList = new ArrayList < Qualification > ();
		List < Qualification > saleList = new ArrayList < Qualification > ();
		List < Qualification > serviceList = new ArrayList < Qualification > ();
		//生产
		if(proQua != null && proQua.size() > 0) {
			for(QualificationBean qb: proQua) {
				proList.addAll(qb.getList());
			}
		}
		//销售
		if(saleQua != null && saleQua.size() > 0) {
			for(QualificationBean qb: saleQua) {
				saleList.addAll(qb.getList());
			}
		}
		//服务
		if(serviceQua != null && serviceQua.size() > 0) {
			for(QualificationBean qb: serviceQua) {
				serviceList.addAll(qb.getList());
			}
		}

		StringBuffer sbUp = new StringBuffer("");
		StringBuffer sbShow = new StringBuffer("");
		if(proList != null && proList.size() > 0) {
			int len = proList.size() + 1;
			for(int i = 1; i < len; i++) {
				sbUp.append("pUp" + i + ",");
				sbShow.append("pShow" + i + ",");
			}
		}
		
		if(saleList != null && saleList.size() > 0) {
			int salelen = saleList.size() + 1;
			for(int i = 1; i < salelen; i++) {
				sbUp.append("saleUp" + i + ",");
				sbShow.append("saleShow" + i + ",");
			}
		}

		if(serviceList != null && serviceList.size() > 0) {
			int serverlen = serviceList.size() + 1;
			for(int i = 1; i < serverlen; i++) {
				sbUp.append("serverUp" + i + ",");
				sbShow.append("serverShow" + i + ",");
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("proQua", proQua);
		map.put("saleQua", saleQua);
		map.put("serviceQua", serviceQua);
		map.put("saleShow", sbShow.toString());
		map.put("saleUp", sbUp.toString());
		return map;
	}
	
	/**
	 *〈简述〉去重
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param list
	 */
	public void removeSame(List < Category > list) {
		if(list != null && list.size() > 1){
			for(int i = 0; i < list.size() - 1; i++) {
				for(int j = list.size() - 1; j > i; j--) {
					if(list.get(j).getId().equals(list.get(i).getId())) {
						list.remove(j);
					}
				}
			}
		}
	}
	public void removeSameSic(List < SupplierItemCategoryBean > list) {
		for(int i = 0; i < list.size() - 1; i++) {
			for(int j = list.size() - 1; j > i; j--) {
				if(list.get(j).getId().equals(list.get(i).getId())) {
					list.remove(j);
				}
			}
		}
	}
	
	@Override
	public List<SupplierItem> removeAuditNotItems(List<SupplierItem> items,
			String supplierId, String code) {
		if(items == null){
			items = this.queryBySupplierAndType(supplierId, code);
		}
		if(items != null){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType(ses.util.Constant.ITMES_PRODUCT_PAGE);
			if(ses.util.Constant.SUPPLIER_PRODUCT.equals(code)){
				supplierAudit.setAuditType(ses.util.Constant.ITMES_PRODUCT_PAGE);
			}
			if(ses.util.Constant.SUPPLIER_SALES.equals(code)){
				supplierAudit.setAuditType(ses.util.Constant.ITEMS_SALES_PAGE);
			}
			List<SupplierAudit> auditList = supplierAuditService.getAuditRecords(supplierAudit, new Integer[]{0,2});
			if(auditList != null){
				StringBuffer errorField = new StringBuffer();
				for(SupplierAudit audit: auditList) {
					errorField.append(audit.getAuditField() + ",");
				}
				StringBuffer pSb = new StringBuffer();// 存储删除的父节点
				Iterator<SupplierItem> itr = items.iterator();
				// 删除审核不通过的品目
				while(itr.hasNext()){
					SupplierItem item = itr.next();
					String cateId = item.getCategoryId();
					if(errorField.indexOf(cateId) != -1){
						if(item.getIsReturned() != 1){
							item.setIsReturned((byte)1);
							supplierItemMapper.updateByPrimaryKeySelective(item);
							// 或者
							/*SupplierItem updateItem = new SupplierItem();
							updateItem.setId(item.getId());
							updateItem.setIsReturned((byte)1);
							supplierItemMapper.updateByPrimaryKeySelective(updateItem);*/
						}
						itr.remove();//这里删除的是末级节点
					}
//					StringBuffer pSb = new StringBuffer();// 存储删除的父节点
					// 查询该节点的所有父节点
					List<Category> pList = categoryService.getPListById(cateId);
					Collections.reverse(pList);// 反转
					if(pList != null && !pList.isEmpty()){
						for(Category p : pList){
							// 查询每一个父节点的子节点（下级节点）
							List<Category> cList = categoryService.findTreeByPidIsPublish(p.getId());
							if(cList != null && !cList.isEmpty()){
								List<String> catIds = new ArrayList<String>();
								for(Category c : cList){
									if(pSb.indexOf(c.getId()) == -1){// 已经确定要删除的不加入对比
										catIds.add(c.getId());
									}
								}
								if(catIds.isEmpty()){
									pSb.append(p.getId() + ",");
								}else{
									int countItems = this.countItemsBySuppIdAndCateIds(supplierId, catIds, code);
									// 如果子节点在品目中没有找到，则删除该父节点对应的品目信息
									if(countItems == 0 && pSb.indexOf(p.getId()) == -1){
										pSb.append(p.getId() + ",");
									}
								}
							}
						}
					}
				}
				// 级联删除审核不通过品目的父节点
				itr = items.iterator();
				while(itr.hasNext()){
					SupplierItem item = itr.next();
					String cateId = item.getCategoryId();
					if(pSb.indexOf(cateId) != -1 && errorField.indexOf(cateId) == -1){
						if(item.getIsReturned() != 1){
							item.setIsReturned((byte)1);
							supplierItemMapper.updateByPrimaryKeySelective(item);
						}
						itr.remove();
					}
				}
			}
			// 删除根节点
			if(items.size() == 1){
				SupplierItem rootItem = items.get(0);
				Category rootCate = categoryService.findById(rootItem.getCategoryId());
				if(rootCate == null){// 如果最后只剩下一个根节点（如：物资生产）则清空节点
					if(rootItem.getIsReturned() != 1){
						rootItem.setIsReturned((byte)1);
						supplierItemMapper.updateByPrimaryKeySelective(rootItem);
					}
					items.clear();
				}
			}
		}
		return items;
	}

	@Override
	public List<SupplierItem> getItemList(String supplierId,
			String type, Byte isReturned, Integer pageNum) {
		if (pageNum != null) {
	        PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
	    }
	    Map<String, Object> param = new HashMap<String, Object>();
	    param.put("supplierId", supplierId);
	    param.put("type", type);
	    param.put("isDeleted", 0);
	    if(isReturned != null){
	    	param.put("isReturned", isReturned);
	    }
	    List<SupplierItem> itemsList = supplierItemMapper.selectByMap(param);
	    return itemsList;
	}
	
	private int countItemsBySuppIdAndCateIds(String supplierId, List<String> catIds, String code){
		return supplierItemMapper.countItemsBySuppIdAndCateIds(supplierId, catIds, code);
	}

	@Override
	public List<SupplierItem> getBySupplierIdCategoryIdIsNotReturned(
			String supplierId, String categoryId, String type) {
		return supplierItemMapper.getBySupplierIdCategoryIdIsNotReturned(supplierId, categoryId, type);
	}

	@Override
	public int deleteItemsBySupplierId(String supplierId, Byte isReturned) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("supplierId", supplierId);
		if(isReturned != null){
			param.put("isReturned", isReturned.toString());
		}
		return supplierItemMapper.deleteByMap(param);
	}

	/**
	 *
	 * Description: 查询公示供应商通过的产品品目
	 *
	 * @author Easong
	 * @version 2017/8/1
	 * @param [map: int age, String supplierId, String type]
	 * @since JDK1.7
	 */
    @Override
    public JdcgResult selectSupPublicityItem(Map<String, Object> map) {
        // 获取条件
        Integer pageNum = (Integer) map.get("pageNum");
        String supplierId = (String) map.get("supplierId");
        String type = (String) map.get("type");

        // 查询数据条件封装
        PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
        Map<String, Object> param = new HashMap<>();
        param.put("supplierId", supplierId);
        param.put("type", type);
        if(Constant.SUPPLIER_SALES.equals(type)){
            param.put("items_sales_page", ses.util.Constant.ITEMS_SALES_PAGE);
        }else {
            param.put("items_product_page", ses.util.Constant.ITMES_PRODUCT_PAGE);
        }
        // 查询该某类型下的所通过的子节点
        List<SupplierItem> supplierItems = supplierItemMapper.selectPassItemByCond(param);
        HashMap<String, Object> selectMap = new HashMap<>();
        // 定义节点封装类实例化
        List<SupplierCateTree> supplierCateTreeList = new ArrayList<>();
        // 定义根节点只执行一次
        boolean flag = true;
        // 定义根节点名称
        String rootNodeName = null;

        if(supplierItems != null && !supplierItems.isEmpty()){
            for (SupplierItem supplierItem : supplierItems){
                // 根据子节点级联查询所有父节点
                selectMap.clear();
                selectMap.put("id", supplierItem.getCategoryId());
                List<Category> categoryByParentNode =  categoryMapper.findCategoryByParentNode(selectMap);
                if(categoryByParentNode != null && !categoryByParentNode.isEmpty()){
                    SupplierCateTree supplierCateTree = new SupplierCateTree();
                    // 查询数据词典
                    if(flag){
                        // 根节点一样只查询一次
                        DictionaryData rootNode = DictionaryDataUtil.findById(categoryByParentNode.get(0).getParentId());
                        rootNodeName = rootNode.getName();
                        flag = false;
                    }
                    // 加入根节点
                    supplierCateTree.setRootNode(rootNodeName);
                    // 定义数组下标
                    int i = 0;
                    // 遍历循环封装节点值
                    do {
                        if(i == 0){
                            // 加入一级节点
                            supplierCateTree.setFirstNode(categoryByParentNode.get(0).getName());
                        }
                        if(i == 1){
                            // 加入二级节点
                            supplierCateTree.setSecondNode(categoryByParentNode.get(1).getName());
                        }
                        if(i == 2){
                            // 加入三级节点
                            supplierCateTree.setThirdNode(categoryByParentNode.get(2).getName());
                        }
                        if(i == 3){
                            // 加入品种名称
                            supplierCateTree.setFourthNode(categoryByParentNode.get(3).getName());
                        }
                        i++;
                    }while (i != categoryByParentNode.size());
                    // 加入集合中
                    supplierCateTreeList.add(supplierCateTree);
                }
            }
        }
        // 封装分页查询
        PageInfo<SupplierItem> pageInfo = new PageInfo<>(supplierItems);
        PageInfo<SupplierCateTree> listInfo = new PageInfo<>();
        listInfo.setEndRow(pageInfo.getEndRow());
        listInfo.setNavigatepageNums(pageInfo.getNavigatepageNums());
        listInfo.setNavigatePages(pageInfo.getNavigatePages());
        listInfo.setNextPage(pageInfo.getNextPage());
        listInfo.setOrderBy(pageInfo.getOrderBy());
        listInfo.setPageNum(pageInfo.getPageNum());
        listInfo.setPageSize(pageInfo.getPageSize());
        listInfo.setPrePage(pageInfo.getPrePage());
        listInfo.setSize(pageInfo.getSize());
        listInfo.setStartRow(pageInfo.getStartRow());
        listInfo.setTotal(pageInfo.getTotal());
        listInfo.setFirstPage(pageInfo.getFirstPage());
        listInfo.setLastPage(pageInfo.getLastPage());
        listInfo.setList(supplierCateTreeList);
        listInfo.setPages(pageInfo.getPages());
        return JdcgResult.ok(listInfo);
    }

}
