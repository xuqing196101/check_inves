package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.sstps.AppraisalContractMapper;
import bss.dao.sstps.ContractsMapper;
import bss.model.sstps.AppraisalContract;
import bss.model.sstps.Contracts;
import bss.service.sstps.AppraisalContractService;

/**
* @Title:AppraisalContractServiceImpl
* @Description: 单一来源采购合同审价实现类
* @author Shen Zhenfei
* @date 2016-9-19上午9:23:36
 */
@Service("appraisalContractService")
public class AppraisalContractServiceImpl implements AppraisalContractService {
	
	@Autowired
	private AppraisalContractMapper appraisalContractMapper;
	
	@Autowired
	private ContractsMapper contractsMapper;

	/**
	 * 新增审价合同
	 */
	@Override
	public void insert(AppraisalContract singleBond) {
		appraisalContractMapper.insert(singleBond);
	}

	/**
	 * 查询审价合同列表
	 */
	@Override
	public List<AppraisalContract> select(AppraisalContract singleBond, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return appraisalContractMapper.select(singleBond);
	}
	
	/**
	 * 修改
	 */
	@Override
	public void update(AppraisalContract singleBond) {
		appraisalContractMapper.update(singleBond);
	}
	
	
	/**
	 * 合同列表
	 */
	@Override
	public List<Contracts> selectContract(Contracts contract, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return contractsMapper.select(contract);
	}

	/**
	 * 合同明细
	 */
	@Override
	public AppraisalContract selectContractInfo(String id) {
		return appraisalContractMapper.selectById(id);
	}

	/**
	 * 查询分配
	 */
	@Override
	public List<AppraisalContract> selectDistribution(AppraisalContract singleBond,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return appraisalContractMapper.selectByObject(singleBond);
	}

	/**
	 * 模糊查询列表
	 */
	@Override
	public List<AppraisalContract> selectByObjectLike(AppraisalContract singleBond,
			Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return appraisalContractMapper.selectByObjectLike(singleBond);
	}

	/**
	 * 根据合同查询id
	 */
	@Override
	public AppraisalContract selectContractId(AppraisalContract record) {
		return appraisalContractMapper.selectContractId(record);
	}

	
}
