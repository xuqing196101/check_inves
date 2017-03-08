package bss.service.ob;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import bss.model.ob.OBRule;
import bss.model.ob.OBSpecialDate;
import common.utils.JdcgResult;

/**
 * 
 * @ClassName: OBRuleService
 * @Description: 竞价规则接口的定义
 * @author Easong
 * @date 2017年3月7日 上午10:41:00
 * 
 */
public interface OBRuleService {

	/**
	 * @param request
	 * 
	 * @Title: addRule
	 * @Description: 竞价规则的添加
	 * @param @param obRule
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult addRule(OBRule obRule, HttpServletRequest request);

	/**
	 * 
	 * @Title: selectAllRules
	 * @Description: 查询所有竞价规则
	 * @param @return 设定文件
	 * @return List<OBRule> 返回类型
	 * @throws
	 */
	public List<OBRule> selectAllOBRules(Map<String, Object> map);

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除规则操作
	 * @param @param ids
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult delete(String ids[]);

	/**
	 * 
	 * @Title: setDefaultRule
	 * @Description: 默认规则设置
	 * @param @param id
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult setDefaultRule(String id);

	/**
	 * 
	 * @Title: addSpecialdate
	 * @Description: 创建特殊日期
	 * @param @param obSpecialDate
	 * @param @param request
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult addSpecialdate(OBSpecialDate obSpecialDate,
			HttpServletRequest request);
	
	/**
	 * 
	* @Title: selectAllOBSpecialDate 
	* @Description: 查询所有节假日
	* @param @return    设定文件 
	* @return List<OBSpecialDate>    返回类型 
	* @throws
	 */
	public List<OBSpecialDate> selectAllOBSpecialDate(Map<String, Object> map);

	/**
	 * 
	* @Title: deleteSpecialDate 
	* @Description: 删除特殊假期
	* @param @param ids
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult deleteSpecialDate(String[] ids);
}
