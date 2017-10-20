package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierAptitute;

public interface SupplierAptituteService {
	public int saveOrUpdateAptitute(SupplierAptitute supplierAptitute);

	public SupplierAptitute queryById(String id);
	
	List<SupplierAptitute> queryByAptitute(String projectId);
	
	public List<SupplierAptitute> queryByCodeAndType(String certType,String matEngId,String code,String type);
	
	public List<String> getPorType(String typeId, String matEngId, String code);

	public int selectByCertCode(String certCode);

	/**
	 * 批量删除工程资质证书详细信息
	 * @param ids
	 * @return
	 */
	public boolean deleteAptituteByIds(String ids);
}
