package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierAptituteRecy;

/**
 * 资质信息回收扩展类
 * @author hxg
 * @date 2017-12-19 下午7:36:04
 */
public interface SupplierAptituteRecyExtMapper {
	/**
	 * 根据供应商id查询最新时间的记录
	 * @param supplierId
	 * @return
	 */
	List<SupplierAptituteRecy> selectBySupplierIdAtLast(String supplierId);
}
