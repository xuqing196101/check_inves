package bss.controller.pms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;
import bss.controller.base.BaseController;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.formbean.AuditParamBean;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.AuditParam;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseAudit;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.pms.UpdateFiled;
import bss.model.ppms.ProjectAttachments;
import bss.model.ppms.Task;
import bss.service.pms.AuditParameService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseAuditService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.pms.UpdateFiledService;
import bss.service.ppms.ProjectAttachmentsService;
import bss.service.ppms.TaskService;
import common.annotation.CurrentUser;
import common.constant.StaticVariables;

import com.ctc.wstx.util.StringUtil;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @Title: TaskAdjustController
 * @Description: 需求计划任务调整 
 * @author Li Xiaoxiao
 * @date  2016年10月8日,上午9:33:12
 *
 */
@Controller
@RequestMapping("/adjust")
public class TaskAdjustController extends BaseController{

	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private CollectPurchaseService collectPurchaseService;
 
	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;
	
	@Autowired
	private UpdateFiledService updateFiledService;
	
	@Autowired
	private OrgnizationServiceI orgnizationService;
	
	@Autowired
	private ProjectAttachmentsService projectAttachmentsService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private AuditParameService auditParameService; 
	
	@Autowired
	private PurchaseAuditService purchaseAuditService;
	
	@Autowired
	private OrgnizationMapper oargnizationMapper;
	
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private PurchaseDetailService purchaseDetailService;
	
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
	
	
	/**
	 * 
	 * 
	* @Title: list
	* @Description: 查询采购计划列表
	* author: Li Xiaoxiao 
	* @param @param model
	* @param @param collectPlan
	* @param @param page
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User user,Model model,Task task,Integer page){
		task.setPurchaseId(user.getOrg().getId());
		task.setTaskNature(0);
		List<Task> list = taskService.listAll(page== null ? 1: page,task);
		PageInfo<Task> info = new PageInfo<Task>(list);
		model.addAttribute("info", info);
        HashMap<String, Object> map = new HashMap<>();
        map.put("typeName", "2");
        List<Orgnization> orgnizations = orgnizationService.findOrgnizationList(map);
        model.addAttribute("list2",orgnizations);
		return "bss/pms/taskadjust/planlist";
		
	}
	/**
	 * 
	* @Title: requiredList
	* @Description: 查看所有需求计划
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/all")
	public String requiredList(String id,Model model){
		Task task = taskService.selectById(id);
		
		
		//所有明细
		List<PurchaseDetail> list = purchaseDetailService.getUnique(task.getCollectId());
		model.addAttribute("list", list);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("typeName", 1);
	    List<PurchaseDep> orgs = purchaseOrgnizationServiceI.findPurchaseDepList(map);
	    model.addAttribute("orgs", orgs);	
	    
	    List<DictionaryData> types = DictionaryDataUtil.find(5);
//	          采购方式 
	    model.addAttribute("types", types);	 
	    String fileId = DictionaryDataUtil.getId("CGJH_ADJUST");
	    model.addAttribute("fileId", fileId);
	    model.addAttribute("id", id);
//		CollectPlan cPlan=collectPlanService.queryById(id);
//		int backInfo=0;
//		if (cPlan.getStatus()!=null && cPlan.getStatus()==4) {
//			backInfo=2;
//			List<CollectPlan> list = collectPlanService.queryCollect(new CollectPlan(), 1);
//			PageInfo<CollectPlan> info = new PageInfo<>(list);
//			model.addAttribute("info", info);
//			model.addAttribute("inf", new CollectPlan());
//			model.addAttribute("backInfo", backInfo);
//			return "bss/pms/taskadjust/planlist";
//		}else{
//			List<PurchaseRequired> purList=new LinkedList<PurchaseRequired>();
//			List<String> list = collectPurchaseService.getNo(id);
//			model.addAttribute("backInfo", backInfo);
//			Map<String,Object> map=new HashMap<String,Object>();
//			for(String str:list){
//				map.put("isMaster", "1");
//				map.put("planNo", str);
//				List<PurchaseRequired> pur = purchaseRequiredService.getByMap(map);
//				 purList.addAll(pur);
//			}
//			model.addAttribute("list", purList);
//			model.addAttribute("id", id);
//			return "bss/pms/taskadjust/edit";
//		}
		return "bss/pms/taskadjust/edit";
	}
	
	/**
	 * 
	* @Title: detail
	* @Description: 根据计划编号查询明细
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/detail")
	public String detail(String planNo,Model model,String id){
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
		List<Orgnization> org = orgnizationService.findOrgnizationList(map);
		
		List<PurchaseRequired> list = purchaseRequiredMapper.queryByNo(planNo);
		
		
		List<PurchaseAudit> audits=new LinkedList<PurchaseAudit>();
		
		for(PurchaseRequired pr:list){
			List<PurchaseAudit> audit = purchaseAuditService.queryByPid(pr.getId());
			audits.addAll(audit);
			}
		//查询出所有审核参数
//				DictionaryData	dictionaryData=new DictionaryData();
//				DictionaryData dd=new DictionaryData();
//				dd.setId("C3013C4B9CFA4645A6D5ACC73D04DACF");

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
				
				
				
		model.addAttribute("list", list);
		model.addAttribute("planNo", planNo);
		
		model.addAttribute("org",org);
		if(list!=null&&list.size()>0){
			model.addAttribute("id", list.get(0).getId());
		}
		DictionaryData dd=new DictionaryData();
		dd.setCode("CGJH_AUDIT");
		String did = dictionaryDataServiceI.find(dd).get(0).getId();
		model.addAttribute("aid", did);
		
		List<DictionaryData> dicType = dictionaryDataServiceI.findByKind("5");
		model.addAttribute("dicType", dicType);
		
		Map<String,Object> maps=new HashMap<String,Object>();
		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(maps);
		model.addAttribute("requires", requires);
		CollectPlan collectPlan = collectPlanService.queryById(id);
//		if(collectPlan.getAuditTurn()!=null){
			model.addAttribute("turns", collectPlan.getAuditTurn());
//		}
		
		return "bss/pms/taskadjust/edit";
	}
	/**
   * 
   * 
  * @Title: planEditList
  * @Description: 采购计划修改列表
  * author: L ChenHao 
  * @param @param model
  * @param @param collectPlan
  * @param @param page
  * @param @return     
  * @return String     
  * @throws
   */
  @RequestMapping("/edit")
  public String planEditList(Model model,CollectPlan collectPlan,Integer page){
    List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
    PageInfo<CollectPlan> info = new PageInfo<>(list);
    List<DictionaryData> types = DictionaryDataUtil.find(5);
    model.addAttribute("types", types);
    model.addAttribute("info", info);
    model.addAttribute("inf", collectPlan);
    return "bss/pms/taskadjust/planeditlist";
    
  }
  
  /**
   * 
  * @Title: planEdit
  * @Description: 根据计划编号查询明细
  * author: L ChenHao 
  * @param @param planNo
  * @param @param model
  * @param @return     
  * @return String     
  * @throws
   */
  @RequestMapping("/pledit")
  public String planEdit(String planNo,Model model,String id) {
		List<PurchaseDetail> list = purchaseDetailService.getUnique(id);
		model.addAttribute("list", list);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("typeName", 1);
	    List<PurchaseDep> orgs = purchaseOrgnizationServiceI.findPurchaseDepList(map);
	    model.addAttribute("orgs", orgs);	
	    
	    List<DictionaryData> types = DictionaryDataUtil.find(5);
//	          采购方式 
	    model.addAttribute("types", types);	 
    
    
    return "bss/pms/taskadjust/updateplan";
  }
  
/**
  * @Title: planEdit
  * @Description: 修改页面
  * author: L ChenHao 
  * @param @param id
  * @param @return     
  * @return String     
  * @throws
   *//*
  @RequestMapping("/pledit")
  public String planEdit(String id,Model model){
    List<PurchaseRequired> purList=new LinkedList<PurchaseRequired>();
    List<String> list = collectPurchaseService.getNo(id);
    
    Map<String,Object> map=new HashMap<String,Object>();
    for(String str:list){
      map.put("isMaster", "1");
      map.put("planNo", str);
      List<PurchaseRequired> pur = purchaseRequiredService.getByMap(map);
       purList.addAll(pur);
    }
    model.addAttribute("list", purList);
    return "bss/pms/taskadjust/planedit";
  }*/
  
	
  @RequestMapping("/updatePlan")
  public String updatePlan(PurchaseRequiredFormBean list){
		if(list!=null){
			if(list.getListDetail()!=null&&list.getListDetail().size()>0){
				for( PurchaseDetail pd:list.getListDetail()){
					if( pd.getId()!=null){
						purchaseDetailService.updateByPrimaryKeySelective(pd);
					}
				}
			}
		}
	  return "redirect:edit.html";
  }
  
	/**
	 * 
	* @Title: updateById
	* @Description: 根据id修改 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	public String updateById(PurchaseRequiredFormBean list){
		Map<String,Object> map=new HashMap<String,Object>();
		if(list!=null){
			if(list.getListDetail()!=null&&list.getListDetail().size()>0){
				for( PurchaseDetail pd:list.getListDetail()){
					if( pd.getId()!=null){
						purchaseDetailService.updateByPrimaryKeySelective(pd);
//						String id = UUID.randomUUID().toString().replaceAll("-", "");
//						map.put("oid", id);
//						PurchaseRequired queryById = purchaseRequiredService.queryById(p.getId());
//						Integer s=Integer.valueOf(purchaseRequiredService.queryByNo(p.getPlanNo()))+1;
//						map.put("historyStatus", s);
//						map.put("id", p.getId());
//						purchaseRequiredService.update(map);
//						if(p.getParentId()!=null){
//							p.setParentId(p.getParentId());
//						}
//						queryById.setId(p.getId());
//						p.setHistoryStatus("0");
//						purchaseRequiredService.add(p);	
//					}else{
//						String id = UUID.randomUUID().toString().replaceAll("-", "");
//						p.setId(id);
//						purchaseRequiredService.add(p);	
					}
				}
				
			}
			
//			if(list.getAudit()!=null&&list.getAudit().size()>0){
//				for(PurchaseAudit pa:list.getAudit()){
//					if(pa.getParamValue()!=null){
//						purchaseAuditService.update(pa);
//					}
//				
//				}
//			}
		}
		
//		ProjectAttachments project=new ProjectAttachments();
//		String id = UUID.randomUUID().toString().replaceAll("-", "");
//		project.setId(id);
//		project.setContentType(file.getContentType());
//		project.setCreatedAt(new Date());
//		project.setFileName(file.getOriginalFilename());
//		project.setIsDeleted(0);
//		project.setProject(project);
//		projectAttachmentsService.save(project); //报错
//		FtpUtil.connectFtp(PropUtil.getProperty("file.upload.path.supplier"));
//		 FtpUtil.upload2("plan", file);
//		FtpUtil.closeFtp();
		return "redirect:list.html";
	}
	/**
	 * 
	* @Title: filed
	* @Description: 查看字段是否允许修改 
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @param name
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/filed")
	@ResponseBody
	public String filed(String planNo,String name){
		String[] str = name.split("\\.");
		List<String> list = collectPurchaseService.getId(planNo);
		if(list.size()>0){
			List<UpdateFiled> filed = updateFiledService.query(list.get(0), null);
			for(UpdateFiled f:filed){
				if(f.getFiled().contains(str[1])){
					return "exit";
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 *〈简述〉取消任务
	 *〈详细描述〉
	 * @author myc
	 * @param ids 选择的主键信息
	 * @return 成功返回ok,失败返回failed
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel",produces="html/text;chartset=UTF-8")
	public String cancel(String ids){
	    if (StringUtils.isNotBlank(ids)){
	        if (ids.contains(StaticVariables.COMMA_SPLLIT)){
	            String [] strArray = ids.split(StaticVariables.COMMA_SPLLIT);
	            for (String id: strArray){
	            	Task task = taskService.selectById(id);
	            	task.setStatus(2);
	            	taskService.update(task);
	            }
	        }  
	    }
	    return StaticVariables.SUCCESS;
	}
	
	/**
	 * 
	 *〈简述〉更新状态
	 *〈详细描述〉
	 * @author myc
	 * @param id
	 * @return
	 */
	private boolean checkStatus(String id){
	    CollectPlan collectPlan = collectPlanService.queryById(id);
        if (collectPlan != null){
            if (collectPlan.getStatus() == 8){
                return false;
            } else {
                collectPlan.setStatus(8);
                collectPlanService.update(collectPlan);
                return true;
            }
        }
        return false;
	} 
}
