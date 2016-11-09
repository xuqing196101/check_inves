package bss.dao.ppms;

import java.util.List;

import bss.model.ppms.FlowExecute;

public interface FlowExecuteMapper {
    
    int delete(String id);

    int insert(FlowExecute fe);

    FlowExecute get(String id);

    int update(FlowExecute fe);

    List<FlowExecute> findList(FlowExecute fe);
}