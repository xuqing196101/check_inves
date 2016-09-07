package yggc.service.oms;

import java.util.HashMap;
import java.util.List;

import yggc.model.oms.Orgnization;

public interface OrgnizationServiceI {
	public List<Orgnization> findOrgnizationList(HashMap<String,Object> map);
}
