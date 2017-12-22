package sums.service.ss.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.cs.ContractRequiredMapper;
import bss.dao.cs.PurchaseContractMapper;
import bss.dao.pms.CollectPlanMapper;
import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.ProjectTaskMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.ppms.TaskMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.service.pms.PurchaseRequiredService;
import ses.dao.bms.UserMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.util.DictionaryDataUtil;
import sums.service.ss.DemandSupervisionService;
import sums.service.ss.SupervisionService;

@Service("demandSupervisionService")
public class DemandSupervisionServiceImpl implements DemandSupervisionService {
    
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
    private PurchaseRequiredMapper requiredMapper;
    
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

    @Override
    public Integer planStatus(String id) {
    	Integer status = collectPlanMapper.collectStatusBySupervision(id);
    	if (status != null) {
    		Integer progressBarPlan = supervisionService.progressBarPlan(status);
    		return progressBarPlan;
		} else {
			return null;
		}
    }

    @Override
    public Integer projectStatus(String id) {
       /* HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", id);
        List<PurchaseDetail> details = purchaseDetailMapper.getByMap(map);
        if(details != null && details.size() > 0){
            HashSet<String> set = new HashSet<>();
            for (PurchaseDetail purchaseDetail : details) {
            	HashMap<String, Object> hashMap = new HashMap<>();
            	hashMap.put("id", purchaseDetail.getId());
            	List<PurchaseDetail> selectByParentId = purchaseDetailMapper.selectByParentId(hashMap);
            	if(selectByParentId != null && selectByParentId.size() == 1){
            		HashMap<String, Object> maps = new HashMap<>();
                    maps.put("requiredId", purchaseDetail.getId());
                    List<ProjectDetail> selectById = projectDetailMapper.selectById(maps);
                    if(selectById != null && selectById.size() > 0){
                    	for (ProjectDetail projectDetail : selectById) {
                    		set.add(projectDetail.getProject().getId());
						}
                    }
            	}
            }
            if(set != null && set.size() > 0){
                List<String> status = new ArrayList<String>();
                for (String string : set) {
                	Project project = projectMapper.selectProjectByPrimaryKey(string);
                	if(project != null && !"4".equals(project.getStatus())){
                    	DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                    	if(!"YJFB".equals(findById.getCode())){
                    		String projectStatus = supervisionService.progressBarProject(project.getStatus());
                            String proStatus = projectStatus + ".00";
                            status.add(proStatus);
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
            }
        }
        return null;*/
    	List<String> supervisionProjectId = purchaseDetailMapper.supervisionProjectId(id);
    	if (supervisionProjectId != null && !supervisionProjectId.isEmpty()) {
    		List<String> status = new ArrayList<String>();
			for (String string : supervisionProjectId) {
				Project project = projectMapper.selectProjectByPrimaryKey(string);
            	if(project != null && !"4".equals(project.getStatus())){
                	DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                	if(!"YJFB".equals(findById.getCode())){
                		String projectStatus = supervisionService.progressBarProject(project.getStatus());
                        String proStatus = projectStatus + ".00";
                        status.add(proStatus);
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
		}
    	return null;
    }

    @Override
    public Integer contractStatus(String id) {
    	List<Integer> statusContract = new ArrayList<Integer>();
    	List<Integer> status = contractRequiredMapper.contractStatusSupervision(id);
    	if (status != null && !status.isEmpty()) {
			for (Integer integer : status) {
				Integer progressBarContract = supervisionService.progressBarContract(integer);
				statusContract.add(progressBarContract);
			}
		}
    	if (statusContract != null && !statusContract.isEmpty()) {
    		Integer num = 0;
            for (Integer integer : statusContract) {
                double number = integer/statusContract.size();
                BigDecimal b = new BigDecimal(number);
                double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                num += (int)total;
            }
            return num;
		} else {
			return null;
		}
        /*List<Integer> statusContract = new ArrayList<Integer>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", id);
        List<PurchaseDetail> details = purchaseDetailMapper.getByMap(map);
        if(details != null && details.size() > 0){
            for (PurchaseDetail purchaseDetail : details) {
                if(!"1".equals(purchaseDetail.getParentId())){
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("requiredId", purchaseDetail.getId());
                    List<ProjectDetail> selectById = projectDetailMapper.selectById(maps);
                    if(selectById != null && selectById.size() > 0){
                        List<ContractRequired> contractRequireds = contractRequiredMapper.selectConRequByDetailId(selectById.get(0).getId());
                        if(contractRequireds != null && contractRequireds.size()>0){
                            PurchaseContract purchaseContract = contractMapper.selectContractByid(contractRequireds.get(0).getContractId());
                            Integer progressBarContract = supervisionService.progressBarContract(purchaseContract.getStatus());
                            statusContract.add(progressBarContract);
                        }
                    }
                }
            }
            
            if(statusContract != null && statusContract.size() > 0){
                Integer num = 0;
                for (Integer integer : statusContract) {
                    double number = integer/statusContract.size();
                    BigDecimal b = new BigDecimal(number);
                    double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    num += (int)total;
                }
                return num;
            }
        }*/
    }

    @Override
    public List<Project> viewProject(String id) {
    	List<String> list = purchaseDetailMapper.supervisionProjectId(id);
    	List<Project> projects = new ArrayList<Project>();
    	if (list != null && !list.isEmpty()) {
			for (String string : list) {
				Project project = projectMapper.selectProjectByPrimaryKey(string);
                if(project != null && !StringUtils.equals("4", project.getStatus())){
                	List<User> users = userMapper.selectByPrimaryKey(project.getAppointMan());
                    project.setAppointMan(users.get(0).getRelName());
                    Orgnization org = orgnizationMapper.findOrgByPrimaryKey(project.getPurchaseDepId());
                    project.setPurchaseDepId(org.getName());
                    DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                    project.setStatus(findById.getName());
                    projects.add(project);
                }
			}
		}
    	if (projects != null && !projects.isEmpty()) {
			return projects;
		} else {
			return null;
		}
    }

    @Override
    public List<Packages> viewPack(String id) {
        
        return null;
    }

}
