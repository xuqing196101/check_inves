/**
 * 
 */
package iss.service.fs.impl;

import iss.dao.fs.PostMapper;
import iss.model.fs.Post;
import iss.service.fs.PostService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
* @Title:PostServiceImpl 
* @Description: 帖子管理实现类
* @author Peng Zhongjun
* @date 2016-9-7下午6:29:01
 */
@Service("postService")
public class PostServiceImpl implements PostService{

	@Autowired
	private PostMapper postMapper;
	
	@Override
	public BigDecimal queryByCount(Post post) {
		// TODO Auto-generated method stub
		return postMapper.queryByCount(post);
	}

	@Override
	public List<Post> queryByList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return postMapper.queryByList(map);
	}


	@Override
	public Post selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return postMapper.selectByPrimaryKey(id);
	}


	@Override
	public void deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		postMapper.deleteByPrimaryKey(id);
	}


	@Override
	public void insertSelective(Post post) {
		// TODO Auto-generated method stub
		postMapper.insertSelective(post);
	}


	@Override
	public void updateByPrimaryKeySelective(Post post) {
		// TODO Auto-generated method stub
		postMapper.updateByPrimaryKeySelective(post);
	}


	@Override
	public List<Post> selectByTopicID(String topicID) {
		// TODO Auto-generated method stub
		return postMapper.selectByTopicID(topicID);
	}


	@Override
	public List<Post> selectByParkID(String parkID) {
		// TODO Auto-generated method stub
		return postMapper.selectByParkID(parkID);
	}
	
	@Override
	public List<Post> queryHotPost() {
		// TODO Auto-generated method stub
		return postMapper.queryHotPost();
	}
}
