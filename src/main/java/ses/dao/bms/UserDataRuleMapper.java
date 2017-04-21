package ses.dao.bms;

import java.util.List;

import ses.model.bms.UserDataRule;

public interface UserDataRuleMapper {
    int insert(UserDataRule record);

    int insertSelective(UserDataRule record);
    /**
     * 根据userID 删除相关的权限 数据
     * @param userId
     * @return
     */
    int deleteByUserId(String userId);
    /**
     * 根据userid 获取全部权限
     * @param userId
     * @return
     */
    List<UserDataRule> selectByUserId(String userId);
    /**
     * 根据userid 获取全部权限
     * @param userId
     * @return
     */
    List<String> getOrgID(String userId);
}