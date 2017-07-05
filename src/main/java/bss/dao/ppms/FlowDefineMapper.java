package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.FlowDefine;

public interface FlowDefineMapper {
    
    int delete(String id);

    int insert(FlowDefine fd);

    FlowDefine get(String id);

    int update(FlowDefine fd);
    
    List<FlowDefine> findList(FlowDefine fd);
    
    List<FlowDefine> getFlow(FlowDefine flowDefine);

}