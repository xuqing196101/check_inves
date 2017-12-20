package ses.dao.oms;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeBigDecimal;
import ses.model.bms.AnalyzeVo;
import ses.model.oms.Orgnization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public interface OrgnizationMapper {
	
    int saveOrgnization(HashMap<String, Object> map);
	
	List<Orgnization> findOrgnizationList(HashMap<String, Object> map);
	
	int updateOrgnization(HashMap<String, Object> map);
	
	List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map);
	
	int delOrgnizationByid(HashMap<String, Object> map);
	
	void updateOrgnizationById(Orgnization orgnization);
    
	Orgnization  findByCategoryId(String id);
	
    List<Orgnization> findByName(Map<String, Object> map);
	
	int updateByCategoryId(Orgnization orgnization);
	
	List<Orgnization> selectByPrimaryKey(Map<String,Object> map);
	
	Orgnization selectByName(String name);
	
	List<Orgnization> verify(HashMap<String, Object> map);
	
	/**
	 * 
	 *〈简述〉
	 * 获取需求部门
	 *〈详细描述〉
	 * @author myc
	 * @param map
	 * @return
	 */
    List<Orgnization> findOrgPartByParam(Map<String, Object> map);
    
    /**
     * 
     *〈简述〉
     * 根据主键逻辑删除部门
     *〈详细描述〉
     * @author myc
     * @param id 主键
     */
    void delOrgById(@Param("id")String id);
    
    /**
     * 
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return Orgnization 对象
     */
    Orgnization  findOrgByPrimaryKey(@Param("id")String id);
    
    /**
     * 
     *〈简述〉根据类型查询组织机构
     *〈详细描述〉
     * @author myc
     * @param type 类型
     * @return
     */
	List<Orgnization> findByType(String type);
	
	/**
	 * 
	 *〈简述〉根据Pid和类型查询
	 *〈详细描述〉
	 * @author myc
	 * @param pid 父级的Id
	 * @param type 对应的类型
	 * @return 组织机构的list
	 */
	List<Orgnization> getListByPidAndType(@Param("pid")String pid, @Param("typeName")String type);
	
	/**
	 * 
	 *〈简述〉根据pid和类型查询数量
	 *〈详细描述〉
	 * @author myc
	 * @param pid 主键
	 * @param type 类型
	 * @return
	 */
	Integer  getChilCountyPidAndType(@Param("pid") String pid, @Param("typeName") String type);
	
	/**
	 * 
	 *〈简述〉保存后返回主键
	 *〈详细描述〉
	 * @author myc
	 * @param orgnization {@link Orgnization}对象
	 * @return 返回主键
	 */
	void saveOrg(Orgnization orgnization);
	
	/**
     * 
    * @Title: findAllUsefulOrg
    * @author QuJie 
    * @date 2016-9-22 下午2:48:23  
    * @Description: 查询所有可用的需求部门
    * @param @param record
    * @param @return      
    * @return int
     */
    List<Orgnization> findAllUsefulOrg();
    
    
    /**
     * 
     *〈简述〉根据chaserDepId 查询
     *〈详细描述〉
     * @author myc
     * @param map
     * @return
     */
    List<Orgnization> getRelaPurchaseOrgList(HashMap<String, Object> map);
    
    /**
     * 
     *〈简述〉根据需求部门查看是否存在
     *〈详细描述〉
     * @author myc
     * @param map
     * @return
     */
    Orgnization queryByName(@Param("name")String name);
    
    /**
     * 
    * @Title: queryById
    * @Description: 根据id查询
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return Orgnization     
    * @throws
     */
    Orgnization queryById(@Param("id")String id);
    
    /**
     * 
     *〈简述〉根据父节点查询当前最大的顺序
     *〈详细描述〉
     * @author myc
     * @param parentId
     * @return
     */
    String getMaxPosition(@Param("parentId")String parentId);
    
    /**
     * 
     *〈简述〉根据父级Id查询所有的子集
     *〈详细描述〉
     * @author myc
     * @param parentId 父级Id
     * @return
     */
    List<Orgnization> getOrgByPid(@Param("parentId") String parentId, @Param("orderId") String orderId, @Param("targetOrderId")String targetOrderId);
    /**
     * 
    * @Title: queryPur
    * @Description: 查询采购机构
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return Orgnization     
    * @throws
     */
    Orgnization queryPur(@Param("id")String id);
    
    /**
     * 
    * @Title: getChildren
    * @Description:根据父级id查询所有明细 
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return List<Orgnization>     
    * @throws
     */
    List<Orgnization> getChildren(@Param("id")String id);
    /**
     * 
    * @Title: getParent
    * @Description: 根据id得到父级id 
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return List<Orgnization>     
    * @throws
     */
    List<Orgnization> getParent(@Param("id")String id);
    
    List<Orgnization>  getMove(@Param("parentId")String id,@Param("position")Integer position);
    
    List<Orgnization>  getNext(@Param("parentId")String id,@Param("position")Integer position,@Param("last")Integer last);
    
    List<Orgnization>  getPrev(@Param("parentId")String id,@Param("position")Integer position,@Param("last")Integer last);
    /**
     * 根据类型查询 相关数据
     * @author YangHongliang
     * @param typeName
     * @return
     */
    List<Orgnization> selectByType();
    
    /**
     * 获取全部可用的采购机构信息
     */
    List<Orgnization> getAllList();
    /**
     * 获取 采购机构 需求部门
     * @author YangHongLiang
     * @return
     */
    List<Orgnization> selectByTypeName();
    
    /**
     * 
     * Description: 根据采购机构简称查询
     * 
     * @author  zhang shubin
     * @version  2017年3月16日 
     * @param  @param shortName
     * @param  @return 
     * @return Orgnization 
     * @exception
     */
    Orgnization selectByShortName(String shortName);
    
    String selectById(@Param("id") String id);
    /**
     * 获取 监控 机构 名称
     * @param userid
     * @return
     */
    List<String> findByUserid(String userid);
    
    /**
     * 
    * @Title: getOrg
    * @Description: 查询采购机构
    * author: Li Xiaoxiao 
    * @param @return     
    * @return List<Orgnization>     
    * @throws
     */
	List<Orgnization> getOrg();
	
	
	void insertOrg(Orgnization orgnization);
	
	/**
	 * 
	 * Description:查询不同组织机构下的供应商
	 * 
	 * @author Easong
	 * @version 2017年5月27日
	 * @return
	 */
	List<AnalyzeBigDecimal> selectSupByOrg();
	
	/**
	 * 
	 * Description:查询不同组织机构下的专家
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	List<AnalyzeBigDecimal> selectExpByOrg();
	
	/**
	 * 
	 * Description: 查询各个省采购机构 分布
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	List<AnalyzeBigDecimal> selectOrgsByArea();
	
	/**
	 * 
	 * Description:当年各采购机构受领任务总金额
	 * 
	 * @author Easong
	 * @version 2017年6月5日
	 * @return
	 */
	List<Analyze> selectNowYearOrgContractMoney();
	
	/**
	 * 
	 * Description:当年各采购机构受领任务总金额
	 * 
	 * @author Easong
	 * @version 2017年6月5日
	 * @return
	 */
	List<Analyze> selectNowYearOrgAcceptTaskMoney();
	
	/**
	 * 
	 * Description:各采购机构完成采购项目数量及总金额 
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	List<AnalyzeVo> selectPurProjectCountAndMoney();
	
	/**
	 * 
	 * Description:各采购机构完成采购合同数量及总金额 
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	List<AnalyzeVo> selectPurContractCountAndMoney();

  /**
   *〈简述〉如果是采购机构，按排序查询
   *〈详细描述〉
   * @author Ye MaoLin
   * @param map
   * @return
   */
  List<Orgnization> findPurchaseOrgByPosition(HashMap<String, Object> map);

	/**
	 * 查询管理部门，筛选已选过的
	 * @param map
	 * @return
	 */
	List<Orgnization> findManageOfOrg(HashMap<String, Object> map);

	/**
	 * 根据机构id查询关联的orgid
	 * @param id
	 * @return
	 */
	String selOrgIdByDepId(String id);
	
	List<Orgnization> selectByIdList(HashMap<String, Object> map);
}