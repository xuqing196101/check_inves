package bss.dao.pms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.PurchaseManagement;

public interface PurchaseManagementMapper {
    int deleteByPrimaryKey(String id);

    int insert(PurchaseManagement record);

    int insertSelective(PurchaseManagement record);

    PurchaseManagement selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PurchaseManagement record);

    int updateByPrimaryKey(PurchaseManagement record);
    
    /**
     * 
    * @Title: queryByMid
    * @Description:根据管理部门id查询对应的需求计划 
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return List<PurchaseManagement>     
    * @throws
     */
    List<PurchaseManagement> queryByMid(@Param("mid") String id);
}