package bss.dao.ob;

import bss.model.ob.OBSupplier;
import bss.model.ob.OBSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBSupplierMapper {
    int countByExample(OBSupplierExample example);

    int deleteByExample(OBSupplierExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBSupplier record);

    int insertSelective(OBSupplier record);

    List<OBSupplier> selectByExample(OBSupplierExample example);

    OBSupplier selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBSupplier record, @Param("example") OBSupplierExample example);

    int updateByExample(@Param("record") OBSupplier record, @Param("example") OBSupplierExample example);

    int updateByPrimaryKeySelective(OBSupplier record);

    int updateByPrimaryKey(OBSupplier record);
}