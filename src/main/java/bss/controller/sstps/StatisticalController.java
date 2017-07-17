package bss.controller.sstps;

import bss.echarts.AxisLabel;
import bss.echarts.AxisPointer;
import bss.echarts.Feature;
import bss.echarts.Legend;
import bss.echarts.MagicType;
import bss.echarts.Option;
import bss.echarts.Restore;
import bss.echarts.SaveAsImage;
import bss.echarts.Series;
import bss.echarts.TextStyle;
import bss.echarts.Title;
import bss.echarts.Toolbox;
import bss.echarts.Tooltip;
import bss.echarts.XAxis;
import bss.echarts.YAxis;
import bss.model.sstps.AppraisalContract;
import bss.service.sstps.AppraisalContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.util.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Scope
@RequestMapping("/statistical")
public class StatisticalController {
	
	@Autowired
	private AppraisalContractService appraisalContractService;
	@Autowired
    private OrgnizationServiceI orgnizationServiceI;
	@ResponseBody
	@RequestMapping("/echarts")
	public Option echarts(HttpServletRequest request, HttpServletResponse response,AppraisalContract appraisalContract){
		Option option = new Option();
		Title title = new Title();
		title.setText("审价结果统计图");
		Toolbox toolbox = new Toolbox();
        Feature feature = new Feature();
        toolbox.setOrient("vertical");
        toolbox.setX("right");
        toolbox.setY("top");
        MagicType magicType = new MagicType();
        magicType.setShow(true);
        List<String> type = new ArrayList<String>();
        type.add("line");
        type.add("bar");
        magicType.setType(type);
        Restore restore = new Restore();
        restore.setShow(true);
        SaveAsImage saveAsImage = new SaveAsImage();
        saveAsImage.setShow(true);
        feature.setMagicType(magicType);
        feature.setRestore(restore);
        feature.setSaveAsImage(saveAsImage);
        toolbox.setShow(true);
        toolbox.setFeature(feature);
        Tooltip tooltip = new Tooltip();
        tooltip.setTrigger("axis");
        tooltip.setShow(true);
        tooltip.setShowContent(true);
        tooltip.setTriggerOn("mousemove");
        tooltip.setFormatter("{b0}<br/>{a0}: {c0}<br />{a1}: {c1}<br />{a2}: {c2}%");
        AxisPointer axisPointer = new AxisPointer();
        axisPointer.setType("line");
        axisPointer.setAnimation(true);
        tooltip.setAxisPointer(axisPointer);
        TextStyle textStyle = new TextStyle();
        textStyle.setColor("#fff");
        textStyle.setFontStyle("normal");
        textStyle.setFontWeight("normal");
        textStyle.setFontFamily("sans-serif");
        textStyle.setFontSize(14);
        tooltip.setTextStyle(textStyle);
        tooltip.setBackgroundColor("rgba(50,50,50,0.7)");
        tooltip.setBorderColor("#333");
        tooltip.setExtraCssText("box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);");
        Legend legend = new Legend();
        List<String> data = new ArrayList<String>();
        data.add("合同金额");
        data.add("审价金额");
        data.add("审减百分比");
        legend.setData(data);
        legend.setX("center");
        legend.setY("top");
        legend.setOrient("horizontal");
        List<XAxis> xs = new ArrayList<XAxis>();
        XAxis xAxis = new XAxis();
        xAxis.setType("category");
        List<String> datax = new ArrayList<String>();
        List<AppraisalContract> list = appraisalContractService.selectStatisical(appraisalContract);
        for(int i=0;i<list.size();i++){
        	Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(list.get(0).getPurchaseDepName());
			if(org!=null){
				datax.add(org.getName());
			}else{
				datax.add("");
			}
        }
        xAxis.setData(datax);
        xAxis.setBoundaryGap(true);
        xs.add(xAxis);
        List<YAxis> ys = new ArrayList<YAxis>();
        YAxis yAxis1 = new YAxis();
        yAxis1.setType("value");
        yAxis1.setName("万");
        yAxis1.setMin(0);
        yAxis1.setMax(9000);
        yAxis1.setInterval(1000);
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setFormatter("{value}");
        yAxis1.setAxisLabel(axisLabel);
        YAxis yAxis2 = new YAxis();
        yAxis2.setType("value");
        yAxis2.setName("%");
        yAxis2.setMin(0);
        yAxis2.setMax(100);
        yAxis2.setInterval(10);
        AxisLabel axisLabel2 = new AxisLabel();
        axisLabel2.setFormatter("{value}%");
        yAxis2.setAxisLabel(axisLabel2);
        ys.add(yAxis1);
        ys.add(yAxis2);
        List<Series> seriesArr = new ArrayList<Series>();
        Series series1 = new Series();
        series1.setName("合同金额");
        series1.setType("bar");
        List<BigDecimal> value1 = new ArrayList<BigDecimal>();
        for(int j=0;j<list.size();j++){
        	value1.add(list.get(j).getMoney());
        }
        series1.setData(value1);
        series1.setBarWidth(30);
        Series series2 = new Series();
        series2.setName("审价金额");
        series2.setType("bar");
        List<BigDecimal> value2 = new ArrayList<BigDecimal>();
        for(int k=0;k<list.size();k++){
        	value2.add(list.get(k).getAuditMoney());
        }
        series2.setData(value2);
        series2.setBarWidth(30);
        Series series3 = new Series();
        series3.setName("审减百分比");
        series3.setType("line");
        List<BigDecimal> value3 = new ArrayList<BigDecimal>();
        for(int m=0;m<list.size();m++){
        	value3.add(list.get(m).getSubtract());
        }
        series3.setSymbol("circle");
        series3.setSymbolSize("5");
        series3.setyAxisIndex(1);
        series3.setData(value3);
        seriesArr.add(series1);
        seriesArr.add(series2);
        seriesArr.add(series3);
        option.setTitle(title);
        option.setToolbox(toolbox);
        option.setTooltip(tooltip);
        option.setCalculable(true);
        option.setxAxis(xs);
        option.setyAxis(ys);
        option.setSeries(seriesArr);
        option.setLegend(legend);
		return option;
		
	}
	
	@RequestMapping("/view")
	public String view(Model model,Integer page,AppraisalContract appraisalContract){
		
		String name = appraisalContract.getName();
		String code = appraisalContract.getCode();
		String purchaseDepName = appraisalContract.getPurchaseDepName();
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(name!=null && !name.equals("")){
			map.put("name", "%"+name+"%");
		}
		if(code!=null && !code.equals("")){
			map.put("code", "%"+code+"%");
		}
		if(purchaseDepName!=null && !purchaseDepName.equals("")){
			map.put("purchaseDepName", "%"+purchaseDepName+"%");
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<AppraisalContract> list = appraisalContractService.selectAppraisal(map,page);
		for(AppraisalContract ac:list){
			Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(ac.getPurchaseDepName());
			if(org!=null){
				ac.setPurchaseDepName(org.getName());
			}else{
				ac.setPurchaseDepName("");
			}
			
		}
        // 查询所有采购机构
        List<Orgnization> purchaseOrgList = orgnizationServiceI.findPurchaseOrgByPosition(null);
        model.addAttribute("purchaseOrgList", purchaseOrgList);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		model.addAttribute("name", name);
		model.addAttribute("code", code);
		model.addAttribute("purchaseDepName", purchaseDepName);
		return "bss/sstps/statistical/list";
	}
	

}
