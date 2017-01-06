/**
 * 
 */
package iss.service.ps.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.dao.ps.DataDownloadMapper;
import iss.model.ps.DataDownload;
import iss.service.ps.DataDownloadService;

/**
 * @Title:DataDownloadServiceImpl
 * @Description: 
 * @author ZhaoBo
 * @date 2017-1-5下午1:45:52
 */
@Service("dataDownloadService")
public class DataDownloadServiceImpl implements DataDownloadService {
	@Autowired
	private DataDownloadMapper dataDownloadMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return dataDownloadMapper.deleteByPrimaryKey(id);
	}

	
	@Override
	public int insertSelective(DataDownload dataDownload) {
		return dataDownloadMapper.insertSelective(dataDownload);
	}

	
	@Override
	public DataDownload selectByPrimaryKey(String id) {
		return dataDownloadMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(DataDownload dataDownload) {
		return dataDownloadMapper.updateByPrimaryKeySelective(dataDownload);
	}

	
	@Override
	public List<DataDownload> findDataByCondition(HashMap<String, Object> map) {
		return dataDownloadMapper.findDataByCondition(map);
	}
	
	@Override
	public List<DataDownload> findPublishedDataByCondition(HashMap<String, Object> map) {
		return dataDownloadMapper.findPublishedDataByCondition(map);
	}

}
