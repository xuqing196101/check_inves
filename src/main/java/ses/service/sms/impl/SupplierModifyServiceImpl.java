package ses.service.sms.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierHistoryMapper;
import ses.dao.sms.SupplierModifyMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.sms.SupplierModifyService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;

@Service(value = "supplierModifyService")
public class SupplierModifyServiceImpl implements SupplierModifyService{
	
	@Autowired
	private SupplierModifyMapper supplierModifyMapper;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private AreaServiceI areaService;
	
	@Autowired
	private SupplierHistoryMapper supplierHistoryMapper;
	
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;
	
	@Autowired
	private  SupplierTypeRelateMapper supplierTypeRelateMapper;
	
	/**
	 * @Title: selectField
	 * @author XuQing 
	 * @date 2017-2-16 下午4:22:14  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return List<SupplierHistory>
	 */
	@Override
	public List<SupplierModify> selectBySupplierId(SupplierModify supplierModify) {
		
		return supplierModifyMapper.selectBySupplierId(supplierModify);
	}

	/**
	 * @Title: delete
	 * @author XuQing 
	 * @date 2017-2-16 下午4:24:18  
	 * @Description:删除
	 * @param @param supplierModify      
	 * @return void
	 */
	@Override
	public void delete(SupplierModify supplierModify) {
		supplierModifyMapper.delete(supplierModify);
		
	}
	
	/**
     * @Title: insertSelective
     * @author XuQing 
     * @date 2017-2-15 下午4:22:06  
     * @Description:插入审核退回后供应商修改记录
     * @param @param supplierModify      
     * @return void
     */
	@Override
	public void insertModifyRecord (SupplierModify supplierModify){	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SupplierHistory supplierHistory =new SupplierHistory();
		String supplierId = supplierModify.getSupplierId();
		
		/**
		 * 供应商类型
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("supplier_type");
		supplierHistory.setListType(12);
		List<SupplierHistory> historyList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		String findBySupplier = supplierTypeRelateService.findBySupplier(supplierId);
		for(SupplierHistory h : historyList){
			if(!findBySupplier.contains(h.getBeforeField())){
				supplierModify.setBeforeField(h.getBeforeField());
				supplierModify.setmodifyType("supplier_type");
				supplierModify.setListType(12);
				supplierModifyMapper.insertSelective(supplierModify);
			}
		}
		
		 
        /*//修改后的类型、
		 StringBuffer editAudit = new StringBuffer();
		
         //全部类型
         StringBuffer typeAll = new StringBuffer();
         List < DictionaryData > wuzi = DictionaryDataUtil.find(6);
		 List < DictionaryData > wlist = DictionaryDataUtil.find(8);
		 for(DictionaryData d : wuzi){
			typeAll.append(d.getCode()+",");
		 }
		 for(DictionaryData d : wlist){
			typeAll.append(d.getCode()+",");
		 }
        
		 
         //现在勾选的类型
		 StringBuffer type = new StringBuffer();
		 List<SupplierTypeRelate> supplierType = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplierId);
		 for(SupplierTypeRelate r : supplierType){
			 type.append(r.getSupplierTypeId() + ",");
		 }
        
		 //历史
		 StringBuffer historyType = new StringBuffer();
		 for(SupplierHistory h: historyList){
			 historyType.append(h.getBeforeField() + ",");
		 }
		 
        
		 for(SupplierHistory h : historyList){
				if(!findBySupplier.contains(h.getBeforeField())){
					editAudit.append(h.getBeforeField() + ",");
				}
			}
		 
		 String[] splitType = type.toString().split(",");
		 for(String t: splitType){
			 if(typeAll.toString().contains(t) && !historyType.toString().contains(t)){
				 editAudit.append(t + ",");
			 }
		 }*/
        
        
        
        
		
		
		
		
		
		
		
		/**
		 * 地址信息
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("basic_page");
		supplierHistory.setListType(1);
		List<SupplierHistory> addressList = supplierHistoryMapper.findListBySupplierId(supplierHistory);

		List<SupplierAddress> supplierAddress = supplierService.get(supplierId).getAddressList();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(1);
		supplierModify.setmodifyType("basic_page");
		for(SupplierHistory history : addressList){
			for(SupplierAddress address: supplierAddress){
				if(history.getRelationId().equals(address.getId())){
					supplierModify.setRelationId(address.getId());
					if(history.getBeforeField() != null && history.getBeforeContent() !=null){
						//生产经营地址邮编：
						if (history.getBeforeField().equals("code") && !history.getBeforeContent().equals(address.getCode())) {
							supplierModify.setBeforeField("code");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//生产经营地址：
						if(history.getBeforeField().equals("address")){
							List < Area > privnce = areaService.findRootArea();
							Area area = new Area();
							area = areaService.listById(address.getAddress());
							String sonAddress = area.getName();
							String parentAddress = null;
							for(int i = 0; i < privnce.size(); i++) {
								if(area.getParentId().equals(privnce.get(i).getId())) {
									parentAddress = privnce.get(i).getName();
								}
							}
							if (!history.getBeforeContent().equals(parentAddress + sonAddress)) {
								supplierModify.setBeforeField("address");
								supplierModify.setBeforeContent(history.getBeforeContent());
								supplierModifyMapper.insertSelective(supplierModify);
							}
						}
	
						//生产经营详细地址：
						if (history.getBeforeField().equals("detailAddress") && !history.getBeforeContent().equals(address.getDetailAddress())) {
							supplierModify.setBeforeField("detailAddress");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
		
		/**
		 * 境外分支
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("basic_page");
		supplierHistory.setListType(2);
		List<SupplierHistory> list = supplierHistoryMapper.findListBySupplierId(supplierHistory);

		List<SupplierBranch> branchList = supplierService.get(supplierId).getBranchList();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(2);
		supplierModify.setmodifyType("basic_page");
		
		for(SupplierHistory history : list){
			for(SupplierBranch supplierBranch: branchList){
				if(history.getRelationId().equals(supplierBranch.getId())){
					supplierModify.setRelationId(supplierBranch.getId());
					
					// 机构名称
					if(history.getBeforeField() != null && history.getBeforeContent() !=null){
						if (history.getBeforeField().equals("organizationName") && !history.getBeforeContent().equals(supplierBranch.getOrganizationName())) {
							supplierModify.setBeforeField("organizationName");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 所在国家（地区）
						if (history.getBeforeField().equals("countryName") && !history.getBeforeContent().equals(supplierBranch.getCountryName())) {
							supplierModify.setBeforeField("countryName");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 详细地址
						if (history.getBeforeField().equals("detailAddress") && !history.getBeforeContent().equals(supplierBranch.getDetailAddress())) {
							supplierModify.setBeforeField("detailAddress");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 生产经营范围
						if (history.getBeforeField().equals("businessSope") && !history.getBeforeContent().equals(supplierBranch.getBusinessSope())) {
							supplierModify.setBeforeField("businessSope");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}

		/**
		 * 财务信息
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("finance_page");
		supplierHistory.setListType(3);
		List<SupplierHistory> financesList = supplierHistoryMapper.findListBySupplierId(supplierHistory);

		List < SupplierFinance > supplierFinance = supplierService.get(supplierId).getListSupplierFinances();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(3);
		supplierModify.setmodifyType("finance_page");
		
		for(SupplierHistory history : financesList){
			for(SupplierFinance finance: supplierFinance){
				if(history.getRelationId().equals(finance.getId())){
					supplierModify.setRelationId(finance.getId());
					if(history.getBeforeField() != null && history.getBeforeContent() !=null){
						//会计事务所名称
						if (history.getBeforeField().equals("name") && !history.getBeforeContent().equals(finance.getName())) {
							supplierModify.setBeforeField("name");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//事务所联系电话
						if (history.getBeforeField().equals("telephone") && !history.getBeforeContent().equals(finance.getTelephone())) {
							supplierModify.setBeforeField("telephone");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//审计人姓名
						if (history.getBeforeField().equals("auditors") && !history.getBeforeContent().equals(finance.getAuditors())) {
							supplierModify.setBeforeField("auditors");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//资产总额
						if (history.getBeforeField().equals("totalAssets") && !history.getBeforeContent().equals(finance.getTotalAssets().toString())) {
							supplierModify.setBeforeField("totalAssets");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//负债总额
						if (history.getBeforeField().equals("totalLiabilities") && !history.getBeforeContent().equals(finance.getTotalLiabilities().toString())) {
							supplierModify.setBeforeField("totalLiabilities");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//净资产总额
						if (history.getBeforeField().equals("totalNetAssets") && !history.getBeforeContent().equals(finance.getTotalNetAssets().toString())) {
							supplierModify.setBeforeField("totalNetAssets");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//营业收入
						if (history.getBeforeField().equals("taking") && !history.getBeforeContent().equals(finance.getTaking().toString())) {
							supplierModify.setBeforeField("taking");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
		
		
		/**
		 * 股东信息
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("shareholder_page");
		supplierHistory.setListType(4);
		List<SupplierHistory> shareholderList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		List<SupplierStockholder> listSupplierStockholders = supplierService.get(supplierId).getListSupplierStockholders();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(4);
		supplierModify.setmodifyType("shareholder_page");
		
		for(SupplierHistory history : shareholderList){
			for(SupplierStockholder stockholder: listSupplierStockholders){
				if(history.getRelationId().equals(stockholder.getId())){
					supplierModify.setRelationId(stockholder.getId());
					if(history.getBeforeField() != null && history.getBeforeContent() !=null){
						//出资人性质
						if (history.getBeforeField().equals("nature") && !history.getBeforeContent().equals(stockholder.getNature())) {
							supplierModify.setBeforeField("nature");
							if(history.getBeforeContent().equals("1")){
								supplierModify.setBeforeContent("法人");
							}else{
								supplierModify.setBeforeContent("自然人");
							}
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//出资人名称或姓名
						if (history.getBeforeField().equals("name") && !history.getBeforeContent().equals(stockholder.getName())) {
							supplierModify.setBeforeField("name");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//统一社会信用代码或身份证号码
						if (history.getBeforeField().equals("identity") && !history.getBeforeContent().equals(stockholder.getIdentity())) {
							supplierModify.setBeforeField("identity");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//出资金额或股份（万元/万份）
						if (history.getBeforeField().equals("shares") && !history.getBeforeContent().equals(stockholder.getShares())) {
							supplierModify.setBeforeField("shares");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//比例（%）
						if (history.getBeforeField().equals("proportion") && !history.getBeforeContent().equals(stockholder.getProportion())) {
							supplierModify.setBeforeField("proportion");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
			
		}
		
		/**
		 * 供应商类型--生产--产品研发能力
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("mat_pro_page");
		supplierHistory.setListType(null);
		List<SupplierHistory> matProList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		SupplierMatPro supplierMatPro = supplierService.get(supplierId).getSupplierMatPro();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setmodifyType("mat_pro_page");
		supplierModify.setListType(5);
		
		for(SupplierHistory history : matProList){
			if(history.getRelationId().equals(supplierMatPro.getId())){
				supplierModify.setRelationId(supplierMatPro.getId());
				
				if(history.getBeforeField() != null && history.getBeforeContent() !=null){
					// 技术人员数量比例(%)
					if (history.getBeforeField().equals("scaleTech") && !history.getBeforeContent().equals(supplierMatPro.getScaleTech())) {
						supplierModify.setBeforeField("scaleTech");
						supplierModify.setBeforeContent(history.getBeforeContent());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					// 高级技术人员数量比例(%)
					if (history.getBeforeField().equals("scaleHeightTech") && !history.getBeforeContent().equals(supplierMatPro.getScaleHeightTech())) {
						supplierModify.setBeforeField("scaleHeightTech");
						supplierModify.setBeforeContent(history.getBeforeContent());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					// 研发部门名称
					if (history.getBeforeField().equals("researchName") && !history.getBeforeContent().equals(supplierMatPro.getResearchName())) {
						supplierModify.setBeforeField("researchName");
						supplierModify.setBeforeContent(history.getBeforeContent());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//  研发部门人数
					if (history.getBeforeField().equals("totalResearch") && !history.getBeforeContent().equals(supplierMatPro.getTotalResearch().toString())) {
						supplierModify.setBeforeField("totalResearch");
						supplierModify.setBeforeContent(history.getBeforeContent());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					// 研发部门负责人
					if (history.getBeforeField().equals("researchLead") && !history.getBeforeContent().equals(supplierMatPro.getResearchLead())) {
						supplierModify.setBeforeField("researchLead");
						supplierModify.setBeforeContent(history.getBeforeContent());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					// 承担国家军队科研项目
					if (history.getBeforeField().equals("countryPro") && !history.getBeforeContent().equals(supplierMatPro.getCountryPro())) {
						supplierModify.setBeforeField("countryPro");
						supplierModify.setBeforeContent(history.getBeforeContent());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					// 获得国家军队科技奖项
					if (history.getBeforeField().equals("countryReward") && !history.getBeforeContent().equals(supplierMatPro.getCountryReward())) {
						supplierModify.setBeforeField("countryReward");
						supplierModify.setBeforeContent(history.getBeforeContent());
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
	
		/**
		 * 生产--资质证书信息
		 */
		supplierHistory.setListType(5);
		List<SupplierHistory> certProList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		supplierModify.setListType(5);
		List<SupplierCertPro> listSupplierCertPros = supplierService.get(supplierId).getSupplierMatPro().getListSupplierCertPros();
		for(SupplierHistory h : certProList){
			for(SupplierCertPro certPro : listSupplierCertPros){
				if(h.getRelationId().equals(certPro.getId())){
					supplierModify.setRelationId(certPro.getId());
					
					if(h.getBeforeField() != null && h.getBeforeContent() !=null){
						// 资质证书名称
						if (h.getBeforeField().equals("name") && !h.getBeforeContent().equals(certPro.getName())) {
							supplierModify.setBeforeField("name");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 证书编号
						if (h.getBeforeField().equals("code") && !h.getBeforeContent().equals(certPro.getCode())) {
							supplierModify.setBeforeField("code");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 资质等级
						if (h.getBeforeField().equals("levelCert") && !h.getBeforeContent().equals(certPro.getLevelCert())) {
							supplierModify.setBeforeField("levelCert");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 发证机关或机构
						if (h.getBeforeField().equals("licenceAuthorith") && !h.getBeforeContent().equals(certPro.getLicenceAuthorith())) {
							supplierModify.setBeforeField("licenceAuthorith");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 资有效期（起始时间）
						if (h.getBeforeField().equals("expStartDate") && !h.getBeforeContent().equals( format.format(certPro.getExpStartDate()))) {
							supplierModify.setBeforeField("expStartDate");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 	有效期（结束时间）
						if (h.getBeforeField().equals("expEndDate") && !h.getBeforeContent().equals( format.format(certPro.getExpEndDate()))) {
							supplierModify.setBeforeField("expEndDate");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 证书状态
						if (h.getBeforeField().equals("mot") && !h.getBeforeContent().equals(certPro.getMot().toString())) {
							supplierModify.setBeforeField("mot");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
			
		}
		
		
		/**
		 * 销售资质证书
		 */
		supplierHistory.setListType(6);
		supplierHistory.setmodifyType("mat_sell_page");
		List<SupplierHistory> sellList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(6);
		supplierModify.setmodifyType("mat_sell_page");
		List<SupplierCertSell> listSupplierCertSells = supplierService.get(supplierId).getSupplierMatSell().getListSupplierCertSells();
		for(SupplierHistory h : sellList){
			for(SupplierCertSell sell : listSupplierCertSells){
				if(h.getRelationId().equals(sell.getId())){
					supplierModify.setRelationId(sell.getId());
					
					if(h.getBeforeField() != null && h.getBeforeContent() !=null){
						// 资质证书名称
						if (h.getBeforeField().equals("name") && !h.getBeforeContent().equals(sell.getName())) {
							supplierModify.setBeforeField("name");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 证书编号	
						if (h.getBeforeField().equals("code") && !h.getBeforeContent().equals(sell.getCode())) {
							supplierModify.setBeforeField("code");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 资质等级	
						if (h.getBeforeField().equals("levelCert") && !h.getBeforeContent().equals(sell.getLevelCert())) {
							supplierModify.setBeforeField("levelCert");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 发证机关或机构
						if (h.getBeforeField().equals("licenceAuthorith") && !h.getBeforeContent().equals(sell.getLicenceAuthorith())) {
							supplierModify.setBeforeField("licenceAuthorith");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 资有效期（起始时间）
						if (h.getBeforeField().equals("expStartDate") && !h.getBeforeContent().equals( format.format(sell.getExpStartDate()))) {
							supplierModify.setBeforeField("expStartDate");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 	有效期（结束时间）
						if (h.getBeforeField().equals("expEndDate") && !h.getBeforeContent().equals( format.format(sell.getExpEndDate()))) {
							supplierModify.setBeforeField("expEndDate");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 证书状态
						if (h.getBeforeField().equals("mot") && !h.getBeforeContent().equals(sell.getMot().toString())) {
							supplierModify.setBeforeField("mot");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
		
		/**
		 * 工程-注册人员
		 */
		supplierHistory.setListType(7);
		supplierHistory.setmodifyType("mat_eng_page");
		List<SupplierHistory> engList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(7);
		supplierModify.setmodifyType("mat_eng_page");
		List<SupplierRegPerson> listSupplierRegPersons = supplierService.get(supplierId).getSupplierMatEng().getListSupplierRegPersons();
		
		for(SupplierHistory h : engList){
			for(SupplierRegPerson regPerson : listSupplierRegPersons){
				if(h.getRelationId().equals(regPerson.getId())){
					supplierModify.setRelationId(regPerson.getId());
					if(h.getBeforeField() != null && h.getBeforeContent() !=null){
						// 注册资格名称
						if (h.getBeforeField().equals("regType") && !h.getBeforeContent().equals(regPerson.getRegType())) {
							supplierModify.setBeforeField("regType");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//注册人姓名
						if (h.getBeforeField().equals("regNumber") && !h.getBeforeContent().equals(regPerson.getRegNumber().toString())) {
							supplierModify.setBeforeField("regNumber");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
				
		/**
		 * 工程-证书信息
		 */
		supplierHistory.setListType(8);
		supplierHistory.setmodifyType("mat_eng_page");
		List<SupplierHistory> certEngList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(8);
		supplierModify.setmodifyType("mat_eng_page");
		List<SupplierCertEng> listSupplierCertEngs = supplierService.get(supplierId).getSupplierMatEng().getListSupplierCertEngs();
		
		for(SupplierHistory h : certEngList){
			for(SupplierCertEng certEng : listSupplierCertEngs){
				if(h.getRelationId().equals(certEng.getId())){
					supplierModify.setRelationId(certEng.getId());
					if(h.getBeforeField() != null && h.getBeforeContent() !=null){
						// 证书名称
						if (h.getBeforeField().equals("certType") && !h.getBeforeContent().equals(certEng.getCertType())) {
							supplierModify.setBeforeField("certType");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//	证书编号
						if (h.getBeforeField().equals("certCode") && !h.getBeforeContent().equals(certEng.getCertCode())) {
							supplierModify.setBeforeField("certCode");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//资质等级
						if (h.getBeforeField().equals("certMaxLevel") && !h.getBeforeContent().equals(certEng.getCertMaxLevel())) {
							supplierModify.setBeforeField("certMaxLevel");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
	
						//发证机关或机构
						if (h.getBeforeField().equals("licenceAuthorith") && !h.getBeforeContent().equals(certEng.getLicenceAuthorith())) {
							supplierModify.setBeforeField("licenceAuthorith");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
	
						//发证日期
						if (h.getBeforeField().equals("expStartDate") && !h.getBeforeContent().equals( format.format(certEng.getExpStartDate()))) {
							supplierModify.setBeforeField("expStartDate");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
	
						//有效截止日期
						if (h.getBeforeField().equals("expEndDate") && !h.getBeforeContent().equals( format.format(certEng.getExpEndDate()))) {
							supplierModify.setBeforeField("expEndDate");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
	
						//证书状态
						if (h.getBeforeField().equals("certStatus") && !h.getBeforeContent().equals(certEng.getCertStatus().toString())) {
							supplierModify.setBeforeField("certStatus");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
		
		/**
		 * 工程-资质证书信息
		 */
		supplierHistory.setListType(9);
		supplierHistory.setmodifyType("mat_eng_page");
		List<SupplierHistory> aptitutesList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(9);
		supplierModify.setmodifyType("mat_eng_page");
		List<SupplierAptitute> listSupplierAptitutes = supplierService.get(supplierId).getSupplierMatEng().getListSupplierAptitutes();
		
		for(SupplierHistory h : aptitutesList){
			for(SupplierAptitute aptitute : listSupplierAptitutes){
				if(h.getRelationId().equals(aptitute.getId())){
					supplierModify.setRelationId(aptitute.getId());
					if(h.getBeforeField() != null && h.getBeforeContent() !=null){
						// 证书名称
						if (h.getBeforeField().equals("certType") && !h.getBeforeContent().equals(aptitute.getCertType())) {
							supplierModify.setBeforeField("certType");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//资质类型
						if (h.getBeforeField().equals("certCode") && !h.getBeforeContent().equals(aptitute.getCertCode())) {
							supplierModify.setBeforeField("certCode");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//资质序列
						if (h.getBeforeField().equals("aptituteSequence") && !h.getBeforeContent().equals(aptitute.getAptituteSequence())) {
							supplierModify.setBeforeField("aptituteSequence");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
	
						//专业类别
						if (h.getBeforeField().equals("professType") && !h.getBeforeContent().equals(aptitute.getProfessType())) {
							supplierModify.setBeforeField("professType");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//资质等级
						if (h.getBeforeField().equals("aptituteLevel") && !h.getBeforeContent().equals(aptitute.getAptituteLevel())) {
							supplierModify.setBeforeField("aptituteLevel");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//是否主项资质
						if (h.getBeforeField().equals("isMajorFund") && !h.getBeforeContent().equals(aptitute.getIsMajorFund().toString())) {
							supplierModify.setBeforeField("isMajorFund");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
		
		/**
		 * 服务证书
		 */
		supplierHistory.setListType(10);
		supplierHistory.setmodifyType("mat_serve_page");
		List<SupplierHistory> serveList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(10);
		supplierModify.setmodifyType("mat_serve_page");
		List<SupplierCertServe> listSupplierCertSes = supplierService.get(supplierId).getSupplierMatSe().getListSupplierCertSes();
		
		for(SupplierHistory h : serveList){
			for(SupplierCertServe certServe : listSupplierCertSes){
				if(h.getRelationId().equals(certServe.getId())){
					supplierModify.setRelationId(certServe.getId());
					
					// 资质证书名称
					if(h.getBeforeField() != null && h.getBeforeContent() !=null){
						if (h.getBeforeField().equals("name") && !h.getBeforeContent().equals(certServe.getName())) {
							supplierModify.setBeforeField("name");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 证书编号	
						if (h.getBeforeField().equals("code") && !h.getBeforeContent().equals(certServe.getCode())) {
							supplierModify.setBeforeField("code");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 资质等级	
						if (h.getBeforeField().equals("levelCert") && !h.getBeforeContent().equals(certServe.getLevelCert())) {
							supplierModify.setBeforeField("levelCert");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 发证机关或机构
						if (h.getBeforeField().equals("licenceAuthorith") && !h.getBeforeContent().equals(certServe.getLicenceAuthorith())) {
							supplierModify.setBeforeField("licenceAuthorith");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 资有效期（起始时间）
						if (h.getBeforeField().equals("expStartDate") && !h.getBeforeContent().equals( format.format(certServe.getExpStartDate()))) {
							supplierModify.setBeforeField("expStartDate");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 	有效期（结束时间）
						if (h.getBeforeField().equals("expEndDate") && !h.getBeforeContent().equals( format.format(certServe.getExpEndDate()))) {
							supplierModify.setBeforeField("expEndDate");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 证书状态
						if (h.getBeforeField().equals("mot") && !h.getBeforeContent().equals(certServe.getMot().toString())) {
							supplierModify.setBeforeField("mot");
							supplierModify.setBeforeContent(h.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
		
		/**
		 * 售后服务机构一览表
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("basic_page");
		supplierHistory.setListType(11);
		List<SupplierHistory> afterSaleDepList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		List<SupplierAfterSaleDep> listSupplierAfterSaleDep = supplierService.get(supplierId).getListSupplierAfterSaleDep();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(11);
		supplierModify.setmodifyType("basic_page");
		
		for(SupplierHistory history : afterSaleDepList){
			for(SupplierAfterSaleDep afterSaleDep: listSupplierAfterSaleDep){
				if(history.getRelationId().equals(afterSaleDep.getId())){
					supplierModify.setRelationId(afterSaleDep.getId());
					
					// 分支（或服务）机构名称
					if(history.getBeforeField() != null && history.getBeforeContent() !=null){
						if (history.getBeforeField().equals("name") && !history.getBeforeContent().equals(afterSaleDep.getName())) {
							supplierModify.setBeforeField("name");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 类别
						if (history.getBeforeField().equals("type") && !history.getBeforeContent().equals(afterSaleDep.getType().toString())) {
							supplierModify.setBeforeField("type");
							if(history.getBeforeField().equals("1")){
								supplierModify.setBeforeContent("自营");
							}else{
								supplierModify.setBeforeContent("合作");
							}
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//所在县市
						if (history.getBeforeField().equals("address") && !history.getBeforeContent().equals(afterSaleDep.getAddress())) {
							supplierModify.setBeforeField("address");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//负责人
						if (history.getBeforeField().equals("leadName") && !history.getBeforeContent().equals(afterSaleDep.getLeadName())) {
							supplierModify.setBeforeField("leadName");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						//联系电话
						if (history.getBeforeField().equals("mobile") && !history.getBeforeContent().equals(afterSaleDep.getMobile())) {
							supplierModify.setBeforeField("mobile");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
					
				}
			}
			
		}
		
	}

	/**
	 * @Title: findBySupplierId
	 * @author XuQing 
	 * @date 2017-2-17 上午10:21:59  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return SupplierModify
	 */
	@Override
	public SupplierModify findBySupplierId(SupplierModify supplierModify) {
		
		return supplierModifyMapper.findBySupplierId(supplierModify);
	}

	/**
     * @Title: add
     * @author XuQing 
     * @date 2017-2-17 下午2:48:40  
     * @Description:插入基本信息
     * @param @param supplierModify      
     * @return void
     */
	@Override
	public void add(SupplierModify supplierModify) {
		
		supplierModifyMapper.add(supplierModify);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void deleteByType(SupplierModify supplierModify) {
		supplierModifyMapper.deleteByType(supplierModify);
	}

	
}
