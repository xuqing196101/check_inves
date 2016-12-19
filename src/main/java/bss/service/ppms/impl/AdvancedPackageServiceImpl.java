package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.AdvancedPackageMapper;
import bss.model.ppms.AdvancedPackages;
import bss.service.ppms.AdvancedPackageService;

@Service("advancedPackageService")
public class AdvancedPackageServiceImpl implements AdvancedPackageService {

    @Autowired
    private AdvancedPackageMapper packageMapper;
    
    @Override
    public AdvancedPackages selectById(String id) {
       
        return packageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(AdvancedPackages packages) {
       
        packageMapper.updateByPrimaryKeySelective(packages);
    }

    @Override
    public void save(AdvancedPackages packages) {
       
        packageMapper.insertSelective(packages);
    }

    @Override
    public AdvancedPackages deleteById(String id) {
        
        return packageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AdvancedPackages> selectByAll(HashMap<String, Object> map) {

        return packageMapper.selectByAll(map);
    }

}
