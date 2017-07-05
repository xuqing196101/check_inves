package app.controller;

import java.util.Date;
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
        //声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
            List<AppInfo> list = appInfoService.list(appInfo, page);
            PageInfo<AppInfo> info = new PageInfo<>(list);
            model.addAttribute("info", info);
            model.addAttribute("appInfo", appInfo);
            model.addAttribute("authType", authType);
        }
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
    public String toAdd(@CurrentUser User user,Model model){
        if(null != user && "4".equals(user.getTypeName())){
            String uuid = UUID.randomUUID().toString().toUpperCase()
                  .replace("-", "");
            model.addAttribute("businessId", uuid);
            model.addAttribute("sysKey", Constant.APP_APK_SYS_KEY);
            return "ses/app/add";
        }
        return "";
    }
    
    /**
     * 
     * Description: 新增
     * 
     * @author zhang shubin
     * @data 2017年6月16日
     * @param 
     * @return
     */
    @RequestMapping("/add")
    public String add(@CurrentUser User user,AppInfo appInfo, Model model,HttpServletRequest request){
        if(null != user && "4".equals(user.getTypeName())){
            String businessId = request.getParameter("businessId");
            String path = appInfoService.selectPathByBusinessId(businessId);
            boolean flag = true;
            if(appInfo.getVersion() == null || ("").equals(appInfo.getVersion())){
                flag = false;
                model.addAttribute("error_version","版本号不能为空");
            }else if (appInfoService.selectByVersion(appInfo.getVersion()) != null){
                flag = false;
                model.addAttribute("error_version","版本号已存在");
            }
            if(path.equals("")){
                flag = false;
                model.addAttribute("errorShangchuan","请上传新版本");
            }
            if(flag == true){
                appInfo.setPath(path);
                appInfo.setCreatedAt(new Date());
                appInfo.setRemark(businessId);
                appInfoService.add(appInfo);
                return "redirect:list.html";
            }else{
                model.addAttribute("appInfo",appInfo);
                model.addAttribute("businessId", businessId);
                model.addAttribute("sysKey", Constant.APP_APK_SYS_KEY);
                return "ses/app/add";
            }
        }
        return "";
    }
    
    /**
     * 
     * Description: 回退
     * 
     * @author zhang shubin
     * @data 2017年6月22日
     * @param 
     * @return
     */
    @RequestMapping("/fallback")
    @ResponseBody
    public String fallback(@CurrentUser User user){
    	if(null != user && "4".equals(user.getTypeName())){
    		Integer i = appInfoService.fallbackByVersion();
            if(i > 0){
                return "success";
            }else{
                return "error";
            }
    	}
    	return "";
    }
    
    /**
     * 
     * Description: 详情查看
     * 
     * @author zhang shubin
     * @data 2017年6月23日
     * @param 
     * @return
     */
    @RequestMapping("/view")
    public String view(@CurrentUser User user,Model model,HttpServletRequest request){
    	if(null != user && "4".equals(user.getTypeName())){
	    	String version = request.getParameter("version") == null ? "" : request.getParameter("version");
	        AppInfo appInfo = appInfoService.selectByVersion(version);
	        String businessId = "";
	        if(appInfo != null){
	            businessId = appInfo.getRemark();
	        }
	        model.addAttribute("appInfo",appInfo);
	        model.addAttribute("businessId", businessId);
	        model.addAttribute("sysKey", Constant.APP_APK_SYS_KEY);
	        return "ses/app/view";
    	}
    	return "";
    }
}
