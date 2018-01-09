package system.service.sms;

import java.io.File;
import java.util.Date;
import java.util.List;

import system.model.sms.SmsRecordTemp;

public interface SmsRecordTempService {

    /**
     * 
     * Description: 查询所有待发送短信
     * 
     * @data 2018年1月5日
     * @param 
     * @return
     */
    List<SmsRecordTemp> findAll();
    
    /**
     * 
     * Description: 删除  修改删除标识
     * 
     * @data 2018年1月5日
     * @param 
     * @return
     */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
     * Description: 插入非空数据
     * 
     * @data 2018年1月5日
     * @param 
     * @return
     */
    int insertSelective(SmsRecordTemp record);
    
    /**
     * 
     * Description: 导出待发送短信
     * 
     * @data 2018年1月4日
     * @param 
     * @return
     */
    void exportSmsRecordTemp(String startTime, String endTime, Date date);
    
    /**
     * 
     * Description: 导入待发送短信
     * 
     * @data 2018年1月4日
     * @param 
     * @return
     */
    void importSmsRecordTemp(File file);
}
