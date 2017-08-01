package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAfterSaleDepMapper;
import ses.model.sms.SupplierAfterSaleDep;
import ses.service.sms.SupplierAfterSaleDepService;

/**
 * 版权：(C) 版权所有
 * <简述>
 * 供应商售后服务机构对应Service接口实现类
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0
 * @since    2017年2月17日 18:25:10
 * @see
 */
@Service(value = "supplierAfterSaleDepService")
public class SupplierAfterSaleDepServiceImpl implements SupplierAfterSaleDepService {

    /** 售后服务机构Mapper **/
    @Autowired
    private SupplierAfterSaleDepMapper supplierAfterSaleDepMapper;
    
    /**
     * @see ses.service.sms.SupplierAfterSaleDepService#queryById(java.lang.String)
     */
    @Override
    public SupplierAfterSaleDep queryById(String id) {
        return supplierAfterSaleDepMapper.selectByPrimaryKey(id);
    }

    /**
     * @see ses.service.sms.SupplierAfterSaleDepService#saveOrUpdateAfterSaleDep(ses.model.sms.SupplierAfterSaleDep)
     */
    @Override
    public void saveOrUpdateAfterSaleDep(SupplierAfterSaleDep supplierAfterSaleDep) {
        if (supplierAfterSaleDep.getId() == null) {
            supplierAfterSaleDepMapper.insertSelective(supplierAfterSaleDep);
        } else {
            supplierAfterSaleDepMapper.updateByPrimaryKeySelective(supplierAfterSaleDep);
        }
    }

    /**
     * @see ses.service.sms.SupplierAfterSaleDepService#deleteAfterSaleDep(java.lang.String)
     */
    @Override
    public void deleteAfterSaleDep(String afterSaleDepIds) {
        if (afterSaleDepIds != null) {
            for (String id : afterSaleDepIds.split(",")) {
                supplierAfterSaleDepMapper.deleteByPrimaryKey(id);
            }
        }
    }

	@Override
	public List<SupplierAfterSaleDep> findAfterSaleDepBySupplierId(
			String supplierId) {
		return supplierAfterSaleDepMapper.findAfterSaleDepBySupplierId(supplierId);
	}

}
