package bss.controller.ppms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;

import bss.controller.base.BaseController;
import bss.model.ppms.PackageAdvice;
import bss.service.ppms.PackageAdviceService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.TerminationService;

/**
 * 
* <p>Title:PackageAdviceController </p>
* <p>Description: 中止/转竞谈 审核</p>
* @author FengTian
* @date 2017-10-25上午10:08:07
 */
@Controller
@Scope("prototype")
@RequestMapping("/packageAdvice")
public class PackageAdviceController extends BaseController {

	@Autowired
	private PackageAdviceService service;
	
	@Autowired
    private ProjectService projectService;
	
	@Autowired
    private PackageService packageService;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private TerminationService terminationService;
	
	/**
	 * 审核原因
	 */
	private static final String ERRO_ADVICE = "erroAdvice";
	
	/**
	 * 审核附件
	 */
	private static final String ERRO_FILE = "erroFile";
	
	/**
	 * 
	* @Title: list
	* @author FengTian 
	* @date 2017-10-25 上午10:11:44  
	* @Description: 审核列表 
	* @param @param user
	* @param @param model
	* @param @param page
	* @param @param packageAdvice
	* @param @return      
	* @return String
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User user, Model model, Integer page, PackageAdvice packageAdvice) {
		if(user != null && StringUtils.isNotBlank(user.getTypeName()) && user.getOrg() != null){
			if (page == null) {
				page = 1;
			}
			List<PackageAdvice> list = service.list(packageAdvice, user, page);
			if (list != null && !list.isEmpty()) {
				model.addAttribute("info", new PageInfo<PackageAdvice>(list));
				model.addAttribute("kind", DictionaryDataUtil.find(5));
				model.addAttribute("packageAdvice", packageAdvice);
			}
		}
		return "bss/ppms/packageAdvice/list";
	}
	
	@RequestMapping("/saveAudit")
	@ResponseBody
	public String saveAudit(String projectId, String packageIds, String advice, String flowDefineId, String auditCode, String type) {
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(packageIds) && StringUtils.isNotBlank(type)
				&& StringUtils.isNotBlank(flowDefineId) && StringUtils.isNotBlank(auditCode)) {
			if (StringUtils.isNotBlank(advice)) {
				/*List<UploadFile> uploadFiles = uploadService.getFilesOther(auditCode, DictionaryDataUtil.getId("ZJTFJ"), "2");
				if (uploadFiles != null && !uploadFiles.isEmpty()) {
					
				} else {
					return ERRO_FILE;
				}*/
				service.savaAudit(projectId, packageIds, advice, flowDefineId, auditCode, type);
				return StaticVariables.SUCCESS;
			} else {
				return ERRO_ADVICE;
			}
		}
		return StaticVariables.FAILED;
	}
	
	@RequestMapping("/audit")
	public String audit(String code, String status, Model model){
		if (StringUtils.isNotBlank(code)) {
			PackageAdvice advice = service.audit(code);
			if (advice != null) {
				BigDecimal budget = service.selectbudget(code);
				advice.setBudget(budget);
				model.addAttribute("auditJZXTP", DictionaryDataUtil.getId("ZJTFJ"));
				model.addAttribute("advice", advice);
				model.addAttribute("status", status);
			}
		}
		return "bss/ppms/packageAdvice/audit";
	}
	
	@RequestMapping("/pass")
	@ResponseBody
	public String pass(@CurrentUser User user, String code, String projectId){
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(code)) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("code", code);
			List<PackageAdvice> find = service.find(map);
			if (find != null && !find.isEmpty()) {
				List<String> packId = new ArrayList<String>();
				String currentFlowDefineId = null;
				for (PackageAdvice advice : find) {
					packId.add(advice.getPackageId());
					currentFlowDefineId = advice.getFlowDefineId();
				}
				if (packId != null && !packId.isEmpty() && currentFlowDefineId != null) {
					String packageIds = StringUtils.join(packId, StaticVariables.COMMA_SPLLIT);
					terminationService.updateTermination(packageIds, projectId, currentFlowDefineId, currentFlowDefineId, "JZXTP");
				}
				service.update(user, find, null, 3);
			}
			return StaticVariables.SUCCESS;
		} else {
			return StaticVariables.FAILED;
		}
	}
	
	@RequestMapping(value="/noPass",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String noPass(@CurrentUser User user, String code, String projectId, String removedReason){
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(code) && StringUtils.isNotBlank(removedReason)) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("code", code);
			List<PackageAdvice> find = service.find(map);
			service.update(user, find, removedReason, 4);
			return StaticVariables.SUCCESS;
		} else {
			return StaticVariables.FAILED;
		}
	}
	
	@RequestMapping("/auditFile")
	public String auditFile(String pachageIds, String projectId, String type, String currHuanjieId, Model model){
		model.addAttribute("pachageIds", pachageIds);
		model.addAttribute("projectId", projectId);
		model.addAttribute("auditJZXTP", DictionaryDataUtil.getId("ZJTFJ"));
		model.addAttribute("auditZZFJ", DictionaryDataUtil.getId("ZZFJ"));
		model.addAttribute("auditCode", WfUtil.createUUID());
		model.addAttribute("currHuanjieId", currHuanjieId);
		model.addAttribute("type", type);
		return "bss/ppms/packageAdvice/upload";
	}
}
