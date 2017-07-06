package ses.service.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;

/**
 * 
 * @Title: PurChaseDepOrg
 * @Description: 机构 多对多服务
 * @author: Tian Kunfeng
 * @date: 2016-9-26上午10:30:11
 */
public interface PurChaseDepOrgService {
  int  saveByMap(HashMap<String, Object> map);
  int delByMap(HashMap<String, Object> map);
  int delByOrgId(HashMap<String, Object> map);
  List<PurchaseOrg> selectById(HashMap<String, Object> map);
  PurchaseDep findByOrgId (String id);
  
  /**
   * 
  * @Title: getDep
  * @Description: 获得所有有资格的采购机构
  * author: Li Xiaoxiao 
  * @param @return     
  * @return List<PurchaseDep>     
  * @throws
   */
  List<PurchaseDep> getDep();
  
  	/**
	 * 查询全部机构
	 * @return
	 */
  List<PurchaseDep> findAllOrg();
  
}
