package ses.service.bms;

import java.util.List;

import ses.model.bms.PreMenu;
import ses.model.bms.User;



public interface PreMenuServiceI {

	/**
	 * Description: 根据条件查询菜单
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param preMenu
	 * @return
	 * @exception IOException
	 */
	List<PreMenu> find(PreMenu preMenu);

	/**
	 * Description: 保存数据
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param menu
	 * @exception IOException
	 */
	void save(PreMenu menu);

	/**
	 * Description: 根据id查询
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param id
	 * @return PreMenu
	 * @exception IOException
	 */
	PreMenu get(String id);

	/**
	 * Description: 更新数据
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param menu
	 * @exception IOException
	 */
	void update(PreMenu menu);

	/**
	 * Description: 根据角色id数组查询角色-菜单关联
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param roleIds
	 * @return List<String>
	 * @exception IOException
	 */
	List<String> findByRids(String[] roleIds);

	/**
	 * Description: 根据用户id数组获取该用户的菜单id
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param userIds
	 * @return List<String>
	 * @exception IOException
	 */
	List<String> findByUids(String userIds);

	/**
	 * Description: 删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-25
	 * @param id
	 * @exception IOException
	 */
	void delete(String id);

	/**
   * Description: 获取用户的权限
   * 
   * @author Ye MaoLin
   * @version 2016-9-25
   * @param id
   * @exception IOException
   */
  List<PreMenu> getMenu(User u);

}
