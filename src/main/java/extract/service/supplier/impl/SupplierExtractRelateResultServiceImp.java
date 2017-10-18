/**
 * 
 */
package extract.service.supplier.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.util.UUIDUtils;
import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRecordMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.supplier.SupplierExtractCondition;
import extract.model.supplier.SupplierExtractResult;
import extract.model.supplier.SupplierVoiceResult;
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
		//预研项目
		if("advPro".equals(projectType) && arrayList.size()>0){
			supplierExtRelateMapper.insertAdv(arrayList);
			return;
		//真实项目
		}else if("relPro".equals(projectType)&& arrayList.size()>0){
			supplierExtRelateMapper.insertRel(arrayList);
			return;
		//支撑系统入口	
		}else if(StringUtils.isBlank(projectType)){
			supplierExtRelate.setId(UUIDUtils.getUUID32());
			supplierExtRelateMapper.insertSelective(supplierExtRelate);
			return;
		}
		
	}

	
	@Override
	public void saveOrUpdateVoiceResult(SupplierExtractCondition condition,
			List<Supplier> suppliers, List<SupplierVoiceResult> suppliers2,String projectType) {
		
		ArrayList<SupplierExtractResult> setSupplierExtractResult = setSupplierExtractResult(condition, suppliers, suppliers2);
		if(null == suppliers){
			//修改状态
			for (SupplierExtractResult supplierExtractResult : setSupplierExtractResult) {
				updateSupplierJoin(supplierExtractResult,projectType);
			}
		}else if(null == suppliers2){
			
			//保存
			for (SupplierExtractResult supplierExtractResult : setSupplierExtractResult) {
				saveResult(supplierExtractResult,projectType);
			}
			
		}
	}

	public ArrayList<SupplierExtractResult> setSupplierExtractResult(SupplierExtractCondition condition,
			List<Supplier> suppliers, List<SupplierVoiceResult> suppliers2) {
		String recordId = condition.getRecordId();
		String conditionId = condition.getId();
		String supplierType = condition.getSupplierTypeCode();
		
		
		ArrayList<SupplierExtractResult> arrayList = new ArrayList<>();
		if(null != suppliers){
			for (Supplier supplier : suppliers) {
				arrayList.add(new SupplierExtractResult(conditionId,supplier.getId(), recordId, null, supplierType, null, supplier.getArmyBuinessTelephone()));
			}
		}else{
			for (SupplierVoiceResult supplier : suppliers2) {
				arrayList.add(new SupplierExtractResult(conditionId,null, recordId, Short.valueOf(supplier.getJoin()), supplierType, null, supplier.getSupplierMobile()));
			}
		}
		
		return arrayList;
	}
	
	public void updateSupplierJoin(SupplierExtractResult supplierExtractResult,String projectType) {
		
		if("advPro".equals(projectType)){
			supplierExtRelateMapper.updateAdvSupplierJoin(supplierExtractResult);
		}else if("relPro".equals(projectType)){
			supplierExtRelateMapper.updateRelSupplierJoin(supplierExtractResult);
		}else if(StringUtils.isBlank(projectType)){
			supplierExtRelateMapper.updateSupplierJoin(supplierExtractResult);
		}
	}
	
}


