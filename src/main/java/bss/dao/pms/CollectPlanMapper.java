package bss.dao.pms;

import java.util.List;

import bss.model.pms.CollectPlan;
/**
 * 
 * @Title: CollectPlanMapper
 * @Description: 是否允许字段修改接口 
 * @author Li Xiaoxiao
 * @date  2016年9月21日,下午5:02:33
 *
 */
public interface CollectPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectPlan record);

    int insertSelective(CollectPlan record);

    CollectPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectPlan record);

    int updateByPrimaryKey(CollectPlan record);
    /**
     * 
    * @Title: query
    * @Description: 分页查询 
    * author: Li Xiaoxiao 
    * @param @param collectPlan
    * @param @return     
    * @return List<CollectPlan>     
    * @throws
     */
    List<CollectPlan> query(CollectPlan collectPlan);
}