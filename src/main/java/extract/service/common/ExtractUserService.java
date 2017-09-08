package extract.service.common;

import java.util.List;

import ses.model.bms.User;

import extract.model.common.ExtractUser;

public interface ExtractUserService {

	List<ExtractUser> getList(ExtractUser user);

	void addPerson(ExtractUser user, User user2);

}
