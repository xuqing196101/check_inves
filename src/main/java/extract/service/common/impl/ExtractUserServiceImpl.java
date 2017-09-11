package extract.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	public void addPerson(ExtractUser extUser,User user) {
		if(StringUtils.isNotEmpty(extUser.getPersonType())){
			String[] personId = null;
			HashMap<String, Object> map = new HashMap<>();
			if(StringUtils.isNotEmpty(extUser.getId())){
				personId = extUser.getId().split(","); //引用的历史人员
				personId[personId.length] = user.getId();
				map.put("personIds", personId);
			}
			map.put("recordId", extUser.getRecordId());
			map.put("personType", extUser.getPersonType());
			if(extUser.getList().size()>0){
				ArrayList<ExtractUser> arrayList = new ArrayList<>();
				//新添加人员
				map.put("personList", extUser.getList());
				for (ExtractUser extractUser : extUser.getList()) {
					if(extractUserMapper.getList(extractUser).size()<1){
						arrayList.add(extractUser);
					}
					//查询当前登陆用户是否存在抽取人员表中
					if(extractUserMapper.selectById(user.getId()).size()<1){
						arrayList.add(new ExtractUser(user.getId()));
					}
				}
				/*if(arrayList.size()>0){
					extractUserMapper.insertSelectiveAll(arrayList);
				}*/
			}
			if(null!=personId ||null!=extUser.getList()){
				personRelMapper.deleteByRecordId(extUser.getRecordId());
				personRelMapper.insertRel(map);
			}
		}
	}
	
}
