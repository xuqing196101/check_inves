/**
 * 
 */
package extract.service.supplier.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtRelate;
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
		//处理操作对象，将不同结果放在SupplierExtractResult 同一个对象中
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

	/**
	 * 
	 * <简述>处理修改或添加的操作对象 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-2下午8:35:36
	 * @param condition
	 * @param suppliers
	 * @param suppliers2
	 * @return
	 */
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
				arrayList.add(new SupplierExtractResult(conditionId,null, recordId, Short.valueOf(supplier.getJoin()), supplierType, null, supplier.getSupplierId()));
			}
		}
		
		return arrayList;
	}
	
	/**
	 * 修改供应商参加状态
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-2下午8:35:56
	 * @param supplierExtractResult
	 * @param projectType
	 */
	public void updateSupplierJoin(SupplierExtractResult supplierExtractResult,String projectType) {
		
		if("advPro".equals(projectType)){
			supplierExtRelateMapper.updateAdvSupplierJoin(supplierExtractResult);
		}else if("relPro".equals(projectType)){
			supplierExtRelateMapper.updateRelSupplierJoin(supplierExtractResult);
		}else if(StringUtils.isBlank(projectType)){
			supplierExtRelateMapper.updateSupplierJoin(supplierExtractResult);
		}
	}


	@Override
	public List<SupplierExtractResult> selectExtractResults(String conditionId) {
		List<SupplierExtractResult> extractResults = supplierExtRelateMapper.selectByConditionId(conditionId);
		return extractResults;
	}


	@Override
	public SupplierExtractResult selectById(String id) {
		return supplierExtRelateMapper.selectByPrimaryKey(id);
	}

	@Override
	public SupplierExtractResult selectAdvById(String id) {
		return supplierExtRelateMapper.selectAdvByPrimaryKey(id);
	}
	
	@Override
	public void updateByPrimaryKeySelective(SupplierExtractResult result) {
		supplierExtRelateMapper.updateByPrimaryKeySelective(result);
	}


	@Override
	public void insertSelective(SupplierExtractResult result) {
		supplierExtRelateMapper.insertSelective(result);
	}


	@Override
	public List<SupplierExtractResult> selectByUpdateDate(Map<String, String> map) {
		return supplierExtRelateMapper.selectByUpdateDate(map);
	}
	
	@Override
	public List<SupplierExtRelate> selectByAdvUpdateDate(Map<String, String> map) {
		return supplierExtRelateMapper.selectAdvByUpdateDate(map);
	}


	@Override
	public void updateAdvByPrimaryKeySelective(SupplierExtractResult result) {
		supplierExtRelateMapper.updateAdvByPrimaryKeySelective(result);
	}


	@Override
	public void insertAdvSelective(SupplierExtractResult result) {
		supplierExtRelateMapper.insertAdvSelective(result);
	}
}


