package ses.service.oms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.oms.OrgnizationMapper;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.util.PropertiesUtil;

@Service("orgnizationService")
public class OrgnizationServiceImpl implements OrgnizationServiceI{
	@Autowired
	private OrgnizationMapper orgniztionMapper;

	@Override
	public List<Orgnization> findOrgnizationList(HashMap<String, Object> map) {
		return orgniztionMapper.findOrgnizationList(map);
	}

	@Override
	public int saveOrgnization(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return orgniztionMapper.saveOrgnization(map);
	}

	@Override
	public int updateOrgnization(HashMap<String, Object> map) {
		return orgniztionMapper.updateOrgnization(map);
	}
	/**
	 * 多对多关联查询
	 */
	@Override
	public List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return orgniztionMapper.findPurchaseOrgList(map);
	}

	@Override
	public int delOrgnizationByid(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return orgniztionMapper.delOrgnizationByid(map);
	}

	@Override
	public int updateOrgnizationById(Orgnization orgnization) {
		// TODO Auto-generated method stub
		return orgniztionMapper.updateOrgnizationById(orgnization);
	}

	@Override
	public List<Orgnization> findByName(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return orgniztionMapper.findByName(map);
	}

}
