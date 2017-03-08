package bss.dao.ob;

import bss.model.ob.OBProduct;
import bss.model.ob.OBProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBProductMapper {
    int countByExample(OBProductExample example);

    int deleteByExample(OBProductExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProduct record);

    int insertSelective(OBProduct record);

    List<OBProduct> selectByExample(OBProductExample example);

    OBProduct selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProduct record, @Param("example") OBProductExample example);

    int updateByExample(@Param("record") OBProduct record, @Param("example") OBProductExample example);

    int updateByPrimaryKeySelective(OBProduct record);

    int updateByPrimaryKey(OBProduct record);
    
    
}