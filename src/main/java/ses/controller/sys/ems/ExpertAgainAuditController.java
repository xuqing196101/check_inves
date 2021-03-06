package ses.controller.sys.ems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.util.ExcelUtils;
import bss.util.ExpertReviewExcelUtils;
import ses.controller.sys.sms.BaseSupplierController;
import ses.dao.bms.TodosMapper;
import ses.dao.ems.ExpertReviewTeamMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.BatchTemporary;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAgainAuditImg;
import ses.model.ems.ExpertAgainAuditReviewTeamList;
import ses.model.ems.ExpertAuditOpinion;
import ses.model.ems.ExpertBatchDetails;
import ses.model.ems.ExpertReviewTeam;
import ses.model.oms.Orgnization;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.ems.ExpertAgainAuditService;
import ses.service.ems.ExpertAuditOpinionService;
import ses.service.ems.ExpertService;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import common.annotation.CurrentUser;
import common.constant.StaticVariables;
import common.utils.JdcgResult;

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
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	@Autowired
	private TodosService todosService; //待办
	@Autowired
	private ExpertAuditOpinionService expertAuditOpinionService;
	@Autowired
	private ExpertReviewTeamMapper expertReviewTeamMapper;
	/*
	 * 提交复审
	 * */
	@RequestMapping("/addAgainAudit")
	public void addAgainAudit(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response, String ids){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
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
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		if(pageNum == null) {
			pageNum = StaticVariables.DEFAULT_PAGE;
		}
		expert.setStatus("1");//查询初审合格专家  
		expert.setSort("1");
		expert.setNotIn("y");
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
		
		if (expert.getExpertsTypeId() != null && !"".equals(expert.getExpertsTypeId())) {
            List<String> listExpertTypeId = Arrays.asList(expert.getExpertsTypeId().split(","));
            expert.setExpertTypeId(listExpertTypeId);
		
		}
		List<Expert> expertList = expertService.findExpertAgainAuditList(expert);
		for (Expert e : expertList) {
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
			if(e.getAuditAt() !=null){
				e.setUpdateTime(dateFormater.format(e.getAuditAt()));
			}
			StringBuffer expertType = new StringBuffer();
            if(e.getExpertsTypeId() != null) {
                for(String typeId: e.getExpertsTypeId().split(",")) {
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(typeId);
                    if(data != null){
                    	if(6 == data.getKind()) {
                            expertType.append(data.getName() + "技术、");
                        } else {
                            expertType.append(data.getName() + "、");
                        }
                    }
                    
                }
                if(expertType.length() > 0){
                	String expertsType = expertType.toString().substring(0, expertType.length() - 1);
                	 e.setExpertsTypeId(expertsType);
                }
            } else {
                e.setExpertsTypeId("");
            }
            
          //专家来源
      		if(e.getExpertsFrom() != null) {
      			DictionaryData expertsFrom = dictionaryDataServiceI.getDictionaryData(e.getExpertsFrom());
      			e.setExpertsFrom(expertsFrom.getName());
      		}
		}
		 // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        // 查询数据字典中的专家类别数据
        List < DictionaryData > jsTypeList = DictionaryDataUtil.find(6);
        for(DictionaryData data: jsTypeList) {
            data.setName(data.getName() + "技术");
        }
        List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
        
        //全部机构
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        hashMap.put("isAuditSupplier", 1);
        List<Orgnization>  allOrg = orgnizationServiceI.findPurchaseOrgByPosition(hashMap);
        
        jsTypeList.addAll(jjTypeList);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("expertList", expertList);
        map.put("allOrg", allOrg);//全部采购机构
        map.put("expTypeList", jsTypeList);//专家类型
        map.put("lyTypeList", lyTypeList);//专家来源
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(map);
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
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
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
	public void findBatch(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,Model model,String batchNumber,String batchName, Date createdAt, Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if("4".equals(user.getTypeName())){
			if(pageNum == null) {
				pageNum = StaticVariables.DEFAULT_PAGE;
			}
			img=againAuditService.findBatch(batchNumber,batchName, createdAt, pageNum);
			super.writeJson(response, img);
			return;
		}else if("6".equals(user.getTypeName())){
			img=againAuditService.fingStayReviewExpertList(user.getId(), batchName,createdAt, pageNum);
			super.writeJson(response, img);
			return;
		}
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
	}
	@RequestMapping("/findBatchList")
	public String findBatchList(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,Model model){
		if("4".equals(user.getTypeName())){
			return "/ses/ems/againAudit/list_batch";
		}else if("6".equals(user.getTypeName())){
			List<ExpertReviewTeam> team = expertReviewTeamMapper.findExpertReviewTeam(user.getId());
			return findBatchDailesList(request, response, model, team.get(0).getBatchId());
		}
		return "/ses/ems/againAudit/list_batch";
	};
	@RequestMapping("/findBatchDetailsList")
	public String findBatchDailesList(HttpServletRequest request,HttpServletResponse response,Model model,String batchId){
		request.setAttribute("batchId", batchId);
		return "/ses/ems/againAudit/details_batch";
	}
	/*
	 * 查询批次详情
	 * */
	@RequestMapping("/findBatchDetails")
	public void findBatchDetails(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,ExpertBatchDetails expertBatchDetails,Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user == null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if("4".equals(user.getTypeName())){
			if(expertBatchDetails==null){
				img.setStatus(false);
				img.setMessage("参数有误");
				super.writeJson(response, img);
				return;
			}
			img=againAuditService.findBatchDetails(expertBatchDetails);
			img.setUserType(user.getTypeName());
			super.writeJson(response, img);
			return;
		}else if("6".equals(user.getTypeName())){
			if(expertBatchDetails==null){
				img.setStatus(false);
				img.setMessage("参数有误");
				super.writeJson(response, img);
				return;
			}
			img=againAuditService.fingStayReviewExpertDetailsList(user.getId(), expertBatchDetails.getBatchId(), pageNum);
			img.setUserType(user.getTypeName());
			super.writeJson(response, img);
			return;
		}
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
	}
	/*
	 * 创建新分组
	 * */
	@RequestMapping("/expertGrouping")
	public void expertGrouping(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId,String ids){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
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
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
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
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
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
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
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
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
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
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
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
	public void findExpertReviewTeam(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String groupId,Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
		if(pageNum == null) {
			pageNum = StaticVariables.DEFAULT_PAGE;
		}
		if(groupId==null){
			img.setStatus(false);
			img.setMessage("操作失败");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.findExpertReviewTeam(groupId,pageNum);
		super.writeJson(response, img);
	}
	/*
	 * 配置审核组成员
	 * */
	@RequestMapping("addExpertReviewTeam")//ExpertAgainAuditReviewTeamList
	public void addExpertReviewTeam(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String userName,String password,ExpertAgainAuditReviewTeamList reviewTeamList){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
		if(reviewTeamList.getList().size()<=0){
			img.setStatus(false);
			img.setMessage("操作有误");
			super.writeJson(response, img);
			return;
		}
		if("".equals(userName)){
			img.setStatus(false);
			img.setMessage("用户名不能为空");
			super.writeJson(response, img);
			return;
		}
		for (Map<String,String> e : reviewTeamList.getList()) {
			if(e!=null){
				if("".equals(e.get("groupId"))){
					img.setStatus(false);
					img.setMessage("请选择您要配置的组");
					super.writeJson(response, img);
					return;
				}
				if("".equals(e.get("relName"))){
					img.setStatus(false);
					img.setMessage("专家姓名不能为空");
					super.writeJson(response, img);
					return;
				}
				if("".equals(e.get("orgName"))){
					img.setStatus(false);
					img.setMessage("单位不能为空");
					super.writeJson(response, img);
					return;
				}
				if("".equals(e.get("duties"))){
					img.setStatus(false);
					img.setMessage("职务不能为空");
					super.writeJson(response, img);
					return;
				}
		}
			img=againAuditService.addExpertReviewTeam(userName,password,reviewTeamList.getList());
			super.writeJson(response, img);
			return;
		}
	}
	/*
	 * 删除审核组成员
	 * */
	@RequestMapping("deleteExpertReviewTeam")
	public void deleteExpertReviewTeam(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String ids){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
		if(ids == null){
			img.setStatus(false);
			img.setMessage("请选择要删除的成员");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.deleteExpertReviewTeam(ids);
		super.writeJson(response, img);
	}
	/*
	 * 校验用户名唯一
	 * */
	@RequestMapping("checkLoginName")
	public void checkLoginName(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String loginName){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		};
		if(loginName == null){
			img.setStatus(false);
			img.setMessage("用户名不能为空");
			super.writeJson(response, img);
			return;
		};
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
        };
		img=againAuditService.checkLoginName(loginName);
		super.writeJson(response, img);
	}
	/*
	 * 设置密码
	 * */
	@RequestMapping("setUpPassword")
	public void setUpPassword(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String groupId,String password,String password2){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		};
		if(groupId == null){
			img.setStatus(false);
			img.setMessage("操作有误");
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
        if( matcher.find() || matcher2.find()) {
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
		img=againAuditService.setUpPassword(groupId, password);
		super.writeJson(response, img);
	}
	/*
	 * 结束审核组成员配置
	 * */
	@RequestMapping("preservationExpertReviewTeam")
	public void preservationExpertReviewTeam(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String groupId) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
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
	public void fingStayReviewExpertList(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchName,Date createdAt, Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"6".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		img=againAuditService.fingStayReviewExpertList(user.getId(), batchName,createdAt, pageNum);
		super.writeJson(response, img);
	}
	/*
	 * 查询审核组成员对应的批次详情
	 * */
	@RequestMapping("fingStayReviewExpertDetailsList")
	public void fingStayReviewExpertDetailsList(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId, Integer pageNum){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		/*if(!"6".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}*/
		if(batchId==null){
			img.setStatus(false);
			img.setMessage("请选择要审核的批次");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.fingStayReviewExpertDetailsList(user.getId(), batchId, pageNum);
		super.writeJson(response, img);
	}
	/*
	 * 校验组状态
	 * */
	@RequestMapping("checkGroupStatus")
	public void checkGroupStatus(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String expertId){
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"6".equals(user.getTypeName())){
		img.setStatus(false);
		img.setMessage("您的权限不足");
		super.writeJson(response, img);
		return;
		}
		if(expertId==null){
			img.setStatus(false);
			img.setMessage("请选择要审核的专家");
			super.writeJson(response, img);
			return;

		}
		img=againAuditService.checkGroupStatus(expertId);
		super.writeJson(response, img);
	}
	/*
	 * 批次自动分组
	 * */
	@RequestMapping("automaticGrouping")
	public void automaticGrouping(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId,int count) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		if(batchId==null){
			img.setStatus(false);
			img.setMessage("操作有误");
			super.writeJson(response, img);
			return;
		}
		if("".equals(count)||count<=0){
			img.setStatus(false);
			img.setMessage("分组数填写有误");
			super.writeJson(response, img);
			return;
		}
		img = againAuditService.automaticGrouping(batchId, count);
		super.writeJson(response, img);
	}
	
	
	/**
     * 复审结束（审核专家操作）
     * @param user
     * @param expertId
     * @return 
     * @return 
     * @return 
     */
	@RequestMapping("/reviewEnd")
	@ResponseBody
	public JdcgResult reviewEnd(@CurrentUser User user, String expertId){
		Expert expert = new Expert();
		expert.setId(expertId);
		//提交审核，更新状态
		expert.setAuditAt(new Date());
		
		//审核人
		//expert.setAuditor(user.getRelName());
		//还原暂存状态
		expert.setAuditTemporary(0);
		// 设置修改时间
		expert.setUpdatedAt(new Date());
		//复审结束标识
		expert.setIsReviewEnd(1);
		expertService.updateByPrimaryKeySelective(expert);
		againAuditService.handleExpertReviewTeam(expertId);
		
		
		/**
		 * 存新意见
		 */
/*		ExpertAuditOpinion expertAuditOpinion = new ExpertAuditOpinion();
		expertAuditOpinion.setExpertId(expertId);
		expertAuditOpinion.setFlagTime(1);
		ExpertAuditOpinion  e = expertAuditOpinionService.selectByExpertId(expertAuditOpinion, null);
		
		if(e.getFlagAudit() !=null && (e.getFlagAudit() == -3  || e.getFlagAudit()== 5)){
			String opinion = e.getOpinion();
			if(opinion !=null && !"".equals(opinion)){
				String newOpinion = opinion;
				
				ExpertAuditOpinion updataAuditOpinion = new ExpertAuditOpinion();
				updataAuditOpinion.setId(e.getId());
				updataAuditOpinion.setOpinion(newOpinion);
				expertAuditOpinionService.updata(updataAuditOpinion);
			}
		}*/
		
		return new JdcgResult(200);
	}
	
	
	/**
     * 复审确认（资源服务中心操作）
     * @param user
     * @param expertId
     * @return 
     * @return 
     * @return 
     */
    @RequestMapping("/reviewConfirm")
    @ResponseBody
    public JdcgResult reviewConfirm(@CurrentUser User user, String[] expertIds){
    	JdcgResult jdcgResult = new JdcgResult();
    	if(expertIds !=null){
    		for(int i=0; i < expertIds.length; i++){
    			// 查询审核意见
        		ExpertAuditOpinion expertAuditOpinion = new ExpertAuditOpinion();
        		expertAuditOpinion.setExpertId(expertIds[i]);
        		expertAuditOpinion.setFlagTime(1);
        		expertAuditOpinion = expertAuditOpinionService.selectByExpertId(expertAuditOpinion);
        		
        		Expert expertInfo = expertService.selectByPrimaryKey(expertIds[i]);
        		if(expertInfo.getIsReviewEnd() !=null && expertInfo.getIsReviewEnd() == 1 && "-2".equals(expertInfo.getStatus())){
        			//更新专家状态
            		Expert expert = new Expert();
            		expert.setId(expertIds[i]);
            		if(expertAuditOpinion !=null && expertAuditOpinion.getFlagAudit() !=null){
            			if(expertAuditOpinion.getFlagAudit() == -3){
            				//预复审合格
            				expert.setStatus("-3");
            			}
            			if(expertAuditOpinion.getFlagAudit() == 5){
            				//复审不合格
            				expert.setStatus("5");
            				
            			}
            			if(expertAuditOpinion.getFlagAudit() == 10){
            				//复审退回修改
            				expert.setStatus("10");
            			}
            		}
            		expert.setAuditAt(new Date());
            		expertService.updateByPrimaryKeySelective(expert);
            		//完成待办
            		todosService.updateIsFinish("expertAudit/basicInfo.html?expertId=" + expertIds[i]);
            		jdcgResult.setStatus(500);
        		}else{
        			jdcgResult.setStatus(503);
        		}
    		}
    	}else{
    		jdcgResult.setStatus(503);
    	}
    	return jdcgResult;
    }
    /*
	 * 获取历史评审专家信息
	 * */
	@RequestMapping("selectReviewTeamAll")
	public void selectReviewTeamAll(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.selectReviewTeamAll();
		super.writeJson(response, img);
	}
	@RequestMapping("downloadExpertReview")
	public void expertRerviewExcel(HttpServletResponse httpServletResponse,String batchId){
		 // 根据编号查询专家信息
    	String name = againAuditService.getbatchName(batchId);
    	Map<String,List<ExpertBatchDetails>> map = new LinkedHashMap<String,List<ExpertBatchDetails>>();
    	ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
    	expertBatchDetails.setBatchId(batchId);
    	expertBatchDetails.setStatus("-2");
    	expertBatchDetails.setNotReviewStatus("1");
    	List<ExpertBatchDetails> list = againAuditService.findBatchDetailsList(expertBatchDetails);
    	List<ExpertBatchDetails> passList = new ArrayList<ExpertBatchDetails>();
    	List<ExpertBatchDetails> notPassList = new ArrayList<ExpertBatchDetails>();
    	List<ExpertBatchDetails> thList = new ArrayList<ExpertBatchDetails>();
    	for (ExpertBatchDetails e : list) {
    		// 查询审核意见
    		ExpertAuditOpinion expertAuditOpinion = new ExpertAuditOpinion();
    		expertAuditOpinion.setExpertId(e.getExpertId());
    		expertAuditOpinion.setFlagTime(1);
    		expertAuditOpinion = expertAuditOpinionService.selectByExpertId(expertAuditOpinion);
    		
    		/*
    		 * 处理旧数据中有“预”字的意见
    		 */
    		if(e.getAuditTemporary()!=null && !"".equals(e.getAuditTemporary())){
    			int indexOf = e.getAuditTemporary().indexOf("。");
    			//手动输入的意见
    			String op = e.getAuditTemporary().substring(indexOf + 1);
    			//自动带出来的那句话
    			String reop = e.getAuditTemporary().substring(0,indexOf + 1);
    			String newOpinion = reop.replace("预", "") + op;
    			e.setAuditTemporary(newOpinion);
    		}
    		
    		
    		if(expertAuditOpinion !=null && expertAuditOpinion.getFlagAudit() !=null){
    			if(expertAuditOpinion.getFlagAudit() == -3){
    				//预复审合格
    				passList.add(e);
    			}
    			if(expertAuditOpinion.getFlagAudit() == 5){
    				//复审不合格
    				notPassList.add(e);
    			}
    			if(expertAuditOpinion.getFlagAudit() == 10){
    				//复审退回修改
    				thList.add(e);
    			}
    		}
		}
    	expertBatchDetails.setReviewStatus("1");
    	expertBatchDetails.setNotReviewStatus(null);
    	List<ExpertBatchDetails> cxList = againAuditService.findBatchDetailsList(expertBatchDetails);
    	if(passList.size()>0){
    		map.put("复审合格", passList);
    	}
    	if(notPassList.size()>0){
    		map.put("复审不合格", notPassList);
    	}
    	if(thList.size()>0){
    		map.put("复审退回修改", thList);
    	}
    	if(cxList.size()>0){
    		map.put("重新复审", cxList);
    	}
    	ExpertReviewExcelUtils excelUtils = new ExpertReviewExcelUtils(response, name+"情况统计表", "sheet1", 1000);
        // 设置冻结行
        excelUtils.setFreezePane(true);
        excelUtils.setFreezePane(new Integer[]{0, 1, 0, 1});
        // 设置序号列
        excelUtils.setOrder(true);
        String titleColumn[] = {"count", "batchDetailsNumber", "orgName", "realName", "gender", "expertsFrom", "expertsTypeId", "workUnit", "professTechTitles", "auditTemporary"};
        String titleName[] = {"序号", "专家编号", "采购机构", "专家姓名", "性别","专家类型", "专家类别", "工作单位", "技术职称(职务)", "复审意见"};
        int titleSize[] = {5, 20, 15, 10, 5, 10, 25, 15, 20, 15, 40};
        excelUtils.wirteExcel(titleColumn, titleName, titleSize, map);
	}
	 //@RequestMapping("downloadExpertReview")
	  /*  public ResponseEntity < byte[] > downloadExpertReview(String batchId,
	        HttpServletRequest request, HttpServletResponse response) throws Exception {
	        // 根据编号查询专家信息
	    	String name = againAuditService.getbatchName(batchId);
	    	List<ExpertBatchDetails> list = againAuditService.findBatchDetailsList(batchId);
	        // 文件存储地址
	        String filePath = request.getSession().getServletContext()
	            .getRealPath("/WEB-INF/upload_file/");
	        // 文件名称
	        String fileName = createWordMethod(list,name, request);
	        // 下载后的文件名
	        String downFileName = "专家复审统计表.doc";
	        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
	            //解决IE下文件名乱码
	            downFileName = URLEncoder.encode(downFileName, "UTF-8");
	        } else {
	            //解决非IE下文件名乱码
	            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
	        }

	        return expertService.downloadFile(fileName, filePath, downFileName);
	    }
	 private String createWordMethod(List<ExpertBatchDetails> list,String batch, HttpServletRequest request) throws Exception {
	      *//** 用于组装word页面需要的数据 *//*
	      Map<String, Object> dataMap = new HashMap<String, Object>();
	      dataMap.put("batch", batch);
	      dataMap.put("list", list);
	      // 文件名称
	        String fileName = new String(("专家复审统计表.doc").getBytes("UTF-8"),
	            "UTF-8");
	        *//** 生成word 返回文件名 *//*
	        String newFileName = WordUtil.createWord(dataMap, "expertReviewTable.ftl",
	            fileName, request);
	        return newFileName;
	 }*/
	 @RequestMapping("selectBatchTemporary")
	 public void selectBatchTemporary(@CurrentUser User user,Expert expert,HttpServletRequest request,HttpServletResponse response) {
		 ExpertAgainAuditImg img = new ExpertAgainAuditImg();
			if(user==null){
				img.setStatus(false);
				img.setMessage("请登录");
				super.writeJson(response, img);
				return;
			}
			if(!"4".equals(user.getTypeName())){
				img.setStatus(false);
				img.setMessage("您的权限不足");
				super.writeJson(response, img);
				return;
			}
			img = againAuditService.selectBatchTemporary(expert);
			super.writeJson(response, img);
	  }
	 @RequestMapping("addBatchTemporary")
	 public void addBatchTemporary(@CurrentUser User user,String ids,HttpServletRequest request,HttpServletResponse response) {
		 ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		 if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		img = againAuditService.addBatchTemporary(user.getTypeId(), ids);
		super.writeJson(response, img);
	 }
	 @RequestMapping("deleteBatchTemporary")
	 public void deleteBatchTemporary(@CurrentUser User user,String ids,HttpServletRequest request,HttpServletResponse response){
		 ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		 if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		if(ids == null){
			img.setStatus(false);
			img.setMessage("请至少选择一项");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.deleteBatchTemporary(ids);
		super.writeJson(response, img);
	 }
	 /*
	  * 重新复审
	  * */
	 @RequestMapping("againReview")
	 public void againReview(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String id){
		 ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		 if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		if(id==null){
			img.setStatus(false);
			img.setMessage("请求参数有误");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.againReview(id);
		super.writeJson(response, img);
	 };
	 /*
	  * 取消复审
	  * */
	 @RequestMapping("cancelReview")
	 public void cancelReview(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String id) {
		 ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		 if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		if(id==null){
			img.setStatus(false);
			img.setMessage("请求参数有误");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.cancelReview(id);
		super.writeJson(response, img);
	}
	/*
	 * 生效
	 * */
	@RequestMapping("takeEffect")
	public void takeEffect(@CurrentUser User user,HttpServletRequest request,HttpServletResponse response,String batchId) {
		 ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		 if(user==null){
			img.setStatus(false);
			img.setMessage("请登录");
			super.writeJson(response, img);
			return;
		}
		if(!"4".equals(user.getTypeName())){
			img.setStatus(false);
			img.setMessage("您的权限不足");
			super.writeJson(response, img);
			return;
		}
		if(batchId==null){
			img.setStatus(false);
			img.setMessage("请求参数有误");
			super.writeJson(response, img);
			return;
		}
		img=againAuditService.takeEffect(batchId);
		super.writeJson(response, img);
	}
}
