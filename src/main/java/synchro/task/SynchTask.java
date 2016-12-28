package synchro.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import synchro.outer.back.service.expert.OuterExpertService;
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
    
    /** 供应商 service **/
    @Autowired
    private OuterSupplierService outerSupplier;
    
    /** 专家业务service **/
    @Autowired
    private OuterExpertService outerExpert;
    
    public void outerSupplierTask() {
        outerSupplier.backupCreated();
    }
    
    /**
     *〈简述〉备份新注册的专家
     *〈详细描述〉
     * @author WangHuijie
     */
    public void outerExpertTask() {
        outerExpert.backupCreated();
    }
}
