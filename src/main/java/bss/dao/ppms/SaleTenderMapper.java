package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bss.model.ppms.Packages;
import bss.model.ppms.SaleTender;

public interface SaleTenderMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SaleTender record);

    /**
     *
     * @param record
     */
    int insertSelective(SaleTender record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SaleTender selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SaleTender record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SaleTender record);
    
    /**
     *〈简述〉批量修改是否到场这个字段
     *〈详细描述〉
     * @author Song Biaowei
     * @param record
     */
    void updateIsTurnUpByPrimaryKey(SaleTender record);

    /**
     * 
     *〈简述〉返回集合
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param record
     * @return
     */
    List<SaleTender> list(SaleTender record);

    /**
     *
     *〈简述〉返回上传的数量
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param businessId
     * @return
     */
    Integer uploadCount(String businessId);
    
    
    /**
     * 
    * @Title: getPackegeSupplier
    * @author Shen Zhenfei 
    * @date 2016-12-12 下午4:54:51  
    * @Description: 根据项目包名，获取供应商
    * @param @param record
    * @param @return      
    * @return List<SaleTender>
     */
    List<SaleTender> getPackegeSupplier(SaleTender record);
    
    
    /**
    * @Title: getPackegeSuppliers
    * @author Shen Zhenfei 
    * @date 2016-12-18 上午11:06:58  
    * @Description: 根据项目包名，获取对应的供应商
    * @param @param record
    * @param @return      
    * @return List<SaleTender>
     */
    List<SaleTender> getPackegeSuppliers(SaleTender record);
    
    /**
     *〈简述〉按照项目id查询所有的包
     *〈详细描述〉
     * @author Song Biaowei
     * @param projectId
     * @return
     */
    List<Packages> getPackageIds(String projectId);
    
    /**
     *〈简述〉 根据供应商和项目查询
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record
     * @return
     */
    List<SaleTender> find(SaleTender record);
    
    List<SaleTender> finds(SaleTender record);
    
    void updateResult(HashMap<String, Object> stMap);
    
    /**
     *〈简述〉
     * 更改SaleTender的移除状态
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     */
    void removeSaleTender(Map<String, Object> map);
    
    /**
     *〈简述〉
     * 根据项目id查询所有saleTender
     *〈详细描述〉
     * @author WangHuijie
     * @param projectId
     * @return
     */
    List<SaleTender> selectListByProjectId(String projectId);
    
    /**
     *〈简述〉
     * 根据包id和供应商id设置经济技术总分
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     */
    void editSumScore(Map<String, Object> map);
    
    /**
     * 
     *〈简述〉移除供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param supplierId
     * @param packagesId
     */
    void delSaleDelete(Map<String, String> map);

    /**
     *〈简述〉插入供应商排名
     *〈详细描述〉
     * @author Ye Maolin
     * @param ranMap
     */
    void updateRank(HashMap<String, Object> ranMap);
    /**
     * 
     * Description:根据 供应商id 和招标文件id 获取该 项目的 状态
     * 
     * @author YangHongLiang
     * @version 2017-5-24
     * @param supplierId
     * @param projectId
     * @return
     */
    List<String> findBySupplierIdProjectId(@Param("supplierId")String supplierId,@Param("projectId")String projectId);
    /**
     * 
     * Description:根据 供应商id 和招标文件id 获取该供应商可参与那些包数据
     * 
     * @author YangHongLiang
     * @version 2017-5-25
     * @param supplierId
     * @param projectId
     * @return
     */
    List<SaleTender> findPackageBySupplierIdProjectId(@Param("supplierId")String supplierId,@Param("projectId")String projectId);
    
    /**
     * 
     *〈根据预研项目ID和包ID查询供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<SaleTender> getAdPackegeSuppliers(HashMap<String, Object> map);
}