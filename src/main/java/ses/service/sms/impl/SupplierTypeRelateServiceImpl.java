package ses.service.sms.impl;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatSellMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierTypeRelateService;

/**
 * @Title: SupplierTypeRelateServiceImpl
 * @Description: 供应商类型关联实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午6:33:33
 */
@Service(value = "supplierTypeRelateService")
public class SupplierTypeRelateServiceImpl implements SupplierTypeRelateService {
	
	@Autowired
	private SupplierTypeRelateMapper supplierTypeRelateMapper;
	
	//物资生产信息
	@Autowired
	private SupplierMatProMapper supplierMatProMapper;
	
	//物资生产证书
	@Autowired
	private SupplierCertProMapper supplierCertProMapper;
	
	@Autowired
	private SupplierMatSellMapper supplierMatSellMapper;
	
	@Autowired
	private SupplierCertSellMapper supplierCertSellMapper;
	
	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;
	
	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;
	
//	@Autowired
//	private SupplierReasonMapper supplierReasonMapper;
	
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	
	@Autowired
	private SupplierMatServeMapper supplierMatServeMapper;
	
	@Autowired
	private SupplierCertServeMapper supplierCertServeMapper;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Override
    @Transactional
	public void saveSupplierTypeRelate(Supplier supplier) {
		List<DictionaryData> dlist=new ArrayList<DictionaryData>();
		DictionaryData dd=new DictionaryData();
		dd.setKind(6);
		List<DictionaryData> list = dictionaryDataServiceI.find(dd);
		DictionaryData dd2=new DictionaryData();
		dd2.setKind(8);
		List<DictionaryData> wlist = dictionaryDataServiceI.find(dd2);
		dlist.addAll(wlist);
		dlist.addAll(list);
		try{
            supplierTypeRelateMapper.deleteBySupplierId(supplier.getId());
            if(StringUtils.isNotBlank(supplier.getSupplierTypeIds())){
                String supplierTypeIds = supplier.getSupplierTypeIds().trim();
                for (String str : supplierTypeIds.split(",")) {
                    SupplierTypeRelate supplierTypeRelate = new SupplierTypeRelate();
                    supplierTypeRelate.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    supplierTypeRelate.setSupplierId(supplier.getId());
                    supplierTypeRelate.setSupplierTypeId(str);
                    supplierTypeRelate.setCreatedAt(new Date());
                    supplierTypeRelateMapper.insertSelective(supplierTypeRelate);
                }
                List<DictionaryData> rlist=new LinkedList<DictionaryData>();
                for(DictionaryData d:dlist){
                    for (String str : supplierTypeIds.split(",")) {
                        if(d.getCode().equals(str)){
                            rlist.add(d);
                        }
                    }
                }
                dlist.removeAll(rlist);
            }
            if(dlist!=null&&dlist.size()>0){
                for(DictionaryData d:dlist){
                    if(d.getCode().equals("sc")){
                        supplierMatProMapper.deleteByPrimaryKey(supplier.getId());
                        supplierCertProMapper.deleteByPrimaryKey(supplier.getSupplierMatPro().getId());
                    }
                    if(d.getCode().equals("xs")){
                        supplierMatSellMapper.deleteByPrimaryKey(supplier.getId());
                        supplierCertSellMapper.deleteByPrimaryKey(supplier.getSupplierMatSell().getId());
                    }
                    if(d.getCode().equals("gc")){
                        supplierMatEngMapper.deleteByPrimaryKey(supplier.getId());
                        supplierCertEngMapper.deleteByPrimaryKey(supplier.getSupplierMatEng().getId());
                        supplierAptituteMapper.deleteByPrimaryKey(supplier.getSupplierMatEng().getId());
                    }
                    if(d.getCode().equals("fw")){
                        supplierMatServeMapper.deleteByPrimaryKey(supplier.getId());
                        supplierCertServeMapper.deleteByPrimaryKey(supplier.getSupplierMatSe().getId());
                    }
                }
            }
        }catch (Exception ex){
		    ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

	}

	@Override
	public List<SupplierTypeRelate> queryBySupplier(String id) {
		// TODO Auto-generated method stub
		return supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
	}

	@Override
	public String findBySupplier(String id) {
		List<SupplierTypeRelate> supplierTypes= supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
		 String supplierTypeIds = "";
		for(SupplierTypeRelate s : supplierTypes){
			String code = s.getSupplierTypeId();
			supplierTypeIds += code + ",";
		}
		return supplierTypeIds;
	}

	@Override
	public void delete(String supplierId, String supplierType) {
		SupplierMatSell matSell = supplierMatSellMapper.selectByPrimaryKey(supplierId);
		if(matSell!=null){
			supplierCertSellMapper.deleteByPrimaryKey(matSell.getId());
			supplierMatSellMapper.deleteByPrimaryKey(supplierId);
		}
		supplierTypeRelateMapper.deleteSupplierType(supplierId, supplierType);
		
	}

}
