/**
 * 
 */
package extract.service.supplier.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extract.dao.supplier.SupplierExtractConTypeMapper;
import extract.model.supplier.SupplierConType;
import extract.service.supplier.SupplierExtractConTypeService;



/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午7:45:38
 * @since  JDK 1.7
 */
@Service
public class SupplierExtractConTypeServiceImp implements SupplierExtractConTypeService {
    @Autowired
    SupplierExtractConTypeMapper conTypeMapper;
    /**
     * @Description:插入
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午7:26:30  
     * @param       
     * @return void
     */
    @Override
    public void insert( SupplierConType record) {
        conTypeMapper.insertSelective(record);
    }

    /**
     * @Description:删除
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午7:26:30  
     * @param       
     * @return void
     */
    @Override
    public void delete(String id) {
        conTypeMapper.deleteConditionId(id);
    }

    /* (non-Javadoc)
     * @see ses.service.ems.ExtConTypeService#update(ses.model.ems.ExtConType)
     */
    @Override
    public void update(SupplierConType conType) {
        conTypeMapper.updateByPrimaryKeySelective(conType);
    }

    /**
     * @Description:获取一个对象
     *
     * @author Wang Wenshuai
     * @version 2016年10月12日 下午3:33:22  
     * @param @param conType      
     * @return void
     */
    public SupplierConType getExtConType(String id){
        return conTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据供应商类型返回抽取数量
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @Override
    public Integer getSupplierTypeById(String conId,String supplierTypeId) {
        Map<String, String>  map = new HashMap<String, String>();
        map.put("conditionId", conId);
        map.put("supplierTypeId", supplierTypeId);
        return conTypeMapper.getSupplierTypeById(map);
        
    }

    /**
     * 
     *〈简述〉获取总和
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param conId
     * @return
     */
    public  Integer getSum(String conId){
        return conTypeMapper.getSum(conId);
    }
}
