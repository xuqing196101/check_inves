/**
 * 
 */
package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpExtConditionMapper;
import ses.dao.ems.ExpExtractRecordMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ProjectExtractMapper;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ExtConType;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierExtRelate;
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
        List<ExpExtCondition> list = conditionMapper.list(new ExpExtCondition(cId, ""));
        if (list != null && list.size() != 0){
            ExpExtCondition show = list.get(0);
            //给专家set查询条件
            Expert expert = new Expert();
            expert.setAddress(show.getAddress());
            //		expert.setBirthday(birthday);
            expert.setExpertsFrom(show.getExpertsFrom());
            //复制对象
            List<ExtConType> conTypeCopy = new ArrayList<ExtConType>();
            for (ExtConType ct : show.getConTypes()) {
                ExtConType dw = new ExtConType();
                BeanUtils.copyProperties(ct, dw, new String[] {"serialVersionUID"});
                conTypeCopy.add(dw);
            }
            //根据不同类型循环查询专家集合
            for (ExtConType extConType : conTypeCopy) {
                show.getConTypes().clear();
                show.getConTypes().add(extConType);
                //查询专家集合
                List<Expert> selectAllExpert = expertMapper.listExtractionExpert(show);
                //循环吧查询出的专家集合insert到专家记录表和专家关联的表中
                ProjectExtract projectExtracts = null;
                List<ProjectExtract> projectExtracts2=new ArrayList<ProjectExtract>();
                for (Expert expert2 : selectAllExpert) {
                    Map<String, String> map=new HashMap<String, String>();
                    map.put("expertId", expert2.getId());
                    map.put("projectId",show.getProjectId());
                    if(extractMapper.getexpCount(map)==0){
                        projectExtracts=new ProjectExtract();
                        //专家id
                        projectExtracts.setExpertId(expert2.getId());
                        //项目id
                        projectExtracts.setProjectId(show.getProjectId());
                        //条件表id
                        projectExtracts.setExpertConditionId(show.getId());
                        projectExtracts.setIsDeleted((short)0);
                        projectExtracts.setOperatingType((short)0);
                        projectExtracts.setConTypeId(extConType.getId());
                        projectExtracts2.add(projectExtracts);
                    }

                }
                //插入projectExtracts
                if(projectExtracts2 != null && projectExtracts2.size() !=0){
                    extractMapper.insertList(projectExtracts2);
                }
            }
        }
        return "";
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

    /**
     * @Description:删除重复记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    @Override
    public void deleteData(Map map){
        extractMapper.deleteData(map);
    }

    /**
     * @Description:当抽取数量满足时修改还未抽取的专家状态为1
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    @Override
    public void updateStatusCount(String type,String conTypeId){
        Map<String, String> map=new HashMap<String, String>();
        map.put("type", type);
        map.put("conTypeId", conTypeId);
        extractMapper.updateStatusCount(map);
    }

    /**
     * @Description:insert
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午4:12:09  
     * @param       
     * @return void
     */
    @Override
    public  void  insertProjectExtract(ProjectExtract projectExtracts){
        //插入projectExtracts
        extractMapper.insertSelective(projectExtracts);
    }

    /**
     * @Description:获取单个对象
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午8:02:39  
     * @param @param projectExtract      
     * @return void
     */
    @Override
    public ProjectExtract getExpExtRelate(String id) {
        // TODO Auto-generated method stub
        return extractMapper.selectByPrimaryKey(id);
    }
}
