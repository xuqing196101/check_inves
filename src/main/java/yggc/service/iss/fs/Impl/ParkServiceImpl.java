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
 * <p>Title:ParkServiceImpl </p>
 * <p>Description: 版块管理服务接口实现类</p>
 * <p>Company: yggc </p> 
 * @author：Peng Zhongjun
 * @date 2016-8-10下午6:32:05
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
