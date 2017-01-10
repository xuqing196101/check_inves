package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.NegotiationMapper;
import bss.model.ppms.Negotiation;
import bss.service.ppms.NegotiationService;

@Service("negotiationService")
public class NegotiationServiceImpl implements NegotiationService {
    @Autowired
    private NegotiationMapper negotiationMapper;

    @Override
    public void deleteByPrimaryKey(String id) {
        
        negotiationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Negotiation record) {

        negotiationMapper.insertSelective(record);
    }

    @Override
    public Negotiation selectById(String id) {
        
        return negotiationMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Negotiation record) {

        negotiationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Negotiation> listByNegotiation(HashMap<String, Object> map) {
        
        return negotiationMapper.listByNegotiation(map);
    }

    @Override
    public Negotiation selectByProjectId(String id) {
        
        return negotiationMapper.selectByProjectId(id);
    }

    @Override
    public Negotiation selectByPackageId(String id) {

        return negotiationMapper.selectByPackageId(id);
    }

}
