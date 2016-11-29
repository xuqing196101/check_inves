/**
 * 
 */
package iss.service.fs.impl;

import iss.dao.fs.ReplyMapper;
import iss.model.fs.Reply;
import iss.service.fs.ReplyService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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
	public List<Reply> queryByList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return replyMapper.queryByList(map);
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
	public List<Reply> selectByPostID(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return replyMapper.selectByPostID(map);
	}

	@Override
	public List<Reply> selectByReplyId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return replyMapper.selectByReplyId(map);
	}
	
	@Override
	public List<Reply> findAllUnReadReply() {
		// TODO Auto-generated method stub
		return replyMapper.findAllUnReadReply();
	}
	
	

}
