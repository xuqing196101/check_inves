package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.ProjectAdviceMapper;
import bss.model.ppms.ProjectAdvice;
import bss.service.ppms.ProjectAdviceService;
@Service
public class ProjectAdviceServiceImpl implements ProjectAdviceService{

 @Autowired
 private ProjectAdviceMapper adviceMapper;
  
  @Override
  public ProjectAdvice selectById(String id) {
    return adviceMapper.selectById(id);
  }

  @Override
  public List<ProjectAdvice> findByList(HashMap<String, Object> map) {
    return adviceMapper.findByList(map);
  }

  @Override
  public void insert(ProjectAdvice ProjectAdvice) {
    adviceMapper.insert(ProjectAdvice);
  }

  @Override
  public void update(ProjectAdvice ProjectAdvice) {
    adviceMapper.update(ProjectAdvice);
  }

}
