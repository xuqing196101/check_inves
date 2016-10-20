package ses.service.sms.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.TodosMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierTypeRelate;
import ses.service.sms.SupplierService;
import ses.util.Encrypt;


/**
 * @Title: SupplierServiceImpl
 * @Description: SupplierServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:14:04
 */
@Service(value = "supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierMapper supplierMapper;
	
	@Autowired
	private TodosMapper todosMapper;
	
	
	@Override
	public Supplier get(String id) {
		Supplier supplier = supplierMapper.getSupplier(id);
		List<SupplierTypeRelate> listSupplierTypeRelates = supplier.getListSupplierTypeRelates();
		String supplierTypeNames = "";
		for(int i = 0; i < listSupplierTypeRelates.size(); i++) {
			if (i > 0) {
				supplierTypeNames += ",";
			}
			supplierTypeNames += listSupplierTypeRelates.get(i).getSupplierTypeName();
		}
		supplier.setSupplierTypeNames(supplierTypeNames);
		return supplier;
	}
	
	/**
	 * @Title: register
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:13:42
	 * @Description: 供应商注册
	 * @param: @param supplier
	 * @param: @return
	 * @return: String
	 */
	@Override
	public Supplier register(Supplier supplier) {
		supplier.setPassword(Encrypt.e(supplier.getPassword()));// 密码 md5 加密
		supplier.setCreatedAt(new Date());
		supplier.setStatus(-1);
		supplier.setScore(0);
		supplierMapper.insertSelective(supplier);
		//System.out.println(1/0);
		return supplier;
	}
	
	/**
	 * @Title: perfectBasic
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:51:16
	 * @Description: 供应商完善基本信息
	 * @param: @param supplier
	 * @return: void
	 */
	@Override
	public void perfectBasic(Supplier supplier) {
		//Supplier oldSupplier = supplierMapper.selectByPrimaryKey(supplier.getId());
		//BeanUtils.copyProperties(supplier, oldSupplier, new String[] {"serialVersionUID", "id", "loginName", "mobile", "password", "createdAt"});
		supplier.setUpdatedAt(new Date());
		supplierMapper.updateByPrimaryKeySelective(supplier);
	}
	
	/**
	 * @Title: selectLastInsertId
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:15:57
	 * @Description: 获取最后插入的数据的 ID
	 * @param: @return
	 * @return: int
	 */
	@Override
	public String selectLastInsertId() {
		return supplierMapper.selectLastInsertId();
	}

	/**
	 * @Title: updateSupplierProcurementDep
	 * @author: Wang Zhaohua
	 * @date: 2016-10-20 下午6:55:52
	 * @Description: 供应商更新审核单位
	 * @param: @param supplier
	 * @return: void
	 */
	@Override
	public void updateSupplierProcurementDep(Supplier supplier) {
		supplierMapper.updateSupplierProcurementDep(supplier);
	}
	
	/**
	 * @Title: commit
	 * @author: Wang Zhaohua
	 * @date: 2016-10-20 下午6:56:27
	 * @Description: 供应商提交审核
	 * @param: @param supplier
	 * @param: @param user
	 * @return: void
	 */
	@Override
	public void commit(Supplier supplier, User user) {
		supplier.setStatus(0);
		supplierMapper.updateStatus(supplier);
		supplier = supplierMapper.getSupplier(supplier.getId());
		// 推送代办
		Todos todos = new Todos();
		todos.setCreatedAt(new Date());
		todos.setIsDeleted((short) 0);
		todos.setIsFinish((short) 0);
		todos.setName("供应商初审");
		todos.setReceiverId(supplier.getProcurementDepId());
		todos.setSenderId(user.getId());
		todos.setUndoType((short) 1);
		todos.setUrl("supplierAudit/essential.html?supplierId=" + supplier.getId());
		todosMapper.insert(todos);
	}
}
