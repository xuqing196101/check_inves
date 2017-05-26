package synchro.outer.back.service.org.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import ses.dao.oms.OrgnizationMapper;
import ses.dao.oms.PurchaseDepMapper;
import ses.formbean.OrgBean;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.service.oms.PurChaseDepOrgService;
import synchro.outer.back.service.org.OrgService;
import synchro.util.FileUtils;


@Service
public class OrgServiceImpl implements OrgService{

	@Autowired
	private PurChaseDepOrgService purChaseDepOrgService;
	
	@Autowired
	private OrgnizationMapper orgnizationMapper;
	
	@Autowired
	private PurchaseDepMapper purchaseDepMapper;
 
	
	@Override
	public void getDep() {
		 OrgBean ob=new OrgBean();
	     List<PurchaseDep> deplist = purChaseDepOrgService.getDep();
		 ob.setDepList(deplist);
		 List<Orgnization> list = orgnizationMapper.getOrg();
		 ob.setOrgList(list);
	     FileUtils.writeFile(FileUtils.getOrgFile(),JSON.toJSONString(ob));
	}

	public void innerOrg(final File file){
		   List<OrgBean> obs = FileUtils.getSupplier(file, OrgBean.class);
		   if(obs!=null&&obs.size()>0){
			   for(OrgBean ob:obs){
				   List<PurchaseDep> depList = ob.getDepList();
				   for(PurchaseDep dep:depList){
					  PurchaseDep purchaseDep = purchaseDepMapper.selectByOrgId(dep.getId());
					   if(purchaseDep==null){
						   purchaseDepMapper.savePurchaseDep(dep);
					   }
					   if(purchaseDep!=null){
						   purchaseDepMapper.update(dep);
					   }
					   
				   }
				   List<Orgnization> orgList = ob.getOrgList();
				   for(Orgnization org:orgList){
					   Orgnization orgnization = orgnizationMapper.findOrgByPrimaryKey(org.getId());
					   if(orgnization==null){
						   orgnizationMapper.insertOrg(org);
					   }
					   if(orgnization!=null){
						   orgnizationMapper.updateOrgnizationById(org);
					   }
				   }
			   }
		   }
		
	}
	
	
}
