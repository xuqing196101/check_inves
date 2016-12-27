package synchro.outer.back.service.supplier.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import synchro.outer.back.service.supplier.OuterSupplierService;
import synchro.service.SynchRecordService;
import synchro.util.DateUtils;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 供应商业务service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class OuterSupplierServiceImpl implements OuterSupplierService{
    
    /**
     * 同步service
     */
    @Autowired
    private SynchRecordService recordService;
    
    /**
     * 用户service
     */
    @Autowired
    private UserServiceI userService;
    
    /**
     * 供应商service
     */
    @Autowired
    private SupplierService supplierService;
    
    /**
     * 供应审核service
     */
    @Autowired
    private SupplierAuditService supplierAuditService;
    
    /**
     * 境外分支 service
     */
    @Autowired
    private SupplierBranchService supplierBranchService;
    
    /**
     * 生产经营地址 service
     */
    @Autowired
    private SupplierAddressService supplierAddressService;
    
    /**
     * 类型关联表
     */
    @Autowired
    private SupplierTypeRelateService supplierTypeRelateService;
    
    
    
    /**
     * 
     * @see synchro.outer.back.service.supplier.OuterSupplierService#backupCreated()
     */
    @Override
    public void backupCreated() {
        getCretedData();
    }

    /**
     *〈简述〉获取新注册的用户信息
     *〈详细描述〉
     * @author myc
     */
    public void getCretedData(){
        List<Supplier> supplierList = supplierService.getCommintSupplierByDate(DateUtils.getYesterDay());
        List<Supplier> list = getSupplierList(supplierList);
        if (list != null && list.size() > 0){
            FileUtils.writeFile(JSON.toJSONString(list));
        }
        recordService.backNewSupplierRecord(new Integer(list.size()).toString());
    }
    
    /**
     * 
     *〈简述〉根据主数据查询关联的数据
     *〈详细描述〉
     * @author myc
     * @param supplierList 主数据
     * @return 组装完成的数据
     */
    private List<Supplier> getSupplierList(List<Supplier> supplierList){
        List <Supplier> list = new ArrayList<>();
        for (Supplier supplier : supplierList){
            supplier.setUser(getUser(supplier.getId()));
            supplier.setListSupplierFinances(getFinance(supplier.getId()));
            supplier.setListSupplierStockholders(getShareholder(supplier.getId()));
            supplier.setBranchList(getBranch(supplier.getId()));
            supplier.setAddressList(getOPeraAddress(supplier.getId()));
            supplier.setListSupplierTypeRelates(getTypeRelate(supplier.getId()));
            supplier.setSupplierMatPro(getMatPro(supplier.getId()));
            list.add(supplier);
        }
        return list;
    }
    
    /**
     * 
     *〈简述〉获取供应商与用户信息
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private User getUser(String supplierId){
        User user = userService.findByTypeId(supplierId);
        return user;
    }
    
    
    /**
     * 
     *〈简述〉供应商近三年财务信息
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return 
     */
    private List<SupplierFinance> getFinance(String supplierId){
        List<SupplierFinance> finaceList = supplierAuditService.supplierFinanceBySupplierId(supplierId);
        return finaceList;
    }
    
    /**
     * 
     *〈简述〉获取股东信息
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierStockholder> getShareholder(String supplierId){
        List<SupplierStockholder> list = supplierAuditService.ShareholderBySupplierId(supplierId);
        return list;
    }
    
    /**
     * 
     *〈简述〉获取境外分支
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierBranch> getBranch(String supplierId){
        List<SupplierBranch> branchList = supplierBranchService.findSupplierBranch(supplierId);
        return branchList;
    }
    
    /**
     * 
     *〈简述〉生产经营地址
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierAddress> getOPeraAddress(String supplierId){
        List<SupplierAddress> list = supplierAddressService.getBySupplierId(supplierId);
        return list;
    }
    
    /**
     * 
     *〈简述〉获取供应商关联表
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierTypeRelate> getTypeRelate(String supplierId){
        List<SupplierTypeRelate> list = supplierTypeRelateService.queryBySupplier(supplierId);
        return list;
    }
    
    /**
     * 
     *〈简述〉获取供应商专业信息表(物资生产型)
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private SupplierMatPro getMatPro(String supplierId){
       return  supplierAuditService.findSupplierMatProBysupplierId(supplierId);
    }
    
}
