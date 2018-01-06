package ses.model.sms.review;

/**
 * 供应商实地考察其他信息
 * 
 * T_SES_SMS_SUPPLIER_INVES_OTHER
 * 
 * @author yggc
 * 
 * @date 2017-12-28
 *
 */
public class SupplierInvesOther {
    /**
     * 主键
     */
    private String id;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 主要生产场所情况
     */
    private String productionPlaceInfo;

    /**
     * 主要设施设备情况
     */
    private String facilitiesInfo;

    /**
     * 拍照录像人员
     */
    private String photographer;

    /**
     * 主键
     * @return ID 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 供应商ID
     * @return SUPPLIER_ID 供应商ID
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * 供应商ID
     * @param supplierId 供应商ID
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 主要生产场所情况
     * @return PRODUCTION_PLACE_INFO 主要生产场所情况
     */
    public String getProductionPlaceInfo() {
        return productionPlaceInfo;
    }

    /**
     * 主要生产场所情况
     * @param productionPlaceInfo 主要生产场所情况
     */
    public void setProductionPlaceInfo(String productionPlaceInfo) {
        this.productionPlaceInfo = productionPlaceInfo;
    }

    /**
     * 主要设施设备情况
     * @return FACILITIES_INFO 主要设施设备情况
     */
    public String getFacilitiesInfo() {
        return facilitiesInfo;
    }

    /**
     * 主要设施设备情况
     * @param facilitiesInfo 主要设施设备情况
     */
    public void setFacilitiesInfo(String facilitiesInfo) {
        this.facilitiesInfo = facilitiesInfo;
    }

    /**
     * 拍照录像人员
     * @return PHOTOGRAPHER 拍照录像人员
     */
    public String getPhotographer() {
        return photographer;
    }

    /**
     * 拍照录像人员
     * @param photographer 拍照录像人员
     */
    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }
}