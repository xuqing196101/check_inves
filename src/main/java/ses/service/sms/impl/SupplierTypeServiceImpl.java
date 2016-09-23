package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierTypeMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.sms.SupplierType;
import ses.model.sms.SupplierTypeTree;
import ses.service.sms.SupplierTypeService;

/**
 * @Title: SupplierTypeServiceImpl
 * @Description: 供应商类型实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午2:16:50
 */
@Service(value = "supplierTypeService")
public class SupplierTypeServiceImpl implements SupplierTypeService {
	
	@Autowired
	private SupplierTypeMapper supplierTypeMapper;
	
	@Autowired
	private SupplierTypeRelateMapper supplierTypeRelateMapper;
	
	/**
	 * @Title: findSupplierType
	 * @author: Wang Zhaohua
	 * @date: 2016-9-18 下午2:48:08
	 * @Description: 查询供应商所有类型
	 * @param: @param supplierId
	 * @param: @return
	 * @return: List<SupplierTypeTree>
	 */
	@Override
	public List<SupplierTypeTree> findSupplierType(String supplierId) {
		// 查询供应商所有类型
		List<SupplierType> listSupplierTypes = supplierTypeMapper.findSupplierType();
		
		// 查询供应商勾选类型
		List<String> listSupplierTypeIds = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplierId);
		
		List<SupplierTypeTree> listSupplierTypeTrees = new ArrayList<SupplierTypeTree>();
		for (SupplierType supplierType : listSupplierTypes) {
			SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
			supplierTypeTree.setId(supplierType.getId());
			supplierTypeTree.setParentId(supplierType.getParentId());
			supplierTypeTree.setName(supplierType.getName());
			if (listSupplierTypeIds.contains(supplierType.getId())) {
				supplierTypeTree.setChecked(true);
			}
			listSupplierTypeTrees.add(supplierTypeTree);
		}
		return listSupplierTypeTrees;
	}

	/**
	 * @Title: findSupplierType
	 * @author: Wang Zhaohua
	 * @date: 2016-9-18 下午2:48:08
	 * @Description: 查询所有类型
	 * @param: @param supplierId
	 * @param: @return
	 * @return: List<SupplierTypeTree>
	 */
	@Override
	public List<SupplierType> findSupplierType() {
		return supplierTypeMapper.findSupplierType();
	}

}
