package bss.service.pms;

import java.util.List;

import bss.model.pms.PurchaseManagement;

public interface PurchaseManagementService {

	public void add(PurchaseManagement purchaseManagement);
	
	List<PurchaseManagement> queryByMid(String mid,Integer page);
}
