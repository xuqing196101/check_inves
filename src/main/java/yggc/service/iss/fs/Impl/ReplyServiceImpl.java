/**
 * 
 */
package yggc.service.iss.fs.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.iss.fs.ReplyMapper;
import yggc.model.iss.fs.Reply;
import yggc.service.iss.fs.ReplyService;

/**
 * <p>Title:ReplyServiceImpl </p>
 * <p>Description: 回复管理服务接口实现类</p>
 * <p>Company: yggc </p> 
 * @author junjunjun1993
 * @date 2016-8-10下午6:48:51
 */
@Service("replyService")
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyMapper replyMapper;
	@Override
	public Integer queryByCount() {
		// TODO Auto-generated method stub
		return replyMapper.queryByCount();
	}


	@Override
	public List<Reply> queryByList(Reply reply) {
		// TODO Auto-generated method stub
		return replyMapper.queryByList(reply);
	}


	@Override
	public Reply selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return replyMapper.selectByPrimaryKey(id);
	}


	@Override
	public void deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		replyMapper.deleteByPrimaryKey(id);
	}


	@Override
	public void insertSelective(Reply reply) {
		// TODO Auto-generated method stub
		replyMapper.insertSelective(reply);
	}


	@Override
	public void updateByPrimaryKeySelective(Reply reply) {
		// TODO Auto-generated method stub
		replyMapper.updateByPrimaryKeySelective(reply);
	}



	@Override
	public List<Reply> selectByPostID(Integer postID) {
		// TODO Auto-generated method stub
		return replyMapper.selectByPostID(postID);
	}

}
