package bss.dao.ob;

import bss.model.ob.OBRule;
import bss.model.ob.OBRuleExample;

import java.util.Date;
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
	  /**
     * 发布更新 发布规则数量
     * @yanghongliang
     * @param id  规则id
     * @return
     */
    int updateCount(@Param("id")String id,@Param("updatedAt")Date updatedAt);
    
    /**
     * 
    * @Title: checkNameUnique 
    * @Description: 校验竞价规则名称是否唯一
    * @author Easong
    * @param @param name
    * @param @return    设定文件 
    * @return Integer    返回类型 
    * @throws
     */
    Integer checkNameUnique(String name);
}