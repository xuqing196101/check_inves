package ses.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.bms.PreMenu;
import ses.service.bms.PreMenuServiceI;

/**
 * 版权：(C) 版权所有 
 * <简述>权限控制
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Component
public class AuthorityUtil {
    
    
    @Autowired
    private PreMenuServiceI preMenuService;
    
    private static AuthorityUtil authorityUtil;
    
    public void setDdService(PreMenuServiceI preMenuService){
        this.preMenuService = preMenuService;
    }
    
    @PostConstruct 
    public void init(){
      authorityUtil = this;
      authorityUtil.preMenuService = this.preMenuService;
    }
    
    /**
     *〈简述〉查询权限菜单表
     *〈详细描述〉
     * @author Ye MaoLin
     * @param preMenu
     * @return
     */
    public static List<PreMenu> find(PreMenu preMenu){
        return authorityUtil.preMenuService.find(preMenu);
    }
    
}
