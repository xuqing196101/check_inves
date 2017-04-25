package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

import ses.model.bms.CategoryParameter;
import ses.model.bms.DictionaryData;
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
	 * 主键ID
	 */
	private String id;
	
	/**
	 * 产品参数ID
	 */
    private String argumentsId;
    /**
	 * 产品类型ID
	 */
    private String categoryParameterId;
    // 参数类型
    private CategoryParameter categoryParameter;
    /**
	 * 产品参数值
	 */
    private String parameterValue;
    
    /**
	 * 产品参数类型
	 */
    private String parameterType;
    
    
    /**
	 * 备注
	 */
    private String remark;
    
    /**
     * 是否是必填   1填写，0可以不填
     */
    private Integer required;
    
    /**
     * 参数名
     */
    private String paramName;
    
    public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

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

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public CategoryParameter getCategoryParameter() {
		return categoryParameter;
	}

	public void setCategoryParameter(CategoryParameter categoryParameter) {
		this.categoryParameter = categoryParameter;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    
}