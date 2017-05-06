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
import ses.model.oms.Orgnization;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.formbean.Line;
import bss.formbean.Maps;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseRequiredService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
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
	public String queryPlan(CollectPlan collectPlan,Integer page,Model model,String year){
//		purchaseRequired.setGoodsType("1");
//		PageHelper.startPage(page==null?1:page,10);
//		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page==null?1:page);
//		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
//		model.addAttribute("info", info);
//		model.addAttribute("inf", purchaseRequired);
//		model.addAttribute("year", year);
//		model.addAttribute("kind", DictionaryDataUtil.find(5));
//		model.addAttribute("goods", DictionaryDataUtil.find(6));
		
		
//		 String json = map(purchaseRequired,year);
//		String json= JSON.toJSONString(getMap());
//		 model.addAttribute("data", json);
//		 
//		 
//			HashMap<String,Object> map=new HashMap<String,Object>();
//			map.put("typeName", 1);
//			List<Orgnization> org = orgnizationServiceI.findOrgnizationList(map);
//			model.addAttribute("org", org);
		
		List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
		PageInfo<CollectPlan> info = new PageInfo<>(list);
		model.addAttribute("info", info);
//		model.addAttribute("inf", collectPlan);
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
		model.addAttribute("dic", dic);
		
		
		return "bss/pms/statistic/list";
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
	public String bar(PurchaseRequired purchaseRequired,String year) throws UnsupportedEncodingException{
		Map<String,Object> dataMap=new HashMap<String,Object>();
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("year",year);
		List<Map<String,Object>> list = purchaseRequiredService.statisticDepartment(map);
		
		List<String> listData = new LinkedList<String>();
		List<String>  data=new LinkedList<String>();
		BigDecimal max=BigDecimal.ZERO;
		
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				listData.add(String.valueOf(m.get("DEPARTMENT"))) ;
				 String str=String.valueOf(m.get("AMOUNT"));
				data.add(str);
				BigDecimal min = new BigDecimal(str);
				int n = max.compareTo(min);
				if(n<0){
					max=min;
				}
			}
		}
		
		dataMap.put("name", listData);
		dataMap.put("data", data);
		dataMap.put("max", max);
		String json = JSON.toJSONString(dataMap);
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
	public String pipe(PurchaseRequired purchaseRequired,String year){
	
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("year",year);
		List<Map<String,Object>> list = purchaseRequiredService.statisticPurchaseMethod(map);
		Map<String,Object> data=new HashMap<String,Object>();
		
		List<Maps> maps=new LinkedList<Maps>();
		List<String> type=new LinkedList<String>();
		
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				DictionaryData dic = DictionaryDataUtil.findById(String.valueOf(m.get("PURCHASETYPE")));
				
				if(dic!=null){
					type.add(dic.getName());
					
				}
				
				 
				 Maps mp=new Maps();
 				 String string = String.valueOf(m.get("AMOUNT"));
 				BigDecimal decimal = new BigDecimal(string);
				 mp.setValue(decimal);
				if(dic!=null){
					 mp.setName(dic.getName());
				}
				 maps.add(mp);
			}
		}
		data.put("maps", maps);
		data.put("type", type);
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
	public String line(PurchaseRequired purchaseRequired,String year){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("year",year);
		List<Map<String,Object>> list = purchaseRequiredService.statisticByMonth(map);
		
		Map<String,Object> data=new HashMap<String,Object>();
		List<String> month=new LinkedList<String>();
		List<String> val=new LinkedList<String>();
		List<Line> lineList=new LinkedList<Line>();
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				month.add(String.valueOf(m.get("MONTH")));
				String string = String.valueOf(m.get("AMOUNT"));
				val.add(string);
			/*	data.setValue(String.valueOf(m.get("AMOUNT")));
				listData.add(data);*/
			}
		}
		Line line=new Line();
		line.setData(val);
		line.setName("测试");
		line.setType("line");
		line.setStack("总量");
		lineList.add(line);
		data.put("month", month);
		data.put("line", lineList);
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
