/**
 * 
 */
package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAgentsMapper;
import ses.dao.sms.SupplierConditionMapper;
import ses.dao.sms.SupplierExtRelateMapper;
import ses.dao.sms.SupplierExtractsMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import ses.service.sms.SupplierExtRelateService;

/**
 * @Description:供应商抽取关联
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月20日下午4:17:22
 * @since  JDK 1.7
 */
@Service
public class SupplierExtRelateServiceImpl implements SupplierExtRelateService {
	@Autowired
	SupplierExtRelateMapper supplierExtRelateMapper;
	@Autowired
	SupplierConditionMapper conditionMapper;
	@Autowired
	SupplierMapper supplierMapper;
	@Autowired
	SupplierExtractsMapper supplierExtractsMapper;
	/**
	 * @Description:insert
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午4:12:09  
	 * @param       
	 * @return void
	 */
	@Override
	public String insert(String cId,String userid) {
		//获取查询条件
		List<SupplierCondition> list = conditionMapper.list(new SupplierCondition(cId, ""));
		if(list!=null&&list.size()!=0){
			SupplierCondition show=list.get(0);
			//给专家set查询条件
			Supplier supplier=new Supplier();
			supplier.setAddress(show.getAddress());
			//		expert.setBirthday(birthday);
//			supplier.setExpertsFrom();
			//查询专家集合
			List<Supplier> selectAllExpert =supplierMapper.getAllSupplier(null);
			//循环吧查询出的专家集合insert到专家记录表和专家关联的表中
			SupplierExtRelate supplierExtRelate=null;
			for (Supplier supplier2 : selectAllExpert) {
				supplierExtRelate=new SupplierExtRelate();
				//供应商id
				supplierExtRelate.setSupplierId(supplier2.getId());
				//项目id
				supplierExtRelate.setProjectId(show.getProjectId());
				//条件表id
				supplierExtRelate.setSupplierConditionId(show.getId());
				supplierExtRelate.setIsDeleted((short)0);
				supplierExtRelate.setOperatingType((short)0);
				//插入supplierExtRelate
				supplierExtRelateMapper.insertSelective(supplierExtRelate);
			}
		}
		return "";
	}

	/**
	 * @Description:集合展示
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午6:07:39  
	 * @param @param projectExtract      
	 * @return void
	 */
	@Override
	public List<SupplierExtRelate> list(SupplierExtRelate projectExtract) {

		return supplierExtRelateMapper.list(projectExtract);

	}
	/**
	 * @Description:修改操作状态
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午8:02:39  
	 * @param @param projectExtract      
	 * @return void
	 */
	@Override
	public void update(SupplierExtRelate projectExtract) {

		supplierExtRelateMapper.updateByPrimaryKeySelective(projectExtract);

	}
}
