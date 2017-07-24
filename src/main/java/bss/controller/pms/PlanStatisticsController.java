package bss.controller.pms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.formbean.Line;
import bss.formbean.Maps;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.pms.impl.PurchaseDetailServiceImpl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
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
	
}
