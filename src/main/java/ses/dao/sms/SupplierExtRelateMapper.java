package ses.dao.sms;

import java.util.List;
import java.util.Map;

import ses.model.sms.SupplierExtRelate;

public interface SupplierExtRelateMapper {
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
    int insert(SupplierExtRelate record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtRelate record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtRelate selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtRelate record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtRelate record);
    
    /**
     * @Description:集合获取中间表
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<SupplierExtRelate>
     */
    List<SupplierExtRelate> list(SupplierExtRelate extract);
    
    /**
     * @Description:删除重复记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    void deleteData(Map map);
    
    /**
     * @Description:当抽取数量满足时修改还未抽取的专家状态为1
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    void updateStatusCount(Map map);
}