package extract.service.common;

import java.util.List;

import extract.model.common.ExtractUser;

public interface ExtractUserService {

	List<ExtractUser> getList(ExtractUser user);

	void addPerson(ExtractUser user);

}
