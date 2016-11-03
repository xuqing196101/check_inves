package common.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import common.constant.Constant;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>初始化类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class CommonServlet extends HttpServlet {

    /**
     * @Fields serialVersionUID : TODO(目的和意义)
     */
    private static final long serialVersionUID = -3413418796193828757L;
    
    /**
     * 
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        Constant.init();
        super.init(config);
    }

    
}
