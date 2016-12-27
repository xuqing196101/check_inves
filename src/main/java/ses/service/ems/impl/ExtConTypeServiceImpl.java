/**
 * 
 */
package ses.service.ems.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExtConTypeMapper;
import ses.model.ems.ExtConType;
import ses.service.ems.ExtConTypeService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午7:45:38
 * @since  JDK 1.7
 */
@Service
public class ExtConTypeServiceImpl implements ExtConTypeService {
    @Autowired
    ExtConTypeMapper conTypeMapper;
	/**
	 * @Description:插入
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月29日 下午7:26:30  
	 * @param       
	 * @return void
	 */
	@Override
	public void insert(ExtConType record) {
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

	/**
     * @Description:修改
     *
     * @author Wang Wenshuai
     * @version 2016年10月12日 下午3:33:22  
     * @param @param conType      
     * @return void
     */
    public void update(ExtConType conType){
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
    public ExtConType getExtConType(String id){
        return conTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 
     * @see ses.service.ems.ExtConTypeService#getExpertTypeById(java.lang.String)
     */
    @Override
    public Integer getExpertTypeById(String conId,String expertTypeId) {
        Map<String, String>  map = new HashMap<String, String>();
        map.put("conditionId", conId);
        map.put("expertTypeId", expertTypeId);
        return conTypeMapper.getExpertTypeById(map);
        
    }
}
