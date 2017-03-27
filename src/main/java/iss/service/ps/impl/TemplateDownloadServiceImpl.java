/**
 * 
 */
package iss.service.ps.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.dao.ps.TemplateDownloadMapper;
import iss.model.ps.TemplateDownload;
import iss.service.ps.TemplateDownloadService;


/**
 * @Title:TemplateDownloadServiceImpl
 * @Description: 
 * @author ZhaoBo
 * @date 2017-1-5下午1:45:52
 */
@Service("templateDownloadService")
public class TemplateDownloadServiceImpl implements TemplateDownloadService {
	@Autowired
	private TemplateDownloadMapper templateDownloadMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return templateDownloadMapper.deleteByPrimaryKey(id);
	}

	
	@Override
	public int insertSelective(TemplateDownload templateDownload) {
		return templateDownloadMapper.insertSelective(templateDownload);
	}

	
	@Override
	public TemplateDownload selectByPrimaryKey(String id) {
		return templateDownloadMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(TemplateDownload templateDownload) {
		return templateDownloadMapper.updateByPrimaryKeySelective(templateDownload);
	}

	
	@Override
	public List<TemplateDownload> findDataByCondition(HashMap<String, Object> map) {
		return templateDownloadMapper.findDataByCondition(map);
	}
	
	@Override
	public List<TemplateDownload> findPublishedDataByCondition(HashMap<String, Object> map) {
		return templateDownloadMapper.findPublishedDataByCondition(map);
	}

}
