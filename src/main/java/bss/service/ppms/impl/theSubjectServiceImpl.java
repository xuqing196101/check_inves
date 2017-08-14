package bss.service.ppms.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.theSubjectMapper;
import bss.model.ppms.theSubject;
import bss.service.ppms.theSubjectService;

@Service
public class theSubjectServiceImpl implements theSubjectService {

  @Autowired
  theSubjectMapper theSubjectMapper;
  /**
   * 插入数据
   * @see bss.service.ppms.theSubjectService#insert(bss.model.ppms.theSubject)
   */
  @Override
  public void insert(theSubject subject) {
      subject.setCreatedAt(new Date());
      theSubjectMapper.insertSelective(subject);
  }

  /**
   * 修改
   * @see bss.service.ppms.theSubjectService#update(bss.model.ppms.theSubject)
   */
  @Override
  public void update(theSubject subject) {
    theSubjectMapper.updateByPrimaryKeySelective(subject);
  }

  /**
   * 查询
   * @see bss.service.ppms.theSubjectService#list(bss.model.ppms.theSubject)
   */
  @Override
  public List<theSubject> list(theSubject subject) {
    return  theSubjectMapper.list(subject);   
  }

  /**
   * 批量插入
   * @see bss.service.ppms.theSubjectService#insertList(java.util.List)
   */
  @Override
  public void insertList(List<theSubject> list) {
    theSubjectMapper.insertList(list);
  }

@Override
public List<theSubject> selectBysupplierIdAndPackagesId(HashMap<String, Object> map) {
	return theSubjectMapper.selectBysupplierIdAndPackagesId(map);
	
}

@Override
public List<theSubject> selectByPackagesId(String packagesId) {
	return theSubjectMapper.selectByPackagesId(packagesId);
}

}
