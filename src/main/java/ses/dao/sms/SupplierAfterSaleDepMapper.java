package ses.dao.sms;

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
}
