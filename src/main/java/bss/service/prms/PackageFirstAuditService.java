package bss.service.prms;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bss.model.prms.PackageFirstAudit;

public interface PackageFirstAuditService {
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午3:36:16  
	  * @Description: TODO 保存全部
	  * @param @param record      
	  * @return void
	 */
	void save(PackageFirstAudit record);
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午3:50:43  
	  * @Description: TODO 根据包id删除数据
	  * @param @param packageId      
	  * @return void
	 */
	void delete(String packageId);
	/**
     * 
      * @Title: selectList
      * @author ShaoYangYang
      * @date 2016年10月17日 下午5:13:41  
      * @Description: TODO 根据项目id 和包id查询关联表集合
      * @param @param map
      * @param @return      
      * @return List<PackageFirstAudit>
     */
    List<PackageFirstAudit>selectList(Map<String,Object> map);
    /**
     * 
      * @Title: update
      * @author ShaoYangYang
      * @date 2016年10月26日 下午8:06:25  
      * @Description: TODO 根据传递的id 修改符合条件的内容
      * @param @param record      
      * @return void
     */
    void update(PackageFirstAudit record);
    
    /**
     *〈简述〉根据项目id和包id数组查询关联表集合
     *〈详细描述〉
     * @author Ye MaoLin
     * @param map
     * @return
     */
    List<PackageFirstAudit> findByProAndPackage(Map<String,Object> map);

    /**
     *〈简述〉根据符合性审查项Id删除
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id
     * @return
     */
    void deleteByFirstAuditId(String id);
    
    /**
     * 
     *〈简述〉生成word文件
     *〈详细描述〉
     * @author myc
     * @param projectId 项目Id
     * @param projectName 项目名称
     * @param projectNo  项目编号
     * @param response   {@link HttpServletResponse}
     * @return 文件名称
     */
    String downLoadBiddingDoc(String projectId,String projectName,String projectNo, HttpServletRequest request);
}
