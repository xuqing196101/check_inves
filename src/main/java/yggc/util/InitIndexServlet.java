package yggc.util;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

import yggc.service.iss.SolrNewsService;

@SuppressWarnings("serial")
public class InitIndexServlet extends HttpServlet implements HttpSessionListener,ServletContextListener{
	@Autowired
	private SolrNewsService solrNewsService;
	
	/**
	 * 
	* @Title: sessionCreated
	* @author MRlovablee
	* @date 2016-5-31 上午9:43:17  
	* @Description: session启动时初始化 
	* @param @param se
	 */
	public void sessionCreated(HttpSessionEvent se) {
		solrNewsService.initIndex();
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
