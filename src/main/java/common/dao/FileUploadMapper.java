package common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import common.model.UploadFile;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */

public interface FileUploadMapper {
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param file {@link UploadFile}
     * @return
     */
    int insertFile(UploadFile file);
    
    /**
     * 
     *〈简述〉删除文件
     *〈详细描述〉
     * @author myc
     * @param tableName 表名称
     * @param businessId 业务ID
     * @param typeId 类型Id
     */
    void updateFile(@Param("tableName")String tableName, @Param("id")String id);
    
    /**
     * 
     *〈简述〉获取文件列表
     *〈详细描述〉
     * @author myc
     * @param tableName 表名称
     * @param businessId 业务Id
     * @param typeId 业务类型
     * @return
     */
    List<UploadFile> getFiles(@Param("tableName")String tableName, @Param("businessId")String businessId, @Param("typeId")String typeId);

    /**
     * 
     *〈简述〉根据主键查询
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @param tableName 表名称
     * @return {@link UploadFile}
     */
    UploadFile getFileById(@Param("id")String id, @Param("tableName")String tableName);
    
    /**
     * 
     *〈简述〉根据业务Id查询
     *〈详细描述〉
     * @author myc
     * @param businessId 业务id
     * @param typeId 业务类型Id
     * @param tableName 表名称
     * @return
     */
    List<UploadFile> getFileByBusinessId(@Param("businessId")String businessId,@Param("typeId")String typeId, @Param("tableName")String tableName);
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param id 主键的数组,如: 1001,1002,1003
     * @param tableName 表名称
     * @return
     */
    List<UploadFile> getFilesByIds(@Param("ids")String[] id, @Param("tableName")String tableName);
    
    /**
     * 
     *〈简述〉根据主键查询
     *〈详细描述〉
     * @author ZhaoBo
     * @param id 主键
     * @param tableName 表名称
     * @return UploadFile
     */
    UploadFile findById(@Param("id")String id,@Param("tableName")String tableName);
    
    UploadFile findBybusinessId(@Param("businessId")String businessId,@Param("tableName")String tableName);
}
