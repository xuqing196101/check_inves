package bss.service.sstps;

import java.util.HashMap;
import java.util.List;

import bss.model.sstps.ContractProduct;

/**
* @Title:ContractProductService 
* @Description: 单一来源采购合同产品接口
* @author Shen Zhenfei
* @date 2016-10-10上午10:11:32
 */
public interface ContractProductService {
	
	public ContractProduct selectById(String id);
	
	public void insert(ContractProduct contractProduct);
	
	public List<ContractProduct> select(HashMap<String, Object> map);
	
	public void update(ContractProduct contractProduct);
	
	public List<ContractProduct> selectList(ContractProduct contractProduct);
	
	public List<ContractProduct> selectProjectList(ContractProduct contractProduct);
	
	

}
