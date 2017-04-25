package bss.service.pms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import bss.dao.pms.AuditPersonMapper;
import bss.model.pms.AuditPerson;
import bss.service.pms.AuditPersonService;
/**
 * 
 * @Title: AuditPersonServiceImpl
 * @Description:  审核人员设置业务实现类
 * @author Li Xiaoxiao
 * @date  2016年9月22日,下午3:09:07
 *
 */
@Service
public class AuditPersonServiceImpl implements AuditPersonService{

	@Autowired
	private AuditPersonMapper auditPersonMapper;
	
	public void add(AuditPerson auditPerson) {
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		auditPerson.setId(id);
		auditPersonMapper.insertSelective(auditPerson);
		
	}

	@Override
	public List<AuditPerson> query(AuditPerson auditPerson, Integer page) {
		PageHelper.startPage(page,10);
		return auditPersonMapper.query(auditPerson);
	}

	
	@Override
	public int findUserByCondition(HashMap<String, Object> map) {
		return auditPersonMapper.findUserByCondition(map);
	}

    @Override
    public List<AuditPerson> selectByMap(HashMap<String, Object> map) {
        
        return auditPersonMapper.selectByMap(map);
    }

	@Override
	public List<AuditPerson> queryByUserIdAndCid(String userId, String collectId) {
		// TODO Auto-generated method stub
		return auditPersonMapper.queryByUserIdAndCid(userId, collectId);
	}

	@Override
	public void updateByPrimaryKeySelective(AuditPerson record) {
		 
		auditPersonMapper.updateByPrimaryKeySelective(record);
	}

}
