package ses.service.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.oms.Orgnization;
import ses.model.oms.util.Ztree;


public interface OrgnizationServiceI {
	
	
    public List<Orgnization> findOrgnizationList(HashMap<String,Object> map);
    /***
     * 
     *〈简述〉根据父级Id和类型进行查询
     *〈详细描述〉
     * @author myc
     * @param root 根目录的PID
     * @param pid 父级treeId
     * @param type 所属类别
     * @return 
     */
    public List<Ztree> findOrgByPidAndType(String pid, String type);
	
    /**
     * 〈简述〉 获取全部可用的采购机构信息  服务接口
     *〈详细描述〉
     * @author YangHongLiang
     * @return JSON
     */
    public String getMechanism();
    
    /**
     * 
     *〈简述〉保存
     *〈详细描述〉
     * @author myc
     * @param org Orgnization对象
     * @param depIds  管理机构Id
     * @return 成功返回0
     */
    int saveOrgnization(Orgnization org,String depIds);
	
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param map 对象集合
     * @return
     */
    int updateOrgnization(Orgnization org,String depIds);
    
	
    List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map);
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉根据ppurchaseDepId 查询 
     * @author myc
     * @param map
     * @return
     */
    List<Orgnization> getRelaPurchaseOrgList(HashMap<String, Object> map);
	
    int delOrgnizationByid(HashMap<String, Object> map);
	
    void updateOrgnizationById(Orgnization orgnization);
	
    List<Orgnization> findByName(Map<String, Object> map);
	
    Orgnization  findByCategoryId(String id);
	
    int updateByCategoryId(Orgnization orgnization);
	
    List<Orgnization> selectByPrimaryKey(Map<String,Object> map);
    
    
    /**
     * 
     *〈简述〉
     * 根据主键查询组织机构对象
     *〈详细描述〉
     * @author myc
     * @param id
     * @return 
     */
    Orgnization getOrgByPrimaryKey(String id);
	
    /**
     * 
     * 〈简述〉 获取需求部门数据 〈详细描述〉
     * 
     * @author myc
     * @param map
     * @return
     */
	List<Orgnization> getNeedOrg(Map<String, Object> map);
	
	/**
	 * 
	 *〈简述〉
	 *  根据主键逻辑删除
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 * @return 成功返回ok
	 */
	public String delOrg(String id);
	
	/**
	 * 
	 *〈简述〉 根据类型查询组织机构
	 *〈详细描述〉
	 * @author myc
	 * @param type 类型
	 * @return Orgnization集合
	 */
	List<Orgnization> initOrgByType(String type);

	/**
	 * 
	 *〈简述〉删除组织机构/管理部门用户
	 *〈详细描述〉
	 * @author myc
	 * @param idsString id集合
	 * @param orgType 组织机构类型
	 * @return
	 */
	public String delUsers(String idsString, String orgType);
	
	List<Orgnization> selectedItem(String selectedItem);
	
	Orgnization selectByName(String name);
	
	List<Orgnization> findOrgPartByParam(Map<String,Object> map);
	
	/**
	 * 
	 *〈简述〉移动排序
	 *〈详细描述〉
	 * @author myc
	 * @param id 当前对象Id
	 * @param targetId 目标Id
	 * @param moveType 移动类型
	 * @return
	 */
    public String moveOrder(String id, String targetId, String moveType);
    
    /**
     * 
    * @Title: orderPosition
    * @Description:移动排序 
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @param targetId     
    * @return void     
    * @throws
     */
    public void orderPosition(String id, Integer position,String targetId,Integer position2);
    
    public void sameDep(String id, Integer position,String targetId,Integer position2,String type);
    
    Boolean verify(Orgnization orgnization);
    
    
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

    String selectById(String id);
    /**
     * 获取需求产品 管理部门 相关数据
     * @author YangHongliang
     * @return
     */
    public List<Orgnization> selectByType();
    /**
     * 根据 用户id 获取 机构名称
     * @param userId
     * @return
     */
    public List<String> findByUserid(String userId);
    /**
     *〈简述〉如果是采购机构，按排序查询
     *〈详细描述〉
     * @author Ye MaoLin
     * @param map
     * @return
     */
    public List<Orgnization> findPurchaseOrgByPosition(HashMap<String, Object> map);
}
