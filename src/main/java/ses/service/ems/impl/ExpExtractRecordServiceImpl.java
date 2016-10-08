/**
 * 
 */
package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpExtractRecordMapper;
import ses.model.ems.ExpExtractRecord;
import ses.service.sms.ExpExtractRecordService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午1:45:37
 * @since  JDK 1.7
 */
@Service
public class ExpExtractRecordServiceImpl implements ExpExtractRecordService {
	@Autowired
	ExpExtractRecordMapper expExtractRecordMapper;

	/**
	 * @Description:插入记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午4:32:28  
	 * @param @param record      
	 * @return void
	 */
	@Override
	public void insert(ExpExtractRecord record) {
		expExtractRecordMapper.insert(record);
	}

	/**
	 * @Description:集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午4:32:28  
	 * @param @param record      
	 * @return void
	 */
	@Override
	public List<ExpExtractRecord> listExtractRecord(
			ExpExtractRecord expExtractRecord) {
		return expExtractRecordMapper.list(expExtractRecord);
	}

	/**
	 * @Description:单个记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月29日 下午2:19:50  
	 * @param @param expExtractRecordService
	 * @param @return      
	 * @return ExpExtractRecord
	 */
	@Override
	public ExpExtractRecord showExpExtractRecord(
			ExpExtractRecordService expExtractRecordService) {
		return expExtractRecordMapper.selectByPrimaryKey("21321");
	}

}
