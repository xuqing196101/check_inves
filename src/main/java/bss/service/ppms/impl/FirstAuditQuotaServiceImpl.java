package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.FirstAuditQuotaMapper;
import bss.model.ppms.FirstAuditQuota;
import bss.service.ppms.FirstAuditQuotaService;

/**
 * 版权：(C) 版权所有 
 * <简述>投标指标值以及得分业务处理接口的实现
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Service("firstAuditQuotaService")
public class FirstAuditQuotaServiceImpl implements FirstAuditQuotaService {

    @Autowired
    private FirstAuditQuotaMapper firstAuditQuotaMapper;

    @Override
    public List<FirstAuditQuota> find(FirstAuditQuota faq) {
        return firstAuditQuotaMapper.findList(faq);
    }

    @Override
    public FirstAuditQuota get(String id) {
        return firstAuditQuotaMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(FirstAuditQuota faq) {
        firstAuditQuotaMapper.update(faq);
    }

    @Override
    public void save(FirstAuditQuota faq) {
        firstAuditQuotaMapper.insert(faq);
    }

    @Override
    public void delete(String id) {
        firstAuditQuotaMapper.delete(id);
    }
    
}
