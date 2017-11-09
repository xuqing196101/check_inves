package sums.service.ss.impl;

import iss.dao.ps.ArticleMapper;
import iss.dao.ps.ArticleTypeMapper;
import iss.model.ps.Article;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.DateUtils;

import bss.dao.cs.ContractRequiredMapper;
import bss.dao.cs.PurchaseContractMapper;
import bss.dao.pms.CollectPlanMapper;
import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.ppms.AdvancedProjectMapper;
import bss.dao.ppms.FlowDefineMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.dao.ppms.NegotiationReportMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.ProjectTaskMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.ppms.TaskMapper;
import bss.dao.pqims.PqInfoMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.model.pqims.PqInfo;
import bss.model.prms.PackageExpert;
import bss.service.pms.PurchaseRequiredService;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ProExtSuperviseMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.SupplierExtUserMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ProExtSupervise;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtUser;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import sums.model.ss.Supervision;
import sums.service.ss.PlanSupervisionService;
import sums.service.ss.SupervisionService;

@Service("planSupervisionService")
public class PlanSupervisionServiceImpl implements PlanSupervisionService{
    
    @Autowired
    private ProjectDetailMapper projectDetailMapper;
    
    @Autowired
    private ContractRequiredMapper contractRequiredMapper;
    
    @Autowired
    private PurchaseContractMapper contractMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;
    
    @Autowired
    private PurchaseRequiredService requiredService;
    
    @Autowired
    private ProjectTaskMapper projectTaskMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    
    @Autowired
    private SupplierCheckPassMapper checkPassMapper;
    
    @Autowired
    private SupplierMapper supplierMapper;
    
    @Autowired
    private SupervisionService supervisionService;
    
    @Autowired
    private PackageMapper packageMapper;
    
    @Autowired
    private CollectPlanMapper collectPlanMapper;
    
    @Autowired
    private FlowDefineMapper flowDefineMapper;
    
    @Autowired
    private FlowExecuteMapper flowExecuteMapper;
    
    @Autowired
    private SaleTenderMapper saleTenderMapper;
    
    @Autowired
	private PackageExpertMapper packageExpertMapper;
    
    @Autowired
    private AdvancedProjectMapper advancedProjectMapper;
    
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper;
    
    @Autowired
    private ArticleTypeMapper articleTypeMapper;
    
    @Autowired
    private ArticleMapper articleMapper;
    
    @Autowired
    private SupplierExtUserMapper extUserMapper;
    
    @Autowired
	private ProExtSuperviseMapper extSuperviseMapper;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private ExpertMapper expertMapper;
    
    @Autowired
    private NegotiationReportMapper reportMapper;
    
    @Autowired
    private PqInfoMapper pqInfoMapper;

    @Override
    public List<PurchaseRequired> viewDemand(String id) {
        List<PurchaseRequired> listRequired = new ArrayList<>();
        List<PurchaseDetail> details = purchaseDetailMapper.getByUinuqeId(id, null, null);
        if(details != null && details.size() > 0){
            HashSet<String> set = new HashSet<>();
            for (PurchaseDetail detail : details) { 
                set.add(detail.getFileId());
            }
            for (String string : set) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("fileId", string);
                List<PurchaseRequired> lists = requiredService.getByMap(map);
                if(lists != null && lists.size() > 0){
                    for (PurchaseRequired purchaseRequired : lists) {
                        if("1".equals(purchaseRequired.getParentId())){
                            List<User> users = userMapper.selectByPrimaryKey(purchaseRequired.getUserId());
                            if(users != null && users.size() > 0){
                                purchaseRequired.setUserId(users.get(0).getRelName());
                            }
                            listRequired.add(purchaseRequired);
                            break;
                        }
                    }
                }
            }
        }
        return listRequired;
    }

    @Override
    public List<Project> viewProject(String id) {
        List<Project> listProject = new ArrayList<Project>();
        //任务信息
        HashMap<String, Object> mapTask = new HashMap<>();
        mapTask.put("collectId", id);
        List<Task> task = taskMapper.listBycollect(mapTask);
        if(task != null && task.size() > 0){
            for (Task task2 : task) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("taskId", task2.getId());
                List<ProjectTask> projectTasks = projectTaskMapper.queryByNo(map);
                HashSet<String> set = new HashSet<>();
                if(projectTasks != null && projectTasks.size() > 0){
                    for (ProjectTask projectTask : projectTasks) {
                        set.add(projectTask.getProjectId());
                    }
                }
                for (String string : set) {
                	HashMap<String, Object> maps = new HashMap<>();
                	maps.put("parentId", string);
                	List<Project> selectByList = projectMapper.selectByList(maps);
                	if(selectByList != null && selectByList.size() > 0){
                		for (Project project : selectByList) {
                			if(!"4".equals(project.getStatus())){
                                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                                if(StringUtils.isNotBlank(project.getAppointMan())){
                                    List<User> users = userMapper.selectByPrimaryKey(project.getAppointMan());
                                    project.setAppointMan(users.get(0).getRelName());
                                    project.setAddress(users.get(0).getAddress());
                                }
                                if(StringUtils.isNotBlank(project.getPurchaseDepId())){
                                    Orgnization org = orgnizationMapper.findOrgByPrimaryKey(project.getPurchaseDepId());
                                    project.setPurchaseDepId(org.getName());
                                }
                                project.setStatus(findById.getName());
                                listProject.add(project);
                            }
						}
                	}
                }
            }
        }
        return listProject;
    }

    @Override
    public List<PurchaseContract> viewContract(String id) {
        List<PurchaseContract> listContract = new ArrayList<PurchaseContract>();
        //任务信息
        HashMap<String, Object> mapTask = new HashMap<>();
        mapTask.put("collectId", id);
        List<Task> task = taskMapper.listBycollect(mapTask);
        if(task != null && task.size() > 0){
            for (Task task2 : task) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("taskId", task2.getId());
                List<ProjectTask> projectTasks = projectTaskMapper.queryByNo(map);
                HashSet<String> set = new HashSet<>();
                if(projectTasks != null && projectTasks.size() > 0){
                    for (ProjectTask projectTask : projectTasks) {
                        set.add(projectTask.getProjectId());
                    }
                }
                for (String string : set) {
                    Project project = projectMapper.selectProjectByPrimaryKey(string);
                    if(project != null){
                        if(!"4".equals(project.getStatus())){
                            SupplierCheckPass checkPass = new SupplierCheckPass();
                            checkPass.setProjectId(project.getId());
                            checkPass.setIsWonBid((short)1);
                            List<SupplierCheckPass> checkPasses = checkPassMapper.listCheckPass(checkPass);
                            if(checkPasses != null && checkPasses.size() > 0){
                                for (SupplierCheckPass supplierCheckPass : checkPasses) {
                                    PurchaseContract contract = contractMapper.selectContractByid(supplierCheckPass.getContractId());
                                    if(contract != null){
                                        listContract.add(contract);
                                    }
                                }
                                if(listContract != null && listContract.size() > 0){
                                    for (PurchaseContract pur : listContract) {
                                        Supplier su = null;
                                        Orgnization org = null;
                                        if(pur.getSupplierDepName()!=null){
                                            su = supplierMapper.selectOne(pur.getSupplierDepName());
                                        }
                                        if(pur.getPurchaseDepName()!=null){
                                            org = orgnizationMapper.findOrgByPrimaryKey(pur.getPurchaseDepName());
                                        }
                                        if(org!=null){
                                            if(org.getName()==null){
                                                pur.setShowDemandSector("");
                                            }else{
                                                pur.setShowDemandSector(org.getName());
                                            }
                                        }
                                        if(su!=null){
                                            if(su.getSupplierName()!=null){
                                                pur.setShowSupplierDepName(su.getSupplierName());
                                            }else{
                                                pur.setShowSupplierDepName("");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return listContract;
    }

    @Override
    public Integer projectStatus(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("collectId", id);
        List<Task> listBycollect = taskMapper.listBycollect(map);
        List<String> status = new ArrayList<String>();
        List<Project> projectList = new ArrayList<Project>();
        if(listBycollect != null && listBycollect.size() > 0){
            for (Task task : listBycollect) {
                map.put("taskId", task.getId());
                List<ProjectTask> projectTasks = projectTaskMapper.queryByNo(map);
                for (ProjectTask projectTask : projectTasks) {
                	HashMap<String, Object> hashMap = new HashMap<>();
                	hashMap.put("parentId", projectTask.getProjectId());
                	List<Project> selectByList = projectMapper.selectByList(hashMap);
                	if(selectByList != null && selectByList.size() > 0){
                		projectList.addAll(selectByList);
                	}
                }
            }
        }
        if(projectList != null && projectList.size() > 0){
        	for (Project project : projectList) {
                if(project != null && !"4".equals(project.getStatus())){
                	DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                	if(!"YJFB".equals(findById.getCode())){
                		String projectStatus = supervisionService.progressBarProject(project.getStatus());
                        String proStatus = projectStatus + ".00";
                        status.add(proStatus);
                	}
                }
			}
        }
        if(status != null && status.size() > 0){
            Integer num = 0;
            for (String string : status) {
                double number = Double.valueOf(string)/status.size();
                BigDecimal b = new BigDecimal(number);
                double total = b.setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
                num += (int)total;
            }
            return num;
        }
        return null;
    }

    @Override
    public Integer contractStatus(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("collectId", id);
        List<Task> listBycollect = taskMapper.listBycollect(map);
        List<Integer> status = new ArrayList<Integer>();
        if(listBycollect != null && listBycollect.size() > 0){
            for (Task task : listBycollect) {
                map.put("taskId", task.getId());
                List<ProjectTask> projectTasks = projectTaskMapper.queryByNo(map);
                for (ProjectTask projectTask : projectTasks) {
                    Project project = projectMapper.selectProjectByPrimaryKey(projectTask.getProjectId());
                    if(project != null && !"4".equals(project.getStatus())){
                        HashMap<String, Object> maps = new HashMap<>();
                        maps.put("id", project.getId());
                        List<ProjectDetail> selectById = projectDetailMapper.selectById(maps);
                        if(selectById != null && selectById.size() > 0){
                            for (ProjectDetail projectDetail : selectById) {
                                List<ContractRequired> contractRequireds = contractRequiredMapper.selectConRequByDetailId(projectDetail.getId());
                                if(contractRequireds != null && contractRequireds.size()>0){
                                    PurchaseContract purchaseContract = contractMapper.selectContractByid(contractRequireds.get(0).getContractId());
                                    Integer progressBarContract = supervisionService.progressBarContract(purchaseContract.getStatus());
                                    status.add(progressBarContract);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(status != null && status.size() > 0){
            Integer num = 0;
            for (Integer integer : status) {
                double number = integer/status.size();
                BigDecimal b = new BigDecimal(number);
                double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                num += (int)total;
            }
            return num;
        }
        return null;
    }

    @Override
    public List<Packages> viewPack(String projectId) {
		List<Packages> list = new ArrayList<Packages>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("parentId", projectId);
		List<Project> lists = projectMapper.selectByList(map);
		if(lists != null && lists.size() > 0){
			for (Project project : lists) {
				HashMap<String, Object> pack = new HashMap<>();
				pack.put("projectId", project.getId());
    			List<Packages> packages2 = packageMapper.findPackageById(pack);
    			if(packages2 != null && packages2.size() > 0){
					for (Packages ps : packages2) {
		                list.add(ps);
					}
				}
			}
		}
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("projectId", projectId);
		List<Packages> packages = packageMapper.findPackageById(maps);
		if(packages != null && packages.size() > 0){
			for (Packages ps : packages) {
                list.add(ps);
			}
		}
		if(list != null && list.size() > 0){
			for (Packages ps : list) {
				HashMap<String,Object> packageId = new HashMap<>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = projectDetailMapper.selectById(packageId);
                if(detailList != null && detailList.size() > 0){
                	for (ProjectDetail projectDetail : detailList) {
                		DictionaryData findById = DictionaryDataUtil.findById(projectDetail.getPurchaseType());
                		projectDetail.setPurchaseType(findById.getName());
                        String[] progressBarPlan = supervisionService.progressBar(projectDetail.getRequiredId(), projectId);
                        projectDetail.setProgressBar(progressBarPlan[0]);
                        projectDetail.setStatus(progressBarPlan[1]);
					}
                	ps.setProjectDetails(detailList);
                }
			}
			sortDatePack(list);
		}
		return list;
	}
    
    public void sortDatePack(List<Packages> list){
        Collections.sort(list, new Comparator<Packages>(){
           @Override
           public int compare(Packages o1, Packages o2) {
        	   Packages task = (Packages) o1;
        	   Packages task2 = (Packages) o2;
              return task.getCreatedAt().compareTo(task2.getCreatedAt());
           }
        });
    }
    
    public void sort(List<ProjectDetail> list){
        Collections.sort(list, new Comparator<ProjectDetail>(){
           @Override
           public int compare(ProjectDetail o1, ProjectDetail o2) {
              Integer i = o1.getPosition() - o2.getPosition();
              return i;
           }
        });
    }

	@Override
	public CollectPlan viewCollectPlan(String id) {
		if(StringUtils.isNotBlank(id)){
			CollectPlan collectPlan = collectPlanMapper.selectByPrimaryKey(id);
			if(collectPlan != null){
				List<User> users = userMapper.selectByPrimaryKey(collectPlan.getUserId());
				if(users != null && users.size() > 0){
					collectPlan.setUserId(users.get(0).getRelName());
	                collectPlan.setPurchaseId(users.get(0).getOrgName());
				}
				HashMap<String, Object> mapTask = new HashMap<>();
                mapTask.put("collectId", collectPlan.getId());
                List<Task> listBycollect = taskMapper.listBycollect(mapTask);
                if(listBycollect != null && listBycollect.size() > 0){
                    collectPlan.setUpdatedAt(listBycollect.get(0).getGiveTime());
                    collectPlan.setTaskId(listBycollect.get(0).getDocumentNumber());
                }
                return collectPlan;
			}
		}
		return null;
	}

	@Override
	public Task viewTask(PurchaseDetail detail) {
		HashMap<String, Object> taskMap = new HashMap<>();
        taskMap.put("collectId", detail.getUniqueId());
        taskMap.put("purchaseId", detail.getOrganization());
        List<Task> task = taskMapper.listBycollect(taskMap);
        if(task != null && task.size() > 0){
            if(StringUtils.isNotBlank(task.get(0).getUserId())){
                List<User> user = userMapper.selectByPrimaryKey(task.get(0).getUserId());
                task.get(0).setUserId(user.get(0).getRelName());
            }
            if(StringUtils.isNotBlank(task.get(0).getCreaterId())){
            	List<User> user = userMapper.selectByPrimaryKey(task.get(0).getCreaterId());
                task.get(0).setCreaterId(user.get(0).getRelName());
                task.get(0).setMaterialsType(user.get(0).getMobile());
            }
            Orgnization org = orgnizationMapper.findOrgByPrimaryKey(task.get(0).getPurchaseId());
            task.get(0).setPurchaseId(org.getShortName());
            return task.get(0);
        }
		return null;
	}

	@Override
	public List<Project> view(String id) {
		List<Project> list = new ArrayList<Project>();
		if(StringUtils.isNotBlank(id)){
			HashMap<String, Object> map = new HashMap<>();
            map.put("requiredId", id);
            List<ProjectDetail> selectById = projectDetailMapper.selectById(map);
            if(selectById != null && selectById.size() > 0){
            	for (ProjectDetail projectDetail : selectById) {
            		Project project = viewProjects(projectDetail.getProject().getId());
                	if(project != null && !"4".equals(project.getStatus())){
                        list.add(project);
                	}
				}
            }
		}
		return list;
	}

	@Override
	public FlowExecute operator(FlowDefine define, String projectId) {
		if(StringUtils.isNotBlank(projectId)){
            List<FlowDefine> find = flowDefineMapper.findList(define);
            if(find != null && find.size() > 0){
            	 FlowExecute execute = new FlowExecute();
                 execute.setProjectId(projectId);
                 execute.setFlowDefineId(find.get(0).getId());
                 execute.setStatus(1);
                 List<FlowExecute> findFlowExecute = flowExecuteMapper.findList(execute);
                 if(findFlowExecute != null && findFlowExecute.size() > 0){
                	 return findFlowExecute.get(0);
                 } else {
                	 execute.setStatus(3);
                     List<FlowExecute> findFlowExecutes = flowExecuteMapper.findList(execute);
                     if(findFlowExecutes != null && findFlowExecutes.size() > 0){
                    	 return findFlowExecutes.get(0);
                     } else {
                         execute.setStatus(2);
                         List<FlowExecute> executes = flowExecuteMapper.findList(execute);
                         if(executes != null && executes.size() > 0){
                        	 return executes.get(0);
                         }
                     }
                 }
            }
		}
		return null;
	}

	@Override
	public TreeSet<Long> releaseTime(String detailId, String projectId) {
		TreeSet<Long> set = new TreeSet<Long>();
		HashMap<String, Object> map = new HashMap<>();
        map.put("requiredId", detailId);
        map.put("id", projectId);
        List<ProjectDetail> selectById = projectDetailMapper.selectById(map);
        if(selectById != null && selectById.size() > 0){
        	SaleTender saleTender = new SaleTender();
        	if(StringUtils.isNotBlank(selectById.get(0).getPackageId())){
        		saleTender.setPackages(selectById.get(0).getPackageId());
                List<SaleTender> saleTenderList = saleTenderMapper.getPackegeSupplier(saleTender);
                for (SaleTender saleTender2 : saleTenderList) {
                    if(saleTender2.getCreatedAt() != null){
                        Date createdAt = saleTender2.getCreatedAt();
                        try {
                            long simp=new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(createdAt)).getTime();
                            set.add(simp);
                         } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
        	}
        }
		return set;
	}

	@Override
	public List<PackageExpert> viewPackageExpert(String detailId, String projectId) {
		if(StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(detailId)){
			HashMap<String, Object> map = new HashMap<>();
	        map.put("requiredId", detailId);
	        map.put("id", projectId);
	        List<ProjectDetail> selectById = projectDetailMapper.selectById(map);
	        if(selectById != null && selectById.size() > 0){
	        	HashMap<String, Object> packageExpertmap = new HashMap<>();
	        	packageExpertmap.put("packageId", selectById.get(0).getPackageId());
                packageExpertmap.put("projectId", projectId);
                //查询专家
                List<PackageExpert> expertIdList = packageExpertMapper.selectList(packageExpertmap);
                if(expertIdList != null && expertIdList.size() > 0){
                	return expertIdList;
                }
	        }
		}
		return null;
	}

	@Override
	public PurchaseContract viewPurchaseContract(String id) {
		if(StringUtils.isNotBlank(id)){
			List<ContractRequired> contractRequireds = contractRequiredMapper.selectConRequByDetailId(id);
			if(contractRequireds != null && contractRequireds.size() > 0){
				PurchaseContract purchaseContract = contractMapper.selectContractByid(contractRequireds.get(0).getContractId());
				if(purchaseContract != null){
					if(StringUtils.isNotBlank(purchaseContract.getPurchaseDepName())){
						Orgnization org = orgnizationMapper.findOrgByPrimaryKey(purchaseContract.getPurchaseDepName());
						if(org != null){
							purchaseContract.setPurchaseDepName(org.getShortName());
						} else {
							purchaseContract.setPurchaseDepName(null);
						}
					}
					if(StringUtils.isNotBlank(purchaseContract.getSupplierDepName())){
						Supplier supplier = supplierMapper.selectByPrimaryKey(purchaseContract.getSupplierDepName());
                        if(supplier != null){
                            purchaseContract.setSupplierDepName(supplier.getSupplierName());
                        }else{
                            purchaseContract.setSupplierDepName(null);
                        }
					}
					return purchaseContract;
				}
			}
		}
		return null;
	}

	@Override
	public PurchaseRequired viewPurchaseRequired(String id) {
		if(StringUtils.isNotBlank(id)){
			PurchaseRequired required = null;
			HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            List<PurchaseRequired> requireds = requiredService.selectByParent(map);
            if(requireds != null && requireds.size() > 0){
                for (PurchaseRequired purchaseRequired : requireds) {
                    if("1".equals(purchaseRequired.getParentId())){
                    	List<User> user = userMapper.selectByPrimaryKey(purchaseRequired.getUserId());
                        if(user != null && user.size() > 0){
                            purchaseRequired.setUserId(user.get(0).getRelName());
                            purchaseRequired.setCode(user.get(0).getMobile());
                        }
                        required = purchaseRequired;
                        break;
                    }
                }
            }
            return required;
		}
		return null;
	}

	@Override
	public List<FlowDefine> getFlows(FlowDefine flowDefine) {
		
		return flowDefineMapper.getFlow(flowDefine);
	}

	@Override
	public Task viewAdvancedTask(String projectId) {
		if(StringUtils.isNotBlank(projectId)){
			HashMap<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            List<ProjectTask> queryByNo = projectTaskMapper.queryByNo(map);
            if(queryByNo != null && queryByNo.size() > 0){
            	Task task = taskMapper.selectByPrimaryKey(queryByNo.get(0).getTaskId());
            	if(task != null){
            		if(StringUtils.isNotBlank(task.getOrgId())){
            			Orgnization orgnization = orgnizationMapper.findOrgByPrimaryKey(task.getOrgId());
            			task.setOrgName(orgnization.getShortName());
            		}
            		if(StringUtils.isNotBlank(task.getPurchaseId())){
            			Orgnization orgnization = orgnizationMapper.findOrgByPrimaryKey(task.getPurchaseId());
            			task.setPurchaseId(orgnization.getShortName());
            		}
                    if(StringUtils.isNotBlank(task.getCreaterId())){
                    	List<User> users = userMapper.selectByPrimaryKey(task.getCreaterId());
                        if(users != null && users.size() > 0){
                        	task.setCreaterId(users.get(0).getRelName());
                        }
                    }
                    
                    if(StringUtils.isNotBlank(task.getUserId())){
                    	List<User> users = userMapper.selectByPrimaryKey(task.getUserId());
                        if(users != null && users.size() > 0){
                        	task.setUserId(users.get(0).getRelName());
                        }
                    }
                    return task;
                }
            }
            
		}
		return null;
	}

	@Override
	public AdvancedProject viewAdvancedProject(String projectId) {
		if(StringUtils.isNotBlank(projectId)){
			AdvancedProject advancedProject = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(projectId);
			if(advancedProject != null && !"0".equals(advancedProject.getStatus())){
				if(StringUtils.isNotBlank(advancedProject.getPurchaseDepId())){
                    Orgnization orgnization = orgnizationMapper.findOrgByPrimaryKey(advancedProject.getPurchaseDepId());
                    advancedProject.setPurchaseDepName(orgnization.getShortName());
                }
				if(StringUtils.isNotBlank(advancedProject.getAppointMan())){
					List<User> users = userMapper.selectByPrimaryKey(advancedProject.getAppointMan());
                    if(users != null && users.size() > 0){
                    	advancedProject.setAppointMan(users.get(0).getRelName());
                    }
                }
				DictionaryData findById = DictionaryDataUtil.findById(advancedProject.getStatus());
				if(findById != null){
					if(!"YYYBYY".equals(findById.getCode())){
                        advancedProject.setIsRehearse(0);
                    }else{
                        advancedProject.setIsRehearse(1);
                    }
				}
				return advancedProject;
			}
		}
		return null;
	}

	@Override
	public HashMap<String, Object> flow(List<Project> list, String detailId, HashMap<String, Object> map) {
		int num = 0;
		for (Project project : list) {
			//先判断relationId是否为空，为空的话说明项目是中止（转竟谈）之前的项目或者正常项目
			if(StringUtils.isBlank(project.getRelationId())){
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("id", project.getId());
				hashMap.put("requiredId", detailId);
				List<ProjectDetail> selectById = projectDetailMapper.selectById(hashMap);
				if(selectById != null && selectById.size() > 0){
					//判断项目下面这条明细有没有分包，没有的话就只有立项这个环节
					if(StringUtils.isNotBlank(selectById.get(0).getPackageId())){
						Packages packages = packageMapper.selectByPrimaryKeyId(selectById.get(0).getPackageId());
						FlowDefine flowDefine = new FlowDefine();
						flowDefine.setId(packages.getOldFlowId());
						flowDefine.setUrl("gt");
	    				List<FlowDefine> flows = flowDefineMapper.getFlow(flowDefine);//获取小于等于中止的环节
	    				if(flows != null && flows.size() > 0){
	    					for (int i=0; i<flows.size();i++) {
	    						DictionaryData data = new DictionaryData();
	    						data.setDescription(flows.get(i).getCode());
	    						List<DictionaryData> find2 = dictionaryDataMapper.findList(data);
	    						if(find2 != null && find2.size() > 0){
	    							if(num != 0){
	    								find2.get(0).setPosition(num);
	    								num++;
	    							}
	    							flowChart(find2.get(0).getCode(),find2.get(0),project,detailId,packages);
	    							if(i == flows.size()-1){
	    								//给最后一个流程加一个标示
	    								map.put(WfUtil.createUUID() + "XMFB", find2.get(0));
	    								num = find2.get(0).getPosition()+1;
	    							}else{
	    								map.put(WfUtil.createUUID(), find2.get(0));
	    							}
	    						}
	    					}
	    				} else if (list.size() == 1){
	    					//正常流程进这里
	    					FlowDefine define = new FlowDefine();
	    					define.setPurchaseTypeId(packages.getPurchaseType());
	    					define.setCode("XMXX");
	    					List<FlowDefine> findList = flowDefineMapper.findList(define);
	    					if(findList != null && findList.size() > 0){
	    						flowDefine.setId(findList.get(0).getId());
	    						flowDefine.setUrl("lt");
	    						List<FlowDefine> defines = flowDefineMapper.getFlow(flowDefine);
	    						for (int i=0; i<defines.size();i++) {
	    							DictionaryData data = new DictionaryData();
	    							data.setDescription(defines.get(i).getCode());
	    							List<DictionaryData> find2 = dictionaryDataMapper.findList(data);
	    							if(find2 != null && find2.size() > 0){
	    								flowChart(find2.get(0).getCode(),find2.get(0),project,detailId,packages);
	    								map.put(find2.get(0).getId(), find2.get(0));
	    							}
	    						}
	    					}
	    				} else if (!"CGLC_CGXMLX".equals(packages.getOldFlowId()) && !"CGLC_CGXMFB".equals(packages.getOldFlowId())) {
	    					FlowDefine define = new FlowDefine();
	    					define.setPurchaseTypeId(packages.getPurchaseType());
	    					define.setCode("XMXX");
	    					List<FlowDefine> findList = flowDefineMapper.findList(define);
	    					if(findList != null && findList.size() > 0){
	    						flowDefine.setId(findList.get(0).getId());
	    						flowDefine.setUrl("lt");
	    						List<FlowDefine> defines = flowDefineMapper.getFlow(flowDefine);
	    						for (int i=0; i<defines.size();i++) {
	    							DictionaryData data = new DictionaryData();
	    							data.setDescription(defines.get(i).getCode());
	    							List<DictionaryData> find2 = dictionaryDataMapper.findList(data);
	    							if(find2 != null && find2.size() > 0){
	    								if(num != 0){
	    									find2.get(0).setPosition(num);
	    									num++;
	    								} else {
	    									num = find2.get(0).getPosition()+1;
	    								}
	    								flowChart(find2.get(0).getCode(),find2.get(0),project,detailId,packages);
	    								map.put(WfUtil.createUUID(), find2.get(0));
	    							}
	    						}
	    					}
	    				}/* else if ("CGLC_CGXMLX".equals(packages.getOldFlowId()) || "CGLC_CGXMFB".equals(packages.getOldFlowId())) {
	    					HashMap<String, Object> flowMap = new HashMap<>();
	    					flowMap.put("lt", packages.getOldFlowId());
	    					List<DictionaryData> viewFlows = dictionaryDataMapper.viewFlows(flowMap);
	    					if(viewFlows != null && viewFlows.size() > 0){
	    						for (int i = 0; i < viewFlows.size(); i++) {
	    							viewFlows.get(i).setPosition(num);
	    							flowChart(viewFlows.get(i).getCode(),viewFlows.get(i),project,detailId,packages);
	    							if(i == viewFlows.size()-1){
    									map.put(WfUtil.createUUID() + "XMFB", viewFlows.get(i));
    									num++;
    								} else {
    									map.put(WfUtil.createUUID(), viewFlows.get(i));
    									num++;
    								}
								}
	    					}
	    				}*/
					} else {
						DictionaryData dictionaryData = DictionaryDataUtil.get("CGLC_CGXMLX");
						dictionaryData.setPosition(num);
						flowChart(dictionaryData.getCode(),dictionaryData,project,detailId,null);
						map.put(WfUtil.createUUID(), dictionaryData);
					}
				}
			} else {
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("id", project.getId());
				hashMap.put("requiredId", detailId);
				List<ProjectDetail> selectById = projectDetailMapper.selectById(hashMap);
				if(selectById != null && selectById.size() > 0){
					Packages packages = packageMapper.selectByPrimaryKeyId(selectById.get(0).getPackageId());
					HashMap<String, Object> maps = new HashMap<>();
					if(StringUtils.isNotBlank(packages.getNewFlowId())){
						maps.put("newFlowId", packages.getNewFlowId());
						maps.put("id", packages.getNewFlowId());
					}
					if(StringUtils.isNotBlank(packages.getOldFlowId())){
						maps.put("oldFlowId", packages.getOldFlowId());
					}
    				List<FlowDefine> flows = flowDefineMapper.viewFlow(maps);
    				if(flows != null && flows.size() > 0){
    					for (int i=0; i<flows.size();i++) {
    						DictionaryData data = new DictionaryData();
    						data.setDescription(flows.get(i).getCode());
    						List<DictionaryData> find2 = dictionaryDataMapper.findList(data);
    						if(find2 != null && find2.size() > 0){
    							find2.get(0).setPosition(num);
    							flowChart(find2.get(0).getCode(),find2.get(0),project,detailId,packages);
    							if(StringUtils.isNotBlank(packages.getOldFlowId())){
    								if(i==flows.size()-1){
    									map.put(WfUtil.createUUID() + "XMFB", find2.get(0));
    									num++;
    								}else{
    									map.put(WfUtil.createUUID(), find2.get(0));
    									num++;
    								}
    							} else {
    								map.put(WfUtil.createUUID(), find2.get(0));
    								num++;
    							}
    							
    						}
    					}
    				} else {
    					HashMap<String, Object> flowMap = new HashMap<>();
    					if(StringUtils.isNotBlank(packages.getNewFlowId())){
    						flowMap.put("gt", packages.getNewFlowId());
    						List<DictionaryData> viewFlows = dictionaryDataMapper.viewFlows(flowMap);
        					if(viewFlows != null && viewFlows.size() > 0){
        						for (int i = 0; i < viewFlows.size(); i++) {
        							viewFlows.get(i).setPosition(num);
        							flowChart(viewFlows.get(i).getCode(),viewFlows.get(i),project,detailId,packages);
    								map.put(WfUtil.createUUID(), viewFlows.get(i));
    								num++;
    							}
        					}
    					}
    				}
				}
			
			}
				
		}
		return map;
	}
	
	
	public void flowChart(String code,DictionaryData dictionaryData, Project project, String detailId, Packages packages){
    	if("CGLC_CGXMLX".equals(code)){
    		dictionaryData.setUpdatedAt(project.getCreateAt());
    		dictionaryData.setDescription(project.getId());
		} else if ("CGLC_CGXMFB".equals(code)){
			dictionaryData.setUpdatedAt(packages.getCreatedAt());
			dictionaryData.setDescription(project.getId());
		} else if ("CGLC_CGWJBB".equals(code)){
			dictionaryData.setUpdatedAt(project.getReplyTime());
			dictionaryData.setDescription(project.getId());
		} else if ("CGLC_CGGGFB".equals(code)){
			Article article = new Article();
            article.setArticleType(articleTypeMapper.selectArticleTypeByCode("purchase_notice"));
            article.setProjectId(project.getId());
            List<Article> articles = articleMapper.selectArticleByProjectId(article);
            if(articles != null && articles.size() > 0){
            	dictionaryData.setUpdatedAt(articles.get(0).getCreatedAt());
            	dictionaryData.setDescription(project.getId());
            }
		} else if ("CGLC_GYSCQ".equals(code)){
			//获取抽取供应商监督人员
            List<SupplierExtUser> listUsers = extUserMapper.list(new SupplierExtUser(project.getId()));
            if(listUsers != null && listUsers.size() > 0){
            	dictionaryData.setUpdatedAt(listUsers.get(0).getCreatedAt());
            	dictionaryData.setDescription(project.getId());
            }
		} else if ("CGLC_CGWJFS".equals(code)){
			TreeSet<Long> releaseTime = releaseTime(detailId, project.getId());
            if(releaseTime != null && releaseTime.size() > 0){
            	Iterator<Long> it = releaseTime.iterator();
                if(releaseTime.size()==1){
                	String format = new SimpleDateFormat("yyyy-MM-dd").format(it.next());
                	dictionaryData.setUpdatedAt(DateUtils.stringToDate(format,"yyyy-MM-dd"));
                	dictionaryData.setDescription(project.getId());
                }else{
                    int sun=0;
                    while (it.hasNext()) {
                        if(sun==0){
                        	String format = new SimpleDateFormat("yyyy-MM-dd").format(it.next());
                        	dictionaryData.setUpdatedAt(DateUtils.stringToDate(format,"yyyy-MM-dd"));
                        	dictionaryData.setDescription(project.getId());
                        }
                        if(sun==(releaseTime.size()-1)){
                        }
                        sun++;
                    }
                }
            }
		} else if ("CGLC_PSZJCQ".equals(code)){
			List<ProExtSupervise> listUser = extSuperviseMapper.list(new ProExtSupervise(project.getId()));
            if(listUser != null && listUser.size() > 0){
            	dictionaryData.setUpdatedAt(listUser.get(0).getCreatedAt());
            	dictionaryData.setDescription(project.getId());
            }
		} else if ("CGLC_GYSQD".equals(code)){
			SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packages.getId());
			List<SaleTender> packegeSupplier = saleTenderMapper.getPackegeSupplier(saleTender);
			if (packegeSupplier != null && !packegeSupplier.isEmpty()) {
				dictionaryData.setUpdatedAt(project.getBidDate());
				dictionaryData.setDescription(project.getId());
			}
		} else if ("CGLC_KB".equals(code)){
			SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packages.getId());
			List<SaleTender> packegeSupplier = saleTenderMapper.getPackegeSupplier(saleTender);
			if (packegeSupplier != null && !packegeSupplier.isEmpty()) {
				dictionaryData.setUpdatedAt(project.getBidDate());
				dictionaryData.setDescription(project.getId());
			}
		} else if ("CGLC_CGXMPS".equals(code)){
            if(packages.getQualificationTime() != null){
            	dictionaryData.setUpdatedAt(packages.getQualificationTime());
            	dictionaryData.setDescription(project.getId());
            } else {
            	dictionaryData.setUpdatedAt(packages.getTechniqueTime());
            	dictionaryData.setDescription(project.getId());
            }
		} else if ("CGLC_ZBGSFB".equals(code)){
			Article article2 = new Article();
            article2.setArticleType(articleTypeMapper.selectArticleTypeByCode("success_notice"));
            article2.setProjectId(project.getId());
            List<Article> articleList= articleMapper.selectArticleByProjectId(article2);
            if(articleList != null && articleList.size() > 0){
                dictionaryData.setUpdatedAt(articleList.get(0).getCreatedAt());
                dictionaryData.setDescription(project.getId());
            }
		} else if ("CGLC_YZBGYSQD".equals(code)){
			SupplierCheckPass pass = new SupplierCheckPass();
            pass.setPackageId(packages.getId());
            pass.setIsWonBid((short)1);
            pass.setProjectId(project.getId());
            List<SupplierCheckPass> listCheckPass = checkPassMapper.listCheckPass(pass);
            if(listCheckPass != null && listCheckPass.size() > 0){
            	dictionaryData.setDescription(project.getId());
                dictionaryData.setUpdatedAt(listCheckPass.get(0).getConfirmTime());
            }
		}
    	if(packages != null && StringUtils.isNotBlank(packages.getEditFlowId())){
    		FlowDefine flowDefine = flowDefineMapper.get(packages.getEditFlowId());
    		DictionaryData data = new DictionaryData();
    		data.setDescription(flowDefine.getCode());
    		List<DictionaryData> findList = dictionaryDataMapper.findList(data);
    		if(findList != null && findList.size() > 0){
    			if(dictionaryData.getId().equals(findList.get(0).getId())){
    				String name = dictionaryData.getName();
    				name += "(转竟谈)";
    				dictionaryData.setName(name);
    			}
    		}
    	}
    }

	@Override
	public List<Supervision> viewSupervision(List<Entry<String, Object>> sortsMap, List<Project> projects, String detailId) {
		List<Supervision> list = new ArrayList<Supervision>();
		for (Project project : projects) {
			Packages packages = null;
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("id", project.getId());
			hashMap.put("requiredId", detailId);
			List<ProjectDetail> selectById = projectDetailMapper.selectById(hashMap);
			if(selectById != null && selectById.size() > 0){
				if(StringUtils.isNotBlank(selectById.get(0).getPackageId())){
					packages = packageMapper.selectByPrimaryKeyId(selectById.get(0).getPackageId());
					DictionaryData data = DictionaryDataUtil.findById(packages.getProjectStatus());
					packages.setProjectStatus(data.getName());
				}
			}
			for (Entry<String, Object> entry : sortsMap) {
				DictionaryData data = (DictionaryData) entry.getValue();
				if (data != null && project.getId().equals(data.getDescription()) && "CGLC_CGXMLX".equals(data.getCode()) && data.getUpdatedAt() != null) {
					Supervision supervision = new Supervision();
					HashMap<String, Object> map = new HashMap<>();
					map.put("1采购项目名称", "25%");
					map.put("2立项审批文件", "10%");
					map.put("3立项部门", "15%");
					map.put("4项目性质", "10%");
					map.put("5立项人", "20%");
					map.put("6立项时间", "20%");
					List<Map.Entry<String, Object>> lists = sorts(map);
					supervision.setName(data.getName());
					supervision.setProject(project);
					supervision.setMap(lists);
					String approval = DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS");
					List<UploadFile> files = uploadService.getFilesOther(project.getId(), approval, "2");
					if(files != null && files.size() > 0){
						supervision.setUploadFile(files.get(0));
					}
					list.add(supervision);
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_CGXMFB".equals(data.getCode()) && data.getUpdatedAt() != null) {
					if(packages != null){
						Supervision supervision = new Supervision();
						HashMap<String, Object> map = new HashMap<>();
						map.put("1包名", "25%");
						map.put("2包号 ", "20%");
						map.put("3状态", "15%");
						map.put("4创建人", "20%");
						map.put("5创建时间", "20%");
						List<Map.Entry<String, Object>> lists = sorts(map);
						supervision.setMap(lists);
						supervision.setName(data.getName());
						supervision.setPackages(packages);
						supervision.setProject(project);
						list.add(supervision);
					}
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_CGWJBB".equals(data.getCode()) && data.getUpdatedAt() != null) {
					String bidId = DictionaryDataUtil.getId("PROJECT_BID");
		            List<UploadFile> files = uploadService.getFilesOther(project.getId(), bidId, Constant.TENDER_SYS_KEY+"");
		            if(files != null && files.size() > 0){
		            	Supervision supervision = new Supervision();
		            	HashMap<String, Object> map = new HashMap<>();
						map.put("1采购文件名称", "25%");
						map.put("2编制人", "10%");
						map.put("3提报时间", "25%");
						map.put("4审核意见", "20%");
						map.put("5意见批复时间", "20%");
						List<Map.Entry<String, Object>> lists = sorts(map);
						supervision.setMap(lists);
		            	supervision.setName(data.getName());
		            	supervision.setUploadFile(files.get(0));
		            	supervision.setProject(project);
		            	FlowDefine define = new FlowDefine();
		                define.setPurchaseTypeId(project.getPurchaseType());
		                define.setCode("NZCGWJ");
		            	FlowExecute flowExecute = operator(define, project.getId());
		            	if(flowExecute != null){
		            		supervision.setFlowExecute(flowExecute);
		            	}
		            	list.add(supervision);
		            }
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_CGGGFB".equals(data.getCode()) && data.getUpdatedAt() != null) {
					Article article = new Article();
		            article.setArticleType(articleTypeMapper.selectArticleTypeByCode("purchase_notice"));
		            article.setProjectId(project.getId());
		            List<Article> articles = articleMapper.selectArticleByProjectId(article);
		            if(articles != null && articles.size() > 0){
		                articles.get(0).setUserId(project.getAppointMan());
		                Supervision supervision = new Supervision();
		                HashMap<String, Object> map = new HashMap<>();
						map.put("1公告名称", "");
						map.put("2编制人", "20%");
						map.put("3编制时间", "20%");
						List<Map.Entry<String, Object>> lists = sorts(map);
						supervision.setMap(lists);
		                supervision.setName(data.getName());
		                supervision.setArticle(articles.get(0));
		                list.add(supervision);
		            }
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_GYSCQ".equals(data.getCode()) && data.getUpdatedAt() != null) {
					List<SupplierExtUser> listUsers = extUserMapper.list(new SupplierExtUser(project.getId()));
					if(listUsers != null && listUsers.size() > 0){
						Supervision supervision = new Supervision();
						HashMap<String, Object> map = new HashMap<>();
						map.put("1抽取记录", "25%");
						map.put("2抽取人", "");
						map.put("3监督人", "20%");
						map.put("4抽取时间", "20%");
						List<Map.Entry<String, Object>> lists = sorts(map);
						supervision.setMap(lists);
						supervision.setName(data.getName());
						//获取抽取供应商监督人员
	            		String userNames = null;
	            		for (SupplierExtUser supplierExtUser : listUsers) {
	            			if (supplierExtUser != null ){
	                            userNames += supplierExtUser.getRelName()+ ",";
	                        }
						}
	            		if(StringUtils.isNotBlank(userNames)){
	            			listUsers.get(0).setRelName(userNames.substring(0, userNames.length()-1));
	            			supervision.setSupplierExtUser(listUsers.get(0));
	            		}
	            		//获取供应商抽取人
	            		FlowDefine fd = new FlowDefine();
	                    fd.setPurchaseTypeId(project.getPurchaseType());
	                    fd.setCode("CQGYS");
	                    FlowExecute execute = operator(fd, project.getId());
	                    if(execute != null){
	                    	supervision.setFlowExecute(execute);
	                    }
	                    supervision.setPackages(packages);
	                    supervision.setProject(project);
	                    list.add(supervision);
	            	}
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_CGWJFS".equals(data.getCode()) && data.getUpdatedAt() != null) {
					//获取文件发售
					Supervision supervision = new Supervision();
					HashMap<String, Object> map = new HashMap<>();
					map.put("1文件发售记录", "");
					map.put("2操作人", "20%");
					map.put("3开始发售时间", "20%");
					List<Map.Entry<String, Object>> lists = sorts(map);
					supervision.setMap(lists);
					supervision.setName(data.getName());
					TreeSet<Long> releaseTime = releaseTime(detailId, project.getId());
	                if(releaseTime != null && releaseTime.size() > 0){
	                	Iterator<Long> it = releaseTime.iterator();
	                    if(releaseTime.size()==1){
	                    	supervision.setBegin(new SimpleDateFormat("yyyy-MM-dd").format(it.next()));
	                    }else{
	                        int sun=0;
	                        while (it.hasNext()) {
	                            if(sun==0){
	                            	supervision.setBegin(new SimpleDateFormat("yyyy-MM-dd").format(it.next()));
	                            }
	                            if(sun==(releaseTime.size()-1)){
	                                supervision.setEnd(new SimpleDateFormat("yyyy-MM-dd").format(it.next()));
	                            }
	                            sun++;
	                        }
	                    }
	                    supervision.setPackages(packages);
	                    FlowDefine defines = new FlowDefine();
	                    defines.setPurchaseTypeId(project.getPurchaseType());
	                    defines.setCode("FSBS");
	                	FlowExecute execute = operator(defines, project.getId());
	                	if(execute != null){
	                		supervision.setFlowExecute(execute);
	                	}
	                	list.add(supervision);
	                }
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_PSZJCQ".equals(data.getCode()) && data.getUpdatedAt() != null) {
					Supervision supervision = new Supervision();
					HashMap<String, Object> map = new HashMap<>();
					map.put("1抽取记录", "25%");
					map.put("2抽取人", "");
					map.put("3监督人", "20%");
					map.put("4抽取时间", "20%");
					List<Map.Entry<String, Object>> lists = sorts(map);
					supervision.setMap(lists);
					supervision.setName(data.getName());
					supervision.setPackages(packages);
					supervision.setProject(project);
					FlowDefine fd = new FlowDefine();
	                fd.setPurchaseTypeId(project.getPurchaseType());
	                fd.setCode("ZZZJPS");
	                FlowExecute execute = operator(fd, project.getId());
	                if(execute != null){
	                	supervision.setFlowExecute(execute);
	                }
	                //获取抽取专家监督人
	                List<ProExtSupervise> listUser = extSuperviseMapper.list(new ProExtSupervise(project.getId()));
	                if(listUser != null && listUser.size() > 0){
	                	String userName = null;
	                	for (ProExtSupervise proExtSupervise : listUser) {
	                		if (proExtSupervise != null ){
	                            userName += proExtSupervise.getRelName()+ ",";
	                          }
						}
	                	if(StringUtils.isNotBlank(userName)){
	                		listUser.get(0).setRelName(userName.substring(0, userName.length()-1));
	                		supervision.setProExtSupervise(listUser.get(0));
	                	}
	                }
	                list.add(supervision);
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_KB".equals(data.getCode()) && data.getUpdatedAt() != null) {
					SaleTender saleTender = new SaleTender();
		            saleTender.setPackages(packages.getId());
					List<SaleTender> packegeSupplier = saleTenderMapper.getPackegeSupplier(saleTender);
					if (packegeSupplier != null && !packegeSupplier.isEmpty()) {
						Supervision supervision = new Supervision();
						HashMap<String, Object> map = new HashMap<>();
						map.put("1投标记录", "25%");
						map.put("2开标一览表", "35%");
						map.put("3开标人", "20%");
						map.put("4开标时间", "20%");
						List<Map.Entry<String, Object>> lists = sorts(map);
						supervision.setMap(lists);
						supervision.setName(data.getName());
						supervision.setPackages(packages);
						supervision.setProject(project);
						//获取开标的操作人
			            FlowDefine define = new FlowDefine();
			            define.setPurchaseTypeId(project.getPurchaseType());
			            define.setCode("KBCB");
			            FlowExecute flowExecute = operator(define, project.getId());
			            if(flowExecute != null){
			            	supervision.setFlowExecute(flowExecute);
			            }
			            list.add(supervision);
					}
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_CGXMPS".equals(data.getCode()) && data.getUpdatedAt() != null) {
					Supervision supervision = new Supervision();
					HashMap<String, Object> map = new HashMap<>();
					map.put("1文件名称", "25%");
					map.put("2查看评审专家打分表", "35%");
					map.put("3查看汇总表", "20%");
					map.put("4评审时间", "20%");
					List<Map.Entry<String, Object>> lists = sorts(map);
					supervision.setMap(lists);
					supervision.setName(data.getName());
					supervision.setPackages(packages);
					supervision.setProject(project);
					//项目评审获取专家
	                List<PackageExpert> packageExperts = viewPackageExpert(detailId, project.getId());
	                List<Expert> experts = new ArrayList<Expert>();
	                if(packageExperts != null && packageExperts.size() > 0){
	                	for (PackageExpert packageExpert : packageExperts) {
	                		Expert expert = expertMapper.selectByPrimaryKey(packageExpert.getExpertId());
	                        packageExpert.setExpertId(expert.getRelName());
	                        experts.add(expert);
						}
	                	supervision.setPackageExperts(packageExperts);
	                	
	                	if(experts != null && experts.size() > 0){
	                		supervision.setExpert(experts);
	                	}
	                }
	                //专家评审报告
	                if("DYLY".equals(DictionaryDataUtil.findById(project.getPurchaseType()).getCode())){
	                	HashMap<String, Object> maps = new HashMap<>();
	        	        maps.put("requiredId", detailId);
	        	        List<ProjectDetail> selectByIds = projectDetailMapper.selectById(maps);
	        	        if(selectByIds != null && selectByIds.size() > 0){
	        	        	NegotiationReport report =  reportMapper.selectByPackageId(selectByIds.get(0).getPackageId());
	                        if(report != null){
	                        	supervision.setNegotiationReport(report);
	                        }
	        	        }
	                }
	                list.add(supervision);
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_ZBGSFB".equals(data.getCode()) && data.getUpdatedAt() != null) {
					Supervision supervision = new Supervision();
					//获取中标公示
		            Article article = new Article();
		            article.setArticleType(articleTypeMapper.selectArticleTypeByCode("success_notice"));
		            article.setProjectId(project.getId());
		            List<Article> articleList= articleMapper.selectArticleByProjectId(article);
		            if(articleList != null && articleList.size() > 0){
		                List<User> user = userMapper.selectByPrimaryKey(articleList.get(0).getUser().getId());
		                articleList.get(0).setUserId(user.get(0).getRelName());
		                HashMap<String, Object> map = new HashMap<>();
						map.put("1中标公示名称", "");
						map.put("2编制人", "20%");
						map.put("3编制时间", "20%");
						List<Map.Entry<String, Object>> lists = sorts(map);
						supervision.setMap(lists);
		                supervision.setArticle(articleList.get(0));
		                supervision.setName(data.getName());
		            }
		            list.add(supervision);
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_YZBGYSQD".equals(data.getCode()) && data.getUpdatedAt() != null) {
					Supervision supervision = new Supervision();
					//确认中标供应商
	                SupplierCheckPass pass = new SupplierCheckPass();
	                pass.setPackageId(selectById.get(0).getPackageId());
	                pass.setIsWonBid((short)1);
	                pass.setProjectId(project.getId());
	                List<SupplierCheckPass> listCheckPass = checkPassMapper.listCheckPass(pass);
	                if(listCheckPass != null && listCheckPass.size() > 0){
	                    for (SupplierCheckPass supplierCheckPass : listCheckPass) {
	                        Supplier supplier = supplierMapper.selectByPrimaryKey(supplierCheckPass.getSupplierId());
	                        if(supplier != null){
	                            supplierCheckPass.setSupplierId(supplier.getSupplierName());
	                        }
	                    }
	                    HashMap<String, Object> map = new HashMap<>();
						map.put("1中标供应商名称", "");
						map.put("2评分排序", "25%");
						map.put("3操作人", "20%");
						map.put("4确定时间", "20%");
						List<Map.Entry<String, Object>> lists = sorts(map);
						supervision.setMap(lists);
	                    supervision.setSupplierCheckPass(listCheckPass);
	                    supervision.setName(data.getName());
	                }
	                //获取中标供应商操作人
	                FlowDefine defines = new FlowDefine();
	                defines.setPurchaseTypeId(project.getPurchaseType());
	                defines.setCode("QRZBGYS");
	                FlowExecute execute = operator(defines, project.getId());
	                if(execute != null){
	                	supervision.setFlowExecute(execute);
	                }
	                supervision.setPackages(packages);
	                list.add(supervision);
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_CGHTQD".equals(data.getCode()) && data.getUpdatedAt() != null) {
					Supervision supervision = new Supervision();
					//合同信息
	                PurchaseContract purchaseContract = viewPurchaseContract(selectById.get(0).getId());
	                if(purchaseContract != null){
	                	HashMap<String, Object> map = new HashMap<>();
						map.put("1合同名称", "");
						map.put("2甲方", "");
						map.put("3乙方", "");
						map.put("4签订时间", "20%");
						List<Map.Entry<String, Object>> lists = sorts(map);
						supervision.setMap(lists);
	                	supervision.setName(data.getName());
	                	supervision.setPurchaseContract(purchaseContract);
	                	list.add(supervision);
	                }
				} else if (data != null && project.getId().equals(data.getDescription()) && "CGLC_CGZJYS".equals(data.getCode()) && data.getUpdatedAt() != null) {
					Supervision supervision = new Supervision();
					PurchaseContract purchaseContract = viewPurchaseContract(selectById.get(0).getId());
	                if(purchaseContract != null){
	                	//质检信息
	                    HashMap<String, Object> hashMaps = new HashMap<>();
	                    hashMaps.put("contract", purchaseContract);
	                    List<PqInfo> selectByCondition = pqInfoMapper.selectByContract(hashMaps);
	                    if(selectByCondition != null && selectByCondition.size() > 0){
	                    	HashMap<String, Object> map = new HashMap<>();
							map.put("1验收记录", "");
							map.put("2质检专家", "");
							map.put("3质检单位", "");
							map.put("4验收时间", "20%");
							List<Map.Entry<String, Object>> lists = sorts(map);
							supervision.setMap(lists);
	                    	supervision.setName(data.getName());
	                    	supervision.setPqInfo(selectByCondition.get(0));
	                    	list.add(supervision);
	                    }
	                }
				}
			}
		}
		return list;
	}

	private List<Map.Entry<String, Object>> sorts(HashMap<String, Object> map) {
		List<Map.Entry<String, Object>> lists = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
		Collections.sort(lists,new Comparator<Map.Entry<String, Object>>() {
			@Override
			public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
				String value1 = (String) o1.getKey().substring(0, 1);
				String value2 = (String) o2.getKey().substring(0, 1);
				return value1.compareTo(value2);
			}
		});
		return lists;
	}
	
	public Project viewProjects(String projectId){
		Project project = projectMapper.selectProjectByPrimaryKey(projectId);
    	if(project != null && !"4".equals(project.getStatus())){
    		if(StringUtils.isNotBlank(project.getAppointMan())){
    			List<User> user = userMapper.selectByPrimaryKey(project.getAppointMan());
                project.setAppointMan(user.get(0).getRelName());
                project.setIpone(user.get(0).getMobile());
            }
            if(StringUtils.isNotBlank(project.getPrincipal())){
            	List<User> user = userMapper.selectByPrimaryKey(project.getPrincipal());
                project.setPrincipal(user.get(0).getRelName());
            }
            if(StringUtils.isNotBlank(project.getPurchaseDepId())){
            	Orgnization org = orgnizationMapper.findOrgByPrimaryKey(project.getPurchaseDepId());
                project.setPurchaseDepName(org.getShortName());
            }
            if(StringUtils.isNotBlank(project.getStatus())){
            	project.setStatus(DictionaryDataUtil.findById(project.getStatus()).getName());
            }
    	}
		return project;
	} 
	
	public void opop(int num, HashMap<String, Object> map, List<FlowDefine> defines, Project project, String detailId, Packages packages){
		for (int i=0; i<defines.size();i++) {
			DictionaryData data = new DictionaryData();
			data.setDescription(defines.get(i).getCode());
			List<DictionaryData> find2 = dictionaryDataMapper.findList(data);
			if(find2 != null && find2.size() > 0){
				flowChart(find2.get(0).getCode(),find2.get(0),project,detailId,packages);
				if(i == defines.size()-1){
					map.put(find2.get(0).getId() + "XMFB", find2.get(0));
					num = find2.get(0).getPosition()+1;
				}else{
					map.put(find2.get(0).getId(), find2.get(0));
				}
			}
		}
	
	}

}
