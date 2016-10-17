package bss.service.cs.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.cs.PurchaseContractMapper;
import bss.model.cs.PurchaseContract;
import bss.model.ppms.Project;
import bss.service.cs.PurchaseContractService;

@Service("purchaseContractService")
public class PurchaseContractServiceImpl implements PurchaseContractService {
	
	@Autowired
	private PurchaseContractMapper purchaseContractMapper;
	
	@Override
	public int insert(PurchaseContract record) {
		return 0;
	}

	@Override
	public int insertSelective(PurchaseContract record) {
		purchaseContractMapper.insertSelective(record);
		return 0;
	}
	
	@Override
	public List<PurchaseContract> selectAllPurchaseContract() {
		List<PurchaseContract> contractList = purchaseContractMapper.selectAllPurchaseContract();
		return contractList;
	}

	@Override
	public PurchaseContract selectByCode(String code) {
		PurchaseContract purchaseContract = purchaseContractMapper.selectByCode(code);
		return purchaseContract;
	}

	@Override
	public PurchaseContract selectById(String id) {
		return purchaseContractMapper.selectContractByid(id);
	}

	@Override
	public List<PurchaseContract> selectDraftContract(Map<String,Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectDraftContract(map);
	}

	@Override
	public PurchaseContract selectDraftById(String id) {
		return purchaseContractMapper.selectDraftById(id);
	}

	@Override
	public void updateByPrimaryKeySelective(PurchaseContract record) {
		purchaseContractMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void deleteDraftByPrimaryKey(String id) {
		purchaseContractMapper.deleteDraftByPrimaryKey(id);
	}

	@Override
	public List<PurchaseContract> selectFormalContract(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectFormalContract(map);
	}

	@Override
	public PurchaseContract selectFormalById(String id) {
		return purchaseContractMapper.selectFormalById(id);
	}
}
