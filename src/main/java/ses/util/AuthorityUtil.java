package ses.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		out.flush();  
		out.close(); 
		return "";
    }
}
