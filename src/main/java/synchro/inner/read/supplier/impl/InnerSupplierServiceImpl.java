package synchro.inner.read.supplier.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ses.dao.sms.SupplierAfterSaleDepMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierBranchMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatSellMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.SupplierRegPersonMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>读取供应商信息service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class InnerSupplierServiceImpl implements InnerSupplierService {
    
    /** 记录service **/
    @Autowired
    private SynchRecordService synchRecordService;
    
    /** 用户service **/
    @Autowired
    private UserServiceI userService;
    
    /** 供应商 service **/
    @Autowired
    private SupplierService supplierSerice;
    
    @Autowired
    private SupplierFinanceMapper supplierFinanceMapper;
    
    /**  生产经营地址 service */
    @Autowired
    private SupplierAddressService supplierAddressService;
    
    @Autowired
    private SupplierBranchMapper supplierBranchMapper;
    
    @Autowired
    private  SupplierStockholderMapper supplierStockholderMapper;
    
    @Autowired
    private SupplierAfterSaleDepMapper supplierAfterSaleDepMapper;
    
    @Autowired
    private SupplierTypeRelateMapper supplierTypeRelateMapper;
    
    @Autowired
    private  SupplierMatProMapper supplierMatProMapper;
    
    @Autowired
    private SupplierMatEngMapper supplierMatEngMapper;
    
    @Autowired
    private SupplierMatSellMapper supplierMatSellMapper;
     
    @Autowired
    private SupplierMatServeMapper supplierMatServeMapper;
    
    @Autowired
    private SupplierCertProMapper supplierCertProMapper;
    
    
    @Autowired
    private SupplierCertSellMapper supplierCertSellMapper;
    
    
    @Autowired
    private SupplierAptituteMapper  supplierAptituteMapper;
    
    @Autowired
    private SupplierCertEngMapper supplierCertEngMapper;
    
    @Autowired
    private SupplierRegPersonMapper supplierRegPersonMapper;
    
    @Autowired
    private SupplierCertServeMapper supplierCertServeMapper;
    
    @Autowired
    private SupplierItemMapper supplierItemMapper;
    
    /**
     * 
     * @see synchro.inner.read.supplier.InnerSupplierService#importSupplierInfo(java.io.File)
     */
    @Override
    public void importSupplierInfo(final File file) {
       List<Supplier> list = getSupplier(file);
       for (Supplier supplier : list){
    	   if(supplier.getListSupplierFinances().size()>0){
    		   for(SupplierFinance sf:supplier.getListSupplierFinances()){
    			   supplierFinanceMapper.insertSelective(sf);
    		   }
    	   }
    	   if(supplier.getAddressList().size()>0){
    			   supplierAddressService.addList(supplier.getAddressList(), supplier.getId());
    	   }
    	   if(supplier.getBranchList().size()>0){
    		   for(SupplierBranch sb:supplier.getBranchList()){
    			   supplierBranchMapper.insertSelective(sb);
    		   }
    	   }
    	   if(supplier.getListSupplierStockholders().size()>0){
    		   for(SupplierStockholder ss:supplier.getListSupplierStockholders()){
    			   supplierStockholderMapper.insertSelective(ss);
    		   }
    	   }
    	   if(supplier.getListSupplierAfterSaleDep().size()>0){
    		   for(SupplierAfterSaleDep sa:supplier.getListSupplierAfterSaleDep()){
    			   supplierAfterSaleDepMapper.insertSelective(sa);
    		   }
    	   }
    	   if(supplier.getListSupplierTypeRelates().size()>0){
    		   for(SupplierTypeRelate str:supplier.getListSupplierTypeRelates()){
    			   supplierTypeRelateMapper.insertSelective(str);
    		   }
    	   }
    	   if(supplier.getSupplierMatPro()!=null){
    		   		supplierMatProMapper.insertSelective(supplier.getSupplierMatPro());
    		   		if(supplier.getSupplierMatPro().getListSupplierCertPros().size()>0){
    		   			for(SupplierCertPro sc:supplier.getSupplierMatPro().getListSupplierCertPros()){
    		   				supplierCertProMapper.insertSelective(sc);
    		   			}
    		   		}
    		   		
    	   }
    	   
    	   if(supplier.getSupplierMatSell()!=null){
    		   supplierMatSellMapper.insertSelective(supplier.getSupplierMatSell());
    		   if(supplier.getSupplierMatSell().getListSupplierCertSells().size()>0){
    			   for(SupplierCertSell sc:supplier.getSupplierMatSell().getListSupplierCertSells()){
		   				supplierCertSellMapper.insertSelective(sc);
		   			}
    		   }
    	   }
    	   if(supplier.getSupplierMatEng()!=null){
    		   supplierMatEngMapper.insertSelective(supplier.getSupplierMatEng());
    		   if(supplier.getSupplierMatEng().getListSupplierAptitutes().size()>0){
    			   for(SupplierAptitute sb:supplier.getSupplierMatEng().getListSupplierAptitutes()){
    				   supplierAptituteMapper.insertSelective(sb);
    			   }
    		   }
    		   if(supplier.getSupplierMatEng().getListSupplierCertEngs().size()>0){
    			   for(SupplierCertEng sce:supplier.getSupplierMatEng().getListSupplierCertEngs()){
    				   supplierCertEngMapper.insertSelective(sce);
    			   }
    		   }
    		   if(supplier.getSupplierMatEng().getListSupplierRegPersons().size()>0){
    			   for(SupplierRegPerson sp:supplier.getSupplierMatEng().getListSupplierRegPersons()){
    				   supplierRegPersonMapper.insertSelective(sp);
    			   }
    		   }
    	   }
    	   if(supplier.getSupplierMatSe()!=null){
    		   supplierMatServeMapper.insertSelective(supplier.getSupplierMatSe());
			   if(supplier.getSupplierMatSe().getListSupplierCertSes().size()>0){
				  for(SupplierCertServe sc:supplier.getSupplierMatSe().getListSupplierCertSes()){
					  supplierCertServeMapper.insertSelective(sc);
				  } 
			   }
		   }
    	   if(supplier.getListSupplierItems()!=null&&supplier.getListSupplierItems().size()>0){
    		   for(SupplierItem st:supplier.getListSupplierItems()){
    			   supplierItemMapper.insertSelective(st);
    		   }
    	   }
    	   
//    	   if(supplier.getSupp){
//    		   
//    	   }
    	   
    	   
           saveUser(supplier.getUser());
           saveSupplier(supplier);
       }
       synchRecordService.importNewSupplierRecord(new Integer(list.size()).toString());
    }
    
    /**
     * 
     *〈简述〉保存用户
     *〈详细描述〉
     * @author myc
     * @param user 用户
     */
    private void saveUser(User user){
        if (user != null){
            userService.saveUser(user);
        }
    }
    
    /**
     * 
     *〈简述〉保存供应商
     *〈详细描述〉
     * @author myc
     * @param supplier 供应商
     */
    private void saveSupplier(Supplier supplier){
        if (supplier != null){
            supplierSerice.saveSupplier(supplier);
        }
    }
    
    /**
     * 
     *〈简述〉获取供应商list
     *〈详细描述〉
     * @author myc
     * @param file 文件
     * @return
     */
    private List<Supplier> getSupplier(final File file){
        List<Supplier> supplierList = FileUtils.getBeans(file, Supplier.class); 
        return supplierList;
    }
    

}
