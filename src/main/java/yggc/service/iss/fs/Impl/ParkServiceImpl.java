/**
 * 
 */
package yggc.service.iss.fs.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.iss.fs.ParkMapper;
import yggc.model.iss.fs.Park;
import yggc.service.iss.fs.ParkService;

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
	public List<Park> queryByList(Park park) {
		// TODO Auto-generated method stub
		return parkMapper.queryByList(park);
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
	
}
