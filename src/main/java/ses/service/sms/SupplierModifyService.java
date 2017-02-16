package ses.service.sms;

import ses.model.sms.SupplierModify;

public interface SupplierModifyService {
    
    /**
     * @Title: insertSelective
     * @author XuQing 
     * @date 2017-2-15 下午4:22:06  
     * @Description:插入审核退回后供应商修改记录
     * @param @param supplierModify      
     * @return void
     */
    void insertModifyRecord (SupplierModify supplierModify);
}
