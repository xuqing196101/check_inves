package bss.service.pms;

import java.util.List;

import bss.model.pms.UpdateFiled;
/**
 * 
 * @Title: UpdateFiledService
 * @Description: 字段是否修改业务接口 
 * @author Li Xiaoxiao
 * @date  2016年9月21日,下午7:38:04
 *
 */
public interface UpdateFiledService {
	
	void add(UpdateFiled updateFiled);
	/**
	 * 
	* @Title: getAll
	* @Description: 查询所有的字段
	* author: Li Xiaoxiao 
	* @param @return     
	* @return List<UpdateFiled>     
	* @throws
	 */
	List<UpdateFiled> getAll();
	/**
	 * 
	* @Title: update
	* @Description: 修改 
	* author: Li Xiaoxiao 
	* @param @param updateFiled     
	* @return void     
	* @throws
	 */
	void update(Integer isUpdate,List<String> list);
}
