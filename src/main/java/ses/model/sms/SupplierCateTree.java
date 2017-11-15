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
    
    private SupplierAptitute supplierAptitute;//资质信息
    /** 工程对应附件Id **/
    private String fileId;
    
    /** 工程对应附件Id **/
    private String qualificationType;
    
    /** 自定义等级 **/
    private String diyLevel;
    //专业类别名称
    private String proName;
    //资质文件数量
    private long fileCount;
    //合同文件数量
    private Long contractCount;
    private String supplierItemId;
    //目录 是否有审核记录 物资生产 如果是其他的目录类型 也是该字段存储
    private long isItemsProductPageAudit; 
    //目录 是否有审核记录 物资销售
    private long isItemsSalesPageAudit; 
    //资质文件 是否有审核记录 物资生产 如果其他类型 也是该字段存储
    private long isAptitudeProductPageAudit;
    //资质文件 是否有审核记录 物资销售
    private long isAptitudeSalesPageAudit;
    //合同文件 是否有资质审核记录  物资生产   如果其他类型 也是该字段存储
    private long isContractProductPageAudit;
    //合同文件 是否有资质审核记录  物资销售
    private long isContractSalesPageAudit;
    //是否历史审核记录
    private int auditIsDeleted;
    //审核记录 type
    private String auditType;
    // 根节点类型（1：物质生产；2：物质销售；3：工程；4：服务）
    private int rootNodeType;
    // 根节点编码（PRODUCT：物质生产；SALES：物质销售；PROJECT：工程；SERVICE：服务）
    private String rootNodeCode;
    // 类别id
    private String categoryId;
    //审核理由
    private String auditReason;
    // 是否被退回
    private byte isReturned;
    //记录一条有资质的节点id
    private String aptitudeId;
    //记录一条有销售合同节点id
    private String contractId;
    private byte isContractModified;// 合同是否修改过
    private byte isAptitudeModified;// 资质是否修改过
    private byte isEngAptitudeModified;// 工程资质是否修改过
    
	public String getAptitudeId() {
		return aptitudeId;
	}

	public void setAptitudeId(String aptitudeId) {
		this.aptitudeId = aptitudeId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public long getIsAptitudeProductPageAudit() {
		return isAptitudeProductPageAudit;
	}

	public void setIsAptitudeProductPageAudit(long isAptitudeProductPageAudit) {
		this.isAptitudeProductPageAudit = isAptitudeProductPageAudit;
	}

	public long getIsAptitudeSalesPageAudit() {
		return isAptitudeSalesPageAudit;
	}

	public SupplierAptitute getSupplierAptitute() {
		return supplierAptitute;
	}

	public void setSupplierAptitute(SupplierAptitute supplierAptitute) {
		this.supplierAptitute = supplierAptitute;
	}

	public void setIsAptitudeSalesPageAudit(long isAptitudeSalesPageAudit) {
		this.isAptitudeSalesPageAudit = isAptitudeSalesPageAudit;
	}

	public long getIsContractSalesPageAudit() {
		return isContractSalesPageAudit;
	}

	public void setIsContractSalesPageAudit(long isContractSalesPageAudit) {
		this.isContractSalesPageAudit = isContractSalesPageAudit;
	}

	public long getIsContractProductPageAudit() {
		return isContractProductPageAudit;
	}

	public void setIsContractProductPageAudit(long isContractProductPageAudit) {
		this.isContractProductPageAudit = isContractProductPageAudit;
	}

	public String getItemsName() {
		return itemsName;
	}

	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}
	
	public long getIsItemsProductPageAudit() {
		return isItemsProductPageAudit;
	}

	public void setIsItemsProductPageAudit(long isItemsProductPageAudit) {
		this.isItemsProductPageAudit = isItemsProductPageAudit;
	}

	public long getIsItemsSalesPageAudit() {
		return isItemsSalesPageAudit;
	}

	public void setIsItemsSalesPageAudit(long isItemsSalesPageAudit) {
		this.isItemsSalesPageAudit = isItemsSalesPageAudit;
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

	public long getFileCount() {
		return fileCount;
	}

	public void setFileCount(long fileCount) {
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
	
	public String getRootNodeCode() {
		return rootNodeCode;
	}

	public void setRootNodeCode(String rootNodeCode) {
		this.rootNodeCode = rootNodeCode;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public byte getIsReturned() {
		return isReturned;
	}

	public void setIsReturned(byte isReturned) {
		this.isReturned = isReturned;
	}

	public byte getIsContractModified() {
		return isContractModified;
	}

	public void setIsContractModified(byte isContractModified) {
		this.isContractModified = isContractModified;
	}

	public byte getIsAptitudeModified() {
		return isAptitudeModified;
	}

	public void setIsAptitudeModified(byte isAptitudeModified) {
		this.isAptitudeModified = isAptitudeModified;
	}

	public byte getIsEngAptitudeModified() {
		return isEngAptitudeModified;
	}

	public void setIsEngAptitudeModified(byte isEngAptitudeModified) {
		this.isEngAptitudeModified = isEngAptitudeModified;
	}

	public int getAuditIsDeleted() {
		return auditIsDeleted;
	}

	public void setAuditIsDeleted(int auditIsDeleted) {
		this.auditIsDeleted = auditIsDeleted;
	}
	
}
