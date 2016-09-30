package bss.service.pms.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.pms.UpdateFiledMapper;
import bss.formbean.FiledNameEnum;
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
	public void update(Integer isUpdate,List<String> list,String collectId) {
		updateFiledMapper.update(isUpdate,list,collectId);
//		updateFiledMapper.updateByPrimaryKeySelective(updateFiled);
	}
	@Override
	public List<FiledNameEnum> getAllFiled() {
		List<FiledNameEnum> list=new LinkedList<FiledNameEnum>();
		list.add(FiledNameEnum.BUDGET);
		list.add(FiledNameEnum.CODE);
		list.add(FiledNameEnum.COUNT);
		list.add(FiledNameEnum.DEPARTMENT);
		list.add(FiledNameEnum.GIVEDATE);
		list.add(FiledNameEnum.GOODSUSE);
		return list;
	}
	@Override
	public List<UpdateFiled> query(String collectId,List<String> list) {
		
		return updateFiledMapper.query(collectId,list);
	}

}
