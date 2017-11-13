package synchro.outer.back.service.supplier.impl;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.sms.SupplierAfterSaleDepMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierAuditNotMapper;
import ses.dao.sms.SupplierAuditOpinionMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierEngQuaMapper;
import ses.dao.sms.SupplierHistoryMapper;
import ses.dao.sms.SupplierItemLevelMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierModifyMapper;
import ses.dao.sms.SupplierRegPersonMapper;
import ses.dao.sms.SupplierSignatureMapper;
import ses.formbean.ContractBean;
import ses.formbean.SupplierAuditFormBean;
import ses.model.bms.Category;
import ses.model.bms.RoleUser;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditNot;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierEngQua;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierItemLevel;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierSignature;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.CategoryService;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import synchro.outer.back.service.supplier.OuterSupplierService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.service.UploadService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 供应商业务service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class OuterSupplierServiceImpl implements OuterSupplierService{
    
    /**
     * 同步service
     */
    @Autowired
    private SynchRecordService recordService;
    
    /**
     * 用户service
     */
    @Autowired
    private UserServiceI userService;
    
    /**
     * 供应商service
     */
    @Autowired
    private SupplierService supplierService;
    
    /**
     * 供应审核service
     */
    @Autowired
    private SupplierAuditService supplierAuditService;
    
    /**
     * 境外分支 service
     */
    @Autowired
    private SupplierBranchService supplierBranchService;
    
    /**
     * 生产经营地址 service
     */
    @Autowired
    private SupplierAddressService supplierAddressService;
    
    /**
     * 类型关联表
     */
    @Autowired
    private SupplierTypeRelateService supplierTypeRelateService;
    
    /** 物资销售 **/
    @Autowired
    private SupplierMatSellService supplierMatSellService;
    
    /** 供应商-工程 **/
    @Autowired
    private SupplierMatEngService supplierMatEngService;
    
    /** 供应商-服务 **/
    @Autowired
    private SupplierMatSeService supplierMatSeService;
    
    /** 供应商Item **/
    @Autowired
    private SupplierItemService supplierItemService;
    
    
    @Autowired
    private SupplierAfterSaleDepMapper supplierAfterSaleDepMapper;
    
    
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
    private SupplierEngQuaMapper supplierEngQuaMapper;
    
    @Autowired
    private FileUploadMapper fileUploadMapper;
    
    @Autowired
    private SupplierHistoryMapper supplierHistoryMapper;
    
    @Autowired
    private SupplierModifyMapper supplierModifyMapper;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
	private CategoryService categoryService;
    
    @Autowired
    private TodosMapper todosMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private SupplierMapper supplierMapper;
    
    @Autowired
    private SupplierAuditMapper supplierAuditMapper;
    
    
    @Autowired
    private SupplierAuditNotMapper supplierAuditNotMapper;
    
    @Autowired
    private SupplierSignatureMapper supplierSignatureMapper;

    @Autowired
    private SupplierAuditOpinionMapper supplierAuditOpinionMapper;

    @Autowired
    private SupplierItemLevelMapper supplierItemLevelMapper;
    
    /**
     * 
     * @see synchro.outer.back.service.supplier.OuterSupplierService#exportCommitSupplier(java.lang.String, java.lang.String, java.util.Date)
     */
    @Override
    public void exportCommitSupplier(String startTime, String endTime, Date synchDate) {
        getExportData(startTime, endTime, synchDate);
    }

    /**
     *〈简述〉获取新注册的用户信息
     *〈详细描述〉
     * @author myc
     */
    public void getExportData(String startTime, String endTime, Date synchDate){
        List<Supplier> supplierList = supplierService.getCommintSupplierByDate(startTime, endTime);
        List<Supplier> list = getSupplierList(supplierList);
//        List<UploadFile> attachList = new ArrayList<>();
        List<SupplierFinance> listSupplierFinances = new ArrayList<SupplierFinance>();
        List<SupplierCertPro> listSupplierCertPros = new ArrayList<SupplierCertPro>();
        List<SupplierMatEng> matEngs = new ArrayList<SupplierMatEng>();
        List<SupplierAptitute> listSupplierCertEngs = new ArrayList<SupplierAptitute>();
        List<SupplierCertSell> listSupplierCertSells = new ArrayList<SupplierCertSell>();
        List<SupplierCertServe> listSupplierCertSes = new ArrayList<SupplierCertServe>();
        List<SupplierEngQua> listSupplierEngQuas = new ArrayList<SupplierEngQua>();
//        List < Category > category = new ArrayList < Category > ();
        for (Supplier supp : list){
        	   //代办导入
        	List<Todos> todos = todosMapper.getTodos(supp.getUser().getId());
        	List<RoleUser> userRoles = userMapper.queryByUserId(supp.getUser().getId(), null);
        	supp.setUserRoles(userRoles);
            supp.setTodoList(todos);
//            List<UploadFile> fileList = uploadService.substrBusniessI(supp.getId());
//            attachList.addAll(fileList);
            listSupplierFinances.addAll(supp.getListSupplierFinances());
            if(supp.getSupplierMatPro()!=null){
            	listSupplierCertPros.addAll(supp.getSupplierMatPro().getListSupplierCertPros());
            }
            if(supp.getSupplierMatEng()!=null){
            	matEngs.add(supp.getSupplierMatEng());
            	listSupplierCertEngs.addAll(supp.getSupplierMatEng().getListSupplierAptitutes());
            	listSupplierEngQuas.addAll(supp.getSupplierMatEng().getListSupplierEngQuas());
            }
            if(supp.getSupplierMatSell()!=null){
            	listSupplierCertSells.addAll(supp.getSupplierMatSell().getListSupplierCertSells());
            }
            
            if(supp.getSupplierMatSe()!=null){
            	listSupplierCertSes.addAll(supp.getSupplierMatSe().getListSupplierCertSes());
            }
      
//    		List < SupplierItem > itemsList = supplierItemService.getSupplierId(supp.getId());
//    		for(SupplierItem item: itemsList) {
//    			 List<UploadFile> itemFiles = uploadService.substrBusniessI(item.getId());
//    			Category cate = categoryService.findById(item.getCategoryId());
//    			if(cate!=null){
//    				cate.setId(item.getId());
//        			category.add(cate);
//    			}
//    			 attachList.addAll(itemFiles);
//    		}
    		
//    		List<SupplierAddress> addressList = supp.getAddressList();
//    		
//    		if(addressList!=null&&addressList.size()>0){
//    			for(SupplierAddress sa:addressList){
//        			List<UploadFile> addrFiles = uploadService.substrBusniessI(sa.getId());
//                    attachList.addAll(addrFiles);
//        		}
//    		}
    		
    		
    		
    		
        }
        //财务信息附件
//        for(SupplierFinance fiance:listSupplierFinances){
//        	   List<UploadFile> fileList = uploadService.findBybusinessId(fiance.getId(), Constant.SUPPLIER_SYS_KEY);
//               attachList.addAll(fileList);
//        }
        
        //生产证书信息附件
//        for(SupplierCertPro proCert:listSupplierCertPros){
//        	List<UploadFile> fileList = uploadService.findBybusinessId(proCert.getId(), Constant.SUPPLIER_SYS_KEY);
//            attachList.addAll(fileList);
//        }
        
        //工程信息主表附件
        
        
        
        
        //工程资质信息附件
         
//        for(SupplierAptitute eng:listSupplierCertEngs){
//        	List<UploadFile> fileList = uploadService.findBybusinessId(eng.getId(), Constant.SUPPLIER_SYS_KEY);
//            attachList.addAll(fileList);
//        }
        
        //销售证书附件
//        for(SupplierCertSell sell:listSupplierCertSells){
//        	List<UploadFile> fileList = uploadService.findBybusinessId(sell.getId(), Constant.SUPPLIER_SYS_KEY);
//            attachList.addAll(fileList);
//        }
        //服务证书信息附件
//        for(SupplierCertServe sell:listSupplierCertSes){
//        	List<UploadFile> fileList = uploadService.findBybusinessId(sell.getId(), Constant.SUPPLIER_SYS_KEY);
//            attachList.addAll(fileList);
//        }
        //资质证书附件
        
        
        
        
		// 查询品目合同信息
//		List < ContractBean > contract = supplierService.getContract(category);
//	      for(ContractBean con:contract){
//	    	  List<UploadFile> fileList = uploadService.findBybusinessId(con.getId(), Constant.SUPPLIER_SYS_KEY);
//	          attachList.addAll(fileList);
//	      }
     
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getNewSupperBackUpFile(),JSON.toJSONString(list));
//            String basePath = FileUtils.attachExportPath(Constant.SUPPLIER_SYS_KEY);
//            if (StringUtils.isNotBlank(basePath)){
//                OperAttachment.writeFile(basePath, attachList);
//                recordService.backupAttach(new Integer(attachList.size()).toString());
//            }
        }
        recordService.commitSupplierRecord(new Integer(list.size()).toString(), synchDate );
    }
    
    /**
     * 
     *〈简述〉根据主数据查询关联的数据
     *〈详细描述〉
     * @author myc
     * @param supplierList 主数据
     * @return 组装完成的数据
     */
    private List<Supplier> getSupplierList(List<Supplier> supplierList){
        List <Supplier> list = new ArrayList<>();
        for (Supplier supplier : supplierList){
            packageSupplier(supplier);
            list.add(supplier);
        }
        return list;
    }
    
    /**
     * 
     *〈简述〉封装关联对象
     *〈详细描述〉
     * @author myc
     * @param supplier 
     */
    private void packageSupplier(Supplier supplier){
        //帐号
        supplier.setUser(getUser(supplier.getId()));
        //财务信息
        supplier.setListSupplierFinances(getFinance(supplier.getId()));
        //股东信息
        supplier.setListSupplierStockholders(getShareholder(supplier.getId()));
        //分支信息
        supplier.setBranchList(getBranch(supplier.getId()));
        //地址信息
        supplier.setAddressList(getOPeraAddress(supplier.getId()));
        //专业关联信息
        supplier.setListSupplierTypeRelates(getTypeRelate(supplier.getId()));
        //物资生产型
        supplier.setSupplierMatPro(getMatPro(supplier.getId()));
        //物资销售型
        supplier.setSupplierMatSell(getMatSell(supplier.getId()));
        //工程型
        supplier.setSupplierMatEng(getMatEng(supplier.getId()));
        //服务型
        supplier.setSupplierMatSe(getMatServer(supplier.getId()));
        //关联品目信息
        supplier.setListSupplierItems(getSupplierItems(supplier.getId()));
        //供应商售后服务机构
        supplier.setListSupplierAfterSaleDep(getSupplierAfterDep(supplier.getId()));
    
        List<UploadFile> files=new LinkedList<UploadFile>();
        
        //查询基本的和工程的承包范围
        List<UploadFile> attchs = fileUploadMapper.substrBusinessId(supplier.getId());
        files.addAll(attchs);
        
        //经营生产地址附件
        List<SupplierAddress> address = getOPeraAddress(supplier.getId());
        for(SupplierAddress sf:address){
        	List<UploadFile> sfFiles = fileUploadMapper.substrBusinessId(sf.getId());
       	 	files.addAll(sfFiles);
       }
        
        //财务信息附件
        List<SupplierFinance> finances = getFinance(supplier.getId());
        for(SupplierFinance sf:finances){
        	List<UploadFile> sfFiles = fileUploadMapper.substrBusinessId(sf.getId());
            files.addAll(sfFiles);
        }
        
        SupplierMatPro matPro = getMatPro(supplier.getId());
        //生产
        if(matPro!=null){
        	List<SupplierCertPro> certPros = matPro.getListSupplierCertPros();
        	for(SupplierCertPro pro:certPros){
        		List<UploadFile> proFiles = fileUploadMapper.substrBusinessId(pro.getId());
        		files.addAll(proFiles);
        	}
        }
        SupplierMatSell matSell = getMatSell(supplier.getId());
        //销售
        if(matSell!=null){
        	List<SupplierCertSell> sells = matSell.getListSupplierCertSells();
        	for(SupplierCertSell sell:sells){
        		List<UploadFile> sellFiles = fileUploadMapper.substrBusinessId(sell.getId());
        		files.addAll(sellFiles);
        	}
        }
        SupplierMatEng matEng = getMatEng(supplier.getId());
        //工程
        if(matEng!=null){
        	List<SupplierAptitute> aptitutes = matEng.getListSupplierAptitutes();
        	for(SupplierAptitute aptitute:aptitutes){
        		List<UploadFile> aptituteFiles = fileUploadMapper.substrBusinessId(aptitute.getId());
        		files.addAll(aptituteFiles);
        	}
        	List<SupplierEngQua> engQuas = matEng.getListSupplierEngQuas();
        	for(SupplierEngQua engQua:engQuas){
        		List<UploadFile> engQuaFiles = fileUploadMapper.substrBusinessId(engQua.getId());
        		files.addAll(engQuaFiles);
        	}
        }
        SupplierMatServe serve = getMatServer(supplier.getId());
        //服务
        if(serve!=null){
        	List<SupplierCertServe> certSes = serve.getListSupplierCertSes();
        	for(SupplierCertServe server:certSes){
        		List<UploadFile> serverFiles = fileUploadMapper.substrBusinessId(server.getId());
        		files.addAll(serverFiles);
        	}
        }
        
        List < Category > category = new ArrayList < Category > ();
        List < SupplierItem > itemsList = supplierItemService.getSupplierId(supplier.getId());
		for(SupplierItem item: itemsList) {
			//资质文件的
			 List<UploadFile> itemFiles = uploadService.substrBusniessI(item.getId());
			 Category cate = categoryService.findById(item.getCategoryId());
			 if(cate!=null){
				 cate.setId(item.getId());
				 category.add(cate);
			 }
			 files.addAll(itemFiles);
		}
		
		// 查询品目合同信息
		List < ContractBean > contract = supplierService.getContract(category);
		for(ContractBean con:contract){
	    	  List<UploadFile> fileList = uploadService.substrBusniessI(con.getId());
//	    	  List<UploadFile> fileList = uploadService.substrBusinessId(con.getId());
	    	  files.addAll(fileList);
		}
	      
        supplier.setAttchList(files);
        
        
//        SupplierHistory sh=new SupplierHistory();
//        sh.setSupplierId(supplier.getId());
//        supplierHistoryMapper.selectAllBySupplierId(sh);
        List<SupplierModify> listModify = supplierModifyMapper.queryBySupplierId(supplier.getId());
        supplier.setModifys(listModify);

        // 查询供应商审核记录表
        Map<String, Object> map = new HashedMap();
        map.put("supplierId", supplier.getId());
        List<SupplierAudit> supplierAudits = supplierAuditMapper.findByMap(map);
        supplier.setSupplierAudits(supplierAudits);
    }
    
    /**
     * 
     *〈简述〉获取供应商与用户信息
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private User getUser(String supplierId){
        User user = userService.findByTypeId(supplierId);
        return user;
    }
    
    /**
     * 
     *〈简述〉供应商近三年财务信息
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return 
     */
    private List<SupplierFinance> getFinance(String supplierId){
        List<SupplierFinance> finaceList = supplierAuditService.supplierFinanceBySupplierId(supplierId);
        return finaceList;
    }
    
    /**
     * 
     *〈简述〉获取股东信息
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierStockholder> getShareholder(String supplierId){
        List<SupplierStockholder> list = supplierAuditService.ShareholderBySupplierId(supplierId);
        return list;
    }
    
    /**
     * 
     *〈简述〉获取境外分支
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierBranch> getBranch(String supplierId){
        List<SupplierBranch> branchList = supplierBranchService.findSupplierBranch(supplierId);
        return branchList;
    }
    
    /**
     * 
     *〈简述〉生产经营地址
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierAddress> getOPeraAddress(String supplierId){
        List<SupplierAddress> list = supplierAddressService.getBySupplierId(supplierId);
        return list;
    }
    
    /**
     * 
     *〈简述〉获取供应商关联表
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierTypeRelate> getTypeRelate(String supplierId){
        List<SupplierTypeRelate> list = supplierTypeRelateService.queryBySupplier(supplierId);
        return list;
    }
    
    /**
     * 
     *〈简述〉获取供应商专业信息表(物资生产型)
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private SupplierMatPro getMatPro(String supplierId){
    	SupplierMatPro pro = supplierAuditService.findSupplierMatProBysupplierId(supplierId);
    	if(pro!=null){
    		List<SupplierCertPro> list = supplierCertProMapper.findCertProByMatProId(pro.getId());
        	/*for(SupplierCertPro sc:list){
        		List<UploadFile> files = fileUploadMapper.findBybusinessId(sc.getId(), "T_SES_SMS_SUPPLIER_ATTACHMENT");
        		sc.setFileList(files);
        	}*/
        	pro.setListSupplierCertPros(list);
    	}
       return pro;
    }
    
    /**
     * 
     *〈简述〉获取供应商专业信息表(物资销售型)
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private SupplierMatSell getMatSell(String supplierId){
    	SupplierMatSell sell = supplierMatSellService.getMatSell(supplierId);
    	if(sell!=null){
	    	List<SupplierCertSell> list = supplierCertSellMapper.findCertSellByMatSellId(sell.getId());
	    	/*for(SupplierCertSell sc:list){
	    		List<UploadFile> files = fileUploadMapper.findBybusinessId(sc.getId(), "T_SES_SMS_SUPPLIER_ATTACHMENT");
	    		sc.setFileList(files);
	    	}*/
	    	sell.setListSupplierCertSells(list);
    	}
        return sell;
    }
    
    /**
     * 
     *〈简述〉获取供应商专业信息表(工程型)
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private SupplierMatEng getMatEng(String supplierId){
       	SupplierMatEng eng = supplierMatEngService.getMatEng(supplierId);
    	if(eng!=null){
    		// 资质证书详细信息
	    	List<SupplierAptitute> apts = supplierAptituteMapper.findAptituteByMatEngId(eng.getId());
	    	eng.setListSupplierAptitutes(apts);
	    	// 资质（认证）证书信息
	    	List<SupplierCertEng> certEngs = supplierCertEngMapper.findCertEngByMatEngId(eng.getId());
	    	/*for(SupplierCertEng sc:certEngs){
	    		List<UploadFile> files = fileUploadMapper.findBybusinessId(sc.getId(), "T_SES_SMS_SUPPLIER_ATTACHMENT");
	    		sc.setFileList(files);
	    	}*/
	    	eng.setListSupplierCertEngs(certEngs);
	    	// 注册人员信息
	    	List<SupplierRegPerson> persons = supplierRegPersonMapper.findRegPersonByMatEngId(eng.getId());
	    	eng.setListSupplierRegPersons(persons);
	    	// 工程资质证书
	    	List<SupplierEngQua> engQuas = supplierEngQuaMapper.findEngQuaByMatEngId(eng.getId());
	    	eng.setListSupplierEngQuas(engQuas);
    	}
        return eng;
    }
    
    /**
     * 
     *〈简述〉获取供应商专业信息表(服务型)
     *〈详细描述〉 附件表倒过来
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private SupplierMatServe getMatServer(String supplierId){
    	SupplierMatServe serve = supplierMatSeService.getMatserver(supplierId);
    	if(serve!=null){
    		List<SupplierCertServe> list = supplierCertServeMapper.findCertSeByMatSeId(serve.getId());
        	/*for(SupplierCertServe sc:list){
        		List<UploadFile> files = fileUploadMapper.findBybusinessId(sc.getId(), "T_SES_SMS_SUPPLIER_ATTACHMENT");
        		sc.setFileList(files);
        	}*/
        	serve.setListSupplierCertSes(list);
    	}
        return serve;
    }
    
    /**
     * 
     *〈简述〉获取供应商品目关联表
     *〈详细描述〉
     * @author myc
     * @param supplierId 供应商Id
     * @return
     */
    private List<SupplierItem> getSupplierItems(String supplierId){
    	List<SupplierItem> list = supplierItemService.getSupplierId(supplierId);
    	for(SupplierItem sc:list){
    		List<UploadFile> files = fileUploadMapper.findBybusinessId(sc.getCategoryId(), "T_SES_SMS_SUPPLIER_ATTACHMENT");
    		sc.setFileList(files);
    	}
    	
        return list;
    }
    
    public List<SupplierAfterSaleDep> getSupplierAfterDep(String supplierId){
    	
    	return supplierAfterSaleDepMapper.findAfterSaleDepBySupplierId(supplierId);
    }
	@Override
	public void modify(String startTime, String endTime, Date synchDate) {
		//获取供应商修改的时间
	    List<Supplier> supplierList = supplierService.getModifySupplierByDate(startTime, endTime);
		
	    List<Supplier> list = getSupplierList(supplierList);
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getNewSupperBackUpFile(),JSON.toJSONString(list));
        }
     //  recordService.commitSupplierRecord(new Integer(list.size()).toString(), synchDate );
		
		
		recordService.importModifySupplierRecord(new Integer(list.size()).toString(), synchDate);
	}

	@Override
	public void auditPass(String startTime, String endTime) {
		Map<String, Object> map=new HashMap<String,Object>();
		List<Supplier> list = supplierMapper.getByTime(startTime, endTime, null);
		List<SupplierAuditFormBean> supplierAudits=new LinkedList<SupplierAuditFormBean>();
		Supplier supplier = null;
		for(Supplier s:list){
			SupplierAuditFormBean saf=new SupplierAuditFormBean();
            supplier = new Supplier();
			/*saf.setSupplierId(s.getId());
			saf.setStatus(s.getStatus());
			saf.setAuditDate(s.getAuditDate());*/
			// 供应商ID
			supplier.setId(s.getId());
            supplier.setStatus(s.getStatus());
            // 审核时间
            supplier.setAuditDate(s.getAuditDate());
            // 审核人
            supplier.setAuditor(s.getAuditor());
            // 审核中状态
            supplier.setAuditTemporary(s.getAuditTemporary());
            // 将供应商基本信息导出
            saf.setSupplier(supplier);

			saf.setUser(getUser(s.getId()));
			map.put("supplierId", s.getId());
			List<SupplierAudit> sa = supplierAuditMapper.findByMap(map);
			saf.setSupplierAudits(sa);
			List<SupplierModify> supplierModifys = supplierModifyMapper.queryBySupplierId(s.getId());
			saf.setSupplierModify(supplierModifys);
			List<SupplierHistory> historys = supplierHistoryMapper.queryBySupplierId(s.getId());
			saf.setSupplierHistory(historys);
			List<SupplierAuditNot> supplierAuditNots = supplierAuditNotMapper.selectQueryBySupplierId(s.getId());
			saf.setSupplierAuditNot(supplierAuditNots);
			List<SupplierSignature> ss = supplierSignatureMapper.queryBySupplierId(s.getId());
			saf.setSupplierSignature(ss);
			supplierAudits.add(saf);
		}
		
		if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getSupperAuidtNotFile(),JSON.toJSONString(supplierAudits));
        }
        recordService.commitSupplierRecord(new Integer(list.size()).toString(), new Date() );
	}

	@Override
	public void tempSupplier(String startTime, String endTime) {
	 
		List<Supplier> list = supplierMapper.tempExportSupplier(startTime, endTime);
		for (Supplier supp : list){
		    List<RoleUser> userRoles = userMapper.queryByUserId(supp.getUser().getId(), null);
	        supp.setUserRoles(userRoles);
	        User user = userService.findByTypeId(supp.getId());
	        supp.setUser(user);
		}
		  
		if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getTempSupperFile(),JSON.toJSONString(list));
		}
		recordService.commitSupplierRecord(new Integer(list.size()).toString(), new Date());
	}

	@Override
	public void backSupplierExport(String startTime, String endTime) {
		 
		List<Supplier> supplierList = supplierService.getModifySupplierByDate(startTime, endTime);
        List<Supplier> list = getSupplierList(supplierList);
        List<UploadFile> attachList = new ArrayList<>();
        List<SupplierFinance> listSupplierFinances = new ArrayList<SupplierFinance>();
        List<SupplierCertPro> listSupplierCertPros = new ArrayList<SupplierCertPro>();
        List<SupplierMatEng> matEngs=new ArrayList<SupplierMatEng>();
        List<SupplierAptitute> listSupplierCertEngs = new ArrayList<SupplierAptitute>();
        List<SupplierCertSell> listSupplierCertSells = new ArrayList<SupplierCertSell>();
        List<SupplierCertServe> listSupplierCertSes = new ArrayList<SupplierCertServe>();
        List<SupplierEngQua> listSupplierEngQuas = new ArrayList<SupplierEngQua>();
        List < Category > category = new ArrayList < Category > ();
        for (Supplier supp : list){
        	//代办导入
        	List<Todos> todos = todosMapper.getTodos(supp.getUser().getId());
           	List<RoleUser> userRoles = userMapper.queryByUserId(supp.getUser().getId(), null);
           	supp.setUserRoles(userRoles);
            supp.setTodoList(todos);
            List<UploadFile> fileList = uploadService.substrBusniessI(supp.getId());
            attachList.addAll(fileList);
            listSupplierFinances.addAll(supp.getListSupplierFinances());
            if(supp.getSupplierMatPro()!=null){
            	listSupplierCertPros.addAll(supp.getSupplierMatPro().getListSupplierCertPros());
            }
            if(supp.getSupplierMatEng()!=null){
            	matEngs.add(supp.getSupplierMatEng());
            	listSupplierCertEngs.addAll(supp.getSupplierMatEng().getListSupplierAptitutes());
            	listSupplierEngQuas.addAll(supp.getSupplierMatEng().getListSupplierEngQuas());
            }
            if(supp.getSupplierMatSell()!=null){
            	listSupplierCertSells.addAll(supp.getSupplierMatSell().getListSupplierCertSells());
            }
            
            if(supp.getSupplierMatSe()!=null){
            	listSupplierCertSes.addAll(supp.getSupplierMatSe().getListSupplierCertSes());
            }
      
    		List < SupplierItem > itemsList = supplierItemService.getSupplierId(supp.getId());
    		for(SupplierItem item: itemsList) {
    			List<UploadFile> itemFiles = uploadService.substrBusniessI(item.getId());
    			Category cate = categoryService.findById(item.getCategoryId());
    			if(cate!=null){
    				cate.setId(item.getId());
        			category.add(cate);
    			}
    			attachList.addAll(itemFiles);
    		}
        }
        //财务信息附件
        for(SupplierFinance fiance:listSupplierFinances){
        	   List<UploadFile> fileList = uploadService.findBybusinessId(fiance.getId(), Constant.SUPPLIER_SYS_KEY);
               attachList.addAll(fileList);
        }
        
        //生产证书信息附件
        for(SupplierCertPro proCert:listSupplierCertPros){
        	List<UploadFile> fileList = uploadService.findBybusinessId(proCert.getId(), Constant.SUPPLIER_SYS_KEY);
            attachList.addAll(fileList);
        }
        
        //工程信息主表附件
        
        //工程资质信息附件
        for(SupplierAptitute eng:listSupplierCertEngs){
        	List<UploadFile> fileList = uploadService.findBybusinessId(eng.getId(), Constant.SUPPLIER_SYS_KEY);
            attachList.addAll(fileList);
        }
        for(SupplierEngQua eng:listSupplierEngQuas){
        	List<UploadFile> fileList = uploadService.findBybusinessId(eng.getId(), Constant.SUPPLIER_SYS_KEY);
        	attachList.addAll(fileList);
        }
        
        //销售证书附件
        for(SupplierCertSell sell:listSupplierCertSells){
        	List<UploadFile> fileList = uploadService.findBybusinessId(sell.getId(), Constant.SUPPLIER_SYS_KEY);
            attachList.addAll(fileList);
        }
        //服务证书信息附件
        for(SupplierCertServe sell:listSupplierCertSes){
        	List<UploadFile> fileList = uploadService.findBybusinessId(sell.getId(), Constant.SUPPLIER_SYS_KEY);
            attachList.addAll(fileList);
        }
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getBackSupplierFile(),JSON.toJSONString(list));
            String basePath = FileUtils.attachExportPath(Constant.SUPPLIER_SYS_KEY);
            if (StringUtils.isNotBlank(basePath)){
                OperAttachment.writeFile(basePath, attachList);
                recordService.backupAttach(new Integer(attachList.size()).toString());
            }
        }
        recordService.commitSupplierRecord(new Integer(list.size()).toString(), new Date() );
	}

    /**
     *
     * Description: 按时间导出公示供应商
     *
     * @author Easong
     * @version 2017/7/9
     * @param startTime
     * @param endTime
     * @since JDK1.7
     */
    @Override
    public void selectSupByPublictyOfExport(String startTime, String endTime) {
        Map<String, Object> map=new HashMap<>();
        Map<String, Object> selectMap=new HashMap<>();
        selectMap.put("startTime", startTime);
        selectMap.put("endTime", endTime);
        selectMap.put("status", -3);
        List<Supplier> list = supplierMapper.selectSupByPublictyOfExport(selectMap);
        List<SupplierAuditFormBean> supplierAudits=new LinkedList<>();
        Supplier supplier = null;
        for(Supplier s:list){
            SupplierAuditFormBean saf = new SupplierAuditFormBean();
            supplier = new Supplier();
			/*saf.setSupplierId(s.getId());
			saf.setStatus(s.getStatus());
			saf.setAuditDate(s.getAuditDate());*/
            // 供应商ID
            supplier.setId(s.getId());
            supplier.setStatus(s.getStatus());
            // 审核时间
            supplier.setAuditDate(s.getAuditDate());
            // 审核人
            supplier.setAuditor(s.getAuditor());
            // 审核中状态
            supplier.setAuditTemporary(s.getAuditTemporary());
            // 将供应商基本信息导出
            saf.setSupplier(supplier);

            saf.setUser(getUser(s.getId()));
            map.put("supplierId", s.getId());
            List<SupplierAudit> sa = supplierAuditMapper.findByMap(map);
            saf.setSupplierAudits(sa);
            List<SupplierModify> supplierModifys = supplierModifyMapper.queryBySupplierId(s.getId());
            saf.setSupplierModify(supplierModifys);
            List<SupplierHistory> historys = supplierHistoryMapper.queryBySupplierId(s.getId());
            saf.setSupplierHistory(historys);
            List<SupplierAuditNot> supplierAuditNots = supplierAuditNotMapper.selectQueryBySupplierId(s.getId());
            saf.setSupplierAuditNot(supplierAuditNots);
            List<SupplierSignature> ss = supplierSignatureMapper.queryBySupplierId(s.getId());
            saf.setSupplierSignature(ss);
            // 查询审核意见
            selectMap.clear();
            selectMap.put("supplierId",s.getId());
            selectMap.put("flagTime",0);
            SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(selectMap);
            saf.setSupplierAuditOpinions(supplierAuditOpinion);
            supplierAudits.add(saf);

        }
        if (list != null && list.size() > 0){
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_PUBLICITY_SUPPLIER_FILENAME, 23),JSON.toJSONString(supplierAudits, SerializerFeature.WriteMapNullValue));
        }
        recordService.synchBidding(null, new Integer(list.size()).toString(), synchro.util.Constant.SYNCH_PUBLICITY_SUPPLIER, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.COMMIT_SYNCH_PUBLICITY_SUPPLIER);
    }

    /**
     * Description:查询注销供应商导出
     *
     * @param startTime
     * @param endTime
     * @author Easong
     * @version 2017/10/16
     * @since JDK1.7
     */
    @Override
    public void selectLogoutSupplierOfExport(String startTime, String endTime) {
        // 查询注销供应商
        Map<String, Object> map = new HashedMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("isDeleted", 1);
        List<User> users = userMapper.selectLogoutSupplier(map);
        // 将查询的数据封装
        //将数据写入文件
        if (!users.isEmpty()) {
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_LOGOUT_SUPPLIER_FILENAME, 31), JSON.toJSONString(users, SerializerFeature.WriteMapNullValue));
        }
        recordService.synchBidding(null, new Integer(users.size()).toString(), synchro.util.Constant.SYNCH_LOGOUT_SUPPLIER, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.EXPORT_SYNCH_LOGOUT_SUPPLIER);
    }
    
    
    /**
     * Description:查询供应商等级导出
     *
     * @param startTime
     * @param endTime
     * @author Easong
     * @version 2017/10/16
     * @since JDK1.7
     */
    @Override
    public void selectSupplierLevelOfExport(String startTime, String endTime) {
    	// 查询注销供应商
    	@SuppressWarnings("unchecked")
		Map<String, Object> map = new HashedMap();
    	map.put("startTime", startTime);
    	map.put("endTime", endTime);
    	
    	List<SupplierItemLevel> levels = supplierItemLevelMapper.selectByMapForExport(map);
    	
    	// 将查询的数据封装
    	//将数据写入文件
    	if (!levels.isEmpty()) {
    		FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SUPPLIER_LEVEL_FILENAME, 37), JSON.toJSONString(levels, SerializerFeature.WriteMapNullValue));
    	}
    	recordService.synchBidding(null, new Integer(levels.size()).toString(), synchro.util.Constant.DATE_SYNCH_SUPPLIER_LEVEL, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.SUPPLIER_LEVEL_COMMIT);
    }

    /**
     * 导入供应商等级
     */
	@Override
	public void importSupplierLevel(File file) {
		int num = 0;
        for (File file2 : file.listFiles()) {
            // 抽取结果信息
            if (file2.getName().contains(FileUtils.SUPPLIER_LEVEL_FILENAME)) {
                List<SupplierItemLevel> levelList = FileUtils.getBeans(file2, SupplierItemLevel.class);
                num += levelList == null ? 0 : levelList.size();
                for (SupplierItemLevel level : levelList) {
                	SupplierItemLevel selectById = supplierItemLevelMapper.selectById(level.getId());
                    if(selectById != null){
                    	supplierItemLevelMapper.updateByPrimaryKey(level);
                    }else{
                    	supplierItemLevelMapper.insert(level);
                    }
                }
            }
        }
        recordService.synchBidding(new Date(), num+"", synchro.util.Constant.DATE_SYNCH_SUPPLIER_LEVEL, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.SUPPLIER_LEVEL_COMMIT_IMPORT);
	}

    
    
}
