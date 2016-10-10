package ses.service.sms;

import java.util.List;

import ses.model.ems.ExpExtractRecord;

/**
 * @Description:获取专家抽取记录
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月27日下午4:31:12
 * @since  JDK 1.7
 */
public interface ExpExtractRecordService {
	/**
	 * @Description:插入记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午4:32:28  
	 * @param @param record      
	 * @return void
	 */
	void insert(ExpExtractRecord record);

	/**
	 * @Description:获取集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午4:33:36  
	 * @param @return      
	 * @return List<ExpExtractRecord>
	 */
	List<ExpExtractRecord> listExtractRecord(ExpExtractRecord expExtractRecord,Integer pageNum);
	
	/**
	 * @Description:单个记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月29日 下午2:19:50  
	 * @param @param expExtractRecordService
	 * @param @return      
	 * @return ExpExtractRecord
	 */
	ExpExtractRecord showExpExtractRecord(ExpExtractRecordService expExtractRecordService);
	
}
