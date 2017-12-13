package ses.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import ses.model.bms.PreMenu;
import ses.model.bms.User;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.UserDataRuleService;
import ses.service.bms.UserServiceI;

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
    @Autowired
    private UserServiceI userService;
    @Autowired
    private UserDataRuleService userDataRuleService;
    
    private static AuthorityUtil authorityUtil;
    
    public void setDdService(PreMenuServiceI preMenuService, UserServiceI userService, UserDataRuleService userDataRuleService){
        this.preMenuService = preMenuService;
        this.userService = userService;
        this.userDataRuleService = userDataRuleService;
    }
    
    @PostConstruct 
    public void init(){
      authorityUtil = this;
      authorityUtil.preMenuService = this.preMenuService;
      authorityUtil.userService = this.userService;
      authorityUtil.userDataRuleService = this.userDataRuleService;
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
    
    /**
     *〈简述〉判断用户数据权限是否维护
     *〈详细描述〉
     * @author Ye Maolin
     * @param dataAccess 
     * @param request 
     * @param response 
     * @return
     * @throws IOException 
     */
    public static String valiDataAccess(Integer dataAccess, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
    	try {
			StringBuilder builder = new StringBuilder();
			builder.append("<HTML><HEAD>");
			builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/backend/js/jquery.min.js'></script>");
			builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/layer/layer.js'></script>");
			builder.append("<link href='"+request.getContextPath()+"/public/backend/css/common.css' media='screen' rel='stylesheet'>");
			builder.append("</HEAD>");
			builder.append("<script type=\"text/javascript\">"); 
			builder.append("$(function() {");
			builder.append("layer.confirm('请管理员维护数据查看权限！',{ btn: ['确定'],title:'提示',area : '240px',offset: ['30%' , '40%'],shade:0.01 },function(){");  
			builder.append("window.top.location.href='"); 
			builder.append(request.getContextPath()+"/login/index.do");  
			builder.append("';"); 
			builder.append("});");
			builder.append("});");
			builder.append("</script>");  
			builder.append("<BODY><div style='width:1000px; height: 1000px;'></div></BODY></HTML>");
			out.print(builder.toString());
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != out){
				out.flush();
				out.close();
			}
		}
		return "";
    }
    
    /**
     *〈简述〉获取当前登录用户的数据查看权限
     *〈详细描述〉
     * @author Ye Maolin
     * @param userId 当前登录用户id
     * @return
     * @throws IOException 
     */
    public static HashMap<String, Object> dataAuthority(String userId) throws IOException{
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	User user = authorityUtil.userService.getUserById(userId);
    	if (user != null) {
    		Integer dataAccess = user.getDataAccess();
    		if (dataAccess == null) {
				//需要管理员维护数据权限
    			//valiDataAccess(dataAccess, AuthorityUtil.request, AuthorityUtil.response);
			}else if (dataAccess == 1) {
				//查询所有数据
    			map.put("dataAccess", 1);
			}else if (dataAccess == 2 && !"5".equals(user.getTypeName())) {
				//如果当前登录用户的数据权限是本单位并且机构类型不是监管部门，则查询该用户所属机构的数据
				map.put("dataAccess", 2);
				List<String> orgId = new ArrayList<String>();
				orgId.add(user.getOrgId());
				map.put("superviseOrgs", orgId);
			}else if (dataAccess == 2 && "5".equals(user.getTypeName())) {
				//如果当前登录用户的数据权限是本单位并且机构类型是监管部门，则查询该用户监管对象机构的数据(用户监管对象关联表：T_SES_BMS_USER_DATA_RULE)
				map.put("dataAccess", 2);
				List<String> orgIds = authorityUtil.userDataRuleService.getOrgID(userId);
				map.put("superviseOrgs", orgIds);
			}else if (dataAccess == 3) {
				//查询本人数据
				map.put("dataAccess", 3);
			}
		}
    	return map;
    }
}
