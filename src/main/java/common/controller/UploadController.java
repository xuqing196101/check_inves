package common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.DelegateFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.model.iacs.Product;
import synchro.util.FileEncryption;
import common.annotation.CurrentUser;
import common.bean.ResBean;
import common.model.UploadFile;
import common.service.UploadService;
import ses.model.bms.User;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 文件上传controller
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/file")
public class UploadController {
    
    /** 注入service */
    @Autowired
    private UploadService uploadService;
    
    /**
     * 
     *〈简述〉分片上传
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     */
    @RequestMapping("/upload")
    @ResponseBody
    public void upload(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String result = uploadService.upload(request);
            PrintWriter out = response.getWriter();
            if (StringUtils.isNotBlank(result)){
            	out.write(FileEncryption.setEncryption(result));
            	/*out.write(result);*/
            	out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     *〈简述〉将文件转移到正式目录,并且记录路径
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    @RequestMapping("/finished")
    @ResponseBody
    public void finishUpload(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String msg = uploadService.saveFile(request);
            if (StringUtils.isNotBlank(msg)){
                response.getWriter().write(msg);
            } 
            response.getWriter().flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     *〈简述〉删除文件
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return {@link java.lang.String}
     */
    @RequestMapping("/deleteFile")
    @ResponseBody
    public String deleteFile(HttpServletRequest request){
        String msg = uploadService.updateFile(request);
        return msg;
    }
    /**
     * 
     *〈简述〉删除文件
     *〈详细描述〉
     * @author Zhou Wei
     * @param request {@link HttpServletRequest}
     * @return {@link java.lang.String}
     */
    @RequestMapping("/delfile")
    public String delfile(HttpServletRequest request){
    	 String msg = uploadService.updateFile(request);
         String id = request.getParameter("pId");
         return "redirect:/open_bidding//bidFile.html?id="+id+"&process=1"+"&delOk="+msg;
    }
    
    
    /**
     * 
     *〈简述〉上传完显示附件
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return {@link List<UploadFile>}
     */
    @RequestMapping("/displayFile")
    @ResponseBody
    public List<UploadFile> disPlayFiles(HttpServletRequest request){
        
        List<UploadFile> list = uploadService.getFiles(request);
        for(UploadFile upload:list){
        	if(upload.getPath()!=null&&!"".equals(upload.getPath())){
        		upload.setPath(upload.getPath().substring(upload.getPath().indexOf("."),upload.getPath().length()));
        	}
        	
        }
        return list;
    }
    
    /**
     * 
     *〈简述〉
     * 图片显示
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    @RequestMapping(value="/viewFile")
    public void viewPicture(HttpServletRequest request ,HttpServletResponse response){
        
        uploadService.viewPicture(request, response);
    }
    
    /**
     * 
     *〈简述〉判断文件是否存在
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/fileExist", produces="application/json;charset=UTF-8")
    public ResBean fileExist(HttpServletRequest request){
        
        return uploadService.fileExist(request);
    }
    
    /**
     *〈简述〉
     * 异步判断有没有超过最大数量上限
     *〈详细描述〉
     * @author WangHuijie
     * @param businessId
     * @param typeId
     * @param sysKey
     * @param maxCount
     * @return
     */
    @ResponseBody
    @RequestMapping("/isOverMaxCount")
    public String isOverMaxCount(String businessId, String typeId, String sysKey, String maxcount, Integer currentCount){
        if (maxcount != null && !maxcount.equals("null")) {
            List<UploadFile> filesOther = uploadService.getFilesOther(businessId, typeId, sysKey);
            if (filesOther.size() >= Integer.parseInt(maxcount.toString())) {
                return "error";
            } else if (filesOther.size() + currentCount >= Integer.parseInt(maxcount.toString())) {
                return "error";
            }
        }
        return "success";
    }
}
