package system.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import system.dao.sms.SmsRecordMapper;
import system.model.sms.SmsRecord;
import system.service.sms.SmsRecordService;

import com.github.pagehelper.PageHelper;

@Service("smsRecordService")
public class SmsRecordServiceImpl implements SmsRecordService{

	@Autowired
	private SmsRecordMapper smsRecordMapper;
	
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

}
