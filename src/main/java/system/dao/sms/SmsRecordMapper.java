package system.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import system.model.sms.SmsRecord;


public interface SmsRecordMapper {
	
	/**
	 * 
	 * 
	 * Description: 删除  修改删除标识
	 * 
	 * @data 2017年12月6日
	 * @param 
	 * @return void
	 */
    void deleteByPrimaryKey(String id);

    int insert(SmsRecord record);

    /**
     * 
     * 
     * Description: 插入非空数据
     * 
     * @data 2017年12月6日
     * @param 
     * @return int
     */
    int insertSelective(SmsRecord record);

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
     * Description: 根据主键修改
     * 
     * @data 2017年12月6日
     * @param 
     * @return int
     */
    int updateByPrimaryKeySelective(SmsRecord record);

    int updateByPrimaryKey(SmsRecord record);
    
    /**
     * 
     * 
     * Description: 条件查询所有
     * 
     * @data 2017年12月6日
     * @param 
     * @return List<SmsRecord>
     */
    List<SmsRecord> findAll(SmsRecord record);
    
    /**
     * 
     * 
     * Description: 根据用户名模糊查询
     * 
     * @data 2017年12月7日
     * @param 
     * @return List<String>
     */
    List<String> selectByName(@Param("name") String name);
    
    /**
     * 
     * 
     * Description: 根据id查询用户名
     * 
     * @data 2017年12月7日
     * @param 
     * @return String
     */
    String selectByUserId(@Param("id") String id);
    
    /**
     * 
     * 
     * Description: 根据上行接口返回的短信id修改短信发送状态
     * 
     * @data 2017年12月11日
     * @param 
     * @return int
     */
    int updateBymsgId(SmsRecord record);
}