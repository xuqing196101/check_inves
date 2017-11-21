package bss.service.ppms;

import bss.model.ppms.AdviceMessages;


public interface AdviceMessagesService {
    void deleteByPrimaryKey(String id);

    void insert(AdviceMessages record);

    void insertSelective(AdviceMessages record);

    AdviceMessages selectByPrimaryKey(String id);

    void updateByPrimaryKeySelective(AdviceMessages record);

    void updateByPrimaryKey(AdviceMessages record);
}