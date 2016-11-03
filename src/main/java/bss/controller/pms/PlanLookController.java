package bss.controller.pms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import bss.controller.base.BaseController;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.formbean.AuditParamBean;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.AuditParam;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseAudit;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.AuditParameService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseAuditService;
import bss.service.pms.PurchaseRequiredService;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
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
		List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
		PageInfo<CollectPlan> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", collectPlan);
		DictionaryData	dictionaryData=new DictionaryData();
		DictionaryData p=new DictionaryData();
		p.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");
		dictionaryData.setParent(p);
		List<DictionaryData> dic = dictionaryDataServiceI.find(dictionaryData);
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
				DictionaryData dd=new DictionaryData();
				dd.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");
				dictionaryData.setParent(dd);
				List<DictionaryData> dic = dictionaryDataServiceI.find(dictionaryData);
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
		DictionaryData p=new DictionaryData();
		p.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");
		dictionaryData.setParent(p);
		List<DictionaryData> dic = dictionaryDataServiceI.find(dictionaryData);
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
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		if(no!=null&&no.size()>0){
			for(String s:no){
				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
				list.addAll(pur);
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("org",org);
		model.addAttribute("id", id);
		
		model.addAttribute("all", all);
		
		model.addAttribute("bean", bean);
		
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
		collectPlan.setStatus(3);
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
}
