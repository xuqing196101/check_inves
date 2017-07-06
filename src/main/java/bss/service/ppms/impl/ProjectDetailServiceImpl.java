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
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import common.constant.StaticVariables;

import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.theSubjectMapper;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.theSubject;
import bss.service.ppms.ProjectDetailService;

@Service("projectDetailService")
public class ProjectDetailServiceImpl implements ProjectDetailService {
	@Autowired
	private ProjectDetailMapper projectDetailMapper ;
	@Autowired
	private theSubjectMapper theSubjectMapper;
	
	@Autowired
	private ProjectMapper projectMapper;

	@Override
	public ProjectDetail selectByPrimaryKey(String id) {
		return projectDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public void insert(ProjectDetail projectDetail) {
		projectDetailMapper.insertSelective(projectDetail);
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		projectDetailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(ProjectDetail projectDetail) {
		projectDetailMapper.updateByPrimaryKeySelective(projectDetail);
	}

	@Override
	public List<ProjectDetail> listByAll(ProjectDetail projectDetail) {
		return projectDetailMapper.listByAll(projectDetail);
	}

	@Override
	public List<ProjectDetail> selectById(HashMap<String,Object> map) {
		List<ProjectDetail> projectDetailList = projectDetailMapper.selectById(map);
		for (ProjectDetail projectDetail : projectDetailList) {
			//定义一个list集合查询符合map条件的标的信息
			map.put("forSubjectDetailId", projectDetail.getId());
			List<theSubject> subjectList = theSubjectMapper.selectById(map);
			projectDetail.setSubjectList(subjectList);
		}
		
		return projectDetailList;
	}

	@Override
	public List<ProjectDetail> selectByCondition(HashMap<String,Object> map,Integer page) {
		return projectDetailMapper.selectByCondition(map);
	}
	
	@Override
	public List<ProjectDetail> selectByParentId(Map<String, Object> map) {
		return projectDetailMapper.selectByParentId(map);
	}

	
	@Override
	public List<ProjectDetail> selectByParent(Map<String, Object> map) {
		return projectDetailMapper.selectByParent(map);
	}

	@Override
	public List<ProjectDetail> selectByProjectIds(HashMap<String, Object> map) {
		return projectDetailMapper.selectByProjectIds(map);
	}

	
	@Override
	public List<ProjectDetail> selectParentIdByPackageId(String packageId) {
		return projectDetailMapper.selectParentIdByPackageId(packageId);
	}

	
	@Override
	public List<ProjectDetail> selectNotEmptyPackageOfDetail(String projectId) {
		return projectDetailMapper.selectNotEmptyPackageOfDetail(projectId);
	}

	
	@Override
	public List<ProjectDetail> findHavePackageIdDetail(HashMap<String, Object> map) {
		return projectDetailMapper.findHavePackageIdDetail(map);
	}

	
	@Override
	public List<ProjectDetail> findNoPackageIdDetail(HashMap<String, Object> map) {
		return projectDetailMapper.findNoPackageIdDetail(map);
	}

	@Override
	public ProjectDetail getMax(String projectId) {
		return projectDetailMapper.getmax(projectId);
	}

	
	@Override
	public List<ProjectDetail> selectByRequiredId(Map<String, Object> map) {
		return projectDetailMapper.selectByRequiredId(map);
	}

	@Override
	public List<ProjectDetail> selectByParentIds(Map<String, Object> map) {
		return projectDetailMapper.selectByParentIds(map);
	}

	@Override
	public void deleteByProject(String id) {
		projectDetailMapper.deleteByProject(id);
		
		
	}

	@Override
	public List<ProjectDetail> selectByParentIdTree(Map<String, Object> map) {
		return projectDetailMapper.selectByParentIdTree(map);
	}

	@Override
	public List<ProjectDetail> getByPidAndRid(String pid, String rid) {
		return projectDetailMapper.getByPidAndRid(pid, rid);
	}

    @Override
    public List<ProjectDetail> selectByDemand(HashMap<String, Object> map) {
    	
    	return projectDetailMapper.selectByDemand(map);
    }
    @Override
    public List<ProjectDetail> selectByPackageId(String packageId) {
    	HashMap<String, Object> map=new HashMap<String, Object>();
    	map.put("packageId", packageId);
        return projectDetailMapper.selectById(map);
    }
	@Override
	public List<ProjectDetail> selectTheSubjectBySupplierId(
			HashMap<String, Object> map, String supplierId) {
		List<ProjectDetail> projectDetailList = projectDetailMapper.selectById(map);
		for (ProjectDetail projectDetail : projectDetailList) {
			//定义一个list集合查询符合map条件的标的信息
			map.put("forSubjectDetailId", projectDetail.getId());
			map.put("supplierId", supplierId);
			List<theSubject> subjectList = theSubjectMapper.selectBySupplierId(map);
			projectDetail.setSubjectList(subjectList);
		}
		
		return projectDetailList;
	}
	/**
	 * 实现 获取项目明细
	 */
  @Override
  public List<ProjectDetail> findByIdPackageId(HashMap<String, Object> map) {
    // TODO Auto-generated method stub
    return projectDetailMapper.selectById(map);
  }

	@Override
	public List<ProjectDetail> viewDetail(String projectId) {
		List<ProjectDetail> list = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", projectId);
		List<ProjectDetail> details = projectDetailMapper.selectById(map);
		if(details != null && details.size() > 0){
			for (ProjectDetail projectDetail : details) {
				 HashMap<String,Object> detailMap = new HashMap<>();
		         detailMap.put("id",projectDetail.getRequiredId());
		         detailMap.put("projectId", projectId);
		         List<ProjectDetail> dlist = projectDetailMapper.selectByParentId(detailMap);
		         if(dlist != null && dlist.size()==1 && projectDetail.getPrice() != null){
		        	 list.add(projectDetail);
		         }
			}
		}
		return list;
	}

	@Override
	public List<ProjectDetail> showDetail(List<ProjectDetail> list, String projectId) {
		List<ProjectDetail> showDetails = new ArrayList<>();
		List<String> parentId = new ArrayList<>();
		String pid = null;
		String str = null;
		for (int i = 0; i < list.size(); i++) {
			if(StringUtils.isBlank(list.get(i).getPackageId())){
				HashMap<String, Object> map = new HashMap<>();
				map.put("id", list.get(i).getRequiredId());
				map.put("projectId", projectId);
				List<ProjectDetail> details = projectDetailMapper.selectByParent(map);
				if(details != null && details.size() > 0){
					if("1".equals(list.get(i).getParentId())){
						pid = list.get(i).getId();
					}
				}
				if(StringUtils.isNotBlank(pid) && !parentId.contains(pid)){
					HashMap<String,Object> detailMap = new HashMap<>();
					detailMap.put("id",list.get(i).getRequiredId());
                    detailMap.put("projectId", projectId);
                    List<ProjectDetail> dlist = projectDetailMapper.selectByParent(detailMap);
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
	                List<ProjectDetail> list3 = projectDetailMapper.selectByParent(detailMap);
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
                    for(int j=0;j<showDetails.size();j++){
                        HashMap<String,Object> detailMap = new HashMap<>();
                        detailMap.put("id",showDetails.get(j).getRequiredId());
                        detailMap.put("projectId", projectId);
                        List<ProjectDetail> dlist = projectDetailMapper.selectByParentId(detailMap);
                        if(dlist != null && dlist.size()>1){
                            showDetails.get(j).setDetailStatus(0);
                        }
                    }
				} else {
					 Project project = projectMapper.selectProjectByPrimaryKey(projectId);
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
	public List<ProjectDetail> showPackDetail(List<ProjectDetail> list, String projectId) {
		List<String> parentId = new ArrayList<>();
		List<ProjectDetail> newDetails = new ArrayList<>();
		for (ProjectDetail projectDetail : list) {
			HashMap<String,Object> dMap = new HashMap<String,Object>();
            dMap.put("projectId", projectId);
            dMap.put("id", projectDetail.getRequiredId());
            List<ProjectDetail> lists = projectDetailMapper.selectByParent(dMap);
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
                List<ProjectDetail> pList = projectDetailMapper.selectByParent(parentMap);
                newDetails.addAll(pList);
            }else{
                HashMap<String,Object> map2 = new HashMap<>();
                map2.put("projectId", projectId);
                map2.put("id", projectDetail.getRequiredId());
                List<ProjectDetail> list3 = projectDetailMapper.selectByParent(map2);
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
