package ses.service.bms;

import java.util.List;

import ses.model.bms.UserDataRule;

/**
 * 数据权限 服务接口
 * @author YangHongLiang
 */
public interface UserDataRuleService {
	/**
	 * 保存数据权限 关系
	 * @param rule
	 * @return
	 */
	Integer insertSelective(UserDataRule rule);
	/**
	 * 根据用户id 删除用户权限关系
	 * @param userId
	 * @return
	 */
    Integer deleteByUserId(String userId);
    /**
     * 根据userid 获取相关的 数据集合
     * @param userId
     * @return
     */
    List<UserDataRule> selectByUserId(String userId);
    /**
     * 根据userID 获取数据
     * @param userId
     * @return
     */
    List<String> getOrgID(String userId);

}
