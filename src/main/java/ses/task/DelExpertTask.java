package ses.task;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.ems.Expert;
import ses.service.ems.ExpertBlackListService;
import ses.service.ems.ExpertService;

/**
 * 版权：(C) 版权所有 2011-2016
 * <简述>
 * 定时任务:删除无效的专家
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
 * @see
 */
@Component("delExpertTask")
public class DelExpertTask {

    /** 专家service **/
    @Autowired
    private ExpertService expertService;
    
    /** 黑名单专家 Easong **/
    @Autowired
    private ExpertBlackListService expertBlackListService;
    
    /**
     *〈简述〉定时删除无效的专家
     *〈详细描述〉
     * @author WangHuijie
     * @throws ParseException 时间转换异常
     */
    public void delExpert() throws ParseException {
        List<Expert> allExpert = expertService.getAllExpert();
        int daysBetween;
        for (Expert expert : allExpert) {
            // 判断多长时间没有进行操作
            if (expert.getUpdatedAt() != null) {
                daysBetween = expertService.daysBetween(expert.getUpdatedAt());
                if (("0".equals(expert.getIsSubmit()) && daysBetween > 90) || ("1".equals(expert.getIsSubmit()) && "3".equals(expert.getStatus()) && daysBetween > 60)) {
                    expertService.deleteByPrimaryKey(expert.getId());
                }
            }
        }
    }
    
    /**
     * 
    * @Title: updateExpertBlackStatus 
    * @Description: 定时修改专家黑名单处罚日期截止时的处罚状态
    * @author Easong
    * @param     设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void updateExpertBlackStatus(){
    	expertBlackListService.updateExpertBlackStatus();
    }
}