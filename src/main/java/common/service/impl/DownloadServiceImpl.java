package common.service.impl;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.surefire.shade.org.apache.maven.shared.artifact.filter.StatisticsReportingArtifactFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropUtil;
import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.service.DownloadService;
import common.utils.UploadUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>文件下载service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class DownloadServiceImpl implements DownloadService {
    
    /** 分割标识 */
    private static final String SPLIT_MARK = ",";
    
    /** 文件dao */
    @Autowired
    private FileUploadMapper fileDao;
    
    /**
     * 
     * @see common.service.DownloadService#download(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Integer systemKey = Integer.parseInt(request.getParameter("key"));
        String tableName = Constant.fileSystem.get(systemKey);
        String zipFileName = request.getParameter("zipFileName");
        String targetFileName = request.getParameter("fileName");
        if (StringUtils.isNotBlank(id)){
            if (id.contains(SPLIT_MARK)){
               String [] array = id.split(SPLIT_MARK);
               List<UploadFile> files =  fileDao.getFilesByIds(array, tableName);
               if (files != null && files.size() > 0){
                   String path = PropUtil.getProperty("file.base.path") + File.separator + PropUtil.getProperty("file.temp.path");
                   UploadUtil.createDir(path);
                   String fileName =  PropUtil.getProperty("file.batch.download.name");
                   if (StringUtils.isNotBlank(zipFileName) && !zipFileName.equals("null")){
                       try {
                        fileName = URLDecoder.decode(zipFileName,"UTF-8") + ".zip";
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                   }
                   File zipFile = new File(path, fileName);
                   zipFile(zipFile, files);
                   downloadFile(request, response, zipFile.getPath(), fileName);
               }
            } else {
                UploadFile file = fileDao.getFileById(id, tableName);
                if (file == null){
                    List<UploadFile> fileByBusinessId = fileDao.getFileByBusinessId(id, null, tableName);
                    if( fileByBusinessId !=null && fileByBusinessId.size() !=0){
                        file=fileByBusinessId.get(0);
                    }
                }
                if (file != null){
                    
                    String fileName = file.getName();
                    if (StringUtils.isNotBlank(targetFileName) && !targetFileName.equals("null")){
                        try {
                            fileName = URLDecoder.decode(targetFileName,"UTF-8") +  fileName.substring(fileName.lastIndexOf(".")) ;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    downloadFile(request, response, file.getPath(), fileName);
                }
            }
         }
       }
    
    /**
     * 
     *〈简述〉将文件写道客户端
     *〈详细描述〉
     * @author myc
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,String filePath ,String fileName){
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
     *〈简述〉将批量下载的文件打成zip包,进行下载
     *〈详细描述〉
     * @author myc
     * @param zipFile 打包的zip文件
     * @param list {@link UploadFile list}
     * @return 成功返回true,失败返回false
     */
    public  boolean  zipFile(File zipFile , List<UploadFile> list) {
        boolean flag = true;
        try {
            ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            
            // 重命名重复文件名
            Map<String, Integer> fileNameMap = new HashMap<String, Integer>();
            for (UploadFile file : list){
            	String fileName = file.getName();
            	if(fileNameMap.containsKey(fileName)){
            		int index = fileNameMap.get(fileName) + 1;
            		fileNameMap.put(fileName, index);
            		String preffix = fileName.substring(0, fileName.lastIndexOf("."));
            		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            		preffix = preffix + "-" + (index);
            		file.setName(preffix + suffix);
            	}else{
            		fileNameMap.put(fileName, 0);
            	}
            }
            
            for (UploadFile file :list){
                InputStream in = new BufferedInputStream(new FileInputStream(new File(file.getPath())));
                //System.out.println(file.getName());
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);
                UploadUtil.writeFile(in, zipOut);
            }
            zipOut.flush();   
            zipOut.close();  
        } catch (FileNotFoundException e) {
            flag = false;
            e.printStackTrace();
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        } 
        
        return flag;
    }
    
    /**
     * 
     * @see common.service.DownloadService#downloadOther(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.String)
     */
    @Override
    public void downloadOther(HttpServletRequest request, HttpServletResponse response, String id, String sysKey) {
        Integer systemKey = Integer.parseInt(sysKey);
        String tableName = Constant.fileSystem.get(systemKey);
        if (StringUtils.isNotBlank(id)){
            if (id.contains(SPLIT_MARK)){
                String [] array = id.split(SPLIT_MARK);
                List<UploadFile> files =  fileDao.getFilesByIds(array, tableName);
                if (files != null && files.size() > 0){
                   String path = PropUtil.getProperty("file.base.path") + File.separator + PropUtil.getProperty("file.temp.path");
                   UploadUtil.createDir(path);
                   String fileName = PropUtil.getProperty("file.batch.download.name");
                   File zipFile = new File(path, fileName);
                   zipFile(zipFile, files);
                   downloadFile(request, response, zipFile.getPath(), fileName);
                }
            } else {
                UploadFile file = fileDao.getFileById(id, tableName);
                downloadFile(request, response, file.getPath(), file.getName());
            }
        }
    }
    
    /**
     * 
     * @see common.service.DownloadService#downLoadFile(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
     */
    @Override
    public void downLoadFile(HttpServletRequest request,HttpServletResponse response, String filePath) {
        if (StringUtils.isNotBlank(filePath)){
            File file = new File(filePath);
            downloadFile(request, response, filePath, file.getName());
        }
    }

	@Override
	public void downloadOneFile(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
        Integer systemKey = Integer.parseInt(request.getParameter("key"));
        String tableName = Constant.fileSystem.get(systemKey);
        String zipFileName = request.getParameter("zipFileName");
        String targetFileName = request.getParameter("fileName");
        if (StringUtils.isNotBlank(id)){
            if (id.contains(SPLIT_MARK)){
               String [] array = id.split(SPLIT_MARK);
               List<UploadFile> files =  fileDao.getFilesByIds(array, tableName);
               if (files != null && files.size() > 0){
            	   UploadFile uploadFile = files.get(0);
            	   String fileName = uploadFile.getName();
            	   if (StringUtils.isNotBlank(targetFileName) && !targetFileName.equals("null")){
                       try {
                           fileName = URLDecoder.decode(targetFileName,"UTF-8") +  fileName.substring(fileName.lastIndexOf(".")) ;
                       } catch (UnsupportedEncodingException e) {
                           e.printStackTrace();
                       }
                   }
                   downloadFile(request, response, uploadFile.getPath(), fileName);
               }
            } else {
                UploadFile file = fileDao.getFileById(id, tableName);
                if (file == null){
                    List<UploadFile> fileByBusinessId = fileDao.getFileByBusinessId(id, null, tableName);
                    if( fileByBusinessId !=null && fileByBusinessId.size() !=0){
                        file=fileByBusinessId.get(0);
                    }
                }
                if (file != null){
                    
                    String fileName = file.getName();
                    if (StringUtils.isNotBlank(targetFileName) && !targetFileName.equals("null")){
                        try {
                            fileName = URLDecoder.decode(targetFileName,"UTF-8") +  fileName.substring(fileName.lastIndexOf(".")) ;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    downloadFile(request, response, file.getPath(), fileName);
                }
            }
         }
		
		
	}
    
    
	public static void main(String[] args) {
		List<String> strs = new ArrayList<String>();
        int index = 0;
        List<String> list = new ArrayList<String>();
        list.add("qqq");
        list.add("ccc");
        list.add("qqq");
        list.add("111");
        for (int i=0;i<list.size(); i++){
        	String str = list.get(i);
        	if(strs.contains(str)){
        		str = str + "-" + (++index);
        		list.set(i, str);
        	}
        	strs.add(str);
        }
        System.out.println(Arrays.toString(list.toArray()));
	}
	
	public List<String> downloadMap(HttpServletRequest request){
		String businessId = request.getParameter("id");
		String typeId = request.getParameter("typeId");
		Integer systemKey = Integer.parseInt(request.getParameter("key"));
		String tableName = Constant.fileSystem.get(systemKey);
		List<UploadFile> findBybusinessId = fileDao.getFileByBusinessId( businessId, typeId,tableName);
		List<String> list = new ArrayList<>();
		for (UploadFile uploadFile : findBybusinessId) {
			String id =uploadFile.getId();
			list.add(id);
		}
		return list;
	}
}
