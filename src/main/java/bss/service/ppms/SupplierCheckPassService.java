package bss.service.ppms;

import java.math.BigDecimal;
import java.util.List;

import bss.model.ppms.Packages;
import bss.model.ppms.SupplierCheckPass;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 中标供应商
 * <详细描述>
 * @author   Wang Wenshuai
 * @version  
 * @since
 * @see
 */
public interface SupplierCheckPassService {
  /**
   * 
   *〈简述〉获取包id和包name
   *〈详细描述〉
   * @author Wang Wenshuai.
   * @param projectId 项目id
   * @return 包集合
   */
  List<Packages> getPackageName(String projectId);

  /**
   * 
   *〈简述〉获取项目包下所有信息 
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param projectId 项目id
   * @return 包集合
   */
  List<Packages> listPackage(String projectId);

  /**
   * 
   *〈简述〉根据包id获取包下为发送通知的供应商和中标未中标的供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param checkPass 对象
   * @return 包集合
   */
  List<SupplierCheckPass> listSupplierCheckPass(SupplierCheckPass checkPass);

  /**
   * 
   *〈简述>修改
   *〈详细描述〉
   * @author Wang Wenshuai
   */
  String update(SupplierCheckPass checkPass);

  /**
   * 
   *〈简述〉修改中标状态
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param id
   */
  void updateBid(String[] id,BigDecimal[] wonPrice,String userId);

  /**
   * 
   *〈简述〉根据包id获取包下为发送通知的供应商和中标未中标的供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param checkPass 对象
   * @return 包集合
   */
  List<SupplierCheckPass> listCheckPass(SupplierCheckPass checkPass);
  /**
   * @author Ma Mingwei
   * @param checkPass
   * @return 包的供应商集合
   */
  List<SupplierCheckPass> listCheckPassBD(SupplierCheckPass checkPass);

  /**
   *〈简述〉 查询每包是否都选择了中标供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param prijectId 项目id
   * @return 集合
   */
  String[] selectWonBid(String prijectId);

  /**
   * 
   * 〈简述〉 〈详细描述〉
   * 
   * @author QuJie 
   * @date 2016-12-22 下午7:40:58  
   * @Description: 根据id查找 
   * @param @param id
   * @param @return      
   * @return SupplierCheckPass
   */
  SupplierCheckPass findByPrimaryKey(String id);

  void delete(String id);

  void insert(SupplierCheckPass record);

  /**
   * 
   *〈简述〉实际成交金额计算
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param priceRatio
   * @param SupplierId
   * @param detail
   * @param packageId
   * @return
   */
  String amountRransaction(String[] priceRatio,String[] SupplierId,String[] detail,String packageId);

  /**
   *〈简述〉根据传过来的ID确定为中标
   *〈详细描述〉
   * @author Ma Mingwei
   * @param packageId   供应商id,是一个","分开的字符串
 * @param priceRatios 
   */
  void changeSupplierWonTheBidding(String packageId, String priceRatios);
  List<SupplierCheckPass> getByContractId(String contractId);
  List<SupplierCheckPass> selectPackageIdWonBid(String packageId);
}
