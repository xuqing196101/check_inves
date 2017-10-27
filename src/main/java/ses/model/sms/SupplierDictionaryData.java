package ses.model.sms;

import java.io.Serializable;

public class SupplierDictionaryData implements Serializable {

	private static final long serialVersionUID = -1590701894388228369L;

	private String supplierTaxCert;  //供应商近三个月完税凭证

	private String supplierBillCert;  //供应商近三年银行基本账户年末对账单

	private String supplierSecurityCert;  //供应商近三个月缴纳社会保险金凭证

	private String supplierBearchCert; //供应商近三年内无重大违法记录声明

	private String supplierLevel;  //供应商分级方法

	private String supplierPledge;  //供应商承诺书

	private String supplierRegList;  //供应商入库申请表

	private String supplierExtractsList;  //供应商抽取记录表

	private String supplierInspectList;  //供应商考察记录表

	private String supplierReviewList;  //供应商考察廉政意见函

	private String supplierExitList;  //供应商退库申请表

	private String supplierChangeList;  //供应商变更申请表

	private String supplierAuditOpinion;  //供应商财务审计报告书中的审计报告

	private String supplierLiabilities;  //供应商财务资产负债表

	private String supplierProfit;  //供应商财务利润表

	private String supplierCashFlow;  //供应商财务现金流量表

	private String supplierOwnerChange;  //供应商财务所有者权益变动表

	private String supplierBusinessCert;  //供应商营业执照

	private String supplierProCert;   //供应商物资生产资质证书

	private String supplierSellCert;  //供应商物资销售资质证书

	private String supplierEngCert;  //供应商工程资质证书
	
	private String supplierEngQua;  //供应商工程资质证书

	private String supplierServeCert;  //供应商服务资质证书

	private String supplierEngCertFile;  //供应商资质资格证书

	private String supplierProductPic;  // 供应产品图片
	
	private String supplierQrcode; //供应商产品二维码
	
	private String supplierIdentityUp;//注册人身份证正面信息
	
	private String supplierIdentitydown;//注册人身份证反面信息
	
	private String supplierCategory;//供应商注册品目修改
	
	private String supplierBank;
	
	private String supplierConAch;//供应商国家或军队保密工程业绩
	
	private String supplierProContract;//供应商省级行政区对应合同主要页
	
	private String supplierPresentation; //供应商现场考察报告

    private String supplierHousePoperty;//供应商房产证明或租赁协议
	
	public String getSupplierTaxCert() {
		return supplierTaxCert;
	}

	public void setSupplierTaxCert(String supplierTaxCert) {
		this.supplierTaxCert = supplierTaxCert;
	}

	public String getSupplierBillCert() {
		return supplierBillCert;
	}

	public void setSupplierBillCert(String supplierBillCert) {
		this.supplierBillCert = supplierBillCert;
	}

	public String getSupplierSecurityCert() {
		return supplierSecurityCert;
	}

	public void setSupplierSecurityCert(String supplierSecurityCert) {
		this.supplierSecurityCert = supplierSecurityCert;
	}

	public String getSupplierBearchCert() {
		return supplierBearchCert;
	}

	public void setSupplierBearchCert(String supplierBearchCert) {
		this.supplierBearchCert = supplierBearchCert;
	}

	public String getSupplierLevel() {
		return supplierLevel;
	}

	public void setSupplierLevel(String supplierLevel) {
		this.supplierLevel = supplierLevel;
	}

	public String getSupplierPledge() {
		return supplierPledge;
	}

	public void setSupplierPledge(String supplierPledge) {
		this.supplierPledge = supplierPledge;
	}

	public String getSupplierRegList() {
		return supplierRegList;
	}

	public void setSupplierRegList(String supplierRegList) {
		this.supplierRegList = supplierRegList;
	}

	public String getSupplierExtractsList() {
		return supplierExtractsList;
	}

	public void setSupplierExtractsList(String supplierExtractsList) {
		this.supplierExtractsList = supplierExtractsList;
	}

	public String getSupplierInspectList() {
		return supplierInspectList;
	}

	public void setSupplierInspectList(String supplierInspectList) {
		this.supplierInspectList = supplierInspectList;
	}

	public String getSupplierReviewList() {
		return supplierReviewList;
	}

	public void setSupplierReviewList(String supplierReviewList) {
		this.supplierReviewList = supplierReviewList;
	}

	public String getSupplierChangeList() {
		return supplierChangeList;
	}

	public void setSupplierChangeList(String supplierChangeList) {
		this.supplierChangeList = supplierChangeList;
	}

	public String getSupplierExitList() {
		return supplierExitList;
	}

	public void setSupplierExitList(String supplierExitList) {
		this.supplierExitList = supplierExitList;
	}

	public String getSupplierAuditOpinion() {
		return supplierAuditOpinion;
	}

	public void setSupplierAuditOpinion(String supplierAuditOpinion) {
		this.supplierAuditOpinion = supplierAuditOpinion;
	}

	public String getSupplierLiabilities() {
		return supplierLiabilities;
	}

	public void setSupplierLiabilities(String supplierLiabilities) {
		this.supplierLiabilities = supplierLiabilities;
	}

	public String getSupplierProfit() {
		return supplierProfit;
	}

	public void setSupplierProfit(String supplierProfit) {
		this.supplierProfit = supplierProfit;
	}

	public String getSupplierCashFlow() {
		return supplierCashFlow;
	}

	public void setSupplierCashFlow(String supplierCashFlow) {
		this.supplierCashFlow = supplierCashFlow;
	}

	public String getSupplierOwnerChange() {
		return supplierOwnerChange;
	}

	public void setSupplierOwnerChange(String supplierOwnerChange) {
		this.supplierOwnerChange = supplierOwnerChange;
	}

	public String getSupplierBusinessCert() {
		return supplierBusinessCert;
	}

	public void setSupplierBusinessCert(String supplierBusinessCert) {
		this.supplierBusinessCert = supplierBusinessCert;
	}

	public String getSupplierProCert() {
		return supplierProCert;
	}

	public void setSupplierProCert(String supplierProCert) {
		this.supplierProCert = supplierProCert;
	}

	public String getSupplierSellCert() {
		return supplierSellCert;
	}

	public void setSupplierSellCert(String supplierSellCert) {
		this.supplierSellCert = supplierSellCert;
	}

	public String getSupplierEngCert() {
		return supplierEngCert;
	}

	public void setSupplierEngCert(String supplierEngCert) {
		this.supplierEngCert = supplierEngCert;
	}
	
	public String getSupplierEngQua() {
		return supplierEngQua;
	}

	public void setSupplierEngQua(String supplierEngQua) {
		this.supplierEngQua = supplierEngQua;
	}

	public String getSupplierServeCert() {
		return supplierServeCert;
	}

	public void setSupplierServeCert(String supplierServeCert) {
		this.supplierServeCert = supplierServeCert;
	}

	public String getSupplierEngCertFile() {
		return supplierEngCertFile;
	}

	public void setSupplierEngCertFile(String supplierEngCertFile) {
		this.supplierEngCertFile = supplierEngCertFile;
	}

	public String getSupplierProductPic() {
		return supplierProductPic;
	}

	public void setSupplierProductPic(String supplierProductPic) {
		this.supplierProductPic = supplierProductPic;
	}

	public String getSupplierQrcode() {
		return supplierQrcode;
	}

	public void setSupplierQrcode(String supplierQrcode) {
		this.supplierQrcode = supplierQrcode;
	}

	public String getSupplierIdentityUp() {
		return supplierIdentityUp;
	}

	public void setSupplierIdentityUp(String supplierIdentityUp) {
		this.supplierIdentityUp = supplierIdentityUp;
	}

	public String getSupplierIdentitydown() {
		return supplierIdentitydown;
	}

	public void setSupplierIdentitydown(String supplierIdentitydown) {
		this.supplierIdentitydown = supplierIdentitydown;
	}

	public String getSupplierCategory() {
		return supplierCategory;
	}

	public void setSupplierCategory(String supplierCategory) {
		this.supplierCategory = supplierCategory;
	}

	public String getSupplierBank() {
		return supplierBank;
	}

	public void setSupplierBank(String supplierBank) {
		this.supplierBank = supplierBank;
	}

    public String getSupplierConAch() {
        return supplierConAch;
    }

    public void setSupplierConAch(String supplierConAch) {
        this.supplierConAch = supplierConAch;
    }

    public String getSupplierProContract() {
        return supplierProContract;
    }

    public void setSupplierProContract(String supplierProContract) {
        this.supplierProContract = supplierProContract;
    }

	public String getSupplierPresentation() {
		return supplierPresentation;
	}

	public void setSupplierPresentation(String supplierPresentation) {
		this.supplierPresentation = supplierPresentation;
	}

    public String getSupplierHousePoperty() {
        return supplierHousePoperty;
    }

    public void setSupplierHousePoperty(String supplierHousePoperty) {
        this.supplierHousePoperty = supplierHousePoperty;
    }

}
