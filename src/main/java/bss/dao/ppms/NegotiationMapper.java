package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.Negotiation;

public interface NegotiationMapper {
    void deleteByPrimaryKey(String id);
    
    void insertSelective(Negotiation record);

    Negotiation selectByPrimaryKey(String id);
    
    Negotiation selectByProjectId(String id);
    
    Negotiation selectByPackageId(String id);
     
    void updateByPrimaryKeySelective(Negotiation record);

    List<Negotiation> listByNegotiation(HashMap<String,Object> map);
}