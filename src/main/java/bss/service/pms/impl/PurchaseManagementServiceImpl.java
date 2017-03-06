package bss.service.pms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.pms.PurchaseManagementMapper;
import bss.model.pms.PurchaseManagement;
import bss.service.pms.PurchaseManagementService;
@Service
public class PurchaseManagementServiceImpl  implements PurchaseManagementService{

	@Autowired
	private PurchaseManagementMapper purchaseManagementMapper;
	
	@Override
	public void add(PurchaseManagement purchaseManagement) {
		purchaseManagementMapper.insertSelective(purchaseManagement);	
	}

	@Override
	public List<PurchaseManagement> queryByMid(String mid,Integer page) {
		// TODO Auto-generated method stub
		 PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
		 List<PurchaseManagement> list = purchaseManagementMapper.queryByMid(mid);
		return list;
	}

	@Override
	public List<PurchaseManagement> queryByPid(String pid) {
		// TODO Auto-generated method stub
		return purchaseManagementMapper.queryByPid(pid);
	}

	@Override
	public void updateStatus(String uniqueId) {
		purchaseManagementMapper.updateStaus(uniqueId);
		
	}
	
 
	

}
