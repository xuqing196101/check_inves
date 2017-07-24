package ses.controller.sys.bms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.User;
import ses.service.bms.AreaServiceI;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import app.model.AppInfo;
import common.annotation.CurrentUser;


/**
 * 
 * @Title:AreaController
 * @Description:地区管理控制类
 * @author FengTian
 * @date 2016-9-7下午6:32:07
 */
@Controller
@Scope("prototype")
@RequestMapping("/area")
public class AreaController {
	@Autowired
	private AreaServiceI areaService;

	/**
	 * 
	 * @Title: createList
	 * @author FengTian
	 * @date 2016-9-1 下午3:27:16
	 * @Description: 创建页面
	 * @param @param model
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User user,Model model) {
		 //声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
            model.addAttribute("authType", authType);
        }
		return "ses/bms/area/list";
	}

	/**
	 * 
	 * @Title: listByOne
	 * @author FengTian
	 * @date 2016-9-7 下午6:30:11
	 * @Description: 生成tree树
	 * @param @param area
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="/listByOne",produces = "application/json;charset=UTF-8")
	public String listByOne(Area area, Model model)throws Exception {
	    return JSON.toJSON(areaService.getTreeList(area.getId(),area.getName())).toString();
	}
	/**
	 * 
	* @Title: add
	* @author FengTian
	* @date 2016-9-29 下午3:47:27  
	* @Description: 跳转添加页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model){
		String pid = request.getParameter("pid");
		Area area = areaService.listById(pid);
		model.addAttribute("area", area);
		return "ses/bms/area/add";
	}
	/**
	 * 
	* @Title: edit
	* @author FengTian
	* @date 2016-10-12 下午4:23:43  
	* @Description: 跳转修改页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(String pid, Model model){
		Area area = areaService.listById(pid);
		String ids = area.getParentId();
		Area area1 = areaService.listById(ids);
		model.addAttribute("area", area);
		model.addAttribute("area1", area1);
		return "ses/bms/area/edit";
	}
	/**
	 * 
	* @Title: save
	* @author FengTian
	* @date 2016-9-29 下午3:47:40  
	* @Description: 添加地区 
	* @param @param area
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(Area area){
	    String msg = null;
	    Area area2 = new Area();
	    area2.setParentId(area.getId());
	    area2.setName(area.getName());
	    List<Area> listByArea = areaService.listByArea(area2);
	    if(listByArea != null && listByArea.size() > 0){
	        msg = "1";
	    }else{
	        areaService.save(area);
	        msg = "0";
	    }
		
		return msg;
	}
	/**
	 * 
	* @Title: update
	* @author FengTian
	* @date 2016-10-12 下午7:05:25  
	* @Description: 修改 
	* @param @param area
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(Area area){
		Area aa = null;
		if(area.getId() != null && !"".equals(area.getId())){
			aa = areaService.listById(area.getId());
		}
		area.setIsDeleted(0);
		areaService.update(area);
		String msg = "{\"msg\":\"success\"}";
		return msg;
	}
	
	@RequestMapping("/delete")
	public String delete(String id){
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Area area = areaService.listById(ids[i]);
			area.setIsDeleted(1);
			areaService.update(area);
		}
		return "redirect:list.html";
	}
	
	@RequestMapping(value = "find_root_area")
	public void findRootArea(HttpServletResponse response) throws IOException {
		List<Area> list = areaService.findRootArea();
		String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	@ResponseBody
	@RequestMapping(value = "find_area_by_parent_id")
	public void findAreaByParentId(HttpServletResponse response, String id) throws IOException {
		List<Area> list = areaService.findAreaByParentId(id);
		String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 
	  * @Title: findAreaByParentId2
	  * @author ShaoYangYang
	  * @date 2016年11月11日 下午4:52:50  
	  * @Description: TODO 根据父id查询子节点
	  * @param @param response
	  * @param @param id
	  * @param @return
	  * @param @throws IOException      
	  * @return List<Area>
	 */
	@ResponseBody
	@RequestMapping(value = "find_by_parent_id",produces = "application/json;charset=UTF-8")
	public String findAreaByParentId2(HttpServletResponse response, String id) throws IOException {
		List<Area> list = areaService.findAreaByParentId(id);
		return JSON.toJSON(list).toString();
	}
	/**
	 * 
	  * @Title: find_by_id
	  * @author ShaoYangYang
	  * @date 2016年11月15日 下午3:09:43  
	  * @Description: TODO 根据id查询节点
	  * @param @param response
	  * @param @param id
	  * @param @return
	  * @param @throws IOException      
	  * @return List<Area>
	 */
	@ResponseBody
	@RequestMapping(value = "find_by_id",produces="application/json;charset=utf-8")
	public String find_by_id(HttpServletResponse response, String id) throws IOException {
		if(StringUtils.isNotEmpty(id)){
		    Area area = areaService.listById(id);
		    //根据父id查询出所有子节点
		    List<Area> listByArea = areaService.findAreaByParentId(area.getParentId());
		    return JSON.toJSON(listByArea).toString();
		}
		return JSON.toJSON(new ArrayList<Area>()).toString();
	}
	
	/**
	 *〈简述〉
	 * 获取父级节点的Id
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param addressId 子节点Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findParentAddress")
	public String findParentAddress(String addressId) {
	    return areaService.listById(addressId).getParentId();
	}
}
