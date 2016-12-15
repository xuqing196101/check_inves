package ses.dao.oms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.oms.PurchaseStatus;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>采购机构状态记录Mapper
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface PurchaseStatusMapper {
    
    /**
     * 
     *〈简述〉保存
     *〈详细描述〉
     * @author myc
     * @param purchaseStatus {@link PurchaseStatus}
     */
    public void save(PurchaseStatus purchaseStatus);
    
    /**
     * 
     *〈简述〉根据采购机构Id查询对应的状态信息
     *〈详细描述〉
     * @author myc
     * @param purchaseId 采购机构状态list
     * @return  PurchaseStatus集合
     */
    public List<PurchaseStatus> findByPurchaseId(@Param("purchaseId")String purchaseId);
}
