package ses.service.bms.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.TempletMapper;
import ses.model.bms.Templet;
import ses.service.bms.TempletService;
import ses.util.PropertiesUtil;


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
	public List<Templet> getAll(Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
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
		t.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
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
		Templet t = new Templet();
		t.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		t.setId(id);
		t.setIsDeleted(1);
		templetMapper.updateByPrimaryKeySelective(t);
		
		/*templetMapper.deleteByPrimaryKey(id);*/
	}

	/**
	 * 6.查询模板条数
	 */
	@Override
	public Integer queryByCount() {
		return templetMapper.queryByCount();
	}

	/**
	 * 7.条件查询
	 */
	@Override
	public List<Templet> search(Integer pageNum, Templet t) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return templetMapper.selectByType(t);
	}
	
}
