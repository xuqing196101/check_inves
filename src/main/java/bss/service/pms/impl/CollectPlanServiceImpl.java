package bss.service.pms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.pms.CollectPlanMapper;
import bss.model.pms.CollectPlan;
import bss.service.pms.CollectPlanService;
/**
 * 
 * @Title: CollectPlanServiceImpl
 * @Description: 采购计划汇总业务实现类
 * @author Li Xiaoxiao
 * @date  2016年9月20日,下午3:18:32
 *
 */
@Service
public class CollectPlanServiceImpl implements CollectPlanService{

	@Autowired
	private CollectPlanMapper collectPlanMapper;
	@Override
	public void add(CollectPlan collectPlan) {
		collectPlanMapper.insertSelective(collectPlan);
		
	}
	@Override
	public List<CollectPlan> queryCollect(CollectPlan collectPlan, Integer page) {
		List<CollectPlan> list = collectPlanMapper.query(collectPlan);
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return list;
	}
	@Override
	public CollectPlan queryById(String id) {
	 
		return collectPlanMapper.selectByPrimaryKey(id);
	}
	@Override
	public void update(CollectPlan collectPlan) {
		// TODO Auto-generated method stub
		collectPlanMapper.updateByPrimaryKeySelective(collectPlan);
	}
	@Override
	public Integer getMax() {
		 
		return collectPlanMapper.getMax();
	}

}
