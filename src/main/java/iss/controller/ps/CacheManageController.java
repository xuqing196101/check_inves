package iss.controller.ps;

import common.annotation.CurrentUser;
import common.utils.JdcgResult;
import iss.model.ps.Cache;
import iss.model.ps.Page;
import iss.service.ps.CacheManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.model.bms.User;

/**
 * 
 * @Title: CacheManageController.java
 * @Package iss.controller.ps
 * @Description: 缓存管理
 * @author SongDong
 * @date 2017年2月27日 下午1:40:11
 * @version 2017年2月27日
 */
@RequestMapping("/cacheManage")
@Controller
public class CacheManageController {

	// 注入缓存管理ServiceO
	@Autowired
	private CacheManageService cacheManageService;

	/**
	 *
	 * Description: 返回缓存页面
	 *
	 * @author Easong
	 * @version 2017/9/27
	 * @param []
	 * @since JDK1.7
	 */
	@RequestMapping("/cachemanage")
	public String list(){
		return "iss/ps/cache/cachemanage";
	}

	/**
	 * 
	 * @Title: cachemanage
	 * @Description: 异步查询所有缓存信息
	 * @author Easong
	 * @param @param model
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/cachemanageAjax")
	@ResponseBody
	public JdcgResult cachemanage(@RequestParam(defaultValue = "1") Integer page) {
		Page<Cache> info = cacheManageService.cachemanage(page);
		return JdcgResult.ok(info);
	}

	/**
	 * 
	 * @Title: clearCache
	 * @Description: 根据健清除缓存
	 * @author Easong
	 * @param @param cacheKey
	 * @param @param cacheType
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping("/clearStringCache")
	@ResponseBody
	public JdcgResult clearCache(String cacheKey, String cacheType) {
		return cacheManageService.clearCache(cacheKey, cacheType);
	}

    /**
     *
     * Description:清空所有缓存
     *
     * @author Easong
     * @version 2017/9/27
     * @param []
     * @since JDK1.7
     */
    @RequestMapping("/clearAllCache")
    @ResponseBody
	public JdcgResult clearAllCache(){
        return cacheManageService.clearAllCache();
    }

	/**
	 * 
	 * @Title: getValueByKey
	 * @Description: 通过建获取值
	 * @author Easong
	 * @param @param cacheKey
	 * @param @param cacheType
	 * @param @param model
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/getValueByKey")
	public Object getValueByKey(String cacheKey, String cacheType, Model model) {
		Object cache = cacheManageService.getValueByKey(cacheKey, cacheType);
		model.addAttribute("cache", cache);
		return "iss/ps/cache/detail";
	}

	/**
	 * 
	 * Description:获取访问量
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @return
	 */
	@RequestMapping("/getPVDate")
	@ResponseBody
	public JdcgResult getPVDate(@CurrentUser User user) {
		return cacheManageService.getPVDate(user);
	}
}
