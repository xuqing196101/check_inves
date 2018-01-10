package synchro.outer.back.service.org;

import java.io.File;

import ses.model.oms.Orgnization;

public interface OrgService {

	
	public void getDep();
	
	public void innerOrg(final File file);
	
	public Orgnization selectByorg(String id);
}
