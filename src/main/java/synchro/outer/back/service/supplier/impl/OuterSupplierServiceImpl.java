package synchro.outer.back.service.supplier.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
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
    
    /** 物资销售 **/
    @Autowired
    private SupplierMatSellService supplierMatSellService;
    
    /** 供应商-工程 **/
    @Autowired
    private SupplierMatEngService supplierMatEngService;
    
    /** 供应商-服务 **/
    @Autowired
    private SupplierMatSeService supplierMatSeService;
    
    /** 供应商Item **/
    @Autowired
    private SupplierItemService supplierItemService;
    
    
    @Override
    public void exportCommitSupplier(String startTime, String endTime, Date synchDate) {
        
    }

    /**
     *〈简述〉获取新注册的用户信息
     *〈详细描述〉
     * @author myc
     */
    public void getExportData(String startTime, String endTime, Date synchDate){
        List<Supplier> supplierList = supplierService.getCommintSupplierByDate(DateUtils.getYesterDay());
        List<Supplier> list = getSupplierList(supplierList);
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getNewSupperBackUpFile(),JSON.toJSONString(list));
        }
        recordService.commitSupplierRecord(new Integer(list.size()).toString(), synchDate );
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
            packageSupplier(supplier);
            list.add(supplier);
        }
        return list;
    }
    
    /**
     * 
     *〈简述〉封装关联对象
     *〈详细描述〉
     * @author myc
     * @param supplier 
     */
    private void packageSupplier(Supplier supplier){
        //帐号
        supplier.setUser(getUser(supplier.getId()));
        //财务信息
        supplier.setListSupplierFinances(getFinance(supplier.getId()));
        //股东信息
        supplier.setListSupplierStockholders(getShareholder(supplier.getId()));
        //分支信息
        supplier.setBranchList(getBranch(supplier.getId()));
        //地址信息
        supplier.setAddressList(getOPeraAddress(supplier.getId()));
        //专业关联信息
        supplier.setListSupplierTypeRelates(getTypeRelate(supplier.getId()));
        //物资生产型
        supplier.setSupplierMatPro(getMatPro(supplier.getId()));
        //物资销售型
        supplier.setSupplierMatSell(getMatSell(supplier.getId()));
        //工程型
        supplier.setSupplierMatEng(getMatEng(supplier.getId()));
        //服务型
        supplier.setSupplierMatSe(getMatServer(supplier.getId()));
        //关联品目信息
        supplier.setListSupplierItems(getSupplierItems(supplier.getId()));
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
    
    /**
     * 
     *〈简述〉获取供应商专业信息表(物资销售型)
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private SupplierMatSell getMatSell(String supplierId){
        return supplierMatSellService.getMatSell(supplierId);
    }
    
    /**
     * 
     *〈简述〉获取供应商专业信息表(工程型)
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private SupplierMatEng getMatEng(String supplierId){
        return supplierMatEngService.getMatEng(supplierId);
    }
    
    /**
     * 
     *〈简述〉获取供应商专业信息表(服务型)
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private SupplierMatServe getMatServer(String supplierId){
        return  supplierMatSeService.getMatserver(supplierId);
    }
    
    /**
     * 
     *〈简述〉获取供应商品目关联表
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierItem> getSupplierItems(String supplierId){
        return supplierItemService.getSupplierId(supplierId);
    }
    
}
