package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierRegPerson;

public interface SupplierRegPersonMapper {
    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierRegPerson record);

    /**
     * 插入数据库记录
     * @param record
     */
    int insertSelective(SupplierRegPerson record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierRegPerson selectByPrimaryKey(String id);

    /**
     * 更新数据库记录
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierRegPerson record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierRegPerson record);
    
    List<SupplierRegPerson> findRegPersonByMatEngId(String supplierMatEngId);
    
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);
    
    /**
     * @Title: deleteByMatEngId
     * @Description: 根据工程信息ID删除
     * author: Li Xiaoxiao
     * @param engId
     * @return
     */
    int deleteByMatEngId(@Param("matEngId")String matEngId);
}