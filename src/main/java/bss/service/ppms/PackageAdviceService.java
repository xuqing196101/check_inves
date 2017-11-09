package bss.service.ppms;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import ses.model.bms.User;
import bss.model.ppms.PackageAdvice;
import bss.model.ppms.Project;


/**
 * 
* <p>Title:PackageAdviceService </p>
* <p>Description: 中止/转竞谈 审核</p>
* @author FengTian
* @date 2017-10-25上午9:58:41
 */
public interface PackageAdviceService {

	void savaAudit(String projectId, String packageIds,String advice,String flowDefineId, String auditCode, String type, String userId);

	List<PackageAdvice> list(PackageAdvice packageAdvice, User user, Integer page);

	PackageAdvice audit(String code);
	
	List<PackageAdvice> find(HashMap<String, Object> map);

	void update(User user, List<PackageAdvice> find, String removedReason, Integer status);

	BigDecimal selectbudget(String code);

	void recheck(String packageIds);
	
}
