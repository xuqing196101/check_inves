package bss.service.prms.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.FirstAuditTemitemMapper;
import bss.model.prms.FirstAuditTemitem;
import bss.service.prms.FirstAuditTemitemService;
import ses.util.WfUtil;
@Service("firstAuditTemitemService")
public class FirstAuditTemitemServiceImpl implements FirstAuditTemitemService {
	@Autowired 
	private FirstAuditTemitemMapper mapper;
	/**
	 * 
	  * @Title: deleteById
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:43:40  
	  * @Description: TODO 删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}
	/**
	 * 
	  * @Title: saveAll
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:43:52  
	  * @Description: TODO 保存全部
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	@Override
	public int saveAll(FirstAuditTemitem record) {
		// TODO Auto-generated method stub
		record.setId(WfUtil.createUUID());
		record.setCreatedAt(new Date());
		return mapper.insert(record);
	}
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:44:03  
	  * @Description: TODO 保存不为空的数据
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	@Override
	public int save(FirstAuditTemitem record) {
		// TODO Auto-generated method stub
		record.setId(WfUtil.createUUID());
		record.setCreatedAt(new Date());
		return mapper.insertSelective(record);
	}
	/**
	 * 
	  * @Title: getById
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:44:13  
	  * @Description: TODO 根据id查询
	  * @param @param id
	  * @param @return      
	  * @return FirstAuditTemitem
	 */
	@Override
	public FirstAuditTemitem getById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}
	/**
	 * 
	  * @Title: update
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:44:22  
	  * @Description: TODO 修改不为空的
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	@Override
	public int update(FirstAuditTemitem record) {
		// TODO Auto-generated method stub
		record.setUpdatedAt(new Date());
		return mapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * 
	  * @Title: updateAll
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午2:44:32  
	  * @Description: TODO 修改全部
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	@Override
	public int updateAll(FirstAuditTemitem record) {
		// TODO Auto-generated method stub
		record.setUpdatedAt(new Date());
		return mapper.updateByPrimaryKey(record);
	}
	  /**
     * 
      * @Title: selectByTemplatId
      * @author ShaoYangYang
      * @date 2016年10月12日 下午2:41:35  
      * @Description: TODO 根据模板id查询初审项集合
      * @param @param templatId
      * @param @return      
      * @return List<FirstAuditTemitem>
     */
	@Override
	public List<FirstAuditTemitem> selectByTemplatId(String templatId) {
		// TODO Auto-generated method stub
		return mapper.selectByTemplatId(templatId);
	}

}
