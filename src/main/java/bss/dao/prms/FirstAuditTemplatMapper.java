package bss.dao.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.FirstAuditTemplat;

public interface FirstAuditTemplatMapper {
    int deleteByPrimaryKey(String id);

    int insert(FirstAuditTemplat record);

    int insertSelective(FirstAuditTemplat record);

    FirstAuditTemplat selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FirstAuditTemplat record);

    int updateByPrimaryKey(FirstAuditTemplat record);
    /**
     * 
      * @Title: selectAllTemplat
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:28:29  
      * @Description: TODO 查询所有公开的和自己私有的模板
      * @param @param userId
      * @param @return      
      * @return List<FirstAuditTemplat>
     */
    List<FirstAuditTemplat> selectAllTemplat(Map<String,Object> map);
    /***
     * 
      * @Title: selectAll
      * @author ShaoYangYang
      * @date 2016年10月12日 上午10:31:05  
      * @Description: TODO 查询列表  可根据名称模糊查询
      * @param @param map
      * @param @return      
      * @return List<FirstAuditTemplat>
     */
    List<FirstAuditTemplat> selectAll(Map<String,Object> map);
}