package bss.service.ppms.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.ComparatorDetail;
import ses.util.DictionaryDataUtil;

import common.constant.StaticVariables;

import bss.dao.ppms.AdvancedDetailMapper;
import bss.dao.ppms.AdvancedProjectMapper;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.service.ppms.AdvancedDetailService;

@Service("advancedDetailService")
public class AdvancedDetailServiceImpl implements AdvancedDetailService {

    @Autowired
    private AdvancedDetailMapper advancedDetailMapper;
    
    @Autowired
    private AdvancedProjectMapper projectMapper;

    @Override
    public AdvancedDetail selectById(String id) {
        
        return advancedDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AdvancedDetail> selectParentIdByPackageId(String packageId) {
        
        return advancedDetailMapper.selectParentIdByPackageId(packageId);
    }

    @Override
    public List<AdvancedDetail> selectByParentId(Map<String, Object> map) {
        
        return advancedDetailMapper.selectByParentId(map);
    }

    @Override
    public List<AdvancedDetail> selectByParent(Map<String, Object> map) {
        
        return advancedDetailMapper.selectByParent(map);
    }

    @Override
    public void update(AdvancedDetail advancedDetail) {
        
        advancedDetailMapper.updateByPrimaryKeySelective(advancedDetail);
    }

    @Override
    public void save(AdvancedDetail AdvancedDetail) {
        
        advancedDetailMapper.insertSelective(AdvancedDetail);
    }

    @Override
    public void deleteById(String id) {
        
        advancedDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AdvancedDetail> selectByAll(HashMap<String, Object> map) {
        
        return advancedDetailMapper.selectByAll(map);
    }

    @Override
    public AdvancedDetail selectByRequiredId(String id) {

        return advancedDetailMapper.selectByRequiredId(id);
    }

    @Override
    public List<AdvancedDetail> findNoPackageIdDetail(Map<String, Object> map) {

        return advancedDetailMapper.findNoPackageIdDetail(map);
    }

    @Override
    public List<AdvancedDetail> findHavePackageIdDetail(Map<String, Object> map) {

        return advancedDetailMapper.findHavePackageIdDetail(map);
    }

    @Override
    public List<AdvancedDetail> selectByCondition(HashMap<String, Object> map) {
        
        return advancedDetailMapper.selectByCondition(map);
    }

    @Override
    public List<AdvancedDetail> viewDetail(String projectId) {
        List<AdvancedDetail> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("advancedProject", projectId);
        List<AdvancedDetail> details = advancedDetailMapper.selectByAll(map);
        if(details != null && details.size() > 0){
            for (AdvancedDetail projectDetail : details) {
                 HashMap<String,Object> detailMap = new HashMap<>();
                 detailMap.put("id",projectDetail.getRequiredId());
                 detailMap.put("projectId", projectId);
                 List<AdvancedDetail> dlist = advancedDetailMapper.selectByParentId(detailMap);
                 if(dlist != null && dlist.size() == 1 && projectDetail.getPrice() != null){
                     list.add(projectDetail);
                 } 
            }
        }
        return list;
    }

    @Override
    public List<AdvancedDetail> showDetail(List<AdvancedDetail> list, String projectId) {
        List<AdvancedDetail> showDetails = new ArrayList<>();
        List<String> parentId = new ArrayList<>();
        String pid = null;
        String str = null;
        for (int i = 0; i < list.size(); i++) {
            if(StringUtils.isBlank(list.get(i).getPackageId())){
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", list.get(i).getRequiredId());
                map.put("projectId", projectId);
                List<AdvancedDetail> details = advancedDetailMapper.selectByParent(map);
                if(details != null && details.size() > 0){
                    if("1".equals(list.get(i).getParentId())){
                        pid = list.get(i).getId();
                    }
                }
                if(StringUtils.isNotBlank(pid) && !parentId.contains(pid)){
                    HashMap<String,Object> detailMap = new HashMap<>();
                    detailMap.put("id",list.get(i).getRequiredId());
                    detailMap.put("projectId", projectId);
                    List<AdvancedDetail> dlist = advancedDetailMapper.selectByParent(detailMap);
                    if(dlist != null && dlist.size() > 0){
                        for(int j= dlist.size()-1; j>= 0; j--){
                            showDetails.add(dlist.get(j));
                         }
                    }
                    str = StaticVariables.OPER_CANCEL_TYPE;
                    parentId.add(pid);
                } else {
                    HashMap<String,Object> detailMap = new HashMap<>();
                    detailMap.put("projectId", projectId);
                    detailMap.put("id", list.get(i).getRequiredId());
                    List<AdvancedDetail> list3 = advancedDetailMapper.selectByParent(detailMap);
                    if(list3 != null && list3.size() > 0){
                        for(int j=0;j<showDetails.size();j++){
                            for(int k=0;k<list3.size();k++){
                                if(showDetails.get(j).getId().equals(list3.get(k).getId())){
                                    list3.remove(list3.get(k));
                                    break;
                                }
                            }
                         }
                    }
                    showDetails.addAll(list3);
                }
            }
            if(i == list.size()-1){
                if(StringUtils.isNotBlank(str)){
                    ComparatorDetail comparator = new ComparatorDetail();
                    Collections.sort(showDetails, comparator);
                    /*for(int j=0;j<showDetails.size();j++){
                        HashMap<String,Object> detailMap = new HashMap<>();
                        detailMap.put("id",showDetails.get(j).getRequiredId());
                        detailMap.put("projectId", projectId);
                        List<AdvancedDetail> dlist = advancedDetailMapper.selectByParentId(detailMap);
                        if(dlist != null && dlist.size()>1){
                            showDetails.get(j).setDetailStatus(0);
                        }
                    }*/
                } else {
                     AdvancedProject project = projectMapper.selectAdvancedProjectByPrimaryKey(projectId);
                     if(project != null){
                         if(DictionaryDataUtil.getId("YJLX").equals(project.getStatus()) || DictionaryDataUtil.getId("XMXXWHZ").equals(project.getStatus()) || DictionaryDataUtil.getId("SSZ_WWSXX").equals(project.getStatus())){
                             project.setStatus(DictionaryDataUtil.getId("FBWC"));
                             projectMapper.updateByPrimaryKeySelective(project);
                         }
                     }
                }
            }
        
        }
        return showDetails;
    }

    @Override
    public List<AdvancedDetail> showPackDetail(List<AdvancedDetail> list, String projectId) {
        List<String> parentId = new ArrayList<>();
        List<AdvancedDetail> newDetails = new ArrayList<>();
        for (AdvancedDetail projectDetail : list) {
            HashMap<String,Object> dMap = new HashMap<String,Object>();
            dMap.put("projectId", projectId);
            dMap.put("id", projectDetail.getRequiredId());
            List<AdvancedDetail> lists = advancedDetailMapper.selectByParent(dMap);
            String ids = null;
            for(int k=0;k<lists.size();k++){
                if("1".equals(lists.get(k).getParentId())){
                    ids = lists.get(k).getId();
                    break;
                }
            }
            if(!parentId.contains(ids)){
                parentId.add(ids);
                HashMap<String,Object> parentMap = new HashMap<>();
                parentMap.put("projectId", projectId);
                parentMap.put("id", projectDetail.getRequiredId());
                List<AdvancedDetail> pList = advancedDetailMapper.selectByParent(parentMap);
                newDetails.addAll(pList);
            }else{
                HashMap<String,Object> map2 = new HashMap<>();
                map2.put("projectId", projectId);
                map2.put("id", projectDetail.getRequiredId());
                List<AdvancedDetail> list3 = advancedDetailMapper.selectByParent(map2);
                for(int j=0;j<newDetails.size();j++){
                    for(int k=0;k<list3.size();k++){
                        if(newDetails.get(j).getId().equals(list3.get(k).getId())){
                            list3.remove(list3.get(k));
                            break;
                        }
                    }
                }
                newDetails.addAll(list3);
            }
        }
        return newDetails;
    }

    
}
