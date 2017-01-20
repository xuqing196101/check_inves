package bss.controller.pms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
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
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.AuditParameService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseAuditService;
import bss.service.pms.PurchaseDetailService;
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
		
		collectPlan.setUserId(user.getId());
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
 
        
    	List<PurchaseDetail> listp = purchaseDetailService.getUnique(id);
		
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
		
	 List<PurchaseDetail> listp = purchaseDetailService.getUnique(id);
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
		
		
		
		List<PurchaseDetail> list =purchaseDetailService.getUnique(id);
		
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
	public String auditlook(String id,Model model,HttpServletRequest request){
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
		  List<PurchaseDep> org = purchaseOrgnizationServiceI.findPurchaseDepList(map);
		
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
		
		List<PurchaseDetail> list = purchaseDetailService.getUnique(id);
		
		model.addAttribute("list", list);
		model.addAttribute("org",org);
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
	public String audit(PurchaseRequiredFormBean list,CollectPlan collectPlan){
		if(list!=null){
			if(list.getListDetail()!=null&&list.getListDetail().size()>0){
				for(PurchaseDetail p:list.getListDetail()){
					if(p.getId()!=null){
						purchaseDetailService.updateByPrimaryKeySelective(p);
					}
				}
			}
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
		CollectPlan plan = collectPlanService.queryById(id);
		model.addAttribute("plan", plan);
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
	
	
}
