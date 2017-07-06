package common.dao;

import java.util.List;
import java.util.Map;

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
     *〈简述〉保存文件
     *〈详细描述〉
     * @author myc
     * @param file
     */
    void saveFile(UploadFile file);
    
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
    
    void updateLoad(UploadFile file);
    
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
     *〈简述〉获取已经删除的文件
     *〈详细描述〉
     * @author FengTian
     * @param tableName
     * @param businessId
     * @param typeId
     * @return
     */
    List<UploadFile> getIsFiles(@Param("tableName")String tableName, @Param("businessId")String businessId, @Param("typeId")String typeId);

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
    
    List<UploadFile> findBybusinessId(@Param("businessId")String businessId,@Param("tableName")String tableName);
    
    /**
     * 
     *〈简述〉根据时间查询附件
     *〈详细描述〉
     * @author myc
     * @param date 日期
     * @param tableName 附件表名称
     * @return
     */
    List<UploadFile> getFileByDate(@Param("date")String date, @Param("tableName")String tableName);
    
    /**
     * 
     *〈简述〉根据Id查询
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @param tableName 表名称
     * @return
     */
    Integer getFileCount(@Param("id")String id,@Param("tableName")String tableName);
    
    /**
     * 
     *〈简述〉更新附件表
     *〈详细描述〉
     * @author myc
     * @param file
     */
    void updateFileById(UploadFile file);
    
    /**
     * 
    * @Title: deleteByBusinessId
    * @Description: 删除对应的附件信息 
    * author: Li Xiaoxiao 
    * @param @param businessId     
    * @return void     
    * @throws
     */
    void deleteByBusinessId(@Param("businessId")String businessId);

	List queryImage(Map<String, Object> map);
	
	
	
	
	/**
	 * 
	* @Title: substr
	* @Description:截取字符窜查询供应商附件
	* author: Li Xiaoxiao 
	* @param @param businessId
	* @param @return     
	* @return List<UploadFile>     
	* @throws
	 */
    List<UploadFile> substrBusinessId(@Param("businessId")String businessId);
    
    
    /**
	 * 
	* @Title: quyerExpertAttchment
	* @Description:查询专家附件
	* author: Li Xiaoxiao 
	* @param @param businessId
	* @param @return     
	* @return List<UploadFile>     
	* @throws
	 */
    List<UploadFile> quyerExpertAttchment(@Param("businessId")String businessId);
    
    /**
     * 
    * @Title: getFileCountByEmp 
    * @Description: 统计供应商、专家、后台管理人员文件上传数量
    * @author Easong
    * @param @param tableName
    * @param @return    设定文件 
    * @return Long    返回类型 
    * @throws
     */
    public Long getFileCountByEmp(Map<String, Object> map);
    
    /**
     * 
    * @Title: queryById
    * @Description: 根据ID查询 
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @param tableName
    * @param @return     
    * @return UploadFile     
    * @throws
     */
    UploadFile queryById(@Param("id")String id,@Param("tableName")String tableName);
    /**
     * 
     * Description:根据参数查询文件数量
     * 
     * @author YangHongLiang
     * @version 2017-6-26
     * @param businessId
     * @param typeId
     * @param tableName
     * @return
     */
    public Long countFileByBusinessId(@Param("businessId")String businessId,@Param("typeId")String typeId, @Param("tableName")String tableName);
}
