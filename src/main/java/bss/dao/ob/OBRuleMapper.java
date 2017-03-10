package bss.dao.ob;

import bss.model.ob.OBRule;
import bss.model.ob.OBRuleExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OBRuleMapper {
    int countByExample(OBRuleExample example);

    int deleteByExample(OBRuleExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBRule record);

    int insertSelective(OBRule record);

    List<OBRule> selectByExample(OBRuleExample example);

    OBRule selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBRule record, @Param("example") OBRuleExample example);

    int updateByExample(@Param("record") OBRule record, @Param("example") OBRuleExample example);

    int updateByPrimaryKeySelective(OBRule record);

    int updateByPrimaryKey(OBRule record);

	List<OBRule> selectAllOBRules(Map<String, Object> map);
	/**
	 * 获取默认 规则数据
	 * @return
	 */
	OBRule selectByStatus();
}