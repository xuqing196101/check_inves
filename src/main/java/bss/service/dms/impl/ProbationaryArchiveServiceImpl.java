/**
 * 
 */
package bss.service.dms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.dms.ProbationaryArchiveMapper;
import bss.model.dms.ProbationaryArchive;
import bss.service.dms.ProbationaryArchiveServiceI;

/**
 * @Title:ProbationaryServiceImpl
 * @Description: 
 * @author ZhaoBo
 * @date 2016-11-14上午9:29:45
 */
@Service("probationaryArchiveService")
public class ProbationaryArchiveServiceImpl implements ProbationaryArchiveServiceI {
	@Autowired
	private ProbationaryArchiveMapper probationaryArchiveMapper;
	
	@Override
	public int insertSelective(ProbationaryArchive probationaryArchive) {
		return probationaryArchiveMapper.insertSelective(probationaryArchive);
	}

	
	@Override
	public int updateByPrimaryKeySelective(ProbationaryArchive probationaryArchive) {
		return probationaryArchiveMapper.updateByPrimaryKeySelective(probationaryArchive);
	}

	
	@Override
	public List<ProbationaryArchive> selectAll(HashMap<String,Object> map) {
		return probationaryArchiveMapper.selectAll(map);
	}

	
	@Override
	public ProbationaryArchive selectById(String id) {
		return probationaryArchiveMapper.selectById(id);
	}

}
