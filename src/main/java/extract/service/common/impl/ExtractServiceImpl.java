package extract.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.Area;

import extract.dao.common.ExtractMapper;
import extract.service.common.ExtractService;

@Service
public class ExtractServiceImpl implements ExtractService{

	@Autowired
	private ExtractMapper extractMapper;
	
	@Override
	public List<Area> getTree() {
		List<Area> treeForExt = extractMapper.getTreeForExt();
		Area area = new Area();
		area.setId("0");
		area.setIsParent("true");
		area.setName("全国");
		treeForExt.add(area);
		return treeForExt;
	}

	
}
