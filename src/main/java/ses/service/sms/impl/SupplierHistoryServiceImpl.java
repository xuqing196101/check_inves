package ses.service.sms.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ses.dao.sms.SupplierHistoryMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierService;
import ses.util.WfUtil;

@Service
public class SupplierHistoryServiceImpl implements SupplierHistoryService{

	@Autowired
	private SupplierHistoryMapper supplierHistoryMapper;
	
	@Autowired
	private SupplierService supplierService;
	
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
	
	/**物资工程-注册人员信息**/
	private static final int REG_PERSON_LIST = 7;
	
	/**物资销售-证书信息**/
	private static final int CERT_ENGS_LIST = 8;
	
	/**物资销售-资质证书信息**/
	private static final int CERT_APT_LIST = 9;
	
	/**物资销售-资质证书信息**/
	private static final int CERT_SE_LIST = 1;
	
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
        
        // 生产经营地址信息
        List<SupplierAddress> addressList = supplier.getAddressList();
        if (addressList != null && addressList.size() > 0) {
            for (SupplierAddress address : addressList) {
                historyInfo = new SupplierHistory();
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("basic_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(address.getId());
    
                // 邮编
                historyInfo.setBeforeField("code" + ADDRESS_LIST);
                historyInfo.setBeforeContent(address.getCode());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 省
                historyInfo.setBeforeField("provinceId" + ADDRESS_LIST);
                historyInfo.setBeforeContent(address.getProvinceId());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 市
                historyInfo.setBeforeField("address" + ADDRESS_LIST);
                historyInfo.setBeforeContent(address.getAddress());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 市
                historyInfo.setBeforeField("detailAddress" + ADDRESS_LIST);
                historyInfo.setBeforeContent(address.getDetailAddress());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
        }
        
        // 境外地址信息
        List<SupplierBranch> branchList = supplier.getBranchList();
        if (branchList != null && branchList.size() > 0) {
            for (SupplierBranch branch : branchList) {
                historyInfo = new SupplierHistory();
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("basic_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(branch.getId());

                // 名称
                historyInfo.setBeforeField("organizationName" + BRANCH_LIST);
                historyInfo.setBeforeContent(branch.getOrganizationName());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 国家
                historyInfo.setBeforeField("country" + BRANCH_LIST);
                historyInfo.setBeforeContent(branch.getCountry());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 地址
                historyInfo.setBeforeField("detailAddress" + BRANCH_LIST);
                historyInfo.setBeforeContent(branch.getDetailAddress());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 生产经营范围
                historyInfo.setBeforeField("businessSope" + BRANCH_LIST);
                historyInfo.setBeforeContent(branch.getBusinessSope());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
        }
        
        // 近三年财务信息
        List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances();
        if (listSupplierFinances != null && listSupplierFinances.size() > 0) {
            for (SupplierFinance finances : listSupplierFinances) {
                historyInfo = new SupplierHistory();
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("finance_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(finances.getId());
    
                // 年份
                historyInfo.setBeforeField("year" + FINANCES_LIST);
                historyInfo.setBeforeContent(finances.getYear());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 名称
                historyInfo.setBeforeField("name" + FINANCES_LIST);
                historyInfo.setBeforeContent(finances.getName());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 联系电话
                historyInfo.setBeforeField("telephone" + FINANCES_LIST);
                historyInfo.setBeforeContent(finances.getTelephone());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 姓名
                historyInfo.setBeforeField("auditors" + FINANCES_LIST);
                historyInfo.setBeforeContent(finances.getAuditors());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 资产总额
                historyInfo.setBeforeField("totalAssets" + FINANCES_LIST);
                historyInfo.setBeforeContent(finances.getTotalAssets().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 负债总额
                historyInfo.setBeforeField("totalLiabilities" + FINANCES_LIST);
                historyInfo.setBeforeContent(finances.getTotalLiabilities().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 净资产总额
                historyInfo.setBeforeField("totalNetAssets" + FINANCES_LIST);
                historyInfo.setBeforeContent(finances.getTotalNetAssets().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 营业收入
                historyInfo.setBeforeField("taking" + FINANCES_LIST);
                historyInfo.setBeforeContent(finances.getTaking().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
        }
     
        // 股东信息
        List<SupplierStockholder> listSupplierStockholders = supplier.getListSupplierStockholders();
        if (listSupplierStockholders != null && listSupplierStockholders.size() > 0) {
            for (SupplierStockholder holder : listSupplierStockholders) {
                historyInfo = new SupplierHistory();
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("shareholder_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(holder.getId());
    
                // 出资人性质
                historyInfo.setBeforeField("nature" + HOLDER_LIST);
                historyInfo.setBeforeContent(holder.getNature());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 出资人姓名
                historyInfo.setBeforeField("name" + HOLDER_LIST);
                historyInfo.setBeforeContent(holder.getName());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 出资人社会统一信用代码
                historyInfo.setBeforeField("identity" + HOLDER_LIST);
                historyInfo.setBeforeContent(holder.getIdentity());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 出资人股份
                historyInfo.setBeforeField("shares" + HOLDER_LIST);
                historyInfo.setBeforeContent(holder.getShares());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 比例
                historyInfo.setBeforeField("proportion" + HOLDER_LIST);
                historyInfo.setBeforeContent(holder.getProportion());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
        }
        
        // 物资生产--信息
        SupplierMatPro supplierMatPro = supplier.getSupplierMatPro();
        if (supplierMatPro != null) {
            historyInfo = new SupplierHistory();
            historyInfo.setSupplierId(supplierId);
            historyInfo.setmodifyType("mat_pro_page");
            historyInfo.setCreatedAt(date);
            historyInfo.setRelationId(supplierMatPro.getId());
            
            // 组织机构
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
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 技术人员数量比例
            historyInfo.setBeforeField("scaleTech");
            historyInfo.setBeforeContent(supplierMatPro.getScaleTech());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 高级技术人员数量比例
            historyInfo.setBeforeField("scaleHeightTech");
            historyInfo.setBeforeContent(supplierMatPro.getScaleHeightTech());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 研发部门名称
            historyInfo.setBeforeField("researchName");
            historyInfo.setBeforeContent(supplierMatPro.getResearchName());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 研发部门人数
            historyInfo.setBeforeField("totalResearch");
            historyInfo.setBeforeContent(supplierMatPro.getTotalResearch().toString());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 研发部门负责人
            historyInfo.setBeforeField("researchLead");
            historyInfo.setBeforeContent(supplierMatPro.getResearchLead());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 国家军队科研项目
            historyInfo.setBeforeField("countryPro");
            historyInfo.setBeforeContent(supplierMatPro.getCountryPro());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 国家军队科技奖项
            historyInfo.setBeforeField("countryReward");
            historyInfo.setBeforeContent(supplierMatPro.getCountryReward());
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 生产线数量
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
            supplierHistoryMapper.insertSelective(historyInfo);
            
            // 物资生产--资质证书信息
            List<SupplierCertPro> listSupplierCertPros = supplierMatPro.getListSupplierCertPros();
            if (listSupplierCertPros != null && listSupplierCertPros.size() > 0) {
                for (SupplierCertPro certPro : listSupplierCertPros) {
                    historyInfo = new SupplierHistory();
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setmodifyType("mat_pro_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(certPro.getId());
                    
                    // 资质证书名称
                    historyInfo.setBeforeField("name" + CERT_PRO_LIST);
                    historyInfo.setBeforeContent(certPro.getName());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 资质等级
                    historyInfo.setBeforeField("levelCert" + CERT_PRO_LIST);
                    historyInfo.setBeforeContent(certPro.getLevelCert());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 发证机关
                    historyInfo.setBeforeField("licenceAuthorith" + CERT_PRO_LIST);
                    historyInfo.setBeforeContent(certPro.getLicenceAuthorith());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 有效期（起始时间）
                    historyInfo.setBeforeField("expStartDate" + CERT_PRO_LIST);
                    historyInfo.setBeforeContent(format.format(certPro.getExpStartDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 有效期（结束时间）
                    historyInfo.setBeforeField("expEndDate" + CERT_PRO_LIST);
                    historyInfo.setBeforeContent(format.format(certPro.getExpEndDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 是否年检
                    historyInfo.setBeforeField("mot" + CERT_PRO_LIST);
                    historyInfo.setBeforeContent(certPro.getMot().toString());
                    supplierHistoryMapper.insertSelective(historyInfo);
                }
            }
        }
        
        // 物资销售--信息
        SupplierMatSell supplierMatSell = supplier.getSupplierMatSell();
        if(supplierMatSell != null){
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
        }
        
        //资质证书信息
        List<SupplierCertSell> listSupplierCertSells = supplierMatSell.getListSupplierCertSells();
        if (listSupplierCertSells != null && listSupplierCertSells.size() > 0) {
        	for (SupplierCertSell certSell : listSupplierCertSells) {
                historyInfo = new SupplierHistory();
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("mat_sell_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(certSell.getId());
                
                // 资质证书名称
                historyInfo.setBeforeField("name" + CERT_SALES_LIST);
                historyInfo.setBeforeContent(certSell.getName());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 资质等级
                historyInfo.setBeforeField("levelCert" + CERT_SALES_LIST);
                historyInfo.setBeforeContent(certSell.getLevelCert());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 发证机关
                historyInfo.setBeforeField("licenceAuthorith" + CERT_SALES_LIST);
                historyInfo.setBeforeContent(certSell.getLicenceAuthorith());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 有效期（起始时间）
                historyInfo.setBeforeField("expStartDate" + CERT_SALES_LIST);
                historyInfo.setBeforeContent(format.format(certSell.getExpStartDate()));
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 有效期（结束时间）
                historyInfo.setBeforeField("expStartDate" + CERT_SALES_LIST);
                historyInfo.setBeforeContent(format.format(certSell.getExpEndDate()));
                supplierHistoryMapper.insertSelective(historyInfo);
                
                //是否年检
                historyInfo.setBeforeField("mot" + CERT_SALES_LIST);
                historyInfo.setBeforeContent(certSell.getMot().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
        	}
        }
        
        // 工程--信息
        SupplierMatEng supplierMatEng = supplier.getSupplierMatEng();
        if (supplierMatEng != null) {
            historyInfo = new SupplierHistory();
            historyInfo.setSupplierId(supplierId);
            historyInfo.setmodifyType(" mat_eng_page");
            historyInfo.setCreatedAt(date);
            historyInfo.setRelationId(supplierMatEng.getId());
            
            // 组织机构
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
            supplierHistoryMapper.insertSelective(historyInfo);
      
            //注册人员信息
            List<SupplierRegPerson> listSupplierRegPersons = supplierMatEng.getListSupplierRegPersons();
            if(listSupplierRegPersons !=null && listSupplierRegPersons.size() >0){
            	for(SupplierRegPerson regPerson : listSupplierRegPersons){
            		 historyInfo = new SupplierHistory();
                     historyInfo.setSupplierId(supplierId);
                     historyInfo.setmodifyType(" mat_eng_page");
                     historyInfo.setCreatedAt(date);
                     historyInfo.setRelationId(regPerson.getId());
                     
                     //注册名称
                     historyInfo.setBeforeField("regType" + REG_PERSON_LIST);
                     historyInfo.setBeforeContent(regPerson.getRegType());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //注册人数
                     historyInfo.setBeforeField("regNumber" + REG_PERSON_LIST);
                     historyInfo.setBeforeContent(regPerson.getRegNumber().toString());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                }
            	
            }
            
            //证书信息
            List<SupplierCertEng> listSupplierCertEngs = supplierMatEng.getListSupplierCertEngs();
            if(listSupplierCertEngs !=null && listSupplierCertEngs.size() >0){
            	for(SupplierCertEng certEng : listSupplierCertEngs){
            		historyInfo = new SupplierHistory();
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setmodifyType(" mat_eng_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(certEng.getId());
            		
            		 // 资质资格类型
                    historyInfo.setBeforeField("certType" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getCertType());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 证书编号
                    historyInfo.setBeforeField("certCode" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getCertCode());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 资质资格最高等级
                    historyInfo.setBeforeField("certMaxLevel" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getCertMaxLevel());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 技术负责人姓名
                    historyInfo.setBeforeField("techName" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getTechName());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 技术负责人职称
                    historyInfo.setBeforeField("techPt" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getTechPt());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 技术负责人职务
                    historyInfo.setBeforeField("techJop" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getTechJop());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 单位负责人姓名
                    historyInfo.setBeforeField("depName" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getDepName());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 单位负责人职称
                    historyInfo.setBeforeField("depPt" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getDepPt());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    //单位负责人职务
                    historyInfo.setBeforeField("depJop" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getDepJop());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 发证机关
                    historyInfo.setBeforeField("licenceAuthorith" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getLicenceAuthorith());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 发证日期	
                    historyInfo.setBeforeField("expStartDate" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(format.format(certEng.getExpStartDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 证书有效期截止日期
                    historyInfo.setBeforeField("expEndDate" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(format.format(certEng.getExpEndDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 证书状态
                    historyInfo.setBeforeField("certStatus" + CERT_ENGS_LIST);
                    historyInfo.setBeforeContent(certEng.getCertStatus().toString());
                    supplierHistoryMapper.insertSelective(historyInfo);
            	}
            }
            
            //资质证书信息
            List<SupplierAptitute> listSupplierAptitutes = supplierMatEng.getListSupplierAptitutes();
            if(listSupplierAptitutes != null && listSupplierAptitutes.size() >0){
            	for(SupplierAptitute aptitute : listSupplierAptitutes){
            		 historyInfo = new SupplierHistory();
                     historyInfo.setSupplierId(supplierId);
                     historyInfo.setmodifyType(" mat_eng_page");
                     historyInfo.setCreatedAt(date);
                     historyInfo.setRelationId(aptitute.getId());
                     
                     //资质资格类型
                     historyInfo.setBeforeField("certType" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getCertType());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //证书编号	
                     historyInfo.setBeforeField("certCode" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getCertCode());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //资质资格序列
                     historyInfo.setBeforeField("aptituteSequence" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getAptituteSequence());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //专业类别
                     historyInfo.setBeforeField("professType" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getProfessType());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //资质资格等级
                     historyInfo.setBeforeField("aptituteLevel" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getAptituteLevel());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //是否主项资质
                     historyInfo.setBeforeField("isMajorFund" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getIsMajorFund().toString());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //批准资质资格内容
                     historyInfo.setBeforeField("aptituteContent" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getAptituteContent());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //首次批准资质资格文号
                     historyInfo.setBeforeField("aptituteCode" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getAptituteCode());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //首次批准资质资格日期
                     historyInfo.setBeforeField("aptituteDate" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(format.format(aptitute.getAptituteDate()));
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //资质资格取得方式
                     historyInfo.setBeforeField("aptituteWay" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getAptituteWay());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //资质资格状态
                     historyInfo.setBeforeField("aptituteStatus" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getAptituteStatus().toString());
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //资质资格状态变更时间
                     historyInfo.setBeforeField("aptituteChangeAt" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(format.format(aptitute.getAptituteChangeAt()));
                     supplierHistoryMapper.insertSelective(historyInfo);
                     
                     //资质资格状态变更原因
                     historyInfo.setBeforeField("aptituteChangeReason" + CERT_APT_LIST);
                     historyInfo.setBeforeContent(aptitute.getAptituteChangeReason());
                     supplierHistoryMapper.insertSelective(historyInfo);
            	}
            }
        }
        
        // 服务--信息
        SupplierMatServe supplierMatSe = supplier.getSupplierMatSe();
        if (supplierMatSe != null) {
            historyInfo = new SupplierHistory();
            historyInfo.setSupplierId(supplierId);
            historyInfo.setmodifyType("mat_serve_page");
            historyInfo.setCreatedAt(date);
            historyInfo.setRelationId(supplierMatSe.getId());
            
            // 组织机构
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
            supplierHistoryMapper.insertSelective(historyInfo);
        
            // 资质证书信息
            List<SupplierCertServe> listSupplierCertSes = supplierMatSe.getListSupplierCertSes();
            if (listSupplierCertSes != null && listSupplierCertSes.size() > 0) {
                for (SupplierCertServe matSe : listSupplierCertSes) {
                    historyInfo = new SupplierHistory();
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setmodifyType("mat_serve_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(matSe.getId());
        
                    // 资质证书名称
                    historyInfo.setBeforeField("name" + CERT_SE_LIST);
                    historyInfo.setBeforeContent(matSe.getName());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 资质等级
                    historyInfo.setBeforeField("levelCert" + CERT_SE_LIST);
                    historyInfo.setBeforeContent(matSe.getLevelCert());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 发证机关
                    historyInfo.setBeforeField("licenceAuthorith" + CERT_SE_LIST);
                    historyInfo.setBeforeContent(matSe.getLicenceAuthorith());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 有效期（起始时间）
                    historyInfo.setBeforeField("expStartDate" + CERT_SE_LIST);
                    historyInfo.setBeforeContent(format.format(matSe.getExpStartDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 有效期（结束时间）
                    historyInfo.setBeforeField("expStartDate" + CERT_SE_LIST);
                    historyInfo.setBeforeContent(format.format(matSe.getExpEndDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    //是否年检
                    historyInfo.setBeforeField("mot" + CERT_SE_LIST);
                    historyInfo.setBeforeContent(matSe.getMot().toString());
                    supplierHistoryMapper.insertSelective(historyInfo);
                }
            }
        }
    }
    
}
