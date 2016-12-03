package ses.dao.sms;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;

/**
 * @Title: SupplierMapper
 * @Description: SupplierMapper
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:06:56
 */
public interface SupplierMapper {

    /**
     * @Title: deleteByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-7 下午6:07:01
     * @Description: 根据主键删除
     * @param: @param id
     * @param: @return
     * @return: int
     */
    int deleteByPrimaryKey(String id);

    /**
     * @Title: insert
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:38:31
     * @Description: 插入数据库记录
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insert(Supplier record);

    /**
     * @Title: insertSelective
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:38:56
     * @Description: 选择性插入
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insertSelective(Supplier record);

    /**
     * @Title: selectByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:39:27
     * @Description: 根据主键获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: SupplierInfo
     */
    Supplier selectByPrimaryKey(String id);

    /**
     * @Title: updateByPrimaryKeySelective
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:40:12
     * @Description: 根据主键选择性更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKeySelective(Supplier record);

    /**
     * @Title: updateByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-1 下午3:40:31
     * @Description: 根据主键更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKey(Supplier record);

    /**
     * @Title: selectLastInsertId
     * @author: Wang Zhaohua
     * @date: 2016-9-5 下午4:12:45
     * @Description: 查询最后一条插入的数据的 ID
     * @param: @return
     * @return: int
     */
    String selectLastInsertId();

    Supplier getSupplier(@Param("id")String id);

    List<Supplier> findSupplier(Supplier record);

    /**
     * @Title: getAllSupplier
     * @author Song Biaowei
     * @date 2016-10-6 下午6:02:17  
     * @Description: 获取所有的供应商
     * @param @param record
     * @param @return      
     * @return List<Supplier>
     */
    List<Supplier> getAllSupplier(Supplier record);


    /**
     * @Title: querySupplier
     * @author Song Biaowei
     * @date 2016-10-5 上午10:22:27  
     * @Description: 查询一张表，不关联 
     * @param @param record
     * @param @return      
     * @return List<Supplier>
     */
    List<Supplier> querySupplier(Supplier record);
    
    /**
     * @Title: querySupplierbytypeAndCategoryIds
     * @author Song Biaowei
     * @date 2016-11-1 下午3:28:37  
     * @Description: 按照品目和类型查询
     * @param @param record
     * @param @return      
     * @return List<Supplier>
     */
    List<Supplier> querySupplierbytypeAndCategoryIds(Supplier record);

    /**
     * @Title: querySupplierbyCategory
     * @author Song Biaowei
     * @date 2016-10-6 下午5:21:20  
     * @Description: 查询品目 
     * @param @param record
     * @param @return      
     * @return List<Supplier>
     */
    List<Supplier> querySupplierbyCategory(Supplier record);

    /**
     * @Title: getCount
     * @author Xu Qing
     * @date 2016-9-21 上午10:11:43  
     * @Description: 根据审核状态获取条数
     * @param @param supplier
     * @param @return      
     * @return Integer
     */
    Integer getCount(Supplier record);

    /**
     * @Title: updateStatus
     * @author Xu Qing
     * @date 2016-9-21 下午4:40:27  
     * @Description: 根据供应商ID更新审核状态
     * @param @param id
     * @param @return      
     * @return Supplier
     */
    void updateStatus(Supplier record);

    int updateSupplierProcurementDep(Supplier supplier);

    int updateScore(Supplier supplier);

    void updateSupplierInspectListById(Supplier record);

    /**
     * @Description: 抽取供应商
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午2:55:31  
     * @param @return      
     * @return List<Expert>
     */
    List<Supplier> listExtractionExpert(SupplierCondition con);
    
    List<Supplier> selectByProjectId(String projectId);
    
    /**
     * @Title: findLoginName
     * @author: Wang Zhaohua
     * @date: 2016-11-6 下午5:08:03
     * @Description: 查询所有的用户名
     * @param: @return
     * @return: List<String>
     */
    List<String> findLoginName();
    
    /**
     * @Title: getByMap
     * @author: Wang Zhaohua
     * @date: 2016-11-7 下午1:41:52
     * @Description: 根据 Map 获取供应商
     * @param: @param param
     * @param: @return
     * @return: Supplier
     */
    Supplier getByMap(Map<String, Object> param);
    
    /**
     * @Title: selectSupplierTypes
     * @author Song Biaowei
     * @date 2016-11-18 下午2:40:13  
     * @Description: 查询供应商的类型
     * @param @param supplierId
     * @param @return      
     * @return String
     */
    String selectSupplierTypes(Supplier supplier);
    
    /**
     * @Title: findSupplierAll
     * @author Xu Qing
     * @date 2016-11-21 上午10:08:29  
     * @Description: 供应商审核-查询供应商
     * @param @param record
     * @param @return      
     * @return String
     */
    List<Supplier> findSupplierAll (Supplier record);
    
    /**
     * 
     *〈简述〉 根据项目返回抽取供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Supplier> listResultSupplier(String ProjectId);
    
    
    
    Supplier  queryByName(@Param("name")String name);
    /**
     * 
    * @Title: query
    * @Description: 查询供应商
    * author: Li Xiaoxiao 
    * @param @param supplier
    * @param @return     
    * @return Supplier     
    * @throws
     */
    List<Supplier> query(Map<String,Object> map);
    
}