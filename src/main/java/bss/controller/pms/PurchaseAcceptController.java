package bss.controller.pms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.StationMessage;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.StationMessageService;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.AuditPerson;
import bss.model.pms.PurchaseManagement;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.AuditPersonService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseManagementService;
import bss.service.pms.PurchaseRequiredService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.service.UpdateHistoryService;

/**
 * 
 * @Title: PurchaseAcceptController
 * @Description: 采购计划受理管理
 * @author Li Xiaoxiao
 * @date 2016年9月20日,上午10:32:29
 * 
 */
@Controller
@RequestMapping("/accept")
public class PurchaseAcceptController extends BaseController{

	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private StationMessageService stationMessageService;
	
	@Autowired
	private OrgnizationServiceI orgnizationService;
	
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
	
	@Autowired
    private PurchaseDetailService purchaseDetailService;
	
	@Autowired
	private PurchaseManagementService purchaseManagementService;
	
	@Autowired
	private PurchaseOrgnizationServiceI purchserOrgnaztionService;
	
	@Autowired
	private UpdateHistoryService updateHistoryService;
	
	@Autowired
	private AuditPersonService auditPersonService;
	
	/**
	 * 
	 * @Title: queryPlan
	 * @Description: 条件查询分页 author: Li Xiaoxiao
	 * @param @param purchaseRequired
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/list")
	public String queryPlan(@CurrentUser User user,PurchaseRequired purchaseRequired, Integer page, Model model,String status) {
		Map<String,Object> map=new HashMap<String,Object>();
//		if(purchaseRequired.getStatus()==null){
//			map.put("status", "2");
//			
//		} else if(purchaseRequired.getStatus().equals("3")){
//			purchaseRequired.setSign("3");
//			map.put("sign", "3");
//			purchaseRequired.setStatus(null);
//		}
//		else if(purchaseRequired.getStatus().equals("total")){
//			purchaseRequired.setSign("2");
//			map.put("sign", "2");
//			purchaseRequired.setStatus(null);
//		}else if(purchaseRequired.getStatus().equals("4")){
//			map.put("status", status);
//		}
//		
		map.put("isMaster", "1");
		map.put("planName", purchaseRequired.getPlanName());
//		purchaseRequired.setIsMaster(1);
		//所有的需求部门
		if(status==null){
			map.put("status", 2);
			status="2";
		}
		if(status!=null){
//			map.put("status", status);
		}
		
		model.addAttribute("status", status);
		if(status==null||status.equals("2")){
			status="1";
		}
		
		if(status.equals("3")){
//			map.put("sign", 3);
		}
		if(status.equals("4")){
//			map.put("status", status);
			status="-2";
		}
		if(status.equals("total")){
//			map.put("sign", 2);
			status="-1";
		}
		 
		List<PurchaseManagement> list2 = purchaseManagementService.queryByMid(user.getOrg().getId(), page==null?1:page,Integer.valueOf(status));
//		List<PurchaseOrg> list2 = purchaseOrgnizationServiceI.get(user.getOrg().getId());
		/*PageInfo<PurchaseManagement> pm = new PageInfo<>(list2);*/
		List<String> listDep=new ArrayList<String>();
		if(list2!=null&&list2.size()>0){
			for(PurchaseManagement p:list2){
//				Orgnization dep= orgnizationService.getOrgByPrimaryKey(p.getOrgId());
//				if(dep!=null){
					listDep.add(p.getPurchaseId());	
//				}
			}
		}else{
			listDep.add("heheh");
		}
		
//		if(){
//			
//		}
		map.put("list", listDep);
		map.put("page", page==null?1:page);
		List<PurchaseRequired> list = purchaseRequiredService.queryListUniqueId(map);
		for (PurchaseRequired pur : list) {
		    pur.setUserId(userServiceI.getUserById(pur.getUserId()).getRelName());
		}
		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
		/*info.setStartRow(pm.getStartRow());
		info.setEndRow(pm.getEndRow());
		info.setPages(pm.getPages());
		info.setTotal(pm.getTotal());
		info.setFirstPage(pm.getFirstPage());
		info.setPageNum(pm.getPageNum());
		info.setPageSize(pm.getPageSize());*/
		model.addAttribute("info", info);
		/*if(status==null){
			status="2";
		}
		model.addAttribute("status", status);*/
		model.addAttribute("inf", purchaseRequired);
//		Map<String,Object> maps=new HashMap<String,Object>();
//		List<Orgnization> requires = orgnizationService.findOrgPartByParam(maps);
//		model.addAttribute("requires", requires);
	
		return "bss/pms/collect/list";
	}
	
	  /**
     * 
    * @Title: submit
    * @Description:跳转到受理页面
    * author: Li Xiaoxiao 
    * @param @return     
    * @return String     
    * @throws
     */
    @RequestMapping("/submit")
    public String submit(String planNo,Model model){
    	PurchaseRequired p=new PurchaseRequired();
		p.setUniqueId(planNo);
		List<PurchaseRequired> list = purchaseRequiredService.queryUnique(p);
		model.addAttribute("planNo", list.get(0).getUniqueId());
		model.addAttribute("list", list);
		
		List<PurchaseManagement> pm = purchaseManagementService.queryByPid(planNo);
		String mid="";
		if(pm!=null&&pm.size()>0){
			mid=pm.get(0).getManagementId();
		}
		List<PurchaseOrg> manages = purchaseOrgnizationServiceI.get(mid);
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
	    List<PurchaseDep> org = purchaseOrgnizationServiceI.findPurchaseDepList(map);
	    List<PurchaseDep> orgs=new LinkedList<PurchaseDep>();
	    for(PurchaseOrg m:manages){
			for(PurchaseDep pd:org){
				if(m.getPurchaseDepId().equals(pd.getOrgId())){
					orgs.add(pd);
				}
			}
		}
	    
		model.addAttribute("org", orgs);
		model.addAttribute("kind", DictionaryDataUtil.find(5));
		
		Map<String,Object> maps=new HashMap<String,Object>();
		List<Orgnization> requires = orgnizationService.findOrgPartByParam(maps);
		model.addAttribute("requires", requires);
		model.addAttribute("types", DictionaryDataUtil.find(6));
		
		String fileId = list.get(0).getFileId();
		String typeId = DictionaryDataUtil.getId("PURCHASE_DETAIL");
		model.addAttribute("typeId", typeId);
		model.addAttribute("fileId", fileId);
		
		
    	return "bss/pms/collect/view";
    }
	
    /**
     * 
    * @Title: submit
    * @Description: 受理，退回
    * author: Li Xiaoxiao 
    * @param @return     
    * @return String     
    * @throws
     */
    @RequestMapping("/update")
    public String updateSubmit(@CurrentUser User user,PurchaseRequiredFormBean list,String reason,HttpServletRequest request,String status,String history,String planNo){
    	
    	String id="";
//    	User user = (User) request.getSession().getAttribute("loginUser");
    	if(list!=null){
    	  	List<PurchaseRequired> plist = list.getList();
    	  	id=	plist.get(0).getUserId();
    		if(plist!=null&&plist.size()>0){
    			if(reason!=null){
    				
    				purchaseManagementService.updateStatus(planNo,3);
    				for(PurchaseRequired p:plist){
    					p.setReason(reason);
    					p.setStatus(status);
        				purchaseRequiredService.updateByPrimaryKeySelective(p);	
        			}
    			}else{
    				AuditPerson auditPerson=new AuditPerson();
    				auditPerson.setUserId(user.getId());
    				auditPerson.setType(4);
    				auditPerson.setCollectId(planNo);
    				auditPerson.setCreateDate(new Date());
    				auditPerson.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    				auditPersonService.add(auditPerson);
    				purchaseManagementService.updateStatus(planNo,2);
    				for(PurchaseRequired p:plist){
    					p.setStatus(status);
         				purchaseRequiredService.updateByPrimaryKeySelective(p);	
        			}
    			}
    			
    		}
    	}

    	if(history!=null&&history.trim().length()!=0){
    		String[] ids = history.split(",");
    		Set<String> set=new HashSet<String>();
    		for(String i:ids){
    			if(i.trim().length()!=0){
    				set.add(i);
    			}
    		}
    		for(String str:set){
    			PurchaseRequired obj = purchaseRequiredService.queryById(str);
    			updateHistoryService.add(str, obj);//记录历史消息
    		}
    	}
    	
		
		
    	//推送消息
    	if(reason!=null){
    		User  maker = userServiceI.getUserById(id);
    		StationMessage sm =new StationMessage();
			String sid = UUID.randomUUID().toString().replaceAll("-", "");
			sm.setId(sid);
    		sm.setReceiverId(id);
    		sm.setName(maker.getRelName()+"退回");
    		sm.setSenderId(user.getId());
    		stationMessageService.insertStationMessage(sm);
    	}
//    	purchaseRequiredService.updateStatus(p);
    	return "redirect:list.html";
    }
    
    /**
     * 
    * @Title: bar
    * @Description:根据父id查询子id
    * author: Li Xiaoxiao 
    * @param @param purchaseRequired
    * @param @param year
    * @param @return
    * @param @throws UnsupportedEncodingException     
    * @return String     
    * @throws
     */
	@RequestMapping(value="/detail",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bar(String id){
		Map<String,Object> map=new HashMap<String,Object>();
		  map.put("id", id);
          List<PurchaseRequired> list = purchaseRequiredService.selectByParentId(map);
 
		  String json = JSON.toJSONString(list);
		return json;
	}
	
	
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(30000); 
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    } 
    
    
    @RequestMapping("/excel")
	@ResponseBody
	public String excel(HttpServletRequest request,HttpServletResponse response,String uniqueId) throws UnsupportedEncodingException{
		 List<PurchaseRequired> list = purchaseRequiredService.getUnique(uniqueId);
 
		
		String filedisplay = "明细.xls";
		response.addHeader("Content-Disposition", "attachment;filename="  + new String(filedisplay.getBytes("gb2312"), "iso8859-1"));
		HSSFWorkbook workbook = new HSSFWorkbook();
	     HSSFSheet sheet = workbook.createSheet("1"); 
	     HSSFCellStyle style = workbook.createCellStyle();
		 style.setBorderBottom(HSSFCellStyle.BORDER_HAIR);
		 style.setBorderLeft(HSSFCellStyle.BORDER_HAIR);
		 style.setBorderTop(HSSFCellStyle.BORDER_HAIR);
		 style.setBorderRight(HSSFCellStyle.BORDER_HAIR);
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
	     String planName = list.get(0).getPlanName();
	     generateName(workbook,sheet,planName);
 
	     generateHeader(workbook,sheet);
 
	        int count=2;
			for(PurchaseRequired p:list){
	        	row = sheet.createRow(count);
	   	        cell = row.createCell(0);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        cell.setCellStyle(style);
	   			cell.setCellValue(p.getSeq()); 
	   	        cell = row.createCell(1);  
		        style.setWrapText(true);
	   	        style.setAlignment(CellStyle.ALIGN_LEFT);
		        cell.setCellStyle(style);
		        if(p.getPurchaseCount()==null){
		        	cell.setCellValue(p.getDepartment());
		        }
	   	        		
	   	      
	   	        cell = row.createCell(2); 
	   	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getGoodsName());
	   	        cell = row.createCell(3); 
	   	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getStand());
	   	        cell = row.createCell(4);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getQualitStand());
	   	        cell = row.createCell(5);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getItem()); 
	   	        cell = row.createCell(6); 
	   	        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		        cell.setCellStyle(style);
	   	        if(p.getPurchaseCount()!=null){
	   	        //	double d=p.getPurchaseCount().setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
	   	           String of = String.valueOf(p.getPurchaseCount());
	   	         cell.setCellValue(of);  
	   	        }
	   	       
	   	        
	   	        cell = row.createCell(7);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		        cell.setCellStyle(style);
	   	        if(p.getPrice()!=null){
		   	        double price = p.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		   	        cell.setCellValue(price);
	   	        }
	   	     
	   	          
	   	        
	   	        cell = row.createCell(8); 
	   	        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		        cell.setCellStyle(style);
	   	        if(p.getBudget()!=null){
	   	         double budget = p.getBudget().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		   	      
		   	        cell.setCellValue(budget); 
	   	        }
	   	      
	   	        
	   	        cell = row.createCell(9);
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getDeliverDate());  
	   	        
	   	        cell = row.createCell(10);  
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
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	         if(p.getPurchaseCount()!=null){
	   	        	 if(p.getOrganization()!=null){
	   	        		 Orgnization orgnization = purchaseRequiredService.queryPur(p.getOrganization());
	 		   	        if(orgnization!=null){
	 	   	        		cell.setCellValue(orgnization.getName());
	 	   	        	}
	   	        	 }
		   	       
	   	         
	   	        }
	   	         
	   	        
	   	        
	   	        cell = row.createCell(12); 
	   	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getSupplier());  
	   	        
	   	        
	   	        cell = row.createCell(13); 
	   	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style.setWrapText(true);
		        cell.setCellStyle(style);
	   	        cell.setCellValue(p.getMemo());  
	    
 
	   	        
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
    
    
    
	 
	 public  void generateHeader(HSSFWorkbook workbook,HSSFSheet sheet){

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
	        cell.setCellValue("采购机构建议"); 
	        
	        cell = row.createCell(12);
	        cell.setCellStyle(style);
	        cell.setCellValue("供应商名称");  
	        
	        
	        cell = row.createCell(13);
	        cell.setCellStyle(style);
	        cell.setCellValue("备注");
		 }   
	 
	 
	 
	 
	 public  void generateName(HSSFWorkbook workbook,HSSFSheet sheet,String planName){
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
	     cell.setCellValue(planName);
	     sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)13));
	     
	 }
	 
	 
	 
}
