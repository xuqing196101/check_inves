package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 版权：(C) 版权所有 
 * <简述>进口代理商实体类
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public class ImportRecommend implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    private String id;
    
    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空") 
    private String name;
    
    /**
     * 企业地址
     */
    @NotBlank(message = "企业地址不能为空") 
    private String address;
    
    /**
     * 法定代表人
     */
    @NotBlank(message = "法定代表人不能为空") 
    private String legalName;
    
    /**
     * 推荐单位
     */
    @NotBlank(message = "推荐单位不能为空") 
    private String recommendDep;
    
    /**
     * 代理商类型
     */
    private Short type;
    
    /**
     * 使用次数
     */
    private Long useCount;
    
    /**
     * 代理商状态
     */
    private Short status;
    
    /**
     * 登陆名
     */
    @NotBlank(message = "登录名不能为空") 
    private String loginName;
    
    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空") 
    private String password;
    
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
    private String creator;

    /**
     * 激活附件
     */
    private String attachment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    public String getRecommendDep() {
        return recommendDep;
    }

    public void setRecommendDep(String recommendDep) {
        this.recommendDep = recommendDep == null ? null : recommendDep.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getUseCount() {
        return useCount;
    }

    public void setUseCount(Long useCount) {
        this.useCount = useCount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }
}