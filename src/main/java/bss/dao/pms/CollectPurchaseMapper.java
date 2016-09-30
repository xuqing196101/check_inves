package bss.dao.pms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.CollectPurchase;

public interface CollectPurchaseMapper {
    int insert(CollectPurchase record);

    int insertSelective(CollectPurchase record);
    List<CollectPurchase> queryByNo(@Param("planNo")String planNo);
    /**
     * 
    * @Title: getByNo
    * @Description: 根据编号得到id 
    * author: Li Xiaoxiao 
    * @param @param planNo
    * @param @return     
    * @return List<String>     
    * @throws
     */
    List<String> getNo(@Param("collectId")String collectId);
    /**
     * 
    * @Title: getById
    * @Description：根据id得到所有的编号 
    * author: Li Xiaoxiao 
    * @param @param collectId
    * @param @return     
    * @return List<String>     
    * @throws
     */
    List<String> getId(@Param("planNo")String planNo);
}