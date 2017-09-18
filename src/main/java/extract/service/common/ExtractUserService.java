package extract.service.common;

import java.util.List;
import java.util.Map;

import ses.model.bms.User;

import extract.model.common.ExtractUser;

public interface ExtractUserService {

	List<ExtractUser> getList(ExtractUser user);

	Map<String, String> addPerson(ExtractUser user, User user2);

}
