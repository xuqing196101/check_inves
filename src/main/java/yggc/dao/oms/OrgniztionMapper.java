package yggc.dao.oms;

import yggc.model.oms.Orgniztion;

public interface OrgniztionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Orgniztion record);

    int insertSelective(Orgniztion record);

    Orgniztion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Orgniztion record);

    int updateByPrimaryKey(Orgniztion record);
}