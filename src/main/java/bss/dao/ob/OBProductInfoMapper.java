package bss.dao.ob;

import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProductInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBProductInfoMapper {
    int countByExample(OBProductInfoExample example);

    int deleteByExample(OBProductInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProductInfo record);

    int insertSelective(OBProductInfo record);

    List<OBProductInfo> selectByExample(OBProductInfoExample example);

    OBProductInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProductInfo record, @Param("example") OBProductInfoExample example);

    int updateByExample(@Param("record") OBProductInfo record, @Param("example") OBProductInfoExample example);

    int updateByPrimaryKeySelective(OBProductInfo record);

    int updateByPrimaryKey(OBProductInfo record);
}