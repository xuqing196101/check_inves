package ses.service.sms;

import common.utils.JdcgResult;
import ses.model.sms.SupplierAuditOpinion;

import java.util.Map;

/**
 * <p>SupplierAuditOpinionService </p>
 * <p>Description:供应商审核意见 </p>
 * @date 2017-4-1下午5:48:39
 */
public interface SupplierAuditOpinionService {
	/**
	 * @Title: insertSelective
	 * @date 2017-4-1 下午5:39:10  
	 * @Description:插入数据
	 * @param @param SupplierAuditOpinionService      
	 * @return void
	 */
	void insertSelective (SupplierAuditOpinion supplierAuditOpinion);
	
	/**
	 * @Title: selectByPrimaryKey
	 * @date 2017-4-1 下午5:39:20  
	 * @Description:查询数据
	 * @param @param SupplierAuditOpinionService      
	 * @return void
	 */
	SupplierAuditOpinion selectByPrimaryKey (SupplierAuditOpinion supplierAuditOpinion );
	
	/**
	 * 
	 * Description:供应商Id查询审核意见
	 * 
	 * @author Easong
	 * @version 2017年7月3日
	 * @param supplierId
	 * @return
	 */
	SupplierAuditOpinion selectByExpertId(String supplierId);

	/**
	 *
	 * Description: 保存审核意见
	 *
	 * @author Easong
	 * @version 2017/7/13
	 * @param [vertifyFlag]
	 * @param [supplierAuditOpinion]
	 * @since JDK1.7
	 */
    JdcgResult insertSelective (SupplierAuditOpinion supplierAuditOpinion, String vertifyFlag);

    /**
     *
     * Description:根据供应商第几次审核查询审核意见
     *
     * @author Easong
     * @version 2017/7/13
     * @param [supplierId]
     * @param [flagTime]
     * @since JDK1.7
     */
    SupplierAuditOpinion selectByExpertIdAndflagTime(String supplierId, Integer flagTime);

    /**
     *
     * Description: 根据供应商第几次审核查询审核意见（Map）
     *
     * @author Easong
     * @version 2017/11/1
     * @param 
     * @since JDK1.7
     */
	SupplierAuditOpinion selectByExpertIdAndflagTime(Map<String, Object> map);
}
