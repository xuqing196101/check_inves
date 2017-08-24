package ses.dao.bms;

import java.util.List;

import ses.model.bms.PreMenu;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;

public interface PreMenuMapper {
	
	/**
	 * Description: 根据id查询
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param id 
	 * @return PreMenu
	 * @exception IOException
	 */
	PreMenu selectByPrimaryKey(String id);
	
	/**
	 * Description: 根据id删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param id
	 * @return int
	 * @exception IOException
	 */
	int deleteByPrimaryKey(String id);
	
    /**
     * Description: 插入数据
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param record
     * @return int
     * @exception IOException
     */
    int insert(PreMenu record);

    /**
     * Description: 插入不为空数据
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param record
     * @return
     * @exception IOException
     */
    int insertSelective(PreMenu record);
    
    /**
     * Description: 更新不为空数据
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param record
     * @return int
     * @exception IOException
     */
    int updateByPrimaryKeySelective(PreMenu record);
    
    /**
     * Description: 更新数据
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param record
     * @return int
     * @exception IOException
     */
    int updateByPrimaryKey(PreMenu record);

	/**
	 * Description: 根据条件查询
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param preMenu
	 * @return List<PreMenu>
	 * @exception IOException
	 */
	List<PreMenu> find(PreMenu preMenu);

	/**
	 * Description: 根据角色id数组获取角色-菜单关联
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param roleIds
	 * @return
	 * @exception IOException
	 */
	List<String> findByRids(String[] roleIds);

	/**
	 * Description: 根据用户id数组获取该用户的菜单id
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param userIds
	 * @exception IOException
	 */
	List<String> findByUids(String[] userIds);
	
	/**
	 * 
	 *〈简述〉获取最新的权限编码
	 *〈详细描述〉
	 * @author myc
	 * @param parentId 父级id
	 * @return
	 */
	String getPermisssinCode(String parentId);

  /**
   * 
   *〈简述〉获取用户权限增减量
   *〈详细描述〉
   * @author Ye Maolin
   * @param userPreMenu1
   * @return
   */
  List<String> findUserPre(UserPreMenu userPreMenu1);

  /**
   * 
   *〈简述〉根据权限id集合获取权限对象
   *〈详细描述〉
   * @author Ye Maolin
   * @param userPreMenu1
   * @return
   */
  List<PreMenu> findByMids(List<String> mIds);

}