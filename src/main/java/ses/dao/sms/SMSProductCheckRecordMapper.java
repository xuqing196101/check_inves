package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SMSProductCheckRecord;
/**
 * 
* @ClassName: SMSProductCheckRecordMapper 
* @Description: 商品审核记录Mapper
* @author Easong
* @date 2017年4月21日 下午1:46:02 
*
 */
public interface SMSProductCheckRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(SMSProductCheckRecord record);

    int insertSelective(SMSProductCheckRecord record);
    int insertBySelective(SMSProductCheckRecord record);

    SMSProductCheckRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SMSProductCheckRecord record);

    int updateByPrimaryKey(SMSProductCheckRecord record);
    /**
     * 根据id 查询 数量
     * @param id
     * @return
     */
    int countById(String id);
    /**
     * 
    * @Title: selectByProductBasicId 
    * @Description: 根据商品id查询审核记录信息
    * @author Easong
    * @param @param productBasicId
    * @param @return    设定文件 
    * @return SMSProductCheckRecord    返回类型 
    * @throws
     */
    SMSProductCheckRecord selectByProductBasicId(String productBasicId);
    /**
     * 根据创建时间范围获取数据
     * @param start
     * @param end
     * @return
     */
    List<SMSProductCheckRecord> selectByCreatedAt(@Param("start")String start,@Param("end")String end);
    
}