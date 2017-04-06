package bss.formbean;

import java.util.List;

import ses.model.ems.ExpertSignature;
import ses.model.sms.SupplierSignature;

import bss.model.pms.PurchaseAudit;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.ProjectDetail;
import bss.model.prms.PackageExpert;
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
	
	private List<AdvancedDetail> detail;
	
	private List<PackageExpert> packageExperts;
	
	private List<PurchaseDetail> listDetail;
	
	private List<ExpertSignature> expertSignatureList;
	
	private List<SupplierSignature> supplierSignatureList;
	
	
	
	public List<SupplierSignature> getSupplierSignatureList() {
		return supplierSignatureList;
	}

	public void setSupplierSignatureList(
			List<SupplierSignature> supplierSignatureList) {
		this.supplierSignatureList = supplierSignatureList;
	}

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

    public List<AdvancedDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<AdvancedDetail> detail) {
        this.detail = detail;
    }

    public List<PackageExpert> getPackageExperts() {
      return packageExperts;
    }

    public void setPackageExperts(List<PackageExpert> packageExperts) {
      this.packageExperts = packageExperts;
    }

	public List<PurchaseDetail> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<PurchaseDetail> listDetail) {
		this.listDetail = listDetail;
	}

	public List<ExpertSignature> getExpertSignatureList() {
		return expertSignatureList;
	}

	public void setExpertSignatureList(List<ExpertSignature> expertSignatureList) {
		this.expertSignatureList = expertSignatureList;
	}
    
    
}
