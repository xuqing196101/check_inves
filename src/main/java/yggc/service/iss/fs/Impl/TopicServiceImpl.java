/**
 * 
 */
package yggc.service.iss.fs.Impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.iss.fs.TopicMapper;
import yggc.model.iss.fs.Topic;
import yggc.service.iss.fs.TopicService;

/**
 * <p>Title:TopicServiceImpl </p>
 * <p>Description:主题管理服务接口实现类 </p>
 * <p>Company: yggc </p> 
 * @author Peng Zhongjun
 * @date 2016-8-10下午6:55:33
 */
@Service("topicService")
public class TopicServiceImpl implements TopicService{

	@Autowired
	private TopicMapper topicMapper;
	
	@Override
	public BigDecimal queryByCount(Topic topic) {
		// TODO Auto-generated method stub
		return topicMapper.queryByCount(topic);
	}

	@Override
	public List<Topic> queryByList(Topic topic) {
		// TODO Auto-generated method stub
		return topicMapper.queryByList(topic);
	}


	@Override
	public Topic selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return topicMapper.selectByPrimaryKey(id);
	}


	@Override
	public void deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		topicMapper.deleteByPrimaryKey(id);
	}


	@Override
	public void insertSelective(Topic topic) {
		// TODO Auto-generated method stub
		topicMapper.insertSelective(topic);
	}


	@Override
	public void updateByPrimaryKeySelective(Topic topic) {
		// TODO Auto-generated method stub
		topicMapper.updateByPrimaryKeySelective(topic);
	}


	@Override
	public List<Topic> selectByParkID(String parkID) {
		// TODO Auto-generated method stub
		return topicMapper.selectByParkID(parkID);
	}

}
