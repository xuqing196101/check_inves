package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.AdvancedProjectMapper;
import bss.model.ppms.AdvancedProject;
import bss.service.ppms.AdvancedProjectService;

@Service("advancedProjectService")
public class AdvancedProjectServiceImpl implements AdvancedProjectService {
    @Autowired
    private AdvancedProjectMapper advancedProjectMapper;
    
    @Override
    public void add(AdvancedProject project) {
        
        advancedProjectMapper.insertSelective(project);
    }

    @Override
    public void update(AdvancedProject project) {
        
        advancedProjectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    public void delete(String id) {
        
        advancedProjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AdvancedProject selectById(String id) {
        
        return advancedProjectMapper.selectAdvancedProjectByPrimaryKey(id);
    }

    @Override
    public List<AdvancedProject> list(AdvancedProject project) {
        
        return advancedProjectMapper.selectByList(project);
    }

    @Override
    public List<AdvancedProject> lists(Integer page, AdvancedProject project) {
        
        return null;
    }

    @Override
    public List<AdvancedProject> selectSuccessProject(Map<String, Object> map) {
        
        return null;
    }

    @Override
    public boolean SameNameCheck(AdvancedProject project) {
        
        return false;
    }

    @Override
    public List<AdvancedProject> provisionalList(Integer page, AdvancedProject project) {
        
        return null;
    }

    @Override
    public List<AdvancedProject> selectProjectByCode(HashMap<String, Object> map) {
        
        return null;
    }

}
