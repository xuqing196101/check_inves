package system.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import system.model.sms.SmsRecordTemp;

public interface SmsRecordTempMapper {
	
    /**
     * 
     * Description: 删除  修改删除标识
     * 
     * @data 2018年1月5日
     * @param 
     * @return
     */
    int deleteByPrimaryKey(String id);

    int insert(SmsRecordTemp record);

    /**
     * 
     * Description: 插入非空数据
     * 
     * @data 2018年1月5日
     * @param 
     * @return
     */
    int insertSelective(SmsRecordTemp record);

    SmsRecordTemp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsRecordTemp record);

    int updateByPrimaryKey(SmsRecordTemp record);
    
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
     * Description: 根据修改时间查询
     * 
     * @data 2018年1月4日
     * @param 
     * @return
     */
    List<SmsRecordTemp> selectByUpdateDate(@Param("start")String start,@Param("end")String end);
}