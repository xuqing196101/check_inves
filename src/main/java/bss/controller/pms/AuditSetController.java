package bss.controller.pms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.oms.Orgnization;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.util.DictionaryDataUtil;
import bss.dao.pms.AuditPersonMapper;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.model.pms.AuditPerson;
import bss.model.pms.AuditPersonList;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.service.pms.AuditParameService;
import bss.service.pms.AuditPersonService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseAuditService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.pms.UpdateFiledService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

/***
 * 
 * @Title: AuditSetController
 * @Description:  审核设置控制类
 * @author Li Xiaoxiao
 * @date  2016年9月21日,下午5:24:21
 *
 */
@Controller
@Scope
@RequestMapping("/set")
public class AuditSetController {

	
	@Autowired
	private UpdateFiledService updateFiledService;
	
	@Autowired
	private ExpertService expertService;
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private AuditPersonService auditPersonService;
	
	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;
	
	@Autowired
	private CollectPurchaseService collectPurchaseService;
	
	@Autowired
	private AuditParameService auditParameService; 
	
	@Autowired
	private PurchaseAuditService purchaseAuditService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;

	@Autowired
	private OrgnizationMapper orgnizationMapper;
	
	@Autowired
	private AuditPersonMapper auditPersonMapper;
	
	@Autowired
	private PurchaseDetailService purchaseDetailService;
	
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	/**
	 * 
	* @Title: set
	* @Description: 采购计划审核设置页面 
	* author: Li Xiaoxiao 
	* @param @param model
	* @param @param page
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String set(Model model,Integer page,String id,HttpServletRequest request,String staff){
		CollectPlan plan = collectPlanService.queryById(id);
		String type = "";
		String auditRound="";
		if(plan.getStatus()==1&&plan.getAuditTurn()!=null){
			plan.setStatus(3);
			collectPlanService.update(plan);
			type = DictionaryDataUtil.getId("SH_1");
			auditRound="第一轮审核设置";
		}
		else if(plan.getStatus()==1){
			type = request.getParameter("type");
			String auditTurn = "";
			auditTurn=DictionaryDataUtil.findById(type).getCode();
			if(auditTurn.equals("SH_1")){
				plan.setAuditTurn(1);
			}else if (auditTurn.equals("SH_2")) {
				plan.setAuditTurn(2);
			}else {
				plan.setAuditTurn(3);
			}
			collectPlanService.update(plan);
			type = DictionaryDataUtil.getId("SH_1");
			auditRound="第一轮审核设置";
		}else if(plan.getStatus()==3){
			type = DictionaryDataUtil.getId("SH_1");
			auditRound="第一轮审核设置";
		}else if(plan.getStatus()==4||plan.getStatus()==5){
			type = DictionaryDataUtil.getId("SH_2");
			auditRound="第二轮审核设置";
		}else if(plan.getStatus()==6||plan.getStatus()==7){
			type = DictionaryDataUtil.getId("SH_3");
			auditRound="第三轮审核设置";
		}
		AuditPerson person = new AuditPerson();
		person.setCollectId(id);
		person.setAuditRound(type);
		List<AuditPerson> listAudit = auditPersonService.query(person, page==null?1:page);
		PageInfo<AuditPerson> info = new PageInfo<>(listAudit);
		model.addAttribute("info", info);
		model.addAttribute("auditRound", auditRound);
		model.addAttribute("id", id);
		model.addAttribute("kind", DictionaryDataUtil.find(4));
		model.addAttribute("type", type);
		model.addAttribute("staff", staff);
		return "bss/pms/collect/auditset";
	}
	/**
	 * 
	* @Title: save
	* @Description: 设置字段是否允许修改
	* author: Li Xiaoxiao 
	* @param @param updateFiled
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	public String save(String val1, String val2,String collectId,String fname2,String fname,String type){
//		String[] field1 = val1.trim().split(",");
//		List<String> list=new LinkedList<String>();
//		
//		for(int i=0;i<field1.length;i++){
//			if(field1[i].trim().length()!=0){
//				list.add(field1[i]);
//			}
//			
//		}
//		List<String> list2=new LinkedList<String>();
//		String[] field2 = val2.trim().split(",");
//		for(int i=0;i<field2.length;i++){
//			if(field2[i].trim().length()!=0){
//				list2.add(field2[i]);
//			}
//		}
//		List<UpdateFiled> filedList=new ArrayList<UpdateFiled>();
//		List<UpdateFiled> filedList2=new ArrayList<UpdateFiled>();
//		if(list.size()>0){
//			filedList = updateFiledService.query(collectId, list);
//		}
//		 if(list2.size()>0){
//			 updateFiledService.query(collectId, list2); 
//		 }
//		 
//		if((filedList!=null&&filedList.size()>0)||(filedList2!=null&&filedList2.size()>0)){
//			
//			updateFiledService.update(1, list,collectId);
//			updateFiledService.update(2, list2,collectId);
//			
//		}else{
//			UpdateFiled u=new UpdateFiled();
//			if(list!=null&&list.size()>0){
//				String[] str = fname.split(",");
//				for(int i=0;i<str.length;i++){
//					
//				
//					String id = UUID.randomUUID().toString().replaceAll("-", "");
//					u.setId(id);
//					u.setCollectId(collectId);
//					u.setFiled(field1[i]);
//					u.setFiledName(str[i]);
////					u.setFiledName("");
//					u.setIsUpdate(1);
//					updateFiledService.add(u);
//				}
//			}
//		if(list2!=null&&list2.size()>0){
//			String[] str = fname2.split(",");
//			for(int i=0;i<str.length;i++){
//				String id = UUID.randomUUID().toString().replaceAll("-", "");
//				u.setId(id);
//				u.setCollectId(collectId);
//				u.setFiled(field2[i]);
//				u.setFiledName(str[i]);
//				u.setIsUpdate(2);
//				updateFiledService.add(u);
//			}
//		}
//			
//		}
		CollectPlan collectPlan = collectPlanService.queryById(collectId);
		DictionaryData sh = DictionaryDataUtil.findById(type);
		if(sh.getCode().equals("SH_1")){
			collectPlan.setStatus(3);
		}
		if(sh.getCode().equals("SH_2")){
			collectPlan.setStatus(5);
		}
		if(sh.getCode().equals("SH_3")){
			collectPlan.setStatus(7);
		}
		
		collectPlanService.update(collectPlan);
		
		return "redirect:/look/list.html?";
	}
	
	/**
	 * 
	* @Title: getExpert
	* @Description: 查询所有专家
	* author: Li Xiaoxiao 
	* @param @param page
	* @param @param expert
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/expert")
	public String getExpert(Integer page,Expert expert,Model model,HttpServletRequest request,String satff){
		String type = request.getParameter("type");
		List<Expert> list = expertService.selectAllExpert(page==null?1:page, expert);
		PageInfo<Expert> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("expert", expert);
		model.addAttribute("type",type);
		model.addAttribute("satff", satff);
		return "bss/pms/collect/expertlist";
	}
	/**
	 * 
	* @Title: getUser
	* @Description: 查询所有用户
	* author: Li Xiaoxiao 
	* @param @param page
	* @param @param suer
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/user")
	public String getUser(Integer page,User user,Model model,HttpServletRequest request){
		String type = request.getParameter("type");
		List<User> list = userServiceI.listWithoutSupplier(page==null?1:page);
		PageInfo<User> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("user", user);
		model.addAttribute("type",type);
		return "bss/pms/collect/userlist";
	}
	/**
	 * 
	* @Title: add
	* @Description: 添加审核人员
	* author: Li Xiaoxiao 
	* @param @param auditPerson
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping(value="/add",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String add(AuditPerson auditPerson,String id,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		Integer num=0;
		 Expert expert = expertService.selectByPrimaryKey(id);
//		 if(auditPerson.getType()==1){
			 map.put("auditRound", auditPerson.getAuditRound());
			 map.put("collectId", auditPerson.getCollectId());
			 map.put("userId", expert.getId());
			 num = auditPersonService.findUserByCondition(map);
		
//		 }
//		 if(auditPerson.getType()==2){
//			 User user = userServiceI.getUserById(id);
//			 map.put("auditRound", request.getParameter("auditRound"));
//			 map.put("collectId", auditPerson.getCollectId());
//			 map.put("userId", user.getId());
//			 num = auditPersonService.findUserByCondition(map);
//		 }
		 if(num==1){
			 return "error";
		}else{
			auditPerson.setName(expert.getRelName());
			auditPerson.setMobile(expert.getMobile());
			auditPerson.setIdNumber(expert.getIdNumber());
			auditPerson.setUnitName(expert.getWorkUnit());
			auditPerson.setUserId(expert.getId());
			auditPerson.setCreateDate(new Date());
			auditPersonService.add(auditPerson);
			auditPerson.setType(1);
			return JSON.toJSONString(auditPerson.getAuditStaff());
		}
		
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	* @Title: excel
	* @Description: 下载一个 excel表格
	* author: Li Xiaoxiao 
	* @param @param request
	* @param @param response
	* @param @param collectPlan
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/excel")
	@ResponseBody
	public String excel(HttpServletRequest request,HttpServletResponse response,CollectPlan collectPlan,String org,String dep,String flag) throws UnsupportedEncodingException{
		CollectPlan plan = collectPlanService.queryById(collectPlan.getId());
		if(dep!=null){
			dep=new String(dep.getBytes("ISO-8859-1"),"UTF-8");
		}

//		collectPlan.setPlanNo("001");
		
//		List<String> no = collectPurchaseService.getNo(collectPlan.getId());
//		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
//		if(no!=null&&no.size()>0){
//			String[] str = plan.getPlanNo().split(",");
//			for(String s:no){
//				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
//				list.addAll(pur);
//			}
//			
//		}
		
		
		//查询出所有审核参数
//		DictionaryData	dictionaryData=new DictionaryData();
//		DictionaryData dd=new DictionaryData();
//		dd.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");
//		dictionaryData.setParent(dd);
//		List<DictionaryData> dic =dictionaryDataServiceI.findByKind("4");
//		List<AuditParam> all=new LinkedList<AuditParam>();
//		AuditParam auditParam=new AuditParam();
//		
//		List<AuditParamBean> bean=new LinkedList<AuditParamBean>();
//		if(dic!=null&&dic.size()>0){
//			for(DictionaryData d:dic){
//				AuditParamBean s=new AuditParamBean();
//				auditParam.setDictioanryId(d.getId());
//				List<AuditParam> a = auditParameService.query(auditParam, 1);
//				all.addAll(a);
//				s.setId(d.getId());
//				s.setSize(a.size());
//				s.setName(d.getName());
//				bean.add(s);
//			}
//		}
		List<PurchaseDetail> list = purchaseDetailService.getUnique(plan.getId(),org,dep);
		
		
		
		String filedisplay = "明细.xls";
		response.addHeader("Content-Disposition", "attachment;filename="  + new String(filedisplay.getBytes("gb2312"), "iso8859-1"));
		HSSFWorkbook workbook = new HSSFWorkbook();
	     HSSFSheet sheet = workbook.createSheet("1"); 
	     HSSFCellStyle style = workbook.createCellStyle();
//	     HSSFFont font = workbook.createFont();   
//		 font.setFontHeightInPoints((short) 22);
		 style.setBorderBottom(HSSFCellStyle.BORDER_HAIR);
		 style.setBorderLeft(HSSFCellStyle.BORDER_HAIR);
		 style.setBorderTop(HSSFCellStyle.BORDER_HAIR);
		 style.setBorderRight(HSSFCellStyle.BORDER_HAIR);
//		 style.setFont(font);
		 style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	     style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
	   
	     
	     
	     sheet.setColumnWidth(0, 2000); 
	     sheet.setColumnWidth(1, 3000); 
	     sheet.setColumnWidth(2, 3000);
	     sheet.setColumnWidth(3, 3000);
	     sheet.setColumnWidth(4, 3200);
	     sheet.setColumnWidth(5, 1200);
	     sheet.setColumnWidth(6, 2300);
	     sheet.setColumnWidth(7, 2300);
	     sheet.setColumnWidth(8, 2300);
	     sheet.setColumnWidth(9, 2300);
	     sheet.setColumnWidth(10, 2500);
	     sheet.setColumnWidth(11, 2300);
	     sheet.setColumnWidth(12, 3000);
	     sheet.setColumnWidth(13, 3000);
	    
	     //表头第一行
	     HSSFRow row = sheet.createRow(0);  
			//
	     HSSFCell  cell = row.createCell(0);
	     style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	     cell.setCellStyle(style);
//	     cell.setCellValue(plan.getFileName());
//	     sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)13));
	     generateName(workbook,sheet,plan);
	     int n=12;
	     int hb=0;
//	     font2.setFontHeightInPoints((short) 9);
//	     style.setFont(font2);
//	     for(AuditParamBean ap:bean){
//	    	 hb=n+1;
//	    	 cell = row.createCell(hb);
//		     
//		     cell.setCellValue(ap.getName());
//		     n=ap.getSize()+n;
//		     sheet.addMergedRegion(new CellRangeAddress(0,(short)0,hb,(short)n)); 
//		    
//	     }
	    
	     /*	  cell = row.createCell(16);  
     		cell.setCellValue("第二轮审核");
	     sheet.addMergedRegion(new CellRangeAddress(0,(short)0,16,(short)17));*/
//	        row = sheet.createRow((int) 1);
//	        cell = row.createCell(0);
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
////	        style.setAlignment(CellStyle.VERTICAL_CENTER);
//	        cell.setCellStyle(style);
//			cell.setCellValue("序号");
//	        cell = row.createCell(1); 
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("需求部门");
//	        cell = row.createCell(2);
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("物资类别及名称");
//	        cell = row.createCell(3); 
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("规格型号");
//	        cell = row.createCell(4); 
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("质量技术标准");
//	        cell = row.createCell(5);  
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("计量单位"); 
//	        cell = row.createCell(6);
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("采购数量");  
//	        
//	        cell = row.createCell(7); 
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("单价（元）");  
//	        
//	        cell = row.createCell(8); 
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("预算金额（万元）");  
//	        
//	        cell = row.createCell(9); 
////	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	        style.setWrapText(true);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("交货期限");  
//	        
//	        cell = row.createCell(10);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("采购方式");  
//	        
//	        cell = row.createCell(11);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("采购机构"); 
//	        
//	        cell = row.createCell(12);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("供应商");  
//	        
//	        
//	        cell = row.createCell(13);
//	        cell.setCellStyle(style);
//	        cell.setCellValue("备注");  
//	        if(plan.getAuditTurn()!=null){
////	        	 if(plan.getStatus()==3||plan.getStatus()==5||plan.getStatus()==7||plan.getStatus()==12||plan.getStatus()==2){
//		        	 cell = row.createCell(14);  
//		 	        cell.setCellValue("一轮审核建议");  
////		        }
//		 	        
//		 	       if((plan.getAuditTurn()==2||plan.getAuditTurn()==3)&&(plan.getStatus()==12||plan.getStatus()==2)){
//				        	 cell = row.createCell(15);  
//				 	        cell.setCellValue("二轮轮审核建议");  
//			        }
//		 	       if(plan.getAuditTurn()==3&&(plan.getStatus()==12||plan.getStatus()==2)){
//			        	cell = row.createCell(16);  
//			 	        cell.setCellValue("三轮审核建议");  
//			        }
//	        }
	     
	 	DecimalFormat df = new DecimalFormat("#,###.00");
	 	DecimalFormat dfn = new DecimalFormat("#,###");
	     generateHeader(workbook,sheet,plan);
//	        if(plan.getAuditTurn()==2){
//	        	 if(plan.getStatus()==3||plan.getStatus()==5||plan.getStatus()==7||plan.getStatus()==12||plan.getStatus()==2){
//		        	 cell = row.createCell(15);  
//		 	        cell.setCellValue("二轮轮审核建议");  
//		        }
//	        }
//	        if(plan.getAuditTurn()==3){
//	        	cell = row.createCell(16);  
//	 	        cell.setCellValue("三轮审核建议");  
//	        }
//	        cell = row.createCell(14);  
//	        cell.setCellValue("采购机构"); 
//	        cell = row.createCell(15);  
//	        cell.setCellValue("其他建议"); 
//	        cell = row.createCell(16);  
//	        cell.setCellValue("技术参意见"); 
//	        cell = row.createCell(17);  
//	        cell.setCellValue("其他建议"); 
	        int count=3;
	        if(flag!=null){
	        	count=2;
	        }
//	        PurchaseAudit purchaseAudit=new PurchaseAudit();
			for(PurchaseDetail p:list){
	        	row = sheet.createRow(count);
	   	        cell = row.createCell(0);
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        cell.setCellStyle(style);
	   			cell.setCellValue(p.getSeq()); 
	   	        cell = row.createCell(1);  
//	   	        if(p.getDepartment()!=null){
//	   	        	Orgnization orgnization = orgnizationMapper.findOrgByPrimaryKey(p.getDepartment());
//	   	        	if(orgnization!=null){
		        style.setWrapText(true);
	   	        style.setAlignment(CellStyle.ALIGN_LEFT);
		        cell.setCellStyle(style);
		        if(p.getSeq().matches("[\u4E00-\u9FA5]")&&!p.getSeq().contains("（")){
		        	cell.setCellValue(p.getDepartment());
		        }
	   	        		
//	   	        	}
//	   	        	
//	   	        }
	   	      
	   	        cell = row.createCell(2); 
	   	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getGoodsName());
	   	        cell = row.createCell(3); 
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getStand());
	   	        cell = row.createCell(4);
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getQualitStand());
	   	        cell = row.createCell(5);
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getItem()); 
	   	        cell = row.createCell(6); 
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		        cell.setCellStyle(style);
	   	        if(p.getPurchaseCount()!=null){
	   	        //	double d=p.getPurchaseCount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	   	        	String of = String.valueOf(p.getPurchaseCount());
	   	         cell.setCellValue(dfn.format(of));  
	   	        }
	   	       
	   	        
	   	        cell = row.createCell(7);
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		        cell.setCellStyle(style);
	   	        if(p.getPrice()!=null){
		   	        double price = p.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		   	        cell.setCellValue(df.format(price));
	   	        }
	   	     
	   	          
	   	        
	   	        cell = row.createCell(8); 
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		        cell.setCellStyle(style);
	   	        if(p.getBudget()!=null){
	   	         double budget = p.getBudget().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		   	      
		   	        cell.setCellValue(df.format(budget)); 
	   	        }
	   	      
	   	        
	   	        cell = row.createCell(9);
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getDeliverDate());  
	   	        
	   	        cell = row.createCell(10);  
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        if(p.getPurchaseCount()!=null){
	   	        	DictionaryData dicType = DictionaryDataUtil.findById(p.getPurchaseType());
	   	        	if(dicType!=null){
	   	        		cell.setCellValue(dicType.getName()); 
	   	        	}
	   	        }
	   	        
	   	        
	   	        cell = row.createCell(11);
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	         if(p.getPurchaseCount()!=null){
		   	        Orgnization orgnization = purchaseRequiredService.queryPur(p.getOrganization());
		   	        if(orgnization!=null){
	   	        		cell.setCellValue(orgnization.getName());
	   	        	}
	   	         
	   	        }
	   	         
	   	        
	   	        
	   	        cell = row.createCell(12); 
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getSupplier());  
	   	        
	   	        
	   	        cell = row.createCell(13); 
//	   	        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getMemo());  
//	   	        if(plan.getAuditTurn()!=null){
//	   	         cell = row.createCell(14);  
//		   	        cell.setCellValue(p.getOneAdvice()); 
//	   	        }
//	   	     if(plan.getAuditTurn()!=null){
//	   	       if(plan.getAuditTurn()==2||plan.getAuditTurn()==3){
//	   	         cell = row.createCell(15);  
//		   	        cell.setCellValue(p.getTwoAdvice()); 
//	   	        }
//	   	     }
//	   	  if(plan.getAuditTurn()!=null){
//	   	      if(plan.getAuditTurn()!=3){
//	   	         cell = row.createCell(16);  
//		   	        cell.setCellValue(p.getThreeAdvice()); 
//	   	        }
//	   	  }
//	   	        int an=13;
//	   	        for(AuditParam ap:all){
//	   	        	 
//	   	        	cell = row.createCell(an);  
//	   	        	purchaseAudit.setAuditParamId(ap.getId());
//	   	        	
//	   	        	purchaseAudit.setPurchaseId(p.getId());
//	   	        	PurchaseAudit audit = purchaseAuditService.query(purchaseAudit);
//	   	        	if(audit!=null){
//	   	        		cell.setCellValue(audit.getParamValue()); 
//	   	        	}
//		   	         
//		   	        an++;
//	   	        }
	   	        
	   	     /*   cell = row.createCell(14);
	   	        cell.setCellValue(p.getOneOrganiza()); 
	   	        cell = row.createCell(15);  
	   	        cell.setCellValue(p.getOneAdvice()); 
	   	        cell = row.createCell(16);  
	   	        cell.setCellValue(p.getTwoTechAdvice()); 
	   	        cell = row.createCell(17);  
	   	        cell.setCellValue(p.getTwoAdvice()); */
	   	        
	   	     count++;
	        }
	        
	        
	     ServletOutputStream fileOut=null;
		 try{
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			fileOut=response.getOutputStream();
		    workbook.write(fileOut);
		    fileOut.close();  
			}catch(Exception e){	
		}
			
		return "";	
	}	
	
	/**
	 * 
	* @Title: addStaff
	* @author ZhaoBo
	* @date 2016-12-15 下午6:34:31  
	* @Description: 添加审核人员性质 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addStaff")
	@ResponseBody
	public String addStaff(HttpServletRequest request){
		String auditStaff = request.getParameter("auditStaff");
		if(auditStaff==null||auditStaff.trim().equals("")){
			return "0";
		}else{
			return "1";
		}
	}
	
	/**
	 * 
	* @Title: addExpert
	* @author ZhaoBo
	* @date 2016-12-15 下午7:03:33  
	* @Description: 添加专家 
	* @param @param auditPerson
	* @param @param id
	* @param @param request      
	* @return void
	 */
	@RequestMapping("/addExpert")
	@ResponseBody
	public void addExpert(AuditPerson auditPerson,String id,HttpServletRequest request){
		Expert expert = expertService.selectByPrimaryKey(id);
		User user = userServiceI.findByTypeId(expert.getId());
		auditPerson.setName(expert.getRelName());
		auditPerson.setMobile(expert.getMobile());
		auditPerson.setIdNumber(expert.getIdNumber());
		auditPerson.setUnitName(expert.getWorkUnit());
		auditPerson.setAuditRound(request.getParameter("auditRound"));
		auditPerson.setUserId(user.getId());
		auditPerson.setAuditStaff(request.getParameter("staff"));
		auditPersonService.add(auditPerson);
	}
	
	/**
	 * 
	* @Title: addUser
	* @author ZhaoBo
	* @date 2016-12-15 下午7:28:35  
	* @Description: 添加用户 
	* @param @param auditPerson
	* @param @param id
	* @param @param request      
	* @return void
	 */
	@RequestMapping(value="/addUser",produces="application/text;charset=utf-8")
	@ResponseBody
	public String addUser(AuditPerson auditPerson){
		HashMap<String,Object> map = new HashMap<String,Object>();
		Integer num=0;
		 User user = userServiceI.getUserById(auditPerson.getUserId());
//		 if(auditPerson.getType()==1){
			 map.put("auditRound", auditPerson.getAuditRound());
			 map.put("collectId", auditPerson.getCollectId());
			 map.put("userId", user.getId());
			 num = auditPersonService.findUserByCondition(map);
		
//		 }
//		 if(auditPerson.getType()==2){
//			 User user = userServiceI.getUserById(id);
//			 map.put("auditRound", request.getParameter("auditRound"));
//			 map.put("collectId", auditPerson.getCollectId());
//			 map.put("userId", user.getId());
//			 num = auditPersonService.findUserByCondition(map);
//		 }
		 if(num==1){
			 return "1";
		}else{
			auditPerson.setName(user.getRelName());
			auditPerson.setMobile(user.getMobile());
//			auditPerson.setIdNumber(user.get);
			if(user.getOrg()!=null){
				if(user.getOrg().getName()!=null){
					auditPerson.setUnitName(user.getOrg().getName());	
				}
			}
			auditPerson.setUserId(user.getId());
			auditPerson.setType(2);
			auditPersonService.add(auditPerson);
			
			return auditPerson.getAuditStaff();
		}
			
	}
	
	/**
	 * 
	* @Title: judgeAddUser
	* @author ZhaoBo
	* @date 2016-12-15 下午7:49:30  
	* @Description: 添加临时专家 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping(value="/judgeAddUser",produces="application/text;charset=utf-8")
	@ResponseBody
	public String judgeAddUser(HttpServletRequest request,AuditPersonList auditPersonList,String index,String collectId,String auditNature,String turns){
		List<AuditPerson> auditPersons = auditPersonList.getAuditPersons();
		String str = "无";
		int ind=Integer.parseInt(index);
		Map<String,Object> map = new HashMap<String,Object>();
		if(auditNature==null||auditNature.equals("")){
			str = "error";
			map.put("auditNatureErr", "审核人员性质不能为空");
		}
		for (AuditPerson auditPerson : auditPersons) {
			
			if(auditPerson.getName()==null||auditPerson.getName().trim().equals("")){
				str = "error";
				map.put("name"+ind, "姓名不能为空");
			}
			if(auditPerson.getMobile()==null||auditPerson.getMobile().trim().equals("")){
				str = "error";
				map.put("phone"+ind, "电话不能为空");
			}
			if(auditPerson.getDuty()==null||auditPerson.getDuty().trim().equals("")){
				str = "error";
				map.put("duty"+ind, "职务不能为空");
			}
			if(auditPerson.getUnitName()==null||auditPerson.getUnitName().trim().equals("")){
				str = "error";
				map.put("unitName"+ind, "单位名称不能为空");
			}
			ind++;
		}
		if(str.equals("error")){
			map.put("isErr", str);
			map.put("length",auditPersons.size());
			return JSONSerializer.toJSON(map).toString();
		}else{
			for (AuditPerson auditPerson2 : auditPersons) {
				auditPerson2.setCollectId(collectId);
				auditPerson2.setAuditRound(turns);
				auditPerson2.setType(3);
				auditPerson2.setCreateDate(new Date());
				auditPersonService.add(auditPerson2);
				map.put("staff", auditPerson2.getAuditStaff());
			}
			map.put("isErr", str);
			map.put("status", 1);
			return JSON.toJSONString(map);
		}
//		if(auditPerson.getAuditStaff()==null||auditPerson.getAuditStaff().trim().equals("")){
//			str = "error";
//			map.put("auditStaff", "审核人员性质不能为空");
//		}
		
	}
	
	   @RequestMapping(value="/delete")
	   @ResponseBody
	public String delete(String id){
	    String[] ids = id.split(",");
	    for(String i:ids){
	        auditPersonMapper.deleteByPrimaryKey(i);
	    }
	    return "";
	}
	
	   /**
	    * 
	    * @Title: auditId
	    * @author Liyi 
	    * @date 2016-12-27 下午8:49:30  
	    * @Description:
	    * @param:     
	    * @return:
	    */
	 @RequestMapping("/tempOnly")
		@ResponseBody
		public String auditId(HttpServletRequest request){
		 	String msg = "";
		 	String id = request.getParameter("id");
		 	String type = request.getParameter("type");
		 	AuditPerson person = new AuditPerson();
		 	person.setCollectId(id);
			person.setAuditRound(type);
			List<AuditPerson> listAudit = auditPersonService.query(person, 1);
			if(listAudit.size()>=1){
				msg="1";
			}else {
				msg="0";
			}
			return msg;
		}
	 
	 
	 @InitBinder  
	    public void initBinder(WebDataBinder binder) {  
	        // 设置List的最大长度  
	        binder.setAutoGrowCollectionLimit(30000); 
	        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    } 
	 
	 
	 
	 public  void generateHeader(HSSFWorkbook workbook,HSSFSheet sheet,CollectPlan plan){

	        HSSFRow row = sheet.createRow(1);
            HSSFCell cell = row.createCell(0);
            sheet.setColumnWidth(0, 2000); 
	   	    sheet.setColumnWidth(1, 3000); 
	   	    sheet.setColumnWidth(2, 3000);
	   	    sheet.setColumnWidth(3, 3000);
	   	    sheet.setColumnWidth(4, 3200);
	   	     sheet.setColumnWidth(5, 1200);
	   	     sheet.setColumnWidth(6, 2300);
	   	     sheet.setColumnWidth(7, 2300);
	   	     sheet.setColumnWidth(8, 2300);
	   	     sheet.setColumnWidth(9, 2300);
	   	     sheet.setColumnWidth(10, 2500);
	   	     sheet.setColumnWidth(11, 2300);
	   	     sheet.setColumnWidth(12, 3000);
	   	     sheet.setColumnWidth(13, 3000);
	   	     
	   	    HSSFCellStyle style = workbook.createCellStyle();
	   	    HSSFFont font = workbook.createFont();  
	   	 
	     	style.setBorderBottom(HSSFCellStyle.BORDER_HAIR);
		    style.setBorderLeft(HSSFCellStyle.BORDER_HAIR);
		    style.setBorderTop(HSSFCellStyle.BORDER_HAIR);
		    style.setBorderRight(HSSFCellStyle.BORDER_HAIR);
		    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    font.setFontHeightInPoints((short) 9);
		    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setFont(font);
	        cell.setCellStyle(style);
			cell.setCellValue("序号");
	        cell = row.createCell(1); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        cell.setCellStyle(style);
	        cell.setCellValue("需求部门");
	        cell = row.createCell(2);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("物资类别及名称");
	        cell = row.createCell(3); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("规格型号");
	        cell = row.createCell(4); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("质量技术标准");
	        cell = row.createCell(5);  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("计量单位"); 
	        cell = row.createCell(6);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("采购数量");  
	        
	        cell = row.createCell(7); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("单价（元）");  
	        
	        cell = row.createCell(8); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("预算金额（万元）");  
	        
	        cell = row.createCell(9); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("交货期限");  
	        
	        cell = row.createCell(10);
	        cell.setCellStyle(style);
	        cell.setCellValue("采购方式");  
	        
	        cell = row.createCell(11);
	        cell.setCellStyle(style);
	        cell.setCellValue("采购机构"); 
	        
	        cell = row.createCell(12);
	        cell.setCellStyle(style);
	        cell.setCellValue("供应商名称");  
	        
	        
	        cell = row.createCell(13);
	        cell.setCellStyle(style);
	        cell.setCellValue("备注");

//	        if(plan.getAuditTurn()!=null){
////	        	 if(plan.getStatus()==3||plan.getStatus()==5||plan.getStatus()==7||plan.getStatus()==12||plan.getStatus()==2){
//		        	 cell = row.createCell(14);  
//		 	        cell.setCellValue("一轮审核建议");  
////		        }
//		 	        
//		 	       if((plan.getAuditTurn()==2||plan.getAuditTurn()==3)&&(plan.getStatus()==12||plan.getStatus()==2)){
//				        	 cell = row.createCell(15);  
//				 	        cell.setCellValue("二轮轮审核建议");  
//			        }
//		 	       if(plan.getAuditTurn()==3&&(plan.getStatus()==12||plan.getStatus()==2)){
//			        	cell = row.createCell(16);  
//			 	        cell.setCellValue("三轮审核建议");  
//			        }
//	        }
	        
	        row = sheet.createRow(2);
	        cell = row.createCell(3); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        cell.setCellValue("合计");
	        
	        cell = row.createCell(8); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        cell.setCellStyle(style);
	        DecimalFormat df = new DecimalFormat("#,###.00");
	        cell.setCellValue(df.format(plan.getBudget()));  
		 }   
	 
	 
	 
	 
	 public  void generateName(HSSFWorkbook workbook,HSSFSheet sheet,CollectPlan plan){
		//表头第一行
	     HSSFRow row = sheet.createRow(0);  
			//
	     HSSFCell  cell = row.createCell(0);
	     HSSFCellStyle style = workbook.createCellStyle();
	   	 HSSFFont font = workbook.createFont(); 
	   	 font.setFontHeightInPoints((short) 22);
	   	 style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	     style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	     style.setFont(font);
	     cell.setCellStyle(style);
	     row.setHeight((short) 800);
	     cell.setCellValue(plan.getFileName());
	     sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)13));
	     
	 }
	 
	 
	 /**
		 * 
		* @Title: isContainChinese
		* @Description: 判断是否是含有中文 
		* author: Li Xiaoxiao 
		* @param @param str
		* @param @return     
		* @return boolean     
		* @throws
		 */
		public boolean isContainChinese(String str){
			boolean bool=true;
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(str);
		    if(m.find()==true&&str.contains("（")){
		        	bool=true;
		     }else{
		        	bool=false;
		    }
			return bool;
		}
		
}
