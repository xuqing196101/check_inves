package bss.model.sstps;

import java.util.Date;

/**
 * 
* @Title:ProductInfo 
* @Description: 装备（产品）技术资料
* @author Shen Zhenfei
* @date 2016-10-13上午9:21:29
 */
public class ProductInfo {
	
    private String id;

    private ContractProduct contractProduct;

    private String name;

    private String designDepartment;

    private Date createdAt;

    private Date updatedAt;
    
    private String productOverview;

    private String productProcess;

    private String productSkill;

    private String conclusion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignDepartment() {
		return designDepartment;
	}

	public void setDesignDepartment(String designDepartment) {
		this.designDepartment = designDepartment;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getProductOverview() {
		return productOverview;
	}

	public void setProductOverview(String productOverview) {
		this.productOverview = productOverview;
	}

	public String getProductProcess() {
		return productProcess;
	}

	public void setProductProcess(String productProcess) {
		this.productProcess = productProcess;
	}

	public String getProductSkill() {
		return productSkill;
	}

	public void setProductSkill(String productSkill) {
		this.productSkill = productSkill;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public ContractProduct getContractProduct() {
		return contractProduct;
	}

	public void setContractProduct(ContractProduct contractProduct) {
		this.contractProduct = contractProduct;
	}
	
	

}