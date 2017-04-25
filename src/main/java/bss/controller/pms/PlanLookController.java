package bss.controller.pms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.maven.model.Organization;
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
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.dao.pms.CollectPlanMapper;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.formbean.AuditParamBean;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.AuditParam;
import bss.model.pms.AuditPerson;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseManagement;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.AuditParameService;
import bss.service.pms.AuditPersonService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseAuditService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseManagementService;
import bss.service.pms.PurchaseRequiredService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
/**
 * 
 * @Title: PlanLookController
 * @Description: 计划信息查看 
 * @author Li Xiaoxiao
 * @date  2016年9月26日,上午10:17:17
 *
 */
@Controller
@RequestMapping("/look")
public class PlanLookController extends BaseController {
	
	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;
	
//	@Autowired
//	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	
	@Autowired
	private CollectPurchaseService collectPurchaseService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private AuditParameService auditParameService; 
	
	@Autowired
	private PurchaseAuditService purchaseAuditService;
	
//	@Autowired
//	private OrgnizationMapper oargnizationMapper;
	
	@Autowired
	private PurchaseRequiredMapper putchaseRequiredMapper;
	
	@Autowired
	private CollectPlanMapper collectPlanMapper;
	
	@Autowired
    private CollectPurchaseService conllectPurchaseService;
	
	
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
	
	
	@Autowired
    private PurchaseDetailService purchaseDetailService;
	  
	@Autowired
	private UserServiceI userService;
	  
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private PurchaseManagementService purchaseManagementService;
	
	@Autowired
	private AuditPersonService auditPersonService;
	/**
	 * 
	 * 
	* @Title: list
	* @Description: 采购计划信息查看 
	* author: Li Xiaoxiao 
	* @param @param collectPlan
	* @param @param page
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User user,CollectPlan collectPlan,Integer page,Model model){
	
		if(collectPlan.getStatus()==null){
			collectPlan.setStatus(1);
		}	else if(collectPlan.getStatus()==0){
			collectPlan.setSign("0");
			collectPlan.setStatus(null);
		}else if(collectPlan.getStatus()==8){
			collectPlan.setSign("8");
			collectPlan.setStatus(null);
		} else if(collectPlan.getStatus()==12){
			collectPlan.setSign("12");
			collectPlan.setStatus(null);
		}   
		
		
		
		List<Role> roles = user.getRoles();
		boolean bool=false;
		if(roles!=null&&roles.size()>0){
			for(Role r:roles){
				if(r.getCode().equals("MANAGE_M")){
					bool=true;
				}
			}
		}
		if(bool!=true){
			collectPlan.setUserId(user.getId());
		} 
		
		
		List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
		PageInfo<CollectPlan> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", collectPlan);
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
		model.addAttribute("dic", dic);
		return "bss/pms/collect/planlist";
	}
	
	
	@RequestMapping("/view1")
    public String view1(String id, Model model,HttpServletRequest request){
//        List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
        List<String> list = conllectPurchaseService.getNo(id);
 
        
    	List<PurchaseDetail> listp = purchaseDetailService.getUnique(id,null,null);
		
		model.addAttribute("list", listp);
		
		
		HashMap<String,Object> maps=new HashMap<String,Object>();
		maps.put("typeName", 1);
	     List<PurchaseDep> orga = purchaseOrgnizationServiceI.findPurchaseDepList(maps);
		
		
	      model.addAttribute("orga", orga);	
	      
	      
		
//        Set<String> set = new HashSet<String>();
//        //List<PurchaseRequired> lista=new LinkedList<PurchaseRequired>();
//        List<String> lists = conllectPurchaseService.getNo(id);
//        if(lists != null && lists.size() > 0){
//            for(String s:list){
//                Map<String,Object> map=new HashMap<String,Object>();
//                map.put("planNo", s);
//                List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
//                for (PurchaseRequired purchaseRequired : list2) {
//                    String org = purchaseRequired.getDepartment();
//                    set.add(org);
//                }
//            }
//        }
//            model.addAttribute("set", set);
        
			List<PurchaseDetail> detail = purchaseDetailService.groupDetail(id);
			for(PurchaseDetail pr:detail){
				User user = userService.getUserById(pr.getUserId());
				Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(user.getOrg().getId());
				pr.setDepartment(orgnization.getShortName());
			}
			
			   model.addAttribute("detail", detail);
            request.getSession().removeAttribute("NoCount");
        	model.addAttribute("kind", DictionaryDataUtil.find(5));
        	String typeId = DictionaryDataUtil.getId("PURCHASE_FILE");
    		model.addAttribute("typeId", typeId);
        return "bss/pms/collect/plan_views";
    }
	
	/**
	 * 
	* @Title: views
	* @Description: 按照需求部门查看
	* author: Li Xiaoxiao 
	* @param @param org
	* @param @param planNo
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/views")
    public String views(String id, String planNo, Model model){
       HashMap<String, Object> map = new HashMap<>();
       map.put("id", id);
//       map.put("planNo", planNo);
//       List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
//       model.addAttribute("list", list);
		List<PurchaseDetail> list = purchaseDetailService.selectByParentId(map);
		model.addAttribute("list", list);
		String typeId = DictionaryDataUtil.getId("PURCHASE_FILE");
		model.addAttribute("typeId", typeId);
		HashMap<String,Object> maps=new HashMap<String,Object>();
		maps.put("typeName", 1);
	     List<PurchaseDep> orga = purchaseOrgnizationServiceI.findPurchaseDepList(maps);
		
	     model.addAttribute("kind", DictionaryDataUtil.find(5));
	      model.addAttribute("org", orga);	
	      
        return "bss/pms/collect/plan_view";
    }
	
	
	//下达查看页面
	@RequestMapping("/view")
    public String view(String id, Model model,HttpServletRequest request){
//	    List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
	   //采购 
	    HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
	 List<PurchaseDep> org = purchaseOrgnizationServiceI.findPurchaseDepList(map);
		
	 List<PurchaseDetail> listp = purchaseDetailService.getUnique(id,null,null);
//        List<String> list = conllectPurchaseService.getNo(id);
//        if(list != null && list.size() > 0){
//            for(String s:list){
//                Map<String,Object> map=new HashMap<String,Object>();
//                map.put("planNo", s);
//                List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
//                listp.addAll(list2);
//            }
//        }
		model.addAttribute("kind", DictionaryDataUtil.find(5));
		
		model.addAttribute("org", org);
        model.addAttribute("list", listp);
        return "bss/pms/collect/plan_view";
    }
	
	
	/**
	 * 
	* @Title: print
	* @Description: 打印
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/print")
	public String print(String id,Model model,HttpServletRequest request){
		CollectPlan plan = collectPlanService.queryById(id);
//		List<String> no = collectPurchaseService.getNo(id);
//		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
//		if(no!=null&&no.size()>0){
//			for(String s:no){
//				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
//				list.addAll(pur);
//			}
//		}
//		model.addAttribute("list", list);
		
		
		
		List<PurchaseDetail> list =purchaseDetailService.getUnique(id,null,null);
		
		model.addAttribute("list", list);
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
	   List<PurchaseDep> org = purchaseOrgnizationServiceI.findPurchaseDepList(map);
		
		
	      model.addAttribute("org", org);	
         model.addAttribute("kind", DictionaryDataUtil.find(5));	
         model.addAttribute("auditTurn", plan.getAuditTurn());	
		return "bss/pms/collect/print";
	}
	
	/**
	 * 
	* @Title: queryOne
	* @Description: 审核页面 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/auditlook")
	public String auditlook(@CurrentUser User users,String id,Model model,HttpServletRequest request){
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
		  List<PurchaseDep> org = purchaseOrgnizationServiceI.findPurchaseDepList(map);
		
		  List<PurchaseDetail> listO = purchaseDetailService.getUnique(id,null,null);
		  PurchaseRequired required = purchaseRequiredService.queryById(listO.get(0).getId());

		   List<PurchaseManagement> pm = purchaseManagementService.queryByPid(required.getUniqueId());
		   String mid="";
			if(pm!=null&&pm.size()>0){
				mid=pm.get(0).getManagementId();
			}
			List<PurchaseOrg> manages = purchaseOrgnizationServiceI.get(mid);
			
			
		   List<PurchaseDep> orgs=new LinkedList<PurchaseDep>();
			for(PurchaseOrg m:manages){
				for(PurchaseDep pd:org){
					if(m.getPurchaseDepId().equals(pd.getOrgId())){
						orgs.add(pd);
					}
				}
			}
			
//		List<String> no = collectPurchaseService.getNo(id);
		
//		List<String> depList = collectPurchaseService.queryDepartMent(no);
	
//		List<PurchaseRequired> list = new LinkedList<PurchaseRequired>();
//		PurchaseRequired p=new PurchaseRequired();
//		if(no!=null&&no.size()>0){
//			for(String s:no){
//				p.setUniqueId(s);
//				p.setIsMaster(1);
//				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByUinuqe(p);
//				list.addAll(pur);
//			}
//		}
		
	/*	for(int i=0;i<list.size();i++){
			if(i<list.size()-1){
				if(list.get(i).getDepartment().equals(p)){
					pr.setSeq("");
				}
			}
			
		}*/
		
		List<PurchaseDetail> list = purchaseDetailService.getUnique(id,null,null);
		
		model.addAttribute("list", list);
		model.addAttribute("org",orgs);
		model.addAttribute("id", id);
		
		List<DictionaryData> mType = dictionaryDataServiceI.findByKind("5");
		model.addAttribute("mType", mType);
		
//		Map<String,Object> maps=new HashMap<String,Object>();
//		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(maps);
//		model.addAttribute("requires", requires);
		
		CollectPlan collectPlan = collectPlanService.queryById(id);
		if(collectPlan.getAuditTurn()!=null){
			model.addAttribute("audit", collectPlan.getAuditTurn());	
		}
		List<PurchaseDetail> detail = purchaseDetailService.groupDetail(id);
		for(PurchaseDetail pr:detail){
			User user = userService.getUserById(pr.getUserId());
			Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(user.getOrg().getId());
			pr.setDepartment(orgnization.getShortName());
		}
		
		   model.addAttribute("detail", detail);
		
		model.addAttribute("status", collectPlan.getStatus());
//		model.addAttribute("depList", depList);
		return "bss/pms/collect/audit";
	}
	
	/**
	 * 
	* @Title: audit
	* @Description: 审核
	* author: Li Xiaoxiao 
	* @param @param list
	* @param @param id
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/audit")
	public String audit(PurchaseRequiredFormBean list,CollectPlan collectPlan,@CurrentUser User user){
		if(list!=null){
			if(list.getListDetail()!=null&&list.getListDetail().size()>0){
				for(PurchaseDetail p:list.getListDetail()){
					if(p.getId()!=null){
						purchaseDetailService.updateByPrimaryKeySelective(p);
					}
				}
			}
		}
		List<AuditPerson> person = auditPersonService.queryByUserIdAndCid(user.getId(), collectPlan.getId());
		if(person!=null&&person.size()>0){
			AuditPerson auditPerson = person.get(0);
			auditPerson.setCreateDate(new Date());
			auditPersonService.updateByPrimaryKeySelective(auditPerson);
		}
		
//		Map<String,Object> map=new HashMap<String,Object>();
//		if(list!=null){
//			if(list.getList()!=null){
//				for(PurchaseRequired p:list.getList()){
//					if(p.getId()!=null){
//						map.put("status", "6");
//						map.put("id", p.getId());	
//						purchaseRequiredService.update(map);	
//					}
//				}
//			}
//		}
//		if(list!=null){
//			if(list.getAudit()!=null&&list.getAudit().size()>0){
//				for(PurchaseAudit a:list.getAudit()){
//					purchaseAuditService.add(a);
//				}
//			}
//		}
		if(collectPlan.getStatus().equals(3)){
			if(collectPlan.getAuditTurn()==1){
				collectPlan.setStatus(12);
			}else{
				collectPlan.setStatus(4);
			}
		}
		if(collectPlan.getStatus().equals(5)){
			if(collectPlan.getAuditTurn()==2){
				collectPlan.setStatus(12);
			}else{
				collectPlan.setStatus(6);
			}
		}
		if(collectPlan.getStatus().equals(7)){
			collectPlan.setStatus(12);
		}
		collectPlan.setOrderAt(new Date());
		collectPlanService.update(collectPlan);
		return "redirect:list.html";
	}
	
	/**
	 * 
	* @Title: report
	* @Description:生成评审报告页面 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/report")
	public String report(String id,Model model){
		List<PurchaseDetail> details = purchaseDetailService.getUnique(id,null,null);
//		CollectPlan plan = collectPlanService.queryById(id);
		model.addAttribute("details", details);
		return "bss/pms/collect/pdf";
	}
	
	/**
	 * 
	* @Title: auditId
	* @author ZhaoBo
	* @date 2016-12-19 下午7:38:45  
	* @Description: 判断计划能不能审核 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/auditId")
	@ResponseBody
	public String auditId(HttpServletRequest request){
		String str = null;
		String id = request.getParameter("id");
		CollectPlan plan = collectPlanService.queryById(id);
		if(plan.getStatus()==3||plan.getStatus()==5||plan.getStatus()==7){
			str = "1";
		}else{
			str = "0";
		}
		return str;
	}
	
	/**
	 * 
	 * @Title: auditPersonCheck
	 * @author Liyi 
	 * @date 2016-12-27 下午3:01:09  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/auditPersonCheck")
	@ResponseBody
	public String auditPersonCheck(HttpServletRequest request){
		String str = null;
		String id = request.getParameter("id");
		CollectPlan plan = collectPlanService.queryById(id);
		Integer status = plan.getStatus();
		Integer turn = plan.getAuditTurn();
		if(status==1&&turn!=null){
			str = "2";
		}
		else if (status==1) {
			str = "1";
		}else{		
			if(status==4||status==6){
				str = "2";
			}else{
				str = "3";
			}
		}
		return str;
	}
	
	/**
	 * 
	 * @Title: auditStatus
	 * @author Liyi 
	 * @date 2016-12-27 下午2:31:00  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/auditStatus")
	@ResponseBody
	public String auditStatus(HttpServletRequest request){
		String str = null;
		String id = request.getParameter("id");
		CollectPlan plan = collectPlanService.queryById(id);
		if(plan.getStatus()==1){
			str = "1";
		}else{
			str = "0";
		}
		return str;
	}
	@RequestMapping("/auditByDepartment")
	public String auditByDepartment(String id,Model model,Integer pageNum,String depart){
		
		DictionaryData	dictionaryData=new DictionaryData();
//		DictionaryData p=new DictionaryData();
//		p.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");
//		dictionaryData.setParent(p);
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
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
		
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
		 List<PurchaseDep> org = purchaseOrgnizationServiceI.findPurchaseDepList(map);
		
//		CollectPlan plan = collectPlanService.queryById(id);
		List<String> no = collectPurchaseService.getNo(id);
		List<PurchaseRequired> list = new LinkedList<PurchaseRequired>();
		List<String> departMent = new ArrayList<>();
		if(no!=null&&no.size()>0){
			for(String s:no){
				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
				list.addAll(pur);
				Map<String,Object> departMap = new HashMap<String,Object>();
				departMap.put("planNo", s);
				departMent.add(purchaseRequiredService.getByMap(departMap).get(0).getDepartment());
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("org",org);
		model.addAttribute("id", id);
		model.addAttribute("departMent", departMent);
		model.addAttribute("all", all);
		
		model.addAttribute("bean", bean);
		
		DictionaryData dd=new DictionaryData();
		dd.setCode("CGJH_ADJUST");
		String did = dictionaryDataServiceI.find(dd).get(0).getId();
		model.addAttribute("aid", did);
		
		List<DictionaryData> mType = dictionaryDataServiceI.findByKind("5");
		model.addAttribute("mType", mType);
		
//		Map<String,Object> maps=new HashMap<String,Object>();
//		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(maps);
//		model.addAttribute("requires", requires);
		
		return "bss/pms/collect/audit";
	}
	
	@RequestMapping(value="/status")
	@ResponseBody
	public String getSatus(CollectPlan collectPlan,String auditTurns){
	
		CollectPlan plan = collectPlanMapper.queryPlan(collectPlan);
		Integer status = plan.getStatus();
		String flag="1";
		DictionaryData data = DictionaryDataUtil.findById(auditTurns);
		if(status==1){
			flag="0";
		}
//		if(auditTurns.equals("4")){
//				 if(status!=1){
//					 flag="1";
//				 }
//			 }
//		if(data!=null){
//		  if(data.getCode().equals("SH_1")){
//				 if(status!=1){
//					 flag="1";
//				 }
//			 }
//		  if(data.getCode().equals("SH_2")){
//				 if(status!=4&&status!=1){
//					 flag="1";
//				 }
//			 }
//		  if(data.getCode().equals("SH_3")){
//				if(status !=6&&status!=1){
//					flag="1";
//				}
//			}
//		}
//		
		return flag;
	}
	
	@RequestMapping(value="/purchaseType",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getPurchaseTyoe(String oldId,String id){
		Map<String,Object> map=new HashMap<String,Object>();
		DictionaryData data1= DictionaryDataUtil.findById(oldId);
		DictionaryData data2= DictionaryDataUtil.findById(id);
		map.put("old", data1.getName());
		map.put("newVal", data2.getName());
		return JSON.toJSONString(map);
	}
	
	@InitBinder  
    public void initBinder(WebDataBinder binder) {  
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(30000);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    } 
	
	/**
	 * 
	* @Title: print
	* @Description:  需求部门和采购机构 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/organddep")
	public String print(String uniqueId,Model model,Integer page){
		List<String> department = purchaseDetailService.queryDepartment(uniqueId,page==null?1:page);
		PageInfo<String> info = new PageInfo<String>(department);
		List<String> orgs = purchaseDetailService.queryOrg(uniqueId);
		List<Orgnization> list=new ArrayList<Orgnization>();
		for(String org:orgs){
			Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(org);
			list.add(orgnization);
		}
		model.addAttribute("info", info);
		model.addAttribute("list", list);
		model.addAttribute("uniqueId", uniqueId);
		return "bss/pms/collect/groupdown";
	}
	
    @RequestMapping("/excel")
	@ResponseBody
	public String excel(HttpServletRequest request,HttpServletResponse response,String uniqueId,String flag,String org,String dep) throws UnsupportedEncodingException{
    	List<PurchaseDetail> list = purchaseDetailService.getUnique(uniqueId,org,dep);
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
			for(PurchaseDetail p:list){
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
	   	        	// d=p.getPurchaseCount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
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
	        cell.setCellValue("采购机构"); 
	        
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
