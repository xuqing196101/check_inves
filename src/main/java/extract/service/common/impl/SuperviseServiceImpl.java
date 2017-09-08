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
	public void addPerson(Supervise user) {
		if(StringUtils.isNotEmpty(user.getPersonType())){
			HashMap<String, Object> map = new HashMap<>();
			if(StringUtils.isNotEmpty(user.getId())){
				String[] personId = user.getId().split(","); //引用的历史人员
				map.put("personIds", personId);
			}
			map.put("recordId", user.getRecordId());
			map.put("personType", user.getPersonType());
			if(user.getList().size()>0){
				ArrayList<Supervise> arrayList = new ArrayList<>();
				//新添加人员
				map.put("personList", user.getList());
				for (Supervise supervise : user.getList()) {
					if(superviseMapper.getList(supervise).size()<1){
						arrayList.add(supervise);
					}
				}
				if(arrayList.size()>0){
					superviseMapper.insertSelectiveAll(arrayList);
				}
			}
			personRelMapper.insertRel(map);
		}
	}

	
}
