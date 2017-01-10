package ses.service.sms;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.github.pagehelper.PageInfo;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditNot;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierType;
/**
 * <p>Title:SupplierAuditServlice </p>
 * <p>Description: 供应商审核接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:02
 */

public interface SupplierAuditNotService {
    /**
     * @Title: insertSelective
     * @author XuQing 
     * @date 2017-1-10 下午4:19:35  
     * @Description:插入记录
     * @param @param record
     * @param @return      
     * @return int
     */
    int insertSelective(SupplierAuditNot supplierAuditNot);
    
    /** 
     * @Title: selectByPrimaryKey
     * @author XuQing 
     * @date 2017-1-10 下午4:19:52  
     * @Description:查询
     * @param @param record
     * @param @return      
     * @return List<SupplierAudit>
     */
    List<SupplierAuditNot> selectByPrimaryKey(SupplierAuditNot supplierAuditNot);
	
} 
