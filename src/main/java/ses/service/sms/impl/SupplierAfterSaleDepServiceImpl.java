package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
    public int saveOrUpdateAfterSaleDep(SupplierAfterSaleDep supplierAfterSaleDep) {
        if (supplierAfterSaleDep.getId() == null) {
            return supplierAfterSaleDepMapper.insertSelective(supplierAfterSaleDep);
        } else {
            return supplierAfterSaleDepMapper.updateByPrimaryKeySelective(supplierAfterSaleDep);
        }
    }

	@Override
	public List<SupplierAfterSaleDep> findAfterSaleDepBySupplierId(
			String supplierId) {
		return supplierAfterSaleDepMapper.findAfterSaleDepBySupplierId(supplierId);
	}

	@Override
	public boolean deleteAfterSaleDepByIds(String ids) {
		boolean isSuccess = false;
	    try{
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                int delCount = 0;
                for(int i=0;i<idArray.length;i++){
                    if(StringUtils.isNotBlank(idArray[i])){
                        int key = supplierAfterSaleDepMapper.deleteByPrimaryKey(idArray[i]);
                        if(key == 1){
                            delCount++;
                        }
                    }
                }
                if(delCount==idArray.length){
                    isSuccess = true;
                }
            }
        }catch (Exception e){
	        e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return isSuccess;
	}

}
