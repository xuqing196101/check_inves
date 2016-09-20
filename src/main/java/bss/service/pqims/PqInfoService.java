/**
 * 
 */
package bss.service.pqims;

import java.util.List;

import bss.model.pqims.PqInfo;

/**
 * @Title:PqInfoService 
 * @Description: 质检信息管理 业务接口
 * @author Liyi
 * @date 2016-9-18下午5:08:47
 *
 */
public interface PqInfoService {
	
	/**
	 * 1.获取所有模板对象
	 */
	List<PqInfo> getAll(Integer pageNum);
	
	/**
	 * 2.添加模板
	 */
	public void add(PqInfo pqInfo);
	
	/**
	 * 3.更新模板
	 */
	public void update(PqInfo pqInfo);
	
	/**
	 * 4.根据主键查询模板
	 */
	PqInfo get(String id);
	
	/**
	 * 5.根据主键删除模板
	 */
	public void delete(String id);
	
	/**
	 * 6.查询模板条数
	 */
	Integer queryByConut();
	
}
