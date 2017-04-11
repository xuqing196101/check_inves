package bss.service.pms;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.PurchaseDetail;


import ses.model.oms.Orgnization;

/**
 * 
 * @Title: PurcharseRequiredService
 * @Description: 采购需求计划 业务接口
 * @author Li Xiaoxiao
 * @date  2016年9月12日,下午1:55:52
 *
 */
public interface PurchaseDetailService {
	/**
	 * 
	* @Title: add
	* @Description: 添加采购计划
	* author: Li Xiaoxiao 
	* @param @param purcharseRequired     
	* @return void     
	* @throws
	 */
	public void add(PurchaseDetail purchaseDetail);
	/**
	 * 
	* @Title: update
	* @Description: 修改采购计划 
	* author: Li Xiaoxiao 
	* @param @param purcharseRequired     
	* @return void     
	* @throws
	 */
	public void update(Map<String,Object> map);
	
	public void updateByPrimaryKeySelective(PurchaseDetail purchaseDetail);
	/**
	 * 
	* @Title: queryById
	* @Description: 根据id查询 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return PurchaseDetail     
	* @throws
	 */
	PurchaseDetail queryById(String id);
	/**
	 * 
	* @Title: query
	* @Description: 查询采购计划
	* author: Li Xiaoxiao 
	* @param @param purcharseRequired
	* @param @return     
	* @return List<PurchaseDetail>     
	* @throws
	 */
	List<PurchaseDetail> query(PurchaseDetail purchaseDetail,Integer page);
	/**
	 * 
	* @Title: queryByNo
	* @Description: 根据编号查询最新的状态
	* author: Li Xiaoxiao 
	* @param @param no
	* @param @return     
	* @return String     
	* @throws
	 */
	String queryByNo(String no);
	/**
	 * 
	* @Title: delete
	* @Description: 逻辑删除一条数据
	* author: Li Xiaoxiao 
	* @param @param planNo     
	* @return void     
	* @throws
	 */
	void delete(String planNo);
	/**
	 * 
	* @Title: update
	* @Description: 修改计划状态
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @param status     
	* @return void     
	* @throws
	 */
	void updateStatus(PurchaseDetail purchaseDetail);
	/**
	 * 
	* @Title: getByMap
	* @Description: 根据map集合查询 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<PurchaseDetail>     
	* @throws
	 */
	List<PurchaseDetail> getByMap(Map<String,Object> map);
	
	/**
	 * 
	* @Title: statisticDepartment
	* @Description: 按需求部门统计 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
	List<Map<String,Object>> statisticDepartment(Map<String,Object> map);
	
	/**
	 * 
	* @Title: selectByParentId
	* @author FengTian
	* @date 2016-10-17 上午9:45:07  
	* @Description: 根据父节点查询子节点 
	* @param @param map
	* @param @return      
	* @return List<PurchaseDetail>
	 */
	List<PurchaseDetail> selectByParentId(Map<String, Object> map);
	
	List<PurchaseDetail> selectByParent(Map<String, Object> map);
	/**
	* @Title: statisticPurchaseMethod
	* @Description: 按采购方式统计
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
	List<Map<String,Object>> statisticPurchaseMethod(Map<String,Object> map);
	
	/**
	 * 
	* @Title: statisticByMonth
	* @Description: 按月份统计 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
	List<Map<String,Object>> statisticByMonth(Map<String,Object> map);
	
	/**
	 * 
	* @Title: statisticOrg
	* @Description: 查看采购分布图 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
	List<Map<String,Object>> statisticOrg(Map<String,Object> map);
	
	
	/**
	 * @throws IOException 
	 * 
	* @Title: batchAdd
	* @Description: 批量插入
	* author: Li Xiaoxiao 
	* @param @param list     
	* @return void     
	* @throws
	 */
	void  batchAdd(List<PurchaseDetail> list) throws IOException;
	
	List<PurchaseDetail> getByProjectStatus(String id, int projectStatus);
	
	
	void updateProjectStatus(String planNo);
	
	void updateIdById(Map<String,Object> map);
	
	/**
	 * 
	* @Title: seqAgain
	* @Description: 需求计划序号排序
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return List<PurchaseDetail>     
	* @throws
	 */
	public List<PurchaseDetail> seqAgain(String id);
	
	
	
	public List<PurchaseDetail> queryList(PurchaseDetail purchaseDetail);
	
	
	public Orgnization queryByName(String name);
	
	/**
	 * 
	* @Title: queryOrg
	* @Description:查询机构然后进行下达
	* author: Li Xiaoxiao 
	* @param @param list
	* @param @return     
	* @return List<Map<String,Object>>     
	* @throws
	 */
    List<String> queryOrg(String  uniqueId);
    
    /**
     * 
    * @Title: queryOrg
    * @Description: 不分页查询 明细
    * author: Li Xiaoxiao 
    * @param @param PurchaseDetail
    * @param @return     
    * @return List<PurchaseDetail>     
    * @throws
     */
    List<PurchaseDetail> queryUnique(PurchaseDetail purchaseDetail); 
    
    
    /**
     * 
    * @Title: queryUnique
    * @Description:根据唯一
    * author: Li Xiaoxiao 
    * @param @param unique
    * @param @return     
    * @return List<PurchaseDetail>     
    * @throws
     */
    List<PurchaseDetail> getUnique(String unique,String org,String dep); 
    
    /**
     * 
    * @Title: getChilden
    * @Description: 根据当前节点查询有几个子节点
    * author: Li Xiaoxiao 
    * @param @param children
    * @param @return     
    * @return Integer     
    * @throws
     */
    Integer getChilden(String children);
    
    
    /**
     * 
    * @Title: groupDetail
    * @Description: 按需求部门查询明细
    * author: Li Xiaoxiao 
    * @param @param uniqueId
    * @param @return     
    * @return List<PurchaseDetail>     
    * @throws
     */
    List<PurchaseDetail> groupDetail(String uniqueId);
    
    /**
     * 
    * @Title: groupDetail
    * @Description:根据uniqueId获取对应的明细
    * author: Li Xiaoxiao 
    * @param @param uniqueId
    * @param @return     
    * @return List<PurchaseDetail>     
    * @throws
     */
    List<PurchaseDetail> getUniqueId(String uniqueId);
    
    /**
     * 
    * @Title: queryDepartment
    * @Description: 查询所有的需求部门 
    * author: Li Xiaoxiao 
    * @param @param uniqueId
    * @param @return     
    * @return List<String>     
    * @throws
     */
    List<String> queryDepartment(String uniqueId,Integer page);
    
    
}
