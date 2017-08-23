package ses.controller.sys.ems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import ses.model.ems.ExpertReviewTeam;
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
	/*
	 * 提交复审
	 * */
	@RequestMapping("/addAgainAudit")
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
	/*
	 * 查询所有带分配专家
	 * */
	@RequestMapping("/againAuditList")
	public void againAuditList(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,Expert expert, Integer pageNum,String batchIds){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}*/
		if(pageNum == null) {
			pageNum = StaticVariables.DEFAULT_PAGE;
		}
		expert.setStatus("11");//查询待分配专家
		if(batchIds != null){
			List<String> idsList = new ArrayList<String>();
			String[] split = batchIds.split(",");
			for (String string : split) {
				if( string != null ){
					idsList.add(string);
				}
			}
			expert.setIds(idsList);
		}
		//查询列表
		List < Expert > expertList = expertService.findExpertAuditList(expert, pageNum);
		PageInfo< Expert > result = new PageInfo < Expert > (expertList);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(result);
		super.writeJson(response, img);
	}
	/*
	 * 专家复审分配列表
	 * */
	@RequestMapping("/findAgainAuditList")
	public String findAgainAuditList(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/allot_list";
	};
	/*
	 * 创建新批次
	 * */
	@RequestMapping("/createBatch")
	public void createBatch(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchName, String batchNumber, String ids){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}*/
		if("".equals(batchName) || batchName == null){
			img.setStatus(false);
			img.setMessage("批次名称不能为空");
			super.writeJson(response, img);
			return;
		}
		if("".equals(batchNumber) || batchNumber == null){
			img.setStatus(false);
			img.setMessage("批次编号不能为空");
			super.writeJson(response, img);
			return;
		}
		if("".equals(ids) || ids == null){
			img.setStatus(false);
			img.setMessage("请选择专家");
			super.writeJson(response, img);
			return;
		}
		img = againAuditService.createBatch(batchName, batchNumber, ids);
		super.writeJson(response, img);
	}
	/*
	 * 查询批次
	 * */
	@RequestMapping("/findBatch")
	public void findBatch(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchNumber,String batchName, Date createdAt, Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}*/
		if(pageNum == null) {
			pageNum = StaticVariables.DEFAULT_PAGE;
		}
		img=againAuditService.findBatch(batchNumber,batchName, createdAt, pageNum);
		super.writeJson(response, img);
	}
	@RequestMapping("/findBatchList")
	public String findBatchList(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/list_batch";
	};
	@RequestMapping("/findBatchDetailsList")
	public String findBatchDailesList(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/details_batch";
	}
	/*
	 * 查询批次详情
	 * */
	@RequestMapping("/findBatchDetails")
	public void findBatchDetails(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId,String status,Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}*/
		if(pageNum == null) {
			pageNum = StaticVariables.DEFAULT_PAGE;
		}
		img=againAuditService.findBatchDetails(batchId,status, pageNum);
		super.writeJson(response, img);
	}
	/*
	 * 创建新分组
	 * */
	@RequestMapping("/expertGrouping")
	public void expertGrouping(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId,String ids){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(batchId==null){
			img.setStatus(false);
			img.setMessage("操作失败");
			super.writeJson(response, img);
			return;
		}
		if(ids==null){
			img.setStatus(false);
			img.setMessage("请选择待分组专家");
			super.writeJson(response, img);
			return;
		}
		img = againAuditService.expertGrouping(batchId, ids);
		super.writeJson(response, img);
	}
	@RequestMapping("/groupBatch")
	public String groupBatch(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/group_batch";
	}
	
	/*
	 * 获取批次中所有的已有组
	 * */
	@RequestMapping("/getGroups")
	public void getGroups(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(batchId==null){
			img.setStatus(false);
			img.setMessage("操作失败");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.getGroups(batchId);
		super.writeJson(response, img);
	}
	
	/*
	 * 添加至已有分组
	 * */
	@RequestMapping("/expertAddGroup")
	public void expertAddGroup(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String groupId,String ids){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(groupId==null){
			img.setStatus(false);
			img.setMessage("请选择正确分组");
			super.writeJson(response, img);
			return;
		}
		if(ids==null){
			img.setStatus(false);
			img.setMessage("请选择待分组专家");
			super.writeJson(response, img);
			return;
		}
		img = againAuditService.expertAddGroup(groupId, ids);
		super.writeJson(response, img);
	}
	/*
	 * 获取所有分组及各个组中的专家
	 * */
	@RequestMapping("/findExpertGroupDetails")
	public void findExpertGroupDetails(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(batchId==null){
			img.setStatus(false);
			img.setMessage("操作失败");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.findExpertGroupDetails(batchId);
		super.writeJson(response, img);
	}
	/*
	 *将已分组专家从本组删除 
	 * */
	@RequestMapping("/delExpertGroupDetails")
	public void delExpertGroupDetails(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String ids){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(ids==null){
			img.setStatus(false);
			img.setMessage("请选择要删除的专家");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.delExpertGroupDetails(ids);
		super.writeJson(response, img);
	}
	@RequestMapping("/editMembers")
	public String editMembers(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/edit_members";
	}
	@RequestMapping("/auditBatch")
	public String auditBatch(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/audit_batch";
	}
	/*
	 * 专家审核复审批次列表
	 * */
	@RequestMapping("/expertAuditBatch")
	public String expertAuditBatch(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/expert_auditBatch";
	}
	/*
	 * 专家审核复审批次详情列表
	 * */
	@RequestMapping("/expertDetailsBatch")
	public String expertDetailsBatch(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/ses/ems/againAudit/expert_detailsBatch";
	}
	@RequestMapping("/checkComplete")
	public void checkComplete(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(batchId==null){
			img.setStatus(false);
			img.setMessage("操作失败");
			super.writeJson(response, img);
			return;
		}
		img = againAuditService.checkComplete(batchId);
		super.writeJson(response, img);
	}
	/*
	 * 获取当前组的审核组成员
	 * */
	@RequestMapping("findExpertReviewTeam")
	public void findExpertReviewTeam(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String groupId){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(groupId==null){
			img.setStatus(false);
			img.setMessage("操作失败");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.findExpertReviewTeam(groupId);
		super.writeJson(response, img);
	}
	/*
	 * 配置审核组成员
	 * */
	@RequestMapping("addExpertReviewTeam")
	public void addExpertReviewTeam(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,ExpertReviewTeam e){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(e!=null){
			if("".equals(e.getGroupId())){
				img.setStatus(false);
				img.setMessage("请选择您要配置的组");
				super.writeJson(response, img);
				return;
			}
			if("".equals(e.getLoginName())){
				img.setStatus(false);
				img.setMessage("用户名不能为空");
				super.writeJson(response, img);
				return;
			}
			if("".equals(e.getRelName())){
				img.setStatus(false);
				img.setMessage("专家姓名不能为空");
				super.writeJson(response, img);
				return;
			}
			if("".equals(e.getOrgName())){
				img.setStatus(false);
				img.setMessage("单位不能为空");
				super.writeJson(response, img);
				return;
			}
			if("".equals(e.getDuties())){
				img.setStatus(false);
				img.setMessage("职务不能为空");
				super.writeJson(response, img);
				return;
			}
			img=againAuditService.addExpertReviewTeam(e);
			super.writeJson(response, img);
			return;
		}
	}
	/*
	 * 删除审核组成员
	 * */
	@RequestMapping("deleteExpertReviewTeam")
	public void deleteExpertReviewTeam(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String id){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(id == null){
			img.setStatus(false);
			img.setMessage("请选择要删除的成员");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.deleteExpertReviewTeam(id);
		super.writeJson(response, img);
	}
	/*
	 * 校验用户名唯一
	 * */
	@RequestMapping("checkLoginName")
	public void checkLoginName(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String loginName){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(loginName == null){
			img.setStatus(false);
			img.setMessage("用户名不能为空");
			super.writeJson(response, img);
			return;
		}
		String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regex);
        Pattern p2 = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(loginName);
        Matcher m2 = p2.matcher(loginName);
        if(loginName.trim().length() < 3 || m.find() || m2.find()){
        	img.setStatus(false);
			img.setMessage("用户名不符合规则");
			super.writeJson(response, img);
			return;
        }
		img=againAuditService.checkLoginName(loginName);
		super.writeJson(response, img);
	}
	/*
	 * 设置密码
	 * */
	@RequestMapping("setUpPassword")
	public void setUpPassword(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String ids,String password,String password2){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(ids == null){
			img.setStatus(false);
			img.setMessage("请选择审核组成员");
			super.writeJson(response, img);
			return;
		}
		if(password==null){
			img.setStatus(false);
			img.setMessage("密码不能为空");
			super.writeJson(response, img);
			return;
		}
		String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regex);
        Pattern p2 = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = p.matcher(password);
        Matcher matcher2 = p2.matcher(password);
        if(password.trim().length() < 6 || matcher.find() || matcher2.find()) {
        	img.setStatus(false);
        	img.setMessage("密码不符合规则");
        	super.writeJson(response, img);
        	return;
        }
        if(!password.equals(password2)){
        	img.setStatus(false);
        	img.setMessage("两次密码输入不一致");
        	super.writeJson(response, img);
        	return;
        }
		img=againAuditService.setUpPassword(ids, password);
		super.writeJson(response, img);
	}
	/*
	 * 结束审核组成员配置
	 * */
	@RequestMapping("preservationExpertReviewTeam")
	public void preservationExpertReviewTeam(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String groupId) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(groupId==null){
			img.setStatus(false);
			img.setMessage("操作失败");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.preservationExpertReviewTeam(groupId);
		super.writeJson(response, img);
	}
	/*
	 * 查询审核组成员对应的批次
	 * */
	@RequestMapping("fingStayReviewExpertList")
	public void fingStayReviewExpertList(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response, Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"6".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		img=againAuditService.fingStayReviewExpertList(user.getId(), pageNum);
		super.writeJson(response, img);
	}
}
