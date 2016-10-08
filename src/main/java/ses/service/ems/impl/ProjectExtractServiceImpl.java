/**
 * 
 */
package ses.service.ems.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpExtConditionMapper;
import ses.dao.ems.ExpExtractRecordMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ProjectExtractMapper;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.service.ems.ProjectExtractService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日下午4:12:52
 * @since  JDK 1.7
 */
@Service
public class ProjectExtractServiceImpl implements ProjectExtractService {
	@Autowired
	ProjectExtractMapper extractMapper;
	@Autowired
	ExpExtConditionMapper conditionMapper;
	@Autowired
	ExpertMapper expertMapper;
	@Autowired
	ExpExtractRecordMapper expExtractRecordMapper;
	/**
	 * @Description:insert
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午4:12:09  
	 * @param       
	 * @return void
	 */
	@Override
	public String insert(String cId,String userid) {
		//获取查询条件
		ExpExtCondition show = conditionMapper.selectByPrimaryKey(cId);
		//给专家set查询条件
		Expert expert=new Expert();
		expert.setAddress(show.getAddress());
		//		expert.setBirthday(birthday);
		expert.setExpertsFrom(show.getExpertsFrom());
		//查询专家集合
		PageHelper.startPage(1, 10);
		List<Expert> selectAllExpert = expertMapper.selectAllExpert(null);
		//给专家记录表set信息并且插入到记录表
		ExpExtractRecord expExtractRecord=new ExpExtractRecord();
		expExtractRecord.setExtractsPeople(userid);
		expExtractRecord.setExtractTheWay((short)1);
		expExtractRecord.setExtractionSites(show.getAddress());
		expExtractRecordMapper.insertSelective(expExtractRecord);
		//循环吧查询出的专家集合insert到专家记录表和专家关联的表中
		ProjectExtract projectExtracts=null;
		for (Expert expert2 : selectAllExpert) {
			projectExtracts=new ProjectExtract();
			//专家id
			projectExtracts.setExpertId(expert2.getId());
			//项目id
			projectExtracts.setProjectId(show.getId());
			//抽取表id
			projectExtracts.setExpertExtractRecordId(expExtractRecord.getId());
			
			projectExtracts.setIsDeleted((short)0);
			projectExtracts.setOperatingType((short)0);
			//插入projectExtracts
			extractMapper.insertSelective(projectExtracts);
		}
		return expExtractRecord.getId();

	}

	/**
	 * @Description:集合展示
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午6:07:39  
	 * @param @param projectExtract      
	 * @return void
	 */
	@Override
	public List<ProjectExtract> list(ProjectExtract projectExtract) {
		return extractMapper.list(projectExtract);
	}

	/**
	 * @Description:修改操作状态
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午8:02:39  
	 * @param @param projectExtract      
	 * @return void
	 */
	@Override
	public void update(ProjectExtract projectExtract) {
		
		extractMapper.updateByPrimaryKeySelective(projectExtract);
		
	}
}
