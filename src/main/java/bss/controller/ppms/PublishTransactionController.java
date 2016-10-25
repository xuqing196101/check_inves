package bss.controller.ppms;
import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.model.bms.User;
import ses.model.oms.util.AjaxJsonData;
import ses.util.FtpUtil;

import bss.model.ppms.Project;
import bss.model.ppms.ProjectAttachments;
import bss.service.ppms.ProjectAttachmentsService;
import bss.service.ppms.ProjectService;

/**
 * @Title: PublishTransactionController
 * @Description: 发布成交公示控制层
 * @author: Song Biaowei
 * @date: 2016-10-24下午7:31:05
 */
@Controller
@Scope("prototype")
@RequestMapping("/pub_tran")
public class PublishTransactionController {
    
    /**
     * @Fields projectService : 引用项目业务实现接口
     */
    @Autowired
    private ProjectService projectService;
    
    /**
     * @Fields articelService :  引用文章业务实现接口
     */
    @Autowired
    private ArticleService articelService;
    
    /**
     * @Fields articelTypeService : 引用文章类型业务实现接口
     */
    @Autowired
    private ArticleTypeService articelTypeService;
    
    /**
     * @Fields attachmentsService : 引用文章附件业务实现接口
     */
    @Autowired
    private ArticleAttachmentsService attachmentsService;
    
    /**
     * @Fields projectAttachmentsService : 引用项目附件业务实现接口
     */
    @Autowired
    private ProjectAttachmentsService projectAttachmentsService;
    
    /**
     * @Fields jsonData : ajax返回数据封装类
     */
    private AjaxJsonData jsonData = new AjaxJsonData();

    @RequestMapping("/bidFile")
    public String bidFile(String id, Model model){
    	Project project = projectService.selectById(id);
    	model.addAttribute("project", project);
    	return "bss/ppms/competitive_negotiation/add";
    }
    
    /**
     * @Title: bidNotice
     * @author Song Biaowei
     * @date 2016-10-24 下午7:31:31  
     * @Description: 进入成交公示页面
     * @param @param projectId
     * @param @param model
     * @param @return      
     * @return String
     */
    @RequestMapping("/bidNotice")
    public String bidNotice(String projectId, Model model){
    	Article article = new Article();
    	article.setProjectId(projectId);
    	ArticleType at = articelTypeService.selectTypeByPrimaryKey("14");
    	article.setArticleType(at);
    	List<Article> articles = articelService.selectArticleByProjectId(article);
    	if (articles != null && articles.size() > 0){
    	    if (articles.get(0).getPublishedAt() != null && articles.get(0).getPublishedName() != null && !"".equals(articles.get(0).getPublishedName())){
    	        model.addAttribute("article", articles.get(0));
    	        model.addAttribute("projectId", projectId);
    	        return "bss/ppms/competitive_negotiation/bid_notice/view";
    	    } else {
    	        model.addAttribute("content", articles.get(0).getContent());
    	        model.addAttribute("name", articles.get(0).getName());
    	        model.addAttribute("articleId", articles.get(0).getId());
    	        model.addAttribute("range", articles.get(0).getRange());
                model.addAttribute("projectId", projectId);
                return "bss/ppms/competitive_negotiation/bid_notice/add";
    	    }
    	} else {
    	    model.addAttribute("projectId", projectId);
    	    return "bss/ppms/competitive_negotiation/bid_notice/add";
    	}
    }
    
   /**
    * @Title: saveBidNotice
    * @author Song Biaowei
    * @date 2016-10-24 下午7:31:51  
    * @Description: 保存成交公示
    * @param @param request
    * @param @param article
    * @param @return
    * @param @throws Exception      
    * @return AjaxJsonData
    */
    @RequestMapping("saveBidNotice")
    @ResponseBody 
    public AjaxJsonData saveBidNotice(HttpServletRequest request, Article article) throws Exception{
    	try {
    		User currUser = (User) request.getSession().getAttribute("loginUser");
    		article.setUser(currUser);
    		Timestamp ts = new Timestamp(new Date().getTime());
    		article.setCreatedAt(ts);
    		Timestamp ts1 = new Timestamp(new Date().getTime());
    		article.setUpdatedAt(ts1);
    		ArticleType at = articelTypeService.selectTypeByPrimaryKey("14");
    		article.setArticleType(at);
    		article.setStatus(0);
    		String[] ranges = request.getParameterValues("ranges");
            if (ranges != null && ranges.length > 0){
                if (ranges.length > 1){
                    article.setRange(2);
              } else {
                  for(int i=0;i<ranges.length;i++){
                      article.setRange(Integer.valueOf(ranges[i]));
                  }
              }
           }
            if (article.getId() != null && !"".equals(article.getId())){
                articelService.update(article);
            } else {
                articelService.addArticle(article);
            }
    		jsonData.setMessage("保存成功");
    		jsonData.setObj(article);
    		return jsonData;
    	} catch (Exception e) {
    		throw new Exception("招标文件保存失败！");
    	}
    }
    
    /**
     * @Title: printView
     * @author Song Biaowei
     * @date 2016-10-24 下午7:34:32  
     * @Description: 打印预览
     * @param @param content
     * @param @param name
     * @param @param projectId
     * @param @param request
     * @param @param model
     * @param @return      
     * @return String
     */
    @RequestMapping("/printView")
    public String printView(String content, String name, String projectId, HttpServletRequest request, Model model){
    	model.addAttribute("content", content);
    	model.addAttribute("name", name);
        model.addAttribute("projectId", projectId);
    	return "bss/ppms/competitive_negotiation/bid_notice/print_view";
    }
    
    
   /**
    * @Title: export
    * @author Song Biaowei
    * @date 2016-10-24 下午7:34:43  
    * @Description: 导出 
    * @param @param request
    * @param @param resp
    * @param @return
    * @param @throws IOException      
    * @return ResponseEntity<byte[]>
    */
    @RequestMapping("/export")
    public ResponseEntity<byte[]> export(HttpServletRequest request, HttpServletResponse resp) throws IOException{
    	String articleName = "成交公示";
    	String name = request.getParameter("name");
    	if(name != null && !"".equals(name)){
    	    articleName = name;
    	}
    	String content = request.getParameter("content");
        resp.reset();  
        resp.setContentType("application/vnd.ms-word;charset=UTF-8"); 
        String guessCharset = "gb2312";  
        String strFileName = new String(articleName.getBytes(guessCharset), "ISO8859-1"); 
        resp.setHeader("Content-Disposition", "attachment;filename=" + strFileName + ".doc"); 
        OutputStream os = resp.getOutputStream();  
        os.write(content.getBytes(), 0, content.getBytes().length);  
        os.flush();  
        os.close(); 
    //		byte[] bs = content.getBytes();
    //		HttpHeaders headers = new HttpHeaders();
    //		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
    //		headers.setContentDispositionFormData("content", new String(articleName.getBytes("UTF-8"), "iso-8859-1"));  
    //		return (new ResponseEntity<byte[]>(bs, headers, HttpStatus.CREATED));  
        return null;
    }
    
    @RequestMapping("/publishEdit")
    public String publishEdit(Model model, String id){
    	model.addAttribute("articleId", id);
    	return "bss/ppms/competitive_negotiation/bid_notice/publish_edit";
    }
    
  /**
   * @Title: publish
   * @author Song Biaowei
   * @date 2016-10-24 下午7:34:55  
   * @Description: 发布成交公示
   * @param @param files
   * @param @param request
   * @param @param response
   * @param @param id
   * @param @return
   * @param @throws Exception      
   * @return String
   */
    @RequestMapping("/publish") 
    public String publish(@RequestParam("files") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response, String id) throws Exception{
        try {
            Article article = articelService.selectArticleById(id);
            Timestamp ts = new Timestamp(new Date().getTime());
            article.setPublishedAt(ts);
            User user = (User) request.getSession().getAttribute("loginUser");
            article.setPublishedName(user.getRelName());
            article.setStatus(2);
            articelService.update(article);
            //上传审批文件
            uploadFile(article, request, files);
            jsonData.setMessage("发布成功");
            return "redirect:bidNotice.html?projectId=" + article.getProjectId();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
  /**
   * @Title: uploadFile
   * @author Song Biaowei
   * @date 2016-10-24 下午7:35:15  
   * @Description: 上传附件
   * @param @param article
   * @param @param request
   * @param @param files      
   * @return void
   */
    public void uploadFile(Article article, HttpServletRequest request, MultipartFile[] files){
    	if (files != null){
            for (int i = 0; i < files.length; i++){
                try {
                    String url = FtpUtil.upload2("bidNotice", files[i]);
                    //截取文件名
                    String filename = url.substring(url.lastIndexOf("/") + 1);
                    //截取文件路径
                    String path = url.substring(0, url.lastIndexOf("/") + 1).replace("\\", "/");
                    ProjectAttachments attachment = new ProjectAttachments();
                    Project project = projectService.selectById(article.getProjectId());
                    attachment.setProject(project);
                    attachment.setFileName(filename);
                    attachment.setCreatedAt(new Date());
                    attachment.setUpdatedAt(new Date());
                    attachment.setContentType(files[i].getContentType());
                    attachment.setFileSize((float) files[i].getSize());
                    attachment.setKind(3);
                    attachment.setAttachmentPath(path);
                    attachment.setIsDeleted(0);
                    projectAttachmentsService.save(attachment);
            	} catch (IllegalStateException e) {
            	    e.printStackTrace();
            	}
            }
    	}
    }
	
}
