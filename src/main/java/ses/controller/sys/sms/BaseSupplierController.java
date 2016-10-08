package ses.controller.sys.sms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.util.PropUtil;

import com.alibaba.fastjson.JSON;

@Controller
@Scope("prototype")
public class BaseSupplierController {
	private static Logger logger = Logger.getLogger(FileUtil.class);

	public void writeJson(HttpServletResponse response, Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFilePath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/").split("\\\\")[0] + "/" + PropUtil.getProperty("file.upload.path.supplier");
	}

	@RequestMapping(value = "download")
	public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			response.reset();
			String fileUrl = this.getFilePath(request) + fileName;
			File file = new File(fileUrl);
			if (!file.isFile()) {
				this.alert(request, response, "无附件下载");
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

	public void alert(HttpServletRequest request, HttpServletResponse response, String msg) {
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
				.append(msg).append("');")//
				.append("setTimeout(function(){window.close();}, 1000);")//
				//.append("window.close();")//
				.append("});")//
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
}
