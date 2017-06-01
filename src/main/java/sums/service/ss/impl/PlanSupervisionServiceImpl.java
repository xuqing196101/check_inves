package sums.service.ss.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.cs.ContractRequiredMapper;
import bss.dao.cs.PurchaseContractMapper;
import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.ProjectTaskMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.ppms.TaskMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.service.pms.PurchaseRequiredService;
import ses.dao.bms.UserMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.util.DictionaryDataUtil;
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
                    Project project = projectMapper.selectProjectByPrimaryKey(string);
                    if(project != null){
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
                        String[] progressBarPlan = supervisionService.progressBar(projectDetail.getRequiredId());
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

}
