package bss.service.ob;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.ob.OBResultSubtabulation;

public interface OBResultSubtabulationService {
	/**
	 * 
	 * Description: 根据竞价信息查询
	 * 
	 * @author zhang shubin
	 * @version 2017年3月30日
	 * @param @param projectId
	 * @param @return
	 * @return List<OBResultSubtabulation>
	 * @exception
	 */
	List<OBResultSubtabulation> selectByProjectId(@Param("projectId") String projectId);
	
	/**
	 * 
	 * Description: 查询竞价结果信息
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月5日 
	 * @param  @param projectId
	 * @param  @param supplierId
	 * @param  @return 
	 * @return List<OBResultSubtabulation> 
	 * @exception
	 */
	List<OBResultSubtabulation> selectByProjectIdAndSupplierId(String projectId,String supplierId);
}
