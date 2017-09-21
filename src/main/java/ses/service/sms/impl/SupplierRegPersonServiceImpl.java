package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.sms.SupplierRegPersonMapper;
import ses.model.sms.SupplierRegPerson;
import ses.service.sms.SupplierRegPersonService;

@Service(value = "supplierRegPersonService")
public class SupplierRegPersonServiceImpl implements SupplierRegPersonService {

	@Autowired
	private SupplierRegPersonMapper supplierRegPersonMapper;

	@Override
	public int saveOrUpdateRegPerson(SupplierRegPerson supplierRegPerson) {
//		String id = supplierRegPerson.getId();
//		if (id != null && !"".equals(id)) {
//			supplierRegPersonMapper.updateByPrimaryKeySelective(supplierRegPerson);
//		} else {
			return supplierRegPersonMapper.insertSelective(supplierRegPerson);
//		}
	}

	@Override
	public SupplierRegPerson queryById(String id) {
		SupplierRegPerson person = supplierRegPersonMapper.selectByPrimaryKey(id);
		return person;
	}

	@Override
	public List<SupplierRegPerson> queryByPerson(String personId) {
		return supplierRegPersonMapper.findRegPersonByMatEngId(personId);
	}

	@Override
	public boolean deleteRegPersonByIds(String ids) {
		boolean isSuccess = false;
	    try{
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                int delCount = 0;
                for(int i=0;i<idArray.length;i++){
                    if(StringUtils.isNotBlank(idArray[i])){
                        int key = supplierRegPersonMapper.deleteByPrimaryKey(idArray[i]);
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
