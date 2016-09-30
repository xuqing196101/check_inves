package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSeMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatSeMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.dao.sms.SupplierTypeMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSe;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSe;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierType;
import ses.model.sms.SupplierTypeRelate;
import ses.service.sms.SupplierAuditService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

/**
 * <p>Title:SupplierAuditServliceImpl </p>
 * <p>Description: 供应商审核实现接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:23
 */
@Service
public class SupplierAuditServiceImpl implements SupplierAuditService {
	
	/**
	 * 供应商信息
	 */
	@Autowired
	private SupplierMapper supplierMapper;
	
	/**
	 * 供应商审核记录
	 */
	@Autowired
	private SupplierAuditMapper supplierAuditMapper;
	
	/**
	 * 财务信息
	 */
	@Autowired
	private SupplierFinanceMapper supplierFinanceMapper;
	
	/**
	 * 股东信息
	 */
	@Autowired
	private SupplierStockholderMapper supplierStockholderMapper;
	
	/**
	 * 所有供应商类型
	 */
	@Autowired
	private SupplierTypeMapper supplierTypeMapper;
	
	/**
	 * 物资生产型-资质证书信息
	 */
	@Autowired
	private SupplierCertProMapper supplierCertProMapper;
	
	/**
	 * 物资生产型专业信息
	 */
	@Autowired
	private SupplierMatProMapper supplierMatProMapper;
	
	/**
	 * 物资销售-资质证书信息
	 */
	@Autowired
	private SupplierCertSellMapper supplierCertSellMapper;
	
	/**
	 * 工程-资质证书信息
	 */
	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;
	
	/**
	 * 工程-资质资格信息
	 */
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	
	/**
	 * 工程-组织结构和人员
	 */
	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;
	
	/**
	 * 服务-资质证书信息
	 */
	@Autowired
	private SupplierCertSeMapper supplierCertSeMapper;
	
	/**
	 * 服务-组织结构和人员
	 */
	@Autowired
	private SupplierMatSeMapper supplierMatSeMapper;
	
	/**
	 * 勾选的供应商类型
	 */
	@Autowired
	SupplierTypeRelateMapper supplierTypeRelateMapper;
	/**
	 * @Title: supplierList
	 * @author Xu Qing
	 * @date 2016-9-14 下午2:10:56  
	 * @Description: 供应商列表 ,可条件查询
	 * @param @return      
	 * @return List<Supplier>
	 */
	@Override
	public List<Supplier> supplierList(Supplier supplier,Integer page) {
		if(page!=null){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		}
		return supplierMapper.findSupplier(supplier);
	}

	/**
	 * @Title: supplierById
	 * @author Xu Qing
	 * @date 2016-9-14 下午3:43:26  
	 * @Description: 根据id查询供应商信息 
	 * @param @param id
	 * @param @return      
	 * @return Supplier
	 */
	@Override
	public Supplier supplierById(String id) {
		
		return supplierMapper.getSupplier(id);
	}
	
	/**
	 * @Title: supplierFinanceByid
	 * @author Xu Qing
	 * @date 2016-9-14 下午5:30:21  
	 * @Description: 根据供应商id查询财务信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierFinance>
	 */
	@Override
	public List<SupplierFinance> supplierFinanceBySupplierId(String supplierId) {
		
		return supplierFinanceMapper.findFinanceBySupplierId(supplierId);
	}
	
	/**
	 * @Title: ShareholderById
	 * @author Xu Qing
	 * @date 2016-9-18 上午9:51:00  
	 * @Description: 根据供应商id查询股东信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierStockholder>
	 */
	@Override
	public List<SupplierStockholder> ShareholderBySupplierId(String supplierId) {
		
		return supplierStockholderMapper.findStockholderBySupplierId(supplierId);
	}

	/**
	 * @Title: auditReasons
	 * @author Xu Qing
	 * @date 2016-9-18 下午5:51:55  
	 * @Description: 审核记录
	 * @param @param supplierAudit      
	 * @return void
	 */
	@Override
	public void auditReasons(SupplierAudit supplierAudit) {
		supplierAuditMapper.insertSelective(supplierAudit);
		
	}

	/**
     * @Title: selectByPrimaryKey
     * @author Xu Qing
     * @date 2016-9-20 下午5:12:26  
     * @Description: 根据供应商id查询审核汇总 
     * @param @param id
     * @param @return      
     * @return List<SupplierAudit>
     */
	@Override
	public List<SupplierAudit> selectByPrimaryKey(String supplierId) {
		
		return supplierAuditMapper.selectByPrimaryKey(supplierId);
	}
	
	/**
     * @Title: updateStatus
     * @author Xu Qing
     * @date 2016-9-20 下午7:24:46  
     * @Description: 根据供应商ID更新审核状态 
     * @param @param supplierId      
     * @return void
     */
	@Override
	public void updateStatus(Supplier supplier) {
		supplierMapper.updateStatus(supplier);
		
	}
	
	/**
     * @Title: getCount
     * @author Xu Qing
     * @date 2016-9-21 上午10:14:27  
     * @Description:根据审核状态获取条数
     * @param @param supplier
     * @param @return      
     * @return Integer
     */
	@Override
	public Integer getCount(Supplier supplier) {
		
		return supplierMapper.getCount(supplier);
	}
	
	/**
     * @Title: findSupplierType
     * @author Xu Qing
     * @date 2016-9-23 下午5:44:18  
     * @Description: 查询所有供应商类型 
     * @param @return      
     * @return List<SupplierType>
     */
	@Override
	public List<SupplierType> findSupplierType() {
		
		return supplierTypeMapper.findSupplierType();
	}
	
	/**
     * @Title: findBySupplierId
     * @author Xu Qing
     * @date 2016-9-26 下午6:43:07  
     * @Description: 资质证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertPro>
     */
	@Override
	public List<SupplierCertPro> findBySupplierId(String supplierId) {
		
		return supplierCertProMapper.findBySupplierId(supplierId);
	}
	
	/**
     * @Title: findSupplierMatProBysupplierId
     * @author Xu Qing
     * @date 2016-9-26 下午8:09:19  
     * @Description: 物资生产型专业信息 
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierMatPro>
     */
	@Override
	public SupplierMatPro findSupplierMatProBysupplierId(String supplierId) {
		
		return supplierMatProMapper.getMatProBySupplierId(supplierId);
	}
	
	/**
     * @Title: findCertSellBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午2:11:49  
     * @Description: 物资销售-资质证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertSell>
     */
	@Override
	public List<SupplierCertSell> findCertSellBySupplierId(String supplierId) {

		return supplierCertSellMapper.findCertSellBySupplierId(supplierId);
	}
	
	/**
     * @Title: findCertEngBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午4:11:02  
     * @Description: 工程专业-资质资格证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertEng>
     */
	@Override
	public List<SupplierCertEng> findCertEngBySupplierId(String supplierId) {
		
		return supplierCertEngMapper.findCertEngBySupplierId(supplierId);
	}
	
	/**
     * @Title: findAptituteBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午5:11:45  
     * @Description: 供应商资质资格信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierAptitute>
     */
	@Override
	public List<SupplierAptitute> findAptituteBySupplierId(String supplierId) {
		
		return supplierAptituteMapper.findAptituteBySupplierId(supplierId);
	}
	
	/**
     * @Title: findMatEngBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午7:36:02  
     * @Description: 供应商组织机构和注册人员 
     * @param @param supplierId
     * @param @return      
     * @return SupplierMatEng
     */
	@Override
	public SupplierMatEng findMatEngBySupplierId(String supplierId) {
		
		return supplierMatEngMapper.getMatEngBySupplierId(supplierId);
	}
	
	/**
     * @Title: findCertSeBySupplierSupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午10:55:54  
     * @Description: 服务专业信息-资质证书 
     * @param @return      
     * @return List<SupplierCertSe>
     */
	@Override
	public List<SupplierCertSe> findCertSeBySupplierId(String supplierId) {
		
		return supplierCertSeMapper.findCertSeBySupplierId(supplierId);
	}
	
	/**
     * @Title: findMatSellBySupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午11:32:26  
     * @Description: 供应商组织机构和人员 
     * @param @param supplierId
     * @param @return      
     * @return SupplierMatSell
     */
	@Override
	public SupplierMatSe findMatSeBySupplierId(String supplierId) {
		
		return supplierMatSeMapper.getMatSeBySupplierId(supplierId);
	}

	@Override
	public void updateBySupplierId(SupplierAudit supplierAudit) {
		supplierAuditMapper.updateBySupplierId(supplierAudit);
		
	}

	@Override
	public String findSupplierTypeNameBySupplierId(String supplierId) {
		
		Supplier supplier = supplierMapper.getSupplier(supplierId);
		List<SupplierTypeRelate> listSupplierTypeRelates = supplier.getListSupplierTypeRelates();
		String supplierTypeNames = "";
		for (int i = 0; i < listSupplierTypeRelates.size(); i++) {
			if (i > 0) {
				supplierTypeNames += ",";
			}
			supplierTypeNames += listSupplierTypeRelates.get(i).getSupplierTypeName();
		}
		//return supplierTypeRelateMapper.findSupplierTypeNameBySupplierId(supplierId);
		return supplierTypeNames;
	}




}
