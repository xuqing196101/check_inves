package system.service.sms.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import system.dao.sms.SmsRecordTempMapper;
import system.model.sms.SmsRecord;
import system.model.sms.SmsRecordTemp;
import system.service.sms.SmsRecordTempService;

import com.alibaba.fastjson.JSON;

import common.utils.SMSUtil;

@Service("smsRecordTempService")
public class SmsRecordTempServiceImpl implements SmsRecordTempService {

	@Autowired
	private SmsRecordTempMapper smsRecordTempMapper;
	
    /** 记录service  **/
    @Autowired
    private SynchRecordService  synchRecordService;
	/**
	 * 查询所有待发送短信
	 */
	@Override
	public List<SmsRecordTemp> findAll() {
		List<SmsRecordTemp> list = smsRecordTempMapper.findAll();
		return list;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		int i = smsRecordTempMapper.deleteByPrimaryKey(id);
		return i;
	}

	@Override
	public int insertSelective(SmsRecordTemp record) {
		record.setIsDeleted((short)0);
		record.setUpdatedAt(new Date());
		record.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		int i = smsRecordTempMapper.insertSelective(record);
		return i;
	}
	
	/**
	 * 导出待发送短信
	 */
	@Override
	public void exportSmsRecordTemp(String startTime, String endTime, Date date) {
        int sum = 0;
        List<SmsRecordTemp> smsRecordTempList = smsRecordTempMapper.selectByUpdateDate(startTime, endTime);
        if (smsRecordTempList != null && smsRecordTempList.size() > 0) {
            sum = sum + smsRecordTempList.size();
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SMS_RECORD_TEMP_PATH_FILENAME, 39), JSON.toJSONString(smsRecordTempList));
            for (SmsRecordTemp smsRecordTemp : smsRecordTempList) {
            	smsRecordTempMapper.deleteByPrimaryKey(smsRecordTemp.getId());
			}
        }
        synchRecordService.synchBidding(new Date(), sum + "",Constant.DATE_SYNCH_SMS_RECORD_TEMP, Constant.OPER_TYPE_EXPORT,Constant.SMS_RECORD_TEMP_COMMIT);
	}

	/**
	 * 导入待发送短信
	 */
	@Override
	public void importSmsRecordTemp(File file) {
        int num = 0;
        for (File file2 : file.listFiles()) {
            if (file2.getName().contains(FileUtils.SMS_RECORD_TEMP_PATH_FILENAME)) {
                List<SmsRecordTemp> smsRecordTempList = FileUtils.getBeans(file2, SmsRecordTemp.class);
                num += smsRecordTempList == null ? 0 : smsRecordTempList.size();
                for (SmsRecordTemp smsRecordTemp : smsRecordTempList) {
                	SmsRecordTemp smsRecordTemp22 = smsRecordTempMapper.selectByPrimaryKey(smsRecordTemp.getId());
                    if(smsRecordTemp22 != null){
                    	smsRecordTempMapper.updateByPrimaryKeySelective(smsRecordTemp);
                    }else{
                    	smsRecordTempMapper.insertSelective(smsRecordTemp);
                    }
                }
                //判断 如果是生产环境才会调用发短信方法
                String environment= PropUtil.getProperty("environment");
                if("1".equals(environment)){
                	List<SmsRecordTemp> list = smsRecordTempMapper.findAll();
                	for (SmsRecordTemp smsRecordTemp : list) {
                		//发送短信
                		SmsRecord smsRecord = new SmsRecord();
                		smsRecord.setSendLink(smsRecordTemp.getSendLink());
                		smsRecord.setOperator(smsRecordTemp.getOperator());
                		smsRecord.setSendContent(smsRecordTemp.getSendContent());
                		smsRecord.setRecipient(smsRecordTemp.getRecipient());
                		smsRecord.setReceiveNumber(smsRecordTemp.getReceiveNumber());
                		smsRecord.setOrgId(smsRecordTemp.getOrgId());
                		SMSUtil.sendMsg(smsRecord);
                		//发送完之后删除
                		smsRecordTempMapper.deleteByPrimaryKey(smsRecordTemp.getId());
                	}
                }
            }
        }
        synchRecordService.synchBidding(new Date(), num+"", Constant.DATE_SYNCH_SMS_RECORD_TEMP, Constant.OPER_TYPE_IMPORT, Constant.SMS_RECORD_TEMP_COMMIT_IMPORT);
	}
}
