package system.service.sms;

import java.io.File;
import java.util.Date;
import java.util.List;

import ses.model.bms.User;
import system.model.sms.SmsRecord;


public interface SmsRecordService {

    /**
     * 
     * 
     * Description: 条件查询所有
     * 
     * @data 2017年12月6日
     * @param 
     * @return List<SmsRecord>
     */
    List<SmsRecord> findAll(SmsRecord record, Integer page, User user);
    
    
	/**
	 * 
	 * 
	 * Description: 插入非空数据
	 * 
	 * @data 2017年12月6日
	 * @param 
	 * @return void
	 */
	void insertSelective(SmsRecord record);
	
    /**
     * 
     * 
     * Description: 根据主键修改
     * 
     * @data 2017年12月6日
     * @param 
     * @return int
     */
    void updateByPrimaryKeySelective(SmsRecord record);
    
    /**
     * 
     * 
     * Description: 根据主键查询
     * 
     * @data 2017年12月6日
     * @param 
     * @return SmsRecord
     */
    SmsRecord selectByPrimaryKey(String id);
    
    /**
     * 
     * 
     * Description: 根据上行接口返回的短信id修改短信发送状态
     * 
     * @data 2017年12月11日
     * @param 
     * @return int
     */
    void updateBymsgId(SmsRecord record);
    
    /**
     * 
     * Description: 短信发送记录导出
     * 
     * @data 2018年1月4日
     * @param 
     * @return
     */
    void exportSmsRecord(String startTime, String endTime, Date date);
    
    /**
     * 
     * Description: 导入短信发送记录
     * 
     * @data 2018年1月4日
     * @param 
     * @return
     */
    void importSmsRecord(File file);
}
