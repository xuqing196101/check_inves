package app.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import app.model.AppInfo;
import app.service.AppInfoService;
import common.annotation.CurrentUser;
import common.constant.Constant;

/**
 * 
 * Description: App版本管理Controller
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Controller
@RequestMapping("/appInfo")
public class AppInfoController {

    //App版本管理
    @Autowired
    private AppInfoService appInfoService;

    /**
     * 
     * Description: App管理列表查询
     * 
     * @author zhang shubin
     * @data 2017年6月16日
     * @param 
     * @return
     */
    @RequestMapping("/list")
    public String list(@CurrentUser User user,AppInfo appInfo, Model model, @RequestParam(defaultValue="1")Integer page){
        List<AppInfo> list = appInfoService.list(appInfo, page);
        PageInfo<AppInfo> info = new PageInfo<>(list);
        model.addAttribute("info", info);
        model.addAttribute("appInfo", appInfo);
        return "ses/app/list";
    }

    /**
     * 
     * Description: 跳转添加页面
     * 
     * @author zhang shubin
     * @data 2017年6月16日
     * @param 
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model){
    	String uuid = UUID.randomUUID().toString().toUpperCase()
  				.replace("-", "");
  		model.addAttribute("businessId", uuid);
  		model.addAttribute("sysKey", Constant.APP_APK_SYS_KEY);
        return "ses/app/add";
    }
    
    /**
     * 
     * Description: 添加
     * 
     * @author zhang shubin
     * @data 2017年6月16日
     * @param 
     * @return
     */
    @RequestMapping("/add")
    public String add(AppInfo appInfo, Model model,HttpServletRequest request){
    	String businessId = request.getParameter("businessId");
    	System.out.println(businessId);
    	return "redirect:list.html";
    }
    
    @RequestMapping("/fallback")
    @ResponseBody
    public String fallback(String version){
    	AppInfo appInfo = appInfoService.selectByVersion(version);
    	Integer i = appInfoService.fallbackByVersion(appInfo.getCreatedAt());
    	if(i > 0){
    		return "success";
    	}else{
    		return "error";
    	}
    }
}
