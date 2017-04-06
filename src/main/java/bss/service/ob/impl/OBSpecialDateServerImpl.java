package bss.service.ob.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;

import com.alibaba.fastjson.JSON;

import bss.dao.ob.OBSpecialDateMapper;
import bss.model.ob.OBSpecialDate;
import bss.service.ob.OBSpecialDateServer;
/**
 * 实现竞价特殊日期
 * @author YangHongliang
 *
 */
@Service
public class OBSpecialDateServerImpl implements OBSpecialDateServer {
	
	@Autowired
    private OBSpecialDateMapper OBSpecialDateMapper;
	  /** 记录service  **/
    @Autowired
    private SynchRecordService  SynchRecordService;
	/**
	 * 实现根据时间获取特殊日期 并保存
	 */
	@Override
	public boolean exportSpecialDate(String startTime, String endTime,
			Date synchDate) {
		boolean boo=false;
		int sum=0;
		List<OBSpecialDate> createList=OBSpecialDateMapper.selectByCreateDate(startTime, endTime);
		List<OBSpecialDate> updateList=OBSpecialDateMapper.selectByUpdateDate(startTime, endTime);
		if(createList!=null&& createList.size()>0){
			sum=sum+createList.size();
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_OB_SPECIAL_DATE_FILENAME, 5),JSON.toJSONString(createList));
		}
		if(updateList!=null&& updateList.size()>0){
			sum=sum+updateList.size();
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.M_OB_SPECIAL_DATE_FILENAME, 5),JSON.toJSONString(updateList));
		}
		SynchRecordService.synchBidding(synchDate, sum+"", Constant.DATE_SYNCH_BIDDING_SPECIAL_DATE, Constant.OPER_TYPE_EXPORT, Constant.OB_SPECIAL_DATE_COMMIT);
		boo=true;
		return boo;
	}
}
