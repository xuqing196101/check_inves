/**
 * 
 */
package bss.controller.ppms;

import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;
import ses.model.oms.util.AjaxJsonData;
import ses.util.FtpUtil;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectAttachments;
import bss.service.ppms.ProjectAttachmentsService;
import bss.service.ppms.ProjectService;

/**
 * @SingleSourceController 
 * @Description:拟制招标文件控制器
 * @author Peng Zhongjun
 * @date 2016-9-29下午4:16:59
 */
@Controller
@Scope("prototype")
@RequestMapping("/single_source")
public class SingleSourceController extends BaseSupplierController{
	

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
     * @Fields projectAttachmentsService : 引用项目附件业务实现接口
     */
    @Autowired
    private ProjectAttachmentsService projectAttachmentsService;
    
    /**
     * @Fields jsonData : ajax返回数据封装类
     */
    private AjaxJsonData jsonData = new AjaxJsonData();
    /**
     * 
    * @Title: notice
    * @author Peng Zhongjun
    * @date 2016-10-25 上午8:46:18  
    * @Description: 单一来源公告 
    * @param @param projectId
    * @param @param model
    * @param @return      
    * @return String
     */
    @RequestMapping("/notice")
    public String notice(String projectId, Model model){
    	Article article = new Article();
    	article.setProjectId(projectId);
    	ArticleType at = articelTypeService.selectTypeByPrimaryKey("10");//单一来源
    	article.setArticleType(at);
    	List<Article> articles = articelService.selectArticleByProjectId(article);
    	if (articles != null && articles.size() > 0){
    	    if (articles.get(0).getPublishedAt() != null && articles.get(0).getPublishedName() != null && !"".equals(articles.get(0).getPublishedName())){
    	        model.addAttribute("article", articles.get(0));
    	        model.addAttribute("projectId", projectId);
    	        return "bss/ppms/single_source/notice/view";
    	    } else {
    	        model.addAttribute("content", articles.get(0).getContent());
    	        model.addAttribute("name", articles.get(0).getName());
    	        model.addAttribute("articleId", articles.get(0).getId());
    	        model.addAttribute("range", articles.get(0).getRange());
                model.addAttribute("projectId", projectId);
                return "bss/ppms/single_source/notice/add";
    	    }
    	} else {
    	    model.addAttribute("projectId", projectId);
    	    return "bss/ppms/single_source/notice/add";
    	}
    }
    /**
     * 
    * @Title: saveBidNotice
    * @author Peng Zhongjun
    * @date 2016-10-25 上午10:48:59  
    * @Description: 保存 
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
    		ArticleType at = articelTypeService.selectTypeByPrimaryKey("10");
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
    		throw new Exception("单一来源公告保存失败！");
    	}
    }
    
    /**
     * 
    * @Title: publishEdit
    * @author Peng Zhongjun
    * @date 2016-10-25 上午10:47:42  
    * @Description: 跳转 
    * @param @param model
    * @param @param id
    * @param @return      
    * @return String
     */
    @RequestMapping("/publishEdit")
    public String publishEdit(Model model, String id){
    	model.addAttribute("articleId", id);
    	return "bss/ppms/open_bidding/bid_notice/publish_edit";
    }
    /**
     * 
    * @Title: publish
    * @author Peng Zhongjun
    * @date 2016-10-25 上午10:55:21  
    * @Description: 发布单一来源采购公告 
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
            return "redirect:notice.html?projectId=" + article.getProjectId();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
    /**
     * 上传附件
     * 上传项目类型附件
     * @param article
     * @param request
     * @param files
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
