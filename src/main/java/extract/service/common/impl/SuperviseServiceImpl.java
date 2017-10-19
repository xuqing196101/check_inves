package extract.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extract.dao.common.PersonRelMapper;
import extract.dao.common.SuperviseMapper;
import extract.model.common.Supervise;
import extract.service.common.SuperviseService;

@Service
public class SuperviseServiceImpl implements SuperviseService {

	@Autowired
	private SuperviseMapper superviseMapper;
	
	@Autowired
	private PersonRelMapper personRelMapper;
	
	
	@Override
	public List<Supervise> getList(Supervise user) {
		return superviseMapper.getList(user);
	}

	@Override
	public HashMap<String, String> addPerson(Supervise user) {
		HashMap<String, String> error = new HashMap<>();
		if(StringUtils.isNotEmpty(user.getPersonType())){
			String[] personId = null;
			HashMap<String, Object> map = new HashMap<>();
			if(StringUtils.isNotEmpty(user.getId())){
				personId = user.getId().split(","); //引用的历史人员
				map.put("personIds", personId);
			}
			map.put("recordId", user.getRecordId());
			map.put("personType", user.getPersonType());
			ArrayList<Supervise> arrayList = new ArrayList<>();
			if(user.getList().size()>0){
				//新添加人员
				map.put("personList", user.getList());
				
				int count = 0;
				int i = 0;
				for (Supervise suer : user.getList()) {
					suer.setOrgId(user.getOrgId());
					if(StringUtils.isBlank(suer.getName())|| suer.getName().length()>5){
						error.put("list["+i+"].name", "姓名不能为空且长度小于5");
						count++;
					}if(StringUtils.isBlank(suer.getDuty())||suer.getDuty().length()>50){
						error.put("list["+i+"].duty", "职务不能为空且长度小于50");
						count++;
					}if(StringUtils.isBlank(suer.getCompary())||suer.getCompary().length()>50){
						error.put("list["+i+"].compary", "公司不能为空且长度小于50");
						count++;
					}if(StringUtils.isBlank(suer.getRank())||suer.getRank().length()>50){
						error.put("list["+i+"].rank", "军衔不能为空且长度小于50");
						count++;
					}
					i++;
					if(count>0){
						return error;
					}
					if(superviseMapper.getList(suer).size()<1){
						arrayList.add(suer);
					}
				}
				
				
				/*
				;
				//新添加人员
				map.put("personList", user.getList());
				for (Supervise supervise : user.getList()) {
					if(superviseMapper.getList(supervise).size()<1){
						arrayList.add(supervise);
					}
				}*/
			}
			if(arrayList.size()>0){
				superviseMapper.insertSelectiveAll(arrayList);
			}
			if(null!=personId ||user.getList().size()>0){
				personRelMapper.deleteByMap(map);
				personRelMapper.insertRel(map);
			}else{
				error.put("All", "人员信息有误，监督人员至少一人，且各信息必须完整");
				return error;
			}
		}
		return null;
	}

	
}
