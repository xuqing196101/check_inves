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
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.Templet;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.service.bms.TempletService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.SupplierCheckPassService;

/**
 * @Title:PreBiddingDocController 
 * @Description:拟制中标文件控制器
 * @author Peng Zhongjun
 * @date 2016-9-29下午4:16:59
 */
@Controller
@Scope("prototype")
@RequestMapping("/resultAnnouncement")
public class ResultAnnouncementController extends BaseSupplierController{
    /** 公告类型 - 采购公告*/
    private static final String  PURCHASE_NOTICE= "purchase";
    /** 公告类型 - 中标公告*/
    private static final String  WIN_NOTICE= "win";
	@Autowired
	private ArticleService articelService;
	@Autowired
	private ArticleTypeService articelTypeService;
	@Autowired
	private TempletService templetService;
	@Autowired
	private ArticleAttachmentsService attachmentsService;
	
	
    /**
     * @Fields detailService : 引用项目详细业务接口
     */
    @Autowired
    private ProjectDetailService detailService;
    /**
     * @Fields packageService : 引用分包业务逻辑接口
     */
    @Autowired
    private PackageService packageService;
    /**
     * @Fields projectService : 引用项目业务实现接口
     */
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private SaleTenderService saleTenderService;
    @Autowired
    private SupplierService supplierService;
    
    /**
     * 成交供应商服务接口
     */
    @Autowired
    private SupplierCheckPassService supplierCheckPassService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
	/**
	 * 
	 * @Title: addBidAnnouncement
	 * @author Peng Zhongjun
	 * @date 2016-10-8 下午1:38:47  
	 * @Description: 跳转拟制招标公告界面 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/resultAnnouncementAdd")
	public String addBidAnnouncement(){
		return "bss/ppms/result/result_announcement";
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
	@RequestMapping("/resultAnnouncementWithTemplate")
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
	@RequestMapping("/preViewResultAnnouncement")
	public String priView(HttpServletRequest request,Model model){
		String content = request.getParameter("content");
		model.addAttribute("content", content);
		return"bss/ppms/result/print";
	}

	/**
	 * @Title: creatWorkLocal
	 * @author Peng Zhongjun
	 * @date 2016-10-9 下午7:45:10  
	 * @Description: 导出到本地 
	 * @param @param request      
	 * @return void
	 */
	@RequestMapping("/outputResultAnnouncement")
	public ResponseEntity<byte[]> loadExpertTemplet(HttpServletRequest request) throws IOException{
		String content = request.getParameter("content");
		byte[] bs = content.getBytes();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("content", new String("招标公告.docx".getBytes("UTF-8"), "iso-8859-1"));  
		return (new ResponseEntity<byte[]>(bs, headers, HttpStatus.CREATED));  
	}


	/**
	 * 
	 * @Title: getAll
	 * @author Liyi 
	 * @date 2016-9-1 下午1:58:18  
	 * @Description:获取模版列表
	 * @param: model
	 * @return: String
	 */
	@RequestMapping("/getAll")
	public String getAll(Model model,Integer page, String projectId){
		List<Templet> templets = templetService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<Templet>(templets));
		model.addAttribute("projectId", projectId);
		return "bss/ppms/result/list";
	}

	/**
	 * 
	 * @Title: view
	 * @author Liyi 
	 * @date 2016-9-6 下午1:36:40  
	 * @Description:获取模板信息
	 * @param:     
	 * @return:
	 */
	@ResponseBody
	@RequestMapping("/view")
	public Object view(Model model,String[] id, String projectId){
		List<User> list=new ArrayList<User>();
		User user=new User();
		user.setAddress("asdf");
		User user1=new User();
		user1.setAddress("asdf1");
		list.add(user);
		list.add(user1);
		Templet templet = templetService.get(id[0]);
		StringBuffer sb=new StringBuffer();
		if(templet.getContent().contains("<table")){
			String content=templet.getContent();
			//开始地址
			int action=content.indexOf("<tbody>");
			//结束地址
			int end =content.indexOf("</tbody>");
			
			String  c=content.substring(action, end);
			sb.append("<tr style='height:48px;page-break-inside:avoid' class='firstRow'><td style='padding: 0px 7px; border-width: 1px; border-style: solid; border-color: windowtext;' width='54' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>包号</span></strong></p></td><td style='padding: 0px 7px; border-left: medium none;' width='81' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>货物名称</span></strong></p></td><td style='padding: 0px 7px; border-left: medium none;' width='78' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>规格型号</span></strong></p></td><td style='padding: 0px 7px; border-left: medium none;' width='60' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>计量</span></strong></p><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>单位</span></strong></p></td><td style='padding: 0px 7px; border-left: medium none;' width='60' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>数量</span></strong></p></td><td style='padding: 0px 7px; border-left: medium none;' width='73' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>交货时间</span></strong></p></td><td style='padding: 0px 7px; border-left: medium none;' width='86' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>交货地点</span></strong></p></td><td style='padding: 0px 7px; border-left: medium none;' width='78' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>交货方式</span></strong></p></td><td style='padding: 0px 7px; border-left: medium none;' width='53' valign='center'><p style='text-align:center'><strong><span style=';font-family:宋体;font-weight:bold;font-size:14px'>备注</span></strong></p></td></tr>");
			for (User user2 : list) {
				sb.append("<tr style='height:30px;page-break-inside:avoid'>"+
						"<td style='padding: 0px 7px; border-left: medium none;' width='54' valign='center'>"+
						"<p><span style='font-family:宋体;font-size:14px'>一</span></p>"+
						"</td>"+
						"<td style='padding: 0px 7px; border-left: medium none;' width='81' valign='center'><br/>"+
						"<p><span style='font-family:宋体;font-size:14px'>"+user2.getAddress()+"</span></p>"+
						"</td>"+		
						"<td style='padding: 0px 7px; border-left: medium none;' width='78' valign='top'><br/>"+
						"<p><span style='font-family:宋体;font-size:14px'>"+user2.getAddress()+"</span></p>"+
						"</td>"+
						"<td style='padding: 0px 7px; border-left: medium none;' width='60' valign='center'><br/>"+
						"<p><span style='font-family:宋体;font-size:14px'>"+user2.getAddress()+"</span></p>"+
						"</td>"+
						"<td style='padding: 0px 7px; border-left: medium none;' width='60' valign='center'><br/>"+
						"<p><span style='font-family:宋体;font-size:14px'>"+user2.getAddress()+"</span></p>"+
						"</td>"+
						"<td style='padding: 0px 7px; border-left: medium none;' width='73' valign='center'><br/>"+
						"<p><span style='font-family:宋体;font-size:14px'>"+user2.getAddress()+"</span></p>"+
						"</td>"+
						"<td style='padding: 0px 7px; border-left: medium none;' width='86' valign='center'><br/>"+
						"<p><span style='font-family:宋体;font-size:14px'>"+user2.getAddress()+"</span></p>"+
						"</td>"+
						"<td style='padding: 0px 7px; border-left: medium none;' width='78' valign='center'><br/>"+
						"<p><span style='font-family:宋体;font-size:14px'>"+user2.getAddress()+"</span></p>"+
						"</td>"+
						"<td style='padding: 0px 7px; border-left: medium none;' width='53' valign='center'><br/>"+
						"<p><span style='font-family:宋体;font-size:14px'>"+user2.getAddress()+"</span></p>"+
						"</td>"+
						"</tr>");
			}
			templet.setContent(templet.getContent().replace(c, sb.toString()));
		}else{
		    StringBuilder builder = new StringBuilder();
            if (projectId != null) {
                Project p = projectService.selectById(projectId);
                List<SaleTender> selectListByProjectId = saleTenderService.selectListByProjectId(p.getId());
                for (SaleTender saleTender : selectListByProjectId) {
                    Packages packages = packageService.selectByPrimaryKeyId(saleTender.getPackages());
                    builder.append("<p>"+packages.getName()+"供应商名称:</p>");
                    Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
                    if(supplier != null){
                        builder.append("<p>&nbsp;&nbsp;"+supplier.getSupplierName()+"</p>");
                    }
                    
                }
                
            }
			templet.setContent(templet.getContent()
					.replace("${projectCode}", "g20-501")
					.replace("${projectName}", "大型服务器")
					.replace("${package}", "1")
					.replace("${goodName}", "服务器")
					.replace("${count}", "12")
					.replace("${bidPrice}","2000")
					.replace("${bidAmount}", "3000").replace("supplier", builder));
		}
		if (projectId != null ){
		    String content = getDefaultTemplate(projectId, templet);
		    templet.setContent(content);
		}
		return templet;
	}

	 public String getContent(String projectId) {
	        HashMap<String, Object> map = new HashMap<String, Object>();
	        map.put("projectId", projectId);
	        List<Packages> list = packageService.findPackageById(map);
	        if(list != null && list.size()>0){
	            for(Packages ps:list){
	                HashMap<String,Object> packageId = new HashMap<String,Object>();
	                packageId.put("packageId", ps.getId());
	                List<ProjectDetail> detailList = detailService.selectById(packageId);
	                ps.setProjectDetails(detailList);
	            }
	        }
	        StringBuilder sb = articelService.getContent(list);
	        return sb.toString();
	    }
	    
	    public String getDefaultTemplate(String projectId ,Templet templet) {
	            String content = templet.getContent();
	            String table = getContent(projectId);
	            Project p = projectService.selectById(projectId);
	            String purchaseTypeName = "";
	            StringBuilder auditResult = new StringBuilder();
	            auditResult.append("");
	            //评分结果
	            HashMap<String ,Object> map = new HashMap<String ,Object>();
	            map.put("projectId", projectId);
	            //查询包信息
	            List<Packages> packageList = packageService.findPackageById(map);
	            for (Packages packages : packageList) {
	              SupplierCheckPass checkPass = new SupplierCheckPass();
	              checkPass.setPackageId(packages.getId());
	              List<SupplierCheckPass> supplierCheckPasses = supplierCheckPassService.listCheckPass(checkPass);
	              auditResult.append("<p>"+packages.getName()+"参加供应商排名:</p>");
	              if (supplierCheckPasses != null && supplierCheckPasses.size() > 0) {
	                for (int i = 1; i < supplierCheckPasses.size()+1; i++) {
	                  Supplier supplier = supplierCheckPasses.get(i-1).getSupplier();
	                  if (supplier != null) {
	                    auditResult.append("<p>&nbsp;&nbsp;第"+i+"名:"+supplier.getSupplierName()+"</p>");
	                  }
	                }
	              }
	            }
	            if (p.getPurchaseType() != null) {
	               purchaseTypeName = DictionaryDataUtil.findById(p.getPurchaseType()).getName();
	            }
	            String purchaserName = "";
	            if (p != null) {
	                String purchaseDepId = p.getPurchaseDepId();
	                Orgnization org = orgnizationService.getOrgByPrimaryKey(purchaseDepId);
	                if (org != null) {
	                  purchaserName = org.getName();
	                }
	            }
	            String contact = "";
	            String contactTelephone = "";
	            String contactAddress = "";
	            String fax = "";
	            String bank = "";
	            String bidDate = "";
	            if (p.getBidDate() != null) {
	              bidDate = new SimpleDateFormat("yyyy年MM月dd日").format(p.getBidDate());
	            }
	            content = content.replace("projectDetail", table).replace("projectName", p.getName()).replace("projectNum", p.getProjectNumber()).replace("purchaseType", purchaseTypeName);
	            content = content.replace("bidDate", bidDate).replace("contact", contact);
	            content = content.replace("purchaserName", purchaserName).replace("telephone", contactTelephone);
	            content = content.replace("address", contactAddress).replace("fax", fax).replace("bank", bank).replace("auditResult", auditResult.toString());
	            return content;
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
		ArticleType at = articelTypeService.selectTypeByPrimaryKey("8");//中标公告
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
		return "redirect:resultAnnouncementAdd.do";
	}
	
	/**
	 * @Description:跳转
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月11日 下午5:54:07  
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/publish")
	public String publish(HttpServletRequest request){
		String content = request.getParameter("content");
		request.getSession().setAttribute("BidAnnouncement", content);
		return "bss/ppms/result/publish_announcement";
	}
	
	/**
	 * @Description:保存到数据库
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月12日 上午10:20:49  
	 * @param @param request
	 * @param @param article
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/saveResultAnnouncement")
	public String save(HttpServletRequest request,Article article){
		Timestamp ts = new Timestamp(new Date().getTime());
		article.setCreatedAt(ts);
		Timestamp ts1 = new Timestamp(new Date().getTime());
		article.setUpdatedAt(ts1);
		ArticleType at = articelTypeService.selectTypeByPrimaryKey("8");//中标公告
		article.setArticleType(at);
		String name = request.getParameter("name");
		article.setName(name);
		article.setStatus(0);//保存
		articelService.addArticle(article);
		return "redirect:resultAnnouncementAdd.do";
	}
	
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
