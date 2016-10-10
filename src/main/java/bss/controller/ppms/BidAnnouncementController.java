/**
 * 
 */
package bss.controller.ppms;

import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import bss.model.ppms.Project;

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
	 * 
	* @Title: createWordForProJect
	* @author Peng Zhongjun
	* @date 2016-9-30 下午1:33:48  
	* @Description: 拟制招标文件导入模板自动匹配项目信息 
	* @param @param project      
	* @return void
	 */
	@RequestMapping("/bidAnnouncementWithTemplate")
	public void importTemplateForWord(HttpServletRequest request,Project project,String filePath,String fileName) throws UnsupportedEncodingException{
		/** 用于组装word页面需要的数据 */
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("projectName", project.getName()==null?"":project.getName());//项目名称
		dataMap.put("projectNumber", project.getProjectNumber()==null?"":project.getProjectNumber());//项目编号
		dataMap.put("projectPrincipal", project.getPrincipal()==null?"":project.getPrincipal());//项目负责人
		dataMap.put("projectIpone", project.getIpone()==null?"":project.getIpone());//项目负责人联系电话		
		dataMap.put("projectLinkman", project.getLinkman()==null?"":project.getLinkman());//项目联系人		
		dataMap.put("projectLinkmanIpone", project.getLinkmanIpone()==null?"":project.getLinkmanIpone());//项目联系人联系电话		
		dataMap.put("projectBidUnit", project.getBidUnit()==null?"":project.getBidUnit());//项目招标单位		
		dataMap.put("projectAddress", project.getAddress()==null?"":project.getAddress());//项目招标单位联系地址
		dataMap.put("projectPostcode", project.getPostcode()==null?"":project.getPostcode());//项目招标单位邮编
		dataMap.put("projectSupplierNumber", project.getSupplierNumber()==null?"":project.getSupplierNumber());//项目最少供应商人数
		dataMap.put("projectOfferStandad", project.getOfferStandard()==null?"":project.getOfferStandard());//项目报价标准分值
		dataMap.put("projectIntroduce", project.getPrIntroduce()==null?"":project.getPrIntroduce());//项目介绍		
		dataMap.put("projectBudgetAmount", project.getBudgetAmount()==null?"":project.getBudgetAmount());//项目预算金额（万）
		dataMap.put("projectPassWord", project.getPassWord()==null?"":project.getPassWord());//项目密码		
		dataMap.put("projectScoringRubric", project.getScoringRubric()==null?"":project.getScoringRubric());//项目评分细则
		dataMap.put("projectOperator", project.getOperator()==null?"":project.getOperator());//项目经办人
		dataMap.put("projectDivisionOfWork", project.getDivisionOfWork()==null?"":project.getDivisionOfWork());//项目工作分工	
		dataMap.put("projectPurchaseType", project.getPurchaseType()==null?"":project.getPurchaseType());//项目采购方式
		dataMap.put("projectMaterialsType", project.getMaterialsType()==null?"":project.getMaterialsType());//项目物资类别		
		dataMap.put("projectSectorOfDemand", project.getSectorOfDemand()==null?"":project.getSectorOfDemand());//项目需求部门
		dataMap.put("projectPurchaseDep", project.getPurchaseDep()==null?"":project.getPurchaseDep());//项目采购机构
		//项目投标开始时间
		dataMap.put("projectDeadline", project.getDeadline()==null?"":project.getDeadline());//项目投标截止时间
		//项目投标地点		
		dataMap.put("projectDateOfEntrustment", project.getDateOfEntrustment()==null?"":project.getDateOfEntrustment());//项目委托日期
		dataMap.put("projectBidDate", project.getBidDate()==null?"":project.getBidDate());//项目开标时间
		dataMap.put("projectBidAddress", project.getBidAddress()==null?"":project.getBidAddress());//项目开标地点
		//文件名称	
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
	@RequestMapping("/publishBidAnnouncement")
	public String publish(HttpServletRequest request,Article article){

		return "redirect:bidAnnouncementAdd.do";
	}
}
