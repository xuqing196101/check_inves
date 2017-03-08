package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ses.dao.sms.AfterSaleSerMapper;
import bss.dao.cs.ContractRequiredMapper;
import bss.model.cs.ContractRequired;
import ses.model.sms.AfterSaleSer;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierStars;
import ses.service.sms.AfterSaleSerService;

public class AfterSaleSerServiceImp {
	/** 售后服务Mapper **/
    @Autowired
    private AfterSaleSerMapper AfterSaleSerMapper;
    
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
    
   /* public ContractRequired selectConRequByPrimaryKey() {
		return ContractRequiredMapper.selectConRequByPrimaryKey(null);
	}
    
    *//**
     * @see ses.service.sms.SupplierAfterSaleDepService#queryById(java.lang.String)
     *//*
    @Override
    public SupplierAfterSaleDep queryByRequiredId(String id) {
        return ContractRequiredMapper.selectByPrimaryKey(id);
    }
    */
    public void updateAfterSaleSer(AfterSaleSer AfterSaleSer) {
    	AfterSaleSerMapper.updateAfterSaleSer(AfterSaleSer);
	}

}
