package common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 上传文件的工具类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class UploadUtil {
    
    
    /**
     * 
     *〈简述〉创建一个文件目录
     *〈详细描述〉
     * @author myc
     * @param dirPath 目录路径
     * @return 返回目录路径
     */
    public static File createDir(String dirPath){
        File file = new File(dirPath);
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }
    
    /**
     * 
     *〈简述〉创建文件
     *〈详细描述〉
     * @author myc
     * @param parentPath 文件目录
     * @param fileName 文件名称
     * @return 文件
     */
    public static File getFile(String parentPath, String fileName){
        File file = new File(parentPath, fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    /**
     * 
     *〈简述〉移动文件
     *〈详细描述〉
     * @author myc
     * @param srcFile 原文件
     * @param tagetFile 目标文件
     * @return 成功返回true,失败返回false
     */
    public static boolean moveFile(File srcFile, File tagetFile){
        return srcFile.renameTo(tagetFile);
    }
    /**
     * 
     *〈简述〉创建目录路径
     *〈详细描述〉
     * @author myc
     * @param path 目录
     * @return 目录路径
     */
    public static String createFileDir(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return path;
    }
    
    /**
     * 
     *〈简述〉获取时间目录
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static String getDataFilePath(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }
    
    /**
     * 
     *〈简述〉将字节转换为kb
     *〈详细描述〉
     * @author myc
     * @param bytes 字节
     * @return 转换后的kb
     */
    public static String bytesTokb(long bytes) {  
        BigDecimal filesize = new BigDecimal(bytes);  
        BigDecimal kilobyte = new BigDecimal(1024);  
        return  filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue() + "";
    }
    
    /**
     * 
     *〈简述〉写文件
     *〈详细描述〉
     * @author myc
     * @param in {@link InputStream}
     * @param out {@link OutputStream}
     * @throws IOException 
     */
    public static void writeFile(InputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[1024 * 1024 * 4];   
        int i = -1;   
        while ((i = in.read(buffer)) != -1) {   
           out.write(buffer, 0, i);  
        }   
        in.close();   
   }
}
