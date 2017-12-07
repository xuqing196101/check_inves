package system.controller.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import system.model.sms.SmsRecord;
import system.service.sms.SmsRecordService;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

/**
 * 
 * Description: 短信发送记录
 * 
 * @date 2017年12月6日
 * @since JDK1.7
 */
@Controller
@RequestMapping("/smsRecord")
public class SmsRecordController {

	@Autowired
	private SmsRecordService smsRecordService;
	
	/**
	 * 
	 * 
	 * Description: 短信发送记录查询
	 * 
	 * @data 2017年12月6日
	 * @param 
	 * @return String
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User user, Model model,@RequestParam(defaultValue = "1") Integer page, SmsRecord smsRecord){
		List<SmsRecord> list = smsRecordService.findAll(smsRecord, page);
		PageInfo<SmsRecord> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		//发送环节
		List<DictionaryData> sendLinkList = DictionaryDataUtil.find(64);
        model.addAttribute("sendLinkList",sendLinkList);
        model.addAttribute("smsRecord",smsRecord);
		return "system/sms/list";
	}
	
	@RequestMapping("/view")
	public String view(@CurrentUser User user, Model model, String id){
		SmsRecord smsRecord = smsRecordService.selectByPrimaryKey(id == null ? "" : id);
		model.addAttribute("smsRecord", smsRecord);
		return "system/sms/view";
	}
}
