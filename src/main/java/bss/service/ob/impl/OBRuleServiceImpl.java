package bss.service.ob.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import ses.util.PropertiesUtil;
import bss.dao.ob.OBRuleMapper;
import bss.dao.ob.OBSpecialDateMapper;
import bss.model.ob.OBRule;
import bss.model.ob.OBRuleExample;
import bss.model.ob.OBRuleExample.Criteria;
import bss.model.ob.OBSpecialDate;
import bss.model.ob.OBSpecialDateExample;
import bss.service.ob.OBRuleService;

import com.github.pagehelper.PageHelper;

import common.utils.JdcgResult;

/**
 * 
 * @ClassName: OBRuleServiceImpl
 * @Description: 竞价规则接口的实现类
 * @author Easong
 * @date 2017年3月7日 上午10:41:21
 * 
 */
@Service
public class OBRuleServiceImpl implements OBRuleService {

	/**
	 * 日志记录
	 */
	Logger log = LoggerFactory.getLogger(OBRuleServiceImpl.class);

	@Autowired
	private OBRuleMapper obRuleMapper;
	
	@Autowired
	private OBSpecialDateMapper obSpecialDateMapper;

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
	@Override
	public JdcgResult addRule(OBRule obRule, User user) throws Exception{
		if (obRule == null) {
			return JdcgResult.build(500, "请填写竞价规则相关信息");
		}
		// 校验表单提交数据开始
		// 竞价规则名称
		if (StringUtils.isEmpty(obRule.getName())) {
			return JdcgResult.build(500, "竞价规则名称不能为空");
		}
		// 竞价规则名称唯一校验
		JdcgResult result = checkNameUnique(obRule.getName());
		if(result.getStatus() == 500){
			return JdcgResult.build(500, "竞价规则名称已存在");
		}
		
		// 间隔工作日
		if (obRule.getIntervalWorkday() == null
				|| "".equals(obRule.getIntervalWorkday())) {
			return JdcgResult.build(500, "间隔工作日不能为空");
		}
		// 具体时间点
		if (obRule.getDefiniteTime() == null
				|| "".equals(obRule.getDefiniteTime())) {
			return JdcgResult.build(500, "具体时间点不能为空");
		}
		// 报价时间
		if (obRule.getQuoteTime() == null || "".equals(obRule.getQuoteTime())) {
			return JdcgResult.build(500, "报价时间不能为空");
		}
		if (obRule.getQuoteTimeSecond() == null || "".equals(obRule.getQuoteTimeSecond())) {
			return JdcgResult.build(500, "二次报价时间不能为空");
		}
		// 确认时间(第一轮)
		if (obRule.getConfirmTime() == null
				|| "".equals(obRule.getConfirmTime())) {
			return JdcgResult.build(500, "确认时间(第一轮)不能为空");
		}
		// 确认时间(第二轮)
		if (obRule.getConfirmTimeSecond() == null
				|| "".equals(obRule.getConfirmTimeSecond())) {
			return JdcgResult.build(500, "确认时间(第二轮)不能为空");
		}
		if (obRule.getLeastSupplierNum() == null || "".equals(obRule.getLeastSupplierNum())) {
			return JdcgResult.build(500, "最少供应商数量不能为空");
		}
		
		if (obRule.getPercent() == null || "".equals(obRule.getPercent())) {
			return JdcgResult.build(500, "有效百分比不能为空");
		}
		
		if (obRule.getPercent().toString().length() >=2 && "00".equals(obRule.getPercent().toString().substring(0, 2))) {
			return JdcgResult.build(500, "输入格式有误");
		}
		// 校验表单提交数据结束
		if (user == null) {
			return JdcgResult.build(500, "请先登录再操作");
		}
		// 设置创建人ID
		obRule.setCreaterId(user.getId());
		// 设置创建时间
		obRule.setCreatedAt(new Date());
		// 设置修改时间
		//obRule.setUpdatedAt(new Date());
		// 是否为默认 1:是 0:否
		obRule.setStatus(0);
		// 保存
		obRuleMapper.insert(obRule);
		return JdcgResult.ok("新增成功");
	}

	/**
	 * 
	 * @Title: selectAllRules
	 * @Description: 查询所有竞价规则
	 * @param @return 设定文件
	 * @return List<OBRule> 返回类型
	 * @throws
	 */
	@Override
	public List<OBRule> selectAllOBRules(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		return obRuleMapper.selectAllOBRules(map);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除规则操作
	 * @param @param ids
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@Override
	public JdcgResult delete(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			try {
				OBRule obRule = obRuleMapper.selectByPrimaryKey(ids[i]);
				if(obRule != null && obRule.getStatus() == 1){
					return JdcgResult.ok("不能删除默认的竞价规则");
				}
				obRuleMapper.deleteByPrimaryKey(ids[i]);
			} catch (Exception e) {
				OBRule obRule = obRuleMapper.selectByPrimaryKey(ids[i]);
				log.info("竞价名称为:" + obRule.getName() + "删除失败");
				return JdcgResult.build(500, "竞价名称为:" + obRule.getName()
						+ "删除失败");
			}
		}
		return JdcgResult.ok("删除成功");
	}

	/**
	 * 
	 * @Title: setDefaultRule
	 * @Description: 默认规则设置
	 * @param @param id
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@Override
	public JdcgResult updateDefaultRule(String id) {
		OBRule obRule = obRuleMapper.selectByPrimaryKey(id);
		if (obRule != null && obRule.getStatus() == 1) {
			return JdcgResult.ok("改项已设置为默认规则");
		}
		// 查询已设置默认项，并修改status为0
		OBRuleExample example = new OBRuleExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		List<OBRule> list = obRuleMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			OBRule rule = list.get(0);
			if (rule != null) {
				rule.setStatus(0);
				obRuleMapper.updateByPrimaryKey(rule);
			}
		}
		obRule.setStatus(1);
		obRuleMapper.updateByPrimaryKey(obRule);
		return JdcgResult.ok("设置默认规则成功");
	}


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
	@Override
	public JdcgResult addSpecialdate(OBSpecialDate obSpecialDate,
			HttpServletRequest req, User user) {
		if (obSpecialDate == null) {
			return JdcgResult.build(500, "请填写相关信息");
		}
		// 校验表单提交数据开始
		if (obSpecialDate.getSpecialDate() == null
				|| "".equals(obSpecialDate.getSpecialDate())) {
			return JdcgResult.build(500, "设置日期不能为空");
		}
		if (obSpecialDate.getDateType() == null
				|| "".equals(obSpecialDate.getDateType())) {
			return JdcgResult.build(500, "类型不能为空");
		}
		// 校验表单提交数据结束
		if (user == null) {
			return JdcgResult.build(500, "请先登录再操作");
		}

		// 判断用户输入的设置日期是否已存在
		OBSpecialDateExample example = new OBSpecialDateExample();
		bss.model.ob.OBSpecialDateExample.Criteria criteria = example
				.createCriteria();
		criteria.andSpecialDateEqualTo(obSpecialDate.getSpecialDate());
		List<OBSpecialDate> list = obSpecialDateMapper.selectByExample(example);
		OBSpecialDate existobSpecialDate = null;
		if (list != null && list.size() > 0) {
			existobSpecialDate = list.get(0);
		}
		if (existobSpecialDate != null) {
			return JdcgResult.build(500, "对不起，设置日期已存在");
		}
		// 设置创建人ID
		obSpecialDate.setCreaterId(user.getId());
		// 设置创建人姓名
		obSpecialDate.setCreaterName(user.getLoginName());
		// 设置创建时间
		obSpecialDate.setCreatedAt(new Date());
		// 设置修改时间
		//obSpecialDate.setUpdatedAt(new Date());
		obSpecialDateMapper.insert(obSpecialDate);
		return JdcgResult.ok("添加成功");
	}

	/**
	 * 
	* @Title: selectAllOBSpecialDate 
	* @Description: 查询所有节假日
	* @param @return    设定文件 
	* @return List<OBSpecialDate>    返回类型 
	* @throws
	 */
	@Override
	public List<OBSpecialDate> selectAllOBSpecialDate(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		return obSpecialDateMapper.selectAllOBSpecialDate(map);
	}

	/**
	 * 
	* @Title: deleteSpecialDate 
	* @Description: 删除特殊假期
	* @param @param ids
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@Override
	public JdcgResult deleteSpecialDate(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			try {
				obSpecialDateMapper.deleteByPrimaryKey(ids[i]);
			} catch (Exception e) {
				OBSpecialDate obSpecialDate = obSpecialDateMapper
						.selectByPrimaryKey(ids[i]);
				log.info("创建信息(创建人+创建时间)为:" + obSpecialDate.getCreaterName()
						+ obSpecialDate.getCreatedAt() + "删除失败");
				return JdcgResult.build(500,
						"创建信息(创建人+创建时间)为:" + obSpecialDate.getCreaterName()
								+ obSpecialDate.getCreatedAt() + "删除失败");
			}
		}
		return JdcgResult.ok("删除成功");
	}

	/**
	* @Title: selectByStatus 
	* @Description: 获取默认规则
	* @param @param 
	* @param @return    设定文件 
	* @return OBRule    返回类型 
	 */
	@Override
	public OBRule selectByStatus() {
		// TODO Auto-generated method stub
		return obRuleMapper.selectByStatus();
	}

	/**
	 * 
	 * @Title: editObRule
	 * @Description: 修改竞价规则数据回显
	 * @author Easong
	 * @param @param id
	 * @param @return 设定文件
	 */
	@Override
	public OBRule editObRule(String id) {
		OBRule obRule = obRuleMapper.selectByPrimaryKey(id);
		if (obRule != null) {
			return obRule;
		}
		return null;
	}

	/**
	 * 
	 * @Title: updateobRule
	 * @Description: 修改竞价规则
	 * @author Easong
	 * @param @param obRule
	 * @param @return 设定文件
	 */
	@Override
	public JdcgResult updateobRule(OBRule obRule) {
		if (obRule == null || obRule.getId() == null) {
			return JdcgResult.build(500, "此竞价规则已失效");
		}
		// 竞价规则名称唯一校验
		OBRule obRuleExist = obRuleMapper.checkNameUnique(obRule.getName());
		OBRule primaryKey = obRuleMapper.selectByPrimaryKey(obRule.getId());
		if(obRuleExist == null || primaryKey == null){
			return JdcgResult.build(500, "竞价规则名称已失效");
		}
		if(obRule.getName().equals(obRuleExist.getName()) && !obRule.getName().equals(primaryKey.getName())){
			return JdcgResult.build(500, "竞价规则名称已存在");
		}
		// 设置修改时间
		obRule.setUpdatedAt(new Date());
		obRuleMapper.updateByPrimaryKeySelective(obRule);
		return JdcgResult.ok("修改成功");
	}

	/**
	 * 
	 * @Title: editSpecialdate
	 * @Description: 编辑特殊节假日数据回显
	 * @author Easong
	 * @param @param id
	 * @param @return 设定文件
	 */
	@Override
	public OBSpecialDate editSpecialdate(String id) {
		OBSpecialDate specialDate = obSpecialDateMapper.selectByPrimaryKey(id);
		if (specialDate != null) {
			return specialDate;
		}
		return null;
	}

	/**
	 * 
	 * @Title: updateobSpecialDate
	 * @Description: 修改特殊节假日
	 * @author Easong
	 * @param @param obSpecialDate
	 * @param @return 设定文件
	 */
	@Override
	public JdcgResult updateobSpecialDate(OBSpecialDate obSpecialDate) throws Exception{
		if (obSpecialDate == null || obSpecialDate.getId() == null) {
			return JdcgResult.build(500, "此特殊节假日已失效");
		}
		// 设置修改时间
		obSpecialDate.setUpdatedAt(new Date());
		obSpecialDateMapper.updateByPrimaryKeySelective(obSpecialDate);
		return JdcgResult.ok("修改成功");
	}

	/**
	 * 
	* @Title: checkNameUnique 
	* @Description: 校验竞价规则名称是否唯一
	* @author Easong
	* @param @param name
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@Override
	public JdcgResult checkNameUnique(String name) {
		OBRule obRule = obRuleMapper.checkNameUnique(name);
		if(obRule != null){
			return JdcgResult.build(500, "竞价规则名称已存在");
		}
		return JdcgResult.ok();
	}
	
	 
}
