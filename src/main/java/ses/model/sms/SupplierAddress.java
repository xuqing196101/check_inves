package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ses.model.bms.Area;

public class SupplierAddress implements Serializable{
    /**
	 * SupplierAddress.java
	 */
	private static final long serialVersionUID = 1L;

	/**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_ADDRESS.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 供应商Id
     * 表字段 : T_SES_SMS_SUPPLIER_ADDRESS.SUPPLIER_ID
     * </pre>
     */
    private String supplierId;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_ADDRESS.OCDE
     * </pre>
     */
    private String code;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_ADDRESS.ADDRESS
     * </pre>
     */
    private String address;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_ADDRESS.DETAIL_ADDRESS
     * </pre>
     */
    private String detailAddress;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_ADDRESS.ID：null
     */
    
    
    private String defaultValue;
    
    private String provinceId;
    
    private List<Area> areaList=new ArrayList<Area>();
    
    private String subAddressName;
    
    private String parentId;
    
    private String parentName;
    
    public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIER_ADDRESS.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：供应商Id
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.SUPPLIER_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_ADDRESS.SUPPLIER_ID：供应商Id
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：供应商Id
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_SES_SMS_SUPPLIER_ADDRESS.SUPPLIER_ID：供应商Id
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.OCDE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_ADDRESS.OCDE：null
     */
    public String getCode() {
        return code;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.OCDE
     * </pre>
     *
     * @param ocde
     *            T_SES_SMS_SUPPLIER_ADDRESS.OCDE：null
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.ADDRESS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_ADDRESS.ADDRESS：null
     */
    public String getAddress() {
        return address;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.ADDRESS
     * </pre>
     *
     * @param address
     *            T_SES_SMS_SUPPLIER_ADDRESS.ADDRESS：null
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.DETAIL_ADDRESS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_ADDRESS.DETAIL_ADDRESS：null
     */
    public String getDetailAddress() {
        return detailAddress;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_ADDRESS.DETAIL_ADDRESS
     * </pre>
     *
     * @param detailAddress
     *            T_SES_SMS_SUPPLIER_ADDRESS.DETAIL_ADDRESS：null
     */
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getSubAddressName() {
		return subAddressName;
	}

	public void setSubAddressName(String subAddressName) {
		this.subAddressName = subAddressName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
    
    
    
}