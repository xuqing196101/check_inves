package dss.controller.rids;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeBigDecimal;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierEditService;
import bss.formbean.Maps;

import com.alibaba.fastjson.JSON;
import common.annotation.SystemControllerLog;
import common.annotation.SystemServiceLog;

import dss.service.rids.PurchaseResourceAnalyzeService;

/**
 * 
 * Description: 采购资源展示Controller
 * 
 * @author Easong
 * @version 2017年5月22日
 * @since JDK1.7
 */
@Controller
@RequestMapping("/resAnalyze")
public class PurchaseResourceAnalyzeController {

	// 注入供应商审核接口Service
	@Autowired
	private SupplierAuditService supplierAuditService;

	// 注入供应商变更信息Service
	@Autowired
	private SupplierEditService supplierEditService;

	// 注入采购资源Service
	@Autowired
	private PurchaseResourceAnalyzeService purchaseResourceAnalyzeService;
	
	/**
	 * 
	 * Description:采购资源展示模块可
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @return
	 */
	@RequestMapping("/list")
	public String list() {
		return "dss/rids/analyze/list";
	}

	/**
	 * 
	 * Description:进入入库供应商统计界面
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-供应商", operType=1)
	@SystemServiceLog(description="采购资源展示-供应商", operType=1)
	@RequestMapping("/analyzeSuppliers")
	public String analyzeSupplier(Supplier sup, Model model) {
		List<Supplier> listSupplier = supplierAuditService.querySupplierbytypeAndCategoryIds(sup, null);

		Map<String, Integer> map = supplierEditService.getMap();
		Integer maxCount = 0;
		Integer totalCount = 0;
		if(listSupplier != null){
			totalCount = listSupplier.size();
			for (Supplier supplier : listSupplier) {
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					if (supplier.getName() != null
							&& !"".equals(supplier.getName())
							&& supplier.getName().indexOf(entry.getKey()) != -1) {
						map.put((String) entry.getKey(),
								(Integer) map.get(entry.getKey()) + 1);
						if (maxCount < map.get(entry.getKey())) {
							maxCount = map.get(entry.getKey());
						}
						break;
					}
				}
			}			
		}
		if (maxCount == 0) {
			maxCount = 2500;
		}
		List<Maps> listMap = new LinkedList<Maps>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			Maps mp = new Maps();
			mp.setValue(new BigDecimal(entry.getValue()));
			mp.setName(entry.getKey());
			listMap.add(mp);
		}
		String json = JSON.toJSONString(listMap);
		model.addAttribute("listMap", listMap);
		model.addAttribute("data", json);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("maxCount", maxCount);
		return "dss/rids/analyze/analyzeSupplier";
	}

	/**
	 * 
	 * Description: 统计个供应商类型数量 物资销售、物资生产、工程、服务
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-供应商", operType=1)
	@SystemServiceLog(description="采购资源展示-供应商", operType=1)
	@RequestMapping("/analyzeSupplierCateType")
	@ResponseBody
	public List<AnalyzeBigDecimal> analyzeSupplierCateType() {
		return purchaseResourceAnalyzeService.findAnalyzeSupplierCateType();
	}
	
	/**
	 * 
	 * Description: 统计供应商企业类型   国企、其他
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-供应商", operType=1)
	@SystemServiceLog(description="采购资源展示-供应商", operType=1)
	@RequestMapping("/analyzeSupplierByNature")
	@ResponseBody
	public List<AnalyzeBigDecimal> analyzeSupplierByNature(){
		return purchaseResourceAnalyzeService.findanalyzeSupplierByNature();
	}
	
	/**
	 * 
	 * Description: 统计不同组织机构下的供应商
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-供应商", operType=1)
	@SystemServiceLog(description="采购资源展示-供应商", operType=1)
	@RequestMapping("/selectSupByOrg")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectSupByOrgS(){
		return purchaseResourceAnalyzeService.selectSupByOrg();
	}
	
	/**
	 * 
	 * Description: 进入入库专家统计界面
	 * 
	 * @author Easong
	 * @version 2017年5月27日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-专家", operType=3)
	@SystemServiceLog(description="采购资源展示-专家", operType=3)
	@RequestMapping("/analyzeExperts")
	public String analyzeExpert(Model model){
		List<AnalyzeBigDecimal> list = purchaseResourceAnalyzeService.selectExpertsByArea();
		String json = JSON.toJSONString(list);
		BigDecimal maxCount = new BigDecimal(0);
		model.addAttribute("data", json);
		if(list != null && !list.isEmpty()){
			AnalyzeBigDecimal analy = list.get(0);
			maxCount = analy.getValue();
			for (AnalyzeBigDecimal analyze : list) {
				if(analyze.getValue().compareTo(maxCount) == 1){
					maxCount = analyze.getValue();
				}
				
			}
		}
		// 查询入库专家数量
		Long totalCount = purchaseResourceAnalyzeService.selectStoreExpertCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("maxCount", maxCount);
		return "dss/rids/analyze/analyzeExpert";
	}
	
	/**
	 * 
	 * Description: 根据TYPE_ID查询专家所属各类型数量：
  	 * 1、物资技术  2、工程技术 3、服务技术 4、物资服务经济 5、工程经济
	 * 
	 * @author Easong
	 * @version 2017年5月27日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-专家", operType=3)
	@SystemServiceLog(description="采购资源展示-专家", operType=3)
	@RequestMapping("/selectExpertCountByCategory")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectExpertCountByCategorys(){
		return purchaseResourceAnalyzeService.selectExpertCountByCategory();
	}
	
	/**
	 * 
	 * Description: 查询军地专家数量  分为：军队、地方
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-专家", operType=3)
	@SystemServiceLog(description="采购资源展示-专家", operType=3)
	@RequestMapping("/selectExpertsCountByArmyType")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectExpertsCountByArmyTypes(){
		return purchaseResourceAnalyzeService.selectExpertsCountByArmyType();
	}
	
	/**
	 * 
	 * Description: 统计不同组织机构下的专家
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-专家", operType=3)
	@SystemServiceLog(description="采购资源展示-专家", operType=3)
	@RequestMapping("/selectExpByOrg")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectExpByOrgS(){
		return purchaseResourceAnalyzeService.selectExpByOrg();
	}
	
	/**
	 * 
	 * Description: 进入采购机构统计界面
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购机构", operType=2)
	@SystemServiceLog(description="采购资源展示-采购机构", operType=2)
	@RequestMapping("/analyzeOrgs")
	public String analyzeOrg(Model model){
		List<AnalyzeBigDecimal> list = purchaseResourceAnalyzeService.selectOrgsByArea();
		String json = JSON.toJSONString(list);
		BigDecimal maxCount = new BigDecimal(0);
		BigDecimal totalCount = new BigDecimal(0);
		model.addAttribute("data", json);
		if(list != null && !list.isEmpty()){
			// 取出最大值并且计算总数量
			AnalyzeBigDecimal analy = list.get(0);
			maxCount = analy.getValue();
			for (AnalyzeBigDecimal analyze : list) {
				totalCount = totalCount.add(analyze.getValue());
				if(analyze.getValue().compareTo(maxCount) == 1){
					maxCount = analyze.getValue();
				}
				
			}
			model.addAttribute("totalCount", totalCount);
		}
		model.addAttribute("maxCount", maxCount);
		return "dss/rids/analyze/analyzeOrg";
	}
	
	/**
	 * 
	 * Description: 统计各采购机构人员数量
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购机构", operType=2)
	@SystemServiceLog(description="采购资源展示-采购机构", operType=2)
	@RequestMapping("/selectMemNumByOrg")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectMemNumByOrgS(){
		return purchaseResourceAnalyzeService.selectMemNumByOrg();
	}
	
	/**
	 * 
	 * Description: 采购人员页面展示
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购人员", operType=2)
	@SystemServiceLog(description="采购资源展示-采购人员", operType=2)
	@RequestMapping("/purchaseMemList")
	public String purchaseMemLists(Model model){
		// 查询采购机构总人员数量
		Long memberNum = purchaseResourceAnalyzeService.selectMemberNum();
		model.addAttribute("totalCount", memberNum);
		return "dss/rids/analyze/analyzePurchaseMember";
	}
	
	/**
	 * 
	 * Description: 采购人员类型展示
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购人员", operType=2)
	@SystemServiceLog(description="采购资源展示-采购人员", operType=2)
	@RequestMapping("/selectMenberByType")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectMenberByType(){
		return purchaseResourceAnalyzeService.selectMenberByType();
	}
	
	/**
	 * 
	 * Description: 查询男女比例数量
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购人员", operType=2)
	@SystemServiceLog(description="采购资源展示-采购人员", operType=2)
	@RequestMapping("/selectMenberByGender")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectMenberByGenders(){
		return purchaseResourceAnalyzeService.selectMenberByGender();
	}
	
	/**
	 * 
	 * Description: 获取供应商企业类型
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	@RequestMapping("/findSupbusinessNature")
	@ResponseBody
	public List<DictionaryData> findSupbusinessNatures(){
		return purchaseResourceAnalyzeService.findSupbusinessNature();
	}
	
	/**
	 * 
	 * Description: 查询专家所属类别
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	@RequestMapping("/findExpertCateType")
	@ResponseBody
	public List<DictionaryData> findExpertCateTypes(){
		return purchaseResourceAnalyzeService.findExpertCateType();
	}
	
	
	/**
	 * 
	 * Description: 根据不同类型，查询数据词典
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @param dictType
	 * @return
	 */
	@RequestMapping("/findDicts")
	@ResponseBody
	public List<DictionaryData> findDicts(String dictType){
		return purchaseResourceAnalyzeService.findDict(dictType);
	}
	
	/**
	 * 
	 * Description: 当年各采购机构签订采购合同金额
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @param dictType
	 * @return
	 */
	@RequestMapping("/selectNowYearOrgContractMoney")
	@ResponseBody
	public List<Analyze> selectNowYearOrgContractMoneys(){
		return purchaseResourceAnalyzeService.selectNowYearOrgContractMoney();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购合同-各产品类型签订采购合同数量
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @param dictType
	 * @return
	 */
	@RequestMapping("/selectpurContractByProductType")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectpurContractByProductTypes(){
		return purchaseResourceAnalyzeService.selectpurContractByProductType();
	}
	
	/**
	 * 
	 * Description: 当年各采购机构受领任务总金额
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @param dictType
	 * @return
	 */
	@RequestMapping("/selectNowYearOrgAcceptTaskMoney")
	@ResponseBody
	public List<Analyze> selectNowYearOrgAcceptTaskMoneys(){
		return purchaseResourceAnalyzeService.selectNowYearOrgAcceptTaskMoney();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购项目
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购项目", operType=2)
	@SystemServiceLog(description="采购资源展示-采购项目", operType=2)
	@RequestMapping("/analyzePurchaseProject")
	public String analyzePurchaseProjects(Model model) {
		// 查询全网采购项目总金额
		BigDecimal totalMoney = purchaseResourceAnalyzeService.selectPurProjectTotalMoney();
		model.addAttribute("totalMoney", totalMoney);
		return "dss/rids/analyze/analyzePurchaseProject";
	}
	
	/**
	 * 
	 * Description: 五种采购方式项目
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购项目", operType=2)
	@SystemServiceLog(description="采购资源展示-采购项目", operType=2)
	@RequestMapping("/selectPurProjectByWay")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectPurProjectByWays(){
		return purchaseResourceAnalyzeService.selectPurProjectByWay();
	}
	
	/**
	 * 
	 * Description: 采购项目数量以及总金额
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购项目", operType=2)
	@SystemServiceLog(description="采购资源展示-采购项目", operType=2)
	@RequestMapping("/selectPurProjectCountAndMoney")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectPurProjectCountAndMoneys(){
		return purchaseResourceAnalyzeService.selectPurProjectCountAndMoney();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购项目
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购合同", operType=2)
	@SystemServiceLog(description="采购资源展示-采购合同", operType=2)
	@RequestMapping("/analyzePurchaseContract")
	public String analyzePurchaseContracts(Model model) {
		// 查询全网采购合同总金额
		BigDecimal totalMoney = purchaseResourceAnalyzeService.selectPurContractTotalMoney();
		model.addAttribute("totalMoney", totalMoney);
		return "dss/rids/analyze/analyzePurchaseContract";
	}
	
	/**
	 * 
	 * Description: 采购合同数量以及总金额
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购合同", operType=2)
	@SystemServiceLog(description="采购资源展示-采购合同", operType=2)
	@RequestMapping("/selectPurContractCountAndMoney")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectPurContractCountAndMoneys(){
		return purchaseResourceAnalyzeService.selectPurContractCountAndMoney();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购需求
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购需求", operType=2)
	@SystemServiceLog(description="采购资源展示-采购需求", operType=2)
	@RequestMapping("/analyzePurchaseRequire")
	public String analyzePurchaseRequires(Model model) {
		// 查询上报采购需求总金额
		BigDecimal totalMoney = purchaseResourceAnalyzeService.selectAllBudget();
		model.addAttribute("totalMoney", totalMoney);
		return "dss/rids/analyze/analyzePurchaseRequire";
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购计划
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购计划", operType=2)
	@SystemServiceLog(description="采购资源展示-采购计划", operType=2)
	@RequestMapping("/analyzePurchasePlan")
	public String analyzePurchasePlans(Model model) {
		// 查询全网采购计划总金额
		BigDecimal totalMoney = purchaseResourceAnalyzeService.selectAllBudgetByPlan();
		model.addAttribute("totalMoney", totalMoney);
		return "dss/rids/analyze/analyzePurchasePlan";
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购公告
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购公告", operType=2)
	@SystemServiceLog(description="采购资源展示-采购公告", operType=2)
	@RequestMapping("/analyzePurchaseNotice")
	public String analyzePurchaseNotices(Model model) {
		// 查询全网采购公告总金额
		BigDecimal totalMoney = purchaseResourceAnalyzeService.selectPurchaseNoticeCount();
		model.addAttribute("totalMoney", totalMoney);
		return "dss/rids/analyze/analyzePurchaseNotice";
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购公告-查询已发布采购公告数量 
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购公告", operType=2)
	@SystemServiceLog(description="采购资源展示-采购公告", operType=2)
	@RequestMapping("/selectNearFiveYearPurchaseNoticeCount")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectNearFiveYearPurchaseNoticeCounts() {
		return purchaseResourceAnalyzeService.selectNearFiveYearPurchaseNoticeCount();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购公告-查询已发布采购公告数量 
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购公告", operType=2)
	@SystemServiceLog(description="采购资源展示-采购公告", operType=2)
	@RequestMapping("/selectNoticeByArticleType")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectNoticeByArticleTypes() {
		return purchaseResourceAnalyzeService.selectNoticeByArticleType();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购公告-根据各类型公告查询
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购公告", operType=2)
	@SystemServiceLog(description="采购资源展示-采购公告", operType=2)
	@RequestMapping("/selectNoticeByCateType")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectNoticeByCateTypes() {
		return purchaseResourceAnalyzeService.selectNoticeByCateType();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购公告-根据各采购方式公告查询
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购公告", operType=2)
	@SystemServiceLog(description="采购资源展示-采购公告", operType=2)
	@RequestMapping("/selectNoticeByPurWay")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectNoticeByPurWays() {
		return purchaseResourceAnalyzeService.selectNoticeByPurWay();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购公告-发布排名前10的产品类别数量
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购公告", operType=2)
	@SystemServiceLog(description="采购资源展示-采购公告", operType=2)
	@RequestMapping("/selectNoticeByProductCate")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectNoticeByProductCates() {
		return purchaseResourceAnalyzeService.selectNoticeByProductCate();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购需求-获取近五年需求总金额
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购需求", operType=2)
	@SystemServiceLog(description="采购资源展示-采购需求", operType=2)
	@RequestMapping("/selectNearFiveYearAllBudget")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectNearFiveYearAllBudgets() {
		return purchaseResourceAnalyzeService.selectNearFiveYearAllBudget();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购需求-各类型需求金额
	 * 
	 * @author Easong
	 * @version 2017年6月8日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购需求", operType=2)
	@SystemServiceLog(description="采购资源展示-采购需求", operType=2)
	@RequestMapping("/selectBudget")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectBudgets() {
		return purchaseResourceAnalyzeService.selectBudget();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购需求-获取各管理部门受理需求金额
	 * 
	 * @author Easong
	 * @version 2017年6月8日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购需求", operType=2)
	@SystemServiceLog(description="采购资源展示-采购需求", operType=2)
	@RequestMapping("/selectOrgBudget")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectOrgBudgets() {
		return purchaseResourceAnalyzeService.selectOrgBudget();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购计划-管理部门获取前10名的总金额
	 * 
	 * @author Easong
	 * @version 2017年6月8日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购计划", operType=2)
	@SystemServiceLog(description="采购资源展示-采购计划", operType=2)
	@RequestMapping("/selectManageBudget")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectManageBudgets() {
		return purchaseResourceAnalyzeService.selectManageBudget();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购计划-近5年下达采购计划批次和金额
	 * 
	 * @author Easong
	 * @version 2017年6月8日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购计划", operType=2)
	@SystemServiceLog(description="采购资源展示-采购计划", operType=2)
	@RequestMapping("/selectNowFiveYearAllBudgetByPlan")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectNowFiveYearAllBudgetByPlans() {
		return purchaseResourceAnalyzeService.selectNowFiveYearAllBudgetByPlan();
	}
	
	/**
	 * 
	 * Description: 统计业务模块-采购计划-采购机构获取前10名的总金额
	 * 
	 * @author Easong
	 * @version 2017年6月9日
	 * @return
	 */
	@SystemControllerLog(description="采购资源展示-采购计划", operType=2)
	@SystemServiceLog(description="采购资源展示-采购计划", operType=2)
	@RequestMapping("/selectPlanBudget")
	@ResponseBody
	public List<AnalyzeBigDecimal> selectPlanBudgets() {
		return purchaseResourceAnalyzeService.selectPlanBudget();
	}
	
}
