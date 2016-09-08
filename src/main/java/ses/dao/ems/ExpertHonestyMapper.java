package ses.dao.ems;

import ses.model.ems.ExpertHonesty;

public interface ExpertHonestyMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertHonesty record);

    int insertSelective(ExpertHonesty record);

    ExpertHonesty selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertHonesty record);

    int updateByPrimaryKey(ExpertHonesty record);
}