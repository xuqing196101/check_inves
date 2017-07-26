package sums.service.oc.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;
import sums.dao.oc.ComplaintMapper;
import sums.model.oc.Complaint;
import sums.service.oc.ComplaintService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import common.model.UploadFile;
import common.service.UploadService;

@Service
public class ComplaintServiceImpl implements ComplaintService {

	@Autowired
	private ComplaintMapper complaintMapper;
	
	/** 记录service  **/
    @Autowired
    private SynchRecordService  synchRecordService;
    
    /** 附件service **/
    @Autowired
    private UploadService uploadService;

	public Complaint selectByPrimaryKey(String id) {
		return complaintMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Complaint> selectAllComplaint(Complaint record,
			Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,
				Integer.parseInt(config.getString("pageSize")));
		return complaintMapper.selectAllComplaint(record);
	}

	@Override
	public void updateIsDeleteByPrimaryKey(String id) {
		complaintMapper.updateIsDeleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(Complaint record) {
		return complaintMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Complaint record) {
		return complaintMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Integer yzsc(String businessid, String typeId) {
		return complaintMapper.yzsc(businessid, typeId);

	}

	/**
	 * 导出网上投诉信息
	 */
	@Override
	public boolean exportComplaintService(String start, String end,Date synchDate) {
		boolean boo=false;
		//获取创建的产品
		List<Complaint> complaintCList= complaintMapper.selectByCreateDate(start, end);
		//获取修改的产品
		List<Complaint> complaintMList=complaintMapper.selectByUpdateDate(start, end);
		int sum=0;
		List<UploadFile> attachList = new ArrayList<>();
		if(complaintCList != null && complaintCList.size() > 0){
			sum=sum+complaintCList.size();
			//附件查询
			for (Complaint complaint : complaintCList) {
				List<UploadFile> fileList = uploadService.findBybusinessId(complaint.getId(), 2);
				attachList.addAll(fileList);
			}
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_ONLINE_COMPLAINTS_PATH_FILENAME, 25),JSON.toJSONString(complaintCList));
		}
		if(complaintMList!=null && complaintMList.size()>0){
			sum=sum+complaintMList.size();
			//附件查询
			for (Complaint complaint : complaintMList) {
				List<UploadFile> fileList = uploadService.findBybusinessId(complaint.getId(), 2);
				attachList.addAll(fileList);
			}
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_ONLINE_COMPLAINTS_PATH_FILENAME, 25),JSON.toJSONString(complaintMList));
		}
		if (attachList.size() > 0){
            FileUtils.writeFile(FileUtils.getInfoAttachmentFile(),JSON.toJSONString(attachList));
            String basePath = FileUtils.attachExportPath(2);
            if (StringUtils.isNotBlank(basePath)){
                OperAttachment.writeFile(basePath, attachList);
                synchRecordService.backupAttach(new Integer(attachList.size()).toString());
            }
        }
		synchRecordService.synchBidding(synchDate, sum+"", Constant.DATE_SYNCH_ONLINE_COMPLAINTS, Constant.OPER_TYPE_EXPORT, Constant.ONLINE_COMPLAINTS_COMMIT);
		boo=true;
		return boo;
	}
	
	 /***
     * 导入网上投诉文件数据
     */
	@Override
	public boolean importProduct(File file) {
		boolean boo=false;
		 List<Complaint> list = FileUtils.getBeans(file, Complaint.class); 
	        if (list != null && list.size() > 0){
	        	for (Complaint complaint : list) {
	        	Integer count=	complaintMapper.countById(complaint.getId());
	        	  if(count==0){
	        		  complaintMapper.insertSelective(complaint);
	        	  }else{
	        		  complaintMapper.updateByPrimaryKeySelective(complaint);
	        	  }
				}
	        	synchRecordService.synchBidding(new Date(), list.size()+"", Constant.DATE_SYNCH_ONLINE_COMPLAINTS, Constant.OPER_TYPE_IMPORT, Constant.ONLINE_COMPLAINTS_COMMIT_IMPORT);
	        }
	        boo=true;
		return boo;
	}

}
