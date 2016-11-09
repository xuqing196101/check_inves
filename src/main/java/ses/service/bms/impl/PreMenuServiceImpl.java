package ses.service.bms.impl;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.PreMenuMapper;
import ses.model.bms.PreMenu;
import ses.service.bms.PreMenuServiceI;

/**
 * Description: 权限菜单业务实现类
 * 
 * @author Ye MaoLin
 * @version 2016-9-18
 * @since JDK1.7
 */
@Service("premenuService")
public class PreMenuServiceImpl implements PreMenuServiceI {

	@Autowired
	private PreMenuMapper preMenuMapper;
	/** 导航权限编码初始值  */
	private final static String NAV_CODE = "1001";
	/** 折叠导航权限编码初始值  */
	private final static String ACC_CODE = "1001001";
	/** 菜单权限编码初始值  */
	private final static String MENU_CODE = "1001001001";
	/** 按钮权限编码初始值  */
	private final static String BUTTON_CODE = "10010010010031";
	
	/** 跟节点初始值  */
    private final static String ROOT_CODE = "1";
    /** 增长步长  */
    private final static String INC_ONE = "001";
    /** 增长步长  */
    private final static String INC_THIRTY_ONE = "0031"; 
	
	/** 导航类型 */
	private final static String NAV_TYPE = "navigation";
	/** 折叠导航类型 */
	private final static String ACC_TYPE = "accordion";
	/** 菜单类型 */
	private final static String MENU_TYPE = "menu";
	/** 按钮类型 */
	private final static String BUTTON_TYPE = "button";
	

	@Override
	public List<PreMenu> find(PreMenu preMenu) {
		return preMenuMapper.find(preMenu);
	}

	@Override
	public void save(PreMenu menu) {
	    genPerimissionCode(menu);
		preMenuMapper.insertSelective(menu);
	}

	@Override
	public PreMenu get(String id) {
		return preMenuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(PreMenu menu) {
		preMenuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public List<String> findByRids(String[] roleIds) {
		return preMenuMapper.findByRids(roleIds);
	}

	@Override
	public List<String> findByUids(String[] userIds) {
		return preMenuMapper.findByUids(userIds);
	}

	@Override
	public void delete(String id) {
		preMenuMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 
	 *〈简述〉
	 *  生成权限编码
	 *〈详细描述〉
	 * @author myc
	 * @param menu {@link PreMenu}
	 */
	private void genPerimissionCode(PreMenu menu){
	    Lock lock = new ReentrantLock();
	    lock.lock();
	    try {
            if (menu != null && menu.getParentId() != null){
                if (StringUtils.isNotBlank(menu.getParentId().getId())){
                    String code = preMenuMapper.getPermisssinCode(menu.getParentId().getId());
                    if (StringUtils.isNotBlank(code)) {
                        long codeInteger = Long.parseLong(code);
                        menu.setPermissionCode(Long.toString(codeInteger + 1));
                    } else {
                        PreMenu premenu = preMenuMapper.selectByPrimaryKey(menu.getParentId().getId());
                        //默认值
                        if (premenu == null) {
                            initPerCode(menu);
                        }
                        //获取父级值
                        if (premenu != null) {
                            if (StringUtils.isNotBlank(menu.getType())) {
                                if (menu.getType().equals(NAV_TYPE) || menu.getType().equals(ACC_TYPE)
                                        || menu.getType().equals(MENU_TYPE)) {
                                    menu.setPermissionCode(premenu.getPermissionCode() + INC_ONE);
                                }
                                if (menu.getType().equals(BUTTON_TYPE)) {
                                    menu.setPermissionCode(premenu.getPermissionCode() + INC_THIRTY_ONE);
                                }
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
	}
	
	/**
	 * 
	 *〈简述〉 初始化默认值
	 *〈详细描述〉
	 * @author myc
	 * @param menu {@link PreMenu}
	 */
	private void initPerCode(PreMenu menu){
	    if (StringUtils.isBlank(menu.getType())){
            menu.setPermissionCode(ROOT_CODE);
        }
        if (StringUtils.isNotBlank(menu.getType()) && menu.getType().equals(NAV_TYPE)) {
            menu.setPermissionCode(NAV_CODE);
        }
        if (StringUtils.isNotBlank(menu.getType()) && menu.getType().equals(ACC_TYPE)) {
            menu.setPermissionCode(ACC_CODE);
        }
        if (StringUtils.isNotBlank(menu.getType()) && menu.getType().equals(MENU_TYPE)) {
            menu.setPermissionCode(MENU_CODE);
        }
        if (StringUtils.isNotBlank(menu.getType()) && menu.getType().equals(BUTTON_TYPE)) {
            menu.setPermissionCode(BUTTON_CODE);
        }
	}

}
