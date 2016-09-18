/**
 * 
 */
package iss.service.fs.impl;

import iss.dao.fs.TopicMapper;
import iss.model.fs.Topic;
import iss.service.fs.TopicService;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;



/**
* @Title:TopicServiceImpl 
* @Description: 主题管理实现类
* @author Peng Zhongjun
* @date 2016-9-7下午6:30:06
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
	public List<Topic> queryByList(Topic topic,Integer page) {
		// TODO Auto-generated method stub
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return topicMapper.queryByList(topic,page);
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


	@Override
	public List<Topic> getAll(Topic topic) {
		// TODO Auto-generated method stub
		return topicMapper.getAll(topic);
	}

}
