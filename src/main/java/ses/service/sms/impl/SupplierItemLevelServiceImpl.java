package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import ses.dao.sms.SupplierItemLevelMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierItemLevel;
import ses.service.sms.SupplierCertEngService;
import ses.service.sms.SupplierItemLevelServer;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import ses.util.SupplierToolUtil;
/**
 * 
 * Description:供应商等级service 实现
 * 
 * @author YangHongLiang
 * @version 2017-6-21
 * @since JDK1.7
 */
@Service
public class SupplierItemLevelServiceImpl implements SupplierItemLevelServer {
	@Autowired
	private SupplierItemLevelMapper supplierItemLevelMapper;
	//供应商service
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SupplierCertEngService supplierCertEngService;

	@Override
	public List<SupplierItemLevel> findSupplierItemLevel(SupplierItemLevel supplier, Integer page, String categoryIds) {
		List<SupplierItemLevel> rutlist=new ArrayList<SupplierItemLevel>();
		if(StringUtils.isBlank(supplier.getSupplierTypeId())){
		 	return rutlist;
		}
		DictionaryData util=DictionaryDataUtil.findById(supplier.getSupplierTypeId());
		if(util ==null){
			return rutlist;
		}
		String supplierType=util.getCode();
		if(supplierType ==null){
			return rutlist;
		}
		page=page==null?0:page;
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		 //判断 是否是工程
        if(SupplierToolUtil.TOOL_PROJECT.equals(supplierType)){
			Supplier sup =new Supplier();
			//生产&& 销售
			if(!SupplierToolUtil.PRODUCT_ID.equals(categoryIds) && !SupplierToolUtil.SALES_ID.equals(categoryIds)){
				sup.setSupplierTypeId(categoryIds);
			}
			sup.setSupplierType(supplierType);
			//查询供应商
			List<Supplier> listSupplier=supplierService.findSupplierByCategoryId(sup);
			if(listSupplier.isEmpty()){
				return rutlist;
			}
	        SupplierItemLevel level=new SupplierItemLevel();
	        for (Supplier item : listSupplier) {
		        //工程等级
		        List < SupplierCertEng > supplierCertEng = supplierCertEngService.findCertEngBySupplierId(item.getId());
		    	for(int i = 0; i < supplierCertEng.size() - 1; i++) {
		    		for(int j = supplierCertEng.size() - 1; j > i; j--) {
		    			if(supplierCertEng.get(j).getId().equals(supplierCertEng.get(i).getId())) {
		    				supplierCertEng.remove(j);
		    			}
		    		}
		    	}
			    for(SupplierCertEng eng : supplierCertEng){
					sup.setGrade(eng.getCertMaxLevel());
		        }
				level.setSupplierId(item.getId());
				level.setArmyBusinessName(item.getArmyBusinessName());
				level.setCategoryId(categoryIds);
				level.setSupplierLevel(sup.getGrade());
				level.setSupplierTypeId(supplierType);
				level.setSupplierName(item.getSupplierName());
				rutlist.add(level);
	        }
	        return rutlist;
	    }else{
	    	return supplierItemLevelMapper.selectByCategoryId(categoryIds,supplier.getArmyBusinessName(),supplier.getSupplierName());
	    }
	}

	@Override
	public int saveItemLevel(Supplier supplier,String categoryId,Date date) {
		SupplierItemLevel item=new SupplierItemLevel();
		item.setCategoryId(categoryId);
		item.setArmyBusinessName(supplier.getArmyBuinessAddress());
		item.setCreatedAt(date);
		item.setSupplierId(supplier.getId());
		item.setSupplierLevel(supplier.getGrade());
		item.setSupplierName(supplier.getSupplierName());
		item.setSupplierTypeId(supplier.getSupplierType());
		return supplierItemLevelMapper.insertSelective(item);
	}

	@Override
	public int isItemLevelBy(String supplierId, String supplierTypeId) {
		return supplierItemLevelMapper.countItemLevel(supplierId, supplierTypeId);
	}

	@Override
	public int updateItemLevel(Supplier supplier,Date date) {
		SupplierItemLevel item=new SupplierItemLevel();
		item.setArmyBusinessName(supplier.getArmyBuinessAddress());
		item.setCreatedAt(date);
		item.setSupplierId(supplier.getId());
		item.setSupplierLevel(supplier.getGrade());
		item.setSupplierName(supplier.getSupplierName());
		item.setSupplierTypeId(supplier.getSupplierTypeId());
		return supplierItemLevelMapper.updateByPrimaryKeyBySupplierIdTypeID(item);
	}

	@Override
	public int deleteItemLevel(String categoryId,String supplierTypeId) {
		return supplierItemLevelMapper.deleteByCategoryIdType(categoryId, supplierTypeId);
	}
	

}