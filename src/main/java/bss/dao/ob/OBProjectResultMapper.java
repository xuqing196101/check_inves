package bss.dao.ob;

import bss.model.ob.BidProductVo;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OBProjectResultMapper {
    int countByExample(OBProjectResultExample example);
    /**
     *  成交供应商 数量
     * @param map
     * @return
     */
    Integer countByStatus(Map<String,Object> map);
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
     * <p>根据供应商查找竞价商品</p>
     * @param supplierId
     * @return
     */
    List<BidProductVo> selectProductBySupplierId(OBProjectResult oBProjectResult);
    
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
     * 获取未确认 的供应商数据
     * @author Yanghongliang
     * @return list
     */
    List<OBProjectResult> selectNotSuppler(String projectID);
    /**
     * 获取是否第二轮
     * 
     */
    List<OBProjectResult> selectSecondRound(String projectID);
    
    /**
     * <p>Description 根据竞价Id和供应商Id查询竞价结果  PSId  project supplier id</p>
     * @author Ma Mingwei
     * @param obProjectResult
     * @return 竞价管理-结果查询 页面信息封装对象
     */
    ConfirmInfoVo selectInfoByPSId(OBProjectResult obProjectResult);
}