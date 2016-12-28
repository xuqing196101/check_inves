package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ProductParamMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierProductsMapper;
import ses.model.bms.Category;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;
import ses.service.bms.CategoryService;
import ses.service.sms.SupplierItemService;

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
		
//		String id = supplierItem.getSupplierId();
		
		
	//	supplierItemMapper.deleteBySupplierId(supplierItem.getSupplierId());
		if(supplierItem.getCategoryId()!=null){
			String categoryId = supplierItem.getCategoryId().trim();
			if(supplierItem.getCategoryId().trim().length()>0){
				String ids[] =categoryId.split(",");
				Map<String,Object> map=new HashMap<String,Object>();
				if(ids.length>=1){
					for(String i:ids){
						SupplierItem si = new SupplierItem();
						String cid = UUID.randomUUID().toString().replaceAll("-", "");
						si.setId(cid);
						si.setSupplierId(supplierItem.getSupplierId());
						si.setCategoryId(i);
						si.setCreatedAt(new Date());
						si.setSupplierTypeRelateId(supplierItem.getSupplierTypeRelateId());
						map.put("supplierId", supplierItem.getSupplierId());
						map.put("categoryId", supplierItem.getCategoryId());
						map.put("type", supplierItem.getSupplierTypeRelateId());
						List<SupplierItem> list = supplierItemMapper.findByMap(map);
						if(list.size()<1){
							supplierItemMapper.insertSelective(si);
						}
						
					}
				}
			}
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
	}

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
					   list.addAll(cateLst);
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
		 
		
 
 
	
	
}
