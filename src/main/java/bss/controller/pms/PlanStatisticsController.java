package bss.controller.pms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import bss.controller.base.BaseController;
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
public class PlanStatisticsController extends BaseController {
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
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
		purchaseRequired.setGoodsType("1");
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page==null?1:page);
		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", purchaseRequired);
		model.addAttribute("year", year);
		
		 String json = map(purchaseRequired,year);
//		String json= JSON.toJSONString(getMap());
		 model.addAttribute("data", json);
		 
		 
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("typeName", 1);
			List<Orgnization> org = orgnizationServiceI.findOrgnizationList(map);
			model.addAttribute("org", org);
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
	
	
	public String map(PurchaseRequired purchaseRequired,String year){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("year",year);
		Map<String,Object> maps=getMap();
		Map<String, Object> province = getAllProvince();
		Set<String> key = province.keySet();
		List<Map<String,Object>> list = purchaseRequiredService.statisticOrg(map);
		
		 for(Map<String,Object> m:list ){
			 String str = (String) m.get("ORGANIZATION");
			 if(str!=null){
				 for(String s:key){
					  if(str.contains(s)){
						String pri=  (String) province.get(s);
						  maps.put(pri, m.get("COUNT"));
					  }
				  } 
			 }
		 }
		 
		 String json = JSON.toJSONString(maps);
		return json;
	}
	
	 
	public Map<String ,Object> getAllProvince(){
//		List<String> list=new ArrayList<String>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("安徽","an_hui");
		map.put("湖南","hu_nan");
		map.put("湖北","hu_bei");
		map.put("江西","jiang_xi" );
		map.put("青海","qing_hai" );
		map.put("宁夏","ning_xia" );
		map.put("台湾","tai_wan" );
		map.put("海南","hai_nan" );
		map.put("四川","si_chuan" );
		map.put("陕西","shan_xi_1" );//陕西
		
		map.put("西藏","xi_zang" );
		map.put("澳门","ao_men" );
		map.put("广东","guang_dong" );
		map.put("北京","bei_jing" );
		map.put("上海","shang_hai" );
		map.put("浙江","zhe_jiang" );
		map.put("香港","xiang_gang" );
		map.put("辽宁","liao_ning" );
		map.put("云南","yun_nan" );
		map.put("黑龙江","hei_long_jiang" );
		
		map.put("广西","guang_xi" );
		map.put("内蒙古","nei_meng_gu" );
		map.put("江苏","jiang_su" );
		map.put("重庆","chong_qing" );
		map.put("贵州","gui_zhou" );
		map.put("福建","fu_jian" );
		map.put("甘肃","gan_su" );
		map.put("河南","he_nan" );
		map.put("河北","he_bei");
		map.put("新疆","xin_jiang" );
		
		map.put("山西","shan_xi_2" );//山西
		map.put("山东","shan_dong");
		map.put("天津","tian_jin" );
		map.put("吉林","ji_lin" );
		return map;
	}
	public  Map<String ,Object> getMap(){
		Map<String,Object> map= new HashMap<String,Object>(40);
		map.put("an_hui", 0);
		map.put("hu_nan", 0);
		map.put("hu_bei", 0);
		map.put("jiang_xi", 0);
		map.put("qing_hai", 0);
		map.put("ning_xia", 0);
		map.put("tai_wan", 0);
		map.put("hai_nan", 0);
		map.put("si_chuan", 0);
		map.put("shan_xi_1", 0);//陕西
		
		map.put("xi_zang", 0);
		map.put("ao_men", 0);
		map.put("guang_dong", 0);
		map.put("bei_jing", 0);
		map.put("shang_hai", 0);
		map.put("zhe_jiang", 0);
		map.put("xiang_gang", 0);
		map.put("liao_ning", 0);
		map.put("yun_nan", 0);
		map.put("hei_long_jiang", 0);
		
		map.put("guang_xi", 0);
		map.put("nei_meng_gu", 0);
		map.put("jiang_su", 0);
		map.put("chong_qing", 0);
		map.put("gui_zhou", 0);
		map.put("fu_jian", 0);
		map.put("gan_su", 0);
		map.put("he_nan", 0);
		map.put("he_bei",0);
		map.put("xin_jiang", 0);
		
		map.put("shan_xi_2", 0);//山西
		map.put("shan_dong",0);
		map.put("tian_jin", 0);
		map.put("ji_lin", 0);
		return map;
	}
	
}
