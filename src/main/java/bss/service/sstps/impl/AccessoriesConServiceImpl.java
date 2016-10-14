package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.AccessoriesConMapper;
import bss.model.sstps.AccessoriesCon;
import bss.service.sstps.AccessoriesConService;

@Service("/accessoriesConService")
public class AccessoriesConServiceImpl implements AccessoriesConService {

	@Autowired
	private AccessoriesConMapper accessoriesConMapper;
	
	/**
	 * 新增
	 */
	@Override
	public void insert(AccessoriesCon accessoriesCon) {
		accessoriesConMapper.insert(accessoriesCon);
	}

	/**
	 * 根据产品查询
	 */
	@Override
	public List<AccessoriesCon> selectProduct(AccessoriesCon accessoriesCon) {
		return accessoriesConMapper.selectProduct(accessoriesCon);
	}

	/**
	 * 根据ID查询
	 */
	@Override
	public AccessoriesCon selectById(String id) {
		return accessoriesConMapper.selectByPrimaryKey(id);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(AccessoriesCon accessoriesCon) {
		accessoriesConMapper.update(accessoriesCon);
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(String id) {
		accessoriesConMapper.delete(id);
	}

}
