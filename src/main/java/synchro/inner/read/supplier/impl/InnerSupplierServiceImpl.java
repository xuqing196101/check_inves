package synchro.inner.read.supplier.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>读取供应商信息service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class InnerSupplierServiceImpl implements InnerSupplierService {
    
    /** 记录service **/
    @Autowired
    private SynchRecordService synchRecordService;
    
    /** 用户service **/
    @Autowired
    private UserServiceI userService;
    
    /** 供应商 service **/
    @Autowired
    private SupplierService supplierSerice;
    
    /**
     * 
     * @see synchro.inner.read.supplier.InnerSupplierService#readNewSupplierInfo(java.io.File)
     */
    @Override
    public void readNewSupplierInfo(final File file) {
       List<Supplier> list = getSupplier(file);
       for (Supplier supplier : list){
           saveUser(supplier.getUser());
           saveSupplier(supplier);
       }
       synchRecordService.importNewSupplierRecord(new Integer(list.size()).toString());
    }
    
    /**
     * 
     *〈简述〉保存用户
     *〈详细描述〉
     * @author myc
     * @param user 用户
     */
    private void saveUser(User user){
        if (user != null){
            userService.saveUser(user);
        }
    }
    
    /**
     * 
     *〈简述〉保存供应商
     *〈详细描述〉
     * @author myc
     * @param supplier 供应商
     */
    private void saveSupplier(Supplier supplier){
        if (supplier != null){
            supplierSerice.saveSupplier(supplier);
        }
    }
    
    /**
     * 
     *〈简述〉获取供应商list
     *〈详细描述〉
     * @author myc
     * @param file 文件
     * @return
     */
    private List<Supplier> getSupplier(final File file){
        List<Supplier> supplierList = FileUtils.getBeans(file, Supplier.class); 
        return supplierList;
    }
    

}
