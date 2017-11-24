package bss.service.ppms;

import java.util.List;

import bss.model.ppms.AdviceMessages;


public interface AdviceMessagesService {
    void deleteByPrimaryKey(String id);

    void insert(AdviceMessages record);

    void insertSelective(AdviceMessages record);

    AdviceMessages selectByPrimaryKey(String id);

    void updateByPrimaryKeySelective(AdviceMessages record);

    void updateByPrimaryKey(AdviceMessages record);
    
    List<AdviceMessages> selectbyList(AdviceMessages record);
}