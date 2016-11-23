/**
 * 
 */
package bss.service.dms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.dms.ArchiveBorrowMapper;
import bss.model.dms.ArchiveBorrow;
import bss.service.dms.ArchiveBorrowServiceI;

/**
 * @Title:ArchiveBorrowServiceImpl
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-26下午5:32:03
 */
@Service("archiveBorrowService")
public class ArchiveBorrowServiceImpl implements ArchiveBorrowServiceI {
	@Autowired
	private ArchiveBorrowMapper archiveBorrowMapper;

	
	@Override
	public int insertSelective(ArchiveBorrow archiveBorrow) {
		return archiveBorrowMapper.insertSelective(archiveBorrow);
	}

	
	@Override
	public int updateByPrimaryKeySelective(ArchiveBorrow archiveBorrow) {
		return archiveBorrowMapper.updateByPrimaryKeySelective(archiveBorrow);
	}


	
	@Override
	public List<ArchiveBorrow> findArchiveById(HashMap<String, Object> map) {
		return archiveBorrowMapper.findArchiveById(map);
	}


	
	@Override
	public List<ArchiveBorrow> findArchiveIdByUserId(String userId) {
		return archiveBorrowMapper.findArchiveIdByUserId(userId);
	}
	
	
}
