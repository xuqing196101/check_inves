package extract.service.common;

import java.util.HashMap;
import java.util.List;

import extract.model.common.Supervise;

public interface SuperviseService {

	List<Supervise> getList(Supervise suser);

	HashMap<String, String> addPerson(Supervise user);

}
