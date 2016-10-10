package bss.controller.pms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.formbean.Chart;
import bss.formbean.Data;
import bss.formbean.FusionCharts;
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
public class PlanStatisticsController {
	
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
	@RequestMapping(value="/chart",produces = "text/html;charset=UTF-8")
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
				data.setLabel(String.valueOf(m.get("department")));
				data.setValue(String.valueOf(m.get("amount")));
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
//			chart.setYaxisName("项目数量");
		}
		if(sign == 2) {
			chart.setCaption("需求部门统计 - 饼状图(3D)");
			chart.setFormatnumberscale("0");
			chart.setShowborder("0");
			chart.setBaseFontSize("14");
		}
		if(sign == 3) {
			chart.setCaption("需求部门统计 - 折线图");
			chart.setFormatnumberscale("0");
			chart.setShowborder("0");
			chart.setYaxisName("项目数量");
			chart.setAlternatehgridcolor("ff5904");
			chart.setDivlinecolor("ff5904");
			chart.setCanvasbordercolor("666666");
			chart.setBasefontcolor("666666");
			chart.setLinecolor("FF5904");
			chart.setBgcolor("ffffff");
			chart.setBaseFontSize("14");
			chart.setShowalternatehgridcolor("1");
		}
		
		/** 封装到 fusionCharts */
		f.setChart(chart);
		f.setData(listData);
		String s=JSON.toJSONString(f);
		return s;
	}
/*	public String pipe(){
		
		return "";
	}
	public String line(){
		return "";
	}*/
}
