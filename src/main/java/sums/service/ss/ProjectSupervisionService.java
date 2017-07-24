package sums.service.ss;

import java.util.List;

import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;

public interface ProjectSupervisionService {
	
	
	/**
	 * 
	* @Title: contractStatus
	* @author FengTian 
	* @date 2017-7-17 上午10:58:18  
	* @Description: 合同状态 
	* @param @param id
	* @param @return      
	* @return Integer
	 */
	Integer contractStatus(String id);
	
	/**
	 * 
	* @Title: viewPack
	* @author FengTian 
	* @date 2017-7-17 上午10:58:38  
	* @Description: 分包 
	* @param @param projectId
	* @param @return      
	* @return List<Packages>
	 */
	List<Packages> viewPack(String projectId);
	
	/**
	 * 
	* @Title: viewPlanDetail
	* @author FengTian 
	* @date 2017-7-17 上午10:58:49  
	* @Description: 查询计划明细 
	* @param @param projectId
	* @param @param planId
	* @param @return      
	* @return List<PurchaseDetail>
	 */
	List<PurchaseDetail> viewPlanDetail(String projectId, String planId);
	
	/**
	 * 
	* @Title: viewDeandDetail
	* @author FengTian 
	* @date 2017-7-17 上午10:58:59  
	* @Description: 查询采购需求明细 
	* @param @param projectId
	* @param @param detailId
	* @param @return      
	* @return List<PurchaseRequired>
	 */
	List<PurchaseRequired> viewDeandDetail(String projectId, String detailId);

}
