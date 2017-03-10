package bss.service.ob;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;

/**
 * 
 * @author Ma Mingwei
 * @description 主要负责存储竞价结果信息
 * @method 没注释的是自动工具生成copy过来
 *
 */
public interface OBProjectResultService {
	int countByExample(OBProjectResultExample example);

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
