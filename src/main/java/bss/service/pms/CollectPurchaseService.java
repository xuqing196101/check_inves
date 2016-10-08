package bss.service.pms;

import java.util.List;

import bss.model.pms.CollectPurchase;

public interface CollectPurchaseService {

	List<CollectPurchase> queryByNo(String planNo);
	
	void add(CollectPurchase collectPurchase);
	
	List<String> getNo(String planNo);
	
	List<String> getId(String Id);
}
