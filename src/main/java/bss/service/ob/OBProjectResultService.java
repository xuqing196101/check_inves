package bss.service.ob;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bss.model.ob.BidProductVo;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import bss.model.ob.OBResultSubtabulation;
import bss.model.ob.SupplierProductVo;
import common.annotation.CurrentUser;
import ses.model.bms.User;

/**
 * 
 * @author Ma Mingwei
 * @description 主要负责存储竞价结果信息
 * @method 没注释的是自动工具生成copy过来
 *
 */
public interface OBProjectResultService {
	int countByExample(OBProjectResultExample example);

    int deleteByExample(OBProjectResultExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProjectResult record);

    int insertSelective(OBProjectResult record);

    List<OBProjectResult> selectByExample(OBProjectResultExample example);
    
    /**
     * @description 根据供应商Id查询结果
     * @param id  供应商id
     * @return
     */
    List<OBProjectResult> selectBySupplierId(String supplierId);
    
    /**
     * 
     * Description: 根据竞价信息查询竞价结果信
     * 
     * @author  zhang shubin
     * @version  2017年3月11日 
     * @param  @param supplierId
     * @param  @return 
     * @return List<OBProjectResult> 
     * @exception
     */
    List<OBProjectResult> selectByProjectId(String supplierId,Integer page);

    OBProjectResult selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProjectResult record, @Param("example") OBProjectResultExample example);

    int updateByExample(@Param("record") OBProjectResult record, @Param("example") OBProjectResultExample example);

    int updateByPrimaryKeySelective(OBProjectResult record);

    int updateByPrimaryKey(OBProjectResult record);

    /**
     * @author MaMingwei
     * @param oBProjectResult
     * @return 查找到的状态
     * @description 查找符合当前竞标的供应商在 竞价结果表 中的status
     */
	String selectSupplierStatus(OBProjectResult oBProjectResult);
	/**
	 * 封装供应商 竞价结果 页面数据 
	 * @author Yanghongliang
	 * @param supplierId
	 * @param projectId
	 * @return
	 */
	ConfirmInfoVo selectSupplierDate(String supplierId,String projectId,String status);
	
	/**
     * <p>Description 根据竞价Id和供应商Id查询竞价结果  PSId  project supplier id</p>
     * @author Ma Mingwei
     * @param obProjectResult
	 * @param confirmStatus 
     * @return 竞价管理-结果查询 页面信息封装对象
     */
    ConfirmInfoVo selectInfoByPSId(OBProjectResult obProjectResult, String confirmStatus);
    
    List<BidProductVo> selectProductBySupplierId(OBProjectResult obProjectResult);
    
    /**
     * <p>Description 把此供应商的状态都改为0，表示放弃</p>
     * @author Ma Mingwei
     * @param confirmStatus 
     * @param obProjectResult封装的条件对象
     * @return 竞价管理-结果查询 
     */
    boolean updateBySupplierId(String projectId,String supplierId, String confirmStatus,String projectResultId);
    
    /**
     * <p>Description 根据供应商Id、产品Id和竞价标题Id修改此条信息	SPPId supplierId、productId和projectId</p>
     * @author Ma Mingwei
     * @param string 
     * @param obProjectResult封装的条件对象
     * @return 竞价管理-结果查询   修改了几条记录数
     */
	public int updateInfoBySPPIdList(User user, List<OBProjectResult> projectResultList, String confirmNum);
	/**
	 * 更新 供应商结果 数据
	 * @author YanghongLiang
	 * @param user
	 * @param projectResultList
	 * @return
	 */
	String updateResult(User user,List<OBResultSubtabulation> projectResultList,String acceptNum);
	/**
     * 根据标题id获取封装的供应商信息
     * @author Ma Mingwei
     */
    List<SupplierProductVo> selectInfoByPID(String projectID, String supplierID);
    
    /**
     * 
    * @Title: findSupplierUnBidding 
    * @Description: 查询未中标的供应商
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<OBProjectResult>    返回类型 
    * @throws
     */
    List<OBProjectResult> findSupplierUnBidding(Map<String, Object> map);
    
    /**
     * 
     * Description: 分组查询竞价结果信息
     * 
     * @author  zhang shubin
     * @version  2017年4月1日 
     * @param  @param projectId
     * @param  @return 
     * @return List<OBProjectResult> 
     * @exception
     */
    
    List<OBProjectResult> selByProjectId(@Param("projectId") String projectId);
    
    /** @Title: findConfirmResult 
    * @Description: 查询 确定第一，第二轮确认结果
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return OBProjectResult    返回类型 
    * @throws
     */
    OBProjectResult findConfirmResult(Map<String, Object> map);
}
