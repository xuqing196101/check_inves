package ses.dao.sms;

import java.util.List;

import ses.model.sms.ImportSupplier;
import ses.model.sms.ImportSupplierWithBLOBs;

public interface ImportSupplierMapper {
    int deleteByPrimaryKey(String id);

    int insert(ImportSupplierWithBLOBs record);

    int insertSelective(ImportSupplierWithBLOBs record);

    ImportSupplierWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImportSupplierWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ImportSupplierWithBLOBs record);

    int updateByPrimaryKey(ImportSupplier record);
    
    
    /**
     * 
    * @Title: selectTotalCount
    * @author sbw
    * @date 2016-9-1 下午4:26:28  
    * @Description: 查询总条数 
    * @param @return      
    * @return int
     */
    int getCount(ImportSupplierWithBLOBs record);
    
    /**
    * @Title: selectByFsInfo
    * @author sbw
    * @date 2016-9-2 下午1:42:20  
    * @Description: 条件查询返回List 
    * @param @param record
    * @param @return      
    * @return List<ImportSupplierWithBLOBs>
     */
    List<ImportSupplierWithBLOBs> selectByFsInfo(ImportSupplierWithBLOBs record);
    
    /**
    * @Title: selectIdByLoginName
    * @author Song Biaowei
    * @date 2016-9-7 上午10:07:53  
    * @Description: 通过用户名找id
    * @param @param record
    * @param @return      
    * @return String
     */
    String selectIdByLoginName(ImportSupplierWithBLOBs record);
}