package bss.dao.ob;

import bss.model.ob.BidProductVo;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import bss.model.ob.SupplierProductVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OBProjectResultMapper {
    int countByExample(OBProjectResultExample example);
    /**
     * @author MaMingwei
     * @description 根据供应商和标题的ID查询总条数
     * @param example主要传过来projectId和供应商ID
     * @return 返回总条数
     */
    int countByPSID(OBProjectResult example);
    /**
     * @author MaMingwei
     * @param example主要传过来projectId
     * @return 返回查找到占比值总和
     */
    OBProjectResult selectProportionByProject(OBProjectResult example);
    /**
     * 全部状态 和现实 状态
     * @author YangHongLiang
     * @param projectId
     * @return
     */
    List<OBProjectResult> getSecond(String projectId);
    /**
     * 获取成交的比例
     * @author Yanghongliang
     * @param projectId
     * @return
     */
    String getProportionSum(String projectId);
    /**
     *  成交供应商 数量
     * @param map
     * @return
     */
    Integer countByStatus(@Param("projectId")String projectId); 
    /**
     * 获取确认供应商
     * @param projectId
     * @return
     */
    int getCountSupplier(@Param("projectId")String projectId);
    /**
     * 获取 结果供应商
     * @param projectId
     * @return
     */
    List<OBProjectResult> selectByPID(@Param("projectId")String projectId);
    /**
     * 获取竞价参与 全部排名
     * @param projectId
     * @return
     */
    List<OBProjectResult> getStatus(@Param("projectId")String projectId);
    /**
     * 获取供应商 结果人数
     * @param projectId
     * @return
     */
    List<OBProjectResult> selectSupplierPeo(@Param("projectId")String projectId);
    /**
     * 获取 确认的总比例
     * @param projectId
     * @return
     */
    OBProjectResult getAllProportion(@Param("projectId")String projectId);
    /**
     *  中标供应商 数量
     * @param map
     * @return
     */
    Integer countProportion(@Param("projectId")String projectId);
    /**
     * 根据产品 id/竞价id获取已经成交的数量
     * @param example
     * @return
     */
    int countByStatus(OBProjectResult example);
    
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
     * Description: 根据竞价信息查询竞价结果信息
     * 
     * @author  zhang shubin
     * @version  2017年3月11日 
     * @param  @param supplierId
     * @param  @return 
     * @return List<OBProjectResult> 
     * @exception
     */
    List<OBProjectResult> selectByProjectId(String supplierId);
    
    /**
     * @author Ma Mingwei
     * <p>根据供应商查找竞价的商品</p>
     * @param supplierId
     * @return
     */
    List<BidProductVo> selectProductBySupplierId(OBProjectResult oBProjectResult);
     /**
      * @author Ma Mingwei
      * <p>根据供应商查找竞价表里的商品信息</p>
      * @param supplierId
      * @return
      */
     List<BidProductVo> selectResultProductBySupplierId(OBProjectResult oBProjectResult);
    
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
	List<OBProjectResult> selectSupplierStatus(OBProjectResult oBProjectResult);
    /**
     * 获取未确认 的供应商数据
     * @author Yanghongliang
     * @return list
     */
    List<OBProjectResult> selectNotSuppler(@Param("id")String projectID ,@Param("status")Integer status,@Param("proportion")String proportion);
    /**
     * 获取是否第二轮
     * @author Ma Mingwei
     */
    List<OBProjectResult> selectSecondRound(String projectID);
    
    /**
     * 根据标题id获取封装的供应商信息
     * @author Ma Mingwei
     */
    List<SupplierProductVo> selectInfoByPID(String projectID);
    
    /**
     * <p>Description 根据竞价Id和供应商Id查询竞价结果  PSId  project supplier id</p>
     * @author Ma Mingwei
     * @param obProjectResult  封装的条件对象
     * @return 竞价管理-结果查询 页面信息封装对象
     */
    ConfirmInfoVo selectInfoByPSId(OBProjectResult obProjectResult);
    /**
     * 获取竞价结果 基础信息
     * @param projectId
     * @param supplierId
     * @return
     */
    ConfirmInfoVo getBasic(@Param("projectId")String projectId,@Param("supplierId")String supplierId);
    
    /**
     * <p>Description 根据竞价Id和供应商Id查询竞价结果  PSId  project supplier id</p>
     * @author Ma Mingwei
     * @param obProjectResult  封装的条件对象
     * @return 竞价管理-结果查询 页面信息封装对象
     */
    ConfirmInfoVo selectInfoByPSIds(OBProjectResult obProjectResult);
    
    /**
     * <p>Description 把此供应商的状态都改为0，表示放弃</p>
     * @author Ma Mingwei
     * @param obProjectResult封装的条件对象
     * @return 竞价管理-结果查询 
     */
    int updateBySupplierId(OBProjectResult record);
    
    /**
     * <p>Description 根据供应商Id、产品Id和竞价标题Id修改此条信息	SPPId supplierId、productId和projectId</p>
     * @author Ma Mingwei
     * @param obProjectResult封装的条件对象
     * @return 竞价管理-结果查询   修改了几条记录数
     */
    int updateInfoBySPPId(OBProjectResult record);
    
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
     * 查询结果 页面数据
     * @author Yanghongliang
     * @param projectId
     * @param supplierId
     * @return
     */
    List<OBProjectResult> selectSupplierDate(@Param("projectId")String projectId,@Param("supplierId")String supplierId);

    
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

    
    /**
     * 
    * @Title: findConfirmResult 
    * @Description: 查询 确定第一，第二轮确认结果
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<OBProjectResult>    返回类型 
    * @throws
     */
    List<OBProjectResult> findConfirmResult(Map<String, Object> map);
    
    /**
     * 
     * Description: 查询竞价结果信息
     * 
     * @author  zhang shubin
     * @version  2017年4月5日 
     * @param  @param projectId
     * @param  @return 
     * @return List<OBProjectResult> 
     * @exception
     */
    List<OBProjectResult> selResultByProjectId(@Param("projectId") String projectId);
    
    /**
     * 
     * Description: 查询供应商确认结果占比
     * 
     * @author  zhang shubin
     * @version  2017年4月6日 
     * @param  @param projectId
     * @param  @return 
     * @return List<OBProjectResult> 
     * @exception
     */
    List<OBProjectResult> selProportion(@Param("projectId") String projectId,@Param("supplierId") String supplierId);
    /**
     * 根据竞价id 获取结果关联全部数据
     * @author YangHongLiang
     * @param projectId
     * @return
     */
    List<OBProjectResult> selectRelationDate(@Param("projectId")String projectId);
    /**
     * 根据id 获取数据的 数量
     * @param id
     * @return
     */
    Integer countById(@Param("id")String id);
}