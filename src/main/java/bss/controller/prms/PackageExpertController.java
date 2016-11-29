package bss.controller.prms;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.prms.ExpertScore;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.model.prms.ext.AuditModelExt;
import bss.model.prms.ext.ExpertSuppScore;
import bss.model.prms.ext.PackExpertExt;
import bss.model.prms.ext.SupplierExt;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.ExpertScoreService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.ReviewFirstAuditService;
import bss.service.prms.ReviewProgressService;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Quote;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.sms.SupplierQuoteService;

@Controller
@RequestMapping("packageExpert")
public class PackageExpertController {
	private final static int ONE = 1;
	private final static short SONE = 1;
	@Autowired
	private PackageExpertService service;
	@Autowired
	private ProjectDetailService detailService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectExtractService projectExtractService;
	@Autowired
	private SaleTenderService saleTenderService;// 供应商查询
	@Autowired
	private ReviewProgressService reviewProgressService;// 进度
	@Autowired
	private ExpertService expertService;// 专家
	@Autowired
	private ReviewFirstAuditService reviewFirstAuditService;// 初审表
	@Autowired
	private PackageExpertService packageExpertService;// 专家项目包 关联表
	@Autowired
	private SupplierQuoteService supplierQuoteService;// 供应商报价
	@Autowired
	private ExpertScoreService expertScoreService;// 专家评分
	@Autowired
	private AduitQuotaService aduitQuotaService;// 评分

	/**
	 * 
	 * @Title: toPackageExpert
	 * @author ShaoYangYang
	 * @date 2016年10月18日 下午3:05:52
	 * @Description: TODO 跳转到组织专家评审页面
	 * @param @param projectId
	 * @param @return
	 * @return String
	 */
	@RequestMapping("toPackageExpert")
	public String toPackageExpert(String projectId, String flag, Model model) {
		// 项目分包信息
		HashMap<String, Object> pack = new HashMap<String, Object>();
		pack.put("projectId", projectId);
		List<Packages> packages = packageService.findPackageById(pack);
		Map<String, Object> list = new HashMap<String, Object>();
		// 关联表集合
		List<PackageExpert> expertIdList = new ArrayList<>();
		// 进度集合
		List<ReviewProgress> reviewProgressList = new ArrayList<>();
		List<ExpertSuppScore> expertScoreAll = new ArrayList<>();
		List<AuditModelExt> auditModelListAll = new ArrayList<>();
		Map<String, Object> mapSearch = new HashMap<String, Object>();
		for (Packages ps : packages) {
			list.put("pack" + ps.getId(), ps);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("packageId", ps.getId());
			List<ProjectDetail> detailList = detailService.selectById(map);
			ps.setProjectDetails(detailList);
			// 设置查询条件
			mapSearch.put("projectId", projectId);
			mapSearch.put("packageId", ps.getId());
			// 查询出该项目下的包关联的信息集合
			List<PackageExpert> selectList = service.selectList(mapSearch);
			// 查询评审项
			List<AuditModelExt> auditModelExtList = aduitQuotaService
					.findAllByMap(mapSearch);
			auditModelListAll.addAll(auditModelExtList);
			// 查询评分信息
			//List<ExpertScore> expertList = expertScoreService.selectByMap(mapSearch);
			// 查询评分信息(由按项目查改为按供应商和包查)
			List<ExpertSuppScore> expertList = expertScoreService.getScoreByMap(mapSearch);
			expertScoreAll.addAll(expertList);
			// 查询进度
			List<ReviewProgress> selectByMap = reviewProgressService
					.selectByMap(map); 
			expertIdList.addAll(selectList);
			reviewProgressList.addAll(selectByMap);
		}
		model.addAttribute("expertIdList", expertIdList);
		// 进度
		model.addAttribute("reviewProgressList", reviewProgressList);
		// 供应商信息
		List<SaleTender> supplierList = saleTenderService.list(new SaleTender(
				projectId), 0);
		model.addAttribute("supplierList", supplierList);
		// 查询条件
		ProjectExtract projectExtract = new ProjectExtract();
		projectExtract.setProjectId(projectId);
		projectExtract.setReason("1");
		// 项目抽取的专家信息
		List<ProjectExtract> expertList = projectExtractService
				.list(projectExtract);
		model.addAttribute("expertList", expertList);
		// 包信息
		model.addAttribute("packageList", packages);
		Project project = projectService.selectById(projectId);
		// 项目实体
		model.addAttribute("project", project);
		// 关联信息集合
		// 封装实体
		List<PackExpertExt> packExpertExtList = new ArrayList<>();
		// 供应商封装实体
		List<SupplierExt> supplierExtList = new ArrayList<>();
		PackExpertExt packExpertExt;
		for (PackageExpert packageExpert : expertIdList) {
			packExpertExt = new PackExpertExt();
			Expert expert = expertService.selectByPrimaryKey(packageExpert
					.getExpertId());
			packExpertExt.setExpert(expert);
			packExpertExt.setPackageId(packageExpert.getPackageId());
			packExpertExt.setProjectId(packageExpert.getProjectId());
			Map<String, Object> map = new HashMap<>();
			// 根据专家id和包id查询改包的这个专家是否评审完成
			map.put("expertId", packageExpert.getExpertId());
			map.put("packageId", packageExpert.getPackageId());
			// 根据专家id查询关联表 确定该专家是否都已评审
			List<PackageExpert> selectList = service.selectList(map);
			int count = 0;
			for (PackageExpert packageExpert2 : selectList) {
				if (packageExpert2.getIsAudit() == SONE) {
					count++;
				}
			}
			if (count > 0) {
				packExpertExt.setIsPass("已评审");
			} else {
				packExpertExt.setIsPass("未评审");
			}
			// 根据供应商id 和包id查询审核表 确定该供应商是否通过评审
			for (SaleTender saleTender : supplierList) {
				SupplierExt supplierExt = new SupplierExt();
				Map<String, Object> map2 = new HashMap<>();
				map2.put("supplierId", saleTender.getSuppliers().getId());
				map2.put("packageId", packageExpert.getPackageId());
				map2.put("expertId", packageExpert.getExpertId());
				List<ReviewFirstAudit> selectList2 = reviewFirstAuditService
						.selectList(map2);
				if (selectList2 != null && selectList2.size() > 0) {
					int count2 = 0;
					for (ReviewFirstAudit reviewFirstAudit : selectList2) {
						if (reviewFirstAudit.getIsPass() == SONE) {
							count2++;
							break;
						}
					}
					// 如果变量大于0 说明有不合格的数据
					if (count2 > 0) {
						supplierExt.setSupplierId(saleTender.getSuppliers()
								.getId());
						supplierExt.setExpertId(packageExpert.getExpertId());
						supplierExt.setPackageId(packageExpert.getPackageId());
						supplierExt.setSuppIsPass("不合格");
					} else {
						supplierExt.setSupplierId(saleTender.getSuppliers()
								.getId());
						supplierExt.setExpertId(packageExpert.getExpertId());
						supplierExt.setPackageId(packageExpert.getPackageId());
						supplierExt.setSuppIsPass("合格");
					}
				} else {
					supplierExt
							.setSupplierId(saleTender.getSuppliers().getId());
					supplierExt.setPackageId(packageExpert.getPackageId());
					supplierExt.setExpertId(packageExpert.getExpertId());
					supplierExt.setSuppIsPass("未评审");
				}

				supplierExtList.add(supplierExt);
			}
			packExpertExtList.add(packExpertExt);
		}
		// 评审项信息
		removeAuditModelExt(auditModelListAll);
		model.addAttribute("auditModelListAll", auditModelListAll);
		model.addAttribute("packExpertExtList", packExpertExtList);
		model.addAttribute("supplierExtList", supplierExtList);
		model.addAttribute("expertScoreList", expertScoreAll);
		// 成功标示
		model.addAttribute("flag", flag);
		return "bss/prms/package_expert";
	}

	/**
	 * 
	 * @Title: removeAuditModelExt
	 * @author ShaoYangYang
	 * @date 2016年11月18日 下午3:18:44
	 * @Description: TODO 去重复
	 * @param @param list
	 * @return void
	 */
	private static void removeAuditModelExt(List<AuditModelExt> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getScoreModelId()
						.equals(list.get(i).getScoreModelId())) {
					list.remove(j);
				}
			}
		}
	}

	/**
	 * 
	 * @Title: relate
	 * @author ShaoYangYang
	 * @date 2016年10月18日 下午2:30:52
	 * @Description: TODO
	 * @param @param chkItem 专家id ，拼接的
	 * @param @param packageExpert 关联实体
	 * @param @param groupId 组长专家id
	 * @param @return
	 * @return String
	 */
	@RequestMapping("relate")
	public String relate(String chkItem, PackageExpert packageExpert,
			String groupId, RedirectAttributes attr) {
		service.deleteByPackageId(packageExpert.getPackageId());
		// 设置变量判断是否操作错误
		int count = 0;
		if (StringUtils.isNotEmpty(chkItem) && StringUtils.isNotEmpty(groupId)) {
			String[] expertIds = chkItem.split(",");
			Set<String> set = new HashSet<>();
			// 去除重复 的专家id
			for (int i = 0; i < expertIds.length; i++) {
				set.add(expertIds[i]);
			}
			// 迭代set集合
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String expertId = (String) iterator.next();
				// 设置专家id
				packageExpert.setExpertId(expertId);
				// 评审状态 未评审
				packageExpert.setIsAudit((short) 0);
				// 初审是否汇总 未汇总
				packageExpert.setIsGather((short) 0);
				// 是否评分
				packageExpert.setIsGrade((short) 0);
				// 评分是否汇总
				packageExpert.setIsGatherGather((short) 0);
				// 判断组长id是否和选择的专家id一致，如果一致就设定为组长
				if (groupId.equals(expertId)) {
					packageExpert.setIsGroupLeader((short) 1);
					count++;
				} else {
					packageExpert.setIsGroupLeader((short) 0);
				}
				service.save(packageExpert);
			}
			// 判断如果count等于0 说明选择的组长不在选择的专家中不对 ，为错误操作
			if (count == 0) {
				attr.addAttribute("flag", "error");
				service.deleteByPackageId(packageExpert.getPackageId());
			} else {
				attr.addAttribute("flag", "success");
			}

		} else {
			attr.addAttribute("flag", "error");
		}
		attr.addAttribute("projectId", packageExpert.getProjectId());
		return "redirect:toPackageExpert.html";
	}

	/**
	 * 
	 * @Title: getPace
	 * @author ShaoYangYang
	 * @date 2016年10月27日 下午8:13:46
	 * @Description: TODO 符合汇总
	 * @param @param projectId
	 * @param @param packageId
	 * @param @param expertId
	 * @return void
	 */
	@RequestMapping("gather")
	@ResponseBody
	public void getPace(PackageExpert record, HttpServletResponse response) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("expertId", record.getExpertId());
			map.put("projectId", record.getProjectId());
			map.put("packageId", record.getPackageId());
			// 查询包关联专家实体
			List<PackageExpert> selectList = packageExpertService
					.selectList(map);
			if (selectList != null && selectList.size() > 0) {
				PackageExpert packageExpert = selectList.get(0);
				// 判断为审核过的 和未汇总的 才执行汇总
				if (packageExpert.getIsAudit() == SONE
						&& packageExpert.getIsGather() != SONE) {
					record.setIsGather((short) 1);
					service.updateByBean(record);
					response.getWriter().print("已汇总！");
				} else {
					response.getWriter().print("不能汇总！");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: isBack
	 * @author ShaoYangYang
	 * @date 2016年10月27日 下午8:13:46
	 * @Description: TODO 初审退回
	 * @param @param projectId
	 * @param @param packageId
	 * @param @param expertId
	 * @return void
	 */
	@RequestMapping("isBack")
	@ResponseBody
	public void isBack(PackageExpert record, HttpServletResponse response) {
		try {
			// 查询是否已评审
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("expertId", record.getExpertId());
			map.put("packageId", record.getPackageId());
			map.put("projectId", record.getProjectId());
			List<PackageExpert> selectList = service.selectList(map);
			if (selectList != null && selectList.size() > 0) {
				PackageExpert packageExpert = selectList.get(0);
				// 必须是已评审 但未评分的数据才能退回
				if (packageExpert.getIsAudit() != SONE
						|| packageExpert.getIsGrade() == SONE) {

					response.getWriter().print("0");
				} else {
					// 初审结果集合
					List<ReviewFirstAudit> reviewFIrstAuditList = reviewFirstAuditService
							.selectList(map);
					// 判断是否全部通过，如果全部通过则不允许退回
					int count = 0;
					for (ReviewFirstAudit reviewFirstAudit : reviewFIrstAuditList) {
						// 为1 证明有不合格数据
						if (reviewFirstAudit.getIsPass() == SONE) {
							count++;
						}
					}
					if (count == 0) {
						response.getWriter().print("0");
						return;
					}
					// 查询是否已评审
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("packageId", record.getPackageId());
					map2.put("projectId", record.getProjectId());
					// 该专家下的审核项目关联集合
					List<PackageExpert> packageExpertList = packageExpertService
							.selectList(map2);
					// 判断是否为全部已评审状态
					for (PackageExpert packageExpert2 : packageExpertList) {
						if (packageExpert2.getIsAudit() == SONE) {
							// 查询改项目的进度信息
							List<ReviewProgress> reviewProgressList = reviewProgressService
									.selectByMap(map2);
							// 更新项目进度
							if (reviewProgressList != null
									&& reviewProgressList.size() > 0) {
								ReviewProgress progress = reviewProgressList
										.get(0);
								// 修改进度
								if (packageExpertList != null
										&& packageExpertList.size() > 0) {
									// 计算当前专家占用的进度比重
									double first = 1 / (double) packageExpertList
											.size();
									BigDecimal b = new BigDecimal(first);
									double firstProgress = b.setScale(2,
											BigDecimal.ROUND_HALF_UP)
											.doubleValue();
									// 计算退回后的初审进度
									Double firstAuditProgress = progress
											.getFirstAuditProgress();
									// 最终进度
									double endFirst = firstAuditProgress
											- firstProgress;
									// 退回后的初审进度
									progress.setFirstAuditProgress(endFirst);
									// 初审退回 评分进度清空 重新评分
									// progress.setScoreProgress((double) 0.0);
									// 总进度比例
									double totalProgress = (firstProgress + progress
											.getScoreProgress()) / 2;
									// 当前总进度
									Double totalProgress2 = progress
											.getTotalProgress();
									// 计算退回之后的总进度
									progress.setTotalProgress(totalProgress2
											- totalProgress);

									// 修改
									reviewProgressService
											.updateByPrimaryKeySelective(progress);
								}
							}
						}
					}
					Short flag = 0;
					record.setIsGather(flag);
					record.setIsAudit(flag);
					service.updateByBean(record);
					response.getWriter().print("1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @Title: isBackScore
	 * @author ShaoYangYang
	 * @date 2016年10月27日 下午8:13:46
	 * @Description: TODO 评分确认或退回
	 * @param @param projectId
	 * @param @param packageId
	 * @param @param expertId
	 * @return void
	 */
	@RequestMapping("isBackScore")
	@ResponseBody
	public void isBackScore(String projectId, String packageId,
			String supplierId, String scoreModelId, Integer flag,
			HttpServletResponse response) {
		try {
			// 查询是否已评审
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectId", projectId);
			map.put("packageId", packageId);
			// 1为退回
			if (flag == ONE) {
				// 判断能不能退回
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("projectId", projectId);
				map2.put("packageId", packageId);
				map2.put("supplierId", supplierId);
				map2.put("scoreModelId", scoreModelId);
				List<ExpertScore> expertScoreList = expertScoreService
						.selectByMap(map2);
				if (expertScoreList == null || expertScoreList.size() == 0) {
					// 为空证明没有评分数据 则不能退回
					response.getWriter().print("tuihui");
					return;
				} else {
					for (ExpertScore expertScore : expertScoreList) {
						BigDecimal score = expertScore.getScore();
						// 如果有没有得分数据的也证明没有评分
						if (score == null) {
							response.getWriter().print("tuihui");
							return;
						}
					}
				}
				// 修改进度
				reviewProgressService.updateProgress(map);
				// 修改评分状态为 未评分
				packageExpertService.updateScore(map);
				// 修改AduitQuota的round状态为退回
				aduitQuotaService.updateStatus(projectId, packageId,
						supplierId, scoreModelId, flag);
				response.getWriter().print("tuihuisuccess");
			} else {
				// 确认 保存最终得分 分数不一致则不执行保存
				String message = aduitQuotaService.updateStatus(projectId,
						packageId, supplierId, scoreModelId, flag);
				response.getWriter().print(message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @Title: supplierQuote
	 * @author ShaoYangYang
	 * @date 2016年11月11日 下午2:46:47
	 * @Description: TODO 查看供应商报价
	 * @param @param packageId
	 * @param @param projectId
	 * @param @return
	 * @return String
	 */
	@RequestMapping("supplierQuote")
	public String supplierQuote(String projectId, String supplierId,
			Model model, Quote quote, String timestamp) {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("projectId", projectId);
			List<Packages> listPackage = supplierQuoteService
					.selectByPrimaryKey(map, null);
			List<Packages> listPackageEach = new ArrayList<Packages>();
			SaleTender saleTender = new SaleTender();
			saleTender.setProjectId(projectId);
			saleTender.setSupplierId(supplierId);
			List<SaleTender> sts = saleTenderService.find(saleTender);
			if (sts != null && sts.size() > 0) {
				String packageStr = sts.get(0).getPackages();
				for (Packages packages : listPackage) {
					if (packageStr.indexOf(packages.getId()) != -1) {
						listPackageEach.add(packages);
					}
				}
			}
			List<List<Quote>> listQuote = new ArrayList<List<Quote>>();
			// 查询时间
			Quote quote2 = new Quote();
			quote2.setSupplierId(supplierId);
			quote.setProjectId(projectId);
			List<Date> listDate = supplierQuoteService.selectQuoteCount(quote2);
			for (Packages pk : listPackageEach) {
				if (StringUtils.isNotEmpty(timestamp)) {
					// 如果传递时间 就按照时间查询
					quote.setCreatedAt(new Timestamp(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
				} else {
					if (listDate != null && listDate.size() > 0) {
						// 否则就查询最后一次报价
						Date date = listDate.get(listDate.size() - 1);
						// SimpleDateFormat sdf = new
						// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// String formatDate = sdf.format(date);
						Timestamp timestamp2 = new Timestamp(date.getTime());
						quote.setCreatedAt(timestamp2);
					}
				}
				quote.setPackageId(pk.getId());
				List<Quote> quoteList = supplierQuoteService
						.selectQuoteHistoryList(quote);
				listQuote.add(quoteList);
			}
			model.addAttribute("listPackage", listPackageEach);
			model.addAttribute("listQuote", listQuote);
			model.addAttribute("listDate", listDate);
			model.addAttribute("length", listDate.size());
			model.addAttribute("projectId", projectId);
			model.addAttribute("supplierId", supplierId);
			if (timestamp != null) {
				model.addAttribute("timestamp", new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").parse(timestamp));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bss/prms/view_quote";
	}

	/**
	 * 
	 * 〈简述〉评分汇总 〈详细描述〉
	 * 
	 * @author this'me
	 * @param packageId
	 * @param projectId
	 * @return
	 */
	@RequestMapping("scoreTotal")
	@ResponseBody
	public void scoreTotal(String packageId, String projectId, String expertId) {
		expertScoreService.gather(packageId, projectId, expertId);
	}

}
