package extract.model.expert;

/**
 * 
 * Description: 专家抽取获取专家筛选条件实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ExpertExtractCateInfo {

    // 工程技术人数（工程专业）
    private String project_i_count;

    // 工程专业信息（工程专业）
    private String project_eng_info;
    
    // 是否同时满足工程专业信息条件（工程专业）
    private String project_eng_isSatisfy;

    // 产品类别（工程专业）
    private String project_type;

    // 技术职称（工程专业）
    private String project_technical;

    // 工程执业资格（工程专业）
    private String project_qualification;

    // 是否同时满足品目条件（工程专业）
    private String project_isSatisfy;

    // 工程经济人数（工程经济）
    private String goods_project_i_count;

    // 工程专业信息（工程经济）
    private String goods_project_eng_info;
    
    // 是否同时满足工程专业信息条件（工程专业）
    private String goods_project_eng_isSatisfy;

    // 产品类别（工程经济）
    private String goods_project_type;

    // 技术职称（工程经济）
    private String goods_project_technical;

    // 工程执业资格（工程经济）
    private String goods_project_qualification;

    // 是否同时满足品目条件（工程经济）
    private String goods_project_isSatisfy;

    // 物资技术人数（物资经济）
    private String goods_i_count;

    // 产品类别（物资经济）
    private String goods_type;

    // 技术职称（物资经济）
    private String goods_technical;

    // 是否同时满足品目条件（物资经济）
    private String goods_isSatisfy;

    // 物资服务经济人数（物资服务经济）
    private String goods_server_i_count;

    // 产品类别（物资服务经济）
    private String goods_server_type;

    // 技术职称（物资服务经济）
    private String goods_server_technical;

    // 是否同时满足品目条件（物资服务经济）
    private String goods_server_isSatisfy;

    // 服务技术人数（服务技术）
    private String service_i_count;

    // 产品类别（服务技术）
    private String service_type;

    // 技术职称（服务技术）
    private String service_technical;

    // 是否同时满足品目条件（服务技术）
    private String service_isSatisfy;

    public String getProject_i_count() {
        return project_i_count;
    }

    public void setProject_i_count(String project_i_count) {
        this.project_i_count = project_i_count;
    }

    public String getProject_eng_info() {
        return project_eng_info;
    }

    public void setProject_eng_info(String project_eng_info) {
        this.project_eng_info = project_eng_info;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getProject_technical() {
        return project_technical;
    }

    public void setProject_technical(String project_technical) {
        this.project_technical = project_technical;
    }

    public String getProject_qualification() {
        return project_qualification;
    }

    public void setProject_qualification(String project_qualification) {
        this.project_qualification = project_qualification;
    }

    public String getProject_isSatisfy() {
        return project_isSatisfy;
    }

    public void setProject_isSatisfy(String project_isSatisfy) {
        this.project_isSatisfy = project_isSatisfy;
    }

    public String getGoods_project_i_count() {
        return goods_project_i_count;
    }

    public void setGoods_project_i_count(String goods_project_i_count) {
        this.goods_project_i_count = goods_project_i_count;
    }

    public String getGoods_project_eng_info() {
        return goods_project_eng_info;
    }

    public void setGoods_project_eng_info(String goods_project_eng_info) {
        this.goods_project_eng_info = goods_project_eng_info;
    }

    public String getGoods_project_type() {
        return goods_project_type;
    }

    public void setGoods_project_type(String goods_project_type) {
        this.goods_project_type = goods_project_type;
    }

    public String getGoods_project_technical() {
        return goods_project_technical;
    }

    public void setGoods_project_technical(String goods_project_technical) {
        this.goods_project_technical = goods_project_technical;
    }

    public String getGoods_project_qualification() {
        return goods_project_qualification;
    }

    public void setGoods_project_qualification(
            String goods_project_qualification) {
        this.goods_project_qualification = goods_project_qualification;
    }

    public String getGoods_project_isSatisfy() {
        return goods_project_isSatisfy;
    }

    public void setGoods_project_isSatisfy(String goods_project_isSatisfy) {
        this.goods_project_isSatisfy = goods_project_isSatisfy;
    }

    public String getGoods_i_count() {
        return goods_i_count;
    }

    public void setGoods_i_count(String goods_i_count) {
        this.goods_i_count = goods_i_count;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_technical() {
        return goods_technical;
    }

    public void setGoods_technical(String goods_technical) {
        this.goods_technical = goods_technical;
    }

    public String getGoods_isSatisfy() {
        return goods_isSatisfy;
    }

    public void setGoods_isSatisfy(String goods_isSatisfy) {
        this.goods_isSatisfy = goods_isSatisfy;
    }

    public String getGoods_server_i_count() {
        return goods_server_i_count;
    }

    public void setGoods_server_i_count(String goods_server_i_count) {
        this.goods_server_i_count = goods_server_i_count;
    }

    public String getGoods_server_type() {
        return goods_server_type;
    }

    public void setGoods_server_type(String goods_server_type) {
        this.goods_server_type = goods_server_type;
    }

    public String getGoods_server_technical() {
        return goods_server_technical;
    }

    public void setGoods_server_technical(String goods_server_technical) {
        this.goods_server_technical = goods_server_technical;
    }

    public String getGoods_server_isSatisfy() {
        return goods_server_isSatisfy;
    }

    public void setGoods_server_isSatisfy(String goods_server_isSatisfy) {
        this.goods_server_isSatisfy = goods_server_isSatisfy;
    }

    public String getService_i_count() {
        return service_i_count;
    }

    public void setService_i_count(String service_i_count) {
        this.service_i_count = service_i_count;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getService_technical() {
        return service_technical;
    }

    public void setService_technical(String service_technical) {
        this.service_technical = service_technical;
    }

    public String getService_isSatisfy() {
        return service_isSatisfy;
    }

    public void setService_isSatisfy(String service_isSatisfy) {
        this.service_isSatisfy = service_isSatisfy;
    }

    public String getProject_eng_isSatisfy() {
        return project_eng_isSatisfy;
    }

    public void setProject_eng_isSatisfy(String project_eng_isSatisfy) {
        this.project_eng_isSatisfy = project_eng_isSatisfy;
    }

    public String getGoods_project_eng_isSatisfy() {
        return goods_project_eng_isSatisfy;
    }

    public void setGoods_project_eng_isSatisfy(String goods_project_eng_isSatisfy) {
        this.goods_project_eng_isSatisfy = goods_project_eng_isSatisfy;
    }

}
