package ses.dao.ems;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.ems.ExpertBlackList;
/**
 * <p>Title:ExpertBlackListMapper </p>
 * <p>Description: 专家黑名单接口</p>
 * @author Xu Qing
 * @date 2016-9-9下午4:46:53
 */
public interface ExpertBlackListMapper {
	/**
	 * @Title: update
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:41:47  
	 * @Description: 更新黑名单
	 * @param @param expertBlackList      
	 * @return void
	 */
	void updateByPrimaryKeySelective(ExpertBlackList expertBlackList);
	/**
	 * @Title: findList
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:42:58  
	 * @Description: 查询黑名单,可条件查询
	 * @param @return      
	 * @return List<ExpertBlackList>
	 */
	List<ExpertBlackList> findList(ExpertBlackList expertBlackList);
	
	/**
	 * 
	 * Description: 专家黑名单添加名称筛选
	 * 
	 * @author  zhang shubin
	 * @version  2017年5月22日 
	 * @param  @param expertBlackList
	 * @param  @return 
	 * @return List<ExpertBlackList> 
	 * @exception
	 */
	List<ExpertBlackList> findListByStatus(ExpertBlackList expertBlackList);
	/**
	 * @Title: insert
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:38:48  
	 * @Description: 新增黑名单 
	 * @param @param expertBlackList      
	 * @return void
	 */
	void insertSelective(ExpertBlackList expertBlackList);
	/**
	 * @Title: selectByPrimaryKey
	 * @author Xu Qing
	 * @date 2016-9-9 下午2:15:06  
	 * @Description: 根据id查询 
	 * @param @param id
	 * @param @return      
	 * @return ExpertBlackList
	 */
	ExpertBlackList selectByPrimaryKey(String id);
	/**
	 * @Title: updateStatus
	 * @author Xu Qing
	 * @date 2016-9-9 下午4:49:59  
	 * @Description: 根据id更新状态信息 
	 * @param @param id      
	 * @return void
	 */
	void updateStatus(ExpertBlackList expertBlackList);
	
	/**
	 * 
	* @Title: findAllBlackListExpert 
	* @Description: 查询所有正在处罚中的专家黑名单
	* @author Easong
	* @param @param status
	* @param @return    设定文件 
	* @return List<ExpertBlackList>    返回类型 
	* @throws
	 */
	List<ExpertBlackList> findAllBlackListExpert(Integer status);
	
	/**
	 * 
	 * Description: 验证是否上传文件
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月11日 
	 * @param  @param id
	 * @param  @return 
	 * @return Integer 
	 * @exception
	 */
	Integer yzsc(@Param("id") String id);
}