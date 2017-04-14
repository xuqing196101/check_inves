package bss.controller.ob;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.ui.Model;

import bss.dao.ob.OBResultsInfoMapper;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBResultSubtabulation;
import bss.model.ob.OBResultsInfo;
import bss.service.ob.OBProjectResultService;
import bss.service.ob.OBResultSubtabulationService;
import bss.util.BigDecimalUtils;

public class BiddingResultCommon {

	/**
	 * 获取竞价结果信息
	 * 
	 * @Title: getBiddingResultInfo
	 * @Description:
	 * @author Easong
	 * @param @param model
	 * @param @param projectId 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void getBiddingResultInfo(Model model, String projectId,
			OBResultsInfoMapper OBResultsInfoMapper,
			OBProjectResultService oBProjectResultService,
			OBResultSubtabulationService obResultSubtabulationService) {
		// 判断是否有第二次竞价
		List<String> biddingIdList = OBResultsInfoMapper
				.isSecondBidding(projectId);
		Boolean flag = true;
		if (biddingIdList != null && biddingIdList.size() > 0) {
			for (String string : biddingIdList) {
				if ("2".equals(string)) {
					flag = false;
				}
			}
		}
		List<OBProjectResult> list = oBProjectResultService
				.selResultByProjectId(projectId);
		Integer countProportion = 0;
		BigDecimal million = new BigDecimal(10000);
		if (list != null && list.size() > 0) {
			for (OBProjectResult obProjectResult : list) {
				if (obProjectResult != null) {
					if (obProjectResult.getStatus() == 1 || obProjectResult.getStatus() == 2) {
						obProjectResult.setStatus(1);
						List<OBProjectResult> prolist = oBProjectResultService
								.selProportion(projectId,
										obProjectResult.getSupplierId());
						if (prolist != null && prolist.size() == 1) {
							obProjectResult.setFirstproportion(prolist.get(0)
									.getProportion());
						}
						if (prolist != null && prolist.size() == 2) {
							obProjectResult.setFirstproportion(prolist.get(0)
									.getProportion());
							obProjectResult.setSecondproportion(prolist.get(1)
									.getProportion());
						}
						List<OBResultSubtabulation> obResultSubtabulation = obResultSubtabulationService
								.selectByProjectIdAndSupplierId(projectId,
										obProjectResult.getSupplierId());
						if (obResultSubtabulation != null
								&& obResultSubtabulation.size() > 0) {
							for (OBResultSubtabulation obResultSubtabulation2 : obResultSubtabulation) {
								if (obResultSubtabulation2 != null) {
									obResultSubtabulation2
											.setTotalMoney(BigDecimalUtils.getSignalDecimalScale4(
													obResultSubtabulation2
															.getTotalMoney(),
													million));
								}
							}
						}
						obProjectResult
								.setObResultSubtabulation(obResultSubtabulation);
						countProportion += Integer.parseInt(obProjectResult
								.getProportion());
						List<OBResultsInfo> listinf = OBResultsInfoMapper
								.selectResult(projectId,
										obProjectResult.getSupplierId());
						obProjectResult.setOBResultsInfo(listinf);
					} else {
						List<OBResultsInfo> listinf = OBResultsInfoMapper
								.selectResult(projectId,
										obProjectResult.getSupplierId());
						obProjectResult.setOBResultsInfo(listinf);
					}
				}
			}
		}
		model.addAttribute("flag", flag);
		model.addAttribute("listres", list);
		model.addAttribute("countProportion", countProportion);
		model.addAttribute("size", list.size());
		model.addAttribute("projectId", projectId);

	}
}
