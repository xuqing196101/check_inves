package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.annotation.AuthValid;
import ses.dao.ems.ExpertMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAgainAuditImg;
import ses.service.ems.ExpertAgainAuditService;
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
	
	@Override
	public ExpertAgainAuditImg addAgainAudit(String ids) {
		//HashMap<String, Object> map = new HashMap<>();
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		String s=ids.replaceAll("＂","\"");
		System.out.println(s);
		List<String> idsList = new ArrayList<String>();
		//map.put("ids", s);
		//List<Expert> list = expertMapper.findAllExpert(map);
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

}
