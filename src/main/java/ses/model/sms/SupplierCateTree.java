package ses.model.sms;

import java.io.Serializable;
import java.util.List;

import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;

/**
 * 版权：(C) 版权所有 2011-2016
 * <简述> 供应商品目信息下载封装类
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0.0
 * @since    2017年1月15日10:18:35
 * @see
 */
public class SupplierCateTree implements Serializable{
    /**
	 * SupplierCateTree.java
	 */
	private static final long serialVersionUID = 1L;
	/** 根节点 **/
    private String rootNode;
    /** 根节点 ID**/
    private String rootNodeID;
    
    /** 一级节点 **/
    private String firstNode;
    /** 一级节点 ID**/
    private String firstNodeID;
    
    /** 二级节点 **/
    private String secondNode;
    /** 二级节点 ID**/
    private String secondNodeID;
    
    /** 三级节点 **/
    private String thirdNode;
    /** 三级节点 ID**/
    private String thirdNodeID;
    
    /** 四级节点 **/
    private String fourthNode;
    /** 四级节点 ID**/
    private String fourthNodeID;
    
    /** 供应商品目ID **/
    private String itemsId;
    /** 供应商品目ID **/
    private String itemsName;
    
    /** 等级 **/
    private DictionaryData level;
    
    /** 证书编号 **/
    private String certCode;
    
    /** 该品目所有等级 **/
    private List<Qualification> typeList;
    
    /** 工程对应附件Id **/
    private String fileId;
    
    /** 工程对应附件Id **/
    private String qualificationType;
    
    /** 自定义等级 **/
    private String diyLevel;
    //专业类别名称
    private String proName;
    //资质文件数量
    private Long fileCount;
    //合同文件数量
    private Long contractCount;
    private String supplierItemId;
    //目录 是否有审核记录
    private Integer isItemsPageAudit; 
    //资质文件 是否有审核记录
    private Integer isAptitudePAgeAudit;
    //合同文件 是否有资质审核记录
    private Integer isContractPageAudit;
    // 根节点类型（1：物质生产；2：物质销售；3：工程；4：服务）
    private int rootNodeType;
    
    // 类别id
    private String categoryId;
    
    
    public String getItemsName() {
		return itemsName;
	}

	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}

	public Integer getIsItemsPageAudit() {
		return isItemsPageAudit;
	}

	public void setIsItemsPageAudit(Integer isItemsPageAudit) {
		this.isItemsPageAudit = isItemsPageAudit;
	}

	public Integer getIsAptitudePAgeAudit() {
		return isAptitudePAgeAudit;
	}

	public void setIsAptitudePAgeAudit(Integer isAptitudePAgeAudit) {
		this.isAptitudePAgeAudit = isAptitudePAgeAudit;
	}

	public Integer getIsContractPageAudit() {
		return isContractPageAudit;
	}

	public void setIsContractPageAudit(Integer isContractPageAudit) {
		this.isContractPageAudit = isContractPageAudit;
	}

	public String getSupplierItemId() {
		return supplierItemId;
	}

	public void setSupplierItemId(String supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

	public String getRootNodeID() {
		return rootNodeID;
	}

	public void setRootNodeID(String rootNodeID) {
		this.rootNodeID = rootNodeID;
	}

	public String getFirstNodeID() {
		return firstNodeID;
	}

	public void setFirstNodeID(String firstNodeID) {
		this.firstNodeID = firstNodeID;
	}

	public String getSecondNodeID() {
		return secondNodeID;
	}

	public void setSecondNodeID(String secondNodeID) {
		this.secondNodeID = secondNodeID;
	}

	public String getThirdNodeID() {
		return thirdNodeID;
	}

	public void setThirdNodeID(String thirdNodeID) {
		this.thirdNodeID = thirdNodeID;
	}

	public String getFourthNodeID() {
		return fourthNodeID;
	}

	public void setFourthNodeID(String fourthNodeID) {
		this.fourthNodeID = fourthNodeID;
	}

	public Long getFileCount() {
		return fileCount;
	}

	public void setFileCount(Long fileCount) {
		this.fileCount = fileCount;
	}

	public Long getContractCount() {
		return contractCount;
	}

	public void setContractCount(Long contractCount) {
		this.contractCount = contractCount;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getFileId() {
        return fileId;
    }

    private String oneContract;
	
	private String twoContract;
	
	private String threeContract;
	
	private String oneBil;
	
	private String twoBil;
	
	private String threeBil;
    
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public DictionaryData getLevel() {
        return level;
    }

    public void setLevel(DictionaryData level) {
        this.level = level;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getRootNode() {
        return rootNode;
    }

    public void setRootNode(String rootNode) {
        this.rootNode = rootNode;
    }

    public String getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(String firstNode) {
        this.firstNode = firstNode;
    }

    public String getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(String secondNode) {
        this.secondNode = secondNode;
    }

    public String getThirdNode() {
        return thirdNode;
    }

    public void setThirdNode(String thirdNode) {
        this.thirdNode = thirdNode;
    }

    public String getFourthNode() {
        return fourthNode;
    }

    public void setFourthNode(String fourthNode) {
        this.fourthNode = fourthNode;
    }

	public String getItemsId() {
		return itemsId;
	}

	public void setItemsId(String itemsId) {
		this.itemsId = itemsId;
	}

    public String getDiyLevel() {
        return diyLevel;
    }

    public void setDiyLevel(String diyLevel) {
        this.diyLevel = diyLevel;
    }

    public List<Qualification> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Qualification> typeList) {
        this.typeList = typeList;
    }

    public String getQualificationType() {
        return qualificationType;
    }

    public void setQualificationType(String qualificationType) {
        this.qualificationType = qualificationType;
    }

	public String getOneContract() {
		return oneContract;
	}

	public void setOneContract(String oneContract) {
		this.oneContract = oneContract;
	}

	public String getTwoContract() {
		return twoContract;
	}

	public void setTwoContract(String twoContract) {
		this.twoContract = twoContract;
	}

	public String getThreeContract() {
		return threeContract;
	}

	public void setThreeContract(String threeContract) {
		this.threeContract = threeContract;
	}

	public String getOneBil() {
		return oneBil;
	}

	public void setOneBil(String oneBil) {
		this.oneBil = oneBil;
	}

	public String getTwoBil() {
		return twoBil;
	}

	public void setTwoBil(String twoBil) {
		this.twoBil = twoBil;
	}

	public String getThreeBil() {
		return threeBil;
	}

	public void setThreeBil(String threeBil) {
		this.threeBil = threeBil;
	}

	public int getRootNodeType() {
		return rootNodeType;
	}

	public void setRootNodeType(int rootNodeType) {
		this.rootNodeType = rootNodeType;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
