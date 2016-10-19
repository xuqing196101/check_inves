package ses.dao.sms;

import java.util.List;

import ses.model.bms.User;
import ses.model.ems.ProExtSupervise;
import ses.model.sms.SupplierExtUser;

public interface SupplierExtUserMapper {
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
    int insert(SupplierExtUser record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtUser record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtUser selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtUser record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtUser record);
    
    /**
     * @Description:集合
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:39:19  
     * @param @param record
     * @param @return      
     * @return List<ProExtSupervise>
     */
    List<User> list(SupplierExtUser record);
    
    /**
     * @Description:根据项目id删除监督信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月15日 下午7:05:15  
     * @param       
     * @return void
     */
    void deleteProjectId(String prjectId);
}