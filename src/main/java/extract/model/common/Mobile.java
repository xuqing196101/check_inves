package extract.model.common;

public class Mobile {

    private String id;
    
    /** 手机号 **/
    private String mobile;
    
    /** 省 **/
    private String province;
    
    /** 市 **/
    private String city;
    
    /** 运营商 **/
    private String corp;
    
    /** 区号 **/
    private String areacode;
    
    /** 邮编 **/
    private String postcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreaCode(String areacode) {
        this.areacode = areacode;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
}
