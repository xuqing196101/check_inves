package extract.service.expert.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extract.dao.expert.ExpertExtractProjectMapper;
import extract.model.expert.ExpertExtractProject;
import extract.service.expert.ExpertExtractProjectService;

/**
 * 
 * Description: 专家抽取项目信息管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service("expertExtractProjectService")
public class ExpertExtractProjectServiceImpl implements ExpertExtractProjectService {

	//专家抽取项目信息
	@Autowired
	private ExpertExtractProjectMapper expertExtractProjectMapper;
	
	
	@Override
	public int save(ExpertExtractProject expertExtractProject) {
		expertExtractProject.setCreatedAt(new Date());
		expertExtractProject.setUpdatedAt(new Date());
		expertExtractProject.setIsDeleted((short) 0);
		return expertExtractProjectMapper.insertSelective(expertExtractProject);
	}

	
}
