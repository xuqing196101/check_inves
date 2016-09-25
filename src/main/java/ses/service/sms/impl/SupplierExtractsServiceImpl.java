/**
 * 
 */
package ses.service.sms.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierExtRelateMapper;
import ses.dao.sms.SupplierExtUserMapper;
import ses.dao.sms.SupplierExtractsMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.sms.SupplierExtractsService;

/**
 * @Description:供应商抽取
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月18日下午2:03:38
 * @since  JDK 1.7
 */
@Service
public class SupplierExtractsServiceImpl implements SupplierExtractsService {

	@Autowired
	private SupplierExtractsMapper supplierExtractsMapper;
	@Autowired
	private SupplierExtRelateMapper supplierExtRelateMapper;
	@Autowired
	private SupplierExtUserMapper supplierExtUserMapper;
	/**
	 * 供应商信息
	 */
	@Autowired
	private SupplierMapper supplierMapper;
	/**
	 * @Description:插入
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午2:24:39  
	 * @param       
	 * @return void
	 */
	public String insert(Supplier supplier,SupplierCondition condition,String id,String ids){
		
		SupplierExtracts supplierExtracts=new SupplierExtracts();
		//抽取条件
		supplierExtracts.setExtractingConditions(condition.toString());
		//抽取地点
		supplierExtracts.setExtractionSites(condition.getLocality());
		//抽取时间
		supplierExtracts.setExtractionTime(new Date());
		//抽取人员
		supplierExtracts.setExtractsPeople(condition.getPeopleId());
		//抽取方式 1人工 2自动
		supplierExtracts.setExtractTheWay(new Short("0"));
		//抽取项目
		supplierExtracts.setProjectName(condition.getProjectName());
		if(id!=null&&!"".equals(id)){
			supplierExtracts.setId(id);
		}else{
			supplierExtractsMapper.insertSelective(supplierExtracts);
		}
		//插入监督人 
		String str[]=ids.split(",");
		SupplierExtUser extUser=null;
		for (String string : str) {
			extUser=new SupplierExtUser();
			extUser.setExtractsId(supplierExtracts.getId());
			extUser.setUserId(string);
			supplierExtUserMapper.insertSelective(extUser);
		}
		extUser=null;
		//条件查询供应商
		List<Supplier> supplierList = supplierMapper.findSupplier(supplier);
		for (Supplier suppliers : supplierList) {
			supplierExtRelateMapper.insert(new SupplierExtRelate(suppliers.getId(), supplierExtracts.getId()));
		}
		return supplierExtracts.getId();
	}
	
	/**
	 * @Description: 分页获取记录集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午2:25:09  
	 * @param @return      
	 * @return List<SupplierExtracts>
	 */
	public List<SupplierExtracts> listExtracts(SupplierExtracts supplierExtracts){
		return supplierExtractsMapper.listExtracts(supplierExtracts);
	}
	/**
	 * @Description: 查看抽取记录
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午2:44:14  
	 * @param @return      
	 * @return SupplierExtracts
	 */
	public SupplierExtracts showExtracts(){
		return null;
	}
}
