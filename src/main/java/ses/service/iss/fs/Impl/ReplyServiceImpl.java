/**
 * 
 */
package ses.service.iss.fs.Impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.iss.fs.ReplyMapper;
import ses.model.iss.fs.Reply;
import ses.service.iss.fs.ReplyService;


/**
* @Title:ReplyServiceImpl 
* @Description: 回复管理实现类
* @author Peng Zhongjun
* @date 2016-9-7下午6:29:38
 */
@Service("replyService")
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyMapper replyMapper;
	@Override
	public BigDecimal queryByCount(Reply reply) {
		// TODO Auto-generated method stub
		return replyMapper.queryByCount(reply);
	}
	@Override
	public BigDecimal queryCountByParkId(String parkId) {
		// TODO Auto-generated method stub
		return replyMapper.queryCountByParkId(parkId);
	}
	@Override
	public BigDecimal queryCountByTopicId(String topicId) {
		// TODO Auto-generated method stub
		return replyMapper.queryCountByTopicId(topicId);
	}

	@Override
	public List<Reply> queryByList(Reply reply) {
		// TODO Auto-generated method stub
		return replyMapper.queryByList(reply);
	}


	@Override
	public Reply selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return replyMapper.selectByPrimaryKey(id);
	}


	@Override
	public void deleteByPrimaryKey(String id) {
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
	public List<Reply> selectByPostID(String postID) {
		// TODO Auto-generated method stub
		return replyMapper.selectByPostID(postID);
	}
	
	

}
