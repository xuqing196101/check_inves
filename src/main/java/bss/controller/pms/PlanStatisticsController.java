package bss.controller.pms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;
import bss.formbean.Chart;
import bss.formbean.Data;
import bss.formbean.FusionCharts;
import bss.formbean.Maps;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;

import com.alibaba.fastjson.JSON;
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
	public String queryPlan(PurchaseRequired purchaseRequired,Integer page,Model model,String year){
		purchaseRequired.setIsMaster("1");
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page==null?1:page);
		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", purchaseRequired);
		model.addAttribute("year", year);
		
		 Maps maps=new Maps();
		 maps.setProvince("河南");
		 maps.setCount(100);
		 String json = JSON.toJSONString(maps);
		 
		 model.addAttribute("data", json);
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
		Integer sign=1;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("year",year);
		List<Map<String,Object>> list = purchaseRequiredService.statisticDepartment(map);
		
		List<Data> listData = new ArrayList<Data>();
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				Data data = new Data();
				data.setLabel(String.valueOf(m.get("DEPARTMENT")));
				data.setValue(String.valueOf(m.get("AMOUNT")));
				listData.add(data);
			}
		}
		
		FusionCharts f=new FusionCharts();
		/** 设置图像属性 */
		Chart chart = new Chart();
		if(sign == 1) {
			chart.setCaption("需求部门统计- 柱状图(3D)");
			chart.setFormatnumberscale("0");
			chart.setShowborder("0");
			chart.setBaseFontSize("14");
			chart.setYaxisName("金额");
		}
	 
		
		/** 封装到 fusionCharts */
		f.setChart(chart);
		f.setData(listData);
		String s=JSON.toJSONString(f);
		return s;
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
		
		List<Data> listData = new ArrayList<Data>();
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				Data data = new Data();
				data.setLabel(String.valueOf(m.get("PURCHASETYPE")));
				data.setValue(String.valueOf(m.get("AMOUNT")));
				listData.add(data);
			}
		}
		
		FusionCharts f=new FusionCharts();
		/** 设置图像属性 */
		Chart chart = new Chart();
 
			chart.setCaption("采购方式统计 - 饼状图(3D)");
			chart.setFormatnumberscale("0");
			chart.setShowborder("0");
			chart.setBaseFontSize("14");
	 
		
		/** 封装到 fusionCharts */
		f.setChart(chart);
		f.setData(listData);
		String s=JSON.toJSONString(f);
		return s;
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
		
		List<Data> listData = new ArrayList<Data>();
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				Data data = new Data();
				data.setLabel(String.valueOf(m.get("MONTH")));
				data.setValue(String.valueOf(m.get("AMOUNT")));
				listData.add(data);
			}
		}
		FusionCharts f=new FusionCharts();
		/** 设置图像属性 */
		Chart chart = new Chart();
 
		chart.setCaption("按月份统计统计 - 折线图");
		chart.setFormatnumberscale("0");
		chart.setShowborder("0");
		chart.setYaxisName("金额");
		chart.setAlternatehgridcolor("ff5904");
		chart.setDivlinecolor("ff5904");
		chart.setCanvasbordercolor("666666");
		chart.setBasefontcolor("666666");
		chart.setLinecolor("FF5904");
		chart.setBgcolor("ffffff");
		chart.setBaseFontSize("14");
		chart.setShowalternatehgridcolor("1");
	 
		
		/** 封装到 fusionCharts */
		f.setChart(chart);
		f.setData(listData);
		String s=JSON.toJSONString(f);
		return s;
	}
	
	@RequestMapping("/map")
	public String map(PurchaseRequired purchaseRequired,String year){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("year",year);
		Map<String,Object> maps=new HashMap<String,Object>();
		List<Map<String,Object>> list = purchaseRequiredService.statisticByMonth(map);
	 for(Map<String,Object> m:list ){
		 String str = (String) m.get("ORGANIZATION");
		  for(String s:getAllProvince()){
			  if(str.contains(str)){
				  maps.put("s", m.get("count"));
			  }
		  }
		 
	 }
		 String json = JSON.toJSONString(maps);
		return json;
	}
	
	
	public  List<String> getAllProvince(){
		List<String> list=new ArrayList<String>();
		list.add("吉林");
		list.add("天津");
		list.add("山东");
		list.add("山西");
		list.add("新疆");
		list.add("河北");
		list.add("河南");
		list.add("甘肃");
		
		list.add("福建");
		list.add("贵州");
		list.add("重庆");
		list.add("江苏");
		
		list.add("内蒙古");
		list.add("广西");
		list.add("黑龙江");
		list.add("云南");
		list.add("辽宁");
		
		list.add("香港");
		list.add("浙江");
		list.add("上海");
		list.add("北京");
		list.add("广东");
		list.add("澳门");
		list.add("西藏");
		list.add("陕西");
		list.add("四川");
		list.add("海南");
		list.add("台湾");
		list.add("宁夏");
		list.add("青海");
		list.add("江西");
		list.add("湖北");
		list.add("湖南");
		list.add("安徽");
		return list;
	}
	public  Map<String ,Integer> getMap(){
		Map<String,Integer> map= new HashMap<String,Integer>(40);
		map.put("安徽", 0);
		map.put("湖南", 0);
		map.put("湖北", 0);
		map.put("江西", 0);
		map.put("青海", 0);
		map.put("宁夏", 0);
		map.put("台湾", 0);
		map.put("海南", 0);
		map.put("四川", 0);
		map.put("陕西", 0);
		
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
		map.put("河北", 0);
		map.put("新疆", 0);
		
		map.put("山西", 0);
		map.put("山东", 0);
		map.put("天津", 0);
		map.put("吉林", 0);
		return map;
	}
	
}
