package ses.service.sms.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.AfterSaleSerMapper;
import bss.dao.cs.ContractRequiredMapper;
import bss.model.cs.ContractRequired;
import ses.model.sms.AfterSaleSer;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierStars;
import ses.service.sms.AfterSaleSerService;
import ses.util.PropertiesUtil;

public class AfterSaleSerServiceImp {
	/** 售后服务Mapper **/
    @Autowired
    private AfterSaleSerMapper AfterSaleSerMapper;
    
    /**
	 * 1.获取所有售后服务信息对象
	 */
	public List<AfterSaleSer> getAll(Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return AfterSaleSerMapper.queryByList();
	}
    
	/** 
	 * 添加售后服务信息
	 */
	public void add(AfterSaleSer AfterSaleSer) {
		AfterSaleSer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		AfterSaleSerMapper.insertSelective(AfterSaleSer);

	}
    
	/** 
	 * 更新售后服务信息
	 */
	public void update(AfterSaleSer AfterSaleSer) {
		AfterSaleSer.setUpdateAt(new Timestamp(System.currentTimeMillis()));
		AfterSaleSerMapper.updateByPrimaryKeySelective(AfterSaleSer);

	}
    
	/**
	 * 根据主键查询售后服务信息
	 */
	public AfterSaleSer get(String id) {
		return AfterSaleSerMapper.selectByPrimaryKey(id);
	}
    
	/**
	 * 根据主键删除
	 */
	public void delete(String id) {
		AfterSaleSerMapper.deleteByPrimaryKey(id);
	}
	
    /**
     * @see ses.service.sms.AfterSaleSerService#queryById(java.lang.String)
     */
    public AfterSaleSer queryById(String id) {
        return AfterSaleSerMapper.selectByPrimaryKey(id);
    }
    
    /**
     * @see ses.service.sms.AfterSaleSerService#saveOrUpdateAfterSale(ses.model.sms.AfterSale)
     */
    public void saveOrUpdateAfterSaleSer(AfterSaleSer AfterSaleSer) {
        if (AfterSaleSer.getId() == null) {
        	AfterSaleSerMapper.insertSelective(AfterSaleSer);
        } else {
        	AfterSaleSerMapper.updateByPrimaryKeySelective(AfterSaleSer);
        }
    }
    public void updateAfterSaleSer(AfterSaleSer AfterSaleSer) {
    	AfterSaleSerMapper.updateAfterSaleSer(AfterSaleSer);
	}

}
