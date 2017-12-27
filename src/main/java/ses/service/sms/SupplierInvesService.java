package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;

/**
 * 供应商实地考察服务接口
 * @author hxg
 * @date 2017-12-26 下午4:19:31
 */
public interface SupplierInvesService {

	/**
	 * 获取供应商列表
	 * @return
	 */
	List<Supplier> getSupplierList(Supplier supplier, Integer pageNum);

}
