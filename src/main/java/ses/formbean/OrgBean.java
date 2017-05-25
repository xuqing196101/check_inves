package ses.formbean;

import java.util.ArrayList;
import java.util.List;

import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;

public class OrgBean {

	private List<Orgnization> orgList=new ArrayList<Orgnization>();
	
	private List<PurchaseDep> depList=new ArrayList<PurchaseDep>();

	public List<Orgnization> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Orgnization> orgList) {
		this.orgList = orgList;
	}

	public List<PurchaseDep> getDepList() {
		return depList;
	}

	public void setDepList(List<PurchaseDep> depList) {
		this.depList = depList;
	}
	
	
}
