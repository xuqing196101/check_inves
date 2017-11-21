package bss.controller.ppms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.AdviceMessages;
import bss.model.ppms.PackageAdvice;
import bss.model.ppms.Packages;
import bss.service.ppms.AdviceMessagesService;
import bss.service.ppms.PackageAdviceService;
import bss.service.ppms.PackageService;
import bss.service.ppms.TerminationService;
import bss.util.comet.Comet;
import bss.util.comet.CometUtil;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

/**
 * 
 *  
 * <p>
 * Title:PackageAdviceController 
 * </p>
 *  
 * <p>
 * Description: 中止/转竞谈 审核
 * </p>
 *  @author FengTian  @date 2017-10-25上午10:08:07
 */
@Controller
@Scope("prototype")
@RequestMapping("/packageAdvice")
public class PackageAdviceController extends BaseController {

	@Autowired
	private PackageAdviceService service;

	@Autowired
	private PackageService packageService;

	@Autowired
	private TerminationService terminationService;

	@Autowired
	private AdviceMessagesService adviceMessagesService;

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
	public String list(@CurrentUser User user, Model model, Integer page, PackageAdvice packageAdvice, Integer type, String proposer) {
		if (user != null && StringUtils.isNotBlank(user.getTypeName()) && user.getOrg() != null) {
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
	public String saveAudit(@CurrentUser User user, String projectId, String packageIds, String advice, String flowDefineId, String auditCode, String type) {
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(packageIds) && StringUtils.isNotBlank(type) && StringUtils.isNotBlank(flowDefineId) && StringUtils.isNotBlank(auditCode)) {
			if (StringUtils.isNotBlank(advice)) {
				/*
				 * List<UploadFile> uploadFiles =
				 * uploadService.getFilesOther(auditCode,
				 * DictionaryDataUtil.getId("ZJTFJ"), "2"); if (uploadFiles !=
				 * null && !uploadFiles.isEmpty()) {
				 * 
				 * } else { return ERRO_FILE; }
				 */
				service.savaAudit(projectId, packageIds, advice, flowDefineId, auditCode, type, user.getId());
				return StaticVariables.SUCCESS;
			} else {
				return ERRO_ADVICE;
			}
		}
		return StaticVariables.FAILED;
	}

	/**
	 * 
	 * @Title: audit
	 * @author FengTian
	 * @date 2017-11-7 上午10:40:40
	 * @Description: 去审核页面
	 * @param @param code
	 * @param @param status
	 * @param @param model
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/audit")
	public String audit(String code, String status, Model model) {
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

	/**
	 * 
	 * @Title: pass
	 * @author FengTian
	 * @date 2017-11-7 上午10:40:27
	 * @Description: 审核通过
	 * @param @param user
	 * @param @param code
	 * @param @param projectId
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/pass")
	@ResponseBody
	public String pass(@CurrentUser User user, String code, String projectId, String packId) {
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(code) && StringUtils.isNotBlank(packId)) {
			String[] packIds = packId.split(StaticVariables.COMMA_SPLLIT);
			String currentFlowDefineId = null;
			for (String string : packIds) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("code", code);
				map.put("packageId", string);
				List<PackageAdvice> find = service.find(map);
				if (find != null && !find.isEmpty()) {
					for (PackageAdvice advice : find) {
						currentFlowDefineId = advice.getFlowDefineId();
					}
					service.update(user, find, null, 3);
				}
			}
			if (currentFlowDefineId != null) {
				/*
				 * terminationService.updateTermination(packId, projectId,
				 * currentFlowDefineId, currentFlowDefineId, "JZXTP");
				 */
				return StaticVariables.SUCCESS;
			}
		}
		return StaticVariables.FAILED;
	}

	/**
	 * 
	 * @Title: noPass
	 * @author FengTian
	 * @date 2017-11-7 上午10:40:18
	 * @Description: 审核不通过
	 * @param @param user
	 * @param @param code
	 * @param @param projectId
	 * @param @param removedReason
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/noPass", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String noPass(@CurrentUser User user, String code, String projectId, String removedReason, String packId) {
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(code) && StringUtils.isNotBlank(removedReason) && StringUtils.isNotBlank(packId)) {
			String[] packIds = packId.split(StaticVariables.COMMA_SPLLIT);
			for (String string : packIds) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("code", code);
				map.put("packageId", string);
				List<PackageAdvice> find = service.find(map);
				if (find != null && !find.isEmpty()) {
					service.update(user, find, removedReason, 4);
				}
				Packages packages = packageService.selectByPrimaryKeyId(string);
				if (packages != null) {
					packages.setProjectStatus(DictionaryDataUtil.getId("ZJTSHBTG"));
					packageService.updateByPrimaryKeySelective(packages);
				}
			}
			return StaticVariables.SUCCESS;
		} else {
			return StaticVariables.FAILED;
		}
	}

	@RequestMapping("/auditFile")
	public String auditFile(String pachageIds, String projectId, String type, String currHuanjieId, Model model) {
		model.addAttribute("pachageIds", pachageIds);
		model.addAttribute("projectId", projectId);
		model.addAttribute("auditJZXTP", DictionaryDataUtil.getId("ZJTFJ"));
		model.addAttribute("auditZZFJ", DictionaryDataUtil.getId("ZZFJ"));
		model.addAttribute("auditCode", WfUtil.createUUID());
		model.addAttribute("currHuanjieId", currHuanjieId);
		model.addAttribute("type", type);
		return "bss/ppms/packageAdvice/upload";
	}

	@RequestMapping("/recheck")
	@ResponseBody
	public String recheck(String packageIds) {
		if (StringUtils.isNotBlank(packageIds)) {
			service.recheck(packageIds);
			return StaticVariables.SUCCESS;
		} else {
			return StaticVariables.FAILED;
		}
	}

	@RequestMapping("/comet")
	@ResponseBody
	public String comet(@CurrentUser User user, String projectId, String proposer, String code) {
		String createUUID = WfUtil.createUUID();
		Comet comet = new Comet();
		comet.setMsgData(proposer + "," + createUUID);
		new CometUtil().pushToAll(comet);
		AdviceMessages adviceMessages = new AdviceMessages();
		adviceMessages.setId(createUUID);
		adviceMessages.setProjectId(projectId);
		adviceMessages.setManagers(proposer);
		adviceMessages.setSender(user.getId());
		adviceMessages.setCode(code);
		adviceMessages.setIsDelete((short) 0);
		adviceMessages.setStatus((short) 0);
		adviceMessages.setCreateAt(new Date());
		adviceMessagesService.insertSelective(adviceMessages);
		return StaticVariables.SUCCESS;
	}

	@RequestMapping("/cometPackage")
	public void cometPackage(String cometId, HttpServletResponse response) {
		AdviceMessages ams = adviceMessagesService.selectByPrimaryKey(cometId);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", ams.getCode());
		map.put("type", 2);
		List<PackageAdvice> PackageAdvices = service.find(map);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (PackageAdvices != null && PackageAdvices.size() > 0) {
			if (PackageAdvices.size() == 1) {
				String status = "";
				// 通过
				if (PackageAdvices.get(0).getStatus() == 3) {
					status = "1";
				} else if (PackageAdvices.get(0).getStatus() == 4) {
					status = "0";
				}
				Packages pack = packageService.selectByPrimaryKeyId(PackageAdvices.get(0).getPackageId());
				sb.append("{\"status\":\"" + status + "\",");
				sb.append("\"packages\":[{\"id\":\"" + pack.getId() + "\",\"name\":\"" + pack.getName() + "\"}]}]");
			} else {
				List<PackageAdvice> packAdviceCount = service.selectByStatus(ams.getCode());
				if (packAdviceCount != null && packAdviceCount.size() == 1) {
					String status = "";
					// 通过
					if (PackageAdvices.get(0).getStatus() == 3) {
						status = "1";
					} else if (PackageAdvices.get(0).getStatus() == 4) {
						status = "0";
					}
					sb.append("{\"status\":\"" + status + "\",\"packages\":[");
					for (PackageAdvice pa : PackageAdvices) {
						Packages pack = packageService.selectByPrimaryKeyId(pa.getPackageId());
						sb.append("{\"id\":\"" + pack.getId() + "\",\"name\":\"" + pack.getName() + "\"},");
					}
					if (sb.length() > 1) {
						sb = sb.deleteCharAt(sb.length() - 1);
					}
					sb.append("]}]");
				} else {
					StringBuffer sb1 = new StringBuffer();
					sb1.append("\"package1\":[");
					StringBuffer sb2 = new StringBuffer();
					sb2.append("\"package2\":[");
					sb.append("{\"status\":\"2\",");
					for (PackageAdvice pa : PackageAdvices) {
						Packages pack = packageService.selectByPrimaryKeyId(pa.getPackageId());
						// 通过
						if (pa.getStatus() == 3) {
							sb1.append("{\"id\":\"" + pack.getId() + "\",\"name\":\"" + pack.getName() + "\"},");
						} else if (pa.getStatus() == 4) {
							sb2.append("{\"id\":\"" + pack.getId() + "\",\"name\":\"" + pack.getName() + "\"},");
						}
					}
					if (sb1.length() > 1) {
						sb1 = sb1.deleteCharAt(sb1.length() - 1);
					}
					sb1.append("]");
					if (sb2.length() > 1) {
						sb2 = sb2.deleteCharAt(sb2.length() - 1);
					}
					sb2.append("]");
					sb = sb.append(sb1.append(",").append(sb2).append("}]"));
				}
			}
		}
		System.out.println(sb.toString());
		super.printOutMsg(response, sb.toString());
	}

	@RequestMapping("/cometPassCheck")
	public void cometPassCheck(String cometId, HttpServletResponse response, Integer type) {
		AdviceMessages ams = adviceMessagesService.selectByPrimaryKey(cometId);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", ams.getCode());
		map.put("type", 2);
		if (type == 1) {
			map.put("status", 3);
		} else {
			map.put("status", 4);
		}
		List<PackageAdvice> PackageAdvices = service.find(map);
		List<Packages> listPackage = new ArrayList<Packages>();
		for (PackageAdvice pa : PackageAdvices) {
			Packages pack = packageService.selectByPrimaryKeyId(pa.getPackageId());
			if (pack.getProjectStatus().equals(DictionaryDataUtil.getId("ZJZXTP")) || pack.getProjectStatus().equals(DictionaryDataUtil.getId("YZZ"))) {
				continue;
			}
			Packages packages = new Packages();
			packages.setId(pack.getId());
			packages.setName(pack.getName());
			listPackage.add(packages);
		}
		if (listPackage != null && listPackage.size() > 0) {
			super.writeJson(response, listPackage);
		} else {
			super.printOutMsg(response, "[{\"msg\":\"no\"}]");
		}

	}

	@RequestMapping("/cometSubmit")
	public void cometSubmit(String cometId, HttpServletResponse response, Integer type, String packs) {
		if (packs != null) {
			AdviceMessages ams = adviceMessagesService.selectByPrimaryKey(cometId);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("code", ams.getCode());
			map.put("type", 2);
			List<PackageAdvice> PackageAdvices = service.find(map);
			if (type == 1) {// 转竞谈
				terminationService.updateTermination(packs, ams.getProjectId(), PackageAdvices.get(0).getFlowDefineId(), PackageAdvices.get(0).getFlowDefineId(), "JZXTP");
				Boolean flg = false;
				for (PackageAdvice pa : PackageAdvices) {
					Packages pack = packageService.selectByPrimaryKeyId(pa.getPackageId());
					if (!pack.getProjectStatus().equals(DictionaryDataUtil.getId("ZJZXTP"))) {
						flg = true;
						break;
					}
				}
				if (!flg) {
					ams.setStatus((short) 1);
					adviceMessagesService.updateByPrimaryKeySelective(ams);
				}
			}

		}
		super.printOutMsg(response, "ok");
	}

}
