package bss.dao.ob;

import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBResultsInfoExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OBResultsInfoMapper {
    int countByExample(OBResultsInfoExample example);

    int deleteByExample(OBResultsInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBResultsInfo record);

    int insertSelective(OBResultsInfo record);

    List<OBResultsInfo> selectByExample(OBResultsInfoExample example);

    OBResultsInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBResultsInfo record, @Param("example") OBResultsInfoExample example);

    int updateByExample(@Param("record") OBResultsInfo record, @Param("example") OBResultsInfoExample example);

    int updateByPrimaryKeySelective(OBResultsInfo record);

    int updateByPrimaryKey(OBResultsInfo record);
    /***
     * 根据竞价id 获取 数据
     * @author YangHongLiang
     * @param secoundBidding 区分第一次报价
     */
    List<OBResultsInfo> selectByProjectId(@Param("id")String projectId,@Param("biddingId")String secoundBidding);
    /**
     * 根据竞价id 获取竞价成交金额
     * @param projectId
     * @return
     */
    List<OBResultsInfo> getDealMoney(@Param("projectId")String projectId,@Param("biddingId")String secoundBidding);
    /**
     * 
    * @Title: selectQuotoInfo 
    * @Description: 查询报价结果
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<OBResultsInfo>    返回类型 
    * @throws
     */
    List<OBResultsInfo> selectQuotoInfo(Map<String, Object> map);
    /**
     * 
     * @Title: selectQuotoInfo 
     * @Description: 查询报价结果
     * @author Easong
     * @param @param map
     * @param @return    设定文件 
     * @return List<OBResultsInfo>    返回类型 
     * @throws
     */
    List<OBResultsInfo> selectQuotoInfoSecond(Map<String, Object> map);
    /**
     * 查询报价 信息
     * @param projectId
     * @param supplierId
     * @return
     */
    List<OBResultsInfo> getProductInfo(@Param("projectId")String projectId ,@Param("supplierId")String supplierId,@Param("biddingId")String bidding);
    
    
    List<OBResultsInfo> selectResult(@Param("projectId")String projectId ,@Param("supplierId")String supplierId);
    /**
     *  根据id /次数/供应商id 获取自报价总和金额
     * @author YnagHongliang
     * @param projectId
     * @param biddingId
     * @return
     */
    List<OBResultsInfo>  selectByBidding(@Param("projectId")String projectId,@Param("biddingId")String biddingId,@Param("supplierId")String supplierId);
    /**
     * 根据id /次数/供应商id 获取自报价数量
     * @param projectId
     * @param biddingId
     * @param supplierId
     * @return
     */
    Integer countByBidding(@Param("projectId")String projectId,@Param("biddingId")String biddingId,@Param("supplierId")String supplierId);
    
    /**
     * 
    * @Title: selectFlagByQuotoSecond 
    * @Description:第二次报价前，两家供应商报价进入第二轮报价时的判断，
	* 				未报价的不能进入，只能这第一次报价的两家供应商才可以进入
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return Integer    返回类型 
    * @throws
     */
    Integer selectFlagByQuotoSecond(Map<String, Object> map);
    /**
     * 获取该竞价的全部报价数量 用于验证
     * @author YangHongLiang
     * @param projectId
     * @return
     */
    Integer countBiddingByID(@Param("projectId")String projectId);
    /**
     * 获取竞价 全部报价金额
     * @param projectId
     * @return
     */
    BigDecimal sumAllDealMoney(@Param("projectId")String projectId);
}