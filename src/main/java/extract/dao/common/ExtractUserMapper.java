package extract.dao.common;

import java.util.List;

import extract.model.common.ExtractUser;

public interface ExtractUserMapper {

	List<ExtractUser> getList(ExtractUser user);

}
