package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.PurchaseDepMapper;
import ses.util.PropertiesUtil;
import bss.dao.ppms.BidAnnouncementMapper;
import bss.dao.ppms.ProjectMapper;
import bss.model.ppms.BidAnnouncement;
import bss.model.ppms.Project;
import bss.service.ppms.BidAnnouncementService;
import bss.service.ppms.ProjectService;

import com.github.pagehelper.PageHelper;

/**
* @Title:BidAnnouncementServiceImpl 
* @Description: 招标公告实现类
* @author Peng Zhongjun
* @date 2016-10-9下午8:22:25
 */
@Service("bidAnnouncement")
public class BidAnnouncementServiceImpl implements BidAnnouncementService {
	@Autowired
	private BidAnnouncementMapper bidAnnouncementMapper;
	
	@Override
	public BidAnnouncement selectBidAnnouncementById(String id) {
		// TODO Auto-generated method stub
		return bidAnnouncementMapper.selectBidAnnouncementById(id);
	}


	@Override
	public void insertSelective(BidAnnouncement bidAnnouncement) {
		// TODO Auto-generated method stub
		bidAnnouncementMapper.insertSelective(bidAnnouncement);
	}
	
}
