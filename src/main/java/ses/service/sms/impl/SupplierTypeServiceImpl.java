package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierTypeMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierType;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.DictionaryDataServiceI;
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
    private DictionaryDataServiceI dictionaryDataService;
	
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
		DictionaryData dictionaryData=new DictionaryData();
		dictionaryData.setKind(6);
		List<DictionaryData> ldlist1 = dictionaryDataService.find(dictionaryData);
		dictionaryData.setKind(8);
		List<DictionaryData> ldlist2 = dictionaryDataService.find(dictionaryData);
//		List<SupplierType> listSupplierTypes = supplierTypeMapper.findSupplierType();
		
		// 查询供应商勾选类型
//		List<SupplierTypeRelate> listSupplierTypeIds = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplierId);
		
		List<SupplierTypeTree> listSupplierTypeTrees = new ArrayList<SupplierTypeTree>();
		for (DictionaryData dd : ldlist2) {
			SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
			supplierTypeTree.setId(dd.getCode());
			supplierTypeTree.setName(dd.getName());
			listSupplierTypeTrees.add(supplierTypeTree);
		}
		for (DictionaryData dd : ldlist1) {
			SupplierTypeTree supplierTypeTree = new SupplierTypeTree();
			supplierTypeTree.setId(dd.getCode());
			supplierTypeTree.setName(dd.getName());
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
