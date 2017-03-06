package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ppms.AdvancedProjectMapper;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;
import bss.service.ppms.AdvancedProjectService;

@Service("advancedProjectService")
public class AdvancedProjectServiceImpl implements AdvancedProjectService {
    @Autowired
    private AdvancedProjectMapper advancedProjectMapper;

    @Override
    public void deleteById(String id) {
        
        advancedProjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(AdvancedProject record) {
        
        advancedProjectMapper.insertSelective(record);
    }

    @Override
    public AdvancedProject selectById(String id) {
        
        return advancedProjectMapper.selectAdvancedProjectByPrimaryKey(id);
    }

    @Override
    public void update(AdvancedProject record) {
        
        advancedProjectMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<AdvancedProject> selectByList(HashMap<String, Object> map) {
        
        return advancedProjectMapper.selectByList(map);
    }

    @Override
    public boolean SameNameCheck(AdvancedProject advancedProject) {
        boolean flag= true;
        List<AdvancedProject> list = advancedProjectMapper.verifyByProject(advancedProject);
        if(list != null && list.size()>0){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<AdvancedProject> selectByDemand(HashMap<String, Object> map) {
        
        return advancedProjectMapper.selectByDemand(map);
    }

    @Override
    public List<AdvancedProject> selectByOrg(HashMap<String, Object> map) {
        
        return advancedProjectMapper.selectByOrg(map);
    }

    @Override
    public List<AdvancedProject> selectProjectByAll(Integer page,AdvancedProject project) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        return advancedProjectMapper.selectProjectByAll(project);
    }
    
   

}
