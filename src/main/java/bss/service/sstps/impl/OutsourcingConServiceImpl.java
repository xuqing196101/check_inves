package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.OutsourcingConMapper;
import bss.model.sstps.OutsourcingCon;
import bss.service.sstps.OutsourcingConService;

@Service("/outsourcingConService")
public class OutsourcingConServiceImpl implements OutsourcingConService {

	@Autowired
	private OutsourcingConMapper outsourcingConMapper;
	
	@Override
	public void insert(OutsourcingCon outsourcingCon) {
		outsourcingConMapper.insert(outsourcingCon);
	}

	@Override
	public List<OutsourcingCon> selectProduct(OutsourcingCon outsourcingCon) {
		return outsourcingConMapper.selectProduct(outsourcingCon);
	}

	@Override
	public OutsourcingCon selectById(String id) {
		return outsourcingConMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(OutsourcingCon outsourcingCon) {
		outsourcingConMapper.update(outsourcingCon);
	}

	@Override
	public void delete(String id) {
		outsourcingConMapper.delete(id);
	}

}
