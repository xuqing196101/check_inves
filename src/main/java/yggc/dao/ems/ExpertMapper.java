package yggc.dao.ems;

import java.util.List;

import yggc.model.ems.Expert;

public interface ExpertMapper {
    int deleteByPrimaryKey(String id);

    int insert(Expert record);

    int insertSelective(Expert record);

    Expert selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Expert record);

    int updateByPrimaryKey(Expert record);
    /**
     * 
      * @Title: selectLoginNameList
      * @author lkzx 
      * @date 2016年9月1日 下午4:50:17  
      * @Description: TODO 根据登录名查询用户
      * @param @return      
      * @return List<String>
     */
    List<Expert> selectLoginNameList(String loginName);
}