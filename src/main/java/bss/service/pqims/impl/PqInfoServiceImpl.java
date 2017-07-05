/**
 * 
 */
package bss.service.pqims.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.model.sms.Supplier;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;

import bss.dao.pqims.PqInfoMapper;
import bss.model.pqims.PqInfo;
import bss.model.sstps.Select;
import bss.service.pqims.PqInfoService;

/**
 * @Title:PqInfoServiceImpl 
 * @Description: 质检信息管理 实现类
 * @author Liyi
 * @date 2016-9-18下午5:40:03
 *
 */
@Service("pqinfoService")
public class PqInfoServiceImpl implements PqInfoService {
	
	/**
	 * 调用质检信息映射类
	 */
	@Resource
	private PqInfoMapper pqInfoMapper;

	/**
	 * 1.获取所有质检信息对象
	 */
	@Override
	public List<PqInfo> getAll(Integer pageNum, HashMap<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return pqInfoMapper.queryByList(map);
	}

	/** 
	 * 添加质检信息
	 */
	@Override
	public void add(PqInfo pqInfo) {
		pqInfo.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		pqInfoMapper.insertSelective(pqInfo);

	}

	/** 
	 * 更新质检信息
	 */
	@Override
	public void update(PqInfo pqInfo) {
		pqInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		pqInfoMapper.updateByPrimaryKeySelective(pqInfo);

	}

	/**
	 * 根据主键查询质检信息
	 */
	@Override
	public PqInfo get(String id) {
		return pqInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据主键删除
	 */
	@Override
	public void delete(String id) {
		pqInfoMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 查询模板条数
	 */
	@Override
	public Integer queryByConut(String id) {
		return pqInfoMapper.queryByCount(id);
	}

	/**
	 * 根据条件查询
	 */
	@Override
	public List<PqInfo> selectByCondition(PqInfo pqInfo,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return pqInfoMapper.selectByCondition(pqInfo);
	}

	
	@Override
	public BigDecimal queryByCountSuccess(String supplierName) {
		return pqInfoMapper.queryByCountSuccess(supplierName);
	}

	
	@Override
	public BigDecimal queryByCountFail(String supplierName) {
		return pqInfoMapper.queryByCountFail(supplierName);
	}

	
	@Override
	public List<String> queryDepName(Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return pqInfoMapper.queryDepName();
	}

	
	@Override
	public List<Supplier> selectByDepName(Integer pageNum, PqInfo pqInfo) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return pqInfoMapper.selectByDepName(pqInfo);
	}


	@Override
	public List<Select> selectChose(String purchaseType) {
		return pqInfoMapper.selectChose(purchaseType);
	}

	/* (non-Javadoc)
	 * @see bss.service.pqims.PqInfoService#queryPath(java.lang.String)
	 */
	@Override
	public String queryPath(String id) {
		return pqInfoMapper.queryPath(id);
	}

    @Override
    public List<PqInfo> selectByContract(HashMap<String, Object> map) {
        
        return pqInfoMapper.selectByContract(map);
    }


}
