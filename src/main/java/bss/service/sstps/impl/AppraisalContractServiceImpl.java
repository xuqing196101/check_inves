package bss.service.sstps.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.cs.ContractRequiredMapper;
import bss.dao.sstps.AppraisalContractMapper;
import bss.dao.sstps.ContractProductMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.sstps.AppraisalContract;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.Select;
import bss.service.cs.ContractRequiredService;
import bss.service.sstps.AppraisalContractService;
import bss.service.sstps.ContractProductService;

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
	private ContractRequiredMapper contractRequiredMapper;
	
	@Autowired
	private ContractProductMapper contractProductMapper;
	
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

	/**
	 * 统计列表
	 */
	@Override
	public List<AppraisalContract> selectAppraisal(HashMap<String, Object> map,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return appraisalContractMapper.selectAppraisal(map);
	}

	/**
	 * 统计图
	 */
	@Override
	public List<AppraisalContract> selectStatisical(AppraisalContract record) {
		return appraisalContractMapper.selectStatisical(record);
	}
	
	@Override
	public List<Select> selectChose(String purchaseType) {
		return appraisalContractMapper.selectChose(purchaseType);
	}

	@Override
	public void updateAppeal(String id) {
		appraisalContractMapper.updateAppeal(id);
	}

	@Override
	public List<AppraisalContract> selectAppraisalContractByContractId(Map<String, Object> map) {
		return appraisalContractMapper.selectAppraisalConByContractId(map);
	}

	@Override
	public List<AppraisalContract> selectDistributionCheck(
			AppraisalContract singleBond, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));		
		return appraisalContractMapper.selectByObjectCheck(singleBond);
	}


	@Override
	public List<AppraisalContract> selectByOffer(AppraisalContract singleBond,
			Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return appraisalContractMapper.selectByOffer(singleBond);
	}


	@Override
	public List<AppraisalContract> selectDistributionUser(
			AppraisalContract singleBond, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));		
		return appraisalContractMapper.selectByObjectUser(singleBond);
	}

	@Override
	public void insertPurchaseContract(PurchaseContract pur) {
		List<DictionaryData> dList = DictionaryDataUtil.find(5);
		String typeId = null;
		for(DictionaryData d:dList){
			if(d.getName().equals("单一来源")){
				typeId = d.getId();
			}
		}
		if(pur.getPurchaseType().equals(typeId)){
			AppraisalContract appraisalContract = new AppraisalContract();
			appraisalContract.setName(pur.getName());
			appraisalContract.setCode(pur.getCode());
			appraisalContract.setMoney(pur.getMoney());
			appraisalContract.setSupplierName(pur.getSupplierDepName());
			appraisalContract.setCreatedAt(new Date());
			appraisalContract.setUpdatedAt(new Date());
			appraisalContract.setAppraisal(0);
			appraisalContract.setDistribution(0);
			appraisalContract.setPurchaseContract(pur);
			appraisalContract.setPurchaseType(pur.getPurchaseType());
			appraisalContract.setPurchaseDepName(pur.getPurchaseDepName());
			
			insert(appraisalContract);
			updateAppeal(pur.getId());
			
			//审价产品
			ContractProduct contractProduct = new ContractProduct();
			//根据合同编号，获取审价ID
			AppraisalContract app = new AppraisalContract();
			app.setPurchaseContract(pur);
			AppraisalContract appc = selectContractId(app);
			
			//ContractRequired contractRequired = new ContractRequired();
			List<ContractRequired> list = contractRequiredMapper.selectConRequeByContractId(pur.getId());
			for(int i=0;i<list.size();i++){
			//	ContractProduct.setId(app.getId());
				//关联审价编号
				if(list.get(i).getDetailId()==null||"".equals(list.get(i).getDetailId())){
					continue;
				}
				contractProduct.setRequirdeId(list.get(i).getId());
				contractProduct.setAppraisalContract(appc);
				//获取合同产品
				contractProduct.setName(list.get(i).getGoodsName());
				contractProduct.setCreatedAt(new Date());
				contractProduct.setUpdatedAt(new Date());
				contractProduct.setOffer(0);
				contractProduct.setAuditOffer(0);
				contractProductMapper.insert(contractProduct);
			}
		}
	}
}
