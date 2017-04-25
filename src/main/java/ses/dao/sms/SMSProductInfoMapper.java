package ses.dao.sms;

import ses.model.sms.SMSProductInfo;

public interface SMSProductInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SMSProductInfo record);

    int insertSelective(SMSProductInfo record);

    SMSProductInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SMSProductInfo record);

    int updateByPrimaryKeyWithBLOBs(SMSProductInfo record);

    int updateByPrimaryKey(SMSProductInfo record);
    /**
     * 根据id 查询 数量
     * @param id
     * @return
     */
    int countById(String id);
    /**
     * 
    * @Title: findSMSProdcutInfo 
    * @Description: 查询商品描述信息以及参数信息
    * @author Easong
    * @param @param map
    * @param @return    设定文件 
    * @return SMSProductInfo    返回类型 
    * @throws
     */
    SMSProductInfo findSMSProdcutInfo(String pid);
    /**
     * 根据 产品主信息 id 查询数据
     * @param proudctBasicId
     * @return
     */
    SMSProductInfo selectByBasicId(String proudctBasicId);
}