package extract.dao.supplier;

import java.util.List;

import extract.model.common.ExtractUser;

public interface SupplierExtractUserMapper {
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
    int insert(ExtractUser record);

    /**
     *
     * @param record
     */
    int insertSelective(ExtractUser record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ExtractUser selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ExtractUser record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(ExtractUser record);
    
    /**
     * @Description:集合
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午7:39:19  
     * @param @param record
     * @param @return      
     * @return List<ProExtSupervise>
     */
    List<ExtractUser> list(ExtractUser record);
    
    /**
     * @Description:根据项目id删除监督信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月15日 下午7:05:15  
     * @param       
     * @return void
     */
    void deleteProjectId(String prjectId);
    
    
    /**
     * 
     *〈简述〉批量插入
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param listInsert
     */
    void listInsert(List<ExtractUser> listInsert);

	String getName(String recordId);
}