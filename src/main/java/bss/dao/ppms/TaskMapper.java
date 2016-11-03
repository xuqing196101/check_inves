package bss.dao.ppms;

import java.util.List;

import bss.model.ppms.Task;

public interface TaskMapper {
    void deleteByPrimaryKey(String id);
    
    void softDelete(String id);

    void insert(Task record);

    void insertSelective(Task record);

    Task selectByPrimaryKey(String id);
     
    List<Task> likeByTask(Task task);
    
    List<Task> listByTask(Task task);
    
    void updateByPrimaryKeySelective(Task record);

    void updateByPrimaryKey(Task record);
    
    void startTask(String id);
    
    List<Task> selectByProject(String id);
    
}