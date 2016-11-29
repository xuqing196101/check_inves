/**
 * 
 */
package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ProExtSuperviseMapper;
import ses.dao.ems.ProjectExtractMapper;
import ses.model.bms.User;
import ses.model.ems.ProExtSupervise;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierExtUser;
import ses.service.ems.ProjectSupervisorServicel;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月14日下午7:34:19
 * @since  JDK 1.7
 */
@Service
public class ProjectSupervisorServicelmpl implements ProjectSupervisorServicel {
	@Autowired
	ProExtSuperviseMapper extSuperviseMapper;
	
	/**
	 * @Description:集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月14日 下午7:34:06  
	 * @param @return      
	 * @return List<ProjectSupervisor>
	 */
	@Override
	public List<User> list(ProExtSupervise extSupervise) {
		return extSuperviseMapper.list(extSupervise);
	}
	
	/**
     * @Description:根据项目id删除监督信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月15日 下午7:05:15  
     * @param       
     * @return void
     */
    @Override
    public void deleteProjectId(String prjectId) {
        extSuperviseMapper.deleteProjectId(prjectId);
    }

    /**
     * @Description:inesrt
     *
     * @author Wang Wenshuai
     * @version 2016年10月15日 下午7:05:15  
     * @param       
     * @return void
     */
    @Override
    public void insert(ProExtSupervise record) {
        extSuperviseMapper.insertSelective(record);
    }


}
