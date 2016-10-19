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

}
