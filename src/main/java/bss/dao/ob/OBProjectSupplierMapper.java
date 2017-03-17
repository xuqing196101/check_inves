package bss.dao.ob;

import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBProjectSupplierExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 
* @ClassName: OBProjectSupplierMapper 
* @Description: Mapper
* @author Easong
* @date 2017年3月17日 下午12:47:51 
*
 */
public interface OBProjectSupplierMapper {
    int countByExample(OBProjectSupplierExample example);

    int deleteByExample(OBProjectSupplierExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProjectSupplier record);

    int insertSelective(OBProjectSupplier record);

    List<OBProjectSupplier> selectByExample(OBProjectSupplierExample example);

    OBProjectSupplier selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProjectSupplier record, @Param("example") OBProjectSupplierExample example);

    int updateByExample(@Param("record") OBProjectSupplier record, @Param("example") OBProjectSupplierExample example);

    int updateByPrimaryKeySelective(OBProjectSupplier record);

    int updateByPrimaryKey(OBProjectSupplier record);
    /**
     * 根据竞价id 删除关系
     */
    int deleteByProjectId(String projectID);

	List<OBProjectSupplier> selectSupplierOBprojectList(Map<String, Object> map);
    
}