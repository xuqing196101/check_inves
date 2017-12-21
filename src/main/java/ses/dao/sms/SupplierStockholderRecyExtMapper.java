package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierStockholderRecy;

/**
 * 供应商股东信息回收扩展类
 * @author hxg
 * @date 2017-12-19 下午7:38:23
 */
public interface SupplierStockholderRecyExtMapper {
	/**
	 * 根据供应商id查询最新时间的记录
	 * @param supplierId
	 * @return
	 */
	List<SupplierStockholderRecy> selectBySupplierIdAtLast(String supplierId);
}
