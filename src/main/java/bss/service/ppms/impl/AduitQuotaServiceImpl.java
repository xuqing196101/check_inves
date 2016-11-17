package bss.service.ppms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.AduitQuotaMapper;
import bss.model.ppms.AduitQuota;
import bss.model.prms.ext.AuditModelExt;
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
	private AduitQuotaMapper aduitQuotaMapper;

    @Override
    public List<AduitQuota> find(AduitQuota aq) {
        return aduitQuotaMapper.findList(aq);
    }

    @Override
    public AduitQuota get(String id) {
        return aduitQuotaMapper.get(id);
    }

    @Override
    public void update(AduitQuota aq) {
        aduitQuotaMapper.update(aq);        
    }

    @Override
    public void save(AduitQuota aq) {
        aduitQuotaMapper.insert(aq);        
    }

    @Override
    public void delete(String id) {
        aduitQuotaMapper.delete(id);
    }
    /**
     * 
      * @Title: findAllByMap
      * @author ShaoYangYang
      * @date 2016年11月15日 下午7:05:15  
      * @Description: TODO 表连接查询评分需要的数据
      * @param @param map
      * @param @return      
      * @return List<AuditModelExt>
     */
    public List<AuditModelExt> findAllByMap(Map<String,Object> map){
    	return aduitQuotaMapper.findAllByMap(map);
    }
}
