package extract.dao.supplier;

import java.util.List;

import extract.model.supplier.SupplierExtractProjectInfo;

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
    int insert(SupplierExtractProjectInfo record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtractProjectInfo record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtractProjectInfo selectByPrimaryKey(String id);

    /**
     *	动态修改项目记录表
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtractProjectInfo record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtractProjectInfo record);
    
    /**
     * @Description:获取集合信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月9日 下午4:59:51  
     * @param @param record
     * @param @return      
     * @return List<SupplierExtracts>
     */
    List<SupplierExtractProjectInfo> list(SupplierExtractProjectInfo record);

    
    /**
     * 下载记录表需要的项目信息
     * @param id
     * @return
     */
	SupplierExtractProjectInfo getProjectInfoById(String id);

	/**
	 * 抽取记录列表
	 * @param string 
	 * @return
	 */
	List<SupplierExtractProjectInfo> getList(SupplierExtractProjectInfo project);

	int saveOrUpdateProjectInfo(SupplierExtractProjectInfo projectInfo);

	void insertProjectInfo(SupplierExtractProjectInfo record);
	
	/**
	 * 模糊查询项目信息
	 * @param p
	 * @return
	 */
	List<SupplierExtractProjectInfo> getListByMap(SupplierExtractProjectInfo p);
}