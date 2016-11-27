package bss.service.sstps.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.sstps.AppraisalContractMapper;
import bss.model.sstps.AppraisalContract;
import bss.model.sstps.Select;
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
	
	
	
}
