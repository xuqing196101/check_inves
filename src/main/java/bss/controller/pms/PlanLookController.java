package bss.controller.pms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.dao.pms.CollectPlanMapper;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.formbean.AuditParamBean;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.AuditParam;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseAudit;
import bss.model.pms.PurchaseRequired;
import bss.model.pqims.PqInfo;
import bss.service.pms.AuditParameService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseAuditService;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageInfo;
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
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
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
	
	@Autowired
	private OrgnizationMapper oargnizationMapper;
	
	@Autowired
	private PurchaseRequiredMapper putchaseRequiredMapper;
	
	@Autowired
	private CollectPlanMapper collectPlanMapper;
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
	public String list(CollectPlan collectPlan,Integer page,Model model){
		collectPlan.setStatus(1);
		List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
		PageInfo<CollectPlan> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", collectPlan);
		DictionaryData	dictionaryData=new DictionaryData();
//		DictionaryData p=new DictionaryData();
//		p.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");
//		dictionaryData.setParent(p);
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
		model.addAttribute("dic", dic);
		return "bss/pms/collect/planlist";
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
	public String print(String id,Model model){
		List<String> no = collectPurchaseService.getNo(id);
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		if(no!=null&&no.size()>0){
			for(String s:no){
				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
				list.addAll(pur);
			}
		}
		model.addAttribute("list", list);
		List<PurchaseAudit> audits=new LinkedList<PurchaseAudit>();
		
		for(PurchaseRequired pr:list){
			List<PurchaseAudit> audit = purchaseAuditService.queryByPid(pr.getId());
			audits.addAll(audit);
			}
		//查询出所有审核参数
				DictionaryData	dictionaryData=new DictionaryData();
		/*		DictionaryData dd=new DictionaryData();
				dd.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");
				dictionaryData.setParent(dd);*/
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
				model.addAttribute("bean", bean);	
				model.addAttribute("all", all);	
				model.addAttribute("audits", audits);
				model.addAttribute("kind", DictionaryDataUtil.find(5));	
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
	public String auditlook(String id,Model model){
		
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
		List<Orgnization> org = orgnizationServiceI.findOrgnizationList(map);
		
//		CollectPlan plan = collectPlanService.queryById(id);
		List<String> no = collectPurchaseService.getNo(id);
		
		List<String> depList = putchaseRequiredMapper.queryDepartMent(no);
	
		List<PurchaseRequired> list = new LinkedList<PurchaseRequired>();
//		List<String> departMent = new ArrayList<>();
		if(no!=null&&no.size()>0){
			for(String s:no){
				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
				list.addAll(pur);
//				Map<String,Object> departMap = new HashMap<String,Object>();
//				departMap.put("planNo", s);
//				departMent.add(purchaseRequiredService.getByMap(departMap).get(0).getDepartment());
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("org",org);
		model.addAttribute("id", id);
//		model.addAttribute("departMent", departMent);
		model.addAttribute("all", all);
		
		model.addAttribute("bean", bean);
		
		DictionaryData dd=new DictionaryData();
		dd.setCode("CGJH_ADJUST");
		String did = dictionaryDataServiceI.find(dd).get(0).getId();
		model.addAttribute("aid", did);
		
		List<DictionaryData> mType = dictionaryDataServiceI.findByKind("5");
		model.addAttribute("mType", mType);
		
		Map<String,Object> maps=new HashMap<String,Object>();
		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(maps);
		model.addAttribute("requires", requires);
		
		CollectPlan collectPlan = collectPlanService.queryById(id);
		if(collectPlan.getAuditTurn()!=null){
			model.addAttribute("audit", collectPlan.getAuditTurn());	
		}
		
		
		model.addAttribute("status", collectPlan.getStatus());
		model.addAttribute("depList", depList);
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
		Map<String,Object> map=new HashMap<String,Object>();
		if(list!=null){
			if(list.getList()!=null){
				for(PurchaseRequired p:list.getList()){
					if(p.getId()!=null){
						map.put("status", "6");
						map.put("id", p.getId());	
						purchaseRequiredService.update(map);	
					}
				}
			}
		}
		if(list!=null){
			if(list.getAudit()!=null&&list.getAudit().size()>0){
				for(PurchaseAudit a:list.getAudit()){
					purchaseAuditService.add(a);
				}
			}
		}
		if(collectPlan.getStatus().equals(3)){
			if(collectPlan.getAuditTurn()==1){
				collectPlan.setStatus(2);
			}else{
				collectPlan.setStatus(4);
			}
		}
		if(collectPlan.getStatus().equals(5)){
			if(collectPlan.getAuditTurn()==2){
				collectPlan.setStatus(2);
			}else{
				collectPlan.setStatus(6);
			}
		}
		if(collectPlan.getStatus().equals(7)){
			collectPlan.setStatus(2);
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
		List<Orgnization> org = orgnizationServiceI.findOrgnizationList(map);
		
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
		
		Map<String,Object> maps=new HashMap<String,Object>();
		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(maps);
		model.addAttribute("requires", requires);
		
		return "bss/pms/collect/audit";
	}
	
	@RequestMapping(value="/status")
	@ResponseBody
	public String getSatus(CollectPlan collectPlan,String auditTurns){
	
		CollectPlan plan = collectPlanMapper.queryPlan(collectPlan);
		Integer status = plan.getStatus();
		String flag="0";
		DictionaryData data = DictionaryDataUtil.findById(auditTurns);
		 if(auditTurns.equals("4")){
				 if(status!=1){
					 flag="1";
				 }
			 }
		if(data!=null){
		  if(data.getCode().equals("SH_1")){
				 if(status!=1){
					 flag="1";
				 }
			 }
		  if(data.getCode().equals("SH_2")){
				 if(status!=4&&status!=1){
					 flag="1";
				 }
			 }
		  if(data.getCode().equals("SH_3")){
				if(status !=6&&status!=1){
					flag="1";
				}
			}
		}
		
		return flag;
	}
	
	
}
