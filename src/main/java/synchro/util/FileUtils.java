package synchro.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    
    /** 供应商文件名称 **/
    private final static String SUPPLIER_FILENAME = "_supplier.dat"; 
    
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
     *〈简述〉获取导出文件
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getBackUpFile(){
        String fileName = System.currentTimeMillis() + SUPPLIER_FILENAME;
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
    public static final void writeFile(final String str){
        try {
            File file = getBackUpFile();
            FileWriter  fw = new FileWriter(file);
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
