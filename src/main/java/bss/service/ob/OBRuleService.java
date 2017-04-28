package bss.service.ob;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ses.model.bms.User;
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
	public JdcgResult addRule(OBRule obRule, User user) throws Exception;

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
	public JdcgResult delete(String ids[]) throws Exception;

	/**
	 * 
	 * @Title: setDefaultRule
	 * @Description: 默认规则设置
	 * @param @param id
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	public JdcgResult updateDefaultRule(String id) throws Exception;

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
			HttpServletRequest request, User user) throws Exception;
	
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
	public JdcgResult deleteSpecialDate(String[] ids) throws Exception;
	/**
	* @Title: selectByStatus 
	* @Description: 获取默认规则
	* @param @param 
	* @param @return    设定文件 
	* @return OBRule    返回类型 
	 */
	public OBRule selectByStatus();
	
	
	/**
	 * 
	* @Title: updateObRule 
	* @Description: 编辑竞价规则数据回显
	* @author Easong
	* @param @param id
	* @param @return    设定文件 
	* @return OBRule    返回类型 
	* @throws
	 */
	public OBRule editObRule(String id);
	
	/**
	 * 
	* @Title: updateobRule 
	* @Description: 修改竞价规则
	* @author Easong
	* @param @param obRule
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult updateobRule(OBRule obRule) throws Exception;
	
	/**
	 * 
	* @Title: editSpecialdate 
	* @Description: 编辑特殊节假日数据回显
	* @author Easong
	* @param @param id
	* @param @return    设定文件 
	* @return OBSpecialDate    返回类型 
	* @throws
	 */
	public OBSpecialDate editSpecialdate(String id);

	/**
	 * 
	* @Title: updateobSpecialDate 
	* @Description: 修改特殊节假日
	* @author Easong
	* @param @param obSpecialDate
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult updateobSpecialDate(OBSpecialDate obSpecialDate) throws Exception;
	
	/**
	 * 
	* @Title: checkNameUnique 
	* @Description: 校验规则名称是否唯一
	* @author Easong
	* @param @param name
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	public JdcgResult checkNameUnique(String name);

}
