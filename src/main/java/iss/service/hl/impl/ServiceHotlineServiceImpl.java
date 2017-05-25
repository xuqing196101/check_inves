package iss.service.hl.impl;

import java.util.List;

import iss.dao.hl.ServiceHotlineMapper;
import iss.model.hl.ServiceHotline;
import iss.service.hl.ServiceHotlineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

/**
 * 
 * Description： 服务热线Service接口实现类
 * 
 * @author  zhang shubin
 * @version  
 * @since JDK1.7
 * @date 2017年5月25日 上午11:26:07 
 *
 */
@Service("/serviceHotlineService")
public class ServiceHotlineServiceImpl implements ServiceHotlineService{

	@Autowired
	private ServiceHotlineMapper serviceHotlineMapper;

	@Override
	public List<ServiceHotline> selectAll(ServiceHotline record,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ServiceHotline> list = serviceHotlineMapper.selectAll(record);
		return list;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return serviceHotlineMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(ServiceHotline record) {
		return serviceHotlineMapper.insertSelective(record);
	}

	@Override
	public ServiceHotline selectByPrimaryKey(String id) {
		return serviceHotlineMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ServiceHotline record) {
		return serviceHotlineMapper.updateByPrimaryKeySelective(record);
	}
	
}
