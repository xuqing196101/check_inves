package extract.service.common;

import java.util.List;

import extract.model.common.Supervise;

public interface SuperviseService {

	List<Supervise> getList(Supervise suser);

	void addPerson(Supervise user);

}
