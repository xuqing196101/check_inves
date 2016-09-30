package bss.service.pms;

import java.util.List;

import bss.formbean.FiledNameEnum;
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
	void update(Integer isUpdate,List<String> list,String collectId);
	
	/**
	 * 
	* @Title: getAll
	* @Description: 得到所有的字段属性集合 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return List<FiledNameEnum>     
	* @throws
	 */
	List<FiledNameEnum> getAllFiled();
	/**
	 * 
	* @Title: query
	* @Description: 根据实体查询
	* author: Li Xiaoxiao 
	* @param @param updateFiled
	* @param @return     
	* @return List<UpdateFiled>     
	* @throws
	 */
	List<UpdateFiled> query(String collectId,List<String> list);
	
}
