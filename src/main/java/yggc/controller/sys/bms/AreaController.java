package yggc.controller.sys.bms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import yggc.model.bms.Area;
import yggc.model.bms.AreaZtree;
import yggc.service.bms.AreaServiceI;

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
	@RequestMapping("/createList")
	public String createList() {
		return "area/list";
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
	@RequestMapping("/listByOne")
	public String listByOne(Area area) throws Exception {
		if (area.getId() == null) {
			area.setId("1");
		}
		List<Area> list = areaService.findTreeByPid(area.getId());
		List<AreaZtree> listTree = new ArrayList<AreaZtree>();
		String ee = "";
		for (Area a : list) {
			List<Area> cList = areaService.findTreeByPid(a.getId());
			AreaZtree az = new AreaZtree();
			if (!cList.isEmpty()) {
				az.setIsParent("true");
			} else {
				az.setIsParent("false");
			}
			az.setId(a.getId());
			az.setName(a.getName());
			az.setpId(a.getAreaType());
			listTree.add(az);
			ee = JSONObject.toJSONString(listTree);
		}
		return ee;
	}

}
