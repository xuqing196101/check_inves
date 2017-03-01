package bss.service.ppms.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.zookeeper.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ses.dao.bms.UserMapper;
import ses.dao.oms.PurchaseDepMapper;
import ses.dao.oms.PurchaseInfoMapper;
import ses.model.bms.User;
import ses.model.oms.PurchaseInfo;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
import bss.dao.ppms.FlowDefineMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.dao.ppms.ProjectMapper;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

import com.github.pagehelper.PageHelper;

/**
 * 
* @Title:ProjectServiceImpl
* @Description: 项目管理业务实现 
* @author FengTian
* @date 2016-9-27上午10:51:27
 */
@Service("project")
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private FlowDefineMapper flowDefineMapper;
	
	@Autowired
	private FlowExecuteMapper flowExecuteMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
  private PurchaseInfoMapper purchaseInfoMapper;
	
	
	
	@Override
	public void add(Project project) {
		projectMapper.insertSelective(project);
	}

	@Override
	public void update(Project project) {
		projectMapper.updateByPrimaryKeySelective(project);
	}

	@Override
	public void delete(String id) {
	    
	    projectMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Project selectById(String id) {
		
		return projectMapper.selectProjectByPrimaryKey(id);
	}

	@Override
	public List<Project> list(Integer page, Project project) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Project> lists = projectMapper.selectProjectByAll(project);
		return lists;
	}

	@Override
	public List<Project> selectSuccessProject(Map<String,Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		List<Project> lists = projectMapper.selectSuccessProject(map);
		return lists;
	}

    @Override
    public boolean SameNameCheck(Project project) {
        boolean flag= true;
        List<Project> list = projectMapper.verifyByProject(project);
        if(list != null && list.size()>0){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Project> lists(Integer page, Project project) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<Project> lists = projectMapper.selectByList(project);
        
        return lists;
    }
    
    /**
     * 
     *〈简述〉查询临时项目
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param project
     * @return
     */
    @Override
    public List<Project>  provisionalList(Integer page, Project project){
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<Project> lists = projectMapper.provisionalList(project);
        return lists;
    }

	
	@Override
	public List<Project> selectProjectByCode(HashMap<String, Object> map) {
		return projectMapper.selectProjectByCode(map);
	}

	@Override
	public void insert(Project project) {
		projectMapper.insertId(project);
	}

    @Override
    public ResponseEntity<byte[]> downloadFile(String fileName, String filePath, String downFileName) {
        try {
            File file=new File(filePath+"/"+fileName);  
                HttpHeaders headers = new HttpHeaders(); 
                headers.setContentDispositionFormData("attachment", downFileName);   
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
                ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
                file.delete();
                return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	
	@Override
	public List<Project> selectProjectsByConition(HashMap<String, Object> map) {
		return projectMapper.selectProjectsByConition(map);
	}

	
	@Override
	public int updatePurchaseDep(Project project) {
		return projectMapper.updatePurchaseDep(project);
	}

  @Override
  public JSONObject getNextFlow(User user, String projectId, String flowDefineId) {
      JSONObject jsonObj = new JSONObject();
      Project project = projectMapper.selectProjectByPrimaryKey(projectId);
      //查询当前项目有没有经办人
      FlowExecute execute0 = new FlowExecute();
      execute0.setProjectId(projectId);
      execute0.setStatus(0);
      execute0.setIsDeleted(0);
      List<FlowExecute> execute0s = flowExecuteMapper.findList(execute0);
      //如果当前项目没有初始化各环节经办人
      if (execute0s == null || execute0s.size() <= 0) {
          //设置各环节经办人默认为项目负责人
          FlowExecute flowExecute = new FlowExecute();
          flowExecute.setProjectId(projectId);
          flowExecute.setStatus(0);
          flowExecute.setCreatedAt(new Date());
          flowExecute.setUpdatedAt(new Date());
          flowExecute.setOperatorId(user.getId());
          flowExecute.setOperatorName(user.getRelName());
          flowExecute.setIsDeleted(0);
          FlowDefine flowDefine = new FlowDefine();
          flowDefine.setPurchaseTypeId(project.getPurchaseType());
          flowDefine.setIsDeleted(0);
          List<FlowDefine> flowDefines = flowDefineMapper.findList(flowDefine);
          for (FlowDefine fd : flowDefines) {
              flowExecute.setId(WfUtil.createUUID());
              flowExecute.setFlowDefineId(fd.getId());
              flowExecute.setStep(fd.getStep());
              flowExecuteMapper.insert(flowExecute);
          }
      }
      
      //当前点击环节
      FlowDefine flowDefine = new FlowDefine();
      if ("0".equals(flowDefineId)) {
          //默认进来第一环节
          FlowDefine define = new FlowDefine();
          define.setIsDeleted(0);
          define.setPurchaseTypeId(project.getPurchaseType());
          define.setStep(1);
          List<FlowDefine> defines = flowDefineMapper.findList(define);
          if (defines != null && defines.size() > 0) {
              flowDefine = defines.get(0);
          }
      } else {
          flowDefine = flowDefineMapper.get(flowDefineId);
      }
      jsonObj.put("currFlowDefineId", flowDefine.getId());
      //当前登录人对当前环节的操作权限
      FlowExecute execute = new FlowExecute();
      execute.setFlowDefineId(flowDefine.getId());
      execute.setIsDeleted(0);
      execute.setProjectId(projectId);
      execute.setStatus(0);
      List<FlowExecute> executes = flowExecuteMapper.findList(execute);
      if (executes != null && executes.size() > 0) {
          List<User> users = userMapper.selectByPrimaryKey(executes.get(0).getOperatorId());
          if (users != null && users.size() > 0) {
              jsonObj.put("operateName", users.get(0).getRelName());
              jsonObj.put("currOperatorId", users.get(0).getId());
          }
          if (executes.get(0).getOperatorId().equals(user.getId())) {
              //具有操作权限
              jsonObj.put("isOperate", 1);
          } else {
              //具有查看权限
              jsonObj.put("isOperate", 0);
          }
      }
      
      FlowDefine fd = new FlowDefine();
      fd.setPurchaseTypeId(flowDefine.getPurchaseTypeId());
      fd.setStep(flowDefine.getStep() + 1);
      List<FlowDefine> nextFlowDefine = flowDefineMapper.findList(fd);
      if (nextFlowDefine != null && nextFlowDefine.size() > 0) {
          //下一环节
          FlowDefine fDefine = nextFlowDefine.get(0);
          FlowExecute flowExecute = new FlowExecute();
          flowExecute.setFlowDefineId(fDefine.getId());
          flowExecute.setIsDeleted(0);
          flowExecute.setProjectId(projectId);
          flowExecute.setStatus(0);
          List<FlowExecute> flowExecutes = flowExecuteMapper.findList(flowExecute);
          if (flowExecutes != null && flowExecutes.size() > 0) {
              FlowExecute flowExecute2 = flowExecutes.get(0);
              jsonObj.put("success", true);
              jsonObj.put("isEnd", false);
              jsonObj.put("operatorId", flowExecute2.getOperatorId());
              jsonObj.put("flowDefineId", fDefine.getId());
              jsonObj.put("flowDefineName", fDefine.getName());
          }
      } else {
          //当前环节是最后一个环节
          jsonObj.put("success", true);
          jsonObj.put("isEnd", true);
      }
      List<PurchaseInfo> purchaseInfo = new ArrayList<>();
      if(user != null && user.getOrg() != null){
         //获取当前用户所属机构人员
         purchaseInfo = purchaseInfoMapper.findPurchaseUserList(user.getOrg().getId());
      }
      jsonObj.put("users", purchaseInfo);
      return jsonObj;
  }

  @Override
  public JSONObject updateCurrOperator(User currLoginUser, String projectId, String currFlowDefineId, String currUpdateUserId) {
      JSONObject jsonObj = new JSONObject();
      FlowExecute flowExecute = new FlowExecute();
      flowExecute.setFlowDefineId(currFlowDefineId);
      flowExecute.setProjectId(projectId);
      flowExecute.setIsDeleted(0);
      flowExecute.setStatus(0);
      List<FlowExecute> flowExecutes = flowExecuteMapper.findList(flowExecute);
      if (flowExecutes != null && flowExecutes.size() > 0) {
          FlowExecute flowExecute2 = flowExecutes.get(0);
          flowExecute2.setOperatorId(currUpdateUserId);
          List<User> users = userMapper.selectByPrimaryKey(currUpdateUserId);
          if (users != null && users.size() > 0) {
              flowExecute2.setOperatorName(users.get(0).getRelName()); 
          }
          flowExecute2.setUpdatedAt(new Date());
          flowExecuteMapper.update(flowExecute2);
          FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
          if (flowDefine != null) {
              jsonObj.put("url", flowDefine.getUrl());
              jsonObj.put("flowDefineName", flowDefine.getName());
          }
          jsonObj.put("currLoginUser", currLoginUser);
          jsonObj.put("success", true);
      } else {
          jsonObj.put("success", false);
      }
      return jsonObj;
  }

  @Override
  public void updateStatus(Project project, String code) {
      if (project != null) {
          project.setStatus(DictionaryDataUtil.getId(code));
          projectMapper.updateByPrimaryKeySelective(project);
      }
  }

    @Override
    public List<FlowExecute> selectFlow(User user) {
        FlowExecute flowExecute = new FlowExecute();
        flowExecute.setOperatorId(user.getId());
        List<FlowExecute> flowExecutes = flowExecuteMapper.findLists(flowExecute);
        removeFlowExecute(flowExecutes);
        /*List<Project> list = new ArrayList<Project>();
        if(flowExecutes != null && flowExecutes.size() > 0){
            for (FlowExecute flowExecute2 : flowExecutes) {
              map.put("id", flowExecute2.getProjectId());
              List<Project> selectProjectsByConition = projectMapper.selectProjectsByConition(map);
              if(selectProjectsByConition != null){
                  list.addAll(selectProjectsByConition);
              }
          }
        }*/
        return flowExecutes;
    }
    
    public void removeFlowExecute(List<FlowExecute> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getProjectId().equals(list.get(i).getProjectId())) {
                    list.remove(j);
                }
            }
        }
    }

    @Override
    public List<Project> selectByConition(HashMap<String, Object> map) {
        
        return projectMapper.selectByConition(map);
    }

    @Override
    public JSONObject isSubmit(String projectId, String currFlowDefineId) {
        JSONObject jsonObj = new JSONObject();
        
        jsonObj.put("success", true);
        return jsonObj;
    }

    @Override
    public JSONObject submitHuanjie(User currLoginUser, String projectId, String currFlowDefineId) {
        JSONObject jsonObj = new JSONObject();
        FlowExecute temp = new FlowExecute();
        temp.setFlowDefineId(currFlowDefineId);
        temp.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowExecuteMapper.findExecuted(temp);
        //如果该项目该环节流程已经执行过
        if (flowExecutes != null && flowExecutes.size() > 0) {
            //执行记录设置为假删除状态
            FlowExecute oldFlowExecute = flowExecutes.get(0); 
            oldFlowExecute.setIsDeleted(1);
            oldFlowExecute.setUpdatedAt(new Date());
            flowExecuteMapper.update(oldFlowExecute);
            //新增一条相同环节记录
            oldFlowExecute.setCreatedAt(new Date());
            oldFlowExecute.setStatus(3);
            oldFlowExecute.setId(WfUtil.createUUID());
            oldFlowExecute.setIsDeleted(0);
            oldFlowExecute.setOperatorId(currLoginUser.getId());
            oldFlowExecute.setOperatorName(currLoginUser.getRelName());
            flowExecuteMapper.insert(oldFlowExecute);
        } else {
            //如果该项目该环节流程没有执行过
            FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setCreatedAt(new Date());
            flowExecute.setFlowDefineId(currFlowDefineId);
            flowExecute.setIsDeleted(0);
            flowExecute.setOperatorId(currLoginUser.getId());
            flowExecute.setOperatorName(currLoginUser.getRelName());
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(3);
            flowExecute.setId(WfUtil.createUUID());
            flowExecute.setStep(flowDefine.getStep());
            flowExecuteMapper.insert(flowExecute);
        }
        jsonObj.put("success", true);
        return jsonObj;
    }

  }
