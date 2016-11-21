/**
 * 
 */
package bss.service.dms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.dms.PurchaseArchiveMapper;
import bss.model.dms.PurchaseArchive;
import bss.service.dms.PurchaseArchiveServiceI;

/**
 * @Title:PurchaseArchiveImpl
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-19上午8:56:31
 */
@Service("purchaseArchiveService")
public class PurchaseArchiveImpl implements PurchaseArchiveServiceI {
	@Autowired
	private PurchaseArchiveMapper purchaseArchiveMapper;
	
	@Override
	public PurchaseArchive selectArchiveById(String id) {
		return purchaseArchiveMapper.selectArchiveById(id);
	}

	
	@Override
	public int insertSelective(PurchaseArchive purchaseArchive) {
		return purchaseArchiveMapper.insertSelective(purchaseArchive);
	}

	
	@Override
	public int updateByPrimaryKeySelective(PurchaseArchive purchaseArchive) {
		return purchaseArchiveMapper.updateByPrimaryKeySelective(purchaseArchive);
	}


	@Override
	public List<PurchaseArchive> selectArchiveList(HashMap<String, Object> map) {
		return purchaseArchiveMapper.selectArchiveList(map);
	}


	@Override
	public List<PurchaseArchive> findAppraisalContract(HashMap<String,Object> map) {
		return purchaseArchiveMapper.findAppraisalContract(map);
	}


	
	@Override
	public List<PurchaseArchive> findCompetitiveWayContract(HashMap<String,Object> map) {
		return purchaseArchiveMapper.findCompetitiveWayContract(map);
	}


	
	@Override
	public List<PurchaseArchive> findContract(HashMap<String, Object> map) {
		return purchaseArchiveMapper.findContract(map);
	}


	
	@Override
	public List<PurchaseArchive> findArchiveByName(String name) {
		return purchaseArchiveMapper.findArchiveByName(name);
	}


	
	@Override
	public List<PurchaseArchive> findArchiveByUserId(HashMap<String, Object> map) {
		return purchaseArchiveMapper.findArchiveByUserId(map);
	}

}
