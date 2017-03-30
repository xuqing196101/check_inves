package bss.dao.ob;

import bss.model.ob.OBProjectRule;
/**
 * 
* @ClassName: OBProjectRuleMapper 
* @Description: 竞价规则表子表Mapper
* @author Easong
* @date 2017年3月29日 上午10:13:19 
*
 */
public interface OBProjectRuleMapper {
    int deleteByPrimaryKey(String id);

    int insert(OBProjectRule record);

    int insertSelective(OBProjectRule record);

    OBProjectRule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OBProjectRule record);

    int updateByPrimaryKey(OBProjectRule record);
}