package bss.service.ppms.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import common.constant.StaticVariables;

import ses.dao.sms.QuoteMapper;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertPro;
import ses.service.bms.TodosService;
import ses.service.sms.SupplierAuditService;
import ses.util.PropertiesUtil;
import bss.dao.ppms.AdvancedDetailMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.ppms.theSubjectMapper;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.Packages;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.theSubject;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.SupplierCheckPassService;
import bss.util.PropUtil;

@Service
public class SupplierCheckPassServiceImpl implements SupplierCheckPassService {
  /** SUCCESS */
  private static final String SUCCESS = "SUCCESS";
  /** ERROR */
  private static final String ERROR = "ERROR";

  @Autowired
  SupplierCheckPassMapper checkPassMapper;

  @Autowired
  TodosService todosService; 
  
  @Autowired
  SupplierAuditService supplierAuditService;
  
  @Autowired
  private AdvancedDetailMapper detailMapper;
  
  @Autowired
  private theSubjectMapper theSubjectMapper;
  
  @Autowired
  private QuoteMapper quoteMapper;
  
  @Autowired
  private BidMethodService bidMethodService;
  
  @Autowired
  private ProjectDetailMapper projectDetailMapper;

  /**
   * 
   *〈简述〉获取包id和包name
   *〈详细描述〉
   * @author Wang Wenshuai.
   * @param projectId 项目id
   * @return 包集合
   */
  @Override
  public List<Packages> getPackageName(String projectId){
    return checkPassMapper.getPackageName(projectId);
  }

  /**
   * 
   *〈简述〉获取项目包下所有信息 
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param projectId 项目id
   * @return 包集合
   */
  @Override
  public List<Packages> listPackage(String projectId){

    return checkPassMapper.listPackage(projectId);

  }

  /**
   * 
   *〈简述〉根据包id获取包下未发送通知的供应商和中标未中标的供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param checkPass 对象
   * @return 包集合
   */
  @Override
  public List<SupplierCheckPass> listSupplierCheckPass(SupplierCheckPass checkPass){
    return checkPassMapper.listSupplierCheckPass(checkPass);
  }

  /**
   * 
   *〈简述〉修改
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param checkPass 对象
   * @return 包集合
   */
  @Override
  public String update(SupplierCheckPass checkPass) {
    checkPassMapper.updateByPrimaryKeySelective(checkPass);
    return SUCCESS;
  }

  /**
   * 
   *〈简述〉修改中标状态
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param id
   */
  @Override
  public void updateBid(String[] ids,BigDecimal[] wonPrice,String userId) {
    SupplierCheckPass record = null;
    String[] ratio = ratio(ids.length);
    for (int i = 0; i < wonPrice.length; i++ ) {
      push(ids[i],userId);
      record = new SupplierCheckPass();
      record.setId(ids[i]);
      record.setIsWonBid((short) 1);
      record.setWonPrice(wonPrice[i]);
      record.setPriceRatio(ratio[i]);
      checkPassMapper.updateByPrimaryKeySelective(record);
    }
  }


  /**
   * 
   *〈简述〉push
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param id
   * @param userId
   */
  private void push(String id,String userId){
    SupplierCheckPass checkPass = checkPassMapper.selectByPrimaryKey(id);
    Todos todos = new Todos();
    todos.setUrl("supplierAudit/essential.html?supplierId="+checkPass.getSupplier().getId());
    todos.setName(checkPass.getSupplier().getName()+"供应商实地考察");
    todosService.updateByUrl(todos);
    //推送者id
    //发送人id
    todos.setSenderId(userId);
    //机构id
    todos.setOrgId(checkPass.getSupplier().getProcurementDepId());
    //权限id
    todos.setPowerId(PropUtil.getProperty("gsyfs"));
    //url
    todos.setUrl("supplierAudit/essential.html?supplierId=" + checkPass.getSupplier().getId());
    //类型
    todos.setUndoType((short) 1);
    todosService.insert(todos);
    //更新待考察
    supplierAuditService.findBySupplierId(checkPass.getSupplier().getId());
    Supplier supplier = new Supplier();
    supplier.setId(checkPass.getSupplier().getId());
    supplier.setStatus(4);
    supplierAuditService.updateStatus(supplier);

  }

  private String[] ratio(Integer key){
    String[] str = null;
    switch (key) {
      case 1:
        str= new String[]{"100"};
        break;
      case 2:
        str= new String[]{"70","30"};
        break;
      case 3:
        str= new String[]{"50","30","20"};
        break;
      case 4:
        str= new String[]{"40","30","20","10"};
        break;
    }
    return str;

  }

  /**
   * 
   *〈简述〉根据包id获取包下为发送通知的供应商和中标未中标的供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param checkPass 对象
   * @return 包集合
   */
  public List<SupplierCheckPass> listCheckPass(SupplierCheckPass checkPass){
    return checkPassMapper.listCheckPass(checkPass);
  }
  public List<SupplierCheckPass> listCheckPassBD(SupplierCheckPass checkPass){
    return checkPassMapper.listCheckPassBD(checkPass);
  }

  /**
   *〈简述〉 查询每包是否都选择了中标供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param projectId 项目id
   * @return 集合
   */
  public String[] selectWonBid(String projectId){
    return checkPassMapper.selectWonBid(projectId);
  }

  @Override
  public SupplierCheckPass findByPrimaryKey(String id) {
    return checkPassMapper.selectByPrimaryKey(id);
  }

  @Override
  public void delete(String id) {
    checkPassMapper.deleteByPrimaryKey(id);
  }

  @Override
  public void insert(SupplierCheckPass record) {
    checkPassMapper.insertSelective(record);
  }
  /**
   * 实际成交金额 
   * @see bss.service.ppms.SupplierCheckPassService#amountRransaction(java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String)
   */
  @Override
  public String amountRransaction(String[] priceRatio, String[] SupplierId, String[] detail,
                                  String packageId) {
    return packageId;
  }

  /**
   *〈简述〉根据传过来的ID确定为中标
   *〈详细描述〉
   * @author Ma Mingwei
   * @param packageId   供应商id,是一个","分开的字符串
   */
	@Override
	public void changeSupplierWonTheBidding(String packageId, String priceRatio) {
		// TODO Auto-generated method stub
		String[] pids = packageId.split(",");
		String[] priceRatios = priceRatio.split(",");
		for (int i = 0;i < pids.length;i++) {
			SupplierCheckPass record = new SupplierCheckPass();
			record.setId(pids[i]);
			record.setIsWonBid((short)1);
			Date cDate = new Date();//把当前时间作为操作人确定中标供应商的时间
			record.setConfirmTime(cDate);
			record.setUpdatedAt(cDate);
			if(priceRatios.length > (i + 1)) {
			}
			record.setPriceRatio(priceRatios[i]);
			checkPassMapper.updateByPrimaryKeySelective(record);
		}
	}
	@Override
	public List<SupplierCheckPass> getByContractId(String contractId){
		return checkPassMapper.getByContractId(contractId);
	}
	@Override
	public List<SupplierCheckPass> selectPackageIdWonBid(String packageId) {
		return checkPassMapper.selectPackageIdWonBid(packageId);
	}

	@Override
	public List<SupplierCheckPass> listsupplier(HashMap<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return checkPassMapper.listsupplier(map);
	}

	@Override
	public List<SupplierCheckPass> listCheckPassOrderRanking(String packId) {
		return checkPassMapper.listCheckPassOrderRanking(packId);
	}

    @Override
    public Boolean checkpassId(String ids, String priceRatios) {
        Boolean flag = true;
        String[] id = ids.split(StaticVariables.COMMA_SPLLIT);
        String[] ratios = priceRatios.split(StaticVariables.COMMA_SPLLIT);
        for (int i = 0; i < id.length; i++ ) {
            SupplierCheckPass pass = checkPassMapper.selectByPrimaryKey(id[i]);
            if(pass != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("packageId", pass.getPackageId());
                List<AdvancedDetail> selectByAll = detailMapper.selectByAll(map);
                if(selectByAll != null && !selectByAll.isEmpty()){
                    for (AdvancedDetail detail : selectByAll) {
                        BigDecimal decimal = new BigDecimal(ratios[i]);
                        BigDecimal multiply = detail.getPurchaseCount().multiply(decimal);
                        BigDecimal divide = multiply.divide(new BigDecimal("100"));
                        if(new BigDecimal(divide.intValue()).compareTo(divide) != 0){
                            flag = false;
                            continue;
                        }
                    }
                } else {
                	List<ProjectDetail> projectDetails = projectDetailMapper.selectByPackageId(pass.getPackageId());
                	if (projectDetails != null && !projectDetails.isEmpty()) {
						for (ProjectDetail projectDetail : projectDetails) {
	                        BigDecimal decimal = new BigDecimal(ratios[i]);
	                        BigDecimal multiply = new BigDecimal(projectDetail.getPurchaseCount()).multiply(decimal);
	                        BigDecimal divide = multiply.divide(new BigDecimal("100"));
	                        if(new BigDecimal(divide.intValue()).compareTo(divide) != 0){
	                            flag = false;
	                            continue;
	                        }
						}
					}
                }
            }
        }
        return flag;
    }

    @Override
    public List<SupplierCheckPass> checkPassSupplier(String supplierIds, String packageId) {
        SupplierCheckPass checkPass = new SupplierCheckPass();
        String[] supplierId = supplierIds.split(StaticVariables.COMMA_SPLLIT);
        String str_id = "";
        for (String id : supplierId) {
            str_id += "'" + id + "',";
        }
        str_id = str_id.substring(0,str_id.lastIndexOf(StaticVariables.COMMA_SPLLIT));
        str_id = "(" + str_id + ")";
        //checkPass.setId(str_id);
        checkPass.setSupplierId(str_id);
        //查询是否中标条件---已中标的
        checkPass.setIsWonBid((short)1);
        checkPass.setPackageId(packageId);
        List<SupplierCheckPass> listSupplierCheckPass = checkPassMapper.listCheckPassBD(checkPass);
        if(listSupplierCheckPass != null && !listSupplierCheckPass.isEmpty()){
            for (SupplierCheckPass supplierCheckPass : listSupplierCheckPass) {
                if(StringUtils.isNotBlank(supplierCheckPass.getSupplierId()) && StringUtils.isNotBlank(supplierCheckPass.getPackageId())){
                    HashMap<String, Object> map=new HashMap<String, Object>();
                    map.put("supplierId", supplierCheckPass.getSupplierId());
                    map.put("packageId", supplierCheckPass.getPackageId());
                    List<theSubject> theSubjects = theSubjectMapper.selectBysupplierIdAndPackagesId(map);
                    if(theSubjects != null && !theSubjects.isEmpty()){
                        BigDecimal totalAmount = BigDecimal.ZERO;
                        for (theSubject theSubject : theSubjects) {
                            if(theSubject.getDetailId()!=null){
                                Double sum= theSubject.getPurchaseCount()==null?0.0:Double.parseDouble(theSubject.getPurchaseCount());
                                BigDecimal price = theSubject.getUnitPrice()==null?new BigDecimal(0):theSubject.getUnitPrice();
                                BigDecimal sumB = new BigDecimal(Double.toString(sum));  
                                BigDecimal multiply = sumB.multiply(price);
                                totalAmount = totalAmount.add(multiply);
                            }
                        }
                        totalAmount=totalAmount.divide(new BigDecimal(10000+""));
                        supplierCheckPass.setMoney(totalAmount);
                        supplierCheckPass.setSubjects(theSubjects);
                    }
                    Quote quote = new Quote();
                    quote.setPackageId(packageId);
                    quote.setSupplierId(supplierCheckPass.getSupplier().getId());
                    List<Quote> quoteList = quoteMapper.selectQuoteHistory(quote);
                    supplierCheckPass.getSupplier().setListQuote(quoteList);

                     String method = bidMethodService.getMethod(supplierCheckPass.getProjectId(),supplierCheckPass.getPackageId());
                      if(StringUtils.isNotBlank(method)){
                          if("PBFF_JZJF".equals(method)||"PBFF_ZDJF".equals(method)){
                              supplierCheckPass.setTotalScoreString("");
                          }else if("OPEN_ZHPFF".equals(method)){
                              supplierCheckPass.setTotalScoreString(supplierCheckPass.getTotalScore()+"");
                          }
                      }
                }
            }
        }
    
        return listSupplierCheckPass;
    }
}
