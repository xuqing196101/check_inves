package common.dao;

import java.util.List;
import java.util.Map;

import common.model.LoginLog;
import common.model.LoginLogVo;

/**
 * 
 * @ClassName: LoginLogMapper
 * @Description: 登录Mapper
 * @author Easong
 * @date 2017年5月3日 下午9:15:52
 * 
 */
public interface LoginLogMapper {

	/**
	 * 
	 * @Title: insertSelective
	 * @Description: 保存登录信息
	 * @author Easong
	 * @param @param record
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */

	int deleteByPrimaryKey(String id);

    int insert(LoginLog record);

    int insertSelective(LoginLog record);

    LoginLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoginLog record);

    int updateByPrimaryKey(LoginLog record);

	/**
	 * 
	 * @Title: deleteByUserId
	 * @Description: 根据用户的的ID删除登录信息
	 * @author Easong
	 * @param @param loginId
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	int deleteByUserId(String loginId);

	/**
	 * 
	 * @Title: getLoginCountByEmp
	 * @Description: 统计用户日登录量
	 * @author Easong
	 * @param @return 设定文件
	 * @return Long 返回类型
	 * @throws
	 */
	Long getLoginCountByEmp(Map<String, Object> map);
	
	/**
	 * 
	* @Title: getListByParam 
	* @Description: 登录日志列表查询
	* @author Easong
	* @param @param map
	* @param @return    设定文件 
	* @return List<LoginLog>    返回类型 
	* @throws
	 */
	List<LoginLog> getListByParam(LoginLogVo loginLogVo);

}