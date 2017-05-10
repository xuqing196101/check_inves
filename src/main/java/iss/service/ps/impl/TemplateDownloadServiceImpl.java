/**
 * 
 */
package iss.service.ps.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.constraints.Pattern.Flag;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.sms.SMSProductCheckRecord;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
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
	
	/** 文件上传接口 **/
	@Autowired
	private UploadService uploadService;
	
	/**导入导出记录**/
    @Autowired
    private SynchRecordService recordService;
	
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


	/**
	 * 
	* @Title: exportTemplateDownload 
	* @Description: 导出模板管理
	* @author Easong
	* @param @param start
	* @param @param end
	* @param @param synchDate
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public boolean exportTemplateDownload(String start, String end,
			Date synchDate) {
		boolean flag = false;
		// 导出模板下载
		List<TemplateDownload> list = templateDownloadMapper.exportTemplateDownload(start, end);
		// 定义文件集合
		List<UploadFile> fileList = new ArrayList<UploadFile>();
		// 遍历集合
		if(list != null && list.size() > 0){
			for (TemplateDownload td : list) {
				// 获取上传附件
				List<UploadFile> up = uploadService.findBybusinessId(td.getId(), Constant.TENDER_SYS_KEY);
				// 将文件封装到集合中
				fileList.addAll(up);
			}
			// 将模板管理列表写入到文件
			FileUtils.writeFile(FileUtils.getExporttFile(
					FileUtils.C_TEMPLATE_DOWNLOAD_FILENAME, 19), JSON
					.toJSONString(list));
			// 存储 同步记录
			recordService.synchBidding(synchDate,
					new Integer(list.size()).toString(),
					synchro.util.Constant.SYNCH_TEMPLATE_DOWNLOAD,
					synchro.util.Constant.OPER_TYPE_EXPORT,
					synchro.util.Constant.COMMIT_SYNCH_TEMPLATE_DOWNLOAD);
		}
		// 如果没有数据
		if(list != null && list.size() == 0){
			// 存储 同步记录
			recordService.synchBidding(synchDate,
					new Integer(list.size()).toString(),
					synchro.util.Constant.SYNCH_TEMPLATE_DOWNLOAD,
					synchro.util.Constant.OPER_TYPE_EXPORT,
					synchro.util.Constant.COMMIT_SYNCH_TEMPLATE_DOWNLOAD);
		}
		if(list == null){
			// 存储 同步记录
			recordService.synchBidding(synchDate,
					"0",
					synchro.util.Constant.SYNCH_TEMPLATE_DOWNLOAD,
					synchro.util.Constant.OPER_TYPE_EXPORT,
					synchro.util.Constant.COMMIT_SYNCH_TEMPLATE_DOWNLOAD);
		}
		
		// 同步附件
		if(fileList != null && fileList.size() > 0){
			// 将附件写入文件
			FileUtils.writeFile(FileUtils.getExporttFile(
					FileUtils.C_FILE_TEMPLATE_DOWNLOAD_FILENAME, 19), JSON
					.toJSONString(fileList));
			String basePath = FileUtils.attachExportPath(20);
			if (StringUtils.isNotBlank(basePath)) {
				OperAttachment.writeFile(basePath, fileList);
				recordService.synchBidding(synchDate,
						new Integer(fileList.size()).toString(),
						synchro.util.Constant.DATA_TYPE_ATTACH_CODE,
						synchro.util.Constant.OPER_TYPE_EXPORT,
						synchro.util.Constant.CREATED_COMMIT_ATTACH);
			}
		}
		// 如果没有数据
		if(list != null && list.size() == 0){
			// 存储 同步记录
			recordService.synchBidding(synchDate,
					new Integer(fileList.size()).toString(),
					synchro.util.Constant.DATA_TYPE_ATTACH_CODE,
					synchro.util.Constant.OPER_TYPE_EXPORT,
					synchro.util.Constant.CREATED_COMMIT_ATTACH);
		}
		if(list == null){
			// 存储 同步记录
			recordService.synchBidding(synchDate,
					"0",
					synchro.util.Constant.DATA_TYPE_ATTACH_CODE,
					synchro.util.Constant.OPER_TYPE_EXPORT,
					synchro.util.Constant.CREATED_COMMIT_ATTACH);
		}
		flag = true;
		return flag;
	}


	/**
	 * 
	* @Title: importTemplateDownload 
	* @Description: 门户模板管理导出
	* @author Easong
	* @param @param file
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public boolean importTemplateDownload(File file) {
		boolean flag = false;
		// 读取文件内容转成pojo
		List<TemplateDownload> list = FileUtils.getBeans(file, TemplateDownload.class); 
		if(list != null && list.size() > 0 ){
			for (TemplateDownload templateDownload : list) {
				// 如果模板存在则更新模板
				TemplateDownload selectByPrimaryKey = templateDownloadMapper.selectByPrimaryKey(templateDownload.getId());
				if(selectByPrimaryKey != null){
					templateDownloadMapper.updateByPrimaryKeySelective(templateDownload);
				}else {
					// 如果模板不存在则新增模板
					templateDownloadMapper.insertSelective(templateDownload);
				}
			}
			recordService.synchBidding(new Date(),
					new Integer(list.size()).toString(),
					synchro.util.Constant.SYNCH_TEMPLATE_DOWNLOAD,
					synchro.util.Constant.OPER_TYPE_IMPORT,
					synchro.util.Constant.IMPORT_SYNCH_TEMPLATE_DOWNLOAD);
		}
		// 如果没有数据
		if(list != null && list.size() == 0){
			// 存储 同步记录
			recordService.synchBidding(new Date(),
					new Integer(list.size()).toString(),
					synchro.util.Constant.SYNCH_TEMPLATE_DOWNLOAD,
					synchro.util.Constant.OPER_TYPE_IMPORT,
					synchro.util.Constant.IMPORT_SYNCH_TEMPLATE_DOWNLOAD);
		}
		if(list == null){
			// 存储 同步记录
			recordService.synchBidding(new Date(),
					"0",
					synchro.util.Constant.SYNCH_TEMPLATE_DOWNLOAD,
					synchro.util.Constant.OPER_TYPE_IMPORT,
					synchro.util.Constant.IMPORT_SYNCH_TEMPLATE_DOWNLOAD);
		}
		flag = true;
		return flag;
	}
	

}
