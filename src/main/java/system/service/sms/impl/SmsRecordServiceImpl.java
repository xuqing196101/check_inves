package system.service.sms.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import system.dao.sms.SmsRecordMapper;
import system.model.sms.SmsRecord;
import system.service.sms.SmsRecordService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

@Service("smsRecordService")
public class SmsRecordServiceImpl implements SmsRecordService{

	@Autowired
	private SmsRecordMapper smsRecordMapper;
	
    /** 记录service  **/
    @Autowired
    private SynchRecordService  synchRecordService;
	
	@Override
	public List<SmsRecord> findAll(SmsRecord record, Integer page, User user) {
		Integer dataAccess = 0;
		if(user != null){
			dataAccess = user.getDataAccess();
		}
		if(dataAccess == 1){
			//所有
		}else if(dataAccess == 2){
			//本单位
			record.setOrgId(user.getOrg().getId());
		}else if(dataAccess == 3){
			//本人
			record.setLoginUser(user.getId());
		}
		//操作人
		String operator = record.getOperator();
		if(operator != null && !"".equals(operator)){
			List<String> list = smsRecordMapper.selectByName(operator);
			if(list != null && list.size() > 0){
				record.setOperatorList(list);
			}
		}
		//接收人
		String recipient = record.getRecipient();
		if(recipient != null && !"".equals(recipient)){
			List<String> list = smsRecordMapper.selectByName(recipient);
			if(list != null && list.size() > 0){
				record.setRecipientList(list);
			}
		}
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<SmsRecord> list = smsRecordMapper.findAll(record);
		for (SmsRecord smsRecord : list) {
			DictionaryData dictionaryData = DictionaryDataUtil.findById(smsRecord.getSendLink() == null ? "" : smsRecord.getSendLink());
			smsRecord.setSendLink(dictionaryData == null ? "" : dictionaryData.getName());
			String oper = smsRecordMapper.selectByUserId(smsRecord.getOperator() == null ? "" : smsRecord.getOperator());
			smsRecord.setOperator(oper == null ? "" : oper);
			String reci = smsRecordMapper.selectByUserId(smsRecord.getRecipient() == null ? "" : smsRecord.getRecipient());
			smsRecord.setRecipient(reci == null ? "" : reci);
		}
		return list;
	}

	@Override
	public void insertSelective(SmsRecord record) {
		smsRecordMapper.insertSelective(record);
	}

	@Override
	public void updateByPrimaryKeySelective(SmsRecord record) {
		smsRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SmsRecord selectByPrimaryKey(String id) {
		return smsRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateBymsgId(SmsRecord record) {
		smsRecordMapper.updateBymsgId(record);
	}

	/**
	 * 短信发送记录导出
	 */
	@Override
	public void exportSmsRecord(String startTime, String endTime, Date date) {
        int sum = 0;
        List<SmsRecord> smsRecordList = smsRecordMapper.selectByUpdateDate(startTime, endTime);
        if (smsRecordList != null && smsRecordList.size() > 0) {
            sum = sum + smsRecordList.size();
            // 专家抽取结果信息
            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.SMS_RECORD_PATH_FILENAME, 38), JSON.toJSONString(smsRecordList));
        }
        synchRecordService.synchBidding(new Date(), sum + "",Constant.DATE_SYNCH_SMS_RECORD, Constant.OPER_TYPE_EXPORT,Constant.SMS_RECORD_COMMIT);
	}

	/**
	 * 导入短信发送记录
	 */
	@Override
	public void importSmsRecord(File file) {
        int num = 0;
        for (File file2 : file.listFiles()) {
            if (file2.getName().contains(FileUtils.SMS_RECORD_PATH_FILENAME)) {
                List<SmsRecord> smsRecordList = FileUtils.getBeans(file2, SmsRecord.class);
                num += smsRecordList == null ? 0 : smsRecordList.size();
                for (SmsRecord smsRecord : smsRecordList) {
                	SmsRecord smsRecord2 = smsRecordMapper.selectByPrimaryKey(smsRecord.getId());
                    if(smsRecord2 != null){
                    	smsRecordMapper.updateByPrimaryKeySelective(smsRecord);
                    }else{
                    	smsRecordMapper.insertSelective(smsRecord);
                    }
                }
            }
        }
        synchRecordService.synchBidding(new Date(), num+"", Constant.DATE_SYNCH_SMS_RECORD, Constant.OPER_TYPE_IMPORT, Constant.SMS_RECORD_COMMIT_IMPORT);
	}

}
