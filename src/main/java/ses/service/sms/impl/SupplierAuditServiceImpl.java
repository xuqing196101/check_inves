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

import ses.constants.SupplierConstants;
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
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
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
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierPublicity;
import ses.model.sms.SupplierStars;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierType;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.service.bms.QualificationService;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierModifyService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.Constant;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	private SupplierService supplierService;
	@Autowired
    private SupplierAptituteService supplierAptituteService;
	@Autowired
	private SupplierStarsMapper supplierStarsMapper;
	@Autowired
	private EngCategoryService engCategoryService;
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	@Autowired
	private SupplierModifyService supplierModifyService;
	@Autowired
	private AreaServiceI areaServiceI;
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
	
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;
	
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
        String queryCategory = supplier.getQueryCategory();
        if(StringUtils.isNotEmpty(queryCategory)){
            List<String> strings = Arrays.asList(queryCategory.split(","));
            supplier.setQueryCategorys(strings);
        }
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
		List<Supplier> listSupplier = supplierMapper.querySupplierbytypeAndCategoryIds(supplier);
        // 封装地区
        StringBuffer sb = new StringBuffer();
        Area area = null;
        if(listSupplier != null && !listSupplier.isEmpty()){
        	for (Supplier sup : listSupplier){
                area = sup.getArea();
                if(area != null){
                    sup.setName(sb.append(area.getName()).append(" ").append(sup.getName()).toString());
                    sb.delete(0, sb.length());
                }
			}
		}
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
		if(supplierAudit != null && supplierAudit.getId() != null){
			return supplierAuditMapper.updateByIdSelective(supplierAudit);
		}
		// 设置默认退回状态
		String auditType = supplierAudit.getAuditType();
		if(auditType != null && (auditType.startsWith("items_") || auditType.equals("supplierType_page"))){
			supplierAudit.setReturnStatus(2);
		}else{
			supplierAudit.setReturnStatus(1);
		}
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
	public int updateStatus(Supplier supplier) {
		return supplierMapper.updateStatus(supplier);
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
		String supplierTypeNames = "";
		if(supplier != null){
			List<SupplierTypeRelate> listSupplierTypeRelates = supplier.getListSupplierTypeRelates();
			for (int i = 0; i < listSupplierTypeRelates.size(); i++) {
				if (i > 0) {
					supplierTypeNames += ",";
				}
				supplierTypeNames += listSupplierTypeRelates.get(i).getSupplierTypeName();
			}
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
	private List<Qualification> isQualificationsCateTree(String supplierId, SupplierCateTree cateTree,
			Integer type,String type_id,Integer typeService){
		List<Qualification> tempList=null;
		List<Qualification> qulist=new ArrayList<>();
		List<CategoryQua> quaList=null;
		//type:4(工程不存在) 3（销售） 2（生产）1（服务）
		//专业资质 要求 有可能是末节节点 有可能是其他节点
		if(StringUtils.isNotBlank(cateTree.getFourthNodeID())){
			quaList= categoryQuaMapper.findListSupplier(cateTree.getFourthNodeID(), type);
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getFourthNodeID());
			map.put("type", isType(typeService));
			//根据第4节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			tempList = pottingQualificationsData(supplierId, itemList, quaList, cateTree, type);
			qulist.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getThirdNodeID())){
			quaList= categoryQuaMapper.findListSupplier(cateTree.getThirdNodeID(), type);
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getThirdNodeID());
			map.put("type", isType(typeService));
			//根据第4节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			tempList = pottingQualificationsData(supplierId, itemList, quaList, cateTree, type);
			qulist.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getSecondNodeID())){
			quaList= categoryQuaMapper.findListSupplier(cateTree.getSecondNodeID(), type);
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getSecondNodeID());
			map.put("type", isType(typeService));
			//根据第4节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			tempList = pottingQualificationsData(supplierId, itemList, quaList, cateTree, type);
			qulist.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getFirstNodeID()) ){
			quaList= categoryQuaMapper.findListSupplier(cateTree.getFirstNodeID(), type);
			Map<String, Object> map=new HashMap<>();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getFirstNodeID());
			map.put("type", isType(typeService));
			//根据第4节目录节点 id(也就是中级目录 id) 获取目录中间表id  获取文件的business_id
			List<SupplierItem> itemList=supplierItemService.findByMap(map);
			tempList = pottingQualificationsData(supplierId, itemList, quaList, cateTree, type);
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
	 * @return
	 */
	private List<Qualification> pottingQualificationsData(String supplierId, List<SupplierItem> itemList,List<CategoryQua> quaList,
			SupplierCateTree cateTree,Integer type){
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
					    	qualification.setAuditCount(countData(supplierId, supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_SALES_PAGE));
					    }else if(2==type){
						   	//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
						    qualification.setAuditCount(countData(supplierId, supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE));
					    }else if(1==type){
						   	//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
					    	qualification.setAuditCount(countData(supplierId, supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE));
					    }
				    	list.add(qualification);
					}
				}
			}
		}
		return list;
	}
	@Override
	public List<Qualification> showQualifications(String supplierId, SupplierCateTree cateTree,Integer type,String type_id,Integer typeService) {
		return isQualificationsCateTree(supplierId, cateTree, type, type_id, typeService);
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
	 * @return
	 */
	public SupplierCateTree cateTreePotting(SupplierCateTree cateTree,String supplierId){
		//封装 目录 物资生产 是否有审核记录数据  如果是其他的 类型 也是该字段存储
		// 审核字段存储：目录末级节点ID
		cateTree.setIsItemsProductPageAudit(countData(supplierId, cateTree.getItemsId(), ses.util.Constant.ITEMS_PRODUCT_PAGE));
		//封装 目录 物资销售 是否有审核记录数据   审核字段存储：目录末级节点ID
		cateTree.setIsItemsSalesPageAudit(countData(supplierId, cateTree.getItemsId(), ses.util.Constant.ITEMS_SALES_PAGE));
		cateTree.setAuditIsDeleted(countDataIsDeleted(supplierId, cateTree.getItemsId(), cateTree.getAuditType()));// 设置是否历史审核记录

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
//			tempList=supplierItemMapper.findByMap(map);
			itemsList.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getThirdNodeID())){
			map.clear();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getThirdNodeID());
			map.put("type", isType(type));
			tempList=supplierItemMapper.findByMapByNull(map);
//			tempList=supplierItemMapper.findByMap(map);
			itemsList.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getSecondNodeID())){
			map.clear();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getSecondNodeID());
			map.put("type", isType(type));
			tempList=supplierItemMapper.findByMapByNull(map);
//			tempList=supplierItemMapper.findByMap(map);
			itemsList.addAll(tempList);
		}
		if(StringUtils.isNotBlank(cateTree.getFirstNodeID()) ){
			map.clear();
			map.put("supplierId", supplierId);
			map.put("categoryId", cateTree.getFirstNodeID());
			map.put("type", isType(type));
			tempList=supplierItemMapper.findByMapByNull(map);
//			tempList=supplierItemMapper.findByMap(map);
			itemsList.addAll(tempList);
		}
		return itemsList;
	}
	@Override
	public List<SupplierCateTree> showProject(String supplierId, SupplierCateTree cateTree,
			Integer type, String type_id) {
		List<SupplierCateTree> cateTreeList=new ArrayList<>();
		List<SupplierItem> itemsList=getProject(cateTree,type,supplierId);//supplierItemService.findByMap(map);
		// 以上查的是有证书编号的品目列表
		/*Map<String, Object> map=new HashMap<>();
		map.put("supplierId", cateTree.getSupplierItemId());
		map.put("categoryId", cateTree.getCategoryId());
		map.put("type", isType(type));
		List<SupplierItem> itemsList=supplierItemService.findByMap(map);*/
		//--工程 审核字段存储：目录末级节点ID关联的SupplierItem的ID
		if(null!=itemsList && !itemsList.isEmpty()){
			for (SupplierItem supplierItem : itemsList) {
				SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierItem.getSupplierId());
				cateTree.setSupplierItemId(supplierItem.getId());
//				if(null != matEng && StringUtils.isNotBlank(supplierItem.getQualificationType()) && StringUtils.isNotBlank(supplierItem.getCertCode()) && StringUtils.isNotBlank(supplierItem.getProfessType())){
				if(null != matEng){
					/*List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(supplierItem.getQualificationType(), matEng.getId(), supplierItem.getCertCode(), supplierItem.getProfessType());
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
				    }*/
					List<SupplierAptitute> apts = matEng.getListSupplierAptitutes();
					if(apts != null && apts.size() > 0){
						for(SupplierAptitute apt: apts){
							if(apt.getCertCode().equals(supplierItem.getCertCode()) && apt.getCertType().equals(supplierItem.getQualificationType())){
								Qualification qua = qualificationService.getQualification(apt.getCertType());
								if(qua != null){
									apt.setCertType(qua.getName());
								}else{
									apt.setCertType("");
								}
								DictionaryData dd = DictionaryDataUtil.findById(apt.getAptituteLevel());
								if(dd != null){
									apt.setAptituteLevel(dd.getName());
								}else{
									apt.setAptituteLevel("");
								}
								cateTree.setFileId(apt.getId());
								cateTree.setSupplierAptitute(apt);
								cateTreeList.add(cateTree);
								break;
							}
						}
					}
			    }
				if(cateTree.getSupplierAptitute() == null){// 如果没有查出供应商所选资质，则查出品目对应需要上传的资质
					SupplierAptitute apt = new SupplierAptitute();
					Qualification qua = qualificationService.getQualification(supplierItem.getQualificationType());
					if(qua != null){
						apt.setCertType(qua.getName());
					}else{
						apt.setCertType("");
					}
					DictionaryData dd = DictionaryDataUtil.findById(supplierItem.getLevel());
					if(dd != null){
						apt.setAptituteLevel(dd.getName());
					}else{
						apt.setAptituteLevel("");
					}
					apt.setCertCode(supplierItem.getCertCode());
					apt.setProfessType(supplierItem.getProfessType());
					cateTree.setFileId("");
					cateTree.setSupplierAptitute(apt);
					cateTreeList.add(cateTree);
			    }
			}
		}
		if(cateTree.getSupplierAptitute() == null){// 如果没有查出供应商所选资质，则查出品目对应需要上传的资质（没有证书编号的）
	    	List < Category > cateList = new ArrayList < Category > ();
			cateList.add(categoryService.selectByPrimaryKey(cateTree.getCategoryId()));
			List < QualificationBean > typeList = supplierService.queryCategoyrId(cateList, 4);
			if(typeList != null && typeList.size() > 0 && typeList.get(0).getList() != null && typeList.get(0).getList().size() > 0) {
				SupplierAptitute apt = new SupplierAptitute();
				apt.setCertType(typeList.get(0).getList().get(0).getName());
				cateTree.setSupplierAptitute(apt);
				cateTreeList.add(cateTree);
			}
	    }
		return cateTreeList;
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
                    // 设置更新时间
                    supplier.setUpdatedAt(new Date());
	                // 设置入库时间
	                supplier.setInstorageAt(new Date());
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
	    // 设置分页
        PageHelper.startPage((Integer) (map.get("page")), PropUtil.getIntegerProperty("pageSize"));
		// 查询公示供应商列表
		SupplierPublicity supplierPublicityQuery = (SupplierPublicity) map.get("supplierPublicity");
		List<SupplierPublicity> list = supplierMapper.selectSupByPublictyList(supplierPublicityQuery);
		if(list != null && !list.isEmpty()){
			// 定义审核意见查询条件
			Map<String, Object> selectMap = new HashMap<>();
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
		
		SupplierCateTree cateTree = new SupplierCateTree();
		cateTree.setSupplierItemId(supplierItemId);
		cateTree.setOneContract(id1);
		cateTree.setTwoContract(id2);
		cateTree.setThreeContract(id3);
		cateTree.setOneBil(id4);
		cateTree.setTwoBil(id5);
		cateTree.setThreeBil(id6);
		/*// 递归获取所有父节点
		List < Category > parentNodeList = categoryService.getAllParentNode(itemId);
		// 加入根节点 物资
		cateTree=categoryService.addNode(parentNodeList);*/
		/*SupplierItem item = supplierItemService.selectByPrimaryKey(supplierItemId);
		String type = item.getSupplierTypeRelateId();
		String typeName = "";
		if("PRODUCT".equals(type)) {
			typeName = "生产";
		} else if("SALES".equals(type)) {
			typeName = "销售";
		}*/
		/*cateTree.setRootNode(cateTree.getRootNode() == null ? "" : cateTree.getRootNode());
		cateTree.setFirstNode(cateTree.getFirstNode() == null ? "" : cateTree.getFirstNode());
		cateTree.setSecondNode(cateTree.getSecondNode() == null ? "" : cateTree.getSecondNode());
		cateTree.setThirdNode(cateTree.getThirdNode() == null ? "" : cateTree.getThirdNode());
		cateTree.setFourthNode(cateTree.getFourthNode() == null ? "" : cateTree.getFourthNode());
		cateTree.setRootNode(cateTree.getRootNode() + typeName);*/
		/*if("PRODUCT".equals(type)) {
			typeName = ses.util.Constant.CONTRACT_PRODUCT_PAGE;
		} else if("SALES".equals(type)) {
			typeName = ses.util.Constant.CONTRACT_SALES_PAGE;
		}else{
			typeName = ses.util.Constant.CONTRACT_PRODUCT_PAGE;
		}*/
		//封装 合同 是否有审核 记录数据
		//* 销售合同：--物资生产/物资销售/服务  审核字段存储：目录末级节点ID关联的SupplierItem的ID_附件typeId
		// 合同 1 
		/*int count=countData(supplierId, cateTree.getSupplierItemId()+"_"+id1, typeName);
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
		cateTree.setIsItemsSalesPageAudit(count);*/
		List < SupplierCateTree > allTreeList = new ArrayList <> ();
		allTreeList.add(cateTree);		
		return allTreeList;
	}

	/**
	 *
	 * Description:查询选择和未通过的产品类别
	 *
	 * @author Easong
	 * @version 2017/7/12
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

        // 判断如果产品全部不通过，则提示需要改类型下所有产品不通过，请审核该类型也不通过
        JdcgResult jdcgResult = this.vertifyAuditItem(supplierId);
        if(jdcgResult != null){
            return jdcgResult;
        }

        return JdcgResult.ok();
	}

	/**
	 *
	 * Description:判断如果产品全部不通过，
	 * 则提示需要改类型下所有产品不通过，
	 * 请审核该类型也不通过
	 *
	 * @author Easong
	 * @version 2017/9/27
	 * @param [supplierId]
	 * @since JDK1.7
	 */
	public JdcgResult vertifyAuditItem(String supplierId){
		// 查询供应商选择的类型
        List<String> supplierTypeRelates = supplierTypeRelateMapper.findTypeBySupplierId(supplierId);
        // 先排除不通过的类型
        // 查询供应商不通过的类型
        // 封装查询map集合
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("supplierId", supplierId);
        selectMap.put("auditType", ses.util.Constant.SUPPLIER_CATE_INFO_ITEM_FLAG);
        List<SupplierAudit> supNoPassType = supplierAuditMapper.selectBySupIdAndType(selectMap);
        // 依次查询通过的类型下所有的品目
        if(supNoPassType != null && !supNoPassType.isEmpty()){
            for(SupplierAudit supplierAudit : supNoPassType){
                supplierTypeRelates.remove(supplierAudit.getType());
            }
        }
        List<SupplierItem> supplierItems;
        List<SupplierAudit> supplierAudits;
        String typeName = "";
        // 定义数量
        int count;
        // 遍历通过的类型
        for (String s : supplierTypeRelates){
            count = 0;
            // 查询选择该类型的所有品目
            selectMap.clear();
            selectMap.put("supplierId", supplierId);
            selectMap.put("type", s);
            supplierItems = supplierItemMapper.selectCountBySupTypeList(selectMap);
            // 遍历品目然后去审核表中查询是否存在该项
            if(supplierItems != null && !supplierItems.isEmpty()){
                for (SupplierItem supItem : supplierItems){
                    selectMap.clear();
                    selectMap.put("supplierId", supplierId);
                    selectMap.put("auditField", supItem.getCategoryId());
                    if(Constant.SUPPLIER_SALES.equals(s)){
                        selectMap.put("auditType", Constant.ITEMS_SALES_PAGE);
                    }else {
                        selectMap.put("auditType", Constant.ITEMS_PRODUCT_PAGE);
                    }
                    supplierAudits = supplierAuditMapper.selectBasicInfoAuditItemSingal(selectMap);
                    if(supplierAudits != null && !supplierAudits.isEmpty()){
                        count++;
                    }
                }
                // 累计个数如果该类型下品目的个数与不通过类型品目的个数相同则提示:某某类型下没有产品请把该某某类型审核不通过
                if(count == supplierItems.size()){
                    if(Constant.SUPPLIER_PRODUCT.equals(s)){
                        typeName = "物资生产";
                    }
                    if(Constant.SUPPLIER_SALES.equals(s)){
                        typeName = "物资销售";
                    }
                    if(Constant.SUPPLIER_PROJECT.equals(s)){
                        typeName = "工程";
                    }
                    if(Constant.SUPPLIER_SERVICE.equals(s)){
                        typeName = "服务";
                    }
                    return JdcgResult.build(500, typeName + "类型下没有产品，请把" + typeName + "类型审核不通过");
                }
            }
        }
        return null;
	}

	@Override
	public JdcgResult selectAuditNoPassItemCount(String supplierId, String flag) {
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
        // 判断如果产品全部不通过，则提示需要该类型下所有产品不通过，请审核该类型也不通过
        JdcgResult jdcgResult = this.vertifyAuditItem(supplierId);
        if(jdcgResult != null){
            return jdcgResult;
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
    
    @Override
    public JdcgResult getTypeAndItemNotPass(String supplierId){
    	// 审核汇总记录中：
		
		// 有退回修改/未修改的记录，最终状态不通过（排除供应商类型/产品类别下的退回修改/未修改记录）
		
		// 所有类型不通过，最终状态不通过
		
		// 有目录下产品全部不通过，最终状态不通过
		
		// 其他情况，最终状态通过
		
		//int isAllTypeNotPass = 0;// 所有类型不通过
		//int isAllItemNotPass = 0;// 类型下所有品目不通过
		int typeNotPassCount = 0;// 类型不通过数量
		SupplierAudit supplierTypeAudit = new SupplierAudit();
		supplierTypeAudit.setSupplierId(supplierId);
		supplierTypeAudit.setAuditType("supplierType_page");
		List<SupplierAudit> supplierTypeAuditList = this.getAuditRecords(supplierTypeAudit, new Integer[]{2});
		List<String> supplierTypeList = supplierTypeRelateService.findTypeBySupplierId(supplierId);
		if(supplierTypeList != null && supplierTypeList.size() > 0){
			for(String supplierType : supplierTypeList){
				DictionaryData dd = DictionaryDataUtil.get(supplierType);
				if(supplierTypeAuditList != null){
					for(SupplierAudit audit : supplierTypeAuditList){
						if(audit.getAuditField() != null && dd != null && audit.getAuditField().equals(dd.getId())){
							typeNotPassCount++;
							break;
						}
					}
				}
				int itemNotPassCount = 0;// 品目不通过数量
				List<SupplierItem> itemList = supplierItemService.getItemList(supplierId, supplierType, null, null);
				SupplierAudit supplierItemAudit = new SupplierAudit();
				supplierItemAudit.setSupplierId(supplierId);
				supplierItemAudit.setAuditType(getAuditType(supplierType));
				List<SupplierAudit> supplierItemAuditList = this.getAuditRecords(supplierItemAudit, new Integer[]{2});
				if(itemList != null && itemList.size() > 0){
					for(SupplierItem item : itemList){
						if(supplierItemAuditList != null){
							for(SupplierAudit audit : supplierItemAuditList){
								if(audit.getAuditField() != null && audit.getAuditField().equals(item.getCategoryId())){
									itemNotPassCount++;
									break;
								}
							}
						}
					}
					// 判断该类型是否审核
					supplierTypeAudit.setAuditField(dd.getId());
					int countSupplierTypeAudit = this.countAuditRecords(supplierTypeAudit, new Integer[]{2});
					if(itemList.size() <= itemNotPassCount && countSupplierTypeAudit == 0){
						//isAllItemNotPass = 1;
						String typeName = getSupplierTypeName(supplierType);
						return JdcgResult.build(2, typeName + "类型下没有产品，请把" + typeName + "类型审核不通过！", supplierType);
					}
				}
			}
			if(supplierTypeList.size() <= typeNotPassCount){
				//isAllTypeNotPass = 1;
				return JdcgResult.build(1, "供应商类型不能全部不通过！");
			}
		}
		return JdcgResult.build(0, "");
    }
    
	private String getAuditType(String code){
		String auditType = "";
		switch (code) {
		case ses.util.Constant.SUPPLIER_PRODUCT:
			auditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
			break;
		case ses.util.Constant.SUPPLIER_SALES:
			auditType = ses.util.Constant.ITEMS_SALES_PAGE;
			break;
		default:
			auditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
			break;
		}
		return auditType;
	}
	
	private String getSupplierTypeName(String code){
		String typeName = "";
		if(Constant.SUPPLIER_PRODUCT.equals(code)){
            typeName = "物资生产";
        }
        if(Constant.SUPPLIER_SALES.equals(code)){
            typeName = "物资销售";
        }
        if(Constant.SUPPLIER_PROJECT.equals(code)){
            typeName = "工程";
        }
        if(Constant.SUPPLIER_SERVICE.equals(code)){
            typeName = "服务";
        }
		return typeName;
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
	
	@Override
	public boolean isContractModified(String supplierId, String supplierItemId){
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
		
		Supplier supplier = supplierMapper.selectByPrimaryKey(supplierId);
		// 退回修改附件
		if(supplier != null && supplier.getStatus() != null && (supplier.getStatus() == 0 || supplier.getStatus() == 9)) {
			SupplierModify supplierFileModify = new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			//supplierFileModify.setBeforeField(supplierItemId);
			supplierFileModify.setRelationId(supplierItemId);
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(m.getRelationId() != null && m.getBeforeField() != null){
					fileModifyField.append(m.getRelationId() + m.getBeforeField() + ",");
				}
			}
			if(fileModifyField.indexOf(supplierItemId+id1) > -1
					|| fileModifyField.indexOf(supplierItemId+id1) > -1
					|| fileModifyField.indexOf(supplierItemId+id2) > -1
					|| fileModifyField.indexOf(supplierItemId+id3) > -1
					|| fileModifyField.indexOf(supplierItemId+id4) > -1
					|| fileModifyField.indexOf(supplierItemId+id5) > -1
					|| fileModifyField.indexOf(supplierItemId+id6) > -1){
				return true;
			}
		}
		return false;
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
	private Integer countData(String supplierId, String auditField, String auditType){
		SupplierAudit audit=new SupplierAudit();
		audit.setSupplierId(supplierId);
		audit.setAuditField(auditField);
		audit.setAuditType(auditType);
		return countAuditRecords(audit, SupplierConstants.AUDIT_RETURN_STATUS);
	}
	private Integer countDataIsDeleted(String supplierId, String auditField, String auditType){
		SupplierAudit audit=new SupplierAudit();
		audit.setSupplierId(supplierId);
		audit.setAuditField(auditField);
		audit.setAuditType(auditType);
		audit.setIsDeleted(1);
		return countAuditRecords(audit, SupplierConstants.AUDIT_RETURN_STATUS);
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
        List<SupplierItem> itemsList=getProject(cateTree, 4, supplierId);
        // 以上查的是有证书编号的品目列表
		/*Map<String, Object> map=new HashMap<>();
		map.put("supplierId", supplierId);
		map.put("categoryId", cateTree.getCategoryId());
		map.put("type", isType(4));
		List<SupplierItem> itemsList=supplierItemService.findByMap(map);*/
		//--工程 审核字段存储：目录末级节点ID关联的SupplierItem的ID
		if(null!=itemsList && !itemsList.isEmpty()){
			for (SupplierItem supplierItem : itemsList) {
				SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierItem.getSupplierId());
				if(null != matEng/* || StringUtils.isNotBlank(supplierItem.getCertCode()) || StringUtils.isNotBlank(supplierItem.getProfessType())*/){
					//List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(supplierItem.getQualificationType(), matEng.getId(), supplierItem.getCertCode(), supplierItem.getProfessType());
					List<SupplierAptitute> apts = matEng.getListSupplierAptitutes();
					if(apts != null && apts.size() > 0){
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
						/*}*/
						for(SupplierAptitute apt: apts){
							if(apt.getCertCode().equals(supplierItem.getCertCode()) && apt.getCertType().equals(supplierItem.getQualificationType())){
								// 工程资质是否修改
								cateTree.setIsEngAptitudeModified(isEngAptitudeModified(supplierItem, apt) ? (byte)1 : (byte)0);
								break;
							}
						}
					}
				}
			}
		}
		
		// 无证书编号的品目标红...
		if(null==itemsList || itemsList.isEmpty() && (product == 0 || sales == 0)){
			temp=countData(supplierId, cateTree.getSupplierItemId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE);
			if(temp>0){
				product+=temp;
			}
			temp=countData(supplierId, cateTree.getSupplierItemId(), ses.util.Constant.APTITUDE_SALES_PAGE);
			if(temp>0){
				sales+=temp;
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
	 * 替换类型 编号
	 *   资质类型Id  1:通用(未知) 2：物资生产型 3：物资销售型 4：工程类
	 * @param supplierType
	 * @return
	 */
	private Integer isSupplierType(String supplierType){
		Integer rut=-2;
		switch(supplierType){
			case "PRODUCT"://物资生产型
				rut=2;
				break;
			case "SALES"://物资销售型
				rut=3;
				break;
			case "PROJECT"://工程类
				rut=4;
				break;
			case "SERVICE"://工程类
				rut=2;
				break;
		}
		return  rut;
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
		Map<String,Object> map=new HashMap<>();
		//专业资质 要求 有可能是末节节点 有可能是其他节点
		if(StringUtils.isNotBlank(cateTree.getFourthNodeID())){
			cateTree= pottingData(cateTree,supplierId,categoryQuaMapper.findListSupplier(cateTree.getFourthNodeID(),isSupplierType(supplierType)),cateTree.getFourthNodeID(),supplierType);
			if(cateTree.getIsAptitudeProductPageAudit() >0 || cateTree.getIsAptitudeSalesPageAudit()>0 || cateTree.getFileCount()>0){
				//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
				productCount=productCount+cateTree.getIsAptitudeProductPageAudit();
				//封装 物资销售 记录 资质文件
				salesCount=salesCount+cateTree.getIsAptitudeSalesPageAudit();
			}
		}
		if(StringUtils.isNotBlank(cateTree.getThirdNodeID())){
			//如果末节点 为空 或者  查询时空
			cateTree= pottingData(cateTree,supplierId,categoryQuaMapper.findListSupplier(cateTree.getThirdNodeID(),isSupplierType(supplierType)),cateTree.getThirdNodeID(),supplierType);
			if(cateTree.getIsAptitudeProductPageAudit() >0 || cateTree.getIsAptitudeSalesPageAudit()>0 || cateTree.getFileCount()>0){
			//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
			productCount=productCount+cateTree.getIsAptitudeProductPageAudit();
			//封装 物资销售 记录 资质文件
			salesCount=salesCount+cateTree.getIsAptitudeSalesPageAudit();
			}
		}
		if(StringUtils.isNotBlank(cateTree.getSecondNodeID())){
			cateTree= pottingData(cateTree,supplierId,categoryQuaMapper.findListSupplier(cateTree.getSecondNodeID(),isSupplierType(supplierType)),cateTree.getSecondNodeID(),supplierType);
			if(cateTree.getIsAptitudeProductPageAudit() >0 || cateTree.getIsAptitudeSalesPageAudit()>0 || cateTree.getFileCount()>0){
			//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
			productCount=productCount+cateTree.getIsAptitudeProductPageAudit();
			//封装 物资销售 记录 资质文件
			salesCount=salesCount+cateTree.getIsAptitudeSalesPageAudit();
			}
		}
		if(StringUtils.isNotBlank(cateTree.getFirstNodeID()) ){
			cateTree= pottingData(cateTree,supplierId,categoryQuaMapper.findListSupplier(cateTree.getFirstNodeID(),isSupplierType(supplierType)),cateTree.getFirstNodeID(),supplierType);
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
	private SupplierCateTree pottingData(SupplierCateTree cateTree, String supplierId,List<CategoryQua> categoryQuaList,
			String categoryId,String supplierType){
		long rut=0,productCount=0,salesCount=0,tempCount=0;
		String ids="";
		if(null != categoryQuaList && !categoryQuaList.isEmpty()){
			
			// 退回修改附件
			StringBuffer fileModifyField = new StringBuffer();
			Supplier supplier = supplierMapper.selectByPrimaryKey(supplierId);
			if(supplier != null && supplier.getStatus() != null && (supplier.getStatus() == 0 || supplier.getStatus() == 9)) {
				SupplierModify supplierFileModify = new SupplierModify();
				supplierFileModify.setSupplierId(supplierId);
				supplierFileModify.setModifyType("file");
				List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
				for(SupplierModify m : fileModify){
					if(m.getBeforeField() != null){
						fileModifyField.append(m.getBeforeField() + ",");
					}
				}
			}
			
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

						//审核记录
						if("SALES".equals(supplierType)){
							//有上传文件 封装 审核数据
							ids=ids+supplierItem.getCategoryId()+",";
							//封装 物资销售 记录 资质文件
							tempCount=countData(supplierId, supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_SALES_PAGE);
							if(tempCount>0){
								salesCount=salesCount+tempCount;
							}
						}else{
							//有上传文件 封装 审核数据
							ids=ids+supplierItem.getCategoryId()+",";
							//封装 物资生产 记录 资质文件  如果是其他的 类型 也是该字段存储
							tempCount=countData(supplierId, supplierItem.getId()+"_"+categoryQua.getQuaId(), ses.util.Constant.APTITUDE_PRODUCT_PAGE);
							if(tempCount>0){
								productCount=productCount+tempCount;
							}
						}
						// 资质是否修改
						String businessId = supplierItem.getId()+categoryQua.getId();
						if(fileModifyField.indexOf(businessId) > -1){
							cateTree.setIsAptitudeModified((byte)1);
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
	public int insertAudit(List<SupplierAudit> supplierAuditList) {
		int i=0;
		for (SupplierAudit supplierAudit : supplierAuditList) {
			i=i+supplierAuditMapper.insertSelective(supplierAudit);
		}
		return i;
	}

	@Override
	public List<SupplierAudit> getAuditRecords(SupplierAudit supplierAudit, Integer[] rss) {
		List<SupplierAudit> reasonsList = supplierAuditMapper.selectAuditRecords(supplierAudit, rss);
		return reasonsList;
	}

	@Override
	public List<SupplierAudit> getAuditRecordsWithSort(
			SupplierAudit supplierAudit) {
		List<SupplierAudit> reasonsList = supplierAuditMapper.selectAuditRecordsWithSort(supplierAudit);
		return reasonsList;
	}

	@Override
	public int updateReturnStatus(String supplierId) {
		int result = 0;
		SupplierAudit supplierAudit = new SupplierAudit();
		supplierAudit.setSupplierId(supplierId);
		supplierAudit.setIsDeleted(1);
		// 查询退回修改的和未修改的进行更新
		// 本来不查询品目审核记录的，但由于老数据的原因，加上状态为0的，可能包含品目的审核记录，在修改状态的时候直接改为“审核不通过(2)”
		List<SupplierAudit> reasonsList = supplierAuditMapper.selectAuditRecords(supplierAudit, new Integer[]{0,1,4});
		if(reasonsList != null && reasonsList.size() > 0){
			for(SupplierAudit audit : reasonsList){
				SupplierModify supplierModify = new SupplierModify();
				supplierModify.setSupplierId(audit.getSupplierId());
				//supplierModify.setModifyType(audit.getAuditType());
				String auditType = audit.getAuditType();
				String auditField = audit.getAuditField();
				if(auditType != null && auditField != null){
					auditField = auditField.replaceAll("_file", "");// 财务附件
					auditField = auditField.replaceAll("_info", "");// 财务信息
					if("mat_eng_page".equals(auditType)){// 承揽业务范围
						String areaId = areaServiceI.selectByName(auditField);
						if(areaId != null){
							auditField = areaId;
						}
					}
					if("supplierPledge".equals(auditField)){// 供应商承诺书
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierPledge();
					}
					if("supplierRegList".equals(auditField)){// 供应商入库申请表
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierRegList();
					}
					if("supplierConAch".equals(auditField)){// 承包合同主要页及保密协议
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierConAch();
					}
					if("supplierBank".equals(auditField)){// 基本账户开户许可证
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierBank();
					}
					if("businessCert".equals(auditField)){// 营业执照
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierBusinessCert();
					}
					if("supplierIdentityUp".equals(auditField)){// 身份证复印件
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierIdentityUp();
					}
					if("taxCert".equals(auditField)){// 近三个月完税凭证
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierTaxCert();
					}
					if("billCert".equals(auditField)){// 近三年银行基本账户年末对账单
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierBillCert();
					}
					if("securityCert".equals(auditField)){// 近三个月缴纳社会保险金凭证
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierSecurityCert();
					}
					if("supplierBearchCert".equals(auditField)){// 保密资格证书
						auditField = dictionaryDataServiceI.getSupplierDictionary().getSupplierBearchCert();
					}
					if(auditType.startsWith("contract_")){// 合同
						String[] fieldAry = auditField.split("_");
						if(fieldAry != null && fieldAry.length > 1){
							auditField = fieldAry[1];
						}
					}
					if(auditType.startsWith("aptitude_")){// 资质
						String[] fieldAry = auditField.split("_");
						if(fieldAry != null && fieldAry.length > 1){
							String itemId = fieldAry[0];// 品目id
							String quaId = fieldAry[1];// 资质文件id
							SupplierItem item = supplierItemService.selectByPrimaryKey(itemId);
							if(item != null){
								String catId = item.getCategoryId();
								Category cate = categoryService.selectByPrimaryKey(catId);
								String flag = "";
								if(cate == null){
									DictionaryData data = DictionaryDataUtil.findById(catId);
									flag = data.getId();
								}else{
									flag = item.getId();
								}
								CategoryQua cq = new CategoryQua();
								cq.setCategoryId(catId);
								cq.setQuaId(quaId);
								List<CategoryQua> cqList = categoryQuaMapper.selectCategoryQuaList(cq);
								if(cqList != null && cqList.size() > 0){
									auditField = flag + cqList.get(0).getId();
								}
							}
						}
					}
					if(auditType.startsWith("items_") || "supplierType_page".equals(auditType)){// 品目/类型
						// 更新状态
						SupplierAudit supplierAuditUpdate = new SupplierAudit();
						supplierAuditUpdate.setId(audit.getId());
						supplierAuditUpdate.setReturnStatus(2);
						result += supplierAuditMapper.updateByIdSelective(supplierAuditUpdate);
						continue;
					}
					String beforeField = auditField;
					String relationId = auditField;
					if("basic_page".equals(auditType)){// 境外分支
						if(auditField.startsWith("organizationName_")){
							beforeField = "organizationName";
							relationId = auditField.replaceAll("organizationName_", "");
						}
						if(auditField.startsWith("countryName_")){
							beforeField = "countryName";
							relationId = auditField.replaceAll("countryName_", "");
						}
						if(auditField.startsWith("detailAddress_")){
							beforeField = "detailAddress";
							relationId = auditField.replaceAll("detailAddress_", "");
						}
						if(auditField.startsWith("businessSope_")){
							beforeField = "businessSope";
							relationId = auditField.replaceAll("businessSope_", "");
						}
					}
					supplierModify.setBeforeField(beforeField);
					supplierModify.setRelationId(relationId);
					int modifyCount = supplierModifyService.countBySupplierId(supplierModify);
					// 更新状态
					SupplierAudit supplierAuditUpdate = new SupplierAudit();
					supplierAuditUpdate.setId(audit.getId());
					supplierAuditUpdate.setUpdatedAt(new Date());
					Integer rs = audit.getReturnStatus();
					if(modifyCount > 0){
						if(rs != null && rs != 3){
							supplierAuditUpdate.setReturnStatus(3);
							result += supplierAuditMapper.updateByIdSelective(supplierAuditUpdate);
						}
					}else{
						if(rs != null && rs != 4){
							supplierAuditUpdate.setReturnStatus(4);
							result += supplierAuditMapper.updateByIdSelective(supplierAuditUpdate);
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<SupplierItem> selectSupplierItemByType(Map<String, Object> map) {
		return supplierItemMapper.selectCountBySupTypeList(map);
	}

	private boolean isEngAptitudeModified(SupplierItem supplierItem, SupplierAptitute aptitude) {
		String supplierId = supplierItem.getSupplierId();
		Supplier supplier = supplierMapper.selectByPrimaryKey(supplierId);
		// 退回修改附件和字段
		if(SupplierConstants.isStatusToAudit(supplier.getStatus())){
			SupplierModify supplierFileModify = new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(m.getRelationId() != null){
					fileModifyField.append(m.getRelationId() + ",");
				}
			}
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_eng_page");
			supplierModify.setListType(9);// 工程资质
			supplierModify.setRelationId(aptitude.getId());
			List < SupplierModify > fieldList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer field = new StringBuffer();
			for(int i = 0; i < fieldList.size(); i++) {
				String beforeField = fieldList.get(i).getBeforeField();
				field.append(beforeField + ",");
			}
			
			if(aptitude.getCertType() != null && aptitude.getCertType().equals(supplierItem.getQualificationType())
					&& aptitude.getCertCode() != null && aptitude.getCertCode().equals(supplierItem.getCertCode())
					&& aptitude.getProfessType() != null && aptitude.getProfessType().equals(supplierItem.getProfessType())
					&& aptitude.getAptituteLevel() != null && aptitude.getAptituteLevel().equals(supplierItem.getLevel())){
				if(fileModifyField.indexOf(aptitude.getId()) > -1 
						|| field.indexOf("certType") > -1
						|| field.indexOf("certCode") > -1
						|| field.indexOf("professType") > -1
						|| field.indexOf("aptituteLevel") > -1){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public JdcgResult auditContractMuti(String userId, String supplierId,
			String supplierTypeId, String suggest, String itemIds) {
		if(StringUtils.isNotBlank(itemIds)){
			
			//合同
			String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
			String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
			String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
			//账单
			String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
			String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
			String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
			
			int result = 0;
			String auditType = ses.util.Constant.CONTRACT_PRODUCT_PAGE;
			String auditFieldName = getContractTypeInfo(supplierTypeId);
			Date auditDate = new Date();
			String[] ids = new String[]{id1,id2,id3,id4,id5,id6};
			
			if(ses.util.Constant.SUPPLIER_PRODUCT.equals(supplierTypeId)){
				auditType = ses.util.Constant.CONTRACT_PRODUCT_PAGE;
			}
			if(ses.util.Constant.SUPPLIER_SALES.equals(supplierTypeId)){
				auditType = ses.util.Constant.CONTRACT_SALES_PAGE;
			}
			
			Supplier supplier = supplierService.selectById(supplierId);
			
			String code = supplierTypeId;
			if("PRODUCT".equals(supplierTypeId) || "SALES".equals(supplierTypeId)){
				code = "GOODS";
			}
			String itemFullName = "";
			DictionaryData dd = DictionaryDataUtil.get(code);
			if(dd != null){
				itemFullName = dd.getName();
			}
			
			String[] itemIdAry = itemIds.split(",");
			for(String itemId : itemIdAry){
				SupplierItem item = supplierItemService.getItemById(itemId);
				if(item != null){
					// 先判断目录是否审核
					SupplierAudit itemAudit = new SupplierAudit();
					itemAudit.setSupplierId(supplierId);
					String itemAuditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
					if(ses.util.Constant.SUPPLIER_PRODUCT.equals(supplierTypeId)){
						itemAuditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
					}
					if(ses.util.Constant.SUPPLIER_SALES.equals(supplierTypeId)){
						itemAuditType = ses.util.Constant.ITEMS_SALES_PAGE;
					}
					itemAudit.setAuditType(itemAuditType);
					itemAudit.setAuditField(item.getCategoryId());
//					int itemAuditCount = supplierAuditMapper.countByPrimaryKey(itemAudit);
					int itemAuditCount = supplierAuditMapper.countAuditRecords(itemAudit, new Integer[]{0,2});
					if(itemAuditCount > 0){
						return new JdcgResult(503, "选择中存在已审核目录，无需再审核合同", null);
					}
					// 获取所有父级节点拼接名称
					List<Category> plist = categoryService.getPListById(item.getCategoryId());
					if(plist != null && plist.size() > 0){
						for(Category cate : plist){
							itemFullName += "/" + cate.getName();
						}
					}
					// 近三年合同以及对账单
					for(int i = 0; i < ids.length; i++){
						
						String auditField = itemId + "_" + ids[i];
						String auditContent = itemFullName + "（"+getContractYearInfo(i+1)+"）";
						
						SupplierAudit audit = new SupplierAudit();
						audit.setUserId(userId);
						audit.setSupplierId(supplierId);
						audit.setAuditType(auditType);
						audit.setSuggest(suggest);
						audit.setAuditField(auditField);
						audit.setAuditFieldName(auditFieldName);
						audit.setAuditContent(auditContent);
						audit.setCreatedAt(auditDate);
						audit.setStatus(supplier.getStatus());
						audit.setReturnStatus(1);
						// 判断是否审核过该合同
//						int count = supplierAuditMapper.countByPrimaryKey(audit);
						int count = supplierAuditMapper.countAuditRecords(audit, new Integer[]{0,1});
						if(count > 0){
							return new JdcgResult(503, "选择中存在已审核，不可重复审核", null);
						}
						
						result += supplierAuditMapper.insertSelective(audit);
					}
				}
			}
			if(result > 0){
				return new JdcgResult(500, "审核成功", null);
			}else{
				return new JdcgResult(502, "审核失败", null);
			}
		}
		return null;
	}
	
	private String getContractYearInfo(int index){
		List<Integer> years = supplierService.getThressYear();
		String info = "";
		switch (index) {
		case 1:
			info = years.get(0) + "年度销售合同";
			break;
		case 2:
			info = years.get(1) + "年度销售合同";
			break;
		case 3:
			info = years.get(2) + "年度销售合同";
			break;
		case 4:
			info = years.get(0) + "年度银行收款证明";
			break;
		case 5:
			info = years.get(1) + "年度银行收款证明";
			break;
		case 6:
			info = years.get(2) + "年度银行收款证明";
			break;
		default:
			break;
		}
		return info;
	}
	
	private String getContractTypeInfo(String type){
		String info = "";
		if("PRODUCT".equals(type)){
			info = "物资-生产销售合同";
		}
		if("SALES".equals(type)){
			info = "物资-销售合同";
		}
		if("SERVICE".equals(type)){
			info = "服务-销售合同";
		}
		return info;
	}

	@Override
	public JdcgResult updateReturnStatus(String ids, Integer status) {
		int result = 0;
		if(StringUtils.isNotBlank(ids)){
			String[] idAry = ids.split(",");
			for(String id : idAry){
				SupplierAudit supplierAuditById = supplierAuditMapper.selectById(id);
				/*if(supplierAuditById != null && supplierAuditById.getReturnStatus() != null && supplierAuditById.getReturnStatus() == 2){
					return new JdcgResult(503, "选择中存在审核不通过的产品目录", null);
				}
				if(supplierAuditById != null && supplierAuditById.getAuditType() != null && supplierAuditById.getAuditType().startsWith("items_")){
					return new JdcgResult(503, "选择中存在审核不通过的产品目录", null);
				}*/
				if(supplierAuditById != null && supplierAuditById.getReturnStatus() != null && status != null){
					if(supplierAuditById.getReturnStatus() == 3){
						return new JdcgResult(503, "选择中包含已修改的记录，已修改的记录不能修改任何状态！可以重新审核", null);
					}
					if(supplierAuditById.getReturnStatus() == 5 || supplierAuditById.getReturnStatus() == 6){
						return new JdcgResult(503, "选择中包含撤销退回/撤销不通过的记录，撤销的记录不能修改任何状态！可以重新审核", null);
					}
					if((supplierAuditById.getReturnStatus() == 1 || supplierAuditById.getReturnStatus() == 4) && status != 5){
						return new JdcgResult(503, "选择中包含退回修改/未修改的记录，退回修改和未修改的记录只能撤销退回！", null);
					}
					if(supplierAuditById.getReturnStatus() == 2 && status != 6){
						return new JdcgResult(503, "选择中包含审核不通过的记录，审核不通过的记录只能撤销不通过！", null);
					}
				}
				SupplierAudit supplierAudit = new SupplierAudit();
				supplierAudit.setId(id);
				if(status == 1){// 退回修改
					supplierAudit.setIsDeleted(0);
				}
				if(status == 3){// 已修改
					supplierAudit.setIsDeleted(1);
				}
				if(status == 4){ // 未修改
					supplierAudit.setIsDeleted(1);
				}
				supplierAudit.setReturnStatus(status);
				supplierAudit.setUpdatedAt(new Date());
				result += supplierAuditMapper.updateByIdSelective(supplierAudit);
			}
			if(result > 0){
				return new JdcgResult(500, "更新成功", null);
			}else{
				return new JdcgResult(502, "更新失败", null);
			}
		}
		return null;
	}

	@Override
	public int countAuditRecords(SupplierAudit supplierAudit, Integer[] rss) {
		return supplierAuditMapper.countAuditRecords(supplierAudit, rss);
	}

	@Override
	public JdcgResult vertifyReturnToModify(String supplierId) {
		SupplierAudit supplierAudit = new SupplierAudit();
		supplierAudit.setSupplierId(supplierId);
//		int auditCount = this.countAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
		int auditCount = this.countAuditRecords(supplierAudit, new Integer[]{0,1,4});
		if(auditCount == 0){
//			return JdcgResult.build(500, "没有审核不通过项！");
			return JdcgResult.build(500, "没有退回修改/未修改的记录！");
		}
		return getTypeAndItemNotPass(supplierId);
	}

	@Override
	public JdcgResult vertifyYushenhe(String supplierId, String flag) {
		SupplierAudit supplierAudit = new SupplierAudit();
		supplierAudit.setSupplierId(supplierId);
		int auditCount = this.countAuditRecords(supplierAudit, new Integer[]{0,1,4});
		JdcgResult result = getTypeAndItemNotPass(supplierId);
		if("0".equals(flag)){// 预审核不通过
			if(auditCount == 0 && (result != null && result.getStatus() == 0)){
				return JdcgResult.build(500, "没有预审核不通过项！");
			}
		}
		if("1".equals(flag)){// 预审核通过
			// 判断基本信息+财务信息+股东信息
//			supplierAudit.setAuditType("basic_page");
//			auditCount = this.countAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			if(auditCount > 0){
//				return JdcgResult.build(500, "基本、财务、股东信息中有不通过项！");
				//暂时去掉基本信息退回修改/未修改的校验
				//return JdcgResult.build(500, "还有退回修改/未修改的记录！");
			}
			if(result != null && result.getStatus() != 0){
				return result;
			}
		}
		return JdcgResult.build(0, "");
	}

	/**
	 *
	 * Description: 查询供应商不通过的类型
	 *
	 * @author Easong
	 * @version 2017/10/20
	 * @param
	 * @since JDK1.7
	 */
	@Override
	public List<SupplierAudit> selectBySupIdAndType(Map<String, Object> map) {
		// 查询供应商不通过的类型
		// 封装查询map集合
		map.put("auditType", ses.util.Constant.SUPPLIER_CATE_INFO_ITEM_FLAG);
		return supplierAuditMapper.selectBySupIdAndType(map);
	}

	@Override
	public int saveAudit(List<SupplierAudit> supplierAuditList) {
		int i=0;
		for (SupplierAudit supplierAudit : supplierAuditList) {
			if(supplierAudit != null && supplierAudit.getId() != null){
				//更新时间？
				i=i+supplierAuditMapper.updateByIdSelective(supplierAudit); 
			}else{
				// 设置默认退回状态
				if(supplierAudit.getAuditType() != null && supplierAudit.getAuditType().startsWith("items_")){
					supplierAudit.setReturnStatus(2);
				}else{
					supplierAudit.setReturnStatus(1);
				}
				i=i+supplierAuditMapper.insertSelective(supplierAudit);
			}
		}
		return i;
	}

	@Override
	public int updateByIdSelective(SupplierAudit supplierAudit) {
		return supplierAuditMapper.updateByIdSelective(supplierAudit);
	}
}
