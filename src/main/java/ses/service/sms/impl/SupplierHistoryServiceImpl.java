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
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatServe;
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
                historyInfo.setId(WfUtil.createUUID());
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("basic_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(address.getId());
    
                // 邮编
                historyInfo.setBeforeField("code");
                historyInfo.setBeforeContent(address.getCode());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 省
                historyInfo.setBeforeField("provinceId");
                historyInfo.setBeforeContent(address.getProvinceId());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 市
                historyInfo.setBeforeField("address");
                historyInfo.setBeforeContent(address.getAddress());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 市
                historyInfo.setBeforeField("detailAddress");
                historyInfo.setBeforeContent(address.getDetailAddress());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
        }
        
        // 境外地址信息
        List<SupplierBranch> branchList = supplier.getBranchList();
        if (branchList != null && branchList.size() > 0) {
            for (SupplierBranch branch : branchList) {
                historyInfo = new SupplierHistory();
                historyInfo.setId(WfUtil.createUUID());
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("basic_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(branch.getId());

                // 名称
                historyInfo.setBeforeField("organizationName");
                historyInfo.setBeforeContent(branch.getOrganizationName());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 国家
                historyInfo.setBeforeField("country");
                historyInfo.setBeforeContent(branch.getCountry());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 地址
                historyInfo.setBeforeField("detailAddress");
                historyInfo.setBeforeContent(branch.getDetailAddress());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 生产经营范围
                historyInfo.setBeforeField("businessSope");
                historyInfo.setBeforeContent(branch.getBusinessSope());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
        }
        
        // 近三年财务信息
        List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances();
        if (listSupplierFinances != null && listSupplierFinances.size() > 0) {
            for (SupplierFinance finances : listSupplierFinances) {
                historyInfo = new SupplierHistory();
                historyInfo.setId(WfUtil.createUUID());
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("finance_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(finances.getId());
    
                // 年份
                historyInfo.setBeforeField("year");
                historyInfo.setBeforeContent(finances.getYear());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 名称
                historyInfo.setBeforeField("name");
                historyInfo.setBeforeContent(finances.getName());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 联系电话
                historyInfo.setBeforeField("telephone");
                historyInfo.setBeforeContent(finances.getTelephone());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 姓名
                historyInfo.setBeforeField("auditors");
                historyInfo.setBeforeContent(finances.getAuditors());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 资产总额
                historyInfo.setBeforeField("totalAssets");
                historyInfo.setBeforeContent(finances.getTotalAssets().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 负债总额
                historyInfo.setBeforeField("totalLiabilities");
                historyInfo.setBeforeContent(finances.getTotalLiabilities().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 净资产总额
                historyInfo.setBeforeField("totalNetAssets");
                historyInfo.setBeforeContent(finances.getTotalNetAssets().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 营业收入
                historyInfo.setBeforeField("taking");
                historyInfo.setBeforeContent(finances.getTaking().toString());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
        }
     
        // 股东信息
        List<SupplierStockholder> listSupplierStockholders = supplier.getListSupplierStockholders();
        if (listSupplierStockholders != null && listSupplierStockholders.size() > 0) {
            for (SupplierStockholder holder : listSupplierStockholders) {
                historyInfo = new SupplierHistory();
                historyInfo.setId(WfUtil.createUUID());
                historyInfo.setSupplierId(supplierId);
                historyInfo.setmodifyType("shareholder_page");
                historyInfo.setCreatedAt(date);
                historyInfo.setRelationId(holder.getId());
    
                // 出资人性质
                historyInfo.setBeforeField("nature");
                historyInfo.setBeforeContent(holder.getNature());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 出资人姓名
                historyInfo.setBeforeField("name");
                historyInfo.setBeforeContent(holder.getName());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 出资人社会统一信用代码
                historyInfo.setBeforeField("identity");
                historyInfo.setBeforeContent(holder.getIdentity());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 出资人股份
                historyInfo.setBeforeField("shares");
                historyInfo.setBeforeContent(holder.getShares());
                supplierHistoryMapper.insertSelective(historyInfo);
                
                // 比例
                historyInfo.setBeforeField("proportion");
                historyInfo.setBeforeContent(holder.getProportion());
                supplierHistoryMapper.insertSelective(historyInfo);
            }
        }
        
        // 物资生产--信息
        SupplierMatPro supplierMatPro = supplier.getSupplierMatPro();
        if (supplierMatPro != null) {
            historyInfo = new SupplierHistory();
            historyInfo.setId(WfUtil.createUUID());
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
                    historyInfo.setId(WfUtil.createUUID());
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setmodifyType("mat_pro_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(certPro.getId());
                    
                    // 资质证书名称
                    historyInfo.setBeforeField("name");
                    historyInfo.setBeforeContent(certPro.getName());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 资质等级
                    historyInfo.setBeforeField("levelCert");
                    historyInfo.setBeforeContent(certPro.getLevelCert());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 发证机关
                    historyInfo.setBeforeField("licenceAuthorith");
                    historyInfo.setBeforeContent(certPro.getLicenceAuthorith());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 有效期（起始时间）
                    historyInfo.setBeforeField("expStartDate");
                    historyInfo.setBeforeContent(format.format(certPro.getExpStartDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 有效期（结束时间）
                    historyInfo.setBeforeField("expEndDate");
                    historyInfo.setBeforeContent(format.format(certPro.getExpEndDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 是否年检
                    historyInfo.setBeforeField("mot");
                    historyInfo.setBeforeContent(certPro.getMot().toString());
                    supplierHistoryMapper.insertSelective(historyInfo);
                }
            }
        }
        
        // 物资销售--信息
        
        // 工程--信息
        
        // 服务--信息
        SupplierMatServe supplierMatSe = supplier.getSupplierMatSe();
        if (supplierMatSe != null) {
            historyInfo = new SupplierHistory();
            historyInfo.setId(WfUtil.createUUID());
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
                    historyInfo.setId(WfUtil.createUUID());
                    historyInfo.setSupplierId(supplierId);
                    historyInfo.setmodifyType("mat_serve_page");
                    historyInfo.setCreatedAt(date);
                    historyInfo.setRelationId(matSe.getId());
        
                    // 资质证书名称
                    historyInfo.setBeforeField("name");
                    historyInfo.setBeforeContent(matSe.getName());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 资质等级
                    historyInfo.setBeforeField("levelCert");
                    historyInfo.setBeforeContent(matSe.getLevelCert());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 发证机关
                    historyInfo.setBeforeField("licenceAuthorith");
                    historyInfo.setBeforeContent(matSe.getLicenceAuthorith());
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    // 有效期（起始时间）
                    historyInfo.setBeforeField("expStartDate");
                    historyInfo.setBeforeContent(format.format(matSe.getExpStartDate()));
                    supplierHistoryMapper.insertSelective(historyInfo);
                    
                    //是否年检
                    historyInfo.setBeforeField("mot");
                    historyInfo.setBeforeContent(matSe.getMot().toString());
                    supplierHistoryMapper.insertSelective(historyInfo);
                }
            }
        }
    }
    
}
