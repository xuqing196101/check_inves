package sums.service.ss;

import java.util.List;

import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;

public interface ProjectSupervisionService {
	
	Integer contractStatus(String id);
	
	List<Packages> viewPack(String projectId);
	
	List<PurchaseDetail> viewPlanDetail(String projectId, String planId);
	
	List<PurchaseRequired> viewDeandDetail(String projectId, String detailId);

}
