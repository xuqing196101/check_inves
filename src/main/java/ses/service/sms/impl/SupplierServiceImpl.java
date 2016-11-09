package ses.service.sms.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.sms.SupplierAuditMapper;
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
	
	@Autowired
	private SupplierAuditMapper supplierAuditMapper;
	
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
        String random = RandomStringUtils.randomAlphanumeric(15);
        pwd = md5.encodePassword(pwd, random);
        user.setLoginName(supplier.getLoginName());
        user.setRandomCode(random);
        user.setPassword(pwd);
        user.setTypeName(4);
        user.setIsDeleted(0);
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
		if (supplier.getStatus() == 7) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("isDeleted", 1);
			param.put("supplierId", supplier.getId());
			supplierAuditMapper.updateByMap(param);
			todosMapper.updateIsFinish(new Todos("supplier/return_edit.html?id="+ supplier.getId()));
		}
		supplier.setStatus(0);
		supplierMapper.updateByPrimaryKeySelective(supplier);
		supplier = supplierMapper.getSupplier(supplier.getId());
		// 推送代办
		Todos todos = new Todos();
		todos.setSenderId(supplier.getId());// 推送者 ID
		todos.setName("供应商初审 !");// 待办名称
		todos.setOrgId(supplier.getProcurementDepId());// 机构ID
		todos.setPowerId(PropUtil.getProperty("gyscs"));// 权限 ID
		todos.setUrl("supplierAudit/essential.html?supplierId=" + supplier.getId());// URL
		todos.setUndoType((short) 1);// 类型
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
	/**
	 * @Title: checkLogin
	 * @author: Wang Zhaohua
	 * @date: 2016-11-7 下午1:37:12
	 * @Description: 校验是否登录
	 * @param: @param user
	 * @param: @return
	 * @return: Map<String,Integer>
	 */
	@Override
	public Map<String, Object> checkLogin(User user) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginName", user.getLoginName());
		Supplier supplier = supplierMapper.getByMap(param);
		Map<String, Object> map = new HashMap<String, Object>();
		Integer status = supplier.getStatus();
		if (status == -1) {
			map.put("status", "信息未提交, 请提交审核 !");
		} else if (status == 0 || status == 8) {
			map.put("status", "信息待初审, 请等待审核 !");
		} else if (status == 1) {
			map.put("status", "信息待复审, 请等待审核 !");
		} else if (status == 2) {
			map.put("status", "初审未通过 !");
		} else if (status == 3) {
			map.put("status", "success");
			map.put("supplier", supplier);
		} else if (status == 4) {
			map.put("status", "复审未通过 !");
		} else if (status == 5) {
			map.put("status", "信息初审中, 请等待审核 !");
		} else if (status == 6) {
			map.put("status", "信息复审中, 请等待审核 !");
		} else if (status == 7) {
			map.put("status", "success");
			map.put("supplier", supplier);
		}
		return map;
	}
}
