package bss.model.pms;

import java.util.List;

/**
 * 
 * @Title:AuditPersonList 
 * @Description: 审核人员集合
 * @author Liyi
 * @date 2016-12-25下午5:27:32
 *
 */
public class AuditPersonList {
	private List<AuditPerson> auditPersons;

	public List<AuditPerson> getAuditPersons() {
		return auditPersons;
	}

	public void setAuditPersons(List<AuditPerson> auditPersons) {
		this.auditPersons = auditPersons;
	}
	
	
}