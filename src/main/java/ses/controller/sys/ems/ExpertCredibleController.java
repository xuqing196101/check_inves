package ses.controller.sys.ems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import ses.model.bms.User;
import ses.model.ems.CredibleRelate;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCredible;
import ses.service.ems.CredibleRelateService;
import ses.service.ems.ExpertCredibleService;
import ses.service.ems.ExpertService;


@Controller
@RequestMapping("credible")
public class ExpertCredibleController {

	@Autowired
	private ExpertCredibleService service;
	@Autowired
	private CredibleRelateService credibleRelateService;
	@Autowired
	private ExpertService expertService;
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年11月2日 下午4:31:47  
	  * @Description: TODO 新增 保存
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("save")
	public String save(ExpertCredible expertCredible){
		service.save(expertCredible);
		return "redirect:list.html";
	}
	/**
	 * 
	  * @Title: list
	  * @author ShaoYangYang
	  * @date 2016年11月2日 下午7:44:06  
	  * @Description: TODO  分页查询
	  * @param @param expertCredible
	  * @param @param page
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("list")
	public String list(@CurrentUser User user,ExpertCredible expertCredible,Integer page,Model model){
		Map<String,Object> map = new HashMap<>();
		if(expertCredible != null){
		map.put("isStatus", expertCredible.getIsStatus());
		map.put("badBehavior", expertCredible.getBadBehavior());
		}
		if(null != user && "4".equals(user.getTypeName())){
			List<ExpertCredible> list = service.list(page==null?0:page, map);
			model.addAttribute("result", new PageInfo<>(list));
		}else{
			model.addAttribute("result", new PageInfo<>());
		}
		
		model.addAttribute("expertCredible", expertCredible);
		return "ses/ems/expert/credible_list";
	}
	/**
	 * 
	 * @Title: findAll
	 * @author ShaoYangYang
	 * @date 2016年11月2日 下午7:44:06  
	 * @Description: TODO  分页查询
	 * @param @param expertCredible
	 * @param @param page
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("findAll")
	public String findAll(String badBehavior,String id,Integer page,Model model){
		Map<String,Object> map = new HashMap<>();
		map.put("isStatus", 1);
		map.put("badBehavior", badBehavior);
		List<ExpertCredible> list = service.list(page==null?0:page, map);
		model.addAttribute("result", new PageInfo<>(list));
		model.addAttribute("badBehavior", badBehavior);
		model.addAttribute("expertId", id);
		return "ses/ems/expert/credible_check";
	}
	/**
	 * 
	  * @Title: toUpdate
	  * @author ShaoYangYang
	  * @date 2016年11月2日 下午6:33:51  
	  * @Description: TODO 去修改页面
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toUpdate")
	public String toUpdate(String id,Model model){
		ExpertCredible expertCredible = service.findById(id);
		model.addAttribute("expertCredible", expertCredible);
		return "ses/ems/expert/credible_edit";
	}
	/**
	 * 
	 * @Title: update
	 * @author ShaoYangYang
	 * @date 2016年11月2日 下午6:33:51  
	 * @Description: TODO 修改页面
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("update")
	public String update(ExpertCredible expertCredible){
		//修改诚信内容  同步修改关联表的诚信内容
		Map<String, Object> map = new HashMap<>();
		map.put("expertCredibleId", expertCredible.getId());
		List<CredibleRelate> selectAllByMap = credibleRelateService.selectAllByMap(map );
		for (CredibleRelate credibleRelate : selectAllByMap) {
			credibleRelate.setCause(expertCredible.getBadBehavior());
			credibleRelateService.updateByBean(credibleRelate);
		}
		//执行修改
		service.updateById(expertCredible);
		return "redirect:list.html";
	}
	/**
	 * 
	 * @Title: deleteAll
	 * @author ShaoYangYang
	 * @date 2016年11月2日 下午6:33:51  
	 * @Description: TODO 删除
	 * @param @param id
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("deleteAll")
	public String deleteAll(String ids){
		String[] id = ids.split(",");
		for (String string : id) {
			ExpertCredible expertCredible = service.findById(string);
			expertCredible.setIsDelete((short) 2);
			service.updateById(expertCredible);
		}
		return "redirect:list.html";
	}
	/**
	 * 
	  * @Title: git
	  * @author ShaoYangYang
	  * @date 2016年11月3日 上午10:04:56  
	  * @Description: TODO 提交评分
	  * @param @param ids
	  * @param @param expertId      
	  * @return void
	 */
	@RequestMapping("git")
	@ResponseBody
	public void git(String ids,String expertId){
		String[] split = ids.split(",");
		//保存关联关系
		CredibleRelate cre;
		List<ExpertCredible> list = new ArrayList<>();
		for (String string : split) {
			cre = new CredibleRelate();
			ExpertCredible expertCredible = service.findById(string);
			list.add(expertCredible);
			cre.setExpertCredibleId(string);
			cre.setExpertId(expertId);
			cre.setCause(expertCredible.getBadBehavior());
			credibleRelateService.save(cre);
		}
		//总分
		int sum = 0;
		for (ExpertCredible expertCredible : list) {
			sum+=expertCredible.getScore();
		}
		//更新专家诚信
		Expert expert = expertService.selectByPrimaryKey(expertId);
		expert.setHonestyScore(expert.getHonestyScore()+sum);
		expertService.updateByPrimaryKeySelective(expert);
	} 
	
}
