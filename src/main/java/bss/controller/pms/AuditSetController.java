package bss.controller.pms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.formbean.AuditParamBean;
import bss.formbean.FiledNameEnum;
import bss.model.pms.AuditParam;
import bss.model.pms.AuditPerson;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseAudit;
import bss.model.pms.PurchaseRequired;
import bss.model.pms.UpdateFiled;
import bss.service.pms.AuditParameService;
import bss.service.pms.AuditPersonService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseAuditService;
import bss.service.pms.UpdateFiledService;

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
	public String set(Model model,Integer page,String id){
		List<FiledNameEnum> list = updateFiledService.getAllFiled();
		List<UpdateFiled> list2 = updateFiledService.query(id,null);
		List<UpdateFiled> listy=new LinkedList<UpdateFiled>();
		List<UpdateFiled> listn=new LinkedList<UpdateFiled>();
		if(list2!=null&&list2.size()>0){
			for(UpdateFiled u:list2){
				if(u.getIsUpdate()==1){
					listy.add(u);
				}else{
					listn.add(u);
				}
			}
		}else{
			model.addAttribute("listf", list);
		}
		List<AuditPerson> listAudit = auditPersonService.query(new AuditPerson(), page==null?1:page);
		PageInfo<AuditPerson> info = new PageInfo<>(listAudit);
		model.addAttribute("info", info);
		model.addAttribute("listy", listy);
		model.addAttribute("listn", listn);
		model.addAttribute("id", id);
		
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
	public String save(String val1, String val2,String collectId,String fname2,String fname){
		String[] field1 = val1.trim().split(",");
		List<String> list=new LinkedList<String>();
		
		for(int i=0;i<field1.length;i++){
			if(field1[i].trim().length()!=0){
				list.add(field1[i]);
			}
			
		}
		List<String> list2=new LinkedList<String>();
		String[] field2 = val2.trim().split(",");
		for(int i=0;i<field2.length;i++){
			if(field2[i].trim().length()!=0){
				list2.add(field2[i]);
			}
		}
		List<UpdateFiled> filedList=new ArrayList<UpdateFiled>();
		List<UpdateFiled> filedList2=new ArrayList<UpdateFiled>();
		if(list.size()>0){
			filedList = updateFiledService.query(collectId, list);
		}
		 if(list2.size()>0){
			 updateFiledService.query(collectId, list2); 
		 }
		 
		if((filedList!=null&&filedList.size()>0)||(filedList2!=null&&filedList2.size()>0)){
			
			updateFiledService.update(1, list,collectId);
			updateFiledService.update(2, list2,collectId);
			
		}else{
			UpdateFiled u=new UpdateFiled();
			if(list!=null&&list.size()>0){
				String[] str = fname.split(",");
				for(int i=0;i<str.length;i++){
					
				
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					u.setId(id);
					u.setCollectId(collectId);
					u.setFiled(field1[i]);
					u.setFiledName(str[i]);
//					u.setFiledName("");
					u.setIsUpdate(1);
					updateFiledService.add(u);
				}
			}
		if(list2!=null&&list2.size()>0){
			String[] str = fname2.split(",");
			for(int i=0;i<str.length;i++){
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				u.setId(id);
				u.setCollectId(collectId);
				u.setFiled(field2[i]);
				u.setFiledName(str[i]);
				u.setIsUpdate(2);
				updateFiledService.add(u);
			}
		}
			
		}
		
		
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
	public String getExpert(Integer page,Expert expert,Model model){
		List<Expert> list = expertService.selectAllExpert(page==null?1:page, expert);
		PageInfo<Expert> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("expert", expert);
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
	public String getUser(Integer page,User user,Model model){
		List<User> list = userServiceI.selectUser(user, page==null?1:page);
		PageInfo<User> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("user", user);
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
	@RequestMapping("/add")
	@ResponseBody
	public String add(AuditPerson auditPerson,String id){
		 if(auditPerson.getType()==1){
			 Expert expert = expertService.selectByPrimaryKey(id);
			 auditPerson.setName(expert.getRelName());
			 auditPerson.setMobile(expert.getMobile());
			 auditPerson.setIdNumber(expert.getIdNumber());
			 auditPersonService.add(auditPerson);
			 return "";
		 }else if(auditPerson.getType()==2){
			 User user = userServiceI.getUserById(id);
			 auditPerson.setName(user.getRelName());
			 auditPerson.setMobile(user.getMobile());
			 auditPersonService.add(auditPerson);
			 return "";
		 }else{
			 auditPersonService.add(auditPerson);
			 return "";
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
	public void excel(HttpServletRequest request,HttpServletResponse response,CollectPlan collectPlan) throws UnsupportedEncodingException{
//		CollectPlan plan = collectPlanService.queryById(collectPlan.getId());
//		collectPlan.setPlanNo("001");
		
		List<String> no = collectPurchaseService.getNo(collectPlan.getId());
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		if(no!=null&&no.size()>0){
//			String[] str = plan.getPlanNo().split(",");
			for(String s:no){
				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
				list.addAll(pur);
			}
			
		}
		
		
		//查询出所有审核参数
		DictionaryData	dictionaryData=new DictionaryData();
//		DictionaryData dd=new DictionaryData();
//		dd.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");
//		dictionaryData.setParent(dd);
		List<DictionaryData> dic = dictionaryDataServiceI.queryAudit(dictionaryData);
		List<AuditParam> all=new LinkedList<AuditParam>();
		AuditParam auditParam=new AuditParam();
		
		List<AuditParamBean> bean=new LinkedList<AuditParamBean>();
		if(dic!=null&&dic.size()>0){
			for(DictionaryData d:dic){
				AuditParamBean s=new AuditParamBean();
				auditParam.setDictioanryId(d.getId());
				List<AuditParam> a = auditParameService.query(auditParam, 1);
				all.addAll(a);
				s.setId(d.getId());
				s.setSize(a.size());
				s.setName(d.getName());
				bean.add(s);
			}
		}
		
		
		String filedisplay = "明细.xls";
		response.addHeader("Content-Disposition", "attachment;filename="  + new String(filedisplay.getBytes("gb2312"), "iso8859-1"));
		HSSFWorkbook workbook = new HSSFWorkbook();
	     HSSFSheet sheet = workbook.createSheet("1"); 
	     HSSFCellStyle style = workbook.createCellStyle();
	     style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
	     
	     //表头第一行
	     HSSFRow row = sheet.createRow((int) 0);  
			//
	     HSSFCell  cell = row.createCell(0);
	     cell.setCellValue("测试采购计划-20160926-物资计划草案");
	     cell.setCellStyle(style);
	     sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)12));
	     int n=12;
	     int hb=0;
	     for(AuditParamBean ap:bean){
	    	 hb=n+1;
	    	 cell = row.createCell(hb);
		     
		     cell.setCellValue(ap.getName());
		     n=ap.getSize()+n;
		     sheet.addMergedRegion(new CellRangeAddress(0,(short)0,hb,(short)n)); 
		    
	     }
	    
	     /*	  cell = row.createCell(16);  
     		cell.setCellValue("第二轮审核");
	     sheet.addMergedRegion(new CellRangeAddress(0,(short)0,16,(short)17));*/
	        row = sheet.createRow((int) 1);
	        cell = row.createCell(0);
			cell.setCellValue("序号"); 
	        cell = row.createCell(1);  
	        cell.setCellValue("需求部门");
	        cell = row.createCell( 2);  
	        cell.setCellValue("物资名称");
	        cell = row.createCell(  3);  
	        cell.setCellValue("规格型号");
	        cell = row.createCell( 4);  
	        cell.setCellValue("质量技术标准");
	        cell = row.createCell(  5);  
	        cell.setCellValue("计量单位"); 
	        cell = row.createCell(  6);  
	        cell.setCellValue("采购数量");  
	        
	        cell = row.createCell( 7);  
	        cell.setCellValue("单价（元）");  
	        
	        cell = row.createCell(  8);  
	        cell.setCellValue("预算金额（万元）");  
	        
	        cell = row.createCell( 9);  
	        cell.setCellValue("交货期限");  
	        
	        cell = row.createCell( 10);  
	        cell.setCellValue("采购方式建议");  
	        
	        
	        cell = row.createCell( 11);  
	        cell.setCellValue("供应商");  
	        
	        
	        cell = row.createCell( 12);  
	        cell.setCellValue("备注");  
	        
	        
	        cell = row.createCell(13);  
	        cell.setCellValue("采购方式");  
	        cell = row.createCell(14);  
	        cell.setCellValue("采购机构"); 
	        cell = row.createCell(15);  
	        cell.setCellValue("其他建议"); 
	        cell = row.createCell(16);  
	        cell.setCellValue("技术参意见"); 
	        cell = row.createCell(17);  
	        cell.setCellValue("其他建议"); 
	        
	        int count=2;
	        PurchaseAudit purchaseAudit=new PurchaseAudit();
			for(PurchaseRequired p:list){
	        	row = sheet.createRow(count);
	   	        cell = row.createCell(0);
	   			cell.setCellValue(p.getSeq()); 
	   	        cell = row.createCell(1);  
	   	        cell.setCellValue(p.getDepartment());
	   	        cell = row.createCell( 2);  
	   	        cell.setCellValue(p.getGoodsName());
	   	        cell = row.createCell(  3);  
	   	        cell.setCellValue(p.getStand());
	   	        cell = row.createCell( 4);  
	   	        cell.setCellValue(p.getQualitStand());
	   	        cell = row.createCell(  5);  
	   	        cell.setCellValue(p.getItem()); 
	   	        cell = row.createCell(  6); 
	   	        if(p.getPurchaseCount()!=null){
	   	        	double d=p.getPurchaseCount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	   	         cell.setCellValue(d);  
	   	        }
	   	       
	   	        
	   	        cell = row.createCell( 7); 
	   	        if(p.getPrice()!=null){
		   	        double price = p.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		   	        cell.setCellValue(price);
	   	        }
	   	     
	   	          
	   	        
	   	        cell = row.createCell(8);  
	   	        if(p.getBudget()!=null){
	   	         double budget = p.getBudget().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		   	        cell.setCellValue(budget); 
	   	        }
	   	      
	   	        
	   	        cell = row.createCell( 9);  
	   	        cell.setCellValue(p.getDeliverDate());  
	   	        
	   	        cell = row.createCell( 10);  
	   	        cell.setCellValue(p.getPurchaseType());  
	   	        
	   	        
	   	        cell = row.createCell( 11);  
	   	        cell.setCellValue(p.getSupplier());  
	   	        
	   	        
	   	        cell = row.createCell( 12);  
	   	        cell.setCellValue(p.getMemo());  
	   	        int an=13;
	   	        for(AuditParam ap:all){
	   	        	
	   	        	cell = row.createCell(an);  
	   	        	purchaseAudit.setAuditParamId(ap.getId());
	   	        	
	   	        	purchaseAudit.setPurchaseId(p.getId());
	   	        	PurchaseAudit audit = purchaseAuditService.query(purchaseAudit);
	   	        	if(audit!=null){
	   	        		cell.setCellValue(audit.getParamValue()); 
	   	        	}
		   	         
		   	        an++;
	   	        }
	   	        
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
			
			
	}	
}
