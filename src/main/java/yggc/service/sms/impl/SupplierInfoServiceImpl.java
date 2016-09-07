package yggc.service.sms.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.sms.SupplierInfoMapper;
import yggc.model.sms.SupplierInfo;
import yggc.service.sms.SupplierInfoService;
import yggc.util.Encrypt;

/**
 * @Title: SupplierInfoServiceImpl
 * @Description: SupplierInfoServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:14:04
 */
@Service(value = "supplierInfoService")
public class SupplierInfoServiceImpl implements SupplierInfoService {

	@Autowired
	private SupplierInfoMapper supplierInfoMapper;
	
	/**
	 * @Title: register
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:13:42
	 * @Description: 供应商注册
	 * @param: @param supplierInfo
	 * @param: @return
	 * @return: String
	 */
	@Override
	public String register(SupplierInfo supplierInfo) {
		supplierInfo.setPassword(Encrypt.e(supplierInfo.getPassword()));// 密码 md5 加密
		supplierInfoMapper.insertSelective(supplierInfo);
		return supplierInfo.getId();
	}
	
	/**
	 * @Title: perfectBasic
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:51:16
	 * @Description: 供应商完善基本信息
	 * @param: @param supplierInfo
	 * @param: @return
	 * @return: String
	 */
	@Override
	public String perfectBasic(SupplierInfo supplierInfo) {
		SupplierInfo oldSupplierInfo = supplierInfoMapper.selectByPrimaryKey(supplierInfo.getId());
		BeanUtils.copyProperties(supplierInfo, oldSupplierInfo, new String[] {});
		return null;
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
		return supplierInfoMapper.selectLastInsertId();
	}


}
