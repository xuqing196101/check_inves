package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierFsInfo;
import ses.model.sms.SupplierFsInfoWithBLOBs;


/**
 * @Title: SupplierFsInfoMapper
 * @Description: 进口供应商注册审核dao层
 * @author: Song Biaowei
 * @date: 2016-9-7下午6:09:28
 */
public interface SupplierFsInfoMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierFsInfoWithBLOBs record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierFsInfoWithBLOBs record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierFsInfoWithBLOBs selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierFsInfoWithBLOBs record);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(SupplierFsInfoWithBLOBs record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierFsInfo record);
    
    /**
     * 
    * @Title: selectTotalCount
    * @author sbw
    * @date 2016-9-1 下午4:26:28  
    * @Description: 查询总条数 
    * @param @return      
    * @return int
     */
    int getCount(SupplierFsInfoWithBLOBs record);
    
    /**
    * @Title: selectByFsInfo
    * @author sbw
    * @date 2016-9-2 下午1:42:20  
    * @Description: 条件查询返回List 
    * @param @param record
    * @param @return      
    * @return List<SupplierFsInfoWithBLOBs>
     */
    List<SupplierFsInfoWithBLOBs> selectByFsInfo(SupplierFsInfoWithBLOBs record);
    
    /**
    * @Title: selectIdByLoginName
    * @author Song Biaowei
    * @date 2016-9-7 上午10:07:53  
    * @Description: 通过用户名找id
    * @param @param record
    * @param @return      
    * @return String
     */
    String selectIdByLoginName(SupplierFsInfoWithBLOBs record);
}