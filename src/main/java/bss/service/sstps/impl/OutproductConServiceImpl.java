package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.OutproductConMapper;
import bss.model.sstps.OutproductCon;
import bss.service.sstps.OutproductConService;

@Service("/outproductConService")
public class OutproductConServiceImpl implements OutproductConService {

	@Autowired
	private OutproductConMapper outproductConMapper;
	
	@Override
	public void insert(OutproductCon outproductCon) {
		outproductConMapper.insert(outproductCon);
	}

	@Override
	public List<OutproductCon> selectProduct(OutproductCon outproductCon) {
		return outproductConMapper.selectProduct(outproductCon);
	}

	@Override
	public OutproductCon selectById(String id) {
		return outproductConMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(OutproductCon outproductCon) {
		outproductConMapper.update(outproductCon);
	}

	@Override
	public void delete(String id) {
		outproductConMapper.delete(id);
	}

}
