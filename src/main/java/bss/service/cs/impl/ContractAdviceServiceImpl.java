package bss.service.cs.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.util.PropertiesUtil;
import ses.util.WfUtil;

import bss.dao.cs.ContractAdviceMapper;
import bss.dao.cs.PurchaseContractMapper;
import bss.model.cs.ContractAdvice;
import bss.model.cs.PurchaseContract;
import bss.service.cs.ContractAdviceService;

@Service("contractAdviceService")
public class ContractAdviceServiceImpl implements ContractAdviceService {
	
	@Autowired
	private ContractAdviceMapper contractAdviceMapper;
	
	@Autowired
	private PurchaseContractMapper contractMapper;
	
	/**
	 * 待审核
	 */
	private static final Integer AUDIT_NOT = 1;
	
	/**
	 * 审核中
	 */
	private static final Integer AUDITING = 2;

	@Override
	public Boolean saveContractAdvice(String id, String projectId, String userId) {
		ContractAdvice contractAdvice = new ContractAdvice();
		contractAdvice.setId(WfUtil.createUUID());
		contractAdvice.setContractId(id);
		contractAdvice.setCreatedAt(new Date());
		contractAdvice.setIsDeleted(0);
		if (StringUtils.isNotBlank(projectId)) {
			contractAdvice.setProjectId(projectId);
		}
		contractAdvice.setProposer(userId);
		contractAdvice.setStatus(AUDIT_NOT);
		Integer num = contractAdviceMapper.insert(contractAdvice);
		if (num < 1) {
			return false;
		} else {
			PurchaseContract purchaseContract = new PurchaseContract();
			purchaseContract.setId(id);
			purchaseContract.setAuditStatus(AUDITING);
			contractMapper.updateByPrimaryKeySelective(purchaseContract);
			return true;
		}
	}

	@Override
	public Integer selectByContractId(String contractId) {
		
		return contractAdviceMapper.selectByContractId(contractId);
	}

	@Override
	public List<ContractAdvice> list(HashMap<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return contractAdviceMapper.selectByAll(map);
	}

	@Override
	public ContractAdvice selectById(String id) {
		
		return contractAdviceMapper.selectById(id);
	}

	@Override
	public void update(ContractAdvice contractAdvice) {
		
		contractAdviceMapper.update(contractAdvice);
	}

	@Override
	public List<ContractAdvice> find(HashMap<String, Object> map) {
		
		return contractAdviceMapper.findByList(map);
	}

}
