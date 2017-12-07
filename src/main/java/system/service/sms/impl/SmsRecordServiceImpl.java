package system.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import system.dao.sms.SmsRecordMapper;
import system.model.sms.SmsRecord;
import system.service.sms.SmsRecordService;

@Service("smsRecordService")
public class SmsRecordServiceImpl implements SmsRecordService{

	@Autowired
	private SmsRecordMapper smsRecordMapper;
	
	@Override
	public List<SmsRecord> findAll(SmsRecord record, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<SmsRecord> list = smsRecordMapper.findAll(record);
		for (SmsRecord smsRecord : list) {
			DictionaryData dictionaryData = DictionaryDataUtil.findById(smsRecord.getSendLink() == null ? "" : smsRecord.getSendLink());
			smsRecord.setSendLink(dictionaryData == null ? "" : dictionaryData.getName());
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

}
