/**
 * 
 */
package iss.service.fs.impl;

import iss.dao.fs.ParkMapper;
import iss.model.fs.Park;
import iss.service.fs.ParkService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;



/**
* @Title:ParkServiceImpl 
* @Description: 版块管理实现类
* @author Peng Zhongjun
* @date 2016-9-7下午6:28:24
 */
@Service("parkService")
public class ParkServiceImpl implements ParkService{

	@Autowired
	private ParkMapper parkMapper;
	
	@Override
	public Integer queryByCount() {
		// TODO Auto-generated method stub
		return parkMapper.queryByCount();
	}

	@Override
	public List<Park> queryByList(Park park,Integer page) {
		// TODO Auto-generated method stub
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return parkMapper.queryByList(park,page);
	}
	

	@Override
	public Park selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return parkMapper.selectByPrimaryKey(id);
	}


	@Override
	public void deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		parkMapper.deleteByPrimaryKey(id);
	}


	@Override
	public void insertSelective(Park park) {
		// TODO Auto-generated method stub
		parkMapper.insertSelective(park);
	}


	@Override
	public void updateByPrimaryKeySelective(Park park) {
		// TODO Auto-generated method stub
		parkMapper.updateByPrimaryKeySelective(park);
	}


	@Override
	public List<Park> getAll(Park park) {
		// TODO Auto-generated method stub
		return parkMapper.getAll(park);
	}

	@Override
	public List<Park> selectParkListByUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return parkMapper.selectParkListByUser(map);
	}

}
