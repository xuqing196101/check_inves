package synchro.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import bss.util.FileUtil;
import common.model.UploadFile;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>操作附件类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class OperAttachment {
	private static Logger log = Logger.getLogger(OperAttachment.class);
    /**
     * 
     *〈简述〉写文件
     *〈详细描述〉
     * @author myc
     * @param path 目标目录
     * @param list 数据
     */
    public static final void writeFile(String path,List<UploadFile> list){
        if (list != null && list.size() > 0){
            for (UploadFile uploadFile : list){
                if (StringUtils.isNotBlank(uploadFile.getPath())){
                    copyFile(uploadFile.getPath(), path);
                }
            }
        }
    }
    
    /**
     * 
     *〈简述〉移动文件夹
     *〈详细描述〉
     * @author myc
     * @param file 待解析文件
     */
    public static final void moveFolder(final File file){
        
        final String path = FileUtils.createBaseFilePath();
        final File destDir = new File(path);
        if (file.isDirectory()){
           try {
            org.apache.commons.io.FileUtils.copyDirectoryToDirectory(file, destDir);
            org.apache.commons.io.FileUtils.deleteDirectory(file);  
            } catch (IOException e) {
            	log.info(e.getMessage());
                e.printStackTrace();
            }
       }
        
    }
    
    /**
     * 
     *〈简述〉移动文件夹
     *〈详细描述〉
     * @author myc
     * @param file 待解析文件
     */
    public static final void moveToPathFolder(final File file,String filePath){
        final String path = FileUtils.createFilePath(filePath);
        final File destDir = new File(path);
        if (file.isDirectory()){
           try {
            org.apache.commons.io.FileUtils.copyDirectoryToDirectory(file, destDir);
            org.apache.commons.io.FileUtils.deleteDirectory(file);  
            } catch (IOException e) {
                e.printStackTrace();
            }
       }
        
    }
    
    /**
     * 
     *〈简述〉拷贝文件
     *〈详细描述〉
     * @author myc
     * @param srcPath 源文件
     * @param destPath 目标文件
     */
    private static final void copyFile(String srcPath, String destPath){
        File srcFile = new File(srcPath);
        try {
            if (srcFile.exists()){
                
                String path = getSubStr(srcFile.getPath(), 2);
                if (!path.contains("/")){
                    return ;
                }
                path = path.substring(0,path.lastIndexOf("/"));
                if (!StringUtils.isNotBlank(path)){
                    return;
                }
                String targetPath = destPath + File.separator + path;
                String targetFilePath = FileUtils.createDir(targetPath);
                
                File destFile = new File(targetFilePath,srcFile.getName());
                FileInputStream fileInputStream=new FileInputStream(srcFile); 
                FileOutputStream fileOutputStream=new FileOutputStream(destFile); 
                byte[] by = new byte[1024];  
                int len;  
                while((len = fileInputStream.read(by))!=-1){  
                    fileOutputStream.write(by, 0, len);  
                }  
                fileInputStream.close();  
                fileOutputStream.close();  
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     *〈简述〉截取倒数的斜杠
     *〈详细描述〉
     * @author myc
     * @param str 字符创
     * @param num 数字
     * @return
     */
    private static String getSubStr(String str, int num) {
        String result = "";
        str = str.replaceAll("\\\\","/");
        int i = 0;
        while(i < num) {
         int lastFirst = str.lastIndexOf('/');
         result = str.substring(lastFirst) + result;
         str = str.substring(0, lastFirst);
         i++;
        }
        return result.substring(1);
     } 
}
