package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.sstps.ContractProductMapper;
import bss.dao.sstps.ContractsMapper;
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
	public List<ContractProduct> select(ContractProduct contractProduct) {
		return contractProductMapper.select(contractProduct);
	}

}
