package ses.controller.sys.sms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.util.PropUtil;
import bss.controller.base.BaseController;

import com.alibaba.fastjson.JSON;

@Controller
@Scope("prototype")
public class BaseSupplierController extends BaseController {
	private static Logger logger = Logger.getLogger(BaseSupplierController.class);

	/**
	 * @Title: writeJson
	 * @author: Wang Zhaohua
	 * @date: 2016-10-13 下午3:55:01
	 * @Description: writeJson
	 * @param: @param response
	 * @param: @param object
	 * @return: void
	 */
	public void writeJson(HttpServletResponse response, Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			System.out.println(json+"--============");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: removeStash
	 * @author: Wang Zhaohua
	 * @date: 2016-10-12 下午7:05:22
	 * @Description: 移除暂存
	 * @param: @param request
	 * @param: @param fileName
	 * @return: void
	 */
	public void removeStash(HttpServletRequest request, String fileName) {
		final String path = this.getStashPath(request) + fileName;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// String path = FileUtil.getPath();
				File file = new File(path);
				if (file.isFile()) {
					file.delete();
				}
			}
		}, 10000);
	}

	/**
	 * @Title: getStashPath
	 * @author: Wang Zhaohua
	 * @date: 2016-10-12 下午6:41:48
	 * @Description: 获取暂存路径
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 */
	public String getStashPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/") + PropUtil.getProperty("file.stashPath") + "/";
		return path.replace("\\", "/");
	}

	/**
	 * @Title: download
	 * @author: Wang Zhaohua
	 * @date: 2016-10-12 下午7:03:46
	 * @Description: 文件下载
	 * @param: @param request
	 * @param: @param response
	 * @param: @param fileName
	 * @return: void
	 */
	@RequestMapping(value = "download")
	public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			response.reset();
			String fileUrl = this.getStashPath(request) + fileName;
			File file = new File(fileUrl);
			if (!file.isFile()) {
				this.alert(request, response, "无附件下载", true);
				return;
			}
			input = new BufferedInputStream(new FileInputStream(file));
			fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
			fileName = fileName.substring(fileName.lastIndexOf("_") + 1);
			fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			output = new BufferedOutputStream(response.getOutputStream());
			IOUtils.copy(input, output);
			output.flush();
		} catch (Exception e) {
			logger.error(e);
			logger.error("<" + fileName + ">下载失败 !");
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}

	/**
	 * @Title: alert
	 * @author: Wang Zhaohua
	 * @date: 2016-10-20 下午7:49:59
	 * @Description: 弹出框 isClosed 下载完后是否关闭窗口
	 * @param: @param request
	 * @param: @param response
	 * @param: @param msg
	 * @param: @param isClosed
	 * @return: void
	 */
	public void alert(HttpServletRequest request, HttpServletResponse response, String msg, boolean isClosed) {
		String path = request.getSession().getServletContext().getContextPath();
		StringBuffer sbff = new StringBuffer();
		sbff.append("<html>")//
				.append("<head>")//
				.append("<script type='text/javascript' src='")//
				.append(path).append("/public/ZHQ/js/jquery.min.js'></script>")//
				.append("<script type='text/javascript' src='")//
				.append(path).append("/public/layer/layer.js'></script>")//
				.append("<script type='text/javascript'>")//
				.append("$(function() {")//
				.append("layer.msg('")//
				.append(msg).append("');");
		if (isClosed) {
			sbff.append("setTimeout(function(){window.close();}, 1000);");
		} else {
			sbff.append("setTimeout(function(){window.history.go(-1);}, 1000);");
		}
		sbff.append("});")//
				.append("</script>")//
				.append("</head>")//
				.append("</html>");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(sbff.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
