/**
 * 
 */
package bss.controller.ppms;

import iss.model.ps.Article;
import iss.model.ps.ArticleAttachments;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;

/**
 * @Title:PreBiddingDocController 
 * @Description:拟制招标文件控制器
 * @author Peng Zhongjun
 * @date 2016-9-29下午4:16:59
 */
@Controller
@Scope("prototype")
@RequestMapping("/bidAnnouncement")
public class BidAnnouncementController extends BaseSupplierController{
	
	@Autowired
	private ArticleService articelService;
	@Autowired
	private ArticleTypeService articelTypeService;
	@Autowired
	private ArticleAttachmentsService attachmentsService;

	/**
	 * 
	* @Title: addBidAnnouncement
	* @author Peng Zhongjun
	* @date 2016-10-8 下午1:38:47  
	* @Description: 跳转拟制招标公告界面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/bidAnnouncementAdd")
	public String addBidAnnouncement(){
		return "bss/ppms/bid/bid_announcement";
	}
	
	/**	 
	* @Title: priView
	* @author Peng Zhongjun
	* @date 2016-10-8 下午6:16:47  
	* @Description: 打印预览 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/preViewBidAnnouncement")
	public String priView(HttpServletRequest request,Model model){
		String content = request.getParameter("content");
		model.addAttribute("content", content);
		return"bss/ppms/bid/print";
	}
	
	/**
	* @Title: creatWorkLocal
	* @author Peng Zhongjun
	* @date 2016-10-9 下午7:45:10  
	* @Description: 导出到本地 
	* @param @param request      
	* @return void
	 */
	@RequestMapping("/outputBidAnnouncement")
	public ResponseEntity<byte[]> loadExpertTemplet(HttpServletRequest request) throws IOException{
		String content = request.getParameter("content");
		byte[] bs = content.getBytes();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("content", new String("招标公告.docx".getBytes("UTF-8"), "iso-8859-1"));  
		return (new ResponseEntity<byte[]>(bs, headers, HttpStatus.CREATED));  
	}
	/**	 
	* @Title: save
	* @author Peng Zhongjun
	* @date 2016-10-10 下午4:30:05  
	* @Description: 保存到数据库 
	* @param @param request
	* @param @param article
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveBidAnnouncement")
	public String save(HttpServletRequest request,Article article){
		Timestamp ts = new Timestamp(new Date().getTime());
		article.setCreatedAt(ts);
		Timestamp ts1 = new Timestamp(new Date().getTime());
		article.setUpdatedAt(ts1);
		ArticleType at = articelTypeService.selectTypeByPrimaryKey("7");
		article.setArticleType(at);
		String name = request.getParameter("name");
		article.setName(name);
		article.setStatus(0);//保存
		articelService.addArticle(article);
		return "redirect:bidAnnouncementAdd.do";
	}
	
	@RequestMapping("/publish")
	public String publish(HttpServletRequest request){
		String content = request.getParameter("content");
		request.getSession().setAttribute("BidAnnouncement", content);
		return "bss/ppms/bid/publish_announcement";
	}
	
	/**	 
	* @Title: save
	* @author Peng Zhongjun
	* @date 2016-10-10 下午4:30:05  
	* @Description: 发布消息
	* @param @param request
	* @param @param article
	* @param @return      
	* @return String
	 * @throws IOException 
	 */
	@RequestMapping("/publishBidAnnouncement")
	public String publishBidAnnouncement(@RequestParam("attaattach") MultipartFile[] attaattach,HttpServletRequest request, HttpServletResponse response,Article article) throws IOException{
		Timestamp ts = new Timestamp(new Date().getTime());
		article.setCreatedAt(ts);
		Timestamp ts1 = new Timestamp(new Date().getTime());
		article.setUpdatedAt(ts1);
		Timestamp ts2 = new Timestamp(new Date().getTime());
		article.setPublishedAt(ts2);
		ArticleType at = articelTypeService.selectTypeByPrimaryKey("7");//招标公告类型
		article.setArticleType(at);
		String content = (String)request.getSession().getAttribute("BidAnnouncement");
		article.setContent(content);
		String[] ranges = request.getParameter("ranges").split(",");
		if(ranges!=null&&!ranges.equals("")){
			if(ranges.length>1){
				article.setRange(2);
			}else{
				for(int i=0;i<ranges.length;i++){
					article.setRange(Integer.valueOf(ranges[i]));
				}
			}
		}
		User user = (User) request.getSession().getAttribute("loginUser");
		article.setUser(user);
		article.setIsDeleted(0);
		article.setShowCount(0);
		article.setDownloadCount(0);	
		article.setStatus(2);//发布
		articelService.addArticle(article); 
		uploadFile(article,request,attaattach);
		return "redirect:bidAnnouncementAdd.do";
	}
	
	/**
	 * 
/**
	 * 
	* @Title: uploadFile
	* @author QuJie 
	* @date 2016-9-9 下午1:36:34  
	* @Description: 上传的公共方法 
	* @param @param article
	* @param @param request
	* @param @param attaattach      
	* @return void
	 */
	public void uploadFile(Article article,HttpServletRequest request,MultipartFile[] attaattach){
		if(attaattach!=null){
			for(int i=0;i<attaattach.length;i++){
				if(attaattach[i].getOriginalFilename()!=null && attaattach[i].getOriginalFilename()!=""){
			        String rootpath = (request.getSession().getServletContext().getRealPath("/")+"upload/").replace("\\", "/");
			        /** 创建文件夹 */
					File rootfile = new File(rootpath);
					if (!rootfile.exists()) {
						rootfile.mkdirs();
					}
			        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attaattach[i].getOriginalFilename();
			        String filePath = rootpath+fileName;
			        File file = new File(filePath);
			        try {
						attaattach[i].transferTo(file);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ArticleAttachments attachment=new ArticleAttachments();
					attachment.setArticle(new Article(article.getId()));
					attachment.setFileName(fileName);
					attachment.setCreatedAt(new Date());
					attachment.setUpdatedAt(new Date());
					attachment.setContentType(attaattach[i].getContentType());
					attachment.setFileSize((float)attaattach[i].getSize());
					attachment.setAttachmentPath(filePath);
					attachmentsService.insert(attachment);
				}
			}
		}
	}
}
