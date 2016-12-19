package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.AdvancedDetailMapper;
import bss.model.ppms.AdvancedDetail;
import bss.service.ppms.AdvancedDetailService;

@Service("advancedDetailService")
public class AdvancedDetailServiceImpl implements AdvancedDetailService {

    @Autowired
    private AdvancedDetailMapper advancedDetailMapper;

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
    
}
