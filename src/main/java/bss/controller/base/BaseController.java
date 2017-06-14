package bss.controller.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	@ModelAttribute
	public void setReqAndResp(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * 输出信息到页面
	 * @param msg：要输出的信息（可以是js脚本等）
	 */
	public void printOutMsg(String msg){
		PrintWriter out = null;
		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.setContentType("text/html;charset=utf-8");
			out = this.response.getWriter();
			out.print(msg);
			//out.flush();
			//out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * 输出信息到页面
	 * @param response
	 * @param msg
	 */
	public void printOutMsg(HttpServletResponse response, String msg){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.print(msg);
			//out.flush();
			//out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
	
}
