package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.UserDataRuleMapper;
import ses.model.bms.UserDataRule;
import ses.service.bms.UserDataRuleService;
/**
 * 数据权限 实现服务接口
 * @author YangHongLiang
 */
@Service
public class UserDataRuleServiceImpl implements UserDataRuleService {
	@Autowired
    private UserDataRuleMapper UserDataRuleMapper;
	/**
	 * 实现 保存 数据权限关系
	 */
	@Override
	public Integer insertSelective(UserDataRule rule) {
		// TODO Auto-generated method stub
		return UserDataRuleMapper.insertSelective(rule);
	}
	/**
	 * 实现 根据用户id 删除数据权限关系
	 */
	@Override
	public Integer deleteByUserId(String userId) {
		// TODO Auto-generated method stub
		return UserDataRuleMapper.deleteByUserId(userId);
	}
	/**
	 * 实现 根据userId 获取相关的集合
	 */
	@Override
	public List<UserDataRule> selectByUserId(String userId) {
		// TODO Auto-generated method stub
		return UserDataRuleMapper.selectByUserId(userId);
	}
	@Override
	public List<String> getOrgID(String userId) {
		// TODO Auto-generated method stub
		return UserDataRuleMapper.getOrgID(userId);
	}

}
