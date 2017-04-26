package bss.service.sstps.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.AppraisalContractMapper;
import bss.dao.sstps.ContractProductMapper;
import bss.model.sstps.AppraisalContract;
import bss.model.sstps.ContractProduct;
import bss.service.sstps.ContractProductService;

/**
* @Title:ContractProductServiceImpl 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-10上午10:16:16
 */
@Service("contractProductService")
public class ContractProductServiceImpl implements ContractProductService {

	@Autowired
	private ContractProductMapper contractProductMapper;
	
	@Autowired
	private AppraisalContractMapper appraisalContractMapper;
	/**
	 * 根据ID查询
	 */
	@Override
	public ContractProduct selectById(String id) {
		return contractProductMapper.selectByPrimaryKey(id);
	}


	/**
	 * 新增
	 */
	@Override
	public void insert(ContractProduct contractProduct) {
		contractProductMapper.insert(contractProduct);
	}


	/**
	 * 查询
	 */
	@Override
	public List<ContractProduct> select(HashMap<String, Object> map) {
		return contractProductMapper.select(map);
	}


	/**
	 * 更改状态
	 */
	@Override
	public void update(ContractProduct contractProduct) {
		contractProductMapper.update(contractProduct);
	}


	@Override
	public List<ContractProduct> selectList(ContractProduct contractProduct) {
		return contractProductMapper.selectList(contractProduct);
	}

	@Override
	public List<ContractProduct> selectProjectList(ContractProduct contractProduct) {
		return contractProductMapper.selectProjectList(contractProduct);
	}


}
