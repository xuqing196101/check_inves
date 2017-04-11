package ses.model.sms;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: ProductArguments 
* @Description: 产品参数
* @author Easong
* @date 2017年4月10日 下午5:40:04 
*
 */
public class SMSProductArguments implements Serializable{
	
	/**
	 * 产品参数主键
	 */
    private String argumentsId;
    /**
	 * 产品参数ID
	 */
    private String categoryParameterId;
    /**
	 * 产品参数值
	 */
    private String parameterValue;
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

    public String getArgumentsId() {
        return argumentsId;
    }

    public void setArgumentsId(String argumentsId) {
        this.argumentsId = argumentsId == null ? null : argumentsId.trim();
    }

    public String getCategoryParameterId() {
        return categoryParameterId;
    }

    public void setCategoryParameterId(String categoryParameterId) {
        this.categoryParameterId = categoryParameterId == null ? null : categoryParameterId.trim();
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue == null ? null : parameterValue.trim();
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
}