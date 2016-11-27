package ses.service.sms.impl;

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
import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierProducts;
import ses.service.sms.SupplierItemService;

@Service(value = "supplierItemService")
public class SupplierItemServiceImpl implements SupplierItemService {

	@Autowired
	private SupplierItemMapper supplierItemMapper;
	
	@Autowired
	private SupplierProductsMapper supplierProductsMapper;
	
	@Autowired
	private ProductParamMapper productParamMapper;

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
		
		String id = supplierItem.getSupplierId();
		supplierItemMapper.deleteBySupplierId(id);
		String ids[] = supplierItem.getCategoryId().split(",");
		for(String i:ids){
			SupplierItem si = new SupplierItem();
			String cid = UUID.randomUUID().toString().replaceAll("-", "");
			si.setId(cid);
			si.setSupplierId(supplierItem.getSupplierId());
			si.setCategoryId(i);
			si.setCreatedAt(new Date());
			supplierItemMapper.insertSelective(si);
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
}
