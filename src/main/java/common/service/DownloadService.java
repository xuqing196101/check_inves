package common.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public interface DownloadService {

    /**
     * 
     *〈简述〉文件下载
     *〈详细描述〉
     * @author myc
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    public void download(HttpServletRequest request, HttpServletResponse response);

    /**
     *〈简述〉文件下载
     *〈详细描述〉直接传参下载
     * @author Ye MaoLin
     * @param request
     * @param response
     * @param fileId 附件id
     * @param sysKey 系统key
     */
    public void downloadOther(HttpServletRequest request, HttpServletResponse response, String fileId, String sysKey);
    
    
    
    /**
     * 
     *〈简述〉下载文件
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @param response{@link HttpServletResponse}
     * @param filePath 文件路径
     */
    public void downLoadFile(HttpServletRequest request,HttpServletResponse response, String filePath);
    /**
     * 重写jsp页面下载，如果上传多个只下载最近上传的一个，根据bussId查找所有的上传
     * @author LiWanlin
     * @param request
     * @param response
     */
    public void downloadOneFile(HttpServletRequest request, HttpServletResponse response);
}
