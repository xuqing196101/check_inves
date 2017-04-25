package ses.model.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import ses.model.bms.Category;
/**
 * 
* @ClassName: ProductBasic 
* @Description: 产品库管理  --产品信息
* @author Easong
* @date 2017年4月10日 下午5:02:07 
*
 */
/**
 * 
* @ClassName: ProductBasic 
* @Description: 产品库管理  --产品信息
* @author Easong
* @date 2017年4月10日 下午5:02:07 
*
 */
public class SMSProductBasic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
    private String id;

    /**
	 * 商品类别ID
	 */
    private String categoryId;
    // 商品类目名称
    private Category category;

    /**
	 * 产品品牌
	 */
    private String brand;

    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品价格
     */
    private BigDecimal price;
    /**
     * 产品型号
     */
    private String typeNum;
    /**
     * 产品主图
     */
    private String pictureMajor;
    /**
     * 产品库存
     */
    private BigDecimal store;
    /**
     * 产品SKU
     */
    private String sku;
    /**
     * 产品状态   1：上架   2.下架
     */
    private Integer productStatus;
    /**
     * 审核状态
     * 0:暂存    1：待审核   2：审核未通过  3：审核通过
     */
    private Integer status;
    /**
     * 是否删除  0：否   1：是
     */
    private Integer isDeleted;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;
    /**
     * 创建人
     */
    private String createrId;
    
    private Supplier supplier;
    //产品信息 详情
    private SMSProductInfo info;
    //产品目录 对应参数集合
    private List<SMSProductArguments> arguments;
    
    
    public SMSProductInfo getInfo() {
		return info;
	}

	public void setInfo(SMSProductInfo info) {
		this.info = info;
	}

	public List<SMSProductArguments> getArguments() {
		return arguments;
	}

	public void setArguments(List<SMSProductArguments> arguments) {
		this.arguments = arguments;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(String typeNum) {
        this.typeNum = typeNum == null ? null : typeNum.trim();
    }

    public String getPictureMajor() {
        return pictureMajor;
    }

    public void setPictureMajor(String pictureMajor) {
        this.pictureMajor = pictureMajor == null ? null : pictureMajor.trim();
    }

    public BigDecimal getStore() {
        return store;
    }

    public void setStore(BigDecimal store) {
        this.store = store;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
}