package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.constant.StaticVariables;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierTypeRelate;
import ses.service.sms.SupplierReviewService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

@Service(value = "/supplierReviewService")
public class SupplierReviewImpl implements SupplierReviewService{

	@Autowired
	private SupplierMapper supplierMapper;
	
	@Autowired
	private SupplierTypeRelateMapper supplierTypeRelateMapper;
	
	@Override
	public List<Supplier> selectReviewList(Supplier supplier, Integer page) {
		if(page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
		
		List<Supplier> supplierList = supplierMapper.selectReviewList(supplier);

        //供应商类型转换
        getSupplierType(supplierList);
		return supplierList;
	}
	
	/**
	 * 类型转换
	 * @param pageInfo
	 * @return
	 */
	private List<Supplier> getSupplierType(List<Supplier> supplierList) {
		for(Supplier supplier: supplierList) {
			List < SupplierTypeRelate > relaList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplier.getId());
			String typeName = "";
			for(SupplierTypeRelate str: relaList) {
				DictionaryData dd = DictionaryDataUtil.get(str.getSupplierTypeId());
				if(dd != null) {
					typeName += dd.getName() + StaticVariables.COMMA_SPLLIT;
				}
			}
			if(typeName.contains(StaticVariables.COMMA_SPLLIT)) {
				typeName = typeName.substring(0, typeName.length() - 1);
			}
			supplier.setSupplierTypeNames(typeName);
		}
		return supplierList;
	}
}
