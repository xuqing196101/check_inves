package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCertEng;

public interface SupplierCertEngService {
	public void saveOrUpdateCertEng(SupplierCertEng supplierCertEng);

	public void deleteCertEng(String certEngIds);
	
	public SupplierCertEng queryById(String id);
	
	
	List<SupplierCertEng> queryByEngId(String endId);
	
	/**
     *〈简述〉
     * 根据证书编号和供应商ID查询
     *〈详细描述〉
     * @author WangHuijie
     * @param code
     * @param supplierId
     * @return
     */
    List<SupplierCertEng> selectCertEngByCode(String code, String supplierId);
}
