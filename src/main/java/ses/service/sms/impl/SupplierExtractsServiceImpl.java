/**
 * 
 */
package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierExtractsMapper;
import ses.model.sms.SupplierExtracts;
import ses.service.sms.SupplierExtractsService;

/**
 * @Description:供应商抽取
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月18日下午2:03:38
 * @since  JDK 1.7
 */
@Service
public class SupplierExtractsServiceImpl implements SupplierExtractsService {
    @Autowired
    SupplierExtractsMapper supplierExtractsMapper;

    /**
     * @Description:插入记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:32:28  
     * @param @param record      
     * @return void
     */
    @Override
    public void insert(SupplierExtracts record) {
        supplierExtractsMapper.insertSelective(record);
    }

    /**
     * @Description:集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:32:28  
     * @param @param record      
     * @return void
     */
    @Override
    public List<SupplierExtracts> listExtractRecord(
        SupplierExtracts expExtractRecord,Integer pageNum) {
        if(pageNum!=0){
            PageHelper.startPage(pageNum, 10);
        }
        return supplierExtractsMapper.list(expExtractRecord);
    }

    /**
     * 
     *〈简述〉修改
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    @Override
    public void update(SupplierExtracts extracts) {
        supplierExtractsMapper.updateByPrimaryKeySelective(extracts);
    }


}
