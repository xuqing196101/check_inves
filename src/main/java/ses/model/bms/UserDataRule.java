package ses.model.bms;

import java.util.Date;
/***
 * 
 * 数据 权限表
 *
 */
public class UserDataRule {
	/***
	 * 用户id
	 */
    private String userId;
    /***
     * 机构id
     */
    private String orgId;
   /***
    * 创建人id
    */
    private String createrId;
    /**
     * 创建日期
     */
    private Date createdAt;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}