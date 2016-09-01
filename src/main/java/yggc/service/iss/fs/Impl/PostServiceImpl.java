/**
 * 
 */
package yggc.service.iss.fs.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.iss.fs.PostMapper;
import yggc.model.iss.fs.Post;
import yggc.service.iss.fs.PostService;

/**
 * <p>Title:PostServiceImpl </p>
 * <p>Description: 帖子管理服务接口实现类</p>
 * <p>Company: yggc </p> 
 * @author junjunjun1993
 * @date 2016-8-10下午6:45:22
 */
@Service("postService")
public class PostServiceImpl implements PostService{

	@Autowired
	private PostMapper postMapper;
	
	@Override
	public Integer queryByCount() {
		// TODO Auto-generated method stub
		return postMapper.queryByCount();
	}

	@Override
	public List<Post> queryByList(Post post) {
		// TODO Auto-generated method stub
		return postMapper.queryByList(post);
	}


	@Override
	public Post selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return postMapper.selectByPrimaryKey(id);
	}


	@Override
	public void deleteByPrimaryKey(Integer id) {
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
	public List<Post> selectByTopicID(Integer topicID) {
		// TODO Auto-generated method stub
		return postMapper.selectByTopicID(topicID);
	}


	@Override
	public List<Post> selectByParkID(Integer parkID) {
		// TODO Auto-generated method stub
		return postMapper.selectByParkID(parkID);
	}
	
	@Override
	public List<Post> selectListByParkID(Integer parkID) {
		// TODO Auto-generated method stub
		return postMapper.selectListByParkID(parkID);
	}
}
