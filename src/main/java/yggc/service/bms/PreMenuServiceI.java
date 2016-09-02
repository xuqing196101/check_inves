package yggc.service.bms;

import java.util.List;

import yggc.model.bms.PreMenu;


public interface PreMenuServiceI {

	List<PreMenu> getAll(PreMenu preMenu);

	void save(PreMenu menu);

	PreMenu get(String id);

}
