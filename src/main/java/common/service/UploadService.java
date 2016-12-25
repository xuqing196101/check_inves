package common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.bean.ResBean;
import common.model.UploadFile;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface UploadService {
    
    /***
     * 
     *〈简述〉 保存附件
     *〈详细描述〉
     * @author myc
     * @param businessId 业务id
     * @param fileTypeId 文件类型id
     * @param systemKey 对应系统key
     * @param request {@link HttpServletRequest}
     * @return 
     */
    public String saveFile(HttpServletRequest request);
    /**
     * 
     *〈简述〉文件上传
     *〈详细描述〉
     * @author myc
     * @param request  {@link HttpServletRequest}
     * @return 成功返回文件路径,失败返回空字符串
     */
    public String upload(HttpServletRequest request);
    /**
     * 
     *〈简述〉文件删除
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return 成功返回ok,失败返回error
     */
    public String updateFile(HttpServletRequest request);
    
    /**
     * 
     *〈简述〉获取上传文件
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return 成功返回文件对象List,否则为空list
     */
    public List<UploadFile> getFiles(HttpServletRequest request);
    
    /***
     * 
     *〈简述〉返回
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    public void viewPicture(HttpServletRequest request ,HttpServletResponse response);

    /**
     * 
     *〈简述〉保存文件
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @param businessId 业务Id
     * @param typeId 业务类型Id
     * @param sysKey 系统key
     * @return
     */
    public String saveOnlineFile(HttpServletRequest request ,String businessId, String typeId, String sysKey);

    
    /**
     *〈简述〉 获取文件
     *〈详细描述〉 直接传参方式获取文件
     * @author Ye MaoLin
     * @param businessId 业务id
     * @param typeId 附件类型id
     * @param sysKey 系统key
     * @return 成功返回文件对象List,否则为空list
     */
    public List<UploadFile> getFilesOther(String businessId, String typeId, String sysKey);
    
    /**
     *〈简述〉文件删除
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id 附件id
     * @param sysKey 系统key
     */
    public String updateFileOther(String id, String sysKey);
    
    /**
     * 
     *〈简述〉根据主键查询
     *〈详细描述〉
     * @author ZhaoBo
     * @param id 主键
     * @param key 对应的key
     * @return UploadFile
     */
    UploadFile findById(String id,Integer key);
    
    /**
     * 
     *〈简述〉根据主键查询
     *〈详细描述〉
     * @author ZhaoBo
     * @param id 主键
     * @param key 对应的key
     * @return UploadFile
     */
    UploadFile findBybusinessId(String businessId, Integer sysKey);
    
    
    /**
     * 
     *〈简述〉 ueEdit 文件上传
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param businessId
     * @param sysKey
     * @param context
     * @return
     */
    public String uploadFileByContext(String businessId, String sysKey, String context);
    
    /**
     * 
     *〈简述〉判断文件是否存在
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return {@link ResBean}
     */
    public ResBean fileExist(HttpServletRequest request);

}
