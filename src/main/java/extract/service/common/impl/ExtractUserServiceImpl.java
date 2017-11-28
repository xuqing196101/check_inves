package extract.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import extract.dao.common.ExtractUserMapper;
import extract.dao.common.PersonRelMapper;
import extract.model.common.ExtractUser;
import extract.service.common.ExtractUserService;

@Service
public class ExtractUserServiceImpl implements ExtractUserService {

	@Autowired 
	private ExtractUserMapper extractUserMapper;
	
	@Autowired
	private PersonRelMapper personRelMapper;//记录关联人员信息
	
	@Override
	public List<ExtractUser> getList(ExtractUser user) {
		return extractUserMapper.getList(user);
	}

	
	
	@Override
	public Map<String, String> addPerson(ExtractUser extUser) {
		
		HashMap<String, String> error = new HashMap<>();
		
		if(StringUtils.isNotEmpty(extUser.getPersonType())){
			String[] personId = null;
			HashMap<String, Object> map = new HashMap<>();
			if(StringUtils.isNotEmpty(extUser.getId())){
				//personId = (extUser.getId()+","+user.getId()).split(","); //引用的历史人员
				personId = extUser.getId().split(","); //引用的历史人员
				map.put("personIds", personId);
			}
			map.put("recordId", extUser.getRecordId());
			map.put("personType", extUser.getPersonType());
			
			ArrayList<ExtractUser> arrayList = new ArrayList<>();
			//查询当前登陆用户是否存在抽取人员表中
			/*if(extractUserMapper.selectById(user.getId()).size()<1){
				ExtractUser extractUser = new ExtractUser();
				extractUser.setId(user.getId());
				extractUser.setDuty(user.getDuites());
				extractUser.setRank("**");
				extractUser.setCompary(user.getOrg().getName());
				extractUser.setName(user.getRelName());
				
				arrayList.add(extractUser);
			}*/
			if(extUser.getList().size()>0){
				//新添加人员
				map.put("personList", extUser.getList());
				
				int count = 0;
				int i =0;
				for (ExtractUser extractUser : extUser.getList()) {
					extractUser.setOrgId(extUser.getOrgId());
					if(StringUtils.isBlank(extractUser.getName())|| extractUser.getName().length()>5){
						error.put("list["+i+"].name", "姓名不能为空且长度小于5");
						count++;
					}if(StringUtils.isBlank(extractUser.getDuty())||extractUser.getDuty().length()>50){
						error.put("list["+i+"].duty", "职务不能为空且长度小于50");
						count++;
					}if(StringUtils.isBlank(extractUser.getCompary())||extractUser.getCompary().length()>50){
						error.put("list["+i+"].compary", "公司不能为空且长度小于50");
						count++;
					}if(StringUtils.isBlank(extractUser.getRank())||extractUser.getRank().length()>50){
						error.put("list["+i+"].rank", "军衔不能为空且长度小于50");
						count++;
					}
					i++;
					if(count>0){
						return error;
					}
					List<ExtractUser> list = extractUserMapper.getList(extractUser);
					if(list.size()<1){
						arrayList.add(extractUser);
					}else {
						extractUser.setId(list.get(0).getId());
					}
				}
			}
			if(arrayList.size()>0){
				extractUserMapper.insertSelectiveAll(arrayList);
			}
			int countPerson = 0;
			countPerson = (null==personId?0:personId.length) + (extUser.getList().size()>0?extUser.getList().size():0);
			if(countPerson>0){
				personRelMapper.deleteByMap(map);
				personRelMapper.insertRel(map);
			}else{
				error.put("All", "人员信息有误，抽取人员至少两人，且各信息必须完整");
				return error;
			}
		}
		return null;
	}
	
}
