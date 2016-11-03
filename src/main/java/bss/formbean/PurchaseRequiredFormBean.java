package bss.formbean;

import java.util.List;

import bss.model.pms.PurchaseAudit;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.ProjectDetail;
/**
 * 
 * @Title: PurchaseRequiredFormBean
 * @Description: 对采购需求类重新封装 
 * @author Li Xiaoxiao
 * @date  2016年9月13日,下午4:54:18
 *
 */
public class PurchaseRequiredFormBean {
	private List<PurchaseRequired> list;
	
	private List<ProjectDetail> lists;

	private List<PurchaseAudit> audit;
	
	public List<PurchaseRequired> getList() {
		return list;
	}

	public void setList(List<PurchaseRequired> list) {
		this.list = list;
	}

    public List<ProjectDetail> getLists() {
        return lists;
    }

    public void setLists(List<ProjectDetail> lists) {
        this.lists = lists;
    }

	public List<PurchaseAudit> getAudit() {
		return audit;
	}

	public void setAudit(List<PurchaseAudit> audit) {
		this.audit = audit;
	}
	
 

}
