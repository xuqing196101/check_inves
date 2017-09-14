package extract.dao.common;

import java.util.HashMap;
import java.util.List;

import extract.model.common.ExtractUser;

public interface PersonRelMapper {

	void insertRel(HashMap<String, Object> map);

	List<ExtractUser> getPersonByRecord(String recordId);

	void deleteByRecordId(String recordId);

	/**
	 * 按记录id 查询抽取人员
	 * @param id
	 * @return
	 */
	String getlistByRid(String id);

	void deleteByMap(HashMap<String, Object> map);

	
}
