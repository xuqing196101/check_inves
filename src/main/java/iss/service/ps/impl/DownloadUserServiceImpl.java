package iss.service.ps.impl;

import iss.dao.ps.DownloadUserMapper;
import iss.model.ps.Article;
import iss.model.ps.DownloadUser;
import iss.service.ps.DownloadUserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;


/*
 *@Title:DownloadUserServiceImpl
 *@Description:
 *@author QuJie
 *@date 2016-9-9上午8:59:22
 */
@Service("downloadUserService")
public class DownloadUserServiceImpl implements DownloadUserService {
	
	@Autowired
	private DownloadUserMapper downloadUserMapper;
	
	/**
	 * 新增下载人信息
	 */
	@Override
	public void addDownloadUser(DownloadUser downloadUser) {
		downloadUserMapper.insertSelective(downloadUser);
	}
	
	/**
	 * 根据文章id查询下载人信息
	 */
	@Override
	public List<DownloadUser> selectByArticleId(String id) {
		return downloadUserMapper.selectByArticleId(id);
	}
}
