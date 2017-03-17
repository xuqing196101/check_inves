package bss.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.dao.ob.OBProjectMapper;
import bss.dao.ppms.ProjectMapper;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectExample;
import bss.model.ob.OBProjectExample.Criteria;

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
	 * @Title: judgeState
	 * @Description: 状态判断
	 * @author Easong
	 * @param @param obProject 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static List<OBProject> judgeState(OBProjectMapper mapper, List<OBProject> obProjectList) {
		if(obProjectList != null){
			for (OBProject obProject : obProjectList) {
				// 发布中(status == 1)状态判断
				if (obProject != null) {
					// 获取当前竞价信息的状态
					// 获取当前系统的时间
					Date systemDate = new Date();
					// 获取竞价开始时间
					Date startTime = obProject.getStartTime();
					// 获取竞价结束时间
					Date endTime = obProject.getEndTime();
					
					// 发布中-等待竞价
					if (obProject.getStatus() == 1) {
						// 发布中，等待竞价
						obProject.setRemark("1");
						// 修改状态
						mapper.updateByPrimaryKeySelective(obProject);
					}
					
					// 竞价中
					if(obProject.getStatus() == 2){
						int compareTo = BiddingStateUtil.compareTo(systemDate, endTime);
						// 报价时间中
						if(compareTo == 1){
							// 开始报价状态
							obProject.setRemark("2");
							// 修改状态
							mapper.updateByPrimaryKeySelective(obProject);
						}
					}
					
					// 竞价结束 --未报价
					/*if(obProject.getStatus() == 3){
						obProject.setRemark("5");
						mapper.updateByPrimaryKeySelective(obProject);
					}*/
				}
			}
		}
		return obProjectList;
	}

	/**
	 * 
	* @Title: compareTo 
	* @Description: 时间比较器
	* @author Easong
	* @param @param startTime
	* @param @param endTime
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
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
}
