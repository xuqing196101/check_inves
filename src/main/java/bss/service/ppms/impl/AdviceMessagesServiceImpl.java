package bss.service.ppms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.AdviceMessagesMapper;
import bss.model.ppms.AdviceMessages;
import bss.service.ppms.AdviceMessagesService;

@Service
public class AdviceMessagesServiceImpl implements AdviceMessagesService {
  @Autowired
  private AdviceMessagesMapper adviceMessagesMapper;

  @Override
  public void deleteByPrimaryKey(String id) {
    adviceMessagesMapper.deleteByPrimaryKey(id);
    
  }

  @Override
  public void insert(AdviceMessages record) {
    adviceMessagesMapper.insert(record);
    
  }

  @Override
  public void insertSelective(AdviceMessages record) {
    adviceMessagesMapper.insertSelective(record);
    
  }

  @Override
  public AdviceMessages selectByPrimaryKey(String id) {
    return adviceMessagesMapper.selectByPrimaryKey(id);
  }

  @Override
  public void updateByPrimaryKeySelective(AdviceMessages record) {
    adviceMessagesMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public void updateByPrimaryKey(AdviceMessages record) {
    adviceMessagesMapper.updateByPrimaryKey(record);    
  }
}