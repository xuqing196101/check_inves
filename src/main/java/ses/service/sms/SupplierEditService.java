package ses.service.sms;

import java.util.List;
import java.util.Map;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierEdit;
/**
 * 版权：(C) 版权所有 
 * <简述>供应商变更信息服务层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public interface SupplierEditService {

    /**
     *〈简述〉新增一条修改记录
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体
     */
    void insertSelective(SupplierEdit se);
    
    /**
     *〈简述〉按照主键查询
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     * @return SupplierEdit
     */
    SupplierEdit selectByPrimaryKey(String id);
    
    /**
     *〈简述〉修改
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体
     */
    void updateByPrimaryKey(SupplierEdit se);

    /**
     *〈简述〉查找所有的变更记录
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体
     * @param page 当前页
     * @return List<SupplierEdit>
     */
    List<SupplierEdit> findAll(SupplierEdit se, Integer page);
    
    /**
     *〈简述〉删除
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 主键
     */
    void delete(String id);
    
    /**
     *〈简述〉按照供应商ID查找
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体
     * @return List<SupplierEdit>
     */
    List<SupplierEdit> getAllbySupplierId(SupplierEdit se);

    /**
     *〈简述〉获取符合条件的记录
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体
     * @return  List<SupplierEdit>
     */
    List<SupplierEdit> getAllRecord(SupplierEdit se);

    /**
     *〈简述〉
     *〈详细描述〉
     * @author Song Biaowei
     * @param 获取修改前后的不同值，在审核的时候显示出来
     * @param supplier 基本信息实体
     * @param se 修改记录实体
     * @return Supplier
     */
    Supplier getResult(SupplierEdit se, Supplier supplier);

    /**
     *〈简述〉赋值
     *〈详细描述〉
     * @author Song Biaowei
     * @param supplier 实体
     * @return SupplierEdit
     */
    SupplierEdit setToSupplierEdit(Supplier supplier);

    /**
     *〈简述〉赋值
     *〈详细描述〉
     * @author Song Biaowei
     * @param se 实体
     * @return Supplier
     */
    Supplier setToSupplier(SupplierEdit se);

    /**
     *〈简述〉
     *〈详细描述〉
     * @author Song Biaowei
     * @param listEdit 供应商变更记录集合
     * @return List<String>
     */
    List<String> getList(List<SupplierEdit> listEdit);

    /**
     *〈简述〉获取省份
     *〈详细描述〉
     * @author Song Biaowei
     * @param address 地址
     * @return String
     */
    String getProvince(String address);

    /**
     *〈简述〉获取所有的省份map和对应的省份的供应商的数量 这个是固定了的
     *〈详细描述〉
     * @author Song Biaowei
     * @return Map<String,Object>
     */
    Map<String, Object> getAllProvince();

    /**
     *〈简述〉获取省份的对应拼音
     *〈详细描述〉
     * @author Song Biaowei
     * @return Map<String,Integer>
     */
    Map<String, Integer> getMap();

    Map<String, Object> getMapArea();
}
