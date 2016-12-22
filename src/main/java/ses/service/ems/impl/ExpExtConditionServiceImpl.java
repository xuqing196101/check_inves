/**
 * 
 */
package ses.service.ems.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpExtConditionMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ProjectExtractMapper;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ExtConType;
import ses.model.ems.ProjectExtract;
import ses.service.ems.ExpExtConditionService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:39:57
 * @since  JDK 1.7
 */
@Service
public class ExpExtConditionServiceImpl  implements ExpExtConditionService {

    /** SCCUESS */
    private static final String SUCCESS = "SUCCESS";
    /** ERROR */
    private static final String ERROR = "ERROR";

    @Autowired
    ExpExtConditionMapper conditionMapper;
    @Autowired
    ExpertMapper expertMapper;
    @Autowired
    ProjectExtractMapper extractMapper;

    /**
     * @Description:添加
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:35:49  
     * @param @param condition      
     * @return void
     */
    @Override
    public void insert(ExpExtCondition condition){
        conditionMapper.insertSelective(condition);
    }

    /**
     * @Description:修改
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:36:05  
     * @param @param condition      
     * @return void
     */
    public void update(ExpExtCondition condition){
        ExpExtCondition cond=new ExpExtCondition();
        cond.setProjectId(condition.getProjectId());
        cond.setStatus((short)1);
        List<ExpExtCondition> list = conditionMapper.list(cond);
        if(list != null && list.size() != 0){
            for (ExpExtCondition expExtCondition : list) {
                conditionMapper.deleteInfo(expExtCondition.getId());
            }
           
        }
        conditionMapper.updateByPrimaryKeySelective(condition);
    }

    /**
     * @Description:集合查询
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:36:20  
     * @param @param condition
     * @param @return      
     * @return List<ExpExtCondition>
     */
    public List<ExpExtCondition> list(ExpExtCondition condition,Integer page){
        if(page!=null&&page!=0){
            PageHelper.startPage(page, 10); 
        }
        return conditionMapper.list(condition);
    }

    /**
     * @Description:获取单个
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午3:17:07  
     * @param @param condition
     * @param @return      
     * @return ExpExtCondition
     */
    @Override
    public ExpExtCondition show(String id) {
        return conditionMapper.selectByPrimaryKey(id);
    }


    /**
     * 
     *〈简述〉更具关联包id查询是否有未抽取的条件
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     * @return
     */
    @Override
    public Integer getCount(String packId) {
        return conditionMapper.getCount(packId);
    }

    /**
     * 删除
     * @see ses.service.ems.ExpExtConditionService#delById(java.lang.String)
     */
    @Override
    public Integer delById(String id) {
        return conditionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 本次抽取是否完成
     * @see ses.service.ems.ExpExtConditionService#isFinish()
     */
    @Override
    public String isFinish(ExpExtCondition condition) {
        List<ExpExtCondition> list = conditionMapper.list(condition);
        if (list != null && list.size() !=0 ){
            return SUCCESS;
        }else{
            return ERROR;
        }

    }

    /**
     * 满足条件人数
     * @see ses.service.ems.ExpExtConditionService#selectLikeExpert(ses.model.ems.ExpExtCondition, ses.model.ems.ExtConType)
     */
    @Override
    public Integer selectLikeExpert(ExpExtCondition condition, ExtConType conType) {
        Integer count = 0;
        //查询专家集合
        List<ExtConType> conTypes = new ArrayList<ExtConType>();
        if(condition.getAgeMax() != null && !"".equals(condition.getAgeMax()) && condition.getAgeMax() !=null && !"".equals(condition.getAgeMax())){
            int max=Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()))-Integer.parseInt(condition.getAgeMax());
            int min=Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()))-Integer.parseInt(condition.getAgeMin());
            condition.setAgeMax(max+"");
            condition.setAgeMin(min+"");
        }
        conTypes.add(conType);
        condition.setConTypes(conTypes);
        List<Expert> selectAllExpert = expertMapper.listExtractionExpert(condition);
        //循环吧查询出的专家集合insert到专家记录表和专家关联的表中
        for (Expert expert2 : selectAllExpert) {
            Map<String, String> map=new HashMap<String, String>();
            map.put("expertId", expert2.getId());
            map.put("projectId",condition.getProjectId());
            if(extractMapper.getexpCount(map)==0){
                count++;
            }
        }
        return count;

    }

}
