package extract.dao.common;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import extract.model.common.ExtractUser;

public interface PersonRelMapper {

	int insertRel(HashMap<String, Object> map);

	List<ExtractUser> getPersonByRecord(String recordId);

	void deleteByRecordId(String recordId);

	/**
	 * 按记录id 查询抽取人员
	 * @param id
	 * @return
	 */
	String getlistByRid(String id);

	void deleteByMap(HashMap<String, Object> map);

	/**
	 * 
	 * <简述>复制当前关联关系创建新记录关联关系
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-12-11上午10:34:29
	 * @param rid_new   新的抽取记录Id
	 * @param recordId 被复制的抽取记录ID
	 */
	void copyPersonRelToAgainByRid(@Param("rid")String rid_new, @Param("recordId")String recordId);
}
