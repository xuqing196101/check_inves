package bss.controller.sstps;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.bms.LoginController;

import bss.model.sstps.AppraisalContract;
import bss.service.sstps.AppraisalContractService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

/**
* @Title:OfferContriller 
* @Description: 报价
* @author Shen Zhenfei
* @date 2016-10-10下午5:08:44
 */
@Controller
@Scope
@RequestMapping("/offer")
public class OfferContriller {
	
	@Autowired
	private AppraisalContractService appraisalContractService;
	
	private Logger logger = Logger.getLogger(LoginController.class); 
	
	@RequestMapping("/list")
	public String list(Model model,Integer page){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/supplier/list";
	}
	
	@RequestMapping("/checkList")
	public String checkList(Model model,Integer page){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/supplier/list";
	}
	
	@RequestMapping("/appraisalList")
	public String appraisalList(Model model,Integer page){
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "bss/sstps/offer/supplier/list";
	}

}
