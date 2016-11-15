package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.model.ppms.AduitQuota;
import bss.service.ppms.AduitQuotaService;

/**
 * 版权：(C) 版权所有 
 * <简述>投标指标值以及得分业务处理接口的实现
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Service("aduitQuotaService")
public class AduitQuotaServiceImpl implements AduitQuotaService {
	
    @Autowired
	private AduitQuotaService aduitQuotaService;

    @Override
    public List<AduitQuota> find(AduitQuota aq) {
        return aduitQuotaService.find(aq);
    }

    @Override
    public AduitQuota get(String id) {
        return aduitQuotaService.get(id);
    }

    @Override
    public void update(AduitQuota aq) {
        aduitQuotaService.update(aq);        
    }

    @Override
    public void save(AduitQuota aq) {
        aduitQuotaService.save(aq);        
    }

    @Override
    public void delete(String id) {
        aduitQuotaService.delete(id);
    }
	
}
