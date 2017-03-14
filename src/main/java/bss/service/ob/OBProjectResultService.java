package bss.service.ob;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.ob.BidProductVo;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;

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
     * <p>Description 根据竞价Id和供应商Id查询竞价结果  PSId  project supplier id</p>
     * @author Ma Mingwei
     * @param obProjectResult
     * @return 竞价管理-结果查询 页面信息封装对象
     */
    ConfirmInfoVo selectInfoByPSId(OBProjectResult obProjectResult);
    
    List<BidProductVo> selectProductBySupplierId(OBProjectResult obProjectResult);
}
