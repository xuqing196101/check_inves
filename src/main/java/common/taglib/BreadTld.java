package common.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ses.model.bms.PreMenu;

public class BreadTld extends TagSupport {

    /**
     * @Fields serialVersionUID : TODO(目的和意义)
     */
    private static final long serialVersionUID = 6904139287771673694L;
    
    private List<String> menuList = null;
    
    private String url;

    @Override
    public int doStartTag() throws JspException {
        menuList = new ArrayList<String>();
        bread();
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        
        if (menuList != null && menuList.size() > 0){
            try {
                JspWriter out = pageContext.getOut();
                out.println("<div class=\"margin-top-10 breadcrumbs \">");
                out.println("<div class=\"container\">");
                out.println("<ul class=\"breadcrumb margin-left-0\">");
                
                for (int i = 0; i<menuList.size(); i++){
                    if (i == menuList.size() -1){
                        out.println("<li><a href='javascript:void(0);'  class=\"active\">" + menuList.get(i) + "</a></li>");
                    } else {
                        out.println("<li><a href='javascript:void(0);'>" + menuList.get(i) + "</a></li>");
                    }
                    
                }
                
                out.print("</ul>");
                out.println("<div class=\"clear\"/>");
                out.print("</div>");
                out.print("</div>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return SKIP_BODY;
    }

    @Override
    public void release() {
        menuList = null;
        url = "";
        super.release();
    }

    @SuppressWarnings("unchecked")
    private void bread(){
        List<PreMenu> list = (List<PreMenu>)pageContext.getSession().getAttribute("resource");
        if (list != null && list.size() > 0){
            for (PreMenu menu : list){
                if (menu != null && menu.getUrl() != null){
                     if (url.indexOf(menu.getUrl()) != -1){
                         load(list, menu.getId());
                     }
                }
            }
        }
    }
    
    private void load(List<PreMenu> list,String id){
        for (PreMenu menu: list){
            if (menu != null){
                if (menu.getId().equals(id)){
                        if (menu.getParentId() != null){
                            load(list, menu.getParentId().getId());
                        }
                    menuList.add(menu.getName());
                }
            }
        }
        
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }
    
    
}
