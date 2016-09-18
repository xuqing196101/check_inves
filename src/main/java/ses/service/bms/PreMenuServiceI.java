package ses.service.bms;

import java.util.List;

import ses.model.bms.PreMenu;
import ses.model.bms.RolePreMenu;



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
	 * Description: 根据角色id查询角色-菜单关联
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param roleIds
	 * @return List<String>
	 * @exception IOException
	 */
	List<String> findByRole(String[] roleIds);

}
