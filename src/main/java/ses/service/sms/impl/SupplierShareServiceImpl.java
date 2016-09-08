package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierShareMapper;
import ses.model.sms.SupplierInfo;
import ses.model.sms.SupplierShare;
import ses.service.sms.SupplierShareService;

/**
 * @Title: SupplierShareServiceImpl
 * @Description: SupplierShareServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午11:45:45
 */
@Service(value = "supplierShareService")
public class SupplierShareServiceImpl implements SupplierShareService {
	
	@Autowired
	private SupplierShareMapper supplierShareMapper;
	
	/**
	 * @Title: saveShare
	 * @author: Wang Zhaohua
	 * @date: 2016-9-8 上午11:46:28
	 * @Description: 保存股东信息
	 * @param: @param supplierInfo
	 * @return: void
	 */
	@Override
	public void saveShare(SupplierInfo supplierInfo) {
		List<SupplierShare> listSupplierShares = supplierInfo.getListSupplierShares();
		for (SupplierShare supplierShare : listSupplierShares) {
			supplierShareMapper.insertSelective(supplierShare);
		}
	}

}
