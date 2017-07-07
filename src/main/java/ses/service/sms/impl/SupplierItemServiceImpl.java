package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import common.utils.JdcgResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.ProductParamMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierProductsMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;
import ses.service.bms.CategoryService;
import ses.service.sms.SupplierItemService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import com.github.pagehelper.PageHelper;

import ses.util.StringUtil;

@Service(value = "supplierItemService")
public class SupplierItemServiceImpl implements SupplierItemService {

	@Autowired
	private SupplierItemMapper supplierItemMapper;
	
	@Autowired
	private CategoryService categoryService;
	
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
                	// 设置末级
                	item.setLevel("3");
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
        boolean threeAllEx = false;
        boolean twoAllEx = false;
        //一级节点
        List<SupplierItem> cateLIst = supplierItemMapper.getBySupplierIdCategoryId(supplierId, categoryId, type);

        for(SupplierItem s:cateLIst){
            //二级节点
            List<Category> categorylist = categoryService.findPublishTree(s.getCategoryId(),null);
            if(null != categorylist && !categorylist.isEmpty()){
                int secondRevanceNum = 0;
                for( Category c:categorylist){
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
                                                List<SupplierItem> cateLs = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cateFive.get(j).getId(),type);
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
                                                List<SupplierItem> cates = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cateFour.get(i).getId(),type);
                                                // 判断是否为空,不为空加入子节点
                                                if (cates != null && !cates.isEmpty()) {
                                                    list.add(cates.get(0));
                                                    supFourListexists.add(cates.get(0));
                                                }
                                            }
                                        }else{//没有子节点时显示本节点
                                            fiveAllEx = true;
                                            //去中间表查是否存在
                                            List<SupplierItem> cates = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cateFour.get(i).getId(),type);
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
                                        List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cs.getId(),type);
                                        // 判断是否为空,不为空加入子节点
                                        if (cateLst != null && cateLst.size() > 0) {
                                            list.add(cateLst.get(0));
                                            supThreeListexists.add(cateLst.get(0));
                                        }
                                    }
                                }else{
                                    fourAllEx = true;
                                    //去中间表查是否存在
                                    List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cs.getId(),type);
                                    // 判断是否为空,不为空加入子节点
                                    if (cateLst != null && cateLst.size() > 0) {
                                        list.add(cateLst.get(0));
                                        supThreeListexists.add(cateLst.get(0));
                                    }
                                }
                            }
                            //如果本节点下所有子节点都已勾选存在,则删除已添加的本级所有子节点,然后添加本节点
                            if(supThreeListexists.size()==cateThree.size() && fourAllEx){
                                threeAllEx = true;
                                for(int i=0;i<supThreeListexists.size();i++){
                                    list.remove(supThreeListexists.get(i));
                                }
                                //去中间表查是否存在
                                List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryId(supplierId, c.getId(),type);
                                // 判断是否为空,不为空加入子节点
                                if (cateLst != null && cateLst.size() > 0) {
                                    list.add(cateLst.get(0));
                                    secondRevanceNum ++;
                                }
                            }
                        }else{
                            threeAllEx = true;
                            //去中间表查是否存在
                            List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryId(supplierId, c.getId(),type);
                            // 判断是否为空,不为空加入子节点
                            if (cateLst != null && cateLst.size() > 0) {
                                list.add(cateLst.get(0));
                                secondRevanceNum ++;
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
                            List<SupplierItem> cateLs = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cateFive.get(j).getId(),type);
                            // 判断是否为空,不为空加入子节点
                            if (cateLs != null && !cateLs.isEmpty()) {
                                list.add(cateLs.get(0));
                            }
                        }
                    }else{
                        //去中间表查是否存在
                        List<SupplierItem> cates = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cateFour.get(i).getId(),type);
                        // 判断是否为空,不为空加入子节点
                        if (cates != null && !cates.isEmpty()) {
                            list.add(cates.get(0));
                        }
                    }
                }
            }else{
                //去中间表查是否存在
                List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cs.getId(),type);
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
		List<Category> cateList=new ArrayList<Category>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("supplierId", supplierId);
		map.put("type", type);
		List<SupplierItem> list = supplierItemMapper.findByMap(map);
		for(SupplierItem item:list){
			List<Category> last = categoryService.findPublishTree(item.getCategoryId(),null);
			if(last.size()<1){
				Category category = categoryService.selectByPrimaryKey(item.getCategoryId());
				category.setId(item.getId());
				cateList.add(category);	
			}
		}
		
		
		return cateList;
	}
	
	@Override
	public List<SupplierItem> findCategoryList(String supplierId, String type, Integer pageNum) {
	    if (pageNum != null) {
	        PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
	    }
	    Map<String, Object> param = new HashMap<String, Object>();
	    param.put("supplierId", supplierId);
	    param.put("type", type);
	    List<SupplierItem> itemsList = supplierItemMapper.selectByMap(param);
	    return itemsList;
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
//                      }
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

}
