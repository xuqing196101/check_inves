package bss.controller.ppms;

import iss.model.ps.Article;
import iss.model.ps.ArticleAttachments;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import bss.service.ppms.ProjectService;

/**
 * Description: 公开招标
 *
 * @author Ye MaoLin
 * @version 2016-10-9
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/open_bidding")
public class OpenBiddingController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ArticleService articelService;
	
	@Autowired
	private ArticleTypeService articelTypeService;
	
	@Autowired
	private ArticleAttachmentsService attachmentsService;
	
	private AjaxJsonData jsonData = new AjaxJsonData();
	
	/**
	 * Description: 进入招标文件页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-14
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/bidFile")
	public String bidFile(String id, Model model){
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bss/ppms/open_bidding/bid_file/add";
	}
	
	/**
	 * Description: 跳转到招标公告页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-14
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/bidNotice")
	public String bidNotice(String projectId, Model model){
		Article article = new Article();
		article.setProjectId(projectId);
		ArticleType at = articelTypeService.selectTypeByPrimaryKey("7");
		article.setArticleType(at);
		List<Article> articles = articelService.selectArticleByProjectId(article);
		if(articles != null && articles.size() > 0){
			model.addAttribute("article", articles.get(0));
			model.addAttribute("projectId", projectId);
			return "bss/ppms/open_bidding/bid_notice/view";
		} else {
			model.addAttribute("projectId", projectId);
			return "bss/ppms/open_bidding/bid_notice/add";
		}
	}
	
	/**
	 * Description: 保存招标公告
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-18
	 * @param request
	 * @param article
	 * @return AjaxJsonData
	 * @throws Exception
	 * @exception IOException
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
			ArticleType at = articelTypeService.selectTypeByPrimaryKey("7");
			article.setArticleType(at);
			article.setStatus(0);//保存
			articelService.addArticle(article);
			jsonData.setMessage("已保存");
			return jsonData;
		} catch (Exception e) {
			throw new Exception("招标文件保存失败！");
		}
	}
	
	/**
	 * Description: 打印预览
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-17
	 * @param request
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/printView")
	public String printView(HttpServletRequest request,Model model){
		String content = request.getParameter("content");
		model.addAttribute("content", content);
		return"bss/ppms/open_bidding/bid_notice/print_view";
	}
	
	/**
	 * Description: 导出到本地
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-17
	 * @param request
	 * @return ResponseEntity<byte[]>
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/export")
	public ResponseEntity<byte[]> export(HttpServletRequest request) throws IOException{
		String articleName = "招标公告.doc";
		String projectId = request.getParameter("projectId");
		Article article = new Article();
		article.setProjectId(projectId);
		ArticleType at = articelTypeService.selectTypeByPrimaryKey("7");
		article.setArticleType(at);
		List<Article> articles = articelService.selectArticleByProjectId(article);
		if(articles != null && articles.size() > 0){
			articleName = articles.get(0).getName() + ".doc";
		}
		String content = request.getParameter("content");
		byte[] bs = content.getBytes();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("content", new String(articleName.getBytes("UTF-8"), "iso-8859-1"));  
		return (new ResponseEntity<byte[]>(bs, headers, HttpStatus.CREATED));  
	}
	
	/**
	 * Description: 发布
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-17
	 * @param request
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/publish") 
	public String publish(@RequestParam("attaattach") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response, Article article) throws IOException{
		Timestamp ts = new Timestamp(new Date().getTime());
		article.setPublishedAt(ts);
		String[] ranges = request.getParameter("ranges").split(",");
		if(ranges != null && !"".equals(ranges)){
			if(ranges.length>1){
				article.setRange(2);
			}else{
				for(int i=0;i<ranges.length;i++){
					article.setRange(Integer.valueOf(ranges[i]));
				}
			}
		}
		User user = (User) request.getSession().getAttribute("loginUser");
		article.setPublishedName(user.getRelName());
		article.setStatus(2);//发布
		articelService.update(article); 
		uploadFile(article, request, files);
		return "redirect:bidAnnouncementAdd.do";
	}
	
	public void uploadFile(Article article,HttpServletRequest request,MultipartFile[] files){
		if(files != null){
			for(int i=0;i<files.length;i++){
			        try {
			        	String url = FtpUtil.upload2("bidNotice", files[i]);
			            //截取文件名
		                String filename=url.substring(url.lastIndexOf("/")+1);
		                //截取文件路径
		                String path = url.substring(0,url.lastIndexOf("/")+1).replace("\\", "/");
			        	ArticleAttachments attachment = new ArticleAttachments();
						attachment.setArticle(new Article(article.getId()));
						attachment.setFileName(filename);
						attachment.setCreatedAt(new Date());
						attachment.setUpdatedAt(new Date());
						attachment.setContentType(files[i].getContentType());
						attachment.setFileSize((float)files[i].getSize());
						attachment.setAttachmentPath(path);
						attachmentsService.insert(attachment);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					}
				}
		}
	}
}
