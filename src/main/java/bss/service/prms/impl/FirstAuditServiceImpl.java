package bss.service.prms.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.FirstAuditMapper;
import bss.model.prms.FirstAudit;
import bss.service.prms.FirstAuditService;
import ses.util.WfUtil;
@Service("firstAuditService")
public class FirstAuditServiceImpl implements FirstAuditService {
	@Autowired
	private FirstAuditMapper mapper;
	
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午3:32:34  
	  * @Description: TODO 根据主键删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
	@Override
	public int delete(String id) {
		return mapper.deleteByPrimaryKey(id);
	}


	/**
	 * 
	  * @Title: insert
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午3:32:46  
	  * @Description: TODO 添加
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	@Override
	public int addAll(FirstAudit record) {
		record.setId(WfUtil.createUUID());
		record.setCreatedAt(new Date());
		return mapper.insert(record);
	}


	/**
     * 
      * @Title: insertSelective
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:32:59  
      * @Description: TODO 添加不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int add(FirstAudit record) {
		record.setId(WfUtil.createUUID());
		record.setCreatedAt(new Date());
		return mapper.insertSelective(record);
	}


	/**
     * 
      * @Title: selectByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:33:14  
      * @Description: TODO 根据id查询
      * @param @param id
      * @param @return      
      * @return FirstAudit
     */
	@Override
	public FirstAudit get(String id) {
		return mapper.selectByPrimaryKey(id);
	}


	/**
     * 
      * @Title: updateByPrimaryKeySelective
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:33:25  
      * @Description: TODO 更新不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int update(FirstAudit record) {
		record.setUpdatedAt(new Date());
		return mapper.updateByPrimaryKeySelective(record);
	}


	/**
     * 
      * @Title: updateByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:33:37  
      * @Description: TODO 更新全部
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int updateAll(FirstAudit record) {
		// TODO Auto-generated method stub
		record.setUpdatedAt(new Date());
		return mapper.updateByPrimaryKey(record);
	}



	/**
     * 
      * @Title: selectListByProjectId
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:27:58  
      * @Description: TODO 根据项目id 查询初审项集合
      * @param @param projectId
      * @param @return      
      * @return List<FirstAudit>
     */
	@Override
	public List<FirstAudit> getListByProjectId(String projectId) throws Exception {
		List<FirstAudit> list = mapper.selectListByProjectId(projectId);
		return list;
	}
}
