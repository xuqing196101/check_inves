package bss.controller.pms;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ses.model.bms.DictionaryData;
import ses.service.bms.DictionaryDataServiceI;
import bss.model.pms.AuditParam;
import bss.service.pms.AuditParameService;

import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title: AuditParamController
 * @Description: 审核参数设置 
 * @author Li Xiaoxiao
 * @date  2016年11月2日,上午9:54:42
 *
 */
@Controller
@RequestMapping("/param")
public class AuditParamController {

	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private AuditParameService auditParameService; 
	
	@RequestMapping("/list")
	public String list(AuditParam auditParam,Model model,Integer page){
		DictionaryData	dictionaryData=new DictionaryData();
	
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
		model.addAttribute("dic", dic);
		
		List<AuditParam> list = auditParameService.query(auditParam, page==null?1:page);
		PageInfo<AuditParam> info = new PageInfo<AuditParam>(list);
		model.addAttribute("info", info);
		model.addAttribute("auditParam", auditParam);
		return "bss/pms/auditparam/list";
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		DictionaryData	dictionaryData=new DictionaryData();
		
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
		model.addAttribute("dic", dic);
		return "bss/pms/auditparam/add";
	}
	
	
	@RequestMapping("/edit")
	public String edit(String id,Model model,Integer page){
		DictionaryData	dictionaryData=new DictionaryData();
	
		List<DictionaryData> dic = dictionaryDataServiceI.findByKind("4");
		model.addAttribute("dic", dic);
		AuditParam param = auditParameService.queryById(id);
		model.addAttribute("aparam", param);
		model.addAttribute("page", page);
		return "bss/pms/auditparam/edit";
	}
	
	@RequestMapping("/save")
	public String save(AuditParam auditParam){
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		auditParam.setId(id);
		auditParameService.add(auditParam);
		return "redirect:list.html";
	}
	@RequestMapping("/update")
	public String update(AuditParam auditParam,Integer page,RedirectAttributes rattr){
		auditParameService.update(auditParam);
		rattr.addAttribute("page",page);
		return "redirect:list.html";
	}
	@RequestMapping("/deletes")
	public String deletes(String ids){
		if(ids!=null){
			String[] strs = ids.split(",");
			for(String s:strs){
				auditParameService.delete(s);	
			}
		}
		return "redirect:list.html";
	}
	
	
}
