package iss.service.fs.impl;

import iss.dao.fs.PostAttachmentsMapper;
import iss.model.fs.PostAttachments;
import iss.service.fs.PostAttachmentsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
* @Title:PostAttachmentsServiceImpl 
* @Description: 帖子附件实现类
* @author Peng Zhongjun
* @date 2016-10-14下午4:36:40
 */
@Service("postAttachmentsService")
public class PostAttachmentsServiceImpl implements PostAttachmentsService {
	
	@Autowired
	private PostAttachmentsMapper postAttachmentsMapper;


	@Override
	public PostAttachments selectPostAttaByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return postAttachmentsMapper.selectPostAttaByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(PostAttachments record) {
		// TODO Auto-generated method stub
		return postAttachmentsMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public List<PostAttachments> selectAllPostAttachments(String id) {
		// TODO Auto-generated method stub
		return postAttachmentsMapper.selectAllPostAttachments(id);
	}


	@Override
	public void softDeleteAtta(String id) {
		// TODO Auto-generated method stub
		postAttachmentsMapper.softDeleteAtta(id);
	}


	@Override
	public void insertSelective(PostAttachments record) {
		// TODO Auto-generated method stub
		postAttachmentsMapper.insertSelective(record);
	}
	

	
}
