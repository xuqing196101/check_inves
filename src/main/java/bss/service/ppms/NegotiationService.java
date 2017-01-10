package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.Negotiation;

public interface NegotiationService {
void deleteByPrimaryKey(String id);
    
    void save(Negotiation record);

    Negotiation selectById(String id);
    
    Negotiation selectByProjectId(String id);
    
    Negotiation selectByPackageId(String id);
     
    void update(Negotiation record);

    List<Negotiation> listByNegotiation(HashMap<String,Object> map);
}
