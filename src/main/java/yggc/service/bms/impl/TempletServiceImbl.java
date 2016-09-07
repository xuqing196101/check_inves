package yggc.service.bms.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import yggc.dao.bms.TempletMapper;
import yggc.model.bms.Templet;
import yggc.service.bms.TempletService;

/**
 * 
 * @Title:TempletServiceImbl
 * @Description:模板管理实现类 
 * @author Liyi
 * @date 2016-9-1上午9:59:10
 *
 */
@Service("templetService")
public class TempletServiceImbl implements TempletService{
	
	/**
	 * 调用模板映射类
	 */
	@Resource
	private TempletMapper templetMapper;
	
	/**
	 * 1.获取所有模板对象
	 */
	@Override
	public List<Templet> getAll() {
		return templetMapper.queryByList();
	}

	/**
	 * 2.添加模板
	 */
	@Override
	public void save(Templet t) {
		t.setIsDeleted(0);      
		t.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		templetMapper.insertSelective(t);
	}

	/**
	 * 3.更新模板
	 */
	@Override
	public void update(Templet t) {
		templetMapper.updateByPrimaryKeySelective(t);
	}

	/**
	 * 4.根据主键查询模板
	 */
	@Override
	public Templet get(String id) {
		return templetMapper.selectByPrimaryKey(id);
	}

	/**
	 * 5.根据主键删除模板
	 */
	@Override
	public void delete(String id) {
		templetMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 6.查询模板条数
	 */
	@Override
	public Integer queryByCount() {
		return queryByCount();
	}
	
}
