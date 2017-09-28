package extract.dao.common;

import java.util.ArrayList;
import java.util.List;

import extract.model.common.Supervise;

public interface SuperviseMapper {

	List<Supervise> getList(Supervise user);

	void insertSelectiveAll(ArrayList<Supervise> arrayList);

	List<Supervise> getlistByRid(String recordId);

}