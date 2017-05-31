package sums.service.ss;

import java.util.List;

import bss.model.ppms.Packages;

public interface ProjectSupervisionService {
	
	Integer contractStatus(String id);
	
	List<Packages> viewPack(String projectId);

}
