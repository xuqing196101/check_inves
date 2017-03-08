package ses.dao.sms;

import java.util.List;

import bss.model.cs.ContractRequired;
import ses.model.sms.AfterSaleSer;
import ses.model.sms.SupplierStars;


public interface AfterSaleSerMapper {
	/**
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author LiChenHao
     * @param id
     * @return AfterSaleSer对象
     */
    public AfterSaleSer selectByPrimaryKey(String id);
    
    public ContractRequired selectConRequByPrimaryKey(String id);
    /**
     *〈简述〉
     * 新增一条记录
     *〈详细描述〉
     * @author LiChenHao
     
     */
    public void insertSelective(AfterSaleSer AfterSaleSer);
    
    /**
     *〈简述〉
     * 根据主键ID修改
     *〈详细描述〉
     * @author LiChenHao
     */
    public void updateByPrimaryKeySelective(AfterSaleSer AfterSaleSer);
    /**
     *〈简述〉
     * 根据明细ID查询
     *〈详细描述〉
     * @author LiChenHao
     
     * @return
     */
    public List < AfterSaleSer > findAfterSaleSerByrequiredId(String requiredId);
    
    int updateAfterSaleSer(AfterSaleSer AfterSaleSer);

}
