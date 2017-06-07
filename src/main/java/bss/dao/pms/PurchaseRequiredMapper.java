package bss.dao.pms;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.PurchaseRequired;
/**
 * 
 * @Title: PurchaseRequiredMapper
 * @Description:采购需求计划dao接口  
 * @author Li Xiaoxiao
 * @date  2016年9月12日,下午1:57:39
 *
 */
public interface PurchaseRequiredMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @Description:根据id删除
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return int     
	* @throws
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 
    * @Title: insert
    * @Description: 插入 
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return int     
    * @throws
     */
    int insert(PurchaseRequired record);
    /**
     *
    * @Title: insertSelective
    * @Description: 插入一条数据 
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return int     
    * @throws
     */
    int insertSelective(PurchaseRequired record);
    /**
     * 
    * @Title: selectByPrimaryKey
    * @Description: 根据id查询
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return PurchaseRequired     
    * @throws
     */
    PurchaseRequired selectByPrimaryKey(String id);
    /***
     * 
    * @Title: updateByPrimaryKeySelective
    * @Description: 修改数据
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return int     
    * @throws
     */
    int updateByPrimaryKeySelective(PurchaseRequired record);
    /**
     * 
    * @Title: updateByPrimaryKey
    * @Description: 修改数据
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return int     
    * @throws
     */
    int updateByPrimaryKey(PurchaseRequired record);
    
    /**@
     * 
    * @Title: query
    * @Description: 根据实体类查询
    * author: Li Xiaoxiao 
    * @param @param purchaseRequired
    * @param @return     
    * @return List<PurchaseRequired>     
    * @throws
     */
    List<PurchaseRequired> query(PurchaseRequired purchaseRequired);
    
    /**
     * 
    * @Title: queryByNo
    * @Description: 根据编号查询最新状态
    * author: Li Xiaoxiao 
    * @param @param no
    * @param @return     
    * @return List<PurchaseRequired>     
    * @throws
     */
    List<PurchaseRequired> queryByNo(@Param("planNo")String planNo);
    /**
     * 
    * @Title: delete
    * @Description:根据编号进行逻辑删除
    * author: Li Xiaoxiao 
    * @param @param planNo     
    * @return void     
    * @throws
     */
    void delete(@Param("planNo")String planNo);
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
    void updateStatus(PurchaseRequired purchaseRequired);
    /**
     * 
    * @Title: getByPurchaseRequired
    * @Description: 根据map集合查询所有属性 
    * author: Li Xiaoxiao 
    * @param @param purchaseRequired
    * @param @return     
    * @return List<PurchaseRequired>     
    * @throws
     */
    List<PurchaseRequired> getByMap(Map<String,Object> map);
    
    /**
     * 
    * @Title: statisticDepartment
    * @Description: 按需求部门统计
    * author: Li Xiaoxiao 
    * @param @param purchaseRequired
    * @param @return     
    * @return List<PurchaseRequired>     
    * @throws
     */
    List<Map<String,Object>> statisticDepartment(Map<String,Object> map);
    /**
     * 
    * @Title: selectByParentId
    * @author FengTian
    * @date 2016-10-17 上午9:43:33  
    * @Description: 根据父节点查询子节点 
    * @param @param map
    * @param @return      
    * @return List<PurchaseRequired>
     */
    List<PurchaseRequired> selectByParentId(Map<String, Object> map);
    
 
    List<PurchaseRequired> selectByParent(Map<String, Object> map);
 
    /**
     * 
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
    * @Description: 按月统计 
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
    * @Description: 统计采购分布 
    * author: Li Xiaoxiao 
    * @param @param map
    * @param @return     
    * @return List<Map<String,Object>>     
    * @throws
     */
    List<Map<String,Object>> statisticOrg(Map<String,Object> map);
    
    /**
     * 
    * @Title: history
    * @Description: 历史修改记录
    * author: Li Xiaoxiao 
    * @param @param record     
    * @return void     
    * @throws
     */
    void history(Map<String,Object> map);
    
    
    void batchAdd(List<PurchaseRequired> list);
    
    
    List<PurchaseRequired> getByProjectStatus(@Param("id")String id,@Param("projectStatus")int i);
    
    
    void updateProjectStatus(String planNo);
    
    void updateIdById(Map<String,Object> map);
    
    List<String> queryDepartMent(List<String>  list);
    /**
     * 
    * @Title: seqAgain
    * @Description: 查询需求计划
    * author: Li Xiaoxiao 
    * @param @param purchaseRequired
    * @param @return     
    * @return PurchaseRequired     
    * @throws
     */
    PurchaseRequired seqAgain(PurchaseRequired purchaseRequired);
    /**
     * 
    * @Title: queryDepartMent
    * @Description: 查询所有的采购机构
    * author: Li Xiaoxiao 
    * @param @param list
    * @param @return     
    * @return List<String>     
    * @throws
     */
    List<Map<String,Object>> queryOrg(List<String>  list);
    
    
    
    /**
     * 
    * @Title: queryByUinuqe
    * @Description: TODO 
    * author: Li Xiaoxiao 
    * @param @param PurchaseRequired
    * @param @return     
    * @return List<PurchaseRequired>     
    * @throws
     */
    List<PurchaseRequired> queryByUinuqe(PurchaseRequired PurchaseRequired);
    
    
    
    /**
     * 
    * @Title: queryByUinuqe
    * @Description: 根据 uniqueId查询计划的明细
    * author: Li Xiaoxiao 
    * @param @param uniqueId
    * @param @return     
    * @return List<PurchaseRequired>     
    * @throws
     */
    List<PurchaseRequired> getByUinuqeId(@Param("uniqueId")String uniqueId);
    
    /**
     * 
    * @Title: queryByParentId
    * @Description: 根据父id查询当前父节点有几个自己点
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return Integer     
    * @throws
     */
    
    Integer queryChilden(@Param("id")String id);
    
    /**
     * 
    * @Title: getByDep
    * @Description:根据需求部门查询
    * author: Li Xiaoxiao 
    * @param @param map
    * @param @return     
    * @return List<PurchaseRequired>     
    * @throws
     */
    List<PurchaseRequired> getByDep(Map<String,Object> map);
    /**
     * 
    * @Title: uddateByUniqueId
    * @Description: 根据UniqueId修改
    * author: Li Xiaoxiao 
    * @param @param uniqueId     
    * @return void     
    * @throws
     */
    void uddateByUniqueId(@Param("uniqueId")String uniqueId);
    
    /**
     * 
    * @Title: getUniqueId
    * @Description:根据id查询uniqueId 
    * author: Li Xiaoxiao 
    * @param @param list
    * @param @return     
    * @return List<String>     
    * @throws
     */
    List<String> getUniqueId(List<String> list);
    
    /**
     * 
     *〈资源展示获取各类型需求金额〉
     *〈详细描述〉
     * @author FengTian
     * @param planType
     * @return
     */
    BigDecimal selectBudget(@Param("planType") String planType);
    
}