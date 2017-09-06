package extract.dao.supplier;

import java.util.List;

import bss.model.ppms.Project;
import extract.model.supplier.SupplierExtracts;

public interface SupplierExtractRecordMapper {
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
    int insert(SupplierExtracts record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtracts record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtracts selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtracts record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtracts record);
    
    /**
     * @Description:获取集合信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月9日 下午4:59:51  
     * @param @param record
     * @param @return      
     * @return List<SupplierExtracts>
     */
    List<SupplierExtracts> list(SupplierExtracts record);

	Project getProjectInfoById(String id);

	List<SupplierExtracts> getList();
}