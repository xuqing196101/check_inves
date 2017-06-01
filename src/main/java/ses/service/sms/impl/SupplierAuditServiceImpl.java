package ses.service.sms.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.SupplierModifyMapper;
import ses.dao.sms.SupplierStarsMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.dao.sms.SupplierTypeMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierStars;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierType;
import ses.model.sms.SupplierTypeRelate;
import ses.service.sms.SupplierAuditService;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
	private SupplierStarsMapper supplierStarsMapper;
	
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
	public void auditReasons(SupplierAudit supplierAudit) {
		supplierAuditMapper.insertSelective(supplierAudit);
		
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
	
}
