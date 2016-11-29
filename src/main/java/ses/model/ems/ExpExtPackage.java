package ses.model.ems;

import java.util.Date;
import java.util.List;

import ses.model.sms.SupplierCondition;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;

public class ExpExtPackage {
    
    public ExpExtPackage() {
        super();
    }
    
    

    public ExpExtPackage(String projectId) {
        super();
        this.projectId = projectId;
    }
    
    /**
     * 项目信息
     */
    private Project project;


    /**
     * 包信息
     */
    private Packages  packages;
    
    /**
     * 抽取条件关联
     */
    private List<SupplierCondition> conditionList;
    
    
    /**
     * <pre>
     * 主键
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 创建日期 格式年月日时分秒
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 修改日期 格式年月日时分秒
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 删除标识 0：未删除，1：删除
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.IS_DELETED
     * </pre>
     */
    private Short isDeleted;

    /**
     * <pre>
     * 项目ID
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 包ID
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.PACKAGE_ID
     * </pre>
     */
    private String packageId;

    /**
     * <pre>
     * 抽取次数
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.COUNT
     * </pre>
     */
    private Long count;

    /**
     * <pre>
     * 完成标识 0：未完成，1：已完成
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.IS_FINISH
     * </pre>
     */
    private Short isFinish;

    /**
     * <pre>
     * 已抽取专家数量
     * 表字段 : T_SES_EMS_EXP_EXT_PACKAGE.NUMBER
     * </pre>
     */
    private Long number;

    /**
     * <pre>
     * 获取：主键
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.ID：主键
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.ID
     * </pre>
     *
     * @param id
     *            T_SES_EMS_EXP_EXT_PACKAGE.ID：主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：创建日期 格式年月日时分秒
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.CREATED_AT
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.CREATED_AT：创建日期 格式年月日时分秒
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建日期 格式年月日时分秒
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_EMS_EXP_EXT_PACKAGE.CREATED_AT：创建日期 格式年月日时分秒
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：修改日期 格式年月日时分秒
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.UPDATED_AT
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.UPDATED_AT：修改日期 格式年月日时分秒
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：修改日期 格式年月日时分秒
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_EMS_EXP_EXT_PACKAGE.UPDATED_AT：修改日期 格式年月日时分秒
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * <pre>
     * 获取：删除标识 0：未删除，1：删除
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.IS_DELETED
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.IS_DELETED：删除标识 0：未删除，1：删除
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * <pre>
     * 设置：删除标识 0：未删除，1：删除
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.IS_DELETED
     * </pre>
     *
     * @param isDeleted
     *            T_SES_EMS_EXP_EXT_PACKAGE.IS_DELETED：删除标识 0：未删除，1：删除
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * <pre>
     * 获取：项目ID
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.PROJECT_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.PROJECT_ID：项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目ID
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_EMS_EXP_EXT_PACKAGE.PROJECT_ID：项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：包ID
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.PACKAGE_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.PACKAGE_ID：包ID
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * <pre>
     * 设置：包ID
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.PACKAGE_ID
     * </pre>
     *
     * @param packageId
     *            T_SES_EMS_EXP_EXT_PACKAGE.PACKAGE_ID：包ID
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    /**
     * <pre>
     * 获取：抽取次数
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.COUNT
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.COUNT：抽取次数
     */
    public Long getCount() {
        return count;
    }

    /**
     * <pre>
     * 设置：抽取次数
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.COUNT
     * </pre>
     *
     * @param count
     *            T_SES_EMS_EXP_EXT_PACKAGE.COUNT：抽取次数
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * <pre>
     * 获取：完成标识 0：未完成，1：已完成
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.IS_FINISH
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.IS_FINISH：完成标识 0：未完成，1：已完成
     */
    public Short getIsFinish() {
        return isFinish;
    }

    /**
     * <pre>
     * 设置：完成标识 0：未完成，1：已完成
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.IS_FINISH
     * </pre>
     *
     * @param isFinish
     *            T_SES_EMS_EXP_EXT_PACKAGE.IS_FINISH：完成标识 0：未完成，1：已完成
     */
    public void setIsFinish(Short isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * <pre>
     * 获取：已抽取专家数量
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.NUMBER
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_PACKAGE.NUMBER：已抽取专家数量
     */
    public Long getNumber() {
        return number;
    }

    /**
     * <pre>
     * 设置：已抽取专家数量
     * 表字段：T_SES_EMS_EXP_EXT_PACKAGE.NUMBER
     * </pre>
     *
     * @param number
     *            T_SES_EMS_EXP_EXT_PACKAGE.NUMBER：已抽取专家数量
     */
    public void setNumber(Long number) {
        this.number = number;
    }



    /**
     * @return Returns the project.
     */
    public Project getProject() {
        return project;
    }



    /**
     * @param project The project to set.
     */
    public void setProject(Project project) {
        this.project = project;
    }



    /**
     * @return Returns the packages.
     */
    public Packages getPackages() {
        return packages;
    }



    /**
     * @param packages The packages to set.
     */
    public void setPackages(Packages packages) {
        this.packages = packages;
    }



    /**
     * @return Returns the conditionList.
     */
    public List<SupplierCondition> getConditionList() {
        return conditionList;
    }



    /**
     * @param conditionList The conditionList to set.
     */
    public void setConditionList(List<SupplierCondition> conditionList) {
        this.conditionList = conditionList;
    }
    
    
}