package bss.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.bms.User;
import common.utils.DateUtils;
import bss.dao.ob.OBProjectMapper;
import bss.dao.ob.OBProjectRuleMapper;
import bss.dao.ob.OBProjectSupplierMapper;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectRule;
import bss.model.ob.OBRule;

/**
 * 
 * @ClassName: BiddingStateUtil
 * @Description: 供应商恢复操作标识判断工具类
 * @author Easong
 * @date 2017年3月17日 下午1:08:01
 * 
 */
public class BiddingStateUtil {

	/**
	 * 
	 * @Title: getQuotoEndTime @Description: 封装报价截止时间 @author
	 *         Easong @param @param obProjectList @param @return 设定文件 @return
	 *         List<OBProject> 返回类型 @throws
	 */
	public static List<OBProject> getQuotoEndTime(List<OBProject> obProjectList, Map<String, Object> map) {
		if (obProjectList != null) {
			for (OBProject obProject : obProjectList) {
				// 发布中(status == 1)状态判断
				if (obProject != null) {
					// 获取当前竞价信息的状态
					// Date quoteEndTime =
					// getQuoteEndTime(obProject,obProjectRuleMapper);
					// 页面回显报价截止时间
					// obProject.setQuoteEndTime(quoteEndTime);

					// 【竞价中】--【已报价待确认结果状态的判断】
					/*
					 * if(obProject.getStatus()==2 &&
					 * "1".equals(obProject.getRemark())){
					 * 
					 * }
					 */
				}

			}
		}
		return obProjectList;
	}

	/**
	 * 
	 * @Title: compareTo @Description: 时间比较器 @author Easong @param @param
	 *         startTime @param @param endTime @param @return 设定文件 @return int
	 *         返回类型 @throws
	 */
	public static int compareTo(Date systemDate, Date endTime) {
		// 创建日历对象，用于时间的比较
		Calendar systemDateC = Calendar.getInstance();
		Calendar biddingTimeC = Calendar.getInstance();
		systemDateC.setTime(systemDate);
		biddingTimeC.setTime(endTime);
		int result = systemDateC.compareTo(biddingTimeC);
		if (result == 0)
			// 时间相等
			return 0;
		else if (result < 0)
			// systemDate < biddingTime
			return 1;
		else
			// systemDate > biddingTime
			return 2;
	}

	/**
	 * 
	 * @Title: updateRemark @Description: 修改remark状态标识符字段 @author
	 *         Easong @param @param mapper @param @param obProject @param @param
	 *         remark 设定文件 @return void 返回类型 @throws
	 */
	public static void updateRemark(OBProjectSupplierMapper mapper, OBProject obProject, User user, String remark) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obProject != null) {
			map.put("obproject_id", obProject.getId());
		}
		if (user != null) {
			map.put("type_id", user.getTypeId());
		}
		map.put("remark", remark);
		mapper.updateByCondition(map);
	}

	/**
	 * 
	 * @Title: getQuoteEndTime @Description: 获取报价结束时间 @author
	 *         Easong @param @param obProject @param @return 设定文件 @return Date
	 *         返回类型 @throws
	 */
	public static Date getQuoteEndTime(OBProject obProject, OBProjectRuleMapper obProjectRuleMapper) {
		// 获取竞价开始时间
		Date startTime = obProject.getStartTime();
		// 获取竞价规则子表规则
		OBProjectRule obProjectRule = obProjectRuleMapper.selectByPrimaryKey(obProject.getId());
		// 获取报价结束时间
		Date quoteEndTime = null;
		// 获取报价时间
		Integer quoteTime = null;
		if (obProjectRule != null) {
			quoteTime = obProjectRule.getQuoteTime();
			if (quoteTime != null) {
				// 报价开始时间到报价结束时间(竞价开始时间加上报价时间)
				quoteEndTime = DateUtils.getAddDate(startTime, quoteTime);
			}
		}
		return quoteEndTime;
	}

	/**
	 * 
	 * @Title: getQuotoEndTimeSecond @Description:获取二次报价结束时间 @author
	 *         Easong @param @param obProject @param @param
	 *         quoteEndTime @param @param obProjectRuleMapper @param @return
	 *         设定文件 @return Date 返回类型 @throws
	 */
	public static Date getQuotoEndTimeSecond(OBProject obProject, Date quoteEndTime,
			OBProjectRuleMapper obProjectRuleMapper) {
		// 获取竞价规则子表规则
		OBProjectRule obProjectRule = obProjectRuleMapper.selectByPrimaryKey(obProject.getId());
		// 获取第二次报价结束时间
		Date quoteEndTimeSecond = null;
		// 获取第二次报价时间
		Integer quoteTimeSecond = null;
		if (obProjectRule != null) {
			quoteTimeSecond = obProjectRule.getQuoteTimeSecond();
			if (quoteTimeSecond != null) {
				// 报价开始时间到报价结束时间(竞价开始时间加上报价时间)
				quoteEndTimeSecond = DateUtils.getAddDate(quoteEndTime, quoteTimeSecond);
			}
		}
		return quoteEndTimeSecond;
	}

}
