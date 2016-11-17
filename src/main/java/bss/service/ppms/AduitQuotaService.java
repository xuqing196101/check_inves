package bss.service.ppms;

import java.util.List;
import java.util.Map;

import bss.model.ppms.AduitQuota;
import bss.model.prms.ext.AuditModelExt;

/**
 * 版权：(C) 版权所有 
 * <简述>投标指标值以及得分业务处理接口
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public interface AduitQuotaService {

    /**
     *〈简述〉查询list
     *〈详细描述〉
     * @author Ye MaoLin
     * @param aq 投标分值对象
     * @return 投标分值对象集合
     */
    List<AduitQuota> find(AduitQuota aq);
    
    /**
     *〈简述〉根据主键id查询投标分值对象
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id主键id
     * @return 投标分值对象
     */
    AduitQuota get(String id);
    
    /**
     *〈简述〉更新数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param aq 投标分值对象
     */
    void update(AduitQuota aq);

    /**
     *〈简述〉保存数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param aq 投标分值对象
     */
    void save(AduitQuota aq);
    
    /**
     *〈简述〉删除数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id 主键id
     */
    void delete(String id);
    /**
     * 
      * @Title: findAllByMap
      * @author ShaoYangYang
      * @date 2016年11月15日 下午7:05:15  
      * @Description: TODO 表连接查询评分需要的数据
      * @param @param map
      * @param @return      
      * @return List<AuditModelExt>
     */
    List<AuditModelExt> findAllByMap(Map<String,Object> map);
}
