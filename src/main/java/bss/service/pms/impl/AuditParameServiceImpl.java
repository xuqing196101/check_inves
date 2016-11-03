package bss.service.pms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;
import bss.dao.pms.AuditParamMapper;
import bss.model.pms.AuditParam;
import bss.service.pms.AuditParameService;

import com.github.pagehelper.PageHelper;
/**
 * 
 * @Title: AuditParameServiceImpl
 * @Description:  审核参数设置业务实现类
 * @author Li Xiaoxiao
 * @date  2016年11月2日,上午10:59:37
 *
 */
@Service
public class AuditParameServiceImpl implements AuditParameService {

	@Autowired
	private AuditParamMapper auditParamMapper;
	@Override
	public void add(AuditParam auditParam) {
		auditParamMapper.insertSelective(auditParam);

	}

	@Override
	public void update(AuditParam auditParam) {
		auditParamMapper.updateByPrimaryKeySelective(auditParam);

	}

	@Override
	public List<AuditParam> query(AuditParam auditParam,Integer page) {
		List<AuditParam> list = auditParamMapper.query(auditParam);
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return list;
	}

	@Override
	public AuditParam queryById(String id) {
		 
		return auditParamMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		auditParamMapper.deleteByPrimaryKey(id);
	}

	
}
