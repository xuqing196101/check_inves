package sums.service.ss.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;

import ses.dao.bms.UserMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import sums.service.ss.ProjectSupervisionService;
import sums.service.ss.SupervisionService;

@Service("projectSupervisionService")
public class ProjectSupervisionServiceImpl implements ProjectSupervisionService {

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
	public Integer contractStatus(String id) {
		HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<ProjectDetail> selectById = projectDetailMapper.selectById(map);
        List<Integer> statusContract = new ArrayList<Integer>();
        if(selectById != null && selectById.size() > 0){
        	for (ProjectDetail projectDetail : selectById) {
                List<ContractRequired> contractRequireds = contractRequiredMapper.selectConRequByDetailId(projectDetail.getId());
                if(contractRequireds != null && contractRequireds.size()>0){
                    PurchaseContract purchaseContract = contractMapper.selectContractByid(contractRequireds.get(0).getContractId());
                    Integer progressBarContract = supervisionService.progressBarContract(purchaseContract.getStatus());
                    statusContract.add(progressBarContract);
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
		}
		return list;
	}	
}
