package common.utils;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.math.NumberUtils;

import ses.util.PropUtil;
import common.bean.MultipartFileBean;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 文件上传工具类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class MultipartFileUploadUtil {
    
    /**
     * 
     *〈简述〉上传文件参数包装成bean对象
     *〈详细描述〉
     * @author myc
     * @param request HttpServletRequest
     * @return {@link} MultipartFileBean
     * @throws Exception
     */
    public static MultipartFileBean parse(HttpServletRequest request) throws Exception{
        
        MultipartFileBean bean = new MultipartFileBean();
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        bean.setMultipart(isMultipart);
        
        if (isMultipart){
            DiskFileItemFactory  factory = new DiskFileItemFactory();
            String fileRepository = PropUtil.getProperty("file.base.path") + File.separator + PropUtil.getProperty("file.temp.path");
            File tmpFile = new File(fileRepository);
            if (!tmpFile.exists()){
                tmpFile.mkdirs();
            }
            factory.setRepository(tmpFile);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            @SuppressWarnings("unchecked")
            List<FileItem> list = upload.parseRequest(request);
            
            for (FileItem item : list){
                switch(item.getFieldName()) {
                    case "id" :
                        bean.setId(item.getString());
                        break;
                    case "name" :
                        bean.setFileName(new String(item.getString().getBytes(
                                "ISO-8859-1"), "UTF-8"));
                        break;
                    case "chunks" :
                        bean.setChunks(NumberUtils.toInt(item.getString()));
                        break;
                    case "chunk" :
                        bean.setChunk(NumberUtils.toInt(item.getString()));
                        break;
                    case "file" :
                        bean.setFileItem(item);
                        bean.setSize(item.getSize());
                        break;
                    case "ntko" :
                        bean.setFileName(new String(item.getString().getBytes(
                                "ISO-8859-1"), "UTF-8"));
                        bean.setFileItem(item);
                        bean.setSize(item.getSize());
                        break;
                    default :
                        bean.getParam().put(item.getFieldName(), item.getString());
                }   
                
            }
            
        }
        
        return bean;
    }
}
