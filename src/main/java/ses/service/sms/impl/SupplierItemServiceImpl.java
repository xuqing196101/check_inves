package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import bss.util.WordUtil;

import ses.dao.sms.ProductParamMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierProductsMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;
import ses.service.bms.CategoryService;
import ses.service.sms.SupplierItemService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import common.constant.StaticVariables;

@Service(value = "supplierItemService")
public class SupplierItemServiceImpl implements SupplierItemService {

	@Autowired
	private SupplierItemMapper supplierItemMapper;
	
	@Autowired
	private SupplierProductsMapper supplierProductsMapper;
	
	@Autowired
	private ProductParamMapper productParamMapper;

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
	public void saveOrUpdate(SupplierItem supplierItem) {
	    String categoryId = supplierItem.getCategoryId();
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
    //		list.addAll(cateLIst);
    
    	for(SupplierItem s:cateLIst){
    	   //二级节点
    	   List<Category> categorylist = categoryService.findPublishTree(s.getCategoryId(),null);
    	   for( Category c:categorylist){
    		   //查询所有的三级节点
    		   List<Category> cateThree = categoryService.findPublishTree(c.getId(),null);
    		   //去中间表查是否存在
    		   for(Category cs:cateThree){
    			   List<SupplierItem> cateLst = supplierItemMapper.getBySupplierIdCategoryId(supplierId, cs.getId(),type);
    			   //list.addAll(cateLst);
    			   // 判断是否为空,不为空加入子节点
    			   if (cateLst != null && cateLst.size() > 0) {
    			       list.add(cateLst.get(0));
    			   }
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
    public void deleteItems(SupplierItem supplierItem) {
        String categoryId = supplierItem.getCategoryId();
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
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("supplierId", supplierItem.getSupplierId());
            param.put("type", supplierItem.getSupplierTypeRelateId());
            List<SupplierItem> allCategory = supplierItemMapper.findByMap(param);
            String parentId = current.getParentId();
            if (parentId != null) {
                out:while (true) {
                    boolean flag = false;
                    in:for (SupplierItem category : allCategory) {
                        Category node = categoryService.findById(category.getCategoryId());
                        if (node != null) {
                            if (parentId.equals(node.getParentId())) {
                                List<Category> childNodes = categoryService.findPublishTree(category.getCategoryId(), null);
                                if (childNodes == null || childNodes.size() == 0) {
                                    flag = true;
                                    break in;
                                }
                            }
                        }
                    }
                    if (!flag) {
                        map.put("categoryId", parentId);
                        supplierItemMapper.deleteByMap(map);
                    }
                    Category category = categoryService.findById(parentId);
                    if (category == null) {
                        break out;
                    } else {
                        parentId = category.getParentId();
                    }
                }
            }
        } else {
            map.put("categoryId", categoryId);
            supplierItemMapper.deleteByMap(map);
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
            supplierItemMapper.updateByPrimaryKeySelective(item);
        }
    }		 
	
}
