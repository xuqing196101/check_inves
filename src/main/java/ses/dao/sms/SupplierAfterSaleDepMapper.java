package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierAfterSaleDep;

/**
 * 版权：(C) 版权所有
 * <简述>
 * 供应商售后服务机构对应Mapper接口
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0
 * @since    2017年2月17日 18:25:10
 * @see
 */
public interface SupplierAfterSaleDepMapper {

    /**
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @return SupplierAfterSaleDep对象
     */
    public SupplierAfterSaleDep selectByPrimaryKey(String id);
    
    /**
     *〈简述〉
     * 根据主键ID删除
     *〈详细描述〉
     * @author WangHuijie
     */
    public void deleteByPrimaryKey(String id);
    
    /**
     *〈简述〉
     * 新增一条记录
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierAfterSaleDep
     */
    public void insertSelective(SupplierAfterSaleDep supplierAfterSaleDep);
    
    /**
     *〈简述〉
     * 根据主键ID修改
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierAfterSaleDep
     */
    public void updateByPrimaryKeySelective(SupplierAfterSaleDep supplierAfterSaleDep);
    
    /**
     *〈简述〉
     * 根据供应商ID查询
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierId
     * @return
     */
    public List < SupplierAfterSaleDep > findAfterSaleDepBySupplierId(String supplierId);
}
