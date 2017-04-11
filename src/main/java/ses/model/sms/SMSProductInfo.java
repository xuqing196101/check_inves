package ses.model.sms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
* @ClassName: ProductInfo 
* @Description: 商品描述基本信息
* @author Easong
* @date 2017年4月10日 下午5:38:37 
*
 */
public class SMSProductInfo {
	/**
	 * 主键
	 */
    private String id;
    /**
	 * 产品子图
	 */
    private String pictureSub;
    /**
	 * 产品参数
	 */
    private String argumentsId;
    /**
	 * 产品包装清单
	 */
    private String detailList;
    /**
	 * 历史价格
	 */
    private BigDecimal historyPrice;
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
	 * 商品基本信息ID
	 */
    private String proudctBasicId;
    /**
	 * 商品介绍
	 */
    private String introduce;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPictureSub() {
        return pictureSub;
    }

    public void setPictureSub(String pictureSub) {
        this.pictureSub = pictureSub == null ? null : pictureSub.trim();
    }

    public String getArgumentsId() {
        return argumentsId;
    }

    public void setArgumentsId(String argumentsId) {
        this.argumentsId = argumentsId == null ? null : argumentsId.trim();
    }

    public String getDetailList() {
        return detailList;
    }

    public void setDetailList(String detailList) {
        this.detailList = detailList == null ? null : detailList.trim();
    }

    public BigDecimal getHistoryPrice() {
        return historyPrice;
    }

    public void setHistoryPrice(BigDecimal historyPrice) {
        this.historyPrice = historyPrice;
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

    public String getProudctBasicId() {
        return proudctBasicId;
    }

    public void setProudctBasicId(String proudctBasicId) {
        this.proudctBasicId = proudctBasicId == null ? null : proudctBasicId.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }
}