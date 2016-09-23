package bss.service.pms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.pms.UpdateFiledMapper;
import bss.model.pms.UpdateFiled;
import bss.service.pms.UpdateFiledService;
@Service
public class UpdateFiledServiceImpl implements UpdateFiledService{

	@Autowired
	private UpdateFiledMapper updateFiledMapper;
	@Override
	public void add(UpdateFiled updateFiled) {
	 
		updateFiledMapper.insertSelective(updateFiled);
	}
	@Override
	public List<UpdateFiled> getAll() {
		return updateFiledMapper.getAll();
	}
	@Override
	public void update(Integer isUpdate,List<String> list) {
		updateFiledMapper.update(isUpdate,list);;
//		updateFiledMapper.updateByPrimaryKeySelective(updateFiled);
	}

}
