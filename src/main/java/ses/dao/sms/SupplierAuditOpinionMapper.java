package ses.dao.sms;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierAuditOpinion;

/**
 * <p>SupplierAuditOpinionMapper </p>
 * <p>Description:供应商审核意见 </p>
 * @date 2017-4-1下午5:48:39
 */
public interface SupplierAuditOpinionMapper {
	
	/**
	 * @Title: insertSelective
	 * @date 2017-4-1 下午5:39:10  
	 * @Description:插入数据
	 * @param @param SupplierAuditOpinionMapper      
	 * @return void
	 */
	void insertSelective (SupplierAuditOpinion supplierAuditOpinion );
	
	/**
	 * @Title: selectByPrimaryKey
	 * @date 2017-4-1 下午5:39:20  
	 * @Description:查询数据
	 * @param @param SupplierAuditOpinionMapper      
	 * @return void
	 */
	SupplierAuditOpinion selectByPrimaryKey (SupplierAuditOpinion supplierAuditOpinion );
	
	/**
	 * 
	 * Description:根据供应商ID查询信息
	 * 
	 * @author Easong
	 * @version 2017年7月3日
	 * @param supplierId
	 * @return
	 */
	SupplierAuditOpinion selectByExpertId(@Param("supplierId") String supplierId);
}
