package ses.service.sms.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierTypeRelate;
import ses.service.sms.SupplierService;
import ses.util.Encrypt;
import ses.util.PropUtil;


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
	
	@Autowired
	private UserMapper userMapper;
	
	
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
		String pwd = supplier.getPassword();
		
		supplier.setPassword(Encrypt.e(pwd));// 密码 md5 加密
		supplier.setCreatedAt(new Date());
		supplier.setStatus(-1);
		supplier.setScore(0);
		supplierMapper.insertSelective(supplier);
		
		// 插入到用户表一份
		User user = new User();
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        md5.setEncodeHashAsBase64(false);
        pwd = md5.encodePassword(pwd, RandomStringUtils.randomAlphanumeric(15));
        user.setLoginName(supplier.getLoginName());
        user.setPassword(pwd);
        user.setTypeName(5);
        userMapper.insertSelective(user);
        
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
	public void commit(Supplier supplier) {
		supplier.setStatus(0);
		supplierMapper.updateByPrimaryKeySelective(supplier);
		supplier = supplierMapper.getSupplier(supplier.getId());
		// 推送代办
		Todos todos = new Todos();
		todos.setSenderId(supplier.getId());
		todos.setName("供应商初审");
		todos.setOrgId(supplier.getProcurementDepId());
		todos.setPowerId(PropUtil.getProperty("gysdb"));
		todos.setUrl("supplierAudit/essential.html?supplierId=" + supplier.getId());
		todos.setUndoType((short) 1);
		todosMapper.insertSelective(todos);
	}
	
	/**
	 * @Title: checkLoginName
	 * @author: Wang Zhaohua
	 * @date: 2016-11-6 下午5:09:03
	 * @Description: 校验 loginName 是否重复
	 * @param: @param loginName
	 * @param: @return
	 * @return: boolean
	 */
	@Override
	public boolean checkLoginName(String loginName) {
		List<String> list = supplierMapper.findLoginName();
		if (list.contains(loginName)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Supplier> selectSupplierByProjectId(String projectId) {
		List<Supplier> list = supplierMapper.selectByProjectId(projectId);
		return list;
	}
}
