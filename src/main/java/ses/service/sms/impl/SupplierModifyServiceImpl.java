package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierHistoryMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatSellMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.SupplierModifyMapper;
import ses.model.bms.Area;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierEngQua;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.service.bms.AreaServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierModifyService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	//财务
	@Autowired
	private  SupplierFinanceMapper supplierFinanceMapper;
	
	//生产
	@Autowired
	private SupplierMatProMapper supplierMatProMapper;
	
	@Autowired
	private SupplierItemMapper supplierItemMapper;
	
	//销售
	@Autowired
	private SupplierMatSellMapper supplierMatSellMapper;
	
	//工程
	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;
	
	//工程
	@Autowired
	private SupplierMatServeMapper supplierMatServeMapper;
	
	@Autowired
	private SupplierAddressService supplierAddressService;
	/**
	 * @Title: selectField
	  
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
      
     * @date 2017-2-15 下午4:22:06  
     * @Description:插入审核退回后供应商修改记录
     * @param @param supplierModify      
     * @return void
     */
	@Override
	public void insertModifyRecord(SupplierModify supplierModify){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SupplierHistory supplierHistory =new SupplierHistory();
		String supplierId = supplierModify.getSupplierId();
		
		 Supplier supplier = supplierService.get(supplierId);// 供应商信息
		
		/**
		 * 供应商类型
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setModifyType("supplier_type");
		supplierHistory.setListType(12);
		List<SupplierHistory> historyList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
//		String findBySupplier = supplierTypeRelateService.findBySupplier(supplierId);
		String findBySupplier = supplier.getSupplierTypeIds();
		if(!findBySupplier.isEmpty() && findBySupplier.length() > 0){
			for(SupplierHistory h : historyList){
				if(!findBySupplier.contains(h.getBeforeField())){
					supplierModify.setBeforeField(h.getBeforeField());
					supplierModify.setModifyType("supplier_type");
					supplierModify.setListType(12);
					supplierModifyMapper.insertSelective(supplierModify);
				}
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
		supplierHistory.setModifyType("basic_page");
		supplierHistory.setListType(1);
		List<SupplierHistory> addressList = supplierHistoryMapper.findListBySupplierId(supplierHistory);

		List<SupplierAddress> supplierAddress = supplier.getAddressList();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(1);
		supplierModify.setModifyType("basic_page");
		if(addressList != null && !addressList.isEmpty()){
			Set<String> idSet = new HashSet<String>();
			for(SupplierHistory history : addressList){
				idSet.add(history.getRelationId());
				for(SupplierAddress address: supplierAddress){
					if(address.getId() !=null){
						if(history.getRelationId().equals(address.getId())){
							idSet.remove(history.getRelationId());
							supplierModify.setRelationId(address.getId());
							if(history.getBeforeField() != null && history.getBeforeContent() !=null){
								//生产经营地址邮编：
								if (history.getBeforeField().equals("code") && !history.getBeforeContent().equals(address.getCode())) {
									supplierModify.setBeforeField("code");
									supplierModify.setBeforeContent(history.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								//生产经营地址：
								if(history.getBeforeField().equals("residence")){
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
										supplierModify.setBeforeField("residence");
										supplierModify.setBeforeContent(history.getBeforeContent());
										supplierModifyMapper.insertSelective(supplierModify);
									}
								}
								
								//生产经营详细地址：
								if (history.getBeforeField().equals("detailedResidence") && !history.getBeforeContent().equals(address.getDetailAddress())) {
									supplierModify.setBeforeField("detailedResidence");
									supplierModify.setBeforeContent(history.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
							}
						}
					}	
				}
			}
			if(!idSet.isEmpty()){
				for(String id : idSet){
					supplierModify.setRelationId(id);
					supplierModify.setBeforeField("id");
					supplierModifyMapper.insertSelective(supplierModify);
				}
			}
		}
		
		/**
		 * 境外分支
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setModifyType("basic_page");
		supplierHistory.setListType(2);
		List<SupplierHistory> list = supplierHistoryMapper.findListBySupplierId(supplierHistory);

		List<SupplierBranch> branchList = supplier.getBranchList();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(2);
		supplierModify.setModifyType("basic_page");
		
		if(list != null && !list.isEmpty()){
			Set<String> idSet = new HashSet<String>();
			for(SupplierHistory history : list){
				idSet.add(history.getRelationId());
				for(SupplierBranch supplierBranch: branchList){
					if(history.getRelationId().equals(supplierBranch.getId())){
						idSet.remove(history.getRelationId());
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
			if(!idSet.isEmpty()){
				for(String id : idSet){
					supplierModify.setRelationId(id);
					supplierModify.setBeforeField("id");
					supplierModifyMapper.insertSelective(supplierModify);
				}
			}
		}

		/**
		 * 财务信息
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setModifyType("finance_page");
		supplierHistory.setListType(3);
		List<SupplierHistory> financesList = supplierHistoryMapper.findListBySupplierId(supplierHistory);

		List < SupplierFinance > supplierFinance = supplier.getListSupplierFinances();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(3);
		supplierModify.setModifyType("finance_page");
		
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
		supplierHistory.setModifyType("shareholder_page");
		supplierHistory.setListType(4);
		List<SupplierHistory> shareholderList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		List<SupplierStockholder> listSupplierStockholders = supplier.getListSupplierStockholders();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(4);
		supplierModify.setModifyType("shareholder_page");
		
		if(shareholderList != null && !shareholderList.isEmpty()){
			Set<String> idSet = new HashSet<String>();
			for(SupplierHistory history : shareholderList){
				idSet.add(history.getRelationId());
				for(SupplierStockholder stockholder: listSupplierStockholders){
					if(history.getRelationId().equals(stockholder.getId())){
						idSet.remove(history.getRelationId());
						supplierModify.setRelationId(stockholder.getId());
						if(history.getBeforeField() != null && history.getBeforeContent() !=null){
							//出资人性质
							if (history.getBeforeField().equals("nature") && !history.getBeforeContent().equals(stockholder.getNature())) {
								supplierModify.setBeforeField("nature");
								if(history.getBeforeContent().equals("1")){
									supplierModify.setBeforeContent("单位投资");
								}else if(history.getBeforeContent().equals("2")){
									supplierModify.setBeforeContent("个人投资");
								}else {
									supplierModify.setBeforeContent(history.getBeforeContent());
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
							
							/*//证件类型
							if (history.getBeforeField().equals("identityType") && !history.getBeforeContent().equals(stockholder.getIdentity())) {
								supplierModify.setBeforeField("identityType");
								supplierModify.setBeforeContent(history.getBeforeContent());
								supplierModifyMapper.insertSelective(supplierModify);
							}*/
							
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
			if(!idSet.isEmpty()){
				for(String id : idSet){
					supplierModify.setRelationId(id);
					supplierModify.setBeforeField("id");
					supplierModifyMapper.insertSelective(supplierModify);
				}
			}
		}
		
		/**
		 * 供应商类型--生产--产品研发能力
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setModifyType("mat_pro_page");
		supplierHistory.setListType(null);
		List<SupplierHistory> matProList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		if(supplier.getSupplierTypeIds() != null && supplier.getSupplierTypeIds().contains("PRODUCT")){
			SupplierMatPro supplierMatPro = supplier.getSupplierMatPro();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_pro_page");
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
					if(history.getBeforeField() != null && history.getBeforeContent() == null){
						// 承担国家军队科研项目
						if (history.getBeforeField().equals("countryPro") && supplierMatPro.getCountryPro() != null) {
							supplierModify.setBeforeField("countryPro");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
						
						// 获得国家军队科技奖项
						if (history.getBeforeField().equals("countryReward") && supplierMatPro.getCountryReward() != null) {
							supplierModify.setBeforeField("countryReward");
							supplierModify.setBeforeContent(history.getBeforeContent());
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
		
	
		/**
		 * 生产--资质证书信息
		 */
		supplierHistory.setListType(5);
		List<SupplierHistory> certProList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		if(supplier.getSupplierTypeIds() != null && supplier.getSupplierTypeIds().contains("PRODUCT")){
			supplierModify.setListType(5);
			SupplierMatPro matPro = supplier.getSupplierMatPro();
			if(matPro != null ){
				List<SupplierCertPro> listSupplierCertPros = matPro.getListSupplierCertPros();
				/*List<SupplierCertPro> listSupplierCertPros = supplier.getSupplierMatPro().getListSupplierCertPros();*/
				if(certProList != null && !certProList.isEmpty()){
					Set<String> idSet = new HashSet<String>();
					for(SupplierHistory h : certProList){
						idSet.add(h.getRelationId());
						for(SupplierCertPro certPro : listSupplierCertPros){
							if(h.getRelationId().equals(certPro.getId())){
								idSet.remove(h.getRelationId());
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
									if (h.getBeforeField().equals("expStartDate") && !h.getBeforeContent().equals(format.format(certPro.getExpStartDate()))) {
										supplierModify.setBeforeField("expStartDate");
										supplierModify.setBeforeContent(h.getBeforeContent());
										supplierModifyMapper.insertSelective(supplierModify);
									}
									
									// 	有效期（结束时间）
									if (h.getBeforeField().equals("expEndDate") && !h.getBeforeContent().equals(format.format(certPro.getExpEndDate()))) {
										supplierModify.setBeforeField("expEndDate");
										supplierModify.setBeforeContent(h.getBeforeContent());
										supplierModifyMapper.insertSelective(supplierModify);
									}
									
									// 证书状态
									if (h.getBeforeField().equals("mot") && !h.getBeforeContent().equals(certPro.getMot())) {
										supplierModify.setBeforeField("mot");
										supplierModify.setBeforeContent(h.getBeforeContent());
										supplierModifyMapper.insertSelective(supplierModify);
									}
								}
							}
						}
					}
					if(!idSet.isEmpty()){
						for(String id : idSet){
							supplierModify.setRelationId(id);
							supplierModify.setBeforeField("id");
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
		supplierHistory.setModifyType("mat_sell_page");
		List<SupplierHistory> sellList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		if(supplier.getSupplierTypeIds() != null && supplier.getSupplierTypeIds().contains("SALES")){
			supplierModify.setListType(6);
			supplierModify.setModifyType("mat_sell_page");
			SupplierMatSell supplierMatSell = supplier.getSupplierMatSell();
			if(supplierMatSell != null){
				List<SupplierCertSell> listSupplierCertSells = supplierMatSell.getListSupplierCertSells();
				if(sellList != null && !sellList.isEmpty()){
					Set<String> idSet = new HashSet<String>();
					for(SupplierHistory h : sellList){
						idSet.add(h.getRelationId());
						for(SupplierCertSell sell : listSupplierCertSells){
							if(h.getRelationId().equals(sell.getId())){
								idSet.remove(h.getRelationId());
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
									if (h.getBeforeField().equals("expStartDate") && !h.getBeforeContent().equals(format.format(sell.getExpStartDate()))) {
										supplierModify.setBeforeField("expStartDate");
										supplierModify.setBeforeContent(h.getBeforeContent());
										supplierModifyMapper.insertSelective(supplierModify);
									}
									
									// 	有效期（结束时间）
									if (h.getBeforeField().equals("expEndDate") && !h.getBeforeContent().equals(format.format(sell.getExpEndDate()))) {
										supplierModify.setBeforeField("expEndDate");
										supplierModify.setBeforeContent(h.getBeforeContent());
										supplierModifyMapper.insertSelective(supplierModify);
									}
									
									// 证书状态
									if (h.getBeforeField().equals("mot") && !h.getBeforeContent().equals(sell.getMot())) {
										supplierModify.setBeforeField("mot");
										supplierModify.setBeforeContent(h.getBeforeContent());
										supplierModifyMapper.insertSelective(supplierModify);
									}
								}
							}
						}
					}
					if(!idSet.isEmpty()){
						for(String id : idSet){
							supplierModify.setRelationId(id);
							supplierModify.setBeforeField("id");
							supplierModifyMapper.insertSelective(supplierModify);
						}
					}
				}
			}
		}
		
		/**
		 * 保密工程业绩
		 */
		supplierHistory.setModifyType("mat_eng_page");
		supplierHistory.setListType(null);
		List<SupplierHistory> secrecyList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		supplierModify.setModifyType("mat_eng_page");
		SupplierMatEng supplierMatEng = supplier.getSupplierMatEng();
		if(supplierMatEng != null){
			for(SupplierHistory h : secrecyList){
				if(h.getBeforeField() != null && h.getBeforeContent() !=null){
					//国家或军队保密工程业绩
					if(h.getBeforeField().equals("confidentialAchievement") && !h.getBeforeContent().equals(supplierMatEng.getConfidentialAchievement())){
						supplierModify.setBeforeField("confidentialAchievement");
						supplierModify.setBeforeContent(h.getBeforeContent());
						supplierModify.setListType(5);
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//是否有国家或军队保密工程业绩
					if(h.getBeforeField().equals("isHavingConAchi") && !h.getBeforeContent().equals(supplierMatEng.getIsHavingConAchi())){
						supplierModify.setBeforeField("isHavingConAchi");
						supplierModify.setBeforeContent(h.getBeforeContent());
						supplierModify.setListType(5);
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
		
		/**
		 * 工程资质证书
		 */
		supplierHistory.setListType(13);
		supplierHistory.setModifyType("mat_eng_page");
		List<SupplierHistory> engQuaList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(13);
		supplierModify.setModifyType("mat_eng_page");
		
		if(supplier.getSupplierTypeIds() != null && supplier.getSupplierTypeIds().contains("PROJECT")){
			List<SupplierEngQua> listSupplierEngQuas = supplier.getSupplierMatEng().getListSupplierEngQuas();
			if(engQuaList != null && !engQuaList.isEmpty()){
				Set<String> idSet = new HashSet<String>();
				for(SupplierHistory h : engQuaList){
					idSet.add(h.getRelationId());
					for(SupplierEngQua engQua : listSupplierEngQuas){
						if(h.getRelationId().equals(engQua.getId())){
							idSet.remove(h.getRelationId());
							supplierModify.setRelationId(engQua.getId());
							
							if(h.getBeforeField() != null && h.getBeforeContent() !=null){
								// 资质证书名称
								if (h.getBeforeField().equals("name") && !h.getBeforeContent().equals(engQua.getName())) {
									supplierModify.setBeforeField("name");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								// 证书编号	
								if (h.getBeforeField().equals("code") && !h.getBeforeContent().equals(engQua.getCode())) {
									supplierModify.setBeforeField("code");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								// 资质等级	
								if (h.getBeforeField().equals("levelCert") && !h.getBeforeContent().equals(engQua.getLevelCert())) {
									supplierModify.setBeforeField("levelCert");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								// 发证机关或机构
								if (h.getBeforeField().equals("licenceAuthorith") && !h.getBeforeContent().equals(engQua.getLicenceAuthorith())) {
									supplierModify.setBeforeField("licenceAuthorith");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								// 资有效期（起始时间）
								if (h.getBeforeField().equals("expStartDate") && !h.getBeforeContent().equals(format.format(engQua.getExpStartDate()))) {
									supplierModify.setBeforeField("expStartDate");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								// 	有效期（结束时间）
								if (h.getBeforeField().equals("expEndDate") && !h.getBeforeContent().equals(format.format(engQua.getExpEndDate()))) {
									supplierModify.setBeforeField("expEndDate");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								// 证书状态
								if (h.getBeforeField().equals("mot") && !h.getBeforeContent().equals(engQua.getMot())) {
									supplierModify.setBeforeField("mot");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
							}
						}
					}
				}
				if(!idSet.isEmpty()){
					for(String id : idSet){
						supplierModify.setRelationId(id);
						supplierModify.setBeforeField("id");
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
		
		/**
		 * 工程-注册人员
		 */
		supplierHistory.setListType(7);
		supplierHistory.setModifyType("mat_eng_page");
		List<SupplierHistory> engList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(7);
		supplierModify.setModifyType("mat_eng_page");
		
		if(supplierMatEng != null){
			List<SupplierRegPerson> listSupplierRegPersons = supplierMatEng.getListSupplierRegPersons();
			/*List<SupplierRegPerson> listSupplierRegPersons = supplier.getSupplierMatEng().getListSupplierRegPersons();*/
			if(engList != null && !engList.isEmpty()){
				Set<String> idSet = new HashSet<String>();
				for(SupplierHistory h : engList){
					idSet.add(h.getRelationId());
					for(SupplierRegPerson regPerson : listSupplierRegPersons){
						if(h.getRelationId().equals(regPerson.getId())){
							idSet.remove(h.getRelationId());
							supplierModify.setRelationId(regPerson.getId());
							if(h.getBeforeField() != null && h.getBeforeContent() !=null){
								// 注册资格名称
								if (h.getBeforeField().equals("regType") && !h.getBeforeContent().equals(regPerson.getRegType())) {
									supplierModify.setBeforeField("regType");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								//注册人姓名
								if (h.getBeforeField().equals("regNumber") && !h.getBeforeContent().equals(regPerson.getRegNumber())) {
									supplierModify.setBeforeField("regNumber");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
							}
						}
					}
				}
				if(!idSet.isEmpty()){
					for(String id : idSet){
						supplierModify.setRelationId(id);
						supplierModify.setBeforeField("id");
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
		
		
				
		/**
		 * 工程-证书信息
		 */
		supplierHistory.setListType(8);
		supplierHistory.setModifyType("mat_eng_page");
		List<SupplierHistory> certEngList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(8);
		supplierModify.setModifyType("mat_eng_page");
		if(supplier.getSupplierTypeIds() != null && supplier.getSupplierTypeIds().contains("PROJECT")){
			SupplierMatEng matEng = supplier.getSupplierMatEng();
			if(matEng != null){
				List<SupplierCertEng> listSupplierCertEngs = matEng.getListSupplierCertEngs();
				/*List<SupplierCertEng> listSupplierCertEngs = supplier.getSupplierMatEng().getListSupplierCertEngs();*/
				if(certEngList != null && !certEngList.isEmpty()){
					Set<String> idSet = new HashSet<String>();
					for(SupplierHistory h : certEngList){
						idSet.add(h.getRelationId());
						for(SupplierCertEng certEng : listSupplierCertEngs){
							if(h.getRelationId().equals(certEng.getId())){
								idSet.remove(h.getRelationId());
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
					if(!idSet.isEmpty()){
						for(String id : idSet){
							supplierModify.setRelationId(id);
							supplierModify.setBeforeField("id");
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
		supplierHistory.setModifyType("mat_eng_page");
		List<SupplierHistory> aptitutesList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(9);
		supplierModify.setModifyType("mat_eng_page");
		
		if(supplier.getSupplierTypeIds() != null && supplier.getSupplierTypeIds().contains("PROJECT")){
			List<SupplierAptitute> listSupplierAptitutes = supplier.getSupplierMatEng().getListSupplierAptitutes();
			if(aptitutesList != null && !aptitutesList.isEmpty()){
				Set<String> idSet = new HashSet<String>();
				for(SupplierHistory h : aptitutesList){
					idSet.add(h.getRelationId());
					for(SupplierAptitute aptitute : listSupplierAptitutes){
						if(h.getRelationId().equals(aptitute.getId())){
							idSet.remove(h.getRelationId());
							supplierModify.setRelationId(aptitute.getId());
							if(h.getBeforeField() != null && h.getBeforeContent() !=null){
								// 证书名称
								if (h.getBeforeField().equals("certName") && !h.getBeforeContent().equals(aptitute.getCertName())) {
									supplierModify.setBeforeField("certName");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								//资质类型
								if (h.getBeforeField().equals("certType") && !h.getBeforeContent().equals(aptitute.getCertType())) {
									supplierModify.setBeforeField("certType");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								//证书编号
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
				if(!idSet.isEmpty()){
					for(String id : idSet){
						supplierModify.setRelationId(id);
						supplierModify.setBeforeField("id");
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
		/**
		 * 服务证书
		 */
		supplierHistory.setListType(10);
		supplierHistory.setModifyType("mat_serve_page");
		List<SupplierHistory> serveList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		
		supplierModify.setListType(10);
		supplierModify.setModifyType("mat_serve_page");
		
		if(supplier.getSupplierTypeIds() != null && supplier.getSupplierTypeIds().contains("SERVICE")){
			List<SupplierCertServe> listSupplierCertSes = supplier.getSupplierMatSe().getListSupplierCertSes();
			if(serveList != null && !serveList.isEmpty()){
				Set<String> idSet = new HashSet<String>();
				for(SupplierHistory h : serveList){
					idSet.add(h.getRelationId());
					for(SupplierCertServe certServe : listSupplierCertSes){
						if(h.getRelationId().equals(certServe.getId())){
							idSet.remove(h.getRelationId());
							supplierModify.setRelationId(certServe.getId());
							
							if(h.getBeforeField() != null && h.getBeforeContent() !=null){
								// 资质证书名称
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
								if (h.getBeforeField().equals("expStartDate") && !h.getBeforeContent().equals(format.format(certServe.getExpStartDate()))) {
									supplierModify.setBeforeField("expStartDate");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								// 	有效期（结束时间）
								if (h.getBeforeField().equals("expEndDate") && !h.getBeforeContent().equals(format.format(certServe.getExpEndDate()))) {
									supplierModify.setBeforeField("expEndDate");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
								
								// 证书状态
								if (h.getBeforeField().equals("mot") && !h.getBeforeContent().equals(certServe.getMot())) {
									supplierModify.setBeforeField("mot");
									supplierModify.setBeforeContent(h.getBeforeContent());
									supplierModifyMapper.insertSelective(supplierModify);
								}
							}
						}
					}
				}
				if(!idSet.isEmpty()){
					for(String id : idSet){
						supplierModify.setRelationId(id);
						supplierModify.setBeforeField("id");
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
		/**
		 * 售后服务机构一览表
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setModifyType("basic_page");
		supplierHistory.setListType(11);
		List<SupplierHistory> afterSaleDepList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		List<SupplierAfterSaleDep> listSupplierAfterSaleDep = supplier.getListSupplierAfterSaleDep();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(11);
		supplierModify.setModifyType("basic_page");
		
		if(afterSaleDepList != null && !afterSaleDepList.isEmpty()){
			Set<String> idSet = new HashSet<String>();
			for(SupplierHistory history : afterSaleDepList){
				idSet.add(history.getRelationId());
				for(SupplierAfterSaleDep afterSaleDep: listSupplierAfterSaleDep){
					if(history.getRelationId().equals(afterSaleDep.getId())){
						idSet.remove(history.getRelationId());
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
								if(history.getBeforeContent().equals("1")){
									supplierModify.setBeforeContent("自营");
								}
								if(history.getBeforeContent().equals("2")){
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
			if(!idSet.isEmpty()){
				for(String id : idSet){
					supplierModify.setRelationId(id);
					supplierModify.setBeforeField("id");
					supplierModifyMapper.insertSelective(supplierModify);
				}
			}
		}
	}

	/**
	 * @Title: findBySupplierId
	  
	 * @date 2017-2-17 上午10:21:59  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return SupplierModify
	 */
	@Override
	public SupplierModify findBySupplierId(SupplierModify supplierModify) {
		List<SupplierModify> findBySupplierId = supplierModifyMapper.findBySupplierId(supplierModify);
		if(findBySupplierId!=null&&findBySupplierId.size()>0){
			return findBySupplierId.get(0);
		}
		return null;
	}

	/**
     * @Title: add
      
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
	
	/**
     * @Title: add
      
     * @date 2017-4-26 下午2:48:40  
     * @Description:插入审核退回后修改的附件
     * @param @param supplierModify      
     * @return void
     */
	@Override
	public void addFileInfo(String businessId, String fileTypeId) {
		SupplierModify supplierModify = new SupplierModify();
		Supplier supplier;
		
		/**
		 * 基本信息附件
		 */
		supplier = supplierService.selectById(businessId);
		if(supplier !=null){
			if(supplier != null && supplier.getStatus() == 2){
				supplierModify.setModifyType("file");
				supplierModify.setBeforeField(fileTypeId);
				supplierModify.setSupplierId(businessId);
				supplierModifyMapper.delete(supplierModify);
				supplierModifyMapper.insertSelective(supplierModify);
			}
		}
		
		
		/**
		 * 财务附件
		 */
		
		String financeSupplierId = supplierFinanceMapper.findSupplierIdById(businessId);
		if(financeSupplierId !=null){
			supplier = supplierService.selectById(financeSupplierId);
			if(supplier != null && supplier.getStatus() == 2){
				supplierModify.setModifyType("file");
				supplierModify.setBeforeField(fileTypeId);
				supplierModify.setSupplierId(financeSupplierId);
				supplierModify.setRelationId(businessId);
				supplierModifyMapper.delete(supplierModify);
				supplierModifyMapper.insertSelective(supplierModify);
			}
		}
		
		/**
		 * 供应商类型
		 */
		//生产
		String proSupplierId = supplierMatProMapper.findSupplierIdById(businessId);
		if(proSupplierId !=null){
			supplier = supplierService.selectById(proSupplierId);
			if(supplier != null && supplier.getStatus() == 2){
				supplierModify.setModifyType("file");
				supplierModify.setBeforeField(fileTypeId);
				supplierModify.setSupplierId(proSupplierId);
				supplierModify.setRelationId(businessId);
				supplierModifyMapper.delete(supplierModify);
				supplierModifyMapper.insertSelective(supplierModify);
			}
		}
		
		//销售
		String sellSupplierId = supplierMatSellMapper.findSupplierIdById(businessId);
		if(sellSupplierId !=null){
			supplier = supplierService.selectById(sellSupplierId);
			if(supplier != null && supplier.getStatus() == 2){
				supplierModify.setModifyType("file");
				supplierModify.setBeforeField(fileTypeId);
				supplierModify.setSupplierId(sellSupplierId);
				supplierModify.setRelationId(businessId);
				supplierModifyMapper.delete(supplierModify);
				supplierModifyMapper.insertSelective(supplierModify);
			}
		}
		
		//工程--资质证书信息
		String engQuaSupplierId  = supplierMatEngMapper.findSupplierIdByEngQuaId(businessId);
		if(engQuaSupplierId !=null){
			supplier = supplierService.selectById(engQuaSupplierId);
			if(supplier != null && supplier.getStatus() == 2){
				supplierModify.setModifyType("file");
				supplierModify.setBeforeField(fileTypeId);
				supplierModify.setSupplierId(engQuaSupplierId);
				supplierModify.setRelationId(businessId);
				supplierModifyMapper.delete(supplierModify);
				supplierModifyMapper.insertSelective(supplierModify);
			}
		}
		//工程--资质证书详细信息
		String engAptSupplierId  = supplierMatEngMapper.findSupplierIdByEngAptId(businessId);
		if(engAptSupplierId !=null){
			supplier = supplierService.selectById(engAptSupplierId);
			if(supplier != null && supplier.getStatus() == 2){
				supplierModify.setModifyType("file");
				supplierModify.setBeforeField(fileTypeId);
				supplierModify.setSupplierId(engAptSupplierId);
				supplierModify.setRelationId(businessId);
				supplierModifyMapper.delete(supplierModify);
				supplierModifyMapper.insertSelective(supplierModify);
			}
		}
		
		//服务
		String serSupplierId  = supplierMatServeMapper.findSupplierIdById(businessId);
		if(serSupplierId !=null){
			supplier = supplierService.selectById(serSupplierId);
			if(supplier != null && supplier.getStatus() == 2){
				supplierModify.setModifyType("file");
				supplierModify.setBeforeField(fileTypeId);
				supplierModify.setSupplierId(serSupplierId);
				supplierModify.setRelationId(businessId);
				supplierModifyMapper.delete(supplierModify);
				supplierModifyMapper.insertSelective(supplierModify);
			}
		}
		
		/**
		 * 资质文件
		 */
		if(businessId.length() > 32){
			String obj = businessId.substring(0, 32);
			String lsitId = businessId.substring(32, businessId.length());
			SupplierItem supplierItem = supplierItemMapper.selectByPrimaryKey(obj);
			if(supplierItem!=null){
				String supplierId = supplierItem.getSupplierId();
				if(supplierId !=null){
					supplier = supplierService.selectById(supplierId);
					if(supplier != null && supplier.getStatus() == 2){
						supplierModify.setModifyType("file");
						supplierModify.setBeforeField(businessId);
						supplierModify.setSupplierId(supplierId);
						supplierModify.setRelationId(lsitId);
						supplierModifyMapper.delete(supplierModify);
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
		/**
		 * 销售和同
		 */
		SupplierItem items = supplierItemMapper.selectByPrimaryKey(businessId);
		if(items !=null){
			String supplierId = items.getSupplierId();
			if(supplierId !=null){
				supplier = supplierService.selectById(supplierId);
				if(supplier != null && supplier.getStatus() == 2){
					supplierModify.setModifyType("file");
					supplierModify.setBeforeField(fileTypeId);
					supplierModify.setSupplierId(supplierId);
					supplierModify.setRelationId(businessId);
					supplierModifyMapper.delete(supplierModify);
					supplierModifyMapper.insertSelective(supplierModify);
				}
			}
		}
		
		//房屋证明附件
		SupplierAddress supplierAddress = supplierAddressService.selectById(businessId);
		if(supplierAddress !=null){
			String supplierId = supplierAddress.getSupplierId();
			if(supplierId != null){
				supplier = supplierService.selectById(supplierId);
				if(supplier != null && supplier.getStatus() == 2){
					supplierModify.setModifyType("file");
					supplierModify.setBeforeField(fileTypeId);
					supplierModify.setSupplierId(supplierId);
					supplierModify.setRelationId(businessId);
					supplierModifyMapper.delete(supplierModify);
					supplierModifyMapper.insertSelective(supplierModify);
				}
			}
		}
		
		//承揽业务范围附件
		if(businessId.indexOf("_") > 0){
			String[] ary = businessId.split("_");
			if(ary != null && ary.length > 1){
				String supplierId = ary[0];
				String areaId = ary[1];
				if(supplierId != null){
					supplier = supplierService.selectById(supplierId);
					if(supplier != null && supplier.getStatus() == 2){
						supplierModify.setModifyType("file");
						supplierModify.setBeforeField(fileTypeId);
						supplierModify.setSupplierId(supplierId);
						supplierModify.setRelationId(areaId);
						supplierModifyMapper.delete(supplierModify);
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
	}
	
	
	/**
     * @Title: updateIsDeleteBySupplierId
      
     * @date 2017-4-28 下午3:50:56  
     * @Description:软删除历史记录
     * @param @param SupplierModify      
     * @return void
     */
	@Override
	public void updateIsDeleteBySupplierId(SupplierModify supplierModify) {
		supplierModifyMapper.updateIsDeleteBySupplierId(supplierModify);
		
	}

	@Override
	public int countBySupplierId(SupplierModify supplierModify) {
		return supplierModifyMapper.countBySupplierId(supplierModify);
	}	
}
