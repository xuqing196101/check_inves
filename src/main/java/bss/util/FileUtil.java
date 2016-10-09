package bss.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
/**
 * @Title: FileUtil
 * @Description: 文件工具类
 * @author: Poppet_Brook
 * @date: 2016-6-14下午4:22:42
 */
public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);
	
	
	/**
	 * @Title: getBys
	 * @author: Poppet_Brook
	 * @date: 2016-7-13 上午10:57:26
	 * @Description: 获取文件字节数组
	 * @param: @param fileName
	 * @param: @return
	 * @return: byte[]
	 */
	public static byte[] getBys(String fileName ,HttpServletRequest request) {
		String path = FileUtil.getPath(request);
		BufferedInputStream input = null;
		try {
			input = new BufferedInputStream(new FileInputStream(path + fileName));
			return IOUtils.toByteArray(input);
		} catch (Exception e) {
			logger.error(e);
			logger.error("<获取" + fileName + ">字节数组失败 !");
			return null;
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			
		}
	}

	/**
	 * @Title: fileStash
	 * @author: Poppet_Brook
	 * @date: 2016-6-14 下午4:23:00
	 * @Description: 根据传递的文件夹名称在暂存文件夹下新建指定文件夹
	 * @param: @param srcFile
	 * @param: @param fileName
	 * @param: @return
	 * @return: String
	 */
	public static String fileStash(File srcFile, String folder, String fileName,HttpServletRequest request) {
		String path = FileUtil.getPath(request);
		path += folder + "/";
		fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + fileName;
		File destFile = new File(path + fileName);
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			logger.error(e);
			logger.error("<" + fileName + ">暂存失败 !");
		}
		return fileName;
	}

	/**
	 * @Title: removeStash
	 * @author: Poppet_Brook
	 * @date: 2016-6-14 下午4:23:18
	 * @Description: 删除暂存文件
	 * @param: @param fileName
	 * @return: void
	 */
	public static void removeStash(String fileName,HttpServletRequest request) {
		final String path = FileUtil.getPath(request) + fileName;
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
	 * @Title: removeStash
	 * @author: Poppet_Brook
	 * @date: 2016-6-14 下午4:23:18
	 * @Description: 删除暂存文件
	 * @param: @param fileName
	 * @return: void
	 */
	public static void removeStash(String fileName, String folder,HttpServletRequest request) {
		final String path = FileUtil.getPath(request) + folder + "/" + fileName;
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
	 * @Title: getPath
	 * @author: Poppet_Brook
	 * @date: 2016-6-14 下午4:23:43
	 * @Description: 获取暂存路径
	 * @param: @return
	 * @return: String
	 */
	public static String getPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/") + PropUtil.getProperty("file.stashPath") + "/";
		return path.replace("\\", "/");
	}
	
	
	/**
	 * @Title: getRootPath
	 * @author: Poppet_Brook
	 * @date: 2016-6-20 上午11:16:32
	 * @Description: 获取项目根路径
	 * @param: @return
	 * @return: String
	 */
	public static String getRootPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}




}