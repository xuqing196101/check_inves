/**
 * 
 */
package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierConditionMapper;
import ses.dao.sms.SupplierExtRelateMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.ems.ExpExtCondition;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierConType;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import ses.service.sms.SupplierConditionService;
import ses.util.PropUtil;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:39:57
 * @since  JDK 1.7
 */
@Service
public class SupplierConditionServiceImpl  implements SupplierConditionService {
	
    /** SCCUESS */
    private static final String SUCCESS = "SUCCESS";
    /** ERROR */
    private static final String ERROR = "ERROR";
    
	@Autowired
	SupplierConditionMapper conditionMapper;
	
    @Autowired
    SupplierMapper supplierMapper;
    
    @Autowired
    SupplierExtRelateMapper supplierExtRelateMapper;
	
	/**
	 * @Description:添加
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:35:49  
	 * @param @param condition      
	 * @return void
	 */
	@Override
	public void insert(SupplierCondition condition){
		conditionMapper.insertSelective(condition);
	}
	
	/**
	 * @Description:修改
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:36:05  
	 * @param @param condition      
	 * @return void
	 */
	public void update(SupplierCondition condition){
		conditionMapper.updateByPrimaryKeySelective(condition);
	}
	
	/**
	 * @Description:集合查询
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:36:20  
	 * @param @param condition
	 * @param @return      
	 * @return List<ExpExtCondition>
	 */
	public List<SupplierCondition> list(SupplierCondition condition,Integer pageNum){
	    if(pageNum!=0){
	        PageHelper.startPage(pageNum,PropUtil.getIntegerProperty("pageSize"));
	    }
		return conditionMapper.list(condition);
	}

	/**
	 * @Description:获取单个
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午3:17:07  
	 * @param @param condition
	 * @param @return      
	 * @return ExpExtCondition
	 */
	@Override
	public SupplierCondition show(String id) {
		return conditionMapper.selectByPrimaryKey(id);
	}
	
	/**
     * 
     *〈简述〉更具关联包id查询是否有未抽取的条件
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     * @return
     */
    @Override
    public Integer getCount(String packId) {
        return conditionMapper.getCount(packId);
    }

    /**
     * 直接删除查询不出结果的查询条件
     * @return 
     * @see ses.service.sms.SupplierConditionService#delById(java.lang.String)
     */
    @Override
    public Integer delById(String Id) {
        return conditionMapper.deleteByPrimaryKey(Id);
    }

    /**
     * 返回满足条件的供应商
     * @see ses.service.sms.SupplierConditionService#selectLikeSupplier(ses.model.sms.SupplierCondition, ses.model.sms.SupplierConType)
     */
    @Override
    public Integer selectLikeSupplier(SupplierCondition condition, SupplierConType conType) {
        Integer count=0;
        List<SupplierConType> conTypes = new ArrayList<SupplierConType>();
        if(conType.getSupplierTypeId() != null &&  !"".equals(conType.getSupplierTypeId())){
            conType.setArraySupplierTypeId(conType.getSupplierTypeId().split("\\,"));
        }
        conTypes.add(conType);
        condition.setConTypes(conTypes);
        //查询供应商集合
       
        List<Supplier> selectAllExpert = supplierMapper.listExtractionExpert(condition);//getAllSupplier(null);
        //循环吧查询出的专家集合insert到专家记录表和专家关联的表中
        for (Supplier supplier2 : selectAllExpert) {
            Map<String, String> map=new HashMap<String, String>();
            map.put("supplierId", supplier2.getId());
            map.put("projectId",condition.getProjectId());
            if(supplierExtRelateMapper.getSupplierId(map)==0){
                count++;
            }
        }
        return count;
    }


    /**
     * 本次抽取是否完成
     * @see ses.service.ems.ExpExtConditionService#isFinish()
     */
    @Override
    public String isFinish(SupplierCondition condition) {
        List<SupplierCondition> list = conditionMapper.list(condition);
        if (list != null && list.size() !=0 ){
            return SUCCESS;
        }else{
            return ERROR;
        }
     
    }
}
