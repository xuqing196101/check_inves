package synchro.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.outer.back.service.supplier.OuterSupplierService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Component
public class SynchTask {
    
    @Autowired
    private OuterSupplierService outerSupplier;
    
    
    public void outerSupplierTask(){
        outerSupplier.backupCreated();
    }
}
