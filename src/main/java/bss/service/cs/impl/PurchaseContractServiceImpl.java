package bss.service.cs.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import bss.dao.cs.PurchaseContractMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.service.cs.PurchaseContractService;

@Service("purchaseContractService")
public class PurchaseContractServiceImpl implements PurchaseContractService {
	
	@Autowired
	private PurchaseContractMapper purchaseContractMapper;
	
	@Override
	public int insert(PurchaseContract record) {
		return 0;
	}

	@Override
	public int insertSelective(PurchaseContract record) {
		purchaseContractMapper.insertSelective(record);
		return 0;
	}
	
	@Override
	public List<PurchaseContract> selectAllPurchaseContract() {
		List<PurchaseContract> contractList = purchaseContractMapper.selectAllPurchaseContract();
		return contractList;
	}

	@Override
	public PurchaseContract selectByCode(String code) {
		PurchaseContract purchaseContract = purchaseContractMapper.selectByCode(code);
		return purchaseContract;
	}

	@Override
	public PurchaseContract selectById(String id) {
		return purchaseContractMapper.selectContractByid(id);
	}

	@Override
	public List<PurchaseContract> selectDraftContract(Map<String,Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectDraftContract(map);
	}

	@Override
	public PurchaseContract selectDraftById(String id) {
		return purchaseContractMapper.selectDraftById(id);
	}

	@Override
	public void updateByPrimaryKeySelective(PurchaseContract record) {
		purchaseContractMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void deleteDraftByPrimaryKey(String id) {
		purchaseContractMapper.deleteDraftByPrimaryKey(id);
	}

	@Override
	public List<PurchaseContract> selectFormalContract(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectFormalContract(map);
	}

	@Override
	public PurchaseContract selectFormalById(String id) {
		return purchaseContractMapper.selectFormalById(id);
	}

	@Override
	public int createWord(PurchaseContract pur,List<ContractRequired> requList) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("contractname", pur.getName());
		dataMap.put("contractCode", pur.getCode());
		dataMap.put("purchaseDepName", pur.getPurchaseDepName());
		dataMap.put("purchaseL", pur.getPurchaseLegal());
		dataMap.put("purchaseAgent", pur.getPurchaseAgent());
		dataMap.put("purchaseContact", pur.getPurchaseContact());
		dataMap.put("purchaseContactTelephone", pur.getPurchaseContactTelephone());
		dataMap.put("purchaseContactAddress", pur.getPurchaseContactAddress());
		dataMap.put("purchaseUnitp", pur.getPurchaseUnitpostCode());
		dataMap.put("purchasePayDep", pur.getPurchasePayDep());
		dataMap.put("purchaseBank", pur.getPurchaseBank());
		dataMap.put("purchaseBankAccount", pur.getPurchaseBankAccount());
		dataMap.put("supplierDepName", pur.getSupplierDepName());
		dataMap.put("supplierLegal", pur.getSupplierLegal());
		dataMap.put("supplierAgent", pur.getSupplierAgent());
		dataMap.put("supplierContact", pur.getSupplierContact());
		dataMap.put("supplierContactTelephone", pur.getSupplierContactTelephone());
		dataMap.put("supplierContactAddress", pur.getSupplierContactAddress());
		dataMap.put("supplierUnitpostCode", pur.getSupplierUnitpostCode());
		dataMap.put("supplierBankName", pur.getSupplierBankName());
		dataMap.put("supplierBank", pur.getSupplierBank());
		dataMap.put("supplierBankAccount", pur.getSupplierBankAccount());
		dataMap.put("documentNumber", pur.getDocumentNumber());
		dataMap.put("approvalNumber", pur.getApprovalNumber());
		dataMap.put("quaCode", pur.getQuaCode());
		dataMap.put("sum", pur.getMoney());
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<requList.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("number", i+1);
			map.put("planNo", requList.get(i).getPlanNo());
			map.put("goodsName", requList.get(i).getGoodsName());
			map.put("brand", requList.get(i).getBrand());
			map.put("stand", requList.get(i).getStand());
			map.put("item", requList.get(i).getItem());
			map.put("purchaseCount", requList.get(i).getPurchaseCount());
			map.put("price", requList.get(i).getPrice());
			map.put("mount", requList.get(i).getAmount());
			map.put("deliverDate", requList.get(i).getDeliverDate());
			map.put("mem", requList.get(i).getMemo());
			list.add(map);
		}
		dataMap.put("list", list);
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(this.getClass(), "ftl");
		Template t = null;
		try {
			t = configuration.getTemplate("test.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File outFile = new File("F:/test"+Math.random()*10000+".doc");
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			t.process(dataMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
