package bss.service.ob.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ob.OBResultSubtabulationMapper;
import bss.model.ob.OBResultSubtabulation;
import bss.service.ob.OBResultSubtabulationService;

@Service("obResultSubtabulationService")
public class OBResultSubtabulationServiceImpl implements
		OBResultSubtabulationService {

	@Autowired
	private OBResultSubtabulationMapper obResultSubtabulationMapper;
	
	@Override
	public List<OBResultSubtabulation> selectByProjectId(String projectId) {
		return obResultSubtabulationMapper.selectByProjectId(projectId);
	}

	@Override
	public List<OBResultSubtabulation> selectByProjectIdAndSupplierId(
			String projectId, String supplierId) {
		return obResultSubtabulationMapper.selectByProjectIdAndSupplierId(projectId, supplierId);
	}

}
