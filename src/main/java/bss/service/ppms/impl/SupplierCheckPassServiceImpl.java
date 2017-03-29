package bss.service.ppms.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.model.ppms.Packages;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SupplierCheckPass;
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
}
