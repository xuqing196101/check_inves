package ses.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Title: FtpUtil
 * @Description: 文件 FTP 上传工具类
 * @Company: yggc 
 * @author: Poppet_Brook
 * @date: 2016-7-7下午1:43:40
 */
public class FtpUtil {

	private static Logger logger = Logger.getLogger(FtpUtil.class);

	private static FTPClient ftp;
	
	/**
	 * @Title: connectFtp
	 * @author: Poppet_Brook
	 * @date: 2016-6-7 下午1:36:45
	 * @Description: 获取 FTP 链接
	 * @param: @return
	 * @param: @throws Exception
	 * @return: boolean
	 */
	public static boolean connectFtp(String path) {
		/** ip, 端口, 用户名, 密码 */
		String host = PropUtil.getProperty("ftp.host");
		Integer port = Integer.parseInt(PropUtil.getProperty("ftp.port"));
		String username = PropUtil.getProperty("ftp.username");
		String password = PropUtil.getProperty("ftp.password");

		ftp = new FTPClient();
		int reply;
		try {
			if (port == null) {
				ftp.connect(host, 21);
			} else {
				ftp.connect(host, port);
			}
			boolean login = ftp.login(username, password);
			boolean setFileType = ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return false;
			}
			if (!ftp.changeWorkingDirectory(path)) {
				ftp.makeDirectory(path);
				ftp.changeWorkingDirectory(path);
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: closeFtp
	 * @author: Poppet_Brook
	 * @date: 2016-6-7 下午1:57:29
	 * @Description: 关闭 FTP
	 * @param:
	 * @return: void
	 */
	public static void closeFtp() {
		if (ftp != null && ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * @Title: upload
	 * @author: Poppet_Brook
	 * @date: 2016-6-8 下午4:31:51
	 * @Description: 文件上传
	 * @param: @param file
	 * @param: @return
	 * @return: String
	 */
	public static String upload(File file) {
		try {
			if (file.isDirectory()) {
				ftp.makeDirectory(file.getName());
				ftp.changeWorkingDirectory(file.getName());
				String[] files = file.list();
				for (String fstr : files) {
					File file1 = new File(file.getPath() + "/" + fstr);
					if (file1.isDirectory()) {
						upload(file1);
						ftp.changeToParentDirectory();
					} else {
						File file2 = new File(file.getPath() + "/" + fstr);
						FileInputStream input = new FileInputStream(file2);
						ftp.storeFile(file2.getName(), input);
						input.close();
					}
				}
				return null;
			} else {
				File file2 = new File(file.getPath());
				FileInputStream input = new FileInputStream(file2);
				String fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase().toString() + "_" +file2.getName();
				boolean flag = ftp.storeFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"), input);
				input.close();
				String url = PropUtil.getProperty("ftp.root") + ftp.printWorkingDirectory() + "/" + fileName;
				return url;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: startDown
	 * @author: Poppet_Brook
	 * @date: 2016-6-8 下午4:32:40
	 * @Description: 下载文件夹, localBaseDir 为本地目录, remoteBaseDir 为远程目录
	 * @param: @param path
	 * @param: @param localBaseDir
	 * @param: @param remoteBaseDir
	 * @return: void
	 */
	public static void startDownFolder(String localBaseDir, String remoteBaseDir) {
		if (FtpUtil.connectFtp(remoteBaseDir)) {
			try {
				FTPFile[] files = null;
				ftp.setControlEncoding("GBK");
				files = ftp.listFiles();
				for (int i = 0; i < files.length; i++) {
					try {
						downloadFolder(files[i], localBaseDir);
					} catch (Exception e) {
						logger.error(e);
						logger.error("<" + files[i].getName() + ">下载失败");
					}
				}
			} catch (Exception e) {
				logger.error(e);
				logger.error("下载过程中出现异常");
			}
		} else {
			logger.error("链接失败！");
		}

	}

	/**
	 * @Title: startDownFile
	 * @author: Poppet_Brook
	 * @date: 2016-6-12 上午8:47:12
	 * @Description: 下载文件
	 * @param: @param localBaseDir
	 * @param: @param remoteBaseDir
	 * @param: @param fileName
	 * @return: void
	 */
	public static void startDownFile(String localBaseDir, String remoteBaseDir, String fileName) {
		if (FtpUtil.connectFtp(remoteBaseDir)) {
			try {
				ftp.setControlEncoding("GBK");
				FTPFile ftpFile = ftp.mlistFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"));
				downloadFile(ftpFile, localBaseDir, fileName);
			} catch (Exception e) {
				logger.error(e);
				logger.error("下载过程中出现异常");
			}
		} else {
			logger.error("链接失败！");
		}

	}

	/**
	 * @Title: downloadFile
	 * @author: Poppet_Brook
	 * @date: 2016-6-12 上午9:27:22
	 * @Description: 进行下载文件
	 * @param: @param ftpFile
	 * @param: @param relativeLocalPath
	 * @param: @param fileName
	 * @return: void
	 */
	private static void downloadFile(FTPFile ftpFile, String relativeLocalPath, String fileName) {
		if (ftpFile.isFile()) {
			if (ftpFile.getName().indexOf("?") == -1) {
				OutputStream outputStream = null;

				/** 创建本地文件夹 */
				File locaFolder = new File(relativeLocalPath);
				if (!locaFolder.exists()) {
					locaFolder.mkdirs();
				}

				try {
					File locaFile = new File(relativeLocalPath + "/" + fileName);
					/** 判断文件是否存在，存在则返回 */
					if (locaFile.exists()) {
						return;
					} else {
						outputStream = new FileOutputStream(relativeLocalPath + "/" + fileName);
						ftp.retrieveFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"), outputStream);
						outputStream.flush();
						outputStream.close();
					}
				} catch (Exception e) {
					logger.error(e);
				} finally {
					try {
						if (outputStream != null) {
							outputStream.close();
						}
					} catch (IOException e) {
						logger.error("输出文件流异常");
					}
				}
			}
		}
	}

	/**
	 * @Title: downloadFile
	 * @author: Poppet_Brook
	 * @date: 2016-6-7 下午2:15:41
	 * @Description: 进行下载文件夹
	 * @param: @param ftpFile
	 * @param: @param relativeLocalPath
	 * @param: @param relativeRemotePath
	 * @return: void
	 */
	private static void downloadFolder(FTPFile ftpFile, String relativeLocalPath) {
		if (ftpFile.isFile()) {
			if (ftpFile.getName().indexOf("?") == -1) {
				OutputStream outputStream = null;

				/** 创建本地文件夹 */
				File locaFolder = new File(relativeLocalPath);
				if (!locaFolder.exists()) {
					locaFolder.mkdirs();
				}

				try {
					File locaFile = new File(relativeLocalPath + "/" + ftpFile.getName());
					/** 判断文件是否存在，存在则返回 */
					if (locaFile.exists()) {
						return;
					} else {
						outputStream = new FileOutputStream(relativeLocalPath + "/" + ftpFile.getName());
						ftp.retrieveFile(ftpFile.getName(), outputStream);
						outputStream.flush();
						outputStream.close();
					}
				} catch (Exception e) {
					logger.error(e);
				} finally {
					try {
						if (outputStream != null) {
							outputStream.close();
						}
					} catch (IOException e) {
						logger.error("输出文件流异常");
					}
				}
			}
		} else {
			String newlocalRelatePath = relativeLocalPath + "/" + ftpFile.getName();
			File fl = new File(newlocalRelatePath);
			if (!fl.exists()) {
				fl.mkdirs();
			}
			try {
				newlocalRelatePath = newlocalRelatePath + '/';
				String currentWorkDir = ftpFile.getName().toString();
				boolean changedir = ftp.changeWorkingDirectory(currentWorkDir);
				if (changedir) {
					FTPFile[] files = null;
					files = ftp.listFiles();
					for (int i = 0; i < files.length; i++) {
						downloadFolder(files[i], newlocalRelatePath);
					}
				}
				if (changedir) {
					ftp.changeToParentDirectory();
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	/**
	 * Description: 上传MultipartFile类型附件
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-6
	 * @param uploadFile
	 * @exception IOException
	 */
	public static String upload2(String path, MultipartFile uploadFile){
		if (FtpUtil.connectFtp(path)){
			try{
				String fileName = new String((UUID.randomUUID().toString().replace("-", "").toUpperCase().toString() + "_" + uploadFile.getOriginalFilename()).getBytes("utf-8"),"iso-8859-1");
				//判断是否存在该文件
				FTPFile[] fs = ftp.listFiles();  
				if (fs!=null && fs.length>0) {
					for(int i=0;i<fs.length;i++){
						if (fs[i].getName().equals(fileName)) {
							ftp.deleteFile(fs[i].getName());
							break;
						}
					}
				}
				OutputStream os = ftp.appendFileStream(fileName);
				byte[] bytes = new byte[1024];
				InputStream is = uploadFile.getInputStream();
				// 开始复制 其实net已经提供了上传的函数，但是我想可能是我这个版本有点问题 
				int c;
				// 暂未考虑中途终止的情况
				while ((c = is.read(bytes)) != -1) {
					os.write(bytes, 0, c);
				}
				os.flush();
				is.close();
				os.close();
				ftp.logout();
				String url = new String((PropUtil.getProperty("ftp.root")+ "/" + path + "/" + fileName).getBytes("ISO-8859-1"),"utf-8");
				return url;
			} catch (IOException e) { 
				e.printStackTrace(); 
			} finally { 
				if (ftp.isConnected()) {  
					try {  
						ftp.disconnect();  
					} catch (IOException ioe) {  
					}  
				}
			}
		} else {
			logger.error("链接失败！");
		}
		return null;
	}
	
	
	public static void download2(HttpServletResponse response,String path, String fileName){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("multipart/form-data;charset=UTF-8");
		if (FtpUtil.connectFtp(path)) {
			try {
				 FTPFile[] fs = ftp.listFiles();  
			        for(int i=0;i<fs.length;i++){  
			        	String tempFileName =  new String(fs[i].getName().getBytes("ISO8859-1"), "UTF-8");
			            if(tempFileName.equals(fileName)){
			            	String saveAsFileName = tempFileName.substring(tempFileName.lastIndexOf("_"));
			    			response.setHeader("Content-Disposition", "attachment;fileName="+saveAsFileName);
			    			OutputStream os = response.getOutputStream();
			    			ftp.retrieveFile(fs[i].getName(), os);
			                os.flush();
			                os.close();
			                break;
			            }
			        }
			        ftp.logout(); 
			} catch (Exception e) {
				logger.error(e);
				logger.error("下载过程中出现异常");
			} finally {  
		        if (ftp.isConnected()) {  
		            try {  
		            	ftp.disconnect();  
		            } catch (IOException ioe) {  
		            }  
		        }  
		    }  
		} else {
			logger.error("链接失败！");
		}
	}
	
	public static void main(String[] args) throws Exception {
//		FtpUtil.connectFtp("E:/ftp/test5");
//		File file = new File("e:\\广东商城接口对接实施方案1.2.doc");
//		FtpUtil.upload(file);
//		FtpUtil.closeFtp();
		// FtpUtil.startDownFolder("E:\\ftpCopy", "zxcvb");
		//FtpUtil.startDownFile("E:/ftpCopy", "test5", "2016-10-08_18时07分03.003秒_log4j.log");
	}

}