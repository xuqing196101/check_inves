package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.NegotiationReport;


public interface NegotiationReportMapper {
    void deleteByPrimaryKey(String id);
    
    void insertSelective(NegotiationReport record);

    NegotiationReport selectByPrimaryKey(String id);
    
    NegotiationReport selectByProjectId(String id);
     
    void updateByPrimaryKeySelective(NegotiationReport record);

    List<NegotiationReport> listByNegotiation(HashMap<String,Object> map);
}