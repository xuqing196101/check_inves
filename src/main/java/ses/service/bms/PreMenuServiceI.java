package ses.service.bms;

import java.util.List;

import ses.model.bms.PreMenu;



public interface PreMenuServiceI {

	List<PreMenu> find(PreMenu preMenu);

	void save(PreMenu menu);

	PreMenu get(String id);

	void update(PreMenu menu);

}
