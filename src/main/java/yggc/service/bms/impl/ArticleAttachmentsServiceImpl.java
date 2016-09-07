package yggc.service.bms.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.bms.ArticleAttachmentsMapper;
import yggc.model.bms.ArticleAttachments;
import yggc.service.bms.ArticleAttachmentsService;

/*
 *@Title:ArticleAttachmentsServiceImpl
 *@Description:信息附件service层实现类
 *<p>Company:yggc</p>
 *@author QuJie
 *@date 2016-9-7上午10:10:58
 */
@Service("articleAttachmentsService")
public class ArticleAttachmentsServiceImpl implements ArticleAttachmentsService {
	
	@Autowired
	private ArticleAttachmentsMapper articleAttachmentsMapper;
	
	/**
	 * 新增一个附件
	 */
	@Override
	public int insert(ArticleAttachments record) {
		return articleAttachmentsMapper.insertSelective(record);
	}
}
