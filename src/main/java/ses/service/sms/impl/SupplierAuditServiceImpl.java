package ses.service.sms.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.constant.StaticVariables;
import common.service.UploadService;
import common.utils.DateUtils;
import common.utils.JdcgResult;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ses.dao.bms.CategoryQuaMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierAuditOpinionMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.SupplierModifyMapper;
import ses.dao.sms.SupplierStarsMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.dao.sms.SupplierTypeMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryQua;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierPublicity;
import ses.model.sms.SupplierStars;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierType;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.CategoryService;
import ses.service.bms.EngCategoryService;
import ses.service.bms.QualificationService;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.util.Constant;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * <p>Title:SupplierAuditServliceImpl </p>
 * <p>Description: 供应商审核实现接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:23
 */
@Service
public class SupplierAuditServiceImpl implements SupplierAuditService {
	
	/**
	 * 供应商信息
	 */
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
    private SupplierAptituteService supplierAptituteService;
	@Autowired
	private SupplierStarsMapper supplierStarsMapper;
	@Autowired
	private EngCategoryService engCategoryService;
	@Autowired
	private CategoryService categoryService;
	/**
	 * 供应商审核记录
	 */
	@Autowired
	private SupplierAuditMapper supplierAuditMapper;
	
	/**
	 * 财务信息
	 */
	@Autowired
	private SupplierFinanceMapper supplierFinanceMapper;
	
	/**
	 * 股东信息
	 */
	@Autowired
	private SupplierStockholderMapper supplierStockholderMapper;
	@Autowired
	private SupplierMatEngService supplierMatEngService;
	/**
	 * 所有供应商类型
	 */
	@Autowired
	private SupplierTypeMapper supplierTypeMapper;
	
	/**
	 * 物资生产型-资质证书信息
	 */
	@Autowired
	private SupplierCertProMapper supplierCertProMapper;
	
	/**
	 * 物资生产型专业信息
	 */
	@Autowired
	private SupplierMatProMapper supplierMatProMapper;
	
	/**
	 * 物资销售-资质证书信息
	 */
	@Autowired
	private SupplierCertSellMapper supplierCertSellMapper;
	
	/**
	 * 工程-资质证书信息
	 */
	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;
	
	/**
	 * 工程-资质资格信息
	 */
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	
	/**
	 * 工程-组织结构和人员
	 */
	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;
	
	/**
	 * 服务-资质证书信息
	 */
	@Autowired
	private SupplierCertServeMapper supplierCertSeMapper;
	
	/**
	 * 服务-组织结构和人员
	 */
	@Autowired
	private SupplierMatServeMapper supplierMatSeMapper;
	
	/**
	 * 勾选的供应商类型
	 */
	@Autowired
	SupplierTypeRelateMapper supplierTypeRelateMapper;
	
	/**
	 * 审核退回后供应商修改记录
	 */
	@Autowired
	SupplierModifyMapper supplierModifyMapper;
	@Autowired
	private CategoryQuaMapper categoryQuaMapper;
	@Autowired
	private QualificationService qualificationService;
	@Autowired
	private SupplierItemService supplierItemService;
	@Autowired
	private UploadService uploadService;
	
	// 注入供应商和供应商类别对应关系Mapper
	@Autowired
	private SupplierItemMapper supplierItemMapper;
	@Autowired
    private SupplierAuditOpinionMapper supplierAuditOpinionMapper;
	
	/**
	 * @Title: supplierList
	 * @author Xu Qing
	 * @date 2016-9-14 下午2:10:56  
	 * @Description: 供应商列表 ,可条件查询
	 * @param @return      
	 * @return List<Supplier>
	 */
	@Override
	public PageInfo<Supplier> supplierList(Supplier supplier) {
		/*if(page!=null){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		}*/
		Integer page = supplier.getPage();
		page = page == null ? 1 : page;
		Integer count = supplierMapper.getCount(supplier);
		if (count != null && count > 0) {
			PageInfo<Supplier> pageInfo = new PageInfo<>();
			Integer pageSize = PropUtil.getIntegerProperty("pageSize"); //获取每页条数
			double dCount = count.doubleValue();
			double dPageSize = pageSize.doubleValue();
			pageInfo.setTotal(count);// 设置总条数
			pageInfo.setPages((int)Math.ceil(dCount / dPageSize));// 总页数
			pageInfo.setStartRow((page - 1) * pageSize + 1); //开始条数
			pageInfo.setEndRow(page * pageSize);  //结束条数
			pageInfo.setPageNum(page);  //当前页
			supplier.setRows(pageSize);
			supplier.setPage(page);
			pageInfo.setList(supplierMapper.findSupplierAll(supplier));
			return pageInfo;
		}
		return null;
	}
	
	/**
	 * @see ses.service.sms.SupplierAuditService#getAuditSupplierList(ses.model.sms.Supplier, java.lang.Integer)
	 */
	@Override
    public List<Supplier> getAuditSupplierList(Supplier supplier, Integer page) {
	    PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
        return supplierMapper.findSupplierAll(supplier);
    }


    @Override
	public List<Supplier> querySupplier(Supplier supplier,Integer page) {
		if(page!=null){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		}
		return supplierMapper.querySupplier(supplier);
	}
	
	@Override
	public List<Supplier> querySupplierbytypeAndCategoryIds(Supplier supplier,Integer page) {
		/*SupplierStars sstart = new SupplierStars();
		sstart.setStatus(1);
        List<SupplierStars> listSs = supplierStarsMapper.findSupplierStars(sstart);
        
        if (supplier.getScore() != null && !"".equals(supplier.getScore())) {
            if (supplier.getScore() == 1) {
                supplier.setScoreStart(listSs.get(0).getOneStars() + "");
                supplier.setScoreEnd(listSs.get(0).getTwoStars() + "");
            }
            if (supplier.getScore() == 2) {
                supplier.setScoreStart(listSs.get(0).getTwoStars() + "");
                supplier.setScoreEnd(listSs.get(0).getThreeStars() + "");
            }
            if (supplier.getScore() == 3) {
                supplier.setScoreStart(listSs.get(0).getThreeStars() + "");
                supplier.setScoreEnd(listSs.get(0).getFourStars() + "");
            }
            if (supplier.getScore() == 4) {
                supplier.setScoreStart(listSs.get(0).getFourStars() + "");
                supplier.setScoreEnd(listSs.get(0).getFiveStars() + "");
            }
            if (supplier.getScore() == 5) {
                supplier.setScoreEnd(listSs.get(0).getFiveStars() + "");
            }
        }*/
        if(page!=null){
            PropertiesUtil config = new PropertiesUtil("config.properties");
            PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        }
		List<Supplier> listSupplier=supplierMapper.querySupplierbytypeAndCategoryIds(supplier);
		/*SupplierStars supplierStars = new SupplierStars();
		supplierStars.setStatus(1);
		List<SupplierStars> listSupplierStars = supplierStarsMapper.findSupplierStars(supplierStars);
		for (SupplierStars ss : listSupplierStars) {
			for (Supplier s : listSupplier) {
				Integer score = s.getScore();
				if (score == null || "".equals(score)) {
				    score = 0;
				}
				Integer oneStars = ss.getOneStars();
				Integer twoStars = ss.getTwoStars();
				Integer threeStars = ss.getThreeStars();
				Integer fourStars = ss.getFourStars();
				Integer fiveStars = ss.getFiveStars();
				if (score < oneStars) {
					s.setLevel("无级别");
				} else if (score < twoStars) {
					s.setLevel("一级");
				} else if (score < threeStars) {
					s.setLevel("二级");
				} else if (score < fourStars) {
					s.setLevel("三级");
				} else if (score < fiveStars) {
					s.setLevel("四级");
				} else {
					s.setLevel("五级");
				}
			}
		}*/
		return listSupplier;
	}
	
	@Override
	public List<Supplier> getAllSupplier(Supplier supplier,Integer page) {
		if(page!=null){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		}
		List<Supplier> listSupplier=supplierMapper.getAllSupplier(supplier);
		SupplierStars supplierStars = new SupplierStars();
		supplierStars.setStatus(1);
		List<SupplierStars> listSupplierStars = supplierStarsMapper.findSupplierStars(supplierStars);
		for (SupplierStars ss : listSupplierStars) {
			for (Supplier s : listSupplier) {
				Integer score = s.getScore();
				Integer oneStars = ss.getOneStars();
				Integer twoStars = ss.getTwoStars();
				Integer threeStars = ss.getThreeStars();
				Integer fourStars = ss.getFourStars();
				Integer fiveStars = ss.getFiveStars();
				if (score < oneStars) {
					s.setLevel("无级别");
				} else if (score < twoStars) {
					s.setLevel("一级");
				} else if (score < threeStars) {
					s.setLevel("二级");
				} else if (score < fourStars) {
					s.setLevel("三级");
				} else if (score < fiveStars) {
					s.setLevel("四级");
				} else {
					s.setLevel("五级");
				}
			}
		}
		return listSupplier;
	}

	/**
	 * @Title: supplierById
	 * @author Xu Qing
	 * @date 2016-9-14 下午3:43:26  
	 * @Description: 根据id查询供应商信息 
	 * @param @param id
	 * @param @return      
	 * @return Supplier
	 */
	@Override
	public Supplier supplierById(String id) {
		
		return supplierMapper.getSupplier(id);
	}
	
	/**
	 * @Title: supplierFinanceByid
	 * @author Xu Qing
	 * @date 2016-9-14 下午5:30:21  
	 * @Description: 根据供应商id查询财务信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierFinance>
	 */
	@Override
	public List<SupplierFinance> supplierFinanceBySupplierId(String supplierId) {
		
		return supplierFinanceMapper.findFinanceBySupplierId(supplierId);
	}
	
	/**
	 * @Title: ShareholderById
	 * @author Xu Qing
	 * @date 2016-9-18 上午9:51:00  
	 * @Description: 根据供应商id查询股东信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierStockholder>
	 */
	@Override
	public List<SupplierStockholder> ShareholderBySupplierId(String supplierId) {
		
		return supplierStockholderMapper.findStockholderBySupplierId(supplierId);
	}

	/**
	 * @Title: auditReasons
	 * @author Xu Qing
	 * @date 2016-9-18 下午5:51:55  
	 * @Description: 审核记录
	 * @param @param supplierAudit      
	 * @return void
	 */
	@Override
	public int auditReasons(SupplierAudit supplierAudit) {
		return supplierAuditMapper.insertSelective(supplierAudit);
		
	}

	/**
     * @Title: selectByPrimaryKey
     * @author Xu Qing
     * @date 2016-9-20 下午5:12:26  
     * @Description: 根据供应商id查询审核汇总 
     * @param @param id
     * @param @return      
     * @return List<SupplierAudit>
     */
	@Override
	public List<SupplierAudit> selectByPrimaryKey(SupplierAudit supplierAudit) {
		
		return supplierAuditMapper.selectByPrimaryKey(supplierAudit);
	}
	
	/**
     * @Title: updateStatus
     * @author Xu Qing
     * @date 2016-9-20 下午7:24:46  
     * @Description: 根据供应商ID更新审核状态 
     * @param @param supplierId      
     * @return void
     */
	@Override
	public void updateStatus(Supplier supplier) {
		supplierMapper.updateStatus(supplier);
		
	}
	
	/**
     * @Title: getCount
     * @author Xu Qing
     * @date 2016-9-21 上午10:14:27  
     * @Description:根据审核状态获取条数
     * @param @param supplier
     * @param @return      
     * @return Integer
     */
	@Override
	public Integer getCount(Supplier supplier) {
		
		return supplierMapper.getCount(supplier);
	}
	
	/**
     * @Title: findSupplierType
     * @author Xu Qing
     * @date 2016-9-23 下午5:44:18  
     * @Description: 查询所有供应商类型 
     * @param @return      
     * @return List<SupplierType>
     */
	@Override
	public List<SupplierType> findSupplierType() {
		
		return supplierTypeMapper.findSupplierType();
	}
	
	/**
     * @Title: findBySupplierId
     * @author Xu Qing
     * @date 2016-9-26 下午6:43:07  
     * @Description: 资质证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertPro>
     */
	@Override
	public List<SupplierCertPro> findBySupplierId(String supplierId) {
		
		return supplierCertProMapper.findBySupplierId(supplierId);
	}
	
	/**
     * @Title: findSupplierMatProBysupplierId
     * @author Xu Qing
     * @date 2016-9-26 下午8:09:19  
     * @Description: 物资生产型专业信息 
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierMatPro>
     */
	@Override
	public SupplierMatPro findSupplierMatProBysupplierId(String supplierId) {
		
		return supplierMatProMapper.getMatProBySupplierId(supplierId);
	}
	
	/**
     * @Title: findCertSellBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午2:11:49  
     * @Description: 物资销售-资质证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertSell>
     */
	@Override
	public List<SupplierCertSell> findCertSellBySupplierId(String supplierId) {

		return supplierCertSellMapper.findCertSellBySupplierId(supplierId);
	}
	
	/**
     * @Title: findCertEngBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午4:11:02  
     * @Description: 工程专业-资质资格证书信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierCertEng>
     */
	@Override
	public List<SupplierCertEng> findCertEngBySupplierId(String supplierId) {
		
		return supplierCertEngMapper.findCertEngBySupplierId(supplierId);
	}
	
	/**
     * @Title: findAptituteBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午5:11:45  
     * @Description: 供应商资质资格信息
     * @param @param supplierId
     * @param @return      
     * @return List<SupplierAptitute>
     */
	@Override
	public List<SupplierAptitute> findAptituteBySupplierId(String supplierId) {
		
		return supplierAptituteMapper.findAptituteBySupplierId(supplierId);
	}
	
	/**
     * @Title: findMatEngBySupplierId
     * @author Xu Qing
     * @date 2016-9-27 下午7:36:02  
     * @Description: 供应商组织机构和注册人员 
     * @param @param supplierId
     * @param @return      
     * @return SupplierMatEng
     */
	@Override
	public SupplierMatEng findMatEngBySupplierId(String supplierId) {
		
		return supplierMatEngMapper.getMatEngBySupplierId(supplierId);
	}
	
	/**
     * @Title: findCertSeBySupplierSupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午10:55:54  
     * @Description: 服务专业信息-资质证书 
     * @param @return      
     * @return List<SupplierCertSe>
     */
	@Override
	public List<SupplierCertServe> findCertSeBySupplierId(String supplierId) {
		
		return supplierCertSeMapper.findCertSeBySupplierId(supplierId);
	}
	
	/**
     * @Title: findMatSellBySupplierId
     * @author Xu Qing
     * @date 2016-9-28 上午11:32:26  
     * @Description: 供应商组织机构和人员 
     * @param @param supplierId
     * @param @return      
     * @return SupplierMatSell
     */
	@Override
	public SupplierMatServe findMatSeBySupplierId(String supplierId) {
		
		return supplierMatSeMapper.getMatSeBySupplierId(supplierId);
	}
	
	/**
     * @Title: updateStatusByid
     * @author Xu Qing
     * @date 2016-10-22 下午4:49:44  
     * @Description: 根据id更新状态 
     * @param @param supplierAudit      
     * @return void
     */
	@Override
	public void updateStatusById(SupplierAudit supplierAudit) {
		supplierAuditMapper.updateByPrimaryKeySelective(supplierAudit);
		
	}
	
	
	@Override
	public void updateSupplierInspectListById(Supplier supplier) {
		supplierMapper.updateSupplierInspectListById(supplier);
		
	}

	@Override
	public String findSupplierTypeNameBySupplierId(String supplierId) {
		Supplier supplier = supplierMapper.getSupplier(supplierId);
		List<SupplierTypeRelate> listSupplierTypeRelates = supplier.getListSupplierTypeRelates();
		String supplierTypeNames = "";
		for (int i = 0; i < listSupplierTypeRelates.size(); i++) {
			if (i > 0) {
				supplierTypeNames += ",";
			}
			supplierTypeNames += listSupplierTypeRelates.get(i).getSupplierTypeName();
		}
		//return supplierTypeRelateMapper.findSupplierTypeNameBySupplierId(supplierId);
		return supplierTypeNames;
	}
	
	public ResponseEntity<byte[]> downloadFile(String filePath,String fileName){
	   	 try {
				File file=new File(filePath+fileName);  
				    HttpHeaders headers = new HttpHeaders(); 
				   String downFileName=new String(fileName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
				    headers.setContentDispositionFormData("attachment",downFileName );   
				    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
				    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
				    return entity;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Supplier> querySupplierbyCategory(Supplier supplier,
			Integer page) {
		if(page!=null){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		}
		List<Supplier> listSupplier=supplierMapper.querySupplierbyCategory(supplier);
		SupplierStars supplierStars = new SupplierStars();
		supplierStars.setStatus(1);
		List<SupplierStars> listSupplierStars = supplierStarsMapper.findSupplierStars(supplierStars);
		for (SupplierStars ss : listSupplierStars) {
			for (Supplier s : listSupplier) {
				Integer score = s.getScore();
				Integer oneStars = ss.getOneStars();
				Integer twoStars = ss.getTwoStars();
				Integer threeStars = ss.getThreeStars();
				Integer fourStars = ss.getFourStars();
				Integer fiveStars = ss.getFiveStars();
				if (score < oneStars) {
					s.setLevel("无级别");
				} else if (score < twoStars) {
					s.setLevel("一级");
				} else if (score < threeStars) {
					s.setLevel("二级");
				} else if (score < fourStars) {
					s.setLevel("三级");
				} else if (score < fiveStars) {
					s.setLevel("四级");
				} else {
					s.setLevel("五级");
				}
			}
		}
		return listSupplier;
	}
	
	@Override
	public List<SupplierAudit> findReason(SupplierAudit supplierAudit) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		for (String str : supplierAudit.getAuditType().split(",")) {
			list.add(str);
		}
		param.put("list", list);
		param.put("supplierId", supplierAudit.getSupplierId());
		param.put("isDeleted", 0);
		return supplierAuditMapper.findByMap(param);
	}

	@Override
	public boolean deleteById(String[] ids) {
		for(int i = 0; i<ids.length; i++){
			supplierAuditMapper.deleteByPrimaryKey(ids[i]);
		}
		return true;
	}
	
	
	
	@Override
	public List<Supplier> selectAllSupplier(Supplier supplier,Integer page) {
		if(page!=null){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		}
		List<Supplier> listSupplier=supplierMapper.getAllSupplier(supplier);
		SupplierStars supplierStars = new SupplierStars();
		supplierStars.setStatus(1);
		return listSupplier;
	}

	
	/**
	 * 发售标书中的供应商
	 * @see ses.service.sms.SupplierAuditService#selectSaleTenderSupplier(ses.model.sms.Supplier, java.lang.Integer)
	 */
  @Override
  public List<Supplier> selectSaleTenderSupplier(Supplier supplier, Integer page) {
    if(page!=null){
      PropertiesUtil config = new PropertiesUtil("config.properties");
      PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
    }
    List<Supplier> listSupplier=supplierMapper.selectSaleTenderSupplier(supplier);
    return listSupplier;
  
  }
  
  /**
   * @Title: deleteBySupplierId
   * @author XuQing 
   * @date 2017-2-14 下午4:59:14  
   * @Description:删除记录
   * @param @param supplierId      
   * @return void
   */
	@Override
	public void deleteBySupplierId(String supplierId) {
		supplierAuditMapper.deleteBySupplierId(supplierId);
	}

	
	 /**
     * 
      * @Title: downloadFile
      * @author ShaoYangYang
      * @date 2017年4月1日 下午2:07:23  
      * @Description: TODO 文件下载
      * @param @param fileName 文件名
      * @param @param filePath 文件地址
      * @param @param downFileName 下载文件名
      * @param @return      
      * @return ResponseEntity<byte[]>
     */
	@Override
    public ResponseEntity<byte[]> downloadFile(String fileName,String filePath,String downFileName){
    	 try {
			File file=new File(filePath+"/"+fileName);  
			    HttpHeaders headers = new HttpHeaders(); 
			    headers.setContentDispositionFormData("attachment", downFileName);   
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
			    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK); 
			    file.delete();
			    return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    
    /**
     * @Title: updateIsDeleteBySupplierId
     * @author XuQing 
     * @date 2017-4-28 下午3:50:56  
     * @Description:软删除历史记录
     * @param @param supplierHistory      
     * @return void
     */
	@Override
	public void updateIsDeleteBySupplierId(SupplierAudit supplierAudit) {
		supplierAuditMapper.updateIsDeleteBySupplierId(supplierAudit);
		
	}
	/**
	 * 
	 * Description:类型判断
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-31
	 * @param type
	 * @return
	 */
	private String isType(Integer type){
		//type:4(工程) 3（销售） 2（生产）1（服务）
		String rut="";
		switch (type) {
			case 4 :
				rut="PROJECT";
				break;
			case 3:
				rut="SALES";
				break;
			case 2:
				rut="PRODUCT";
				break;
			case 1:
				rut="SERVICE";
				break;
		}
		return rut;
	}
	/**
	 * 
	 * Description: 组装封装 数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-24
	 * @param cateTree
	 * @return
	 */
	private List<Qualification> isQualificationsCateTree(SupplierCateTree cateTree,
			Integer type,String type_id,Integer syskey){
		List<Qualification> tempList=null;
		List<Qualification> qulist=new ArrayList<>();
		List<CategoryQua> quaList=null;
		//type:4(工程) 3（销售） 2（生产）1（服务）
		//专业资质 要求 有可能是末节节点 有可能是其他节点
		if(StringUtils.isNotBlank(cateTree.getFourthNodeID())){
			quaList= categoryQuaMapper.findListSupplier(cateTree.getFourthNodeID(), type);
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", cateTree.getItemsId());
			map.put("categoryId", cateTree.getFourthNodeID());
			map.put("type", isType(type));
			//根据第4节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			tempList=  pottingQualificationsDate(itemList, quaList, cateTree, type, type_id, syskey);
			qulist.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getThirdNodeID())){
			quaList= categoryQuaMapper.findListSupplier(cateTree.getThirdNodeID(), type);
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", cateTree.getItemsId());
			map.put("categoryId", cateTree.getThirdNodeID());
			map.put("type", isType(type));
			//根据第4节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			tempList=  pottingQualificationsDate(itemList, quaList, cateTree, type, type_id, syskey);
			qulist.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getSecondNodeID())){
			quaList= categoryQuaMapper.findListSupplier(cateTree.getSecondNodeID(), type);
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", cateTree.getItemsId());
			map.put("categoryId", cateTree.getSecondNodeID());
			map.put("type", isType(type));
			//根据第4节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			tempList=  pottingQualificationsDate(itemList, quaList, cateTree, type, type_id, syskey);
			qulist.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getFirstNodeID()) ){
			quaList= categoryQuaMapper.findListSupplier(cateTree.getFirstNodeID(), type);
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", cateTree.getItemsId());
			map.put("categoryId", cateTree.getFirstNodeID());
			map.put("type", isType(type));
			//根据第4节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			tempList=  pottingQualificationsDate(itemList, quaList, cateTree, type, type_id, syskey);
			qulist.addAll(tempList);
		}
		return qulist;
	}
	/**
	 * 
	 * Description:封装 不同级别类别 数据 专业资质要求
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-25
	 * @param cateTree
	 * @param supplierId
	 * @param categoryQuaList
	 * @param categoryId
	 * @return
	 */
	private List<Qualification> pottingQualificationsDate(List<SupplierItem> itemList,List<CategoryQua> quaList,
			SupplierCateTree cateTree,Integer type,String type_id,Integer syskey){
		List<Qualification> list=new ArrayList<>();
		//资质文件：物资生产/物资销售/服务  审核字段存储：目录三级节点ID关联的SupplierItem的ID
		Qualification qualification=null;
		if(null!=itemList && !itemList.isEmpty()){
			SupplierItem supplierItem=itemList.get(0);
			if(null!=quaList && !quaList.isEmpty()){
				for (CategoryQua categoryQua : quaList) {
					qualification= qualificationService.getQualification(categoryQua.getQuaId());
					if(null!=/*temp=uploadService.countFileByBusinessId(supplierItem.getId()+categoryQua.getId(), type_id, syskey);*/qualification){

						//资质文件：物资生产/物资销售/服务  审核字段存储：目录三级节点ID关联的SupplierItem的ID
						qualification.setSupplierItemId(supplierItem.getId());
					    qualification.setFlag(supplierItem.getId()+categoryQua.getId());
					    //type:4(工程) 3（销售） 2（生产）1（服务）工程不在该方法内
						//审核记录
					    if(3==type){
				    	//封装 物资销售 记录 资质文件
					    	qualification.setAuditCount(countData(cateTree.getItemsId(), supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_SALES_PAGE));
						 }else if(2==type){
						   	//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
						    qualification.setAuditCount(countData(cateTree.getItemsId(), supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE));
						  }else if(1==type){
						   	//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
						   qualification.setAuditCount(countData(cateTree.getItemsId(), supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE));
						 }
						   list.add(qualification);
					}
				}
			}
		}
		return list;
	}
	@Override
	public List<Qualification> showQualifications(SupplierCateTree cateTree,Integer type,String type_id,Integer syskey) {
		return isQualificationsCateTree(cateTree, type, type_id, syskey);
	}
	/**
	 * 
	 * Description:私有 封装 数据
	 *  产品目录：审核字段存储：目录末级节点ID
	 * 资质文件：--物资生产/物资销售/服务
     * 审核字段存储：三级节点ID关联的SupplierItem的ID
     *    --工程
     * 审核字段存储：末级节点ID关联的SupplierItem的ID
     * 销售合同：
     * --物资生产/物资销售/服务
     * 审核字段存储：末级节点ID关联的SupplierItem的ID_附件typeId
	 * @author YangHongLiang
	 * @version 2017-6-30
	 * @param catetree
	 * @return
	 */
	public SupplierCateTree cateTreePotting(SupplierCateTree cateTree,String supplierId){
		//封装 目录 物资生产 是否有审核记录数据  如果是其他的 类型 也是该字段存储
		// 审核字段存储：目录末级节点ID
		cateTree.setIsItemsProductPageAudit(countData(supplierId, cateTree.getItemsId(), ses.util.Constant.ITMES_PRODUCT_PAGE));
		//封装 目录 物资销售 是否有审核记录数据   审核字段存储：目录末级节点ID
		cateTree.setIsItemsSalesPageAudit(countData(supplierId, cateTree.getItemsId(), ses.util.Constant.ITEMS_SALES_PAGE));

		//资质文件：物资生产/物资销售/服务  审核字段存储：目录三级节点ID关联的SupplierItem的ID
		//--工程 审核字段存储：目录末级节点ID关联的SupplierItem的ID
		//销售合同：--物资生产/物资销售/服务
		//审核字段存储：目录末级节点ID关联的SupplierItem的ID_附件typeId
		 if("工程".equals(cateTree.getRootNode())) {
			//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
			//cateTree.setIsAptitudeProductPageAudit(countData(supplierId, cateTree.getSupplierItemId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE));
			//封装 物资销售 记录 资质文件
			//cateTree.setIsAptitudeSalesPageAudit(countData(supplierId, cateTree.getSupplierItemId(), ses.util.Constant.APTITUDE_SALES_PAGE));
		 }else{
			/*Map<String, Object> map=new HashMap<>();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getSecondNodeID());
			//根据第三节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			String supplierItemId="0";
			if(null != itemList && !itemList.isEmpty()){
				supplierItemId=itemList.get(0).getId();
			}
			//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
			cateTree.setIsAptitudeProductPageAudit(countData(supplierId, supplierItemId, ses.util.Constant.APTITUDE_PRODUCT_PAGE));
			//封装 物资销售 记录 资质文件
			cateTree.setIsAptitudeSalesPageAudit(countData(supplierId, supplierItemId, ses.util.Constant.APTITUDE_SALES_PAGE));*/
			//封装 物资生产 销售合同文件  如果是其他的 类型 也是该字段存储
			cateTree.setIsContractProductPageAudit(countAptitudeProuct(supplierId, cateTree.getSupplierItemId()));
			//封装 物资销售 记录 销售合同
			cateTree.setIsContractSalesPageAudit(countAptitudeSales(supplierId, cateTree.getSupplierItemId()));
		 }
	return cateTree;
	}
	/**
	 * 
	 * Description:封装 工程 专业资质要求 数据审核
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-31
	 * @param cateTree
	 * @param type
	 * @return
	 */
	private List<SupplierItem> getProject(SupplierCateTree cateTree,Integer type,String supplierId){
		List<SupplierItem> itemsList=new ArrayList<>();
		List<SupplierItem> tempList=null;
		Map<String, Object> map=new HashMap<>();
		if(StringUtils.isNotBlank(cateTree.getFourthNodeID())){
			//根据末节目录节点 id) 获取目录中间表id  获取文件的business_id
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getFourthNodeID());
			map.put("type", isType(type));
			tempList=supplierItemMapper.findByMapByNull(map);
			itemsList.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getThirdNodeID())){
			map.clear();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getThirdNodeID());
			map.put("type", isType(type));
			tempList=supplierItemMapper.findByMapByNull(map);
			itemsList.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getSecondNodeID())){
			map.clear();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getSecondNodeID());
			map.put("type", isType(type));
			tempList=supplierItemMapper.findByMapByNull(map);
			itemsList.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getFirstNodeID()) ){
			map.clear();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getFirstNodeID());
			map.put("type", isType(type));
			tempList=supplierItemMapper.findByMapByNull(map);
			itemsList.addAll(tempList);
		}
		return itemsList;
	}
	@Override
	public List<SupplierCateTree> showProject(SupplierCateTree cateTree,
			Integer type, String type_id, Integer syskey) {
		List<SupplierCateTree> cateList=new ArrayList<>();
		List<SupplierItem> itemsList=getProject(cateTree, type,cateTree.getSupplierItemId());//supplierItemService.findByMap(map);
		//--工程 审核字段存储：目录末级节点ID关联的SupplierItem的ID
		if(null!=itemsList && !itemsList.isEmpty()){
			for (SupplierItem supplierItem : itemsList) {
				SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierItem.getSupplierId());
			cateTree.setSupplierItemId(supplierItem.getId());
			if(null !=  matEng || StringUtils.isNotBlank(supplierItem.getCertCode()) || StringUtils.isNotBlank(supplierItem.getProfessType())){
				List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(null,matEng.getId(), supplierItem.getCertCode(), supplierItem.getProfessType());
				if(certEng != null && certEng.size() > 0) {
					cateTree.setFileId(certEng.get(0).getId());
					Qualification qua= qualificationService.getQualification(certEng.get(0).getCertType());
					if(null != qua){
						certEng.get(0).setCertType(qua.getName());
					}else{
						certEng.get(0).setCertType("");
					}
					DictionaryData data=DictionaryDataUtil.findById(certEng.get(0).getAptituteLevel());
					if(null != data){
						certEng.get(0).setAptituteLevel(data.getName());
					}else{
						certEng.get(0).setAptituteLevel("");
					}
					cateTree.setSupplierAptitute(certEng.get(0));
					cateList.add(cateTree);
				   }
			    }
			}
		}
		return cateList;
	}

	@Override
	public int countByPrimaryKey(SupplierAudit audit) {
		return supplierAuditMapper.countByPrimaryKey(audit);
	}

	/**
	 * 
	 * Description:修改公示状态
	 * 
	 * @author Easong
	 * @version 2017年6月26日
	 * @param ids
	 * @return
	 */
	@Override
	public JdcgResult updatePublicityStatus(String[] ids) {
		if(ids != null){
			Supplier supplier = null;
			for (int i = 0; i < ids.length; i++) {
				supplier = new Supplier();
				supplier.setId(ids[i]);
				// 设置公示状态
				supplier.setStatus(-3);
				supplier.setUpdatedAt(new Date());
				supplierMapper.updateStatus(supplier);
			}
		}
		return JdcgResult.ok();
	}

	/**
	 * 
	 * Description:查询公示供应商，公示7天后自动入库
	 * 
	 * @author Easong
	 * @version 2017年6月26日
	 */
	@Override
	public void handlerPublictySup() {
		// 获取当前时间
	    Date nowDate = new Date();
	    // 查询所有公示供应商
	    String nowDateString = DateUtils.getDateOfFormat(nowDate);
	    List<Supplier> list = supplierMapper.selectSupByPublicty();
	    if(list != null && !list.isEmpty()){
	        for (Supplier supplier : list) {
	            // 将公示7天的拟入库供应商入库 
	            // 获取七天后的今天
	            String afterDateString = DateUtils.getDateOfFormat(DateUtils.addDayDate(supplier.getAuditDate(), 7));
	            if(nowDateString.equals(afterDateString)){
	                // 审核通过，自动入库
	                supplier.setStatus(1);
	                // 修改
	                supplierMapper.updateStatus(supplier);
	            }
	        }
         }
	}

	/**
	 * 
	 * Description:供应商公示列表
	 * 
	 * @author Easong
	 * @version 2017年6月28日
	 * @param map
	 * @return
	 */
	@Override
	public List<SupplierPublicity> selectSupByPublictyList(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),Integer.parseInt(config.getString("pageSize")));
		// 查询公示供应商列表
		SupplierPublicity supplierPublicityQuery = (SupplierPublicity) map.get("supplierPublicity");
		List<SupplierPublicity> list = supplierMapper.selectSupByPublictyList(supplierPublicityQuery);
		if(list != null && !list.isEmpty()){
			// 定义审核意见查询条件
			Map<String, Object> selectMap = new HashedMap();
			// 封装供应商类型
			StringBuffer sb = new StringBuffer(); 
			for (SupplierPublicity supplierPublicity : list) {
				// 查询供应商类型
				List<SupplierTypeRelate> supplierTypes = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplierPublicity.getId());
				if(supplierTypes != null && !supplierTypes.isEmpty()){
					for (SupplierTypeRelate supplierType : supplierTypes) {
						// 封装注册供应商类型
						DictionaryData dict = DictionaryDataUtil.get(supplierType.getSupplierTypeId());
						if(dict != null) {
							sb.append(dict.getName()).append(StaticVariables.COMMA_SPLLIT);
						}
					}
					supplierPublicity.setSupplierTypeNames(sb.toString().substring(0, sb.toString().length() - 1));
					// 清空sb
					sb.delete(0, sb.toString().length());
				}
				// 查询选择和未通过的产品类别
				//selectChooseOrNoPassCateOfDB(supplierPublicity);
				// 查询审核意见
				selectMap.put("supplierId",supplierPublicity.getId());
				selectMap.put("flagTime",0);
				SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(selectMap);
				if(supplierAuditOpinion != null && StringUtils.isNotEmpty(supplierAuditOpinion.getOpinion())){
					supplierPublicity.setAuditOpinion(supplierAuditOpinion.getOpinion());
				}else {
				    // 没意见设置为""
                    supplierPublicity.setAuditOpinion("");
                }
			}
		}
		return list;
	}

    /**
     *
     * Description: 查询选择和未通过的产品类别
     *
     * @author Easong
     * @version 2017/7/12
     * @param [supplierPublicity]
     * @since JDK1.7
     */
    public SupplierPublicity selectChooseOrNoPassCateOfDB(SupplierPublicity supplierPublicity){
        String supplierId = supplierPublicity.getId();
        int total = 0;
        int noTotal = 0;
        int passTotal=0;
        List<SupplierItem> itemList = null;
        List<SupplierAudit> supplierAudits = new ArrayList<>();
        // 不通过的
        List<SupplierAudit> supplierAuditsTotal = new ArrayList<>();
        List<SupplierItem> sales = null;
        // 封装查询map集合
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("supplierId", supplierPublicity.getId());
        selectMap.put("auditType", ses.util.Constant.SUPPLIER_CATE_INFO_ITEM_FLAG);
        List<SupplierAudit> supNoPassType = supplierAuditMapper.selectBySupIdAndType(selectMap);
        List<String> list  =new ArrayList<>();
        if(supNoPassType != null && !supNoPassType.isEmpty()){
            for (SupplierAudit supAudit : supNoPassType){
                list.add(supAudit.getType());
            }
        }
        String SALES_TEMP="",PRODUCT_TEMP="",SERVICE_TEMP="",PROJECT_TEMP="";
        if(!list.isEmpty()){
            for (String s : list) {
                if ("SALES".equals(s)) {
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("type", "SALES");
                    itemList = supplierItemMapper.selectCountBySupTypeList(selectMap);
                    if (itemList != null) {
                        total += itemList.size();
                        noTotal += itemList.size();
                    }
                    SALES_TEMP="SALES";
                }

                if("PRODUCT".equals(s)){
                    // 封装查询map集合
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("type", "PRODUCT");
                    itemList = supplierItemMapper.selectCountBySupTypeList(selectMap);
                    if(itemList != null && !itemList .isEmpty()){
                        total+=itemList.size();
                        noTotal+=itemList.size();
                    }
                    PRODUCT_TEMP="PRODUCT";
                }

                if("SERVICE".equals(s)){
                    // 封装查询map集合
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("type", "SERVICE");
                    itemList = supplierItemMapper.selectCountBySupTypeList(selectMap);
                    if(itemList != null && !itemList.isEmpty()){
                        total+=itemList.size();
                        noTotal+=itemList.size();
                    }
                    SERVICE_TEMP="SERVICE";
                }

                if("PROJECT".equals(s)){
                    // 封装查询map集合
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("type", "PROJECT");
                    itemList = supplierItemMapper.selectCountBySupTypeList(selectMap);
                    if(itemList != null && !itemList.isEmpty()){
                        total+=itemList.size();
                        noTotal+=itemList.size();
                    }
                    PROJECT_TEMP="PROJECT";
                }
            }
        }

        if(!"SALES".equals(SALES_TEMP)){
            selectMap.clear();
            selectMap.put("supplierId", supplierId);
            selectMap.put("type", "SALES");
            sales = supplierItemMapper.selectCountBySupTypeList(selectMap);
            if(sales != null && !sales.isEmpty()){
                for (SupplierItem supItem :sales){
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("auditField", supItem.getCategoryId());
                    selectMap.put("auditType", "items_sales_page");
                    supplierAudits = supplierAuditMapper.selectBasicInfoAuditItemSingal(selectMap);
                    supplierAuditsTotal.addAll(supplierAudits);
                }
                passTotal += sales.size()- supplierAuditsTotal.size();
                total += sales.size();
                noTotal +=supplierAuditsTotal.size();
            }
        }

        if(!PRODUCT_TEMP.equals("PRODUCT")) {
            // 封装查询map集合
            selectMap.clear();
            selectMap.put("supplierId", supplierId);
            selectMap.put("type", "PRODUCT");
            sales = supplierItemMapper.selectCountBySupTypeList(selectMap);
            supplierAudits.clear();
            if (sales != null && !sales.isEmpty()) {
                supplierAuditsTotal.clear();
                for (SupplierItem supItem : sales) {
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("auditField", supItem.getCategoryId());
                    selectMap.put("auditType", "items_product_page");
                    supplierAudits = supplierAuditMapper.selectBasicInfoAuditItemSingal(selectMap);
                    supplierAuditsTotal.addAll(supplierAudits);
                }
				passTotal += sales.size() - supplierAuditsTotal.size();
				total += sales.size();
				noTotal += supplierAuditsTotal.size();
            }
        }
        if(!SERVICE_TEMP.equals("SERVICE")) {
            // 封装查询map集合
            selectMap.clear();
            selectMap.put("supplierId", supplierId);
            selectMap.put("type", "SERVICE");
            sales = supplierItemMapper.selectCountBySupTypeList(selectMap);
            supplierAudits.clear();
            if (sales != null && !sales.isEmpty()) {
                supplierAuditsTotal.clear();
                for (SupplierItem supItem : sales) {
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("auditField", supItem.getCategoryId());
                    selectMap.put("auditType", "items_product_page");
                    supplierAudits = supplierAuditMapper.selectBasicInfoAuditItemSingal(selectMap);
                    supplierAuditsTotal.addAll(supplierAudits);
                }
				passTotal += sales.size() - supplierAuditsTotal.size();
				total += sales.size();
				noTotal += supplierAuditsTotal.size();
            }
        }

        if(!PROJECT_TEMP.equals("PROJECT")) {
            selectMap.clear();
            selectMap.put("supplierId", supplierId);
            selectMap.put("type", "PROJECT");
            sales = supplierItemMapper.selectCountBySupTypeList(selectMap);
            supplierAudits.clear();
            if (sales != null && !sales.isEmpty()) {
                supplierAuditsTotal.clear();
                for (SupplierItem supItem : sales) {
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("auditField", supItem.getCategoryId());
                    selectMap.put("auditType", "items_product_page");
                    supplierAudits = supplierAuditMapper.selectBasicInfoAuditItemSingal(selectMap);
                    supplierAuditsTotal.addAll(supplierAudits);
                }
				passTotal += sales.size() - supplierAuditsTotal.size();
				total += sales.size();
				noTotal += supplierAuditsTotal.size();
            }
        }
        // 获取选择的
        supplierPublicity.setPassCateCount(total);
        // 通过的
        supplierPublicity.setNoPassCateCount(noTotal);
        return supplierPublicity;
    }

	@Override
	public List<SupplierCateTree> showContractData(String itemId,String supplierId,String supplierItemId) {
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
		SupplierCateTree cateTree=null;
		// 递归获取所有父节点
		List < Category > parentNodeList = categoryService.getAllParentNode(itemId);
		// 加入根节点 物资
		cateTree=categoryService.addNode(parentNodeList);
		SupplierItem item = supplierItemService.selectByPrimaryKey(supplierItemId);
		String type=item.getSupplierTypeRelateId();
		String typeName = "";
		if("PRODUCT".equals(type)) {
			typeName = "生产";
		} else if("SALES".equals(type)) {
			typeName = "销售";
		}
		List < SupplierCateTree > allTreeList = new ArrayList <> ();
		cateTree.setOneContract(id1);
		cateTree.setTwoContract(id2);
		cateTree.setThreeContract(id3);
		cateTree.setOneBil(id4);
		cateTree.setTwoBil(id5);
		cateTree.setThreeBil(id6);
		cateTree.setRootNode(cateTree.getRootNode() == null ? "" : cateTree.getRootNode());
		cateTree.setFirstNode(cateTree.getFirstNode() == null ? "" : cateTree.getFirstNode());
		cateTree.setSecondNode(cateTree.getSecondNode() == null ? "" : cateTree.getSecondNode());
		cateTree.setThirdNode(cateTree.getThirdNode() == null ? "" : cateTree.getThirdNode());
		cateTree.setFourthNode(cateTree.getFourthNode() == null ? "" : cateTree.getFourthNode());
		cateTree.setSupplierItemId(supplierItemId);
		cateTree.setRootNode(cateTree.getRootNode() + typeName);
		if("PRODUCT".equals(type)) {
			typeName = ses.util.Constant.CONTRACT_PRODUCT_PAGE;
		} else if("SALES".equals(type)) {
			typeName = ses.util.Constant.CONTRACT_SALES_PAGE;
		}else{
			typeName = ses.util.Constant.CONTRACT_PRODUCT_PAGE;
		}
		//封装 合同 是否有审核 记录数据
		//* 销售合同：--物资生产/物资销售/服务  审核字段存储：目录末级节点ID关联的SupplierItem的ID_附件typeId
		// 合同 1 
		int count=countData(supplierId, cateTree.getSupplierItemId()+"_"+id1, typeName);
		cateTree.setIsAptitudeProductPageAudit(count);
		// 合同 2 
		count=countData(supplierId, cateTree.getSupplierItemId()+"_"+id2, typeName);
		cateTree.setIsAptitudeSalesPageAudit(count);
		// 合同 3 
		count=countData(supplierId, cateTree.getSupplierItemId()+"_"+id3, typeName);
		cateTree.setIsContractProductPageAudit(count);
		// 账单 4 
		count=countData(supplierId, cateTree.getSupplierItemId()+"_"+id4, typeName);
		cateTree.setIsContractSalesPageAudit(count);
		// 账单5 
		count=countData(supplierId, cateTree.getSupplierItemId()+"_"+id5, typeName);
		cateTree.setIsItemsProductPageAudit(count);
		// 账单 6 
		count=countData(supplierId, cateTree.getSupplierItemId()+"_"+id6, typeName);
		cateTree.setIsItemsSalesPageAudit(count);
		allTreeList.add(cateTree);		
		return allTreeList;
	}

	/**
	 *
	 * Description:查询选择和未通过的产品类别
	 *
	 * @author Easong
	 * @version 2017/7/12
	 * @param [supplierPublicity]
	 * @since JDK1.7
	 */
	@Override
	public SupplierPublicity selectChooseOrNoPassCate(SupplierPublicity supplierPublicity) {
        return selectChooseOrNoPassCateOfDB(supplierPublicity);
	}

	@Override
	public JdcgResult selectAndVertifyAuditItem(String supplierId) {
		/**
		 *
		 * Description:审核前判断是否有通过项和未通过项--是否符合通过要求
		 *
		 * @author Easong
		 * @version 2017/7/13
		 * @param [supplierId]
		 * @since JDK1.7
		 */
		// 判断审核意见是否填写
        /*Map<String, Object> selectMap = new HashedMap();
        selectMap.put("supplierId",supplierId);
        selectMap.put("flagTime",0);
        SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(selectMap);
        if(supplierAuditOpinion == null || (supplierAuditOpinion != null && StringUtils.isEmpty(supplierAuditOpinion.getOpinion()))){
            return JdcgResult.build(500, "审核意见不能为空");
        }*/

        // 判断基本信息+财务信息+股东信息
        Map<String, Object> map = new HashedMap();
        map.put("supplierId",supplierId);
        map.put("auditType", Constant.SUPPLIER_BASIC_INFO_ITEM_FLAG);
        Integer count;
        // 定义选择类型数量
        Integer selectCount;
        count = supplierAuditMapper.selectBasicInfoAuditItem(map);
        if(count != null && count > 0){
            return JdcgResult.build(500, "基本、财务、股东信息中有不通过项");
        }
        // 判断供应商类型和产品类别分别不能有全不通过项
        // 获取供应商选择品目的类型
        List<String> supplierTypeBySupplierIdList = supplierItemMapper.findSupplierTypeBySupplierId(supplierId);
        // 获取供应商不通过品目的类型
        map.put("auditType", Constant.SUPPLIER_CATE_INFO_ITEM_FLAG);
        count = supplierAuditMapper.selectBasicInfoAuditItem(map);
        if(supplierTypeBySupplierIdList != null && !supplierTypeBySupplierIdList.isEmpty()){
            selectCount = supplierTypeBySupplierIdList.size();
            if(count != null && (selectCount - count) <= 0){
                return JdcgResult.build(500, "类型不能全部为不通过项");
            }
        }

        // 判断产品类别
        SupplierPublicity supplierPublicity = new SupplierPublicity();
        supplierPublicity.setId(supplierId);
        SupplierPublicity supplierPublicityAfter = this.selectChooseOrNoPassCateOfDB(supplierPublicity);
        // 获取选择的产品类别数量
        selectCount = supplierPublicityAfter.getPassCateCount();
        count = supplierPublicityAfter.getNoPassCateCount();
        if(count != null && selectCount != null && (selectCount - count) <= 0){
            return JdcgResult.build(500, "产品类别不能全部为不通过项");
        }
        return JdcgResult.ok();
	}

	@Override
	public JdcgResult selectAuditNoPassItemCount(String supplierId) {
	    /**
	     * @deprecated:
	     *
	     * @Author:Easong
	     * @Date:Created in 2017/7/22
	     * @param: [supplierId]
	     * @return: common.utils.JdcgResult
	     *
	     */
	    Map<String, Object> map = new HashedMap();
	    map.put("supplierId", supplierId);
        Integer auditNoPassCount = supplierAuditMapper.selectBasicInfoAuditItem(map);
	    if(auditNoPassCount != null && auditNoPassCount == 0){
	        return JdcgResult.build(500, "没有审核不通过项");
        }
        return JdcgResult.ok();
	}

    @Override
    public JdcgResult vertifyOpinion(String supplierId) {
	    /**
	     * @deprecated:判断审核意见
	     *
	     * @Author:Easong
	     * @Date:Created in 2017/7/23
	     * @param: [supplierId]
	     * @return: common.utils.JdcgResult
	     *
	     */
        // 判断审核意见是否填写
        Map<String, Object> selectMap = new HashedMap();
        selectMap.put("supplierId",supplierId);
        selectMap.put("flagTime",0);
        SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(selectMap);
        if(supplierAuditOpinion == null || (supplierAuditOpinion != null && StringUtils.isEmpty(supplierAuditOpinion.getOpinion()))){
            return JdcgResult.build(500, "审核意见不能为空");
        }
        return JdcgResult.ok();
    }

    /**
	 * 
	 * Description:封装 合计 销售合同 物资 生产
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-6
	 * @param supplierId
	 * @param auditField
	 * @return
	 */
	private Integer countAptitudeProuct(String supplierId, String auditField){
		int rut=0;
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
		// 合同 1 物资生产
		int count=countData(supplierId, auditField+"_"+id1, ses.util.Constant.CONTRACT_PRODUCT_PAGE);
		rut=rut+count;
		// 合同 2 物资生产
		count=countData(supplierId, auditField+"_"+id2, ses.util.Constant.CONTRACT_PRODUCT_PAGE);
		rut=rut+count;
		// 合同 3 物资生产
		count=countData(supplierId, auditField+"_"+id3, ses.util.Constant.CONTRACT_PRODUCT_PAGE);
		rut=rut+count;
		// 账单 4 物资生产
		count=countData(supplierId, auditField+"_"+id4, ses.util.Constant.CONTRACT_PRODUCT_PAGE);
		rut=rut+count;
		// 账单5 物资生产
		count=countData(supplierId, auditField+"_"+id5, ses.util.Constant.CONTRACT_PRODUCT_PAGE);
		rut=rut+count;
		// 账单 6 物资生产
		count=countData(supplierId, auditField+"_"+id6, ses.util.Constant.CONTRACT_PRODUCT_PAGE);
		rut=rut+count;
		return rut;
	}
	/**
	 * 
	 * Description:封装 合计 销售合同 物资 销售
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-6
	 * @param supplierId
	 * @param auditField
	 * @return
	 */
	private Integer countAptitudeSales(String supplierId, String auditField){
		int rut=0;
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
		// 合同 1 物资销售
		int count=countData(supplierId, auditField+"_"+id1, ses.util.Constant.CONTRACT_SALES_PAGE);
		rut=rut+count;
		// 合同 2 物资销售
		count=countData(supplierId, auditField+"_"+id2, ses.util.Constant.CONTRACT_SALES_PAGE);
		rut=rut+count;
		// 合同 3 物资销售
		count=countData(supplierId, auditField+"_"+id3, ses.util.Constant.CONTRACT_SALES_PAGE);
		rut=rut+count;
		// 账单 4 物资销售
		count=countData(supplierId, auditField+"_"+id4, ses.util.Constant.CONTRACT_SALES_PAGE);
		rut=rut+count;
		// 账单5 物资销售
		count=countData(supplierId, auditField+"_"+id5, ses.util.Constant.CONTRACT_SALES_PAGE);
		rut=rut+count;
		// 账单 6 物资销售
		count=countData(supplierId, auditField+"_"+id6, ses.util.Constant.CONTRACT_SALES_PAGE);
		rut=rut+count;
		return rut;
	}
	/**
	 * 
	 * Description:封装私有  方法 简化 查询审核 记录
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-6
	 * @param supplierId
	 * @param auditField
	 * @param auditType
	 * @return
	 */
	private Integer countData(String supplierId, String auditField,String auditType ){
		SupplierAudit audit=new SupplierAudit();
		audit.setSupplierId(supplierId);
		audit.setAuditField(auditField);
		audit.setAuditType(auditType);
		return countByPrimaryKey(audit);
	}
	@Override
	public SupplierCateTree countEngCategoyrId(SupplierCateTree cateTree, String supplierId) {
		long product=0,sales=0,temp=0;
		String ids="";
		/*SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierId);
		String type_id=DictionaryDataUtil.getId(ses.util.Constant.SUPPLIER_ENG_CERT);
		List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(null,matEng.getId(), cateTree.getCertCode(), cateTree.getProName());
        if(certEng != null && !certEng.isEmpty()) {
		  rut=rut+uploadService.countFileByBusinessId(certEng.get(0).getId(), type_id, common.constant.Constant.SUPPLIER_SYS_KEY);
        }*/
        List<SupplierItem> itemsList=getProject(cateTree, 4,supplierId);
		//--工程 审核字段存储：目录末级节点ID关联的SupplierItem的ID
		if(null!=itemsList && !itemsList.isEmpty()){
			for (SupplierItem supplierItem : itemsList) {
				SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierItem.getSupplierId());
			if(null !=  matEng || StringUtils.isNotBlank(supplierItem.getCertCode()) || StringUtils.isNotBlank(supplierItem.getProfessType())){
				List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(null,matEng.getId(), supplierItem.getCertCode(), supplierItem.getProfessType());
				if(certEng != null && !certEng.isEmpty()) {
					ids=ids+supplierItem.getCategoryId()+",";
					/*rut=rut+uploadService.countFileByBusinessId(certEng.get(0).getId(), type_id, common.constant.Constant.SUPPLIER_SYS_KEY);
					if(rut>0){*/
						temp=countData(supplierId, supplierItem.getId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE);
			        		if(temp>0){
			        			product+=temp;
			        		}
			        		temp=countData(supplierId, supplierItem.getId(), ses.util.Constant.APTITUDE_SALES_PAGE);
			        		if(temp>0){
			        			sales+=temp;
			        		}
					  }
				/*}*/
				
			}
			}
		}
        
        //封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
		cateTree.setIsAptitudeProductPageAudit(product);
		//封装 物资销售 记录 资质文件
		cateTree.setIsAptitudeSalesPageAudit(sales);
		//资质标记的 id
		cateTree.setAptitudeId(ids);
		return cateTree;
	}
	/**
	 * 
	 * Description: 组装封装 数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-24
	 * @param cateTree
	 * @return
	 */
	private SupplierCateTree isCateTree(SupplierCateTree cateTree, String supplierId,String supplierType){
		long productCount=0,salesCount=0;
		//专业资质 要求 有可能是末节节点 有可能是其他节点
		if(StringUtils.isNotBlank(cateTree.getFourthNodeID())){
			cateTree= pottingDate(cateTree,supplierId,categoryQuaMapper.findList(cateTree.getFourthNodeID()),cateTree.getFourthNodeID(),supplierType);
			if(cateTree.getIsAptitudeProductPageAudit() >0 || cateTree.getIsAptitudeSalesPageAudit()>0 || cateTree.getFileCount()>0){
				//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
				productCount=productCount+cateTree.getIsAptitudeProductPageAudit();
				//封装 物资销售 记录 资质文件
				salesCount=salesCount+cateTree.getIsAptitudeSalesPageAudit();
			}
		}
		if(StringUtils.isNotBlank(cateTree.getThirdNodeID())){
			//如果末节点 为空 或者  查询时空
			cateTree= pottingDate(cateTree,supplierId,categoryQuaMapper.findList(cateTree.getThirdNodeID()),cateTree.getThirdNodeID(),supplierType);
			if(cateTree.getIsAptitudeProductPageAudit() >0 || cateTree.getIsAptitudeSalesPageAudit()>0 || cateTree.getFileCount()>0){
			//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
			productCount=productCount+cateTree.getIsAptitudeProductPageAudit();
			//封装 物资销售 记录 资质文件
			salesCount=salesCount+cateTree.getIsAptitudeSalesPageAudit();
			}
		}
		if(StringUtils.isNotBlank(cateTree.getSecondNodeID())){
			cateTree= pottingDate(cateTree,supplierId,categoryQuaMapper.findList(cateTree.getSecondNodeID()),cateTree.getSecondNodeID(),supplierType);
			if(cateTree.getIsAptitudeProductPageAudit() >0 || cateTree.getIsAptitudeSalesPageAudit()>0 || cateTree.getFileCount()>0){
			//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
			productCount=productCount+cateTree.getIsAptitudeProductPageAudit();
			//封装 物资销售 记录 资质文件
			salesCount=salesCount+cateTree.getIsAptitudeSalesPageAudit();
			}
		}
		if(StringUtils.isNotBlank(cateTree.getFirstNodeID()) ){
			cateTree= pottingDate(cateTree,supplierId,categoryQuaMapper.findList(cateTree.getFirstNodeID()),cateTree.getFirstNodeID(),supplierType);
			if(cateTree.getIsAptitudeProductPageAudit() >0 || cateTree.getIsAptitudeSalesPageAudit()>0 || cateTree.getFileCount()>0){
			//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
			productCount=productCount+cateTree.getIsAptitudeProductPageAudit();
			//封装 物资销售 记录 资质文件
			salesCount=salesCount+cateTree.getIsAptitudeSalesPageAudit();
			}
		}
		//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
		cateTree.setIsAptitudeProductPageAudit((int)productCount);
		//封装 物资销售 记录 资质文件
		cateTree.setIsAptitudeSalesPageAudit((int)salesCount);
		return cateTree;
	}
	/**
	 * 
	 * Description:封装 不同级别类别 数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-25
	 * @param cateTree
	 * @param supplierId
	 * @param categoryQuaList
	 * @param categoryId
	 * @return
	 */
	private SupplierCateTree pottingDate(SupplierCateTree cateTree, String supplierId,List<CategoryQua> categoryQuaList,
			String categoryId,String supplierType){
		long rut=0,productCount=0,salesCount=0,tempCount=0;
		String ids="";
		if(null != categoryQuaList && !categoryQuaList.isEmpty()){
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", supplierId);
			map.put("categoryId", categoryId);
			map.put("type", supplierType);
			//根据第三节目录节点 id(也就是中级目录 id) 获取目录中间表id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			if(null != itemList && !itemList.isEmpty()){
				for (SupplierItem supplierItem : itemList) {
		            for (CategoryQua categoryQua : categoryQuaList) {
		            	//组合 资质文件上传的 business_id
	    				/*String business_id=supplierItem.getId()+categoryQua.getId();
						temp=uploadService.countFileByBusinessId(business_id, type_id, common.constant.Constant.SUPPLIER_SYS_KEY);
						rut=rut+temp;*/
						//有上传文件 封装 审核数据
							ids=ids+supplierItem.getCategoryId()+",";
						//审核记录
							if("SALES".equals(supplierType)){
								//封装 物资销售 记录 资质文件
								tempCount=countData(supplierId, supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_SALES_PAGE);
								if(tempCount>0){
									salesCount=salesCount+tempCount;
								}
							}else{
							//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
								tempCount=countData(supplierId, supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE);
								if(tempCount>0){
									productCount=productCount+tempCount;
								}
							}
		            }
				}
				//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
				cateTree.setIsAptitudeProductPageAudit((int)productCount);
				//封装 物资销售 记录 资质文件
				cateTree.setIsAptitudeSalesPageAudit((int)salesCount);
				//封装 资质文件 id
				cateTree.setAptitudeId(ids);
			}
		}
		return cateTree;
	}
	@Override
	public SupplierCateTree countCategoyrId(SupplierCateTree cateTree, String supplierId,String supplierType) {
		//根据第三节目录节点 id(也就是中级目录 id) 品目id查询所要上传的资质文件
      /*  if(StringUtils.isEmpty(cateTree.getSecondNodeID())){
          return cateTree;
        }*/
		
		isCateTree(cateTree, supplierId,supplierType);
		return cateTree;
	}

	@Override
	public List<SupplierAudit> findByTypeId(SupplierAudit supplierAudit) {
		return supplierAuditMapper.findByTypeId(supplierAudit);
	}

	@Override
	public int insertAudit(List<SupplierAudit> supplierAudit) {
		int i=0;
		for (SupplierAudit supplierAudit2 : supplierAudit) {
			i=i+supplierAuditMapper.insertSelective(supplierAudit2);
		}
		return i;
	}
}
