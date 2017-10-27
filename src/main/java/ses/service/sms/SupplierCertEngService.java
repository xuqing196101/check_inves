package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCertEng;

public interface SupplierCertEngService {
	public int saveOrUpdateCertEng(SupplierCertEng supplierCertEng);

	public SupplierCertEng queryById(String id);
	
	List<SupplierCertEng> queryByEngId(String engId);
	
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
    
    /**
     *〈简述〉
     * 校验证书编号是否重复
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierCertEng
     * @return
     */
    boolean validateCertCode(SupplierCertEng supplierCertEng);
    
    /**
     *〈简述〉根据类型和证书编号查询等级
     *〈详细描述〉
     * @author WangHuijie
     * @param typeId
     * @param certCode
     * @return
     */
    String getLevel(String typeId, String certCode, String supplierId);
    /**
	 * 
	 * Description:根据供应商id 查询目录数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-22
	 * @param supplierId
	 * @return
	 */
	public List<SupplierCertEng> findCertEngBySupplierId(String supplierId);

	/**
	 * 批量删除工程资质证书
	 * @param ids
	 * @return
	 */
	public boolean deleteCertEngByIds(String ids);
}
