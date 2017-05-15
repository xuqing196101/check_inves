package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.service.DownloadService;

@Controller
@RequestMapping("/file")
public class DownloadController {
    
    /** 文件下载service */
    @Autowired
    private DownloadService downloadService;
    
    /**
     * 
     *〈简述〉文件下载
     *〈详细描述〉
     * @author myc
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    @RequestMapping("/download")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response){
        downloadService.download(request, response);
    }
    @RequestMapping("/downloadOneFile")
    @ResponseBody
    public void downloadOneFile(HttpServletRequest request, HttpServletResponse response){
        downloadService.downloadOneFile(request, response);
    }
}
