package extract.dao.supplier;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import extract.model.supplier.SupplierExtractProjectInfo;

public interface SupplierExtractRecordMapper {


    /**
     * 根据主键获取一条数据库记录(关联查询，带有省市中文名称)
     *
     * @param id
     */
    SupplierExtractProjectInfo selectByPrimaryKey(@Param("id") String id);

    
    /**
     * 下载记录表需要的项目信息（仅是表数据）
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

	/**
	 * 
	 * <简述> 修改数据
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-25下午9:29:03
	 * @param projectInfo
	 * @return
	 */
	int saveOrUpdateProjectInfo(SupplierExtractProjectInfo projectInfo);

	/**
	 * 插入数据
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-25下午9:29:20
	 * @param record
	 */
	void insertProjectInfo(SupplierExtractProjectInfo record);
	
	/**
	 * 模糊查询项目信息
	 * @param p
	 * @return
	 */
	List<SupplierExtractProjectInfo> getListByMap(SupplierExtractProjectInfo p);

	/**
	 * 获取自动抽取待通知项目
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-10-19下午7:16:43
	 * @return
	 */
	List<SupplierExtractProjectInfo> selectAutoExtractProject();
}