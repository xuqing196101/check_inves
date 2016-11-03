package ses.dao.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpertCredible;

public interface ExpertCredibleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertCredible record);

    int insertSelective(ExpertCredible record);

    ExpertCredible selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertCredible record);

    int updateByPrimaryKey(ExpertCredible record);
    
    List<ExpertCredible> selectAll(Map<String,Object> map);
}