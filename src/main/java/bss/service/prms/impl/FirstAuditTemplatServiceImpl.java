package bss.service.prms.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import bss.dao.prms.FirstAuditMapper;
import bss.dao.prms.FirstAuditTemitemMapper;
import bss.dao.prms.FirstAuditTemplatMapper;
import bss.model.prms.FirstAudit;
import bss.model.prms.FirstAuditTemitem;
import bss.model.prms.FirstAuditTemplat;
import bss.service.prms.FirstAuditTemplatService;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
@Service("firstAuditTemplatService")
public class FirstAuditTemplatServiceImpl implements FirstAuditTemplatService {
	@Autowired
	private FirstAuditTemplatMapper mapper;//模板管理
	@Autowired
	private FirstAuditTemitemMapper temMapper; //模板下的初审项管理
	@Autowired
	private FirstAuditMapper auditMapper;//初审项管理
	/**
	 * 
	  * @Title: deleteById
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午3:34:35  
	  * @Description: TODO 根据id删除
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
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午3:34:45  
	  * @Description: TODO 新增不为空的数据
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	@Override
	public int save(FirstAuditTemplat record) {
		// TODO Auto-generated method stub
		//record.setIsOpen((short)0);
		//record.setIsUse((short)0);
		record.setCreatedAt(new Date());
		record.setId(WfUtil.createUUID());
		return mapper.insertSelective(record);
	}
	 /**
     * 
      * @Title: saveAll
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:35:09  
      * @Description: TODO 新增所有
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int saveAll(FirstAuditTemplat record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}
	/**
     * 
      * @Title: getById
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:35:24  
      * @Description: TODO 根据id查询
      * @param @param id
      * @param @return      
      * @return FirstAuditTemplat
     */
	@Override
	public FirstAuditTemplat getById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}
	/**
     * 
      * @Title: update
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:35:33  
      * @Description: TODO 修改不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int update(FirstAuditTemplat record) {
		// TODO Auto-generated method stub
		record.setUpdatedAt(new Date());
		return mapper.updateByPrimaryKeySelective(record);
	}
	/**
     * 
      * @Title: updateAll
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:35:45  
      * @Description: TODO 修改全部
      * @param @param record
      * @param @return      
      * @return int
     */
	@Override
	public int updateAll(FirstAuditTemplat record) {
		record.setUpdatedAt(new Date());
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}
	/**
     * 
      * @Title: selectAllTemplat
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:28:29  
      * @Description: TODO 查询所有公开的和自己私有的模板
      * @param @param userId
      * @param @return      
      * @return List<FirstAuditTemplat>
     */
	@Override
	public List<FirstAuditTemplat> selectAllTemplat(Map<String,Object> map,Integer page) {
		// TODO Auto-generated method stub
		 PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<FirstAuditTemplat> list = mapper.selectAllTemplat(map);
		return list;
	}
	/***
     * 
      * @Title: selectAll
      * @author ShaoYangYang
      * @date 2016年10月12日 上午10:31:05  
      * @Description: TODO 查询列表  可根据名称模糊查询
      * @param @param map
      * @param @return      
      * @return List<FirstAuditTemplat>
     */
   public List<FirstAuditTemplat> selectAll(Map<String,Object> map,Integer pageNum){
	   PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		List<FirstAuditTemplat> list = mapper.selectAll(map);
    	return list;
    }
   /**
    * 
     * @Title: relate
     * @author ShaoYangYang
     * @date 2016年10月12日 下午5:34:50  
     * @Description: TODO 关联模板数据
     * @param @param ids      
     * @return void
    */
   public void relate(String ids,String projectId){
		FirstAuditTemplat templat = mapper.selectByPrimaryKey(ids);
		//根据模板id查询所有模板下的初审项
		List<FirstAuditTemitem> list = temMapper.selectByTemplatId(templat.getId());
		FirstAudit audit;
		//复制属性 到初审项定义表中
	   for (FirstAuditTemitem firstAuditTemitem : list) {
		   audit = new FirstAudit();
		   audit.setId(WfUtil.createUUID());
		   audit.setCreater(firstAuditTemitem.getCreater());
		   audit.setName(firstAuditTemitem.getName());
		   audit.setKind(firstAuditTemitem.getKind());
		   audit.setCreatedAt(firstAuditTemitem.getCreatedAt());
		   audit.setProjectId(projectId);
		   
		   auditMapper.insertSelective(audit);
	}
   }
}
