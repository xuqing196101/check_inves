package iss.service.ps.impl;

import iss.dao.ps.DownloadUserMapper;
import iss.model.ps.Article;
import iss.model.ps.DownloadUser;
import iss.service.ps.DownloadUserService;

import java.util.List;
import java.util.Map;

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
	public List<DownloadUser> selectByArticleId(Map<String,Object> map) {
		return downloadUserMapper.selectByArticleId(map);
	}
	
	/**
	 * 根据id删除下载人信息
	 */
	@Override
	public void deleteDownloadUserById(String id) {
		downloadUserMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据id查找下载人
	 */
	@Override
	public DownloadUser selectDownloadUserById(String id) {
		return downloadUserMapper.selectDownloadByPrimaryKey(id);
	}
	
	/**
	 * 根据条件查询
	 */
	@Override
	public List<DownloadUser> selectDownloadUserByParam(
			DownloadUser downloadUser) {
		return downloadUserMapper.selectDownloadUserByParam(downloadUser);
	}
}
