package synchro.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.LineIterator;

import com.alibaba.fastjson.JSON;

import ses.util.PropUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>文件工具类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class FileUtils {
    
    /** 文件同步基础目录 **/
    private final static String BASE_PATH = PropUtil.getProperty("file.sync.base");
    
    /** 文件同步导出目录 **/
    private final static String BACKUP_PATH = PropUtil.getProperty("file.sync.backup");
    
    /** 文件同步导入目录 **/
    private final static String IMPORT_PATH = PropUtil.getProperty("file.sync.import");
    
    /** 文件同步完成目录 **/
    private final static String FINISH_PATH = PropUtil.getProperty("file.sync.finish");
    
    /** 新注册供应商文件名称 **/
    public final static String C_SUPPLIER_FILENAME = "_c_supplier.dat"; 
    
    /** 修改供应商文件名称 **/
    public final static String M_SUPPLIER_FILENAME = "_m_supplier.dat";
    
    /** 新注册专家文件名称 **/
    public final static String C_EXPERT_FILENAME = "_c_expert.dat";
    
    /** 修改专家文件名称 **/
    public final static String M_EXPERT_FILENAME = "_m_expert.dat";
    
    /** 信息文件名称 **/
    public final static String C_INFOS_FILENAME = "_c_infos.dat";
    
    /** 附件文件名称 **/
    public final static String C_ATTACH_FILENAME = "_c_attach.dat";
    
    /**
     * 
     *〈简述〉创建根目录
     *〈详细描述〉
     * @author myc
     */
    public static void createBasePath(){
        File file = new File(BASE_PATH);
        if (!file.exists()){
            file.mkdirs();
        }
    }
    
    /**
     * 
     *〈简述〉创建文件,返回路径
     *〈详细描述〉
     * @author myc
     * @param path 路径
     * @return 
     */
    public static final String createFilePath(final String path){
        createBasePath();
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return file.getPath();
    }
    
    /**
     * 
     *〈简述〉获取到处的文件路径
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final  String getBackUpPath(){
       return createFilePath(BASE_PATH + BACKUP_PATH);
    }
    
    /**
     * 
     *〈简述〉获取待导入的文件路径
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final String  getImportPath(){
       return createFilePath(BASE_PATH + IMPORT_PATH);
    }
    
    /**
     * 
     *〈简述〉 获取待导入的文件
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getImportFile(){
        File file = new File(getImportPath());
        return file;
    }
    
    /**
     * 
     *〈简述〉获取完成的文件路径
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final String  getFinishPath(){
        return createFilePath(BASE_PATH + FINISH_PATH);
     }
    
    /**
     * 
     *〈简述〉获取新注册供应商导出文件
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getNewSupperBackUpFile(){
        String fileName = System.currentTimeMillis() + C_SUPPLIER_FILENAME;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }
    
    /**
     * 
     *〈简述〉获取新注册供应商导出文件
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getModifySupplierBackUpFile(){
        String fileName = System.currentTimeMillis() + M_SUPPLIER_FILENAME;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }
    
    /**
     * 
     *〈简述〉获取新注册供应商导出文件
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getInfoBackUpFile(){
        String fileName = System.currentTimeMillis() + C_INFOS_FILENAME;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }
    
    /**
     * 
     *〈简述〉获取附件
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getInfoAttachmentFile(){
        String fileName = System.currentTimeMillis() + C_ATTACH_FILENAME;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }
    
    
    /**
     * 
     *〈简述〉将一个字符串写到文件
     *〈详细描述〉
     * @author myc
     * @param str 字符串
     */
    public static final void writeFile(final File file , final String str){
        try {
            FileWriter  fw = new FileWriter(file);
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     *〈简述〉读取文件
     *〈详细描述〉
     * @author myc
     * @param file 待读取文件
     * @return 文件
     */
    public static final String readFile(final File file ){
        LineIterator it  = null;
        final StringBuffer sb = new StringBuffer();
        try {
            it = org.apache.commons.io.FileUtils.lineIterator(file,"UTF-8");
            while (it.hasNext()) {
                sb.append(it.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
          if (it != null){
              LineIterator.closeQuietly(it);
          }  
          moveFile(file);
        }
        return sb.toString();
    }
    
    /**
     * 
     *〈简述〉获取文件类型
     *〈详细描述〉
     * @author myc
     * @param file 文件
     * @param cls 类型
     * @return
     */
    public static <T> List<T> getBeans(final File file, Class<T> cls) {
        String jsonString =  readFile(file);
        List<T> list = new ArrayList<T>();  
        try {  
          list = JSON.parseArray(jsonString, cls);  
        } catch (Exception e) {  
            
        }  
        return list;  
    }  
    
    /**
     * 
     *〈简述〉移动文件
     *〈详细描述〉
     * @author myc
     * @param file 原文件
     */
    public static void moveFile(final File file){
        file.renameTo(new File(getFinishPath(),file.getName()));
    }
    
    /**
     *〈简述〉获取新注册专家导出文件
     *〈详细描述〉
     * @author WangHuijie
     * @return
     */
    public static final File getNewExpertBackUpFile(){
        String fileName = System.currentTimeMillis() + C_EXPERT_FILENAME;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }
    
    /**
     * 
     *〈简述〉获取修改专家导出文件
     *〈详细描述〉
     * @author WangHuijie
     * @return
     */
    public static final File getModifyExpertBackUpFile(){
        String fileName = System.currentTimeMillis() + M_EXPERT_FILENAME;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }
    
}
