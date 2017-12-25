package bss.controller.pms;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.AuthorityUtil;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.formbean.Maps;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Project;
import bss.model.ppms.Task;
import bss.model.prms.PackageExpert;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.TaskService;
import bss.service.prms.PackageExpertService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.StaticVariables;
/**
 * 
 * @Title: PlanStatisticsController
 * @Description: 计划汇总统计分 
 * @author Li Xiaoxiao
 * @date  2016年10月9日,下午2:33:08
 *
 */
@Controller
@RequestMapping("/statistic")
public class PlanStatisticsController extends BaseController {
	
	@Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private PurchaseDetailService purchaseDetailService;
	
	@Autowired
	private TaskService taskservice;
	
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private PackageExpertService packageExpertService;
	
	@Autowired
	private SupplierCheckPassService checkPassService;
	
	/**
	 * 
	* @Title: queryPlan
	* @Description: 统计分析查询
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @param page
	* @param @param model
	* @param @param year
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String queryPlan(@CurrentUser User user,CollectPlan collectPlan,Integer page,Model model,String year){
		Orgnization orgnization = orgnizationServiceI.findByCategoryId(user.getOrg().getId());
		if(orgnization!=null&&"2".equals(orgnization.getTypeName())){
			collectPlan.setUserId(user.getId());
		}else{
			collectPlan.setUserId("0");
		}
		List<CollectPlan> list = collectPlanService.getSummary(collectPlan, page==null?1:page);
		PageInfo<CollectPlan> info = new PageInfo<CollectPlan>(list);
		model.addAttribute("info", info);
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("6");
		model.addAttribute("dic", dic);
		model.addAttribute("collectPlan", collectPlan);
		
		//只有采购管理部门才能操作
    if("2".equals(user.getTypeName())){
      model.addAttribute("auth", "show");
    }else {
      model.addAttribute("auth", "hidden");
    }
		return "bss/pms/statistic/list";
	}
	/**
	 * 需求计划明细查询
	 * @param user
	 * @param collectPlan
	 * @param page
	 * @param model
	 * @param year
	 * @return
	 */
	@RequestMapping("/detailList")
	public String detailList(@CurrentUser User user,CollectPlan collectPlan,Integer page,Model model,String year){
		Orgnization orgnization = orgnizationServiceI.findByCategoryId(user.getOrg().getId());
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		if(orgnization!=null&&"2".equals(orgnization.getTypeName())){
			hashMap.put("userId", user.getId());
		}else{
			hashMap.put("userId", "0");
		}
		page=page==null?1:page;
		hashMap.put("page", page);
		hashMap.put("planName", collectPlan.getFileName());
		hashMap.put("planNo", collectPlan.getPlanNo());
		hashMap.put("planType", collectPlan.getGoodsType());
		List<PurchaseDetail> list = purchaseDetailService.getdetailAllByUserId(hashMap);
		PageInfo<PurchaseDetail> info = new PageInfo<PurchaseDetail>(list);
		for(PurchaseDetail p:info.getList()){
			DictionaryData dic = DictionaryDataUtil.findById(String.valueOf(p.getPurchaseType()));
			if(dic!=null){
				p.setPurchaseType(dic.getName());
			}
		}
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("6");
		model.addAttribute("dic", dic);
		model.addAttribute("info", info);
		return "bss/pms/statistic/detailList";
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	* @Title: bar
	* @Description: 图形统计
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @param sign
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping(value="/bar",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bar(@CurrentUser User user,PurchaseRequired purchaseRequired,String year) throws UnsupportedEncodingException{
		Orgnization orgnization = orgnizationServiceI.findByCategoryId(user.getOrg().getId());
		HashMap<String,Object> map=new HashMap<String,Object>();
		if(orgnization!=null&&"2".equals(orgnization.getTypeName())){
			map.put("userId",user.getId());
		}else{
			map.put("userId","0");
		}
		Map<String, Object> getbar = purchaseDetailService.getbar(map);
		String json = JSON.toJSONString(getbar);
		return json;
	}
	
	/**
	 * 
	* @Title: pipe
	* @Description: 按采购方式统计 
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @param year
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping(value="/pipe",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String pipe(@CurrentUser User user,PurchaseRequired purchaseRequired,String year){
		Orgnization orgnization = orgnizationServiceI.findByCategoryId(user.getOrg().getId());
		HashMap<String,Object> map=new HashMap<String,Object>();
		if(orgnization!=null&&"2".equals(orgnization.getTypeName())){
			map.put("userId",user.getId());
		}else{
			map.put("userId","0");
		}
		Map<String, Object> data = purchaseDetailService.getpipe(map);
		String json = JSON.toJSONString(data);
		return json;
	}
	
	/**
	 * 
	* @Title: line
	* @Description: 按月份统计
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @param year
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping(value="/line",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String line(@CurrentUser User user,PurchaseRequired purchaseRequired,String year){
		Orgnization orgnization = orgnizationServiceI.findByCategoryId(user.getOrg().getId());
		HashMap<String,Object> map=new HashMap<String,Object>();
		if(orgnization!=null&&"2".equals(orgnization.getTypeName())){
			map.put("userId",user.getId());
		}else{
			map.put("userId","0");
		}
		Map<String, Object> data = purchaseDetailService.getline(map);
		String s=JSON.toJSONString(data);
		return s;
	}
	
	
	public String map(PurchaseRequired purchaseRequired,String year){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("year",year);
		Map<String,Object> maps=getMap();
		List<Maps> listMap=new LinkedList<Maps>();
		
		
		Set<String> key = maps.keySet();
		List<Map<String,Object>> list = purchaseRequiredService.statisticOrg(map);
		
		 for(Map<String,Object> m:list ){
			 String str = (String) m.get("ORGANIZATION");
			 if(str!=null){
				 for(String s:key){
					  if(str.contains(s)){
						  maps.put(s, m.get("COUNT"));
					  }
				  } 
			 }
		 }
		 
		 for(String s:key){
			 Maps mp=new Maps();
//			 BigDecimal str =  (BigDecimal) maps.get(s);
//			 
			 String string = String.valueOf(maps.get(s));
			 mp.setValue(new BigDecimal(string));
			 mp.setName(s);
			 listMap.add(mp);
		 }
		 
		 String json = JSON.toJSONString(listMap);
		return json;
	}
	
	public  Map<String ,Object> getMap(){
		Map<String,Object> map= new HashMap<String,Object>(40);
		map.put("安徽", 0);
		map.put("湖南", 0);
		map.put("湖北", 0);
		map.put("江西", 0);
		map.put("青海", 0);
		map.put("宁夏", 0);
		map.put("台湾", 0);
		map.put("海南", 0);
		map.put("四川", 0);
		map.put("陕西", 0);//陕西
		
		map.put("西藏", 0);
		map.put("澳门", 0);
		map.put("广东", 0);
		map.put("北京", 0);
		map.put("上海", 0);
		map.put("浙江", 0);
		map.put("香港", 0);
		map.put("辽宁", 0);
		map.put("云南", 0);
		map.put("黑龙江", 0);
		
		map.put("广西", 0);
		map.put("内蒙古", 0);
		map.put("江苏", 0);
		map.put("重庆", 0);
		map.put("贵州", 0);
		map.put("福建", 0);
		map.put("甘肃", 0);
		map.put("河南", 0);
		map.put("河北",0);
		map.put("新疆", 0);
		
		map.put("山西", 0);//山西
		map.put("山东",0);
		map.put("天津", 0);
		map.put("吉林", 0);
		return map;
	}
	
	
	@RequestMapping("/taskList")
  public String queryPlan(@CurrentUser User user,Model model,HttpServletResponse response,HttpServletRequest request,Integer page,Task task,String beginDate,String endDate) throws IOException{
	  long begin=System.currentTimeMillis();
	  if (user != null) {
		  if (StringUtils.isNotBlank(task.getName())) {
			  task.setName(task.getName().trim());
		  }
		  if (StringUtils.isNotBlank(task.getDocumentNumber())) {
			  task.setDocumentNumber(task.getDocumentNumber().trim());
		  }
		  if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
			  task.setBeginDate(beginDate.trim());
			  task.setEndDate(endDate.trim());
		  }
		  HashMap<String, Object> dataMap = AuthorityUtil.dataAuthority(user.getId());
		  Integer dataAccess = (Integer) dataMap.get("dataAccess");
		  if (dataAccess == 2){
			  List<String> superviseOrgId = (List<String>) dataMap.get("superviseOrgs");
			  if (superviseOrgId != null && !superviseOrgId.isEmpty()) {
				  if (StringUtils.equals("2", user.getTypeName())) {
					  task.setOrgId(user.getOrg().getId());
				  } else if (StringUtils.equals("5", user.getTypeName())) {
					  task.setOrgList(superviseOrgId);
				  }
			  }
		  } else if (dataAccess == 3) {
			  task.setOrgId(user.getOrg().getId());
		  }
		  List<Task> list = taskservice.searchByTask(task,page==null?1:page);
		  List<PurchaseOrg> listOrg = purchaseOrgnizationServiceI.getOrg(user.getOrg().getId());
		  List<Orgnization> list2=new ArrayList<Orgnization>();
		  for (PurchaseOrg purchaseOrg : listOrg) {
			  Orgnization orgByPrimaryKey = orgnizationServiceI.getOrgByPrimaryKey(purchaseOrg.getPurchaseDepId());
			  list2.add(orgByPrimaryKey);
		  }
		  model.addAttribute("allOrg", list2);
		  PageInfo<Task> info = new PageInfo<>(list);
		  model.addAttribute("info", info);
		  model.addAttribute("task", task);
		  long end=System.currentTimeMillis();
		  System.out.println("耗时："+(end-begin));
	  }
	  return "bss/pms/statistic/task_list";
  }
	@RequestMapping("/taskDetailList")
  public String taskDetailList(@CurrentUser User user,Model model,HttpServletResponse response,HttpServletRequest request,Integer page, PurchaseDetail detail,
		  String beginDate,String endDate,String projectNumber,String proBeginDate, String proEndDate,String code, String materialsType) throws IOException{
		if (user != null) {
			  HashMap<String, Object> dataMap = AuthorityUtil.dataAuthority(user.getId());
			  List<String> superviseOrgId = (List<String>) dataMap.get("superviseOrgs");
			  if (superviseOrgId != null && !superviseOrgId.isEmpty()) {
				  HashMap<String, Object> hashMap = new HashMap<>();
				  if(detail.getGoodsName()!=null){
					  hashMap.put("goodsName", detail.getGoodsName().trim());
				  }
				  if(detail.getTaskNumber()!=null){
					  hashMap.put("taskNumber", detail.getTaskNumber().trim());
				  }
				  if (detail != null && StringUtils.isNotBlank(detail.getDepartment())) {
					  hashMap.put("department", detail.getDepartment());
				  }
				  if (detail != null && StringUtils.isNotBlank(detail.getPurchaseType())) {
					  hashMap.put("purchaseType", detail.getPurchaseType());
				  }
				  if(beginDate!=null&&!"".equals(beginDate.trim())&&endDate!=null&&!"".equals(endDate.trim())){
					  hashMap.put("beginDate", beginDate);
					  hashMap.put("endDate", endDate);
				  }
				  if (StringUtils.isNotBlank(materialsType)) {
					  hashMap.put("materialsType", materialsType);
				  }
				  if (StringUtils.isNotBlank(projectNumber)) {
					  hashMap.put("projectNumber", projectNumber.trim());
				  }
				  if (StringUtils.isNotBlank(proBeginDate) && StringUtils.isNotBlank(proEndDate)) {
					  hashMap.put("proEndDate", proEndDate.trim());
					  hashMap.put("proBeginDate", proBeginDate.trim());
				  }
				  if (StringUtils.isNotBlank(code)) {
					  hashMap.put("code", code.trim());
				  }
				  if (detail != null && StringUtils.isNotBlank(detail.getOrganization())) {
					  hashMap.put("organization", detail.getOrganization());
				  }
				  if (StringUtils.equals("2", user.getTypeName())) {
					  hashMap.put("orgId", user.getOrg().getId());
				  } else if (StringUtils.equals("5", user.getTypeName())) {
					  hashMap.put("orgList", superviseOrgId);
				  }
				  List<PurchaseDetail> PurchaseDetailList = purchaseDetailService.selectByTask(hashMap,page==null?1:page);
				  PageInfo<PurchaseDetail> info = new PageInfo<>(PurchaseDetailList);
				  model.addAttribute("info", info);
				  model.addAttribute("detail", detail);
				  model.addAttribute("projectNumber", projectNumber);
				  model.addAttribute("proEndDate", proEndDate);
				  model.addAttribute("proBeginDate", proBeginDate);
				  model.addAttribute("code", code);
				  model.addAttribute("materialsType", materialsType);
				  model.addAttribute("planTypes", DictionaryDataUtil.find(6));
				  model.addAttribute("dataType", DictionaryDataUtil.find(5));
				  List<Orgnization> list2=new ArrayList<Orgnization>();
				  List<Orgnization> list3=new ArrayList<Orgnization>();
				  if (user.getOrg() != null) {
					  List<PurchaseOrg> listOrg = purchaseOrgnizationServiceI.getOrg(user.getOrg().getId());
					  for (PurchaseOrg purchaseOrg : listOrg) {
						  Orgnization orgByPrimaryKey = orgnizationServiceI.getOrgByPrimaryKey(purchaseOrg.getPurchaseDepId());
						  if(orgByPrimaryKey!=null){
							  list2.add(orgByPrimaryKey);
						  }
					  }
					  
					  List<PurchaseOrg> byPurchaseDepId = purchaseOrgnizationServiceI.getByPurchaseDepId(user.getOrg().getId());
					  for (PurchaseOrg purchaseOrg : byPurchaseDepId) {
						  Orgnization orgByPrimaryKey = orgnizationServiceI.getOrgByPrimaryKey(purchaseOrg.getOrgId());
						  if(orgByPrimaryKey!=null){
							  list3.add(orgByPrimaryKey);
						  }
					  }
					  
				  } else {
					  HashMap<String, Object> map = new HashMap<>();
					  map.put("userId", superviseOrgId);
					  List<Orgnization> selectByIdList = orgnizationServiceI.selectByIdList(map);
					  for (Orgnization orgnization : selectByIdList) {
						  if (StringUtils.equals("0", orgnization.getTypeName())) {
							  list3.add(orgnization);
						  } else if (StringUtils.equals("1", orgnization.getTypeName())) {
							  list2.add(orgnization);
						  }
					  }
				  }
				  model.addAttribute("allOrg",list2);
				  model.addAttribute("allXq", list3);
			  }
		}
		return "bss/pms/statistic/task_detail";
	}
	@RequestMapping("/charDept")
  public String charDept(@CurrentUser User user,Model model,HttpServletResponse response,HttpServletRequest request){
	  if(user.getTypeName().equals("2")){
	  List<PurchaseDetail> selectByDept = purchaseDetailService.selectByDept(user.getOrg().getId());
	  List<String> name=new ArrayList<String>();
	  List<String> data=new ArrayList<String>();
	  BigDecimal max=BigDecimal.ZERO;
	  for (PurchaseDetail purchaseDetail : selectByDept) {
	    name.add(purchaseDetail.getDepartment());
	    data.add(purchaseDetail.getBudget()+"");
	    BigDecimal min = new BigDecimal(purchaseDetail.getBudget()+"");
      int n = max.compareTo(min);
      if(n<0){
        max=min;
      }
    }
	  model.addAttribute("name", JSON.toJSONString(name));
	  model.addAttribute("data", JSON.toJSONString(data));
	  model.addAttribute("max", max);
	  }
	 return "bss/pms/statistic/task_dept";
	}
	@RequestMapping("/charType")
  public String charType(@CurrentUser User user,Model model,HttpServletResponse response,HttpServletRequest request){
	  if(user.getTypeName().equals("2")){
	  List<PurchaseDetail> selectByDept = purchaseDetailService.selectByType(user.getOrg().getId());
    List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
    List<String> type=new ArrayList<String>();
    for (PurchaseDetail purchaseDetail : selectByDept) {
      Map<String, Object> map=new HashMap<String, Object>();
      if(purchaseDetail.getPurchaseType()!=null){
        DictionaryData findById = DictionaryDataUtil.findById(purchaseDetail.getPurchaseType());
        if(findById!=null){
          map.put("name",findById.getName());
          map.put("value", purchaseDetail.getBudget());
          list.add(map);
          type.add(findById.getName());
        }
      }
    }
    model.addAttribute("type", JSON.toJSONString(type));
    model.addAttribute("data", JSON.toJSONString(list));
	  }
   return "bss/pms/statistic/task_type";
  }
	@RequestMapping("/charMonth")
  public String charMonth(@CurrentUser User user,Model model,HttpServletResponse response,HttpServletRequest request){
	  if(user.getTypeName().equals("2")){
	  List<Map<String, Object>> selectByMonth = purchaseDetailService.selectByMonth(user.getOrg().getId());
    List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
    List<String> month=new ArrayList<String>();
    List<String> bud=new ArrayList<String>();
    Map<String, Object> map=new HashMap<String, Object>();
    if(selectByMonth!=null&&selectByMonth.size()>0){
      for (Map<String, Object> purchaseDetail : selectByMonth) {
        month.add((String) purchaseDetail.get("TASKGIVETIME"));
        bud.add(purchaseDetail.get("BUDGET").toString());
      }
    }
    map.put("data", bud);
    map.put("name", "金额");
    map.put("stack", "总量");
    map.put("type", "line");
    list.add(map);
    model.addAttribute("month", JSON.toJSONString(month));
    model.addAttribute("data", JSON.toJSONString(list));
	  }
   return "bss/pms/statistic/task_month";
  }
	
	/**
	 * 
	* @Title: taskExcel
	* @author FengTian 
	* @date 2017-12-11 上午10:40:18  
	* @Description: 导出Excel 
	* @param @param user
	* @param @param response
	* @param @param request
	* @param @param detail
	* @param @param beginDate
	* @param @param endDate      
	* @return void
	 */
	/*@RequestMapping("/taskExcel")
	public void taskExcel(@CurrentUser User user, HttpServletResponse response, HttpServletRequest request,
			PurchaseDetail detail,String beginDate,String endDate,String projectNumber,String proBeginDate, 
			String proEndDate,String code, String materialsType, String type, String name){
		if(StringUtils.equals("2", user.getTypeName())){
			HashMap<String, Object> map = new HashMap<>();
		    if(detail != null && StringUtils.isNotBlank(detail.getGoodsName())){
		    	map.put("goodsName", detail.getGoodsName().trim());
		    }
		    if(detail != null && StringUtils.isNotBlank(detail.getTaskNumber())){
		    	map.put("taskNumber", detail.getTaskNumber().trim());
		    }
		    if (detail != null && StringUtils.isNotBlank(detail.getDepartment())) {
		    	map.put("department", detail.getDepartment());
			}
		    if (detail != null && StringUtils.isNotBlank(detail.getPurchaseType())) {
		    	map.put("purchaseType", detail.getPurchaseType());
			}
		    if (detail != null && StringUtils.isNotBlank(detail.getOrganization())) {
		    	map.put("organization", detail.getOrganization());
			}
		    if (StringUtils.isNotBlank(materialsType)) {
				map.put("materialsType", materialsType);
			}
		    if(StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)){
		    	map.put("beginDate", beginDate);
		    	map.put("endDate", endDate);
		    }
		    if (StringUtils.isNotBlank(projectNumber)) {
		    	map.put("projectNumber", projectNumber.trim());
		    }
		    if (StringUtils.isNotBlank(proBeginDate) && StringUtils.isNotBlank(proEndDate)) {
		    	map.put("proEndDate", proEndDate.trim());
				  map.put("proBeginDate", proBeginDate.trim());
			}
		    if (StringUtils.isNotBlank(code)) {
		    	map.put("code", code.trim());
		    }
		    detail.setOrgId(user.getOrg().getId());
		    List<PurchaseDetail> list = purchaseDetailService.selectByTask(map, null);
		    if (list != null && !list.isEmpty()) {
		    	for (PurchaseDetail purchaseDetail : list) {
					if (StringUtils.isBlank(purchaseDetail.getPurchaseType()) || StringUtils.equals("空值", purchaseDetail.getPurchaseType())) {
						purchaseDetail.setPurchaseType(null);
					} else {
						purchaseDetail.setPurchaseType(DictionaryDataUtil.findById(purchaseDetail.getPurchaseType()).getName());
					}
					if (StringUtils.isNotBlank(purchaseDetail.getOrganization())) {
						Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(purchaseDetail.getOrganization());
						if (orgnization != null) {
							purchaseDetail.setOrganization(orgnization.getShortName());
						} else {
							purchaseDetail.setOrganization(null);
						}
					}
				}
		    	ExcelUtils excelUtils = new ExcelUtils(response, "明细信息", "sheet1", 3000);
		    	// 设置冻结行
		        excelUtils.setFreezePane(true);
		        excelUtils.setFreezePane(new Integer[]{0, 1, 0, 1});
		        // 设置序号列
		        excelUtils.setOrder(true);
		        String titleColumn[] = {"orderNum", "seq", type, "department", "goodsName",
		                "item", "purchaseCount", "price", "budget", "purchaseType", "organization", "taskGiveTime"};
		        String titleName[] = {"序号", "产品编号", name, "需求部门", "产品名称", "单位", "采购数量",
		                "单价(元)", "金额(万元)", "采购方式", "采购机构", "下达时间"};
		        //int titleSize[] = {5, 10, 10, 70, 10, 13, 13, 16, 20, 20, 22};
		        excelUtils.wirteExcel(titleColumn, titleName, null, list);
			}
		}
	}*/
	
	
	/*@RequestMapping("/nextStep")
	public String nextStep(Model model, PurchaseDetail detail, Project project, String proBeginDate, 
			String proEndDate,String code, String materialsType){
		model.addAttribute("detail", detail);
		model.addAttribute("project", project);
		model.addAttribute("proBeginDate", proBeginDate);
		model.addAttribute("proEndDate", proEndDate);
		model.addAttribute("code", code);
		model.addAttribute("materialsType", materialsType);
		return "bss/pms/statistic/task_select";
	}*/
	
	@RequestMapping("/taskExcel")
	public String taskExcel(@CurrentUser User user, Model model, HttpServletResponse response, HttpServletRequest request,
			PurchaseDetail detail,String beginDate,String endDate,String projectNumber,String proBeginDate, 
			String proEndDate,String code, String materialsType, String showValue, String showName){
		if(StringUtils.equals("2", user.getTypeName())){
			HashMap<String, Object> map = new HashMap<>();
		    if(detail != null && StringUtils.isNotBlank(detail.getGoodsName())){
		    	map.put("goodsName", detail.getGoodsName().trim());
		    }
		    if(detail != null && StringUtils.isNotBlank(detail.getTaskNumber())){
		    	map.put("taskNumber", detail.getTaskNumber().trim());
		    }
		    if (detail != null && StringUtils.isNotBlank(detail.getDepartment())) {
		    	map.put("department", detail.getDepartment());
			}
		    if (detail != null && StringUtils.isNotBlank(detail.getPurchaseType())) {
		    	map.put("purchaseType", detail.getPurchaseType());
			}
		    if (detail != null && StringUtils.isNotBlank(detail.getOrganization())) {
		    	map.put("organization", detail.getOrganization());
			}
		    if (StringUtils.isNotBlank(materialsType)) {
				map.put("materialsType", materialsType);
			}
		    if(StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)){
		    	map.put("beginDate", beginDate);
		    	map.put("endDate", endDate);
		    }
		    if (StringUtils.isNotBlank(projectNumber)) {
		    	map.put("projectNumber", projectNumber.trim());
		    }
		    if (StringUtils.isNotBlank(proBeginDate) && StringUtils.isNotBlank(proEndDate)) {
		    	map.put("proEndDate", proEndDate.trim());
				map.put("proBeginDate", proBeginDate.trim());
			}
		    if (StringUtils.isNotBlank(code)) {
		    	map.put("code", code.trim());
		    }
		    detail.setOrgId(user.getOrg().getId());
		    List<PurchaseDetail> list = purchaseDetailService.selectByTask(map, null);
		    if (list != null && !list.isEmpty()) {
		    	for (PurchaseDetail purchaseDetail : list) {
		    		if (StringUtils.isNotBlank(showValue)) {
		    			if (showValue.indexOf("expertName") > -1) {
							if (StringUtils.isNotBlank(purchaseDetail.getPackId())) {
								String expertName = packageExpertService.selectByExpertName(purchaseDetail.getPackId());
								if (StringUtils.isNotBlank(expertName)) {
									purchaseDetail.setExpertName(expertName);
								}
							}
						}
						if (showValue.indexOf("supplier") > -1) {
							if (StringUtils.isNotBlank(purchaseDetail.getPackId())) {
								String supplierName = checkPassService.selectBySupplierName(purchaseDetail.getPackId());
								if (StringUtils.isNotBlank(supplierName)) {
									purchaseDetail.setSupplier(supplierName);
								}
							}
						}
					}
				}
		    	if (StringUtils.isNotBlank(showValue) && StringUtils.isNotBlank(showName)) {
		    		String[] types = showValue.split(StaticVariables.COMMA_SPLLIT);
			    	String[] names = showName.split(StaticVariables.COMMA_SPLLIT);
			    	model.addAttribute("types", types);
			    	model.addAttribute("names", names);
				}
		    	model.addAttribute("list", list);
			}
		}
		return "bss/pms/statistic/task_excel";
	}
}
