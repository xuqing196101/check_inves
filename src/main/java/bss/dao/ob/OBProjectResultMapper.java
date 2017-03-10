package bss.dao.ob;

import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBProjectResultMapper {
    int countByExample(OBProjectResultExample example);
    /**
     * 根据产品 id/竞价id获取已经成交的数量
     * @param example
     * @return
     */
    int countByStatus(OBProjectResult example);
    int deleteByExample(OBProjectResultExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProjectResult record);

    int insertSelective(OBProjectResult record);

    List<OBProjectResult> selectByExample(OBProjectResultExample example);
    
    /**
     * @description 根据供应商Id查询结果
     * @param id  供应商id
     * @return
     */
    List<OBProjectResult> selectBySupplierId(String supplierId);

    OBProjectResult selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProjectResult record, @Param("example") OBProjectResultExample example);

    int updateByExample(@Param("record") OBProjectResult record, @Param("example") OBProjectResultExample example);

    int updateByPrimaryKeySelective(OBProjectResult record);

    int updateByPrimaryKey(OBProjectResult record);
}