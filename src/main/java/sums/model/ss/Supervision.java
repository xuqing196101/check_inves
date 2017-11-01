package sums.model.ss;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import ses.model.ems.Expert;
import ses.model.ems.ProExtSupervise;
import ses.model.sms.SupplierExtUser;
import iss.model.ps.Article;
import common.model.UploadFile;

import bss.model.cs.PurchaseContract;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SupplierCheckPass;
import bss.model.pqims.PqInfo;
import bss.model.prms.PackageExpert;

/**
 * 
* <p>Title:Supervision </p>
* <p>Description: 监督实体类</p>
* @author FengTian
* @date 2017-7-10下午2:46:20
 */
public class Supervision {
	
	/**
	 * 监督流程名称
	 */
	private String name;
	
	/**
	 * 项目实体
	 */
	private Project project;
	
	/**
	 * 预研项目实体
	 */
	private AdvancedProject advancedProject;
	
	/**
	 * 包实体
	 */
	private Packages packages;
	
	/**
	 * 预研包实体
	 */
	private AdvancedPackages advancedPackages;
	
	/**
	 * 上传实体
	 */
	private UploadFile uploadFile;
	
	/**
	 * 流程实体
	 */
	private FlowExecute flowExecute;
	
	/**
	 * 公告实体
	 */
	private Article article;
	
	/**
	 * 专家关联包实体
	 */
	private List<PackageExpert> packageExperts;
	
	/**
	 * 专家实体集合
	 */
	private List<Expert> expert;
	
	/**
	 * 谈判记录实体
	 */
	private NegotiationReport negotiationReport;
	
	/**
	 * 供应商抽取实体
	 */
	private	SupplierExtUser supplierExtUser;
	
	/**
	 * 专家抽取实体
	 */
	private ProExtSupervise proExtSupervise;
	
	/**
	 * 确认供应商实体
	 */
	private List<SupplierCheckPass> supplierCheckPass;
	
	/**
	 * 合同实体
	 */
	private PurchaseContract purchaseContract;
	
	/**
	 * 质检实体
	 */
	private PqInfo pqInfo;
	
	private String begin;
	
	private String end;
	
	private List<Entry<String, Object>> map;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Packages getPackages() {
		return packages;
	}

	public void setPackages(Packages packages) {
		this.packages = packages;
	}

	public UploadFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public FlowExecute getFlowExecute() {
		return flowExecute;
	}

	public void setFlowExecute(FlowExecute flowExecute) {
		this.flowExecute = flowExecute;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
	public List<PackageExpert> getPackageExperts() {
		return packageExperts;
	}

	public void setPackageExperts(List<PackageExpert> packageExperts) {
		this.packageExperts = packageExperts;
	}

	public List<Expert> getExpert() {
		return expert;
	}

	public void setExpert(List<Expert> expert) {
		this.expert = expert;
	}

	public NegotiationReport getNegotiationReport() {
		return negotiationReport;
	}

	public void setNegotiationReport(NegotiationReport negotiationReport) {
		this.negotiationReport = negotiationReport;
	}

	public SupplierExtUser getSupplierExtUser() {
		return supplierExtUser;
	}

	public void setSupplierExtUser(SupplierExtUser supplierExtUser) {
		this.supplierExtUser = supplierExtUser;
	}

	public ProExtSupervise getProExtSupervise() {
		return proExtSupervise;
	}

	public void setProExtSupervise(ProExtSupervise proExtSupervise) {
		this.proExtSupervise = proExtSupervise;
	}

	public List<SupplierCheckPass> getSupplierCheckPass() {
		return supplierCheckPass;
	}

	public void setSupplierCheckPass(List<SupplierCheckPass> supplierCheckPass) {
		this.supplierCheckPass = supplierCheckPass;
	}

	public PurchaseContract getPurchaseContract() {
		return purchaseContract;
	}

	public void setPurchaseContract(PurchaseContract purchaseContract) {
		this.purchaseContract = purchaseContract;
	}
	
	public AdvancedProject getAdvancedProject() {
		return advancedProject;
	}

	public void setAdvancedProject(AdvancedProject advancedProject) {
		this.advancedProject = advancedProject;
	}

	public AdvancedPackages getAdvancedPackages() {
		return advancedPackages;
	}

	public void setAdvancedPackages(AdvancedPackages advancedPackages) {
		this.advancedPackages = advancedPackages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public PqInfo getPqInfo() {
		return pqInfo;
	}

	public void setPqInfo(PqInfo pqInfo) {
		this.pqInfo = pqInfo;
	}

	public List<Entry<String, Object>> getMap() {
		return map;
	}

	public void setMap(List<Entry<String, Object>> map) {
		this.map = map;
	}

}
