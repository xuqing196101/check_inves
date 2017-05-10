/**
 * 
 */
package iss.service.ps.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

import bss.model.pms.UpdateFiled;

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
	/**文件**/
	@Autowired
    private UploadService uploadService;
	 /**
     * 同步service
     */
    @Autowired
    private SynchRecordService recordService;
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

    /***
     * 实现 资料导出
     */
	@Override
	public boolean exportData(String start, String end, Date date) {
		// TODO Auto-generated method stub
		List<DataDownload> list=dataDownloadMapper.selectByUpdatedAt(start, end);
		List<UploadFile> uploadList=new ArrayList<UploadFile>();
		if(list!=null && list.size()>0){
			for (DataDownload data : list) {
				//查询文件路径
				List<UploadFile> fileList = uploadService.findBybusinessId(data.getId(),Constant.TENDER_SYS_KEY);
				if(fileList!=null && fileList.size()>0){
					uploadList.addAll(fileList);
				}
			}
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_DATA_DOWNLOAD_FILENAME, 17),JSON.toJSONString(list));
			 //同步附件
	        if (uploadList != null && uploadList.size() > 0){
	            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_FILE_DATA_DOWNLOAD_FILENAME, 17),JSON.toJSONString(uploadList));
	            String basePath = FileUtils.attachExportPath(18);
	            if (StringUtils.isNotBlank(basePath)){
	                OperAttachment.writeFile(basePath, uploadList);
	                recordService.synchBidding(date, new Integer(uploadList.size()).toString(), synchro.util.Constant.DATA_TYPE_ATTACH_CODE, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.COMMIT_FILE_SYNCH_DATA);
	            }
	        }
		}
		if(list!=null ){
		recordService.synchBidding(date, new Integer(list.size()).toString(), synchro.util.Constant.SYNCH_DATA, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.COMMIT_SYNCH_DATA);
		}
		return false;
	}

    /**
     * 实现资料导入
     */
	@Override
	public boolean importDate(File file) {
		// TODO Auto-generated method stub
		List<DataDownload> list=FileUtils.getBeans(file, DataDownload.class);
		if(list!=null && list.size()>0){
			for (DataDownload dataDownload : list) {
				Integer count=dataDownloadMapper.countByPrimaryKey(dataDownload.getId());
				if(count!=null && count>0){
					dataDownloadMapper.updateByPrimaryKeySelective(dataDownload);
				}else{
					dataDownloadMapper.insertSelective(dataDownload);
				}
			}
			recordService.synchBidding(new Date(), list.size()+"", synchro.util.Constant.SYNCH_DATA, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.IMPORT_FILE_SYNCH_DATA);
		}
		return false;
	}

}
