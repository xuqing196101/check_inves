package app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import app.dao.app.AppInfoMapper;
import app.model.AppInfo;
import app.service.AppInfoService;

/**
 * 
 * Description: App版本管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service
public class AppInfoServiceImpl implements AppInfoService{

    //App版本管理
    @Autowired
    private AppInfoMapper appInfoMapper;
    
    @Override
    public List<AppInfo> list(AppInfo appInfo, Integer page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<AppInfo> list = appInfoMapper.list(appInfo);
        return list;
    }

    @Override
    public AppInfo selectByVersion(String version) {
        return appInfoMapper.selectByVersion(version);
    }

    @Override
    public Integer fallbackByVersion(Date createAt) {
        return appInfoMapper.fallbackByVersion(createAt);
    }

}
