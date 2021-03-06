package sums.service.ss.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.cs.ContractRequiredMapper;
import bss.dao.cs.PurchaseContractMapper;
import bss.dao.pms.CollectPlanMapper;
import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.ppms.AdvancedDetailMapper;
import bss.dao.ppms.AdvancedProjectMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.ProjectTaskMapper;
import bss.dao.ppms.TaskMapper;
import bss.dao.pqims.PqInfoMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.model.pqims.PqInfo;
import bss.service.pms.PurchaseRequiredService;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import sums.service.ss.SupervisionService;

@Service("supervisionService")
public class SupervisionServiceImpl implements SupervisionService {
    
    @Autowired
    private ProjectDetailMapper projectDetailMapper;
    
    @Autowired
    private ContractRequiredMapper contractRequiredMapper;
    
    @Autowired
    private PurchaseContractMapper contractMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private CollectPlanMapper collectPlanMapper;
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;
    
    @Autowired
    private PurchaseRequiredService requiredService;
    
    @Autowired
    private PqInfoMapper pqInfoMapper;
    
    @Autowired
    private AdvancedDetailMapper advancedDetailMapper;
    
    @Autowired
    private AdvancedProjectMapper advancedProjectMapper;
    
    @Autowired
    private ProjectTaskMapper projectTaskMapper;
    
    @Autowired
    private PackageMapper packageMapper;
    
    @Override
    public String[] progressBar(String id, String projectId) {
        String[] progressBar = null;
        if(StringUtils.isNotBlank(id)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("requiredId", id);
            if(StringUtils.isNotBlank(projectId)){
            	map.put("id", projectId);
            }
            List<ProjectDetail> selectById = projectDetailMapper.selectById(map);
            if(selectById != null && selectById.size() > 0){
                List<ContractRequired> contractRequireds = contractRequiredMapper.selectConRequByDetailId(selectById.get(0).getId());
                if(contractRequireds != null && contractRequireds.size()>0){
                    PurchaseContract purchaseContract = contractMapper.selectContractByid(contractRequireds.get(0).getContractId());
                    progressBar = progressBars(String.valueOf(purchaseContract.getStatus()),purchaseContract.getId());
                } else {
                	if(StringUtils.isBlank(selectById.get(0).getPackageId())){
                		Project project = projectMapper.selectProjectByPrimaryKey(selectById.get(0).getProject().getId());
                        progressBar = progressBars(project.getStatus(),id);
                	} else {
                		Packages packages = packageMapper.selectByPrimaryKeyId(selectById.get(0).getPackageId());
                		progressBar = progressBars(packages.getProjectStatus(),id);
                	}
                    
                }
            } else {
                PurchaseDetail detail = purchaseDetailMapper.selectByPrimaryKey(id);
                if(detail != null){
                    map.put("collectId", detail.getUniqueId());
                    map.put("purchaseId", detail.getOrganization());
                    List<Task> listBycollect = taskMapper.listBycollect(map);
                    if(listBycollect != null && listBycollect.size() > 0){
                        progressBar = progressBars(String.valueOf(listBycollect.get(0).getStatus()),listBycollect.get(0).getId());
                    } else {
                        progressBar = progressBars(detail.getStatus(),detail.getUniqueId());
                       /* HashMap<String, Object> maps = new HashMap<>();
                        maps.put("requiredId", detail.getId());
                        List<ProjectDetail> selectById2 = projectDetailMapper.selectById(maps);
                        if(selectById2 != null && selectById2.size() > 0){
                            
                        }else{
                            
                            PurchaseRequired purchaseRequired = requiredService.queryById(detail.getId());
                            progressBar = adProgressBar(purchaseRequired.getId());
                        }*/
                    }
                } else {
                    PurchaseRequired purchaseRequired = requiredService.queryById(id);
                    if(purchaseRequired != null){
                        String[] adProgressBar = adProgressBar(purchaseRequired.getId());
                        if(adProgressBar != null){
                            progressBar = adProgressBar(purchaseRequired.getId());
                        }else{
                            progressBar = progressBars(purchaseRequired.getStatus(),purchaseRequired.getUniqueId());
                        }
                    }
                }
                
            }
        }
        return progressBar;
    }
    
    
    
    
    public String[] progressBars(String status, String id){
        double number = 100.00/39.00;
        BigDecimal b = new BigDecimal(number);
        double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        String num = null;
        String name = null;
        PurchaseContract contract = contractMapper.selectContractByid(id);
        if(contract == null){
            DictionaryData findById = DictionaryDataUtil.findById(status);
            if(findById == null){
                Task task = taskMapper.selectByPrimaryKey(id);
                if(task == null){
                    CollectPlan queryById = collectPlanMapper.selectByPrimaryKey(id);
                    if(queryById != null){
                        String valueOf = String.valueOf(queryById.getStatus());
                        if("1".equals(valueOf)){
                            int one = 5;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购计划审核轮次设置";
                        }
                        if("3".equals(valueOf) || "4".equals(valueOf) || "5".equals(valueOf) || "6".equals(valueOf) || "7".equals(valueOf)){
                            int one = 6;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购计划审核中";
                        }
                        if("12".equals(valueOf) || "13".equals(valueOf)){
                            int one = 7;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购计划未下达";
                        }
                        if("2".equals(valueOf)){
                            int one = 8;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购计划已下达";
                        }
                    } else {
                        if("1".equals(status)){
                            int one = 1;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购需求未提交";
                        }
                        if("2".equals(status)){
                            int one = 2;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购需求待受理";
                        }
                        if("3".equals(status)){
                            int one = 3;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购需求已受理";
                        }
                        if("4".equals(status)){
                            int one = 4;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购需求受理退回";
                        }
                        if("5".equals(status)){
                            int one = 5;
                            num = String.valueOf(Math.round(total*one));
                            name = "采购需求已受理";
                        }
                    }
                } else {
                    if(task.getStatus() == 0){
                        int one = 9;
                        num = String.valueOf(Math.round(total*one));
                        name = "采购任务未受领";
                    }
                    if(task.getStatus() == 1){
                        int one = 10;
                        num = String.valueOf(Math.round(total*one));
                        name = "采购任务已受领";
                    }
                    if(task.getStatus() == 2){
                        int one = 11;
                        num = String.valueOf(Math.round(total*one));
                        name = "采购任务已取消";
                    }
                }
            } else {
                num = String.valueOf(Math.round(total*(findById.getPosition() + 11)));
                name = findById.getName();
            }
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("contract", contract);
            List<PqInfo> selectByContract = pqInfoMapper.selectByContract(map);
            if(selectByContract != null && selectByContract.size() > 0){
                if("0".equals(selectByContract.get(0).getConclusion())){
                    //int one = 38;
                    num = "100";
                    name = "质检合格";
                }
                if("1".equals(selectByContract.get(0).getConclusion())){
                    int one = 37;
                    num = String.valueOf(Math.round(total*one));
                    name = "质检不合格";
                }
            }else{
                if(contract.getStatus() == 0){
                    int one = 34;
                    num = String.valueOf(Math.round(total*one));
                    name = "暂存合同";
                }
                if(contract.getStatus() == 1){
                    int one = 35;
                    num = String.valueOf(Math.round(total*one));
                    name = "已生成合同草案";
                }
                if(contract.getStatus() == 2){
                    int one = 36;
                    num = String.valueOf(Math.round(total*one));
                    name = "正式合同";
                }
            }
        }
        String[] names = {num,name};
        return names;
    }




    @Override
    public String progressBarProject(String status) {
        String num = null;
        if(StringUtils.isNotBlank(status)){
            DictionaryData findById = DictionaryDataUtil.findById(status);
            if(findById != null){
                double number = 100.00/21.00;
                BigDecimal b = new BigDecimal(number);
                double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                num = String.valueOf(Math.round(total*findById.getPosition()));
            }
        }
        return num;
    }




    @Override
    public Integer progressBarPlan(Integer status) {
        Integer num = null;
        if(status == 1){
            num = 20;
        }
        if(status == 3 || status == 4 || status == 5 || status == 6  || status == 7 ){
            num = 50;
        }
        if(status == 12 || status == 13){
            num = 80;
        }
        if(status == 2){
            num = 100;
        }
        return num;
    }




    @Override
    public Integer progressBarContract(Integer status) {
        Integer num = null;
        if(status == 0){
            num = 20;
        }
        if(status == 1){
            num = 50;
        }
        if(status == 2){
            num = 100;
        }
        return num;
    }




    @Override
    public String[] adProgressBar(String id) {
        String[] progressBar = null;
        if(StringUtils.isNotBlank(id)){
            AdvancedDetail detail = advancedDetailMapper.selectByRequiredId(id);
            if(detail != null){
                AdvancedProject project = advancedProjectMapper.selectAdvancedProjectByPrimaryKey(detail.getAdvancedProject());
                if(project != null){
                    progressBar = adBar(project.getStatus(),project.getId());
                }
            }
        }
        return progressBar;
    }
    
    
    public String[] adBar(String status, String id){
        double number = 100.00/30.00;
        BigDecimal b = new BigDecimal(number);
        double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        String num = null;
        String name = null;
        DictionaryData findById = DictionaryDataUtil.findById(status);
        if(findById != null){
            num = String.valueOf(Math.round(total*(findById.getPosition() + 8)));
            name = findById.getName();
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("projectId", id);
            List<ProjectTask> queryByNo = projectTaskMapper.queryByNo(map);
            Task task = taskMapper.selectByPrimaryKey(queryByNo.get(0).getTaskId());
            if(task == null){
                if("1".equals(status)){
                    int one = 1;
                    num = String.valueOf(Math.round(total*one));
                    name = "采购需求未提交";
                }
                if("2".equals(status)){
                    int one = 2;
                    num = String.valueOf(Math.round(total*one));
                    name = "采购需求待受理";
                }
                if("3".equals(status)){
                    int one = 3;
                    num = String.valueOf(Math.round(total*one));
                    name = "采购需求已受理";
                }
                if("4".equals(status)){
                    int one = 4;
                    num = String.valueOf(Math.round(total*one));
                    name = "采购需求受理退回";
                }
                if("5".equals(status)){
                    int one = 5;
                    num = String.valueOf(Math.round(total*one));
                    name = "采购需求已受理";
                }
            } else {
                if(task.getStatus() == 0){
                    int one = 6;
                    num = String.valueOf(Math.round(total*one));
                    name = "采购任务未受领";
                }
                if(task.getStatus() == 1){
                    int one = 7;
                    num = String.valueOf(Math.round(total*one));
                    name = "采购任务已受领";
                }
                if(task.getStatus() == 2){
                    int one = 8;
                    num = String.valueOf(Math.round(total*one));
                    name = "采购任务已取消";
                }
            }
        }
        String[] names = {num,name};
        return names;
    }

}
