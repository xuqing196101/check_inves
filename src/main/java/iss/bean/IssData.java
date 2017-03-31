package iss.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ses.model.ems.Expert;
import ses.model.sms.Supplier;
import ses.service.ems.ExpertService;
import ses.service.sms.SupplierService;
import synchro.util.SpringBeanUtil;

/*
 *@Title:IssData
 *@Description:首页信息类,在自定义jstl标签中使用
 *@author tianzhiqiang
 *@date 2017-3-30 15:50:22
 */

public class IssData {

	// 专家名录
	public static List<Expert> getExpertList() {
		Expert expert=new Expert();
		ExpertService expertService=SpringBeanUtil.getBean(ExpertService.class);
		 //只显示公开的
		expert.setIsPublish(1);
        List<Expert> expertList = expertService.selectAllExpert(10, expert);
        return expertList;
	}
	
	//供应商名录
	public static List<Supplier> getSupplierList() {
		SupplierService suppService=SpringBeanUtil.getBean(SupplierService.class);
        Map<String, Object> sMap = new HashMap<String, Object>();
        //只显示公开的
        sMap.put("IS_PUBLISH", 1);
        List<Supplier> supplierList = suppService.query(sMap);
        return supplierList;
	}
	
	//测试方法
	 public static String sayHello(String name) {  
	      return "Hello " + name;  
	 }  

}
