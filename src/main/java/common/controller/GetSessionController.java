package common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.util.SessionListener;

import com.alibaba.fastjson.JSON;
import common.annotation.CurrentUser;

/**
 * 
 * Description: 获取当前登录的session
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Controller
@RequestMapping("/getSession")
public class GetSessionController {

	/**
	 * 
	 * Description: 获取到当前登录人的id和sessionMap
	 * 
	 * @author zhang shubin
	 * @data 2017年9月15日
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSessionMap")
	public String getSessionMap(@CurrentUser User user){
		Map<String, Object> map = new HashMap<String, Object>();
		if(user != null){
			map.put("userId", user.getId());
		}else{
			map.put("userId", "");
		}
		map.put("sessionMap", SessionListener.sessionMap);
		return JSON.toJSONString(map);
	}
}
