package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.NegotiationReport;


public interface NegotiationReportService {
void deleteByPrimaryKey(String id);
    
    void save(NegotiationReport record);

    NegotiationReport selectById(String id);
    
    NegotiationReport selectByProjectId(String id);
     
    void update(NegotiationReport record);

    List<NegotiationReport> listByNegotiation(HashMap<String,Object> map);
}
