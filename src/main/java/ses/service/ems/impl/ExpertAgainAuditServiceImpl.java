package ses.service.ems.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertBatchDetailsMapper;
import ses.dao.ems.ExpertBatchMapper;
import ses.dao.ems.ExpertGroupMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ExpertReviewTeamMapper;
import ses.model.bms.RoleUser;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAgainAuditImg;
import ses.model.ems.ExpertBatch;
import ses.model.ems.ExpertBatchDetails;
import ses.model.ems.ExpertGroup;
import ses.model.ems.ExpertReviewTeam;
import ses.service.ems.ExpertAgainAuditService;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
/**
 * @ExpertAgainAuditServiceImpl 
 * @Description:专家复审ServiceImpl类
 * @author ShiShuai
 * @date 2017-08-10上午10:10:40
 */
@Service("expertAgainAuditService")
public class ExpertAgainAuditServiceImpl implements ExpertAgainAuditService {
	@Autowired
	private ExpertMapper expertMapper;
	@Autowired
	private ExpertBatchMapper expertBatchMapper;
	@Autowired
	private ExpertBatchDetailsMapper expertBatchDetailsMapper;
	@Autowired
	private ExpertGroupMapper expertGroupMapper;
	@Autowired
	private ExpertReviewTeamMapper expertReviewTeamMapper;
	@Autowired
	private UserMapper userMapper;
	
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	@Override
	public ExpertAgainAuditImg addAgainAudit(String ids) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		List<String> idsList = new ArrayList<String>();
		Expert e = new Expert();
		String[] split = ids.split(",");
		for (String string : split) {
			if( string != null ){
				idsList.add(string);
			}
		}
		e.setIds(idsList);
		List<Expert> list = expertMapper.findExpertByInList(e);
		for (Expert expert : list) {
			if("1".equals(expert.getStatus())){
				expert.setStatus("11");
				expert.setUpdatedAt(new Date());
			}else{
				img.setStatus(false);
				img.setMessage("只有初审合格才可以提交复审");
				return img;
			}
		}
		for (Expert expert : list) {
			expertMapper.updateByPrimaryKey(expert);
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg createBatch(String batchName, String batchNumber, String ids) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertBatch expertBatch = new ExpertBatch();
		expertBatch.setBatchId(WfUtil.createUUID());
		expertBatch.setBatchName(batchName);
		expertBatch.setBatchNumber(batchNumber);
		expertBatch.setCreatedAt(new Date());
		expertBatch.setUpdatedAt(new Date());
		expertBatchMapper.insert(expertBatch);
		List<String> idsList = new ArrayList<String>();
		Expert e = new Expert();
		String[] split = ids.split(",");
		for (String string : split) {
			if( string != null ){
				idsList.add(string);
			}
		}
		e.setIds(idsList);
		List<Expert> list = expertMapper.findExpertByInList(e);
		int count=1;
		for (Expert expert : list) {
			ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
			expertBatchDetails.setId(WfUtil.createUUID()); 
			expertBatchDetails.setBatchId(expertBatch.getBatchId());
			expertBatchDetails.setExpertId(expert.getId());
			expertBatchDetails.setBatchNumber(batchNumber);
			String number=count>=10?(count>=100?count+"":"0"+count):"00"+count;
			expertBatchDetails.setBatchDetailsNumber(batchNumber+"-"+number);
			expertBatchDetails.setCount(number);
			expertBatchDetails.setBatchName(batchName);
			expertBatchDetails.setCreatedAt(new Date());
			expertBatchDetails.setUpdatedAt(new Date());
			expertBatchDetailsMapper.insert(expertBatchDetails);
			count++;
			expert.setStatus("14");//将专家状态改为待分组
			expertMapper.updateByPrimaryKey(expert);
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg findBatch(String batchNumber,String batchName, Date createdAt, Integer pageNum) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("batchNumber", batchNumber);
		map.put("batchName", batchName);
		map.put("createdAt", createdAt);
		List<ExpertBatch> list = expertBatchMapper.getAllExpertBatch(map);
		PageInfo< ExpertBatch > result = new PageInfo < ExpertBatch > (list);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(result);
		return img;
	}

	@Override
	public ExpertAgainAuditImg findBatchDetails(String batchId,String status, Integer pageNum) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,1/*Integer.parseInt(config.getString("pageSize"))*/);
		}
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		expertBatchDetails.setBatchId(batchId);
		expertBatchDetails.setStatus(status);
		List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		PageInfo< ExpertBatchDetails > result = new PageInfo < ExpertBatchDetails > (list);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(result);
		return img;
	}
	
	public  String numToStr(int num) {    
        String u[] = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" }; 
        String s[]={"","十","百"};
        String rstr = "";
        int sw=num/10;
        if(sw!=1){
        	rstr=rstr+u[sw];
        }
        if((num/10)!=0){
        	 rstr = rstr +  s[String.valueOf(num/10).length()];
        }
        int g=num%10;
        rstr=rstr+u[g];
        return rstr;    
    }
	@Override
	public ExpertAgainAuditImg expertGrouping(String batchId, String ids) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		String maxGroupCount = expertGroupMapper.getMaxGroupCount(batchId);
		int count=1;
		if(maxGroupCount != null){
			count=Integer.valueOf(maxGroupCount)+1;
		}
		String groupName=numToStr(count)+"组";
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setGroupId(WfUtil.createUUID());
		expertGroup.setBatchId(batchId);
		expertGroup.setGroupName(groupName);
		expertGroup.setCount(count+"");
		expertGroup.setCreatedAt(new Date());
		expertGroup.setUpdatedAt(new Date());
		expertGroup.setStatus("1");
		expertGroupMapper.insert(expertGroup);
		List<String> idsList = new ArrayList<String>();
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		String[] split = ids.split(",");
		for (String string : split) {
			if( string != null ){
				idsList.add(string);
			}
		}
		expertBatchDetails.setIds(idsList);
		List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		for (ExpertBatchDetails batchDetails : list) {
			batchDetails.setGroupId(expertGroup.getGroupId());
			batchDetails.setGroupName(groupName);
			batchDetails.setUpdatedAt(new Date());
			expertBatchDetailsMapper.updateExpertBatchDetailsGrouping(batchDetails);
			Expert expert = expertMapper.selectByPrimaryKey(batchDetails.getExpertId());
			expert.setStatus("4");
			expertMapper.updateByPrimaryKey(expert);
		}
		img.setStatus(true);
		img.setMessage(groupName+"创建成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg getGroups(String batchId) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setBatchId(batchId);
		List<ExpertGroup> list = expertGroupMapper.getGroup(expertGroup);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(list);
		return img;
	}

	@Override
	public ExpertAgainAuditImg expertAddGroup(String groupId, String ids) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setGroupId(groupId);
		List<ExpertGroup> list = expertGroupMapper.getGroup(expertGroup);
		if(list == null ){
			img.setStatus(false);
			img.setMessage("请选择正确的分组");
			return img;
		}
		expertGroup=list.get(0);
		List<String> idsList = new ArrayList<String>();
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		String[] split = ids.split(",");
		for (String string : split) {
			if( string != null ){
				idsList.add(string);
			}
		}
		expertBatchDetails.setIds(idsList);
		List<ExpertBatchDetails> batchDetailslist = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		for (ExpertBatchDetails batchDetails : batchDetailslist) {
			batchDetails.setGroupId(expertGroup.getGroupId());
			batchDetails.setGroupName(expertGroup.getGroupName());
			batchDetails.setUpdatedAt(new Date());
			expertBatchDetailsMapper.updateExpertBatchDetailsGrouping(batchDetails);
			Expert expert = expertMapper.selectByPrimaryKey(batchDetails.getExpertId());
			expert.setStatus("4");
			expertMapper.updateByPrimaryKey(expert);
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg findExpertGroupDetails(String batchId) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setBatchId(batchId);
		List<ExpertGroup> groupList = expertGroupMapper.getGroup(expertGroup);
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (ExpertGroup group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", group.getGroupId());
			map.put("name", group.getGroupName());
			map.put("groupStatus", group.getStatus());
			ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
			expertBatchDetails.setGroupId(group.getGroupId());
			List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
			map.put("expertList", list);
			resultList.add(map);
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(resultList);
		return img;
	}

	@Override
	public ExpertAgainAuditImg delExpertGroupDetails(String ids) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		List<String> idsList = new ArrayList<String>();
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		String[] split = ids.split(",");
		for (String string : split) {
			if( string != null ){
				idsList.add(string);
			}
		}
		expertBatchDetails.setIds(idsList);
		List<ExpertBatchDetails> batchDetailslist = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		for (ExpertBatchDetails batchDetails : batchDetailslist) {
			batchDetails.setGroupId("");
			batchDetails.setGroupName("");
			batchDetails.setUpdatedAt(new Date());
			expertBatchDetailsMapper.updateExpertBatchDetailsGrouping(batchDetails);
			Expert expert = expertMapper.selectByPrimaryKey(batchDetails.getExpertId());
			expert.setStatus("14");
			expertMapper.updateByPrimaryKey(expert);
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg checkComplete(String batchId) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		expertBatchDetails.setBatchId(batchId);
		List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		for (ExpertBatchDetails e : list) {
			if("".equals(e.getGroupName())||e.getGroupName()==null){
				img.setStatus(false);
				img.setMessage("需将当前批次中所有专家全部分组才可以结束当前环节");
				return img;
			}
		}
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setBatchId(batchId);
		List<ExpertGroup> group = expertGroupMapper.getGroup(expertGroup);
		for (ExpertGroup g : group) {
			expertBatchDetails.setGroupId(g.getGroupId());
			List<ExpertBatchDetails> details = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
			if(details.size()<=0){
				img.setStatus(false);
				img.setMessage("组内必须有专家");
				return img;
			}
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg findExpertReviewTeam(String groupId) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setGroupId(groupId);
		List<ExpertGroup> group = expertGroupMapper.getGroup(expertGroup);
		expertGroup=group.get(0);
		if("3".equals(expertGroup.getStatus())){
			img.setStatus(false);
			img.setMessage("当前组已配置完成");
			return img;
		}
		ExpertReviewTeam expertReviewTeam = new ExpertReviewTeam();
		expertReviewTeam.setGroupId(groupId);
		List<ExpertReviewTeam> list = expertReviewTeamMapper.getExpertReviewTeamList(expertReviewTeam);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("groupId", groupId);
		map.put("list", list);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(map);
		return img;
	}

	@Override
	public ExpertAgainAuditImg addExpertReviewTeam(ExpertReviewTeam expertReviewTeam) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		expertReviewTeam.setId(WfUtil.createUUID());
		User user = new User();
		user.setId(WfUtil.createUUID());
		user.setLoginName(expertReviewTeam.getLoginName());
		user.setRelName(expertReviewTeam.getRelName());
		user.setOrgName(expertReviewTeam.getOrgName());
		user.setDuties(expertReviewTeam.getDuties());
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		user.setIsDeleted(0);
		user.setTypeName("6");
		user.setTypeId(expertReviewTeam.getId());
		String ipAddressType = PropUtil.getProperty("ipAddressType");
		user.setNetType(Integer.valueOf(ipAddressType));
		userMapper.saveUser(user);
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setGroupId(expertReviewTeam.getGroupId());
		List<ExpertGroup> group = expertGroupMapper.getGroup(expertGroup);
		expertGroup=group.get(0);
		expertReviewTeam.setBatchId(expertGroup.getBatchId());
		expertReviewTeam.setUserId(user.getId());
		expertReviewTeam.setCreatedAt(new Date());
		expertReviewTeam.setUpdatedAt(new Date());
		RoleUser roleUser = new RoleUser();
		roleUser.setUserId(user.getId());
		roleUser.setRoleId("2A47E9E432CF4E4DACED2BC099715BCC");
		userMapper.saveUserRole(roleUser);
		expertGroup.setStatus("2");
		expertGroupMapper.updateStatus(expertGroup);
		expertReviewTeamMapper.insert(expertReviewTeam);
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg deleteExpertReviewTeam(String ids) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		String[] split = ids.split(",");
		for (String id : split) {
			ExpertReviewTeam expertReviewTeam = expertReviewTeamMapper.getExpertReviewTeam(id);
			List<User> list = userMapper.selectByPrimaryKey(expertReviewTeam.getUserId());
			if(list!=null&&list.size()>0){
				User user=list.get(0);
				user.setIsDeleted(1);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		    	String date = format.format(new Date());
		    	StringBuffer suffix = new StringBuffer();
		    	suffix.append("_del_bak_");
		    	suffix.append(date);
		    	user.setLoginName(user.getLoginName()+suffix.toString());
		    	userMapper.updateByPrimaryKeySelective(user);
			}
			expertReviewTeamMapper.deleteByPrimaryKey(id);
		}
		
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg setUpPassword(String ids,String passWord) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		String[] split = ids.split(",");
		for (String id : split) {
			ExpertReviewTeam expertReviewTeam = expertReviewTeamMapper.getExpertReviewTeam(id);
			User user = new User();
			user.setId(expertReviewTeam.getUserId());
			//生成15位随机码
			String randomCode = generateString(15);
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
			// false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
			md5.setEncodeHashAsBase64(false);     
			String pwd = md5.encodePassword(passWord, randomCode);
			user.setPassword(pwd);
			user.setRandomCode(randomCode);
			userMapper.updateByPrimaryKeySelective(user);
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}
	public String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sb.toString();  
    }

	@Override
	public ExpertAgainAuditImg checkLoginName(String loginName) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		List<User> list = userMapper.queryByLoginName(loginName);
		if(list!=null && list.size()>0){
			img.setStatus(false);
			img.setMessage("用户名已存在");
			return img;
		}
		img.setStatus(true);
		img.setMessage("用户名可用");
		return img;
	}

	@Override
	public ExpertAgainAuditImg preservationExpertReviewTeam(String groupId) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setGroupId(groupId);
		expertGroup=expertGroupMapper.findGroup(expertGroup);
		if("1".equals(expertGroup.getStatus())){
			img.setStatus(false);
			img.setMessage("请先配置审核组成员");
			return img;
		}
		if("3".equals(expertGroup.getStatus())){
			img.setStatus(false);
			img.setMessage("配置已完成无法进行此操作");
			return img;
		}
		expertGroup.setStatus("3");
		ExpertReviewTeam expertReviewTeam = new ExpertReviewTeam();
		expertReviewTeam.setGroupId(groupId);
		List<ExpertReviewTeam> expertReviewTeamList = expertReviewTeamMapper.getExpertReviewTeamList(expertReviewTeam);
		for (ExpertReviewTeam e : expertReviewTeamList) {
			List<User> list = userMapper.selectByPrimaryKey(e.getUserId());
			if("".equals(list.get(0).getPassword())){
				img.setStatus(false);
				img.setMessage("请为所有审核组成员设置密码");
				return img;
			}
		}
		expertGroupMapper.updateStatus(expertGroup);
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg fingStayReviewExpertList(String userId, Integer pageNum) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}
		ExpertReviewTeam findExpertReviewTeam = expertReviewTeamMapper.findExpertReviewTeam(userId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("batchId", findExpertReviewTeam.getBatchId());
		List<ExpertBatch> list = expertBatchMapper.getAllExpertBatch(map);
		PageInfo< ExpertBatch > result = new PageInfo < ExpertBatch > (list);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(result);
		return img;
	} 
	
}
