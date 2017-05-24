package bss.service.cs.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.oms.OrgnizationServiceI;
import ses.util.PathUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.ValidateUtils;
import bss.dao.cs.PurchaseContractMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.service.cs.PurchaseContractService;

import com.github.pagehelper.PageHelper;

import common.utils.UploadUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("purchaseContractService")
public class PurchaseContractServiceImpl implements PurchaseContractService {
	
	@Autowired
	private PurchaseContractMapper purchaseContractMapper;
	
	@Autowired
	private OrgnizationMapper orgnizationMapper;
	
	@Autowired
	private SupplierMapper supplierMapper;
	
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
	public List<PurchaseContract> selectRoughContract(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectRoughContract(map);
	}

	@Override
	public PurchaseContract selectRoughById(String id) {
		return purchaseContractMapper.selectRoughById(id);
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
	public void deleteRoughByPrimaryKey(String id) {
		purchaseContractMapper.deleteRoughByPrimaryKey(id);
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
	public Map createWord(PurchaseContract pur,List<ContractRequired> requList,HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(pur.getName()!=null){
			dataMap.put("contractname", pur.getName());
		}else{
			dataMap.put("contractname", "");
		}
		if(pur.getCode()!=null){
			dataMap.put("contractCode", pur.getCode());
		}else{
			dataMap.put("contractCode", "");
		}
		if(pur.getPurchaseDepName()!=null){
			Orgnization org = orgnizationMapper.findOrgByPrimaryKey(pur.getPurchaseDepName());
			dataMap.put("purchaseDepName", org.getName());
		}else{
			dataMap.put("purchaseDepName", "");
		}
		if(pur.getPurchaseLegal()!=null){
			dataMap.put("purchaseL", pur.getPurchaseLegal());
		}else{
			dataMap.put("purchaseL", "");
		}
		if(pur.getPurchaseAgent()!=null){
			dataMap.put("purchaseAgent", pur.getPurchaseAgent());
		}else{
			dataMap.put("purchaseAgent", "");
		}
		if(pur.getPurchaseContact()!=null){
			dataMap.put("purchaseContact", pur.getPurchaseContact());
		}else{
			dataMap.put("purchaseContact", "");
		}
		if(pur.getPurchaseContactTelephone()!=null){
			dataMap.put("purchaseContactTelephone", pur.getPurchaseContactTelephone());
		}else{
			dataMap.put("purchaseContactTelephone", "");
		}
		if(pur.getPurchaseContactAddress()!=null){
			dataMap.put("purchaseContactAddress", pur.getPurchaseContactAddress());
		}else{
			dataMap.put("purchaseContactAddress", "");
		}
		if(pur.getPurchaseUnitpostCode()!=null){
			dataMap.put("purchaseUnitp", pur.getPurchaseUnitpostCode());
		}else{
			dataMap.put("purchaseUnitp", "");
		}
		if(pur.getPurchaseBank()!=null){
			dataMap.put("purchaseBank", pur.getPurchaseBank());
		}else{
			dataMap.put("purchaseBank", "");
		}
		if(pur.getPurchaseBankAccount_string()!=null){
			dataMap.put("purchaseBankAccount", pur.getPurchaseBankAccount_string());
		}else{
			dataMap.put("purchaseBankAccount", "");
		}
		
		if(pur.getPurchasePayDep()!=null){
			dataMap.put("purchasePayDep", pur.getPurchasePayDep());
		}else{
			dataMap.put("purchasePayDep", "");
		}
		
		if(ValidateUtils.isNull(pur.getSupplierDepName())){
			dataMap.put("supplierDepName", "");
		}else{
			Supplier su = supplierMapper.selectOne(pur.getSupplierDepName());
			if(su!=null){
				if(ValidateUtils.isNull(su.getSupplierName())){
					dataMap.put("supplierDepName", "");
				}else{
					dataMap.put("supplierDepName", su.getSupplierName());
				}
			}else{
				dataMap.put("supplierDepName", pur.getSupplierDepName());
			}
		}
		if(pur.getSupplierLegal()!=null){
			dataMap.put("supplierLegal", pur.getSupplierLegal());
		}else{
			dataMap.put("supplierLegal", "");
		}
		if(pur.getSupplierAgent()!=null){
			dataMap.put("supplierAgent", pur.getSupplierAgent());
		}else{
			dataMap.put("supplierAgent", "");
		}
		if(pur.getSupplierContact()!=null){
			dataMap.put("supplierContact", pur.getSupplierContact());
		}else{
			dataMap.put("supplierContact", "");
		}
		if(pur.getSupplierContactTelephone()!=null){
			dataMap.put("supplierContactTelephone", pur.getSupplierContactTelephone());
		}else{
			dataMap.put("supplierContactTelephone", "");
		}
		if(pur.getSupplierContactAddress()!=null){
			dataMap.put("supplierContactAddress", pur.getSupplierContactAddress());
		}else{
			dataMap.put("supplierContactAddress", "");
		}
		if(pur.getSupplierUnitpostCode()!=null){
			dataMap.put("supplierUnitpostCode", pur.getSupplierUnitpostCode());
		}else{
			dataMap.put("supplierUnitpostCode", "");
		}
		if(pur.getSupplierBankName()!=null){
			dataMap.put("supplierBankName", pur.getSupplierBankName());
		}else{
			dataMap.put("supplierBankName", "");
		}
		if(pur.getSupplierBank()!=null){
			dataMap.put("supplierBank", pur.getSupplierBank());
		}else{
			dataMap.put("supplierBank", "");
		}
		if(pur.getSupplierBankAccount_string()!=null){
			dataMap.put("supplierBankAccount", pur.getSupplierBankAccount_string());
		}else{
			dataMap.put("supplierBankAccount", "");
		}
		if(pur.getDocumentNumber()!=null){
			dataMap.put("documentNumber", pur.getDocumentNumber());
		}else{
			dataMap.put("documentNumber", "");
		}
		if(pur.getApprovalNumber()!=null){
			dataMap.put("approvalNumber", pur.getApprovalNumber());
		}else{
			dataMap.put("approvalNumber", "");
		}
		if(pur.getQuaCode()!=null){
			dataMap.put("quaCode", pur.getQuaCode());
		}else{
			dataMap.put("quaCode", "");
		}
		if(pur.getMoney()!=null){
			dataMap.put("sum", pur.getMoney());
		}else{
			dataMap.put("sum", "");
		}
		if(pur.getMoney()!=null){
			dataMap.put("money", pur.getMoney());
		}else{
			dataMap.put("money", "");
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(requList!=null){
			for(int i=0;i<requList.size();i++){
				if(requList.get(i).getGoodsName()==null){
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("number", i+1);
				if(requList.get(i).getPlanNo()!=null && requList.get(i).getPlanNo()!=""){
					map.put("planNo", requList.get(i).getPlanNo());
				}else{
					map.put("planNo", "");
				}
				if(requList.get(i).getGoodsName()!=null && requList.get(i).getGoodsName()!=""){
					map.put("goodsName", requList.get(i).getGoodsName());
				}else{
					map.put("goodsName", "");
				}
				if(requList.get(i).getBrand()!=null && requList.get(i).getBrand()!=""){
					map.put("brand", requList.get(i).getBrand());
				}else{
					map.put("brand", "");
				}
				if(requList.get(i).getStand()!=null && requList.get(i).getStand()!=""){
					map.put("stand", requList.get(i).getStand());
				}else{
					map.put("stand", "");
				}
				if(requList.get(i).getItem()!=null && requList.get(i).getItem()!=""){
					map.put("item", requList.get(i).getItem());
				}else{
					map.put("item", "");
				}
				if(requList.get(i).getPurchaseCount()!=null){
					map.put("purchaseCount", requList.get(i).getPurchaseCount());
				}else{
					map.put("purchaseCount", "");
				}
				if(requList.get(i).getPrice()!=null){
					map.put("price", requList.get(i).getPrice());
				}else{
					map.put("price", "");
				}
				if(requList.get(i).getAmount()!=null){
					map.put("mount", requList.get(i).getAmount());
				}else{
					map.put("mount", "");
				}
				if(requList.get(i).getDeliverDate()!=null){
					map.put("deliverDate", requList.get(i).getDeliverDate());
				}else{
					map.put("deliverDate", "");
				}
				if(requList.get(i).getMemo()!=null){
					map.put("mem", requList.get(i).getMemo());
				}else{
					map.put("mem", "");
				}
				list.add(map);
			}
		}
		dataMap.put("list", list);
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
//		configuration.setClassForTemplateLoading(this.getClass(), "/template");
//		System.out.println(this.getClass());
		Template t = null;
		try {

			if(pur.getStatus()!=null){
				if(pur.getStatus()==2){
					t=configuration.getTemplate("formalcontract.ftl");
				}else{
					t=configuration.getTemplate("contract.ftl");
				}
			}else{
				t = configuration.getTemplate("contract.ftl");
			}

			if(pur.getStatus()==null){
				t = configuration.getTemplate("contract.ftl");
			}else if (pur.getStatus()==2) {
				t=configuration.getTemplate("formalcontract.ftl");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
//		String rootpath = (PathUtil.getWebRoot() + "contract/").replace("\\", "/");
//		File rootFile = new File(rootpath);
//		if(!rootFile.exists()){
//			rootFile.mkdirs();
//		}
		String name = "";
		if(pur.getName()!=null){
			name = pur.getName();
		}
//		String targetFileName = System.currentTimeMillis()+ "_"+ name +".doc";
//		String finalPath = PropUtil.getProperty("file.base.path");
//        int type = 2;
//        String fileSysPath = getFileDir(type);
//        File file = null;
//        if (StringUtils.isNotBlank(fileSysPath)){
//        	finalPath = finalPath + fileSysPath + File.separator + UploadUtil.getDataFilePath();
//            UploadUtil.createDir(finalPath);
//            file = UploadUtil.getFile(finalPath, targetFileName);
//    		Writer out = null;
//    		try {
//    			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
//    		} catch (FileNotFoundException e) {
//    			e.printStackTrace();
//    		} catch (UnsupportedEncodingException e) {
//    			e.printStackTrace();
//    		}
//    		try {
//    			t.process(dataMap, out);
//    			out.flush();
//                out.close();
//    		} catch (TemplateException e) {
//    			e.printStackTrace();
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
//        }
		String fileName = System.currentTimeMillis()+".doc";
		String filePath = "";
            try {
            	String basePath = PropUtil.getProperty("file.base.path");
                String temp = PropUtil.getProperty("file.temp.path");
                String path = basePath + File.separator + temp;
                File file = new File(path);
                if (!file.exists()){
                    file.mkdirs();
                }
                File rootFile = new File(path,fileName);
                if(!rootFile.exists()){
                	rootFile.createNewFile();
                }
                	Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rootFile),"UTF-8"));
                    t.process(dataMap, out);
                    out.flush();
                    out.close();
                    filePath = rootFile.getPath().replaceAll("\\\\","/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fileName", fileName);
            map.put("filePath", filePath);
		return map;
	}
	
	 public String getFileDir(int type){
	        String path = "";
	        switch (type){
	            case 1 : path = PropUtil.getProperty("file.supplier.system.path"); break;
	            case 2 : path = PropUtil.getProperty("file.tender.system.path"); break;
	            case 3 : path = PropUtil.getProperty("file.expert.system.path"); break;
	            case 4 : path = PropUtil.getProperty("file.forum.system.path"); break;
	        }
	        return path;
	    }

	@Override
	public List<PurchaseContract> selectFormalByContractType(
			Integer contractType) {
		return purchaseContractMapper.selectFormalByContractType(contractType);
	}
	
	/**
     * @Title: findPurchaseContractByMap
     * @author: Wang Zhaohua
     * @date: 2016-11-2 下午8:02:07
     * @Description: 根据条件查询合同
     * @param: @param param
     * @param: @return
     * @return: List<PurchaseContract>
     */
	@Override
	public List<PurchaseContract> findPurchaseContractByMap(PurchaseContract purchaseContract, int page) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isDeleted", 0);// 未删除
		param.put("status", 2);// 正常合同
		param.put("isDeclare", purchaseContract.getIsDeclare());// 待报
		param.put("projectName", purchaseContract.getProjectName());// 项目名称
		
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
		
		return purchaseContractMapper.findPurchaseContractByMap(param);
	}

	@Override
	public List<PurchaseContract> selectAllContract() {
		return purchaseContractMapper.selectAllContract();
	}

	@Override
	public void insertSelectiveById(PurchaseContract record) {
		purchaseContractMapper.insertSelectiveById(record);
	}

	@Override
	public List<Orgnization> findAllUsefulOrg() {
		return orgnizationMapper.findAllUsefulOrg();
	}

	@Override
	public List<PurchaseContract> selectAllContractByStatus(
			Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectAllContractByStatus(map);
	}
	
	@Override
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,String filePath ,String fileName){
        response.reset();
        String userAgent = request.getHeader("User-Agent"); 
        try {
            if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
            }
            File files = new File(filePath);
            response.setContentType("application/octet-stream");   
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            response.addHeader("Content-Length", "" + files.length());
            response.setCharacterEncoding("UTF-8"); 
            InputStream fis = new BufferedInputStream(new FileInputStream(files));   
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            UploadUtil.writeFile(fis, toClient);
            toClient.flush();   
            toClient.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

	@Override
	public List<PurchaseContract> selectContractByCode() {
		return purchaseContractMapper.selectContractByCode();
	}

	@Override
	public List<PurchaseContract> selectAllContractByCode(
			Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectAllContractByCode(map);
	}

	@Override
	public List<PurchaseContract> selectAllContractBySupplierId(
			Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectAllContractBySupplierId(map);
		
	}

	@Override
	public List<PurchaseContract> selectAllContractBySupplier(
			Map<String, Object> map) {
		return purchaseContractMapper.selectFormalContract(map);
	}

	@Override
	public List<PurchaseContract> selectByProjectCode(String code) {
		return purchaseContractMapper.selectByProjectCode(code);
	}
}
