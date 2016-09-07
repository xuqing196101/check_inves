package yggc.service.bms.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.bms.UserTaskMapper;
import yggc.formbean.UserTaskFormBean;
import yggc.model.bms.UserTask;
import yggc.service.bms.UserTaksService;

/**
 * 
 * @Title: UserTaksServiceImpl
 * @Description: 用户任务管理service实现接口 
 * @author Li Xiaoxiao
 * @date  2016年9月7日,下午5:42:21
 *
 */
@Service
public class UserTaksServiceImpl implements UserTaksService {

	@Autowired
	private UserTaskMapper userTaskMapper;
	/**
	* @Title: add
	* @Description: 增加任务
	* author: Li Xiaoxiao 
	* @param @param userTask     
	* @return void     
	* @throws
	 */
	@Override
	public void add(UserTask userTask) {
 
		userTaskMapper.insertSelective(userTask);
	}
	/**
	 * 修改任务
	* @Title: update
	* @Description: TODO 
	* author: Li Xiaoxiao 
	* @param @param userTask     
	* @return void     
	* @throws
	 */
	@Override
	public void update(UserTask userTask) {
		userTaskMapper.updateByPrimaryKeySelective(userTask);
		
	}
	/**
	* @Title: getAl
	* @Description: 查询当前月的所有任务 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<UserTaskFormBean>     
	* @throws
	 */
	@Override
	public List<UserTaskFormBean> getAl(Map<String, Object> map) {
		SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM"); 
		String date = sdfs.format(new Date());
		map.put("date", date);
		List<UserTask> byMonth = userTaskMapper.getByMonth(map);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<UserTaskFormBean> list=new LinkedList<UserTaskFormBean>();
		for (UserTask u:byMonth) {
			UserTaskFormBean uf=new UserTaskFormBean();
			uf.setId(u.getId());
			uf.setStart_date(sdf.format(u.getStartDate()));
			uf.setEnd_date(sdf.format(u.getEndDate()));
			uf.setText(u.getContent());
			uf.setSubject(u.getLevel());
			list.add(uf);
			
		}
		
		
		return list;
	}
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询任务 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return UserTask     
	* @throws
	 */
	@Override
	public UserTask getById(String id) {
		 
		return userTaskMapper.selectByPrimaryKey(id);
	}
	/**
	 * 
	* @Title: delete
	* @Description: 删除任务
	* author: Li Xiaoxiao 
	* @param @param id     
	* @return void     
	* @throws
	 */
	@Override
	public void delete(String id) {
		userTaskMapper.deleteByPrimaryKey(id);
	}

}
