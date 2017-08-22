package bss.service.ppms.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ses.dao.bms.UserMapper;
import ses.dao.oms.PurchaseInfoMapper;
import ses.dao.sms.QuoteMapper;
import ses.model.bms.User;
import ses.model.oms.PurchaseInfo;
import ses.model.sms.Quote;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.ppms.FlowDefineMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.TaskMapper;
import bss.model.pms.PurchaseDetail;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.Task;
import bss.service.ppms.ProjectService;

import com.github.pagehelper.PageHelper;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import common.service.impl.UploadServiceImpl;

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
	
	@Autowired
	private ProjectDetailMapper detailMapper;
	
	@Autowired
	private PurchaseDetailMapper purchaseDetailMapper;
	
	@Autowired
    private QuoteMapper quoteMapper;
	
	@Autowired

	private PackageMapper packageMapper;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private UploadService uploadService;
	
	
	
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
        if(StringUtils.isNotBlank(project.getId())){
            Project project2 = projectMapper.selectProjectByPrimaryKey(project.getId());
            if(project2 != null){
                if(project2.getProjectNumber().equals(project.getProjectNumber())){
                    flag= true;
                }else{
                    List<Project> list = projectMapper.verifyByProject(project);
                    for (int i = 0; i < list.size(); i++ ) {
                        if(list.get(i).getId().equals(project.getId())){
                            list.remove(i);
                        }
                    }
                    if(list != null && list.size()>0){
                        flag = false;
                    }
                }
            }else{
                List<Project> list = projectMapper.verifyByProject(project);
                if(list != null && list.size()>0){
                    flag = false;
                }
            }
        }else{
            List<Project> list = projectMapper.verifyByProject(project);
            if(list != null && list.size()>0){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public List<Project> lists(HashMap<String,Object> map) {
        
        return projectMapper.selectByList(map);
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
	      FlowDefine fd0 = new FlowDefine();
	      fd0.setIsDeleted(0);
	      fd0.setPurchaseTypeId(project.getPurchaseType());
	      List<FlowDefine> fds = flowDefineMapper.findList(fd0);
	      //如果当前项目没有初始化各环节经办人,或者初始化的环节不够
	      if (execute0s == null || execute0s.size() < fds.size()) {
  	        for (FlowExecute flowExecute : execute0s) {
  	            flowExecuteMapper.delete(flowExecute.getId());
  	        }
	          //设置各环节经办人默认为项目负责人
	          FlowExecute flowExecute = new FlowExecute();
	          flowExecute.setProjectId(projectId);
	          flowExecute.setStatus(0);
	          flowExecute.setCreatedAt(new Date());
	          flowExecute.setUpdatedAt(new Date());
	          List<User> operator = userMapper.selectByPrimaryKey(project.getPrincipal());
	          if (operator != null && operator.size() > 0) {
	              flowExecute.setOperatorId(operator.get(0).getId());
	              flowExecute.setOperatorName(operator.get(0).getRelName());
	          }
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
	      
	      //获取环节是否结束
          FlowExecute fe = new FlowExecute();
          fe.setFlowDefineId(flowDefine.getId());
          fe.setProjectId(projectId);
          fe.setStatus(3);
          List<FlowExecute> fes = flowExecuteMapper.findList(fe);
          if(fes != null && fes.size() > 0){
              jsonObj.put("isFes", 1);
          }else{
              jsonObj.put("isFes", 0);
          }
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
	              //环节是否结束
	              if(fes != null && fes.size() > 0){
	                  jsonObj.put("isOperate", 0);
	              } else {
	                //具有操作权限
	                  jsonObj.put("isOperate", 1);
	              }
	              
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
	              jsonObj.put("nextOperatorName", flowExecute2.getOperatorName());
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
         //获取当前项目所属机构人员
         String orgId = project.getPurchaseDepId();
         if (orgId != null && !"".equals(orgId)) {
             purchaseInfo = purchaseInfoMapper.findPurchaseUserList(orgId);
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
        FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
        if(flowDefine != null){
        	jsonObj.put("flowType", flowDefine.getCode());
            if("XMFB".equals(flowDefine.getCode())){
                //项目分包
                jsonObj.put("flowType", "XMFB");
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", projectId);
                List<ProjectDetail> details = detailMapper.selectById(map);
                List<ProjectDetail> bottomDetails = new ArrayList<>();//底层的明细
                for(ProjectDetail detail:details){
                    HashMap<String,Object> detailMap = new HashMap<>();
                    detailMap.put("id",detail.getRequiredId());
                    detailMap.put("projectId", projectId);
                    List<ProjectDetail> dlist = detailMapper.selectByParentId(detailMap);
                    if(dlist.size()==1){
                        bottomDetails.add(detail);
                    }
                }
                
                for(int i=0;i<bottomDetails.size();i++){
                    if(bottomDetails.get(i).getPackageId()==null){
                        jsonObj.put("success", false);
                        jsonObj.put("msg", "项目有明细未分包，是否默认为一包");
                        break;
                    }else if(i==bottomDetails.size()-1){
                        jsonObj.put("success", true);
                    }
                }
            }else if("XMXX".equals(flowDefine.getCode())){
                //项目信息
                jsonObj.put("flowTypes", "XMXX");
                Project project = projectMapper.selectProjectByPrimaryKey(projectId);
                if (project != null && project.getSupplierNumber() != null && project.getDeadline() != null && project.getBidDate() != null && !"".equals(project.getBidAddress()) && project.getBidAddress() != null ) {
                  jsonObj.put("success", true);
                }else {
                  jsonObj.put("success", false);
                  jsonObj.put("msgs", "请完善并保存项目信息");
                }
            }else if("KBCB".equals(flowDefine.getCode())){
                //开标唱标
                jsonObj.put("flowTypes", "KBCB");
                Quote quoteCondition = new Quote();
                quoteCondition.setProjectId(projectId);
                List<Date> listDate =  quoteMapper.selectQuoteCount(quoteCondition);
                if(listDate != null && listDate.size() > 0){
                    jsonObj.put("success", true);
                }else{
                    jsonObj.put("success", false);
                    jsonObj.put("msgs", "请填写报价");
                }
                
            }else if("NZCGWJ".equals(flowDefine.getCode())){
              String typeId = DictionaryDataUtil.getId("PROJECT_BID");
              List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
              if(files != null && files.size() > 0){
                  jsonObj.put("success", true);
              } else {
                  jsonObj.put("success", false);
                  jsonObj.put("msg", "请完善信息");
              }

            }else {
                jsonObj.put("success", true);
            }
        }
        //转竞争性谈判先不做！
        /*FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
        Project project = projectMapper.selectProjectByPrimaryKey(projectId);
        if(project != null){
            if(DictionaryDataUtil.getId("GKZB").equals(project.getStatus())){
                if("发售标书".equals(flowDefine.getName())){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("projectId",projectId);
                    List<Packages> list = packageMapper.findPackageById(map);
                    if(list != null && list.size() > 0){
                        for (int i = 0; i < list.size(); i++ ) {
                            SaleTender saleTender = new SaleTender();
                            Packages packages = list.get(i);
                            saleTender.setProject(new Project(projectId));
                            saleTender.setPackages(packages.getId());
                            List<SaleTender> saleTenderList = saleTenderMapper.getPackegeSupplier(saleTender);
                            if(saleTenderList.size() < 3){
                                jsonObj.put("erro", false);
                            }
                            
                        }
                    }
                }
            }
        }*/
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
            
            FlowDefine define = flowDefineMapper.get(currFlowDefineId);
            Project project = projectMapper.selectProjectByPrimaryKey(projectId);
            if ("FSBS".equals(define.getCode())) {
            	String status = DictionaryDataUtil.getId("GYSQD");
            	project.setStatus(status);
            	packageStatus(projectId, status);
            } else if ("GYSQD".equals(define.getCode())) {
            	String status = DictionaryDataUtil.getId("DKB");
            	project.setStatus(status);
            	packageStatus(projectId, status);
            } else if ("KBCB".equals(define.getCode())) {
            	String status = DictionaryDataUtil.getId("KBCBZ");
            	project.setStatus(status);
            	packageStatus(projectId, status);
            } else if ("ZZZJPS".equals(define.getCode())) {
            	String status = DictionaryDataUtil.getId("NZZBGG");
            	project.setStatus(status);
            	packageStatus(projectId, status);
            } else if ("QRZBGYS".equals(define.getCode())) {
            	String status = DictionaryDataUtil.getId("SSJS");
            	project.setStatus(status);
            	packageStatus(projectId, status);
            }
            projectMapper.updateByPrimaryKeySelective(project);
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
        FlowDefine flowDefine = flowDefineMapper.get(currFlowDefineId);
        if (flowDefine != null) {
            jsonObj.put("url", flowDefine.getUrl());
        }
        jsonObj.put("success", true);
        return jsonObj;
    }

	private void packageStatus(String projectId, String status) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("projectId", projectId);
		List<Packages> findByIds = packageMapper.findByID(hashMap);
		if(findByIds != null && findByIds.size() > 0){
			for (Packages packages : findByIds) {
				packages.setProjectStatus(status);
				packageMapper.updateByPrimaryKeySelective(packages);
			}
		}
	}

    @Override
    public List<Project> selectByOrg(HashMap<String, Object> map) {

        return projectMapper.selectByOrg(map);
    }

    @Override
    public List<Project> selectByDemand(HashMap<String, Object> map) {
        
        return projectMapper.selectByDemand(map);
    }

    @Override
    public List<Project> selectProjectByAudit(Integer page, Project project) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<Project> lists = projectMapper.selectProjectByAudit(project);
        return lists;
    }

    @Override
    public HashMap<String, Object> getFlowDefine(String purchaseTypeId, String projectId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        FlowDefine fd = new FlowDefine();
        fd.setPurchaseTypeId(purchaseTypeId);
        //该采购方式定义的流程环节
        List<FlowDefine> fds = flowDefineMapper.findList(fd);
        for (FlowDefine flowDefine : fds) {
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setProjectId(projectId);
            flowExecute.setFlowDefineId(flowDefine.getId());
            //获取该项目该环节的执行情况
            List<FlowExecute> flowExecutes2 = flowExecuteMapper.findStatusDesc(flowExecute);
            if (flowExecutes2 != null && flowExecutes2.size() > 0) {
                Integer s = flowExecutes2.get(0).getStatus();
                if (s == 1) {
                    //已执行状态
                    flowDefine.setStatus(1);
                } else if (s == 2) {
                    //执行中状态
                    flowDefine.setStatus(2);
                }else if (s == 3) {
                    //当前环节结束
                    flowDefine.setStatus(3);
                }
            } else {
                //未执行状态
                flowDefine.setStatus(0);
            }
        }
        map.put("url", fds.get(0).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(0).getId());
        map.put("fds", fds);
        return map;
    }

    @Override
    public JSONObject getNext(String projectId, String flowDefineId) {
        JSONObject jsonObj = new JSONObject();
      //判断是否要进入开标环节
        int count = 0;
        Project project = projectMapper.selectProjectByPrimaryKey(projectId);
        FlowDefine fds = new FlowDefine();
        fds.setPurchaseTypeId(project.getPurchaseType());
        List<FlowDefine> find = flowDefineMapper.findList(fds);
        for (FlowDefine flowDefine : find) {
            if("KBCB".equals(flowDefine.getCode())){
                count = flowDefine.getStep();
                break;
            }
        }
        FlowDefine define = flowDefineMapper.get(flowDefineId);
        // 组织专家评审前判断开标唱标是否完成
        if(define != null && "ZZZJPS".equals(define.getCode()) && define.getStep() >= count){
            FlowExecute execute = new FlowExecute();
            execute.setProjectId(projectId);
            execute.setStep(8);
            List<FlowExecute> executes = flowExecuteMapper.findList(execute);
            StringBuffer sb = new StringBuffer();
            if(executes != null && !executes.isEmpty()){
                for (FlowExecute fe : executes){
                    sb.append(fe.getStatus() + ",");
                }
                if(!sb.toString().contains("3")){
                    FlowDefine define2 = flowDefineMapper.get(executes.get(0).getFlowDefineId());
                    jsonObj.put("name", define2.getName());
                    jsonObj.put("next", "1");
                    return jsonObj;
                }
            }
        }

        if(define != null && !"ZZZJPS".equals(define.getCode()) && define.getStep() >= count){
            //根据采购方式获取当前所有的环节
            FlowDefine fd = new FlowDefine();
            fd.setPurchaseTypeId(project.getPurchaseType());
            List<FlowDefine> defines = flowDefineMapper.findList(fd);
            List<FlowDefine> list = new ArrayList<FlowDefine>();
            if(defines != null && defines.size() > 0){
               //根据当前环节的步骤获取前面的环节
                for (FlowDefine flowDefine : defines) {
                    if(flowDefine.getStep() < define.getStep()){
                        if(!"CQPSZJ".equals(flowDefine.getCode())){
                            list.add(flowDefine);
                        }
                    }
                }
            }
            
            //获取到所有小于当前环节的流程
            if(list != null && list.size() > 0){
                for (FlowDefine flowDefine : list) {
                    FlowExecute execute = new FlowExecute();
                    execute.setProjectId(projectId);
                    execute.setFlowDefineId(flowDefine.getId());
                    List<FlowExecute> executes = flowExecuteMapper.findList(execute);
                    if(executes != null && executes.size() > 0){
                        for (int i = 0; i < executes.size(); i++ ) {
                            //判断每一个环节是否有环节结束的状态，有的话跳出循环
                            if(executes.get(i).getStatus() == 3){
                                break;
                            } else if (i == executes.size() - 1){
                                FlowDefine define2 = flowDefineMapper.get(executes.get(i).getFlowDefineId());
                                jsonObj.put("name", define2.getName());
                                jsonObj.put("next", "1");
                                return jsonObj;
                            }
                        }
                    }
                }
            }
        }
        jsonObj.put("next", "2");
        return jsonObj;
    }

    @Override
    public void addProejctDetail(List<PurchaseDetail> list, String projectId, Integer position) {
        if(list != null && list.size() > 0 && StringUtils.isNotBlank(projectId)){
            for (PurchaseDetail purchaseDetail : list) {
                ProjectDetail projectDetail = new ProjectDetail();
                if(StringUtils.isNotBlank(purchaseDetail.getId())){
                    projectDetail.setRequiredId(purchaseDetail.getId());
                }
                if(StringUtils.isNotBlank(purchaseDetail.getSeq())){
                    projectDetail.setSerialNumber(purchaseDetail.getSeq());
                }
                if(StringUtils.isNotBlank(purchaseDetail.getDepartment())){
                    projectDetail.setDepartment(purchaseDetail.getDepartment());
                }
                if(StringUtils.isNotBlank(purchaseDetail.getGoodsName())){
                    projectDetail.setGoodsName(purchaseDetail.getGoodsName());
                }
                if(StringUtils.isNotBlank(purchaseDetail.getStand())){
                    projectDetail.setStand(purchaseDetail.getStand());
                }
                if(StringUtils.isNotBlank(purchaseDetail.getQualitStand())){
                    projectDetail.setQualitStand(purchaseDetail.getQualitStand());
                }
                if(StringUtils.isNotBlank(purchaseDetail.getItem())){
                    projectDetail.setItem(purchaseDetail.getItem());
                }
                projectDetail.setCreatedAt(new Date());
                projectDetail.setProject(new Project(projectId));
                if (purchaseDetail.getPurchaseCount() != null) {
                    projectDetail.setPurchaseCount(purchaseDetail.getPurchaseCount().doubleValue());
                }
                if (purchaseDetail.getPrice() != null) {
                    projectDetail.setPrice(purchaseDetail.getPrice().doubleValue());
                }
                if (purchaseDetail.getBudget() != null) {
                    projectDetail.setBudget(purchaseDetail.getBudget().doubleValue());
                }
                if (StringUtils.isNotBlank(purchaseDetail.getDeliverDate())) {
                    projectDetail.setDeliverDate(purchaseDetail.getDeliverDate());
                }
                if (StringUtils.isNotBlank(purchaseDetail.getPurchaseType())) {
                    projectDetail.setPurchaseType(purchaseDetail.getPurchaseType());
                }
                if (StringUtils.isNotBlank(purchaseDetail.getSupplier())) {
                    projectDetail.setSupplier(purchaseDetail.getSupplier());
                }
                if (StringUtils.isNotBlank(purchaseDetail.getIsFreeTax())) {
                    projectDetail.setIsFreeTax(purchaseDetail.getIsFreeTax());
                }
                if (StringUtils.isNotBlank(purchaseDetail.getGoodsUse())) {
                    projectDetail.setGoodsUse(purchaseDetail.getGoodsUse());
                }
                if (StringUtils.isNotBlank(purchaseDetail.getUseUnit())) {
                    projectDetail.setUseUnit(purchaseDetail.getUseUnit());
                }
                if (StringUtils.isNotBlank(purchaseDetail.getParentId())) {
                    projectDetail.setParentId(purchaseDetail.getParentId());
                }
                if (purchaseDetail.getDetailStatus() != null) {
                    projectDetail.setStatus(String.valueOf(purchaseDetail.getDetailStatus()));
                }
                if(StringUtils.isNotBlank(purchaseDetail.getMemo())){
                    projectDetail.setMemo(purchaseDetail.getMemo());
                }
                projectDetail.setPosition(position);
                position++;
                detailMapper.insertSelective(projectDetail);
            }
        }
    }

    @Override
    public void updateDetailStatus(List<PurchaseDetail> list, String projectId) {
        if(list != null && list.size() > 0){
            HashSet<String> set = new HashSet<>();
            for (PurchaseDetail purchaseDetail : list) {
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("id", purchaseDetail.getId());
                List<PurchaseDetail> details = purchaseDetailMapper.selectByParentId(map);
                if(details != null && details.size() == 1){
                    //如果是末级，则设为暂时选中状态
                    purchaseDetail.setProjectStatus(2);
                    purchaseDetailMapper.updateByPrimaryKeySelective(purchaseDetail);
                    
                    if (purchaseDetail.getParentId() != null && !"1".equals(purchaseDetail.getParentId())) {
                      set.add(purchaseDetail.getParentId());
                    }
                } 
            }
            //List<PurchaseDetail> bottomDetails = new ArrayList<PurchaseDetail>();
            for (String string : set) {
                PurchaseDetail detail = purchaseDetailMapper.selectByPrimaryKey(string);
                if(detail != null){
                    updateDetailStatusParent(projectId, detail);
                    
                   /* map.put("id", detail.getId());
                    List<PurchaseDetail> list2 = purchaseDetailMapper.selectByParentId(map);
                    for (PurchaseDetail purchaseDetail1 : list2) {
                        if(!purchaseDetail1.getId().equals(string)){
                            bottomDetails.add(purchaseDetail1);
                        }
                    }*/
                   /* for (int i = 0; i < bottomDetails.size(); i++ ) {
                        if(bottomDetails.get(i).getProjectStatus() == 0){
                            break;
                        }else if(i == bottomDetails.size()-1){
                            detail.setProjectStatus(2);
                            purchaseDetailMapper.updateByPrimaryKeySelective(detail);
                        }
                    }*/
                }
            }
        }
    }

	@Override
	public List<Project> selectByOrgnization(HashMap<String, Object> map) {
		
		return projectMapper.selectByOrgnization(map);
	}
    private void updateDetailStatusParent(String projectId, PurchaseDetail purchaseDetail) {
      Map<String,Object> map=new HashMap<String,Object>();
      map.put("parentId", purchaseDetail.getId());
      List<PurchaseDetail> detailChilds = purchaseDetailMapper.getByMap(map);
      //被选中子节点的数量
      int count = 0;
      for (int i = 0; i < detailChilds.size(); i++) {
        if (detailChilds.get(i).getProjectStatus() == 0) {
          break;
        } else if (detailChilds.get(i).getProjectStatus() == 2){
          String isUse = isUseForPlanDetail(projectId, detailChilds.get(i).getId());
          if (isUse != null && "flase".equals(isUse)) {
            break;
          }
          if (isUse != null && "true".equals(isUse)) {
            count += 1;
          }
        }else if(detailChilds.get(i).getProjectStatus() == 1){
          count += 1;
        }
      }
      if (count == detailChilds.size()) {
        //如果子节点全部被选用
        purchaseDetail.setProjectStatus(2);
        purchaseDetailMapper.updateByPrimaryKeySelective(purchaseDetail);
        PurchaseDetail detailParent = purchaseDetailMapper.selectByPrimaryKey(purchaseDetail.getParentId());
        if(detailParent != null){
          updateDetailStatusParent(projectId, detailParent);
        }
      }else {
        //如果子节点未全部被选用
        purchaseDetail.setProjectStatus(0);
        purchaseDetailMapper.updateByPrimaryKeySelective(purchaseDetail);
        PurchaseDetail detailParent = purchaseDetailMapper.selectByPrimaryKey(purchaseDetail.getParentId());
        if(detailParent != null){
          updateDetailStatusParent(projectId, detailParent);
        }
      }
      
    }

    @Override
    public List<Project> selectByProject(HashMap<String, Object> map) {
        return projectMapper.selectByProject(map);
    }

    @Override
    public String isUseForPlanDetail(String projectId, String detailId) {
        PurchaseDetail purchaseDetail = purchaseDetailMapper.selectByPrimaryKey(detailId);
        if (purchaseDetail != null && purchaseDetail.getProjectStatus() == 1) {
            //该明细已经被立项引用
            return "true";
        } else if (purchaseDetail != null && purchaseDetail.getProjectStatus() == 2) {
            //该明细被暂时引用过
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("requiredId", detailId);
            map.put("id", projectId);
            List<ProjectDetail> projectDetails = detailMapper.selectById(map);
            if (projectDetails != null && projectDetails.size() > 0) {
              //该明细正在被该项目引用
              return "true";
            }else {
              //该明细未被该项目暂时引用
              return "false";
            }
        } else if (purchaseDetail != null && purchaseDetail.getProjectStatus() == 0) {
          //该明细未被引用
          return "false";
        } else {
          return null;
        }
    }

    @Override
    public void saveProject(User user, Project project, String checkId) {
      project.setIsRehearse(0);
      project.setIsProvisional(0);
      project.setAppointMan(user.getId());
      String type = null;
      String[] id = checkId.split(",");
      List<String> list = getIds(id);
      for (int i = 0; i < list.size(); i++ ) {
         ProjectDetail detail = detailMapper.selectByPrimaryKey(list.get(i));
         if (detail != null) {
             HashMap<String, Object> map = new HashMap<>();
             map.put("projectId", project.getId());
             map.put("id", detail.getRequiredId());
             List<ProjectDetail> lists = detailMapper.selectByParentId(map);
             if(lists != null && lists.size() == 1){
               //获取子节点采购方式
               type = detail.getPurchaseType();
               break;
             }
         }
      }
      if(StringUtils.isNotBlank(type)){
          project.setPurchaseType(type);
      }
     
      projectMapper.updateByPrimaryKeySelective(project);
    }
    
    /**
     * 
     *〈数组去重〉
     *〈详细描述〉
     * @author FengTian
     * @param ids
     * @return
     */
    public List<String> getIds(String ids[]){
        List<String> list= new ArrayList<String>();
        for(String id:ids){
            list.add(id);
        }
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).toString().equals(list.get(i).toString())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    @Override
    public void updateProjectStatus(User currUser, String cheeckedDetail, String projectId) {
      String[] id = cheeckedDetail.split(",");
      List<String> list = getIds(id);
      HashSet<String> uniqueIds = new HashSet<>();
      for (int i = 0; i < list.size(); i++ ) {
        ProjectDetail detail = detailMapper.selectByPrimaryKey(list.get(i));
        if (detail != null) {
          PurchaseDetail purchaseDetail = purchaseDetailMapper.selectByPrimaryKey(detail.getRequiredId());
          uniqueIds.add(purchaseDetail.getUniqueId());
        }
      }
      if (uniqueIds != null && uniqueIds.size() > 0) {
        for (String uniqueId : uniqueIds) {
          String organization = currUser.getOrg().getId();
          
          List<PurchaseDetail> lists = purchaseDetailMapper.getUniquesTempByTask(projectId, uniqueId, organization);
          if (lists != null && lists.size() > 0) {
            for (PurchaseDetail purchaseDetail : lists) {
              purchaseDetail.setProjectStatus(1);
              purchaseDetailMapper.updateByPrimaryKeySelective(purchaseDetail);
            }
          }
          
          List<Integer> groupByStatus = purchaseDetailMapper.groupByStatus(uniqueId);
          if(groupByStatus != null && groupByStatus.size() == 1){
              if(groupByStatus.get(0) == 1){
                  HashMap<String, Object> map = new HashMap<>();
                  map.put("purchaseId", organization);
                  map.put("collectId", uniqueId);
                  List<Task> listBycollect = taskMapper.listBycollect(map);
                  if(listBycollect != null && listBycollect.size() > 0){
                      listBycollect.get(0).setNotDetail(1);
                      taskMapper.updateByPrimaryKeySelective(listBycollect.get(0));
                  }
              }
          }
        }
      }
      
    } 
  }
