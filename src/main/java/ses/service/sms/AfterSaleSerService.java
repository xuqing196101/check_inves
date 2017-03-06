package ses.service.sms;

import java.util.List;

import ses.model.sms.AfterSaleSer;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierStars;


public interface AfterSaleSerService {
	
	/**
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author 
     * @param id
     * @return SupplierAfterSaleDep对象
     */
    public AfterSaleSer queryById(String id);
    
    /**
     *〈简述〉
     * 添加或修改售后服务信息
     *〈详细描述〉
     * @author 
     * @param AfterSaleSer
     */
    public void saveOrUpdateAfterSaleSer(AfterSaleSer AfterSaleSer);
    
    public List<AfterSaleSer> findAfterSaleSer();
    
    public AfterSaleSer get(String id);
    
    public void updateAfterSaleSer(AfterSaleSer AfterSaleSer);

}
