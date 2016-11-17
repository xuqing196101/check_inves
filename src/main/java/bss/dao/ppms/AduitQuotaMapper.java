package bss.dao.ppms;

import java.util.List;
import java.util.Map;

import bss.model.ppms.AduitQuota;
import bss.model.prms.ext.AuditModelExt;

/**
 * 版权：(C) 版权所有 
 * <简述>投标指标值以及得分实体数据持久化接口
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public interface AduitQuotaMapper {
    
    /**
     *〈简述〉删除
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id 主键
     */
    void delete(String id);

    /**
     *〈简述〉插入数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record 对象数据
     */
    void insert(AduitQuota record);

    /**
     *〈简述〉根据主键id获取数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id 主键
     * @return 实体对象
     */
    AduitQuota get(String id);

    /**
     *〈简述〉根据id更新数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record 对象数据
     */
    void update(AduitQuota record);
    
    /**
     *〈简述〉根据条件查询
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record 对象数据
     * @return 对象集合
     */
    List<AduitQuota> findList(AduitQuota record);
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