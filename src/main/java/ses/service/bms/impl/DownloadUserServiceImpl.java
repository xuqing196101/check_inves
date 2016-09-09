package ses.service.bms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DownloadUserMapper;
import ses.model.bms.DownloadUser;
import ses.service.bms.DownloadUserService;

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
}
