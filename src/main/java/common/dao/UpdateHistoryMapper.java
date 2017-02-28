package common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import common.model.UpdateHistory;


public interface UpdateHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(UpdateHistory record);

    int insertSelective(UpdateHistory record);

    UpdateHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UpdateHistory record);

    int updateByPrimaryKeyWithBLOBs(UpdateHistory record);

    int updateByPrimaryKey(UpdateHistory record);
    
    /**
     * 
    * @Title: queryByUpdateId
    * @Description: 根据updateId查询所有的修改历史数据
    * author: Li Xiaoxiao 
    * @param @param updateId
    * @param @return     
    * @return List<UpdateHistory>     
    * @throws
     */
    List<UpdateHistory> queryByUpdateId(@Param("updateId")String updateId);
    /**
     * 
    * @Title: getLast
    * @Description:得到版本最新的历史数据 
    * author: Li Xiaoxiao 
    * @param @param updateId
    * @param @return     
    * @return UpdateHistory     
    * @throws
     */
    public UpdateHistory getLast(@Param("updateId")String updateId);
    
    /**
     * 
    * @Title: getMax
    * @Description: 得到最新的值
    * author: Li Xiaoxiao 
    * @param @param updateId
    * @param @return     
    * @return Integer     
    * @throws
     */
    public Integer getMax(@Param("updateId")String updateId);
    
}