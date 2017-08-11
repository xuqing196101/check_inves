package ses.controller.sys.ems;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;
import net.sf.json.JSONObject;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAgainAuditImg;
import ses.service.ems.ExpertAgainAuditService;
import ses.service.ems.ExpertService;

/**
 * <p>Title:ExpertAgainAuditController </p>
 * <p>Description: 专家复审</p>
 * @author ShiShuai
 * @date 2017-08-10上午10:05:33
 */
@Controller
@RequestMapping("/expertAgainAudit")
public class ExpertAgainAuditController extends BaseSupplierController {
	@Autowired
	private ExpertAgainAuditService againAuditService;
	@Autowired
	private ExpertService expertService;
	@RequestMapping("addAgainAudit")
	public void addAgainAudit(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response, String ids){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(!"1".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		if(ids != null){
			img=againAuditService.addAgainAudit(ids);
		}else{
			img.setStatus(false);
			img.setMessage("请选择提交复审专家");
		}
		super.writeJson(response, img); 
	}
	@RequestMapping("againAuditList")
	public void againAuditList(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,Expert expert, Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		//Date date = expert.getUpdatedAt();
		/*if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}*/
		if(pageNum == null) {
			pageNum = StaticVariables.DEFAULT_PAGE;
		}
		expert.setStatus("0");//查询待分配专家
		//查询列表
		List < Expert > expertList = expertService.findExpertAuditList(expert, pageNum);
		PageInfo< Expert > result = new PageInfo < Expert > (expertList);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(result);
		super.writeJson(response, img);
	}
	@RequestMapping("findAgainAuditList")
	public String findAgainAuditList(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/list";
	};
	
}
