package ses.dao.sms;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SMSProductBasic;
/**
 * 
* @ClassName: SMSProductBasicMapper 
* @Description: 商品基本信息Mapper
* @author Easong
* @date 2017年4月20日 下午3:15:07 
*
 */
public interface SMSProductBasicMapper {
    int deleteByPrimaryKey(String id);

    int insert(SMSProductBasic record);

    int insertSelective(SMSProductBasic record);

    SMSProductBasic selectByPrimaryKey(String id);
    SMSProductBasic getByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SMSProductBasic record);

    int updateByPrimaryKey(SMSProductBasic record);
    /**
     * 根据id 查询 数量
     * @param id
     * @return
     */
    int countById(String id);
    /**
     * 
    * @Title: findAllProductLibBasicInfo 
    * @Description: 查询产品的基本信息
    * @author Easong
    * @param @return    设定文件 
    * @return List<SMSProductBasic>    返回类型 
    * @throws
     */
    List<SMSProductBasic> findAllProductLibBasicInfo(Map<String, Object> map);
    
    /**
     * 
    * @Title: findAllWaitCheck 
    * @Description: 审核查询信息
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return List<SMSProductBasic>    返回类型 
    * @throws
     */
    List<SMSProductBasic> findAllWaitCheck(Map<String, Object> map);
    
    /**
     * 
    * @Title: vartifyUniqueSKU 
    * @Description: SKU唯一校验
    * @author Easong
    * @param @param sku
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    String vertifyUniqueSKU(String sku);
    /**
     * 根据时间获取相关的数据
     * @param start
     * @return
     */
    List<SMSProductBasic> selectByCreatedAt(@Param("start")String start,@Param("end")String end);

    void updateStatusById(@Param("id") String id);
}