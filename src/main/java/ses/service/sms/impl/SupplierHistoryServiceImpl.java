package ses.service.sms.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ses.dao.sms.SupplierHistoryMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
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
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierService;

@Service(value = "supplierHistoryService")
public class SupplierHistoryServiceImpl implements SupplierHistoryService{

	@Autowired
	private SupplierHistoryMapper supplierHistoryMapper;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private AreaServiceI areaService;
	
	@Autowired
	private SupplierTypeRelateMapper supplierTypeRelateMapper;
	
	/**地址信息**/
	private static final int ADDRESS_LIST = 1;
	
	/**境外分支信息**/
	private static final int BRANCH_LIST = 2;
	
	/**近三年财务信息**/
	private static final int FINANCES_LIST = 3;
	
	/**股东信息**/
	private static final int HOLDER_LIST = 4;
	
	/**物资生产-资质证书信息**/
	private static final int CERT_PRO_LIST = 5;
	
	/**物资销售-资质证书信息**/
	private static final int CERT_SALES_LIST = 6;
	
	/**工程-注册人员信息**/
	private static final int REG_PERSON_LIST = 7;
	
	/**工程-证书（认证）信息**/
	private static final int CERT_ENGS_LIST = 8;
	
	/**工程-资质证书详细信息**/
	private static final int CERT_APT_LIST = 9;
	
	/**工程-资质证书信息**/
	private static final int ENG_QUA_LIST = 13;
	
	/**物资服务-资质证书信息**/
	private static final int CERT_SE_LIST = 10;
	
	/**售后服务机构一览表**/
	private static final int AFTER_SALE_SERVICE_LIST = 11;
	
	/**供应商类型**/
	private static final int SUPPLIER_TYPE_LIST = 12;
	
	public void  add(SupplierHistory supplierHistory){
		supplierHistoryMapper.insertSelective(supplierHistory);
	}
	
	public SupplierHistory findBySupplierId(SupplierHistory supplierHistory) {
		return supplierHistoryMapper.selectBySupplierId(supplierHistory);
	}

	@Override
	public List<SupplierHistory> selectAllBySupplierId(SupplierHistory supplierHistory) {
		
		return supplierHistoryMapper.selectAllBySupplierId(supplierHistory);
	}

    /**
     * @see ses.service.sms.SupplierHistoryService#delete(ses.model.sms.SupplierHistory)
     */
    @Override
    public void delete(SupplierHistory supplierHistory) {
        supplierHistoryMapper.delete(supplierHistory);
    }

    /**
     * @see ses.service.sms.SupplierHistoryService#insertHistoryInfo(java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertHistoryInfo(String supplierId) {
        Date date = new Date();
        SupplierHistory historyInfo = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        // 获取供应商信息
        Supplier supplier = supplierService.get(supplierId);
        
        //供应商类型
        List<SupplierTypeRelate> supplierType = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplierId);
        for(SupplierTypeRelate type : supplierType){
        	if(type !=null){
        		historyInfo = new SupplierHistory();
	           	String id = UUID.randomUUID().toString().replaceAll("-", "");
	           	historyInfo.setId(id);
                historyInfo.setSupplierId(supplierId);
                historyInfo.setModifyType("supplier_type");
                historyInfo.setListType(SUPPLIER_TYPE_LIST);
                historyInfo.setCreatedAt(date);

                // 名称
                if(type.getSupplierTypeName() !=null){
               	 	historyInfo.setBeforeField(type.getSupplierTypeId());
                    historyInfo.setBeforeContent(type.getSupplierTypeName());
                    historyInfo.setRelationId(type.getId());
                    supplierHistoryMapper.insertSelective(historyInfo);
                }
        		
        	}   
        }
        
        
        // 生产经营地址信息
        List<SupplierAddress> addressList = supplier.getAddressList();
        if (addressList != null && addressList.size() > 0) {
            for (SupplierAddress address : addressList) {
            	if(address !=null){
            		historyInfo = new SupplierHistory();
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setModifyType("basic_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(address.getId());
                    historyInfo.setListType(ADDRESS_LIST);
                    // 邮编
                    if(address.getCode() !=null){
                    	historyInfo.setBeforeField("code");
                        historyInfo.setBeforeContent(address.getCode());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    	
                    }
                    
                    //生产经营地址：
    				if(address.getAddress() !=null){
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
    	                historyInfo.setBeforeField("residence");
    	                historyInfo.setBeforeContent(parentAddress + sonAddress);
    	                supplierHistoryMapper.insertSelective(historyInfo);             	            	
    				}

                    //生产经营详细地址
    				if(address.getDetailAddress() !=null){
    					historyInfo.setBeforeField("detailedResidence");
    	                historyInfo.setBeforeContent(address.getDetailAddress());
    	                supplierHistoryMapper.insertSelective(historyInfo);              	          	
    				}
            	} 
            }
        }
        
        // 境外地址信息
        List<SupplierBranch> branchList = supplier.getBranchList();
        if (branchList != null && branchList.size() > 0) {
            for (SupplierBranch branch : branchList) {
            	if(branch !=null){
            		historyInfo = new SupplierHistory();
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setModifyType("basic_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(branch.getId());
                    historyInfo.setListType(BRANCH_LIST);

                    // 名称
                    if(branch.getOrganizationName() !=null){
                    	historyInfo.setBeforeField("organizationName");
                        historyInfo.setBeforeContent(branch.getOrganizationName());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 国家
                    if(branch.getCountryName() !=null){
                    	historyInfo.setBeforeField("countryName");
                        historyInfo.setBeforeContent(branch.getCountryName());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }

                    // 地址
                    if(branch.getDetailAddress() !=null){
                    	historyInfo.setBeforeField("detailAddress");
                        historyInfo.setBeforeContent(branch.getDetailAddress());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                       
                    // 生产经营范围
                    if(branch.getBusinessSope() !=null){
                    	historyInfo.setBeforeField("businessSope");
                        historyInfo.setBeforeContent(branch.getBusinessSope());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
            	}  
            }
        }
        
        // 近三年财务信息
        List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances();
        if (listSupplierFinances != null && listSupplierFinances.size() > 0) {
            for (SupplierFinance finances : listSupplierFinances) {
            	if(finances !=null){
            		historyInfo = new SupplierHistory();
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setModifyType("finance_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(finances.getId());
                    historyInfo.setListType(FINANCES_LIST);
                    // 年份
                    if(finances.getYear() !=null){
                    	historyInfo.setBeforeField("year");
                        historyInfo.setBeforeContent(finances.getYear());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 名称
                    if(finances.getName() !=null){
                    	historyInfo.setBeforeField("name");
                        historyInfo.setBeforeContent(finances.getName());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }

                    // 联系电话
                    if(finances.getTelephone() !=null){
                    	 historyInfo.setBeforeField("telephone");
                         historyInfo.setBeforeContent(finances.getTelephone());
                         supplierHistoryMapper.insertSelective(historyInfo);
                    }
                   
                    // 姓名
                    if(finances.getAuditors() !=null){
                    	historyInfo.setBeforeField("auditors");
                        historyInfo.setBeforeContent(finances.getAuditors());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 资产总额
                    if(finances.getTotalAssets() !=null){
                    	historyInfo.setBeforeField("totalAssets");
                        historyInfo.setBeforeContent(finances.getTotalAssets().toString());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 负债总额
                    if(finances.getTotalLiabilities() !=null){
                    	 historyInfo.setBeforeField("totalLiabilities");
                         historyInfo.setBeforeContent(finances.getTotalLiabilities().toString());
                         supplierHistoryMapper.insertSelective(historyInfo);
                    }
                   
                    // 净资产总额
                    if(finances.getTotalNetAssets() !=null){
                    	 historyInfo.setBeforeField("totalNetAssets");
                         historyInfo.setBeforeContent(finances.getTotalNetAssets().toString());
                         supplierHistoryMapper.insertSelective(historyInfo);
                    }
                   
                    // 营业收入
                    if(finances.getTaking() !=null){
                    	historyInfo.setBeforeField("taking");
                        historyInfo.setBeforeContent(finances.getTaking().toString());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
            	}  
            }
        }
     
        // 股东信息
        List<SupplierStockholder> listSupplierStockholders = supplier.getListSupplierStockholders();
        if (listSupplierStockholders != null && listSupplierStockholders.size() > 0) {
            for (SupplierStockholder holder : listSupplierStockholders) {
            	if(holder !=null){
            		historyInfo = new SupplierHistory();
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setModifyType("shareholder_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(holder.getId());
                    historyInfo.setListType(HOLDER_LIST);
        
                    // 出资人性质
                    if(holder.getNature() !=null){
                    	historyInfo.setBeforeField("nature");
                        historyInfo.setBeforeContent(holder.getNature());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 出资人姓名
                    if(holder.getName() !=null){
                    	historyInfo.setBeforeField("name");
                        historyInfo.setBeforeContent(holder.getName());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
					
					// 证件类型
                    if(holder.getIdentityType() !=null){
                    	historyInfo.setBeforeField("identityType");
                        historyInfo.setBeforeContent(holder.getIdentityType().toString());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 出资人社会统一信用代码
                    if(holder.getIdentity() !=null){
                    	historyInfo.setBeforeField("identity");
                        historyInfo.setBeforeContent(holder.getIdentity());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 出资人股份
                    if(holder.getShares() !=null){
                    	historyInfo.setBeforeField("shares");
                        historyInfo.setBeforeContent(holder.getShares());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 比例
                    if(holder.getProportion() !=null){
                    	 historyInfo.setBeforeField("proportion");
                         historyInfo.setBeforeContent(holder.getProportion());
                         supplierHistoryMapper.insertSelective(historyInfo);
                    }
            	} 
            }
        }
        
        // 物资生产--信息
        SupplierMatPro supplierMatPro = supplier.getSupplierMatPro();
        if (supplierMatPro != null) {
            historyInfo = new SupplierHistory();
            historyInfo.setSupplierId(supplierId);
            historyInfo.setModifyType("mat_pro_page");
            historyInfo.setCreatedAt(date);
            historyInfo.setRelationId(supplierMatPro.getId());
            
            /*// 组织机构
            historyInfo.setBeforeField("orgName");
            historyInfo.setBeforeContent(supplierMatPro.getOrgName());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 人员总数
            historyInfo.setBeforeField("totalPerson");
            historyInfo.setBeforeContent(supplierMatPro.getTotalPerson().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 管理人员数量
            historyInfo.setBeforeField("totalMange");
            historyInfo.setBeforeContent(supplierMatPro.getTotalMange().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 技术人员数量
            historyInfo.setBeforeField("totalTech");
            historyInfo.setBeforeContent(supplierMatPro.getTotalTech().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 工人数量
            historyInfo.setBeforeField("totalWorker");
            historyInfo.setBeforeContent(supplierMatPro.getTotalWorker().toString());
            supplierHistoryMapper.insertSelective(historyInfo);*/
            
            // 技术人员数量比例
            if(supplierMatPro.getScaleTech() !=null){
            	historyInfo.setBeforeField("scaleTech");
                historyInfo.setBeforeContent(supplierMatPro.getScaleTech());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
            
            // 高级技术人员数量比例
            if(supplierMatPro.getScaleHeightTech() !=null){
            	historyInfo.setBeforeField("scaleHeightTech");
                historyInfo.setBeforeContent(supplierMatPro.getScaleHeightTech());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
            
            // 研发部门名称
            if(supplierMatPro.getResearchName() !=null){
            	historyInfo.setBeforeField("researchName");
                historyInfo.setBeforeContent(supplierMatPro.getResearchName());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
            
            // 研发部门人数
            if(supplierMatPro.getTotalResearch() !=null){
            	historyInfo.setBeforeField("totalResearch");
                historyInfo.setBeforeContent(supplierMatPro.getTotalResearch().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
            
            // 研发部门负责人
            if(supplierMatPro.getResearchLead() !=null){
            	historyInfo.setBeforeField("researchLead");
                historyInfo.setBeforeContent(supplierMatPro.getResearchLead());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
            
            // 国家军队科研项目
            // 由于是非必填信息，字段不做非空判断
//            if(supplierMatPro.getCountryPro() !=null){
            	historyInfo.setBeforeField("countryPro");
                historyInfo.setBeforeContent(supplierMatPro.getCountryPro() == null ? "" : supplierMatPro.getCountryPro());
                supplierHistoryMapper.insertSelective(historyInfo);
//            }
            
            // 国家军队科技奖项
            // 由于是非必填信息，字段不做非空判断
//            if(supplierMatPro.getCountryReward() !=null){
            	historyInfo.setBeforeField("countryReward");
                historyInfo.setBeforeContent(supplierMatPro.getCountryReward() == null ? "" : supplierMatPro.getCountryReward());
                supplierHistoryMapper.insertSelective(historyInfo);
//            }
            
            
            /*// 生产线数量
            historyInfo.setBeforeField("totalBeltline");
            historyInfo.setBeforeContent(supplierMatPro.getTotalBeltline().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 生产设备数量
            historyInfo.setBeforeField("totalDevice");
            historyInfo.setBeforeContent(supplierMatPro.getTotalDevice().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 质量检测部门
            historyInfo.setBeforeField("qcName");
            historyInfo.setBeforeContent(supplierMatPro.getQcName());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 质量部门人数
            historyInfo.setBeforeField("totalQc");
            historyInfo.setBeforeContent(supplierMatPro.getTotalQc().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 组织机构
            historyInfo.setBeforeField("orgName");
            historyInfo.setBeforeContent(supplierMatPro.getOrgName());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 质监部门负责人
            historyInfo.setBeforeField("qcLead");
            historyInfo.setBeforeContent(supplierMatPro.getQcLead());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 质量检测设备名称
            historyInfo.setBeforeField("qcDevice");
            historyInfo.setBeforeContent(supplierMatPro.getQcDevice());
            supplierHistoryMapper.insertSelective(historyInfo);*/
            
            // 物资生产--资质证书信息
            List<SupplierCertPro> listSupplierCertPros = supplierMatPro.getListSupplierCertPros();
            if (listSupplierCertPros != null && listSupplierCertPros.size() > 0) {
                for (SupplierCertPro certPro : listSupplierCertPros) {
                	if(certPro !=null){
                		historyInfo = new SupplierHistory();
                        historyInfo.setSupplierId(supplierId);
                        historyInfo.setModifyType("mat_pro_page");
                        historyInfo.setCreatedAt(date);
                        historyInfo.setRelationId(certPro.getId());
                        historyInfo.setListType(CERT_PRO_LIST);
                        
                        // 资质证书名称
                        if(certPro.getName() !=null){
                        	historyInfo.setBeforeField("name");
                            historyInfo.setBeforeContent(certPro.getName());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        // 证书编号
                        if(certPro.getCode() !=null){
                        	 historyInfo.setBeforeField("code");
                             historyInfo.setBeforeContent(certPro.getCode());
                             supplierHistoryMapper.insertSelective(historyInfo);
                        }
                       
                        // 资质等级
                        if(certPro.getLevelCert() !=null){
                        	historyInfo.setBeforeField("levelCert");
                            historyInfo.setBeforeContent(certPro.getLevelCert());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        // 发证机关
                        if(certPro.getLicenceAuthorith() !=null){
                        	historyInfo.setBeforeField("licenceAuthorith");
                            historyInfo.setBeforeContent(certPro.getLicenceAuthorith());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        // 有效期（起始时间）
                        if(certPro.getExpStartDate() != null ){
                        	historyInfo.setBeforeField("expStartDate");
                        	 historyInfo.setBeforeContent(format.format(certPro.getExpStartDate()));
                        	 supplierHistoryMapper.insertSelective(historyInfo);
                        }
                       
                        // 有效期（结束时间）
                        if(certPro.getExpEndDate() != null){
                        	historyInfo.setBeforeContent(format.format(certPro.getExpEndDate()));
                        	historyInfo.setBeforeField("expEndDate");
                        	supplierHistoryMapper.insertSelective(historyInfo);
                        	
                        }

                        // 证书状态
                        if(certPro.getMot() !=null){
                        	historyInfo.setBeforeField("mot");
                        	historyInfo.setBeforeContent(certPro.getMot());
                        	supplierHistoryMapper.insertSelective(historyInfo);
                        }
                	}
                }
            }
        }
        
        // 物资销售--信息
        SupplierMatSell supplierMatSell = supplier.getSupplierMatSell();
        /* if(supplierMatSell != null){
        	historyInfo = new SupplierHistory();
            historyInfo.setSupplierId(supplierId);
            historyInfo.setmodifyType("mat_sell_page");
            historyInfo.setCreatedAt(date);
            historyInfo.setRelationId(supplierMatSell.getId());
            
            // 组织机构
            historyInfo.setBeforeField("orgName");
            historyInfo.setBeforeContent(supplierMatSell.getOrgName());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 人员总数
            historyInfo.setBeforeField("totalPerson");
            historyInfo.setBeforeContent(supplierMatSell.getTotalPerson().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 管理人员数量
            historyInfo.setBeforeField("totalMange");
            historyInfo.setBeforeContent(supplierMatSell.getTotalMange().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 技术人员数量
            historyInfo.setBeforeField("totalTech");
            historyInfo.setBeforeContent(supplierMatSell.getTotalTech().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 工人（职员）数量
            historyInfo.setBeforeField("totalWorker");
            historyInfo.setBeforeContent(supplierMatSell.getTotalWorker().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
        }*/
        
        //资质证书信息
        //由于是非必填信息，字段不做非空判断
        if(supplierMatSell !=null){
        	List<SupplierCertSell> listSupplierCertSells = supplierMatSell.getListSupplierCertSells();
            if (listSupplierCertSells != null && listSupplierCertSells.size() > 0) {
            	for (SupplierCertSell certSell : listSupplierCertSells) {
            		if(certSell !=null){
            			historyInfo = new SupplierHistory();
                        historyInfo.setSupplierId(supplierId);
                        historyInfo.setModifyType("mat_sell_page");
                        historyInfo.setCreatedAt(date);
                        historyInfo.setRelationId(certSell.getId());
                        historyInfo.setListType(CERT_SALES_LIST);
                        
                        // 资质证书名称
//                        if(certSell.getName() !=null){
                        	historyInfo.setBeforeField("name");
                            historyInfo.setBeforeContent(certSell.getName() == null ? "" : certSell.getName());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }

                        // 证书编号
//                        if(certSell.getCode() !=null){
                        	historyInfo.setBeforeField("code");
                            historyInfo.setBeforeContent(certSell.getCode() == null ? "" : certSell.getCode());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
                        
                        // 资质等级
//                        if(certSell.getLevelCert() !=null){
                        	historyInfo.setBeforeField("levelCert");
                            historyInfo.setBeforeContent(certSell.getLevelCert() == null ? "" : certSell.getLevelCert());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
                        
                        // 发证机关
//                        if(certSell.getLicenceAuthorith() !=null){
                        	historyInfo.setBeforeField("licenceAuthorith");
                            historyInfo.setBeforeContent(certSell.getLicenceAuthorith() == null ? "" : certSell.getLicenceAuthorith());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
                        
                        // 有效期（起始时间）
//                        if(certSell.getExpStartDate() !=null){
                        	historyInfo.setBeforeField("expStartDate");
                            historyInfo.setBeforeContent(certSell.getExpStartDate() == null ? "" : format.format(certSell.getExpStartDate()));
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
                        
                        // 有效期（结束时间）
//                        if(certSell.getExpEndDate() !=null){
                        	historyInfo.setBeforeField("expEndDate");
                            historyInfo.setBeforeContent(certSell.getExpEndDate() == null ? "" : format.format(certSell.getExpEndDate()));
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
                        
                        //证书状态
//                        if(certSell.getMot() !=null){
                        	historyInfo.setBeforeField("mot");
                            historyInfo.setBeforeContent(certSell.getMot() == null ? "" : certSell.getMot());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
            		}  
            	}
            }
        }
        
        
        // 工程--信息
        SupplierMatEng supplierMatEng = supplier.getSupplierMatEng();
        if (supplierMatEng != null) {
            historyInfo = new SupplierHistory();
            historyInfo.setSupplierId(supplierId);
            historyInfo.setModifyType("mat_eng_page");
            historyInfo.setCreatedAt(date);
            historyInfo.setRelationId(supplierMatEng.getId());
            
            /*// 组织机构
            historyInfo.setBeforeField("orgName");
            historyInfo.setBeforeContent(supplierMatEng.getOrgName());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            //技术负责人数量
            historyInfo.setBeforeField("totalTech");
            historyInfo.setBeforeContent(supplierMatEng.getTotalTech().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 中级以上职称人员数量
            historyInfo.setBeforeField("totalGlNormal");
            historyInfo.setBeforeContent(supplierMatEng.getTotalGlNormal().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 现场管理人员数量
            historyInfo.setBeforeField("totalMange");
            historyInfo.setBeforeContent(supplierMatEng.getTotalMange().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 技术和工人数量
            historyInfo.setBeforeField("totalTechWorker");
            historyInfo.setBeforeContent(supplierMatEng.getTotalTechWorker().toString());
            supplierHistoryMapper.insertSelective(historyInfo);*/
            
            //国家或军队保密工程业绩
            if(supplierMatEng.getConfidentialAchievement() !=null){
            	historyInfo.setBeforeField("confidentialAchievement");
                historyInfo.setBeforeContent(supplierMatEng.getConfidentialAchievement());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
            
            //是否有国家或军队保密工程业绩
            if(supplierMatEng.getIsHavingConAchi() !=null){
            	historyInfo.setBeforeField("isHavingConAchi");
                historyInfo.setBeforeContent(supplierMatEng.getIsHavingConAchi());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
            
            // 资质证书信息
            // 由于是非必填信息，字段不做非空判断
            List<SupplierEngQua> listSupplierEngQuas = supplierMatEng.getListSupplierEngQuas();
            if (listSupplierEngQuas != null && listSupplierEngQuas.size() > 0) {
                for (SupplierEngQua engQua : listSupplierEngQuas) {
                	if(engQua !=null){
                		historyInfo = new SupplierHistory();
                        historyInfo.setSupplierId(supplierId);
                        historyInfo.setModifyType("mat_eng_page");
                        historyInfo.setCreatedAt(date);
                        historyInfo.setRelationId(engQua.getId());
                        historyInfo.setListType(ENG_QUA_LIST);
                        
                        // 资质证书名称
//                        if(engQua.getName() !=null){
                        	historyInfo.setBeforeField("name");
                            historyInfo.setBeforeContent(engQua.getName() == null ? "" : engQua.getName());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
                        
                        // 证书编号
//    					if(engQua.getCode() !=null){
    						historyInfo.setBeforeField("code");
    	                    historyInfo.setBeforeContent(engQua.getCode() == null ? "" : engQua.getCode());
    	                    supplierHistoryMapper.insertSelective(historyInfo);                	
//    					}

                        // 资质等级
//    					if(engQua.getLevelCert() !=null){
    						historyInfo.setBeforeField("levelCert");
    	                    historyInfo.setBeforeContent(engQua.getLevelCert() == null ? "" : engQua.getLevelCert());
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                        
                        // 发证机关
//    					if(engQua.getLicenceAuthorith() !=null){
    						historyInfo.setBeforeField("licenceAuthorith");
    	                    historyInfo.setBeforeContent(engQua.getLicenceAuthorith() == null ? "" : engQua.getLicenceAuthorith());
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                        
                        // 有效期（起始时间）
//    					if(engQua.getExpStartDate() !=null){
    						historyInfo.setBeforeField("expStartDate");
    	                    historyInfo.setBeforeContent(engQua.getExpStartDate() == null ? "" : format.format(engQua.getExpStartDate()));
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                        
                        // 有效期（结束时间）
//    					if(engQua.getExpEndDate() !=null){
    						historyInfo.setBeforeField("expEndDate");
    	                    historyInfo.setBeforeContent(engQua.getExpEndDate() == null ? "" : format.format(engQua.getExpEndDate()));
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                        
                        //证书状态
//    					if(engQua.getMot() !=null){
    						historyInfo.setBeforeField("mot");
    	                    historyInfo.setBeforeContent(engQua.getMot() == null ? "" : engQua.getMot());
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                	}
                }
            }
            
            //注册人员信息
            //由于是非必填信息，字段不做非空判断
            List<SupplierRegPerson> listSupplierRegPersons = supplierMatEng.getListSupplierRegPersons();
            if(listSupplierRegPersons !=null && listSupplierRegPersons.size() > 0){
            	for(SupplierRegPerson regPerson : listSupplierRegPersons){
            		if(regPerson !=null){
            			historyInfo = new SupplierHistory();
                        historyInfo.setSupplierId(supplierId);
                        historyInfo.setModifyType("mat_eng_page");
                        historyInfo.setCreatedAt(date);
                        historyInfo.setRelationId(regPerson.getId());
                        historyInfo.setListType(REG_PERSON_LIST);
                        
                        //注册资格名称
//                        if(regPerson.getRegType() !=null){
                       	 	historyInfo.setBeforeField("regType");
                            historyInfo.setBeforeContent(regPerson.getRegType() == null ? "" : regPerson.getRegType());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
                        
                        //注册人名字
//                        if(regPerson.getRegNumber() !=null){
                       	 	historyInfo.setBeforeField("regNumber");
                            historyInfo.setBeforeContent(regPerson.getRegNumber() == null ? "" : regPerson.getRegNumber());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
            		}	   
                }	
            }
            
            //资质证书（认证）信息
            List<SupplierCertEng> listSupplierCertEngs = supplierMatEng.getListSupplierCertEngs();
            if(listSupplierCertEngs !=null && listSupplierCertEngs.size() >0){
            	for(SupplierCertEng certEng : listSupplierCertEngs){
            		if(certEng !=null){
            			historyInfo = new SupplierHistory();
                        historyInfo.setSupplierId(supplierId);
                        historyInfo.setModifyType("mat_eng_page");
                        historyInfo.setCreatedAt(date);
                        historyInfo.setRelationId(certEng.getId());
                        historyInfo.setListType(CERT_ENGS_LIST);
                        
                		 // 证书名称
                        if(certEng.getCertType() !=null){
                        	historyInfo.setBeforeField("certType");
                            historyInfo.setBeforeContent(certEng.getCertType());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        if(certEng.getCertCode() !=null){
                        	// 证书编号
                            historyInfo.setBeforeField("certCode");
                            historyInfo.setBeforeContent(certEng.getCertCode());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        // 资质资格最高等级
                        if(certEng.getCertMaxLevel() !=null){
                        	historyInfo.setBeforeField("certMaxLevel");
                            historyInfo.setBeforeContent(certEng.getCertMaxLevel());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        
                        /*// 技术负责人姓名
                        historyInfo.setBeforeField("techName");
                        historyInfo.setBeforeContent(certEng.getTechName());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        // 技术负责人职称
                        historyInfo.setBeforeField("techPt");
                        historyInfo.setBeforeContent(certEng.getTechPt());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        // 技术负责人职务
                        historyInfo.setBeforeField("techJop");
                        historyInfo.setBeforeContent(certEng.getTechJop());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        // 单位负责人姓名
                        historyInfo.setBeforeField("depName");
                        historyInfo.setBeforeContent(certEng.getDepName());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        // 单位负责人职称
                        historyInfo.setBeforeField("depPt");
                        historyInfo.setBeforeContent(certEng.getDepPt());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        //单位负责人职务
                        historyInfo.setBeforeField("depJop");
                        historyInfo.setBeforeContent(certEng.getDepJop());
                        supplierHistoryMapper.insertSelective(historyInfo);*/
                        
                        // 发证机关
                        if(certEng.getLicenceAuthorith() !=null){
                        	 historyInfo.setBeforeField("licenceAuthorith");
                             historyInfo.setBeforeContent(certEng.getLicenceAuthorith());
                             supplierHistoryMapper.insertSelective(historyInfo);
                        }
                       
                        
                        // 发证日期
    					if(certEng.getExpStartDate() !=null){
    						historyInfo.setBeforeField("expStartDate");
    	                    historyInfo.setBeforeContent(format.format(certEng.getExpStartDate()));
    	                    supplierHistoryMapper.insertSelective(historyInfo);
    					}
                        
    					// 证书有效期截止日期
    					if(certEng.getExpEndDate() !=null){
    						historyInfo.setBeforeField("expEndDate");
    	                    historyInfo.setBeforeContent(format.format(certEng.getExpEndDate()));
    	                    supplierHistoryMapper.insertSelective(historyInfo);
    					}
                        
                        // 证书状态
                        if(certEng.getCertStatus() !=null){
                        	historyInfo.setBeforeField("certStatus");
                            historyInfo.setBeforeContent(certEng.getCertStatus().toString());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
            		}  
            	}
            }
            
            //资质证书详细信息
            List<SupplierAptitute> listSupplierAptitutes = supplierMatEng.getListSupplierAptitutes();
            if(listSupplierAptitutes != null && listSupplierAptitutes.size() >0){
            	for(SupplierAptitute aptitute : listSupplierAptitutes){
            		if(aptitute !=null){
            			historyInfo = new SupplierHistory();
                        historyInfo.setSupplierId(supplierId);
                        historyInfo.setModifyType("mat_eng_page");
                        historyInfo.setCreatedAt(date);
                        historyInfo.setRelationId(aptitute.getId());
                        historyInfo.setListType(CERT_APT_LIST);
                        
                        //证书名称
                        if(aptitute.getCertType() !=null){
                       	 historyInfo.setBeforeField("certName");
                            historyInfo.setBeforeContent(aptitute.getCertName());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        //资质类型
                        if(aptitute.getCertType() !=null){
                       	 historyInfo.setBeforeField("certType");
                            historyInfo.setBeforeContent(aptitute.getCertType());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        //证书编号
                        if(aptitute.getCertCode() !=null){
                       	 historyInfo.setBeforeField("certCode");
                            historyInfo.setBeforeContent(aptitute.getCertCode());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        //资质序列
                        if(aptitute.getAptituteSequence() !=null){
                       	 historyInfo.setBeforeField("aptituteSequence");
                            historyInfo.setBeforeContent(aptitute.getAptituteSequence());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        //专业类别
                        if(aptitute.getProfessType() !=null){
                       	 historyInfo.setBeforeField("professType");
                            historyInfo.setBeforeContent(aptitute.getProfessType());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        //资质资格等级
                        if(aptitute.getAptituteLevel() !=null){
                       	 historyInfo.setBeforeField("aptituteLevel");
                            historyInfo.setBeforeContent(aptitute.getAptituteLevel());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        //是否主项资质
                        if(aptitute.getIsMajorFund() !=null){
                       	 historyInfo.setBeforeField("isMajorFund");
                            historyInfo.setBeforeContent(aptitute.getIsMajorFund().toString());
                            supplierHistoryMapper.insertSelective(historyInfo);
                        }
                        
                        /*//批准资质资格内容
                        historyInfo.setBeforeField("aptituteContent");
                        historyInfo.setBeforeContent(aptitute.getAptituteContent());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        //首次批准资质资格文号
                        historyInfo.setBeforeField("aptituteCode");
                        historyInfo.setBeforeContent(aptitute.getAptituteCode());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        //首次批准资质资格日期
                        historyInfo.setBeforeField("aptituteDate");
                        historyInfo.setBeforeContent(format.format(aptitute.getAptituteDate()));
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        //资质资格取得方式
                        historyInfo.setBeforeField("aptituteWay");
                        historyInfo.setBeforeContent(aptitute.getAptituteWay());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        //资质资格状态
                        historyInfo.setBeforeField("aptituteStatus");
                        historyInfo.setBeforeContent(aptitute.getAptituteStatus().toString());
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        //资质资格状态变更时间
                        historyInfo.setBeforeField("aptituteChangeAt");
                        historyInfo.setBeforeContent(format.format(aptitute.getAptituteChangeAt()));
                        supplierHistoryMapper.insertSelective(historyInfo);
                        
                        //资质资格状态变更原因
                        historyInfo.setBeforeField("aptituteChangeReason");
                        historyInfo.setBeforeContent(aptitute.getAptituteChangeReason());
                        supplierHistoryMapper.insertSelective(historyInfo);*/
            		}
            	}		 
            }
        }
        
        // 服务--信息
        SupplierMatServe supplierMatSe = supplier.getSupplierMatSe();
        if (supplierMatSe != null) {
            historyInfo = new SupplierHistory();
            historyInfo.setSupplierId(supplierId);
            historyInfo.setModifyType("mat_serve_page");
            historyInfo.setCreatedAt(date);
            historyInfo.setRelationId(supplierMatSe.getId());
            
            /*// 组织机构
            historyInfo.setBeforeField("orgName");
            historyInfo.setBeforeContent(supplierMatSe.getOrgName());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 人员总数
            historyInfo.setBeforeField("totalPerson");
            historyInfo.setBeforeContent(supplierMatSe.getTotalPerson().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 管理人员数量
            historyInfo.setBeforeField("totalMange");
            historyInfo.setBeforeContent(supplierMatSe.getTotalMange().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 技术人员数量
            historyInfo.setBeforeField("totalTech");
            historyInfo.setBeforeContent(supplierMatSe.getTotalTech().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 工人（职员）数量
            historyInfo.setBeforeField("totalWorker");
            historyInfo.setBeforeContent(supplierMatSe.getTotalWorker().toString());
            supplierHistoryMapper.insertSelective(historyInfo);*/
        
            // 资质证书信息
            // 由于是非必填信息，字段不做非空判断
            List<SupplierCertServe> listSupplierCertSes = supplierMatSe.getListSupplierCertSes();
            if (listSupplierCertSes != null && listSupplierCertSes.size() > 0) {
                for (SupplierCertServe matSe : listSupplierCertSes) {
                	if(matSe !=null){
                		historyInfo = new SupplierHistory();
                        historyInfo.setSupplierId(supplierId);
                        historyInfo.setModifyType("mat_serve_page");
                        historyInfo.setCreatedAt(date);
                        historyInfo.setRelationId(matSe.getId());
                        historyInfo.setListType(CERT_SE_LIST);
                        
                        // 资质证书名称
//                        if(matSe.getName() !=null){
                        	historyInfo.setBeforeField("name");
                            historyInfo.setBeforeContent(matSe.getName() == null ? "" : matSe.getName());
                            supplierHistoryMapper.insertSelective(historyInfo);
//                        }
                        
                        // 证书编号
//    					if(matSe.getCode() !=null){
    						historyInfo.setBeforeField("code");
    	                    historyInfo.setBeforeContent(matSe.getCode() == null ? "" : matSe.getCode());
    	                    supplierHistoryMapper.insertSelective(historyInfo);                	
//    					}

                        // 资质等级
//    					if(matSe.getLevelCert() !=null){
    						historyInfo.setBeforeField("levelCert");
    	                    historyInfo.setBeforeContent(matSe.getLevelCert() == null ? "" : matSe.getLevelCert());
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                        
                        // 发证机关
//    					if(matSe.getLicenceAuthorith() !=null){
    						historyInfo.setBeforeField("licenceAuthorith");
    	                    historyInfo.setBeforeContent(matSe.getLicenceAuthorith() == null ? "" : matSe.getLicenceAuthorith());
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                        
                        // 有效期（起始时间）
//    					if(matSe.getExpStartDate() !=null){
    						historyInfo.setBeforeField("expStartDate");
    	                    historyInfo.setBeforeContent(matSe.getExpStartDate() == null ? "" : format.format(matSe.getExpStartDate()));
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                        
                        // 有效期（结束时间）
//    					if(matSe.getExpEndDate() !=null){
    						historyInfo.setBeforeField("expEndDate");
    	                    historyInfo.setBeforeContent(matSe.getExpEndDate() == null ? "" : format.format(matSe.getExpEndDate()));
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                        
                        //证书状态
//    					if(matSe.getMot() !=null){
    						historyInfo.setBeforeField("mot");
    	                    historyInfo.setBeforeContent(matSe.getMot() == null ? "" : matSe.getMot());
    	                    supplierHistoryMapper.insertSelective(historyInfo);
//    					}
                	}
                }
            }
        }
        
        
        // 售后服务机构一览表
        List<SupplierAfterSaleDep> listSupplierAfterSaleDep = supplier.getListSupplierAfterSaleDep();
        if (listSupplierStockholders != null && listSupplierStockholders.size() > 0) {
            for (SupplierAfterSaleDep afterSaleDep : listSupplierAfterSaleDep) {
            	if(afterSaleDep !=null){
            		historyInfo = new SupplierHistory();
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setModifyType("basic_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(afterSaleDep.getId());
                    historyInfo.setListType(AFTER_SALE_SERVICE_LIST);
        
                    // 分支（或服务）机构名称
                    if(afterSaleDep.getName() !=null){
                    	historyInfo.setBeforeField("name");
                        historyInfo.setBeforeContent(afterSaleDep.getName());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    // 类别
                    if(afterSaleDep.getType() !=null){
                    	historyInfo.setBeforeField("type");
                        historyInfo.setBeforeContent(afterSaleDep.getType().toString());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    //所在县市
                    if(afterSaleDep.getAddress() !=null){
                    	historyInfo.setBeforeField("address");
                        historyInfo.setBeforeContent(afterSaleDep.getAddress());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    //负责人
                    if(afterSaleDep.getLeadName() !=null){
                    	historyInfo.setBeforeField("leadName");
                        historyInfo.setBeforeContent(afterSaleDep.getLeadName());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
                    
                    //联系电话
                    if(afterSaleDep.getMobile() !=null){
                    	historyInfo.setBeforeField("mobile");
                        historyInfo.setBeforeContent(afterSaleDep.getMobile());
                        supplierHistoryMapper.insertSelective(historyInfo);
                    }
            	} 
            }
        }
    }

	/**
	 * @Title: updateIsDeleteBySupplierId
	 * @author XuQing 
	 * @date 2017-4-28 下午3:50:56  
	 * @Description:软删除历史记录
	 * @param @param supplierId      
	 * @return void
	 */
	@Override
	public void updateIsDeleteBySupplierId(SupplierHistory supplierHistory) {
		supplierHistoryMapper.updateIsDeleteBySupplierId(supplierHistory);
		
	}

	@Override
	public void softDelete(String supplierId, String relationId) {
		supplierHistoryMapper.softDelete(supplierId, relationId);
	} 
}
