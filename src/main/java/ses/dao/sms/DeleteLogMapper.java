package ses.dao.sms;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.DeleteLog;


public interface DeleteLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(DeleteLog record);

    int insertSelective(DeleteLog record);

    DeleteLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DeleteLog record);

    int updateByPrimaryKey(DeleteLog record);
    
    DeleteLog queryByTypeId(@Param("typeId")String typeId);
}