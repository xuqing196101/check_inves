package ses.service.ems.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.UserMapper;
import ses.dao.ems.BatchTemporaryMapper;
import ses.dao.ems.ExpertAuditOpinionMapper;
import ses.dao.ems.ExpertBatchDetailsMapper;
import ses.dao.ems.ExpertBatchMapper;
import ses.dao.ems.ExpertGroupMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ExpertReviewTeamMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.RoleUser;
import ses.model.bms.User;
import ses.model.ems.BatchTemporary;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAgainAuditImg;
import ses.model.ems.ExpertAuditOpinion;
import ses.model.ems.ExpertBatch;
import ses.model.ems.ExpertBatchDetails;
import ses.model.ems.ExpertGroup;
import ses.model.ems.ExpertReviewTeam;
import ses.service.ems.ExpertAgainAuditService;
import ses.util.DictionaryDataUtil;
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
	private DictionaryDataMapper dictionaryDataMapper;
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
	@Autowired
	private ExpertAuditOpinionMapper expertAuditOpinionMapper;
	@Autowired
	private BatchTemporaryMapper batchTemporaryMapper;
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
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("batchNumber", batchNumber);
		List<ExpertBatch> batchList = expertBatchMapper.getAllExpertBatch(map);
		if(batchList.size()>0){
			img.setStatus(true);
			img.setMessage("已存在该批次编号！");
			return img;
		}
		ExpertBatch expertBatch = new ExpertBatch();
		expertBatch.setBatchId(WfUtil.createUUID());
		expertBatch.setBatchName(batchName);
		expertBatch.setBatchNumber(batchNumber);
		expertBatch.setCreatedAt(new Date());
		expertBatch.setUpdatedAt(new Date());
		
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
		for (Expert ex : list) {
			if(!"1".equals(ex.getStatus())){
				img.setStatus(true);
				img.setMessage("请选择待分配专家");
				return img;
			}
		}
		expertBatchMapper.insert(expertBatch);
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
		/*if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}*/
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		expertBatchDetails.setBatchId(batchId);
		expertBatchDetails.setStatus(status);
		//expertBatchDetails.setSort("1");
		Map<String,Object> map = new HashMap<String,Object>();
		List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		if(list.size()>0){
			map.put("batchId", list.get(0).getBatchId());
			map.put("batchName", list.get(0).getBatchName());
			for (ExpertBatchDetails e : list) {
				StringBuffer expertType = new StringBuffer();
	            if(e.getExpertsTypeId() != null) {
	                for(String typeId: e.getExpertsTypeId().split(",")) {
	                    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(typeId);
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
	      			DictionaryData expertsFrom = dictionaryDataMapper.selectByPrimaryKey(e.getExpertsFrom());
	      			e.setExpertsFrom(expertsFrom.getName());
	      		}
			}
		}else{
			if(status != null){
				img.setStatus(false);
				img.setMessage("全部专家已分组完成");
				return img;
			}
		}
		
		//PageInfo< ExpertBatchDetails > result = new PageInfo < ExpertBatchDetails > (list);
		map.put("list", list);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(map);
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
			if("3".equals(group.getStatus())){
				ExpertReviewTeam expertReviewTeam = new ExpertReviewTeam();
				expertReviewTeam.setGroupId(group.getGroupId());
				List<ExpertReviewTeam> teamList = expertReviewTeamMapper.getExpertReviewTeamList(expertReviewTeam);
				String team="";
				for (ExpertReviewTeam t : teamList) {
					team=team+t.getRelName()+",";
				}
				map.put("team", team.subSequence(0, team.length()-1));
			}
			ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
			expertBatchDetails.setGroupId(group.getGroupId());
			List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
			for (ExpertBatchDetails e : list) {

				StringBuffer expertType = new StringBuffer();
	            if(e.getExpertsTypeId() != null) {
	                for(String typeId: e.getExpertsTypeId().split(",")) {
	                    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(typeId);
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
	      			DictionaryData expertsFrom = dictionaryDataMapper.selectByPrimaryKey(e.getExpertsFrom());
	      			e.setExpertsFrom(expertsFrom.getName());
	      		}
			
			}
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
		if(batchDetailslist.size()>0){
			if(batchDetailslist.get(0).getGroupId()!=null){
				ExpertGroup expertGroup = new ExpertGroup();
				expertGroup.setGroupId(batchDetailslist.get(0).getGroupId());
				ExpertGroup group = expertGroupMapper.findGroup(expertGroup);
				if(!"1".equals(group.getStatus())){
					img.setStatus(false);
					img.setMessage("当前组不能删除专家");
					return img;
				}
			}
		}
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
	public ExpertAgainAuditImg findExpertReviewTeam(String groupId,Integer pageNum) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setGroupId(groupId);
		List<ExpertGroup> group = expertGroupMapper.getGroup(expertGroup);
		expertGroup=group.get(0);
		/*if("3".equals(expertGroup.getStatus())){
			img.setStatus(false);
			img.setMessage("当前组已配置完成");
			return img;
		}*/
		ExpertReviewTeam expertReviewTeam = new ExpertReviewTeam();
		expertReviewTeam.setGroupId(groupId);
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}
		List<ExpertReviewTeam> list = expertReviewTeamMapper.getExpertReviewTeamList(expertReviewTeam);
		int password=0;
		String userName="";
		for (ExpertReviewTeam e : list) {
			if(e.getPassWord()!=null){
				password=1;
				e.setPassWord(null);
			}
			if(e.getIsDeleted()==1){
				e.setLoginName(e.getLoginName().substring(0, e.getLoginName().length()-21));
			}
			userName=e.getLoginName();
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		PageInfo< ExpertReviewTeam > result = new PageInfo < ExpertReviewTeam > (list);
		map.put("groupId", groupId);
		map.put("groupStatus", expertGroup.getStatus());
		map.put("password", password);
		map.put("userName", userName);
		map.put("list", result);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(map);
		return img;
	}

	@Override
	public ExpertAgainAuditImg addExpertReviewTeam(String userName,String password,List<Map<String, String>> e) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		List<User> list = userMapper.queryByLoginName(userName);
		User user = new User();
		if(list!=null && list.size()>0){
			user=list.get(0);
			expertReviewTeamMapper.deleteGroupreReviewTeam(e.get(0).get("groupId"));
		}else{
			user.setId(WfUtil.createUUID());
			user.setLoginName(userName);
			user.setRelName(userName);
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			user.setIsDeleted(0);
			user.setTypeName("6");
			if(password!=null){
				//生成15位随机码
				String randomCode = generateString(15);
				Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
				// false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
				md5.setEncodeHashAsBase64(false);     
				String pwd = md5.encodePassword(password, randomCode);
				user.setPassword(pwd);
				user.setRandomCode(randomCode);
			}
			//user.setTypeId(expertReviewTeam.getId());
			String ipAddressType = PropUtil.getProperty("ipAddressType");
			user.setNetType(Integer.valueOf(ipAddressType));
			userMapper.saveUser(user);
		}
		
		
		
		for (Map<String, String> map : e) {
			ExpertReviewTeam expertReviewTeam = new ExpertReviewTeam();
			expertReviewTeam.setGroupId(map.get("groupId"));
			//expertReviewTeam.setLoginName(map.get("loginName"));
			expertReviewTeam.setRelName(map.get("relName"));
			expertReviewTeam.setOrgName(map.get("orgName"));
			expertReviewTeam.setDuties(map.get("duties"));
			expertReviewTeam.setId(WfUtil.createUUID());
			
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
			expertGroup.setStatus("3");
			expertGroupMapper.updateStatus(expertGroup);
			expertReviewTeamMapper.insert(expertReviewTeam);
		}
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
	public ExpertAgainAuditImg setUpPassword(String groupId,String passWord) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		//String[] split = ids.split(",");
		ExpertReviewTeam expertReviewTeam=new ExpertReviewTeam();
		expertReviewTeam.setGroupId(groupId);
		List<ExpertReviewTeam> reviewTeamList = expertReviewTeamMapper.getExpertReviewTeamList(expertReviewTeam);
		if(reviewTeamList.size()>0){
			expertReviewTeam=reviewTeamList.get(0);
			List<User> list = userMapper.findById(expertReviewTeam.getUserId());
			User user =list.get(0);
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
	public ExpertAgainAuditImg fingStayReviewExpertList(String userId,String batchName,Date createdAt, Integer pageNum) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}
		List<ExpertReviewTeam> findExpertReviewTeam = expertReviewTeamMapper.findExpertReviewTeam(userId);
		if(findExpertReviewTeam.size()<0){
			img.setStatus(true);
			img.setMessage("操作有误");
			return img;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("batchId", findExpertReviewTeam.get(0).getBatchId());
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
	public ExpertAgainAuditImg fingStayReviewExpertDetailsList(String userId,String batchId, Integer pageNum) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}
		List<ExpertReviewTeam> findExpertReviewTeam = expertReviewTeamMapper.findExpertReviewTeam(userId);
		if(findExpertReviewTeam.size()<0){
			img.setStatus(true);
			img.setMessage("操作有误");
			return img;
		}
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		expertBatchDetails.setBatchId(batchId);
		expertBatchDetails.setGroupId(findExpertReviewTeam.get(0).getGroupId());
		List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		Map<String,Object> map = new HashMap<String,Object>();
		ExpertAuditOpinion expertAuditOpinion = new ExpertAuditOpinion();
		if(list.size()>0){
			map.put("batchId", list.get(0).getBatchId());
			map.put("batchName", list.get(0).getBatchName());
			// 供应商系统key文件上传key
			Integer sysKey = common.constant.Constant.EXPERT_SYS_KEY;
			// 定义文件上传类型
			DictionaryData dictionaryData = DictionaryDataUtil
					.get(synchro.util.Constant.EXPERT_REVIEW_APPROVE);
			if (dictionaryData != null) {
				map.put("typeId", dictionaryData.getId());
			}
			map.put("sysKey", sysKey);
			for (ExpertBatchDetails e : list) {
				StringBuffer expertType = new StringBuffer();
	            if(e.getExpertsTypeId() != null) {
	                for(String typeId: e.getExpertsTypeId().split(",")) {
	                    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(typeId);
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
	      			DictionaryData expertsFrom = dictionaryDataMapper.selectByPrimaryKey(e.getExpertsFrom());
	      			e.setExpertsFrom(expertsFrom.getName());
	      		}
	      		
	      		//查询附件是否被下载
	      		expertAuditOpinion.setExpertId(e.getExpertId());
	      		expertAuditOpinion.setFlagTime(1);
	    		ExpertAuditOpinion selectByExpertId = expertAuditOpinionMapper.selectByExpertId(expertAuditOpinion);
	    		if(selectByExpertId != null && selectByExpertId.getIsDownLoadAttch() != null && selectByExpertId.getIsDownLoadAttch() == 1){
	    			e.setIsDownload(1);
	    		}else{
	    			e.setIsDownload(0);
	    		}
			}
		}		
		PageInfo< ExpertBatchDetails > result = new PageInfo < ExpertBatchDetails > (list);
		map.put("list", result);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(map);
		return img;
	}

	@Override
	public ExpertAgainAuditImg checkGroupStatus(String expertId) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		expertBatchDetails.setExpertId(expertId);
		expertBatchDetails=expertBatchDetailsMapper.findExpertBatchDetails(expertBatchDetails);
		ExpertGroup expertGroup = new ExpertGroup();
		expertGroup.setGroupId(expertBatchDetails.getGroupId());
		expertGroup=expertGroupMapper.findGroup(expertGroup);
		if(!"3".equals(expertGroup.getStatus())){
			img.setStatus(false);
			img.setMessage("当前组还未结束分配无法审核");
			return img;
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public void handleExpertReviewTeam(String expertId) {
		// TODO Auto-generated method stub
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		expertBatchDetails.setExpertId(expertId);
		expertBatchDetails=expertBatchDetailsMapper.findExpertBatchDetails(expertBatchDetails);
		List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		boolean status=true;
		for (ExpertBatchDetails e : list) {
			Expert expert = expertMapper.selectByPrimaryKey(e.getExpertId());
			if(!"5".equals(expert.getStatus())&&!"6".equals(expert.getStatus())&&!"10".equals(expert.getStatus())){
				status=false;
			}
		}
		if(status){
			ExpertReviewTeam expertReviewTeam = new ExpertReviewTeam();
			expertReviewTeam.setGroupId(expertBatchDetails.getGroupId());
			List<ExpertReviewTeam> expertReviewTeamList = expertReviewTeamMapper.getExpertReviewTeamList(expertReviewTeam);
			for (ExpertReviewTeam team : expertReviewTeamList) {
				List<User> userList = userMapper.selectByPrimaryKey(team.getUserId());
				if(userList.size()>0){
					User user=userList.get(0);
					user.setIsDeleted(1);
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
			    	String date = format.format(new Date());
			    	StringBuffer suffix = new StringBuffer();
			    	suffix.append("_del_bak_");
			    	suffix.append(date);
			    	user.setLoginName(user.getLoginName()+suffix.toString());
			    	userMapper.updateByPrimaryKeySelective(user);
				}
			}
		}
	}

	@Override
	public ExpertAgainAuditImg automaticGrouping(String batchId, int count) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		expertBatchDetails.setStatus("14");
		expertBatchDetails.setBatchId(batchId);
		expertBatchDetails.setSort("1");
		List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		if(list.size()>0){
			int number=list.size()/count;
			if(number>0){
				int eCount=0;
				int gCount=0;
				String ids="";
				for (ExpertBatchDetails e : list) {
					ids=ids+e.getId()+",";
					eCount++;
					if(eCount==number){
						expertGrouping(batchId, ids.substring(0, ids.length()-1));
						ids="";
						eCount=0;
						gCount++;
						if(gCount==count){
							break;
						}
					}
				}
				img.setStatus(true);
				img.setMessage("操作成功");
			}else{
				img.setStatus(false);
				img.setMessage("当前批次剩余待分组专家不满足要求的分组数,请降低分组数量");
			}
		}else{
			img.setStatus(false);
			img.setMessage("当前批次剩余待分组专家不足,请手动分配");
		}
		return img;
	}

	@Override
	public ExpertAgainAuditImg selectReviewTeamAll() {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		List<ExpertReviewTeam> list2 = new ArrayList<ExpertReviewTeam>();
		List<ExpertReviewTeam> list = expertReviewTeamMapper.selectReviewTeamAll();
		Set<ExpertReviewTeam> set = new  LinkedHashSet<ExpertReviewTeam>(list); 
		list2.addAll(set);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(list2);
		return img;
	} 
	public List<ExpertBatchDetails> findBatchDetailsList(String batchId) {
		ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
		expertBatchDetails.setBatchId(batchId);
		List<ExpertBatchDetails> list = expertBatchDetailsMapper.getExpertBatchDetails(expertBatchDetails);
		int i=1;
		if(list.size()>0){
			for (ExpertBatchDetails e : list) {
				e.setCount(i+"");
				i++;
				StringBuffer expertType = new StringBuffer();
	            if(e.getExpertsTypeId() != null) {
	                for(String typeId: e.getExpertsTypeId().split(",")) {
	                    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(typeId);
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
	      			DictionaryData expertsFrom = dictionaryDataMapper.selectByPrimaryKey(e.getExpertsFrom());
	      			e.setExpertsFrom(expertsFrom.getName());
	      		}
	      	  //专家复审意见
	      		ExpertAuditOpinion expertAuditOpinion = new ExpertAuditOpinion();
	      		expertAuditOpinion.setExpertId(e.getExpertId());
	      		expertAuditOpinion.setFlagTime(1);
	      		ExpertAuditOpinion opinion = expertAuditOpinionMapper.selectByExpertId(expertAuditOpinion);
	      		if(opinion!=null){
	      			e.setAuditTemporary(opinion.getOpinion());
	      		}else{
	      			e.setAuditTemporary("");
	      		}
	      		
			}
		}
		return list;
		
	}
	public ExpertAgainAuditImg selectBatchTemporary(Expert expert) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		List<BatchTemporary> list = batchTemporaryMapper.selectBatchTemporaryAll(expert);
		if(list.size()>0){
			for (BatchTemporary e : list) {
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
				if(e.getAuditAt() !=null){
					e.setUpdateTime(dateFormater.format(e.getAuditAt()));
				}
				StringBuffer expertType = new StringBuffer();
	            if(e.getExpertsTypeId() != null) {
	                for(String typeId: e.getExpertsTypeId().split(",")) {
	                    DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(typeId);
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
	      			DictionaryData expertsFrom = dictionaryDataMapper.selectByPrimaryKey(e.getExpertsFrom());
	      			e.setExpertsFrom(expertsFrom.getName());
	      		}
			}
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(list);
		return img;
	}
	public ExpertAgainAuditImg addBatchTemporary(String expertId,String ids) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(ids!=null){
			String[] split = ids.split(",");
			for (String string : split) {
				BatchTemporary t = new BatchTemporary();
				t.setExpertId(expertId);
				t.setBatchExpertId(string);
				t.setCreatedAt(new Date());
				t.setUpdatedAt(new Date());
				batchTemporaryMapper.addBatchTemporary(t);
			}
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}
	public void deleteByPrimaryKey() {
		batchTemporaryMapper.deleteByPrimaryKey();
	}
	public ExpertAgainAuditImg deleteBatchTemporary(String ids) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		if(ids!=null){
			String[] split = ids.split(",");
			for (String string : split) {
				batchTemporaryMapper.deleteBatchTemporary(string);
			}
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}
}
