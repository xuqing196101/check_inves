package bss.dao.prms;

import java.util.List;

import bss.model.prms.FirstAuditTemitem;

public interface FirstAuditTemitemMapper {
    int deleteByPrimaryKey(String id);

    int insert(FirstAuditTemitem record);

    int insertSelective(FirstAuditTemitem record);

    FirstAuditTemitem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FirstAuditTemitem record);

    int updateByPrimaryKey(FirstAuditTemitem record);
    /**
     * 
      * @Title: selectByTemplatId
      * @author ShaoYangYang
      * @date 2016年10月12日 下午2:41:35  
      * @Description: TODO 根据模板id查询初审项集合
      * @param @param templatId
      * @param @return      
      * @return List<FirstAuditTemitem>
     */
    List<FirstAuditTemitem> selectByTemplatId(String templatId);
}