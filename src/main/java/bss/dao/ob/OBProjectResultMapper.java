package bss.dao.ob;

import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OBProjectResultMapper {
    int countByExample(OBProjectResultExample example);
    /**
     *  成交供应商 数量
     * @param map
     * @return
     */
    Integer countByStatus(Map<String,Object> map);
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
    
    /**
     * 
     * Description: 根据竞价信息查询竞价结果信息
     * 
     * @author  zhang shubin
     * @version  2017年3月11日 
     * @param  @param supplierId
     * @param  @return 
     * @return List<OBProjectResult> 
     * @exception
     */
    List<OBProjectResult> selectByProjectId(String supplierId);

    OBProjectResult selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProjectResult record, @Param("example") OBProjectResultExample example);

    int updateByExample(@Param("record") OBProjectResult record, @Param("example") OBProjectResultExample example);

    int updateByPrimaryKeySelective(OBProjectResult record);

    int updateByPrimaryKey(OBProjectResult record);
    /**
     * 获取未确认 的供应商数据
     * @author Yanghongliang
     * @return list
     */
    List<OBProjectResult> selectNotSuppler(String projectID);
    /**
     * 获取是否第二轮
     * 
     */
    List<OBProjectResult> selectSecondRound(String projectID);
}