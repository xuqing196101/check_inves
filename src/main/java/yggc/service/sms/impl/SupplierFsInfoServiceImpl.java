package yggc.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.sms.SupplierFsInfoMapper;
import yggc.model.sms.SupplierFsInfoWithBLOBs;
import yggc.service.sms.SupplierFsInfoService;

/**
* <p>Title:SupplierFsInfoServiceImpl </p>
* <p>Description:进口供应商服务层实现 </p> 
* @author Song Biaowei
* @date 2016-9-7下午5:55:01
 */

@Service
public class SupplierFsInfoServiceImpl implements SupplierFsInfoService {
	@Autowired
	private SupplierFsInfoMapper sfiMapper;
	
	@Override
	public void register(SupplierFsInfoWithBLOBs sfi) {
		sfiMapper.insertSelective(sfi);
	}

	@Override
	public void updateRegisterInfo(SupplierFsInfoWithBLOBs sfi) {
		sfiMapper.updateByPrimaryKeySelective(sfi);
	}

	@Override
	public SupplierFsInfoWithBLOBs findById(String id) {
		SupplierFsInfoWithBLOBs sfi=sfiMapper.selectByPrimaryKey(id);
		return sfi;
	}

	@Override
	public int getCount(SupplierFsInfoWithBLOBs sfi) {
		int count=sfiMapper.getCount(sfi);
		return count;
	}

	@Override
	public List<SupplierFsInfoWithBLOBs> selectByFsInfo(
			SupplierFsInfoWithBLOBs sfi) {
		List<SupplierFsInfoWithBLOBs> sfiList=sfiMapper.selectByFsInfo(sfi);
		return sfiList;
	}

	@Override
	public SupplierFsInfoWithBLOBs selectByPrimaryKey(
			SupplierFsInfoWithBLOBs sfi) {
		SupplierFsInfoWithBLOBs supplierFsInfoWithBLOBs=sfiMapper.selectByPrimaryKey(sfi.getId());
		return supplierFsInfoWithBLOBs;
	}

	@Override
	public String selectIdByLoginName(SupplierFsInfoWithBLOBs sfi) {
		String id=sfiMapper.selectIdByLoginName(sfi);
		return id;
	}

}
