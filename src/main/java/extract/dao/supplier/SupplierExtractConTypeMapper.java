package extract.dao.supplier;

import java.util.Map;

import extract.model.supplier.SupplierConType;

public interface SupplierExtractConTypeMapper {
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
    int insert(SupplierConType record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierConType record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierConType selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierConType record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierConType record);
    
    /**
     * @Description:删除数据
     *
     * @author Wang Wenshuai
     * @version 2016年10月12日 下午3:40:50  
     * @param @param id
     * @param @return      
     * @return int
     */
    int deleteConditionId(String id);
    
    /**
     * 根据供应商类型返回抽取数量
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    Integer getSupplierTypeById(Map<String, String> map);
    
    /**
     * 
     *〈简述〉获取总和
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param conId
     * @return
     */
    Integer getSum(String conId);
}