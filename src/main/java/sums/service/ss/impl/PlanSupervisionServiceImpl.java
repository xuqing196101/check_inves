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

import common.utils.DateUtils;

import bss.dao.cs.ContractRequiredMapper;
import bss.dao.cs.PurchaseContractMapper;
import bss.dao.pms.AuditPersonMapper;
import bss.dao.pms.CollectPlanMapper;
import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.ppms.AdvancedProjectMapper;
import bss.dao.ppms.FlowDefineMapper;
import bss.dao.ppms.FlowExecuteMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.ProjectTaskMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.ppms.TaskMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.AuditPerson;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.model.prms.PackageExpert;
import bss.service.pms.PurchaseRequiredService;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ProExtSuperviseMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.SupplierExtUserMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ProExtSupervise;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtUser;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
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
        if(listBycollect != null && listBycollect.size() > 0){
            for (Task task : listBycollect) {
                map.put("taskId", task.getId());
                List<ProjectTask> projectTasks = projectTaskMapper.queryByNo(map);
                for (ProjectTask projectTask : projectTasks) {
                    Project project = projectMapper.selectProjectByPrimaryKey(projectTask.getProjectId());
                    if(project != null && !"4".equals(project.getStatus())){
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
            		Project project = projectMapper.selectProjectByPrimaryKey(projectDetail.getProject().getId());
                	if(project != null){
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
			if(StringUtils.isBlank(project.getRelationId())){
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("id", project.getId());
				hashMap.put("requiredId", detailId);
				List<ProjectDetail> selectById = projectDetailMapper.selectById(hashMap);
				if(selectById != null && selectById.size() > 0){
					Packages packages = packageMapper.selectByPrimaryKeyId(selectById.get(0).getPackageId());
					FlowDefine flowDefine = new FlowDefine();
					flowDefine.setId(packages.getOldFlowId());
					flowDefine.setUrl("gt");
    				List<FlowDefine> flows = flowDefineMapper.getFlow(flowDefine);
    				for (int i=0; i<flows.size();i++) {
						DictionaryData data = new DictionaryData();
						data.setDescription(flows.get(i).getCode());
						List<DictionaryData> find2 = dictionaryDataMapper.findList(data);
						if(find2 != null && find2.size() > 0){
							flowChart(find2.get(0).getCode(),find2.get(0),project,detailId,packages);
							if(i==flows.size()-1){
								map.put(find2.get(0).getId() + "XMFB", find2.get(0));
								num = find2.get(0).getPosition()+1;
							}else{
								map.put(find2.get(0).getId(), find2.get(0));
							}
						}
					}
				}
			} else {
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("id", project.getId());
				hashMap.put("requiredId", detailId);
				List<ProjectDetail> selectById = projectDetailMapper.selectById(hashMap);
				if(selectById != null && selectById.size() > 0){
					Packages packages = packageMapper.selectByPrimaryKeyId(selectById.get(0).getPackageId());
	    			FlowDefine define = new FlowDefine();
	    			//define.setCode(packages.getFlowId());
	    			define.setPurchaseTypeId(project.getPurchaseType());
	    			List<FlowDefine> find = flowDefineMapper.findList(define);
	    			if(find != null && find.size() > 0){
	    				find.get(0).setUrl("lt");
	    				List<FlowDefine> flows = flowDefineMapper.getFlow(find.get(0));
	    				for (int i=0; i<flows.size();i++) {
    						DictionaryData data = new DictionaryData();
    						data.setDescription(flows.get(i).getCode());
    						List<DictionaryData> find2 = dictionaryDataMapper.findList(data);
    						if(find2 != null && find2.size() > 0){
    							find2.get(0).setPosition(num);
    							flowChart(find2.get(0).getCode(),find2.get(0),project,detailId,packages);
    							if(i==flows.size()-1){
    								map.put(WfUtil.createUUID() + "XMFB", find2.get(0));
    								num++;
    							}else{
    								map.put(WfUtil.createUUID(), find2.get(0));
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
		} else if ("CGLC_CGWJBB".equals(code)){
			dictionaryData.setUpdatedAt(project.getApprovalTime());
		} else if ("CGLC_CGGGFB".equals(code)){
			Article article = new Article();
            article.setArticleType(articleTypeMapper.selectArticleTypeByCode("purchase_notice"));
            article.setProjectId(project.getId());
            List<Article> articles = articleMapper.selectArticleByProjectId(article);
            if(articles != null && articles.size() > 0){
            	dictionaryData.setUpdatedAt(articles.get(0).getCreatedAt());
            }
		} else if ("CGLC_GYSCQ".equals(code)){
			//获取抽取供应商监督人员
            List<SupplierExtUser> listUsers = extUserMapper.list(new SupplierExtUser(project.getId()));
            if(listUsers != null && listUsers.size() > 0){
            	dictionaryData.setUpdatedAt(listUsers.get(0).getCreatedAt());
            }
		} else if ("CGLC_CGWJFS".equals(code)){
			TreeSet<Long> releaseTime = releaseTime(detailId, project.getId());
            if(releaseTime != null && releaseTime.size() > 0){
            	Iterator<Long> it = releaseTime.iterator();
                if(releaseTime.size()==1){
                	String format = new SimpleDateFormat("yyyy-MM-dd").format(it.next());
                	dictionaryData.setUpdatedAt(DateUtils.stringToDate(format,"yyyy-MM-dd"));
                }else{
                    int sun=0;
                    while (it.hasNext()) {
                        if(sun==0){
                        	String format = new SimpleDateFormat("yyyy-MM-dd").format(it.next());
                        	dictionaryData.setUpdatedAt(DateUtils.stringToDate(format,"yyyy-MM-dd"));
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
            }
		} else if ("CGLC_KB".equals(code)){
			dictionaryData.setUpdatedAt(project.getBidDate());
		} else if ("CGLC_CGXMPS".equals(code)){
            if(packages.getQualificationTime() != null){
            	dictionaryData.setUpdatedAt(packages.getQualificationTime());
            } else {
            	dictionaryData.setUpdatedAt(packages.getTechniqueTime());
            }
		} else if ("CGLC_ZBGSFB".equals(code)){
			Article article2 = new Article();
            article2.setArticleType(articleTypeMapper.selectArticleTypeByCode("success_notice"));
            article2.setProjectId(project.getId());
            List<Article> articleList= articleMapper.selectArticleByProjectId(article2);
            if(articleList != null && articleList.size() > 0){
                dictionaryData.setUpdatedAt(articleList.get(0).getCreatedAt());
            }
		} else if ("CGLC_YZBGYSQD".equals(code)){
			SupplierCheckPass pass = new SupplierCheckPass();
            pass.setPackageId(packages.getId());
            pass.setIsWonBid((short)1);
            pass.setProjectId(project.getId());
            List<SupplierCheckPass> listCheckPass = checkPassMapper.listCheckPass(pass);
            if(listCheckPass != null && listCheckPass.size() > 0){
                dictionaryData.setUpdatedAt(listCheckPass.get(0).getConfirmTime());
            }
		}
    }

}
