package ses.service.sms;

import ses.model.sms.SupplierAfterSaleDep;

/**
 * 版权：(C) 版权所有
 * <简述>
 * 供应商售后服务机构对应Service接口
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0
 * @since    2017年2月17日 18:25:10
 * @see
 */
public interface SupplierAfterSaleDepService {
    
    /**
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @return SupplierAfterSaleDep对象
     */
    public SupplierAfterSaleDep queryById(String id);
    
    /**
     *〈简述〉
     * 添加或修改售后服务机构信息
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierAfterSaleDep
     */
    public void saveOrUpdateAfterSaleDep(SupplierAfterSaleDep supplierAfterSaleDep);

    /**
     *〈简述〉
     * 批量删除售后服务机构信息
     *〈详细描述〉
     * @author WangHuijie
     * @param afterSaleDepIds
     */
    public void deleteAfterSaleDep(String afterSaleDepIds);
}
