/**
 * 
 */
package extract.service.supplier.impl;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMapper;
import ses.util.UUIDUtils;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRecordMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.supplier.SupplierExtractResult;
import extract.service.supplier.SupplierExtractRelateResultService;

/**
 * @Description:供应商抽取关联
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月20日下午4:17:22
 * @since  JDK 1.7
 */
@Service
public class SupplierExtractRelateResultServiceImp implements SupplierExtractRelateResultService {
  @Autowired
  SupplierExtractRelateResultMapper supplierExtRelateMapper;
  @Autowired
  SupplierExtractConditionMapper conditionMapper;
  @Autowired
  SupplierMapper supplierMapper;
  @Autowired
  SupplierExtractRecordMapper supplierExtractsMapper;
  
  /**
   * 存储结果
   */
	@Override
	public void saveResult(SupplierExtractResult supplierExtRelate,String projectType) {
		
		ArrayList<SupplierExtractResult> arrayList = new ArrayList<>();
		String[] packageIds = supplierExtRelate.getPackageIds();
		if(null!=packageIds){
			for (String packageId : packageIds) {
				SupplierExtractResult supplierExtractResult = new SupplierExtractResult();
				try {
					BeanUtils.copyProperties(supplierExtRelate,supplierExtractResult,new String[] {"serialVersionUID"});
				} catch (Exception e) {
					e.printStackTrace();
				}
				supplierExtractResult.setId(UUIDUtils.getUUID32());
				supplierExtractResult.setPackageId(packageId);
				arrayList.add(supplierExtractResult);
			}
		}
		if("advPro".equals(projectType) && arrayList.size()>0){
			supplierExtRelateMapper.insertAdv(arrayList);
			return;
		}else if("relPro".equals(projectType)&& arrayList.size()>0){
			supplierExtRelateMapper.insertRel(arrayList);
			return;
		}else if(StringUtils.isBlank(projectType)){
			supplierExtRelate.setId(UUIDUtils.getUUID32());
			supplierExtRelateMapper.insertSelective(supplierExtRelate);
			return;
		}
		
	}
	
}


