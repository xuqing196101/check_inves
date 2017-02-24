package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.theSubject;

public interface theSubjectMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);


    /**
     *
     * @param record
     */
    int insertSelective(theSubject record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    theSubject selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(theSubject record);
    
    /**
     * 集合
     */
    List<theSubject> list(theSubject subject);
    /**
     * 
     */
    List<theSubject> selectById(HashMap<String,Object> map);
    
    /**
     *〈简述〉批量插入
     */
     void insertList(List<theSubject> list);

}