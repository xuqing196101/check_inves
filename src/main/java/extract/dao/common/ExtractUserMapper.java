package extract.dao.common;

import java.util.List;

import extract.model.common.ExtractUser;

public interface ExtractUserMapper {

	List<ExtractUser> getList(ExtractUser user);

	void insertSelectiveAll(List<ExtractUser> list);

	List<ExtractUser> selectById(String id);

	List<ExtractUser> getlistByRid(String recordId);


}
