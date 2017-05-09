package synchro.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

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
    public final static String BASE_PATH = PropUtil.getProperty("file.sync.base");
    
    /** 文件同步导出目录 **/
    private final static String BACKUP_PATH = PropUtil.getProperty("file.sync.export");
    
    /** 文件同步导入目录 **/
    public final static String IMPORT_PATH = PropUtil.getProperty("file.sync.import");
    
    /** 文件同步完成目录 **/
    private final static String FINISH_PATH = PropUtil.getProperty("file.sync.finish");
    
    /** 供应商附件文件路径 **/
    public final static String SUPPLIER_ATTFILE_PATH = PropUtil.getProperty("file.supplier.system.path");
    
    /** 招标附件文件路径 **/
    public final static String TENDER_ATTFILE_PATH = PropUtil.getProperty("file.tender.system.path");
    
    /** 专家附件文件路径 **/
    private final static String EXPERT_ATTFILE_PATH = PropUtil.getProperty("file.expert.system.path");
    
    /** 论坛附件文件路径 **/
    private final static String FORUM_ATTFILE_PATH = PropUtil.getProperty("file.forum.system.path");
    
    /** 正式附件路径 **/
    public final static String BASE_ATTCH_PATH = PropUtil.getProperty("file.base.path");
    
    /**竞价 信息 文件路径  5 **/
    public final static String OB_PROJECT_PATH=PropUtil.getProperty("file.bidding.system.path");
   
    /**竞价 信息 文件路径  10 **/
    public final static String OB_PROJECT_FILE_PATH=PropUtil.getProperty("file.bidding_project_file.system.path");
    /**产品 库管理 内网 文件路径**/
    public final static String SYNCH_INNER_PRODUCT_LIBRARY_PATH=PropUtil.getProperty("file.inner_product_library.system.path");
    /**产品 库管理 外网 文件路径**/
    public final static String SYNCH_OUTER_PRODUCT_LIBRARY_PATH=PropUtil.getProperty("file.outer_product_library.system.path");
    /**产品 库管理 外网  附件文件路径**/
    public final static String SYNCH_OUTER_FILE_PRODUCT_LIBRARY_PATH=PropUtil.getProperty("file.outer_file_product_library.system.path");
    /**竞价  #竞价定型产品目录 路径 6**/
    public final static String OB_PROJECT_PRODUCT_PATH=PropUtil.getProperty("file.bidding_product.system.path");
    /**竞价   #竞价特殊日期目录路径 7**/
    public final static String OB_PROJECT_SPECIAL_DATE_PATH=PropUtil.getProperty("file.bidding_special_date.system.path");
    /**竞价 #竞价供应商管理目录 路径 8**/
    public final static String OB_PROJECT_SUPPLIER_PATH=PropUtil.getProperty("file.bidding_supplier.system.path");
    /**竞价  #竞价结果目录 路径 9**/
    public final static String OB_PROJECT_RESULT_PATH=PropUtil.getProperty("file.bidding_result.system.path");
    
    /** 新注册供应商文件名称 **/
    public final static String C_SUPPLIER_FILENAME = "_c_supplier.dat"; 
    
    
    /** 外网供应商退回修改 **/
    public final static String C_SUPPLIER_BACK_FILENAME = "_c_back_supplier.dat"; 
    
    
    /** 所有审核不通过的 **/
    public final static String C_SUPPLIER_ALL_FILE="_c_supplier_not.dat";
    
    /** 临时供应商的导入导出 **/
    public final static String C_TMEP_SUPPLIER_FILE="_c_temp_supplier.dat";
    
    /** 修改供应商文件名称 **/
    public final static String M_SUPPLIER_FILENAME = "_m_supplier.dat";
    
    /** 新注册专家文件名称 **/
    public final static String C_EXPERT_FILENAME = "_c_expert.dat";
    
    /** 修改专家文件名称 **/
    public final static String M_EXPERT_FILENAME = "_m_expert.dat";

    /** 所有专家审核不通过的 **/
    public final static String C_EXPERT_ALL_NOT="_c_expert_not.dat";
    
    /** 信息文件名称 **/
    public final static String C_INFOS_FILENAME = "_c_infos.dat";
    
    /** 附件文件名称 **/
    public final static String C_ATTACH_FILENAME = "_c_attach.dat";
    /**竞价 定型产品数据名称 **/
    public final static String C_OB_PRODUCT_FILENAME="_c_ob_product.dat";
    /**竞价定型产品更新数据名称**/
    public final static String M_OB_PRODUCT_FILENAME="_m_ob_product.dat";
    /**竞价 特殊日期 创建数据名称 **/
    public final static String C_OB_SPECIAL_DATE_FILENAME="_c_ob_special_date.dat";
    /**竞价 特殊日期修改数据名称**/
    public final static String M_OB_SPECIAL_DATE_FILENAME="_m_ob_special_date.dat";
    /**竞价 供应商数据创建名称 **/
    public final static String C_OB_SUPPLIER_FILENAME="_c_ob_supplier.dat";
    /**竞价 供应商数据修改名称**/
    public final static String M_OB_SUPPLIER_FILENAME="_m_ob_supplier.dat";
    /**竞价 信息文件名称**/
    public final static String C_OB_PROJECT_FILE_FILENAME="_c_ob_project_file.dat";
    /**竞价信息 非暂存 数据名称**/
    public final static String C_OB_PROJECT_STATUS_FILENAME="_c_ob_project.dat";
    /**竞价结果文件名称**/
    public final static String C_OB_PROJECT_RESULT_FILENAME="_c_ob_project_result.dat";
    /***产品库管理 导出 名称 内网**/
    public final static String C_SYNCH_INNER_PRODUCT_LIBRARY="_c_synch_inner_productLibrary.dat";
    /***产品库管理 导出 数据名称 外网**/
    public final static String C_SYNCH_OUTER_PRODUCT_LIBRARY="_c_synch_outer_productLibrary.dat";
    /***产品库管理 导出 附件数据名称 外网**/
    public final static String C_SYNCH_OUTER_FILE_PRODUCT_LIBRARY="_c_synch_outer_file_productLibrary.dat";
   
    /***产品目录管理 导出创建数据名称***/
    public final static String C_CATEGORY_FILENAME="_c_category.dat";
    /**产品目录管理 导出路径***/
    public final static String T_SES_BMS_CATEGORY_PATH=PropUtil.getProperty("file.t_ses_bms_category_path.system.path");
    /***产品目录管理 导出更新数据名称***/
    public final static String U_CATEGORY_FILENAME="_u_category.dat";
    /**产品目录管理 导出附件数据名称**/
    public final static String C_FILE_CATEGORY_FILENAME="_c_file_category.dat";
    /**产品目录管理 导出路径***/
    public final static String FILE_T_SES_BMS_CATEGORY_PATH=PropUtil.getProperty("file.file_t_ses_bms_category_path.system.path");
    
    
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
     *〈简述〉获取文件路径
     *〈详细描述〉
     * @author myc
     * @param dir
     * @return
     */
    public static final String createDir(String dir){
        if (StringUtils.isNotBlank(dir)){
            File file = new File(dir);
            if (!file.exists()){
                file.mkdirs();
            }
            return file.getPath();
        }
        return "";
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
    public static final File getBackSupplierFile(){
        String fileName = System.currentTimeMillis() + C_SUPPLIER_BACK_FILENAME;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }
    
    
    /**
     * 
     *〈简述〉获取所有审核不通过的
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getSupperAuidtNotFile(){
        String fileName = System.currentTimeMillis() + C_SUPPLIER_ALL_FILE;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }

    /**
     *
     *〈简述〉获取所有专家审核不通过的
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getExpertAuidtNot(){
        String fileName = System.currentTimeMillis() + C_EXPERT_ALL_NOT;
        String path = getBackUpPath();
        final File file = new File(path,fileName);
        return file;
    }
    
    
    /**
     * 
     *〈简述〉获取所有临时供应商
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final File getTempSupperFile(){
        String fileName = System.currentTimeMillis() + C_TMEP_SUPPLIER_FILE;
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
     * 根据传参导出相关的数据文件
     * @author YangHongLiang
     * @param fileNameType 导出文件名称
     * @param key 对应封装集合
     * @return
     */
    public static final File getExporttFile(String fileNameType,int key){
        String fileName = System.currentTimeMillis() + fileNameType;
        String path = createFilePath(BASE_PATH + BACKUP_PATH+getSynchAttachFile(key));
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
     *〈简述〉获取基础的文件路径
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static final String createBaseFilePath(){
        final File file = new File(BASE_ATTCH_PATH);
        if (!file.exists()){
            file.mkdirs();
        }
        return file.getPath();
    }
    
    /**
     * 
     *〈简述〉读取文件
     *〈详细描述〉
     * @author myc
     * @param file 待读取文件
     * @return 文件
     */
    public static final String readFileMoveFile(final File file ,String toFilePath){
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
          moveToFile(file,toFilePath);
        }
        return sb.toString();
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
     * 供应商导入文件(不移动文件)
    * @Title: supplierFile
    * @Description: TODO 
    * author: Li Xiaoxiao 
    * @param @param file
    * @param @return     
    * @return String     
    * @throws
     */
    public static final String supplierFile(final File file ){
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
//          moveFile(file);
        }
        return sb.toString();
    }

    /**
     * 专家导入文件(不移动文件)
    * @Title: supplierFile
    * @param @param file
    * @param @return
    * @return String
    * @throws
     */
    public static final String expertFile(final File file ){
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
//          moveFile(file);
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
    public static <T> List<T> getBeansMoveFile(final File file, Class<T> cls,String toFilePath) {
        String jsonString =  readFileMoveFile(file,toFilePath);
        List<T> list = new ArrayList<T>();  
        try {  
          list = JSON.parseArray(jsonString, cls);  
        } catch (Exception e) {  
            
        }  
        return list;  
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
    * @Title: getSupplier
    * @Description: 供应商导入文件
    * author: Li Xiaoxiao 
    * @param @param file
    * @param @param cls
    * @param @return     
    * @return List<T>     
    * @throws
     */
    public static <T> List<T> getSupplier(final File file, Class<T> cls) {
        String jsonString =  supplierFile(file);
        List<T> list = new ArrayList<T>();  
        try {  
          list = JSON.parseArray(jsonString, cls);  
        } catch (Exception e) {  
            
        }  
        return list;  
    }

    /**
     *
    * @Title: getSupplier
    * @Description: 专家导入文件
    * @param @param file
    * @param @param cls
    * @param @return
    * @return List<T>
    * @throws
     */
    public static <T> List<T> getExpert(final File file, Class<T> cls) {
        String jsonString =  expertFile(file);
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
     * @param toFile 目标文件
     */
    public static void moveToFile(final File file,String toFilePath){
        file.renameTo(new File(createFilePath(toFilePath),file.getName()));
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
    
    /**
     * 
     *〈简述〉根据系统key获取对应的附件目录
     *〈详细描述〉
     * @author myc
     * @param key 系统key
     * @return
     */
    public static final String attachExportPath(Integer key){
        String path = getSynchAttachFile(key);
        if (StringUtils.isNotBlank(path)){
            String finalPath = getBackUpPath() + path;
            return createFilePath(finalPath);
        }
        return "";
    }
    
    /**
     * 
     *〈简述〉根据系统key获取对应的附件目录
     *〈详细描述〉
     * @author myc
     * @param key 系统key
     * @return
     */
    public static final  String getSynchAttachFile(Integer key){
        String filePath = "";
        switch (key){
          case 1 :  filePath = SUPPLIER_ATTFILE_PATH; break;
          case 2 :  filePath = TENDER_ATTFILE_PATH; break;
          case 3 :  filePath = EXPERT_ATTFILE_PATH; break;
          case 4 :  filePath = FORUM_ATTFILE_PATH; break;
          case 5 :  filePath = OB_PROJECT_PATH; break;
          case 6 :  filePath =OB_PROJECT_PRODUCT_PATH;break;
          case 7 :  filePath =OB_PROJECT_SPECIAL_DATE_PATH;break;
          case 8 :  filePath =OB_PROJECT_SUPPLIER_PATH;break;
          case 9 :  filePath =OB_PROJECT_RESULT_PATH;break;
          case 10:  filePath=OB_PROJECT_FILE_PATH;break;
          case 11:  filePath=SYNCH_INNER_PRODUCT_LIBRARY_PATH;break;
          case 12:  filePath=SYNCH_OUTER_PRODUCT_LIBRARY_PATH;break;
          case 13:  filePath=SYNCH_OUTER_FILE_PRODUCT_LIBRARY_PATH;break;
          case 14: filePath=T_SES_BMS_CATEGORY_PATH;break;
          case 15: filePath=FILE_T_SES_BMS_CATEGORY_PATH;break;
        }
        return filePath;
    }
    
}
