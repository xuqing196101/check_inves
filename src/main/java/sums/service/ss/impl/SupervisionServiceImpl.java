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
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.TaskMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.Task;

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
    

    @Override
    public String progressBarPlan(String id) {
        String progressBar = null;
        if(StringUtils.isNotBlank(id)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("requiredId", id);
            List<ProjectDetail> selectById = projectDetailMapper.selectById(map);
            if(selectById != null && selectById.size() > 0){
                List<ContractRequired> contractRequireds = contractRequiredMapper.selectConRequByDetailId(selectById.get(0).getId());
                if(contractRequireds != null && contractRequireds.size()>0){
                    PurchaseContract purchaseContract = contractMapper.selectContractByid(contractRequireds.get(0).getContractId());
                    progressBar = progressBar(String.valueOf(purchaseContract.getStatus()),purchaseContract.getId());
                } else {
                    Project project = projectMapper.selectProjectByPrimaryKey(selectById.get(0).getProject().getId());
                    progressBar = progressBar(project.getStatus(),id);
                }
            } else {
                PurchaseDetail detail = purchaseDetailMapper.selectByPrimaryKey(id);
                if(detail != null){
                    map.put("collectId", detail.getUniqueId());
                    map.put("purchaseId", detail.getOrganization());
                    List<Task> listBycollect = taskMapper.listBycollect(map);
                    if(listBycollect != null && listBycollect.size() > 0){
                        progressBar = progressBar(String.valueOf(listBycollect.get(0).getStatus()),listBycollect.get(0).getId());
                    } else {
                        progressBar = progressBar(detail.getStatus(),detail.getUniqueId());
                    }
                }
                
            }
        }
        return progressBar;
    }
    
    
    
    
    public String progressBar(String status, String id){
        double number = 100.00/36.00;
        BigDecimal b = new BigDecimal(number);
        double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        String num = null;
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
                        }
                        if("3".equals(valueOf) || "4".equals(valueOf) || "5".equals(valueOf) || "6".equals(valueOf) || "7".equals(valueOf)){
                            int one = 6;
                            num = String.valueOf(Math.round(total*one));
                        }
                        if("12".equals(valueOf) || "13".equals(valueOf)){
                            int one = 7;
                            num = String.valueOf(Math.round(total*one));
                        }
                        if("2".equals(valueOf)){
                            int one = 8;
                            num = String.valueOf(Math.round(total*one));
                        }
                    } else {
                        if("1".equals(status)){
                            int one = 1;
                            num = String.valueOf(Math.round(total*one));
                        }
                        if("2".equals(status)){
                            int one = 2;
                            num = String.valueOf(Math.round(total*one));
                        }
                        if("3".equals(status)){
                            int one = 3;
                            num = String.valueOf(Math.round(total*one));
                        }
                        if("4".equals(status)){
                            int one = 4;
                            num = String.valueOf(Math.round(total*one));
                        }
                        if("5".equals(status)){
                            int one = 5;
                            num = String.valueOf(Math.round(total*one));
                        }
                    }
                } else {
                    if("0".equals(status)){
                        int one = 9;
                        num = String.valueOf(Math.round(total*one));
                    }
                    if("1".equals(status)){
                        int one = 10;
                        num = String.valueOf(Math.round(total*one));
                    }
                    if("2".equals(status)){
                        int one = 11;
                        num = String.valueOf(Math.round(total*one));
                    }
                }
            } else {
                num = String.valueOf(Math.round(total*(findById.getPosition() + 11)));
            }
        } else {
            if(contract.getStatus() == 0){
                int one = 34;
                num = String.valueOf(Math.round(total*one));
            }
            if(contract.getStatus() == 1){
                int one = 35;
                num = String.valueOf(Math.round(total*one));
            }
            if(contract.getStatus() == 2){
                int one = 36;
                num = String.valueOf(Math.round(total*one));
            }
        }
        return num;
    }

}
