package iss.controller.ps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.util.PropUtil;

import common.utils.UploadUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/browser")
public class BrowserController {
	
	
	@RequestMapping("/index")
	public String browserIndex(){
		return "/iss/ps/browsers";
	}
	
	/**
	 * 
	 *〈简述〉文件下载
	 *〈详细描述〉
	 * @author myc
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
	@RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response){
         String browserVersion = request.getParameter("ver");
		 String path = PropUtil.getProperty("file.base.path") + PropUtil.getProperty("file.browser.path");
		 UploadUtil.createDir(path);
		 String fileName = getBrowserVersion(browserVersion);
		 String filePath = path + File.separator + fileName;
		 downloadFile(request, response, filePath, fileName);
    }
	
	/**
	 * 
	 *〈简述〉文件下载
	 *〈详细描述〉
	 * @author myc
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 */
	private void downloadFile(HttpServletRequest request, HttpServletResponse response,String filePath ,String fileName){
        response.reset();
        String userAgent = request.getHeader("User-Agent"); 
        try {
            if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
            }
            File files = new File(filePath);
            response.setContentType("application/octet-stream");   
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            response.addHeader("Content-Length", "" + files.length());
            response.setCharacterEncoding("UTF-8"); 
            InputStream fis = new BufferedInputStream(new FileInputStream(files));   
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            UploadUtil.writeFile(fis, toClient);
            toClient.flush();   
            toClient.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  
	   }
	
	/**
	 * 
	 *〈简述〉 根据游览器版本返回文件名称
	 *〈详细描述〉
	 * @author myc
	 * @param browserVersion 游览器版本
	 * @return
	 */
	private String getBrowserVersion(String browserVersion){
		String fileName ="";
		 switch (browserVersion){
		   case "ie10_64" : 
			   fileName = PropUtil.getProperty("file.browser.ie10_64"); break;
		   case "ie11_32" :
			   fileName = PropUtil.getProperty("file.browser.ie11_32"); break;
		   case "ie11_64" :
			   fileName = PropUtil.getProperty("file.browser.ie11_64"); break;
		   case "firefox" :
			   fileName = PropUtil.getProperty("file.browser.firefox"); break;
		   case "chrome_64" :
			   fileName = PropUtil.getProperty("file.browser.chrome_64"); break;
		   case "chrome_32" :
			   fileName = PropUtil.getProperty("file.browser.chrome_32"); break;
		 }
		 return fileName;
	}
	
  	/**
     * 
     *〈简述〉ntko文件下载
     *〈详细描述〉
     * @author Ye Maolin
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    @RequestMapping("/ntkoDownload")
    public void ntkoDownload(HttpServletRequest request, HttpServletResponse response){
     String path = PropUtil.getProperty("file.base.path") + PropUtil.getProperty("file.ntko.path");
     UploadUtil.createDir(path);
     String fileName = "NTKO.exe";
     String filePath = path + File.separator + fileName;
     downloadFile(request, response, filePath, fileName);
    }
}

