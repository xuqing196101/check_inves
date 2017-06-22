package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ses.dao.sms.SupplierAddressMapper;
import ses.model.sms.SupplierAddress;
import ses.service.sms.SupplierAddressService;
import ses.util.WfUtil;

@Service(value = "SupplierAddressService")
public class SupplierAddressServiceImpl implements SupplierAddressService {

	@Autowired
	private SupplierAddressMapper supplierAddressMapper;
	
	@Override
	public void addList(List<SupplierAddress> list,String supplierId) {
		if(null != list){
			for(SupplierAddress addr:list){
				addr.setSupplierId(supplierId);
			     if (addr.getId() != null) {// 对比id进行添加和修改的操作
			    	 SupplierAddress sa =  supplierAddressMapper.selectByPrimaryKey(addr.getId());
			    	 if(sa == null){
			    		 supplierAddressMapper.insertSelective(addr);
			    	 }else{
			    		 supplierAddressMapper.updateByPrimaryKeySelective(addr);
			    	 }
			     } else {
			         //addr.setId(WfUtil.createUUID());
	                 //supplierAddressMapper.insertSelective(addr);
			     }
			}
		}
		/*supplierAddressMapper.deleteBySupplierId(supplierId);
		 for(SupplierAddress addr:list){
		     if (addr.getId() != null) {
		         addr.setSupplierId(supplierId);
		         supplierAddressMapper.insertSelective(addr); 
		     } else {
		         addr.setId(WfUtil.createUUID());
                 addr.setSupplierId(supplierId);
                 supplierAddressMapper.insertSelective(addr); 
		     }
		 }*/
	}

	@Override
	public List<SupplierAddress> getBySupplierId(String sid) {
		return supplierAddressMapper.getBySupplierId(sid);
	}

	/**
	 * @Title: queryBySupplierId
	 * @author XuQing 
	 * @date 2017-1-3 下午6:19:52  
	 * @Description:联表查询供应商地址信息
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierAddress>
	 */
	@Override
	public List<SupplierAddress> queryBySupplierId(String supplierId) {
		
		return supplierAddressMapper.queryBySupplierId(supplierId);
	}
	
	/**
	 * @see ses.service.sms.SupplierAddressService#delAddressByPrimaryId(java.lang.String)
	 */
	@Override
    public int delAddressByPrimaryId(String id) {
	    return supplierAddressMapper.deleteByPrimaryKey(id);
	}

    @Override
    @Transactional
    public boolean deleteAddressByIds(String ids) {
	    boolean isSuccess = false;
	    try{
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                int delCount = 0;
                for(int i=0;i<idArray.length;i++){
                    if(StringUtils.isNotBlank(idArray[i])){
                        int key = supplierAddressMapper.deleteByPrimaryKey(idArray[i]);
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

    
    /**
     * @Title: selectById
     * @author XuQing 
     * @date 2017-5-5 下午4:17:22  
     * @Description:根据id查询
     * @param @return      
     * @return List<SupplierAddress>
     */
	@Override
	public SupplierAddress selectById(String id) {
		
		return supplierAddressMapper.selectById(id);
	}
}
