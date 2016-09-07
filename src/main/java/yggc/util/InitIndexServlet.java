package yggc.util;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import yggc.service.iss.SolrNewsService;

@SuppressWarnings("serial")
public class InitIndexServlet extends HttpServlet implements HttpSessionListener,ServletContextListener{
	@Autowired
	private SolrNewsService solrNewsService;
	
	private ApplicationContext applicationContext;
	
	/**
	 * 
	* @Title: sessionCreated
	* @author MRlovablee
	* @date 2016-5-31 上午9:43:17  
	* @Description: session启动时初始化 
	* @param @param se
	 */
	public void sessionCreated(HttpSessionEvent se) {
//		ServletContext servletContext = se.getSession().getServletContext();
//		if (applicationContext == null) {
//			applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//		}
//		if (solrNewsService == null) {
//			solrNewsService = (SolrNewsService)applicationContext.getBean("solrNewsService");
//		}
//		solrNewsService.initIndex();
	}
	
	/**
	 * 
	* @Title: contextDestroyed
	* @author MRlovablee
	* @date 2016-5-31 上午9:43:44  
	* @Description: 服务器关闭时自动将isIndex设定为未索引状态 
	* @param @param sce
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}
}
